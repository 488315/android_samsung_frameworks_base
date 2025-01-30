package androidx.appcompat.animation;

import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SeslAnimationUtils {
    public static final Interpolator SINE_IN_OUT_70 = new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f);
    public static final Interpolator SINE_IN_OUT_80 = new PathInterpolator(0.33f, 0.0f, 0.2f, 1.0f);
    public static final Interpolator SINE_IN_OUT_90 = new PathInterpolator(0.33f, 0.0f, 0.1f, 1.0f);
    public static final Interpolator SINE_OUT_80 = new PathInterpolator(0.17f, 0.17f, 0.2f, 1.0f);

    static {
        new PathInterpolator(0.17f, 0.17f, 0.3f, 1.0f);
        new SeslElasticInterpolator(1.0f, 0.8f);
        new SeslElasticInterpolator(1.0f, 0.7f);
    }
}
