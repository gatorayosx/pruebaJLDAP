package main.java;

//Sample code file: var/ndk/webBuildengine/tmp/viewable_samples/f91a68eb-ad37-4526-92b1-b1938f37b871/Search.java //Warning: This code has been marked up for HTML

/*******************************************************************************

* $Novell: Search.java,v 1.21 2006/05/17 10:56:09 $

* Copyright (C) 1999-2003 Novell, Inc. All Rights Reserved.

*

* THIS WORK IS SUBJECT TO U.S. AND INTERNATIONAL COPYRIGHT LAWS AND

* TREATIES. USE AND REDISTRIBUTION OF THIS WORK IS SUBJECT TO THE LICENSE

* AGREEMENT ACCOMPANYING THE SOFTWARE DEVELOPMENT KIT (SDK) THAT CONTAINS

* THIS WORK. PURSUANT TO THE SDK LICENSE AGREEMENT, NOVELL HEREBY GRANTS TO

* DEVELOPER A ROYALTY-FREE, NON-EXCLUSIVE LICENSE TO INCLUDE NOVELL'S SAMPLE

* CODE IN ITS PRODUCT. NOVELL GRANTS DEVELOPER WORLDWIDE DISTRIBUTION RIGHTS

* TO MARKET, DISTRIBUTE, OR SELL NOVELL'S SAMPLE CODE AS A COMPONENT OF

* DEVELOPER'S PRODUCTS. NOVELL SHALL HAVE NO OBLIGATIONS TO DEVELOPER OR

* DEVELOPER'S CUSTOMERS WITH RESPECT TO THIS CODE.

*

* $name:         Search.java

* $description:  The Search.java example returns all the entries in the

*                specified container (search base).  All attributes and

*                values are printed. If some attribute values are not 

*                printable, they will be base64 encoded.

******************************************************************************/

//import com.novell.ldap.*;

import com.novell.ldap.LDAPAttribute;

import com.novell.ldap.LDAPAttributeSet;

import com.novell.ldap.LDAPConnection;

import com.novell.ldap.LDAPEntry;

import com.novell.ldap.LDAPException;

import com.novell.ldap.LDAPSearchResults;

import com.novell.ldap.util.Base64;

import java.util.Enumeration;

import java.util.Iterator;

import java.io.UnsupportedEncodingException;

public class Search

{

	public static void main(String[] args)

	{

		if (args.length != 5) {

			System.out.println("Usage:   java Search <host name> <login dn>"

					+ " <password> <search base>\n"

					+ "         <search filter>");

			System.out.println("Example: java Search Acme.com"

					+ " \"cn=admin,o=Acme\""

					+ " secret \"ou=sales,o=Acme\"\n"

					+ "         \"(objectclass=*)\"");

			System.exit(0);

		}

		int ldapPort = LDAPConnection.DEFAULT_PORT;

		int searchScope = LDAPConnection.SCOPE_ONE;

		int ldapVersion = LDAPConnection.LDAP_V3;

		String ldapHost = args[0];

		String loginDN = args[1];

		String password = args[2];

		String searchBase = args[3];

		String searchFilter = args[4];

		LDAPConnection lc = new LDAPConnection();

		try {

			// connect to the server

			lc.connect(ldapHost, ldapPort);

			// bind to the server

			lc.bind(ldapVersion, loginDN, password.getBytes("UTF8"));

			LDAPSearchResults searchResults =

					lc.search(searchBase,

							searchScope,

							searchFilter,

							null, // return all attributes

							false); // return attrs and values

			/*
			 * To print out the search results,
			 * 
			 * -- The first while loop goes through all the entries
			 * 
			 * -- The second while loop goes through all the attributes
			 * 
			 * -- The third while loop goes through all the attribute values
			 * 
			 */

			while (searchResults.hasMore()) {

				LDAPEntry nextEntry = null;

				try {

					nextEntry = searchResults.next();

				}

				catch (LDAPException e) {

					System.out.println("Error: " + e.toString());

					// Exception is thrown, go for next entry

					if (e.getResultCode() == LDAPException.LDAP_TIMEOUT
							|| e.getResultCode() == LDAPException.CONNECT_ERROR)

						break;

					else

						continue;

				}

				System.out.println("\n" + nextEntry.getDN());

				System.out.println("  Attributes: ");

				LDAPAttributeSet attributeSet = nextEntry.getAttributeSet();

				Iterator allAttributes = attributeSet.iterator();

				while (allAttributes.hasNext()) {

					LDAPAttribute attribute =

							(LDAPAttribute) allAttributes.next();

					String attributeName = attribute.getName();

					System.out.println("    " + attributeName);

					Enumeration allValues = attribute.getStringValues();

					if (allValues != null) {

						while (allValues.hasMoreElements()) {

							String Value = (String) allValues.nextElement();

							if (Base64.isLDIFSafe(Value)) {

								// is printable

								System.out.println("      " + Value);

							}

							else {

								// base64 encode and then print out

								Value = Base64.encode(Value.getBytes());

								System.out.println("      " + Value);

							}

						}

					}

				}

			}

			// disconnect with the server

			lc.disconnect();

		}

		catch (LDAPException e) {

			System.out.println("Error: " + e.toString());

		}

		catch (UnsupportedEncodingException e) {

			System.out.println("Error: " + e.toString());

		}

		System.exit(0);

	}

}
