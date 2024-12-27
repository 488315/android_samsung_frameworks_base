package com.android.systemui.qs.tiles.impl.onehanded.domain.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class OneHandedModeTileModel {
    public final boolean isEnabled;

    private /* synthetic */ OneHandedModeTileModel(boolean z) {
        this.isEnabled = z;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ OneHandedModeTileModel m2093boximpl(boolean z) {
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
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OneHandedModeTileModel(isEnabled="), this.isEnabled, ")");
    }
}
