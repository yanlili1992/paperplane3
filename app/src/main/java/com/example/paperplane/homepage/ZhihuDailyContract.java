package com.example.paperplane.homepage;

import com.example.paperplane.BasePresenter;
import com.example.paperplane.BaseView;
import com.example.paperplane.bean.ZhihuDailyNews;

import java.util.ArrayList;

/**
 * Created by liyanli on 2017/3/8.
 */

public interface ZhihuDailyContract {
    interface View extends BaseView<Presenter> {
        void showError();
        void showLoading();
        void stopLoading();
        void showResults(ArrayList<ZhihuDailyNews.Question> list);
    }
    interface Presenter extends BasePresenter {
        void loadPosts(long date, boolean clearing);
        void refresh();
        void loadMore(long date);
        void startReading(int position);
        void feelLucky();
    }
}
