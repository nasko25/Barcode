package com.cre8.atanaspashov.barcode;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ScreenSlidePageFragment extends Fragment {
    static TextView barcodeResult, error;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.activity_recycle_history, container, false);

        barcodeResult = rootView.findViewById(R.id.on_barcode_result);
        error = rootView.findViewById(R.id.error_textview);
        /* RecycleHistory rc = new RecycleHistory();
        TextView history_textview = rootView.findViewById(R.id.recycle_history_textview);
        TextView history_textview2 = rootView.findViewById(R.id.history_textview2);
        // TODO extract as a method ?
        AccessDatabase recycleHistory_DB = AccessDatabase.getDatabaseInstance(rc);
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
        rc.finish(); */
        return rootView;
    }
    public static void onTextChange(String text) {
        barcodeResult.setText(text);
    }
    public static void onError(String text) {
        error.setText(text);
    }
}