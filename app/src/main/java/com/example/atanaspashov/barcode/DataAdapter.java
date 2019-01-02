package com.example.atanaspashov.barcode;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<DataProvider> dataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView material;
        public TextView count;
        public ViewHolder(View v) {
            super(v);
            material = v.findViewById(R.id.material);
            count = v.findViewById(R.id.material_count);
        }
    }

    public DataAdapter(ArrayList<DataProvider> dataset) {
        this.dataset = dataset;
    }

    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_list, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from the dataset at this position
        // - replace the contents of the view with that element
        DataProvider dp = dataset.get(position);
        holder.material.setText(dp.getMaterial());
        holder.count.setText(dp.getTimes_recycled());

    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }



}
