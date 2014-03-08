/*  The the FlightDBAccess class gives access to an SQL Server database that contains the Flight information
 * 2/22/14
 * The initial class just has connections to the database.  Container sets will be added to store the information the class.
 * 3/1/14
 * FlightMgr Added to add flights from database
 */
import java.sql.*;
import java.util.Calendar;

public class FlightDBAccess {

	private Connection connect = null;
	private Statement statement = null;
	private ResultSet resultSet = null;
	private FlightManager m_fltMgr = null;
	
	public void SetFlightMgr(FlightManager fm){m_fltMgr = fm;}
	
	public void readDataBase (String airineCd){
		Calendar today;
		today = Calendar.getInstance();
		
		try{
		
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "jdbc:microsoft:sqlserver://localhost:1433;databaseName=AirlineFlights";
			String userName = "da";
			String password = "password";
			connect = DriverManager.getConnection(url, userName, password); 
			
			statement = connect.createStatement();
			
			resultSet = statement.executeQuery("SELECT * FROM Flights");
			
			Flight newFlight = null;
			
			if(m_fltMgr != null)
			{
				while(resultSet.next()){
				
					newFlight = new Flight();
					
					//set Flight fields;
					// need to test on Date

					newFlight.flightNO = resultSet.getString("FLT_NO");  
					newFlight.destination = resultSet.getString("DEST");
					newFlight.origin = resultSet.getString("ORIG");
					
					Calendar cal = Calendar.getInstance();					
					java.sql.Date tmpDate= resultSet.getDate("DEP_DATE");
					cal.setTime(tmpDate);
					
					int hour = cal.get(Calendar.HOUR);
					int minute = cal.get(Calendar.MINUTE);
					newFlight.departureTime = hour + ":" + minute;
					
					tmpDate = resultSet.getDate("ARR_DATE");
					cal.setTime(tmpDate);
					
					hour = cal.get(Calendar.HOUR);
					minute = cal.get(Calendar.MINUTE);
					newFlight.arrivalTime = hour + ":" + minute;
					
					
					if(newFlight != null)
					{
						m_fltMgr.addFlight(newFlight, newFlight.flightNO);
					}
				
				}
			}
			
			
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