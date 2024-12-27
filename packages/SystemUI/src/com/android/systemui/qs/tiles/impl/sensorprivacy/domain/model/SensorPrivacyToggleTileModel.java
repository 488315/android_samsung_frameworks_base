package com.android.systemui.qs.tiles.impl.sensorprivacy.domain.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

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
