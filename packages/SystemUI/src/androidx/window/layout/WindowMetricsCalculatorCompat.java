package androidx.window.layout;

import android.content.Context;
import android.view.WindowManager;
import androidx.core.view.WindowInsetsCompat;
import androidx.window.layout.util.DensityCompatHelper;
import androidx.window.layout.util.DensityCompatHelperApi34Impl;
import androidx.window.layout.util.WindowMetricsCompatHelper;
import androidx.window.layout.util.WindowMetricsCompatHelperApi34Impl;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class WindowMetricsCalculatorCompat implements WindowMetricsCalculator {
    public WindowMetricsCalculatorCompat() {
        this(null, 1, null);
    }

    public final WindowMetrics computeCurrentWindowMetrics(Context context) {
        WindowMetricsCompatHelper.Companion.getClass();
        WindowMetricsCompatHelperApi34Impl.INSTANCE.getClass();
        WindowManager windowManager = (WindowManager) context.getSystemService(WindowManager.class);
        return new WindowMetrics(windowManager.getCurrentWindowMetrics().getBounds(), WindowInsetsCompat.toWindowInsetsCompat(null, windowManager.getCurrentWindowMetrics().getWindowInsets()), windowManager.getCurrentWindowMetrics().getDensity());
    }

    public WindowMetricsCalculatorCompat(DensityCompatHelper densityCompatHelper) {
        CollectionsKt__CollectionsKt.arrayListOf(1, 2, 4, 8, 16, 32, 64, 128);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public WindowMetricsCalculatorCompat(DensityCompatHelper densityCompatHelper, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            DensityCompatHelper.Companion.getClass();
            densityCompatHelper = DensityCompatHelperApi34Impl.INSTANCE;
        }
        this(densityCompatHelper);
    }
}
