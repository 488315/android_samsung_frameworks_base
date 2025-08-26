package com.samsung.sesl.compose.component;

import androidx.compose.material3.IconButtonColors;
import androidx.compose.material3.IconButtonDefaults;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import com.samsung.sesl.compose.component.tokens.SeslButtonColorSchemeKeyTokens;
import com.samsung.sesl.compose.foundation.theme.BasicColorSchemeKt;

/* loaded from: classes4.dex */
public final class SeslIconButtonDefaults {
    public static final SeslIconButtonDefaults INSTANCE = new SeslIconButtonDefaults();

    private SeslIconButtonDefaults() {
    }

    public static IconButtonColors iconButtonColors(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1466351074);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslIconButtonDefaults.iconButtonColors (IconButton.kt:239)");
        }
        IconButtonDefaults iconButtonDefaults = IconButtonDefaults.INSTANCE;
        long color = BasicColorSchemeKt.toColor(SeslButtonColorSchemeKeyTokens.ContentColor, composerImpl);
        iconButtonDefaults.getClass();
        IconButtonColors iconButtonColorsM267iconButtonColorsro_MJ88 = IconButtonDefaults.m267iconButtonColorsro_MJ88(0L, color, composerImpl, 13);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return iconButtonColorsM267iconButtonColorsro_MJ88;
    }
}
