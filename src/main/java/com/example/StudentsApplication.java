package com.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class StudentsApplication extends SpringBootServletInitializer {
	 @Override protected SpringApplicationBuilder
	  configure(SpringApplicationBuilder application) { return
	 application.sources(StudentsApplication.class); }

	public static void main(String[] args) {
		SpringApplication.run(StudentsApplication.class, args);
	}

}
