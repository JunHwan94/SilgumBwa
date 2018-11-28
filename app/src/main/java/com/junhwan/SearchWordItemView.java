package com.junhwan;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchWordItemView extends LinearLayout {
    private TextView rankTextView;
    private TextView searchWordTextView;

    public SearchWordItemView(Context context) {
        super(context);
        init(context);
    }

    public SearchWordItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(final Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.searchword_item, this, true);

        rankTextView = findViewById(R.id.rankTextView);

        searchWordTextView = findViewById(R.id.wordTextView);
    }

    public void setRankTextView(String rank) {
        rankTextView.setText(rank);
    }

    public void setRankTextColor(int code){
        rankTextView.setTextColor(getResources().getColor(code));
    }

    public void setSearchWordTextView(String searchWord) {
        searchWordTextView.setText(searchWord);
    }
}
