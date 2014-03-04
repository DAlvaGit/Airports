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
	
	// Function retrieves all the flights from a specific airport "code"
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

	// Function retrieves all the flights arriving into a specific airport "code"
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

	
	public void getFlights(String origin, String dest, Vector flights){
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
	
}
