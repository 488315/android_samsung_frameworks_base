package androidx.compose.foundation.layout;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BorderModifierNodeElement$$ExternalSyntheticOutline0;
import androidx.compose.foundation.layout.internal.InlineClassHelperKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PaddingValuesImpl implements PaddingValues {
    public final float bottom;
    public final float end;
    public final float start;
    public final float top;

    public /* synthetic */ PaddingValuesImpl(float f, float f2, float f3, float f4, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, f3, f4);
    }

    @Override // androidx.compose.foundation.layout.PaddingValues
    /* renamed from: calculateBottomPadding-D9Ej5fM */
    public final float mo109calculateBottomPaddingD9Ej5fM() {
        return this.bottom;
    }

    @Override // androidx.compose.foundation.layout.PaddingValues
    /* renamed from: calculateLeftPadding-u2uoSUM */
    public final float mo110calculateLeftPaddingu2uoSUM(LayoutDirection layoutDirection) {
        return layoutDirection == LayoutDirection.Ltr ? this.start : this.end;
    }

    @Override // androidx.compose.foundation.layout.PaddingValues
    /* renamed from: calculateRightPadding-u2uoSUM */
    public final float mo111calculateRightPaddingu2uoSUM(LayoutDirection layoutDirection) {
        return layoutDirection == LayoutDirection.Ltr ? this.end : this.start;
    }

    @Override // androidx.compose.foundation.layout.PaddingValues
    /* renamed from: calculateTopPadding-D9Ej5fM */
    public final float mo112calculateTopPaddingD9Ej5fM() {
        return this.top;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof PaddingValuesImpl)) {
            return false;
        }
        PaddingValuesImpl paddingValuesImpl = (PaddingValuesImpl) obj;
        return Dp.m836equalsimpl0(this.start, paddingValuesImpl.start) && Dp.m836equalsimpl0(this.top, paddingValuesImpl.top) && Dp.m836equalsimpl0(this.end, paddingValuesImpl.end) && Dp.m836equalsimpl0(this.bottom, paddingValuesImpl.bottom);
    }

    public final int hashCode() {
        Dp.Companion companion = Dp.Companion;
        return Float.hashCode(this.bottom) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.end, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.top, Float.hashCode(this.start) * 31, 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("PaddingValues(start=");
        BorderModifierNodeElement$$ExternalSyntheticOutline0.m(this.start, ", top=", sb);
        BorderModifierNodeElement$$ExternalSyntheticOutline0.m(this.top, ", end=", sb);
        BorderModifierNodeElement$$ExternalSyntheticOutline0.m(this.end, ", bottom=", sb);
        sb.append((Object) Dp.m837toStringimpl(this.bottom));
        sb.append(')');
        return sb.toString();
    }

    private PaddingValuesImpl(float f, float f2, float f3, float f4) {
        this.start = f;
        this.top = f2;
        this.end = f3;
        this.bottom = f4;
        if (!(f >= 0.0f)) {
            InlineClassHelperKt.throwIllegalArgumentException("Start padding must be non-negative");
        }
        if (!(f2 >= 0.0f)) {
            InlineClassHelperKt.throwIllegalArgumentException("Top padding must be non-negative");
        }
        if (!(f3 >= 0.0f)) {
            InlineClassHelperKt.throwIllegalArgumentException("End padding must be non-negative");
        }
        if (f4 >= 0.0f) {
            return;
        }
        InlineClassHelperKt.throwIllegalArgumentException("Bottom padding must be non-negative");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public PaddingValuesImpl(float r8, float r9, float r10, float r11, int r12, kotlin.jvm.internal.DefaultConstructorMarker r13) {
        /*
            r7 = this;
            r13 = r12 & 1
            r0 = 0
            if (r13 == 0) goto L8
            float r8 = (float) r0
            androidx.compose.ui.unit.Dp$Companion r13 = androidx.compose.ui.unit.Dp.Companion
        L8:
            r2 = r8
            r8 = r12 & 2
            if (r8 == 0) goto L10
            float r9 = (float) r0
            androidx.compose.ui.unit.Dp$Companion r8 = androidx.compose.ui.unit.Dp.Companion
        L10:
            r3 = r9
            r8 = r12 & 4
            if (r8 == 0) goto L18
            float r10 = (float) r0
            androidx.compose.ui.unit.Dp$Companion r8 = androidx.compose.ui.unit.Dp.Companion
        L18:
            r4 = r10
            r8 = r12 & 8
            if (r8 == 0) goto L20
            float r11 = (float) r0
            androidx.compose.ui.unit.Dp$Companion r8 = androidx.compose.ui.unit.Dp.Companion
        L20:
            r5 = r11
            r6 = 0
            r1 = r7
            r1.<init>(r2, r3, r4, r5, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.layout.PaddingValuesImpl.<init>(float, float, float, float, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
