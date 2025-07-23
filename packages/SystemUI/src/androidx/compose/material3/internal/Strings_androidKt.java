package androidx.compose.material3.internal;

import android.content.Context;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class Strings_androidKt {
    /* renamed from: getString-2EP1pXo, reason: not valid java name */
    public static final String m321getString2EP1pXo(int i, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.internal.getString (Strings.android.kt:30)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.consume(AndroidCompositionLocals_androidKt.LocalConfiguration);
        String string = ((Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext)).getResources().getString(i);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return string;
    }
}
