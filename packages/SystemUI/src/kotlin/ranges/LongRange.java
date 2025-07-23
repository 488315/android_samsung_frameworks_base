package kotlin.ranges;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class LongRange extends LongProgression {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        new LongRange(1L, 0L);
    }

    public LongRange(long j, long j2) {
        super(j, j2, 1L);
    }

    @Override // kotlin.ranges.LongProgression
    public final boolean equals(Object obj) {
        if (!(obj instanceof LongRange)) {
            return false;
        }
        if (isEmpty() && ((LongRange) obj).isEmpty()) {
            return true;
        }
        LongRange longRange = (LongRange) obj;
        return this.first == longRange.first && this.last == longRange.last;
    }

    @Override // kotlin.ranges.LongProgression
    public final int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        long j = this.first;
        long j2 = 31 * (j ^ (j >>> 32));
        long j3 = this.last;
        return (int) (j2 + (j3 ^ (j3 >>> 32)));
    }

    @Override // kotlin.ranges.LongProgression
    public final boolean isEmpty() {
        return this.first > this.last;
    }

    @Override // kotlin.ranges.LongProgression
    public final String toString() {
        return this.first + ".." + this.last;
    }
}
