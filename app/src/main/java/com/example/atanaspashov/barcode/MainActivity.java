package com.example.atanaspashov.barcode;

import android.content.Intent;
import android.database.SQLException;
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
    TextView barcodeResult;
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
                    barcodeResult.setText("Barcode value: " + barcode.displayValue);
                }
                else {
                    barcodeResult.setText("No Barcode Found");
                    GetData getData = new GetData();
                    getData.execute();
                }
            }

        }
            else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }

    private class GetData extends AsyncTask<String, String, String> {

        // JDBC driver name and database URL
        static final String JDBC_Driver = "com.mysql.jdbc.Driver";
        static final String DB_URL = "jdbc:mysql://" + DB_Strings.database_URL + "/" + DB_Strings.database_NAME;

        @Override
        protected void onPreExecute() {
            // someTextView.setText("Connecting to DB");
            super.onPreExecute();
        }


        @Override
        protected String doInBackground(String... strings) {
            Connection conn = null;
            Statement stmt = null;  // SELECT * FROM table;

            try {
                Class.forName(JDBC_Driver);
                conn = DriverManager.getConnection(DB_URL, DB_Strings.username, DB_Strings.password);

                stmt = conn.createStatement();
                String sql_request = "SELECT * FROM codes";
                ResultSet rs = stmt.executeQuery(sql_request);

                while(rs.next()) // while it has a next result
                {
                    String type = rs.getString("type"); // type is the name of the column
                    String description = "";
                    Log.d("cow", "Type from DB" + type);
                    codesMap.put(type, description); //35:11
                }
                rs.close();
                stmt.close();
                conn.close();

            } catch (java.sql.SQLException connExcept) {
                connExcept.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }  finally { // just in case there was an error in the previous try
                try{
                    if (stmt != null) {
                        stmt.close();
                    }
                    if (conn != null) {
                        conn.close();
                    }

                } catch (java.sql.SQLException e) {
                    e.printStackTrace();
                }
            }


            return null;
        }
    }

} // end of MainActivity outer class
