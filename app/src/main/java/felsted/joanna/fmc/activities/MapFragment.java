package felsted.joanna.fmc.activities;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import felsted.joanna.fmc.R;
import felsted.joanna.fmc.model.FamilyModel;
import felsted.joanna.fmc.model.Filters;
import felsted.joanna.fmc.model.Settings;
import felsted.joanna.fmc.model.event;
import felsted.joanna.fmc.model.person;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.RED;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap map;
    private TextView textView;
    private ImageView genderImageView;
    private MapView mapView;
    private FamilyModel mFamilyModel = FamilyModel.getInstance();
    private Settings mSettings = Settings.getInstance();
    private Filters mFilters = Filters.getInstance();

    static final float WIDTH = 10;  // in pixels
    static final int color = BLUE;


    //TODO up arrow goes to original activity, NOT a new activity
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

        return view;
    }

    // NOTE
    // https://stackoverflow.com/questions/35496493/getmapasync-in-fragment
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);//when you already implement OnMapReadyCallback in your fragment
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
        inflater.inflate(R.menu.fragment_map, menu); //TODO ...why aren't my buttons coming up?

        //NOTE https://www.concretepage.com/android/android-options-menu-example-using-getmenuinflater-inflate-oncreateoptionsmenu-and-onoptionsitemselected
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
        Intent i  = getActivity().getIntent(); // NOTE THIS IS WHAT IS MOVING THE MAP
            //CENTER_EVENT_ID
            if(i.hasExtra("CENTER_EVENT_ID")){
                event e = mFamilyModel.getEvent(i.getStringExtra("CENTER_EVENT_ID"));
                LatLng center = getLatLng(e);
                CameraUpdate update = CameraUpdateFactory.newLatLng(getLatLng(mFamilyModel.getEvent(i.getStringExtra("CENTER_EVENT_ID"))));
                map.animateCamera(update);
//                map.addMarker(new MarkerOptions().position(center));
            }

    }

    void initMap() {
        centerMap();
        zoomMap(1);
        setMapType();
        setClickListener();
        addMarkers();
        setBounds();
        setMarkerListener();
        drawLines();
    }

    public void centerMap() {
        Intent i  = getActivity().getIntent();
        //CENTER_EVENT_ID
        if(i.hasExtra("CENTER_EVENT_ID")){
            event e = mFamilyModel.getEvent(i.getStringExtra("CENTER_EVENT_ID"));
            LatLng center = getLatLng(e);
            CameraUpdate update = CameraUpdateFactory.newLatLng(getLatLng(mFamilyModel.getEvent(i.getStringExtra("CENTER_EVENT_ID"))));
            map.animateCamera(update);
            map.addMarker(new MarkerOptions().position(center));
        }else {
            LatLng center = mFamilyModel.averageLatLng();
            CameraUpdate update = CameraUpdateFactory.newLatLng(center);
            map.moveCamera(update);
            map.addMarker(new MarkerOptions().position(center));
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

    void setClickListener() {
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
//                textView.setText(latLng.toString());
            }
        });
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
        for(event e :mFamilyModel.getEvents()){
            if(checkFilters(e)) {
                addMarker(e.getCity(), new LatLng(e.getLatitude(), e.getLongitude()), e);
            }
        }
    }

    Boolean checkFilters(event e){
        //filter by event-type
        if(!mFilters.getEventFilter(e.getEventType())){
            return false;
        }

        //filter by gender
        String person_id = e.getPersonID();
        String gender = mFamilyModel.getPerson(person_id).getGender();
        if(gender.equals("m") & !mFilters.getShowMale()){
            return false;
        }
        if(gender.equals("f") & !mFilters.getShowFemale()){
            return false;
        }

        //filter by maternal/paternal side
        if(e.getPersonID().equals(mFamilyModel.getCurrentUser())){
            return true; //if the event is for the root user, it won't be in maternal/paternal lists
        }
        if(!mFilters.getShowMale() && mFamilyModel.isPaternal(e.getPersonID())){
            return false;
        }
        if(!mFilters.getShowFemale() && mFamilyModel.isMaternal(e.getPersonID())){
            return false;
        }
        return true;
    }

    void addMarker(String city, LatLng latLng, event e) {
        MarkerOptions options =
                new MarkerOptions().position(latLng).title(city)
                        .icon(defaultMarker(HUE_BLUE));
        Marker marker = map.addMarker(options);
        marker.setTag(e.getEventID());
    }

    void setBounds() {
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (event e : mFamilyModel.getEvents()) {
            builder.include(getLatLng(e));
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
                return false;
            }
        });
    }

    void drawLines() {
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
        if(mSettings.isShowLifeStoryLines()){
            for (event e1: mFamilyModel.getEvents()){
                drawFamLines(e1, mSettings.getFamilyTreeLinesColor(), WIDTH);
            }
        }
        if(mSettings.isShowSpouseLines()){
            for(event e: mFamilyModel.getEvents()){
                event spouse = mFamilyModel.getSpousesBirth(e.getPersonID());
                if(spouse != null) {
                    drawLine(getLatLng(e), getLatLng(spouse), mSettings.getSpouseLinesColor(), WIDTH);
                }

            }
        }
        if(mSettings.isShowLifeStoryLines()){
            for(event e: mFamilyModel.getEvents()){
                for(event e2 :mFamilyModel.getPersonsEvents(e.getPersonID())){
                    if(e2 != null) {
                        drawLine(getLatLng(e), getLatLng(e2), mSettings.getLifeStoryLinesColor(), WIDTH);
                    }
                }
            }
        }
    }

    void drawFamLines(event e, int color, float width){

        //TODO lines get progessively thinner
        event dad= mFamilyModel.getFathersBirth(e.getPersonID());
        if(dad != null){
            drawLine(getLatLng(e), getLatLng(dad), color, width);
            drawFamLines(dad, color, width);
        }

        event mom= mFamilyModel.getMothersBirth(e.getPersonID());
        if(mom != null){
            drawLine(getLatLng(e), getLatLng(mom), color, width);
            drawFamLines(mom, color, width);
        }

    }

    void drawLine(LatLng point1, LatLng point2, int color, float width) {
        map.addPolyline(new PolylineOptions().add(point1, point2).width(12).geodesic(true).color(color));
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
