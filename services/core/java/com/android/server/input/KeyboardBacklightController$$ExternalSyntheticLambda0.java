package com.android.server.input;

import android.animation.ValueAnimator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class KeyboardBacklightController$$ExternalSyntheticLambda0
        implements KeyboardBacklightController.AnimatorFactory {
    @Override // com.android.server.input.KeyboardBacklightController.AnimatorFactory
    public final ValueAnimator makeIntAnimator(int i, int i2) {
        return ValueAnimator.ofInt(i, i2);
    }
}
