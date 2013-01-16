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
@Table(name = "contrato")
public class Contrato extends TSActiveRecordAb<Contrato> {

	@Id
	@SequenceGenerator(name = "CONTRATO_ID_SEQ", sequenceName = "contrato_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "CONTRATO_ID_SEQ", strategy = GenerationType.SEQUENCE)
	private Long id;

	private String descricao;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

	@Column(name = "data_contrato")
	private Date dataContrato;

	@Column(name = "flag_ativo")
	private Boolean flagAtivo;

	public Contrato() {

	}

	public Contrato(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}
	
	public Contrato(Long id) {
		this.id = id;
	}
	
	public Contrato(Cliente cliente) {
		this.cliente = cliente;
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getDataContrato() {
		return dataContrato;
	}

	public void setDataContrato(Date dataContrato) {
		this.dataContrato = dataContrato;
	}

	public String getDescricaoTotal() {
		StringBuilder sb = new StringBuilder(this.getCliente().getNome().toUpperCase() + ": ");
		sb.append(this.getId() + ". ");
		sb.append(this.getDescricao());

		return sb.toString();
	}
	
	@Override
	public List<Contrato> findByModel(String... fieldsOrderBy) {
		return findByModel(null,fieldsOrderBy);
	}

	@Override
	public List<Contrato> findByModel(Map<String, Object> map,String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();

		query.append(" from Contrato c where 1=1 ");

		if (!TSUtil.isEmpty(flagAtivo)) {
			query.append("and c.flagAtivo = ? ");
		}

		if (!TSUtil.isEmpty(cliente) && !TSUtil.isEmpty(cliente.getId())) {
			query.append("and c.cliente.id = ? ");
		}

		if (!TSUtil.isEmpty(dataContrato)) {
			query.append("and c.dataContrato = ?");
		}

		List<Object> params = new ArrayList<Object>();

		if (!TSUtil.isEmpty(flagAtivo)) {
			params.add(flagAtivo);
		}

		if (!TSUtil.isEmpty(cliente) && !TSUtil.isEmpty(cliente.getId())) {
			params.add(cliente.getId());
		}

		if (!TSUtil.isEmpty(dataContrato)) {
			params.add(dataContrato);
		}

		return super.find(query.toString(), "descricao", params.toArray());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((dataContrato == null) ? 0 : dataContrato.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((flagAtivo == null) ? 0 : flagAtivo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Contrato other = (Contrato) obj;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (dataContrato == null) {
			if (other.dataContrato != null)
				return false;
		} else if (!dataContrato.equals(other.dataContrato))
			return false;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
