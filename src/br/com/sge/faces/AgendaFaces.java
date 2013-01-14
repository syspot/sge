package br.com.sge.faces;

import java.util.ArrayList;
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
import br.com.topsys.util.TSUtil;

@ViewScoped
@ManagedBean(name = "agendaFaces")
public class AgendaFaces extends CrudFaces<Agenda> {

	private List<SelectItem> comboClientes;
	private List<SelectItem> comboContratos;
	private List<SelectItem> comboTiposServicos;
	private List<SelectItem> comboOperadores;
	private List<SelectItem> comboEquipamentos;

	private Medicao medicaoSelecionada;

	@PostConstruct
	protected void init() {
		this.clearFields();
		this.comboTiposServicos = super.initCombo(new TipoServico().findByModel("descricao"), "id", "descricao");
		this.comboClientes = super.initCombo(new Cliente(Boolean.TRUE).findByModel("nome"), "id", "nome");
		this.comboOperadores = super.initCombo(new Operador(Boolean.TRUE).findByModel("nome"), "id", "nome");
		this.comboEquipamentos = super.initCombo(new Equipamento(Boolean.TRUE).findByModel("descricao"), "id", "descricao");
		setFieldOrdem("dataInicial");
	}

	@Override
	public String limpar() {
		super.limpar();
		getCrudModel().setTipoServico(new TipoServico());
		getCrudModel().setContrato(new Contrato());
		getCrudModel().setOperador(new Operador());
		getCrudModel().setEquipamento(new Equipamento());
		getCrudModel().setMedicoes(new ArrayList<Medicao>());
		return null;

	}

	@Override
	public String limparPesquisa() {
		super.limparPesquisa();
		getCrudPesquisaModel().setTipoServico(new TipoServico());
		getCrudPesquisaModel().setContrato(new Contrato());
		getCrudPesquisaModel().setOperador(new Operador());
		getCrudPesquisaModel().setEquipamento(new Equipamento());
		return null;

	}

	protected void posDetail() {

		if (TSUtil.isEmpty(getCrudModel().getMedicoes())) {
			getCrudModel().setMedicoes(new ArrayList<Medicao>());
		}

		if (TSUtil.isEmpty(getCrudModel().getEquipamento())) {
			getCrudModel().setEquipamento(new Equipamento());
		}

		if (TSUtil.isEmpty(getCrudModel().getOperador())) {
			getCrudModel().setOperador(new Operador());
		}

	}
	
	public void atualizarContratos() {
		getCrudModel().getContrato().getCliente().setContratos(getCrudModel().getContrato().findByModel("descricao"));
	}

	public void atualizarTipoServico() {
		getCrudModel().setTipoServico(getCrudModel().getTipoServico().getById());
	}

	public void addMedicao() {
		Medicao medicao = new Medicao();
		medicao.setAgenda(this.getCrudModel());
		medicao.setOperador(getCrudModel().getOperador());
		if (this.getCrudModel().getMedicoes().isEmpty()) {
			medicao.setDataInicial(getCrudModel().getDataInicial());
			medicao.setDataFinal(getCrudModel().getDataFinal());
			medicao.setValor(getCrudModel().getValor());
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

	public List<SelectItem> getComboOperadores() {
		return comboOperadores;
	}

	public void setComboOperadores(List<SelectItem> comboOperadores) {
		this.comboOperadores = comboOperadores;
	}

	public List<SelectItem> getComboEquipamentos() {
		return comboEquipamentos;
	}

	public void setComboEquipamentos(List<SelectItem> comboEquipamentos) {
		this.comboEquipamentos = comboEquipamentos;
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

}
