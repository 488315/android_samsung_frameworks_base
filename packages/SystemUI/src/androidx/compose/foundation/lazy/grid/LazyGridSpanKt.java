package androidx.compose.foundation.lazy.grid;

import androidx.compose.foundation.internal.InlineClassHelperKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class LazyGridSpanKt {
    public static final long GridItemSpan(int i) {
        if (!(i > 0)) {
            InlineClassHelperKt.throwIllegalArgumentException("The span value should be higher than 0");
        }
        return i;
    }
}
