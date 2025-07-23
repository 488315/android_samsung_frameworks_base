package androidx.compose.material3.internal;

import androidx.compose.material3.SheetValue;
import java.util.Iterator;
import java.util.Map;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class MapDraggableAnchors<T> implements DraggableAnchors<T> {
    public final Map anchors;

    public MapDraggableAnchors(Map<T, Float> map) {
        this.anchors = map;
    }

    public final Object closestAnchor(float f) {
        T next;
        Iterator<T> it = this.anchors.entrySet().iterator();
        if (it.hasNext()) {
            next = it.next();
            if (it.hasNext()) {
                float abs = Math.abs(f - ((Number) ((Map.Entry) next).getValue()).floatValue());
                do {
                    T next2 = it.next();
                    float abs2 = Math.abs(f - ((Number) ((Map.Entry) next2).getValue()).floatValue());
                    if (Float.compare(abs, abs2) > 0) {
                        next = next2;
                        abs = abs2;
                    }
                } while (it.hasNext());
            }
        } else {
            next = null;
        }
        Map.Entry entry = (Map.Entry) next;
        if (entry != null) {
            return entry.getKey();
        }
        return null;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MapDraggableAnchors) {
            return Intrinsics.areEqual(this.anchors, ((MapDraggableAnchors) obj).anchors);
        }
        return false;
    }

    @Override // androidx.compose.material3.internal.DraggableAnchors
    public final boolean hasAnchorFor(SheetValue sheetValue) {
        return this.anchors.containsKey(sheetValue);
    }

    public final int hashCode() {
        return this.anchors.hashCode() * 31;
    }

    @Override // androidx.compose.material3.internal.DraggableAnchors
    public final float minAnchor() {
        Float valueOf;
        Iterator<T> it = this.anchors.values().iterator();
        if (it.hasNext()) {
            float floatValue = ((Number) it.next()).floatValue();
            while (it.hasNext()) {
                floatValue = Math.min(floatValue, ((Number) it.next()).floatValue());
            }
            valueOf = Float.valueOf(floatValue);
        } else {
            valueOf = null;
        }
        if (valueOf != null) {
            return valueOf.floatValue();
        }
        return Float.NaN;
    }

    @Override // androidx.compose.material3.internal.DraggableAnchors
    public final float positionOf(Object obj) {
        Float f = (Float) this.anchors.get(obj);
        if (f != null) {
            return f.floatValue();
        }
        return Float.NaN;
    }

    public final String toString() {
        return "MapDraggableAnchors(" + this.anchors + ')';
    }

    public final Object closestAnchor(float f, boolean z) {
        T next;
        Iterator<T> it = this.anchors.entrySet().iterator();
        if (it.hasNext()) {
            next = it.next();
            if (it.hasNext()) {
                float floatValue = ((Number) ((Map.Entry) next).getValue()).floatValue();
                float f2 = z ? floatValue - f : f - floatValue;
                if (f2 < 0.0f) {
                    f2 = Float.POSITIVE_INFINITY;
                }
                do {
                    T next2 = it.next();
                    float floatValue2 = ((Number) ((Map.Entry) next2).getValue()).floatValue();
                    float f3 = z ? floatValue2 - f : f - floatValue2;
                    if (f3 < 0.0f) {
                        f3 = Float.POSITIVE_INFINITY;
                    }
                    if (Float.compare(f2, f3) > 0) {
                        next = next2;
                        f2 = f3;
                    }
                } while (it.hasNext());
            }
        } else {
            next = null;
        }
        Map.Entry entry = (Map.Entry) next;
        if (entry != null) {
            return entry.getKey();
        }
        return null;
    }
}
