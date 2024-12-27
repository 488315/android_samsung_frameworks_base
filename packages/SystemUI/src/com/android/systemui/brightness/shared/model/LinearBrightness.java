package com.android.systemui.brightness.shared.model;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class LinearBrightness {
    public final float floatValue;

    private /* synthetic */ LinearBrightness(float f) {
        this.floatValue = f;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ LinearBrightness m935boximpl(float f) {
        return new LinearBrightness(f);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m936toStringimpl(float f) {
        return "LinearBrightness(floatValue=" + f + ")";
    }

    public final boolean equals(Object obj) {
        if (obj instanceof LinearBrightness) {
            return Float.compare(this.floatValue, ((LinearBrightness) obj).floatValue) == 0;
        }
        return false;
    }

    public final int hashCode() {
        return Float.hashCode(this.floatValue);
    }

    public final String toString() {
        return m936toStringimpl(this.floatValue);
    }
}
