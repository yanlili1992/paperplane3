package com.example.paperplane.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.paperplane.R;

/**
 * Created by liuht on 2017/3/6.
 */

public class SearchActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);

        SearchFragment fragment = SearchFragment.newInstance();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.container,fragment)
                .commit();

        new SearchPresenter(this,fragment);
    }
}
