package com.example.paperplane.interfaze;

import com.android.volley.VolleyError;

/**
 * Created by liuht on 2017/3/6.
 */

public interface OnStringListener {
    /**
     * 请求成功是回调
     */
    void onSuccess(String result);

    /**
     * 请求失败时回调
     */
    void onError(VolleyError error);
}
