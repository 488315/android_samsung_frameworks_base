package com.android.systemui.biometrics.domain.model;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class BiometricUserInfo {
    public final int deviceCredentialOwnerId;
    public final int userId;

    public BiometricUserInfo(int i, int i2) {
        this.userId = i;
        this.deviceCredentialOwnerId = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BiometricUserInfo)) {
            return false;
        }
        BiometricUserInfo biometricUserInfo = (BiometricUserInfo) obj;
        return this.userId == biometricUserInfo.userId && this.deviceCredentialOwnerId == biometricUserInfo.deviceCredentialOwnerId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.deviceCredentialOwnerId) + (Integer.hashCode(this.userId) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BiometricUserInfo(userId=");
        sb.append(this.userId);
        sb.append(", deviceCredentialOwnerId=");
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.deviceCredentialOwnerId, ")");
    }

    public /* synthetic */ BiometricUserInfo(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i3 & 2) != 0 ? i : i2);
    }
}
