package com.android.server;

import android.app.Service;
import android.content.Intent;
import android.drm.DrmManagerClient;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/* loaded from: classes5.dex */
public class DrmEventService extends Service {
    public static final String ACTION_BOOT_COMPLETED = "android.intent.action.BOOT_COMPLETED";
    private static final String TAG = "DrmEventService";
    private ServiceHandler mServiceHandler;
    private Looper mServiceLooper;
    DrmMediaResourceHelper rchelp;
    public static DrmManagerClient mDrmManagerClient = null;
    static boolean isLogEnabled = false;

    @Override // android.app.Service
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if (isLogEnabled) {
            Log.i(TAG, "onStart intent.getAction() :" + intent.getAction());
        }
        if (isLogEnabled) {
            Log.i(TAG, "DrmEventService : onStart");
        }
        Message msg = this.mServiceHandler.obtainMessage();
        msg.obj = "START_RESOURCE_HELPER";
        this.mServiceHandler.sendMessage(msg);
    }

    @Override // android.app.Service, android.content.ComponentCallbacks
    public void onLowMemory() {
        if (isLogEnabled) {
            Log.i(TAG, "DrmEventService : OnLowMemory....Save the Phone");
        }
        super.onLowMemory();
    }

    @Override // android.app.Service
    public void onDestroy() {
        if (isLogEnabled) {
            Log.i(TAG, "DrmEventService : onDestroy");
        }
        this.mServiceLooper.quit();
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        if (isLogEnabled) {
            Log.i(TAG, "DrmEventService : onBind");
            return null;
        }
        return null;
    }

    @Override // android.app.Service
    public void onCreate() {
        if (isLogEnabled) {
            Log.i(TAG, "DrmEventService : onCreate");
        }
        if (mDrmManagerClient == null) {
            mDrmManagerClient = new DrmManagerClient(this);
        }
        mDrmManagerClient.saveDevID();
        HandlerThread thread = new HandlerThread(TAG, -2);
        thread.start();
        this.mServiceLooper = thread.getLooper();
        this.mServiceHandler = new ServiceHandler(this.mServiceLooper);
    }

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (DrmEventService.isLogEnabled) {
                Log.i(DrmEventService.TAG, "DrmEventService : handleMessage msg received");
            }
            if (msg != null) {
                String action = (String) msg.obj;
                Log.i(DrmEventService.TAG, "action event :" + action);
                if (action != null) {
                    if (action.equalsIgnoreCase("START_RESOURCE_HELPER") && DrmEventService.this.rchelp == null) {
                        DrmEventService.this.rchelp = new DrmMediaResourceHelper();
                        return;
                    }
                    return;
                }
                if (DrmEventService.isLogEnabled) {
                    Log.i(DrmEventService.TAG, "Intent Action is Null :");
                }
            }
        }
    }
}
