package br.thiago.projetofinal.service;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Service;

@Service
public class AuditingService implements AuditorAware<String>  {

	@Override
	public Optional<String> getCurrentAuditor() {
		
		return Optional.of("Thiago Augusto");
	}
	
	
	

}
