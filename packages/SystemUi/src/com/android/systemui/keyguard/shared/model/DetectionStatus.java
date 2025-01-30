package com.android.systemui.keyguard.shared.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.picker.model.viewdata.AppInfoViewData$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DetectionStatus {
    public final boolean isStrongBiometric;
    public final int sensorId;
    public final int userId;

    public DetectionStatus(int i, int i2, boolean z) {
        this.sensorId = i;
        this.userId = i2;
        this.isStrongBiometric = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DetectionStatus)) {
            return false;
        }
        DetectionStatus detectionStatus = (DetectionStatus) obj;
        return this.sensorId == detectionStatus.sensorId && this.userId == detectionStatus.userId && this.isStrongBiometric == detectionStatus.isStrongBiometric;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int m42m = AppInfoViewData$$ExternalSyntheticOutline0.m42m(this.userId, Integer.hashCode(this.sensorId) * 31, 31);
        boolean z = this.isStrongBiometric;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return m42m + i;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DetectionStatus(sensorId=");
        sb.append(this.sensorId);
        sb.append(", userId=");
        sb.append(this.userId);
        sb.append(", isStrongBiometric=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.isStrongBiometric, ")");
    }
}
