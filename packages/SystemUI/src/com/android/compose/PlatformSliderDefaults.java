package com.android.compose;

import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.unit.Dp;

/* loaded from: classes.dex */
public final class PlatformSliderDefaults {
    public static final float DefaultPlatformSliderDraggingCornerRadius;
    public static final PlatformSliderDefaults INSTANCE = new PlatformSliderDefaults();

    static {
        Dp.Companion companion = Dp.Companion;
        DefaultPlatformSliderDraggingCornerRadius = 8;
    }

    private PlatformSliderDefaults() {
    }

    public static PlatformSliderColors defaultPlatformSliderColors(Composer composer) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(481782045);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.android.compose.PlatformSliderDefaults.defaultPlatformSliderColors (PlatformSlider.kt:458)");
        }
        MaterialTheme.INSTANCE.getClass();
        PlatformSliderColors platformSliderColors = new PlatformSliderColors(MaterialTheme.getColorScheme(composerImpl).secondaryContainer, MaterialTheme.getColorScheme(composerImpl).primary, MaterialTheme.getColorScheme(composerImpl).onPrimary, MaterialTheme.getColorScheme(composerImpl).onPrimary, MaterialTheme.getColorScheme(composerImpl).onSecondaryContainer, MaterialTheme.getColorScheme(composerImpl).surfaceContainerHighest, MaterialTheme.getColorScheme(composerImpl).surfaceContainerHighest, MaterialTheme.getColorScheme(composerImpl).outline, MaterialTheme.getColorScheme(composerImpl).onSurfaceVariant, null);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return platformSliderColors;
    }
}
