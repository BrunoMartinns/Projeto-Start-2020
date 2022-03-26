package br.com.dao;

//import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import com.mysql.jdbc.PreparedStatement;
import br.com.conexao.Conexao;

@ManagedBean
public class Login {

	private String user;
	private String password;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean checklogin() throws Exception {

		ResultSet ResultSet = null;
		boolean check = false;

		try {
			Connection con = Conexao.createConnectionToMySQL();

			String sql = "select login, senha from funcionarios where login = '" + user + "' and senha = '" + password
					+ "'";
			PreparedStatement pstm = (PreparedStatement) con.prepareStatement(sql);
			ResultSet = pstm.executeQuery(sql);

			System.out.println();
			if (ResultSet.next()) {

				Login login = new Login();

				login.setUser(ResultSet.getString(1));

				check = true;

				ResultSet.close();
				con.close();

			} else {

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login ou Senha incorreto!", " "));
				check = false;
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Conexao.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		}

		return check;
	}

//________________________________________________________________________________________________________________

	public String Acessar() throws Exception {

		String link = null;
		if (checklogin() == false) {
			return null;

		} else {

			if (this.user.equals("admin")) {

				link = "/pages/index?faces-redirect=true";
			} else {
				link = "/pages/caixa?faces-redirect=true";

			}

			return link;
		}

	}
}
