package felsted.joanna.fmc.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import felsted.joanna.fmc.R;
import felsted.joanna.fmc.model.FamilyModel;
import felsted.joanna.fmc.model.Filters;
import felsted.joanna.fmc.model.Settings;
import felsted.joanna.fmc.model.event;
import felsted.joanna.fmc.model.person;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.RED;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private TextView textView;
    private ImageView genderImageView;
    private MapView mapView;
    private FamilyModel mFamilyModel = FamilyModel.getInstance();
    private List<event> mShownEvents = new ArrayList<>();
    private Settings mSettings = Settings.getInstance();
    private Filters mFilters = Filters.getInstance();


    static final float WIDTH = 10;  // in pixels
    static final int color = BLUE;

    List<Polyline> polylines = new ArrayList<Polyline>();

    //I should do ... up arrow goes to original activity, NOT a new activity
    //DONE update map when returning from other activities (change filters or settings)
    //DONE write bottom section of screen layout
    //DONE get information from clicking on markers to show up in bottom half of screen

    //DONE clicking on the icons in the menu takes the user to the Settings, Filters, or search activity
        //DONE the buttons are in the MENU
    //DONE filter by event-type
    //DONE filter by gender
    //DONE filter by maternal/paternal side

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);


        if(getActivity() instanceof MainActivity){
            setHasOptionsMenu(true);
        }

        textView = view.findViewById(R.id.mapText);
        genderImageView = view.findViewById(R.id.mapGender);

        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        setTextViewListener();

        Filters filters = Filters.getInstance();
        mShownEvents =  filters.filterEvents(mFamilyModel.getEvents());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.onResume();
        mapView.getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        setHasOptionsMenu(true);
        inflater.inflate(R.menu.fragment_map, menu);

        MenuItem searchItem = menu.findItem(R.id.search_button);
        searchItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                startSearchActivity();
                return true;
            }
        });

        MenuItem filterItem = menu.findItem(R.id.filter_button);
        filterItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item){
                startFilterActivity();
                return true;
            }
        });

        MenuItem settingsItem = menu.findItem(R.id.settings_button);
        settingsItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
           @Override
           public boolean onMenuItemClick(MenuItem settingsItem){
                startSettingsActivity();
                return true;
           }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        initMap();
        try {
            if (getActivity().getIntent() != null) {
                Intent i = getActivity().getIntent(); // NOTE THIS IS WHAT IS MOVING THE MAP
                //CENTER_EVENT_ID
                if (i.hasExtra("CENTER_EVENT_ID")) {
                    event e = mFamilyModel.getEvent(i.getStringExtra("CENTER_EVENT_ID"));
                    LatLng center = getLatLng(e);
                    CameraUpdate update = CameraUpdateFactory.newLatLng(getLatLng(mFamilyModel.getEvent(i.getStringExtra("CENTER_EVENT_ID"))));
                    map.animateCamera(update);

                    //setup textview below map
                    person p = mFamilyModel.getPerson(e.getPersonID());
                    String info = e.getEventType() + " : " +e.getCity() + ", " + e.getCountry() + " (" + e.getYear() + ")";
                    String name = p.getFirstName() + " " + p.getLastName();
                    String all = name + "\n" + info;
                    textView.setText(all);
                    textView.setTag(e.getPersonID());

                    if(p.getGender().startsWith("m") || p.getGender().startsWith("M")) {
                        Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).sizeDp(40);
                        genderImageView.setImageDrawable(genderIcon);
                        genderImageView.setColorFilter(BLUE);
                    }else{
                        Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).sizeDp(40);
                        genderImageView.setImageDrawable(genderIcon);
                        genderImageView.setColorFilter(RED);
                    }

                    drawLines(e);
                }
            }
        }catch(NullPointerException e){
            System.out.println("CAUGHT NULL POINTER EXCEPTION. EVERYTHING IS FINE");
        }

    }

    void initMap() {
        filterEvents();
        centerMap();
        zoomMap(1);
        setMapType();
        addMarkers();
        setBounds();
        setMarkerListener();
//        drawLines();
    }

    private void filterEvents(){
        Filters filters = Filters.getInstance();

        List<person> people = filters.filterPersons(mFamilyModel.getPersons());
        mShownEvents=filters.filterEvents(mFamilyModel.getEvents());
    }

    public void centerMap() {
        Intent i  = getActivity().getIntent();
        //CENTER_EVENT_ID
        if(i.hasExtra("CENTER_EVENT_ID")){
            CameraUpdate update = CameraUpdateFactory.newLatLng(getLatLng(mFamilyModel.getEvent(i.getStringExtra("CENTER_EVENT_ID"))));
            map.animateCamera(update);
        }else {
            LatLng center = mFamilyModel.averageLatLng();
            CameraUpdate update = CameraUpdateFactory.newLatLng(center);
            map.moveCamera(update);
        }
    }

    void zoomMap(float amount) {
        CameraUpdate update = CameraUpdateFactory.zoomTo(amount);
        map.moveCamera(update);
    }

    void setMapType() {
        switch(mSettings.getMapType()){
            case 2 :
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case 3 :
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case 4 :
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            default : map.setMapType(GoogleMap.MAP_TYPE_NORMAL); // will get here if case 1
        }
    }

    void setTextViewListener(){
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String person_id = (String)textView.getTag();
                startPersonActivity(person_id);
            }
        });
    }

    LatLng getLatLng(event e) {
        return new LatLng(e.getLatitude(), e.getLongitude());
    }

    void addMarkers() {
        mFamilyModel.setupColors();
        for(event e : mShownEvents){
            if(mFilters.showEvent(e) && mFilters.showPerson(mFamilyModel.getPerson(e.getPersonID()))) {
                addMarker(e.getCity(), new LatLng(e.getLatitude(), e.getLongitude()), e);
            }
        }
    }

    void addMarker(String city, LatLng latLng, event e) {
        //not already filtered event and person, we need to show this marker
        person p = mFamilyModel.getPerson(e.getPersonID());
        MarkerOptions options ;
            options =
                    new MarkerOptions().position(latLng).title(city)
                            .icon(defaultMarker(mFamilyModel.getColor(e.getEventType())));
        Marker marker = map.addMarker(options);
        marker.setTag(e.getEventID());
    }

    void setBounds() {
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (event e : mShownEvents) {
            builder.include(getLatLng(e));
        }
        if(mShownEvents.size() == 0){
            LatLng temp = new LatLng(40.7500d, -110.1167d);
            builder.include(temp);
        }
        LatLngBounds bounds = builder.build();
        CameraUpdate update =
                CameraUpdateFactory.newLatLngZoom(bounds.getCenter(), 1);
        map.moveCamera(update);

    }

    void setMarkerListener() {
        //NOTE this is where I set the textView text
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                event e = mFamilyModel.getEvent((String) marker.getTag());
                person p = mFamilyModel.getPerson(e.getPersonID());
                String info = e.getEventType() + " : " +e.getCity() + ", " + e.getCountry() + " (" + e.getYear() + ")";
                String name = p.getFirstName() + " " + p.getLastName();
                String all = name + "\n" + info;
                textView.setText(all);
                textView.setTag(e.getPersonID());


                if(p.getGender().startsWith("m") || p.getGender().startsWith("M")) {
                    Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).sizeDp(40);
                    genderImageView.setImageDrawable(genderIcon);
                    genderImageView.setColorFilter(BLUE);
                }else{
                    Drawable genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).sizeDp(40);
                    genderImageView.setImageDrawable(genderIcon);
                    genderImageView.setColorFilter(RED);
                }

                map.clear();
                addMarkers();

                drawLines(e);
                return false;
            }
        });
    }

    void drawLines(event e) {
        /*
                    spouse lines
                        selected event to birth event of spouse (or earliest event)
                    family tree lines
                        selected event and birth (or earliest event) of father (if no father or no father events, no line
                        selected event and birth (or earliest event) of mother (if no father or no father events, no line
                        these lines are drawn recursively up through the generations with thinner and thinner lines
                    life story lines
                        connecting each event in a persons story, ordered chronologically
                            if some events not visible (bc of filtering) leave them out
                 */
        if(mSettings.isShowFamilyTreeLines()){ //TODO family tree lines not checking for gender
            drawFamLines(e, mSettings.getFamilyTreeLinesColor(), WIDTH);
        }
        if(mSettings.isShowSpouseLines()){
            event spouse = mFamilyModel.getSpousesBirth(e.getPersonID());
            if(spouse != null && mFilters.showEvent(e) && mFilters.showEvent(spouse)) {
                drawLine(getLatLng(e), getLatLng(spouse), mSettings.getSpouseLinesColor(), WIDTH);
            }
        }
        if(mSettings.isShowLifeStoryLines()){ //TODO show lifeSToryLines
            List<event> lifeEvents = mFamilyModel.getPersonsEvents(e.getPersonID());
            lifeEvents = mFilters.filterEvents(lifeEvents);
            Collections.sort(lifeEvents);

            if(lifeEvents.size() != 0){
                event first = lifeEvents.get(0);

                for(event secondEvent : lifeEvents){
                    if(secondEvent != null && mFilters.showEvent(first) && mFilters.showEvent(secondEvent)) {
                        drawLine(getLatLng(first), getLatLng(secondEvent), mSettings.getLifeStoryLinesColor(), WIDTH);
                    }
                    first = secondEvent;
                }
            }

        }
    }

    void drawFamLines(event e, int color, float width){
        float given_width =  width;

        //DONE lines get progressively thinner
        event dad= mFamilyModel.getFathersBirth(e.getPersonID());

        //draw line to dad's birth
        if(dad != null && mFilters.getShowFathersSide()){
            if(width > 3){
                width = width -3;
            }
            if(mFilters.showEvent(e) && mFilters.showEvent(dad)) {
                drawLine(getLatLng(e), getLatLng(dad), color, width);
            }
            drawFamLines(dad, color, width);
        }

        event mom= mFamilyModel.getMothersBirth(e.getPersonID());
        width = given_width;
        if(mom != null && mFilters.getShowMothersSide()){
            if(width > 3){
                width = width -3;
            }
            if(mFilters.showEvent(e) && mFilters.showEvent(mom)) {
                drawLine(getLatLng(e), getLatLng(mom), color, width);
            }
            drawFamLines(mom, color, width);
        }

    }

    void drawLine(LatLng point1, LatLng point2, int color, float width){
        map.addPolyline(new PolylineOptions().add(point1, point2).width(width).geodesic(true).color(color));
        polylines.add(map.addPolyline(new PolylineOptions().add(point1, point2).width(width).geodesic(true).color(color)));
    }

    private void startSettingsActivity(){
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        intent.putExtra("SETTINGS", mSettings);
        intent.putExtra("FAMILY_MODEL", mFamilyModel);
        startActivity(intent);
    }

    private void startSearchActivity(){
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        intent.putExtra("SETTINGS", mSettings);
        intent.putExtra("FAMILY_MODEL", mFamilyModel);
        startActivity(intent);
    }

    private void startPersonActivity(String personID){
        Intent intent = new Intent(getActivity(), PersonActivity.class);
        intent.putExtra("SETTINGS", mSettings);
        intent.putExtra("FAMILY_MODEL", mFamilyModel);
        intent.putExtra("PERSON_ID", personID);

        startActivity(intent);
    }

    private void startFilterActivity(){
        Intent intent = new Intent(getActivity(), FilterActivity.class);
        intent.putExtra("SETTINGS", mSettings);
        intent.putExtra("FAMILY_MODEL", mFamilyModel);
        startActivity(intent);
    }


    //----------- Temporary Setup Functions
    public FamilyModel getFamilyModel() {
        return mFamilyModel;
    }

    public void setFamilyModel(FamilyModel familyModel) {
        mFamilyModel = familyModel;
    }
}
