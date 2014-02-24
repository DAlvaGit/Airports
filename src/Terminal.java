/* The Terminal class manages its gates.
 * 2/22/2014 
 * Initially the Terminal class only manages gates.  To be added is the management of baggage claim.
 * Assumes every gate is associated to only one airline. Eventually gates will be flexible to give access to different airlines.
 * */
import java.util.*;

public class Terminal {

	private String m_terminalCode;
	private Hashtable<String, String> m_Gates;
	private int m_numGates;
	
	public Terminal(){
		m_Gates = new Hashtable<String, String>();
		m_terminalCode = new String();
	}
	
	public void addNumGates(int num){
		m_numGates = num;
	}
	public void addTerminalCode(String tc){
		m_terminalCode = tc;
	}
	
	// Function assumes that every gate is associated to one airline
	public String addGate(String gt, String airLn){
		if(m_numGates > m_Gates.size())
		{
			m_Gates.put(m_terminalCode + gt, airLn);
			return m_terminalCode + gt;
		}
		return "\0";
	}
}

