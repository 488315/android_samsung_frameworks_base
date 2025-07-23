package androidx.compose.material3.internal;

import androidx.compose.foundation.layout.AndroidWindowInsets;
import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsetsHolder;
import androidx.compose.foundation.layout.WindowInsetsKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SystemBarsDefaultInsets_androidKt {
    public static final WindowInsets getSystemBarsForVisualComponents(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.internal.<get-systemBarsForVisualComponents> (SystemBarsDefaultInsets.android.kt:25)");
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.foundation.layout.<get-systemBars> (WindowInsets.android.kt:179)");
        }
        WindowInsetsHolder.Companion.getClass();
        WindowInsetsHolder current = WindowInsetsHolder.Companion.current(composer);
        boolean isTraceInProgress = ComposerKt.isTraceInProgress();
        AndroidWindowInsets androidWindowInsets = current.systemBars;
        if (isTraceInProgress) {
            ComposerKt.traceEventEnd();
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.foundation.layout.<get-displayCutout> (WindowInsets.android.kt:143)");
        }
        WindowInsetsHolder.Companion.getClass();
        WindowInsetsHolder current2 = WindowInsetsHolder.Companion.current(composer);
        boolean isTraceInProgress2 = ComposerKt.isTraceInProgress();
        AndroidWindowInsets androidWindowInsets2 = current2.displayCutout;
        if (isTraceInProgress2) {
            ComposerKt.traceEventEnd();
        }
        WindowInsets union = WindowInsetsKt.union(androidWindowInsets, androidWindowInsets2);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return union;
    }
}
