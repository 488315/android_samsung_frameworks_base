package androidx.compose.foundation.gestures.snapping;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.foundation.lazy.grid.LazyGridItemInfo;
import androidx.compose.foundation.lazy.grid.LazyGridMeasuredItem;
import androidx.compose.ui.unit.IntOffset;

/* loaded from: classes.dex */
public abstract class LazyGridSnapLayoutInfoProviderKt {
    public static final int offsetOnMainAxis(LazyGridItemInfo lazyGridItemInfo, Orientation orientation) {
        long j;
        if (orientation == Orientation.Vertical) {
            long j2 = ((LazyGridMeasuredItem) lazyGridItemInfo).offset;
            IntOffset.Companion companion = IntOffset.Companion;
            j = j2 & 4294967295L;
        } else {
            long j3 = ((LazyGridMeasuredItem) lazyGridItemInfo).offset;
            IntOffset.Companion companion2 = IntOffset.Companion;
            j = j3 >> 32;
        }
        return (int) j;
    }
}
