package com.example.paperplane.innerbrowser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.paperplane.R;

/**
 * Created by liyanli on 2017/3/8.
 */

public class InnerBrowserActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);

        getSupportFragmentManager().beginTransaction().replace(R.id.container,InnerBrowserFragment.getInstance()).commit();
    }
}
