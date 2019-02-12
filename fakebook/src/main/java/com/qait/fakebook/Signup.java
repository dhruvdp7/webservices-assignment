package com.qait.fakebook;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("signup")
public class Signup {

	@POST
	@Produces(MediaType.TEXT_HTML)
	public Response signup(@FormParam("fname") String fname, @FormParam("lname") String lname,
			@FormParam("email") String email, @FormParam("password") String password) throws URISyntaxException {
		try {
			// DATABASE-CONNECTION
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fakebook", "root", "root");

			PreparedStatement ps = con.prepareStatement("insert into information values(?,?,?,?)");

			ps.setString(1, fname);
			ps.setString(2, lname);
			ps.setString(3, password);
			ps.setString(4, email);

			ps.executeUpdate();

			System.out.println("Registered");
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		String output = "<html><body>" + "<h1>Signup Successful</h1><br><br>"
				+ " <a href='http://localhost:8080/fakebook/login.html'>Click here</a> to login." + "</body></html>";

		URI location = new URI("http://localhost:8080/fakebook/signup.html");
		return Response.status(200).entity(output).build();
	}

}
