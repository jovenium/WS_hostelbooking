package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author Lucas Vauterin & Valentin Eloy
 *
 */
public class AuthentRepository {

	/**
	 * Requete vers la DB pour authentifier via nom et pwd de l'utilisateur
	 * @param t
	 * @param name
	 * @param pwd
	 * @return le resultat de la requete
	 */
	public static String findIdByNameAndPWD(Statement t, String name,
			String pwd) {
		try {
			ResultSet result = t.executeQuery("SELECT id from customer c WHERE c.name = '"+ name +"' AND c.pwd = '"+ pwd +"'" );
			if(result.next()){
				return String.valueOf( result.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		return "";
	}
}
