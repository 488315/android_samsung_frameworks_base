package com.android.systemui.qs.tiles.impl.airplane.domain.model;

import defpackage.MoveResult$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AirplaneModeTileModel {
    public final boolean isEnabled;

    private /* synthetic */ AirplaneModeTileModel(boolean z) {
        this.isEnabled = z;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ AirplaneModeTileModel m2910boximpl(boolean z) {
        return new AirplaneModeTileModel(z);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof AirplaneModeTileModel) {
            return this.isEnabled == ((AirplaneModeTileModel) obj).isEnabled;
        }
        return false;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isEnabled);
    }

    public final String toString() {
        return MoveResult$$ExternalSyntheticOutline0.m(new StringBuilder("AirplaneModeTileModel(isEnabled="), this.isEnabled, ")");
    }
}
