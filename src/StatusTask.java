/*StatusTask was added to create similar but individual Threads do initiate a similar task
 * 3/24/2014
 * The Tasks are initiated by the AirPort class to wait a certain time before checking on a type of status
 * The Tasks are expected to be Interrupted or continue to check the status
 * 
 */

public class StatusTask extends Thread {
	private int m_delay = 1000;
	private AirPort m_airport = null;
	private AirPort.Task m_task; 
	
	StatusTask(int delay, AirPort aport, AirPort.Task task)
	{
		m_delay = delay;
		m_airport = aport;
		m_task = task;
	}
	

	public void run()
	{
		try{
			while (!Thread.currentThread().isInterrupted())
			{
				System.out.println("going to sleep" + m_task.toString());
				Thread.sleep(m_delay);
					
				m_airport.checkStatus(m_task);	
			}
		}catch (InterruptedException e)
		{
			System.out.println("The thread was indeed interrupted: " + m_task.toString());
		};
	}
}
