package com.android.server.input;

import android.animation.ValueAnimator;

public final /* synthetic */ class KeyboardBacklightController$$ExternalSyntheticLambda0
        implements KeyboardBacklightController.AnimatorFactory {
    @Override // com.android.server.input.KeyboardBacklightController.AnimatorFactory
    public final ValueAnimator makeIntAnimator(int i, int i2) {
        return ValueAnimator.ofInt(i, i2);
    }
}
