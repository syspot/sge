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
@Table(name = "fonte")
public class Fonte extends TSActiveRecordAb<Fonte>  {

	@Id
	@SequenceGenerator(name = "FONTE_ID_SEQ", sequenceName = "fonte_id_seq", allocationSize = 1)
	@GeneratedValue(generator="FONTE_ID_SEQ", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	private String descricao;	
	
	@Column(name="flag_ativo")
	private Boolean flagAtivo;
	
	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
	
}
