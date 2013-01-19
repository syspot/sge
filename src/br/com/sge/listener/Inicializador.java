package br.com.sge.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Inicializador implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.getProperties().put("org.apache.el.parser.COERCE_TO_ZERO", "false");
	}
}