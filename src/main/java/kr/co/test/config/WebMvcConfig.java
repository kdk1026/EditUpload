package kr.co.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <pre>
 * 개정이력
 * -----------------------------------
 * 2021. 7. 11. 김대광	최초작성
 * </pre>
 * 
 *
 * @author 김대광
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	private static final long MAX_AGE_SECONDS = 3600;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry
			.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("GET", "POST", "PUT", "DELETE")
			.allowedHeaders("*")
			.maxAge(MAX_AGE_SECONDS);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/upload/**")
			.addResourceLocations("file:/D:/upload/");
	}
	
	
	
}
