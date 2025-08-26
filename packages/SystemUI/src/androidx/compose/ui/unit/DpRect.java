package androidx.compose.ui.unit;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BorderModifierNodeElement$$ExternalSyntheticOutline0;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class DpRect {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final float bottom;
    public final float left;
    public final float right;
    public final float top;

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

    public /* synthetic */ DpRect(float f, float f2, float f3, float f4, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, f3, f4);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DpRect)) {
            return false;
        }
        DpRect dpRect = (DpRect) obj;
        return Dp.m838equalsimpl0(this.left, dpRect.left) && Dp.m838equalsimpl0(this.top, dpRect.top) && Dp.m838equalsimpl0(this.right, dpRect.right) && Dp.m838equalsimpl0(this.bottom, dpRect.bottom);
    }

    public final int hashCode() {
        Dp.Companion companion = Dp.Companion;
        return Float.hashCode(this.bottom) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.right, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.top, Float.hashCode(this.left) * 31, 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DpRect(left=");
        BorderModifierNodeElement$$ExternalSyntheticOutline0.m(this.left, ", top=", sb);
        BorderModifierNodeElement$$ExternalSyntheticOutline0.m(this.top, ", right=", sb);
        BorderModifierNodeElement$$ExternalSyntheticOutline0.m(this.right, ", bottom=", sb);
        sb.append((Object) Dp.m839toStringimpl(this.bottom));
        sb.append(')');
        return sb.toString();
    }

    public /* synthetic */ DpRect(long j, long j2, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2);
    }

    private DpRect(float f, float f2, float f3, float f4) {
        this.left = f;
        this.top = f2;
        this.right = f3;
        this.bottom = f4;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private DpRect(long j, long j2) {
        float fM842getXD9Ej5fM = DpOffset.m842getXD9Ej5fM(j);
        float fM843getYD9Ej5fM = DpOffset.m843getYD9Ej5fM(j);
        float fM847getWidthD9Ej5fM = DpSize.m847getWidthD9Ej5fM(j2) + DpOffset.m842getXD9Ej5fM(j);
        Dp.Companion companion = Dp.Companion;
        this(fM842getXD9Ej5fM, fM843getYD9Ej5fM, fM847getWidthD9Ej5fM, DpSize.m846getHeightD9Ej5fM(j2) + DpOffset.m843getYD9Ej5fM(j), null);
    }
}
