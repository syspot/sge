package br.com.sge.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.sge.model.Fonte;
import br.com.sge.model.TipoTransacao;

@ViewScoped
@ManagedBean(name = "fonteFaces")
public class FonteFaces extends CrudFaces<Fonte> {

	private List<SelectItem> comboTiposTransacoes;

	@PostConstruct
	protected void init() {
		this.clearFields();
		this.comboTiposTransacoes = super.initCombo(new TipoTransacao().findByModel("descricao"), "id", "descricao");
		setFieldOrdem("descricao");
	}

	@Override
	public String limpar() {
		super.limpar();
		getCrudModel().setTipoTransacao(new TipoTransacao());		
		getCrudModel().setFlagAtivo(Boolean.TRUE);
		return null;

	}

	@Override
	public String limparPesquisa() {
		super.limparPesquisa();
		getCrudPesquisaModel().setTipoTransacao(new TipoTransacao());		
		getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		return null;

	}

	public List<SelectItem> getComboTiposTransacoes() {
		return comboTiposTransacoes;
	}

	public void setComboTiposTransacoes(List<SelectItem> comboTiposTransacoes) {
		this.comboTiposTransacoes = comboTiposTransacoes;
	}

}
