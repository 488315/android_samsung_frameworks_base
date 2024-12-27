package com.android.systemui.keyguard.shared.model;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public final int hashCode() {
        return Boolean.hashCode(this.isTrustManaged) + (Integer.hashCode(this.userId) * 31);
    }

    public final String toString() {
        return "TrustManagedModel(userId=" + this.userId + ", isTrustManaged=" + this.isTrustManaged + ")";
    }
}
