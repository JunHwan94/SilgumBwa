package com.junhwan;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.MobileAds;

import static com.junhwan.MainActivity.DAUM;
import static com.junhwan.MainActivity.KEY;
import static com.junhwan.MainActivity.NAVER;
import static com.junhwan.NetworkStatus.TYPE_CONNECTED;

public class PortalFragment extends Fragment {
    static final String URL_NAVER = "https://www.naver.com/index.html";
    static final String URL_DAUM = "https://www.daum.net";
    ListView listView;
    String portal;
    FragmentCallBack callBack;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.portal_fragment, container, false);

        Toast.makeText(this.getContext(), R.string.first_toast, Toast.LENGTH_LONG).show();

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
                        Toast.makeText(getContext(), "새로고침 완료", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "새로고침 실패\n네트워크 연결 상태를 확인하세요", Toast.LENGTH_SHORT).show();
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
        SearchWordAdapter adapter = new SearchWordAdapter(this.getContext(), R.id.listView, portal);;

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

        if(adapter != null) listView.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(callBack == null){
            callBack = (FragmentCallBack)context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        if(callBack != null){
            callBack = null;
        }
    }
}
