package com.example.paperplane.customtabs;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by liyanli on 2017/3/8.
 */

public class KeepAliveService extends Service {

    private static final Binder sBinder = new Binder();

    @Override
    public IBinder onBind(Intent intent){return sBinder;}

}
