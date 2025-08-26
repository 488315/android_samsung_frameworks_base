package com.android.wm.shell.shared.animation;

import android.graphics.PointF;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.animation.Interpolator;
import androidx.compose.animation.FlingCalculator$FlingInfo$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class WindowAnimator {
    public static final WindowAnimator INSTANCE = new WindowAnimator();

    private WindowAnimator() {
    }

    public static PointF getPosition(DisplayMetrics displayMetrics, Rect rect, float f, float f2) {
        PointF pointF = new PointF(rect.left, rect.top);
        if (0.0f > f || f > 1.0f) {
            throw new IllegalStateException("Check failed.");
        }
        float f3 = 1 - f;
        float f4 = 2;
        pointF.offset((rect.width() * f3) / f4, (rect.height() * f3) / f4);
        pointF.offset(0.0f, (int) TypedValue.applyDimension(1, f2, displayMetrics));
        return pointF;
    }

    public final class BoundsAnimationParams {
        public final long durationMs;
        public final float endOffsetYDp;
        public final float endScale;
        public final Interpolator interpolator;
        public final float startOffsetYDp;
        public final float startScale;

        public BoundsAnimationParams(long j, float f, float f2, float f3, float f4, Interpolator interpolator) {
            this.durationMs = j;
            this.startOffsetYDp = f;
            this.endOffsetYDp = f2;
            this.startScale = f3;
            this.endScale = f4;
            this.interpolator = interpolator;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof BoundsAnimationParams)) {
                return false;
            }
            BoundsAnimationParams boundsAnimationParams = (BoundsAnimationParams) obj;
            return this.durationMs == boundsAnimationParams.durationMs && Float.compare(this.startOffsetYDp, boundsAnimationParams.startOffsetYDp) == 0 && Float.compare(this.endOffsetYDp, boundsAnimationParams.endOffsetYDp) == 0 && Float.compare(this.startScale, boundsAnimationParams.startScale) == 0 && Float.compare(this.endScale, boundsAnimationParams.endScale) == 0 && Intrinsics.areEqual(this.interpolator, boundsAnimationParams.interpolator);
        }

        public final int hashCode() {
            return this.interpolator.hashCode() + FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.endScale, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.startScale, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.endOffsetYDp, FlingCalculator$FlingInfo$$ExternalSyntheticOutline0.m(this.startOffsetYDp, Long.hashCode(this.durationMs) * 31, 31), 31), 31), 31);
        }

        public final String toString() {
            return "BoundsAnimationParams(durationMs=" + this.durationMs + ", startOffsetYDp=" + this.startOffsetYDp + ", endOffsetYDp=" + this.endOffsetYDp + ", startScale=" + this.startScale + ", endScale=" + this.endScale + ", interpolator=" + this.interpolator + ")";
        }

        public /* synthetic */ BoundsAnimationParams(long j, float f, float f2, float f3, float f4, Interpolator interpolator, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(j, (i & 2) != 0 ? 0.0f : f, (i & 4) != 0 ? 0.0f : f2, (i & 8) != 0 ? 1.0f : f3, (i & 16) != 0 ? 1.0f : f4, interpolator);
        }
    }
}
