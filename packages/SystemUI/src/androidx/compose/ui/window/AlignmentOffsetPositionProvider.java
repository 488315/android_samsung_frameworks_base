package androidx.compose.ui.window;

import androidx.compose.ui.Alignment;
import androidx.compose.ui.unit.IntOffset;
import androidx.compose.ui.unit.IntRect;
import androidx.compose.ui.unit.IntSize;
import androidx.compose.ui.unit.LayoutDirection;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class AlignmentOffsetPositionProvider implements PopupPositionProvider {
    public final Alignment alignment;
    public final long offset;

    public /* synthetic */ AlignmentOffsetPositionProvider(Alignment alignment, long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(alignment, j);
    }

    @Override // androidx.compose.ui.window.PopupPositionProvider
    /* renamed from: calculatePosition-llwVHH4 */
    public final long mo49calculatePositionllwVHH4(IntRect intRect, long j, LayoutDirection layoutDirection, long j2) {
        IntSize.Companion companion = IntSize.Companion;
        companion.getClass();
        IntSize.Companion companion2 = IntSize.Companion;
        long jMo353alignKFBX0sM = this.alignment.mo353alignKFBX0sM(0L, (intRect.getWidth() << 32) | (intRect.getHeight() & 4294967295L), layoutDirection);
        companion.getClass();
        long jMo353alignKFBX0sM2 = this.alignment.mo353alignKFBX0sM(0L, j2, layoutDirection);
        IntOffset.Companion companion3 = IntOffset.Companion;
        long j3 = ((-((int) (jMo353alignKFBX0sM2 >> 32))) << 32) | ((-((int) (jMo353alignKFBX0sM2 & 4294967295L))) & 4294967295L);
        long j4 = this.offset;
        return IntOffset.m853plusqkQi6aY(IntOffset.m853plusqkQi6aY(IntOffset.m853plusqkQi6aY(intRect.m859getTopLeftnOccac(), jMo353alignKFBX0sM), j3), (4294967295L & ((int) (j4 & 4294967295L))) | ((((int) (j4 >> 32)) * (layoutDirection == LayoutDirection.Ltr ? 1 : -1)) << 32));
    }

    private AlignmentOffsetPositionProvider(Alignment alignment, long j) {
        this.alignment = alignment;
        this.offset = j;
    }
}
