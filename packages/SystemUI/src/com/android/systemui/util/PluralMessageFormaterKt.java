package com.android.systemui.util;

import android.content.res.Resources;
import android.util.PluralsMessageFormatter;
import java.util.Collections;
import kotlin.Pair;

/* loaded from: classes3.dex */
public final class PluralMessageFormaterKt {
    public static final String icuMessageFormat(Resources resources, int i, int i2) {
        Pair pair = new Pair(SystemUIAnalytics.QPNE_KEY_COUNT, Integer.valueOf(i2));
        return PluralsMessageFormatter.format(resources, Collections.singletonMap(pair.getFirst(), pair.getSecond()), i);
    }
}
