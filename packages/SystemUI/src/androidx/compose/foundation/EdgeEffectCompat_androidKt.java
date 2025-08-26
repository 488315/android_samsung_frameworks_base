package androidx.compose.foundation;

import android.view.ViewConfiguration;

/* loaded from: classes.dex */
public abstract class EdgeEffectCompat_androidKt {
    public static final double DecelMinusOne;
    public static final double DecelerationRate;
    public static final float PlatformFlingScrollFriction = ViewConfiguration.getScrollFriction();

    static {
        double dLog = Math.log(0.78d) / Math.log(0.9d);
        DecelerationRate = dLog;
        DecelMinusOne = dLog - 1.0d;
    }
}
