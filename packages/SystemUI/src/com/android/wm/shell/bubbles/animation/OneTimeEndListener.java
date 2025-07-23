package com.android.wm.shell.bubbles.animation;

import androidx.dynamicanimation.animation.DynamicAnimation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class OneTimeEndListener implements DynamicAnimation.OnAnimationEndListener {
    @Override // androidx.dynamicanimation.animation.DynamicAnimation.OnAnimationEndListener
    public void onAnimationEnd(DynamicAnimation dynamicAnimation, boolean z, float f, float f2) {
        dynamicAnimation.removeEndListener(this);
    }
}
