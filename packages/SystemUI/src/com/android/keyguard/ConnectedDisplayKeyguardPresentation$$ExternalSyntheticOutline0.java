package com.android.keyguard;

import android.util.Log;
import androidx.dynamicanimation.animation.SpringForce;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
