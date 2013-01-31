package br.com.sge.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "financeiro")
public class Financeiro extends TSActiveRecordAb<Financeiro> {

	@Id
	@SequenceGenerator(name = "FINANCEIRO_ID_SEQ", sequenceName = "financeiro_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "FINANCEIRO_ID_SEQ", strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "agenda_id")
	private Agenda agenda;

	@ManyToOne
	@JoinColumn(name = "tipo_transacao_id")
	private TipoTransacao tipoTransacao;

	@ManyToOne
	@JoinColumn(name = "fonte_id")
	private Fonte fonte;

	private String descricao;

	private Double valor;

	@Column(name = "data_lancamento")
	private Date dataLancamento;

	@Column(name = "data_pagamento")
	private Date dataPagamento;
	
	@OneToMany(mappedBy = "financeiro", cascade=CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)	
	private List<FinanceiroParcial> parciais;

	@Transient
	private Date dataInicialLancamento;

	@Transient
	private Date dataFinalLancamento;

	@Transient
	private Date dataInicialPagamento;

	@Transient
	private Date dataFinalPagamento;

	@Transient
	private Boolean flagPago;

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSituacao() {
		return (!TSUtil.isEmpty(dataPagamento) ? "Pago" : "Em Aberto");
	}

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}

	public TipoTransacao getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(TipoTransacao tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

	public Fonte getFonte() {
		return fonte;
	}

	public void setFonte(Fonte fonte) {
		this.fonte = fonte;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	@Override
	public List<Financeiro> findByModel(String... fieldsOrderBy) {
		return findByModel(null, fieldsOrderBy);
	}

	@Override
	public List<Financeiro> findByModel(Map<String, Object> map, String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();

		query.append(" from Financeiro f where 1=1 ");

		if (!TSUtil.isEmpty(agenda) && !TSUtil.isEmpty(agenda.getId())) {
			query.append("and f.agenda.id = ? ");
		}

		if (!TSUtil.isEmpty(agenda) && !TSUtil.isEmpty(agenda.getContrato()) && !TSUtil.isEmpty(agenda.getContrato().getId())) {
			query.append("and f.agenda.contrato.id = ? ");
		}

		if (!TSUtil.isEmpty(agenda) && !TSUtil.isEmpty(agenda.getContrato()) && !TSUtil.isEmpty(agenda.getContrato().getCliente()) && !TSUtil.isEmpty(agenda.getContrato().getCliente().getId())) {
			query.append("and f.agenda.contrato.cliente.id = ? ");
		}

		if (!TSUtil.isEmpty(tipoTransacao) && !TSUtil.isEmpty(tipoTransacao.getId())) {
			query.append("and f.tipoTransacao.id = ? ");
		}

		if (!TSUtil.isEmpty(fonte) && !TSUtil.isEmpty(fonte.getId())) {
			query.append("and f.fonte.id = ? ");
		}

		if (!TSUtil.isEmpty(descricao)) {
			query.append("and lower(f.descricao) like ? ");
		}

		if (!TSUtil.isEmpty(dataInicialLancamento)) {
			query.append("and f.dataLancamento >= ? ");
		}

		if (!TSUtil.isEmpty(dataFinalLancamento)) {
			query.append("and f.dataLancamento <= ? ");
		}

		if (!TSUtil.isEmpty(dataInicialPagamento)) {
			query.append("and f.dataPagamento >= ? ");
		}

		if (!TSUtil.isEmpty(dataFinalPagamento)) {
			query.append("and f.dataPagamento <= ? ");
		}

		if (!TSUtil.isEmpty(flagPago)) {
			if (flagPago) {
				query.append("and f.dataPagamento is not null ");
			} else {
				query.append("and f.dataPagamento is null ");
			}
		}

		List<Object> params = new ArrayList<Object>();

		if (!TSUtil.isEmpty(agenda) && !TSUtil.isEmpty(agenda.getId())) {
			params.add(agenda.getId());
		}

		if (!TSUtil.isEmpty(agenda) && !TSUtil.isEmpty(agenda.getContrato()) && !TSUtil.isEmpty(agenda.getContrato().getId())) {
			params.add(agenda.getContrato().getId());
		}

		if (!TSUtil.isEmpty(agenda) && !TSUtil.isEmpty(agenda.getContrato()) && !TSUtil.isEmpty(agenda.getContrato().getCliente()) && !TSUtil.isEmpty(agenda.getContrato().getCliente().getId())) {
			params.add(agenda.getContrato().getCliente().getId());
		}

		if (!TSUtil.isEmpty(tipoTransacao) && !TSUtil.isEmpty(tipoTransacao.getId())) {
			params.add(tipoTransacao.getId());
		}

		if (!TSUtil.isEmpty(fonte) && !TSUtil.isEmpty(fonte.getId())) {
			params.add(fonte.getId());
		}

		if (!TSUtil.isEmpty(descricao)) {
			params.add("%" + descricao.toLowerCase() + "%");
		}

		if (!TSUtil.isEmpty(dataInicialLancamento)) {
			params.add(dataInicialLancamento);
		}

		if (!TSUtil.isEmpty(dataFinalLancamento)) {
			params.add(dataFinalLancamento);
		}

		if (!TSUtil.isEmpty(dataInicialPagamento)) {
			params.add(dataInicialPagamento);
		}

		if (!TSUtil.isEmpty(dataFinalPagamento)) {
			params.add(dataFinalPagamento);
		}

		return super.find(query.toString(), "f.dataLancamento", params.toArray());
	}

	public int importarMedicoes() throws TSApplicationException {

		return super.executeSQL("INSERT INTO FINANCEIRO (TIPO_TRANSACAO_ID, FONTE_ID, AGENDA_ID, VALOR) SELECT 1, 1, A.ID, SUM(M.VALOR) FROM AGENDA A, AGENDA_MEDICAO M WHERE A.ID = M.AGENDA_ID AND A.FLAG_CONCLUIDO AND NOT EXISTS (SELECT 1 FROM FINANCEIRO F WHERE F.AGENDA_ID = A.ID) GROUP BY A.ID", null);
			
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Date getDataInicialLancamento() {
		return dataInicialLancamento;
	}

	public void setDataInicialLancamento(Date dataInicialLancamento) {
		this.dataInicialLancamento = dataInicialLancamento;
	}

	public Date getDataFinalLancamento() {
		return dataFinalLancamento;
	}

	public void setDataFinalLancamento(Date dataFinalLancamento) {
		this.dataFinalLancamento = dataFinalLancamento;
	}

	public Date getDataInicialPagamento() {
		return dataInicialPagamento;
	}

	public void setDataInicialPagamento(Date dataInicialPagamento) {
		this.dataInicialPagamento = dataInicialPagamento;
	}

	public Date getDataFinalPagamento() {
		return dataFinalPagamento;
	}

	public void setDataFinalPagamento(Date dataFinalPagamento) {
		this.dataFinalPagamento = dataFinalPagamento;
	}
	
	public Double getValorParcial() {
		
		
		if (!TSUtil.isEmpty(parciais)) {
			Double valor = 0D;
			for (FinanceiroParcial model: parciais) {
				valor = valor + model.getValor();
			}
			return valor;
		}
		
		return 0D;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agenda == null) ? 0 : agenda.hashCode());
		result = prime * result + ((dataFinalLancamento == null) ? 0 : dataFinalLancamento.hashCode());
		result = prime * result + ((dataFinalPagamento == null) ? 0 : dataFinalPagamento.hashCode());
		result = prime * result + ((dataInicialLancamento == null) ? 0 : dataInicialLancamento.hashCode());
		result = prime * result + ((dataInicialPagamento == null) ? 0 : dataInicialPagamento.hashCode());
		result = prime * result + ((dataLancamento == null) ? 0 : dataLancamento.hashCode());
		result = prime * result + ((dataPagamento == null) ? 0 : dataPagamento.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((fonte == null) ? 0 : fonte.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((tipoTransacao == null) ? 0 : tipoTransacao.hashCode());
		result = prime * result + ((valor == null) ? 0 : valor.hashCode());
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
		Financeiro other = (Financeiro) obj;
		if (agenda == null) {
			if (other.agenda != null)
				return false;
		} else if (!agenda.equals(other.agenda))
			return false;
		if (dataFinalLancamento == null) {
			if (other.dataFinalLancamento != null)
				return false;
		} else if (!dataFinalLancamento.equals(other.dataFinalLancamento))
			return false;
		if (dataFinalPagamento == null) {
			if (other.dataFinalPagamento != null)
				return false;
		} else if (!dataFinalPagamento.equals(other.dataFinalPagamento))
			return false;
		if (dataInicialLancamento == null) {
			if (other.dataInicialLancamento != null)
				return false;
		} else if (!dataInicialLancamento.equals(other.dataInicialLancamento))
			return false;
		if (dataInicialPagamento == null) {
			if (other.dataInicialPagamento != null)
				return false;
		} else if (!dataInicialPagamento.equals(other.dataInicialPagamento))
			return false;
		if (dataLancamento == null) {
			if (other.dataLancamento != null)
				return false;
		} else if (!dataLancamento.equals(other.dataLancamento))
			return false;
		if (dataPagamento == null) {
			if (other.dataPagamento != null)
				return false;
		} else if (!dataPagamento.equals(other.dataPagamento))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (fonte == null) {
			if (other.fonte != null)
				return false;
		} else if (!fonte.equals(other.fonte))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (tipoTransacao == null) {
			if (other.tipoTransacao != null)
				return false;
		} else if (!tipoTransacao.equals(other.tipoTransacao))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	public Boolean getFlagPago() {
		return flagPago;
	}

	public void setFlagPago(Boolean flagPago) {
		this.flagPago = flagPago;
	}

	public Date getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(Date dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	public List<FinanceiroParcial> getParciais() {
		return parciais;
	}

	public void setParciais(List<FinanceiroParcial> parciais) {
		this.parciais = parciais;
	}

}
