package androidx.compose.foundation.text.selection;

import androidx.compose.ui.Alignment;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntOffsetKt;
import androidx.compose.ui.unit.IntRect;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.window.PopupPositionProvider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class HandlePositionProvider implements PopupPositionProvider {
    public final Alignment handleReferencePoint;
    public final OffsetProvider positionProvider;
    public long prevPosition;

    public HandlePositionProvider(Alignment alignment, OffsetProvider offsetProvider) {
        this.handleReferencePoint = alignment;
        this.positionProvider = offsetProvider;
        Offset.Companion.getClass();
        this.prevPosition = 0L;
    }

    @Override // androidx.compose.ui.window.PopupPositionProvider
    /* renamed from: calculatePosition-llwVHH4 */
    public final long mo48calculatePositionllwVHH4(IntRect intRect, long j, LayoutDirection layoutDirection, long j2) {
        long mo197provideF1C5BW0 = this.positionProvider.mo197provideF1C5BW0();
        if ((9223372034707292159L & mo197provideF1C5BW0) == 9205357640488583168L) {
            mo197provideF1C5BW0 = this.prevPosition;
        }
        this.prevPosition = mo197provideF1C5BW0;
        IntSize.Companion.getClass();
        return IntOffset.m851plusqkQi6aY(IntOffset.m851plusqkQi6aY(intRect.m857getTopLeftnOccac(), IntOffsetKt.m854roundk4lQ0M(mo197provideF1C5BW0)), this.handleReferencePoint.mo352alignKFBX0sM(j2, 0L, layoutDirection));
    }
}
