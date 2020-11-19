package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class AuthentRepository {

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
