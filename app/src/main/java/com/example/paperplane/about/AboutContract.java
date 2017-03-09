package com.example.paperplane.about;

import com.example.paperplane.homepage.ZhihuDailyContract;
import com.example.paperplane.util.BasePresenter;
import com.example.paperplane.util.BaseView;

/**
 * Created by liuht on 2017/3/7.
 */

public interface AboutContract {
    interface View extends BaseView<Presenter>{
        void showRateError();
        void showFeedbackError();
        void showBrowserNotFoundError();
    }
    interface Presenter extends BasePresenter{
        void rate();
        void openLicense();
        void followOnGithub();
        void followOnZhihu();
        void feedback();
        void donate();
        void showEasterEgg();
    }
}
