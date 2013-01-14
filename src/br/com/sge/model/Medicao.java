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

	private Double valor;

	public Medicao() {

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
}
