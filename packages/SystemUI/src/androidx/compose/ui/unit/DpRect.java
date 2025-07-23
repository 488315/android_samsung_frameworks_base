package androidx.compose.ui.unit;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BorderModifierNodeElement$$ExternalSyntheticOutline0;
import androidx.compose.ui.unit.Dp;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DpRect {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final float bottom;
    public final float left;
    public final float right;
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
        return Dp.m836equalsimpl0(this.left, dpRect.left) && Dp.m836equalsimpl0(this.top, dpRect.top) && Dp.m836equalsimpl0(this.right, dpRect.right) && Dp.m836equalsimpl0(this.bottom, dpRect.bottom);
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
        sb.append((Object) Dp.m837toStringimpl(this.bottom));
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private DpRect(long r7, long r9) {
        /*
            r6 = this;
            float r1 = androidx.compose.ui.unit.DpOffset.m840getXD9Ej5fM(r7)
            float r2 = androidx.compose.ui.unit.DpOffset.m841getYD9Ej5fM(r7)
            float r0 = androidx.compose.ui.unit.DpOffset.m840getXD9Ej5fM(r7)
            float r3 = androidx.compose.ui.unit.DpSize.m845getWidthD9Ej5fM(r9)
            float r3 = r3 + r0
            androidx.compose.ui.unit.Dp$Companion r0 = androidx.compose.ui.unit.Dp.Companion
            float r7 = androidx.compose.ui.unit.DpOffset.m841getYD9Ej5fM(r7)
            float r8 = androidx.compose.ui.unit.DpSize.m844getHeightD9Ej5fM(r9)
            float r4 = r8 + r7
            r5 = 0
            r0 = r6
            r0.<init>(r1, r2, r3, r4, r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.unit.DpRect.<init>(long, long):void");
    }
}
