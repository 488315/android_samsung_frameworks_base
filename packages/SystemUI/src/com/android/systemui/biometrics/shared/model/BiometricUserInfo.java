package com.android.systemui.biometrics.shared.model;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
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
        return Integer.hashCode(this.userIdForPasswordEntry) + ReorderTile$$ExternalSyntheticOutline0.m(this.deviceCredentialOwnerId, Integer.hashCode(this.userId) * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BiometricUserInfo(userId=");
        sb.append(this.userId);
        sb.append(", deviceCredentialOwnerId=");
        sb.append(this.deviceCredentialOwnerId);
        sb.append(", userIdForPasswordEntry=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.userIdForPasswordEntry, ")", sb);
    }

    public /* synthetic */ BiometricUserInfo(int i, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i4 & 2) != 0 ? i : i2, (i4 & 4) != 0 ? i : i3);
    }
}
