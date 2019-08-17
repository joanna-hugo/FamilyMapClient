package felsted.joanna.fmc.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import felsted.joanna.fmc.R;
import felsted.joanna.fmc.model.settings;

public class MainActivity extends AppCompatActivity {
    settings mSettings; //NOTE THIS IS WHERE I KEEP THE SETTING APPARENTLY TODO
    private static final int REQUEST_ERROR = 0;

    //TODO Figure out where to store "settings" info (pass in continually or in some shared space)
    //DONE Connect client to server through the Server Proxy
    //DONE Wire up Login Fragment to Server
    //TODO setup maps https://developers.google.com/maps/documentation/android-sdk/start
    //TODO Map Fragment
    //TODO Activities
        //TODO Event Activity
        //TODO Filter Activity
        //TODO Person Activity
        //TODO Search Activity
        //TODO Settings Activity
    //Probably helpful
        // chapter 14 SQLite DB
        // chapter 12 Dialogs
        // chapter 25 HTTP
        // chapter 27 SEARCH
        // chapter 33 Locations
        // chapter 34 Maps



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            settings s = null;
            s = s.getInstance();
            if (!s.isMainLoadMapFragOnCreate())
                fragment = new LoginFragment();
            else {
                s.setMapFragInMain(true);
                fragment = new MapFragment();
            }

            fragment = new LoginFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    protected void switchToMapFragment()
    {
        settings s = null;
        s = s.getInstance();
        s.setMapFragInMain(true);
        s.setMainLoadMapFragOnCreate(true);
        Fragment mapFrag = new MapFragment();
        FragmentManager fm = this.getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_container, mapFrag)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (errorCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = apiAvailability
                    .getErrorDialog(this, errorCode, REQUEST_ERROR,
                            new DialogInterface.OnCancelListener() {

                                @Override
                                public void onCancel(DialogInterface dialog) {
                                    // Leave if services are unavailable.
                                    finish();
                                }
                            });

            errorDialog.show();
        }
    }
}
