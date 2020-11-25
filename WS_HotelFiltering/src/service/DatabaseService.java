package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Lucas Vauterin & Valentin Eloy
 *
 */
public class DatabaseService {
	
	private static Connection co ;
	private static Statement stmt ;
	
	public static Statement getStatement() {
		if ( DatabaseService.co == null || DatabaseService.stmt == null){
			createConnectionToDB_HOTEL();
		}
		return DatabaseService.stmt;
	}
	
	private static void createConnectionToDB_HOTEL() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
        	String url = "jdbc:mysql://localhost:3306/DB_HOTEL?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			/*
			 * ################################# ATTENTION MOT DE PASSE USER DB_HOTEL #####################
			 */
        	DatabaseService.co = DriverManager.getConnection(url,"root","root");
			DatabaseService.stmt = DatabaseService.co.createStatement() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("CONNEXION BD_HOTEL KO");
			e.printStackTrace();
		}
        System.out.println("CONNEXION BD_HOTEL OK");
	}

	public static void closeConnection() {
		try {
			DatabaseService.co.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
