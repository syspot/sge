package br.com.sge.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "financeiro_parcial")
public class FinanceiroParcial extends TSActiveRecordAb<FinanceiroParcial> {

	@Id
	@SequenceGenerator(name = "FINANCEIRO_PARCIAL_ID_SEQ", sequenceName = "financeiro_parcial_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "FINANCEIRO_PARCIAL_ID_SEQ", strategy = GenerationType.SEQUENCE)
	private Long id;	

	@ManyToOne
	@JoinColumn(name = "financeiro_id")
	private Financeiro financeiro;

	@Column(name = "data_pagamento")
	private Date dataPagamento;

	private String observacao;
	
	private Double valor;

	public FinanceiroParcial() {

	}
	
	public FinanceiroParcial(Financeiro financeiro) {
		this.financeiro = financeiro;
	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Financeiro getFinanceiro() {
		return financeiro;
	}

	public void setFinanceiro(Financeiro financeiro) {
		this.financeiro = financeiro;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dataPagamento == null) ? 0 : dataPagamento.hashCode());
		result = prime * result + ((financeiro == null) ? 0 : financeiro.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
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
		FinanceiroParcial other = (FinanceiroParcial) obj;
		if (dataPagamento == null) {
			if (other.dataPagamento != null)
				return false;
		} else if (!dataPagamento.equals(other.dataPagamento))
			return false;
		if (financeiro == null) {
			if (other.financeiro != null)
				return false;
		} else if (!financeiro.equals(other.financeiro))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		return true;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

}
