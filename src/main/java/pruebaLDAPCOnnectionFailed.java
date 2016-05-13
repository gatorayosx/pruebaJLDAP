package main.java;

import com.novell.ldap.LDAPConnection;
import com.novell.ldap.LDAPException;

public class pruebaLDAPCOnnectionFailed {
	
	public static void main(String[] args) throws LDAPException {
		
		final int CONNECTION_MAX_MILISECS = 1;
		final int PORT = 390;
		String[] addresses = {	"fakeAddress1",
								"fakeAddress2",
								"fakeAddress3",
								"fakeAddress4",
								"tireasol47.ral.tirea.es",
								"fakeAddress5"};
		
		
		LDAPConnection lc = new LDAPConnection(CONNECTION_MAX_MILISECS);
		
		for(String address : addresses)
			try {
				lc.connect(address, PORT);
				//Connection found, we connect without time out
				lc.disconnect();
				lc = new LDAPConnection();
				lc.connect(address, PORT);
				break;
			} catch (Exception e) {
				System.out.println("Error connecting to: " + address);
			}
		
		if(lc.isConnected())
			System.out.println("Connected to: " + lc.getHost());
		else
			System.out.println("Error, not available connection!!");
			
		lc.disconnect();
	}
}
