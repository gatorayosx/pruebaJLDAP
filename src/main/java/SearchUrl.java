package main.java;


import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchResult;

/**
 * Demonstrates how to search by specifying a URL.
 *
 * usage: java SearchURL
 */
class SearchURL {
    public static void main(String[] args) {

	try {
	    // Create initial context
	    DirContext ctx = new InitialDirContext();

	    // Perform search using URL
	    NamingEnumeration<SearchResult> answer = ctx.search(
		"ldap://localhost:10389/dc=usuarios", null);

	    // Close the context when we're done
	    ctx.close();
	} catch (NamingException e) {
	    e.printStackTrace();
	}
    }
}