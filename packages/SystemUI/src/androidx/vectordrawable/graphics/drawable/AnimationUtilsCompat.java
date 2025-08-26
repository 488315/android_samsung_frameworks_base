package androidx.vectordrawable.graphics.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

/* loaded from: classes.dex */
public class AnimationUtilsCompat {
    private AnimationUtilsCompat() {
    }

    public static Interpolator loadInterpolator(int i, Context context) throws Resources.NotFoundException {
        Interpolator interpolatorLoadInterpolator = AnimationUtils.loadInterpolator(context, i);
        if (interpolatorLoadInterpolator != null) {
            return interpolatorLoadInterpolator;
        }
        throw new NullPointerException("Failed to parse interpolator, no start tag found");
    }
}
