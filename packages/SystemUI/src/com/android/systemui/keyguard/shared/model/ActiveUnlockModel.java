package com.android.systemui.keyguard.shared.model;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ActiveUnlockModel {
    public final boolean isRunning;
    public final int userId;

    public ActiveUnlockModel(boolean z, int i) {
        this.isRunning = z;
        this.userId = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActiveUnlockModel)) {
            return false;
        }
        ActiveUnlockModel activeUnlockModel = (ActiveUnlockModel) obj;
        return this.isRunning == activeUnlockModel.isRunning && this.userId == activeUnlockModel.userId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.userId) + (Boolean.hashCode(this.isRunning) * 31);
    }

    public final String toString() {
        return "ActiveUnlockModel(isRunning=" + this.isRunning + ", userId=" + this.userId + ")";
    }
}
