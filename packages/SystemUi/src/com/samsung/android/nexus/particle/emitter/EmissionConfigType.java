package com.samsung.android.nexus.particle.emitter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public enum EmissionConfigType {
    APPLY_PARENT_ANGULAR_VELOCITY,
    APPLY_PARENT_POS_VECTOR,
    APPLY_PARENT_ROTATION_TO_SHAPE;

    final boolean defaultValue;
    int idx;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Holder {
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
