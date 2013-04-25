package br.com.sge.faces;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import br.com.sge.model.Agenda;
import br.com.sge.model.Cliente;
import br.com.sge.model.Contrato;
import br.com.sge.model.Equipamento;
import br.com.sge.model.Medicao;
import br.com.sge.model.Operador;
import br.com.sge.model.TipoServico;
import br.com.sge.util.Constantes;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@ViewScoped
@ManagedBean(name = "agendaFaces")
public class AgendaFaces extends CrudFaces<Agenda> {

	private List<SelectItem> comboClientes;
	private List<SelectItem> comboContratos;
	private List<SelectItem> comboTiposServicos;
	private List<SelectItem> comboOperadoresPesquisa;
	private List<SelectItem> comboEquipamentosPesquisa;

	private Agenda agendaSelecionada;
	private Medicao medicaoSelecionada;

	private List<Medicao> Medicoes;

	@PostConstruct
	protected void init() {
		this.clearFields();
		this.comboTiposServicos = super.initCombo(new TipoServico().findByModel("descricao"), "id", "descricao");
		this.comboClientes = super.initCombo(new Cliente(Boolean.TRUE).findByModel("nome"), "id", "nome");
		this.comboOperadoresPesquisa = super.initCombo(new Operador(Boolean.TRUE).findByModel("nome"), "id", "nome");
		this.comboEquipamentosPesquisa = super.initCombo(new Equipamento(Boolean.TRUE).findByModel("descricao"), "id", "descricao");
		this.Medicoes = new ArrayList<Medicao>();
		setFieldOrdem("dataInicial");
	}

	@Override
	public String limpar() {
		super.limpar();
		getCrudModel().setTipoServico(new TipoServico());
		getCrudModel().setContrato(new Contrato());
		getCrudModel().getContrato().setCliente(new Cliente());
		getCrudModel().setEquipamento(new Equipamento());
		getCrudModel().setMedicoes(new ArrayList<Medicao>());
		return null;

	}

	@Override
	public String limparPesquisa() {
		super.limparPesquisa();
		getCrudPesquisaModel().setTipoServico(new TipoServico());
		getCrudPesquisaModel().setContrato(new Contrato());
		getCrudPesquisaModel().getContrato().setCliente(new Cliente());
		getCrudPesquisaModel().setEquipamento(new Equipamento());
		return null;

	}

	public void atualizarValorMedicao(Medicao medicao) {

		medicao.setValor(calcularValor(medicao.getDataInicial(), medicao.getDataFinal()));
	}

	public Double calcularValor(Date dataInicial, Date dataFinal) {

		if (!TSUtil.isEmpty(getCrudModel().getEquipamento()) && !TSUtil.isEmpty(getCrudModel().getEquipamento().getId()) && !TSUtil.isEmpty(getCrudModel().getTipoServico()) && !TSUtil.isEmpty(getCrudModel().getTipoServico().getId())) {

			Double qtdDias = 0D;
			Double qtdHoras = 0D;

			Calendar d1 = new GregorianCalendar();
			Calendar d2 = new GregorianCalendar();

			d1.setTime(dataInicial);
			d2.setTime(dataFinal);
			Long diferenca;
			diferenca = d2.getTimeInMillis() - d1.getTimeInMillis();
			qtdDias = diferenca.doubleValue() / 1000 / 60 / 60 / 24;
			qtdDias = Math.max(1, qtdDias);

			qtdHoras = diferenca.doubleValue() / 1000 / 60 / 60;
			qtdHoras = Math.max(1, qtdHoras);

			if (Constantes.ALUGUEL.equals(getCrudModel().getTipoServico().getId())) {
				return getCrudModel().getEquipamento().getById().getTipoEquipamento().getValorHora() * qtdHoras;
			} else if (Constantes.SERVICO.equals(getCrudModel().getTipoServico().getId())) {
				return getCrudModel().getEquipamento().getById().getTipoEquipamento().getValorServico() * qtdDias;
			}

		}
		return 0D;

	}

	public String pesquisarBaseadoEquipamentos() {

		setGrid(getCrudPesquisaModel().pesquisarBaseadoEquipamentos());

		if (TSUtil.isEmpty(getGrid())) {
			setGrid(new ArrayList<Agenda>());
		} else {
			posDetail();
		}

		return null;

	}

	protected void posDetail() {

		for (Agenda agenda : getGrid()) {

			setCrudModel(agenda);
			getCrudModel().getContrato().getCliente().setContratos(new Contrato(getCrudModel().getContrato().getCliente()).findByModel("descricao"));
			getCrudModel().setMedicoes(new Medicao(getCrudModel()).findByModel("dataInicial"));

		}

		if (!TSUtil.isEmpty(getGrid())) {
			setCrudModel(getGrid().get(0));
		}

	}

	public void atualizarContratos() {
		getCrudModel().getContrato().getCliente().setContratos(new Contrato(getCrudModel().getContrato().getCliente()).findByModel("descricao"));
	}

	public void atualizarContratos(Contrato contrato) {
		contrato.getCliente().setContratos(new Contrato(contrato.getCliente()).findByModel("descricao"));
	}

	public void atualizarContratosPesquisa() {
		getCrudPesquisaModel().getContrato().getCliente().setContratos(new Contrato(getCrudPesquisaModel().getContrato().getCliente()).findByModel("descricao"));
	}

	public void atualizarTipoServico() {
		getCrudModel().setTipoServico(getCrudModel().getTipoServico().getById());
	}

	public void salvarAlteracoes() throws TSApplicationException {

		for (Agenda agenda : getGrid()) {

			setCrudModel(agenda);

			if (!TSUtil.isEmpty(getCrudModel().getContrato()) && !TSUtil.isEmpty(getCrudModel().getContrato().getId()) && !TSUtil.isEmpty(getCrudModel().getEquipamento()) && !TSUtil.isEmpty(getCrudModel().getEquipamento().getId())) {

				if (TSUtil.isEmpty(getCrudModel().getId())) {
					insert();
				} else {
					update();
				}

			}

		}

		if (!TSUtil.isEmpty(getGrid())) {
			setCrudModel(getGrid().get(0));
		}

	}

	public void addAgenda() {
		Agenda a = new Agenda();

		a.setTipoServico(new TipoServico());
		a.setDataInicial(getCrudPesquisaModel().getDataInicial());
		a.setContrato(new Contrato());
		a.getContrato().setCliente(new Cliente());
		a.setEquipamento(new Equipamento());
		a.setMedicoes(new ArrayList<Medicao>());
		a.setSequencia(new Long(getGrid().size() + 1));
		setCrudModel(a);

		this.getGrid().add(a);
	}

	public void addMedicao() {
		Medicao medicao = new Medicao();
		medicao.setAgenda(getCrudModel());
		medicao.setOperador(new Operador());

		if (TSUtil.isEmpty(this.getCrudModel().getMedicoes())) {
			this.getCrudModel().setMedicoes(new ArrayList<Medicao>());
		}

		this.getCrudModel().getMedicoes().add(medicao);
	}

	public void excluirMedicao() {
		this.getCrudModel().getMedicoes().remove(this.medicaoSelecionada);
	}

	public List<SelectItem> getComboContratos() {
		return comboContratos;
	}

	public void setComboContratos(List<SelectItem> comboContratos) {
		this.comboContratos = comboContratos;
	}

	public List<SelectItem> getComboTiposServicos() {
		return comboTiposServicos;
	}

	public void setComboTiposServicos(List<SelectItem> comboTiposServicos) {
		this.comboTiposServicos = comboTiposServicos;
	}

	public Medicao getMedicaoSelecionada() {
		return medicaoSelecionada;
	}

	public void setMedicaoSelecionada(Medicao medicaoSelecionada) {
		this.medicaoSelecionada = medicaoSelecionada;
	}

	public List<SelectItem> getComboClientes() {
		return comboClientes;
	}

	public void setComboClientes(List<SelectItem> comboClientes) {
		this.comboClientes = comboClientes;
	}

	public List<SelectItem> getComboOperadoresPesquisa() {
		return comboOperadoresPesquisa;
	}

	public void setComboOperadoresPesquisa(List<SelectItem> comboOperadoresPesquisa) {
		this.comboOperadoresPesquisa = comboOperadoresPesquisa;
	}

	public List<SelectItem> getComboEquipamentosPesquisa() {
		return comboEquipamentosPesquisa;
	}

	public void setComboEquipamentosPesquisa(List<SelectItem> comboEquipamentosPesquisa) {
		this.comboEquipamentosPesquisa = comboEquipamentosPesquisa;
	}

	public Agenda getAgendaSelecionada() {
		return agendaSelecionada;
	}

	public void setAgendaSelecionada(Agenda agendaSelecionada) {
		this.agendaSelecionada = agendaSelecionada;
	}

	public List<Medicao> getMedicoes() {
		return Medicoes;
	}

	public void setMedicoes(List<Medicao> medicoes) {
		Medicoes = medicoes;
	}

	public String getLaranja() {
		return "laranja";
	}

}
