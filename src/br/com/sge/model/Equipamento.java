package br.com.sge.model;

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
@Table(name = "equipamento")
public class Equipamento extends TSActiveRecordAb<Equipamento> {

	@Id
	@SequenceGenerator(name = "EQUIPAMENTO_ID_SEQ", sequenceName = "equipamento_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "EQUIPAMENTO_ID_SEQ", strategy = GenerationType.SEQUENCE)
	private Long id;

	private String descricao;

	@ManyToOne
	@JoinColumn(name = "tipo_equipamento_id")
	private TipoEquipamento tipoEquipamento;

	@ManyToOne
	@JoinColumn(name = "fornecedor_id")
	private Fornecedor fornecedor;

	@Column(name = "percentual_fornecedor")
	private Double percentualFornecedor;

	private String identificador;

	@Column(name = "flag_ativo")
	private Boolean flagAtivo;

	public Equipamento() {

	}

	public Equipamento(Boolean flagAtivo) {

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

	public String getSituacao() {
		return (flagAtivo.equals(Boolean.TRUE) ? "ATIVO" : "INATIVO");
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoEquipamento getTipoEquipamento() {
		return tipoEquipamento;
	}

	public void setTipoEquipamento(TipoEquipamento tipoEquipamento) {
		this.tipoEquipamento = tipoEquipamento;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Double getPercentualFornecedor() {
		return percentualFornecedor;
	}

	public void setPercentualFornecedor(Double percentualFornecedor) {
		this.percentualFornecedor = percentualFornecedor;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

}
