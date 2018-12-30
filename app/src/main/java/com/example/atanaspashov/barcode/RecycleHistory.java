package com.example.atanaspashov.barcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.util.ArrayList;

public class RecycleHistory extends AppCompatActivity {

    private TextView history_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_history);
        history_textview = findViewById(R.id.recycle_history_textview);

        // TODO extract as a method ?
        AccessDatabase recycleHistory_DB = AccessDatabase.getDatabaseInstance(this);
        recycleHistory_DB.open("history");
        ArrayList<String> recycled_items = recycleHistory_DB.getRecycledItems();
        if (recycled_items.size() != 0) {
            // history_textview.setText("");
            history_textview.setText("Recycled item\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tTimes recycled\n\n");
            for (int i = 0; i < recycled_items.size(); i++) {
                // TODO get the number of times an item has been recycled and append it here
                history_textview.setText(history_textview.getText() + recycled_items.get(i) + "\n");
            }
        }

        recycleHistory_DB.close("history");

        // if getRecycledElementsNumber() > 0
        // query for each item
        // setText(getText() + the new item)

    }
}
