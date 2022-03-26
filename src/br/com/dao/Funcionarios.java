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

//import javax.swing.JOptionPane;
import br.com.conexao.Conexao;

@ManagedBean(name = "Funcionarios")
public class Funcionarios {

	private String nomeFuncionario;
	private String cargo;
	private String login;
	private String senha;
	private String idFuncionario;

	public String getNomeFuncionario() {
		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(String idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

//_____________________________________________________________________________________________________________	

	/*public Funcionarios FuncionarioExiste() throws Exception {

		ResultSet ResultSet = null;
		boolean check = false;

		try {
			Connection con = Conexao.createConnectionToMySQL();

			String sql = "select login, senha from funcionarios where login = ? and senha = ?";
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql);

			pstm.setString(1, login);
			pstm.setString(2, senha);

			ResultSet = pstm.executeQuery(sql);

			if (ResultSet.next()) {

				check = true;

				ResultSet.close();
				con.close();
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Conexao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		}

		//return check;

	}*/

//_____________________________________________________________________________________________________________	

	public void Insert() {
		String sql = "INSERT INTO funcionarios (idFuncionario, nomeFuncionario, cargo, login, senha) VALUES ( ?, ?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement CadastroFuncionairos = null;

		try {
			conn = Conexao.createConnectionToMySQL();

			CadastroFuncionairos = (PreparedStatement) conn.prepareStatement(sql);

			CadastroFuncionairos.setString(1, idFuncionario);
			CadastroFuncionairos.setString(2, nomeFuncionario);
			CadastroFuncionairos.setString(3, cargo);
			CadastroFuncionairos.setString(4, login);
			CadastroFuncionairos.setString(5, senha);

			CadastroFuncionairos.execute();

			CadastroFuncionairos.close();
			conn.close();
			FacesContext.getCurrentInstance().addMessage(" ", new FacesMessage("Sucesso!", "Cadastro realizado!"));

		} catch (Exception e) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar!", " "));

			e.printStackTrace();
		}
	}
//______________________________________________________________________________________________________________	

	public List<Funcionarios> listFuncionarios() throws Exception {

		ArrayList<Funcionarios> lista = new ArrayList<Funcionarios>();
		Connection con = Conexao.createConnectionToMySQL();
		Statement st = null;
		ResultSet rs = null;

		try {
			st = con.createStatement();
			String sql = "select * from funcionarios";
			rs = st.executeQuery(sql);

			while (rs.next()) {
				Funcionarios funcionarios = new Funcionarios();
				funcionarios.setIdFuncionario(rs.getString(1));
				funcionarios.setNomeFuncionario(rs.getString(2));
				funcionarios.setCargo(rs.getString(3));
				funcionarios.setLogin(rs.getString(4));
				funcionarios.setSenha(rs.getString(5));
				lista.add(funcionarios);

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

	public List<Funcionarios> getFuncionario() throws Exception {

		Funcionarios lista = new Funcionarios();
		List<Funcionarios> listFuncionarios = lista.listFuncionarios();

		return listFuncionarios;
	}

//_______________________________________________________________________________________________________________	
	public void Pesquisar() throws SQLException {

		try {

			Connection conexao = Conexao.createConnectionToMySQL();
			String sql = "Select * from funcionarios where idFuncionario = '" + idFuncionario + "';";

			PreparedStatement funcionario = conexao.prepareStatement(sql);
			funcionario.executeQuery();
			ResultSet rs = funcionario.executeQuery(sql);
			while (rs.next()) {

				idFuncionario = rs.getString("idFuncionario");
				nomeFuncionario = rs.getString("nomeFuncionario");
				cargo = rs.getString("cargo");
				login = rs.getString("login");
				senha = rs.getString("senha");
			}
			conexao.close();

		} catch (Exception ex) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Funcionário não encontrado!", " "));

			ex.printStackTrace();
		}
	}
//_______________________________________________________________________________________________________________	

	public void Update() {

		String sql = "UPDATE funcionarios SET  nomeFuncionario = ?, cargo = ?, login = ?, senha = ? where idFuncionario = ?";

		try {

			Connection conn = Conexao.createConnectionToMySQL();

			PreparedStatement cadastroCliente = conn.prepareStatement(sql);

			cadastroCliente.setString(1, nomeFuncionario);
			cadastroCliente.setString(2, cargo);
			cadastroCliente.setString(3, login);
			cadastroCliente.setString(4, senha);
			cadastroCliente.setString(5, idFuncionario);
			cadastroCliente.execute();

			cadastroCliente.close();
			conn.close();

			FacesContext.getCurrentInstance().addMessage(" ", new FacesMessage("Cadastro atualizado!", " "));
		} catch (Exception ex) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível cadastrar!", " "));
			ex.printStackTrace();
		}
	}
//______________________________________________________________________________________________________________	

	public void Delete() throws Exception {

		PreparedStatement FuncionariosDELETE = null;
		Connection conn = Conexao.createConnectionToMySQL();

		try {

			String sql = "DELETE FROM funcionarios WHERE idFuncionario = ?";

			FuncionariosDELETE = (PreparedStatement) conn.prepareStatement(sql);

			FuncionariosDELETE.setString(1, idFuncionario);

			FuncionariosDELETE.execute();

			conn.close();
			FacesContext.getCurrentInstance().addMessage(" ", new FacesMessage("Sucesso!", "Cadastro retirado!"));
		} catch (SQLException ex) {
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível deletar!", " "));
		}
	}
//______________________________________________________________________________________________________________	

}
