package com.infocz.igviewer.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	// @Bean
	// CommandLineRunner initDatabase(EmployeeRepository employeeRepository, TeamRepository teamRepository) {
	// 	return args -> {
	// 		long totalCnt = employeeRepository.count();
	// 		log.info("total count = " + totalCnt);
	// 		if(totalCnt == 0) {
	// 			Team team = new Team("개발팀", "소프트웨어 개발");
	// 			log.info("Preloading " + teamRepository.save(team));
	// 			log.info("Preloading " + employeeRepository.save(new Employee("Bilbo Baggins", "burglar", team)));
	// 			log.info("Preloading " + employeeRepository.save(new Employee("Frodo Baggins", "thief", team)));
	// 		}
	// 	};
	// }
}
