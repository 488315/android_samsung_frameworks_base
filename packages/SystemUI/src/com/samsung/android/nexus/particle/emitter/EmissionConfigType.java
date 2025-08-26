package com.samsung.android.nexus.particle.emitter;

/* loaded from: classes4.dex */
public enum EmissionConfigType {
    APPLY_PARENT_ANGULAR_VELOCITY,
    APPLY_PARENT_POS_VECTOR,
    APPLY_PARENT_ROTATION_TO_SHAPE;

    final boolean defaultValue;
    int idx;

    public class Holder {
        public static final int sCount;
        public static final EmissionConfigType[] sValuesCache;

        static {
            EmissionConfigType[] emissionConfigTypeArrValues = EmissionConfigType.values();
            sValuesCache = emissionConfigTypeArrValues;
            sCount = emissionConfigTypeArrValues.length;
        }

        private Holder() {
        }
    }

    EmissionConfigType() {
        this.idx = ordinal();
        this.defaultValue = false;
    }

    EmissionConfigType(boolean z) {
        this.idx = ordinal();
        this.defaultValue = z;
    }
}
