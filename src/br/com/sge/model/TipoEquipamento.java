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
	
}
