package com.example.paperplane.license;

import com.example.paperplane.homepage.GuokrContract;
import com.example.paperplane.util.BasePresenter;
import com.example.paperplane.util.BaseView;

/**
 * Created by liuht on 2017/3/8.
 */

public interface OpenSourceContract {

    interface View extends BaseView<Presenter> {
        void loadLicense(String path);
    }
    interface Presenter extends BasePresenter {

    }
}
