/*Airline class contains and manages an airline's flights.  What it needs it know from an Airport's perspective are the gates assigned to it.  
The object also needs to know what it's airline code is
2/22/14
The initial iteration assumes every flight has a unique flight number.  The Flight starts without the time members. Time members with member functions to be added

3/1/14
The Flights Hashtable is replaced by the FlightManager to manage the Flights

3/12/14
Added A Check Flight Status method that checks the flight status of flight arriving and departing from an Airport and writes results to corresponding files
*/

import java.text.SimpleDateFormat;
import java.util.*; 
import java.io.*;

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
		
		if(min < 10)
		{
			newFL.departureTime = hour  + ":0" + min;			
		}
		else
		{
			newFL.departureTime = hour  + ":" + min;
		}
		
		newFL.arrivalTime = (hour + 3) + ":" + min;
		
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
		
	
	// This method gets a list of flight numbers that matches the Origin and Destination
	public void GetDestinationFlights(String orig, String dest, Vector<String> flNOs){

		m_flightMgr.getFlights(orig, dest, flNOs);
	}
	
	// This method gets all the flight numbers arriving into one airport, dest = airport code
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

	
	//This method returns all of the flights arriving in the range of startTime and finalTime  12:00 - 13:00
	//departing tests on Flight departure time (true) else test on arrival time.
	//airCode gives the airport code of flights  
	public void GetFlights(String startTime, String finalTime, Vector<String> flNOs, boolean departing, String airCode)
	{		
		m_flightMgr.getFlights(startTime, finalTime, flNOs, departing, airCode);
	}
	
	// This method takes in a starting hour and checks both the status of flights scheduled to arrive and depart at that time
	// The check must also include flights to and from a specific airport
	// The result is written out to a file to a Departure.txt and Arrival.txt files
	public void CheckFlightStatus(String startingHour, String localAirport)
	{		
		Vector<Flight> flights = new Vector<Flight>();		
		
		try{
			m_flightMgr.GetSortedFlights(flights, startingHour, true);	
			
			File fdStatus = new File("Departures.txt");
			BufferedWriter bw = new BufferedWriter(new FileWriter(fdStatus));

			String line = null;
			for(Flight fl: flights)
			{
				if(localAirport == fl.origin)
				{
					line = m_airLineCode + " Flight " + fl.flightNO + " to " + fl.destination + " Departing at " + fl.departureTime + " is "+ fl.status.toString();
					bw.write(line);
					bw.newLine();
		
				}
			}
			bw.flush();
			bw.close();

			flights.clear();
			File faStatus = new File("Arrivals.txt");
			bw = new BufferedWriter(new FileWriter(faStatus));
			
			m_flightMgr.GetSortedFlights(flights, startingHour, false);			
			for(Flight fl: flights)
			{
				if(localAirport == fl.destination)
				{
					line = m_airLineCode + " Flight " + fl.flightNO + " from " + fl.origin + " Arrives at " + fl.arrivalTime + " is "+ fl.status.toString();
					bw.write(line);
					bw.newLine();
				}
			}
			bw.flush();
			bw.close();
			
		}
		catch(IOException e)
		{
			
		}
	}

}
