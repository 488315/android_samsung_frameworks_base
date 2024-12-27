package com.android.server.biometrics;

import android.hardware.biometrics.IBiometricAuthenticator;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class BiometricSensor {
    public final int id;
    public final IBiometricAuthenticator impl;
    boolean mIsUpdatedOemStrength;
    public boolean mIsUpdatedStrengthByDeviceConfig;
    public int mUpdatedStrength;
    public final int modality;
    public int oemStrength;
    public int mSensorState = 0;
    public int mCookie = 0;

    public BiometricSensor(int i, int i2, int i3, IBiometricAuthenticator iBiometricAuthenticator) {
        this.id = i;
        this.modality = i2;
        this.oemStrength = i3;
        this.impl = iBiometricAuthenticator;
        this.mUpdatedStrength = i3;
    }

    public final int getCurrentStrength() {
        updateOemStrengthIfNotCached();
        return this.mUpdatedStrength | this.oemStrength;
    }

    public final String toString() {
        return "ID("
                + this.id
                + "), oemStrength: "
                + this.oemStrength
                + ", updatedStrength: "
                + this.mUpdatedStrength
                + ", modality "
                + this.modality
                + ", state: "
                + this.mSensorState
                + ", cookie: "
                + this.mCookie;
    }

    public void updateOemStrengthIfNotCached() {
        if (this.modality == 8 || this.mIsUpdatedOemStrength) {
            return;
        }
        int i = this.oemStrength;
        try {
            i =
                    Utils.propertyStrengthToAuthenticatorStrength(
                            this.impl.getSensorProperties("BiometricService/Sensor")
                                    .sensorStrength);
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

    public final void updateStrength(int i) {
        this.mUpdatedStrength = i;
        Slog.d(
                "BiometricService/Sensor",
                ("updateStrength: Before(" + this + ")") + " After(" + this + ")");
        this.mIsUpdatedStrengthByDeviceConfig = true;
    }
}
