package com.android.systemui.qs.tiles.impl.sensorprivacy.domain.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SensorPrivacyToggleTileModel {
    public final boolean isBlocked;

    private /* synthetic */ SensorPrivacyToggleTileModel(boolean z) {
        this.isBlocked = z;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ SensorPrivacyToggleTileModel m2096boximpl(boolean z) {
        return new SensorPrivacyToggleTileModel(z);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof SensorPrivacyToggleTileModel) {
            return this.isBlocked == ((SensorPrivacyToggleTileModel) obj).isBlocked;
        }
        return false;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isBlocked);
    }

    public final String toString() {
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("SensorPrivacyToggleTileModel(isBlocked="), this.isBlocked, ")");
    }
}
