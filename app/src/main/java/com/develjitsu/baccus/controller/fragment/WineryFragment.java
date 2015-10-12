package com.develjitsu.baccus.controller.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.develjitsu.baccus.R;
import com.develjitsu.baccus.model.Wine;

/**
 * Created by hadock on 12/10/15.
 *
 */
public class WineryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_winery, container, false);

        //Creamos models
        Wine bembibre = Wine.getDumyWine(1);
        Wine vegaval = Wine.getDumyWine(2);

        //Referencia al tabHost de Fragment
        FragmentTabHost tabHost = (FragmentTabHost) root.findViewById(android.R.id.tabhost);
        //Seteamos el content
        tabHost.setup(getActivity(), getActivity().getSupportFragmentManager(), android.R.id.tabcontent);



        Bundle arguments = new Bundle();

        arguments.putSerializable(WineFragment.ARG_WINE,bembibre);
        tabHost.addTab(tabHost.newTabSpec(bembibre.getName()).setIndicator(bembibre.getName()), WineFragment.class, arguments);

        arguments = new Bundle();
        arguments.putSerializable(WineFragment.ARG_WINE,vegaval);
        tabHost.addTab(tabHost.newTabSpec(vegaval.getName()).setIndicator(vegaval.getName()),WineFragment.class,arguments);

        return root;
    }
}
