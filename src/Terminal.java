/* The Terminal class manages its gates.
 * 2/22/2014 
 * Initially the Terminal class only manages gates.  To be added is the management of baggage claim.
 * Assumes every gate is associated to only one airline. Eventually gates will be flexible to give access to different airlines.
 * 3/22/14
 * The Gates are now being managed by a Map
 * 4/2/14
 * The Gates are keyed by Airlines and Gate Code.
 * Gatecodes are initalized from an XML file
 * */
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class Terminal {

	private String m_terminalCode;
	private Map<String, List<String>> m_sortGates;	// Airline , GateNo's;  This sorts the Gates by airlines
	
	private Map<String, String[]> m_gates;		//Gate code, List of hour  This is the list of gates  
	private int m_numGates;
	private Vector<String> m_gateCodes;
	private Set<String> m_airLines = new HashSet<String>();
	
	
	public Terminal(){
		m_terminalCode = new String();
		m_sortGates = new TreeMap<String, List<String>>();
		
	}
	
	public void addNumGates(int num){
		m_numGates = num;
	}
	public void addTerminalCode(String tc){
		m_terminalCode = tc;
	}
	
	public String getTerminalCode()
	{
		return m_terminalCode;
	}
	
	public void getAirLines(Set<String> alNames)
	{
		alNames.addAll(m_airLines);
	}
	
	// Search on Airline to find gates
	public void getGates(String airLn, Vector<String> gates)
	{
		m_airLines.add(airLn);
		List<String> specAL = m_sortGates.get(airLn);
		
		if(specAL != null)
		{
			gates.addAll(specAL);
		}		
	}
	
	// Method assumes that every gate is associated to one airline
	public String addGate(String gt, String airLn){

		List<String> specAL = m_sortGates.get(airLn);
		m_airLines.add(airLn);
		
		if(specAL == null)
		{
			List<String> newAL = new LinkedList<String>();
			newAL.add(gt);
			m_sortGates.put(airLn, newAL);
		}
		else
		{			
			specAL.add(gt);
		}		

		return m_terminalCode + gt;
	}
	
	//This method assigns the gate number to the Airline
	//On 4/2  gates can be associated to different Airlines
	public boolean assignGate(String gt, String  airLn)
	{
		boolean exists = false;
		for(String gc: m_gateCodes)
		{
			if(gc.equals(gt))
			{
				exists = true;
				break;
			}
		}
		
		if(!exists)
		{
			return false;
		}
		
		List<String> specAL = m_sortGates.get(airLn);
		m_airLines.add(airLn);
		
		if(specAL == null)
		{
			List<String> newAL = new LinkedList<String>();
			newAL.add(gt);
			m_sortGates.put(airLn, newAL);
		}
		else
		{			
			specAL.add(gt);
		}		

		return true;
	}
	
	
	// This method will read in from a 'file' the gate numbers and will initialize the gates
	public void initGates()
	{

		m_gateCodes = new Vector<String>();
//		m_gates = new TreeMap<String, String[]>();
		
		File xmlFile = new File("terminalCfg.xml");
	
		System.out.println("the files does exist : " + xmlFile.exists());
	
		if(xmlFile.exists())
		{
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser parser = null;
			
			try{
				parser = spf.newSAXParser();				
			}
			catch(SAXException se){
				System.out.println("There was a SAX2 Exception");			
			}
			catch(ParserConfigurationException pe){
				System.out.println("There was a Parser Exception");
			}
			
			TerminalSAXHandler sah = new TerminalSAXHandler();
			
			try
			{
				parser.parse(xmlFile,sah);
			}
			catch (IOException ioe ){
				System.out.println("There was an IO Exception");
				
			}
			catch (SAXException sep)
			{
				System.out.println("There was a SAX Parsing Exception");
				sep.printStackTrace();
			}
			
			Vector<String> terms = null;
			
			terms = sah.getGates(m_terminalCode);
			
			if(terms != null)
			{
				System.out.println("The gates at Terminal " + m_terminalCode);
			
				m_numGates = terms.size();
				m_gateCodes.addAll(terms);
				for(String gate:terms)
				{
					System.out.println(gate);
				}
				terms = null;
			}
		}
	}
	
	public void setGate(Flight fl, boolean departing)
	{
		// is it a departing flight?  //
		// which gate is assigned to the airline
		// select a gate and assign to the flight based on availability 
		fl.arrivalGate = "TBA";
	}
	/*
	 * Requirements:
	 * 1) If the flight is a departing international flight then the flight reserves a gate for 3 hours before departure
	 * 2) If the flight is a departing non-international flight then the flight reserves a gate for 1 hour
	 * 3) If the flight is an arriving flight then the flight reserves a gate for 1 hour
	 * 	3a) If the flight is not international than the Airplane's tail wing number is checked to see for it's next flight.  
	 * 		IE arrival FlightNo could change to new departing flight number
	 * 
	 */
	
	public void retrieveGateCodes(Vector<String> code)
	{
		//return a copy of gate codes
		if(code != null)
		code.addAll(m_gateCodes);
	}
	
}

