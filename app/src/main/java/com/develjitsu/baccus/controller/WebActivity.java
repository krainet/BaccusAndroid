package com.develjitsu.baccus.controller;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.develjitsu.baccus.R;
import com.develjitsu.baccus.model.Wine;

import static com.develjitsu.baccus.R.*;

/**
 * Created by hadock on 11/10/15.
 *
 */

public class WebActivity extends AppCompatActivity{

    private WebView mBrowser = null;
    private ProgressBar mLoading = null;
    private static final String STATE_URL = "url";
    public static final String EXTRA_WINE = "wine";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(layout.activity_web);

        //Wine dummyWine = Wine.getDumyWine();
        Wine dummyWine = (Wine) getIntent().getSerializableExtra(EXTRA_WINE);

        //Asociamos vista y controlador
        mBrowser = (WebView)  findViewById(id.browser);
        mLoading = (ProgressBar) findViewById(id.loading);

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

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(STATE_URL, mBrowser.getUrl());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_web,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()== id.menu_reload){
            mBrowser.reload();
            return true;
        }
        return false;
    }
}
