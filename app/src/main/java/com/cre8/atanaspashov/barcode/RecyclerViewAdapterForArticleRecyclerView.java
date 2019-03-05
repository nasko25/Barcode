package com.cre8.atanaspashov.barcode;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapterForArticleRecyclerView extends RecyclerView.Adapter<RecyclerViewAdapterForArticleRecyclerView.ViewHolder>{

    private ArrayList<DataProviderForArticleRV> data;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView title;
        public TextView text;
        public ViewHolder(View v) {
            super(v);
            img = v.findViewById(R.id.image);
            title = v.findViewById(R.id.title);
            text = v.findViewById(R.id.body);
        }
    }

    public RecyclerViewAdapterForArticleRecyclerView(ArrayList<DataProviderForArticleRV> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterForArticleRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_list, parent, false);

        RecyclerViewAdapterForArticleRecyclerView.ViewHolder vh = new RecyclerViewAdapterForArticleRecyclerView.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerViewAdapterForArticleRecyclerView.ViewHolder holder, int position) {
        // - get element from the dataset at this position
        // - replace the contents of the view with that element
        //DataProvider dp = dataset.get(position);
        //holder.material.setText(dp.getMaterial());
      //  holder.count.setText(dp.getTimes_recycled());
        DataProviderForArticleRV provider = data.get(position);
        // holder.img.setImageResource(R.drawable.img1);
        holder.title.setText(provider.getTitle());
        holder.text.setText(provider.getText());
        holder.img.setImageResource(provider.getImage());
    }

    @Override
    public int getItemCount() {
       return data.size();
    }



}
