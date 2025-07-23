package com.android.wm.shell.common.split;

import com.android.wm.shell.shared.animation.Interpolators;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface ParallaxSpec {
    default float getDimValue(int i, DividerSnapAlgorithm dividerSnapAlgorithm) {
        float f;
        if (i < dividerSnapAlgorithm.mFirstSplitTarget.position) {
            f = 1.0f - ((i - dividerSnapAlgorithm.getStartInset()) / (r3.position - dividerSnapAlgorithm.getStartInset()));
        } else {
            f = i > dividerSnapAlgorithm.mLastSplitTarget.position ? (i - r3) / ((dividerSnapAlgorithm.mDismissEndTarget.position - r3) - dividerSnapAlgorithm.mDividerSize) : 0.0f;
        }
        return Interpolators.DIM_INTERPOLATOR.getInterpolation(Math.max(0.0f, Math.min(f, 1.0f)));
    }

    default int getDimmingSide(int i, DividerSnapAlgorithm dividerSnapAlgorithm, boolean z) {
        if (i < dividerSnapAlgorithm.mFirstSplitTarget.position) {
            return z ? 1 : 2;
        }
        if (i > dividerSnapAlgorithm.mLastSplitTarget.position) {
            return z ? 3 : 4;
        }
        return -1;
    }
}
