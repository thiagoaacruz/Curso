package br.thiago.projetofinal.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.thiago.projetofinal.entity.Curso;
import br.thiago.projetofinal.repository.ICurso;
import br.thiago.projetofinal.request.CursoGetResponse;
import br.thiago.projetofinal.request.CursoPostRequest;
import br.thiago.projetofinal.request.CursoPutRequest;
import io.swagger.annotations.ApiOperation;

@Controller
@CrossOrigin(origins = "", allowedHeaders = "")
public class CursoController {

	@Autowired
	ICurso repository;

	private static final String ENDPOINT = "api/curso";

	@ApiOperation("Serviço para cadastro de Curso")

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

	@ApiOperation("Serviço para atualização dos dados de um Curso")

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

	@ApiOperation("Serviço para deletar Curso")

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

	@ApiOperation("Serviço para consultar todos os Cursos")

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

	@ApiOperation("Serviço para consultar todos os Cursos por id")

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

//_______________________________________________________________________________________________________________________________			

	@ApiOperation("Serviço para consultar pelo nome")

	@GetMapping(value = "/descricaoAssunto")
	public ResponseEntity<List<CursoGetResponse>> listDescricaoAssunto(String descricaoAssunto) {

		List<CursoGetResponse> response = new ArrayList<>();

		for (Curso curso : repository.findByDescricaoAssunto(descricaoAssunto)) {

			CursoGetResponse buscaPorNome = new CursoGetResponse();

			buscaPorNome.setIdcurso(curso.getIdcurso());
			buscaPorNome.setDescricaoAssunto(curso.getDescricaoAssunto());
			buscaPorNome.setDataInicio(curso.getDataInicio());
			buscaPorNome.setDataTermino(curso.getDataTermino());
			buscaPorNome.setQuantidadeAluno(curso.getQuantidadeAluno());
			buscaPorNome.setCategoria(curso.getCategoria());

			response.add(buscaPorNome);
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

//_______________________________________________________________________________________________________________________________				

	@ApiOperation("Serviço para consultar por data")

	@GetMapping(value = "/faixa/{dataInicio}/{dataTermino}")
	public ResponseEntity<List<CursoGetResponse>> listafaixa(
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataInicio,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dataTermino) {

		List<CursoGetResponse> response = new ArrayList<>();

		for (Curso curso : repository.findByDataInicioBetween(dataInicio, dataTermino)) {

			CursoGetResponse buscaPorData = new CursoGetResponse();

			buscaPorData.setIdcurso(curso.getIdcurso());
			buscaPorData.setDescricaoAssunto(curso.getDescricaoAssunto());
			buscaPorData.setDataInicio(curso.getDataInicio());
			buscaPorData.setDataTermino(curso.getDataTermino());
			buscaPorData.setQuantidadeAluno(curso.getQuantidadeAluno());
			buscaPorData.setCategoria(curso.getCategoria());

			response.add(buscaPorData);
		}
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

//_______________________________________________________________________________________________________________________________					

	@ApiOperation("Serviço para deletar Curso específico")

	@DeleteMapping("/{idcurso}")
	public ResponseEntity<String> apagar(@PathVariable("idcurso") Integer idcurso) {
		try {
			Optional<Curso> lista = repository.findById(idcurso);

			if (lista.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Curso não encontrado");

			} else {

				Curso curso = lista.get();

				if (curso.getDataTermino().isBefore(LocalDate.now())) {//Adicionei esse lógica para verificar se a data final está antes

					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Curso não pode ser deletado");

				} else {

					repository.delete(curso);
				}

				return ResponseEntity.status(HttpStatus.OK).body("Curso excluido");
			}
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro :" + e.getMessage());
		}

	}

}
