package felsted.joanna.fmc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import felsted.joanna.fmc.R;
import felsted.joanna.fmc.model.FamilyModel;
import felsted.joanna.fmc.model.event;
import felsted.joanna.fmc.model.person;
import felsted.joanna.fmc.model.settings;

public class PersonActivity extends AppCompatActivity {

    private FamilyModel mFamilyModel;
    private settings mSettings = settings.getInstance();
    private List<event> mEvents = new ArrayList<event>();
    private person mPerson = new person();

    private RecyclerView mEventRecyclerView;
    private EventAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        Intent i = getIntent();
        String personID = i.getStringExtra("PERSON_ID");
        setFamilyModel((FamilyModel) i.getSerializableExtra("FAMILY_MODEL"));

        setupData(personID);
        //((TextView)findViewById(R.id.textPersonFirstName)).setText(person.getFirstName());
        //
//        TextView first_name = view.findViewById()

        configureEventButton();

        mEventRecyclerView = (RecyclerView) findViewById(R.id.event_recycler_view);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateUI();
    }

//    protected void onCreateView()

    private void configureEventButton(){
        Button searchButton = findViewById(R.id.toEvent);
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

    private void setupData(String personID){
        mPerson = mFamilyModel.getPerson(personID);
        mEvents = mFamilyModel.getPersonsEvents(personID);
    }

    private void updateUI() {
        ((TextView) findViewById(R.id.first_name)).setText(mPerson.getFirstName());
        ((TextView) findViewById(R.id.last_name)).setText(mPerson.getLastName());
        ((TextView) findViewById(R.id.person_gender)).setText(mPerson.getGender());

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
        private EventHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_event, parent, false));

            mType =  itemView.findViewById(R.id.event_type);
            mLoc = itemView.findViewById(R.id.event_location);
//            mYear = itemView.findViewById(R.id.event_year);
            mName = itemView.findViewById(R.id.event_person);
            itemView.setOnClickListener(this);
        }

        private void bind(event e) {
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
                    "you clicked a " + myEvent.getEventType() + " event!", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(v.getContext(), EventActivity.class);
            i.putExtra("SETTINGS", mSettings);
            i.putExtra("center_event", myEvent.getEventID());
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
//--------------
    // NOTE If you want to make the list expandable, follow this BigNerdRanch Tutorial
    // https://bignerdranch.github.io/expandable-recycler-view/
}
