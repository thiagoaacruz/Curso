package br.thiago.projetofinal.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "Curso")
public class Curso extends AbstractAuditingEntity  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcurso")
	private Integer idCurso;

	@Column(name = "descricao_assunto", length = 150, nullable = false)
	private String descricaoAssunto;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "data_inicio", length = 15, nullable = false)
	private LocalDate dataInicio;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "data_termino", length = 15, nullable = false)
	private LocalDate dataTermino;

	@Column(name = "quantidade_aluno")
	private Integer quantidadeAluno;

	@ManyToOne
	// @JsonIgnore
	@JoinColumn(name = "cursofk")
	private Categoria categoria;

	public Curso() {
		// TODO Auto-generated constructor stub
	}

	public Curso(Integer idCurso, String descricaoAssunto, LocalDate dataInicio, LocalDate dataTermino,
			Integer quantidadeAluno, Categoria categoria) {
		super();
		this.idCurso = idCurso;
		this.descricaoAssunto = descricaoAssunto;
		this.dataInicio = dataInicio;
		this.dataTermino = dataTermino;
		this.quantidadeAluno = quantidadeAluno;
		this.categoria = categoria;
	}

	public Integer getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(Integer idCurso) {
		this.idCurso = idCurso;
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
