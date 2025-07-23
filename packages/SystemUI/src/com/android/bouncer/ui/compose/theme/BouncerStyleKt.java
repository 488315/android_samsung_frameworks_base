package com.android.bouncer.ui.compose.theme;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class BouncerStyleKt {
    public static final long getDpTextUnit(float f, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-2116717958);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.bouncer.ui.compose.theme.<get-dpTextUnit> (BouncerStyle.kt:64)");
        }
        long mo59toSp0xMU5do = ((Density) composerImpl.consume(CompositionLocalsKt.LocalDensity)).mo59toSp0xMU5do(f);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return mo59toSp0xMU5do;
    }
}
