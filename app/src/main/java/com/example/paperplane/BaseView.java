package com.example.paperplane;

import android.view.View;

/**
 * Created by liyanli on 2017/3/9.
 */

public interface BaseView <T>{

    void setPresenter(T presenter);


    void initViews(View view);
}