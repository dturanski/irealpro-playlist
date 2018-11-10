package org.dturanski.irealpro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories
public class IRealProPlaylistApplication {

	public static void main(String[] args) {
		SpringApplication.run(IRealProPlaylistApplication.class, args);
	}
}
