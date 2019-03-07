package com.cre8.atanaspashov.barcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Suggest extends AppCompatActivity {
    private String barcode = "";
    private EditText barcodeSuggestion;
    private EditText productSuggestion;
    private EditText descriptionSuggestion;
    private EditText materialSuggestion;
    private String barcodeStr, product, description, material;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest);

        barcodeSuggestion = findViewById(R.id.barcode_to_suggest);
        productSuggestion = findViewById(R.id.product_name);
        descriptionSuggestion = findViewById(R.id.description);
        materialSuggestion = findViewById(R.id.materials);

        barcode =getIntent().getStringExtra("barcode");
        if (barcode != null && !barcode.equals("")) {
            // long barcodeLong = Long.parseLong(barcode);
            barcodeSuggestion.setText(barcode, TextView.BufferType.EDITABLE);
        }
    }

    public void onSuggest(View v) {
        barcodeStr = barcodeSuggestion.getText().toString();
        product = productSuggestion.getText().toString();
        description = descriptionSuggestion.getText().toString();
        material = materialSuggestion.getText().toString();

        new SendASuggestion().execute(barcodeStr, product, description, material);
    }
}
