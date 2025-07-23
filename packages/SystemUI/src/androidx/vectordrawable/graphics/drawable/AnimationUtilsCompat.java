package androidx.vectordrawable.graphics.drawable;

import android.content.Context;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AnimationUtilsCompat {
    private AnimationUtilsCompat() {
    }

    public static Interpolator loadInterpolator(int i, Context context) {
        Interpolator loadInterpolator = AnimationUtils.loadInterpolator(context, i);
        if (loadInterpolator != null) {
            return loadInterpolator;
        }
        throw new NullPointerException("Failed to parse interpolator, no start tag found");
    }
}
