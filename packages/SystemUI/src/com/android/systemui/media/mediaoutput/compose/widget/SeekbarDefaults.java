package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.material3.MaterialTheme;
import androidx.compose.material3.SliderColors;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.unit.Dp;
import com.samsung.sesl.compose.component.tokens.SeslSliderColorSchemeKeyTokens;
import com.samsung.sesl.compose.foundation.theme.BasicColorSchemeKt;

public final class SeekbarDefaults {
    public static final SeekbarDefaults INSTANCE = new SeekbarDefaults();
    public static final float thumbSize;

    static {
        Dp.Companion companion = Dp.Companion;
        thumbSize = 13;
    }

    private SeekbarDefaults() {
    }

    /* renamed from: colors-q0g_0yA, reason: not valid java name */
    public static SliderColors m1979colorsq0g_0yA(Composer composer) {
        long Color;
        long Color2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(116419295);
        long color = BasicColorSchemeKt.toColor(SeslSliderColorSchemeKeyTokens.ThumbFillColor, composerImpl);
        long color2 = BasicColorSchemeKt.toColor(SeslSliderColorSchemeKeyTokens.ActivateTrackColor, composerImpl);
        long color3 = BasicColorSchemeKt.toColor(SeslSliderColorSchemeKeyTokens.ActivateTickColor, composerImpl);
        long color4 = BasicColorSchemeKt.toColor(SeslSliderColorSchemeKeyTokens.InactivateTrackColor, composerImpl);
        long color5 = BasicColorSchemeKt.toColor(SeslSliderColorSchemeKeyTokens.InactiveTickColor, composerImpl);
        MaterialTheme materialTheme = MaterialTheme.INSTANCE;
        materialTheme.getClass();
        long m394compositeOverOWjLjI = ColorKt.m394compositeOverOWjLjI(color, MaterialTheme.getColorScheme(composerImpl).surface);
        materialTheme.getClass();
        long m394compositeOverOWjLjI2 = ColorKt.m394compositeOverOWjLjI(color3, MaterialTheme.getColorScheme(composerImpl).surface);
        Color = ColorKt.Color(Color.m391getRedimpl(color2), Color.m390getGreenimpl(color2), Color.m388getBlueimpl(color2), Color.m387getAlphaimpl(color2) * 0.4f, Color.m389getColorSpaceimpl(color2));
        materialTheme.getClass();
        long m394compositeOverOWjLjI3 = ColorKt.m394compositeOverOWjLjI(color5, MaterialTheme.getColorScheme(composerImpl).surface);
        Color2 = ColorKt.Color(Color.m391getRedimpl(color4), Color.m390getGreenimpl(color4), Color.m388getBlueimpl(color4), Color.m387getAlphaimpl(color4) * 0.4f, Color.m389getColorSpaceimpl(color4));
        OpaqueKey opaqueKey = ComposerKt.invocation;
        SliderColors sliderColors = new SliderColors(color, color2, color3, color4, color5, m394compositeOverOWjLjI, Color, m394compositeOverOWjLjI2, Color2, m394compositeOverOWjLjI3, null);
        composerImpl.end(false);
        return sliderColors;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void SliderContainer(final androidx.compose.runtime.State r18, androidx.compose.ui.Modifier r19, final androidx.compose.runtime.MutableState r20, final kotlin.jvm.functions.Function1 r21, com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel r22, final kotlin.jvm.functions.Function3 r23, androidx.compose.runtime.Composer r24, final int r25, final int r26) {
        /*
            Method dump skipped, instructions count: 621
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults.SliderContainer(androidx.compose.runtime.State, androidx.compose.ui.Modifier, androidx.compose.runtime.MutableState, kotlin.jvm.functions.Function1, com.android.systemui.media.mediaoutput.viewmodel.LabsViewModel, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int, int):void");
    }

    /* renamed from: Thumb-FJfuzF0, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void m1980ThumbFJfuzF0(final androidx.compose.runtime.State r19, androidx.compose.ui.Modifier r20, androidx.compose.material3.SliderColors r21, boolean r22, final float r23, androidx.compose.runtime.Composer r24, final int r25, final int r26) {
        /*
            Method dump skipped, instructions count: 472
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults.m1980ThumbFJfuzF0(androidx.compose.runtime.State, androidx.compose.ui.Modifier, androidx.compose.material3.SliderColors, boolean, float, androidx.compose.runtime.Composer, int, int):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void Track(final androidx.compose.material3.SliderState r20, final androidx.compose.runtime.State r21, androidx.compose.ui.Modifier r22, androidx.compose.material3.SliderColors r23, androidx.compose.runtime.Composer r24, final int r25, final int r26) {
        /*
            Method dump skipped, instructions count: 650
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults.Track(androidx.compose.material3.SliderState, androidx.compose.runtime.State, androidx.compose.ui.Modifier, androidx.compose.material3.SliderColors, androidx.compose.runtime.Composer, int, int):void");
    }
}
