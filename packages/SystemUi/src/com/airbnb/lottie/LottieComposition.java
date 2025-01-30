package com.airbnb.lottie;

import android.graphics.Rect;
import androidx.collection.LongSparseArray;
import androidx.collection.SparseArrayCompat;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.utils.Logger;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class LottieComposition {
    public Rect bounds;
    public SparseArrayCompat characters;
    public float endFrame;
    public Map fonts;
    public float frameRate;
    public boolean hasDashPattern;
    public Map images;
    public LongSparseArray layerMap;
    public List layers;
    public Map precomps;
    public float startFrame;
    public final PerformanceTracker performanceTracker = new PerformanceTracker();
    public final HashSet warnings = new HashSet();
    public int maskAndMatteCount = 0;

    public final void addWarning(String str) {
        Logger.warning(str);
        this.warnings.add(str);
    }

    public final float getDuration() {
        return (long) (((this.endFrame - this.startFrame) / this.frameRate) * 1000.0f);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("LottieComposition:\n");
        Iterator it = this.layers.iterator();
        while (it.hasNext()) {
            sb.append(((Layer) it.next()).toString("\t"));
        }
        return sb.toString();
    }
}
