package com.example.atanaspashov.barcode;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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
    Button recycle_btn, history_btn, suggest_btn;
    Map<String, String> codesMap;
    String barcodeResultString;
    public String buttonName = "button";
    private static final int NUM_PAGES = 2;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    Long code = 0l;

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

        // AccessDatabase recycleHistory_DB = AccessDatabase.getDatabaseInstance(this);
        // recycleHistory_DB.open("history");
        // Log.w("COW", "Number of recycled elements: " + recycleHistory_DB.getRecycledElementsNumber());

        barcodeResult = (TextView)findViewById(R.id.sample_text);
        barcodeResult.setVisibility(View.GONE);
        errorTextView = (TextView)findViewById(R.id.errorTextView);
        errorTextView.setVisibility(View.GONE);
        recycle_btn = (Button) findViewById(R.id.recycle_button);
        recycle_btn.setVisibility(View.GONE); // View.Visible
        history_btn = findViewById(R.id.history);
        suggest_btn = findViewById(R.id.suggest);
        suggest_btn.setVisibility(View.GONE);

        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
    }
    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // TODO add different articles for the different pages (items)
        @Override
        public Fragment getItem(int position) {
            if (position == 0)
                return new ScreenSlidePageFragment();

            return new ScreenSlidePageFragmentForPage2();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    @Override
    protected void onStart() {
        super.onStart();
        AccessDatabase recycleHistory_DB = AccessDatabase.getDatabaseInstance(this);
        recycleHistory_DB.open("history");
        history_btn.setText(String.valueOf(recycleHistory_DB.getRecycledElementsNumber()));
        recycleHistory_DB.close("history");
    }

    // add click event to the scan barcode button
    public void scanBarcode(View v){
        Intent intent = new Intent(this, ScanBarcodeActivity.class);
        startActivityForResult(intent, 0);    // TODO check if the app has a camera permission, and if it does not query for it

    }
    public void openHistory(View v){
        // Intent intent = new Intent(this, RecycleHistory.class);
        Intent intent = new Intent(this, RecycleHistoryActivity.class); // TODO if the database is empty, call another activity that says the database is empty
        startActivityForResult(intent, 0);
    }

    // override onActivityResult to get barcode from ScanBarcodeActivity
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==0){
            if (resultCode==CommonStatusCodes.SUCCESS){
                if(data!=null){
                    Barcode barcode = data.getParcelableExtra("barcode");
                    // barcodeResult.setText("Barcode value: " + barcode.displayValue);

                    try {
                        code = Long.parseLong(barcode.displayValue);
                    }
                    catch (java.lang.NumberFormatException e) {
                        errorTextView.setText("Incompatible barcode");
                        ScreenSlidePageFragment.onError("Incompatible barcode");
                    }
                    AccessDatabase ac = AccessDatabase.getDatabaseInstance(this);
                    ac.open("barcode");
                    if (ac.getType(code).equals("") || ac.getDescription(ac.getType(code)).equals("")) {    // TODO refactor with variables (don't call them unnecessarily)
                        barcodeResult.setText("Barcode not found in the database");
                        ScreenSlidePageFragment.onTextChange("Barcode not found in the database");
                        recycle_btn.setVisibility(View.GONE);
                        suggest_btn.setVisibility(View.VISIBLE);

                    }
                    else {
                        barcodeResultString = ac.getType(code);
                        errorTextView.setText("");
                        ScreenSlidePageFragment.onError("");
                        barcodeResult.setText("Type of plastic: " + ac.getType(code));
                        ScreenSlidePageFragment.onTextChange("Type of plastic: " + ac.getType(code));
                        // barcodeResult.setText(barcodeResult.getText() + "\nDescription: " + ac.getDescription(ac.getType(code)));
                        ScreenSlidePageFragment.onTextChange(barcodeResult.getText() + "\nDescription: " + ac.getDescription(ac.getType(code)));
                        suggest_btn.setVisibility(View.GONE);
                        recycle_btn.setVisibility(View.VISIBLE);
                    }
                    ac.close("barcode");

                }
                else {
                    barcodeResult.setText("No Barcode Found");
                    ScreenSlidePageFragment.onTextChange("No Barcode Found");
                    suggest_btn.setVisibility(View.GONE);
                }
                }
                else {
                    barcodeResult.setText("No Barcode Found");
                    ScreenSlidePageFragment.onTextChange("No Barcode Found");
                    suggest_btn.setVisibility(View.GONE);
            }

        }
            else{
            barcodeResult.setText("No Barcode Found");
            ScreenSlidePageFragment.onTextChange("No Barcode Found");
            suggest_btn.setVisibility(View.GONE);
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    // TODO after the user clicks on the Recycle button, it should either make the button disappear (and somehow notify the user that the material was added to the database), or it should just notify the user that it was added
    public void OnRecycle(View v) {
        AccessDatabase ac = AccessDatabase.getDatabaseInstance(this);
        ac.open("history");
        // call writeToRecycle("")
        // ac.writeToRecycle("test_plastic", "");
        ac.writeToRecycle(barcodeResultString, "");
        Log.w("COW", "Number of recycled elements: " + ac.getRecycledElementsNumber());
        history_btn.setText(String.valueOf(ac.getRecycledElementsNumber()));
        ac.close("history");
    }

    public void OnSuggest(View v) {
        Intent intent = new Intent(this, Suggest.class);
        intent.putExtra("barcode", String.valueOf(code));
        startActivityForResult(intent, 0);
    }
} // end of MainActivity outer class
