package androidx.compose.ui.layout;

import androidx.compose.ui.unit.IntSize;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
final class FixedSizeIntrinsicsPlaceable extends Placeable {
    public FixedSizeIntrinsicsPlaceable(int i, int i2) {
        IntSize.Companion companion = IntSize.Companion;
        m626setMeasuredSizeozmzZPI((i2 & 4294967295L) | (i << 32));
    }

    @Override // androidx.compose.ui.layout.Measured
    public final int get(AlignmentLine alignmentLine) {
        return Integer.MIN_VALUE;
    }

    @Override // androidx.compose.ui.layout.Placeable
    /* renamed from: placeAt-f8xVGno, reason: not valid java name */
    public final void mo611placeAtf8xVGno(long j, float f, Function1 function1) {
    }
}
