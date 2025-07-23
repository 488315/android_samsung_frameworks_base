package androidx.compose.foundation.lazy;

import androidx.compose.foundation.lazy.layout.LazyLayoutIntervalContent;
import androidx.compose.foundation.lazy.layout.MutableIntervalList;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LazyListIntervalContent extends LazyLayoutIntervalContent<LazyListInterval> implements LazyListScope {
    public final MutableIntervalList intervals = new MutableIntervalList();

    public LazyListIntervalContent(Function1 function1) {
        function1.mo779invoke(this);
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutIntervalContent
    public final MutableIntervalList getIntervals$1() {
        return this.intervals;
    }
}
