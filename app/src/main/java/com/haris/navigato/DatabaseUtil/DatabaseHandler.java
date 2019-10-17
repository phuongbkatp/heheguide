package com.haris.navigato.DatabaseUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.haris.navigato.ConstantUtil.Constant;
import com.haris.navigato.Utility.Utility;


/**
 * Created by hp on 4/7/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static String DATABASE_NAME = "Navigato";
    String CREATE_TABLE = "CREATE TABLE " + Constant.DatabaseColumn.TABLE_NAME + "("
            + Constant.DatabaseColumn.ID_COLUMN + " INTEGER PRIMARY KEY," + Constant.DatabaseColumn.PLACE_PICTURE + " TEXT ,"
            + Constant.DatabaseColumn.PLACE_ID + " TEXT," + Constant.DatabaseColumn.PLACE_NAME + " TEXT," +
            Constant.DatabaseColumn.PRICE_LEVEL + " TEXT," + Constant.DatabaseColumn.PLACE_RATING + " TEXT," +
            Constant.DatabaseColumn.PLACE_TYPE + " TEXT ," + Constant.DatabaseColumn.PLACE_ADDRESS + " TEXT ," + Constant.DatabaseColumn.PLACE_LATITUDE + " Text ,"
            + Constant.DatabaseColumn.PLACE_LONGITUDE + " TEXT " +
            ")";

    String CREATE_HISTORY_TABLE = "CREATE TABLE " + Constant.DatabaseColumn.HISTORY_TABLE_NAME + "("
            + Constant.DatabaseColumn.HISTORY_ID_COLUMN + " INTEGER PRIMARY KEY," + Constant.DatabaseColumn.ROUTE_COLUMN + " TEXT ,"
            + Constant.DatabaseColumn.BY_COLUMN + " TEXT," + Constant.DatabaseColumn.SOURCE_NAME_COLUMN + " TEXT," +
            Constant.DatabaseColumn.DESTINATION_NAME_COLUMN + " TEXT," + Constant.DatabaseColumn.DISTANCE_COLUMN + " TEXT," +
            Constant.DatabaseColumn.DURATION_COLUMN + " TEXT ," + Constant.DatabaseColumn.PETROL_COLUMN + " TEXT ," + Constant.DatabaseColumn.SOURCE_LATITUDE_COLUMN + " Text ,"
            + Constant.DatabaseColumn.SOURCE_LONGITUDE_COLUMN + " TEXT ," + Constant.DatabaseColumn.DESTINATION_LATITUDE_COLUMN + " TEXT ," +
            Constant.DatabaseColumn.DESTINATION_LONGITUDE_COLUMN + " TEXT " + ")";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Utility.extraData("Db Table", CREATE_TABLE);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_HISTORY_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed

        ///db.execSQL("ALTER TABLE " + Constant.Route.TABLE_NAME + " ADD COLUMN " + Constant.Route.FAVOURITE + " TEXT '" + Constant.Route.NOT_FAVOURITE + "'");

        db.execSQL("DROP TABLE IF EXISTS " + Constant.DatabaseColumn.TABLE_NAME);
        

        // Create tables again
        onCreate(db);
    }

}
