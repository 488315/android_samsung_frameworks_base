package androidx.compose.ui.layout;

import androidx.compose.ui.unit.IntSize;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class FixedSizeIntrinsicsPlaceable extends Placeable {
    public FixedSizeIntrinsicsPlaceable(int i, int i2) {
        IntSize.Companion companion = IntSize.Companion;
        m624setMeasuredSizeozmzZPI((i2 & 4294967295L) | (i << 32));
    }

    @Override // androidx.compose.ui.layout.Measured
    public final int get(AlignmentLine alignmentLine) {
        return Integer.MIN_VALUE;
    }

    @Override // androidx.compose.ui.layout.Placeable
    /* renamed from: placeAt-f8xVGno, reason: not valid java name */
    public final void mo609placeAtf8xVGno(long j, float f, Function1 function1) {
    }
}
