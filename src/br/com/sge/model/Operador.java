package br.com.sge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "operador")
public class Operador extends TSActiveRecordAb<Operador>  {

	@Id
	@SequenceGenerator(name = "OPERADOR_ID_SEQ", sequenceName = "operador_id_seq", allocationSize = 1)
	@GeneratedValue(generator="OPERADOR_ID_SEQ", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	private String nome;
	
	private String matricula;
	
	@Column(name="valor_hora")
	private Double valorHora;
	
	@Column(name="flag_ativo")
	private Boolean flagAtivo;
	
	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Double getValorHora() {
		return valorHora;
	}

	public void setValorHora(Double valorHora) {
		this.valorHora = valorHora;
	}
	
	public String getSituacao() {		
		return (flagAtivo.equals(Boolean.TRUE)?"ATIVO":"INATIVO");
	}
	
}
