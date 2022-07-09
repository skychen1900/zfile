package im.zhaojun.zfile.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;

/**
 * @author zhaojun
 */
@Entity(name = "system_config")
@Data
public class SystemConfig {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "k")
	private String key;

	@Lob
	private String value;

	private String remark;

}