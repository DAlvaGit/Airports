/* The Airport class is the testing class that knows about all the active classes {Airlines, Terminals}
 * 2/22/14
 * The initial iteration has one Terminal with less than a couple of airlines to test with.
 * Needs an airport code to run searches such as flights from or depart from.
 * Assigns Terminal Gates and those gates to Airlines.
 * Calls for Airlines to initialize their flight information.
 * 3/21/2014
 * The CheckFlight Status function was populated and a GUI Panel was added to display minimal information 
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
	
	private volatile Thread m_flightStatusTrd;
	private String m_airportCode;
	private Vector<Airline> m_airLines;
	private Terminal m_airportTerm = null;    // assumes one terminal at airport
	

	public AirPort(int numAL){
		m_airLines = new Vector<Airline>(numAL);
		m_airportTerm = new Terminal();		
	}
	
	public void Airport(){
		m_airLines = new Vector<Airline>();
		m_airportTerm = new Terminal();
	}
	
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

	//Becomes a Threaded function that checks all Flight Statuses every 2 Minutes
	//Calls Airline to check the status of every Flight during a specific hour
	//Tells the airline to print to a status file	
	private void CheckFlightStatus() 
	{	
		System.out.println("Flight Status Thread Started");
		boolean continueStatus = true;
		
		while (continueStatus)
		{
			try{
				System.out.println("going to sleep");
				Thread.sleep(120000);
			}catch (InterruptedException e)
			{
				System.out.println("The thread was indeed interrupted");
			}

			if(!m_thePanel.GetFlightStatusFlag())
			{
				continueStatus = false;
			}
			else
			{
				Calendar currCal = Calendar.getInstance();
				String time = new SimpleDateFormat("HH:mm").format(currCal.getTime());
				System.out.println("Updating Status at " + currCal.getTime().toString());
				
				Enumeration<Airline> emration = m_airLines.elements();
				while(emration.hasMoreElements()){
					Airline al = (Airline) emration.nextElement();
					al.CheckFlightStatus(time, m_airportCode);
				}
				
				m_thePanel.SetFlightStatusText(currCal.getTime().toString());
			}
		}			
	}
	
	private void CheckGateStatus()
	{
		//Becomes a Threaded function that checks Gate Status' every 2 minutes
	}
	
	
	public static void main(String[] args){
		final AirPort Logan = new AirPort(2);  // at most two airlines at airport
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
			if(al.GetAirLineCode() == "DL")
			{
				al.AddGate(Logan.m_airportTerm.addGate("3", "DL"));
				al.AddGate(Logan.m_airportTerm.addGate("4", "DL"));
				
			}
			else if(al.GetAirLineCode() == "AF")
			{
				al.AddGate(Logan.m_airportTerm.addGate("1", "AF"));
				al.AddGate(Logan.m_airportTerm.addGate("2", "AF"));
			}
			else if(al.GetAirLineCode() == "LH")
			{
				al.AddGate(Logan.m_airportTerm.addGate("7", "LH"));
				al.AddGate(Logan.m_airportTerm.addGate("8", "LH"));				
			}
			else if(al.GetAirLineCode() == "LX")
			{
				al.AddGate(Logan.m_airportTerm.addGate("5", "LX"));
				al.AddGate(Logan.m_airportTerm.addGate("6", "LX"));
			}
			
			al.PrintGates();
							
			// create airline flights
			al.CreateFlights(currCal);
		}
		

		Logan.m_thePanel = new AirportPanel();	
		Logan.m_thePanel.SetTerminals(Logan.GetTerminal());
	    SwingUtilities.invokeLater( new Runnable (){
	    	public void run()
	    	{
	    		Logan.m_thePanel.InitandDisplay();
	    	}
	    });
	    // Create Thread to Check Flight Status periodically
	    Logan.CheckFlightStatus();
	    
	    System.out.println((new Date()).toString());
		System.out.println("Ultimately Done!!!");
	}
	

}
