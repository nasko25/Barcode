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
    private SQLiteDatabase database;
    private static AccessDatabase instanse;
    Cursor cursor;

    private AccessDatabase(Context context) {
        OpenHelper = new GetData(context);
    } // private

    public static AccessDatabase getDatabaseInstance(Context context) {
        if (instanse == null) {
            instanse = new AccessDatabase(context);
        }
        return instanse;
    }

    public void open() {
        database = OpenHelper.getReadableDatabase(); // writable
    }

    public void close() {
        if (database != null) {
            database.close();
        }
    }

    public String getAddress(long code) {
        cursor = database.rawQuery("SELECT type FROM codes WHERE code = ?", new String[]{"671860013624"});
        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            String address = cursor.getString(0); Log.w("COW", "address " + cursor);
            buffer.append("" + address);
        }
        return buffer.toString();
    }

    private class GetData extends SQLiteAssetHelper {
        private static final String DB_name = "Barcodes.db";
        private static final int DB_version = 1;

        public GetData(Context context) {
            super(context, DB_name, null, DB_version);
        }

    }


} // end of AccessDatabase outer class
