package androidx.compose.foundation.shape;

import androidx.compose.ui.unit.Density;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PxCornerSize implements CornerSize {
    public final float size;

    public PxCornerSize(float f) {
        this.size = f;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof PxCornerSize) && Float.compare(this.size, ((PxCornerSize) obj).size) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.size);
    }

    @Override // androidx.compose.foundation.shape.CornerSize
    /* renamed from: toPx-TmRCtEA */
    public final float mo184toPxTmRCtEA(Density density, long j) {
        return this.size;
    }

    public final String toString() {
        return DpCornerSize$$ExternalSyntheticOutline0.m(this.size, ".px)", new StringBuilder("CornerSize(size = "));
    }
}
