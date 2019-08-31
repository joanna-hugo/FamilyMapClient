package felsted.joanna.fmc.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbOpener extends SQLiteOpenHelper { //CrimeBaseHelper
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "FMC.db";

    public dbOpener(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table " + DBschema.EventTable.NAME +  " ( " +
        "_id string primary key autoincrement, " +
        DBschema.EventTable.Cols.CITY + ", " +
        DBschema.EventTable.Cols.COUNTRY + ", " +
        DBschema.EventTable.Cols.DESCENDANT + "," +
        DBschema.EventTable.Cols.EVENTID +", " +
        DBschema.EventTable.Cols.EVENTTYPE + ", " +
         DBschema.EventTable.Cols.LONGITUDE + ", " +
          DBschema.EventTable.Cols.LATITUDE + ", " +
          DBschema.EventTable.Cols.PERSONID + ", " +
         DBschema.EventTable.Cols.YEAR + ")" );

        db.execSQL("Create table " + DBschema.PersonTable.NAME +  " ( " +
                "_id string primary key autoincrement, " +
                DBschema.PersonTable.Cols.FIRSTNAME + ", " +
                DBschema.PersonTable.Cols.LASTNAME + ", " +
                DBschema.PersonTable.Cols.DESCENDANT + "," +
                DBschema.PersonTable.Cols.GENDER +", " +
                DBschema.PersonTable.Cols.FATHER + ", " +
                DBschema.PersonTable.Cols.MOTHER + ", " +
                DBschema.PersonTable.Cols.SPOUSE + ", " +
                DBschema.PersonTable.Cols.PERSONID + " )" );
    }

    /*
    CREATE TABLE Users(
	username TEXT NOT NULL UNIQUE,
	password TEXT NOT NULL,
	email TEXT NOT NULL,
	first_name TEXT NOT NULL,
	last_name TEXT NOT NULL,
	gender TEXT NOT NULL,
	personID TEXT NOT NULL UNIQUE,
	PRIMARY KEY (personID)

);

CREATE table Persons(
	personID TEXT NOT NULL UNIQUE,
	descendant TEXT NOT NULL,
	first_name TEXT NOT NULL,
	last_name TEXT NOT NULL,
	gender TEXT NOT NULL,
	father TEXT,
	mother TEXT,
	spouse TEXT,
	PRIMARY KEY (personID)
);

CREATE TABLE Events(
	event_id TEXT NOT NULL UNIQUE,
	descendant TEXT NOT NULL,
	personID TEXT NOT NULL,
	latitude INT not null,
	longitude int not null,
	country text not null,
	city text not null,
	year int not null,
	event_type text not null,
	PRIMARY KEY (personID)
);

CREATE TABLE Auth(
	auth_token TEXT NOT NULL UNIQUE,
	username TEXT NOT NULL,
	PRIMARY KEY (auth_token)
);
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
