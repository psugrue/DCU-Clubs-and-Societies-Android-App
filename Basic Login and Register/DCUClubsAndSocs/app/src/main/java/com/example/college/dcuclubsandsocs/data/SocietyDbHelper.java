package com.example.college.dcuclubsandsocs.data;

/**
 * Created by Paul on 02/03/2015.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.college.dcuclubsandsocs.data.SocietyContract.SocietyEntry;
import com.example.college.dcuclubsandsocs.data.SocietyContract.LocationEntry;

/**
 * Manages a local database for weather data.
 */
public class SocietyDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "weather.db";

    public SocietyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_SOCIETY_TABLE = "CREATE TABLE " + SocietyEntry.TABLE_NAME + " (" +
// Why AutoIncrement here, and not above?
// Unique keys will be auto-generated in either case. But for weather
// forecasting, it's reasonable to assume the user will want information
// for a certain date and all dates *following*, so the forecast data
// should be sorted accordingly.
                SocietyEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
// the ID of the location entry associated with this weather data
                SocietyEntry.COLUMN_SOC_KEY + " INTEGER NOT NULL, " +
                SocietyEntry.COLUMN_SOC_NAME + " INTEGER NOT NULL, " +

// Set up the location column as a foreign key to location table.
                " FOREIGN KEY (" + SocietyEntry.COLUMN_SOC_KEY + ") REFERENCES " +
                LocationEntry.TABLE_NAME + " (" + LocationEntry._ID + "), " +
// To assure the application have just one weather entry per day
// per location, it's created a UNIQUE constraint with REPLACE strategy
                " UNIQUE (" + SocietyEntry.COLUMN_SOC_NAME + ", " +
                SocietyEntry.COLUMN_SOC_KEY + ") ON CONFLICT REPLACE);";
        sqLiteDatabase.execSQL(SQL_CREATE_SOCIETY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
// This database is only a cache for online data, so its upgrade policy is
// to simply to discard the data and start over
// Note that this only fires if you change the version number for your database.
// It does NOT depend on the version number for your application.
// If you want to update the schema without wiping data, commenting out the next 2 lines
// should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LocationEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SocietyEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

