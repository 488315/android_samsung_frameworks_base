package com.android.internal.util;

import android.app.Service;
import android.content.Intent;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.Message;
import android.p009os.Messenger;
import android.util.Log;

/* loaded from: classes5.dex */
public abstract class AsyncService extends Service {
    public static final int CMD_ASYNC_SERVICE_DESTROY = 16777216;
    public static final int CMD_ASYNC_SERVICE_ON_START_INTENT = 16777215;
    protected static final boolean DBG = true;
    private static final String TAG = "AsyncService";
    AsyncServiceInfo mAsyncServiceInfo;
    Handler mHandler;
    protected Messenger mMessenger;

    public static final class AsyncServiceInfo {
        public Handler mHandler;
        public int mRestartFlags;
    }

    public abstract AsyncServiceInfo createHandler();

    public Handler getHandler() {
        return this.mHandler;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        AsyncServiceInfo createHandler = createHandler();
        this.mAsyncServiceInfo = createHandler;
        this.mHandler = createHandler.mHandler;
        this.mMessenger = new Messenger(this.mHandler);
    }

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.m94d(TAG, "onStartCommand");
        Message msg = this.mHandler.obtainMessage();
        msg.what = 16777215;
        msg.arg1 = flags;
        msg.arg2 = startId;
        msg.obj = intent;
        this.mHandler.sendMessage(msg);
        return this.mAsyncServiceInfo.mRestartFlags;
    }

    @Override // android.app.Service
    public void onDestroy() {
        Log.m94d(TAG, "onDestroy");
        Message msg = this.mHandler.obtainMessage();
        msg.what = 16777216;
        this.mHandler.sendMessage(msg);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mMessenger.getBinder();
    }
}
