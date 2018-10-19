package com.junhwan;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static com.junhwan.MainActivity.DAUM;
import static com.junhwan.MainActivity.NAVER;

public class SearchWordItemView extends LinearLayout {
    private TextView rankTextView;
    private TextView searchWordTextView;
    private String portal;
    public static final String KEY = "1";

    public SearchWordItemView(Context context, String portal) {
        super(context);
        init(context);
        this.portal = portal;
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
