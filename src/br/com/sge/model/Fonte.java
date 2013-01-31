package br.com.sge.model;

import java.util.ArrayList;
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
@Table(name = "fonte")
public class Fonte extends TSActiveRecordAb<Fonte> {

	@Id
	@SequenceGenerator(name = "FONTE_ID_SEQ", sequenceName = "fonte_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "FONTE_ID_SEQ", strategy = GenerationType.SEQUENCE)
	private Long id;

	private String descricao;
	
	@ManyToOne
	@JoinColumn(name = "tipo_transacao_id")
	private TipoTransacao tipoTransacao;

	@Column(name = "flag_ativo")
	private Boolean flagAtivo;

	public Fonte() {

	}

	public Fonte(Boolean flagAtivo, TipoTransacao tipoTransacao) {

		this.flagAtivo = flagAtivo;
		
		this.tipoTransacao = tipoTransacao;

	}

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
	
	@Override
	public List<Fonte> findByModel(String... fieldsOrderBy) {
		return findByModel(null, fieldsOrderBy);
	}

	@Override
	public List<Fonte> findByModel(Map<String, Object> map, String... fieldsOrderBy) {

		StringBuilder query = new StringBuilder();

		query.append(" from Fonte f where 1=1 ");

		if (!TSUtil.isEmpty(tipoTransacao) && !TSUtil.isEmpty(tipoTransacao.getId())) {
			query.append("and f.tipoTransacao.id = ? ");
		}

		if (!TSUtil.isEmpty(descricao)) {
			query.append("and lower(f.descricao) like ? ");
		}
		
		if (!TSUtil.isEmpty(flagAtivo)) {
			query.append("and f.flagAtivo = ?");			
		}

		List<Object> params = new ArrayList<Object>();

		if (!TSUtil.isEmpty(tipoTransacao) && !TSUtil.isEmpty(tipoTransacao.getId())) {
			params.add(tipoTransacao.getId());
		}

		if (!TSUtil.isEmpty(descricao)) {
			params.add("%" + descricao.toLowerCase() + "%");
		}
		
		if (!TSUtil.isEmpty(flagAtivo)) {
			params.add(flagAtivo);
		}

		
		return super.find(query.toString(), "descricao", params.toArray());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		Fonte other = (Fonte) obj;
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

	public TipoTransacao getTipoTransacao() {
		return tipoTransacao;
	}

	public void setTipoTransacao(TipoTransacao tipoTransacao) {
		this.tipoTransacao = tipoTransacao;
	}

}
