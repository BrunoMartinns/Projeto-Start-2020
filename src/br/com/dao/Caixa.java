package br.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

/*import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;*/
import br.com.conexao.Conexao;

@ManagedBean(name = "Caixa")
public class Caixa {

	private String codigoBarras;
	private Float quantidadeProd;
	private String valorUnitario;
	private String nomeProduto;
	private String unidadeMedida;
	private String secao;
	private Float porcentagemGanho;
	private Float valorTotal;
	private Float valorTotalVendas;
	private Float precoUnitario;
	private Float valorFinal;

	
	public Float getQuantidadeProd() {
		return quantidadeProd;
	}

	public void setQuantidadeProd(Float quantidadeProd) {
		this.quantidadeProd = quantidadeProd;
	}
	public Float getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(Float valorFinal) {
		this.valorFinal = valorFinal;
	}

	public Float getPrecoUnitario() {
		return precoUnitario;
	}

	public void setPrecoUnitario(Float precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}



	public String getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(String valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

	public String getSecao() {
		return secao;
	}

	public void setSecao(String secao) {
		this.secao = secao;
	}

	public Float getPorcentagemGanho() {
		return porcentagemGanho;
	}

	public void setPorcentagemGanho(Float porcentagemGanho) {
		this.porcentagemGanho = porcentagemGanho;
	}

	public Float getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Float valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Float getValorTotalVendas() {
		return valorTotalVendas;
	}

	public void setValorTotalVendas(Float valorTotalVendas) {
		this.valorTotalVendas = valorTotalVendas;
	}

//______________________________________________________________________________________________________________

	public void Pesquisar() throws SQLException {

		try {
			Connection conexao = Conexao.createConnectionToMySQL();
			String sql = "Select * from produtos where codigoBarras = '" + codigoBarras + "';";

			PreparedStatement produtosSELECT = conexao.prepareStatement(sql);
			produtosSELECT.executeQuery();
			ResultSet rs = produtosSELECT.executeQuery(sql);

			while (rs.next()) {

				codigoBarras = rs.getString("codigoBarras");
				nomeProduto = rs.getString("nomeProduto");
				valorUnitario = rs.getString("precoUnitario");

			}
			conexao.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

//______________________________________________________________________________________________________________

	public void Insert() {

		String sql = "INSERT INTO funcionarios (codigoBarras, quantidadeProd) VALUES (?, ?)";

		try {
			Connection conn = Conexao.createConnectionToMySQL();

			PreparedStatement cadastroVendas = conn.prepareStatement(sql);

			cadastroVendas.setString(1, codigoBarras);
			cadastroVendas.setFloat(2, quantidadeProd);

			cadastroVendas.execute();

			cadastroVendas.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//______________________________________________________________________________________________________________

	public void DescontoEstoque() throws Exception {

		Connection conexao = null;
		conexao = Conexao.createConnectionToMySQL();

		try {

			String sql = "UPDATE produtos (nomeProduto, secao, porcentagemGanho, codigoBarras, unidadeMedida) VALUES ( ?, ?, ?, ?, ?)";

			PreparedStatement UpdateEstoque = (PreparedStatement) conexao.prepareStatement(sql);

			UpdateEstoque.setString(1, nomeProduto);
			UpdateEstoque.setString(2, secao);
			UpdateEstoque.setFloat(3, porcentagemGanho);
			UpdateEstoque.setString(4, codigoBarras);
			UpdateEstoque.setString(5, unidadeMedida);

			UpdateEstoque.execute();

		} catch (SQLException ex) {
			// JOptionPane.showMessageDialog(null, "Erro ao atualizar!" + ex);
		} finally {
			conexao.close();
		}
	}
//______________________________________________________________________________________________________________

	public void calculoCaixa() {

		Produtos produto = new Produtos();
		float PrecoUnitario = produto.getPrecoUnitario();

		valorTotal = quantidadeProd * PrecoUnitario;

	}
//______________________________________________________________________________________________________________

	public void descontoEstoque() {
		
		//Produtos produto = new Produtos();
		//int quantidadeTotal = produto.getQuantidadeTotal();

		//quantidadeTotal = quantidadeTotal - quantidadeProd;

	}
//______________________________________________________________________________________________________________

	public void valorTotalVendas() {
 
		valorTotalVendas =  valorTotalVendas + valorTotal;
		
	}
//______________________________________________________________________________________________________________


// --------------------------------------------------------------------------------------------------------------------------------//
	
	public void Caixazin() {
		
	    valorFinal = quantidadeProd*precoUnitario;

		String sql = "INSERT INTO vendas (codigoBarras, nomeProduto, precoUnitario, quantidadeProd, valorFinal) VALUES (?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement caixa = null;

		try {

			conn = Conexao.createConnectionToMySQL();

			caixa = (PreparedStatement) conn.prepareStatement(sql);

			caixa.setString(1, codigoBarras);
			caixa.setString(2, nomeProduto);
			caixa.setFloat(3, precoUnitario);
			caixa.setFloat(4, quantidadeProd);
			caixa.setFloat(5, valorFinal);

			caixa.execute();
			FacesContext.getCurrentInstance().addMessage(" ", new FacesMessage("Sucesso!", "Cadastro realizado!"));
			caixa.close();
			conn.close();

		} catch (Exception e) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário já está cadastrado!", " "));

			e.printStackTrace();
			
		}
	}
	
	
//---------------------------------------------------------------------------------------------------------------------------------------------//
	
	
	public List<Caixa> listCaixa() throws Exception {

		ArrayList<Caixa> lista = new ArrayList<Caixa>();
		Connection con = Conexao.createConnectionToMySQL();
		Statement st = null;
		ResultSet rs = null;

		try {

			st = con.createStatement();
			String sql = "select * from vendas ORDER BY codVendas";
			rs = st.executeQuery(sql);

			while (rs.next()) {

				Caixa caixa = new Caixa();

				caixa.setNomeProduto(rs.getString(5));
				caixa.setPrecoUnitario(rs.getFloat(6));
				caixa.setQuantidadeProd(rs.getFloat(3));
				caixa.setValorFinal(rs.getFloat(4));
				lista.add(caixa);

			}
			rs.close();
			st.close();
			con.close();
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Conexao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		}
		return lista;
	}

	public List<Caixa> getCaixa() throws Exception {

		Caixa caixa = new Caixa();
		List<Caixa> listCaixa = caixa.listCaixa();

		return listCaixa;
	}	
	
	
	
	
}
