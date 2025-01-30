package com.android.server.biometrics.sensors.face.aidl;

import android.hardware.biometrics.face.ISession;
import android.hardware.keymaster.HardwareAuthToken;
import com.android.server.biometrics.sensors.face.aidl.Sensor;

/* loaded from: classes.dex */
public class AidlSession {
    public final int mHalInterfaceVersion;
    public final Sensor.HalSessionCallback mHalSessionCallback;
    public final ISession mSession;
    public final int mUserId;

    public boolean hasContextMethods() {
        return false;
    }

    public AidlSession(int i, ISession iSession, int i2, Sensor.HalSessionCallback halSessionCallback) {
        this.mHalInterfaceVersion = i;
        this.mSession = iSession;
        this.mUserId = i2;
        this.mHalSessionCallback = halSessionCallback;
    }

    public ISession getSession() {
        return this.mSession;
    }

    public int getUserId() {
        return this.mUserId;
    }

    public Sensor.HalSessionCallback getHalSessionCallback() {
        return this.mHalSessionCallback;
    }

    public void resetLockout(HardwareAuthToken hardwareAuthToken) {
        Sensor.HalSessionCallback halSessionCallback = this.mHalSessionCallback;
        if (halSessionCallback != null) {
            halSessionCallback.onLockoutCleared();
        }
    }
}
