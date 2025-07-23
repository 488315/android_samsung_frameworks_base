package androidx.compose.ui.platform;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LazyWindowInfo implements WindowInfo {
    public final MutableState isWindowFocused$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);

    @Override // androidx.compose.ui.platform.WindowInfo
    public final boolean isWindowFocused() {
        return ((Boolean) ((SnapshotMutableStateImpl) this.isWindowFocused$delegate).getValue()).booleanValue();
    }
}
