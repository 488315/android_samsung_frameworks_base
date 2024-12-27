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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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

    /* JADX WARN: Removed duplicated region for block: B:102:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0268  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x008e  */
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

    /* JADX WARN: Removed duplicated region for block: B:10:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00bc  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x01d4  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x00e1  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0060  */
    /* JADX WARN: Type inference failed for: r9v15, types: [com.android.systemui.media.mediaoutput.compose.widget.SeekbarDefaults$Thumb$1$1, kotlin.jvm.internal.Lambda] */
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

    /* JADX WARN: Removed duplicated region for block: B:13:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00e3  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0286  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x00c2  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0076  */
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
