package br.com.sge.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "tipo_identificador")
public class TipoIdentificador extends TSActiveRecordAb<TipoIdentificador>  {

	@Id	
	private Long id;
	
	private String descricao;	
	
	private String mascara;
	
	public TipoIdentificador() {
		
	}
	
	public TipoIdentificador(Long id) {
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

	public String getMascara() {
		return mascara;
	}

	public void setMascara(String mascara) {
		this.mascara = mascara;
	}
	
}
