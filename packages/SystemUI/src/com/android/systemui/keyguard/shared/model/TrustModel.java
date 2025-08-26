package com.android.systemui.keyguard.shared.model;

import com.android.keyguard.TrustGrantFlags;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public final class TrustModel extends TrustMessage {
    public final TrustGrantFlags flags;
    public final boolean isTrusted;
    public final int userId;

    public TrustModel(boolean z, int i, TrustGrantFlags trustGrantFlags) {
        super(null);
        this.isTrusted = z;
        this.userId = i;
        this.flags = trustGrantFlags;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TrustModel)) {
            return false;
        }
        TrustModel trustModel = (TrustModel) obj;
        return this.isTrusted == trustModel.isTrusted && this.userId == trustModel.userId && Intrinsics.areEqual(this.flags, trustModel.flags);
    }

    public final int hashCode() {
        return this.flags.hashCode() + ReorderTile$$ExternalSyntheticOutline0.m(this.userId, Boolean.hashCode(this.isTrusted) * 31, 31);
    }

    public final String toString() {
        return "TrustModel(isTrusted=" + this.isTrusted + ", userId=" + this.userId + ", flags=" + this.flags + ")";
    }
}
