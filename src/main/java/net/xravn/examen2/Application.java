package net.xravn.examen2;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import net.xravn.examen2.cli.testBackend;
import net.xravn.examen2.controller.configuration.ConfigurationManager;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// checks if it needs to run witouth spring boot
		if (Arrays.asList(args).contains("--no-init")) {
			System.out.println("Running without spring boot");
			// checks if it needs to test backend services
			if (Arrays.asList(args).contains("--test-backend")) {
				if (Arrays.asList(args).contains("--test-all")) {
					new testBackend().testAll();
				} else if (Arrays.asList(args).contains("--test-database")) {
					new testBackend().testDatabase();
				} else {
					System.out.println("No test specified");
				}
			}
		} else {
			ConfigurationManager configurationManager = ConfigurationManager.getInstance();
			SpringApplication app = new SpringApplication(Application.class);
			System.out.println("Iniciando servidor en: " + configurationManager.getWebServerPort());
			app.setDefaultProperties(Collections.singletonMap("server.port", configurationManager.getWebServerPort()));
			app.run(args);
		}

	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("Let's inspect the beans provided by Spring Boot:");
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
		};
	}

	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/api/login").permitAll()
				.anyRequest().authenticated();
		}
	}

}
