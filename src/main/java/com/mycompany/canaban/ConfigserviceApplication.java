package com.mycompany.canaban;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableAutoConfiguration
@EnableEurekaServer
@EnableConfigServer
@EnableAdminServer
public class ConfigserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigserviceApplication.class, args);
	}
}
