package br.com.sge.faces;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.sge.model.Agenda;
import br.com.sge.model.Cliente;
import br.com.sge.model.Contrato;
import br.com.sge.model.Financeiro;
import br.com.sge.model.Fonte;
import br.com.sge.model.TipoTransacao;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@ViewScoped
@ManagedBean(name = "financeiroFaces")
public class FinanceiroFaces extends CrudFaces<Financeiro> {

	private List<SelectItem> comboTiposTransacoes;
	private List<SelectItem> comboFontes;
	private List<SelectItem> comboClientes;
	private List<SelectItem> comboContratos;

	private Financeiro financeiroSelecionado;

	@PostConstruct
	protected void init() {
		this.clearFields();
		this.comboTiposTransacoes = super.initCombo(new TipoTransacao().findByModel("descricao"), "id", "descricao");
		this.comboFontes = super.initCombo(new Fonte(Boolean.TRUE).findByModel("descricao"), "id", "descricao");
		this.comboClientes = super.initCombo(new Cliente(Boolean.TRUE).findByModel("nome"), "id", "nome");
		setFieldOrdem("dataLancamento");

	}

	@Override
	public String limpar() {
		setTabIndex(1);
		super.limpar();
		getCrudModel().setTipoTransacao(new TipoTransacao());
		getCrudModel().setAgenda(new Agenda());
		getCrudModel().getAgenda().setContrato(new Contrato());
		getCrudModel().getAgenda().getContrato().setCliente(new Cliente());
		getCrudModel().setFonte(new Fonte());
		getCrudModel().setDataLancamento(new Date());
		return null;

	}

	@Override
	public String limparPesquisa() {
		super.limparPesquisa();
		getCrudPesquisaModel().setTipoTransacao(new TipoTransacao());
		getCrudPesquisaModel().setAgenda(new Agenda());
		getCrudPesquisaModel().getAgenda().setContrato(new Contrato());
		getCrudPesquisaModel().getAgenda().getContrato().setCliente(new Cliente());
		getCrudPesquisaModel().setFonte(new Fonte());
		return null;

	}

	@Override
	protected void prePersist() {

		if (!TSUtil.isEmpty(getCrudModel().getAgenda()) && TSUtil.isEmpty(getCrudModel().getAgenda().getId())) {
			getCrudModel().setAgenda(null);
		}
				
	}

	protected void posDetail() {

		if (TSUtil.isEmpty(getCrudModel().getAgenda())) {
			getCrudModel().setAgenda(new Agenda());
		}

		setTabIndex(1);

	}

	protected void posDelete() {

		setTabIndex(0);

	}

	public void atualizarSituação() {
		if (TSUtil.isEmpty(getCrudModel().getDataPagamento())) {
			getCrudModel().setDataPagamento(new Date());
		} else {
			getCrudModel().setDataPagamento(null);
		}
		
		super.updateEvent();
	}
	
	public void importarMedicoes() throws TSApplicationException {
		getCrudModel().importarMedicoes();
		super.findEvent();
	}

	public void atualizarContratosPesquisa() {
		getCrudPesquisaModel().getAgenda().getContrato().getCliente().setContratos(new Contrato(getCrudPesquisaModel().getAgenda().getContrato().getCliente()).findByModel("descricao"));
	}

	public List<SelectItem> getComboTiposTransacoes() {
		return comboTiposTransacoes;
	}

	public void setComboTiposTransacoes(List<SelectItem> comboTiposTransacoes) {
		this.comboTiposTransacoes = comboTiposTransacoes;
	}

	public List<SelectItem> getComboFontes() {
		return comboFontes;
	}

	public void setComboFontes(List<SelectItem> comboFontes) {
		this.comboFontes = comboFontes;
	}

	public Financeiro getFinanceiroSelecionado() {
		return financeiroSelecionado;
	}

	public void setFinanceiroSelecionado(Financeiro financeiroSelecionado) {
		this.financeiroSelecionado = financeiroSelecionado;
	}

	public List<SelectItem> getComboClientes() {
		return comboClientes;
	}

	public void setComboClientes(List<SelectItem> comboClientes) {
		this.comboClientes = comboClientes;
	}

	public List<SelectItem> getComboContratos() {
		return comboContratos;
	}

	public void setComboContratos(List<SelectItem> comboContratos) {
		this.comboContratos = comboContratos;
	}

}
