package androidx.compose.foundation;

import android.content.res.Configuration;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class DarkThemeKt {
    public static final boolean isSystemInDarkTheme(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.foundation.isSystemInDarkTheme (DarkTheme.kt:36)");
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.foundation._isSystemInDarkTheme (DarkTheme.android.kt:45)");
        }
        boolean z = (((Configuration) ((ComposerImpl) composer).consume(AndroidCompositionLocals_androidKt.LocalConfiguration)).uiMode & 48) == 32;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return z;
    }
}
