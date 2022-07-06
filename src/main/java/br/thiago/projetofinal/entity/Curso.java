package br.thiago.projetofinal.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Curso")
public class Curso {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcurso")
	private Integer idcurso;
	
	@Column(name = "descricaoAssunto", length = 150, nullable = false)
	private String descricaoAssunto;
	
	
	@Column(name = "dataInicio", length = 15, nullable = false)
	private LocalDate dataInicio;
	
	
	@Column(name = "dataTermino", length = 15, nullable = false)
	private LocalDate dataTermino;
	
	
	@Column(name = "quantidadeAluno")
	private Integer quantidadeAluno;
	
	
	@ManyToOne
	//@JsonIgnore
	@JoinColumn(name = "cursofk")
	private Categoria categoria;
	
	
	public Curso() {
		// TODO Auto-generated constructor stub
	}


	public Curso(Integer idcurso, String descricaoAssunto, LocalDate dataInicio, LocalDate dataTermino,
			Integer quantidadeAluno, Categoria categoria) {
		super();
		this.idcurso = idcurso;
		this.descricaoAssunto = descricaoAssunto;
		this.dataInicio = dataInicio;
		this.dataTermino = dataTermino;
		this.quantidadeAluno = quantidadeAluno;
		this.categoria = categoria;
	}


	public Integer getIdcurso() {
		return idcurso;
	}


	public void setIdcurso(Integer idcurso) {
		this.idcurso = idcurso;
	}


	public String getDescricaoAssunto() {
		return descricaoAssunto;
	}


	public void setDescricaoAssunto(String descricaoAssunto) {
		this.descricaoAssunto = descricaoAssunto;
	}


	public LocalDate getDataInicio() {
		return dataInicio;
	}


	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}


	public LocalDate getDataTermino() {
		return dataTermino;
	}


	public void setDataTermino(LocalDate dataTermino) {
		this.dataTermino = dataTermino;
	}


	public Integer getQuantidadeAluno() {
		return quantidadeAluno;
	}


	public void setQuantidadeAluno(Integer quantidadeAluno) {
		this.quantidadeAluno = quantidadeAluno;
	}


	public Categoria getCategoria() {
		return categoria;
	}


	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}


	
	
	
}
