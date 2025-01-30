package com.android.systemui.statusbar;

import android.view.View;
import android.view.animation.PathInterpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CrossFadeHelper {
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

    public static void fadeOut(final View view, long j, final Runnable runnable) {
        view.animate().cancel();
        view.animate().alpha(0.0f).setDuration(j).setInterpolator(Interpolators.ALPHA_OUT).setStartDelay(0).withEndAction(new Runnable() { // from class: com.android.systemui.statusbar.CrossFadeHelper.1
            @Override // java.lang.Runnable
            public final void run() {
                Runnable runnable2 = runnable;
                if (runnable2 != null) {
                    runnable2.run();
                }
                if (view.getVisibility() != 8) {
                    view.setVisibility(4);
                }
            }
        });
        if (view.hasOverlappingRendering()) {
            view.animate().withLayer();
        }
    }

    public static void updateLayerType(View view, float f) {
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
        updateLayerType(view, interpolation);
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
        updateLayerType(view, interpolation);
    }
}
