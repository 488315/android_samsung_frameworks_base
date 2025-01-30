package com.android.systemui.keyguard.shared.model;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TrustManagedModel extends TrustMessage {
    public final boolean isTrustManaged;
    public final int userId;

    public TrustManagedModel(int i, boolean z) {
        super(null);
        this.userId = i;
        this.isTrustManaged = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TrustManagedModel)) {
            return false;
        }
        TrustManagedModel trustManagedModel = (TrustManagedModel) obj;
        return this.userId == trustManagedModel.userId && this.isTrustManaged == trustManagedModel.isTrustManaged;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = Integer.hashCode(this.userId) * 31;
        boolean z = this.isTrustManaged;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return hashCode + i;
    }

    public final String toString() {
        return "TrustManagedModel(userId=" + this.userId + ", isTrustManaged=" + this.isTrustManaged + ")";
    }
}
