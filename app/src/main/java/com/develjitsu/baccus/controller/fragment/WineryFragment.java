package com.develjitsu.baccus.controller.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    private ProgressDialog mProgressDialog=null;

    public static final String PREF_LAST_WINE_INDEX = "lastWine";

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
        final View root = inflater.inflate(R.layout.fragment_winery, container, false);

        AsyncTask<Void,Void,Winery> wineryDownloader = new AsyncTask<Void, Void, Winery>() {
            @Override
            protected Winery doInBackground(Void... params) {
                return Winery.getInstance();
            }

            @Override
            protected void onPostExecute(Winery winery) {
                super.onPostExecute(winery);
                mWinery= Winery.getInstance();
                //Referencia a la actionbar
                mActionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();

                mPager = (ViewPager) root.findViewById(R.id.pager);
                mPager.setAdapter(new WineryPagerAdapter(getFragmentManager()));

                //Activo listener de PageView e implemento la interfaz/metodos
                mPager.setOnPageChangeListener(WineryFragment.this);

                int initialWineIndex = getArguments().getInt(ARG_WINE_INDEX);
                updateActionBar(initialWineIndex);
                mPager.setCurrentItem(initialWineIndex);

                mProgressDialog.dismiss();

            }
        };

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle(getString(R.string.loading));

        if(!Winery.isInstanceAviable()){
            mProgressDialog.show();
        }

        wineryDownloader.execute();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mPager!=null) {
            mPager.setCurrentItem(1);
            mPager.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mPager.setCurrentItem(argument_wine);
                }
            }, 100);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        updateActionBar(position);
        //Forzar Prepare menu && aqui no hace falta porque damos soporte a version 8 de android
        //getActivity().invalidateOptionsMenu();

        //Salvo vino seleccionado
        PreferenceManager.getDefaultSharedPreferences(getActivity())
                .edit()
                .putInt(PREF_LAST_WINE_INDEX,position)
                .commit();
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
        if(mPager!=null){
            if(item.getItemId()== R.id.menu_next  && mPager.getCurrentItem()<mWinery.getWineCount()-1){
                mPager.setCurrentItem(mPager.getCurrentItem()+1);
                return true;
            }
            else if(item.getItemId()==R.id.menu_prev && mPager.getCurrentItem()>0){
                mPager.setCurrentItem(mPager.getCurrentItem()-1);
                return true;
            }else{
                return super.onOptionsItemSelected(item);
            }
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(mPager!=null){
            MenuItem menuNext = menu.findItem(R.id.menu_next);
            MenuItem menuPrevious = menu.findItem(R.id.menu_prev);
            menuNext.setEnabled(mPager.getCurrentItem()<mWinery.getWineCount()-1);
            menuPrevious.setEnabled(mPager.getCurrentItem() > 0);
        }
    }

    public void changeWine(int wineIndex){
        mPager.setCurrentItem(wineIndex);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mPager!=null){
            getArguments().putInt(ARG_WINE_INDEX,mPager.getCurrentItem());
        }
    }
}
