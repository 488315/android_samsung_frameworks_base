package androidx.compose.ui.draw;

import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.DensityKt;
import androidx.compose.ui.unit.LayoutDirection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class EmptyBuildDrawCacheParams implements BuildDrawCacheParams {
    public static final EmptyBuildDrawCacheParams INSTANCE = new EmptyBuildDrawCacheParams();
    public static final Density density;
    public static final LayoutDirection layoutDirection;
    public static final long size;

    static {
        Size.Companion.getClass();
        size = Size.Unspecified;
        layoutDirection = LayoutDirection.Ltr;
        density = DensityKt.Density(1.0f, 1.0f);
    }

    private EmptyBuildDrawCacheParams() {
    }

    @Override // androidx.compose.ui.draw.BuildDrawCacheParams
    public final Density getDensity() {
        return density;
    }

    @Override // androidx.compose.ui.draw.BuildDrawCacheParams
    public final LayoutDirection getLayoutDirection() {
        return layoutDirection;
    }

    @Override // androidx.compose.ui.draw.BuildDrawCacheParams
    /* renamed from: getSize-NH-jbRc */
    public final long mo360getSizeNHjbRc() {
        return size;
    }
}
