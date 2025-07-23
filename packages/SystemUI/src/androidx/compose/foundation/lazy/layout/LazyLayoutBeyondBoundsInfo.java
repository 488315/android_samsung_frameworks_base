package androidx.compose.foundation.lazy.layout;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.foundation.internal.InlineClassHelperKt;
import androidx.compose.runtime.collection.MutableVector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LazyLayoutBeyondBoundsInfo {
    public final MutableVector beyondBoundsItems = new MutableVector(new Interval[16], 0);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Interval {
        public final int end;
        public final int start;

        public Interval(int i, int i2) {
            this.start = i;
            this.end = i2;
            if (!(i >= 0)) {
                InlineClassHelperKt.throwIllegalArgumentException("negative start index");
            }
            if (i2 >= i) {
                return;
            }
            InlineClassHelperKt.throwIllegalArgumentException("end index greater than start");
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Interval)) {
                return false;
            }
            Interval interval = (Interval) obj;
            return this.start == interval.start && this.end == interval.end;
        }

        public final int hashCode() {
            return Integer.hashCode(this.end) + (Integer.hashCode(this.start) * 31);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Interval(start=");
            sb.append(this.start);
            sb.append(", end=");
            return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.end, ')');
        }
    }
}
