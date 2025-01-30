package com.android.systemui.keyguard.shared.model;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TrustModel extends TrustMessage {
    public final boolean isTrusted;
    public final int userId;

    public TrustModel(boolean z, int i) {
        super(null);
        this.isTrusted = z;
        this.userId = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TrustModel)) {
            return false;
        }
        TrustModel trustModel = (TrustModel) obj;
        return this.isTrusted == trustModel.isTrusted && this.userId == trustModel.userId;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4 */
    public final int hashCode() {
        boolean z = this.isTrusted;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        return Integer.hashCode(this.userId) + (r0 * 31);
    }

    public final String toString() {
        return "TrustModel(isTrusted=" + this.isTrusted + ", userId=" + this.userId + ")";
    }
}
