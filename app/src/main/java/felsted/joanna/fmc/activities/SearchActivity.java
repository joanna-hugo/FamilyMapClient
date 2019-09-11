package felsted.joanna.fmc.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.List;

import felsted.joanna.fmc.R;
import felsted.joanna.fmc.model.FamilyModel;
import felsted.joanna.fmc.model.Settings;
import felsted.joanna.fmc.model.event;
import felsted.joanna.fmc.model.person;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.RED;

public class SearchActivity extends AppCompatActivity {
    private FamilyModel mFamilyModel = FamilyModel.getInstance();
    private Settings mSettings = Settings.getInstance();
    private EditText searchBar;
    private List<event> mEvents;
    private List<person> mPersons;

    private RecyclerView mEventRecyclerView;
    private EventAdapter mEventAdapter;

    private RecyclerView mPersonRecyclerView;
    private PersonAdapter mPersonAdapter;



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
        mEventRecyclerView.setAdapter(mEventAdapter);

        mPersonRecyclerView = findViewById(R.id.search_person_recycler_view);
        mPersonRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPersonRecyclerView.setAdapter(mPersonAdapter);

        setupSearchText();
    }

    public void setupSearchText(){
        searchBar = findViewById(R.id.search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String search = s.toString();
                mEvents = mFamilyModel.searchEvents(search);
                mPersons = mFamilyModel.searchPersons(search);

                mEventAdapter = new EventAdapter(mEvents);
                mEventRecyclerView.setAdapter(mEventAdapter);

                mPersonAdapter = new PersonAdapter(mPersons);
                mPersonRecyclerView.setAdapter(mPersonAdapter);

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    private class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mType;
        private event myEvent;
        private ImageView markerImageView;
        private EventHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.search_item, parent, false));

            mType =  itemView.findViewById(R.id.search_event_info);
            markerImageView = itemView.findViewById(R.id.search_image);
        }

        private void bind(event e) {
            myEvent = e;
            person p = mFamilyModel.getPerson(myEvent.getPersonID());

            String info = myEvent.getEventType() + " : " +myEvent.getCity() + ", " + myEvent.getCountry() + " (" + myEvent.getYear() + ")";
            String name = p.getFirstName() + " " + p.getLastName();
            String all = info  + "\n" + name;
            mType.setText(all);

            Drawable markerIcon = new IconDrawable(SearchActivity.this, FontAwesomeIcons.fa_map_marker).sizeDp(40);
            markerImageView.setImageDrawable(markerIcon);
        }

        @Override
        public void onClick(View v){
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
        private ImageView genderImageView;

        private PersonHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.search_item, parent, false));

            data = itemView.findViewById(R.id.search_event_info);
            data.setPadding(10,10,10,10);
            genderImageView = itemView.findViewById(R.id.search_image);

            itemView.setOnClickListener(this);

        }

        private void bind(person p) {
            myPerson = p;
            String all = myPerson.getFirstName() + " " + myPerson.getLastName();
            data.setText(all);

            if(p.getGender().startsWith("m") || p.getGender().startsWith("M")) {
                Drawable genderIcon = new IconDrawable(SearchActivity.this, FontAwesomeIcons.fa_male).sizeDp(40);
                genderImageView.setImageDrawable(genderIcon);
                genderImageView.setColorFilter(BLUE);
            }else{
                Drawable genderIcon = new IconDrawable(SearchActivity.this, FontAwesomeIcons.fa_female).sizeDp(40);
                genderImageView.setImageDrawable(genderIcon);
                genderImageView.setColorFilter(RED);
            }
        }

        @Override
        public void onClick(View v){
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

    public FamilyModel getFamilyModel() {
        return mFamilyModel;
    }

    public void setFamilyModel(FamilyModel familyModel) {
        mFamilyModel = familyModel;
    }
}
