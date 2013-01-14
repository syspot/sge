package br.com.sge.faces;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.sge.model.Fornecedor;

@ViewScoped
@ManagedBean(name = "fornecedorFaces")
public class FornecedorFaces extends CrudFaces<Fornecedor> {

	@PostConstruct
	protected void init() {
		this.clearFields();		
		setFieldOrdem("nome");
	}
	
	
	@Override
	public String limpar(){
		super.limpar();
		getCrudModel().setFlagAtivo(Boolean.TRUE);
		return null;
		
	}
	
	@Override
	public String limparPesquisa(){
		super.limparPesquisa();
		getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		return null;
		
	}
	

}
