/*FlightStatusPanel added to create a GUI to display the status of Arrival and Departure fligthts
 * 3/26/2014 First iteration created to display a static Departing Table and static Arriving Table
 * To be added is a refreshing of actual flight status information at an interval of time
 */
import java.awt.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;

public class FlightStatusPanel {

	public void InitandDisplay()
	{
		JLabel departLbl = new JLabel("Departures: ");
		JLabel arrLbl = new JLabel("Arrivals: ");
		
	    JPanel panel = new JPanel();  
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    
	    Box box1 = Box.createHorizontalBox();
	    box1.add(departLbl);
	    panel.add(Box.createVerticalStrut(30));
		panel.add(box1);
		
	    String[] colNames = {"AirLine", "Flight No", "Destination","Time","Status","Gate"};
	    Object[][] data = {
	    		{"Delta", "100", "LAX", "7:00pm", "At Gate", "A17"},
	    		{"Air France", "220", "CDG", "6:40PM", "At Gate", "E7"}
	    };
//		Add the Departing Table		
		final JTable depTable = new JTable(data,colNames);
		JScrollPane depScrollPane = new JScrollPane(depTable);
	    depTable.setPreferredScrollableViewportSize(new Dimension(400,200));		
		depTable.setFillsViewportHeight(true);

		Box box2 = Box.createHorizontalBox();
		box2.add(depScrollPane);
		panel.add(box2);
		panel.add(Box.createVerticalStrut(50));

		Box box3 = Box.createHorizontalBox();
		box3.add(arrLbl);
		panel.add(box3);
		
//		Add the Arriving Table		
		String[] arrColNames = {"AirLine", "Flight No", "Origin","Time","Status","Gate"};
		final JTable arrTable = new JTable(data,arrColNames);
		JScrollPane arrScrollPane = new JScrollPane(arrTable);
	    arrTable.setPreferredScrollableViewportSize(new Dimension(400,200));		
		arrTable.setFillsViewportHeight(true);
		
		Box box4 = Box.createHorizontalBox();
		box4.add(arrScrollPane);
		panel.add(box4);
		
	    JFrame top = new JFrame("Logan Flight Status");	    
	    
	    top.setSize(500, 700);
	    top.setResizable(false);
	    top.setLocationRelativeTo(null);
	    FlowLayout flow = new FlowLayout();
	    Container content = top.getContentPane();
	    content.setLayout(flow);
	    
	    top.getContentPane().add(panel);

	    top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
	    top.setVisible(true);		 
	}
}
