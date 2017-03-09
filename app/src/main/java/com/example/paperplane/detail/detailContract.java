package com.example.paperplane.detail;

import android.webkit.WebView;

import com.example.paperplane.homepage.ZhihuDailyContract;
import com.example.paperplane.util.BasePresenter;
import com.example.paperplane.util.BaseView;

/**
 * Created by liuht on 2017/3/7.
 */

public class detailContract {
    interface View extends BaseView<Presenter> {

        void showLoading();
        void stopLoading();
        void showLoadingError();
        void showSharignError();
        void showResult(String result);
        void showResultWithoutBody(String url);
        void showCover(String url);
        void setTitle(String title);
        void setImageMode(boolean showImage);
        void showBrowserNotFoundError();
        void showTextCopied();
        void showCopyTextError();
        void showAddedToBookmarks();
        void showDeletedFromBookmarks();
    }
    interface Presenter extends BasePresenter {
        void openInBrowser();
        void shareAsText();
        void openUrl(WebView webView, String url);
        void copyText();
        void copyLink();
        void addToOrDeleteFromBookmarks();
        boolean queryIfIsBookmarked();
        void requestData();
    }
}
