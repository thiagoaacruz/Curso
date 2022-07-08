package br.thiago.projetofinal.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.thiago.projetofinal.entity.Curso;

@Repository
public interface ICurso extends JpaRepository<Curso, Integer> {
	
	
	List<Curso> findByDescricaoAssunto(String descricaoAssunto);
	
	
	/*<p>Este método atua como uma ponte entre o baseado em array e o baseado em coleção
    * API.
    *
    * @return um array contendo todos os elementos desta lista no devido*/
	
	List<Curso> findByDataInicioBetween(LocalDate dataInicio, LocalDate dataTermino);
	
	
	
	
}
