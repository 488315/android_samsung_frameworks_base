package androidx.compose.foundation.pager;

import androidx.compose.foundation.lazy.layout.LazyLayoutNearestRangeState;
import androidx.compose.runtime.MutableFloatState;
import androidx.compose.runtime.MutableIntState;
import androidx.compose.runtime.PrimitiveSnapshotStateKt;
import androidx.compose.runtime.SnapshotIntStateKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PagerScrollPosition {
    public final MutableIntState currentPage$delegate;
    public final MutableFloatState currentPageOffsetFraction$delegate;
    public boolean hadFirstNotEmptyLayout;
    public Object lastKnownCurrentPageKey;
    public final LazyLayoutNearestRangeState nearestRangeState;
    public final PagerState state;

    public PagerScrollPosition(int i, float f, PagerState pagerState) {
        this.state = pagerState;
        this.currentPage$delegate = SnapshotIntStateKt.mutableIntStateOf(i);
        this.currentPageOffsetFraction$delegate = PrimitiveSnapshotStateKt.mutableFloatStateOf(f);
        this.nearestRangeState = new LazyLayoutNearestRangeState(i, 30, 100);
    }

    public /* synthetic */ PagerScrollPosition(int i, float f, PagerState pagerState, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0 : i, (i2 & 2) != 0 ? 0.0f : f, pagerState);
    }
}
