package com.junhwan;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import static com.junhwan.MainActivity.DAUM;
import static com.junhwan.MainActivity.NAVER;

public class SearchWordItemView extends ScrollView {
    private TextView rankTextView;
    private TextView searchWordTextView;
    private String portal;

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
        searchWordTextView.setClickable(true);
        searchWordTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = null;

                switch(portal){
                    case NAVER:
                        url = "https://search.naver.com/search.naver?where=nexearch&sm=top_sly.hst&fbm=1&acr=2&ie=utf8&query="
                                + searchWordTextView.getText().toString();
                        break;

                    case DAUM:
                        url = "https://search.daum.net/search?w=tot&DA=YZR&t__nil_searchbox=btn&sug=&sugo=&q="
                                + searchWordTextView.getText().toString();
                        break;
                }
                if(url != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.getApplicationContext().startActivity(intent);
                }
            }
        });
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
