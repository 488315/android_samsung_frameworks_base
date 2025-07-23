package androidx.compose.foundation.layout;

import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class InsetsPaddingValues implements PaddingValues {
    public final Density density;
    public final WindowInsets insets;

    public InsetsPaddingValues(WindowInsets windowInsets, Density density) {
        this.insets = windowInsets;
        this.density = density;
    }

    @Override // androidx.compose.foundation.layout.PaddingValues
    /* renamed from: calculateBottomPadding-D9Ej5fM, reason: not valid java name */
    public final float mo109calculateBottomPaddingD9Ej5fM() {
        WindowInsets windowInsets = this.insets;
        Density density = this.density;
        return density.mo54toDpu2uoSUM(windowInsets.getBottom(density));
    }

    @Override // androidx.compose.foundation.layout.PaddingValues
    /* renamed from: calculateLeftPadding-u2uoSUM, reason: not valid java name */
    public final float mo110calculateLeftPaddingu2uoSUM(LayoutDirection layoutDirection) {
        WindowInsets windowInsets = this.insets;
        Density density = this.density;
        return density.mo54toDpu2uoSUM(windowInsets.getLeft(density, layoutDirection));
    }

    @Override // androidx.compose.foundation.layout.PaddingValues
    /* renamed from: calculateRightPadding-u2uoSUM, reason: not valid java name */
    public final float mo111calculateRightPaddingu2uoSUM(LayoutDirection layoutDirection) {
        WindowInsets windowInsets = this.insets;
        Density density = this.density;
        return density.mo54toDpu2uoSUM(windowInsets.getRight(density, layoutDirection));
    }

    @Override // androidx.compose.foundation.layout.PaddingValues
    /* renamed from: calculateTopPadding-D9Ej5fM, reason: not valid java name */
    public final float mo112calculateTopPaddingD9Ej5fM() {
        WindowInsets windowInsets = this.insets;
        Density density = this.density;
        return density.mo54toDpu2uoSUM(windowInsets.getTop(density));
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof InsetsPaddingValues)) {
            return false;
        }
        InsetsPaddingValues insetsPaddingValues = (InsetsPaddingValues) obj;
        return Intrinsics.areEqual(this.insets, insetsPaddingValues.insets) && Intrinsics.areEqual(this.density, insetsPaddingValues.density);
    }

    public final int hashCode() {
        return this.density.hashCode() + (this.insets.hashCode() * 31);
    }

    public final String toString() {
        return "InsetsPaddingValues(insets=" + this.insets + ", density=" + this.density + ')';
    }
}
