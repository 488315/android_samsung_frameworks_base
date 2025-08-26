package com.android.systemui.media.mediaoutput.compose.ext;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.platform.CompositionLocalsKt;
import androidx.compose.ui.unit.Density;

/* loaded from: classes2.dex */
public abstract class UnitExtKt {
    public static final float toDp(int i, Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-727809474);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.systemui.media.mediaoutput.compose.ext.toDp (UnitExt.kt:13)");
        }
        float fMo55toDpu2uoSUM = ((Density) composerImpl.consume(CompositionLocalsKt.LocalDensity)).mo55toDpu2uoSUM(i);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return fMo55toDpu2uoSUM;
    }
}
