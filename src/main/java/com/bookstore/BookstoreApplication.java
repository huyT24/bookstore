package com.bookstore;

import com.bookstore.service.ISecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class BookstoreApplication implements CommandLineRunner {

	@Autowired
	private ISecurityService securityService;

	public static void main(String[] args) {
		SpringApplication.run(BookstoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		try {
			securityService.generateUsersRoles();
		} catch (Exception e) {

		}
	}
}
