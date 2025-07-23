package androidx.compose.ui.graphics.colorspace;

import androidx.compose.animation.AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class WhitePoint {
    public final float x;
    public final float y;

    public WhitePoint(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WhitePoint)) {
            return false;
        }
        WhitePoint whitePoint = (WhitePoint) obj;
        return Float.compare(this.x, whitePoint.x) == 0 && Float.compare(this.y, whitePoint.y) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.y) + (Float.hashCode(this.x) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("WhitePoint(x=");
        sb.append(this.x);
        sb.append(", y=");
        return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.y, ')');
    }

    public final float[] toXyz$ui_graphics_release() {
        float f = this.x;
        float f2 = this.y;
        return new float[]{f / f2, 1.0f, ((1.0f - f) - f2) / f2};
    }

    public WhitePoint(float f, float f2, float f3) {
        this(f, f2, f3, f + f2 + f3);
    }

    private WhitePoint(float f, float f2, float f3, float f4) {
        this(f / f4, f2 / f4);
    }
}
