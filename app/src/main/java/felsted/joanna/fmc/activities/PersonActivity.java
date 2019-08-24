package felsted.joanna.fmc.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.EventLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.bignerdranch.expandablerecyclerview.model.Parent;

import java.util.ArrayList;
import java.util.List;

import felsted.joanna.fmc.R;
import felsted.joanna.fmc.model.event;
import felsted.joanna.fmc.model.person;

public class PersonActivity extends AppCompatActivity {

    private List<event> mEvents = new ArrayList<event>();
    private person mPerson = new person();

    private RecyclerView mEventRecyclerView;
    private EventAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        configureBackButton();
        configureEventButton();

        setupMockData();

        mEventRecyclerView = (RecyclerView) findViewById(R.id.event_recycler_view);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateUI();
    }

    private void configureBackButton(){
        Button searchButton = findViewById(R.id.personBack);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void configureEventButton(){
        Button searchButton = findViewById(R.id.toEvent);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), EventActivity.class));
            }
        });
    }

    private void setupMockData(){
        mPerson = new person("12345", "redjojo", "Esther", "Everett",
                "f", "","","");
        event temp = new event("54321", "redjojo", "12345",
                12345d, 12345d, "Korea", "Seoul", "Visit", 2014);
        event temp2 = new event("12345", "redjojo", "12345",
                12345d, 12345d, "America", "Provo","Born", 1990);
        event temp3 = new event("33333", "lokaek", "55555",
                13579d, 13579d, "Argentina", "Capitol", "Mission", 1993);
        mEvents.add(temp);
        mEvents.add(temp2);
        mEvents.add(temp3);
    }


    private void updateUI() {
        List<event> crimes = mEvents;

        mAdapter = new EventAdapter(crimes);
        mEventRecyclerView.setAdapter(mAdapter);
    }



    private class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mType;
        private TextView mLoc;
        private TextView mYear;
        private TextView mName;

        private event myEvent;
        public EventHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_event, parent, false));

            mType =  itemView.findViewById(R.id.event_type);
            mLoc = itemView.findViewById(R.id.event_location);
//            mYear = itemView.findViewById(R.id.event_year);
            mName = itemView.findViewById(R.id.event_person);
            itemView.setOnClickListener(this);
        }

        public void bind(event e) {
            myEvent = e;
            mType.setText(myEvent.getEventType());
            String loc = myEvent.getCity() + ", " + myEvent.getCountry();
            mLoc.setText(loc);
//            mYear.setText(myEvent.getYear());
            mName.setText(myEvent.getPerson());
        }

        @Override
        public void onClick(View v){
            Toast.makeText(v.getContext(),
                    "you clicked a " + myEvent.getEventType() + " event!", Toast.LENGTH_SHORT).show(); //TODO this should launch an event activity
        }
    }

    private class EventAdapter extends RecyclerView.Adapter<EventHolder> {

        private List<event> myEvents;

        public EventAdapter(List<event> events) {
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


    //--------------
    // NOTE If you want to make the list expandable, follow this BigNerdRanch Tutorial
    // https://bignerdranch.github.io/expandable-recycler-view/
}
