package com.example.paperplane.homepage;

import com.example.paperplane.BasePresenter;
import com.example.paperplane.BaseView;
import com.example.paperplane.bean.DoubanMomentNews;

import java.util.ArrayList;

/**
 * Created by liyanli on 2017/3/8.
 */

public interface DoubanContract {
    interface View extends BaseView<Presenter> {
        void startLoading();

        void stopLoading();

        void showLoadingError();

        void showResults(ArrayList<DoubanMomentNews.posts> list);

    }
    interface Presenter extends BasePresenter {
        void startReading(int position);

        void loadPosts(long date, boolean clearing);

        void refresh();

        void loadMore(long date);

        void feelLucky();
    }
}
