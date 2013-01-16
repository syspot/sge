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
@Table(name = "tipo_equipamento")
public class TipoEquipamento extends TSActiveRecordAb<TipoEquipamento>  {

	@Id
	@SequenceGenerator(name = "TIPO_EQUIPAMENTO_ID_SEQ", sequenceName = "tipo_equipamento_id_seq", allocationSize = 1)
	@GeneratedValue(generator="TIPO_EQUIPAMENTO_ID_SEQ", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	private String descricao;		
	
	@Column(name="valor_hora")
	private Double valorHora;
	
	@Column(name="valor_servico")
	private Double valorServico;
	
	@Column(name="flag_ativo")
	private Boolean flagAtivo;
	
	public TipoEquipamento() {
		
	}
	
	public TipoEquipamento(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}
	
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

	public Double getValorHora() {
		return valorHora;
	}

	public void setValorHora(Double valorHora) {
		this.valorHora = valorHora;
	}
	
	public String getSituacao() {		
		return (flagAtivo.equals(Boolean.TRUE)?"ATIVO":"INATIVO");
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValorServico() {
		return valorServico;
	}

	public void setValorServico(Double valorServico) {
		this.valorServico = valorServico;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((flagAtivo == null) ? 0 : flagAtivo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((valorHora == null) ? 0 : valorHora.hashCode());
		result = prime * result + ((valorServico == null) ? 0 : valorServico.hashCode());
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
		TipoEquipamento other = (TipoEquipamento) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
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
		if (valorHora == null) {
			if (other.valorHora != null)
				return false;
		} else if (!valorHora.equals(other.valorHora))
			return false;
		if (valorServico == null) {
			if (other.valorServico != null)
				return false;
		} else if (!valorServico.equals(other.valorServico))
			return false;
		return true;
	}
	
}
