package com.android.server.knox.zt.usertrust;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.view.MotionEvent;

import com.samsung.android.knox.zt.usertrust.IAuthTouchEventListener;

/* loaded from: classes5.dex */
public class AuthFactorTouchManager {
    private static final String TAG = "AuthFactorTouchManager";
    private static AuthFactorTouchManager mAuthFactorTouchManager;
    private Context mContext;
    private PackageManager packageManager;
    private final RemoteCallbackList<IAuthTouchEventListener> mAuthTouchEventListener =
            new RemoteCallbackList<>();
    private boolean isServiceConnected = false;
    private boolean isEnableListenerRegistered = false;

    private AuthFactorTouchManager(Context context) {
        this.mContext = context;
        this.packageManager = context.getPackageManager();
    }

    public static AuthFactorTouchManager getInstance(Context context) {
        if (mAuthFactorTouchManager == null) {
            mAuthFactorTouchManager = new AuthFactorTouchManager(context);
        }
        return mAuthFactorTouchManager;
    }

    private void connectService() {}

    public boolean isServiceConnected() {
        return this.isServiceConnected;
    }

    public boolean isEnableListenerRegistered() {
        return this.isEnableListenerRegistered;
    }

    public boolean registerAuthTouchEventListener(IAuthTouchEventListener listener) {
        Log.d(TAG, "registerAuthTouchEventListener: " + listener);
        if (listener == null) {
            return false;
        }
        this.mAuthTouchEventListener.register(listener);
        return true;
    }

    public boolean unregisterAuthTouchEventListener(IAuthTouchEventListener listener) {
        Log.d(TAG, "unregisterAuthTouchEventListener " + listener);
        if (listener == null) {
            return false;
        }
        this.mAuthTouchEventListener.unregister(listener);
        return true;
    }

    public boolean setTouchEvent(boolean ret, boolean debugMode) {
        return true;
    }

    public void onPointerEvent(MotionEvent event) {
        for (int i = this.mAuthTouchEventListener.beginBroadcast() - 1; i >= 0; i--) {
            try {
                this.mAuthTouchEventListener.getBroadcastItem(i).onPointerEvent(event);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to notify AuthTouchEventListener", e);
            }
        }
        this.mAuthTouchEventListener.finishBroadcast();
    }

    private boolean isServiceInstalled(ComponentName component) {
        try {
            this.packageManager.getServiceInfo(component, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
