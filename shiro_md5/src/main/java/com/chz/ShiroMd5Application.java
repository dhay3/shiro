package com.chz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.chz.dao"})
public class ShiroMd5Application {

	public static void main(String[] args) {
		SpringApplication.run(ShiroMd5Application.class, args);
	}

}
