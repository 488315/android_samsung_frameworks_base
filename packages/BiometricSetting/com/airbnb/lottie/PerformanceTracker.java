package com.airbnb.lottie;

import androidx.collection.ArraySet;
import androidx.collection.IndexBasedArrayIterator;
import com.airbnb.lottie.utils.MeanCalculator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public final class PerformanceTracker {
    private boolean enabled = false;
    private final ArraySet frameListeners = new ArraySet();
    private final Map<String, MeanCalculator> layerRenderTimes = new HashMap();

    public interface FrameListener {
        void onFrameRendered();
    }

    public final void recordRenderTime(String str) {
        if (!this.enabled) {
            return;
        }
        HashMap hashMap = (HashMap) this.layerRenderTimes;
        MeanCalculator meanCalculator = (MeanCalculator) hashMap.get(str);
        if (meanCalculator == null) {
            meanCalculator = new MeanCalculator();
            hashMap.put(str, meanCalculator);
        }
        meanCalculator.add();
        if (!str.equals("__container")) {
            return;
        }
        Iterator it = this.frameListeners.iterator();
        while (true) {
            IndexBasedArrayIterator indexBasedArrayIterator = (IndexBasedArrayIterator) it;
            if (!indexBasedArrayIterator.hasNext()) {
                return;
            } else {
                ((FrameListener) indexBasedArrayIterator.next()).onFrameRendered();
            }
        }
    }

    final void setEnabled(boolean z) {
        this.enabled = z;
    }
}
