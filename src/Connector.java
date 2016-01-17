import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class Connector {

	private static Connection conn = null;

	public static Connection Connector() {
		String data = "jdbc:mysql://localhost/world";
		String user = "root";
		String pass = "toot";
		try {
			conn = DriverManager.getConnection(data, user, pass);

		} catch (Exception e) {

			JOptionPane.showMessageDialog(null, e.getMessage());

		}
		if (conn != null) {

			System.out.println("Connection Suceess");
			return conn;

		} else {

			return conn;

		}

	}

}
