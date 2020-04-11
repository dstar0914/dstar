package kr.side.dstar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DstarApplication {
	public static void main(String[] args) {
		SpringApplication.run(DstarApplication.class, args);
	}
}
