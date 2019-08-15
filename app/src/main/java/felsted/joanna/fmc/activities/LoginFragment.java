package felsted.joanna.fmc.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.IOException;

import felsted.joanna.fmc.R;
import felsted.joanna.fmc.ServerProxy;
import felsted.joanna.fmc.model.registerRequest;
import felsted.joanna.fmc.model.loginRequest;
import felsted.joanna.fmc.model.loginResponse;

public class LoginFragment extends Fragment {
    private EditText mServerHost;
    private EditText mServerPort;
    private EditText mUserName;
    private EditText mPassword;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;

    private RadioButton mMale;
    private RadioButton mFemale;
    private RadioGroup mGender;

    private Button mSignIn;
    private Button mRegister;

    private registerRequest mRegisterRequest; //TODO eventually I will be editing these as the text changes
    private loginRequest mLoginRequest;

    private static final String TAG = "LoginFrag";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        //TODO abstract listener setup into a voi function (to cleanup screen)
        mRegisterRequest = new registerRequest();
        mLoginRequest =  new loginRequest();

        mServerHost = v.findViewById(R.id.serverHost);
        mServerHost.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRegisterRequest.setServerHost(s.toString());
                mLoginRequest.setServerHost(s.toString());
                changeAccessibility();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mServerPort = v.findViewById(R.id.serverPort);
        mServerPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRegisterRequest.setServerPort(s.toString());
                mLoginRequest.setServerPort(s.toString());
                changeAccessibility();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mUserName = v.findViewById(R.id.userName);
        mUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRegisterRequest.setUsername(s.toString());
                mLoginRequest.setUsername(s.toString());
                changeAccessibility();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPassword = v.findViewById(R.id.password);
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRegisterRequest.setPassword(s.toString());
                mLoginRequest.setPassword(s.toString());
                changeAccessibility();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mFirstName = v.findViewById(R.id.first_name);
        mFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRegisterRequest.setFirstName(s.toString());
                changeAccessibility();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mLastName = v.findViewById(R.id.last_name);
        mLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRegisterRequest.setLastName(s.toString());
                changeAccessibility();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEmail = v.findViewById(R.id.email);
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mRegisterRequest.setEmail(s.toString());
                changeAccessibility();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mGender = v.findViewById(R.id.gender_radio_group);
        mGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.mFemale:
                        mRegisterRequest.setGender("f");
                        break;
                    case R.id.mMale:
                        mRegisterRequest.setGender("m");
                }
                changeAccessibility();
            }
        });

        mMale = v.findViewById(R.id.mMale);

        mFemale = v.findViewById(R.id.mFemale);



        mRegister = v.findViewById(R.id.Register);
        mRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new RegisterRequest().execute();
            }
        });
        mRegister.setEnabled(false); //TODO change this to false for full functionality

        mSignIn = v.findViewById(R.id.SignIn);
        mSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new LoginRequest().execute(); //this function handles switching to mapFrag if needed
            }
        });
        mSignIn.setEnabled(false); //TODO temp change just for testing, set to true when ready

        return v;
    }


    private void switchToMapActivity() {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.switchToMapFragment();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.mMale:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.mFemale:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }

    private void changeAccessibility() {
        if(mLoginRequest.allInfo()) {
            mSignIn.setEnabled(true);
            }if(mRegisterRequest.allInfo()) {
            mRegister.setEnabled(true);
        }
    }

    private class LoginRequest extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params){
            try{
                loginResponse result = new ServerProxy().login(mLoginRequest);
                //TODO I should save the auth token somewhere
                Log.i(TAG, "logged in " + result.getUsername());
                switchToMapActivity();
            }catch(IOException ioe){
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }
            return null;
        }
    }

    private class RegisterRequest extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params){
            try{
                loginResponse result = new ServerProxy().register(mRegisterRequest);
                //TODO I should save the auth token somewhere
                Log.i(TAG, "logged in " + result.getUsername());
                switchToMapActivity();
            }catch(IOException ioe){
                Log.e(TAG, "Failed to fetch URL: ", ioe);
            }
            return null;
        }
    }
}
