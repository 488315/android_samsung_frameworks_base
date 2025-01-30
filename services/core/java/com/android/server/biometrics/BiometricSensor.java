package com.android.server.biometrics;

import android.content.Context;
import android.hardware.biometrics.IBiometricAuthenticator;
import android.hardware.biometrics.IBiometricSensorReceiver;
import android.os.IBinder;
import android.util.Slog;

/* loaded from: classes.dex */
public abstract class BiometricSensor {

    /* renamed from: id */
    public final int f1650id;
    public final IBiometricAuthenticator impl;
    public final Context mContext;
    public int mCookie;
    public int mError;
    boolean mIsUpdatedOemStrength;
    public boolean mIsUpdatedStrengthByDeviceConfig;
    public int mSensorState;
    public int mUpdatedStrength;
    public final int modality;
    public int oemStrength;

    public abstract boolean confirmationAlwaysRequired(int i);

    public abstract boolean confirmationSupported();

    public BiometricSensor(Context context, int i, int i2, int i3, IBiometricAuthenticator iBiometricAuthenticator) {
        this.mContext = context;
        this.f1650id = i;
        this.modality = i2;
        this.oemStrength = i3;
        this.impl = iBiometricAuthenticator;
        this.mUpdatedStrength = i3;
        goToStateUnknown();
    }

    public void goToStateUnknown() {
        this.mSensorState = 0;
        this.mCookie = 0;
        this.mError = 0;
    }

    public void goToStateWaitingForCookie(boolean z, IBinder iBinder, long j, int i, IBiometricSensorReceiver iBiometricSensorReceiver, String str, long j2, int i2, boolean z2) {
        this.mCookie = i2;
        this.impl.prepareForAuthentication(z, iBinder, j, i, iBiometricSensorReceiver, str, j2, i2, z2);
        this.mSensorState = 1;
    }

    public void goToStateCookieReturnedIfCookieMatches(int i) {
        if (i == this.mCookie) {
            Slog.d("BiometricService/Sensor", "Sensor(" + this.f1650id + ") matched cookie: " + i);
            this.mSensorState = 2;
        }
    }

    public void startSensor() {
        int i = this.mCookie;
        if (i == 0) {
            return;
        }
        this.impl.startPreparedClient(i);
        this.mSensorState = 3;
    }

    public void goToStateCancelling(IBinder iBinder, String str, long j) {
        if (this.mSensorState != 4) {
            this.impl.cancelAuthenticationFromService(iBinder, str, j);
            this.mSensorState = 4;
        }
    }

    public void goToStoppedStateIfCookieMatches(int i, int i2) {
        if (i == this.mCookie) {
            Slog.d("BiometricService/Sensor", "Sensor(" + this.f1650id + ") now in STATE_STOPPED");
            this.mError = i2;
            this.mSensorState = 5;
        }
    }

    public int getCurrentStrength() {
        updateOemStrengthIfNotCached();
        return this.mUpdatedStrength | this.oemStrength;
    }

    public int getSensorState() {
        return this.mSensorState;
    }

    public int getCookie() {
        return this.mCookie;
    }

    public void updateStrength(int i) {
        this.mUpdatedStrength = i;
        Slog.d("BiometricService/Sensor", ("updateStrength: Before(" + this + ")") + " After(" + this + ")");
        this.mIsUpdatedStrengthByDeviceConfig = true;
    }

    public String toString() {
        return "ID(" + this.f1650id + "), oemStrength: " + this.oemStrength + ", updatedStrength: " + this.mUpdatedStrength + ", modality " + this.modality + ", state: " + this.mSensorState + ", cookie: " + this.mCookie;
    }

    public void updateOemStrengthIfNotCached() {
        if (this.modality == 8 || this.mIsUpdatedOemStrength) {
            return;
        }
        int i = this.oemStrength;
        try {
            i = Utils.propertyStrengthToAuthenticatorStrength(this.impl.getSensorProperties("BiometricService/Sensor").sensorStrength);
            this.mIsUpdatedOemStrength = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (i == this.oemStrength) {
            return;
        }
        String str = "updateStrength: Before(" + this + ")";
        this.oemStrength = i;
        if (!this.mIsUpdatedStrengthByDeviceConfig) {
            this.mUpdatedStrength = i;
        }
        Slog.d("BiometricService/Sensor", str + " After(" + this + ")");
    }
}
