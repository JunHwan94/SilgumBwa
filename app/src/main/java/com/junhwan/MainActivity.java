package com.junhwan;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    public static final String NAVER = "naver";
    public static final String DAUM = "daum";
    public static final String KEY = "key";
    private ViewPager pager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = findViewById(R.id.viewPager);

        setPortalFragment();

        
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