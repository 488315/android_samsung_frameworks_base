package com.android.systemui.util;

import android.content.res.Resources;
import android.util.PluralsMessageFormatter;
import kotlin.Pair;
import kotlin.collections.MapsKt__MapsJVMKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class PluralMessageFormaterKt {
    public static final String icuMessageFormat(Resources resources, int i, int i2) {
        return PluralsMessageFormatter.format(resources, MapsKt__MapsJVMKt.mapOf(new Pair(SystemUIAnalytics.QPNE_KEY_COUNT, Integer.valueOf(i2))), i);
    }
}
