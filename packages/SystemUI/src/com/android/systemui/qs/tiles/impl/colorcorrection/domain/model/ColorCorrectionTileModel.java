package com.android.systemui.qs.tiles.impl.colorcorrection.domain.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ColorCorrectionTileModel {
    public final boolean isEnabled;

    private /* synthetic */ ColorCorrectionTileModel(boolean z) {
        this.isEnabled = z;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ ColorCorrectionTileModel m2089boximpl(boolean z) {
        return new ColorCorrectionTileModel(z);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ColorCorrectionTileModel) {
            return this.isEnabled == ((ColorCorrectionTileModel) obj).isEnabled;
        }
        return false;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isEnabled);
    }

    public final String toString() {
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("ColorCorrectionTileModel(isEnabled="), this.isEnabled, ")");
    }
}
