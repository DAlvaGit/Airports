/* The Terminal class manages its gates.
 * 2/22/2014 
 * Initially the Terminal class only manages gates.  To be added is the management of baggage claim.
 * Assumes every gate is associated to only one airline. Eventually gates will be flexible to give access to different airlines.
 * 3/22/14
 * The Gates are now being managed by a Map
 * */
import java.util.*;

public class Terminal {

	private String m_terminalCode;
	private Map<String, List<String>> m_sortGates;	// Airline , Gateno's
	private int m_numGates;
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
	
	public String GetTerminalCode()
	{
		return m_terminalCode;
	}
	
	public void GetAirLines(Set<String> alNames)
	{
		alNames.addAll(m_airLines);
	}
	
	// Search on Airline to find gates
	public void GetGates(String airLn, Vector<String> gates)
	{
		m_airLines.add(airLn);
		List<String> specAL = m_sortGates.get(airLn);
		
		if(specAL != null)
		{
			gates.addAll(specAL);
		}		
	}
	
	// Function assumes that every gate is associated to one airline
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
}

