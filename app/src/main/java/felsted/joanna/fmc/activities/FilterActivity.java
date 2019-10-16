package felsted.joanna.fmc.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.List;

import felsted.joanna.fmc.R;
import felsted.joanna.fmc.model.Filters;
import felsted.joanna.fmc.model.event;
import felsted.joanna.fmc.model.person;

public class FilterActivity extends AppCompatActivity {
//    private FamilyModel mFamilyModel;
    private Filters mFilters = Filters.getInstance();
    private Context context;

    RecyclerView filterRecyclerView;
    FilterAdapter mFilterAdapter;


    //DONE make layout pretty
    //DONE add all buttons and listeners
    //DONE add recycler view(?) to dynamically show event_types
        //DONE I should get the person activity running well then apply what I learn here


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        filterRecyclerView = findViewById(R.id.filter_recycler);
        filterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFilterAdapter = new FilterAdapter(mFilters.getEvent_type_filters_names());
        filterRecyclerView.setAdapter(mFilterAdapter);

        addListeners();
        setFilters();

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            // Respond to the action bar's Up/Home button
//            case android.R.id.home:
//                NavUtils.navigateUpFromSameTask(this);
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    void addListeners(){
        Switch temp = findViewById(R.id.show_father_switch);
        temp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mFilters.setShowFathersSide( isChecked);
                System.out.println("FILTERS: CLICKED SHOW FATHERS SIDE TO " + isChecked);
            }
        });

        temp=findViewById(R.id.show_mother_switch);
        temp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mFilters.setShowMothersSide( isChecked);
                System.out.println("FILTERS: CLICKED SHOW MOTHERS SIDE TO " + isChecked);
            }
        });

        temp=findViewById(R.id.show_male_switch);
        temp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mFilters.setShowMale( isChecked);
                System.out.println("FILTERS: CLICKED SHOW MALE SIDE TO " + isChecked);
            }
        });

        temp=findViewById(R.id.show_female_switch);
        temp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mFilters.setShowFemale(isChecked);
                System.out.println("FILTERS: CLICKED SHOW FEMALE SIDE TO " + isChecked);
            }
        });
    }

    void setFilters(){
        Switch temp = findViewById(R.id.show_father_switch);
        temp.setChecked(mFilters.getShowFathersSide());

        temp=findViewById(R.id.show_mother_switch);
        temp.setChecked(mFilters.getShowMothersSide());

        temp=findViewById(R.id.show_male_switch);
        temp.setChecked(mFilters.getShowMale());

        temp=findViewById(R.id.show_female_switch);
        temp.setChecked(mFilters.getShowFemale());
    }

// -----------------------------------------------------------------------------------------------------

    private class FilterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView data;
        private Switch showSwitch;
        private String event_type;

        private FilterViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.filter_layout, parent, false));

            data = itemView.findViewById(R.id.filter_text);
            showSwitch = itemView.findViewById(R.id.filters_button);
        }

        private void bind(final String given){
            StringBuilder sb = new StringBuilder(given + " Events \n FILTER BY " + given.toUpperCase() + " EVENTS");
            data.setText(sb.toString());
            showSwitch.setChecked(mFilters.getMappedFilter(given));

            showSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mFilters.setEvent_type_filters(given, isChecked);
                    System.out.println("FILTERS: CLICKED " + data.getText() + " to " + isChecked);
                }
            });
        }

        @Override
        public void onClick(View v){
            Toast.makeText(v.getContext(),
                    "you clicked a " + data + " filter", Toast.LENGTH_SHORT).show();
        }
    }

    private class FilterAdapter extends RecyclerView.Adapter<FilterViewHolder> {

        private List<String> myFilters;

        private FilterAdapter(List<String> filters) {
            myFilters = filters;
        }

        @Override
        public FilterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

            return new FilterViewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(FilterViewHolder holder, int position) {

            holder.bind(myFilters.get(position));
        }

        @Override
        public int getItemCount() {
            return myFilters.size();
        }
    }





}
