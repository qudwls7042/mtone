package com.torun.mtone;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.torun.mtone.mapper")
@Slf4j
public class MtoneApplication {

	public static void main(String[] args) {
		SpringApplication.run(MtoneApplication.class, args);
	}

}
