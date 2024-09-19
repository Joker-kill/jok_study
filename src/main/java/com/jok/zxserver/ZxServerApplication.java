package com.jok.zxserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ZxServerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(ZxServerApplication.class, args);
		String[] beanDefinitionNames = run.getBeanDefinitionNames();
		for(String s:beanDefinitionNames){
			System.out.println(s);
		}
	}

}
