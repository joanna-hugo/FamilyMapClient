package felsted.joanna.fmc.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import felsted.joanna.fmc.R;
import felsted.joanna.fmc.model.FamilyModel;
import felsted.joanna.fmc.model.Settings;
import felsted.joanna.fmc.model.event;

public class EventActivity extends AppCompatActivity {
    Settings mSettings = Settings.getInstance();
    private static final int REQUEST_ERROR = 0;
    private FamilyModel mFamilyModel;

    event center_event;
    //DONE center on the event chosen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Intent i = getIntent();
        setFamilyModel((FamilyModel) i.getSerializableExtra("FAMILY_MODEL"));

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);


        MapFragment mapFrag = new MapFragment();
        mapFrag.setFamilyModel(mFamilyModel);
        String center_event_id = (String) i.getStringExtra("CENTER_EVENT_ID");
        fm.beginTransaction()
                .add(R.id.fragment_container, mapFrag)
                .commit();

//        mapFrag.centerMap(mFamilyModel.getEvent(center_event_id));


        /*
        MapFragment mapFragment = new MapFragment();
        mapFragment.setHideOptions(true);
        mapFragment.setFamilyModel(familyModel);
        mapFragment.setEventId(eventId);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mapFragment).commit();
         */
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

    private void centerOnEvent(){
        Intent i = getIntent();
        String event_id = i.getStringExtra("center_event");
    }

    public Settings getSettings() {
        return mSettings;
    }

    public void setSettings(Settings settings) {
        mSettings = settings;
    }

    public FamilyModel getFamilyModel() {
        return mFamilyModel;
    }

    public void setFamilyModel(FamilyModel familyModel) {
        mFamilyModel = familyModel;
    }

    public event getCenter_event() {
        return center_event;
    }

    public void setCenter_event(event center_event) {
        this.center_event = center_event;
    }
}
