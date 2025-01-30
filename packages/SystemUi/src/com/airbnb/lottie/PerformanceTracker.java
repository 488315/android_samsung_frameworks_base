package com.airbnb.lottie;

import androidx.collection.ArraySet;
import androidx.core.util.Pair;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PerformanceTracker {
    public boolean enabled = false;
    public final ArraySet frameListeners = new ArraySet();
    public final Map layerRenderTimes = new HashMap();

    public PerformanceTracker() {
        new Comparator(this) { // from class: com.airbnb.lottie.PerformanceTracker.1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                float floatValue = ((Float) ((Pair) obj).second).floatValue();
                float floatValue2 = ((Float) ((Pair) obj2).second).floatValue();
                if (floatValue2 > floatValue) {
                    return 1;
                }
                return floatValue > floatValue2 ? -1 : 0;
            }
        };
    }
}
