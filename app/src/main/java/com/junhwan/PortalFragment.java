package com.junhwan;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import static com.junhwan.MainActivity.DAUM;
import static com.junhwan.MainActivity.KEY;
import static com.junhwan.MainActivity.NAVER;

public class PortalFragment extends Fragment {
    static final String URL_NAVER = "https://www.naver.com/index.html";
    static final String URL_DAUM = "https://www.daum.net";
    ListView listView;
    String portal;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.portal_fragment, container, false);

        Toast.makeText(this.getContext(), R.string.first_toast, Toast.LENGTH_LONG).show();

        TextView portalTextView = rootView.findViewById(R.id.portalTextView);
        listView = rootView.findViewById(R.id.listView);

        if(bundle != null){
            portal = bundle.getString(KEY);

            String targetUrl;
            switch(portal){
                case NAVER:
                    portalTextView.setText(R.string.naver);
                    portalTextView.setBackgroundColor(getResources().getColor(R.color.colorNaver));
                    portalTextView.setTextColor(Color.WHITE);
                    targetUrl = URL_NAVER;

                    requestSearchWords(targetUrl);
                    break;

                case DAUM:
                    portalTextView.setText(R.string.daum);
                    portalTextView.setBackgroundColor(getResources().getColor(R.color.colorDaum));
                    portalTextView.setTextColor(Color.WHITE);
                    targetUrl = URL_DAUM;

                    requestSearchWords(targetUrl);
                    break;
            }
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
        SearchWordAdapter adapter = null;

        int i;
        switch(portal){
            case NAVER:
                adapter = new SearchWordAdapter(this.getContext(), R.id.listView, portal);
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
                adapter = new SearchWordAdapter(this.getContext(), R.id.listView, portal);
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

        if(adapter != null) listView.setAdapter(adapter);
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
