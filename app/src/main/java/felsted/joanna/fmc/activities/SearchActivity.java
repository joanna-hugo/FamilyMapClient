package felsted.joanna.fmc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import felsted.joanna.fmc.R;
import felsted.joanna.fmc.model.FamilyModel;
import felsted.joanna.fmc.model.Settings;
import felsted.joanna.fmc.model.event;
import felsted.joanna.fmc.model.person;

public class SearchActivity extends AppCompatActivity {
    private FamilyModel mFamilyModel;
    private Settings mSettings = Settings.getInstance();
    private EditText searchBar;
    private List<event> mEvents;
    private List<person> mPersons;

    private RecyclerView mEventRecyclerView;
    private EventAdapter mAdapter;

    //TODO make layout
    //TODO allow filtering
    //TODO search function
        // use FamilyModel searches

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mEventRecyclerView = (RecyclerView) findViewById(R.id.search_event_recycler_view);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mEventRecyclerView.setAdapter(mAdapter);

    }

    public void setupSearchText(){
        searchBar = findViewById(R.id.search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEvents = mFamilyModel.searchEvents(s.toString());

                mAdapter = new EventAdapter(mEvents);
                mEventRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mType;
//        private TextView mLoc;
//        private TextView mYear;
//        private TextView mName;

        private event myEvent;
        private EventHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_event, parent, false));

            mType =  itemView.findViewById(R.id.event_type);
//            mLoc = itemView.findViewById(R.id.event_location);
//            mYear = itemView.findViewById(R.id.event_year);
//            mName = itemView.findViewById(R.id.event_person);
//            itemView.setOnClickListener(this);
        }

        private void bind(event e) {
            myEvent = e;
            mType.setText(myEvent.getEventType());
            String loc = myEvent.getCity() + ", " + myEvent.getCountry();
//            mLoc.setText(loc);
//            mYear.setText(myEvent.getYear());
//            mName.setText(myEvent.getPerson());
        }

        @Override
        public void onClick(View v){
            Toast.makeText(v.getContext(),
                    "you clicked a " + myEvent.getEventType() + " event!", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(v.getContext(), EventActivity.class);
            i.putExtra("SETTINGS", mSettings);
            i.putExtra("CENTER_EVENT_ID", myEvent.getEventID());
            i.putExtra("FAMILY_MODEL", mFamilyModel);
            startActivity(new Intent(i));
        }
    }

    private class EventAdapter extends RecyclerView.Adapter<EventHolder> {

        private List<event> myEvents;

        private EventAdapter(List<event> events) {
            myEvents = events;
        }

        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

            return new EventHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(EventHolder holder, int position) {
            event event = myEvents.get(position);
            holder.bind(event);
        }

        @Override
        public int getItemCount() {
            return mEvents.size();
        }
    }

    public FamilyModel getFamilyModel() {
        return mFamilyModel;
    }

    public void setFamilyModel(FamilyModel familyModel) {
        mFamilyModel = familyModel;
    }
}
