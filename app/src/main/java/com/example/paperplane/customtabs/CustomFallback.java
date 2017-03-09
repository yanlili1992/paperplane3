package com.example.paperplane.customtabs;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.example.paperplane.innerbrowser.InnerBrowserActivity;

/**
 * Created by liuht on 2017/3/8.
 */

public class CustomFallback implements CustomTabActivityHelper.CustomTabFallback{

    @Override
    public void openUri(Activity activity,Uri uri){
        activity.startActivity(new Intent(activity, InnerBrowserActivity.class).putExtra("url",uri.toString()));
    }
}
