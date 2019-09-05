package felsted.joanna.fmc.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import felsted.joanna.fmc.R;
import felsted.joanna.fmc.model.FamilyModel;
import felsted.joanna.fmc.model.Settings;

public class MainActivity extends AppCompatActivity {
    Settings mSettings = Settings.getInstance(); // NOTE THIS IS WHERE I KEEP THE SETTING APPARENTLY DONE
    private static final int REQUEST_ERROR = 0;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    //DONE Figure out where to store "Settings" info (pass in continually or in some shared space)
    //DONE Connect client to server through the Server Proxy
    //DONE Wire up Login Fragment to Server
    //DONE setup maps https://developers.google.com/maps/documentation/android-sdk/start
    //TODO up button coming back to login screen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            Settings s = null;
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

    protected void switchToMapFragment(FamilyModel familyModel)
    {
        mSettings.setMapFragInMain(true);
        mSettings.setMainLoadMapFragOnCreate(true);
        Fragment mapFrag = new MapFragment();
        ((MapFragment) mapFrag).setFamilyModel(familyModel);
        FragmentManager fm = this.getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_container, mapFrag)
                .commit();
    }

    protected void switchToLoginFragment()
    {
        mSettings.setMapFragInMain(false);
        mSettings.setMainLoadMapFragOnCreate(false);
        Fragment loginFrag = new LoginFragment();
        FragmentManager fm = this.getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_container, loginFrag)
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
