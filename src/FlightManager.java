import java.text.SimpleDateFormat;
import java.util.*;

/*The Flight Manager Class contains and manages flights.
 * 3/1/14 Class created to manage flights */


// needs to become a thread safe class
public class FlightManager {

	private Hashtable<String, Flight> m_flights;  //Assumes every flight number has a unique flight 
	
	
	public FlightManager(){
		m_flights = new Hashtable<String, Flight>();
	}
	public void addFlight(Flight flt, String fltNo){
		
		flt.status = FlightStatus.SCHEDULED;
		m_flights.put(fltNo, flt);
		
		//this function will add a flight to the manager regardless what type of container is used

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
	public void getDepartingFlights(String origin, Vector flights){

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
	public void getArrivingFlights(String dest, Vector flights){
		
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
