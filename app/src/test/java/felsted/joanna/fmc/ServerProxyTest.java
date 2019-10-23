package felsted.joanna.fmc;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;
import felsted.joanna.fmc.ServerProxy;
import felsted.joanna.fmc.activities.LoginFragment;
import felsted.joanna.fmc.model.loginResponse;
import felsted.joanna.fmc.model.registerRequest;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ServerProxyTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void registration_pass() throws IOException {
        registerRequest rqst = new registerRequest();
        rqst.setServerHost("localhost");
        rqst.setServerPort("8080");
        rqst.setUsername("redjojo");
        rqst.setPassword("myPassword");
        rqst.setFirstName("JoJo");
        rqst.setLastName("Smith");
        rqst.setEmail("myemail@gmail.com");
        rqst.setGender("f");
        loginResponse result = new ServerProxy().register(rqst);
        // loginResponse result = new ServerProxy().register(mRegisterRequest);

//        new LoginFragment.RegisterRequest().execute();


        assertEquals(4, 2 + 2);
    }
    /*
    logging in,
    registering a new user,
    retrieving people, and
    retrieving events
     */

}
