package com.android.systemui.navigationbar.gestural;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class Step {
    public final float factor;
    public boolean hasCrossedUpperBoundAtLeastOnce;
    public final float lowerFactor;
    public final Object postThreshold;
    public final Object preThreshold;
    public Value previousValue;
    public final Value startValue;
    public final float threshold;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Value {
        public final boolean isNewState;
        public final Object value;

        public Value(Object obj, boolean z) {
            this.value = obj;
            this.isNewState = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Value)) {
                return false;
            }
            Value value = (Value) obj;
            return Intrinsics.areEqual(this.value, value.value) && this.isNewState == value.isNewState;
        }

        public final int hashCode() {
            Object obj = this.value;
            return Boolean.hashCode(this.isNewState) + ((obj == null ? 0 : obj.hashCode()) * 31);
        }

        public final String toString() {
            return "Value(value=" + this.value + ", isNewState=" + this.isNewState + ")";
        }
    }

    public Step(float f, float f2, Object obj, Object obj2) {
        this.threshold = f;
        this.factor = f2;
        this.postThreshold = obj;
        this.preThreshold = obj2;
        this.lowerFactor = 2 - f2;
        this.hasCrossedUpperBoundAtLeastOnce = false;
        Value value = new Value(obj2, false);
        this.startValue = value;
        this.previousValue = value;
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x004a, code lost:
    
        if (r6 == null) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.systemui.navigationbar.gestural.Step.Value get(float r6) {
        /*
            r5 = this;
            float r0 = r5.threshold
            float r1 = r5.factor
            float r1 = r1 * r0
            int r1 = (r6 > r1 ? 1 : (r6 == r1 ? 0 : -1))
            r2 = 0
            r3 = 1
            if (r1 <= 0) goto Ld
            r1 = r3
            goto Le
        Ld:
            r1 = r2
        Le:
            float r4 = r5.lowerFactor
            float r0 = r0 * r4
            int r6 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
            if (r6 <= 0) goto L17
            r6 = r3
            goto L18
        L17:
            r6 = r2
        L18:
            if (r1 == 0) goto L28
            boolean r0 = r5.hasCrossedUpperBoundAtLeastOnce
            if (r0 != 0) goto L28
            r5.hasCrossedUpperBoundAtLeastOnce = r3
            com.android.systemui.navigationbar.gestural.Step$Value r6 = new com.android.systemui.navigationbar.gestural.Step$Value
            java.lang.Object r0 = r5.postThreshold
            r6.<init>(r0, r3)
            goto L4d
        L28:
            r0 = 0
            if (r6 == 0) goto L3a
            com.android.systemui.navigationbar.gestural.Step$Value r6 = r5.previousValue
            if (r6 != 0) goto L30
            goto L31
        L30:
            r0 = r6
        L31:
            java.lang.Object r6 = r0.value
            com.android.systemui.navigationbar.gestural.Step$Value r0 = new com.android.systemui.navigationbar.gestural.Step$Value
            r0.<init>(r6, r2)
        L38:
            r6 = r0
            goto L4d
        L3a:
            boolean r6 = r5.hasCrossedUpperBoundAtLeastOnce
            if (r6 == 0) goto L48
            r5.hasCrossedUpperBoundAtLeastOnce = r2
            com.android.systemui.navigationbar.gestural.Step$Value r6 = new com.android.systemui.navigationbar.gestural.Step$Value
            java.lang.Object r0 = r5.preThreshold
            r6.<init>(r0, r3)
            goto L4d
        L48:
            com.android.systemui.navigationbar.gestural.Step$Value r6 = r5.startValue
            if (r6 != 0) goto L4d
            goto L38
        L4d:
            r5.previousValue = r6
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.navigationbar.gestural.Step.get(float):com.android.systemui.navigationbar.gestural.Step$Value");
    }

    public /* synthetic */ Step(float f, float f2, Object obj, Object obj2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, (i & 2) != 0 ? 1.1f : f2, obj, obj2);
    }
}
