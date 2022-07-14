package br.thiago.projetofinal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.thiago.projetofinal.entity.Categoria;
import br.thiago.projetofinal.repository.ICategoria;



@RestController
@RequestMapping(value = "/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {
	
	
	@Autowired
	ICategoria repository;
	
	
	
	public List<Categoria> getAll(){
		return repository.findAll();
	}

}
