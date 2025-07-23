package com.android.systemui.qs.tiles.impl.saver.domain.model;

import defpackage.MoveResult$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DataSaverTileModel {
    public final boolean isEnabled;

    private /* synthetic */ DataSaverTileModel(boolean z) {
        this.isEnabled = z;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ DataSaverTileModel m2918boximpl(boolean z) {
        return new DataSaverTileModel(z);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof DataSaverTileModel) {
            return this.isEnabled == ((DataSaverTileModel) obj).isEnabled;
        }
        return false;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isEnabled);
    }

    public final String toString() {
        return MoveResult$$ExternalSyntheticOutline0.m(new StringBuilder("DataSaverTileModel(isEnabled="), this.isEnabled, ")");
    }
}
