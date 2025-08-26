package androidx.compose.foundation.gestures;

import androidx.compose.foundation.internal.InlineClassHelperKt;
import java.util.Arrays;
import java.util.List;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes.dex */
public final class DefaultDraggableAnchors<T> implements DraggableAnchors<T> {
    public final float[] anchors;
    public final List keys;
    public final int size;

    public DefaultDraggableAnchors(List<? extends T> list, float[] fArr) {
        this.keys = list;
        this.anchors = fArr;
        if (!(list.size() == fArr.length)) {
            InlineClassHelperKt.throwIllegalArgumentException("DraggableAnchors were constructed with inconsistent key-value sizes. Keys: " + list + " | Anchors: " + ArraysKt___ArraysKt.toList(fArr));
        }
        this.size = fArr.length;
    }

    @Override // androidx.compose.foundation.gestures.DraggableAnchors
    public final Object anchorAt(int i) {
        return CollectionsKt___CollectionsKt.getOrNull(i, this.keys);
    }

    public final Object closestAnchor(float f) {
        float[] fArr = this.anchors;
        int length = fArr.length;
        int i = -1;
        float f2 = Float.POSITIVE_INFINITY;
        int i2 = 0;
        int i3 = 0;
        while (i2 < length) {
            int i4 = i3 + 1;
            float fAbs = Math.abs(f - fArr[i2]);
            if (fAbs <= f2) {
                i = i3;
                f2 = fAbs;
            }
            i2++;
            i3 = i4;
        }
        return this.keys.get(i);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DefaultDraggableAnchors)) {
            return false;
        }
        DefaultDraggableAnchors defaultDraggableAnchors = (DefaultDraggableAnchors) obj;
        if (Intrinsics.areEqual(this.keys, defaultDraggableAnchors.keys) && Arrays.equals(this.anchors, defaultDraggableAnchors.anchors)) {
            return this.size == defaultDraggableAnchors.size;
        }
        return false;
    }

    @Override // androidx.compose.foundation.gestures.DraggableAnchors
    public final int getSize() {
        return this.size;
    }

    public final int hashCode() {
        return ((Arrays.hashCode(this.anchors) + (this.keys.hashCode() * 31)) * 31) + this.size;
    }

    @Override // androidx.compose.foundation.gestures.DraggableAnchors
    public final float maxPosition() {
        Float fValueOf;
        float[] fArr = this.anchors;
        if (fArr.length == 0) {
            fValueOf = null;
        } else {
            float fMax = fArr[0];
            int i = 1;
            int length = fArr.length - 1;
            if (1 <= length) {
                while (true) {
                    fMax = Math.max(fMax, fArr[i]);
                    if (i == length) {
                        break;
                    }
                    i++;
                }
            }
            fValueOf = Float.valueOf(fMax);
        }
        if (fValueOf != null) {
            return fValueOf.floatValue();
        }
        return Float.NaN;
    }

    @Override // androidx.compose.foundation.gestures.DraggableAnchors
    public final float minPosition() {
        Float fValueOf;
        float[] fArr = this.anchors;
        if (fArr.length == 0) {
            fValueOf = null;
        } else {
            float fMin = fArr[0];
            int i = 1;
            int length = fArr.length - 1;
            if (1 <= length) {
                while (true) {
                    fMin = Math.min(fMin, fArr[i]);
                    if (i == length) {
                        break;
                    }
                    i++;
                }
            }
            fValueOf = Float.valueOf(fMin);
        }
        if (fValueOf != null) {
            return fValueOf.floatValue();
        }
        return Float.NaN;
    }

    public final float positionOf(Object obj) {
        int iIndexOf = this.keys.indexOf(obj);
        Function1 function1 = AnchoredDraggableKt.GetOrNan;
        if (iIndexOf >= 0) {
            float[] fArr = this.anchors;
            if (iIndexOf <= fArr.length - 1) {
                return fArr[iIndexOf];
            }
        }
        return ((Number) ((AnchoredDraggableKt$GetOrNan$1) function1).mo781invoke(Integer.valueOf(iIndexOf))).floatValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String toString() {
        float fFloatValue;
        StringBuilder sb = new StringBuilder("DraggableAnchors(anchors={");
        int i = 0;
        while (true) {
            int i2 = this.size;
            if (i >= i2) {
                sb.append("})");
                return sb.toString();
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append(CollectionsKt___CollectionsKt.getOrNull(i, this.keys));
            sb2.append('=');
            Function1 function1 = AnchoredDraggableKt.GetOrNan;
            if (i >= 0) {
                float[] fArr = this.anchors;
                if (i <= fArr.length - 1) {
                    fFloatValue = fArr[i];
                } else {
                    ((AnchoredDraggableKt$GetOrNan$1) function1).mo781invoke(Integer.valueOf(i));
                    fFloatValue = Float.valueOf(Float.NaN).floatValue();
                }
            }
            sb2.append(fFloatValue);
            sb.append(sb2.toString());
            if (i < i2 - 1) {
                sb.append(", ");
            }
            i++;
        }
    }

    public final Object closestAnchor(float f, boolean z) {
        float[] fArr = this.anchors;
        int length = fArr.length;
        int i = -1;
        int i2 = 0;
        float f2 = Float.POSITIVE_INFINITY;
        int i3 = 0;
        while (i2 < length) {
            float f3 = fArr[i2];
            int i4 = i3 + 1;
            float f4 = z ? f3 - f : f - f3;
            if (f4 < 0.0f) {
                f4 = Float.POSITIVE_INFINITY;
            }
            if (f4 <= f2) {
                i = i3;
                f2 = f4;
            }
            i2++;
            i3 = i4;
        }
        return this.keys.get(i);
    }
}
