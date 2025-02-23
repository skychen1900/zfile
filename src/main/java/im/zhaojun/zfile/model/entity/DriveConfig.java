package im.zhaojun.zfile.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import im.zhaojun.zfile.model.enums.StorageTypeEnum;
import lombok.Data;

/**
 * 驱动器
 *
 * @author zhaojun
 */
@Entity(name = "driver_config")
@Data
public class DriveConfig {

	@Id
	private Integer id;

	private Boolean enable;

	private String name;

	private Boolean enableCache;

	private Boolean autoRefreshCache;

	private StorageTypeEnum type;

	private Boolean searchEnable;

	private Boolean searchIgnoreCase;

	private Boolean searchContainEncryptedFile;

	private Integer orderNum;

	private Boolean defaultSwitchToImgMode;

}