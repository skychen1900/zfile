package im.zhaojun.zfile.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import im.zhaojun.zfile.model.enums.StorageTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhaojun
 */
@Entity(name = "storage_config")
@Data
@NoArgsConstructor
public class StorageConfig {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private StorageTypeEnum type;

	@Column(name = "k")
	private String key;

	private String title;

	@Lob
	private String value;

	private Integer driveId;

	public StorageConfig(String key, String title) {
		this.key = key;
		this.title = title;
	}

}