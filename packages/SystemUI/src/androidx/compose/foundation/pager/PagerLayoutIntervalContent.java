package androidx.compose.foundation.pager;

import androidx.compose.foundation.lazy.layout.LazyLayoutIntervalContent;
import androidx.compose.foundation.lazy.layout.MutableIntervalList;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class PagerLayoutIntervalContent extends LazyLayoutIntervalContent<PagerIntervalContent> {
    public final MutableIntervalList intervals;

    public PagerLayoutIntervalContent(Function4 function4, Function1 function1, int i) {
        MutableIntervalList mutableIntervalList = new MutableIntervalList();
        mutableIntervalList.addInterval(i, new PagerIntervalContent(function1, function4));
        this.intervals = mutableIntervalList;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutIntervalContent
    public final MutableIntervalList getIntervals$1() {
        return this.intervals;
    }
}
