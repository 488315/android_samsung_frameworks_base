package androidx.window.layout;

import android.content.Context;
import android.view.WindowManager;
import androidx.core.view.WindowInsetsCompat;
import androidx.window.layout.util.DensityCompatHelper;
import androidx.window.layout.util.WindowMetricsCompatHelper;
import androidx.window.layout.util.WindowMetricsCompatHelperApi34Impl;
import kotlin.collections.CollectionsKt__CollectionsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public WindowMetricsCalculatorCompat(androidx.window.layout.util.DensityCompatHelper r1, int r2, kotlin.jvm.internal.DefaultConstructorMarker r3) {
        /*
            r0 = this;
            r2 = r2 & 1
            if (r2 == 0) goto Lb
            androidx.window.layout.util.DensityCompatHelper$Companion r1 = androidx.window.layout.util.DensityCompatHelper.Companion
            r1.getClass()
            androidx.window.layout.util.DensityCompatHelperApi34Impl r1 = androidx.window.layout.util.DensityCompatHelperApi34Impl.INSTANCE
        Lb:
            r0.<init>(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.window.layout.WindowMetricsCalculatorCompat.<init>(androidx.window.layout.util.DensityCompatHelper, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
