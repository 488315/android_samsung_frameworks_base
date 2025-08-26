package com.android.systemui.brightness.shared.model;

import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;

/* loaded from: classes.dex */
public final class GammaBrightness {
    public final int value;

    private /* synthetic */ GammaBrightness(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ GammaBrightness m1066boximpl(int i) {
        return new GammaBrightness(i);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m1067toStringimpl(int i) {
        return ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "GammaBrightness(value=", ")");
    }

    public final boolean equals(Object obj) {
        if (obj instanceof GammaBrightness) {
            return this.value == ((GammaBrightness) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return m1067toStringimpl(this.value);
    }
}
