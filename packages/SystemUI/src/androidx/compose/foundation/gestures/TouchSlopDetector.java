package androidx.compose.foundation.gestures;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerInputChange;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TouchSlopDetector {
    public final Orientation orientation;
    public long totalPositionChange;

    public /* synthetic */ TouchSlopDetector(Orientation orientation, long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(orientation, j);
    }

    /* renamed from: addPointerInputChange-dBAh8RU, reason: not valid java name */
    public final long m86addPointerInputChangedBAh8RU(PointerInputChange pointerInputChange, float f) {
        long m401plusMKHz9U = Offset.m401plusMKHz9U(this.totalPositionChange, Offset.m400minusMKHz9U(pointerInputChange.position, pointerInputChange.previousPosition));
        this.totalPositionChange = m401plusMKHz9U;
        Orientation orientation = this.orientation;
        if ((orientation == null ? Offset.m397getDistanceimpl(m401plusMKHz9U) : Math.abs(m87mainAxisk4lQ0M(m401plusMKHz9U))) < f) {
            Offset.Companion.getClass();
            return Offset.Unspecified;
        }
        if (orientation == null) {
            long j = this.totalPositionChange;
            return Offset.m400minusMKHz9U(this.totalPositionChange, Offset.m402timestuRUvjQ(f, Offset.m395divtuRUvjQ(Offset.m397getDistanceimpl(j), j)));
        }
        float m87mainAxisk4lQ0M = m87mainAxisk4lQ0M(this.totalPositionChange) - (Math.signum(m87mainAxisk4lQ0M(this.totalPositionChange)) * f);
        long j2 = this.totalPositionChange;
        Orientation orientation2 = Orientation.Horizontal;
        float intBitsToFloat = Float.intBitsToFloat((int) (orientation == orientation2 ? j2 & 4294967295L : j2 >> 32));
        if (orientation == orientation2) {
            return (Float.floatToRawIntBits(m87mainAxisk4lQ0M) << 32) | (Float.floatToRawIntBits(intBitsToFloat) & 4294967295L);
        }
        return (Float.floatToRawIntBits(intBitsToFloat) << 32) | (Float.floatToRawIntBits(m87mainAxisk4lQ0M) & 4294967295L);
    }

    /* renamed from: mainAxis-k-4lQ0M, reason: not valid java name */
    public final float m87mainAxisk4lQ0M(long j) {
        return Float.intBitsToFloat((int) (this.orientation == Orientation.Horizontal ? j >> 32 : j & 4294967295L));
    }

    private TouchSlopDetector(Orientation orientation, long j) {
        this.orientation = orientation;
        this.totalPositionChange = j;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TouchSlopDetector(androidx.compose.foundation.gestures.Orientation r2, long r3, int r5, kotlin.jvm.internal.DefaultConstructorMarker r6) {
        /*
            r1 = this;
            r6 = r5 & 1
            r0 = 0
            if (r6 == 0) goto L6
            r2 = r0
        L6:
            r5 = r5 & 2
            if (r5 == 0) goto L11
            androidx.compose.ui.geometry.Offset$Companion r3 = androidx.compose.ui.geometry.Offset.Companion
            r3.getClass()
            r3 = 0
        L11:
            r1.<init>(r2, r3, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.TouchSlopDetector.<init>(androidx.compose.foundation.gestures.Orientation, long, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
