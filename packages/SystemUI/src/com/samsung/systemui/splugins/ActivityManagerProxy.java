package com.samsung.systemui.splugins;

import android.app.ActivityManager;
import android.app.UserSwitchObserver;
import android.os.RemoteException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ActivityManagerProxy {
    private static String TAG = "ActivityManagerProxyImpl";
    int mUserId = -10000;
    UserSwitchObserver mUserSwitchObserver = new UserSwitchObserver() { // from class: com.samsung.systemui.splugins.ActivityManagerProxy.1
        public void onUserSwitchComplete(int i) throws RemoteException {
            ActivityManagerProxy.this.mUserId = i;
            int i2 = ActivityManagerProxy.this.mUserId;
        }
    };

    private void register() {
        try {
            ActivityManager.getService().registerUserSwitchObserver(this.mUserSwitchObserver, TAG);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }

    public synchronized int getCurrentUser() {
        try {
            if (this.mUserId == -10000) {
                register();
                this.mUserId = ActivityManager.getCurrentUser();
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.mUserId;
    }

    public void unregister() {
        try {
            ActivityManager.getService().unregisterUserSwitchObserver(this.mUserSwitchObserver);
        } catch (RemoteException e) {
            e.rethrowAsRuntimeException();
        }
    }
}
