package androidx.compose.foundation.lazy;

import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.SnapshotIntStateKt;

/* loaded from: classes.dex */
public final class LazyItemScopeImpl implements LazyItemScope {
    public final MutableIntState maxWidthState = SnapshotIntStateKt.mutableIntStateOf(Integer.MAX_VALUE);
    public final MutableIntState maxHeightState = SnapshotIntStateKt.mutableIntStateOf(Integer.MAX_VALUE);
}
