package felsted.joanna.fmc.model;

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

    public event getSpousesBirth(String person_id){
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
            filters.addEventTypeFilter(e.getEventType());
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
                this.children.get(p.getFatherID()).add(p.getPersonID());
            }

            //if needed, create a map entry for Mother
            if (!this.children.containsKey(p.getMotherID())) {
                this.children.put(p.getMotherID(), new ArrayList<String>());
            }
            //add current user as child to Mother
            if(p.getMotherID() != null && !p.getMotherID().equals("")){
                this.children.get(p.getMotherID()).add(p.getPersonID());
            }
        }
    }

    public Boolean isMaternal(String person_id){
        return this.maternalAncestors.contains(person_id);
    }

    public Boolean isPaternal(String person_id){
        return this.paternalAncestors.contains(person_id);
    }

    private void traverseFamily(Boolean isMaternal, String person_id){
        //if isMaternal add to maternal ancestors
            //otherwise, add to paternal ancestors

        //get the father
        //get the mother
        person root = getPerson(person_id);
        String father_id = root.getFatherID();
        String mother_id = root.getMotherID();

        //if father, then add to array and traverseFamily
        if(father_id != null && father_id.equals(root.getFatherID())){ //NOTE possible to return dummy family member
            person father = getPerson(father_id);
            if(isMaternal){
                this.maternalAncestors.add(father);
            }else{
                this.paternalAncestors.add(father);
            }
            traverseFamily(isMaternal, father_id);
        }else{
            System.out.println("END OF FAMILY LINE");
        }
        //if mother, then add to array and traverseFamily
        if(mother_id!= null && mother_id.equals(root.getMotherID())){
            person mother = getPerson(mother_id);
            if(isMaternal){
                this.maternalAncestors.add(mother);
            }else{
                this.paternalAncestors.add(mother);
            }
            traverseFamily(isMaternal, mother_id);
        }else{
            System.out.println("END OF FAMILY LINE");
        }
    }

    public List<event> searchEvents(String input){
        List<event> valid = new ArrayList<>();
        for(event e: events){
            String year = Integer.toString(e.getYear());
            if(e.getCountry().contains(input)){
                valid.add(e);
            }else if(e.getCity().contains(input)){
                valid.add(e);
            }else if(e.getEventType().contains(input)){
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
            if (p.getLastName().contains(input)) {
                valid.add(p);
            } else if (p.getFirstName().contains(input)) {
                valid.add(p);
            }
        }
        return valid;
    }

}
