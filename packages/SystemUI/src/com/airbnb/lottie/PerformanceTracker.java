package com.airbnb.lottie;

import androidx.collection.ArraySet;
import androidx.core.util.Pair;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class PerformanceTracker {
    public boolean enabled = false;
    public final ArraySet frameListeners = new ArraySet();
    public final Map layerRenderTimes = new HashMap();

    public PerformanceTracker() {
        new Comparator(this) { // from class: com.airbnb.lottie.PerformanceTracker.1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                float fFloatValue = ((Float) ((Pair) obj).second).floatValue();
                float fFloatValue2 = ((Float) ((Pair) obj2).second).floatValue();
                if (fFloatValue2 > fFloatValue) {
                    return 1;
                }
                return fFloatValue > fFloatValue2 ? -1 : 0;
            }
        };
    }
}
