package felsted.joanna.fmc.model;

import java.io.Serializable;

/**
 * This is the model Event class. It's members include --
 *     Event ID: Unique identifier for this event (non-empty string)
 *     Descendant: User (Username) to which this person belongs
 *     Person: ID of person to which this event belongs
 *     Latitude: Latitude of event’s location
 *     Longitude: Longitude of event’s location
 *     Country: Country in which event occurred
 *     City: City in which event occurred
 *     EventType: Type of event (birth, baptism, christening, marriage, death, etc.)
 *     Year: Year in which event occurred
 *
 * New events can only be constructed when given ALL data members
 * All members can only be accessed through getters and setters
 */
public class event  implements Serializable, Comparable <event> {

    /*
    Event ID: Unique identifier for this event (non-empty string)
    Descendant: User (Username) to which this person belongs
    Person: ID of person to which this event belongs
    Latitude: Latitude of event’s location
    Longitude: Longitude of event’s location
    Country: Country in which event occurred
    City: City in which event occurred
    EventType: Type of event (birth, baptism, christening, marriage, death, etc.)
    Year: Year in which event occurred

     */
    private String eventID;
    private String associatedUsername; //persons username to which this event belongs
    private String personID; // ID of person to which this event belongs
    private Double latitude;
    private Double longitude;
    private String country;
    private String city;
    private String eventType;
    private int    year;

    public event(String eventID, String associatedUsername, String person, Double latitude,
                 Double longitude, String country, String city, String eventType, int year) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = person;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventType;
        this.year = year;
    }

    @Override
    public int compareTo(event e){
        //check for birth and death types
        if(this.getEventType().equalsIgnoreCase("birth")){
            return -1;
        }
        if(e.getEventType().equalsIgnoreCase("birth")){
            return 1;
        }
        if(this.getEventType().equalsIgnoreCase("death")){
            return 1;
        }
        if(e.getEventType().equalsIgnoreCase("death")){
            return -1;
        }

        //check for chronological order
        if(this.getYear() < e.getYear()){
            return -1;
        }
        if(this.getYear() > e.getYear()){
            return 1;
        }

        //order by event_type in lowercase
        return this.getEventType().toLowerCase().compareTo(e.getEventType().toLowerCase());
    }

    public String getEventID() {
        return eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public event(){}

    // Getters and Setters-------------------------------------------------------------
    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPerson() {
        return personID;
    }

    public void setPerson(String person) {
        this.personID = person;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public event(String eventID) {
        this.eventID = eventID;
    }
}
