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
import android.widget.Toast;

import java.io.IOException;

import felsted.joanna.fmc.R;
import felsted.joanna.fmc.ServerProxy;
import felsted.joanna.fmc.model.FamilyModel;
import felsted.joanna.fmc.model.event;
import felsted.joanna.fmc.model.eventListResponse;
import felsted.joanna.fmc.model.person;
import felsted.joanna.fmc.model.personListResponse;
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

    private registerRequest mRegisterRequest;
    private loginRequest mLoginRequest;

    private FamilyModel mFamilyModel = FamilyModel.getInstance();

    private static final String TAG = "LoginFrag";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        fakeTestData();
    }

    private void fakeTestData(){
        //String personID, String descendant, String firstName, String lastName,
        //                  String gender, String father, String mother, String spouse
        mFamilyModel.addPerson(new person("Sheila_Parker", "sheila",
                "Sheila", "Parker", "f", "Patrick_Spencer", "Im_really_good_at_names", ""));
        mFamilyModel.addPerson(new person("Patrick_Spencer", "sheila",
                "Patrick", "Spencer", "m", "", "", "Im_really_good_at_names"));
        mFamilyModel.addPerson(new person("Im_really_good_at_names", "sheila",
                "Nicole", "Spencer", "f", "", "", "Patrick_Spencer"));

        //String eventID, String descendant, String person, Double latitude,
        //                 Double longitude, String country, String city, String eventType, int year
        mFamilyModel.addEvent(new event("Sheila_Family_Map", "sheila", "Sheila_Parker", 40.7500d, -110.1167d, "United States", "Salt Lake City", "started family map", 2016));
        mFamilyModel.addEvent(new event("Sheila_Family_Map2", "sheila", "Patrick_Spencer", 45.50d, -115d, "United States", "Murray", "birth", 1990));
        mFamilyModel.addEvent(new event("Sheila_Family_Map3", "sheila", "Sheila_Parker", 43d, -116d, "United States", "Area 51", "bored", 2000));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        //NOTE abstract listener setup into a voi function (to cleanup screen)
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
        mRegister.setEnabled(true); //TODO change to false when done testing

        mSignIn = v.findViewById(R.id.SignIn);
        mSignIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new LoginRequest().execute(); //this function handles switching to mapFrag if needed
            }
        });
        mSignIn.setEnabled(false);

        return v;
    }


    private void switchToMapActivity(FamilyModel familyModel) {
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.switchToMapFragment(familyModel);
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
                Log.i(TAG, "logged in " + result.getUsername());
                personListResponse persons = new ServerProxy().getPersons(result.getAuthToken());
                eventListResponse events = new ServerProxy().getEvents(result.getAuthToken());
                mFamilyModel.setCurrentUser(result.getPersonID());
                mFamilyModel.setEvents(events.getData());
                mFamilyModel.setPersons(persons.getData());
                mFamilyModel.setToken(result.getAuthToken());
                mFamilyModel.setupFilters();
                mFamilyModel.setupAncestors();
                mFamilyModel.setupChildren();
                mFamilyModel.setReSyncRequest(mLoginRequest);
                switchToMapActivity(mFamilyModel);
            }catch(IOException ioe){
                Log.e(TAG, "Failed to fetch URL: ", ioe);
                Toast.makeText(LoginFragment.this.getContext(), R.string.register400, Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    }

    private class RegisterRequest extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params){
//            try{
//                loginResponse result = new ServerProxy().register(mRegisterRequest); //TODO uncomment this, this is just for testing so I don't have to login every time
            //TODO check for 200 response, then create a toast if not

//                personListResponse persons = new ServerProxy().getPersons(result.getAuthToken());
//                eventListResponse events = new ServerProxy().getEvents(result.getAuthToken());
//                mFamilyModel.setEvents(events.getData());
//                mFamilyModel.setPersons(persons.getData());
//                mFamilyModel.setToken(result.getAuthToken());
//                switchToMapActivity();
//            }catch(IOException ioe){
//                Log.e(TAG, "Failed to fetch URL: ", ioe);
//            }
            mFamilyModel.setupFilters();

            switchToMapActivity(mFamilyModel); //TODO uncomment above when done with testing
            return null;
        }
    }
}