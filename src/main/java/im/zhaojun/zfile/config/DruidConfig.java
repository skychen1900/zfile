package im.zhaojun.zfile.config;

// application.yml に設定を参照
//@Configuration
public class DruidConfig {

	// 配置 Druid 监控管理后台的Servlet；
	// 内置 Servlet 容器时没有web.xml文件，所以使用 Spring Boot 的注册 Servlet 方式
	//	@Bean
	//	public ServletRegistrationBean<StatViewServlet> statViewServlet() {
	//		ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(),
	//				"/druid/*");
	//
	//		// 这些参数可以在 com.alibaba.druid.support.http.StatViewServlet
	//		// 的父类 com.alibaba.druid.support.http.ResourceServlet 中找到
	//		Map<String, String> initParams = new HashMap<>();
	//		initParams.put("loginUsername", "admin"); // 后台管理界面的登录账号
	//		initParams.put("loginPassword", "admin"); // 后台管理界面的登录密码
	//
	//		// 后台允许谁可以访问
	//		// initParams.put("allow", "localhost")：表示只有本机可以访问
	//		// initParams.put("allow", "")：为空或者为null时，表示允许所有访问
	//		initParams.put("allow", "");
	//		// deny：Druid 后台拒绝谁访问
	//		// initParams.put("deny", "192.168.1.20");表示禁止此ip访问
	//
	//		// 设置初始化参数
	//		bean.setInitParameters(initParams);
	//		return bean;
	//	}

	// 配置 Druid 监控 之 web 监控的 filter
	// WebStatFilter：用于配置Web和Druid数据源之间的管理关联监控统计
	//	@Bean
	//	public FilterRegistrationBean<WebStatFilter> webStatFilter() {
	//		FilterRegistrationBean<WebStatFilter> bean = new FilterRegistrationBean<>();
	//		bean.setFilter(new WebStatFilter());
	//
	//		// exclusions：设置哪些请求进行过滤排除掉，从而不进行统计
	//		Map<String, String> initParams = new HashMap<>();
	//		initParams.put("exclusions", "*.js,*.gif,*.jpg,*png,*.ico,*.css,/druid/*,/jdbc/*");
	//		bean.setInitParameters(initParams);
	//
	//		// "/*" 表示过滤所有请求
	//		bean.setUrlPatterns(Arrays.asList("/*"));
	//		return bean;
	//	}
}
