package felsted.joanna.fmc.db;

public class DBschema {
    public static final class EventTable{
        public static final String NAME = "events";

        public static final class Cols {
            public static final String EVENTID = "eventID";
            public static final String DESCENDANT = "descendant";
            public static final String PERSONID = "personID";
            public static final String COUNTRY = "country";
            public static final String CITY = "city";
            public static final String EVENTTYPE = "eventType";
            public static final String YEAR = "year";
            public static final String LATITUDE = "latitude";
            public static final String LONGITUDE = "longitude";
        }
    }
    public static final class UserTable{
        public static final String NAME = "users";

        public static final class Cols{
            public static final String USERNAME = "userName";
            public static final String PASSWORD = "password";
            public static final String EMAIL = "email";
            public static final String FIRSTNAME = "firstName";
            public static final String LASTNAME = "lastName";
            public static final String GENDER = "gender";
            public static final String PERSONID = "personID"; //primary key
        }
    }
    public static final class PersonTable{
        public static final String NAME = "persons";

        public static final class Cols{
            public static final String PERSONID = "personID";
            public static final String DESCENDANT = "descendant";
            public static final String FIRSTNAME = "firstName";
            public static final String LASTNAME = "lastName";
            public static final String GENDER = "gender";
            public static final String FATHER = "father";
            public static final String MOTHER = "mother";
            public static final String SPOUSE = "spouse";
        }
    }
    public static final class AuthTable{
        public static final String NAME = "auth";

        public static final class Cols{
            public static final String AUTHTOKEN = "auth_token";
            public static final String USERNAME = "userName";
        }
    }

}