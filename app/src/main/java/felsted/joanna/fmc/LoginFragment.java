package felsted.joanna.fmc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class LoginFragment extends Fragment {
    private EditText mServerHost;
    private EditText mServerPort;
    private EditText mUserName;
    private EditText mPassword;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private RadioGroup mGender;
    private Button mSignIn;
    private Button mRegister;

//    private RegisterRequest mRegisterRequest; //eventually I will be editing these as the text changes
//    private LoginRequest mLoginRequest;

    /*
        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v){
               checkAnswer(true);
           }
        });
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mRegister = v.findViewById(R.id.Register);
        mRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                switchToMapActivity();
            }
        });
        return v;
    }


    private void switchToMapActivity()
    {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.switchToMapFragment();
    }
}
