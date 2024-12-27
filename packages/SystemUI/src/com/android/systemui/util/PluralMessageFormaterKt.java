package com.android.systemui.util;

import android.content.res.Resources;
import android.util.PluralsMessageFormatter;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsJVMKt;

public final class PluralMessageFormaterKt {
    public static final String icuMessageFormat(Resources resources, int i, int i2) {
        return PluralsMessageFormatter.format(resources, MapsKt__MapsJVMKt.mapOf(new Pair(SystemUIAnalytics.QPNE_KEY_COUNT, Integer.valueOf(i2))), i);
    }
}
