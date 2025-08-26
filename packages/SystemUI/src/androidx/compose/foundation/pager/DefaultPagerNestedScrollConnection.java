package androidx.compose.foundation.pager;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection;
import androidx.compose.ui.input.nestedscroll.NestedScrollSource;
import androidx.compose.ui.unit.Velocity;
import java.util.concurrent.CancellationException;
import kotlin.coroutines.Continuation;
import kotlin.ranges.RangesKt___RangesKt;

/* loaded from: classes.dex */
final class DefaultPagerNestedScrollConnection implements NestedScrollConnection {
    public final Orientation orientation;
    public final PagerState state;

    public DefaultPagerNestedScrollConnection(PagerState pagerState, Orientation orientation) {
        this.state = pagerState;
        this.orientation = orientation;
    }

    @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
    /* renamed from: onPostFling-RZ2iAVY */
    public final Object mo78onPostFlingRZ2iAVY(long j, long j2, Continuation continuation) {
        return Velocity.m878boximpl(this.orientation == Orientation.Vertical ? Velocity.m879copyOhffZ5M$default(0.0f, 0.0f, j2, 2) : Velocity.m879copyOhffZ5M$default(0.0f, 0.0f, j2, 1));
    }

    @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
    /* renamed from: onPostScroll-DzOQY0M */
    public final long mo79onPostScrollDzOQY0M(int i, long j, long j2) {
        NestedScrollSource.Companion.getClass();
        if (i == NestedScrollSource.SideEffect) {
            if (Float.intBitsToFloat((int) (this.orientation == Orientation.Horizontal ? j2 >> 32 : 4294967295L & j2)) != 0.0f) {
                throw new CancellationException("Scroll cancelled");
            }
        }
        Offset.Companion.getClass();
        return 0L;
    }

    @Override // androidx.compose.ui.input.nestedscroll.NestedScrollConnection
    /* renamed from: onPreScroll-OzD1aCk, reason: not valid java name */
    public final long mo176onPreScrollOzD1aCk(int i, long j) {
        NestedScrollSource.Companion.getClass();
        if (i == NestedScrollSource.UserInput) {
            PagerState pagerState = this.state;
            if (Math.abs(pagerState.getCurrentPageOffsetFraction()) > 1.0E-6d) {
                float currentPageOffsetFraction = pagerState.getCurrentPageOffsetFraction() * pagerState.getPageSize$foundation_release();
                float f = ((((PagerMeasureResult) pagerState.getLayoutInfo()).pageSize + ((PagerMeasureResult) pagerState.getLayoutInfo()).pageSpacing) * (-Math.signum(pagerState.getCurrentPageOffsetFraction()))) + currentPageOffsetFraction;
                if (pagerState.getCurrentPageOffsetFraction() > 0.0f) {
                    f = currentPageOffsetFraction;
                    currentPageOffsetFraction = f;
                }
                Orientation orientation = Orientation.Horizontal;
                Orientation orientation2 = this.orientation;
                float fIntBitsToFloat = -pagerState.scrollableState.dispatchRawDelta(-RangesKt___RangesKt.coerceIn(Float.intBitsToFloat((int) (orientation2 == orientation ? j >> 32 : j & 4294967295L)), currentPageOffsetFraction, f));
                float fIntBitsToFloat2 = orientation2 == orientation ? fIntBitsToFloat : Float.intBitsToFloat((int) (j >> 32));
                if (orientation2 != Orientation.Vertical) {
                    fIntBitsToFloat = Float.intBitsToFloat((int) (j & 4294967295L));
                }
                Offset.Companion companion = Offset.Companion;
                return (Float.floatToRawIntBits(fIntBitsToFloat) & 4294967295L) | (Float.floatToRawIntBits(fIntBitsToFloat2) << 32);
            }
        }
        Offset.Companion.getClass();
        return 0L;
    }
}
