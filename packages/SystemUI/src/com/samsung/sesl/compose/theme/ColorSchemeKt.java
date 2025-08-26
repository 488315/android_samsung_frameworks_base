package com.samsung.sesl.compose.theme;

import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import com.samsung.sesl.compose.component.tokens.SeslPaletteTokens;

/* loaded from: classes4.dex */
public abstract class ColorSchemeKt {
    public static final StaticProvidableCompositionLocal LocalSeslColorScheme;
    public static final SeslColorScheme seslDefaultDarkColorScheme;
    public static final SeslColorScheme seslDefaultLightColorScheme = m3357seslLightColorSchemeFD3wquc$default();

    static {
        Color.Companion.getClass();
        long j = Color.Unspecified;
        SeslPaletteTokens.INSTANCE.getClass();
        long j2 = SeslPaletteTokens.GRAY_TEXT_D6;
        seslDefaultDarkColorScheme = new SeslColorScheme(j, j, j, j, j, j, j, ColorKt.Color(Color.m463getRedimpl(j2), Color.m462getGreenimpl(j2), Color.m460getBlueimpl(j2), 0.8f, Color.m461getColorSpaceimpl(j2)), null);
        LocalSeslColorScheme = new StaticProvidableCompositionLocal(new ColorSchemeKt$$ExternalSyntheticLambda0());
    }

    /* renamed from: seslLightColorScheme-FD3wquc$default, reason: not valid java name */
    public static SeslColorScheme m3357seslLightColorSchemeFD3wquc$default() {
        Color.Companion companion = Color.Companion;
        companion.getClass();
        long j = Color.Unspecified;
        companion.getClass();
        companion.getClass();
        companion.getClass();
        companion.getClass();
        companion.getClass();
        companion.getClass();
        SeslPaletteTokens.INSTANCE.getClass();
        long j2 = SeslPaletteTokens.GRAY_TEXT_D6;
        return new SeslColorScheme(j, j, j, j, j, j, j, ColorKt.Color(Color.m463getRedimpl(j2), Color.m462getGreenimpl(j2), Color.m460getBlueimpl(j2), 0.8f, Color.m461getColorSpaceimpl(j2)), null);
    }
}
