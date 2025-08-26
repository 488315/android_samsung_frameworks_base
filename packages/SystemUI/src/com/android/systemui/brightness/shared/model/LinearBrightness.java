package com.android.systemui.brightness.shared.model;

/* loaded from: classes.dex */
public final class LinearBrightness {
    public final float floatValue;

    private /* synthetic */ LinearBrightness(float f) {
        this.floatValue = f;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ LinearBrightness m1069boximpl(float f) {
        return new LinearBrightness(f);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m1070toStringimpl(float f) {
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
        return m1070toStringimpl(this.floatValue);
    }
}
