package com.socgen.rabbit.qpid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QpidEmbeddedApplication {

	public static void main(String[] args) {
		SpringApplication.run(QpidEmbeddedApplication.class, args);
	}
}
