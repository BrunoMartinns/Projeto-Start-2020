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

@ManagedBean(name = "Cadastrar")
public class Clientes {

	private String nomeCliente;
	private String dataNascimento;
	private String cpf;
	private String endereco;
	private String telefone;

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

//______________________________________________________________________________________________________________

	public void Insert() {

		String sql = "INSERT INTO clientes (nomeCliente, dataNascimento, cpf, endereco, telefone) VALUES (?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement cadastroCliente = null;

		try {

			conn = Conexao.createConnectionToMySQL();

			cadastroCliente = (PreparedStatement) conn.prepareStatement(sql);

			cadastroCliente.setString(1, nomeCliente);
			cadastroCliente.setString(2, dataNascimento);
			cadastroCliente.setString(3, cpf);
			cadastroCliente.setString(4, endereco);
			cadastroCliente.setString(5, telefone);

			cadastroCliente.execute();
			FacesContext.getCurrentInstance().addMessage(" ", new FacesMessage("Sucesso!", "Cadastro realizado!"));

		} catch (Exception e) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuário já está cadastrado!", " "));

			e.printStackTrace();
		} finally {
			try {
				if (cadastroCliente != null) {
					cadastroCliente.close();
				}
				if (cadastroCliente != null) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
//______________________________________________________________________________________________________

	public List<Clientes> listUsuarios() throws Exception {

		ArrayList<Clientes> lista = new ArrayList<Clientes>();
		Connection con = Conexao.createConnectionToMySQL();
		Statement st = null;
		ResultSet rs = null;

		try {

			st = con.createStatement();
			String sql = "select * from clientes ORDER BY nomeCliente";
			rs = st.executeQuery(sql);

			while (rs.next()) {

				Clientes usuario = new Clientes();

				usuario.setNomeCliente(rs.getString(1));
				usuario.setDataNascimento(rs.getString(2));
				usuario.setCpf(rs.getString(3));
				usuario.setEndereco(rs.getString(4));
				usuario.setTelefone(rs.getString(5));
				lista.add(usuario);

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

	public List<Clientes> getUsuarios() throws Exception {

		Clientes clientes = new Clientes();
		List<Clientes> listaUsuarios = clientes.listUsuarios();

		return listaUsuarios;
	}

//_____________________________________________________________________________________________________________	

	public void Pesquisar() throws SQLException {

		try {
			Connection conexao = Conexao.createConnectionToMySQL();
			String sql = "Select * from clientes where cpf = '" + cpf + "';";

			PreparedStatement estoqueSELECT = conexao.prepareStatement(sql);
			estoqueSELECT.executeQuery();
			ResultSet rs = estoqueSELECT.executeQuery(sql);
			while (rs.next()) {

				nomeCliente = rs.getString("nomeCliente");
				dataNascimento = rs.getString("dataNascimento");
				cpf = rs.getString("cpf");
				endereco = rs.getString("endereco");
				telefone = rs.getString("telefone");
			}
			conexao.close();

		} catch (Exception ex) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cliente não encontrado!", " "));

			ex.printStackTrace();
		}
	}

//________________________________________________________________________________________________________________		
	public void Update() {

		String sql = "UPDATE clientes SET nomeCliente = ?, dataNascimento = ?, cpf = ?, endereco = ?, telefone = ? where cpf = ?";
		Connection conn = null;
		PreparedStatement cadastroCliente = null;

		try {

			conn = Conexao.createConnectionToMySQL();

			cadastroCliente = (PreparedStatement) conn.prepareStatement(sql);

			cadastroCliente.setString(1, nomeCliente);
			cadastroCliente.setString(2, dataNascimento);
			cadastroCliente.setString(3, cpf);
			cadastroCliente.setString(4, endereco);
			cadastroCliente.setString(5, telefone);
			cadastroCliente.setString(6, cpf);
			cadastroCliente.execute();

			cadastroCliente.close();
			conn.close();
			FacesContext.getCurrentInstance().addMessage(" ", new FacesMessage("Sucesso!", "Atualização realizada!"));
		} catch (Exception ex) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível atualizar!", " "));

			ex.printStackTrace();
		}
	}
//_____________________________________________________________________________________________________________	

	public void Delete() throws Exception {

		PreparedStatement ClientesDELETE = null;
		Connection conn = Conexao.createConnectionToMySQL();

		try {

			String sql = "DELETE FROM clientes WHERE cpf = ?";

			ClientesDELETE = (PreparedStatement) conn.prepareStatement(sql);

			ClientesDELETE.setString(1, cpf);

			ClientesDELETE.execute();

			FacesContext.getCurrentInstance().addMessage(" ", new FacesMessage("Sucesso!", "Cadastro retirado!"));
		} catch (SQLException ex) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível deletar!", " "));

		} finally {
			conn.close();

		}
	}

//_____________________________________________________________________________________________________________	

}
