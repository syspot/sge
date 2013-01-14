package br.com.sge.model;

import java.util.Date;
import java.util.List;

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
}
