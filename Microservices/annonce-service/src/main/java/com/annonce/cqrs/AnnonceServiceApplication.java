package com.annonce.cqrs;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@ComponentScan({ "com.annonce.CQRS" }) redondance
@EnableDiscoveryClient

public class AnnonceServiceApplication {




	public static void main(String[] args) {
		SpringApplication.run(AnnonceServiceApplication.class, args);
	}


	

}
