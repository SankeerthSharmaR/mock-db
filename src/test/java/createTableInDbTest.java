
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;
import org.mockito.Mockito;

import com.mockdb.creation.captureUserDetails;
import com.mockdb.creation.loginValidation;

public class createTableInDbTest {

	
@Test
public void validateUsernameAndPasswordTest() throws IOException {
	BufferedReader br = Mockito.mock(BufferedReader.class);
	when(br.readLine()).thenReturn("giri789a95bc72cf82d4eaa0874de0175dc2091789cricket789demo1");
	 boolean actual = loginValidation.validateUsernameAndPassword("src/main/resources/autherization.txt","giri", "a95bc72cf82d4eaa0874de0175dc2091");
	assertTrue(actual);

}

@Test
public void validateUsernameAndPasswordTestFalse() throws IOException {
	BufferedReader br = Mockito.mock(BufferedReader.class);
	when(br.readLine()).thenReturn("giri789a95bc72cf82d4eaa0874de0175dc2091789cricket789demo1");
	 boolean actual = loginValidation.validateUsernameAndPassword("src/main/resources/autherization.txt","giri", "a95bc72d4eaa0874de0175dc2091");
	assertFalse(actual);

}

@Test
public void validateUsernameAndPasswordAndSecurityTest() throws IOException {
	BufferedReader br = Mockito.mock(BufferedReader.class);
	when(br.readLine()).thenReturn("giri789a95bc72cf82d4eaa0874de0175dc2091789cricket789demo1");
	 boolean actual = loginValidation.validateUsernameAndPasswordAndSecurity("src/main/resources/autherization.txt", "giri", "a95bc72cf82d4eaa0874de0175dc2091", "cricket");
	 assertTrue(actual);
}

@Test
public void validateUsernameAndPasswordAndSecurityTestFalse() throws IOException {
	BufferedReader br = Mockito.mock(BufferedReader.class);
	when(br.readLine()).thenReturn("giri789a95bc72cf82d4eaa0874de0175dc2091789cricket789demo1");
	 boolean actual = loginValidation.validateUsernameAndPasswordAndSecurity("src/main/resources/autherization.txt", "giri", "a95bc72cf82d4eaa0874de0175dc2091", "football");
	 assertFalse(actual);
}

@Test
public void checkSignUpUsernameTest() throws IOException {
	BufferedReader br = Mockito.mock(BufferedReader.class);
	when(br.readLine()).thenReturn("giri789a95bc72cf82d4eaa0874de0175dc2091789cricket789demo1");
	File fr = new File("src/main/resources/autherization.txt");
	boolean actual = captureUserDetails.checkSignUpUsername(fr, "giri");
	assertTrue(actual);
}

@Test
public void checkSignUpUsernameTestFalse() throws IOException {
	BufferedReader br = Mockito.mock(BufferedReader.class);
	when(br.readLine()).thenReturn("giri789a95bc72cf82d4eaa0874de0175dc2091789cricket789demo1");
	File fr = new File("src/main/resources/autherization.txt");
	boolean actual = captureUserDetails.checkSignUpUsername(fr, "siri");
	assertFalse(actual);
}

}
