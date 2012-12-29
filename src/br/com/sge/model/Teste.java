package br.com.sge.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.topsys.database.hibernate.TSActiveRecordAb;
import br.com.topsys.util.TSUtil;

@Entity
@Table(name = "teste")
public class Teste extends TSActiveRecordAb<Teste>  {

	@Id
	@SequenceGenerator(name = "TESTE_ID_SEQ", sequenceName = "teste_id_seq", allocationSize = 1)
	@GeneratedValue(generator="TESTE_ID_SEQ", strategy=GenerationType.SEQUENCE)
	private Long id;
	
	private String descricao;		
	
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
	
}
