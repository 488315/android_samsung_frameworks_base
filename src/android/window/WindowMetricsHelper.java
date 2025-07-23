package android.window;

import android.graphics.Rect;
import android.view.WindowInsets;
import android.view.WindowMetrics;

/* loaded from: classes5.dex */
public final class WindowMetricsHelper {
    private WindowMetricsHelper() {
    }

    public static Rect getBoundsExcludingNavigationBarAndCutout(WindowMetrics windowMetrics) {
        WindowInsets windowInsets = windowMetrics.getWindowInsets();
        Rect rect = new Rect(windowMetrics.getBounds());
        rect.inset(windowInsets.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() | WindowInsets.Type.displayCutout()));
        return rect;
    }
}
