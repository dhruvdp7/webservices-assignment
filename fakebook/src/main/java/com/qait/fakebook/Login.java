package com.qait.fakebook;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;

@Path("login")
public class Login {
	
	@POST
	@Produces(MediaType.TEXT_HTML)
	public Response login(@FormParam("email") String email,@FormParam("password") String password) throws URISyntaxException{
		String output="";
		try{  
			
    		Class.forName("com.mysql.jdbc.Driver");
    		System.out.println("connecting to database.....");
    		Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/fakebook","root","root");    
    		Statement stmt=con.createStatement();  
    		ResultSet rs=stmt.executeQuery("select email,password from information");
    		
    		while(rs.next())  
    		{
    			
    			if(email.equals(rs.getString(1)) && password.equals(rs.getString(2)))
    			{
    				
    				System.out.println("connected....");
    				URI location = new URI("http://localhost:8080/fakebook/home.html");
    				
    		    	return Response.seeOther(location).build();
    		    	
    			}
//    			else {
//    				System.out.println("found");
//    				
//    				output="<html></body>"
//    						+ "<h1>INCORRECT USER NAME OR PASSWORD.</h1><br><br>"
//    						+ " <a href='http://localhost:8080/fakebook/login.html'>Click here</a> to login again"
//    						+ "</body></html>";
//    				
//    				
//    			}
    			
    			
    		}
    		con.close();  
    		}catch(Exception e){ System.out.println(e);}  
		output="<html></body>"
				+ "<h1>INCORRECT USER NAME OR PASSWORD.</h1><br><br>"
				+ " <a href='http://localhost:8080/fakebook/login.html'>Click here</a> to login again"
				+ "</body></html>";
		URI location = new URI("http://localhost:8080/fakebook/login.html");
		return Response.status(401).entity(output).build();
    	}
	

}
