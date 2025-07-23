package androidx.compose.foundation.lazy.layout;

import androidx.compose.foundation.internal.InlineClassHelperKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class IntervalList$Interval<T> {
    public final int size;
    public final int startIndex;
    public final Object value;

    public IntervalList$Interval(int i, int i2, T t) {
        this.startIndex = i;
        this.size = i2;
        this.value = t;
        if (!(i >= 0)) {
            InlineClassHelperKt.throwIllegalArgumentException("startIndex should be >= 0");
        }
        if (i2 > 0) {
            return;
        }
        InlineClassHelperKt.throwIllegalArgumentException("size should be > 0");
    }
}
