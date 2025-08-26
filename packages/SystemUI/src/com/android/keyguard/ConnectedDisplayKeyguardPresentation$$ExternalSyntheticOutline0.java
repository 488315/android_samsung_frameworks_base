package com.android.keyguard;

import android.util.Log;
import androidx.dynamicanimation.animation.SpringForce;

/* loaded from: classes.dex */
public abstract /* synthetic */ class ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0 {
    public static SpringForce m(float f, float f2) {
        SpringForce springForce = new SpringForce();
        springForce.setStiffness(f);
        springForce.setDampingRatio(f2);
        return springForce;
    }

    public static void m(int i, String str, String str2) {
        Log.i(str2, str + i);
    }
}
