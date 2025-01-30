package com.android.systemui.navigationbar.gestural;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Step {
    public final float factor;
    public boolean hasCrossedUpperBoundAtLeastOnce;
    public final float lowerFactor;
    public final Object postThreshold;
    public final Object preThreshold;
    public Value previousValue;
    public Value startValue;
    public final float threshold;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

        /* JADX WARN: Multi-variable type inference failed */
        public final int hashCode() {
            Object obj = this.value;
            int hashCode = (obj == null ? 0 : obj.hashCode()) * 31;
            boolean z = this.isNewState;
            int i = z;
            if (z != 0) {
                i = 1;
            }
            return hashCode + i;
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

    /* JADX WARN: Code restructure failed: missing block: B:25:0x004d, code lost:
    
        if (r6 == null) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Value get(float f) {
        Value value;
        float f2 = this.factor;
        float f3 = this.threshold;
        boolean z = f > f2 * f3;
        boolean z2 = f > f3 * this.lowerFactor;
        if (!z || this.hasCrossedUpperBoundAtLeastOnce) {
            if (z2) {
                Value value2 = this.previousValue;
                r0 = value2 != null ? value2 : null;
                Object obj = r0.value;
                r0.getClass();
                r0 = new Value(obj, false);
            } else if (this.hasCrossedUpperBoundAtLeastOnce) {
                this.hasCrossedUpperBoundAtLeastOnce = false;
                value = new Value(this.preThreshold, true);
            } else {
                value = this.startValue;
            }
            value = r0;
        } else {
            this.hasCrossedUpperBoundAtLeastOnce = true;
            value = new Value(this.postThreshold, true);
        }
        this.previousValue = value;
        return value;
    }

    public /* synthetic */ Step(float f, float f2, Object obj, Object obj2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(f, (i & 2) != 0 ? 1.1f : f2, obj, obj2);
    }
}
