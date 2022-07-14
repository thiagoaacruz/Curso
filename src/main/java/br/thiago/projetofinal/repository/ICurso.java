package br.thiago.projetofinal.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.thiago.projetofinal.entity.Curso;

@Repository
public interface ICurso extends JpaRepository<Curso, Integer>, RevisionRepository<Curso, Integer, Integer>  {
	
	
	List<Curso> findByDescricaoAssunto(String descricaoAssunto);
	
	
	/*<p>Este método atua como uma ponte entre o baseado em array e o baseado em coleção
    * API.
    *
    * @return um array contendo todos os elementos desta lista no devido*/
	
	//List<Curso> findByDataInicioBetween(LocalDate dataInicio, LocalDate dataTermino);

	
	@Query(value = "from Curso c where c.dataInicio BETWEEN :startDate AND :endDate")
    public List<Curso> getAllBetweenDates(@Param("startDate")LocalDate startDate,@Param("endDate")LocalDate endDate);
	
	
	
	
	@Query(value = "select count(c.idCurso) from Curso c where (c.dataInicio between :dataInicio and :dataTermino "
            + "or (c.dataTermino between :dataInicio and :dataTermino)" + "or (c.dataInicio <= :dataInicio and c.dataTermino >= :dataTermino)"
            + "or (c.dataInicio >= :dataInicio and c.dataTermino <= :dataTermino))" + "and (c.idCurso != :idCurso)")
    
	public Integer consultaGeralDatas(@Param("dataInicio") LocalDate dataInicio, @Param("dataTermino") LocalDate dataTermino,
            @Param("idCurso") Integer idCurso);
	
	
	
	
	
	
}
