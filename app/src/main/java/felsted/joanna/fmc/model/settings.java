package felsted.joanna.fmc.model;
import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bondd on 12/9/2017.
 */

public class settings implements Serializable {

    //TODO layout for all of these
    //TODO pass this item between all activities
        //TODO go for extra cred and add to DB whenever changed??

    private static final settings instance = new settings(); //can also think of as SINGLETON

    private boolean mainLoadMapFragOnCreate = false;

    private int lifeStoryLinesColor = Color.GREEN;
    private boolean showLifeStoryLines = true;

    private int familyTreeLinesColor = Color.BLUE;
    private boolean showTreeLines = true;

    private int spouseLinesColor = Color.RED;
    private boolean showSpouseLines = true;

    private int mapType = GoogleMap.MAP_TYPE_NORMAL;


    //SINGLETON
    private settings(){ }

    public static settings getInstance(){
        return instance;
    }

    private boolean mapFragInMain = true;
    public void setMapFragInMain(boolean mapFragInMain) {
        this.mapFragInMain = mapFragInMain;
    }

    public boolean isMainLoadMapFragOnCreate() {
        return mainLoadMapFragOnCreate;
    }

    public void setMainLoadMapFragOnCreate(boolean mainLoadMapFragOnCreate) {
        this.mainLoadMapFragOnCreate = mainLoadMapFragOnCreate;
    }

    public int getLifeStoryLinesColor() {
        return lifeStoryLinesColor;
    }

    public void setLifeStoryLinesColor(int lifeStoryLinesColor) {
        this.lifeStoryLinesColor = lifeStoryLinesColor;
    }

    public boolean isShowLifeStoryLines() {
        return showLifeStoryLines;
    }

    public void setShowLifeStoryLines(boolean showLifeStoryLines) {
        this.showLifeStoryLines = showLifeStoryLines;
    }

    public int getFamilyTreeLinesColor() {
        return familyTreeLinesColor;
    }

    public void setFamilyTreeLinesColor(int familyTreeLinesColor) {
        this.familyTreeLinesColor = familyTreeLinesColor;
    }

    public boolean isShowTreeLines() {
        return showTreeLines;
    }

    public void setShowTreeLines(boolean showTreeLines) {
        this.showTreeLines = showTreeLines;
    }

    public int getSpouseLinesColor() {
        return spouseLinesColor;
    }

    public void setSpouseLinesColor(int spouseLinesColor) {
        this.spouseLinesColor = spouseLinesColor;
    }

    public boolean isShowSpouseLines() {
        return showSpouseLines;
    }

    public void setShowSpouseLines(boolean showSpouseLines) {
        this.showSpouseLines = showSpouseLines;
    }

    public int getMapType() {
        return mapType;
    }

    public void setMapType(int mapType) {
        this.mapType = mapType;
    }

    public boolean isMapFragInMain() {
        return mapFragInMain;
    }
}