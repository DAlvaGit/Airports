/*Airline class contains and manages an airline's flights.  What it needs it know from an Airport's perspective are the gates assigned to it.  
The object also needs to know what it's airline code is
2/22/14
The initial iteration assumes every flight has a unique flight number.  The Flight starts without the time members. Time members with member functions to be added
3/1/14
The Flights Hashtable is replaced by the FlightManager to manage the Flights
*/

import java.text.SimpleDateFormat;
import java.util.*; 


public class Airline {
	private String m_airLineCode;
	
	//Flight container class can be switched
	private FlightManager m_flightMgr;
	private Vector<String> m_localGates;
	private FlightDBAccess m_dbFlights;

	public Airline(String ac){
		m_airLineCode = ac;
		m_localGates = new Vector();
		m_flightMgr = new FlightManager();		
	} 
	public Airline(){
		m_flightMgr = new FlightManager();
		m_localGates = new Vector();
	}
	
	public void SetAirLineCode(String ac){
		m_airLineCode = ac;
	}
	
	public String GetAirLineCode()	{
		return m_airLineCode;
	}
	
	// could have multiple gates at different terminals
	public void AddGate(String GateCd){
		m_localGates.add(GateCd);
	}
	
	public void PrintGates(){
		Enumeration e = m_localGates.elements();
		int i = 0;
		while(e.hasMoreElements())
		{
			String gate = (String)e.nextElement();
			i++;
			System.out.println(m_airLineCode + " Gate " + i + " is " + gate);
		}
	}

	
	public void CreateFlights(Calendar today){
		// read database to create flights
		
		//need to pass in Calendar to get a today's flights
		//set flight airline code in flightmanager
//		m_dbFlights = new FlightDBAccess();
//		m_dbFlights.SetFlightMgr(m_flightMgr);
//		m_dbFlights.readDataBase(m_airLineCode);
	}
	
	// This function will set the initial gates for departing flights
	public void SetInitialGates(){
		
	}

	public Flight getFlight(String fltNo)
	{
		Flight srcFlt = m_flightMgr.getFlight(fltNo);
		return srcFlt;
	}
	
	// for testing purposes, add temp flights to FLightManager	
	public void addFlight(String flNO, String dest, String orig, int hour, int min)
	{
		Flight newFL = new Flight();
		newFL.flightNO = flNO;
		newFL.destination = dest;
		newFL.origin = orig;
		newFL.status = FlightStatus.SCHEDULED;
		newFL.departureTime = hour  + ":" + min;
		
		m_flightMgr.addFlight(newFL, flNO);
	}
	
	public String GetDepartureTime(String flno)
	{
		Flight srcFl = m_flightMgr.getFlight(flno);
		
		return srcFl.departureTime;
	}

	public String GetArrivalTime(String flno)
	{
		Flight srcFl = m_flightMgr.getFlight(flno);
		
		return srcFl.arrivalTime;
	}

	
	public void SetDepartureTime(String flNO, String arr){
		
		Flight temp = m_flightMgr.getFlight(flNO);
		
		if(temp != null)
		{
			temp.departureTime = arr;
		}		
	}
	
	public void SetArrivalTime(String flNO, String arr){
		
		Flight temp = m_flightMgr.getFlight(flNO);
		
		if(temp != null)
		{
			temp.arrivalTime = arr;
		}		
	}
	
	public void SetDepartureGate(String flNO, String depGate){
		
		Flight temp = m_flightMgr.getFlight(flNO);
		
		if(temp != null)
		{
			temp.departGate = depGate;
		}		
	}
	
	public void SetArrivalGate(String flNO, String arrGate){

		Flight temp = m_flightMgr.getFlight(flNO);
		
		if(temp != null)
		{
			temp.arrivalGate = arrGate;
		}
	}	
		
	
	// This function gets a list of flight numbers that matches the Origin and Destination
	public void GetDestinationFlights(String orig, String dest, Vector<String> flNOs){

		m_flightMgr.getFlights(orig, dest, flNOs);
	}
	
	// This function gets all the flight numbers arriving into one airport, dest = airport code
	public void GetAllFlightsToDestination(String dest, Vector<String> flNOs){

		m_flightMgr.getArrivingFlights(dest, flNOs);		
	}
	
	//This function returns the Origin and Destination of a given flight number
	//Managed by fight manager
	public boolean GetFlightLocations(String flNO, String orig, String dest)
	{
		Flight temp = m_flightMgr.getFlight(flNO);
		
		if(temp != null)
		{
			orig = temp.origin;
			dest = temp.destination;
			return true;
		}
		return false;
	}

	
	//This functions returns all of the flights arriving in the range of startTime and finalTime  12:00 - 13:00
	//departing tests on Flight departure time (true) else test on arrival time.
	//airCode gives the airport code of flights  
	public void GetFlights(String startTime, String finalTime, Vector<String> flNOs, boolean departing, String airCode)
	{		
		m_flightMgr.getFlights(startTime, finalTime, flNOs, departing, airCode);
	}
	
}
