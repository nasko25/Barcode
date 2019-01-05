package com.example.atanaspashov.barcode;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ScreenSlidePageFragmentForPage2 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.article_recycler_view, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        RecycleHistory context = new RecycleHistory();
        AccessDatabase ac = AccessDatabase.getDatabaseInstance(context);
        ac.open("article");
        ArrayList<String> titles = ac.getTitle();
        ArrayList<String> texts = ac.getText();
        ArrayList<Integer> imagesIDs = ac.getArticleIds();
        ArrayList<DataProviderForArticleRV>  providers = new ArrayList<>();

        for (int i = 0; i < imagesIDs.size(); i++) {
            providers.add(new DataProviderForArticleRV(titles.get(i), texts.get(i), imagesIDs.get(i)));
        }

        // recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        RecyclerView.Adapter adapter  = new RecyclerViewAdapterForArticleRecyclerView(providers, context);
        recyclerView.setAdapter(adapter);

        context.finish();
        ac.close("article");
        return rootView;
    }
}