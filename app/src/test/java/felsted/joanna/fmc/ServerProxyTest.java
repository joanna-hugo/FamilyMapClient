package felsted.joanna.fmc;
import org.junit.Test;
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
    public void registration_pass() {
//        registerRequest rqst = new registerRequest();
//        loginResponse result = new ServerProxy().register(rqst);


        assertEquals(4, 2 + 2);
    }
    /*
    logging in,
    registering a new user,
    retrieving people, and
    retrieving events
     */

}
