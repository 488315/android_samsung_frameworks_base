package com.android.systemui.bouncer.data.model;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SimBouncerModel {
    public final boolean isSimPukLocked;
    public final int subscriptionId;

    public SimBouncerModel(boolean z, int i) {
        this.isSimPukLocked = z;
        this.subscriptionId = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SimBouncerModel)) {
            return false;
        }
        SimBouncerModel simBouncerModel = (SimBouncerModel) obj;
        return this.isSimPukLocked == simBouncerModel.isSimPukLocked && this.subscriptionId == simBouncerModel.subscriptionId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.subscriptionId) + (Boolean.hashCode(this.isSimPukLocked) * 31);
    }

    public final String toString() {
        return "SimBouncerModel(isSimPukLocked=" + this.isSimPukLocked + ", subscriptionId=" + this.subscriptionId + ")";
    }
}
