package br.com.sge.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "permissao")
public class Permissao extends TSActiveRecordAb<Permissao> {

	@Id
	@SequenceGenerator(name = "PERMISSAO_ID_SEQ", sequenceName = "permissao_id_seq", allocationSize = 1)
	@GeneratedValue(generator="PERMISSAO_ID_SEQ", strategy=GenerationType.SEQUENCE)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "grupo_id")
	private Grupo grupo;

	@ManyToOne
	@JoinColumn(name = "menu_id")	 
	private Menu menu;

	@Column(name = "flag_inserir")
	private Boolean flagInserir;

	@Column(name = "flag_alterar")
	private Boolean flagAlterar;

	@Column(name = "flag_excluir")
	private Boolean flagExcluir;

	public Long getId() {
		return TSUtil.tratarLong(id);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Boolean getFlagInserir() {
		return flagInserir;
	}

	public void setFlagInserir(Boolean flagInserir) {
		this.flagInserir = flagInserir;
	}

	public Boolean getFlagAlterar() {
		return flagAlterar;
	}

	public void setFlagAlterar(Boolean flagAlterar) {
		this.flagAlterar = flagAlterar;
	}

	public Boolean getFlagExcluir() {
		return flagExcluir;
	}

	public void setFlagExcluir(Boolean flagExcluir) {
		this.flagExcluir = flagExcluir;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	public List<Permissao> pesquisarPermissoes() {

		StringBuilder query = new StringBuilder();
		
		query.append(" from Permissao p join fetch p.menu m left join fetch m.menus where p.grupo.id = ? order by m.ordem, m.descricao");
		
		List<Object> params = new ArrayList<Object>();
		
		params.add(grupo.getId());		
		
		return super.find(query.toString(), null, params.toArray());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((grupo == null) ? 0 : grupo.hashCode());
		result = prime * result + ((menu == null) ? 0 : menu.hashCode());
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
		Permissao other = (Permissao) obj;
		if (grupo == null) {
			if (other.grupo != null)
				return false;
		} else if (!grupo.equals(other.grupo))
			return false;
		if (menu == null) {
			if (other.menu != null)
				return false;
		} else if (!menu.equals(other.menu))
			return false;
		return true;
	}

	
	

}
