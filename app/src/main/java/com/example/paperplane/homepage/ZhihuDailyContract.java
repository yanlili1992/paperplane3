package com.example.paperplane.homepage;

import com.example.paperplane.search.SearchContract;
import com.example.paperplane.util.BasePresenter;
import com.example.paperplane.util.BaseView;

import java.util.ArrayList;

/**
 * Created by liuht on 2017/3/6.
 */

public interface ZhihuDailyContract {
    interface View extends BaseView<SearchContract.Presenter> {
        void showError();
        void showLoading();
        void stopLoading();
        void showesults(ArrayList<ZhihuDailyNews.Question> list);
    }
    interface Presenter extends BasePresenter{
        void loadPosts(long date, boolean clearing);
        void refresh();
        void loadMore(long date);
        void startReading(int position);
        void feelLucky();
    }
}
