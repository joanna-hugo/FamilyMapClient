package felsted.joanna.fmc.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

import felsted.joanna.fmc.R;
import felsted.joanna.fmc.model.FamilyModel;
import felsted.joanna.fmc.model.Filters;

public class FilterActivity extends AppCompatActivity {
    private FamilyModel mFamilyModel;
    private Filters filters = Filters.getInstance();
    private Context context;

    private RecyclerView filterRecyclerView;
    private FilterAdapter mFilterAdapter;

    //TODO make layout pretty
    //TODO add all buttons and listeners
    //TODO add recycler view(?) to dynamically show event_types
        //TODO I should get the person activity running well then apply what I learn here


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        configureBackButton();

        filterRecyclerView = findViewById(R.id.filter_recycler);
        filterRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFilterAdapter = new FilterAdapter(filters.getEvent_type_filters());
        filterRecyclerView.setAdapter(mFilterAdapter);
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

    private class TypeFilterHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mTextView;
        private Switch switchButton;
        private Filters.Filter myFilter;

        private TypeFilterHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.filter_layout, parent, false));

            mTextView = itemView.findViewById(R.id.filter_text);

//            switchButton = new Switch(parent.getContext());
            switchButton = (Switch) findViewById(R.id.filters_button);

            switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        myFilter.setShow(true);
                    } else {
                        myFilter.setShow(false);
                    }
                }
            });

            itemView.setOnClickListener(this);
        }

        private void bind(Filters.Filter filter){
            myFilter = filter;
            mTextView.setText(myFilter.getEvent_type());
        }

        @Override
        public void onClick(View v){

        }
    }

    private class FilterAdapter extends RecyclerView.Adapter<TypeFilterHolder> {

        private List<Filters.Filter> myFilters;

        private FilterAdapter(List<Filters.Filter> filters) {
            myFilters = filters;
        }

        @Override
        public TypeFilterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

            return new TypeFilterHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TypeFilterHolder holder, int position) {
            Filters.Filter filter = myFilters.get(position);
            holder.bind(filter);
        }

        @Override
        public int getItemCount() {
            return myFilters.size();
        }
    }




}
