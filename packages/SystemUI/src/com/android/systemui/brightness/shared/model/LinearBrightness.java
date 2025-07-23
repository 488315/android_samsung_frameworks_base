package com.android.systemui.brightness.shared.model;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LinearBrightness {
    public final float floatValue;

    private /* synthetic */ LinearBrightness(float f) {
        this.floatValue = f;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ LinearBrightness m1067boximpl(float f) {
        return new LinearBrightness(f);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m1068toStringimpl(float f) {
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
        return m1068toStringimpl(this.floatValue);
    }
}
