package androidx.compose.foundation.layout;

import androidx.compose.foundation.layout.WindowInsetsSides;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
final class LimitInsets implements WindowInsets {
    public final WindowInsets insets;
    public final int sides;

    public /* synthetic */ LimitInsets(WindowInsets windowInsets, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(windowInsets, i);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LimitInsets)) {
            return false;
        }
        LimitInsets limitInsets = (LimitInsets) obj;
        if (Intrinsics.areEqual(this.insets, limitInsets.insets)) {
            int i = limitInsets.sides;
            WindowInsetsSides.Companion companion = WindowInsetsSides.Companion;
            if (this.sides == i) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getBottom(Density density) {
        WindowInsetsSides.Companion.getClass();
        if ((WindowInsetsSides.Bottom & this.sides) != 0) {
            return this.insets.getBottom(density);
        }
        return 0;
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getLeft(Density density, LayoutDirection layoutDirection) {
        int i;
        if (layoutDirection == LayoutDirection.Ltr) {
            WindowInsetsSides.Companion.getClass();
            i = WindowInsetsSides.AllowLeftInLtr;
        } else {
            WindowInsetsSides.Companion.getClass();
            i = WindowInsetsSides.AllowLeftInRtl;
        }
        if ((i & this.sides) != 0) {
            return this.insets.getLeft(density, layoutDirection);
        }
        return 0;
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getRight(Density density, LayoutDirection layoutDirection) {
        int i;
        if (layoutDirection == LayoutDirection.Ltr) {
            WindowInsetsSides.Companion.getClass();
            i = WindowInsetsSides.AllowRightInLtr;
        } else {
            WindowInsetsSides.Companion.getClass();
            i = WindowInsetsSides.AllowRightInRtl;
        }
        if ((i & this.sides) != 0) {
            return this.insets.getRight(density, layoutDirection);
        }
        return 0;
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getTop(Density density) {
        WindowInsetsSides.Companion.getClass();
        if ((WindowInsetsSides.Top & this.sides) != 0) {
            return this.insets.getTop(density);
        }
        return 0;
    }

    public final int hashCode() {
        int iHashCode = this.insets.hashCode() * 31;
        WindowInsetsSides.Companion companion = WindowInsetsSides.Companion;
        return Integer.hashCode(this.sides) + iHashCode;
    }

    public final String toString() {
        return "(" + this.insets + " only " + ((Object) WindowInsetsSides.m150toStringimpl(this.sides)) + ')';
    }

    private LimitInsets(WindowInsets windowInsets, int i) {
        this.insets = windowInsets;
        this.sides = i;
    }
}
