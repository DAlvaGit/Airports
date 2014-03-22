/*AirportPanel added to create a UI for the Airport Project
 * 3/21/2014 Initially created to 
 * (1)Display current airlines 
 * (2)Display the last time the Airlines Checked their Flight Status
 * (3)Provide a control to stop the Flight Status loop 
 * Other functionality added later.
 * 3/22/2014 Displays a Table with Airlines and their associated Gates
 */
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.lang.*;

public class AirportPanel {


	private JButton m_btnFlSts;
	private JTextField m_statusFld;
	
	private Thread m_FLSThread = null;
	private boolean m_bFLS;
	private Terminal m_terminals;
	
	public void SetTerminals(Terminal term)
	{
		m_terminals = term;
	}
	
	public void SetFlightStatus(boolean fls)
	{
		m_bFLS = fls;
	}
	public boolean GetFlightStatusFlag()
	{
		return m_bFLS;
	}

	public void SetFlightStatusThread(Thread newThread)
	{
		m_FLSThread = newThread;
	}
	public void SetFlightStatusText(String updTime)
	{
		m_statusFld.setText(updTime);				
	}
	
	public void InitandDisplay()
	{
		    
	    Date statusDate = new Date();
	    JLabel statusLbl = new JLabel("Current Flight Status:");
	    m_btnFlSts = new JButton("Flight Status Stop: ");
	    
	    m_statusFld = new JTextField(statusDate.toString());

	    JLabel termLbl = new JLabel("Terminals and Gates:");
	    
	    JPanel panel = new JPanel();  
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

	    //Adding the Terminal Table
	    String[] colNames = {"AirLine", "Terminal", "Gates"};
	    
	    DefaultTableModel dataModel = new DefaultTableModel(colNames,0); 
	    
	    HashSet<String> names = new HashSet<String>();
	    m_terminals.GetAirLines(names);	    

	    for(String airline: names)	
	    {
	    	String [] data = new String[3];
	    	
	    	data[0] = airline;
	    	data[1] = m_terminals.GetTerminalCode();
	    	
	    	Vector<String> gates = new Vector<String>();
	    	m_terminals.GetGates(airline, gates);
	    	String gatesNum = new String();
	    	
	    					
			Enumeration<String> emration = gates.elements();
			while(emration.hasMoreElements()){
				String al = (String) emration.nextElement();
		    	gatesNum += al + ",";
				
			}
	    	data[2] = gatesNum;
	    	
	    	dataModel.addRow(data);
	    }
	    
	    final JTable table = new JTable();
	    
	    table.setModel(dataModel);
	    
	    table.setPreferredScrollableViewportSize(new Dimension(100,50));
	    table.setFillsViewportHeight(true);
	    JScrollPane scrollPane = new JScrollPane(table);	    
	    Box boxMDL = Box.createHorizontalBox();
	    boxMDL.add(scrollPane);
	    panel.add(boxMDL);
	    
	    
	    Box box2 = Box.createHorizontalBox();
	    box2.add(m_btnFlSts);
	    box2.add(Box.createHorizontalStrut(20));
	    box2.add(statusLbl);
	    box2.add(m_statusFld);

	    panel.add(box2);
	    panel.add(Box.createVerticalStrut(20));
	    
	   
	    m_btnFlSts.addActionListener(new ButtonListener());

	    JFrame top = new JFrame("Airport Demo");
	    top.getContentPane().add(panel);
	    top.pack();
	    top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	    m_bFLS = true;	    
	    top.setVisible(true);	  

	}
	
	
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getSource() == m_btnFlSts)
			{
				Date statusDate = new Date();
				System.out.println("Status Button Selected " + statusDate.toString());
				m_bFLS = false;
				m_statusFld.setForeground(Color.red);
				m_statusFld.setText(statusDate.toString());
			}
		}
	}
}
