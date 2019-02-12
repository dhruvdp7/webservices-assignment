package com.qait.fakebook;

import java.sql.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This class contains three different methods. AddComment method for adding a
 * comment GetComment method for getting comment of a particular user
 * GetAllComments method for getting all the comments.
 */

@Path("comment")
public class Comment {

	private static final ArrayList<JSONObject> JSONObject = null;

	/**
	 * This service will add a comment to the database. It will accept two
	 * parameters that the user will enter. ie, Email and Comment
	 * 
	 */
	@POST
	@Path("addcomment")
	@Produces(MediaType.TEXT_PLAIN)
	public Response AddComment(@FormParam("comment") String comment, @FormParam("email") String email)
			throws URISyntaxException {

		try {
			// DATABASE-CONNECTION
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fakebook", "root", "root");

			PreparedStatement ps = con.prepareStatement("insert into comment values (?,?)");

			ps.setString(1, email);
			ps.setString(2, comment);

			ps.executeUpdate();

			System.out.println("Comment Added");
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		URI location = new URI("http://localhost:8080/fakebook/home.html");
		return Response.seeOther(location).build();

	}

	/**
	 * This service will fetch a comment of the person from the database whose email
	 * is specified in the textbox by user. It will accept one parameter ie, email.
	 * and send the response in JSON format
	 */

	@GET
	@Path("getcomment")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)

	public Response getComment(@QueryParam("email") String email) throws URISyntaxException {

		JSONArray jarray = new JSONArray();
		JSONObject usercomment = new JSONObject();
		String output = "";
		String comment = "";

		try {
			// DATABASE-CONNECTION
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fakebook", "root", "root");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select email, comment from comment where email='" + email + "'");

			while (rs.next()) {
				JSONObject jobj = new JSONObject();
				email = rs.getString(1);
				comment = rs.getString(2);
				jobj.put("email", email);
				jobj.put("comment", comment);
				jarray.put(jobj);
			}

			usercomment.put("comment", jarray);

		} catch (Exception e) {
			System.out.println(e);
		}

		return Response.status(200).entity(usercomment.toString()).build();

	}

	/**
	 * This service will fetch all comments from the database and sends the response
	 * in Json format
	 */

	@GET
	@Path("getallcomments")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllComments() throws URISyntaxException {
		JSONArray jarray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		JSONObject usercomment = new JSONObject();
		String comment = "";
		String email = "";

		try {
			// DATABASE-CONNECTION
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fakebook", "root", "root");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select email, comment from comment");
			while (rs.next()) {

				JSONObject jobj = new JSONObject();
				email = rs.getString(1);
				comment = rs.getString(2);
				jobj.put("email", email);
				jobj.put("comment", comment);
				jarray.put(jobj);

			}
			usercomment.put("comments", jarray);

		} catch (Exception e) {
			System.out.println(e);
		}
		return Response.status(200).entity(usercomment.toString()).build();
	}
}
