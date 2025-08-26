package com.samsung.sesl.compose.theme;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;

/* loaded from: classes4.dex */
public final class SeslTheme {
    public static final SeslTheme INSTANCE = new SeslTheme();

    private SeslTheme() {
    }

    public static SeslColorScheme getColorScheme(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.samsung.sesl.compose.theme.SeslTheme.<get-colorScheme> (Theme.kt:107)");
        }
        SeslColorScheme seslColorScheme = (SeslColorScheme) ((ComposerImpl) composer).consume(ColorSchemeKt.LocalSeslColorScheme);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return seslColorScheme;
    }
}
