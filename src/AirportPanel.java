/*AirportPanel added to create a UI for the Airport Project
 * 3/21/2014 Initially created to 
 * (1)Display current airlines 
 * (2)Display the last time the Airlines Checked their Flight Status
 * (3)Provide a control to stop the Flight Status loop 
 * Other functionality added later.
 * 
 */
import javax.swing.*;
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
		JLabel aLineLabel = new JLabel("Airlines: ", JLabel.RIGHT);
	    String[] Airlines = {"Air France", "Delta"};
	
	    
	    JList arLineList = new JList(Airlines);
	    
	    Date statusDate = new Date();
	    JLabel statusLbl = new JLabel("Current Flight Status:");
	    m_btnFlSts = new JButton("Flight Status Stop: ");
	    
	    m_statusFld = new JTextField(statusDate.toString());

	    
	    JPanel panel = new JPanel();  
	    panel.add(aLineLabel);
	    panel.add(arLineList);
	    panel.add(m_btnFlSts);
	    panel.add(statusLbl);
	    panel.add(m_statusFld);
	    
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
