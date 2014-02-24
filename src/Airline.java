/*Airline class contains and manages an airline's flights.  What it needs it know from an Airport's perspective are the gates assigned to it.  
The object also needs to know what it's airline code is
2/22/14
The initial iteration assumes every flight has a unique flight number.  The Flight starts without the time members. Time members with member functions to be added
*/

import java.util.*; 

public class Airline {
	private String m_airLineCode;
	
	//Flight container class can be switched
	private Hashtable<String, Flight> m_Flights;  // assumes every flight has unique flight number
	private Vector<String> m_localGates;
//	private FlightDBAccess m_dbFlights;

	public Airline(String ac){
		m_airLineCode = ac;
		m_Flights = new Hashtable<String, Flight>();
		m_localGates = new Vector();
	} 
	public Airline(){
		m_Flights = new Hashtable<String, Flight>();
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

	
	public void CreateFlights(){
		// read database to create flights
//		m_dbFlights = new FlightDBAccess();
//		m_dbFlights.readDataBase(m_airLineCode);
	}
		
	public void addFlight(String flNO, String dest, String orig)
	{
		Flight temp = new Flight();
		temp.flightNO = flNO;
		temp.destination = dest;
		temp.origin = orig;
		
		m_Flights.put(flNO, temp);
	}
	
	public void SetDepartureGate(String flNO, String depGate){
		Flight temp = m_Flights.get(flNO);
		
		if(temp != null)
		{
			temp.departGate = depGate;
		}		
	}
	
	public void SetFlightArrivalGate(String flNO, String arrGate){
		Flight temp = m_Flights.get(flNO);
		
		if(temp != null)
		{
			temp.arrivalGate = arrGate;
		}
	}	
		
	
	// This function gets a list of flight numbers that matches the Origin and Destination
	public void GetDestinationFlights(String orig, String dest, Vector flNOs){
		Enumeration<String> enumKey = m_Flights.keys();
		Flight currFlight;
		while(enumKey.hasMoreElements()){
			String nextKey = enumKey.nextElement();
			currFlight = m_Flights.get(nextKey);
			if(currFlight != null && (currFlight.origin == orig && currFlight.destination == dest)){
				flNOs.add( m_airLineCode + currFlight.flightNO);
			}
		}
	}
	
	// This function gets all the flight numbers arriving into one airport, dest = airport code
	public void GetAllFlightsToDestination(String dest, Vector flNOs){
		Enumeration<String> enumKey = m_Flights.keys();
		Flight currFlight;
		while(enumKey.hasMoreElements()){
			String nextKey = enumKey.nextElement();
			currFlight = m_Flights.get(nextKey);
			if(currFlight != null && currFlight.destination == dest)
			{
				flNOs.add( m_airLineCode + currFlight.flightNO);
			}
		}
		
	}
	
	//This function returns the Origin and Destination of a given flight number
	public boolean GetFlightLocations(String flNO, String orig, String dest)
	{
		Flight temp = m_Flights.get(flNO);
		
		if(temp != null)
		{
			orig = temp.origin;
			dest = temp.destination;
			return true;
		}
		return false;
	}

	
	

	public class Flight{
		public String flightNO;
		public String destination;
		public String origin;
		//arrivalTime
		//departTime
		//delayedArrivalTime
		//delayedDepartureTime
		public String departGate;
		public String arrivalGate;
//		public boolean connectingFlt;
//		public int baggageClaim;
		
//      Potential other Class AirCraft
//		public string plane;
//		public int firstClassPass;
//		public int ecoCabinPass;
//		public int totalPassengers;
		
	}
}
