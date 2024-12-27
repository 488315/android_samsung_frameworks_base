package com.android.systemui.qs.tiles.impl.reducebrightness.domain.model;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ReduceBrightColorsTileModel {
    public final boolean isEnabled;

    private /* synthetic */ ReduceBrightColorsTileModel(boolean z) {
        this.isEnabled = z;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ ReduceBrightColorsTileModel m2094boximpl(boolean z) {
        return new ReduceBrightColorsTileModel(z);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ReduceBrightColorsTileModel) {
            return this.isEnabled == ((ReduceBrightColorsTileModel) obj).isEnabled;
        }
        return false;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.isEnabled);
    }

    public final String toString() {
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("ReduceBrightColorsTileModel(isEnabled="), this.isEnabled, ")");
    }
}
