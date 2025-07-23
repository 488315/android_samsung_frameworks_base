package androidx.compose.foundation.layout;

import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class UnionInsets implements WindowInsets {
    public final WindowInsets first;
    public final WindowInsets second;

    public UnionInsets(WindowInsets windowInsets, WindowInsets windowInsets2) {
        this.first = windowInsets;
        this.second = windowInsets2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof UnionInsets)) {
            return false;
        }
        UnionInsets unionInsets = (UnionInsets) obj;
        return Intrinsics.areEqual(unionInsets.first, this.first) && Intrinsics.areEqual(unionInsets.second, this.second);
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getBottom(Density density) {
        return Math.max(this.first.getBottom(density), this.second.getBottom(density));
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getLeft(Density density, LayoutDirection layoutDirection) {
        return Math.max(this.first.getLeft(density, layoutDirection), this.second.getLeft(density, layoutDirection));
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getRight(Density density, LayoutDirection layoutDirection) {
        return Math.max(this.first.getRight(density, layoutDirection), this.second.getRight(density, layoutDirection));
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getTop(Density density) {
        return Math.max(this.first.getTop(density), this.second.getTop(density));
    }

    public final int hashCode() {
        return (this.second.hashCode() * 31) + this.first.hashCode();
    }

    public final String toString() {
        return "(" + this.first + " ∪ " + this.second + ')';
    }
}
