package com.android.systemui.keyguard.shared.model;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import com.android.keyguard.TrustGrantFlags;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        return this.flags.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.userId, Boolean.hashCode(this.isTrusted) * 31, 31);
    }

    public final String toString() {
        return "TrustModel(isTrusted=" + this.isTrusted + ", userId=" + this.userId + ", flags=" + this.flags + ")";
    }
}
