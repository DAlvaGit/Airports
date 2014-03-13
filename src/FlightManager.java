/*The Flight Manager Class contains and manages flights.
 * 3/01/14 Class created to manage flights 
 * 3/13/14 Maps were added to manage the arrival and departure times of a flight and sort them according to time
 * */

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.*;
import java.util.TreeMap;

// needs to become a thread safe class
public class FlightManager {

	private Hashtable<String, Flight> m_flights;  //Assumes every flight number has a unique flight
	private Map<String, List<String>> m_sortArrFlights;
	private Map<String, List<String>> m_sortDepFlights;
	
	
	public FlightManager(){
		m_flights = new Hashtable<String, Flight>();
		
		m_sortArrFlights = new TreeMap<String, List<String>>();
		m_sortDepFlights = new TreeMap<String, List<String>>();
	}
	
	
	//This method will add a flight to the manager and will keep track of flights departing and arriving at a certain hour
	public void addFlight(Flight flt, String fltNo){

		flt.status = FlightStatus.SCHEDULED;
		m_flights.put(fltNo, flt);
	
		String theHour  = flt.arrivalTime.substring(0, 2);
		
		List<String> certainHour = m_sortArrFlights.get(theHour);
				
		if(certainHour == null)
		{
			List<String> newHour = new LinkedList<String>();
			newHour.add(fltNo);
			m_sortArrFlights.put(theHour, newHour);
		}
		else
		{			
			certainHour.add(fltNo);
		}
		
		
		theHour = flt.departureTime.substring(0, 2);		
		certainHour = m_sortDepFlights.get(theHour);
		if(certainHour == null)
		{
			List<String> newHour = new LinkedList<String>();
			newHour.add(fltNo);
			m_sortDepFlights.put(theHour, newHour);
		}
		else
		{			
			certainHour.add(fltNo);
		}	
	}
	
	//This method fills a Flights list with flights flights arriving in the specified time.
	//The departing flag signifies if the user wants departing or arrival flignts
	public void GetSortedFlights(Vector<Flight> flights,String time, boolean departing)
	{
		List<String> avlFlights = null;
		String theHour = time.substring(0, 2);

		if(departing)
		{
			avlFlights = m_sortDepFlights.get(theHour);
		}
		else 
		{
			avlFlights = m_sortArrFlights.get(theHour);			
		}
		
		if(avlFlights != null)
		{
			for(String fl: avlFlights)
			{
				flights.add(m_flights.get(fl));
			}
		}
		
	}
	
	public Flight getFlight(String flNo)
	{
		Flight temp = m_flights.get(flNo);

		if(temp != null){
			return temp;
		}
		
		return null;
	}
	
	// Method retrieves all the flights from a specific airport "code"
	public void getDepartingFlights(String origin, Vector<String> flights){

		Enumeration<String> enumKey = m_flights.keys();
		Flight currFlight;
		while(enumKey.hasMoreElements()){
			String nextKey = enumKey.nextElement();
			currFlight = m_flights.get(nextKey);
			if(currFlight != null && currFlight.origin == origin){
				flights.add( currFlight.flightNO);  
			}
		}
		
	}

	// Method retrieves all the flights arriving into a specific airport "code"
	public void getArrivingFlights(String dest, Vector<String> flights){
		
		Enumeration<String> enumKey = m_flights.keys();
		Flight currFlight;
		while(enumKey.hasMoreElements()){
			String nextKey = enumKey.nextElement();
			currFlight = m_flights.get(nextKey);
			if(currFlight != null && currFlight.destination == dest){
				flights.add( currFlight.flightNO);  
			}
		}
	}

	//Method returns a list of flight numbers in which the flight matches the origin and destination
	public void getFlights(String origin, String dest, Vector<String> flights){
		Enumeration<String> enumKey = m_flights.keys();
		Flight currFlight;
		while(enumKey.hasMoreElements()){
			String nextKey = enumKey.nextElement();
			currFlight = m_flights.get(nextKey);
			if(currFlight != null && (currFlight.destination == dest && currFlight.origin == origin)){
				flights.add( currFlight.flightNO);  
			}
		}		
	}
	

	// Method returns a "list" of flight numbers in which the flight time is between the startTime and finalTime
	//departing tests on Flight departure time (true) else test on arrival time 
	//airCode specifies which airport the query is for
	public void getFlights(String startTime, String finalTime, Vector<String> fl, boolean departing, String airCode){
		Date first = convert(startTime);
		Date second = convert(finalTime);
		Date flightTime;

		Enumeration<String> enumKey = m_flights.keys();
		Flight currFlight;
		String airport;
		while(enumKey.hasMoreElements())
		{
			String nextKey = enumKey.nextElement();
			currFlight = m_flights.get(nextKey);
	
			if(currFlight != null)
			{
				if(departing)
				{
					flightTime = convert(currFlight.departureTime);
					airport = currFlight.origin;
				}
				else
				{					
					flightTime = convert(currFlight.arrivalTime);
					airport = currFlight.destination;
				}
				
				if((first.compareTo(flightTime) < 0) || (first.compareTo(flightTime) == 0) &&
						(second.after(flightTime) || second.compareTo(flightTime) == 0) &&
						(airport == airCode))
				{
					fl.add(currFlight.flightNO);
				}
			}	
		}
	}
	
	//converts a string to a time to compare dates
	private Date convert(String time){
		SimpleDateFormat inputParser = new SimpleDateFormat("HH:mm", Locale.US);		
	    try {
	        return inputParser.parse(time);
	    } catch (java.text.ParseException e) {
	        return new Date(0);
	    }
	}	
	
}
