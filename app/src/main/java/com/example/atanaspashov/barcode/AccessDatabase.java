package com.example.atanaspashov.barcode;


import android.content.Context;
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

    private SQLiteOpenHelper OpenHelper;
    private SQLiteDatabase BarcodeDatabase;
    private static AccessDatabase instance;
    Cursor cursor;

    private AccessDatabase(Context context) {
        OpenHelper = new GetData(context);
    } // private

    public static AccessDatabase getDatabaseInstance(Context context) {
        if (instance == null) {
            instance = new AccessDatabase(context);
        }
        return instance;
    }

    public void open() {
        BarcodeDatabase = OpenHelper.getReadableDatabase(); // writable
    }

    public void close() {
        if (BarcodeDatabase != null) {
            BarcodeDatabase.close();
        }
    }

    public String getType(long code) {
        cursor = BarcodeDatabase.rawQuery("SELECT type FROM barcodes WHERE code = ?", new String[]{Long.toString(code) /*or String.valueOf()*/});
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            String address = cursor.getString(0);
            buffer.append("" + address);
        }
        return buffer.toString();
    }

    public String getDescription(String type) {
        cursor = BarcodeDatabase.rawQuery("SELECT description FROM type_of_material WHERE type = ?", new String[]{type /*or String.valueOf()*/});
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            String address = cursor.getString(0); Log.w("COW", "address " + cursor);
            buffer.append("" + address);
        }
        return buffer.toString();
    }

    private class GetData extends SQLiteAssetHelper {
        private static final String DB_name = "BarcodeMaterialType.db";
        private static final int DB_version = 1;

        public GetData(Context context) {
            super(context, DB_name, null, DB_version);
        }

    }


} // end of AccessDatabase outer class
