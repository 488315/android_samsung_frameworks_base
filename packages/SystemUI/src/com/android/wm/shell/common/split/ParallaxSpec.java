package com.android.wm.shell.common.split;

import com.android.wm.shell.shared.animation.Interpolators;

/* loaded from: classes3.dex */
public interface ParallaxSpec {
    default float getDimValue(int i, DividerSnapAlgorithm dividerSnapAlgorithm) {
        float startInset;
        if (i < dividerSnapAlgorithm.mFirstSplitTarget.position) {
            startInset = 1.0f - ((i - dividerSnapAlgorithm.getStartInset()) / (r3.position - dividerSnapAlgorithm.getStartInset()));
        } else {
            startInset = i > dividerSnapAlgorithm.mLastSplitTarget.position ? (i - r3) / ((dividerSnapAlgorithm.mDismissEndTarget.position - r3) - dividerSnapAlgorithm.mDividerSize) : 0.0f;
        }
        return Interpolators.DIM_INTERPOLATOR.getInterpolation(Math.max(0.0f, Math.min(startInset, 1.0f)));
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
