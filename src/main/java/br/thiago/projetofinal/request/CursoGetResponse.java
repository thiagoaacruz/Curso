package br.thiago.projetofinal.request;

import java.time.LocalDate;

import br.thiago.projetofinal.entity.Categoria;

public class CursoGetResponse {
	
	private Integer idcurso;
	private String descricaoAssunto;
	private LocalDate dataInicio;
	private LocalDate dataTermino;
	private Integer quantidadeAluno;
	private Categoria categoria;
	
	

	
	public CursoGetResponse() {
		// TODO Auto-generated constructor stub
	}




	public CursoGetResponse(Integer idcurso, String descricaoAssunto, LocalDate dataInicio, LocalDate dataTermino,
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
