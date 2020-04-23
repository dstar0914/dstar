package kr.side.dstar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DstarApplication {
	public static final String APPLICATION_LOCATIONS = "spring.config.location="
			+ "classpath:application.yml,"
			+ "/test-service/real-application.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(DstarApplication.class)
				.properties(APPLICATION_LOCATIONS)
				.run(args);
	}
}
