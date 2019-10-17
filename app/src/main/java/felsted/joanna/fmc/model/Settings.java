package felsted.joanna.fmc.model;
import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;

import java.io.Serializable;


public class Settings implements Serializable {

    private static final Settings instance = new Settings(); //SINGLETON

    private boolean mainLoadMapFragOnCreate = false;

    private int lifeStoryLinesColor = Color.GREEN;
    private boolean showLifeStoryLines = true;

    private int familyTreeLinesColor = Color.BLUE;
    private boolean showFamilyTreeLines = true;

    private int spouseLinesColor = Color.RED;
    private boolean showSpouseLines = true;

    private int mapType = GoogleMap.MAP_TYPE_NORMAL;


    //SINGLETON
    private Settings(){ }

    public static Settings getInstance(){
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

    public boolean isShowFamilyTreeLines() {
        return showFamilyTreeLines;
    }

    public void setShowFamilyTreeLines(boolean showFamilyTreeLines) {
        this.showFamilyTreeLines = showFamilyTreeLines;
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