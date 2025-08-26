package com.android.systemui.communal.ui.compose.extensions;

import androidx.compose.foundation.lazy.grid.LazyGridItemInfo;
import androidx.compose.foundation.lazy.grid.LazyGridMeasuredItem;
import androidx.compose.ui.unit.IntRectKt;
import java.util.Iterator;

/* loaded from: classes2.dex */
public abstract class LazyGridStateExtKt {
    /* renamed from: firstItemAtOffset-Uv8p0NA, reason: not valid java name */
    public static final LazyGridItemInfo m1090firstItemAtOffsetUv8p0NA(Iterable iterable, long j) {
        Object next;
        Iterator it = iterable.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            LazyGridMeasuredItem lazyGridMeasuredItem = (LazyGridMeasuredItem) ((LazyGridItemInfo) next);
            if (IntRectKt.toRect(IntRectKt.m860IntRectVbeCjmY(lazyGridMeasuredItem.offset, lazyGridMeasuredItem.size)).m407containsk4lQ0M(j)) {
                break;
            }
        }
        return (LazyGridItemInfo) next;
    }
}
