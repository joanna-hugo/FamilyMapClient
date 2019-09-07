package felsted.joanna.fmc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import felsted.joanna.fmc.R;
import felsted.joanna.fmc.model.FamilyModel;
import felsted.joanna.fmc.model.Settings;
import felsted.joanna.fmc.model.event;
import felsted.joanna.fmc.model.person;

public class PersonActivity extends AppCompatActivity {

    private FamilyModel mFamilyModel;
    private Settings mSettings = Settings.getInstance();
    private List<event> mEvents = new ArrayList<>();
    private person mPerson = new person();

    private RecyclerView mEventRecyclerView;
    private EventAdapter mEventAdapter;

    private RecyclerView mFamilyRecyclerView;
    private PersonAdapter mPersonAdapter;

    //TODO pretty layout
        //TODO lists are EXPANDABLE
    //DONE order events chronologically
    //DONE list family
    //TODO set gender specific icons
    //DONE trigger person activity by clicking on person (person activity for THAT person)
    //TODO implement filtering

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        Intent i = getIntent();
        String personID = i.getStringExtra("PERSON_ID");
        setFamilyModel((FamilyModel) i.getSerializableExtra("FAMILY_MODEL"));

        setupData(personID);
        mFamilyModel.setupChildren();

        mEventRecyclerView = (RecyclerView) findViewById(R.id.event_recycler);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        mFamilyRecyclerView = (RecyclerView) findViewById(R.id.person_recycler);
        mFamilyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateUI();
    }

    private void setupData(String personID){
        mPerson = mFamilyModel.getPerson(personID);
        mEvents = mFamilyModel.getPersonsEventsOrdered(personID);
    }

    private void updateUI() {
        ((TextView) findViewById(R.id.first_name)).setText(mPerson.getFirstName());
        ((TextView) findViewById(R.id.last_name)).setText(mPerson.getLastName());
        if(mPerson.getGender().startsWith("f")|| mPerson.getGender().startsWith("F")) {
            ((TextView) findViewById(R.id.person_gender)).setText("Female");
        }else{
            ((TextView) findViewById(R.id.person_gender)).setText("Male");
        }

        mEventAdapter = new EventAdapter(mEvents);
        mEventRecyclerView.setAdapter(mEventAdapter);

        List<person> family = mFamilyModel.getImmediateFam(mPerson.getPersonID());
        mPersonAdapter = new PersonAdapter(family);
        mFamilyRecyclerView.setAdapter(mPersonAdapter);
    }

    public FamilyModel getFamilyModel() {
        return mFamilyModel;
    }

    public void setFamilyModel(FamilyModel familyModel) {
        mFamilyModel = familyModel;
    }

    private class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView data;
        private event myEvent;

        private EventHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_event, parent, false));

            data = itemView.findViewById(R.id.event_info);

            itemView.setOnClickListener(this);

        }

        private void bind(event e) {
            myEvent = e;
            person p = mFamilyModel.getPerson(myEvent.getPersonID());

            String info = myEvent.getEventType() + " : " +myEvent.getCity() + ", " + myEvent.getCountry() + " (" + myEvent.getYear() + ")";
            String name = p.getFirstName() + " " + p.getLastName();
            String all = info  + "\n" + name;
            data.setText(all);

//            Drawable marker = new IconDrawable(this, FontAwesomeIcons.fa_map_marker).sizeDp(15);
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

    private class PersonHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        private TextView data;
        private person myPerson;

        private PersonHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_event, parent, false));

            data = itemView.findViewById(R.id.event_info);

            itemView.setOnClickListener(this);

        }

        private void bind(person p) {
            myPerson = p;
            String all = myPerson.getFirstName() + " " + myPerson.getLastName();
            if(mPerson.getMother().equals(myPerson.getPersonID())){
                all += "\nMother";
            }
            else if(mPerson.getFather().equals(myPerson.getPersonID())){
                all += "\nFather";
            }
            else if(mPerson.getSpouse().equals(myPerson.getPersonID())){
                all += "\nSpouse";
            }
            else{
                all += "\nChild";
            }
            data.setText(all);
        }

        @Override
        public void onClick(View v){
//            Toast.makeText(v.getContext(),
//                    "you clicked a " + myEvent.getEventType() + " event!", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(v.getContext(), PersonActivity.class);
            i.putExtra("SETTINGS", mSettings);
            i.putExtra("PERSON_ID", myPerson.getPersonID());
            i.putExtra("FAMILY_MODEL", mFamilyModel);
            startActivity(new Intent(i));
        }
    }

    private class PersonAdapter extends RecyclerView.Adapter<PersonHolder>{
        private List<person> mFamily;

        private PersonAdapter(List<person> family){
            mFamily = family;
        }

        @Override
        public PersonHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

            return new PersonHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(PersonHolder holder, int position) {
            person p = mFamily.get(position);
            holder.bind(p);
        }

        @Override
        public int getItemCount() {
            return mFamily.size();
        }
    }


//--------------
    // NOTE If you want to make the list expandable, follow this BigNerdRanch Tutorial
    // https://bignerdranch.github.io/expandable-recycler-view/
}
