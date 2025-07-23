package com.android.systemui.communal.ui.compose.extensions;

import androidx.compose.foundation.lazy.grid.LazyGridItemInfo;
import androidx.compose.foundation.lazy.grid.LazyGridMeasuredItem;
import androidx.compose.ui.unit.IntRectKt;
import java.util.Iterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class LazyGridStateExtKt {
    /* renamed from: firstItemAtOffset-Uv8p0NA, reason: not valid java name */
    public static final LazyGridItemInfo m1088firstItemAtOffsetUv8p0NA(Iterable iterable, long j) {
        Object obj;
        Iterator it = iterable.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            LazyGridMeasuredItem lazyGridMeasuredItem = (LazyGridMeasuredItem) ((LazyGridItemInfo) obj);
            if (IntRectKt.toRect(IntRectKt.m858IntRectVbeCjmY(lazyGridMeasuredItem.offset, lazyGridMeasuredItem.size)).m405containsk4lQ0M(j)) {
                break;
            }
        }
        return (LazyGridItemInfo) obj;
    }
}
