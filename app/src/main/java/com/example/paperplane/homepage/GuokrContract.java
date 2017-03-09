package com.example.paperplane.homepage;

import com.example.paperplane.bean.GuokrHandpickNews;
import com.example.paperplane.util.BasePresenter;
import com.example.paperplane.util.BaseView;

import java.util.ArrayList;

/**
 * Created by liuht on 2017/3/8.
 */

public interface GuokrContract {
    interface View extends BaseView<Presenter> {
        void showError();

        void showResults(ArrayList<GuokrHandpickNews.result> list);

        void showLoading();

        void stopLoading();
    }
    interface Presenter extends BasePresenter {
        void loadPosts();

        void refresh();

        void startReading(int position);

        void feelLucky();
    }
}
