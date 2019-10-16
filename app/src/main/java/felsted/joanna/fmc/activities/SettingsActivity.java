package felsted.joanna.fmc.activities;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.maps.GoogleMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import felsted.joanna.fmc.R;
import felsted.joanna.fmc.ServerProxy;
import felsted.joanna.fmc.model.FamilyModel;
import felsted.joanna.fmc.model.Settings;
import felsted.joanna.fmc.model.eventListResponse;
import felsted.joanna.fmc.model.personListResponse;

public class SettingsActivity extends AppCompatActivity  {

    //DONE Life Story line color
    //DONE Family Tree line color
    //DONE Spouse line color
    //DONE Map Type
    //DONE Re-sync data
    //DONE Logout
    //DONE make layout pretty
    //DONE hook up Re-sync to server //DONE test
    //TODO Radio buttons not toggles
    //DONE make map reload when going back to map (in case Settings change)

    private Settings mSettings = Settings.getInstance();
    private String TAG = "SETTINGS";
    private FamilyModel mFamilyModel= FamilyModel.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        configureLifeStorySpinner();
        configureFamilyTreeSpinner();
        configureSpouseSpinner();
        configureMapTypeSpinner();
        configureReSync();
        configureLogout();
    }

    private void configureLifeStorySpinner(){
//        https://www.journaldev.com/9231/android-spinner-drop-down-list

        //https://docs.oracle.com/javase/tutorial/uiswing/components/spinner.html
        Spinner life_story_spinner = findViewById(R.id.life_story_spinner);

        // Spinner click listener
        life_story_spinner.setOnItemSelectedListener(new life_story_listener());

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("Blue");
        categories.add("Black");
        categories.add("Red");
        categories.add("Green");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        life_story_spinner.setAdapter(dataAdapter);

        //set default to GREEN to match default Settings object
        life_story_spinner.setSelection(colorToInt(mSettings.getLifeStoryLinesColor()));
        ToggleButton button = findViewById(R.id.life_story_button);
        button.setChecked(mSettings.isShowLifeStoryLines());
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSettings.setShowLifeStoryLines(true);
                    Log.i(TAG, "set ShowLifeStoryLines to true");
                } else {
                    mSettings.setShowLifeStoryLines(false);
                    Log.i(TAG, "set ShowLifeStoryLines to false");
                }
            }
        });
    }

    private void configureFamilyTreeSpinner(){
//        https://www.journaldev.com/9231/android-spinner-drop-down-list

        //https://docs.oracle.com/javase/tutorial/uiswing/components/spinner.html
        Spinner life_story_spinner = findViewById(R.id.family_tree_spinner);

        // Spinner click listener
        life_story_spinner.setOnItemSelectedListener(new family_tree_listener());

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("Blue");
        categories.add("Black");
        categories.add("Red");
        categories.add("Green");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        life_story_spinner.setAdapter(dataAdapter);

        //set default to BLUE to match default Settings object
        life_story_spinner.setSelection(colorToInt(mSettings.getFamilyTreeLinesColor()));
        ToggleButton button = findViewById(R.id.family_tree_button);
        button.setChecked(mSettings.isShowFamilyTreeLines());
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSettings.setShowFamilyTreeLines(true);
                    Log.i(TAG, "set ShowLifeStoryLines to true");
                } else {
                    mSettings.setShowFamilyTreeLines(false);
                    Log.i(TAG, "set ShowLifeStoryLines to false");
                }
            }
        });
    }

    private void configureSpouseSpinner(){
//        https://www.journaldev.com/9231/android-spinner-drop-down-list

        //https://docs.oracle.com/javase/tutorial/uiswing/components/spinner.html
        Spinner life_story_spinner = findViewById(R.id.spouse_spinner);

        // Spinner click listener
        life_story_spinner.setOnItemSelectedListener(new spouse_listener());

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("Blue");
        categories.add("Black");
        categories.add("Red");
        categories.add("Green");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        life_story_spinner.setAdapter(dataAdapter);

        //set default to RED to match default Settings object
        life_story_spinner.setSelection(colorToInt(mSettings.getSpouseLinesColor()));
        ToggleButton button = findViewById(R.id.spouse_button);
        button.setChecked(mSettings.isShowSpouseLines());
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSettings.setShowSpouseLines(true);
                } else {
                    mSettings.setShowSpouseLines(false);
                }
            }
        });
    }

    private void configureMapTypeSpinner() {
//        https://www.journaldev.com/9231/android-spinner-drop-down-list

        //https://docs.oracle.com/javase/tutorial/uiswing/components/spinner.html
        Spinner life_story_spinner = findViewById(R.id.map_spinner);

        // Spinner click listener
        life_story_spinner.setOnItemSelectedListener(new map_listener());

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("Normal");
        categories.add("Hybrid");
        categories.add("Satellite");
        categories.add("Terrain");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        life_story_spinner.setAdapter(dataAdapter);

        //set default to NORMAL to match default Settings object
        life_story_spinner.setSelection(mapToInt(mSettings.getMapType()));
    }

    private int mapTypeToIndex(){
        int type = mSettings.getMapType();
        switch (type) {
            case 2: //Satellite
                return 2;
            case 3: //Terrain
                return 3;
            case 4://Hybrid
                return 1;
            default: return 0; //Normal
        }
    }

    private void configureReSync(){
        //DONE config resync
        Button sync = findViewById(R.id.resync_button);
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this,
                        "Resyncing data",
                        Toast.LENGTH_SHORT).show();
                new ResyncRequest().execute();
            }
        });
    }

    private class ResyncRequest extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... params){
            try{
                personListResponse persons = new ServerProxy().getPersons(mFamilyModel.getToken());
                eventListResponse events = new ServerProxy().getEvents(mFamilyModel.getToken());
                if (persons != null && events != null) {
                    mFamilyModel.setEvents(events.getData());
                    mFamilyModel.setPersons(persons.getData());
                    mFamilyModel.setupFilters();
                    mFamilyModel.setupAncestors();
                    mFamilyModel.setupChildren();
                } else {
                    Toast.makeText(SettingsActivity.this, "Resync Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }catch(IOException ioe){
                Log.e(TAG, "Failed to fetch URL: ", ioe);
                Toast.makeText(SettingsActivity.this, R.string.register400, Toast.LENGTH_SHORT).show();
            }
            return null;
        }
    }

    private void configureLogout(){
        Button logout = findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this,
                        "Logging out...",
                        Toast.LENGTH_SHORT).show();

                mSettings.setMainLoadMapFragOnCreate(false);
                mFamilyModel.logout();
                finish();
            }
        });
    }

    private int colorToInt(int color){
        switch(color){
            case Color.BLACK :
                return 1;
            case Color.RED :
                return 2;
            case Color.GREEN :
                return 3;
            default : return 0; // BLUE
        }
    }

    private int mapToInt(int mapType){
        switch(mapType){
            case GoogleMap.MAP_TYPE_HYBRID :
                return 1;
            case GoogleMap.MAP_TYPE_SATELLITE:
                return 2;
            case GoogleMap.MAP_TYPE_TERRAIN:
                return 3;
            default : return 0; // BLUE
        }
    }

    //HELPER CLASSES FOLLOW

    private class life_story_listener implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int index = parent.getSelectedItemPosition();
            switch(index){
                case 0:
                    mSettings.setLifeStoryLinesColor(Color.BLUE);
                    break;
                case 1:
                    mSettings.setLifeStoryLinesColor(Color.BLACK);
                    break;
                case 2:
                    mSettings.setLifeStoryLinesColor(Color.RED);
                    break;
                case 3:
                    mSettings.setLifeStoryLinesColor(Color.GREEN);
                    break;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class family_tree_listener implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int index = parent.getSelectedItemPosition();
            switch(index){
                case 0:
                    mSettings.setFamilyTreeLinesColor(Color.BLUE);
                    break;
                case 1:
                    mSettings.setFamilyTreeLinesColor(Color.BLACK);
                    break;
                case 2:
                    mSettings.setFamilyTreeLinesColor(Color.RED);
                    break;
                case 3:
                    mSettings.setFamilyTreeLinesColor(Color.GREEN);
                    break;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class spouse_listener implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int index = parent.getSelectedItemPosition();
            switch(index){
                case 0:
                    mSettings.setSpouseLinesColor(Color.BLUE);
                    break;
                case 1:
                    mSettings.setSpouseLinesColor(Color.BLACK);
                    break;
                case 2:
                    mSettings.setSpouseLinesColor(Color.RED);
                    break;
                case 3:
                    mSettings.setSpouseLinesColor(Color.GREEN);
                    break;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private class map_listener implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int index = parent.getSelectedItemPosition();
            switch(index){
                case 0:
                    mSettings.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    break;
                case 1:
                    mSettings.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    break;
                case 2:
                    mSettings.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                    break;
                case 3:
                    mSettings.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                    break;
            }
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
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
}
