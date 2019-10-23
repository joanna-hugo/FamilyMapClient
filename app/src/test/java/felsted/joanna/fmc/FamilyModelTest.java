package felsted.joanna.fmc;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import felsted.joanna.fmc.model.FamilyModel;
import felsted.joanna.fmc.model.Filters;
import felsted.joanna.fmc.model.event;
import felsted.joanna.fmc.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class FamilyModelTest {
    FamilyModel mFamilyModel = FamilyModel.getInstance();
    Filters mFilters =  Filters.getInstance();

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Before
    public void setUp(){
        mFamilyModel.addPerson(new person("123", "mine",
                "Nancy", "Everette", "f", "345", "678",
                "999"));
        mFamilyModel.addPerson(new person("678", "mine",
                "Esther", "Everette", "f", "111", "222",
                "345" ));
        mFamilyModel.addPerson(new person("345","mine",
                "Mark", "Everette", "m", "333", "444",
                "678"));
        mFamilyModel.addPerson(new person("999", "mine",
                "Chuck", "Hugo", "m", "888", "777",
                "123"));

        mFamilyModel.addEvent(new event("1", "mine", "123",
                123d, 123d, "Provo", "USA", "birth",
                2000));
        mFamilyModel.addEvent(new event("2", "mine", "123",
                123d, 123d, "Provo", "USA", "death",
                2100));
        mFamilyModel.addEvent(new event("3", "mine", "123",
                123d, 123d, "Provo", "USA", "married",
                2050));
        mFamilyModel.addEvent(new event("4", "mine", "345",
                123d, 123d, "Provo", "USA", "married",
                2050));
        mFamilyModel.addEvent(new event("5", "mine", "678",
                123d, 123d, "Provo", "USA", "married",
                2050));
        mFamilyModel.setupFilters();
        mFamilyModel.setupChildren();
        mFamilyModel.setupAncestors();
    }

    @After
    public void tearDown() {
        List <event> temp = new ArrayList<>();
        mFamilyModel.setEvents(temp);
    }

    @Test
    public void calculateRelationshipsPass(){
        int sizeOfFam = mFamilyModel.getImmediateFam("123").size();
        assertEquals(sizeOfFam, 3 );

        assertTrue(mFamilyModel.isMaternal("678"));
        assertTrue(mFamilyModel.isPaternal("345"));
    }
    @Test
    public void calculateRelationshipFail(){
        //NOTE you have to pass in the right IDs
        int sizeOfFam = mFamilyModel.getImmediateFam("1").size();
        assertNotEquals(sizeOfFam, 4 );

        assertFalse(mFamilyModel.isPaternal("111")); //NOTE switched maternal and paternal
        assertFalse(mFamilyModel.isMaternal("123"));
    }

    @Test
    public void orderingEventsPass(){
        List<event> life_events = mFamilyModel.getPersonsEventsOrdered("123");
        assertEquals(life_events.get(0).getEventType(), "birth");
        assertEquals(life_events.get(2).getEventType(), "death");
    }
    @Test
    public void orderingEventsFail(){

        mFamilyModel.addEvent(new event("1", "mine", "123",
                123d, 123d, "Provo", "USA", "birth",
                3000));

        List<event> life_events = mFamilyModel.getPersonsEventsOrdered("123");
        assertEquals(life_events.get(0).getYear(), 3000);
            // NOTE We inserted bad data, a birth event AFTER a death event.
            // The function returns the bad birth event first
    }

    @Test
    public void getPeopleAndEventsPass(){
        assertEquals(mFamilyModel.getEvent("1").getYear(), 2000);
        assertEquals(mFamilyModel.getPerson("123").getFirstName(), "Nancy");
    }
    @Test
    public void getPeopleAndEventsFail(){
        //Weird responses if given invalid input
        //so we have isPerson function to double check before we call
        assertEquals(mFamilyModel.getEvent("4").getYear(), 2050);
        assertEquals(mFamilyModel.getPerson("314").getFirstName(), "Nancy");
    }

    @Test
    public void filterPeoplePass(){
        //test filters for show fathers side
        assertTrue(mFilters.showPerson(mFamilyModel.getPerson("345")));
        mFilters.setShowFathersSide(false);
        assertFalse(mFilters.showPerson(mFamilyModel.getPerson("345")));
        mFilters.setShowFathersSide(true);

        //test filters for show mothers side
        assertTrue(mFilters.showPerson(mFamilyModel.getPerson("678")));
        mFilters.setShowMothersSide(false);
        assertFalse(mFilters.showPerson(mFamilyModel.getPerson("678")));
        mFilters.setShowMothersSide(true);

        //test filters for show male side
        assertTrue(mFilters.showPerson(mFamilyModel.getPerson("999")));
        mFilters.setShowMale(false);
        assertFalse(mFilters.showPerson(mFamilyModel.getPerson("999")));
        mFilters.setShowMale(true);

        //test filters for show male side
        assertTrue(mFilters.showPerson(mFamilyModel.getPerson("123")));
        mFilters.setShowFemale(false);
        assertFalse(mFilters.showPerson(mFamilyModel.getPerson("123")));
        mFilters.setShowFemale(true);
    }
    @Test
    public void filterPeopleFail(){
        person temp = new person("123", "notmine",
                "fake", "fake", "m", "", "", "");

        mFilters.setShowMale(false);
        assertTrue(mFilters.showPerson(temp));
        //This should not work, but the temp person has not been inserted into the FamilyModel
        mFilters.setShowMale(true);
    }

    @Test
    public void filterEventsPass(){
        //test filters for show fathers side
        assertTrue(mFilters.showEvent(mFamilyModel.getEvent("4")));
        mFilters.setShowFathersSide(false);
        assertFalse(mFilters.showEvent(mFamilyModel.getEvent("4")));
        mFilters.setShowFathersSide(true);

        //test filters for show mothers side
        assertTrue(mFilters.showEvent(mFamilyModel.getEvent("5")));
        mFilters.setShowMothersSide(false);
        assertFalse(mFilters.showEvent(mFamilyModel.getEvent("5")));
        mFilters.setShowMothersSide(true);

        //test filters for show male side
        assertTrue(mFilters.showEvent(mFamilyModel.getEvent("4")));
        mFilters.setShowMale(false);
        assertFalse(mFilters.showEvent(mFamilyModel.getEvent("4")));
        mFilters.setShowMale(true);

        //test filters for show male side
        assertTrue(mFilters.showEvent(mFamilyModel.getEvent("5")));
        mFilters.setShowFemale(false);
        assertFalse(mFilters.showEvent(mFamilyModel.getEvent("5")));
        mFilters.setShowFemale(true);
    }
    @Test
    public void filterEventFail(){
        event temp = new event ("999", "notmine", "999",
                20d, 20d, "USA", "Provo", "birth", 2010);
        mFilters.setShowFemale(false);
        assertTrue(mFilters.showEvent(temp));
        //This should not work, but the temp event has not been inserted into the FamilyModel
        mFilters.setShowFemale(true);
    }
}
