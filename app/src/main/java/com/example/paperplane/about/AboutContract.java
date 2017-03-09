package com.example.paperplane.about;

import com.example.paperplane.BasePresenter;
import com.example.paperplane.BaseView;

/**
 * Created by liyanli on 2017/3/7.
 */

public interface AboutContract {
    interface View extends BaseView<Presenter> {
        void showRateError();
        void showFeedbackError();
        void showBrowserNotFoundError();
    }
    interface Presenter extends BasePresenter {
        void rate();
        void openLicense();
        void followOnGithub();
        void followOnZhihu();
        void feedback();
        void donate();
        void showEasterEgg();
    }
}
