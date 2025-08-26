package androidx.compose.ui.platform;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;

/* loaded from: classes.dex */
public final class LazyWindowInfo implements WindowInfo {
    public final MutableState isWindowFocused$delegate = SnapshotStateKt.mutableStateOf$default(Boolean.FALSE);

    @Override // androidx.compose.ui.platform.WindowInfo
    public final boolean isWindowFocused() {
        return ((Boolean) ((SnapshotMutableStateImpl) this.isWindowFocused$delegate).getValue()).booleanValue();
    }
}
