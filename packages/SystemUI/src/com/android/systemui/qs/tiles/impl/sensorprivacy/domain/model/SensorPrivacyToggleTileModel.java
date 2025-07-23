package com.android.systemui.qs.tiles.impl.sensorprivacy.domain.model;

import defpackage.MoveResult$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class SensorPrivacyToggleTileModel {
    public final boolean isBlocked;

    private /* synthetic */ SensorPrivacyToggleTileModel(boolean z) {
        this.isBlocked = z;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ SensorPrivacyToggleTileModel m2919boximpl(boolean z) {
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
        return MoveResult$$ExternalSyntheticOutline0.m(new StringBuilder("SensorPrivacyToggleTileModel(isBlocked="), this.isBlocked, ")");
    }
}
