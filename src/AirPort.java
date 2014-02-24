/* The Airport class is the testing class that knows about all the active classes {Airlines, Terminals}
 * 2/22/14
 * The initial iteration has one Terminal with less than a couple of airlines to test with.
 * Needs an airport code to run searches such as flights from or depart from.
 * Assigns Terminal Gates and those gates to Airlines.
 * Calls for Airlines to initialize their flight information.
 * */

import java.util.*;

public class AirPort {

	private String m_airportCode;
	private Vector<Airline> m_airLines;
	private Terminal m_airportTerm;    // assumes one terminal at airport
	
	public AirPort(int numAL){
		m_airLines = new Vector(numAL);
		m_airportTerm = new Terminal();		
	}
	
	public void Airport(){
		m_airLines = new Vector();
		m_airportTerm = new Terminal();
	}
	
	public void setAiportCode(String ac){
		m_airportCode = ac;
	}
	
	public String getAirportCode(){
		return m_airportCode;
	}
	public void addAirLine(String alCode){
		Airline newAL = new Airline(alCode);
		m_airLines.add(newAL);
	}
	
	//adds a Terminal Gate and associates an airline to it
	public void addTerminals(String gate, String alCode){
//		m_airportTerm.AddGate(gate, alCode);
	}
	
	public static void main(String[] args){
		AirPort Logan = new AirPort(2);  // at most two airlines at airport
		Logan.setAiportCode("BOS");
		
		
		Logan.m_airportTerm.addTerminalCode("E");
		Logan.m_airportTerm.addNumGates(7);

		Logan.m_airLines.add(new Airline("DL"));
		Logan.m_airLines.add(new Airline("AF"));
		
		//initialize Airlines
		Enumeration emration = Logan.m_airLines.elements();
		while(emration.hasMoreElements()){
			Airline al = (Airline) emration.nextElement(); 
			
			//set Terminal Gates to Each airline
			if(al.GetAirLineCode() == "DL")
			{
				al.AddGate(Logan.m_airportTerm.addGate("3", "DL"));
				al.AddGate(Logan.m_airportTerm.addGate("4", "DL"));
			}
			else if(al.GetAirLineCode() == "AF")
			{
				al.AddGate(Logan.m_airportTerm.addGate("1", "DL"));
				al.AddGate(Logan.m_airportTerm.addGate("2", "DL"));
			}
			al.PrintGates();
			
			// create airline flights
			al.CreateFlights();

		}
		
				
	}
}
