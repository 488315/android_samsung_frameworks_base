package androidx.compose.foundation.shape;

import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
final class DpCornerSize implements CornerSize {
    public final float size;

    public /* synthetic */ DpCornerSize(float f, DefaultConstructorMarker defaultConstructorMarker) {
        this(f);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof DpCornerSize) && Dp.m838equalsimpl0(this.size, ((DpCornerSize) obj).size);
    }

    public final int hashCode() {
        Dp.Companion companion = Dp.Companion;
        return Float.hashCode(this.size);
    }

    @Override // androidx.compose.foundation.shape.CornerSize
    /* renamed from: toPx-TmRCtEA */
    public final float mo185toPxTmRCtEA(Density density, long j) {
        return density.mo58toPx0680j_4(this.size);
    }

    public final String toString() {
        return DpCornerSize$$ExternalSyntheticOutline0.m(this.size, ".dp)", new StringBuilder("CornerSize(size = "));
    }

    private DpCornerSize(float f) {
        this.size = f;
    }
}
