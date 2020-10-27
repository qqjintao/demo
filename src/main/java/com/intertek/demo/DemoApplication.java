package com.intertek.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author barry.jt.huang
 */
@SpringBootApplication
@EnableAsync
@EnableTransactionManagement
@MapperScan("com.intertek.demo.*.mapper")
public class DemoApplication {
	public static void main(String[] args) {
		new SpringApplicationBuilder(DemoApplication.class).run(args);
	}
}
