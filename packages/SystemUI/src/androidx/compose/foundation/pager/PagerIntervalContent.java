package androidx.compose.foundation.pager;

import androidx.compose.foundation.lazy.layout.LazyLayoutIntervalContent;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PagerIntervalContent implements LazyLayoutIntervalContent.Interval {
    public final Function4 item;
    public final Function1 key;

    public PagerIntervalContent(Function1 function1, Function4 function4) {
        this.key = function1;
        this.item = function4;
    }

    @Override // androidx.compose.foundation.lazy.layout.LazyLayoutIntervalContent.Interval
    public final Function1 getKey() {
        return this.key;
    }
}
