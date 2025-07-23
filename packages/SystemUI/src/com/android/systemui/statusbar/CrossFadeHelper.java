package com.android.systemui.statusbar;

import android.animation.Animator;
import android.view.View;
import android.view.animation.PathInterpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CrossFadeHelper {
    public static void fadeIn(View view, long j, int i) {
        view.animate().cancel();
        if (view.getVisibility() == 4) {
            view.setAlpha(0.0f);
            view.setVisibility(0);
        }
        view.animate().alpha(1.0f).setDuration(j).setStartDelay(i).setInterpolator(Interpolators.ALPHA_IN).withEndAction(null);
        if (!view.hasOverlappingRendering() || view.getLayerType() == 2) {
            return;
        }
        view.animate().withLayer();
    }

    public static void fadeOut(long j, final View view, final Runnable runnable) {
        view.animate().cancel();
        view.animate().alpha(0.0f).setDuration(j).setInterpolator(Interpolators.ALPHA_OUT).setStartDelay(0).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.CrossFadeHelper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                Runnable runnable2 = runnable;
                View view2 = view;
                if (runnable2 != null) {
                    runnable2.run();
                }
                if (view2.getVisibility() != 8) {
                    view2.setVisibility(4);
                }
            }
        });
        if (view.hasOverlappingRendering()) {
            view.animate().withLayer();
        }
    }

    public static void updateLayerType(float f, View view) {
        if (!view.hasOverlappingRendering() || f <= 0.0f || f >= 1.0f) {
            if (view.getLayerType() != 2 || view.getTag(R.id.cross_fade_layer_type_changed_tag) == null) {
                return;
            }
            view.setLayerType(0, null);
            return;
        }
        if (view.getLayerType() != 2) {
            view.setLayerType(2, null);
            view.setTag(R.id.cross_fade_layer_type_changed_tag, Boolean.TRUE);
        }
    }

    public static void fadeOut(final View view, Animator.AnimatorListener animatorListener) {
        view.animate().cancel();
        view.animate().alpha(0.0f).setDuration(210L).setInterpolator(Interpolators.ALPHA_OUT).setStartDelay(0).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.CrossFadeHelper$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                View view2 = view;
                if (view2.getVisibility() != 8) {
                    view2.setVisibility(4);
                }
            }
        }).setListener(animatorListener);
        if (view.hasOverlappingRendering()) {
            view.animate().withLayer();
        }
    }

    public static void fadeIn(View view, Animator.AnimatorListener animatorListener) {
        view.animate().cancel();
        if (view.getVisibility() == 4) {
            view.setAlpha(0.0f);
            view.setVisibility(0);
        }
        view.animate().alpha(1.0f).setDuration(210L).setStartDelay(0).setInterpolator(Interpolators.ALPHA_IN).setListener(animatorListener);
        if (!view.hasOverlappingRendering() || view.getLayerType() == 2) {
            return;
        }
        view.animate().withLayer();
    }

    public static void fadeOut(View view, float f, boolean z) {
        view.animate().cancel();
        if (f == 1.0f && view.getVisibility() != 8) {
            view.setVisibility(4);
        } else if (view.getVisibility() == 4) {
            view.setVisibility(0);
        }
        if (z) {
            f = Math.min(f / 0.5833333f, 1.0f);
        }
        float interpolation = ((PathInterpolator) Interpolators.ALPHA_OUT).getInterpolation(1.0f - f);
        view.setAlpha(interpolation);
        updateLayerType(interpolation, view);
    }

    public static void fadeIn(View view, float f, boolean z) {
        view.animate().cancel();
        if (view.getVisibility() == 4) {
            view.setVisibility(0);
        }
        if (z) {
            f = Math.min(f / 0.5833333f, 1.0f);
        }
        float interpolation = ((PathInterpolator) Interpolators.ALPHA_IN).getInterpolation(f);
        view.setAlpha(interpolation);
        updateLayerType(interpolation, view);
    }
}
