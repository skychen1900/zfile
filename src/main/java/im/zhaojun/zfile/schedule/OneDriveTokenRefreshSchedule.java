package im.zhaojun.zfile.schedule;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.alibaba.fastjson.JSON;

import im.zhaojun.zfile.context.DriveContext;
import im.zhaojun.zfile.model.entity.DriveConfig;
import im.zhaojun.zfile.model.enums.StorageTypeEnum;
import im.zhaojun.zfile.service.DriveConfigService;
import im.zhaojun.zfile.service.base.AbstractOneDriveServiceBase;
import lombok.extern.slf4j.Slf4j;

/**
 * 计划任务工具类
 * 
 * @author zhaojun
 */
@Configuration
@EnableScheduling
@Slf4j
public class OneDriveTokenRefreshSchedule {

	@Resource
	private DriveConfigService driveConfigService;

	@Resource
	private DriveContext driveContext;

	/**
	 * 项目启动 30 秒后, 每 15 分钟执行一次刷新 OneDrive Token 的定时任务.
	 */
	@Scheduled(fixedRate = 1000 * 60 * 10, initialDelay = 1000 * 30)
	public void autoRefreshOneDriveToken() {

		try {
			// log.debug("尝试调用 OneDrive 自动刷新 AccessToken 定时任务");
			log.debug("Attempt to call the OneDrive auto-refresh AccessToken cron task");

			List<DriveConfig> driveConfigList = driveConfigService.findByType(StorageTypeEnum.ONE_DRIVE);
			driveConfigList.addAll(driveConfigService.findByType(StorageTypeEnum.ONE_DRIVE_CHINA));
			driveConfigList.addAll(driveConfigService.findByType(StorageTypeEnum.SHAREPOINT_DRIVE));
			driveConfigList.addAll(driveConfigService.findByType(StorageTypeEnum.SHAREPOINT_DRIVE_CHINA));

			driveConfigList.forEach(driveConfig -> {
				try {
					AbstractOneDriveServiceBase driveService = (AbstractOneDriveServiceBase) driveContext
							.get(driveConfig.getId());
					driveService.refreshOneDriveToken();
//					log.info("尝试刷新 OneDrive Token, DriveInfo: {}", JSON.toJSONString(driveConfig));
					log.info("Attempt refresh OneDrive Token, DriveInfo: {}", JSON.toJSONString(driveConfig));
				} catch (Exception e) {
//					log.error("刷新 OneDrive Token 失败, DriveInfo: {}", JSON.toJSONString(driveConfig), e);
					log.error("Refresh OneDrive Token Failure, DriveInfo: {}", JSON.toJSONString(driveConfig), e);
				}
			});

		} catch (Throwable e) {
//			log.error("尝试调用 OneDrive 自动刷新 AccessToken 定时任务出现未知异常", e);
			log.error(
					"An unknown exception occurs when trying to call OneDrive to automatically refresh the AccessToken scheduled task",
					e);
		}

	}

}