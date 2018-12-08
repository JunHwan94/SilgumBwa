package com.junhwan;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import static com.junhwan.MainActivity.DAUM;
import static com.junhwan.MainActivity.KEY;
import static com.junhwan.MainActivity.NAVER;
import static com.junhwan.NetworkStatus.TYPE_CONNECTED;

public class PortalFragment extends Fragment {
    static final String URL_NAVER = "https://www.naver.com/index.html";
    static final String URL_DAUM = "https://www.daum.net";
    ListView listView;
    String portal;
    SearchWordAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.portal_fragment, container, false);

        Toast.makeText(this.getContext(), R.string.first_toast, Toast.LENGTH_SHORT).show();

        TextView portalTextView = rootView.findViewById(R.id.portalTextView);
        listView = rootView.findViewById(R.id.listView);

        LinearLayout linearLayout = rootView.findViewById(R.id.linearLayout);
        ImageButton refreshButton = rootView.findViewById(R.id.refreshButton);

        if(bundle != null){
            portal = bundle.getString(KEY);

            String targetUrl = null;
            switch(portal){
                case NAVER:
                    portalTextView.setText(R.string.naver);
                    portalTextView.setBackgroundColor(getResources().getColor(R.color.colorNaver));
                    portalTextView.setTextColor(Color.WHITE);
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.colorNaver));
                    targetUrl = URL_NAVER;

                    requestSearchWords(targetUrl);
                    break;

                case DAUM:
                    portalTextView.setText(R.string.daum);
                    portalTextView.setBackgroundColor(getResources().getColor(R.color.colorDaum));
                    portalTextView.setTextColor(Color.WHITE);
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.colorDaum));
                    targetUrl = URL_DAUM;

                    requestSearchWords(targetUrl);
                    break;
            }

            final String url = targetUrl;
            refreshButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int netStat = NetworkStatus.getConnectivityStatus(getContext().getApplicationContext());
                    if(netStat == TYPE_CONNECTED) {
                        requestSearchWords(url);
                    } else{
                        Toast.makeText(getContext(), R.string.refresh_fail, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        return rootView;
    }

    public void requestSearchWords(final String targetUrl){
        StringRequest request = new StringRequest(
                Request.Method.GET,
                targetUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        processResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("응답 결과", "에러");
                    }
                }
        );

        request.setShouldCache(false);
        HttpHelper.getInstance(this.getContext()).addToRequestQueue(request);
    }

    public void processResponse(String response){;
        String[] splitResponse = response.split("\n");

        String searchWord;
        SearchWordItem item;
        adapter = new SearchWordAdapter(this.getContext(), R.id.listView);

        int i;
        switch(portal){
            case NAVER:
                i = 0;
                for(String s : splitResponse){
                    if(s.contains("span class=\"ah_k")){
                        searchWord = s.split("ah_k\">")[1].split("<")[0];
                        String rank = Integer.toString(i + 1);
                        item = new SearchWordItem(rank, searchWord, R.color.colorNaver);

                        adapter.addItem(item);

                        i++;
                        if(i==20) break;
                    }
                }
                break;

            case DAUM:
                i = 0;
                for (String s : splitResponse) {
                    if (s.contains("tabindex=\"-1")) {
                        searchWord = s.split("-1\">")[1].split("<")[0];
                        String rank = Integer.toString(i + 1);
                        item = new SearchWordItem(rank, searchWord, R.color.colorDaum);

                        adapter.addItem(item);

                        i++;
                        if (i == 10) break;
                    }
                }
                break;
        }

        if(listView.getAdapter() != null){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), R.string.refresh_done, Toast.LENGTH_SHORT).show();
                }
            }, 300);
        }

        if(adapter != null) listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = null;
                String searchWord = adapter.getItem(position).getSearchWord();
                switch(portal){
                    case NAVER:
                        url = "https://search.naver.com/search.naver?where=nexearch&sm=top_sly.hst&fbm=1&acr=2&ie=utf8&query="
                                + searchWord;
                        break;
                    case DAUM:
                        url = "https://search.daum.net/search?w=tot&DA=YZR&t__nil_searchbox=btn&sug=&sugo=&q="
                                + searchWord;
                        break;
                }

                if(url != null) {
                    CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                    builder.setToolbarColor(getContext().getColor(R.color.colorPrimary));

                    CustomTabsIntent intent = builder.build();
                    intent.launchUrl(getContext(), Uri.parse(url));
                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
