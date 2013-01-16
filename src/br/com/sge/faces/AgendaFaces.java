package br.com.sge.faces;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.LazyScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import br.com.sge.model.Agenda;
import br.com.sge.model.Cliente;
import br.com.sge.model.Contrato;
import br.com.sge.model.Equipamento;
import br.com.sge.model.Medicao;
import br.com.sge.model.Operador;
import br.com.sge.model.TipoServico;
import br.com.topsys.exception.TSApplicationException;
import br.com.topsys.util.TSUtil;

@ViewScoped
@ManagedBean(name = "agendaFaces")
public class AgendaFaces extends CrudFaces<Agenda> {

	private List<SelectItem> comboClientes;
	private List<SelectItem> comboContratos;
	private List<SelectItem> comboTiposServicos;
	private List<SelectItem> comboOperadores;
	private List<SelectItem> comboEquipamentos;

	private ScheduleModel eventModel;
	private ScheduleModel lazyEventModel;
	private ScheduleEvent event = new DefaultScheduleEvent();
	private DefaultScheduleEvent sm;

	private Medicao medicaoSelecionada;

	@PostConstruct
	protected void init() {
		this.clearFields();
		this.comboTiposServicos = super.initCombo(new TipoServico().findByModel("descricao"), "id", "descricao");
		this.comboClientes = super.initCombo(new Cliente(Boolean.TRUE).findByModel("nome"), "id", "nome");
		this.comboOperadores = super.initCombo(new Operador(Boolean.TRUE).findByModel("nome"), "id", "nome");
		this.comboEquipamentos = super.initCombo(new Equipamento(Boolean.TRUE).findByModel("descricao"), "id", "descricao");
		setFieldOrdem("dataInicial");
		instanciarAgenda();
	}

	@Override
	public String limpar() {
		super.limpar();
		getCrudModel().setTipoServico(new TipoServico());
		getCrudModel().setContrato(new Contrato());
		getCrudModel().getContrato().setCliente(new Cliente());
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

	@Override
	protected void prePersist() {
		for (Medicao medicao : getCrudModel().getMedicoes()) {
			medicao.setOperador(medicao.getOperadorTemp());
		}
	}

	protected void posDetail() {

		if (TSUtil.isEmpty(getCrudModel().getMedicoes())) {
			getCrudModel().setMedicoes(new ArrayList<Medicao>());			
		} else {
			for (Medicao medicao : getCrudModel().getMedicoes()) {
				medicao.setOperadorTemp(new Operador(medicao.getOperador().getId()));
			}
		}

		if (TSUtil.isEmpty(getCrudModel().getEquipamento())) {
			getCrudModel().setEquipamento(new Equipamento());
		}

		if (TSUtil.isEmpty(getCrudModel().getOperador())) {
			getCrudModel().setOperador(new Operador());
		}

	}

	@SuppressWarnings("serial")
	private void instanciarAgenda() {

		eventModel = new DefaultScheduleModel();

		lazyEventModel = new LazyScheduleModel() {

			@Override
			public void loadEvents(Date start, Date end) {

				clear();

				getCrudPesquisaModel().setDataInicial(start);

				getCrudPesquisaModel().setDataFinal(end);

				for (Agenda model : getCrudPesquisaModel().findByModel("dataInicial")) {

					sm = new DefaultScheduleEvent(model.getContrato().getDescricaoTotal(), model.getDataInicial(), model.getDataFinal(), false);

					sm.setData(model);

					if (model.getFlagConcluido()) {

						sm.setStyleClass(model.getTipoServico().getCssConcluido());

					} else {

						sm.setStyleClass(model.getTipoServico().getCss());

					}

					addEvent(sm);

				}

			}

		};

	}

	public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {

		event = selectEvent.getScheduleEvent();

		setCrudModel((Agenda) event.getData());

		super.detail();

	}

	public void addEvent(ActionEvent actionEvent) throws TSApplicationException {

		if (event.getId() == null) {

			lazyEventModel.addEvent(event);

		} else {

			lazyEventModel.updateEvent(event);

		}

		event = new DefaultScheduleEvent();

	}

	public void delEvent(ActionEvent actionEvent) throws TSApplicationException {

		if (event.getId() != null) {

			eventModel.deleteEvent(event);

		}

		event = new DefaultScheduleEvent();

	}

	public void onDateSelect(DateSelectEvent selectEvent) {

		RequestContext context = RequestContext.getCurrentInstance();

		limpar();

		getCrudModel().setDataInicial(selectEvent.getDate());

		event = new DefaultScheduleEvent(null,

		selectEvent.getDate(), null, false);

		context.addCallbackParam("valido", true);

	}

	public void atualizarContratos() {
		getCrudModel().getContrato().getCliente().setContratos(new Contrato(getCrudModel().getContrato().getCliente()).findByModel("descricao"));
	}

	public void atualizarTipoServico() {
		getCrudModel().setTipoServico(getCrudModel().getTipoServico().getById());
	}

	public void addMedicao() {
		Medicao medicao = new Medicao();
		medicao.setAgenda(this.getCrudModel());
		medicao.setOperador(new Operador());
		medicao.setOperadorTemp(new Operador());
		if (!TSUtil.isEmpty(getCrudModel().getOperador()) && !TSUtil.isEmpty(getCrudModel().getOperador().getId())) {
			medicao.getOperadorTemp().setId(getCrudModel().getOperador().getId());
		}
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

	@Override
	protected String insert() throws TSApplicationException {

		RequestContext context = RequestContext.getCurrentInstance();

		context.addCallbackParam("valido", true);

		return super.insert();

	}

	@Override
	protected String update() throws TSApplicationException {

		RequestContext context = RequestContext.getCurrentInstance();

		context.addCallbackParam("valido", true);

		return super.update();

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

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public ScheduleModel getLazyEventModel() {
		return lazyEventModel;
	}

	public void setLazyEventModel(ScheduleModel lazyEventModel) {
		this.lazyEventModel = lazyEventModel;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public DefaultScheduleEvent getSm() {
		return sm;
	}

	public void setSm(DefaultScheduleEvent sm) {
		this.sm = sm;
	}

}
