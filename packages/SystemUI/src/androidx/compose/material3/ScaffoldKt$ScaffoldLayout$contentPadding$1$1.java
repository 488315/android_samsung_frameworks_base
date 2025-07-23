package androidx.compose.material3;

import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.foundation.layout.PaddingValues;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.LayoutDirection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ScaffoldKt$ScaffoldLayout$contentPadding$1$1 implements PaddingValues {
    public final MutableState paddingHolder$delegate;

    public ScaffoldKt$ScaffoldLayout$contentPadding$1$1() {
        Dp.Companion companion = Dp.Companion;
        this.paddingHolder$delegate = SnapshotStateKt.mutableStateOf$default(PaddingKt.m119PaddingValues0680j_4(0));
    }

    @Override // androidx.compose.foundation.layout.PaddingValues
    /* renamed from: calculateBottomPadding-D9Ej5fM */
    public final float mo109calculateBottomPaddingD9Ej5fM() {
        return ((PaddingValues) ((SnapshotMutableStateImpl) this.paddingHolder$delegate).getValue()).mo109calculateBottomPaddingD9Ej5fM();
    }

    @Override // androidx.compose.foundation.layout.PaddingValues
    /* renamed from: calculateLeftPadding-u2uoSUM */
    public final float mo110calculateLeftPaddingu2uoSUM(LayoutDirection layoutDirection) {
        return ((PaddingValues) ((SnapshotMutableStateImpl) this.paddingHolder$delegate).getValue()).mo110calculateLeftPaddingu2uoSUM(layoutDirection);
    }

    @Override // androidx.compose.foundation.layout.PaddingValues
    /* renamed from: calculateRightPadding-u2uoSUM */
    public final float mo111calculateRightPaddingu2uoSUM(LayoutDirection layoutDirection) {
        return ((PaddingValues) ((SnapshotMutableStateImpl) this.paddingHolder$delegate).getValue()).mo111calculateRightPaddingu2uoSUM(layoutDirection);
    }

    @Override // androidx.compose.foundation.layout.PaddingValues
    /* renamed from: calculateTopPadding-D9Ej5fM */
    public final float mo112calculateTopPaddingD9Ej5fM() {
        return ((PaddingValues) ((SnapshotMutableStateImpl) this.paddingHolder$delegate).getValue()).mo112calculateTopPaddingD9Ej5fM();
    }
}
