package com.antibry.recruitment;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.antibry.recruitment.domain.acl.UserAccount;
import com.antibry.recruitment.domain.acl.Role;
import com.antibry.recruitment.repository.RoleRepository;
import com.antibry.recruitment.repository.UserAccountRepository;
import com.antibry.recruitment.service.ApplicantService;

@SpringBootApplication
public class RecruitmentApiApplication {
	
	private static final Logger log = LoggerFactory.getLogger(RecruitmentApiApplication.class);
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(RecruitmentApiApplication.class, args);
	}
	
	@Bean
	CommandLineRunner start(ApplicantService service, RoleRepository roleRepo, UserAccountRepository userRepo) {
		return args -> {
			log.info("@@ Inserting data...");
			service.insertData();
			
			log.info("@@ findAll() call...");
			service.findAll().forEach(entry -> log.info(entry.toString()));
			
			Role admRole = new Role();
			admRole.setAuthority("ADMIN");
			admRole.setDescription("Admin Role");
			Role newAdmRole = roleRepo.save(admRole);
			
			Role cliRole = new Role();
			cliRole.setAuthority("CLIENT");
			cliRole.setDescription("Client Role");
			Role newRole = roleRepo.save(cliRole);
			
			UserAccount user = new UserAccount();
			user.setUsername("mauricio");
			user.setPassword(bCryptPasswordEncoder.encode("password"));
			user.setRoles(new ArrayList<Role>() {{
				add(newRole);
			}});
			
			userRepo.save(user);
			
			UserAccount admin = new UserAccount();
			admin.setUsername("adamant");
			admin.setPassword(bCryptPasswordEncoder.encode("password"));
			admin.setRoles(new ArrayList<Role>() {{
				add(newAdmRole);
			}});
			
			userRepo.save(admin);
		};
	}
}
