package com.samsung.android.nexus.particle.emitter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public enum ParticleConfigType {
    DISABLE_WHEN_DISAPPEARED(true),
    DISABLE_WHEN_OUTSIDE(true),
    AUTO_ROTATE_ALONG_MOVE_DIRECTION,
    APPLY_DRAW_MORPHING_BY_SPEED;

    final boolean defaultValue;
    int idx;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Holder {
        public static final int sCount;
        public static final ParticleConfigType[] sValuesCache;

        static {
            ParticleConfigType[] values = ParticleConfigType.values();
            sValuesCache = values;
            sCount = values.length;
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
