package felsted.joanna.fmc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import felsted.joanna.fmc.R;

public class MapFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        configureSearchButton(v);
        configureFilterButton(v);
        configureSettingsButton(v);
        configurePersonButton(v);

        return v;
    }

    private void configureSearchButton(View v){
        Button searchButton = v.findViewById(R.id.toSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), SearchActivity.class));
            }
        });
    }

    private void configureFilterButton(View v){
        Button searchButton = v.findViewById(R.id.toFilter);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), FilterActivity.class));
            }
        });
    }

    private void configureSettingsButton(View v){
        Button searchButton = v.findViewById(R.id.toSettings);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), SettingsActivity.class));
            }
        });
    }

    private void configurePersonButton(View v){
        Button searchButton = v.findViewById(R.id.toPerson);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), PersonActivity.class));
            }
        });
    }








}
