package felsted.joanna.fmc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import felsted.joanna.fmc.R;
import felsted.joanna.fmc.model.FamilyModel;
import felsted.joanna.fmc.model.event;
import felsted.joanna.fmc.model.person;
import felsted.joanna.fmc.model.settings;

import static android.graphics.Color.BLUE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker;

public class MapFragment extends Fragment {

    private GoogleMap map;
    private TextView textView;
    private MapView mapView;
    private FamilyModel mFamilyModel;
    private settings mSettings = settings.getInstance();

    static final float WIDTH = 10;  // in pixels
    static final int color = BLUE;

    //TODO write bottom section of screen layout
    //TODO get information from clicking on markers to show up in bottom half of screen

    //TODO clicking on the icons in the menu takes the user to the settings, filters, or search activity
        //TODO the buttons are in the MENU
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        textView = view.findViewById(R.id.mapText);

        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
//                initMap();
                centerMap();
                zoomMap(10);
                setMapType();
                setClickListener();
                zoomMap(2);
                addMarkers();
                setBounds();
                setMarkerListener();
                drawLines();
            }
        });

        configureSearchButton(view);
        configureFilterButton(view);
        configureSettingsButton(view);
        setTextViewListener();
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    //TODO update map when returning from changing settings

    @Override
    public void onStart(){
        super.onStart(); //NOTE this is the function when returning from settings activity
//        initMap();
//        drawLines();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        setHasOptionsMenu(true);
        inflater.inflate(R.menu.fragment_map, menu); //TODO ...why aren't my buttons coming up?

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

    void initMap() {
        centerMap();
        zoomMap(10);
        setMapType();
        setClickListener();
        zoomMap(2);
        addMarkers();
        setBounds();
        setMarkerListener();
        drawLines();
    }

    public void centerMap() {
        LatLng byu = new LatLng(40.2518, -111.6493); //TODO why an error here? when moving Person --> Event
        CameraUpdate update = CameraUpdateFactory.newLatLng(byu);
        map.moveCamera(update);
        map.addMarker(new MarkerOptions().position(byu));
    }

    public void centerMap(event e) {
//        if(map == null){
////            View view = inflater.inflate(R.layout.fragment_map, container, false);
////
////            mapView = view.findViewById(R.id.map);
////            mapView.onCreate(savedInstanceState);
//
//            mapView.getMapAsync(new OnMapReadyCallback() {
//                @Override
//                public void onMapReady(GoogleMap googleMap) {
//                    map = googleMap;
//                    initMap();
//                }
//            });
//        }
        LatLng center_event = new LatLng(e.getLatitude(), e.getLongitude());
        CameraUpdate update = CameraUpdateFactory.newLatLng(center_event);
        map.moveCamera(update);
        map.addMarker(new MarkerOptions().position(center_event));
    }

    void zoomMap(float amount) {
        CameraUpdate update = CameraUpdateFactory.zoomTo(amount);
        map.moveCamera(update);
    }

    void setMapType() {
        map.setMapType(mSettings.getMapType());
    }

    void setClickListener() {
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                textView.setText(latLng.toString());
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
        for(event e :mFamilyModel.getEvents()){ //TODO why error Person --> Event
            addMarker(e.getCity(), new LatLng(e.getLatitude(), e.getLongitude()), e);
        }
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
                CameraUpdateFactory.newLatLngZoom(bounds.getCenter(), 5);
        map.moveCamera(update);
    }

    void setMarkerListener() {
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                event e = mFamilyModel.getEvent((String) marker.getTag());
                String loc = e.getCountry() + ", " + e.getCity();
                textView.setText(loc);
                textView.setTag(e.getPersonID());
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

        LatLng lastCity = null;
        for (event e : mFamilyModel.getEvents()) {
            LatLng latLng = getLatLng(e);
            if (lastCity != null)
                drawLine(lastCity, latLng, color, WIDTH);
            lastCity = latLng;
        }
//        for (String[] strings : locations) {
//            LatLng latLng = getLatLng(strings);
//            if (lastCity != null)
//                drawLine(lastCity, latLng);
//            lastCity = latLng;
//        }
    }

    void drawFamLines(event e, int color, float width){
//        person temp = mFamilyModel.getPerson(e.getPersonID());
        //TODO test with large family, do we need return statement, or will the if statements work?

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
        PolylineOptions options =
                new PolylineOptions().add(point1, point2)
                        .color(color).width(width);
        map.addPolyline(options);
    }

    private void startSettingsActivity(){
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
//        intent.putExtra("SettingsActivity", Util.getGson().toJson(settings)); TODO investigate if this is a good way to pass settings object between activities
        intent.putExtra("SETTINGS", mSettings);
        intent.putExtra("FAMILY_MODEL", mFamilyModel);
        startActivity(intent);
    }

    private void startSearchActivity(){
        Intent intent = new Intent(getActivity(), SearchActivity.class);
//        intent.putExtra("SettingsActivity", Util.getGson().toJson(settings)); TODO investigate if this is a good way to pass settings object between activities
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
//        intent.putExtra("SettingsActivity", Util.getGson().toJson(settings)); TODO investigate if this is a good way to pass settings object between activities
        intent.putExtra("SETTINGS", mSettings);
        intent.putExtra("FAMILY_MODEL", mFamilyModel);
        startActivity(intent);
    }


    //----------- Temporary Setup Functions

    private void configureSearchButton(View v){
        Button searchButton = v.findViewById(R.id.toSearch);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearchActivity();
            }
        });
    }

    private void configureFilterButton(View v){
        Button searchButton = v.findViewById(R.id.toFilter);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFilterActivity();
            }
        });
    }

    private void configureSettingsButton(View v){
        Button searchButton = v.findViewById(R.id.toSettings);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSettingsActivity();
            }
        });
    }

    public FamilyModel getFamilyModel() {
        return mFamilyModel;
    }

    public void setFamilyModel(FamilyModel familyModel) {
        mFamilyModel = familyModel;
    }
}
