package felsted.joanna.fmc.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Filters {

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
            event_type_filters.put(e.getEventType().toLowerCase(), true);
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
        List<event> filtered = new ArrayList<>();
            for(event e: givenEvents){
                //check paternal
                if(mFamilyModel.isPaternal(e.getPersonID()) && !getShowFathersSide() ){
                     continue;
                }

                //check maternal
                if(mFamilyModel.isMaternal(e.getPersonID()) && !getShowMothersSide()){
                    continue;
                }

                //check type
                if(!getMappedFilter(e.getEventType().toLowerCase())){
                    continue;
                }

                //check gender
                if(mFamilyModel.getPerson(e.getPersonID()).getGender().equalsIgnoreCase("f") && !getShowFemale()){
                    continue;
                }

                if(mFamilyModel.getPerson(e.getPersonID()).getGender().equalsIgnoreCase("m") && !getShowMale()){
                    continue;
                }
                filtered.add(e);
            }
            return filtered;
    }

    public List<person> filterPersons(List<person> people){
        List<person> filtered = new ArrayList<>();
        for(person p: people){
            //check paternal
            if(mFamilyModel.isPaternal(p.getPersonID()) && !getShowFathersSide() ){
                continue;
            }

            //check maternal
            if(mFamilyModel.isMaternal(p.getPersonID()) && !getShowMothersSide()){
                continue;
            }

            //check gender
            if(mFamilyModel.getPerson(p.getPersonID()).getGender().equalsIgnoreCase("f") && !getShowFemale()){
                continue;
            }

            if(mFamilyModel.getPerson(p.getPersonID()).getGender().equalsIgnoreCase("m") && !getShowMale()){
                continue;
            }
            filtered.add(p);
        }
        return filtered;
    }

    public Boolean showPerson(person p){
        List<person> filtered = new ArrayList<>();
            //check paternal
            if(mFamilyModel.isPaternal(p.getPersonID()) && !getShowFathersSide() ){
                return false;
            }

            //check maternal
            if(mFamilyModel.isMaternal(p.getPersonID()) && !getShowMothersSide()){
                return false;
            }

            //check gender
            if(mFamilyModel.getPerson(p.getPersonID()).getGender().equalsIgnoreCase("f") && !getShowFemale()){
                return false;
            }

            if(mFamilyModel.getPerson(p.getPersonID()).getGender().equalsIgnoreCase("m") && !getShowMale()){
                return false;
            }
            return true;
    }

    public Boolean showEvent(event e){
        if(mFamilyModel.isPaternal(e.getPersonID()) && !getShowFathersSide() ){
            return false;
        }

        //check maternal
        if(mFamilyModel.isMaternal(e.getPersonID()) && !getShowMothersSide()){
            return false;
        }

        //check type
        if(!getMappedFilter(e.getEventType().toLowerCase())){
            return false;
        }

        //check gender
        if(mFamilyModel.getPerson(e.getPersonID()).getGender().equalsIgnoreCase("f") && !getShowFemale()){
            return false;
        }

        if(mFamilyModel.getPerson(e.getPersonID()).getGender().equalsIgnoreCase("m") && !getShowMale()){
            return false;
        }

        return true;
    }


}
