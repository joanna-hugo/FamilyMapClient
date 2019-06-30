package felsted.joanna.fmc;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import felsted.joanna.fmc.model.settings;

public class MainActivity extends AppCompatActivity {
    settings mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            settings s = null;
            s = s.getInstance();
            if (!s.isMainLoadMapFragOnCreate())
                fragment = new LoginFragment();
            else {
                s.setMapFragInMain(true);
                fragment = new MapFragment();
            }

            fragment = new LoginFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    protected void switchToMapFragment()
    {
        settings s = null;
        s = s.getInstance();
        s.setMapFragInMain(true);
        s.setMainLoadMapFragOnCreate(true);
        Fragment mapFrag = new MapFragment();
        FragmentManager fm = this.getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_container, mapFrag)
                .commit();
    }
}
