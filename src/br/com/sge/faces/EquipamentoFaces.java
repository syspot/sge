package br.com.sge.faces;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.sge.model.Equipamento;
import br.com.sge.model.Fornecedor;
import br.com.sge.model.TipoEquipamento;
import br.com.topsys.util.TSUtil;

@ViewScoped
@ManagedBean(name = "equipamentoFaces")
public class EquipamentoFaces extends CrudFaces<Equipamento> {

	private List<SelectItem> comboFornecedores;
	private List<SelectItem> comboTiposEquipamentos;

	@PostConstruct
	protected void init() {
		this.clearFields();
		this.comboFornecedores = super.initCombo(new Fornecedor(Boolean.TRUE).findByModel("nome"), "id", "nome");
		this.comboTiposEquipamentos = super.initCombo(new TipoEquipamento(Boolean.TRUE).findByModel("descricao"), "id", "descricao");
		setFieldOrdem("descricao");
	}

	@Override
	public String limpar() {
		super.limpar();
		getCrudModel().setFornecedor(new Fornecedor());
		getCrudModel().setTipoEquipamento(new TipoEquipamento());
		getCrudModel().setFlagAtivo(Boolean.TRUE);
		return null;

	}

	@Override
	public String limparPesquisa() {
		super.limparPesquisa();
		getCrudPesquisaModel().setFornecedor(new Fornecedor());
		getCrudPesquisaModel().setTipoEquipamento(new TipoEquipamento());
		getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		return null;

	}
	
	protected void prePersist(){
		
		if (!TSUtil.isEmpty(getCrudModel().getFornecedor()) && TSUtil.isEmpty(getCrudModel().getFornecedor().getId())) {
			getCrudModel().setFornecedor(null);
		}
		
	}

	protected void posDetail() {

		if (TSUtil.isEmpty(getCrudModel().getFornecedor())) {
			getCrudModel().setFornecedor(new Fornecedor());
		}

	}

	public List<SelectItem> getComboFornecedores() {
		return comboFornecedores;
	}

	public void setComboFornecedores(List<SelectItem> comboFornecedores) {
		this.comboFornecedores = comboFornecedores;
	}

	public List<SelectItem> getComboTiposEquipamentos() {
		return comboTiposEquipamentos;
	}

	public void setComboTiposEquipamentos(List<SelectItem> comboTiposEquipamentos) {
		this.comboTiposEquipamentos = comboTiposEquipamentos;
	}

}
