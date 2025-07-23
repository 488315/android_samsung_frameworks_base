package androidx.compose.ui.layout;

import androidx.compose.animation.AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class FixedScale implements ContentScale {
    public final float value;

    public FixedScale(float f) {
        this.value = f;
    }

    @Override // androidx.compose.ui.layout.ContentScale
    /* renamed from: computeScaleFactor-H7hwNQA */
    public final long mo606computeScaleFactorH7hwNQA(long j, long j2) {
        float f = this.value;
        long floatToRawIntBits = (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(f) & 4294967295L);
        int i = ScaleFactor.$r8$clinit;
        return floatToRawIntBits;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof FixedScale) && Float.compare(this.value, ((FixedScale) obj).value) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.value);
    }

    public final String toString() {
        return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(new StringBuilder("FixedScale(value="), this.value, ')');
    }
}
