package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class conexionDB {
	static String Nombre_Server="localhost";
	static String Puerto=":3306";
	static String driver= "com.mysql.jdbc.Driver";
	static String server= "jdbc:mysql://"+Nombre_Server+Puerto+"/";
	static String bd= "db_sgtlab";
	static String user= "sgtroot";
	static String password= "sgtutm001";
	
	static Connection connection = null;
	
	public conexionDB() {
		  
	}
	
	public static Connection getConnection(){
		try {
			if(connection==null){
				Class.forName(driver);
				connection =  DriverManager.getConnection(server+bd,user,password);
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
				System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public static void desconectar(){
		try {
			connection.close();
			if(connection.isClosed()){
				//	System.out.println("Conexion cerrada correctamente");
				connection=null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
