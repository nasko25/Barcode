package com.example.atanaspashov.barcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Suggest extends AppCompatActivity {
    private String barcode = "";
    private EditText barcodeSuggestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);

        barcodeSuggestion = findViewById(R.id.barcode_to_suggest);
        barcode =getIntent().getStringExtra("barcode");
        if (barcode != null && !barcode.equals("")) {
            Log.w("COW", "here");
            // long barcodeLong = Long.parseLong(barcode);
            barcodeSuggestion.setText(barcode, TextView.BufferType.EDITABLE);
        }
    }

    public void onSuggest(View v) {
        new SendASuggestion().execute("");
    }
}
