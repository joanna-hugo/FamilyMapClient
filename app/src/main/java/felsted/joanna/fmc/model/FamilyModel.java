package felsted.joanna.fmc.model;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class FamilyModel implements Serializable {
    private List<person> persons = new ArrayList<>();
    private List<event> events = new ArrayList<>();
    private String token = new String();



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String currentUser;

    public List<person> getPersons() {
        return persons;
    }

    public void setPersons(List<person> persons) {
        this.persons = persons;
    }

    public void addPerson(person p){
        persons.add(p);
    }

    public person getPerson(String person_id){
        for(person p : persons){
            if(p.getPersonID().equals( person_id)){
                return p;
            }
        }
        Log.e("FAMILY_MODEL", "Failed to find correct person, returned dummy");
        return persons.get(0);
    }

    public List<event> getPersonsEvents(String personID){
        List<event> myEvents = new ArrayList<>();
        for(event e: events){
            if(e.getPersonID().equals( personID)){
                myEvents.add(e);
            }
        }
        return myEvents;
    }

    public List<event> getEvents() {
        return events;
    }

    public event getEvent(String event_id){
        for(event e : events){
            if(e.getEventID().equals(event_id)){
                return e;
            }
        }
        Log.e("FAMILY MODEL", "failed to find correct event, returned dummy event");
        return events.get(0);
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
        events.add(e);
    }

    public void setEvents(List<event> events) {
        this.events = events;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
}
