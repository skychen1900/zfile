package im.zhaojun.zfile.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * @author zhaojun
 */
@Entity(name = "filter_config")
@Data
public class FilterConfig {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer driveId;

	private String expression;

}