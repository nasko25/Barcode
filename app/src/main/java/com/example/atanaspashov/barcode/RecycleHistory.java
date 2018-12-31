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
        TextView history_textview2 = findViewById(R.id.history_textview2);
        // TODO extract as a method ?
        AccessDatabase recycleHistory_DB = AccessDatabase.getDatabaseInstance(this);
        recycleHistory_DB.open("history");

        ArrayList<String> recycled_items = recycleHistory_DB.getRecycledItems();
        ArrayList<String> recycled_items_count = recycleHistory_DB.getRecycledItemsNumbers();
        if (recycled_items.size() != 0) {
            // history_textview.setText("");
            history_textview.setText("Recycled item\n\n");
            history_textview2.setText("Times recycled\n\n");
            for (int i = 0; i < recycled_items.size(); i++) {
                history_textview.setText(history_textview.getText() + recycled_items.get(i) + "\n");
                history_textview2.setText(history_textview2.getText() + recycled_items_count.get(i) + "\n");
            }
        }

        recycleHistory_DB.close("history");

        // TODO add a third text view that will only appear when there is no data in the database (and in this case the other two text views will disappear), and it will say @string/text
        // if getRecycledElementsNumber() > 0
        // query for each item
        // setText(getText() + the new item)

    }
}
