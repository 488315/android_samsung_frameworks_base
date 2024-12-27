package com.android.systemui.qs.tiles.impl.inversion.domain.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

public final class ColorInversionTileModel {
    public final boolean isEnabled;

    private /* synthetic */ ColorInversionTileModel(boolean z) {
        this.isEnabled = z;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ ColorInversionTileModel m2091boximpl(boolean z) {
        return new ColorInversionTileModel(z);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ColorInversionTileModel) {
            return this.isEnabled == ((ColorInversionTileModel) obj).isEnabled;
        }
        return false;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isEnabled);
    }

    public final String toString() {
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("ColorInversionTileModel(isEnabled="), this.isEnabled, ")");
    }
}
