package felsted.joanna.fmc.model;

import java.util.HashMap;
import java.util.Map;

public class Filters {
    private Map<String, Boolean> event_filters = new HashMap<>();
    private Boolean showMale = true;

    private Boolean showFemale = true;
    private Boolean showFathersSide = true;
    private Boolean showMothersSide = true;
    private static final Filters instance = new Filters();

    //SINGLETON
    private Filters(){ }

    public static Filters getInstance(){
        return instance;
    }

    public void addEventTypeFilter(String filter){
        event_filters.put(filter, true);
    }

    public Boolean getEventFilter(String filter){
        return event_filters.get(filter);
    }

    public void setEventFilter(String filter, Boolean show){
        event_filters.put(filter, show);
    }

    public Boolean getShowMale() {
        return showMale;
    }

    public void setShowMale(Boolean showMale) {
        this.showMale = showMale;
    }

    public Boolean getShowFemale() {
        return showFemale;
    }

    public void setShowFemale(Boolean showFemale) {
        this.showFemale = showFemale;
    }

    public Boolean getShowFathersSide() {
        return showFathersSide;
    }

    public void setShowFathersSide(Boolean showFathersSide) {
        this.showFathersSide = showFathersSide;
    }

    public Boolean getShowMothersSide() {
        return showMothersSide;
    }

    public void setShowMothersSide(Boolean showMothersSide) {
        this.showMothersSide = showMothersSide;
    }
}
