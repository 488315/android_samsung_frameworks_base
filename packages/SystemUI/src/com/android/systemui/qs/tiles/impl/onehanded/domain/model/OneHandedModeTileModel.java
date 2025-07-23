package com.android.systemui.qs.tiles.impl.onehanded.domain.model;

import defpackage.MoveResult$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class OneHandedModeTileModel {
    public final boolean isEnabled;

    private /* synthetic */ OneHandedModeTileModel(boolean z) {
        this.isEnabled = z;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ OneHandedModeTileModel m2916boximpl(boolean z) {
        return new OneHandedModeTileModel(z);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof OneHandedModeTileModel) {
            return this.isEnabled == ((OneHandedModeTileModel) obj).isEnabled;
        }
        return false;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isEnabled);
    }

    public final String toString() {
        return MoveResult$$ExternalSyntheticOutline0.m(new StringBuilder("OneHandedModeTileModel(isEnabled="), this.isEnabled, ")");
    }
}
