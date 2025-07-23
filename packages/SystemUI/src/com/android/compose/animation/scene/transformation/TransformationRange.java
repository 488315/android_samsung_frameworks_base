package com.android.compose.animation.scene.transformation;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.Easing;
import androidx.compose.animation.core.EasingKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TransformationRange {
    public final Easing easing;
    public final float end;
    public final float start;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public TransformationRange(Float f, Float f2, Easing easing, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : f, (i & 2) != 0 ? null : f2, (i & 4) != 0 ? EasingKt.LinearEasing : easing);
    }

    public static boolean isSpecified(float f) {
        return !(f == Float.MIN_VALUE);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TransformationRange)) {
            return false;
        }
        TransformationRange transformationRange = (TransformationRange) obj;
        return Float.compare(this.start, transformationRange.start) == 0 && Float.compare(this.end, transformationRange.end) == 0 && Intrinsics.areEqual(this.easing, transformationRange.easing);
    }

    public final int hashCode() {
        return this.easing.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.end, Float.hashCode(this.start) * 31, 31);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: IfRegionVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r6v2 float, still in use, count: 2, list:
          (r6v2 float) from 0x0043: PHI (r6v5 float) = (r6v2 float), (r6v3 float), (r6v0 float), (r6v4 float), (r6v6 float) binds: [B:24:0x0040, B:22:0x0037, B:18:0x002b, B:14:0x0039, B:10:0x0042] A[DONT_GENERATE, DONT_INLINE]
          (r6v2 float) from 0x003e: CMP_G (r6v2 float), (0.0f float) A[WRAPPED] (LINE:63)
        	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
        	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
        	at jadx.core.dex.visitors.regions.TernaryMod.makeTernaryInsn(TernaryMod.java:125)
        	at jadx.core.dex.visitors.regions.TernaryMod.processRegion(TernaryMod.java:62)
        	at jadx.core.dex.visitors.regions.TernaryMod.enterRegion(TernaryMod.java:45)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:67)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at java.base/java.util.Collections$UnmodifiableCollection.forEach(Collections.java:1117)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.lambda$traverseInternal$0(DepthRegionTraversal.java:68)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1604)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseInternal(DepthRegionTraversal.java:68)
        	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverse(DepthRegionTraversal.java:19)
        	at jadx.core.dex.visitors.regions.TernaryMod.process(TernaryMod.java:35)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.process(IfRegionVisitor.java:34)
        	at jadx.core.dex.visitors.regions.IfRegionVisitor.visit(IfRegionVisitor.java:30)
        */
    public final float progress(float r6) {
        /*
            r5 = this;
            float r0 = r5.start
            boolean r1 = isSpecified(r0)
            r2 = 0
            r3 = 1065353216(0x3f800000, float:1.0)
            float r4 = r5.end
            if (r1 == 0) goto L21
            boolean r1 = isSpecified(r4)
            if (r1 == 0) goto L21
            float r6 = r6 - r0
            float r4 = r4 - r0
            float r6 = r6 / r4
            int r0 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r0 >= 0) goto L1b
            goto L1c
        L1b:
            r2 = r6
        L1c:
            int r6 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1))
            if (r6 <= 0) goto L42
            goto L39
        L21:
            boolean r1 = isSpecified(r0)
            if (r1 != 0) goto L2e
            boolean r1 = isSpecified(r4)
            if (r1 != 0) goto L2e
            goto L43
        L2e:
            boolean r1 = isSpecified(r4)
            if (r1 == 0) goto L3b
            float r6 = r6 / r4
            int r0 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r0 <= 0) goto L43
        L39:
            r6 = r3
            goto L43
        L3b:
            float r6 = r6 - r0
            float r3 = r3 - r0
            float r6 = r6 / r3
            int r0 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r0 >= 0) goto L43
        L42:
            r6 = r2
        L43:
            androidx.compose.animation.core.Easing r5 = r5.easing
            float r5 = r5.transform(r6)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.compose.animation.scene.transformation.TransformationRange.progress(float):float");
    }

    public final String toString() {
        return "TransformationRange(start=" + this.start + ", end=" + this.end + ", easing=" + this.easing + ")";
    }

    public TransformationRange(float f, float f2, Easing easing) {
        this.start = f;
        this.end = f2;
        this.easing = easing;
        if (isSpecified(f) && (0.0f > f || f > 1.0f)) {
            throw new IllegalArgumentException("Failed requirement.");
        }
        if (isSpecified(f2) && (0.0f > f2 || f2 > 1.0f)) {
            throw new IllegalArgumentException("Failed requirement.");
        }
        if (isSpecified(f) && isSpecified(f2) && f > f2) {
            throw new IllegalArgumentException("Failed requirement.");
        }
    }

    public TransformationRange(Float f, Float f2, Easing easing) {
        this(f != null ? f.floatValue() : Float.MIN_VALUE, f2 != null ? f2.floatValue() : Float.MIN_VALUE, easing);
    }
}
