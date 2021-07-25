package com.magnatron.WuzzufSparkSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class WuzzufSparkSpringApplication
{

	public static void main(String[] args) {
		SpringApplication.run(WuzzufSparkSpringApplication.class, args);
	}

}
