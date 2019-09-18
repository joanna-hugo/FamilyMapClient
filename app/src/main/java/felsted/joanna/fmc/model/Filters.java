package felsted.joanna.fmc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Filters {
//    private Map<String, Boolean> event_filters = new HashMap<>();

    private Map<String, Boolean> event_type_filters_2 = new HashMap<>();
    private FamilyModel mFamilyModel = FamilyModel.getInstance();
    private Boolean showMale = true;

    private Boolean showFemale = true;
    private Boolean showFathersSide = true;
    private Boolean showMothersSide = true;
    private static final Filters instance = new Filters();

    //SINGLETON
    private Filters(){
        for(event e: mFamilyModel.getEvents()){
            event_type_filters_2.put(e.getEventType(), true);
        }
    }

    public static Filters getInstance(){
        return instance;
    }

    public void addTypeFilter(String event_type, Boolean isShow){
        event_type_filters_2.put(event_type, isShow);
    }


    public void addEventTypeFilter(String type){
        event_type_filters_2.put(type, true);
    }

    public Boolean getMappedFilter(String type){
        return event_type_filters_2.get(type);
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

    public class Filter {
        private Boolean show = true;
        private String event_type;

        public Filter(Boolean show, String event_type) {
            this.show = show;
            this.event_type = event_type;
        }

        public Boolean getShow() {
            return show;
        }

        public void setShow(Boolean show) {
            this.show = show;
        }

        public String getEvent_type() {
            return event_type;
        }

        public void setEvent_type(String event_type) {
            this.event_type = event_type;
        }

        public void toggleFilter(){

                    if(show){
                        setShow(false);
                    }else {
                        setShow(true);
                    }
        }
    }
}
