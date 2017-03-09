package com.example.paperplane.settings;

import android.preference.Preference;

import com.example.paperplane.about.AboutContract;
import com.example.paperplane.util.BasePresenter;
import com.example.paperplane.util.BaseView;

/**
 * Created by liuht on 2017/3/8.
 */

public interface SettingsContract {

    interface View extends BaseView<Presenter> {
    void showcleanGlideCacheDone();
    }
    interface Presenter extends BasePresenter{
        void setNoPictureMode(Preference preference);
        void setInAppBrowser(Preference preference);
        void cleanGlideCache();
        void setTimeOfSavingArticles(Preference preference, Object newValue);
        String getTimeSummary();
    }
}
