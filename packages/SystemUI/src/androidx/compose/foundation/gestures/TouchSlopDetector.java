package androidx.compose.foundation.gestures;

import androidx.compose.ui.geometry.Offset;
import androidx.compose.ui.input.pointer.PointerInputChange;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class TouchSlopDetector {
    public final Orientation orientation;
    public long totalPositionChange;

    public /* synthetic */ TouchSlopDetector(Orientation orientation, long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(orientation, j);
    }

    /* renamed from: addPointerInputChange-dBAh8RU, reason: not valid java name */
    public final long m87addPointerInputChangedBAh8RU(PointerInputChange pointerInputChange, float f) {
        long jM403plusMKHz9U = Offset.m403plusMKHz9U(this.totalPositionChange, Offset.m402minusMKHz9U(pointerInputChange.position, pointerInputChange.previousPosition));
        this.totalPositionChange = jM403plusMKHz9U;
        Orientation orientation = this.orientation;
        if ((orientation == null ? Offset.m399getDistanceimpl(jM403plusMKHz9U) : Math.abs(m88mainAxisk4lQ0M(jM403plusMKHz9U))) < f) {
            Offset.Companion.getClass();
            return Offset.Unspecified;
        }
        if (orientation == null) {
            long j = this.totalPositionChange;
            return Offset.m402minusMKHz9U(this.totalPositionChange, Offset.m404timestuRUvjQ(f, Offset.m397divtuRUvjQ(Offset.m399getDistanceimpl(j), j)));
        }
        float fM88mainAxisk4lQ0M = m88mainAxisk4lQ0M(this.totalPositionChange) - (Math.signum(m88mainAxisk4lQ0M(this.totalPositionChange)) * f);
        long j2 = this.totalPositionChange;
        Orientation orientation2 = Orientation.Horizontal;
        float fIntBitsToFloat = Float.intBitsToFloat((int) (orientation == orientation2 ? j2 & 4294967295L : j2 >> 32));
        if (orientation == orientation2) {
            return (Float.floatToRawIntBits(fM88mainAxisk4lQ0M) << 32) | (Float.floatToRawIntBits(fIntBitsToFloat) & 4294967295L);
        }
        return (Float.floatToRawIntBits(fIntBitsToFloat) << 32) | (Float.floatToRawIntBits(fM88mainAxisk4lQ0M) & 4294967295L);
    }

    /* renamed from: mainAxis-k-4lQ0M, reason: not valid java name */
    public final float m88mainAxisk4lQ0M(long j) {
        return Float.intBitsToFloat((int) (this.orientation == Orientation.Horizontal ? j >> 32 : j & 4294967295L));
    }

    private TouchSlopDetector(Orientation orientation, long j) {
        this.orientation = orientation;
        this.totalPositionChange = j;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public TouchSlopDetector(Orientation orientation, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        DefaultConstructorMarker defaultConstructorMarker2 = null;
        orientation = (i & 1) != 0 ? null : orientation;
        if ((i & 2) != 0) {
            Offset.Companion.getClass();
            j = 0;
        }
        this(orientation, j, defaultConstructorMarker2);
    }
}
