package felsted.joanna.fmc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

public class SearchActivity extends AppCompatActivity {
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
                startActivity(new Intent(v.getContext(), EventActivity.class));
            }
        });
    }

    private void configurePersonButton(){
        Button searchButton = findViewById(R.id.searchToPerson);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), PersonActivity.class));
            }
        });
    }

}
