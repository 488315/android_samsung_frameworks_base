package androidx.compose.foundation.lazy.layout;

import androidx.collection.MutableScatterMap;
import androidx.collection.ScatterMapKt;

/* loaded from: classes.dex */
public final class PrefetchMetrics {
    public Averages lastUsedAverage;
    public Object lastUsedContentType;
    public final Averages overallAverage = new Averages();
    public final MutableScatterMap averagesByContentType = ScatterMapKt.mutableScatterMapOf();

    public final Averages getAverage(Object obj) {
        Averages averages = this.lastUsedAverage;
        if (this.lastUsedContentType == obj && averages != null) {
            return averages;
        }
        MutableScatterMap mutableScatterMap = this.averagesByContentType;
        Object obj2 = mutableScatterMap.get(obj);
        if (obj2 == null) {
            Averages averages2 = this.overallAverage;
            averages2.getClass();
            Averages averages3 = new Averages();
            averages3.compositionTimeNanos = averages2.compositionTimeNanos;
            averages3.measureTimeNanos = averages2.measureTimeNanos;
            mutableScatterMap.set(obj, averages3);
            obj2 = averages3;
        }
        Averages averages4 = (Averages) obj2;
        this.lastUsedContentType = obj;
        this.lastUsedAverage = averages4;
        return averages4;
    }
}
