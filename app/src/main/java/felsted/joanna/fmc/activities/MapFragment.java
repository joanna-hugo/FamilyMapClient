package felsted.joanna.fmc.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import felsted.joanna.fmc.R;

import static android.graphics.Color.BLUE;
import static com.google.android.gms.maps.GoogleMap.MAP_TYPE_SATELLITE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.defaultMarker;

public class MapFragment extends Fragment {

    private GoogleMap map;
    private TextView textView;
    private MapView mapView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        textView = (TextView) view.findViewById(R.id.mapText);

        mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                initMap();
            }
        });

        return view;

    }

    // really need to override all lifecycle methods
    // to make the MapView work correctly
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_map, menu);

        MenuItem searchItem = menu.findItem(R.id.search_button);

        MenuItem filterItem = menu.findItem(R.id.filter_button);

        MenuItem settingsItem = menu.findItem(R.id.settings_button);
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

    void centerMap() {
        LatLng byu = new LatLng(40.2518, -111.6493);
        CameraUpdate update = CameraUpdateFactory.newLatLng(byu);
        map.moveCamera(update);
        map.addMarker(new MarkerOptions().position(byu));
    }

    void zoomMap(float amount) {
        CameraUpdate update = CameraUpdateFactory.zoomTo(amount);
        map.moveCamera(update);
    }

    //    static final int mapType = MAP_TYPE_NORMAL;
    static final int mapType = MAP_TYPE_SATELLITE;

    void setMapType() {
        map.setMapType(mapType);
    }

    void setClickListener() {
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                textView.setText(latLng.toString());

            }
        });
    }

    String[][] locations = {
            {"Fairbanks", "64.8378", "-147.7164"},
            {"Anchorage", "61.2181", "-149.9003"},
            {"Sitka", "57.0531", "-135.3300"},
            {"North Pole", "64.7511", "-147.3494"}
    };
    String getCity(String[] strings) {
        return strings[0];
    }
    LatLng getLatLng(String[] strings) {
        return new LatLng(Double.valueOf(strings[1]), Double.valueOf(strings[2]));
    }

    void addMarkers() {
        for (String[] strings : locations) {
            addMarker(getCity(strings), getLatLng(strings));
        }
    }

    void addMarker(String city, LatLng latLng) {
        MarkerOptions options =
                new MarkerOptions().position(latLng).title(city)
                        .icon(defaultMarker(HUE_BLUE));
        Marker marker = map.addMarker(options);
        marker.setTag(city);
    }

    void setBounds() {
        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (String[] strings : locations) {
            builder.include(getLatLng(strings));
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
                String city = (String)marker.getTag();
                textView.setText(city);
                return false;
            }
        });
    }

    void drawLines() {
        LatLng lastCity = null;
        for (String[] strings : locations) {
            LatLng latLng = getLatLng(strings);
            if (lastCity != null)
                drawLine(lastCity, latLng);
            lastCity = latLng;
        }
    }

    static final float WIDTH = 10;  // in pixels
    static final int color = BLUE;

    void drawLine(LatLng point1, LatLng point2) {
        PolylineOptions options =
                new PolylineOptions().add(point1, point2)
                        .color(color).width(WIDTH);
        map.addPolyline(options);
    }

}
