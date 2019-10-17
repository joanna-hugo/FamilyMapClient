package felsted.joanna.fmc.model;

import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FamilyModel implements Serializable {
    private String currentUser;
    private List<person> persons = new ArrayList<>();
    private List<event> events = new ArrayList<>();

    private String token = new String();

    private List<person> maternalAncestors = new ArrayList<>();
    private List<person> paternalAncestors = new ArrayList<>();
    private Map<String, List<String>> children= new HashMap<>();
    private Map<String, String> event_type_colors = new HashMap<>();

    private loginRequest reSyncRequest =  new loginRequest();

    private static FamilyModel instance = new FamilyModel();

    private FamilyModel(){};

    public void logout(){
        this.currentUser = "";
        this.persons.clear();
        this.events.clear();
        this.token = "";
        this.maternalAncestors.clear();
        this.paternalAncestors.clear();
        this.children.clear();
    }

    public static FamilyModel getInstance() {
        return instance;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<person> getPersons() {
        return this.persons;
    }

    public void setPersons(List<person> persons) {
        this.persons = persons;
        setupChildren();
        setupAncestors();
    }

    public List<person> getImmediateFam(String personID){
        List<person> fam = new ArrayList<>();
        person root = getPerson(personID);
        if(root.getFatherID() != null && root.getFatherID().length()>0){
            fam.add(getPerson(root.getFatherID()));
        }
        if(root.getMotherID() != null && root.getMotherID().length()>0){
            fam.add(getPerson(root.getMotherID()));
        }
        if(root.getSpouseID() != null && root.getSpouseID().length()>0){
            fam.add(getPerson(root.getSpouseID()));
        }

        List<String> myChildren = children.get(personID);
        if(myChildren == null){
            return fam;
        }
        for(String s: myChildren){
            fam.add(getPerson(s));
        }

        return fam;
    }

    public void addPerson(person p){
        this.persons.add(p);
    }

    public person getPerson(String person_id){
        for(person p : persons){
            if(p.getPersonID().equals( person_id)){
                return p;
            }
        }
        Log.e("FAMILY_MODEL", "Failed to find correct person, returned dummy");
        return this.persons.get(0);
    }

    private boolean isPerson(String person_id){
        if(person_id == null || person_id.equals("")){
            return false;
        }
        person temp = getPerson(person_id);
        return temp.getPersonID().equals(person_id);
    }

    public List<event> getPersonsEvents(String personID){
        List<event> myEvents = new ArrayList<>();
        for(event e: this.events){
            if(e.getPersonID().equals( personID)){
                myEvents.add(e);
            }
        }
        return myEvents;
    }

    public List<event> getPersonsEventsOrdered(String personID){
        List<event> life = getPersonsEvents(personID);
        Collections.sort(life);
        return life;
    }

    public List<event> getEvents() {
        return this.events;
    }

    public event getEvent(String event_id){
        for(event e : this.events){
            if(e.getEventID().equals(event_id)){
                return e;
            }
        }
        Log.e("FAMILY MODEL", "failed to find correct event, returned dummy event");
        return this.events.get(0);
    }

    public LatLng averageLatLng(){
        double avgLat = 0d;
        double avgLong = 0d;
        for(event e: events){
            avgLat += e.getLatitude();
            avgLong += e.getLongitude();
        }
        avgLat = avgLat/events.size();
        avgLong = avgLong/events.size();

        return new LatLng(avgLat, avgLong);
    }

    public event getFathersBirth(String person_id){
        person p = getPerson(person_id);
        List<event> fatherEvents= getPersonsEvents(p.getFatherID());

        if(fatherEvents.isEmpty()){
            return null;
        }
        event temp = fatherEvents.get(0);
        for(event e: fatherEvents){
            if (e.getYear() < temp.getYear()){
                temp = e;
            }
        }
        return temp;
    }

    public event getMothersBirth(String person_id){
        person p = getPerson(person_id);
        List<event> motherEvents= getPersonsEvents(p.getMotherID());

        if(motherEvents.isEmpty()){
            return null;
        }
        event temp = motherEvents.get(0);
        for(event e: motherEvents){
            if (e.getYear() < temp.getYear()){
                temp = e;
            }
        }
        return temp;
    }

    public event getSpousesBirth(String person_id){ //TODO fix this
        person p = getPerson(person_id);
        List<event> spouseEvengs= getPersonsEvents(p.getSpouseID());

        if(spouseEvengs.isEmpty()){
            return null;
        }
        event temp = spouseEvengs.get(0);
        for(event e: spouseEvengs){
            if (e.getYear() < temp.getYear()){
                temp = e;
            }
        }
        return temp;
    }

    public void addEvent(event e){
        this.events.add(e);
    }

    public void setEvents(List<event> events) {
        this.events = events;
    }

    public String getCurrentUser() {
        return this.currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public void setupFilters(){
        Filters filters = Filters.getInstance();
        for(event e: events){
            filters.addEventTypeFilter(e.getEventType().toLowerCase());
        }
    }

    public void setupAncestors(){
        traverseFamily(true, currentUser);
        traverseFamily(false, currentUser);
    }

    public void setupChildren(){
        for (person p : this.persons) {
            //if needed, create a map entry for Father
            if (!this.children.containsKey(p.getFatherID())) {
                this.children.put(p.getFatherID(), new ArrayList<String>());
            }
            //add current user as child to father (if father exists)
            if(p.getFatherID()!= null && !p.getFatherID().equals("")) {
                //if this child is not already listed, add them
                if(!this.children.get(p.getFatherID()).contains(p.getPersonID())) {
                    this.children.get(p.getFatherID()).add(p.getPersonID());
                }
            }

            //if needed, create a map entry for Mother
            if (!this.children.containsKey(p.getMotherID())) {
                this.children.put(p.getMotherID(), new ArrayList<String>());
            }
            //add current user as child to Mother
            if(p.getMotherID() != null && !p.getMotherID().equals("")){
                if(!this.children.get(p.getMotherID()).contains(p.getPersonID())) {
                    this.children.get(p.getMotherID()).add(p.getPersonID());
                }
            }
        }
    }

    public void setupColors(){
        int temp =0;
        for(event e: events){
            if(!event_type_colors.containsKey(e.getEventType().toLowerCase())){
//                event_type_colors.put(e.getEventType().toLowerCase(), Color.argb(temp, temp, temp).toString()); //TODO
            }
        }
    }

    public Boolean isMaternal(String person_id){
        return this.maternalAncestors.contains(getPerson(person_id));
    }

    public Boolean isPaternal(String person_id){
        return this.paternalAncestors.contains(getPerson(person_id));
    }

    public List<person> getMaternalAncestors() {
        return maternalAncestors;
    }

    public void setMaternalAncestors(List<person> maternalAncestors) {
        this.maternalAncestors = maternalAncestors;
    }

    public List<person> getPaternalAncestors() {
        return paternalAncestors;
    }

    public void setPaternalAncestors(List<person> paternalAncestors) {
        this.paternalAncestors = paternalAncestors;
    }

    private void traverseFamily(Boolean givenIsMaternal, String person_id){
        //if givenIsMaternal add to maternal ancestors
            //otherwise, add to paternal ancestors

        //get the father
        //get the mother

        person root = getPerson(person_id);
        String father_id = root.getFatherID();
        String mother_id = root.getMotherID();

        //if father, then add to array and traverseFamily
        if(isPerson(father_id)){ //NOTE possible to return dummy family member
            person father = getPerson(father_id);
            if(givenIsMaternal){
                this.maternalAncestors.add(father);
            }else{
                this.paternalAncestors.add(father);
            }
            traverseFamily(givenIsMaternal, father_id);
        }else{
            System.out.println("END OF FAMILY LINE");
        }
        //if mother, then add to array and traverseFamily
        if(isPerson(mother_id)){
            person mother = getPerson(mother_id);
            if(givenIsMaternal){
                this.maternalAncestors.add(mother);
            }else{
                this.paternalAncestors.add(mother);
            }
            traverseFamily(givenIsMaternal, mother_id);
        }else{
            System.out.println("END OF FAMILY LINE");
        }
    }

    public List<event> searchEvents(String input){
        List<event> valid = new ArrayList<>();
        for(event e: events){
            String year = Integer.toString(e.getYear());
            if(e.getCountry().toLowerCase().contains(input)){
                valid.add(e);
            }else if(e.getCity().toLowerCase().contains(input)){
                valid.add(e);
            }else if(e.getEventType().toLowerCase().contains(input)){
                valid.add(e);
            }else if(year.contains(input)){
                valid.add(e);
            }
        }
        return valid;
    }

    public List<person> searchPersons(String input) {
        List<person> valid = new ArrayList<>();
        for (person p : persons) {
            if (p.getLastName().toLowerCase().contains(input)) {
                valid.add(p);
            } else if (p.getFirstName().toLowerCase().contains(input)) {
                valid.add(p);
            }
        }
        return valid;
    }

}
