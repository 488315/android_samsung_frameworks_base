package com.samsung.sesl.compose.foundation;

import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import androidx.compose.foundation.shape.DpCornerSize$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslFeedbackAlpha {
    public static final Companion Companion = new Companion(null);
    public static final SeslFeedbackAlpha Unspecified = new SeslFeedbackAlpha(0.0f, 0.0f, 0.0f, 0.0f);
    public final float draggedAlpha;
    public final float focusedAlpha;
    public final float hoveredAlpha;
    public final float pressedAlpha;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Impl28 {
            public static final Impl28 INSTANCE = new Impl28();

            private Impl28() {
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static SeslFeedbackAlpha takeOrDefault(SeslFeedbackAlpha seslFeedbackAlpha) {
            boolean z;
            if (seslFeedbackAlpha != null) {
                SeslFeedbackAlpha.Companion.getClass();
                z = !seslFeedbackAlpha.equals(SeslFeedbackAlpha.Unspecified);
            } else {
                z = false;
            }
            if (!z) {
                seslFeedbackAlpha = null;
            }
            if (seslFeedbackAlpha != null) {
                return seslFeedbackAlpha;
            }
            SeslFeedbackDefaults.INSTANCE.getClass();
            return SeslFeedbackDefaults.feedbackAlpha;
        }

        private Companion() {
        }
    }

    public SeslFeedbackAlpha(float f, float f2, float f3, float f4) {
        this.draggedAlpha = f;
        this.focusedAlpha = f2;
        this.hoveredAlpha = f3;
        this.pressedAlpha = f4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeslFeedbackAlpha)) {
            return false;
        }
        SeslFeedbackAlpha seslFeedbackAlpha = (SeslFeedbackAlpha) obj;
        return Float.compare(this.draggedAlpha, seslFeedbackAlpha.draggedAlpha) == 0 && Float.compare(this.focusedAlpha, seslFeedbackAlpha.focusedAlpha) == 0 && Float.compare(this.hoveredAlpha, seslFeedbackAlpha.hoveredAlpha) == 0 && Float.compare(this.pressedAlpha, seslFeedbackAlpha.pressedAlpha) == 0;
    }

    public final int hashCode() {
        return Float.hashCode(this.pressedAlpha) + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.hoveredAlpha, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.focusedAlpha, Float.hashCode(this.draggedAlpha) * 31, 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SeslFeedbackAlpha(draggedAlpha=");
        sb.append(this.draggedAlpha);
        sb.append(", focusedAlpha=");
        sb.append(this.focusedAlpha);
        sb.append(", hoveredAlpha=");
        sb.append(this.hoveredAlpha);
        sb.append(", pressedAlpha=");
        return DpCornerSize$$ExternalSyntheticOutline0.m(this.pressedAlpha, ")", sb);
    }
}
