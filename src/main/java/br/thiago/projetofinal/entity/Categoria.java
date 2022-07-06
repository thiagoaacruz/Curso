package br.thiago.projetofinal.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Categoria")
public class Categoria {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcategoria")
	private Integer idcategoria;
	
	@Column(name = "categoria")
	private String Categoria;
	
	
	@OneToMany (mappedBy = "categoria")
	@JsonIgnore
	private List<Curso> curso;
	

	
	
	
	public Categoria() {
		// TODO Auto-generated constructor stub
	}





	public Categoria(Integer idcategoria, String categoria, List<Curso> curso) {
		super();
		this.idcategoria = idcategoria;
		Categoria = categoria;
		this.curso = curso;
	}





	public Integer getIdcategoria() {
		return idcategoria;
	}





	public void setIdcategoria(Integer idcategoria) {
		this.idcategoria = idcategoria;
	}





	public String getCategoria() {
		return Categoria;
	}





	public void setCategoria(String categoria) {
		Categoria = categoria;
	}





	public List<Curso> getCurso() {
		return curso;
	}





	public void setCurso(List<Curso> curso) {
		this.curso = curso;
	}
	
	
	

}
