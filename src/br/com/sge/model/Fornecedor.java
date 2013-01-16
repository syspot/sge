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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((flagAtivo == null) ? 0 : flagAtivo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fornecedor other = (Fornecedor) obj;
		if (flagAtivo == null) {
			if (other.flagAtivo != null)
				return false;
		} else if (!flagAtivo.equals(other.flagAtivo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
}
