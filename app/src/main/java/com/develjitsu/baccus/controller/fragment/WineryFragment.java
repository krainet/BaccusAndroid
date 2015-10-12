package com.develjitsu.baccus.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.develjitsu.baccus.R;
import com.develjitsu.baccus.controller.adapter.WineryPagerAdapter;
import com.develjitsu.baccus.model.Winery;

/**
 * Created by hadock on 12/10/15.
 *
 */
public class WineryFragment extends Fragment implements ViewPager.OnPageChangeListener {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private ViewPager mPager = null;
    private ActionBar mActionBar = null;
    private Winery mWinery = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_winery, container, false);

        mWinery= Winery.getInstance();

        mPager = (ViewPager) root.findViewById(R.id.pager);
        mPager.setAdapter(new WineryPagerAdapter(getFragmentManager()));

        //Referencia a la actionbar
        mActionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

        //Activo listener de PageView e implemento la interfaz/metodos
        mPager.setOnPageChangeListener(this);

        updateActionBar(0);


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPager.setCurrentItem(1);
        mPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPager.setCurrentItem(0);
            }
        }, 100);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        updateActionBar(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void updateActionBar(int index){
        mActionBar.setTitle(mWinery.getWine(index).getName());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }
}
