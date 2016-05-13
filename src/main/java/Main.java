package main.java;

import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchResult;

public class Main {

	public static void main(String[] args) {
		try {
			// Create initial context
			DirContext ctx = new InitialDirContext();

			// Perform search using URL
			NamingEnumeration<SearchResult> answer = ctx.search("ldap://tireasol47.ral.tirea.es:390/ou=Explotacion,ou=Empleados,o=TIREA,c=ES",null);

			// Close the context when we're done
			ctx.close();
		    
		    
		    
//		    String[] addresses = { "server1.example.com", "server2.example.com" };
//		    int[]    ports     = { 389, 389 };
//
//		    RoundRobinServerSet roundRobinSet = new RoundRobinServerSet(addresses, ports);
//		    BindRequest bindRequest =
//		         new SimpleBindRequest("uid=pool.user,dc=example,dc=com", "password");
//		    LDAPConnectionPool pool =
//		         new LDAPConnectionPool(roundRobinSet, bindRequest, 10);
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}

	}

}
