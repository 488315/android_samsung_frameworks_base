package androidx.compose.foundation.lazy;

import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutNearestRangeState;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.SnapshotIntStateKt;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class LazyListScrollPosition {
    public boolean hadFirstNotEmptyLayout;
    public final MutableIntState index$delegate;
    public Object lastKnownFirstItemKey;
    public final LazyLayoutNearestRangeState nearestRangeState;
    public final MutableIntState scrollOffset$delegate;

    /* JADX WARN: Illegal instructions before constructor call */
    public LazyListScrollPosition() {
        int i = 0;
        this(i, i, 3, null);
    }

    public final int getIndex() {
        return ((SnapshotMutableIntStateImpl) this.index$delegate).getIntValue();
    }

    public final int getScrollOffset() {
        return ((SnapshotMutableIntStateImpl) this.scrollOffset$delegate).getIntValue();
    }

    public final void update(int i, int i2) {
        if (i < 0.0f) {
            InlineClassHelperKt.throwIllegalArgumentException("Index should be non-negative (" + i + ')');
        }
        ((SnapshotMutableIntStateImpl) this.index$delegate).setIntValue(i);
        this.nearestRangeState.update(i);
        ((SnapshotMutableIntStateImpl) this.scrollOffset$delegate).setIntValue(i2);
    }

    public LazyListScrollPosition(int i, int i2) {
        this.index$delegate = SnapshotIntStateKt.mutableIntStateOf(i);
        this.scrollOffset$delegate = SnapshotIntStateKt.mutableIntStateOf(i2);
        this.nearestRangeState = new LazyLayoutNearestRangeState(i, 30, 100);
    }

    public /* synthetic */ LazyListScrollPosition(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? 0 : i2);
    }
}
