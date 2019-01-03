package com.example.atanaspashov.barcode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class RecycleHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public ArrayList<String> materials, times_recycled;

    // TODO when you click on the material, it should open up a new activity that displays more information about it.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_history2);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        AccessDatabase ac = AccessDatabase.getDatabaseInstance(this);
        ac.open("history");
        materials = ac.getRecycledItems();
        times_recycled = ac.getRecycledItemsNumbers();
        ArrayList<DataProvider>  dataProviders = new ArrayList<>();
        for (int i = 0; i < materials.size(); i++) {
            dataProviders.add(new DataProvider(materials.get(i), times_recycled.get(i)));
        }


        // improves performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        adapter = new DataAdapter(dataProviders);
        recyclerView.setAdapter(adapter);
        ac.close("history");
    }
}
