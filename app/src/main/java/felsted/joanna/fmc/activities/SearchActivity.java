package felsted.joanna.fmc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import felsted.joanna.fmc.R;
import felsted.joanna.fmc.model.FamilyModel;
import felsted.joanna.fmc.model.Settings;

public class SearchActivity extends AppCompatActivity {
    private FamilyModel mFamilyModel;
    private Settings mSettings = Settings.getInstance();

    //TODO make layout
    //TODO allow filtering
    //TODO search function
        //TODO search names (first and last)
        //TODO search countries,
        //TODO search cities,
        //TODO search event-types,
        //TODO search years

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        configureBackButton();
        configureEventButton();
        configurePersonButton();
    }

    private void configureBackButton(){
        Button searchButton = findViewById(R.id.back);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void configureEventButton(){
        Button searchButton = findViewById(R.id.searchToEvent);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EventActivity.class);
                intent.putExtra("SETTINGS", mSettings);
                intent.putExtra("FAMILY_MODEL", mFamilyModel);
                startActivity(intent);
            }
        });
    }

    private void configurePersonButton(){
        Button searchButton = findViewById(R.id.searchToPerson);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PersonActivity.class);
                intent.putExtra("SETTINGS", mSettings);
                intent.putExtra("FAMILY_MODEL", mFamilyModel);
                startActivity(intent);
            }
        });
    }

    public FamilyModel getFamilyModel() {
        return mFamilyModel;
    }

    public void setFamilyModel(FamilyModel familyModel) {
        mFamilyModel = familyModel;
    }
}
