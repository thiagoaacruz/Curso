package br.thiago.projetofinal.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.thiago.projetofinal.entity.Curso;
import br.thiago.projetofinal.repository.ICurso;
import br.thiago.projetofinal.request.CursoGetResponse;
import br.thiago.projetofinal.request.CursoPostRequest;
import br.thiago.projetofinal.request.CursoPutRequest;

@Controller
@CrossOrigin(origins = "", allowedHeaders = "")
public class CursoController {
	
	@Autowired
	ICurso repository;
	
	private static final String ENDPOINT = "api/curso";
	
	
	//@ApiOperation("Serviço para cadastro de Curso")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.POST)
	public ResponseEntity<String> post(@RequestBody CursoPostRequest request) {
		try {
			Curso curso = new Curso();

			curso.setDescricaoAssunto(request.getDescricaoAssunto());
			curso.setDataInicio(request.getDataInicio());
			curso.setDataTermino(request.getDataTermino());
			curso.setQuantidadeAluno(request.getQuantidadeAluno());
			curso.setCategoria(request.getCategoria());
			
			

			repository.save(curso);

			return ResponseEntity.status(HttpStatus.OK).body("Curso cadastrado");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro" + e.getMessage());
		}
	
	}
	
//_______________________________________________________________________________________________________________________________	
	
	
	//@ApiOperation("Serviço para atualização dos dados de um Curso")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.PUT)
	public ResponseEntity<String> put(@RequestBody CursoPutRequest request) {
		try {

			Optional<Curso> item = repository.findById(request.getIdcurso());

			if (item.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Curso não encontrado");
			} else {

				Curso curso = item.get();
				
				curso.setDescricaoAssunto(request.getDescricaoAssunto());
				curso.setDataInicio(request.getDataInicio());
				curso.setDataTermino(request.getDataTermino());
				curso.setQuantidadeAluno(request.getQuantidadeAluno());
				curso.setCategoria(request.getCategoria());
				
				repository.save(curso);

				return ResponseEntity.status(HttpStatus.OK).body("Atualizado");
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro :" + e.getMessage());
		}

	}
	
//_______________________________________________________________________________________________________________________________		

	
	//@ApiOperation("Serviço para deletar Curso")
	@RequestMapping(value = ENDPOINT + "/{idcurso}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable("idcurso") Integer idcurso) {
		try {
			Optional<Curso> item = repository.findById(idcurso);

			if (item.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Aluno não encontrado");

			} else {
				Curso curso = item.get();
				repository.delete(curso);
				return ResponseEntity.status(HttpStatus.OK).body("Curso excluido");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro :" + e.getMessage());
		}

	}
	
	
//_______________________________________________________________________________________________________________________________			
	

	//@ApiOperation("Serviço para consultar todos os Cursos")
	@RequestMapping(value = ENDPOINT, method = RequestMethod.GET)
	public ResponseEntity<List<CursoGetResponse>> get() {

		List<CursoGetResponse> response = new ArrayList<CursoGetResponse>();

		for (Curso curso : repository.findAll()) {

			CursoGetResponse item = new CursoGetResponse();

			item.setIdcurso(curso.getIdcurso());
			item.setDescricaoAssunto(curso.getDescricaoAssunto());
			item.setDataInicio(curso.getDataInicio());
			item.setDataTermino(curso.getDataTermino());			
			item.setQuantidadeAluno(curso.getQuantidadeAluno());			
			item.setCategoria(curso.getCategoria());
			

			response.add(item);
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
//_______________________________________________________________________________________________________________________________			
	
	
	
	//@ApiOperation("Serviço para consultar todos os Cursos por id")
	@RequestMapping(value = ENDPOINT + "/{idcurso}", method = RequestMethod.GET)
	public ResponseEntity<CursoGetResponse> getId(@PathVariable("idcurso") Integer idcurso) {
		Optional<Curso> item = repository.findById(idcurso);

		if (item.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			CursoGetResponse response = new CursoGetResponse();

			Curso curso = item.get();

			response.setIdcurso(curso.getIdcurso());
			response.setDescricaoAssunto(curso.getDescricaoAssunto());
			response.setDataInicio(curso.getDataInicio());
			response.setDataTermino(curso.getDataTermino());
			response.setQuantidadeAluno(curso.getQuantidadeAluno());
			response.setCategoria(curso.getCategoria());
					
				

			return ResponseEntity.status(HttpStatus.OK).body(response);
		}

	}
	

}
