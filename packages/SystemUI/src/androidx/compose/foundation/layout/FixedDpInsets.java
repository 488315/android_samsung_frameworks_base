package androidx.compose.foundation.layout;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.BorderModifierNodeElement$$ExternalSyntheticOutline0;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
final class FixedDpInsets implements WindowInsets {
    public final float bottomDp;
    public final float leftDp;
    public final float rightDp;
    public final float topDp;

    public /* synthetic */ FixedDpInsets(float f, float f2, float f3, float f4, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, f2, f3, f4);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FixedDpInsets)) {
            return false;
        }
        FixedDpInsets fixedDpInsets = (FixedDpInsets) obj;
        return Dp.m838equalsimpl0(this.leftDp, fixedDpInsets.leftDp) && Dp.m838equalsimpl0(this.topDp, fixedDpInsets.topDp) && Dp.m838equalsimpl0(this.rightDp, fixedDpInsets.rightDp) && Dp.m838equalsimpl0(this.bottomDp, fixedDpInsets.bottomDp);
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getBottom(Density density) {
        return density.mo52roundToPx0680j_4(this.bottomDp);
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getLeft(Density density, LayoutDirection layoutDirection) {
        return density.mo52roundToPx0680j_4(this.leftDp);
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getRight(Density density, LayoutDirection layoutDirection) {
        return density.mo52roundToPx0680j_4(this.rightDp);
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getTop(Density density) {
        return density.mo52roundToPx0680j_4(this.topDp);
    }

    public final int hashCode() {
        Dp.Companion companion = Dp.Companion;
        return Float.hashCode(this.bottomDp) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.rightDp, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.topDp, Float.hashCode(this.leftDp) * 31, 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Insets(left=");
        BorderModifierNodeElement$$ExternalSyntheticOutline0.m(this.leftDp, ", top=", sb);
        BorderModifierNodeElement$$ExternalSyntheticOutline0.m(this.topDp, ", right=", sb);
        BorderModifierNodeElement$$ExternalSyntheticOutline0.m(this.rightDp, ", bottom=", sb);
        sb.append((Object) Dp.m839toStringimpl(this.bottomDp));
        sb.append(')');
        return sb.toString();
    }

    private FixedDpInsets(float f, float f2, float f3, float f4) {
        this.leftDp = f;
        this.topDp = f2;
        this.rightDp = f3;
        this.bottomDp = f4;
    }
}
