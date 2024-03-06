package com.tushar.accounts;

import com.tushar.accounts.audit.AuditAwareImpl;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
/*
@ComponentScan(basePackages = "com.tushar.accounts") //this annotation is used to scan all the classes in the specified package and register them as Spring beans.
@EnableJpaRepositories(basePackages = "com.tushar.accounts.repository") //this annotation is used to enable JPA repositories into the specific packages.
@EntityScan(basePackages = "com.tushar.accounts.model") //this annotation is used to enable JPA entity scanning of the given packages.
 */
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl") //this annotation is added for the BaseEntity
@OpenAPIDefinition(
		info = @Info(
				title = "Account Microservice REST API documentation",
				description = "EAZYBANK Accounts microservice REST API Documentation",
				version = "V1",
				contact = @Contact(
						name = "Tushar Dashrath Fadol",
						email = "tushar.fadol1@wipro.com"
				),
				license = @License(
						name = "APACHE 2.0",
						url = "https://www.tushar.com"
				)

		),
		externalDocs = @ExternalDocumentation(
				description = "EAZYBANK Accounts microservice REST API Documentation",
				url = "https://www.tushar.com"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
