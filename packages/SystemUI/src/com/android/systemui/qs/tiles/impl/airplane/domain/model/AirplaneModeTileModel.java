package com.android.systemui.qs.tiles.impl.airplane.domain.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AirplaneModeTileModel {
    public final boolean isEnabled;

    private /* synthetic */ AirplaneModeTileModel(boolean z) {
        this.isEnabled = z;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ AirplaneModeTileModel m2088boximpl(boolean z) {
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
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("AirplaneModeTileModel(isEnabled="), this.isEnabled, ")");
    }
}
