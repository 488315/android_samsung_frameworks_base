package com.android.systemui.qs.tiles.impl.location.domain.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

public final class LocationTileModel {
    public final boolean isEnabled;

    private /* synthetic */ LocationTileModel(boolean z) {
        this.isEnabled = z;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ LocationTileModel m2092boximpl(boolean z) {
        return new LocationTileModel(z);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof LocationTileModel) {
            return this.isEnabled == ((LocationTileModel) obj).isEnabled;
        }
        return false;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isEnabled);
    }

    public final String toString() {
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("LocationTileModel(isEnabled="), this.isEnabled, ")");
    }
}
