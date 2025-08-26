package androidx.compose.foundation.text.selection;

import androidx.compose.ui.Alignment;
import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntOffsetKt;
import androidx.compose.ui.unit.IntRect;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.window.PopupPositionProvider;

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
    public final long mo49calculatePositionllwVHH4(IntRect intRect, long j, LayoutDirection layoutDirection, long j2) {
        long jMo198provideF1C5BW0 = this.positionProvider.mo198provideF1C5BW0();
        if ((9223372034707292159L & jMo198provideF1C5BW0) == 9205357640488583168L) {
            jMo198provideF1C5BW0 = this.prevPosition;
        }
        this.prevPosition = jMo198provideF1C5BW0;
        IntSize.Companion.getClass();
        return IntOffset.m853plusqkQi6aY(IntOffset.m853plusqkQi6aY(intRect.m859getTopLeftnOccac(), IntOffsetKt.m856roundk4lQ0M(jMo198provideF1C5BW0)), this.handleReferencePoint.mo353alignKFBX0sM(j2, 0L, layoutDirection));
    }
}
