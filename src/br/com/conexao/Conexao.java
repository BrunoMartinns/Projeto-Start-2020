package br.com.conexao;

import java.sql.Connection;
import java.sql.DriverManager;


public class Conexao {

	private static final String USERNAME = "root";
	private static final String PASSWORD = "start";
	private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/tynesbd?useSSL=false";
	

	public static Connection createConnectionToMySQL() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		
		Connection conexao = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
		
		return conexao;
	}
	
	public static void main (String [] args) throws Exception {
		Connection con = createConnectionToMySQL();
		
		if(con!=null) {
			System.out.println("CONECTADO COM SUCESSO!");
			con.close();
		}
	}

	
}

	
	