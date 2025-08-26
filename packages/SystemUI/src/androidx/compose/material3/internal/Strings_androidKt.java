package androidx.compose.material3.internal;

import android.content.Context;
import android.content.res.Resources;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;

/* loaded from: classes.dex */
public abstract class Strings_androidKt {
    /* renamed from: getString-2EP1pXo, reason: not valid java name */
    public static final String m322getString2EP1pXo(int i, Composer composer) throws Resources.NotFoundException {
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
