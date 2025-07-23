package androidx.compose.material3.internal;

import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsetsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MutableWindowInsets implements WindowInsets {
    public final MutableState insets$delegate;

    public MutableWindowInsets() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getBottom(Density density) {
        return ((WindowInsets) ((SnapshotMutableStateImpl) this.insets$delegate).getValue()).getBottom(density);
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getLeft(Density density, LayoutDirection layoutDirection) {
        return ((WindowInsets) ((SnapshotMutableStateImpl) this.insets$delegate).getValue()).getLeft(density, layoutDirection);
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getRight(Density density, LayoutDirection layoutDirection) {
        return ((WindowInsets) ((SnapshotMutableStateImpl) this.insets$delegate).getValue()).getRight(density, layoutDirection);
    }

    @Override // androidx.compose.foundation.layout.WindowInsets
    public final int getTop(Density density) {
        return ((WindowInsets) ((SnapshotMutableStateImpl) this.insets$delegate).getValue()).getTop(density);
    }

    public MutableWindowInsets(WindowInsets windowInsets) {
        this.insets$delegate = SnapshotStateKt.mutableStateOf$default(windowInsets);
    }

    public /* synthetic */ MutableWindowInsets(WindowInsets windowInsets, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? WindowInsetsKt.WindowInsets() : windowInsets);
    }
}
