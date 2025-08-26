package androidx.compose.material3.internal;

import androidx.compose.foundation.layout.AndroidWindowInsets;
import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.foundation.layout.WindowInsetsHolder;
import androidx.compose.foundation.layout.WindowInsetsKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;

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
        WindowInsetsHolder windowInsetsHolderCurrent = WindowInsetsHolder.Companion.current(composer);
        boolean zIsTraceInProgress = ComposerKt.isTraceInProgress();
        AndroidWindowInsets androidWindowInsets = windowInsetsHolderCurrent.systemBars;
        if (zIsTraceInProgress) {
            ComposerKt.traceEventEnd();
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.foundation.layout.<get-displayCutout> (WindowInsets.android.kt:143)");
        }
        WindowInsetsHolder.Companion.getClass();
        WindowInsetsHolder windowInsetsHolderCurrent2 = WindowInsetsHolder.Companion.current(composer);
        boolean zIsTraceInProgress2 = ComposerKt.isTraceInProgress();
        AndroidWindowInsets androidWindowInsets2 = windowInsetsHolderCurrent2.displayCutout;
        if (zIsTraceInProgress2) {
            ComposerKt.traceEventEnd();
        }
        WindowInsets windowInsetsUnion = WindowInsetsKt.union(androidWindowInsets, androidWindowInsets2);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return windowInsetsUnion;
    }
}
