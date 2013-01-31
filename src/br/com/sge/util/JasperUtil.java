package br.com.sge.util;

import java.io.File;
import java.sql.Connection;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import br.com.topsys.exception.TSSystemException;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.util.TSFacesUtil;


public class JasperUtil {

	public void gerarRelatorio(String jasper, Map<String, Object> parametros) {

		Connection con = null;

		try {

			con = ConnectionFactory.getConnection();

			jasper = TSFacesUtil.getServletContext().getRealPath("WEB-INF" + File.separator + "relatorios" + File.separator + jasper);

			JasperPrint impressao = JasperFillManager.fillReport(jasper, parametros, con);

			if (!TSUtil.isEmpty(impressao)) {

				ExternalContext econtext = TSFacesUtil.getFacesContext().getExternalContext();

				HttpServletResponse response = (HttpServletResponse) econtext.getResponse();

				response.setContentType("APPLICATION/PDF");

				response.setHeader("Content-Disposition", "inline");

				JasperExportManager.exportReportToPdfStream(impressao, response.getOutputStream());

				TSFacesUtil.getFacesContext().responseComplete();

			}

		} catch (Exception e) {
	
			throw new TSSystemException(e);
		
		} finally {

			ConnectionFactory.closeConnection(con);

		}

	}

}