package com.samsung.android.nexus.particle.emitter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public enum ParticleConfigType {
    DISABLE_WHEN_DISAPPEARED(true),
    DISABLE_WHEN_OUTSIDE(true),
    AUTO_ROTATE_ALONG_MOVE_DIRECTION,
    APPLY_DRAW_MORPHING_BY_SPEED;

    final boolean defaultValue;
    int idx;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Holder {
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
