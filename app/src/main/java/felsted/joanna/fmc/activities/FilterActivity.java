package felsted.joanna.fmc.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import felsted.joanna.fmc.R;
import felsted.joanna.fmc.model.FamilyModel;
import felsted.joanna.fmc.model.Filters;

public class FilterActivity extends AppCompatActivity {
    private FamilyModel mFamilyModel;
    private Filters filters = Filters.getInstance();

    //TODO make layout pretty
    //TODO add all buttons and listeners
    //TODO add recycler view(?) to dynamically show event_types
        //TODO I should get the person activity running well then apply what I learn here


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        configureBackButton();
    }

    private void configureBackButton(){
        Button searchButton = findViewById(R.id.backButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
