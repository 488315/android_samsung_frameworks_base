package com.android.systemui.media.mediaoutput.compose.ext;

import android.content.res.Configuration;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt;
import androidx.compose.ui.unit.Dp;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class ConfigurationExtKt {
    public static final boolean isFold(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1004834635);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        composerImpl.startReplaceGroup(386913985);
        float f = ((Configuration) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalConfiguration)).smallestScreenWidthDp;
        Dp.Companion companion = Dp.Companion;
        composerImpl.end(false);
        boolean z = Float.compare(f, (float) 411) > 0;
        composerImpl.end(false);
        return z;
    }

    public static final boolean isLandscape(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1466071787);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        boolean z = ((Configuration) composerImpl.consume(AndroidCompositionLocals_androidKt.LocalConfiguration)).orientation == 2;
        composerImpl.end(false);
        return z;
    }

    public static final boolean isPortrait(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-378085797);
        OpaqueKey opaqueKey = ComposerKt.invocation;
        boolean z = !isLandscape(composerImpl);
        composerImpl.end(false);
        return z;
    }
}
