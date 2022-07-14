package br.thiago.projetofinal.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.thiago.projetofinal.ProjetofinalApplication;
import br.thiago.projetofinal.entity.Curso;
import br.thiago.projetofinal.repository.ICurso;
import io.swagger.annotations.ApiOperation;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CursoController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProjetofinalApplication.class);// criando log

	@Autowired
	ICurso repository;

	@PersistenceContext
	EntityManager em;

	private static final String ENDPOINT = "api/curso";

//_______________________________________________________________________________________________________________________________	

	/*
	 * @ApiOperation("Serviço para cadastro de Curso")
	 * 
	 * @RequestMapping(value = ENDPOINT, method = RequestMethod.POST) public
	 * ResponseEntity<String> post(@RequestBody CursoPostRequest request) { try {
	 * Curso curso = new Curso();
	 * 
	 * curso.setDescricaoAssunto(request.getDescricaoAssunto());
	 * curso.setDataInicio(request.getDataInicio());
	 * curso.setDataTermino(request.getDataTermino());
	 * curso.setQuantidadeAluno(request.getQuantidadeAluno());
	 * curso.setCategoria(request.getCategoria());
	 * 
	 * repository.save(curso);
	 * 
	 * return ResponseEntity.status(HttpStatus.OK).body("Curso cadastrado");
	 * 
	 * } catch (Exception e) { return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro" +
	 * e.getMessage()); }
	 * 
	 * }
	 */

//_______________________________________________________________________________________________________________________________	

	// Não será permitida a inclusão de cursos com a data de início menor que a data
	// atual.

	@ApiOperation("Serviço para cadastro")

	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody Curso curso) {

		try {
			validaData(curso);// passando o método de validação de periodo por data.

			if (curso.getDataInicio().isBefore(LocalDate.now())) {// Adicionei esse lógica para verificar se a data
																	// final está antes

				LOGGER.info("Serviço para cadastro não realizado!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body("Curso não cadastrado, data de início menor " + "que a data atual.");

			} else {

				repository.save(curso);

				LOGGER.info("Serviço para cadastro realizado com sucesso.");
				return ResponseEntity.status(HttpStatus.OK).body("Curso cadastrado");

			}

		} catch (Exception e) {
			LOGGER.error("Erro ao cadastrar.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro :" + e.getMessage());
		}

	}

//_______________________________________________________________________________________________________________________________		

	// Método de validação de periodo por data

	private void validaData(Curso curso) {
		if (curso.getDataInicio().isAfter(curso.getDataTermino())) {
			LOGGER.info("Periodo por data requisitado com sucesso!");
			throw new RuntimeException("não permitido");
		}

		List<Curso> cursosBuscados = repository.getAllBetweenDates(curso.getDataInicio(), curso.getDataTermino());
		if (cursosBuscados.size() > 0) {
			LOGGER.info("Existe(m) curso(s) planejados(s) dentro do período informado.");
			// Método de validação de data não permitido
			throw new RuntimeException("Já existem cursos cadastrados no período informado.");
		}
	}

//_______________________________________________________________________________________________________________________________			
	@ApiOperation("Serviço para atualização")

	@RequestMapping(value = ENDPOINT, method = RequestMethod.PUT)

	public ResponseEntity<String> put(@RequestBody Curso curso) {

		try {

			if (curso.getDataInicio().isBefore(LocalDate.now())) {// Adicionei esse lógica para verificar se a data
																	// final está antes

				LOGGER.info("Serviço para atualizar não realizado!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Curso não atualizado");

			} else {
				
				repository.consultaGeralDatas(curso.getDataTermino(), curso.getDataInicio(), curso.getIdCurso());
				repository.save(curso);
				LOGGER.info("Serviço para atualizar realizado.");
				return ResponseEntity.status(HttpStatus.OK).body("Curso atualizado");
			}

		} catch (Exception e) {
			LOGGER.error("Erro ao atualizar.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro :" + e.getMessage());
		}

	}

//_______________________________________________________________________________________________________________________________		

	@ApiOperation("Serviço para deletar Curso")

	@RequestMapping(value = ENDPOINT + "/{idcurso}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("idcurso") Integer idcurso) {
		try {
			Optional<Curso> item = repository.findById(idcurso);

			if (item.isEmpty()) {

				LOGGER.info("Serviço para deletar não efetuado!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Aluno não encontrado");

			} else {
				Curso curso = item.get();
				repository.delete(curso);
				LOGGER.info("Serviço para deletar efetuado!");
				return ResponseEntity.status(HttpStatus.OK).body("Curso excluido");
			}
		} catch (Exception e) {
			LOGGER.error("Erro ao deletar.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro :" + e.getMessage());
		}

	}

//_______________________________________________________________________________________________________________________________			

	@ApiOperation("Serviço para consultar (descrição - data Inicio - data termino)")

	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET)

	public ResponseEntity<List<Curso>> get(@RequestParam(required = false) String descricaoAssunto,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
			@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataTermino) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Curso> cq = cb.createQuery(Curso.class);

		Root<Curso> cursos = cq.from(Curso.class);

		List<Predicate> predicates = new ArrayList<>();

		if (descricaoAssunto != "") {

			Predicate descricaoAssuntoPredicate = cb.equal(cursos.get("descricaoAssunto"), descricaoAssunto);
			predicates.add(descricaoAssuntoPredicate);
		}
		
		
		if (dataInicio != null) {
			Predicate dataInicioPredicate = cb.greaterThanOrEqualTo(cursos.get("dataInicio"), dataInicio);
			predicates.add(dataInicioPredicate);
		}

		if (dataTermino != null) {
			Predicate dataTerminoPredicate = cb.lessThanOrEqualTo(cursos.get("dataTermino"), dataTermino);
			predicates.add(dataTerminoPredicate);
		}
		
		

		Predicate[] predicateArray = new Predicate[predicates.size()];
		predicates.toArray(predicateArray);
		cq.where(predicateArray);

		TypedQuery<Curso> query = em.createQuery(cq);

		LOGGER.info("Serviço para consulta realizada com sucesso!");
		return ResponseEntity.status(HttpStatus.OK).body(query.getResultList());

	}

//_______________________________________________________________________________________________________________________________			

	@ApiOperation("Serviço para consultar todos os Cursos por id")

	@RequestMapping(value = ENDPOINT + "/{idCurso}", method = RequestMethod.GET)
	public ResponseEntity<Curso> getId(@PathVariable("idCurso") Integer idCurso) {
		Optional<Curso> optional = repository.findById(idCurso);

		if (!optional.isPresent()) {

			LOGGER.info("Serviço para consulta não realizado!");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {

			Curso curso = optional.get();

			LOGGER.info("Serviço para consulta realizada com sucesso!");
			return ResponseEntity.status(HttpStatus.OK).body(curso);
		}

	}

//_______________________________________________________________________________________________________________________________					

	@ApiOperation("Serviço para deletar Curso específico")

	@DeleteMapping("/{idcurso}")
	public ResponseEntity<String> apagar(@PathVariable("idcurso") Integer idcurso) {
		try {
			Optional<Curso> lista = repository.findById(idcurso);

			if (lista.isEmpty()) {

				LOGGER.info("Serviço para deletar não realizado!");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Curso não encontrado");

			} else {

				Curso curso = lista.get();

				if (curso.getDataTermino().isBefore(LocalDate.now())) {// Adicionei esse lógica para verificar se a data
																		// final está antes
					LOGGER.info("Serviço para deletar não realizado!");
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Curso não pode ser deletado");

				} else {

					repository.delete(curso);
				}

				LOGGER.info("Serviço para deletar realizado!");
				return ResponseEntity.status(HttpStatus.OK).body("Curso excluido");
			}

		} catch (Exception e) {
			LOGGER.error("Erro ao deletar.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro :" + e.getMessage());
		}

	}
	
//_______________________________________________________________________________________________________________________________	
	
	
	
	
	

}
