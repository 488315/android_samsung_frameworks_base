package com.samsung.android.battauthmanager;

import android.app.AppGlobals;
import android.content.Context;
import android.os.HandlerThread;
import android.util.Log;

public final class BattAuthManager {
    public final HandlerThread mHandlerThread;
    public final WpcAuthenticator mWpcAuthenticator;

    public BattAuthManager(Context context) {
        Log.i("BattAuthManager", "BattAuthManager start");
        try {
            boolean isFirstBoot = AppGlobals.getPackageManager().isFirstBoot();
            Log.i("BattAuthManager", "isFirstBoot : " + isFirstBoot);
            if (this.mWpcAuthenticator == null) {
                HandlerThread handlerThread = new HandlerThread("BattAuthManager");
                this.mHandlerThread = handlerThread;
                handlerThread.start();
                this.mWpcAuthenticator =
                        new WpcAuthenticator(context, this.mHandlerThread.getLooper());
                if (isFirstBoot) {
                    WpcAuthenticator.removeDigests();
                }
            }
        } catch (Throwable th) {
            Log.i("BattAuthManager", "BattAuthManager error");
            th.printStackTrace();
        }
    }
}
