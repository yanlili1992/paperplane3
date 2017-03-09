package com.example.paperplane.settings;

import android.preference.Preference;

import com.example.paperplane.BasePresenter;
import com.example.paperplane.BaseView;

/**
 * Created by liyanli on 2017/3/8.
 */

public interface SettingsContract {

    interface View extends BaseView<Presenter> {
    void showCleanGlideCacheDone();
    }
    interface Presenter extends BasePresenter {
        void setNoPictureMode(Preference preference);
        void setInAppBrowser(Preference preference);
        void cleanGlideCache();
        void setTimeOfSavingArticles(Preference preference, Object newValue);
        String getTimeSummary();
    }
}
