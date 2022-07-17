package im.zhaojun.zfile.context;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import im.zhaojun.zfile.exception.InvalidDriveException;
import im.zhaojun.zfile.model.entity.DriveConfig;
import im.zhaojun.zfile.model.enums.StorageTypeEnum;
import im.zhaojun.zfile.service.DriveConfigService;
import im.zhaojun.zfile.service.base.AbstractBaseFileService;
import im.zhaojun.zfile.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;

/**
 * 每个驱动器对应一个 Service, 其中初始化好了与对象存储的连接信息. 此驱动器上下文环境用户缓存每个 Service, 避免重复创建连接.
 * 
 * @author zhaojun
 */
@Component
@DependsOn("springContextHolder")
@Slf4j
public class DriveContext implements ApplicationContextAware {

	/**
	 * Map<Integer, AbstractBaseFileService> Map<驱动器 ID, 驱动器连接 Service>
	 */
	private static Map<Integer, AbstractBaseFileService> drivesServiceMap = new ConcurrentHashMap<>();

	@Resource
	private DriveConfigService driveConfigService;

	/**
	 * 项目启动时, 自动调用数据库已存储的所有驱动器进行初始化.
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		List<DriveConfig> list = driveConfigService.list();
		for (DriveConfig driveConfig : list) {
			try {
				init(driveConfig.getId());
				log.info("At boot, initializing the drive succeeded. The drive info is : {}",
						JSON.toJSONString(driveConfig));
			} catch (Exception e) {
				log.error("At boot, initializing the drive failed. The drive info is : {}",
						JSON.toJSONString(driveConfig), e);
			}
		}
	}

	/**
	 * 初始化指定驱动器的 Service, 添加到上下文环境中.
	 *
	 * @param driveId 驱动器 ID.
	 */
	public void init(Integer driveId) {
		AbstractBaseFileService baseFileService = getBeanByDriveId(driveId);
		if (baseFileService != null) {
			if (log.isDebugEnabled()) {
				log.debug("try initializing the drive, driveId: {}", driveId);
			}
			baseFileService.init(driveId);
			if (log.isDebugEnabled()) {
				log.debug("Initialized drive succeeded, driveId: {}", driveId);
			}
			drivesServiceMap.put(driveId, baseFileService);
		}
	}

	/**
	 * 获取指定驱动器的 Service.
	 *
	 * @param driveId 驱动器 ID
	 *
	 * @return 驱动器对应的 Service
	 */
	public AbstractBaseFileService get(Integer driveId) {
		AbstractBaseFileService abstractBaseFileService = drivesServiceMap.get(driveId);
		if (abstractBaseFileService == null) {
			throw new InvalidDriveException("此驱动器不存在或初始化失败, 请检查后台参数配置");
		}
		return abstractBaseFileService;
	}

	/**
	 * 销毁指定驱动器的 Service.
	 *
	 * @param driveId 驱动器 ID
	 */
	public void destroy(Integer driveId) {
		if (log.isDebugEnabled()) {
			log.debug("clean up the drive context object, driveId: {}", driveId);
		}
		drivesServiceMap.remove(driveId);
	}

	/**
	 * 获取指定驱动器对应的 Service, 状态为未初始化
	 *
	 * @param driveId 驱动器 ID
	 *
	 * @return 驱动器对应未初始化的 Service
	 */
	private AbstractBaseFileService getBeanByDriveId(Integer driveId) {
		StorageTypeEnum storageTypeEnum = driveConfigService.findStorageTypeById(driveId);
		Map<String, AbstractBaseFileService> beansOfType = SpringContextHolder
				.getBeansOfType(AbstractBaseFileService.class);
		for (AbstractBaseFileService value : beansOfType.values()) {
			if (Objects.equals(value.getStorageTypeEnum(), storageTypeEnum)) {
				return SpringContextHolder.getBean(value.getClass());
			}
		}
		return null;
	}

	/**
	 * 更新上下文环境中的驱动器 ID
	 *
	 * @param updateId 驱动器原 ID
	 *
	 * @param newId    驱动器新 ID
	 */
	public void updateDriveId(Integer updateId, Integer newId) {
		AbstractBaseFileService fileService = drivesServiceMap.remove(updateId);
		fileService.setDriveId(newId);
		drivesServiceMap.put(newId, fileService);
	}

}