package com.android.server.knox.zt.usertrust;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.view.MotionEvent;
import com.samsung.android.knox.zt.usertrust.IAuthTouchEventListener;

/* loaded from: classes6.dex */
public class AuthFactorTouchManager {
    private static final String TAG = "AuthFactorTouchManager";
    private static AuthFactorTouchManager mAuthFactorTouchManager;
    private Context mContext;
    private PackageManager packageManager;
    private final RemoteCallbackList<IAuthTouchEventListener> mAuthTouchEventListener = new RemoteCallbackList<>();
    private boolean isServiceConnected = false;
    private boolean isEnableListenerRegistered = false;

    private void connectService() {
    }

    public boolean setTouchEvent(boolean z, boolean z2) {
        return true;
    }

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

    public boolean isServiceConnected() {
        return this.isServiceConnected;
    }

    public boolean isEnableListenerRegistered() {
        return this.isEnableListenerRegistered;
    }

    public boolean registerAuthTouchEventListener(IAuthTouchEventListener iAuthTouchEventListener) {
        Log.d(TAG, "registerAuthTouchEventListener: " + iAuthTouchEventListener);
        if (iAuthTouchEventListener == null) {
            return false;
        }
        this.mAuthTouchEventListener.register(iAuthTouchEventListener);
        return true;
    }

    public boolean unregisterAuthTouchEventListener(IAuthTouchEventListener iAuthTouchEventListener) {
        Log.d(TAG, "unregisterAuthTouchEventListener " + iAuthTouchEventListener);
        if (iAuthTouchEventListener == null) {
            return false;
        }
        this.mAuthTouchEventListener.unregister(iAuthTouchEventListener);
        return true;
    }

    public void onPointerEvent(MotionEvent motionEvent) {
        for (int beginBroadcast = this.mAuthTouchEventListener.beginBroadcast() - 1; beginBroadcast >= 0; beginBroadcast--) {
            try {
                this.mAuthTouchEventListener.getBroadcastItem(beginBroadcast).onPointerEvent(motionEvent);
            } catch (RemoteException e) {
                Log.e(TAG, "Failed to notify AuthTouchEventListener", e);
            }
        }
        this.mAuthTouchEventListener.finishBroadcast();
    }

    private boolean isServiceInstalled(ComponentName componentName) {
        try {
            this.packageManager.getServiceInfo(componentName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }
}
