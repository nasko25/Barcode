package com.example.atanaspashov.barcode;


import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class AccessDatabase {

/*    static final String database_URL = "localhost:3306";
    static final String database_NAME = "Barcodes";
    static final String username = "app_user";
    static final String password = "pass";*/

    private SQLiteOpenHelper OpenHelperBarcode;
    private SQLiteOpenHelper OpenHelperRecycleHistrory;
    private SQLiteDatabase BarcodeDatabase;
    private SQLiteDatabase RecycleHistoryDatabase;
    private static AccessDatabase instance;
    Cursor cursor, cursorToWrite;

    private AccessDatabase(Context context) {
        OpenHelperBarcode = new GetData(context, "BarcodeMaterialType.db");
        OpenHelperRecycleHistrory = new GetData(context, "RecycleHistory.db");
    } // private

    public static AccessDatabase getDatabaseInstance(Context context) {
        if (instance == null) {
            instance = new AccessDatabase(context);
        }
        return instance;
    }

    public void open(String db) {
        if (db.equals("barcode")){
            BarcodeDatabase = OpenHelperBarcode.getReadableDatabase(); // writable
        }
        else if (db.equals("history")) {
            RecycleHistoryDatabase = OpenHelperRecycleHistrory.getReadableDatabase(); // more likely to be writable
        }

    }

    public void close(String db) {
        if (BarcodeDatabase != null && db.equals("barcode")){
            BarcodeDatabase.close();
            return;
        }
        if (RecycleHistoryDatabase != null && db.equals("history")) {
            RecycleHistoryDatabase.close();
            return;
        }
        // if the argument is invalid:
        // RecycleHistoryDatabase.close();
        // BarcodeDatabase.close();
    }

    public String getType(long code) {
        if (BarcodeDatabase != null) {
        cursor = BarcodeDatabase.rawQuery("SELECT type FROM barcodes WHERE code = ?", new String[]{Long.toString(code) /*or String.valueOf()*/});
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            String address = cursor.getString(0);
            buffer.append("" + address);
        }
        return buffer.toString(); }
        return "";
    }

    public String getDescription(String type) {
        if (BarcodeDatabase != null) {
        cursor = BarcodeDatabase.rawQuery("SELECT description FROM type_of_material WHERE type = ?", new String[]{type /*or String.valueOf()*/});
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            String address = cursor.getString(0); Log.w("COW", "address " + cursor);
            buffer.append("" + address);
        }
        return buffer.toString(); }
        return "";
    }

    public int getRecycledElementsNumber() {
        if (RecycleHistoryDatabase != null) {
            cursor = RecycleHistoryDatabase.rawQuery("SELECT times_recycled FROM history", new String[] {});
            int recycledElementsNumber = 0;
            while (cursor.moveToNext()) {
                recycledElementsNumber+=cursor.getInt(0);
            }
            Log.w("COW", "" + recycledElementsNumber);
            return recycledElementsNumber;
        }
        return 0;
    }
    protected void writeToRecycle(String type, String ToWrite){
        if (RecycleHistoryDatabase != null) {
            cursor = RecycleHistoryDatabase.rawQuery("SELECT type FROM history", new String[] {});
            RecycleHistoryDatabase = OpenHelperRecycleHistrory.getWritableDatabase();
            cursorToWrite = RecycleHistoryDatabase.rawQuery("UPDATE history set times_recycled = 1 where type = ?;", new String[] {type});
            while (cursor.moveToNext()) {
                if(cursor.getString(0).equals(type)) {
                    cursorToWrite.moveToFirst();

                    // TODO read from the DB and increment the number for times_recycled
                    // TODO if there is no entry for the type, create one, and set the times_recycled to 1
                }
            }
            cursor.close();
            cursorToWrite.close();
        }
    }

    private class GetData extends SQLiteAssetHelper {
        // private static final String DB_name = "BarcodeMaterialType.db";
        private static final int DB_version = 1;

        public GetData(Context context, String DB_name) {
            super(context, DB_name, null, DB_version);
        }

    }


} // end of AccessDatabase outer class
