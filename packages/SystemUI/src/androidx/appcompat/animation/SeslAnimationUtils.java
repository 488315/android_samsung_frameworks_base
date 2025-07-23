package androidx.appcompat.animation;

import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SeslAnimationUtils {
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
