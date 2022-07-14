package br.thiago.projetofinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.thiago.projetofinal.entity.Categoria;


public interface ICategoria extends JpaRepository<Categoria, Integer> {
	
	
	

}
