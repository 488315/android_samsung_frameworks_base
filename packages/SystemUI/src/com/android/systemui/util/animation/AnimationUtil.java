package com.android.systemui.util.animation;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class AnimationUtil {
    public static final int $stable = 0;
    public static final Companion Companion = new Companion(null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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

        private Companion() {
        }
    }
}
