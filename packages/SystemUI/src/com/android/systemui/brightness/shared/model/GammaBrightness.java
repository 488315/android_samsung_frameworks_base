package com.android.systemui.brightness.shared.model;

import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class GammaBrightness {
    public final int value;

    private /* synthetic */ GammaBrightness(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ GammaBrightness m933boximpl(int i) {
        return new GammaBrightness(i);
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
        return LazyListMeasuredItem$$ExternalSyntheticOutline0.m(this.value, "GammaBrightness(value=", ")");
    }
}
