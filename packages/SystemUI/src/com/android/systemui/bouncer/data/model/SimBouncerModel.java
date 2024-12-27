package com.android.systemui.bouncer.data.model;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
