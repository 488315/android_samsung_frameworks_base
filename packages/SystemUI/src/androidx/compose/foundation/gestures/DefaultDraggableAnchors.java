package androidx.compose.foundation.gestures;

import androidx.compose.foundation.internal.InlineClassHelperKt;
import java.util.Arrays;
import java.util.List;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            float abs = Math.abs(f - fArr[i2]);
            if (abs <= f2) {
                i = i3;
                f2 = abs;
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
        Float valueOf;
        float[] fArr = this.anchors;
        if (fArr.length == 0) {
            valueOf = null;
        } else {
            float f = fArr[0];
            int i = 1;
            int length = fArr.length - 1;
            if (1 <= length) {
                while (true) {
                    f = Math.max(f, fArr[i]);
                    if (i == length) {
                        break;
                    }
                    i++;
                }
            }
            valueOf = Float.valueOf(f);
        }
        if (valueOf != null) {
            return valueOf.floatValue();
        }
        return Float.NaN;
    }

    @Override // androidx.compose.foundation.gestures.DraggableAnchors
    public final float minPosition() {
        Float valueOf;
        float[] fArr = this.anchors;
        if (fArr.length == 0) {
            valueOf = null;
        } else {
            float f = fArr[0];
            int i = 1;
            int length = fArr.length - 1;
            if (1 <= length) {
                while (true) {
                    f = Math.min(f, fArr[i]);
                    if (i == length) {
                        break;
                    }
                    i++;
                }
            }
            valueOf = Float.valueOf(f);
        }
        if (valueOf != null) {
            return valueOf.floatValue();
        }
        return Float.NaN;
    }

    public final float positionOf(Object obj) {
        int indexOf = this.keys.indexOf(obj);
        Function1 function1 = AnchoredDraggableKt.GetOrNan;
        if (indexOf >= 0) {
            float[] fArr = this.anchors;
            if (indexOf <= fArr.length - 1) {
                return fArr[indexOf];
            }
        }
        return ((Number) ((AnchoredDraggableKt$GetOrNan$1) function1).mo779invoke(Integer.valueOf(indexOf))).floatValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0053 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String toString() {
        /*
            r7 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "DraggableAnchors(anchors={"
            r0.<init>(r1)
            r1 = 0
        L8:
            int r2 = r7.size
            if (r1 >= r2) goto L56
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.util.List r4 = r7.keys
            java.lang.Object r4 = kotlin.collections.CollectionsKt___CollectionsKt.getOrNull(r1, r4)
            r3.append(r4)
            r4 = 61
            r3.append(r4)
            kotlin.jvm.functions.Function1 r4 = androidx.compose.foundation.gestures.AnchoredDraggableKt.GetOrNan
            if (r1 < 0) goto L2d
            float[] r5 = r7.anchors
            int r6 = r5.length
            int r6 = r6 + (-1)
            if (r1 > r6) goto L2d
            r4 = r5[r1]
            goto L40
        L2d:
            java.lang.Integer r5 = java.lang.Integer.valueOf(r1)
            androidx.compose.foundation.gestures.AnchoredDraggableKt$GetOrNan$1 r4 = (androidx.compose.foundation.gestures.AnchoredDraggableKt$GetOrNan$1) r4
            r4.mo779invoke(r5)
            r4 = 2143289344(0x7fc00000, float:NaN)
            java.lang.Float r4 = java.lang.Float.valueOf(r4)
            float r4 = r4.floatValue()
        L40:
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r0.append(r3)
            int r2 = r2 + (-1)
            if (r1 >= r2) goto L53
            java.lang.String r2 = ", "
            r0.append(r2)
        L53:
            int r1 = r1 + 1
            goto L8
        L56:
            java.lang.String r7 = "})"
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.gestures.DefaultDraggableAnchors.toString():java.lang.String");
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
