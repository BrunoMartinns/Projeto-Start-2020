package br.com.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import br.com.conexao.Conexao;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class Relatorio {

	JasperReport jr;
	Connection conn;
	JasperPrint jp;

	public void gerar() throws Exception {

		try {

			String arquivoXML = "Relator.jrxml";
			conn = Conexao.createConnectionToMySQL();
			jr = JasperCompileManager.compileReport(arquivoXML);
			jp = JasperFillManager.fillReport(jr, null, conn);
			JasperViewer.viewReport(jp);
			conn.close();

		} catch (SQLException ex) {
			Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
		} catch (JRException ex) {
			Logger.getLogger(Relatorio.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void main(String[] args) throws Exception {

		Relatorio r = new Relatorio ();
		r.gerar();
		
		
	}
}
