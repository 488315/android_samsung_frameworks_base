package kotlin.ranges;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class LongRange extends LongProgression {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        new LongRange(1L, 0L);
    }

    public LongRange(long j, long j2) {
        super(j, j2, 1L);
    }

    public final boolean contains(long j) {
        return this.first <= j && j <= this.last;
    }

    @Override // kotlin.ranges.LongProgression
    public final boolean equals(Object obj) {
        if (obj instanceof LongRange) {
            if (!isEmpty() || !((LongRange) obj).isEmpty()) {
                LongRange longRange = (LongRange) obj;
                if (this.first != longRange.first || this.last != longRange.last) {
                }
            }
            return true;
        }
        return false;
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
