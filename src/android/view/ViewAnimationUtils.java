package android.view;

import android.animation.Animator;
import android.animation.RevealAnimator;

/* loaded from: classes4.dex */
public final class ViewAnimationUtils {
    private ViewAnimationUtils() {
    }

    public static Animator createCircularReveal(View view, int i, int i2, float f, float f2) {
        return new RevealAnimator(view, i, i2, f, f2);
    }
}
