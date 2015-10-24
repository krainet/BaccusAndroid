package com.develjitsu.baccus.controller.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.develjitsu.baccus.R;
import com.develjitsu.baccus.controller.activity.WineryActivity;
import com.develjitsu.baccus.model.Wine;
import com.develjitsu.baccus.model.Winery;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WineListFragment extends Fragment {

    private OnWineSelectedListener mOnWineSelectedListener=null;
    private ProgressDialog mProgressDialog=null;

    public WineListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_wine_list, container, false);

        //no-async Winery winery = Winery.getInstance();

        //Async task
        AsyncTask<Void,Void,Winery> wineryDownloader = new AsyncTask<Void, Void, Winery>() {
            @Override
            protected Winery doInBackground(Void... params) {
                return Winery.getInstance();
            }

            @Override
            protected void onPostExecute(Winery winery) {
                super.onPostExecute(winery);
                //ArrayAdapter<Wine> adapter = new ArrayAdapter<Wine>(getActivity(), android.R.layout.simple_list_item_1, winery.getWineList());

                //Usamos adaptador propio para tener lineas personalizadas en la tableview
                WineListAdapter adapter = new WineListAdapter(getActivity(),winery.getWineList());
                ListView listView = (ListView) root.findViewById(android.R.id.list);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (mOnWineSelectedListener != null) {
                            mOnWineSelectedListener.onWineSelected(position);
                        }
                    }
                });

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

    class WineListAdapter extends ArrayAdapter<Wine> {
        public WineListAdapter(Context context, List<Wine> wineList){
            super(context,R.layout.list_item_wine,wineList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //View wineRow = super.getView(position, convertView, parent);

            View wineRow = inflater.inflate(R.layout.list_item_wine,parent,false);
            ImageView wineImage = (ImageView) wineRow.findViewById(R.id.wine_image);
            TextView  wineName = (TextView) wineRow.findViewById(R.id.wine_name);
            TextView  wineCompany = (TextView) wineRow.findViewById(R.id.wine_company);

            Wine currentWine = getItem(position);

            try {
                wineImage.setImageBitmap(currentWine.getPhoto(getActivity()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            wineName.setText(currentWine.getName());
            wineCompany.setText(currentWine.getCompanyName());

            Typeface tf = Typeface.createFromAsset(getContext().getAssets(),"Valentina-Regular.otf");
            wineName.setTypeface(tf);
            wineCompany.setTypeface(tf);

            return wineRow;
        }
    }
}
