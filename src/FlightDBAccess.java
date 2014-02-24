/*  The the FlightDBAccess class gives access to an SQL Server database that contains the Flight information
 * 2/22/14
 * The initial class just has connections to the database.  Container sets will be added to store the information the class.
 */
import java.sql.*;

public class FlightDBAccess {

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	
	public void readDataBase (String airineCd){
		try{
		
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:microsoft:sqlserver://localhost:1433;databaseName=AirlineFlights";
			String userName = "da";
			String password = "password";
			connect = DriverManager.getConnection(url, userName, password); 
			
			statement = connect.createStatement();
			
			resultSet = statement.executeQuery("SELECT * FROM Flights");
			
			// store resultSet in container classes for Airline class to build flights
			
		}catch(ClassNotFoundException e){
			e.printStackTrace();
			System.exit(1);
		}		
		catch (SQLException e)
		{
			e.printStackTrace();
			System.exit(2);

		} finally{
			close();
		}
	}

public void close(){
	try{
		if (resultSet != null)
		{
			resultSet.close();
		}
		
		if(statement != null)
		{
			statement.close();
		}
		if (connect != null)
		{
			connect.close();
		}
	}catch(Exception e){
	}
	}
}