package androidx.compose.ui.res;

import android.content.res.Resources;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class StringResources_androidKt {
    public static final String stringResource(int i, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.ui.res.stringResource (StringResources.android.kt:33)");
        }
        String string = ((Resources) ((ComposerImpl) composer).consume(AndroidCompositionLocals_androidKt.LocalResources)).getString(i);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return string;
    }

    public static final String stringResource(int i, Object[] objArr, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.ui.res.stringResource (StringResources.android.kt:46)");
        }
        String string = ((Resources) ((ComposerImpl) composer).consume(AndroidCompositionLocals_androidKt.LocalResources)).getString(i, Arrays.copyOf(objArr, objArr.length));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return string;
    }
}
