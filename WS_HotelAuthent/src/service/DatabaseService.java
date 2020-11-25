package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Gestion de la connexion a la base de données, ATTENTION au pwd de la base de donnees
 * @author Lucas Vauterin & Valentin Eloy
 *
 */
public class DatabaseService {
	
	private static Connection co ;
	private static Statement stmt ;
	
	/**
	 * Si il n'y a pas de statement, on essaye de le creer
	 * @return
	 */
	public static Statement getStatement() {
		if ( DatabaseService.co == null || DatabaseService.stmt == null){
			createConnectionToDB_HOTEL();
		}
		return DatabaseService.stmt;
	}
	
	/**
	 * Creation de la connexion avec la DB_HOTEL
	 * Attention au mot de passe
	 */
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
        	 * ################### MOT DE PASSE UTILISATEUR MYSQL #####################
        	 */
        	DatabaseService.co = DriverManager.getConnection(url,"root","root");
			DatabaseService.stmt = DatabaseService.co.createStatement() ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("CONNEXION BD_HOTEL from Authent KO");
			e.printStackTrace();
		}
        System.out.println("CONNEXION BD_HOTEL from Authent OK");
	}

	/**
	 * fermeture de la connexion à la DB
	 */
	public static void closeConnection() {
		try {
			DatabaseService.co.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
