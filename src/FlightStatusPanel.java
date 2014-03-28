/*FlightStatusPanel added to create a GUI to display the status of Arrival and Departure fligthts
 * 3/26/2014 First iteration created to display a static Departing Table and static Arriving Table
 * To be added is a refreshing of actual flight status information at an interval of time
 * 3/27/2014  Created private Tables to be updated dynamically using an Inner Class StatusModel
 * 3/28/2014  Added Status Coloring for the Status column of the tables
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.DefaultTableModel;

public class FlightStatusPanel {

	private String[] m_depColNames = {"AirLine", "Flight No", "Destination","Time","Status","Gate"};
	private JTable m_depTable = null;
	private JTable m_arrTable = null;
	private JScrollPane m_depScroll = null;
	private JScrollPane m_arrScroll = null;
//	private DefaultTableModel m_depModel = null;	
	private StatusModel m_depModel1 = null;
	private StatusModel m_arrModel = null;
	private String[] m_arrColNames = {"AirLine", "Flight No", "Origin","Time","Status","Gate"};
	private JTextField m_statusFld;	
	
	
	
	JButton m_btn = new JButton("Add Status");
		
	public FlightStatusPanel()
	{		
	    Date statusDate = new Date();		
		JLabel departLbl = new JLabel("Departures: ");
		JLabel arrLbl = new JLabel("Arrivals: ");
	    m_statusFld = new JTextField(statusDate.toString());		
		
	    JPanel panel = new JPanel();  
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    
	    Box box1 = Box.createHorizontalBox();
	    box1.add(departLbl);
	    panel.add(Box.createVerticalStrut(18));
		panel.add(box1);
		
/*
	    Object[][] data = {
	    		{"Delta", "100", "AMS", "7:00pm", "In Flight", "E8"},    			    		
	    		{"Air France", "220", "CDG", "6:40PM", "In Flight", "E7"},
	    		{"Lufthansa", "310", "FRA", "6:15PM", "Cancelled", ""},
	    		{"British Airways", "615", "LHR", "5:45PM", "In Flight", "E2"},
	    		{"Alitalia", "316", "FCO", "5:30PM", "In Flight", "E4"},
	    		{"Iberia", "117", "MAD", "7:15PM", "Delayed", ""},
	    		{"Swiss Air", "220", "ZRC", "7:15PM", "In Flight", "E3"},
	    		{"Air Canada", "110", "YTZ", "6:00PM", "Delayed", ""},
	    		{"Delta", "150", "LHR", "6:40PM", "In Flight", "E7"},
	    		{"Air Lingus", "545", "DUB", "6:30PM", "In Flight", "E8"},
	    		{"Icelandair", "154", "KEF", "7:15PM", "In Flight", "E4"}	    			    		
	    };
*/	    
	    String [] column = {"KLM", "100", "AMS", "7:00pm", "Inflight", "E8"};
//        m_depModel = new DefaultTableModel(data,m_depColNames);
     
		Vector<String> depCNames = new Vector<String>();
		for(String in: m_depColNames){depCNames.addElement(in);}

        
        m_depModel1 = new StatusModel(depCNames);
        m_depModel1.addValue(column);
        
//		Add the Departing Table		
		m_depTable = new JTable(m_depModel1);
		m_depTable.setDefaultRenderer(String.class, new StatRenderer());

		
		m_depScroll = new JScrollPane(m_depTable);
	    m_depTable.setPreferredScrollableViewportSize(new Dimension(500,150));		
		m_depTable.setFillsViewportHeight(true);

		Box box2 = Box.createHorizontalBox();
		box2.add(m_depScroll);
		panel.add(box2);
		panel.add(Box.createVerticalStrut(50));

		Box box3 = Box.createHorizontalBox();
		box3.add(arrLbl);
		panel.add(box3);
		
//		Add the Arriving Table
/*		
	    Object[][] data2 = {
	    		{"Delta", "101", "AMS", "4:45pm", "Landed", "E8"},
	    		{"Air France", "220", "CDG", "2:40PM", "Landed", "E7"},
	    		{"Lufthansa", "310", "FRA", "2:55PM", "Landed", "E3"},
	    		{"British Airways", "617", "LHR", "3:20PM", "Delayed", ""},
	    		{"Alitalia", "318", "FCO", "4:50PM", "Landed", "E4"},
	    		{"Swiss Air", "420", "ZRC", "3:15PM", "Landed", "E3"},
	    		{"Air Canada", "110", "YTZ", "3:00PM", "Landed", "E5"},
	    		{"Air Canada", "410", "YUL", "4:40PM", "Cancelled", ""},
	    		{"American", "333", "DUS", "3:15PM", "Cancelled", ""},
	    		{"Icelandair", "155", "KEF", "4:15PM", "Landed", "E4"}	    			    		
	    };
*/	    
	    depCNames.clear();
	    for(String name: m_arrColNames){depCNames.addElement(name);}
	    column = new String [] {"Swiss Air", "420", "ZRC", "3:15PM", "Landed", "E3"};

        m_arrModel = new StatusModel(depCNames);
        m_arrModel.addValue(column);	    
	    
		m_arrTable = new JTable(m_arrModel);
		m_arrTable.setDefaultRenderer(String.class, new StatRenderer());		
		m_arrScroll = new JScrollPane(m_arrTable);
	    m_arrTable.setPreferredScrollableViewportSize(new Dimension(500,150));		
		m_arrTable.setFillsViewportHeight(true);
		
		Box box4 = Box.createHorizontalBox();
		box4.add(m_arrScroll);
		panel.add(box4);
		panel.add(Box.createVerticalStrut(25));
	
	    JLabel statusLbl = new JLabel("Current Flight Status:");		
		Box box6 = Box.createHorizontalBox();
		box6.add(statusLbl);
	    box6.add(Box.createHorizontalStrut(20));		
		box6.add(m_statusFld);
		panel.add(box6);
		panel.add(Box.createVerticalStrut(25));
	
		
		Box box5 = Box.createHorizontalBox();
		m_btn.addActionListener(new ButtonListener());
		box5.add(m_btn);
		panel.add(box5);
		

	    JFrame top = new JFrame("Logan Flight Status");
	    
	    
	    top.setSize(600, 600);
	    top.setResizable(false);
	    top.setLocationRelativeTo(null);
	    FlowLayout flow = new FlowLayout();
	    Container content = top.getContentPane();
	    content.setLayout(flow);
	    
	    top.getContentPane().add(panel);

	    top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
	    top.setVisible(true);		 
	}
	
	public void addDepartureStatus(String [] status)
	{
		m_depModel1.addValue(status);
//		m_depModel1.fireTableDataChanged();
	}
	
	public void refreshDepTable()
	{
		m_depTable.repaint();
		m_depScroll.repaint();		
	}
	
	public void addArrivalStatus(String [] status)
	{
		m_arrModel.addValue(status);
//		m_arrModel.fireTableDataChanged();
	}

	public void refreshArrTable()
	{
		m_arrTable.repaint();
		m_arrScroll.repaint();				
	}
	
	public void refreshTables()
	{
		m_arrTable.repaint();
		m_arrScroll.repaint();				
		m_depTable.repaint();
		m_depScroll.repaint();		
	}
	
	public void clearTables()
	{
		m_depModel1.removeAllRows();
		m_arrModel.removeAllRows();
	}
	
	public void SetFlightStatusText(String updTime)
	{
		m_statusFld.setText(updTime);				
	}	
	
	//Created inner class to make Tables not editable
	private class StatusModel extends AbstractTableModel{
		private Vector<String> colNames;
		private Vector<Vector<String>> data;
		
		public StatusModel(){
			this.colNames = new Vector<String>();
			this.data = new Vector<Vector<String>>();
		}
		public StatusModel(Vector<String> colNames)
		{
			this.colNames = new Vector<String>();
			this.data = new Vector<Vector<String>>();
			
			this.colNames.addAll(colNames);
		}
		public void setColumnNames(Vector<String> cn)
		{
			this.colNames.addAll(cn);
		}
		
		public String getColumnName(int column)
		{
			return this.colNames.get(column);
		}
		
		public int getColumnCount() {
			
			return this.colNames.size();
		}

		
		public int getRowCount() {
			return this.data.size();
		}


//		public Object getValueAt(int row, int col)		
		public String getValueAt(int row, int col) 		
		{
//			Object cell = (Object) this.data.get(row).get(col);
//			return cell;
			return this.data.get(row).get(col);
		}
		

		public void addValue(String [] val)
		{
			Vector<String> newVec = new Vector<String>();

			for(String cell: val)
			{
				newVec.add(cell);
			}
			
			this.data.addElement(newVec);
		}
		
		public Class<?> getColumnClass(int columnIndex)
		{
			return String.class;
		}
		
		public void removeAllRows()
		{
			this.data.clear();
		}
		
		public void remove(int row)
		{
			this.data.remove(row);
		}
		
		public String getState(int row, int col)
		{
			return this.data.get(row).get(col);
		}
		
	}
		
	   private static class StatRenderer extends DefaultTableCellRenderer 
	   {
	        Color backgroundColor = getBackground();
	        @Override
	        public Component getTableCellRendererComponent(
	            JTable table, Object value, boolean isSelected,
	            boolean hasFocus, int row, int column) 
	        {
	            Component c = super.getTableCellRendererComponent(
	                table, value, isSelected, hasFocus, row, column);
	            StatusModel model = (StatusModel) table.getModel();
	    
	            String status = model.getState(row, column); 
	            
	            if (status.equals(FlightStatus.INFLIGHT.toString()) ||
	            	status.equals(FlightStatus.LANDED.toString())	||
	            	status.equals(FlightStatus.BOARDING.toString())) 
	            {
	                c.setBackground(Color.YELLOW.brighter());
	            } 
	            else if(status.equals(FlightStatus.DELAYED.toString()) ||
	            		status.equals(FlightStatus.CANCELLED.toString()))
	            {
	                c.setBackground(Color.PINK);	            	
	            }
	            else if(status.equals(FlightStatus.SCHEDULED.toString()))
	            {
	                c.setBackground(Color.GREEN.brighter());	            		            	
	            }
	            else if (!isSelected) 
	            {
	                c.setBackground(backgroundColor);
	            }
	            return c;
	        }
	    }	
	
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(e.getSource() == m_btn)
			{
				Date statusDate = new Date();
				System.out.println("Adding a new Status " + statusDate.toString());
				m_depModel1.addValue(new String[]{"United", "200", "JFK", "9:30am", "Inflight", "B10"});
				
				m_depModel1.fireTableDataChanged();
				m_depTable.repaint();
				m_depScroll.repaint();
			}
		}
	}	
}
