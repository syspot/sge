package br.com.sge.faces;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.sge.model.Cliente;
import br.com.sge.model.Contrato;
import br.com.sge.model.TipoIdentificador;
import br.com.topsys.util.TSUtil;

@ViewScoped
@ManagedBean(name = "clienteFaces")
public class ClienteFaces extends CrudFaces<Cliente> {

	private List<SelectItem> tiposIdentificadores;
	private Contrato contratoSelecionado;	

	@PostConstruct
	protected void init() {
		this.clearFields();
		this.tiposIdentificadores = super.initCombo(new TipoIdentificador().findByModel("descricao"), "id", "descricao");		
		setFieldOrdem("nome");
	}

	@Override
	public String limpar() {
		super.limpar();
		getCrudModel().setTipoIdentificador(new TipoIdentificador(2L).getById());
		getCrudModel().setContratos(new ArrayList<Contrato>());
		getCrudModel().setFlagAtivo(Boolean.TRUE);
		return null;

	}

	@Override
	public String limparPesquisa() {
		super.limparPesquisa();
		getCrudPesquisaModel().setFlagAtivo(Boolean.TRUE);
		return null;

	}

	protected void posDetail() {

		if (TSUtil.isEmpty(getCrudModel().getContratos())) {
			getCrudModel().setContratos(new ArrayList<Contrato>());
		}

	}
	
	public void atualizarTipoIdentificador() {
		getCrudModel().setTipoIdentificador(getCrudModel().getTipoIdentificador().getById());
	}

	public void addContrato() {
		Contrato contrato = new Contrato();
		contrato.setCliente(this.getCrudModel());
		contrato.setFlagAtivo(Boolean.TRUE);

		this.getCrudModel().getContratos().add(contrato);
	}

	public void excluirContrato() {
		this.getCrudModel().getContratos().remove(this.contratoSelecionado);
	}

	public List<SelectItem> getTiposIdentificadores() {
		return tiposIdentificadores;
	}

	public void setTiposIdentificadores(List<SelectItem> tiposIdentificadores) {
		this.tiposIdentificadores = tiposIdentificadores;
	}

	public Contrato getContratoSelecionado() {
		return contratoSelecionado;
	}

	public void setContratoSelecionado(Contrato contratoSelecionado) {
		this.contratoSelecionado = contratoSelecionado;
	}

}
