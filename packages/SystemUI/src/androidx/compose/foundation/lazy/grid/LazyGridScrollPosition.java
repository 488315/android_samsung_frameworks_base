package androidx.compose.foundation.lazy.grid;

import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutNearestRangeState;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.SnapshotIntStateKt;
import androidx.compose.runtime.SnapshotMutableIntStateImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LazyGridScrollPosition {
    public boolean hadFirstNotEmptyLayout;
    public final MutableIntState index$delegate;
    public Object lastKnownFirstItemKey;
    public final LazyLayoutNearestRangeState nearestRangeState;
    public final MutableIntState scrollOffset$delegate;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public LazyGridScrollPosition() {
        /*
            r3 = this;
            r0 = 3
            r1 = 0
            r2 = 0
            r3.<init>(r2, r2, r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.grid.LazyGridScrollPosition.<init>():void");
    }

    public final int getIndex() {
        return ((SnapshotMutableIntStateImpl) this.index$delegate).getIntValue();
    }

    public final int getScrollOffset() {
        return ((SnapshotMutableIntStateImpl) this.scrollOffset$delegate).getIntValue();
    }

    public final void update(int i, int i2) {
        if (i < 0.0f) {
            InlineClassHelperKt.throwIllegalArgumentException("Index should be non-negative");
        }
        ((SnapshotMutableIntStateImpl) this.index$delegate).setIntValue(i);
        this.nearestRangeState.update(i);
        ((SnapshotMutableIntStateImpl) this.scrollOffset$delegate).setIntValue(i2);
    }

    public LazyGridScrollPosition(int i, int i2) {
        this.index$delegate = SnapshotIntStateKt.mutableIntStateOf(i);
        this.scrollOffset$delegate = SnapshotIntStateKt.mutableIntStateOf(i2);
        this.nearestRangeState = new LazyLayoutNearestRangeState(i, 90, 200);
    }

    public /* synthetic */ LazyGridScrollPosition(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? 0 : i2);
    }
}
