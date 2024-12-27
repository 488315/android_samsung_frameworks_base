package com.android.systemui.communal.ui.compose.extensions;

import androidx.compose.foundation.lazy.grid.LazyGridItemInfo;
import androidx.compose.foundation.lazy.grid.LazyGridMeasuredItem;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.unit.IntRect;
import androidx.compose.ui.unit.IntRectKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class LazyGridStateExtKt {
    /* renamed from: isItemAtOffset-Uv8p0NA, reason: not valid java name */
    public static final boolean m944isItemAtOffsetUv8p0NA(LazyGridItemInfo lazyGridItemInfo, long j) {
        IntRect m756IntRectVbeCjmY = IntRectKt.m756IntRectVbeCjmY(((LazyGridMeasuredItem) lazyGridItemInfo).offset, ((LazyGridMeasuredItem) lazyGridItemInfo).size);
        return new Rect(m756IntRectVbeCjmY.left, m756IntRectVbeCjmY.top, m756IntRectVbeCjmY.right, m756IntRectVbeCjmY.bottom).m336containsk4lQ0M(j);
    }
}
