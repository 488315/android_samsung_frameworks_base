package com.android.systemui.keyguard.shared.model;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4 */
    public final int hashCode() {
        boolean z = this.isRunning;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        return Integer.hashCode(this.userId) + (r0 * 31);
    }

    public final String toString() {
        return "ActiveUnlockModel(isRunning=" + this.isRunning + ", userId=" + this.userId + ")";
    }
}
