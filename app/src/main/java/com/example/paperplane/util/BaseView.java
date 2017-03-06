package com.example.paperplane.util;

import android.view.View;

/**
 * Created by liuht on 2017/3/6.
 */

public interface BaseView <T>{
    /**
     * set the presenter of mvp
     * @param presenter
     */
    void setPresenter(T presenter);

    /**
     * init the views of fragment
     * @param view
     */
    void initViews(View view);
}
