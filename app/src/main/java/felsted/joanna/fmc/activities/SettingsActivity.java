package felsted.joanna.fmc.activities;

import android.content.Intent;
import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.List;

import felsted.joanna.fmc.R;
import felsted.joanna.fmc.model.FamilyModel;
import felsted.joanna.fmc.model.Settings;

public class SettingsActivity extends AppCompatActivity  {

    //DONE Life Story line color
    //DONE Family Tree line color
    //DONE Spouse line color
    //DONE Map Type
    //DONE Re-sync data
    //DONE Logout
    //DONE make layout pretty
    //TODO hook up Re-sync to server
    //TODO make map reload when going back to map (in case Settings change)

    private Settings mSettings = Settings.getInstance();
    private String TAG = "SETTINGS";

    private FamilyModel mFamilyModel;

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
        button.setChecked(true);
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
        button.setChecked(true);
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
        button.setChecked(true);
        button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSettings.setShowLifeStoryLines(true);
                } else {
                    mSettings.setShowLifeStoryLines(false);
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
        life_story_spinner.setSelection(0);
    }

    private void configureReSync(){
        Button sync = findViewById(R.id.resync_button);
        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this,
                        "Button worked but sync not hooked up",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void configureLogout(){
        Button logout = findViewById(R.id.logout_button);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO LOGOUT
                Toast.makeText(SettingsActivity.this,
                        "Button worked but logout not hooked up",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("FAMILY_MODEL", mFamilyModel);
                startActivity(intent);
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
