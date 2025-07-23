package com.samsung.android.nexus.particle.emitter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public enum EmissionConfigType {
    APPLY_PARENT_ANGULAR_VELOCITY,
    APPLY_PARENT_POS_VECTOR,
    APPLY_PARENT_ROTATION_TO_SHAPE;

    final boolean defaultValue;
    int idx;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Holder {
        public static final int sCount;
        public static final EmissionConfigType[] sValuesCache;

        static {
            EmissionConfigType[] values = EmissionConfigType.values();
            sValuesCache = values;
            sCount = values.length;
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
