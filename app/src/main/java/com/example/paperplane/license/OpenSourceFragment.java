package com.example.paperplane.license;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Created by liuht on 2017/3/8.
 */

public class OpenSourceFragment extends Fragment implements OpenSourceContract.View{

    private WebView webView;
    public OpenSourceFragment() {
        // requires an empty constructor
    }

    public static OpenSourceFragment newInstance() {
        return new OpenSourceFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open_source_license, container, false);

        initViews(view);
        setHasOptionsMenu(true);

        loadLicense("file:///android_asset/license.html");

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            getActivity().onBackPressed();
        }
        return true;
    }

    @Override
    public void setPresenter(OpenSourceContract.Presenter presenter) {

    }

    @Override
    public void initViews(View view) {
        AppCompatActivity activity = (OpenSourceLicenseActivity) getActivity();
        activity.setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView = (WebView) view.findViewById(R.id.web_view);
    }

    @Override
    public void loadLicense(String path) {
        webView.loadUrl(path);
    }

}
