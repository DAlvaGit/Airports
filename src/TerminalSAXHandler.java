/* The TerminalSAXHandler class was added to gather all the terminal gate numbers from an XML File via SAX.
 * 4/2/14
 * Class Created
 * */
import java.util.*;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class TerminalSAXHandler extends DefaultHandler{
	
	private boolean m_hvTermName = false;
	private String m_termName = null;
	private int m_termNum = 0;
	private Vector<String> gates = null;
	private Map<String, List<String>> m_gates;
	private String m_elementNm = null;
	private String m_elemVal = null;
	
	public TerminalSAXHandler()
	{
		gates = new Vector<String>();
		m_gates = new TreeMap<String, List<String>>();		
	}
	public void startDocument()
	{
		System.out.println("Start document");
		
	}
	public void endDocument()
	{
		System.out.println("End document");			
	}	
	
	public void startElement(String uri, String localName, String qname, Attributes attr)
	{
		System.out.println("Start Element name: " + qname);
		m_elementNm = qname;
		m_hvTermName = false;
	}
	
	public void endElement(String uri, String localName, String qname)
	{
		System.out.println("End Element name: " + qname);
		
		if(qname.equals("terminalname"))
		{
			m_hvTermName = true;
			m_termNum++;
		}
		else if(qname.equals("gate"))
		{	
			gates.add(m_elemVal);
		
			System.out.println("This is the terminal name: " + m_termName);
			List<String> thisGate = m_gates.get(m_termName);
			
			if(thisGate == null)
			{
				List<String> newGates = new LinkedList<String>();
				newGates.add(m_elemVal);
				m_gates.put(m_termName, newGates);
			}
			else
			{			
				thisGate.add(m_elemVal);
			}		
		}	
	}
	
	public void characters(char [] ch, int start, int length)
	{
		String element = new String(ch,start, length);		
		if(m_elementNm != null)
		if(m_elementNm.equals("terminalname"))
		{
			if(!m_hvTermName)
				m_termName = element;
			System.out.println("Terminal Name " + element);
		}
		else if(m_elementNm.equals("gate"))
		{
			m_elemVal = element;
		}
		System.out.println("ELement: " + element);			
		
	}
	
	public void ignorableWhiteSpace(char [] ch, int start, int length)
	{
		System.out.println("IGR white space " + length);			
	}
	
	// This method gets passed in a terminal name.
	// If the terminal exists it retreives all the gates in the terminal
	public Vector<String> getGates(String terminal)
	{
		List<String> thisGate = m_gates.get(terminal);
		
		if(thisGate != null)
		{
			gates = new Vector<String>();
			gates.addAll(thisGate);
			return gates;
		}
		
		return null;
	}
}
