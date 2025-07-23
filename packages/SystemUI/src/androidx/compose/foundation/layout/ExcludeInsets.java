package androidx.compose.foundation.layout;

import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ExcludeInsets implements WindowInsets {
    public final WindowInsets excluded;
    public final WindowInsets included;

    public ExcludeInsets(WindowInsets windowInsets, WindowInsets windowInsets2) {
        this.included = windowInsets;
        this.excluded = windowInsets2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ExcludeInsets)) {
            return false;
        }
        ExcludeInsets excludeInsets = (ExcludeInsets) obj;
        return Intrinsics.areEqual(excludeInsets.included, this.included) && Intrinsics.areEqual(excludeInsets.excluded, this.excluded);
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getBottom(Density density) {
        int bottom = this.included.getBottom(density) - this.excluded.getBottom(density);
        if (bottom < 0) {
            return 0;
        }
        return bottom;
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getLeft(Density density, LayoutDirection layoutDirection) {
        int left = this.included.getLeft(density, layoutDirection) - this.excluded.getLeft(density, layoutDirection);
        if (left < 0) {
            return 0;
        }
        return left;
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getRight(Density density, LayoutDirection layoutDirection) {
        int right = this.included.getRight(density, layoutDirection) - this.excluded.getRight(density, layoutDirection);
        if (right < 0) {
            return 0;
        }
        return right;
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getTop(Density density) {
        int top = this.included.getTop(density) - this.excluded.getTop(density);
        if (top < 0) {
            return 0;
        }
        return top;
    }

    public final int hashCode() {
        return this.excluded.hashCode() + (this.included.hashCode() * 31);
    }

    public final String toString() {
        return "(" + this.included + " - " + this.excluded + ')';
    }
}
