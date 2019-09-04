package felsted.joanna.fmc.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import felsted.joanna.fmc.R;
import felsted.joanna.fmc.model.FamilyModel;

public class FilterActivity extends AppCompatActivity {
    private FamilyModel mFamilyModel;

    //TODO make layout pretty
    //TODO filter by event-type
    //TODO filter by gender
    //TODO filter by side

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
