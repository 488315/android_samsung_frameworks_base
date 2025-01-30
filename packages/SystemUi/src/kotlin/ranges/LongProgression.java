package kotlin.ranges;

import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.markers.KMappedMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class LongProgression implements Iterable, KMappedMarker {
    public final long first;
    public final long last;
    public final long step;

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
    }

    public LongProgression(long j, long j2, long j3) {
        if (j3 == 0) {
            throw new IllegalArgumentException("Step must be non-zero.");
        }
        if (j3 == Long.MIN_VALUE) {
            throw new IllegalArgumentException("Step must be greater than Long.MIN_VALUE to avoid overflow on negation.");
        }
        this.first = j;
        if (j3 > 0) {
            if (j < j2) {
                long j4 = j2 % j3;
                long j5 = j % j3;
                long j6 = ((j4 < 0 ? j4 + j3 : j4) - (j5 < 0 ? j5 + j3 : j5)) % j3;
                j2 -= j6 < 0 ? j6 + j3 : j6;
            }
        } else {
            if (j3 >= 0) {
                throw new IllegalArgumentException("Step is zero.");
            }
            if (j > j2) {
                long j7 = -j3;
                long j8 = j % j7;
                long j9 = j2 % j7;
                long j10 = ((j8 < 0 ? j8 + j7 : j8) - (j9 < 0 ? j9 + j7 : j9)) % j7;
                j2 += j10 < 0 ? j10 + j7 : j10;
            }
        }
        this.last = j2;
        this.step = j3;
    }

    public boolean equals(Object obj) {
        if (obj instanceof LongProgression) {
            if (!isEmpty() || !((LongProgression) obj).isEmpty()) {
                LongProgression longProgression = (LongProgression) obj;
                if (this.first != longProgression.first || this.last != longProgression.last || this.step != longProgression.step) {
                }
            }
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        long j = 31;
        long j2 = this.first;
        long j3 = this.last;
        long j4 = (((j2 ^ (j2 >>> 32)) * j) + (j3 ^ (j3 >>> 32))) * j;
        long j5 = this.step;
        return (int) (j4 + (j5 ^ (j5 >>> 32)));
    }

    public boolean isEmpty() {
        long j = this.step;
        long j2 = this.first;
        long j3 = this.last;
        if (j > 0) {
            if (j2 > j3) {
                return true;
            }
        } else if (j2 < j3) {
            return true;
        }
        return false;
    }

    @Override // java.lang.Iterable
    public final Iterator iterator() {
        return new LongProgressionIterator(this.first, this.last, this.step);
    }

    public String toString() {
        StringBuilder sb;
        long j;
        if (this.step > 0) {
            sb = new StringBuilder();
            sb.append(this.first);
            sb.append("..");
            sb.append(this.last);
            sb.append(" step ");
            j = this.step;
        } else {
            sb = new StringBuilder();
            sb.append(this.first);
            sb.append(" downTo ");
            sb.append(this.last);
            sb.append(" step ");
            j = -this.step;
        }
        sb.append(j);
        return sb.toString();
    }
}
