package com.scratchbling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication//(exclude = {SecurityAutoConfiguration.class})
public class SbApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbApiApplication.class, args);
	}

}
