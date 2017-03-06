package com.example.paperplane.bean;

import android.content.Context;

import com.example.paperplane.app.VolleySingleton;
import com.example.paperplane.interfaze.OnStringListener;

/**
 * Created by liuht on 2017/3/6.
 */

public class StringModelImpl {
    private Context context;
    public StringModelImpl(Context context){
        this.context = context;
    }
    public void load(String url. final OnStringListener){
        StringRequest request = new StringRequest(url,new Response.Listener<String>(){
            @Override
            public void onResponse(String s){
                listener.onSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError vollryError){
                listener.onError(volleyError);
            }
        });
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        VolleySingleton.getVolleySingleton(context).addToRequestQueue(request);
    }
}
