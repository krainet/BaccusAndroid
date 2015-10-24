package com.develjitsu.baccus.controller.fragment;


import android.app.ExpandableListActivity;
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
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.develjitsu.baccus.R;
import com.develjitsu.baccus.controller.activity.WineryActivity;
import com.develjitsu.baccus.model.Wine;
import com.develjitsu.baccus.model.Winery;

import java.io.IOException;
import java.util.List;

import static com.develjitsu.baccus.R.id.wine_type;

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
            protected void onPostExecute(final Winery winery) {
                super.onPostExecute(winery);
                //ArrayAdapter<Wine> adapter = new ArrayAdapter<Wine>(getActivity(), android.R.layout.simple_list_item_1, winery.getWineList());

                //Usamos adaptador propio para tener lineas personalizadas en la tableview
                WineListAdapter adapter = new WineListAdapter(getActivity(),winery);
                ExpandableListView listView = (ExpandableListView) root.findViewById(android.R.id.list);
                listView.setAdapter(adapter);

                listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        int index = winery.getAbsolutePosition(Winery.WineType.values()[groupPosition],childPosition);
                        if (mOnWineSelectedListener != null) {
                            mOnWineSelectedListener.onWineSelected(index);
                        }
                        return true;
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

    // ahora heredamos de baseexpandablelist adapter para poder usarlo en las categorias
    // class WineListAdapter extends ArrayAdapter<Wine> {
    class WineListAdapter extends BaseExpandableListAdapter {
        private Context mContext = null;
        private Winery mWinery = null;

        public WineListAdapter(Context context, Winery winery){
            mContext=context;
            mWinery=winery;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //View wineRow = super.getView(position, convertView, parent);

            View wineRow = inflater.inflate(R.layout.list_item_wine,parent,false);
            ImageView wineImage = (ImageView) wineRow.findViewById(R.id.wine_image);
            TextView  wineName = (TextView) wineRow.findViewById(R.id.wine_name);
            TextView  wineCompany = (TextView) wineRow.findViewById(R.id.wine_company);

            Wine currentWine = getChild(groupPosition,childPosition);

            try {
                wineImage.setImageBitmap(currentWine.getPhoto(getActivity()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            wineName.setText(currentWine.getName());
            wineCompany.setText(currentWine.getCompanyName());

            Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "Valentina-Regular.otf");
            wineName.setTypeface(tf);
            wineCompany.setTypeface(tf);

            return wineRow;
        }

        @Override
        public int getGroupCount() {
            return Winery.WineType.values().length;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return mWinery.getWineCount(getGroup(groupPosition));
        }

        @Override
        public Winery.WineType getGroup(int groupPosition) {
            return Winery.WineType.values()[groupPosition];
        }

        @Override
        public Wine getChild(int groupPosition, int childPosition) {
            return mWinery.getWine(getGroup(groupPosition),childPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View wineHeader = inflater.inflate(R.layout.list_item_wine_header,parent,false);

            TextView headerText = (TextView) wineHeader.findViewById(R.id.wine_type);
            Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "Valentina-Regular.otf");
            headerText.setTypeface(tf);

            if(getGroup(groupPosition)==Winery.WineType.RED){
                headerText.setText(R.string.red);
            }else if(getGroup(groupPosition)==Winery.WineType.ROSE){
                headerText.setText(R.string.rose);
            }else if(getGroup(groupPosition)==Winery.WineType.WHITE){
                headerText.setText(R.string.white);
            }else{
                headerText.setText(R.string.other);
            }

            return wineHeader;
        }


        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
