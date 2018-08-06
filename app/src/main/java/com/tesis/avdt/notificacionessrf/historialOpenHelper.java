package com.tesis.avdt.notificacionessrf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class historialOpenHelper extends SQLiteOpenHelper {

    // It's a good idea to always define a log tag like this.
    private static final String TAG =  historialOpenHelper.class.getSimpleName();
    // has to be 1 first time or app will crash
    private static final int DATABASE_VERSION = 1;
    private static final String HISTORIAL_LIST_TABLE = "entradasColumnas";
    private static final String DATABASE_NAME = "historialDB";


    // Column names...
    public static final String KEY_ID = "_id";
    public static final String KEY_DAY = "day";
    public static final String KEY_HOUR = "hour";

    // ... and a string array of columns.
    private static final String[] COLUMNS = { KEY_ID,KEY_HOUR,KEY_DAY};

    private static final  String HISTORIAL_TABLE_CREATE =
            "CREATE TABLE " + HISTORIAL_LIST_TABLE + " ("
            +  KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_HOUR + " TEXT NOT NULL, "
            + KEY_DAY + " TEXT NOT NULL);";

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;


    //Methods for helping control the DB


    public historialOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HISTORIAL_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(historialOpenHelper.class.getName(), "Upgrading database from version " +
                oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " +HISTORIAL_LIST_TABLE);
        onCreate(db);
    }

    public alertaItem query(int position) {
        String query = "SELECT * FROM " + HISTORIAL_LIST_TABLE +
                " ORDER BY " + KEY_ID + " DESC " + "LIMIT " + position + ",1";

        Cursor cursor = null;
        alertaItem entry = new alertaItem();

        try {
            if (mReadableDB == null) {
                mReadableDB = getReadableDatabase();
            }
            cursor = mReadableDB.rawQuery(query,null);
            cursor.moveToFirst();


            entry.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            entry.setFecha(cursor.getString(cursor.getColumnIndex(KEY_DAY)));
            entry.setHora(cursor.getString(cursor.getColumnIndex(KEY_HOUR)));
            Log.d(TAG,"SET HOUR: " + cursor.getString(cursor.getColumnIndex(KEY_HOUR)));
        }

        catch (Exception e) {
            Log.d(TAG, "EXCEPTION! "  + e);
        }

        finally {
            cursor.close();
            return entry;
        }
    }

    public long insert(String[] informacion){

        long newId = 0;

        ContentValues values = new ContentValues();

        values.put(KEY_DAY, informacion[0]);
        values.put(KEY_HOUR, informacion[1]);

        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            newId = mWritableDB.insert(HISTORIAL_LIST_TABLE, null, values);
        }
        catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }

        return newId;
    }

    public long count(){
        if (mReadableDB == null) {
            mReadableDB = getReadableDatabase();
        }
        return DatabaseUtils.queryNumEntries(mReadableDB, HISTORIAL_LIST_TABLE);
    }

    public int delete(int id) {
        int deleted = 0;

        try {
            if (mWritableDB == null) {
                mWritableDB = getWritableDatabase();
            }
            deleted = mWritableDB.delete(HISTORIAL_LIST_TABLE, KEY_ID + " = ? ",
                    new String[]{String.valueOf(id)});
        }
        catch (Exception e) {
            Log.d (TAG, "DELETE EXCEPTION! " + e.getMessage());
        }
        return deleted;
    }
}
