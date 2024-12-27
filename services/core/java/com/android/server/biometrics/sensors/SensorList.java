package com.android.server.biometrics.sensors;

import android.app.IActivityManager;
import android.app.SynchronousUserSwitchObserver;
import android.os.RemoteException;
import android.util.Slog;
import android.util.SparseArray;

public final class SensorList {
    public final IActivityManager mActivityManager;
    public final SparseArray mSensors = new SparseArray();

    public SensorList(IActivityManager iActivityManager) {
        this.mActivityManager = iActivityManager;
    }

    public final void addSensor(
            int i,
            Object obj,
            int i2,
            SynchronousUserSwitchObserver synchronousUserSwitchObserver) {
        this.mSensors.put(i, obj);
        try {
            this.mActivityManager.registerUserSwitchObserver(
                    synchronousUserSwitchObserver, "SensorList");
            if (i2 == -10000) {
                synchronousUserSwitchObserver.onUserSwitching(0);
            }
        } catch (RemoteException unused) {
            Slog.e("SensorList", "Unable to register user switch observer");
        }
    }
}
