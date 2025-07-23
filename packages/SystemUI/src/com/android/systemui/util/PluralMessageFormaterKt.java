package com.android.systemui.util;

import android.content.res.Resources;
import android.util.PluralsMessageFormatter;
import java.util.Collections;
import kotlin.Pair;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PluralMessageFormaterKt {
    public static final String icuMessageFormat(Resources resources, int i, int i2) {
        Pair pair = new Pair(SystemUIAnalytics.QPNE_KEY_COUNT, Integer.valueOf(i2));
        return PluralsMessageFormatter.format(resources, Collections.singletonMap(pair.getFirst(), pair.getSecond()), i);
    }
}
