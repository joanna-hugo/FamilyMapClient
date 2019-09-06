package felsted.joanna.fmc.model;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
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

    private static FamilyModel instance = new FamilyModel();
    //sorted list of events for each person
    //list of children for each person
    //paternal ancestors
    //maternal ancestors
    private FamilyModel(){};

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

    public event getFathersBirth(String person_id){
        person p = getPerson(person_id);
        List<event> fatherEvents= getPersonsEvents(p.getFather());

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
        List<event> motherEvents= getPersonsEvents(p.getMother());

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
        List<event> spouseEvengs= getPersonsEvents(p.getSpouse());

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
            if (!this.children.containsKey(p.getFather())) {
                this.children.put(p.getFather(), new ArrayList<String>());
            }
            //add current user as child to father (if father exists)
            if(!p.getFather().equals("")) {
                this.children.get(p.getFather()).add(p.getPersonID());
            }

            //if needed, create a map entry for Mother
            if (!this.children.containsKey(p.getMother())) {
                this.children.put(p.getMother(), new ArrayList<String>());
            }
            //add current user as child to Mother
            if(!p.getMother().equals("")){
                this.children.get(p.getMother()).add(p.getPersonID());
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
        String father_id = root.getFather();
        String mother_id = root.getMother();

        //if father, then add to array and traverseFamily
        if(!father_id.equals("")){
            person father = getPerson(father_id);
            if(isMaternal){
                this.maternalAncestors.add(father);
            }else{
                this.paternalAncestors.add(father);
            }
            traverseFamily(isMaternal, father_id);
        }
        //if mother, then add to array and traverseFamily
        if(!mother_id.equals("")){
            person mother = getPerson(mother_id);
            if(isMaternal){
                this.maternalAncestors.add(mother);
            }else{
                this.paternalAncestors.add(mother);
            }
            traverseFamily(isMaternal, mother_id);
        }
    }

}
