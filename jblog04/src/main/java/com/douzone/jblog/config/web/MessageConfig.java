package com.douzone.jblog.config.web;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageConfig {
	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		
		messageSource.setBasename("com.douzone.jblog.config.web.properties.messages_ko"); //확장자명은 생략
		messageSource.setDefaultEncoding("UTF-8");
		return messageSource;
	}
	
}
