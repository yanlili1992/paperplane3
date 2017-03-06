package com.example.paperplane.search;

import com.example.paperplane.util.BasePresenter;
import com.example.paperplane.util.BaseView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by liuht on 2017/3/6.
 */

public interface SearchContract {

    interface View extends BaseView<Presenter> {
        void showResults(ArrayList<ZhihuDailyNews.Question> zhihuList,
                         ArrayList<GuokrHandpickNews.result> guokrList,
                         ArrayList<DoubanMomentNews.posts> doubanList,
                         ArrayList<Integer> types);
        void showLoading();
        void stopLoading();
    }

    interface Presenter extends BasePresenter{
        void loadResults(String queryWords);
        void startReading(BeanType type,int position);
    }
}
