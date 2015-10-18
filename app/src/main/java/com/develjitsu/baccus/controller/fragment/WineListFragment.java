package com.develjitsu.baccus.controller.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.develjitsu.baccus.R;
import com.develjitsu.baccus.controller.activity.WineryActivity;
import com.develjitsu.baccus.model.Wine;
import com.develjitsu.baccus.model.Winery;

/**
 * A simple {@link Fragment} subclass.
 */
public class WineListFragment extends Fragment {

    private OnWineSelectedListener mOnWineSelectedListener=null;

    public WineListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Winery winery = Winery.getInstance();
        View root = inflater.inflate(R.layout.fragment_wine_list, container, false);
        Log.v("RAMON","entramos en list fragment");

        ListView listView = (ListView) root.findViewById(android.R.id.list);
        ArrayAdapter<Wine> adapter = new ArrayAdapter<Wine>(getActivity(), android.R.layout.simple_list_item_1, winery.getWineList());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/*                Intent wineryIntent = new Intent(getActivity(), WineryActivity.class);
                wineryIntent.putExtra(WineryActivity.EXTRA_WINE_INDEX,position);
                startActivity(wineryIntent);*/
                if(mOnWineSelectedListener!=null){
                    mOnWineSelectedListener.onWineSelected(position);
                }
            }
        });

        return root;
    }

    public interface OnWineSelectedListener {
        void onWineSelected(int wineIndex);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnWineSelectedListener = (OnWineSelectedListener)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnWineSelectedListener = null;
    }
}
