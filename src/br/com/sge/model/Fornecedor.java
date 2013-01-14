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
@Table(name = "fornecedor")
public class Fornecedor extends TSActiveRecordAb<Fornecedor>  {

	@Id
	@SequenceGenerator(name = "FORNECEDOR_ID_SEQ", sequenceName = "fornecedor_id_seq", allocationSize = 1)
	@GeneratedValue(generator="FORNECEDOR_ID_SEQ", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	private String nome;	
	
	@Column(name="flag_ativo")
	private Boolean flagAtivo;
	
	public Long getId() {
		return TSUtil.tratarLong(id);
	}
	
	public Fornecedor() {
		
	}
	
	public Fornecedor(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
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
	
	public String getSituacao() {		
		return (flagAtivo.equals(Boolean.TRUE)?"ATIVO":"INATIVO");
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
