package com.android.systemui.keyguard.animator;

import androidx.dynamicanimation.animation.SpringForce;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract /* synthetic */ class ActionUpOrCancelHandler$$ExternalSyntheticOutline0 {
    public static SpringForce m(float f, float f2) {
        SpringForce springForce = new SpringForce();
        springForce.setStiffness(f);
        springForce.setDampingRatio(f2);
        return springForce;
    }
}
