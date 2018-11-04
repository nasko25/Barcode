package com.example.atanaspashov.barcode;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;      // TODO temporary; remove when not needed
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView barcodeResult, errorTextView;
    Map<String, String> codesMap;
    public String buttonName = "button";

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        // tv.setText(stringFromJNI());
        tv.setText(" Hello This is a Barcode scanner ");
        //https://stackoverflow.com/questions/44842887/how-to-access-a-java-variable-in-strings-xml



        barcodeResult = (TextView)findViewById(R.id.sample_text);
        errorTextView = (TextView)findViewById(R.id.errorTextView);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // add click event to the scan barcode button
    public void scanBarcode(View v){
        Intent intent = new Intent(this, ScanBarcodeActivity.class);
        startActivityForResult(intent, 0);

    }
    // override onActivityResult to get barcode from ScanBarcodeActivity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==0){
            if (resultCode==CommonStatusCodes.SUCCESS){
                if(data!=null){
                    Barcode barcode = data.getParcelableExtra("barcode");
                    // barcodeResult.setText("Barcode value: " + barcode.displayValue);
                    Long code = 0l;
                    try {
                        code = Long.parseLong(barcode.displayValue);
                    }
                    catch (java.lang.NumberFormatException e) {
                        errorTextView.setText("Incompatible barcode");
                    }
                    AccessDatabase ac = AccessDatabase.getDatabaseInstance(this);
                    ac.open();
                    if (ac.getAddress(code).equals("")) {
                        barcodeResult.setText("Barcode not found in the database");
                    }
                    else {
                        errorTextView.setText("");
                        barcodeResult.setText("nothing" + ac.getAddress(code)); }
                    ac.close();

                }
                }
                else {
                    barcodeResult.setText("No Barcode Found");

            }

        }
            else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

} // end of MainActivity outer class
