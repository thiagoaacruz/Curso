package br.thiago.projetofinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import br.thiago.projetofinal.service.AuditingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditingService")
public class ProjetofinalApplication {
	private static final Logger LOGGER= LoggerFactory.getLogger(ProjetofinalApplication.class);

	public static void main(String[] args) {
		LOGGER.info("iniciando SpringBoot");
		SpringApplication.run(ProjetofinalApplication.class, args);
		LOGGER.info("SpringBoot iniciado com sucesso!!!");
	}
	
	@Bean
	AuditorAware<String> auditorProvider(){
		
		return new AuditingService();
	}

}
