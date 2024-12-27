package com.android.systemui.biometrics.data.repository;

import com.android.systemui.biometrics.shared.model.SensorStrength;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
