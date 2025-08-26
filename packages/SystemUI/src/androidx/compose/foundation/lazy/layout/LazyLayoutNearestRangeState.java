package androidx.compose.foundation.lazy.layout;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.State;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
public final class LazyLayoutNearestRangeState implements State<IntRange> {
    public static final Companion Companion = new Companion(null);
    public final int extraItemCount;
    public int lastFirstVisibleItem;
    public final int slidingWindowSize;
    public final MutableState value$delegate;

    final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public LazyLayoutNearestRangeState(int i, int i2, int i3) {
        this.slidingWindowSize = i2;
        this.extraItemCount = i3;
        Companion.getClass();
        int i4 = (i / i2) * i2;
        this.value$delegate = SnapshotStateKt.mutableStateOf(RangesKt___RangesKt.until(Math.max(i4 - i3, 0), i4 + i2 + i3), SnapshotStateKt.structuralEqualityPolicy());
        this.lastFirstVisibleItem = i;
    }

    @Override // androidx.compose.runtime.State
    public final Object getValue() {
        return (IntRange) ((SnapshotMutableStateImpl) this.value$delegate).getValue();
    }

    public final void update(int i) {
        if (i != this.lastFirstVisibleItem) {
            this.lastFirstVisibleItem = i;
            Companion.getClass();
            int i2 = this.slidingWindowSize;
            int i3 = (i / i2) * i2;
            int i4 = this.extraItemCount;
            ((SnapshotMutableStateImpl) this.value$delegate).setValue(RangesKt___RangesKt.until(Math.max(i3 - i4, 0), i3 + i2 + i4));
        }
    }
}
