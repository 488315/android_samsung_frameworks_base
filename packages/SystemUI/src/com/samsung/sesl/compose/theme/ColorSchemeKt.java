package com.samsung.sesl.compose.theme;

import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import com.samsung.sesl.compose.component.tokens.SeslPaletteTokens;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class ColorSchemeKt {
    public static final StaticProvidableCompositionLocal LocalSeslColorScheme;
    public static final SeslColorScheme seslDefaultDarkColorScheme;
    public static final SeslColorScheme seslDefaultLightColorScheme = m3339seslLightColorSchemeFD3wquc$default();

    static {
        long Color;
        SeslPaletteTokens.INSTANCE.getClass();
        long j = SeslPaletteTokens.Primary_Blue;
        long j2 = SeslPaletteTokens.Primary_Blue_Text_Dark;
        long j3 = SeslPaletteTokens.GRAYSCALE_D7;
        long j4 = SeslPaletteTokens.Primary_Blue_Point_Dark;
        Color.Companion.getClass();
        long j5 = Color.Unspecified;
        Color = ColorKt.Color(Color.m461getRedimpl(r0), Color.m460getGreenimpl(r0), Color.m458getBlueimpl(r0), 0.8f, Color.m459getColorSpaceimpl(SeslPaletteTokens.GRAY_TEXT_D6));
        seslDefaultDarkColorScheme = new SeslColorScheme(j, j2, j3, j4, j5, j5, j5, Color, null);
        LocalSeslColorScheme = new StaticProvidableCompositionLocal(new ColorSchemeKt$$ExternalSyntheticLambda0());
    }

    /* renamed from: seslLightColorScheme-FD3wquc$default, reason: not valid java name */
    public static SeslColorScheme m3339seslLightColorSchemeFD3wquc$default() {
        long Color;
        SeslPaletteTokens seslPaletteTokens = SeslPaletteTokens.INSTANCE;
        seslPaletteTokens.getClass();
        long j = SeslPaletteTokens.Primary_Blue;
        seslPaletteTokens.getClass();
        long j2 = SeslPaletteTokens.Primary_Blue_Text_Light;
        seslPaletteTokens.getClass();
        long j3 = SeslPaletteTokens.GRAYSCALE_L5;
        seslPaletteTokens.getClass();
        long j4 = SeslPaletteTokens.Primary_Blue_Point_Light;
        Color.Companion companion = Color.Companion;
        companion.getClass();
        long j5 = Color.Unspecified;
        companion.getClass();
        companion.getClass();
        seslPaletteTokens.getClass();
        Color = ColorKt.Color(Color.m461getRedimpl(r0), Color.m460getGreenimpl(r0), Color.m458getBlueimpl(r0), 0.8f, Color.m459getColorSpaceimpl(SeslPaletteTokens.GRAY_TEXT_D6));
        return new SeslColorScheme(j, j2, j3, j4, j5, j5, j5, Color, null);
    }
}
