package androidx.compose.ui.res;

import android.content.res.Resources;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.Dp;

/* loaded from: classes.dex */
public abstract class PrimitiveResources_androidKt {
    public static final float dimensionResource(int i, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.ui.res.dimensionResource (PrimitiveResources.android.kt:72)");
        }
        ComposerImpl composerImpl = (ComposerImpl) composer;
        float dimension = ((Resources) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalResources)).getDimension(i) / ((Density) composerImpl.consume(CompositionLocalsKt.LocalDensity)).getDensity();
        Dp.Companion companion = Dp.Companion;
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return dimension;
    }

    public static final int integerResource(int i, Composer composer) throws Resources.NotFoundException {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.ui.res.integerResource (PrimitiveResources.android.kt:36)");
        }
        int integer = ((Resources) ((ComposerImpl) composer).consume(AndroidCompositionLocals_androidKt.LocalResources)).getInteger(i);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return integer;
    }
}
