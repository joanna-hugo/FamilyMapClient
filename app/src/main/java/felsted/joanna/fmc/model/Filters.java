package felsted.joanna.fmc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Filters {
//    private Map<String, Boolean> event_filters = new HashMap<>();

    private Map<String, Boolean> event_type_filters = new HashMap<>(); //this is the event_types
    private FamilyModel mFamilyModel = FamilyModel.getInstance();
    
    private Boolean showMale = true;
    private Boolean showFemale = true;

    private Boolean showFathersSide = true;
    private Boolean showMothersSide = true;
    private static final Filters instance = new Filters();

    //SINGLETON
    private Filters(){
        for(event e: mFamilyModel.getEvents()){
            event_type_filters.put(e.getEventType(), true);
        }
    }

    public static Filters getInstance(){
        return instance;
    }

    public void addTypeFilter(String event_type, Boolean isShow){
        event_type_filters.put(event_type, isShow);
    }

    public Map<String, Boolean> getEvent_type_filters() {
        return event_type_filters;
    }

    public void setEvent_type_filters(Map<String, Boolean> event_type_filters) {
        this.event_type_filters = event_type_filters;
    }

    public void setEvent_type_filters(String event_type, Boolean show){
        if(this.event_type_filters.containsKey(event_type)){
            this.event_type_filters.put(event_type, show);
        }
    }

    public List<String> getEvent_type_filters_names(){
        List<String> myFilters = new ArrayList<>();
        for (Map.Entry<String, Boolean> entry : event_type_filters.entrySet()) {
            myFilters.add(entry.getKey());
        }
        return myFilters;
    }

    public void addEventTypeFilter(String type){
        event_type_filters.put(type, true);
    }

    public Boolean getMappedFilter(String type){
        return event_type_filters.get(type);
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

    public List<event> filterEvents(List<event> givenEvents){
            for(event e: givenEvents){
                //check paternal
                if(mFamilyModel.isPaternal(e.getPersonID()) && !getShowFathersSide() ){
                 givenEvents.remove(e);
                }

                //check maternal
                if(mFamilyModel.isMaternal(e.getPersonID()) && !getShowMothersSide()){
                    givenEvents.remove(e);
                }

                //check type
                if(!getMappedFilter(e.getEventType())){
                    givenEvents.remove(e);
                }

                //check gender
                if(mFamilyModel.getPerson(e.getPersonID()).getGender().equalsIgnoreCase("f") && !getShowFemale()){
                    givenEvents.remove(e);
                }

                if(mFamilyModel.getPerson(e.getPersonID()).getGender().equalsIgnoreCase("m") && !getShowMale()){
                    givenEvents.remove(e);
                }

            }
            return givenEvents;
    }

    public List<person> filterPersons(List<person> people){
        for(person p: people){
            //check paternal
            if(mFamilyModel.isPaternal(p.getPersonID()) && !getShowFathersSide() ){
                people.remove(p);
            }

            //check maternal
            if(mFamilyModel.isMaternal(p.getPersonID()) && !getShowMothersSide()){
                people.remove(p);
            }

            //check gender
            if(mFamilyModel.getPerson(p.getPersonID()).getGender().equalsIgnoreCase("f") && !getShowFemale()){
                people.remove(p);
            }

            if(mFamilyModel.getPerson(p.getPersonID()).getGender().equalsIgnoreCase("m") && !getShowMale()){
                people.remove(p);
            }

        }
        return people;
    }
}
