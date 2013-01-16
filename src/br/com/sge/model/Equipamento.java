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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((flagAtivo == null) ? 0 : flagAtivo.hashCode());
		result = prime * result + ((fornecedor == null) ? 0 : fornecedor.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((identificador == null) ? 0 : identificador.hashCode());
		result = prime * result + ((percentualFornecedor == null) ? 0 : percentualFornecedor.hashCode());
		result = prime * result + ((tipoEquipamento == null) ? 0 : tipoEquipamento.hashCode());
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
		Equipamento other = (Equipamento) obj;
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
		if (fornecedor == null) {
			if (other.fornecedor != null)
				return false;
		} else if (!fornecedor.equals(other.fornecedor))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (identificador == null) {
			if (other.identificador != null)
				return false;
		} else if (!identificador.equals(other.identificador))
			return false;
		if (percentualFornecedor == null) {
			if (other.percentualFornecedor != null)
				return false;
		} else if (!percentualFornecedor.equals(other.percentualFornecedor))
			return false;
		if (tipoEquipamento == null) {
			if (other.tipoEquipamento != null)
				return false;
		} else if (!tipoEquipamento.equals(other.tipoEquipamento))
			return false;
		return true;
	}

}
