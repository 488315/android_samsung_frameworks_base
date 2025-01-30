package com.android.server.biometrics.sensors;

import android.app.IActivityManager;
import android.app.SynchronousUserSwitchObserver;
import android.os.RemoteException;
import android.util.Slog;
import android.util.SparseArray;

/* loaded from: classes.dex */
public class SensorList {
    public final IActivityManager mActivityManager;
    public final SparseArray mSensors = new SparseArray();

    public SensorList(IActivityManager iActivityManager) {
        this.mActivityManager = iActivityManager;
    }

    public void addSensor(int i, Object obj, int i2, SynchronousUserSwitchObserver synchronousUserSwitchObserver) {
        this.mSensors.put(i, obj);
        Slog.w("SensorList", "registerUserSwitchObserver");
        registerUserSwitchObserver(i2, synchronousUserSwitchObserver);
    }

    public final void registerUserSwitchObserver(int i, SynchronousUserSwitchObserver synchronousUserSwitchObserver) {
        try {
            this.mActivityManager.registerUserSwitchObserver(synchronousUserSwitchObserver, "SensorList");
            if (i == -10000) {
                synchronousUserSwitchObserver.onUserSwitching(0);
            }
        } catch (RemoteException unused) {
            Slog.e("SensorList", "Unable to register user switch observer");
        }
    }

    public Object valueAt(int i) {
        return this.mSensors.valueAt(i);
    }

    public Object get(int i) {
        return this.mSensors.get(i);
    }

    public int keyAt(int i) {
        return this.mSensors.keyAt(i);
    }

    public int size() {
        return this.mSensors.size();
    }

    public boolean contains(int i) {
        return this.mSensors.contains(i);
    }
}
