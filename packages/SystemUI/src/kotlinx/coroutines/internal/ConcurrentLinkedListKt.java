package kotlinx.coroutines.internal;

import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ConcurrentLinkedListKt {
    public static final Symbol CLOSED = new Symbol("CLOSED");

    public static final Object findSegmentInternal(Segment segment, long j, Function2 function2) {
        while (true) {
            if (segment.id >= j && !segment.isRemoved()) {
                return segment;
            }
            Object obj = segment._next.value;
            Symbol symbol = CLOSED;
            if (obj == symbol) {
                return symbol;
            }
            Segment segment2 = (Segment) ((ConcurrentLinkedListNode) obj);
            if (segment2 == null) {
                segment2 = (Segment) function2.invoke(Long.valueOf(segment.id + 1), segment);
                if (segment._next.compareAndSet(null, segment2)) {
                    if (segment.isRemoved()) {
                        segment.remove();
                    }
                }
            }
            segment = segment2;
        }
    }
}
