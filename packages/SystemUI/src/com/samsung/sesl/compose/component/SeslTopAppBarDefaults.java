package com.samsung.sesl.compose.component;

import androidx.compose.foundation.DarkThemeKt;
import androidx.compose.foundation.layout.PaddingKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.text.PlatformTextStyle;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.LineHeightStyle;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.text.style.TextIndent;
import androidx.compose.ui.text.style.TextMotion;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.TextUnitKt;
import com.samsung.sesl.compose.component.tokens.SeslAppBarColorSchemeKeyTokens;
import com.samsung.sesl.compose.component.tokens.SeslPaletteTokens;
import com.samsung.sesl.compose.foundation.theme.BasicColorSchemeKt;
import com.samsung.sesl.compose.phone.ui.text.SeslFontWeight;
import com.samsung.sesl.compose.theme.SeslColorScheme;
import com.samsung.sesl.compose.theme.SeslTheme;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class SeslTopAppBarDefaults {
    public static final SeslTopAppBarDefaults INSTANCE = new SeslTopAppBarDefaults();
    public static final float NavigateUpButtonInsetStart;
    public static final float TopAppBarTitleSlotInsetStart;
    public static final TextStyle titleTextStyle;

    static {
        Dp.Companion companion = Dp.Companion;
        NavigateUpButtonInsetStart = 12;
        long sp = TextUnitKt.getSp(21);
        long sp2 = TextUnitKt.getSp(28);
        SeslFontWeight.INSTANCE.getClass();
        titleTextStyle = new TextStyle(0L, sp, SeslFontWeight.Bold, (FontStyle) null, (FontSynthesis) null, (FontFamily) null, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (DrawStyle) null, 0, 0, sp2, (TextIndent) null, (PlatformTextStyle) null, (LineHeightStyle) null, 0, 0, (TextMotion) null, 16646137, (DefaultConstructorMarker) null);
        float f = 8;
        float f2 = 0;
        PaddingKt.m123PaddingValuesa9UjIt4(f, f2, f, f2);
        PaddingKt.m123PaddingValuesa9UjIt4(6, f2, 20, f2);
        TextUnitKt.getSp(34);
        TopAppBarTitleSlotInsetStart = 28;
    }

    private SeslTopAppBarDefaults() {
    }

    /* renamed from: topAppBarColors-5tl4gsc, reason: not valid java name */
    public static SeslTopAppBarColors m3345topAppBarColors5tl4gsc(long j, Composer composer) {
        long j2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(2117422296);
        Color.Companion companion = Color.Companion;
        companion.getClass();
        long j3 = Color.Unspecified;
        companion.getClass();
        companion.getClass();
        companion.getClass();
        companion.getClass();
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.samsung.sesl.compose.component.SeslTopAppBarDefaults.topAppBarColors (AppBar.kt:515)");
        }
        SeslTheme.INSTANCE.getClass();
        SeslColorScheme colorScheme = SeslTheme.getColorScheme(composerImpl);
        composerImpl.startReplaceGroup(-1646470043);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("com.samsung.sesl.compose.component.<get-defaultTopAppBarColors> (AppBar.kt:568)");
        }
        if (colorScheme.defaultTopAppBarColorsCached == null) {
            long color = BasicColorSchemeKt.toColor(SeslAppBarColorSchemeKeyTokens.TopAppBarBackgroundColor, composerImpl);
            Color.Companion.getClass();
            long j4 = Color.Unspecified;
            if (DarkThemeKt.isSystemInDarkTheme(composerImpl)) {
                SeslPaletteTokens.INSTANCE.getClass();
                j2 = SeslPaletteTokens.GRAY_TEXT_D1;
            } else {
                SeslPaletteTokens.INSTANCE.getClass();
                j2 = SeslPaletteTokens.GRAYSCALE_D1;
            }
            colorScheme.defaultTopAppBarColorsCached = new SeslTopAppBarColors(color, j4, j2, BasicColorSchemeKt.toColor(SeslAppBarColorSchemeKeyTokens.TopAppBarTitleTextColor, composerImpl), BasicColorSchemeKt.toColor(SeslAppBarColorSchemeKeyTokens.TopAppBarMenuTextColor, composerImpl), BasicColorSchemeKt.toColor(SeslAppBarColorSchemeKeyTokens.TopAppBarSubTitleTextColor, composerImpl), null);
        }
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        SeslTopAppBarColors seslTopAppBarColors = new SeslTopAppBarColors(j, j3, j3, j3, j3, j3, null);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
        return seslTopAppBarColors;
    }
}
