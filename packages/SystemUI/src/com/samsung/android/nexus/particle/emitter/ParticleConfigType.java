package com.samsung.android.nexus.particle.emitter;

/* loaded from: classes4.dex */
public enum ParticleConfigType {
    DISABLE_WHEN_DISAPPEARED(true),
    DISABLE_WHEN_OUTSIDE(true),
    AUTO_ROTATE_ALONG_MOVE_DIRECTION,
    APPLY_DRAW_MORPHING_BY_SPEED;

    final boolean defaultValue;
    int idx;

    public class Holder {
        public static final int sCount;
        public static final ParticleConfigType[] sValuesCache;

        static {
            ParticleConfigType[] particleConfigTypeArrValues = ParticleConfigType.values();
            sValuesCache = particleConfigTypeArrValues;
            sCount = particleConfigTypeArrValues.length;
        }

        private Holder() {
        }
    }

    ParticleConfigType() {
        this.idx = ordinal();
        this.defaultValue = false;
    }

    ParticleConfigType(boolean z) {
        this.idx = ordinal();
        this.defaultValue = z;
    }
}
