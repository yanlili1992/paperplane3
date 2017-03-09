package com.example.paperplane.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.paperplane.R;
import com.example.paperplane.bean.BeanType;

/**
 * Created by liyanli on 2017/3/7.
 */

public class DetailActivity extends AppCompatActivity{

    private detailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);

        if(savedInstanceState!=null){
            fragment = (detailFragment) getSupportFragmentManager().getFragment(savedInstanceState,"detailFragment");
        }else{
            fragment = new detailFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
        }

        Intent intent = getIntent();
        detailPresenter presenter = new detailPresenter(DetailActivity.this,fragment);

        presenter.setType((BeanType) intent.getSerializableExtra("type"));
        presenter.setId(intent.getIntExtra("id",0));
        presenter.setTitle(intent.getStringExtra("title"));
        presenter.setCoverUrl(intent.getStringExtra("coverUrl"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        if(fragment.isAdded()){
            getSupportFragmentManager().putFragment(outState,"detailFragment",fragment);
        }
    }
}
