package com.android.systemui.util.animation;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class AnimationUtil {
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static long getFrames(int i) {
            if (i < 0) {
                throw new IllegalArgumentException("numFrames must be >= 0");
            }
            double d = (i * 1000.0f) / 60.0f;
            if (Double.isNaN(d)) {
                throw new IllegalArgumentException("Cannot round NaN value.");
            }
            return Math.round(d);
        }
    }
}
