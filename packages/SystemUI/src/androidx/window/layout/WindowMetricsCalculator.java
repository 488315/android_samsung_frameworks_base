package androidx.window.layout;

import androidx.core.view.WindowInsetsCompat;
import androidx.window.layout.util.WindowMetricsCompatHelper;
import androidx.window.layout.util.WindowMetricsCompatHelperApi34Impl;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface WindowMetricsCalculator {
    public static final Companion Companion = Companion.$$INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final Function1 decorator = WindowMetricsCalculator$Companion$decorator$1.INSTANCE;
        public static final WindowMetricsCalculatorCompat windowMetricsCalculatorCompat = new WindowMetricsCalculatorCompat(null, 1, null);

        private Companion() {
        }

        public static WindowMetricsCalculatorCompat getOrCreate() {
            ((WindowMetricsCalculator$Companion$decorator$1) decorator).getClass();
            return windowMetricsCalculatorCompat;
        }

        public static WindowMetrics translateWindowMetrics$window_release(android.view.WindowMetrics windowMetrics) {
            WindowMetricsCompatHelper.Companion.getClass();
            WindowMetricsCompatHelperApi34Impl.INSTANCE.getClass();
            return new WindowMetrics(windowMetrics.getBounds(), WindowInsetsCompat.toWindowInsetsCompat(null, windowMetrics.getWindowInsets()), windowMetrics.getDensity());
        }
    }
}
