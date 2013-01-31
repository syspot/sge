package br.com.sge.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import br.com.sge.model.Agenda;
import br.com.sge.model.Cliente;
import br.com.sge.model.Contrato;
import br.com.sge.model.Financeiro;
import br.com.sge.model.FinanceiroParcial;
import br.com.sge.model.Fonte;
import br.com.sge.model.TipoTransacao;
import br.com.sge.util.Constantes;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@ViewScoped
@ManagedBean(name = "financeiroFaces")
public class FinanceiroFaces extends CrudFaces<Financeiro> {

	private List<SelectItem> comboTiposTransacoes;
	private List<SelectItem> comboFontes;
	private List<SelectItem> comboFontesPesquisa;
	private List<SelectItem> comboClientes;
	private List<SelectItem> comboContratos;

	private Double totalDespesas;
	private Double totalReceitas;
	private Double totalDespesasPagas;
	private Double totalReceitasPagas;

	private Financeiro financeiroSelecionado;
	private FinanceiroParcial parcialSelecionado;

	@PostConstruct
	protected void init() {
		this.clearFields();
		this.comboTiposTransacoes = super.initCombo(new TipoTransacao().findByModel("descricao"), "id", "descricao");
		this.comboFontes = super.initCombo(new Fonte(Boolean.TRUE, new TipoTransacao(2L)).findByModel("descricao"), "id", "descricao");
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
		getCrudModel().setParciais(new ArrayList<FinanceiroParcial>());
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
		getCrudPesquisaModel().setParciais(new ArrayList<FinanceiroParcial>());
		totalDespesas = 0D;
		totalReceitas = 0D;
		totalDespesasPagas = 0D;
		totalReceitasPagas = 0D;
		return null;

	}

	public void addParcial() {
		FinanceiroParcial f = new FinanceiroParcial(getCrudModel());
		f.setDataPagamento(new Date());
		f.setValor(getCrudModel().getValor() - getCrudModel().getValorParcial());
		getCrudModel().getParciais().add(f);
	}

	public void delParcial() {
		getCrudModel().getParciais().remove(parcialSelecionado);
	}

	@Override
	protected void prePersist() {

		if (!TSUtil.isEmpty(getCrudModel().getAgenda()) && TSUtil.isEmpty(getCrudModel().getAgenda().getId())) {
			getCrudModel().setAgenda(null);
		}

	}

	protected void posPersist() {
		calcularGrid();
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam("valido", true);

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
	
	public void pagarIntegralmente() {
		addParcial();
		atualizarSituacao();
	}

	public void atualizarSituacao() {

		if (TSUtil.isEmpty(getCrudModel().getParciais())) {
			getCrudModel().setDataPagamento(null);
		} else {
			getCrudModel().setDataPagamento(new Date());
		}

		super.updateEvent();
		
	}

	public void importarMedicoes() throws TSApplicationException {
		getCrudModel().importarMedicoes();
		find();
	}

	public void atualizarContratosPesquisa() {
		getCrudPesquisaModel().getAgenda().getContrato().getCliente().setContratos(new Contrato(getCrudPesquisaModel().getAgenda().getContrato().getCliente()).findByModel("descricao"));
	}

	public void atualizarComboFontes() {
		this.comboFontes = super.initCombo(new Fonte(Boolean.TRUE, getCrudModel().getTipoTransacao()).findByModel("descricao"), "id", "descricao");
	}

	public void atualizarComboFontesPesquisa() {
		this.comboFontesPesquisa = super.initCombo(new Fonte(Boolean.TRUE, getCrudPesquisaModel().getTipoTransacao()).findByModel("descricao"), "id", "descricao");
	}

	private void calcularGrid() {

		totalDespesas = 0D;
		totalReceitas = 0D;
		totalDespesasPagas = 0D;
		totalReceitasPagas = 0D;

		for (Financeiro model : getGrid()) {
			if (Constantes.RECEITA.equals(model.getTipoTransacao().getId())) {

				totalReceitas = totalReceitas + model.getValor();
				totalReceitasPagas = totalReceitasPagas + model.getValorParcial();

			} else if (Constantes.DESPESA.equals(model.getTipoTransacao().getId())) {

				totalDespesas = totalDespesas + model.getValor();
				totalDespesasPagas = totalDespesasPagas + model.getValorParcial();

			}
		}

	}

	@Override
	protected String find() {

		super.find();

		calcularGrid();

		return null;
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

	public Double getTotalDespesas() {
		return totalDespesas;
	}

	public void setTotalDespesas(Double totalDespesas) {
		this.totalDespesas = totalDespesas;
	}

	public Double getTotalReceitas() {
		return totalReceitas;
	}

	public void setTotalReceitas(Double totalReceitas) {
		this.totalReceitas = totalReceitas;
	}

	public Double getTotalDespesasPagas() {
		return totalDespesasPagas;
	}

	public void setTotalDespesasPagas(Double totalDespesasPagas) {
		this.totalDespesasPagas = totalDespesasPagas;
	}

	public Double getTotalReceitasPagas() {
		return totalReceitasPagas;
	}

	public void setTotalReceitasPagas(Double totalReceitasPagas) {
		this.totalReceitasPagas = totalReceitasPagas;
	}

	public List<SelectItem> getComboFontesPesquisa() {
		return comboFontesPesquisa;
	}

	public void setComboFontesPesquisa(List<SelectItem> comboFontesPesquisa) {
		this.comboFontesPesquisa = comboFontesPesquisa;
	}

	public FinanceiroParcial getParcialSelecionado() {
		return parcialSelecionado;
	}

	public void setParcialSelecionado(FinanceiroParcial parcialSelecionado) {
		this.parcialSelecionado = parcialSelecionado;
	}

}
