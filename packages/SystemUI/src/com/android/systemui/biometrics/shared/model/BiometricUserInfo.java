package com.android.systemui.biometrics.shared.model;

import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BiometricUserInfo {
    public final int deviceCredentialOwnerId;
    public final int userId;
    public final int userIdForPasswordEntry;

    public BiometricUserInfo(int i, int i2, int i3) {
        this.userId = i;
        this.deviceCredentialOwnerId = i2;
        this.userIdForPasswordEntry = i3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BiometricUserInfo)) {
            return false;
        }
        BiometricUserInfo biometricUserInfo = (BiometricUserInfo) obj;
        return this.userId == biometricUserInfo.userId && this.deviceCredentialOwnerId == biometricUserInfo.deviceCredentialOwnerId && this.userIdForPasswordEntry == biometricUserInfo.userIdForPasswordEntry;
    }

    public final int hashCode() {
        return Integer.hashCode(this.userIdForPasswordEntry) + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.deviceCredentialOwnerId, Integer.hashCode(this.userId) * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BiometricUserInfo(userId=");
        sb.append(this.userId);
        sb.append(", deviceCredentialOwnerId=");
        sb.append(this.deviceCredentialOwnerId);
        sb.append(", userIdForPasswordEntry=");
        return Anchor$$ExternalSyntheticOutline0.m(this.userIdForPasswordEntry, ")", sb);
    }

    public /* synthetic */ BiometricUserInfo(int i, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i4 & 2) != 0 ? i : i2, (i4 & 4) != 0 ? i : i3);
    }
}
