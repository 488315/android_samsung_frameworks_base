package com.android.systemui.biometrics.data.repository;

import com.android.systemui.biometrics.shared.model.SensorStrength;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FaceSensorInfo {
    public final int id;
    public final SensorStrength strength;

    public FaceSensorInfo(int i, SensorStrength sensorStrength) {
        this.id = i;
        this.strength = sensorStrength;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FaceSensorInfo)) {
            return false;
        }
        FaceSensorInfo faceSensorInfo = (FaceSensorInfo) obj;
        return this.id == faceSensorInfo.id && this.strength == faceSensorInfo.strength;
    }

    public final int hashCode() {
        return this.strength.hashCode() + (Integer.hashCode(this.id) * 31);
    }

    public final String toString() {
        return "FaceSensorInfo(id=" + this.id + ", strength=" + this.strength + ")";
    }
}
