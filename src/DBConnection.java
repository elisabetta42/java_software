import java.awt.Dimension;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
public class DBConnection {
	public static void inserisci_religioso(String query) throws SQLException{
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/Preti",
					"postgres", "clarinetto");
		} catch (SQLException e) {
			System.out.println("Connection Failed!");
			e.printStackTrace();
			return;
		}

		Statement sql = connection.createStatement();
		if (connection != null) {
			System.out.println(query);
			sql.executeQuery(query);
		} else {
			System.out.println("Problem with the connection!");
		}
	}
	public static ResultSet prendi_religioso(String query) throws SQLException {
		Connection connection = null;		
		try {
			connection = DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/Preti",
					"postgres", "clarinetto");
		} catch (SQLException e) {
			System.out.println("Connection Failed!");
			e.printStackTrace();
			return null;
		}

		Statement stmt = null;
		ResultSet rs;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);

		} finally {

		}
		return rs;

	}
	public static void initialize(){
		Main_Panel s= new  Main_Panel();
		s.setSize(new Dimension(600, 500));
		s.setVisible(true);
		ImageIcon image = new ImageIcon("C:/Users/User03/Dropbox/preti/maschera.png");
		s.setIconImage(image.getImage());
	}
	public static ResultSet cerca_qualifica(String qualifica){
		ResultSet rs=null;
		try {
			rs=DBConnection.prendi_religioso("SELECT qualifica"
					+ " FROM existing_qualifiche WHERE qualifica LIKE"+"'"+qualifica+"%"+"'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}



	public static void main(String[] args) throws ClassNotFoundException,
	SQLException {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out
			.println("Include in your library path the JDBC jar file!");
			e.printStackTrace();
			return;
		}
		System.out.println("PostgreSQL JDBC Driver Registered!");
		initialize();


	}

}
