package br.com.sge.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
@Table(name = "agenda_medicao")
public class Medicao extends TSActiveRecordAb<Medicao> {

	@Id
	@SequenceGenerator(name = "AGENDA_MEDICAO_ID_SEQ", sequenceName = "agenda_medicao_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "AGENDA_MEDICAO_ID_SEQ", strategy = GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "agenda_id")
	private Agenda agenda;

	@ManyToOne
	@JoinColumn(name = "operador_id")
	private Operador operador;

	@Column(name = "data_inicial")
	private Date dataInicial;

	@Column(name = "data_final")
	private Date dataFinal;
	
	@Column(name = "data_inicial2")
	private Date dataInicial2;

	@Column(name = "data_final2")
	private Date dataFinal2;	

	private Double valor;
	
	private String observacao;

	public Medicao() {

	}
	
	public Medicao(Agenda agenda) {
		
		this.agenda = agenda;

	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
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

	public Agenda getAgenda() {
		return agenda;
	}

	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}
	
	@Override
	public List<Medicao> findByModel(String... fieldsOrderBy) {
		return findByModel(null, fieldsOrderBy);
	}

	
	@Override
	public List<Medicao> findByModel(Map<String, Object> map, String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();

		query.append(" from Medicao m where 1=1 ");
		
		if (!TSUtil.isEmpty(agenda) && !TSUtil.isEmpty(agenda.getId())) {
			query.append("and m.agenda.id = ? ");
		} else {
			query.append("and m.agenda.id is null");
		}
		
		List<Object> params = new ArrayList<Object>();

		if (!TSUtil.isEmpty(agenda) && !TSUtil.isEmpty(agenda.getId())) {
			params.add(agenda.getId());
		}
		
		return super.find(query.toString(), "m.dataInicial, m.id", params.toArray());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((agenda == null) ? 0 : agenda.hashCode());
		result = prime * result + ((dataFinal == null) ? 0 : dataFinal.hashCode());
		result = prime * result + ((dataInicial == null) ? 0 : dataInicial.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((operador == null) ? 0 : operador.hashCode());
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
		Medicao other = (Medicao) obj;
		if (agenda == null) {
			if (other.agenda != null)
				return false;
		} else if (!agenda.equals(other.agenda))
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (operador == null) {
			if (other.operador != null)
				return false;
		} else if (!operador.equals(other.operador))
			return false;
		if (valor == null) {
			if (other.valor != null)
				return false;
		} else if (!valor.equals(other.valor))
			return false;
		return true;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDataInicial2() {
		return dataInicial2;
	}

	public void setDataInicial2(Date dataInicial2) {
		this.dataInicial2 = dataInicial2;
	}

	public Date getDataFinal2() {
		return dataFinal2;
	}

	public void setDataFinal2(Date dataFinal2) {
		this.dataFinal2 = dataFinal2;
	}
}
