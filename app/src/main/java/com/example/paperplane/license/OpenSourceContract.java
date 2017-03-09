package com.example.paperplane.license;

import com.example.paperplane.BasePresenter;
import com.example.paperplane.BaseView;

/**
 * Created by liyanli on 2017/3/8.
 */

public interface OpenSourceContract {

    interface View extends BaseView<Presenter> {
        void loadLicense(String path);
    }
    interface Presenter extends BasePresenter {

    }
}
