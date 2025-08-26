package androidx.compose.ui.res;

import android.content.Context;
import android.content.res.Resources;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.core.content.res.ResourcesCompat;

/* loaded from: classes.dex */
public abstract class ColorResources_androidKt {
    public static final long colorResource(int i, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.ui.res.colorResource (ColorResources.android.kt:34)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        Context context = (Context) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalContext);
        Resources resources = (Resources) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalResources);
        Resources.Theme theme = context.getTheme();
        ThreadLocal threadLocal = ResourcesCompat.sTempTypedValue;
        long jColor = ColorKt.Color(resources.getColor(i, theme));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return jColor;
    }
}
