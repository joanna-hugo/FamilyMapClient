package felsted.joanna.fmc.model;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bondd on 12/9/2017.
 */

public class settings {

    private boolean showLifeStoryLines;
    private int lifeStoryLinesColor;
    private boolean showFamilyTreeLines;
    private int familyTreeLinesColor;
    private boolean showSpouseLines;
    private int spouseLinesColor;
    private String mapType;

    private boolean mainLoadMapFragOnCreate = false;

    private static Map<String, Float> eventColors;
    private boolean mapFragInMain;
    private static settings instance = null;


    //SINGLETON
    private settings()
    {
        showLifeStoryLines = false;
        showFamilyTreeLines = false;
        showSpouseLines = false;
        mapType = "Normal";

        eventColors = new HashMap<>();
    }

    public static settings getInstance()
    { //this will create a new instance of settings if none exists
        if (instance == null)
            instance = new settings();
        return instance;
    }

    public void setMapFragInMain(boolean mapFragInMain) {
        this.mapFragInMain = mapFragInMain;
    }

    public boolean isMainLoadMapFragOnCreate() {
        return mainLoadMapFragOnCreate;
    }

    public void setMainLoadMapFragOnCreate(boolean mainLoadMapFragOnCreate) {
        this.mainLoadMapFragOnCreate = mainLoadMapFragOnCreate;
    }

}