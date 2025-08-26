package com.android.bouncer.ui.compose.theme;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;

/* loaded from: classes.dex */
public abstract class BouncerStyleKt {
    public static final long getDpTextUnit(float f, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-2116717958);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.bouncer.ui.compose.theme.<get-dpTextUnit> (BouncerStyle.kt:64)");
        }
        long jMo60toSp0xMU5do = ((Density) composerImpl.consume(CompositionLocalsKt.LocalDensity)).mo60toSp0xMU5do(f);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return jMo60toSp0xMU5do;
    }
}
