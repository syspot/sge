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
	public List<Equipamento> findByModel(String... fieldsOrderBy) {
		return findByModel(null, fieldsOrderBy);
	}

	@Override
	public List<Equipamento> findByModel(Map<String, Object> map, String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();

		query.append(" from Equipamento e where 1=1 ");

		if (!TSUtil.isEmpty(flagAtivo)) {
			query.append("and e.flagAtivo = ? ");
		}

		if (!TSUtil.isEmpty(tipoEquipamento) && !TSUtil.isEmpty(tipoEquipamento.getId())) {
			query.append("and e.tipoEquipamento.id = ? ");
		}

		if (!TSUtil.isEmpty(descricao)) {
			query.append("and lower(e.descricao) like ?");
		}

		if (!TSUtil.isEmpty(identificador)) {
			query.append("and lower(e.identificador) like ?");
		}

		if (!TSUtil.isEmpty(fornecedor) && !TSUtil.isEmpty(fornecedor.getId())) {
			query.append("and e.fornecedor.id = ? ");
		}

		List<Object> params = new ArrayList<Object>();

		if (!TSUtil.isEmpty(flagAtivo)) {
			params.add(flagAtivo);
		}

		if (!TSUtil.isEmpty(tipoEquipamento) && !TSUtil.isEmpty(tipoEquipamento.getId())) {
			params.add(tipoEquipamento.getId());
		}

		if (!TSUtil.isEmpty(descricao)) {
			params.add("%" + descricao.toLowerCase() + "%");
		}

		if (!TSUtil.isEmpty(identificador)) {
			params.add("%" + identificador.toLowerCase() + "%");
		}

		if (!TSUtil.isEmpty(fornecedor) && !TSUtil.isEmpty(fornecedor.getId())) {
			params.add(fornecedor.getId());
		}

		return super.find(query.toString(), "descricao", params.toArray());
	}

	public List<Equipamento> Disponiveis(Long equipamentoID, Date dataInicial, Date dataFinal) {		

		List<Object> lista = new ArrayList<Object>();
		
		if (TSUtil.isEmpty(equipamentoID)) {
			equipamentoID = 0L;
		}

		String query = "SELECT * FROM EQUIPAMENTO E WHERE (FLAG_ATIVO AND NOT EXISTS (SELECT 1 FROM AGENDA A WHERE A.EQUIPAMENTO_ID = E.ID AND FLAG_CONCLUIDO = FALSE AND (A.DATA_INICIAL BETWEEN ? AND ? OR A.DATA_FINAL BETWEEN ? AND ?))) OR ID = ? ORDER BY E.DESCRICAO";

		lista.add(dataInicial);
		lista.add(dataFinal);
		lista.add(dataInicial);
		lista.add(dataFinal);	
		lista.add(equipamentoID);

		return findBySQL(query, lista.toArray());

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
