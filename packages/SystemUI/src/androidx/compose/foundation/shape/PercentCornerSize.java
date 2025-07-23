package androidx.compose.foundation.shape;

import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.unit.Density;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PercentCornerSize implements CornerSize {
    public final float percent;

    public PercentCornerSize(float f) {
        this.percent = f;
        if (f < 0.0f || f > 100.0f) {
            InlineClassHelperKt.throwIllegalArgumentException("The percent should be in the range of [0, 100]");
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof PercentCornerSize) && Float.compare(this.percent, ((PercentCornerSize) obj).percent) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.percent);
    }

    @Override // androidx.compose.foundation.shape.CornerSize
    /* renamed from: toPx-TmRCtEA */
    public final float mo184toPxTmRCtEA(Density density, long j) {
        return (this.percent / 100.0f) * Size.m416getMinDimensionimpl(j);
    }

    public final String toString() {
        return DpCornerSize$$ExternalSyntheticOutline0.m(this.percent, "%)", new StringBuilder("CornerSize(size = "));
    }
}
