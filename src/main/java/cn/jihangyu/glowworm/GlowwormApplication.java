package cn.jihangyu.glowworm;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.didispace.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;

@SpringBootApplication
@EnableSwagger2Doc
public class GlowwormApplication extends SpringBootServletInitializer{
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(GlowwormApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(GlowwormApplication.class, args);

	}
	//使用阿里巴巴的fastjson
	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		HttpMessageConverter<?> converter = fastConverter;
		return new HttpMessageConverters(converter);
	}
}
