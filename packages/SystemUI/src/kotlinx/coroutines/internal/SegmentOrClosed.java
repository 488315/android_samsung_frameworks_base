package kotlinx.coroutines.internal;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SegmentOrClosed {
    public final Object value;

    /* renamed from: getSegment-impl, reason: not valid java name */
    public static final Segment m3465getSegmentimpl(Object obj) {
        if (obj != ConcurrentLinkedListKt.CLOSED) {
            return (Segment) obj;
        }
        throw new IllegalStateException("Does not contain segment");
    }

    /* renamed from: isClosed-impl, reason: not valid java name */
    public static final boolean m3466isClosedimpl(Object obj) {
        return obj == ConcurrentLinkedListKt.CLOSED;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof SegmentOrClosed) {
            return Intrinsics.areEqual(this.value, ((SegmentOrClosed) obj).value);
        }
        return false;
    }

    public final int hashCode() {
        Object obj = this.value;
        if (obj == null) {
            return 0;
        }
        return obj.hashCode();
    }

    public final String toString() {
        return "SegmentOrClosed(value=" + this.value + ")";
    }
}
