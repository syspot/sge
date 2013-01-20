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
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "agenda")
public class Agenda extends TSActiveRecordAb<Agenda> {

	@Id
	@SequenceGenerator(name = "AGENDA_ID_SEQ", sequenceName = "agenda_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "AGENDA_ID_SEQ", strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "contrato_id")
	private Contrato contrato;

	@ManyToOne
	@JoinColumn(name = "tipo_servico_id")
	private TipoServico tipoServico;
	
	@ManyToOne
	@JoinColumn(name = "equipamento_id")
	private Equipamento equipamento;
	
	@ManyToOne
	@JoinColumn(name = "operador_id")
	private Operador operador;
	
	@Column(name = "data_inicial")
	private Date dataInicial;
	
	@Column(name = "data_final")
	private Date dataFinal;

	private Double valor;

	@Column(name = "flag_concluido")	
	private Boolean flagConcluido;
	
	private String observacao;
	
	@OneToMany(mappedBy = "agenda", cascade=CascadeType.ALL, orphanRemoval=true, fetch = FetchType.EAGER)
	@OrderBy("dataInicial")
	private List<Medicao> medicoes;

	public Agenda() {

	}

	public Agenda(Boolean flagConcluido) {
		this.flagConcluido = flagConcluido;
	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSituacao() {
		return (flagConcluido.equals(Boolean.TRUE) ? "Concluído" : "Em Aberto");
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}

	public TipoServico getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(TipoServico tipoServico) {
		this.tipoServico = tipoServico;
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public Operador getOperador() {
		return operador;
	}

	public void setOperador(Operador operador) {
		this.operador = operador;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Boolean getFlagConcluido() {
		return flagConcluido;
	}

	public void setFlagConcluido(Boolean flagConcluido) {
		this.flagConcluido = flagConcluido;
	}

	public List<Medicao> getMedicoes() {
		return medicoes;
	}

	public void setMedicoes(List<Medicao> medicoes) {
		this.medicoes = medicoes;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	@Override
	public List<Agenda> findByModel(String... fieldsOrderBy) {
		return findByModel(null,fieldsOrderBy);
	}
	
	@Override
	public List<Agenda> findByModel(Map<String, Object> map,String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();

		query.append(" from Agenda a where 1=1 ");				

		if (!TSUtil.isEmpty(tipoServico) && !TSUtil.isEmpty(tipoServico.getId())) {
			query.append("and a.tipoServico.id = ? ");
		}
		
		if (!TSUtil.isEmpty(contrato) && !TSUtil.isEmpty(contrato.getId())) {
			query.append("and a.contrato.id = ? ");
		}
		
		if (!TSUtil.isEmpty(contrato) && !TSUtil.isEmpty(contrato.getCliente()) && !TSUtil.isEmpty(contrato.getCliente().getId())) {
			query.append("and a.contrato.cliente.id = ? ");
		}
		
		if (!TSUtil.isEmpty(equipamento) && !TSUtil.isEmpty(equipamento.getId())) {
			query.append("and a.equipamento.id = ? ");
		}
		
		if (!TSUtil.isEmpty(operador) && !TSUtil.isEmpty(operador.getId())) {
			query.append("and a.operador.id = ? ");
		}
		
		if (!TSUtil.isEmpty(flagConcluido)) {
			query.append("and a.flagConcluido = ? ");
		}

		if (!TSUtil.isEmpty(dataInicial)) {
			query.append("and a.dataInicial >= ? ");
		}

		if (!TSUtil.isEmpty(dataFinal)) {
			query.append("and a.dataFinal <= ?");
		}

		List<Object> params = new ArrayList<Object>();
		
		if (!TSUtil.isEmpty(tipoServico) && !TSUtil.isEmpty(tipoServico.getId())) {
			params.add(tipoServico.getId());
		}
		
		if (!TSUtil.isEmpty(contrato) && !TSUtil.isEmpty(contrato.getId())) {
			params.add(contrato.getId());
		}
		
		if (!TSUtil.isEmpty(contrato) && !TSUtil.isEmpty(contrato.getCliente()) && !TSUtil.isEmpty(contrato.getCliente().getId())) {
			params.add(contrato.getCliente().getId());
		}
		
		if (!TSUtil.isEmpty(equipamento) && !TSUtil.isEmpty(equipamento.getId())) {
			params.add(equipamento.getId());
		}
		
		if (!TSUtil.isEmpty(operador) && !TSUtil.isEmpty(operador.getId())) {
			params.add(operador.getId());
		}

		if (!TSUtil.isEmpty(flagConcluido)) {
			params.add(flagConcluido);
		}

		if (!TSUtil.isEmpty(dataInicial)) {
			params.add(dataInicial);
		}

		if (!TSUtil.isEmpty(dataFinal)) {
			params.add(dataFinal);
		}

		return super.find(query.toString(), "dataInicial", params.toArray());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contrato == null) ? 0 : contrato.hashCode());
		result = prime * result + ((dataFinal == null) ? 0 : dataFinal.hashCode());
		result = prime * result + ((dataInicial == null) ? 0 : dataInicial.hashCode());
		result = prime * result + ((equipamento == null) ? 0 : equipamento.hashCode());
		result = prime * result + ((flagConcluido == null) ? 0 : flagConcluido.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((medicoes == null) ? 0 : medicoes.hashCode());
		result = prime * result + ((observacao == null) ? 0 : observacao.hashCode());
		result = prime * result + ((operador == null) ? 0 : operador.hashCode());
		result = prime * result + ((tipoServico == null) ? 0 : tipoServico.hashCode());
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
		Agenda other = (Agenda) obj;
		if (contrato == null) {
			if (other.contrato != null)
				return false;
		} else if (!contrato.equals(other.contrato))
			return false;
		if (dataFinal == null) {
			if (other.dataFinal != null)
				return false;
		} else if (!dataFinal.equals(other.dataFinal))
			return false;
		if (dataInicial == null) {
			if (other.dataInicial != null)
				return false;
		} else if (!dataInicial.equals(other.dataInicial))
			return false;
		if (equipamento == null) {
			if (other.equipamento != null)
				return false;
		} else if (!equipamento.equals(other.equipamento))
			return false;
		if (flagConcluido == null) {
			if (other.flagConcluido != null)
				return false;
		} else if (!flagConcluido.equals(other.flagConcluido))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (medicoes == null) {
			if (other.medicoes != null)
				return false;
		} else if (!medicoes.equals(other.medicoes))
			return false;
		if (observacao == null) {
			if (other.observacao != null)
				return false;
		} else if (!observacao.equals(other.observacao))
			return false;
		if (operador == null) {
			if (other.operador != null)
				return false;
		} else if (!operador.equals(other.operador))
			return false;
		if (tipoServico == null) {
			if (other.tipoServico != null)
				return false;
		} else if (!tipoServico.equals(other.tipoServico))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}
}
