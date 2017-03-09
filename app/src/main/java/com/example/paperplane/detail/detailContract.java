package com.example.paperplane.detail;

import android.webkit.WebView;

import com.example.paperplane.BasePresenter;
import com.example.paperplane.BaseView;

/**
 * Created by liyanli on 2017/3/7.
 */

public class detailContract {
    interface View extends BaseView<Presenter> {

        void showLoading();
        void stopLoading();
        void showLoadingError();
        void showSharingError();
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
