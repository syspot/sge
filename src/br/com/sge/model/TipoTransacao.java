package br.com.sge.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "tipo_transacao")
public class TipoTransacao extends TSActiveRecordAb<TipoTransacao>  {

	@Id	
	private Long id;
	
	private String descricao;	
	
	private String css;
	
	@Column(name="css_pago")
	private String cssPago;
	
	public TipoTransacao() {
		
	}
	
	public TipoTransacao(Long id) {
		this.id = id;
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

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getCssPago() {
		return cssPago;
	}

	public void setCssPago(String cssPago) {
		this.cssPago = cssPago;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((css == null) ? 0 : css.hashCode());
		result = prime * result + ((cssPago == null) ? 0 : cssPago.hashCode());
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
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
		TipoTransacao other = (TipoTransacao) obj;
		if (css == null) {
			if (other.css != null)
				return false;
		} else if (!css.equals(other.css))
			return false;
		if (cssPago == null) {
			if (other.cssPago != null)
				return false;
		} else if (!cssPago.equals(other.cssPago))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
