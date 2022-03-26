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

import br.com.conexao.Conexao;

@ManagedBean(name = "Produtos")
public class Produtos {

	private String codigoBarras;
	private String nomeProduto;
	private String unidadeMedida;
	private String secao;
	private float precoUnitario;
	private String lote;
	private String validade;
	private int quantidadeLote;
	private int quantidadeTotal;
	private float valorLote;
	
	
	public float getPrecoUnitario() {
		return precoUnitario;
	}
	public void setPrecoUnitario(float precoUnitario) {
		this.precoUnitario = precoUnitario;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getValidade() {
		return validade;
	}
	public void setValidade(String validade) {
		this.validade = validade;
	}
	public int getQuantidadeLote() {
		return quantidadeLote;
	}
	public void setQuantidadeLote(int quantidadeLote) {
		this.quantidadeLote = quantidadeLote;
	}
	public int getQuantidadeTotal() {
		return quantidadeTotal;
	}
	public void setQuantidadeTotal(int quantidadeTotal) {
		this.quantidadeTotal = quantidadeTotal;
	}
	public float getValorLote() {
		return valorLote;
	}
	public void setValorLote(float valorLote) {
		this.valorLote = valorLote;
	}
	public String getCodigoBarras() {
		return codigoBarras;
	}
	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
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


//______________________________________________________________________________________________________________

	public void InsertEstoque() {

		String sql = "UPDATE produtos SET lote = ?, validade = ?, quantidadeLote = ?, quantidadeTotal = ?, valorLote = ? where codigoBarras = ?";

		Connection conn = null;
		PreparedStatement cadastroEstoque = null;

		try {

			conn = Conexao.createConnectionToMySQL();

			cadastroEstoque = (PreparedStatement) conn.prepareStatement(sql);

			cadastroEstoque.setString(1, lote);
			cadastroEstoque.setString(2, validade);
			cadastroEstoque.setInt(3, quantidadeLote);
			cadastroEstoque.setInt(4, quantidadeTotal);
			cadastroEstoque.setFloat(5, valorLote);
			cadastroEstoque.setString(6, codigoBarras);

			cadastroEstoque.execute();
			System.out.print("Entrou!");
			FacesContext.getCurrentInstance().addMessage(" ", new FacesMessage("Estoque alterado!", " "));
		} catch (Exception e) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível inserir estoque!", " "));

			e.printStackTrace();
		} finally {
			try {
				if (cadastroEstoque != null) {
					cadastroEstoque.close();
				}
				if (cadastroEstoque != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
//______________________________________________________________________________________________________________

	public void Insert() {

		String sql = "INSERT INTO produtos (nomeProduto, secao, codigoBarras, unidadeMedida) VALUES (?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement cadastroProdutos = null;

		try {

			conn = Conexao.createConnectionToMySQL();

			cadastroProdutos = (PreparedStatement) conn.prepareStatement(sql);

			cadastroProdutos.setString(1, nomeProduto);
			cadastroProdutos.setString(2, secao);
			cadastroProdutos.setString(3, codigoBarras);
			cadastroProdutos.setString(4, unidadeMedida);

			cadastroProdutos.execute();
			System.out.print("Entrou!");
			FacesContext.getCurrentInstance().addMessage(" ", new FacesMessage("Sucesso!", "Cadastro realizado!"));
		} catch (Exception e) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Produto já cadastrado!", " "));

			e.printStackTrace();
		} finally {
			try {
				if (cadastroProdutos != null) {
					cadastroProdutos.close();
				}
				if (cadastroProdutos != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

//______________________________________________________________________________________________________________
	public List<Produtos> listProdutos() throws Exception {

		ArrayList<Produtos> lista = new ArrayList<Produtos>();
		Connection con = Conexao.createConnectionToMySQL();

		try {

			Statement st = con.createStatement();
			String sql = "select * from produtos ORDER BY nomeProduto";
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {

				Produtos produtos = new Produtos();

				produtos.setNomeProduto(rs.getString(1));
				produtos.setSecao(rs.getString(2));		
				produtos.setCodigoBarras(rs.getString(4));
				produtos.setUnidadeMedida(rs.getString(5));
				produtos.setQuantidadeTotal(rs.getInt(9));
				produtos.setPrecoUnitario(rs.getFloat(11));
				
				lista.add(produtos);
			
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
	
	public List<Produtos> getProdutos() throws Exception {

		Produtos clientes = new Produtos();
		List<Produtos> listaProdutos = clientes.listProdutos();

		return listaProdutos;
	}

//______________________________________________________________________________________________________________
	public void Pesquisar() throws SQLException {

		try {
			Connection conexao = Conexao.createConnectionToMySQL();
			String sql = "Select * from produtos where codigoBarras = '"+codigoBarras+"';";

			PreparedStatement estoqueSELECT = conexao.prepareStatement(sql);
			estoqueSELECT.executeQuery();
			ResultSet rs = estoqueSELECT.executeQuery(sql);
			while (rs.next()) {

				nomeProduto = rs.getString("nomeProduto");
				unidadeMedida = rs.getString("unidadeMedida");
				secao = rs.getString("secao");
				codigoBarras = rs.getString("codigoBarras");
				precoUnitario = rs.getFloat("precoUnitario");
				
			}
			conexao.close();

		} catch (Exception ex) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Produto não encontrado!", " "));
			ex.printStackTrace();
		}
	}

//______________________________________________________________________________________________________________
	public void Update() throws Exception {

		Connection conexao = null;
		PreparedStatement UpdateEstoque = null;
		conexao = Conexao.createConnectionToMySQL();

		try {

			String sql = "UPDATE produtos SET nomeProduto = ?, secao = ?, codigoBarras = ?, unidadeMedida = ? where codigoBarras = ?";
			
			UpdateEstoque = (PreparedStatement) conexao.prepareStatement(sql);

			UpdateEstoque.setString(1, nomeProduto);
			UpdateEstoque.setString(2, secao);
			UpdateEstoque.setString(3, codigoBarras);
			UpdateEstoque.setString(4, unidadeMedida);
			UpdateEstoque.setString(5, codigoBarras);

			UpdateEstoque.execute();
			FacesContext.getCurrentInstance().addMessage(" ", new FacesMessage("Atualização realizada!", " "));

		} catch (SQLException ex) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível atualizar!", " "));
		} finally {
			conexao.close();

		}
	}
//______________________________________________________________________________________________________________

	public void Delete() throws Exception {

		PreparedStatement ProdutosDELETE = null;
		Connection conn = Conexao.createConnectionToMySQL();

		try {

			String sql = "DELETE FROM produtos WHERE codigoBarras = ?";

			ProdutosDELETE = (PreparedStatement) conn.prepareStatement(sql);

			ProdutosDELETE.setString(1, codigoBarras);

			ProdutosDELETE.execute();

			FacesContext.getCurrentInstance().addMessage(" ", new FacesMessage("Cadastro retirado!", " "));

		} catch (SQLException ex) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível deletar!", " "));

		} finally {
			conn.close();
		}
	}

//______________________________________________________________________________________________________________

}
