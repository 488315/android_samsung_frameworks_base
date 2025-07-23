package androidx.compose.foundation.layout;

import androidx.compose.foundation.layout.WindowInsetsHolder;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.core.graphics.Insets;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class WindowInsets_androidKt {
    public static final WindowInsets getSafeDrawing(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.foundation.layout.<get-safeDrawing> (WindowInsets.android.kt:198)");
        }
        WindowInsetsHolder.Companion.getClass();
        WindowInsets windowInsets = WindowInsetsHolder.Companion.current(composer).safeDrawing;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return windowInsets;
    }

    public static final InsetsValues toInsetsValues(Insets insets) {
        return new InsetsValues(insets.left, insets.top, insets.right, insets.bottom);
    }
}
