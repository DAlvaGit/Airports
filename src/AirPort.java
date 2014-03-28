/* The Airport class is the testing class that knows about all the active classes {Airlines, Terminals}
 * 2/22/14
 * The initial iteration has one Terminal with less than a couple of airlines to test with.
 * Needs an airport code to run searches such as flights from or depart from.
 * Assigns Terminal Gates and those gates to Airlines.
 * Calls for Airlines to initialize their flight information.
 * 3/21/2014
 * The CheckFlight Status function was populated and a GUI Panel was added to display minimal information
 * 3/28/2014
 * CheckStatus method reads from Airline status rather than writes to a file
 * FlightStatusPanel added to Main 
 * */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.Thread;

import javax.swing.*;


public class AirPort {


	private AirportPanel m_thePanel = null;
	private FlightStatusPanel m_statusPanel = null;
	
	private StatusTask m_flightStatusTrd;
	private StatusTask m_terminalStsTrd;
	private String m_airportCode;
	private Vector<Airline> m_airLines;
	private Terminal m_airportTerm = null;    // assumes one terminal at airport
	

	public AirPort(int numAL){
		m_airLines = new Vector<Airline>(numAL);
		m_airportTerm = new Terminal();		
	}
	
	public AirPort(){
		m_airLines = new Vector<Airline>();
		m_airportTerm = new Terminal();
	}

	static enum Task {FLIGHT, TERMINAL, GATE}
	
	
	public Terminal GetTerminal()
	{
		return m_airportTerm;
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
	
	//adds a Terminal Gate and associates an airline to it.  Assumes one airline per gate
	public void addTerminals(String gate, String alCode){
//		m_airportTerm.AddGate(gate, alCode);
	}


	/*
	 * Depending on the Status type an individual thread calls this method to determine which status to check
	*/
	public void checkStatus(Task statusTp)
	{
		Calendar currCal = Calendar.getInstance();
		String time = new SimpleDateFormat("HH:mm").format(currCal.getTime());

		
		switch(statusTp){
		case FLIGHT:
		{

			Enumeration<Airline> emration = m_airLines.elements();
			Vector<Flight> flights = new Vector<Flight>();

			m_statusPanel.clearTables();
			
			
			while(emration.hasMoreElements()){
				Airline currAirline = (Airline) emration.nextElement();
				
				flights = currAirline.getSortedFlights(time, 2, true, m_airportCode);
				
				if(flights != null)
				{
					for(Flight fl: flights)
					{
						String [] status = { currAirline.getAirLineCode(),fl.flightNO, fl.destination, fl.departureTime,fl.status.toString(),""};// gate will be assigned later
						m_statusPanel.addDepartureStatus(status);
					}
					flights.clear();
				}
				
				flights = currAirline.getSortedFlights(time, 2, false, m_airportCode);
				
				if(flights != null)
				{
					for(Flight fl: flights)
					{
						String [] status = { currAirline.getAirLineCode(),fl.flightNO, fl.origin, fl.arrivalTime,fl.status.toString(),""}; // gate will be assigned later
						m_statusPanel.addArrivalStatus(status);
					}
					flights.clear();	
				}
			}		
			m_statusPanel.refreshTables();
			
/*
			Enumeration<Airline> emration = m_airLines.elements();
			while(emration.hasMoreElements()){
				Airline al = (Airline) emration.nextElement();
				al.CheckFlightStatus(time, m_airportCode);
				
			}
*/			
			m_statusPanel.SetFlightStatusText(currCal.getTime().toString());
			System.out.println("Updating Flight Status at " + currCal.getTime().toString());
			m_thePanel.SetFlightStatusText(currCal.getTime().toString());
			break;
		}
		case TERMINAL:
		{
			System.out.println("Updating Terminal Status at " + currCal.getTime().toString());	
			break;
		}
	}
	}
	
	//Terminal Status Thread initiated to check terminal status every 4 minutes
	public void checkTerminalStatus()
	{
		System.out.println("Terminal Status Thread Started");
		m_terminalStsTrd = new StatusTask(240000,this, Task.TERMINAL);
		m_terminalStsTrd.start();		
	}
	
	//Flight Status Thread initiated to check flight status every 2 minutes
	private void checkFlightStatus() 
	{	
		System.out.println("Flight Status Thread Started");

		m_flightStatusTrd = new StatusTask(10000,this, Task.FLIGHT);
		m_flightStatusTrd.start();
		m_thePanel.SetFlightStatusThread(m_flightStatusTrd);		
	}
	
	private void checkGateStatus()
	{
		//Becomes a Threaded function that checks Gate Status' every 2 minutes
	}
	
	
	public static void main(String[] args){
		final AirPort Logan = new AirPort();  // at most two airlines at airport
		Logan.setAiportCode("BOS");
		
		Logan.m_airportTerm.addTerminalCode("E");
		Logan.m_airportTerm.addNumGates(8);

		Logan.m_airLines.add(new Airline("DL"));
		Logan.m_airLines.add(new Airline("AF"));
		Logan.m_airLines.add(new Airline("LX"));  //Swiss Air
		Logan.m_airLines.add(new Airline("LH"));  //Lufthansa

		Calendar currCal = Calendar.getInstance();		
		//initialize Airlines
		Enumeration<Airline> emration = Logan.m_airLines.elements();
		while(emration.hasMoreElements()){
			Airline al = (Airline) emration.nextElement(); 
			
			//set Terminal Gates to Each airline
			String airlineCode = al.getAirLineCode(); 
			if( airlineCode.equals("DL"))
			{
				al.addGate(Logan.m_airportTerm.addGate("3", "DL"));
				al.addGate(Logan.m_airportTerm.addGate("4", "DL"));			
				
			}
			else if(airlineCode.equals("AF") )
			{
				al.addGate(Logan.m_airportTerm.addGate("1", "AF"));
				al.addGate(Logan.m_airportTerm.addGate("2", "AF"));
					
			}
			else if(airlineCode.equals("LH"))
			{
				al.addGate(Logan.m_airportTerm.addGate("7", "LH"));
				al.addGate(Logan.m_airportTerm.addGate("8", "LH"));				
				
			}
			else if(airlineCode.equals("LX"))
			{
				al.addGate(Logan.m_airportTerm.addGate("5", "LX"));
				al.addGate(Logan.m_airportTerm.addGate("6", "LX"));
			}
			
			al.printGates();
							
			// create airline flights
			al.createFlights(currCal);
		}
		

		Logan.m_thePanel = new AirportPanel();	
		Logan.m_thePanel.SetTerminals(Logan.GetTerminal());

	    SwingUtilities.invokeLater( new Runnable (){
	    	public void run()
	    	{
	    		Logan.m_thePanel.InitandDisplay();
	    	}
	    });
	    

	    SwingUtilities.invokeLater( new Runnable (){
	    	public void run()
	    	{
	    		Logan.m_statusPanel = new FlightStatusPanel();
	    	}
	    });	    
	    // Create Thread to Check Flight Status periodically
	    Logan.checkFlightStatus();
	    Logan.checkTerminalStatus();
	    
	    System.out.println((new Date()).toString());
		System.out.println("Ultimately Done!!!");
	}
	
}
