package androidx.compose.material3;

import androidx.compose.foundation.layout.WindowInsets;
import androidx.compose.material3.internal.SystemBarsDefaultInsets_androidKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ScaffoldDefaults {
    public static final ScaffoldDefaults INSTANCE = new ScaffoldDefaults();

    private ScaffoldDefaults() {
    }

    public static WindowInsets getContentWindowInsets(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.ScaffoldDefaults.<get-contentWindowInsets> (Scaffold.kt:295)");
        }
        WindowInsets.Companion companion = WindowInsets.Companion;
        WindowInsets systemBarsForVisualComponents = SystemBarsDefaultInsets_androidKt.getSystemBarsForVisualComponents(composer);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return systemBarsForVisualComponents;
    }
}
