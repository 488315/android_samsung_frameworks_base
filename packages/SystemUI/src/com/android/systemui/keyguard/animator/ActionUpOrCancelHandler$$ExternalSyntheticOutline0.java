package com.android.systemui.keyguard.animator;

import androidx.dynamicanimation.animation.SpringForce;

public abstract /* synthetic */ class ActionUpOrCancelHandler$$ExternalSyntheticOutline0 {
    public static SpringForce m(float f, float f2) {
        SpringForce springForce = new SpringForce();
        springForce.setStiffness(f);
        springForce.setDampingRatio(f2);
        return springForce;
    }
}
