package com.android.server.biometrics.sensors;

import android.app.IActivityManager;
import android.app.SynchronousUserSwitchObserver;
import android.os.RemoteException;
import android.util.Slog;
import android.util.SparseArray;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SensorList {
    public final IActivityManager mActivityManager;
    public final SparseArray mSensors = new SparseArray();

    public SensorList(IActivityManager iActivityManager) {
        this.mActivityManager = iActivityManager;
    }

    public final void addSensor(int i, Object obj, int i2, SynchronousUserSwitchObserver synchronousUserSwitchObserver) {
        this.mSensors.put(i, obj);
        try {
            this.mActivityManager.registerUserSwitchObserver(synchronousUserSwitchObserver, "SensorList");
            if (i2 == -10000) {
                synchronousUserSwitchObserver.onUserSwitching(0);
            }
        } catch (RemoteException unused) {
            Slog.e("SensorList", "Unable to register user switch observer");
        }
    }
}
