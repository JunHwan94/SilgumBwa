package com.junhwan;

import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

import static com.junhwan.NetworkStatus.TYPE_NOT_CONNECTED;

public class MainActivity extends AppCompatActivity{
    public static final String NAVER = "naver";
    public static final String DAUM = "daum";
    public static final String KEY = "key";
    private ViewPager pager;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int netStat = NetworkStatus.getConnectivityStatus(getApplicationContext());
        if(netStat == TYPE_NOT_CONNECTED){
            Toast.makeText(this, "인터넷에 연결되어있지 않습니다\n앱을 종료합니다", Toast.LENGTH_SHORT).show();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 1000);
        }
        else {
            pager = findViewById(R.id.viewPager);

            setPortalFragment();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MobileAds.initialize(getBaseContext(), "ca-app-pub-4270520106393473~1961853823");
                        }
                    });
                }
            }).start();

            AdView adView = findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
        }
    }

    public void setPortalFragment(){
        PortalPagerAdapter adapter = new PortalPagerAdapter(getSupportFragmentManager());
        Fragment portalFragment;
        String[] portal = new String[]{NAVER, DAUM};
        Bundle bundle;

        for(int i = 0; i < portal.length; i++){
            portalFragment = new PortalFragment();
            bundle = new Bundle();
            bundle.putString(KEY, portal[i]);
            portalFragment.setArguments(bundle);
            adapter.addItem(portalFragment);
        }

        pager.setAdapter(adapter);
    }

    static class PortalPagerAdapter extends FragmentStatePagerAdapter{
        List<Fragment> items = new ArrayList<>();

        public PortalPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addItem(Fragment item){
            items.add(item);
        }
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return super.getPageTitle(position);
        }

        @Override
        public Fragment getItem(int position) {
            return items.get(position);
        }

        @Override
        public int getCount() {
            return items.size();
        }
    }
}