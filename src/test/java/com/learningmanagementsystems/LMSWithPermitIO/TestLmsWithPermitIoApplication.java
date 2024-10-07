package com.learningmanagementsystems.LMSWithPermitIO;

import org.springframework.boot.SpringApplication;

public class TestLmsWithPermitIoApplication {

	public static void main(String[] args) {
		SpringApplication.from(LmsWithPermitIoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
