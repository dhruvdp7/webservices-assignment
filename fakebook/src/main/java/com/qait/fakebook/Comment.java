package com.qait.fakebook;
import java.sql.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

@Path("comment")
public class Comment {
	
	@POST
	@Path("addcomment")
	@Produces(MediaType.TEXT_PLAIN)
	public Response AddComment(@FormParam("comment") String comment,@FormParam("email") String email) throws URISyntaxException{
		try{  
    		Class.forName("com.mysql.jdbc.Driver");  
    		Connection con=DriverManager.getConnection(  
    		"jdbc:mysql://localhost:3306/fakebook","root","root");  
    		  
    		PreparedStatement ps=con.prepareStatement("update information set comment = ? where email = ?");  
    			
    		ps.setString(1,comment);  
    		ps.setString(2,email );
    		
    		ps.executeUpdate();
    		
    		System.out.println("Comment Added");
    		con.close();  
    		}catch(Exception e){ System.out.println(e);}  
    		  
    	URI location = new URI("http://localhost:8080/fakebook/home.html");
    	return Response.seeOther(location).build(); 

}
	
	

//	@POST
//    @Path("getcomment")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces(MediaType.APPLICATION_JSON)
//
//	public String getComment(@FormParam("email") String email) throws URISyntaxException{
//		JSONObject jsonObject;
//		String output="";
//		String comment="";
//		
//		try{  
//    		Class.forName("com.mysql.jdbc.Driver");  
//    		Connection con=DriverManager.getConnection(  
//    		"jdbc:mysql://localhost:3306/fakebook","root","root");  
//    		Statement stmt=con.createStatement();  
//    		ResultSet rs=stmt.executeQuery("select email, comment from information where email='"+email+"'");
//    	
//    		while(rs.next())  
//    		{
//    			email= rs.getString(1); 
//    			comment=rs.getString(2);
//    		}
//    		
//		
//		
//	}
//		catch(Exception e) {
//		System.out.println(e);	
//		}
//		output=String.format("{'email': %s, 'comment':%s}", email,comment);
//		jsonObject=new JSONObject(output);
//		return jsonObject.toString();
//		
//	}
	
	
	
	@POST
    @Path("getcomment")
    @Produces(MediaType.TEXT_HTML)
    public Response getComment(@FormParam("email") String email) throws URISyntaxException {
    	String output = "";
    	try{  
    		Class.forName("com.mysql.jdbc.Driver");  
    		Connection con=DriverManager.getConnection(  
    		"jdbc:mysql://localhost:3306/fakebook","root","root");  
    		Statement stmt=con.createStatement();  
    		ResultSet rs=stmt.executeQuery("select comment, email from information where email='"+email+"'");
    		
    		while(rs.next())  
    		{
    			System.out.println("got");
    			output +="<b>Comment:</b> "+ rs.getString(1)+"<b><br> By:</b> " +rs.getString(2)+"";
    		}
    		
    		con.close();
    		return Response.status(200).entity(output).build();	
    		}
    	catch(Exception e){ System.out.println(e);
    	}	
    	URI location = new URI("http://localhost:8080/fakebook/home.html");
    	return Response.seeOther(location).build(); 
    	}
	
	
	
//	@GET
//	@Path("getallcomments")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@Produces(MediaType.APPLICATION_JSON)
//	public String getAllComments() throws URISyntaxException{
//		JSONObject jsonObject=new JSONObject();
//		
//		String comments="";
//	
//		try{  
//    		Class.forName("com.mysql.jdbc.Driver");  
//    		Connection con=DriverManager.getConnection(  
//    		"jdbc:mysql://localhost:3306/fakebook","root","root");  
//    		Statement stmt=con.createStatement();  
//    		ResultSet rs=stmt.executeQuery("select email, comment from information"); 
//    		while(rs.next()) {
//    			
//    			
//		comments=String.format("{comments: [ { 'email' %s, 'comment': %s }]}",rs.getString(1),rs.getString(2));}}
//    		catch(Exception e){ System.out.println(e);
//      	}
//		//jsonObject=new JSONObject(comments);
//		return jsonObject.toString();	
//	}
		
	
	@GET
    @Path("getallcomments")
    @Produces(MediaType.TEXT_HTML)
    public Response getAllComments() throws URISyntaxException {
    	String output = "";
    	try{  
    		Class.forName("com.mysql.jdbc.Driver");  
    		Connection con=DriverManager.getConnection(  
    		"jdbc:mysql://localhost:3306/fakebook","root","root");  
    		Statement stmt=con.createStatement();  
    		ResultSet rs=stmt.executeQuery("select comment, email from information");
    		output="<html><body>";
    		while(rs.next())  
    		{
    			output += "<b>Comment:</b>"+ rs.getString(1)+"<br><b>By:</b>" +rs.getString(2)+ "<br><br><br><br>";
    		}
    		output +="</body></html>";
    		con.close();
    		return Response.status(200).entity(output).build();	
    		}
    	catch(Exception e){ System.out.println(e);
    	}
    	URI location = new URI("http://localhost:8080/fakebook/home.html");
    	return Response.seeOther(location).build(); 
    	}
}
