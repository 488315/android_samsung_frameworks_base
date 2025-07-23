package androidx.compose.ui;

import androidx.compose.animation.AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.LayoutDirection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BiasAlignment implements Alignment {
    public final float horizontalBias;
    public final float verticalBias;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Horizontal implements Alignment.Horizontal {
        public final float bias;

        public Horizontal(float f) {
            this.bias = f;
        }

        @Override // androidx.compose.ui.Alignment.Horizontal
        public final int align(int i, int i2, LayoutDirection layoutDirection) {
            float f = (i2 - i) / 2.0f;
            LayoutDirection layoutDirection2 = LayoutDirection.Ltr;
            float f2 = this.bias;
            if (layoutDirection != layoutDirection2) {
                f2 *= -1;
            }
            return Math.round((1 + f2) * f);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Horizontal) && Float.compare(this.bias, ((Horizontal) obj).bias) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.bias);
        }

        public final String toString() {
            return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(new StringBuilder("Horizontal(bias="), this.bias, ')');
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Vertical implements Alignment.Vertical {
        public final float bias;

        public Vertical(float f) {
            this.bias = f;
        }

        public final int align(int i, int i2) {
            return Math.round((1 + this.bias) * ((i2 - i) / 2.0f));
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Vertical) && Float.compare(this.bias, ((Vertical) obj).bias) == 0;
        }

        public final int hashCode() {
            return Float.hashCode(this.bias);
        }

        public final String toString() {
            return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(new StringBuilder("Vertical(bias="), this.bias, ')');
        }
    }

    public BiasAlignment(float f, float f2) {
        this.horizontalBias = f;
        this.verticalBias = f2;
    }

    @Override // androidx.compose.ui.Alignment
    /* renamed from: align-KFBX0sM */
    public final long mo352alignKFBX0sM(long j, long j2, LayoutDirection layoutDirection) {
        float f = (((int) (j2 >> 32)) - ((int) (j >> 32))) / 2.0f;
        float f2 = (((int) (j2 & 4294967295L)) - ((int) (j & 4294967295L))) / 2.0f;
        LayoutDirection layoutDirection2 = LayoutDirection.Ltr;
        float f3 = this.horizontalBias;
        if (layoutDirection != layoutDirection2) {
            f3 *= -1;
        }
        float f4 = 1;
        float f5 = (f3 + f4) * f;
        float f6 = (f4 + this.verticalBias) * f2;
        int round = Math.round(f5);
        long round2 = (Math.round(f6) & 4294967295L) | (round << 32);
        IntOffset.Companion companion = IntOffset.Companion;
        return round2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BiasAlignment)) {
            return false;
        }
        BiasAlignment biasAlignment = (BiasAlignment) obj;
        return Float.compare(this.horizontalBias, biasAlignment.horizontalBias) == 0 && Float.compare(this.verticalBias, biasAlignment.verticalBias) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.verticalBias) + (Float.hashCode(this.horizontalBias) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("BiasAlignment(horizontalBias=");
        sb.append(this.horizontalBias);
        sb.append(", verticalBias=");
        return AndroidFlingSpline$FlingResult$$ExternalSyntheticOutline0.m(sb, this.verticalBias, ')');
    }
}
