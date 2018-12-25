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

public class MainActivity extends AppCompatActivity {                        // TODO think of a way to add images
    TextView barcodeResult, errorTextView;
    Button recycle_btn;
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

        AccessDatabase recycleHistory_DB = AccessDatabase.getDatabaseInstance(this);
        recycleHistory_DB.open("history");
        Log.w("COW", "Number of recycled elements: " + recycleHistory_DB.getRecycledElementsNumber());

        barcodeResult = (TextView)findViewById(R.id.sample_text);
        errorTextView = (TextView)findViewById(R.id.errorTextView);
        recycle_btn = (Button) findViewById(R.id.recycle_button);
        recycle_btn.setVisibility(View.GONE); // View.Visible
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // add click event to the scan barcode button
    public void scanBarcode(View v){
        Intent intent = new Intent(this, ScanBarcodeActivity.class);
        startActivityForResult(intent, 0);    // TODO check if the app has a camera permission, and if it does not query for it

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
                    ac.open("barcode");
                    if (ac.getType(code).equals("") || ac.getDescription(ac.getType(code)).equals("")) {    // TODO refactor with variables (don't call them unnecessarily)
                        barcodeResult.setText("Barcode not found in the database");
                    }
                    else {
                        errorTextView.setText("");
                        barcodeResult.setText("Type of plastic: " + ac.getType(code));
                        barcodeResult.setText(barcodeResult.getText() + "\nDescription: " + ac.getDescription(ac.getType(code)));
                        recycle_btn.setVisibility(View.VISIBLE);
                    }
                    ac.close("barcode");

                }
                else {
                    barcodeResult.setText("No Barcode Found");
                }
                }
                else {
                    barcodeResult.setText("No Barcode Found");

            }

        }
            else{
            barcodeResult.setText("No Barcode Found");
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
    public void OnRecycle(View v) {
        AccessDatabase ac = AccessDatabase.getDatabaseInstance(this);
        ac.open("history");
        // call writeToRecycle("")
        ac.writeToRecycle("test_plastic", "");
        Log.w("COW", "Number of recycled elements: " + ac.getRecycledElementsNumber());
        ac.close("history");
    }
} // end of MainActivity outer class
