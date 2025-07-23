package android.view;

import android.view.animation.Interpolator;

/* loaded from: classes4.dex */
public interface InsetsAnimationSpec {
    long getDurationMs(boolean z);

    Interpolator getInsetsInterpolator(boolean z);
}
