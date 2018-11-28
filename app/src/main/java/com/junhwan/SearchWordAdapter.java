package com.junhwan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchWordAdapter extends ArrayAdapter{

    public SearchWordAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    List<SearchWordItem> items = new ArrayList<>();

    public void addItem(SearchWordItem item){
        items.add(item);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public SearchWordItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchWordItemView view = new SearchWordItemView(getContext());
        SearchWordItem item = items.get(position);
        view.setRankTextView(item.getRank());
        view.setRankTextColor(item.getColorCode());
        view.setSearchWordTextView(item.getSearchWord());

        return view;
    }
}