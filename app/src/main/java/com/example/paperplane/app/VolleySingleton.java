package com.example.paperplane.app;

import android.content.Context;

/**
 * Created by liuht on 2017/3/6.
 */

public class VolleySingleton {
    private static VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    private VolleySingleton(Context context){
        requestQueue=Volley.newRequestQueue(context.getApplicationContext());
    }
    public static synchronized VolleySingleton getVolleySingleton(Context context){
        if(volleySingleton==null){
            volleySingleton = new VolleySingleton(context);
        }
        return volleySingleton;
    }
    public RequestQueue getRequestQueue(){
        return this.requestQueue;
    }
    public <T> void addRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }
}
