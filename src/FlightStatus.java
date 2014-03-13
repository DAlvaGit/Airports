
public enum FlightStatus {SCHEDULED, ATGATE,BOARDING, INFLIGHT,LANDED, DELAYED, CANCELLED; 

	public String toString()
	{
		   String s = super.toString();
		   return s.substring(0, 1) + s.substring(1).toLowerCase();
	}
}
