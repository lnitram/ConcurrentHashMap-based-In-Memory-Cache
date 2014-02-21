package CacheSystem.util;

public class ValueObj {
	private Object object;
	private long expiry;
	
	public ValueObj (Object obj, long TTL) {
		this.object 	 = obj;
		this.expiry = ((new java.util.GregorianCalendar ()).getTimeInMillis() + TTL); 
	}
	
	public boolean isExpired () {
		return (expiry <= (new java.util.GregorianCalendar ()).getTimeInMillis());   
	}
	
	public Object getObject() {
		return object;
	}
}
