package felsted.joanna.fmc.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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


    //TODO make layout pretty
    //TODO add all buttons and listeners
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
    }


// -----------------------------------------------------------------------------------------------------

    private class FilterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView data;
        private Switch showSwitch;

        private FilterViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.filter_layout, parent, false));

            data = itemView.findViewById(R.id.filter_text);
            showSwitch = itemView.findViewById(R.id.filters_button);
            showSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                     mFilters.setEvent_type_filters(data.getText().toString(), isChecked);
                }
            });

        }

        private void bind(String given ){
            StringBuilder sb = new StringBuilder(given + " Events \n FILTER BY " + given.toUpperCase() + " EVENTS");
            data.setText(sb.toString());
            showSwitch.setChecked(mFilters.getMappedFilter(given));
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
