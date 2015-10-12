package com.develjitsu.baccus.controller.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.develjitsu.baccus.R;
import com.develjitsu.baccus.model.Wine;

/**
 * Created by hadock on 12/10/15.
 *
 */
public class WebFragment extends Fragment {

    private WebView mBrowser = null;
    private ProgressBar mLoading = null;
    private static final String STATE_URL = "url";
    public static final String ARG_WINE = "com.develjitsu.baccus.controller.activity.WebFragment.ARG_WINE";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        //setContentView(R.layout.fragment_web);  //en lugar de on Createview usamos inflater
        View root = inflater.inflate(R.layout.fragment_web,container,false);


        //En lugar de getIntent se usa GetArguments y el prefix es ARG_ en lugar de EXTRA_
        //Wine dummyWine = (Wine) getIntent().getSerializableExtra(EXTRA_WINE);
        Wine dummyWine = (Wine) getArguments().getSerializable(ARG_WINE);

        //Asociamos vista y controlador
        //para el findViewById ponemos delante la vista (root)
        mBrowser = (WebView)  root.findViewById(R.id.browser);
        mLoading = (ProgressBar) root.findViewById(R.id.loading);

        //Configuro / Sync vista-modelo
        mBrowser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mLoading.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mLoading.setVisibility(View.GONE);
            }
        });

        mBrowser.getSettings().setJavaScriptEnabled(true);
        mBrowser.getSettings().setBuiltInZoomControls(true);

        //Cargo Web
        if(savedInstanceState==null || !savedInstanceState.containsKey(STATE_URL)){
            mBrowser.loadUrl(dummyWine.getCompanyWeb());
        }else{
            mBrowser.loadUrl(savedInstanceState.getString(STATE_URL));
        }

        return root;
    }

    //En un fragment este metodo es public en lugar de protected
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(STATE_URL, mBrowser.getUrl());
    }


    //OnCreateOptionsMenu cambia en un gragment


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_web, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()== R.id.menu_reload){
            mBrowser.reload();
            return true;
        }
        return false;
    }

}
