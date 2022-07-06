package br.thiago.projetofinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.thiago.projetofinal.entity.Curso;

@Repository
public interface ICurso extends JpaRepository<Curso, Integer> {
	


}
