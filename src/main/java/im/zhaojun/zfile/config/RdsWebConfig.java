package im.zhaojun.zfile.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class RdsWebConfig {
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		logger.info("Initializing PostgreSQL datasource");
		return DataSourceBuilder.create()
				.driverClassName("org.postgresql.Driver")
				.url("jdbc:postgresql://" + System.getenv("RDS_HOSTNAME") + ":" + System.getenv("RDS_PORT") + "/ebdb")
				.username(System.getenv("RDS_USERNAME"))
				.password(System.getenv("RDS_PASSWORD"))
				.build();
	}
}
