package com.android.systemui.util.animation;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt__MathJVMKt;

public final class AnimationUtil {
    public static final int $stable = 0;
    public static final Companion Companion = new Companion(null);

    public final class Companion {
        private Companion() {
        }

        public final long getFrames(int i) {
            return getMsForFrames(i);
        }

        public final long getMsForFrames(int i) {
            if (i >= 0) {
                return MathKt__MathJVMKt.roundToLong((i * 1000.0f) / 60.0f);
            }
            throw new IllegalArgumentException("numFrames must be >= 0");
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
