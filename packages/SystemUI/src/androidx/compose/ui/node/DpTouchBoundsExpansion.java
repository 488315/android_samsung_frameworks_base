package androidx.compose.ui.node;

import androidx.compose.animation.BoundsAnimationElement$$ExternalSyntheticOutline0;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BorderModifierNodeElement$$ExternalSyntheticOutline0;
import androidx.compose.ui.internal.InlineClassHelperKt;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DpTouchBoundsExpansion {
    public final float bottom;
    public final float end;
    public final boolean isLayoutDirectionAware;
    public final float start;
    public final float top;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public /* synthetic */ DpTouchBoundsExpansion(float f, float f2, float f3, float f4, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, f3, f4, z);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DpTouchBoundsExpansion)) {
            return false;
        }
        DpTouchBoundsExpansion dpTouchBoundsExpansion = (DpTouchBoundsExpansion) obj;
        return Dp.m836equalsimpl0(this.start, dpTouchBoundsExpansion.start) && Dp.m836equalsimpl0(this.top, dpTouchBoundsExpansion.top) && Dp.m836equalsimpl0(this.end, dpTouchBoundsExpansion.end) && Dp.m836equalsimpl0(this.bottom, dpTouchBoundsExpansion.bottom) && this.isLayoutDirectionAware == dpTouchBoundsExpansion.isLayoutDirectionAware;
    }

    public final int hashCode() {
        Dp.Companion companion = Dp.Companion;
        return Boolean.hashCode(this.isLayoutDirectionAware) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.bottom, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.end, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.top, Float.hashCode(this.start) * 31, 31), 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DpTouchBoundsExpansion(start=");
        BorderModifierNodeElement$$ExternalSyntheticOutline0.m(this.start, ", top=", sb);
        BorderModifierNodeElement$$ExternalSyntheticOutline0.m(this.top, ", end=", sb);
        BorderModifierNodeElement$$ExternalSyntheticOutline0.m(this.end, ", bottom=", sb);
        BorderModifierNodeElement$$ExternalSyntheticOutline0.m(this.bottom, ", isLayoutDirectionAware=", sb);
        return BoundsAnimationElement$$ExternalSyntheticOutline0.m(sb, this.isLayoutDirectionAware, ')');
    }

    private DpTouchBoundsExpansion(float f, float f2, float f3, float f4, boolean z) {
        this.start = f;
        this.top = f2;
        this.end = f3;
        this.bottom = f4;
        this.isLayoutDirectionAware = z;
        if (!(f >= 0.0f)) {
            InlineClassHelperKt.throwIllegalArgumentException("Left must be non-negative");
        }
        if (!(f2 >= 0.0f)) {
            InlineClassHelperKt.throwIllegalArgumentException("Top must be non-negative");
        }
        if (!(f3 >= 0.0f)) {
            InlineClassHelperKt.throwIllegalArgumentException("Right must be non-negative");
        }
        if (f4 >= 0.0f) {
            return;
        }
        InlineClassHelperKt.throwIllegalArgumentException("Bottom must be non-negative");
    }
}
