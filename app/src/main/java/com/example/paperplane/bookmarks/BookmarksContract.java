package com.example.paperplane.bookmarks;

import com.example.paperplane.BasePresenter;
import com.example.paperplane.BaseView;
import com.example.paperplane.bean.BeanType;
import com.example.paperplane.bean.DoubanMomentNews;
import com.example.paperplane.bean.GuokrHandpickNews;
import com.example.paperplane.bean.ZhihuDailyNews;

import java.util.ArrayList;

/**
 * Created by liyanli on 2017/3/8.
 */

public interface BookmarksContract {
    interface View extends BaseView<Presenter> {

        void showResults(ArrayList<ZhihuDailyNews.Question> zhihhuList,
                         ArrayList<GuokrHandpickNews.result> guokrList,
                         ArrayList<DoubanMomentNews.posts> doubanList,
                         ArrayList<Integer> types
        );

        void notifyDataChanged();

        void showLoading();

        void stopLoading();

    }
    interface Presenter extends BasePresenter {

        void loadResults(boolean refresh);

        void startReading(BeanType type, int position);

        void checkForFreshData();

        void feelLucky();

    }
}
