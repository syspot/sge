package br.com.sge.faces;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import br.com.sge.model.Agenda;
import br.com.sge.model.Cliente;
import br.com.sge.model.Contrato;
import br.com.sge.model.Equipamento;
import br.com.sge.model.Operador;
import br.com.sge.util.Constantes;
import br.com.sge.util.JasperUtil;
import br.com.topsys.web.faces.TSMainFaces;
import br.com.topsys.web.util.TSFacesUtil;

@ViewScoped
@ManagedBean(name = "relatorioFaces")
public class RelatorioFaces extends TSMainFaces {
	
	private List<SelectItem> comboClientes;
	private List<SelectItem> comboEquipamentos;
	private List<SelectItem> comboContratos;
	private List<SelectItem> comboOperadores;
	
	private Agenda agendaSelecionada;
	

	@PostConstruct
	protected void init() {
		this.clearFields();		
		
	}
	
	@Override
	protected void clearFields() {
		agendaSelecionada = new Agenda();
		agendaSelecionada.setEquipamento(new Equipamento());
		agendaSelecionada.setContrato(new Contrato());		
		agendaSelecionada.getContrato().setCliente(new Cliente());
		agendaSelecionada.setOperador(new Operador());
		this.comboClientes = super.initCombo(new Cliente(Boolean.TRUE).findByModel("nome"), "id", "nome");
		this.comboOperadores = super.initCombo(new Operador(Boolean.TRUE).findByModel("nome"), "id", "nome");
		this.comboEquipamentos = super.initCombo(new Equipamento(Boolean.TRUE).findByModel("descricao"), "id", "descricao");
	}	
	
	public String imprimirProgramacaoAnaliticaCliente() {

		try {

			Map<String, Object> parametros = new HashMap<String, Object>();

			parametros.put("CLIENTE_ID", this.agendaSelecionada.getContrato().getCliente().getId());
			
			parametros.put("CONTRATO_ID", this.agendaSelecionada.getContrato().getId());			

			parametros.put("DATA_INICIAL", this.agendaSelecionada.getDataInicial());
			
			parametros.put("DATA_FINAL", this.agendaSelecionada.getDataFinal());

			parametros.put("SUBREPORT_DIR", TSFacesUtil.getServletContext().getRealPath("WEB-INF" + File.separator + Constantes.PASTA_RELATORIO) + File.separator);

			JasperUtil jasperUtil = new JasperUtil();

			jasperUtil.gerarRelatorio("programacaoAnaliticaCliente.jasper", parametros);


		} catch (Exception ex) {
			this.addErrorMessage("Erro ao imprimir, contate o administrador do sistema");
			ex.printStackTrace();
		}

		return SUCESSO;

	}
	
	public String imprimirMedicaoOperador() {

		try {

			Map<String, Object> parametros = new HashMap<String, Object>();

			parametros.put("OPERADOR_ID", this.agendaSelecionada.getOperador().getId());
			
			parametros.put("CONTRATO_ID", this.agendaSelecionada.getContrato().getId());			

			parametros.put("DATA_INICIAL", this.agendaSelecionada.getDataInicial());
			
			parametros.put("DATA_FINAL", this.agendaSelecionada.getDataFinal());

			parametros.put("SUBREPORT_DIR", TSFacesUtil.getServletContext().getRealPath("WEB-INF" + File.separator + Constantes.PASTA_RELATORIO) + File.separator);

			JasperUtil jasperUtil = new JasperUtil();

			jasperUtil.gerarRelatorio("medicaoOperador.jasper", parametros);

		} catch (Exception ex) {
			this.addErrorMessage("Erro ao imprimir, contate o administrador do sistema");
			ex.printStackTrace();
		}

		return SUCESSO;

	}
	
	public String imprimirMedicaoEquipamento() {

		try {

			Map<String, Object> parametros = new HashMap<String, Object>();

			parametros.put("EQUIPAMENTO_ID", this.agendaSelecionada.getEquipamento().getId());
			
			parametros.put("CONTRATO_ID", this.agendaSelecionada.getContrato().getId());			

			parametros.put("DATA_INICIAL", this.agendaSelecionada.getDataInicial());
			
			parametros.put("DATA_FINAL", this.agendaSelecionada.getDataFinal());

			parametros.put("SUBREPORT_DIR", TSFacesUtil.getServletContext().getRealPath("WEB-INF" + File.separator + Constantes.PASTA_RELATORIO) + File.separator);

			JasperUtil jasperUtil = new JasperUtil();

			jasperUtil.gerarRelatorio("medicaoEquipamento.jasper", parametros);
			
			RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("valido", true);

		} catch (Exception ex) {
			this.addErrorMessage("Erro ao imprimir, contate o administrador do sistema");
			ex.printStackTrace();
		}

		return SUCESSO;

	}
	
	public String imprimirFinanceiroAnalitico() {

		try {

			Map<String, Object> parametros = new HashMap<String, Object>();

			parametros.put("CLIENTE_ID", this.agendaSelecionada.getContrato().getCliente().getId());
			
			parametros.put("CONTRATO_ID", this.agendaSelecionada.getContrato().getId());			

			parametros.put("DATA_INICIAL", this.agendaSelecionada.getDataInicial());
			
			parametros.put("DATA_FINAL", this.agendaSelecionada.getDataFinal());

			parametros.put("SUBREPORT_DIR", TSFacesUtil.getServletContext().getRealPath("WEB-INF" + File.separator + Constantes.PASTA_RELATORIO) + File.separator);

			JasperUtil jasperUtil = new JasperUtil();

			jasperUtil.gerarRelatorio("financeiroAnalitico.jasper", parametros);
			
			RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("valido", true);

		} catch (Exception ex) {
			this.addErrorMessage("Erro ao imprimir, contate o administrador do sistema");
			ex.printStackTrace();
		}

		return SUCESSO;

	}
	
	public String imprimirFinanceiroCliente() {

		try {

			Map<String, Object> parametros = new HashMap<String, Object>();

			parametros.put("CLIENTE_ID", this.agendaSelecionada.getContrato().getCliente().getId());							

			parametros.put("DATA_INICIAL", this.agendaSelecionada.getDataInicial());
			
			parametros.put("DATA_FINAL", this.agendaSelecionada.getDataFinal());

			parametros.put("SUBREPORT_DIR", TSFacesUtil.getServletContext().getRealPath("WEB-INF" + File.separator + Constantes.PASTA_RELATORIO) + File.separator);

			JasperUtil jasperUtil = new JasperUtil();

			jasperUtil.gerarRelatorio("financeiroCliente.jasper", parametros);
			
			RequestContext context = RequestContext.getCurrentInstance();
			context.addCallbackParam("valido", true);

		} catch (Exception ex) {
			this.addErrorMessage("Erro ao imprimir, contate o administrador do sistema");
			ex.printStackTrace();
		}

		return SUCESSO;

	}
	
	public void atualizarContratos() {
		agendaSelecionada.getContrato().getCliente().setContratos(new Contrato(agendaSelecionada.getContrato().getCliente()).findByModel("descricao"));
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

	public Agenda getAgendaSelecionada() {
		return agendaSelecionada;
	}

	public void setAgendaSelecionada(Agenda agendaSelecionada) {
		this.agendaSelecionada = agendaSelecionada;
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

}
