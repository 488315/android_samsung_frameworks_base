package com.airbnb.lottie;

import android.graphics.Rect;
import androidx.collection.LongSparseArray;
import androidx.collection.SparseArrayCompat;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.Marker;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.utils.Logger;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class LottieComposition {
    private Rect bounds;
    private SparseArrayCompat<FontCharacter> characters;
    private float endFrame;
    private Map<String, Font> fonts;
    private float frameRate;
    private boolean hasDashPattern;
    private Map<String, LottieImageAsset> images;
    private LongSparseArray<Layer> layerMap;
    private List<Layer> layers;
    private List<Marker> markers;
    private Map<String, List<Layer>> precomps;
    private float startFrame;
    private final PerformanceTracker performanceTracker = new PerformanceTracker();
    private final HashSet<String> warnings = new HashSet<>();
    private int maskAndMatteCount = 0;

    public final void addWarning(String str) {
        Logger.warning(str);
        this.warnings.add(str);
    }

    public final Rect getBounds() {
        return this.bounds;
    }

    public final SparseArrayCompat<FontCharacter> getCharacters() {
        return this.characters;
    }

    public final float getDuration() {
        return (long) (((this.endFrame - this.startFrame) / this.frameRate) * 1000.0f);
    }

    public final float getDurationFrames() {
        return this.endFrame - this.startFrame;
    }

    public final float getEndFrame() {
        return this.endFrame;
    }

    public final Map<String, Font> getFonts() {
        return this.fonts;
    }

    public final float getFrameRate() {
        return this.frameRate;
    }

    public final Map<String, LottieImageAsset> getImages() {
        return this.images;
    }

    public final List<Layer> getLayers() {
        return this.layers;
    }

    public final Marker getMarker(String str) {
        this.markers.size();
        for (int i = 0; i < this.markers.size(); i++) {
            Marker marker = this.markers.get(i);
            if (marker.matchesName(str)) {
                return marker;
            }
        }
        return null;
    }

    public final int getMaskAndMatteCount() {
        return this.maskAndMatteCount;
    }

    public final PerformanceTracker getPerformanceTracker() {
        return this.performanceTracker;
    }

    public final List<Layer> getPrecomps(String str) {
        return this.precomps.get(str);
    }

    public final float getStartFrame() {
        return this.startFrame;
    }

    public final boolean hasDashPattern() {
        return this.hasDashPattern;
    }

    public final void incrementMatteOrMaskCount(int i) {
        this.maskAndMatteCount += i;
    }

    public final void init(Rect rect, float f, float f2, float f3, List<Layer> list, LongSparseArray<Layer> longSparseArray, Map<String, List<Layer>> map, Map<String, LottieImageAsset> map2, SparseArrayCompat<FontCharacter> sparseArrayCompat, Map<String, Font> map3, List<Marker> list2) {
        this.bounds = rect;
        this.startFrame = f;
        this.endFrame = f2;
        this.frameRate = f3;
        this.layers = list;
        this.layerMap = longSparseArray;
        this.precomps = map;
        this.images = map2;
        this.characters = sparseArrayCompat;
        this.fonts = map3;
        this.markers = list2;
    }

    public final Layer layerModelForId(long j) {
        return this.layerMap.get(j);
    }

    public final void setHasDashPattern() {
        this.hasDashPattern = true;
    }

    public final void setPerformanceTrackingEnabled(boolean z) {
        this.performanceTracker.setEnabled(z);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("LottieComposition:\n");
        Iterator<Layer> it = this.layers.iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString("\t"));
        }
        return sb.toString();
    }
}
