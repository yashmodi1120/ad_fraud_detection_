package com.example.Assignment_2;

import com.example.Assignment_2.model.RedisConfig;
import com.example.Assignment_2.service.IpService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(RedisConfig.class)
@EnableCaching
public class Assignment2Application {

	@Autowired
	private final IpService ipService;

	public Assignment2Application(IpService ipService) {
		this.ipService = ipService;
	}

	public static void main(String[] args) {
		SpringApplication.run(Assignment2Application.class, args);
	}
	@PostConstruct
	public void init() {
		ipService.updateCacheFromDatabase();
	}

}
