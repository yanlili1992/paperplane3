package com.example.paperplane.license;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.paperplane.R;

/**
 * Created by liyanli on 2017/3/8.
 */

public class OpenSourceLicenseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);

        OpenSourceFragment fragment = OpenSourceFragment.newInstance();

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.container, fragment)
                .commit();

    }

}