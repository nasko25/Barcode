package com.cre8.atanaspashov.barcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GetMaterialInformationActivity extends AppCompatActivity {

    private TextView barcodeInformation;
    private String plastic, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_material_information);

        plastic = getIntent().getStringExtra("plastic");

        AccessDatabase database = AccessDatabase.getDatabaseInstance(this);
        database.open("barcode");
        description = database.getDescription(plastic);
        // display all the information for the material of the type of plastic
        barcodeInformation = findViewById(R.id.barcode_information);
        description = description.replace("\\n", "\r\n\t\t");
        barcodeInformation.setText("\t\t Type of product: " + plastic + "\n\n\t\t Description: " + description);

        SetupTheMargin();
    }

    private void SetupTheMargin() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // set the margin of the textbox depending on how much text/characters there is
        CharSequence barcodeInfo = barcodeInformation.getText();
        int infoSize = barcodeInfo.length();

        if (infoSize <= 20) {
            params.setMargins(8,400,8,8);
        }
        else if (infoSize > 20 && infoSize < 100) {
            params.setMargins(8, 300, 8, 8);
        }
        else if (infoSize >= 100 && infoSize < 200) {
            params.setMargins(8, 50, 8, 8);
        }
        else {
            params.setMargins(8, 20, 8, 8);
            barcodeInformation.setTextSize(20);
        }
        barcodeInformation.setLayoutParams(params);
    }
}
