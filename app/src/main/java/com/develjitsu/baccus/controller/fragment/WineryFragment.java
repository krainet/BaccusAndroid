package com.develjitsu.baccus.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    public static final String ARG_WINE_INDEX = "com.develjitsu.baccus.controller.fragment.ARG_WINE_INDEX";
    private static int argument_wine = 0;

    public static WineryFragment newInstance(int wineIndex) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_WINE_INDEX, wineIndex);
        argument_wine=wineIndex;
        WineryFragment fragment = new WineryFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

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

        int initialWineIndex = getArguments().getInt(ARG_WINE_INDEX);
        updateActionBar(initialWineIndex);
        mPager.setCurrentItem(initialWineIndex);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPager.setCurrentItem(1);
        mPager.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPager.setCurrentItem(argument_wine);
            }
        }, 100);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        updateActionBar(position);
        //Forzar Prepare menu && aqui no hace falta porque damos soporte a version 8 de android
        //getActivity().invalidateOptionsMenu();
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
        boolean superValue = super.onOptionsItemSelected(item);
        if(item.getItemId()== R.id.menu_next  && mPager.getCurrentItem()<mWinery.getWineCount()-1){
            mPager.setCurrentItem(mPager.getCurrentItem()+1);
            return true;
        }
        else if(item.getItemId()==R.id.menu_prev && mPager.getCurrentItem()>0){
            mPager.setCurrentItem(mPager.getCurrentItem()-1);
            return true;
        }else{
            return superValue;
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuNext = menu.findItem(R.id.menu_next);
        MenuItem menuPrevious = menu.findItem(R.id.menu_prev);

        menuNext.setEnabled(mPager.getCurrentItem()<mWinery.getWineCount()-1);
        menuPrevious.setEnabled(mPager.getCurrentItem() > 0);
    }

    public void changeWine(int wineIndex){
        mPager.setCurrentItem(wineIndex);
    }

}
