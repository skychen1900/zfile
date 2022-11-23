package im.zhaojun.zfile.config;

import javax.servlet.Filter;

import com.amazonaws.xray.javax.servlet.AWSXRayServletFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

	@Bean
	public Filter TracingFilter() {
		return new AWSXRayServletFilter("Zfile");
	}
}
