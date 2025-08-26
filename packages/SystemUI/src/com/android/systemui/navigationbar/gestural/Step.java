package com.android.systemui.navigationbar.gestural;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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

    public final Value get(float f) {
        Value value;
        float f2 = this.threshold;
        boolean z = f > this.factor * f2;
        boolean z2 = f > f2 * this.lowerFactor;
        if (!z || this.hasCrossedUpperBoundAtLeastOnce) {
            if (z2) {
                Value value2 = this.previousValue;
                value = new Value((value2 != null ? value2 : null).value, false);
            } else if (this.hasCrossedUpperBoundAtLeastOnce) {
                this.hasCrossedUpperBoundAtLeastOnce = false;
                value = new Value(this.preThreshold, true);
            } else {
                value = this.startValue;
                if (value == null) {
                }
            }
            value = value;
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
