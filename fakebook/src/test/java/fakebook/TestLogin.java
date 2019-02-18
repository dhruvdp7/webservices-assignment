package fakebook;

import java.net.URISyntaxException;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qait.fakebook.Login;

public class TestLogin {
	
	Login login;
	Boolean expectedLogin;
	Boolean actualLogin;
	
	@Test
	public void testForCorrectCredentials() throws URISyntaxException{
		login=new Login();
		String email="dhruvpande@qainfotech.com";
		String password="dhruv123";
		expectedLogin=true;
		Response rs=
		//login.login(email, password);
		actualLogin=login.islogin;
		AssertJUnit.assertEquals(expectedLogin,actualLogin);
		
	}
	
	@Test
	public void testForFalseCredentials() throws URISyntaxException{
		login=new Login();
		String email="abc@qainfotech.com";
		String password="dhru23";
		expectedLogin=false;
		login.login(email, password);
		actualLogin=login.islogin;
		AssertJUnit.assertEquals(expectedLogin,actualLogin);
		
	}
	
	

}
