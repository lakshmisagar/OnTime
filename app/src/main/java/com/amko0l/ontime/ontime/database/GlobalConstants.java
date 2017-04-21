package com.amko0l.ontime.ontime.database;

/**
 * Created by Lakshmisagar on 4/19/2017.
 */

public class GlobalConstants {
    static String TableName  = "OnTimeTable";
    static String Col_ID = "EventId";
    static String Hour = "Hour";
    static String Minute = "Minute";
    static String Preference = "Preference";
    static String ClassName = "className";

    static String CREATE_TABLE = "CREATE TABLE "+
            TableName +"(" + Col_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Hour + " TEXT, "+ ClassName + " TEXT, "  + Preference + " TEXT, "
            + Minute + " TEXT);";

    static String DELETE_TABLE =  "DROP TABLE IF EXISTS " +TableName;
    static String CHECK_TABLE = "SELECT name FROM sqlite_master WHERE type='table' AND name ="+TableName;
    static boolean isDBupdated = false;

}
