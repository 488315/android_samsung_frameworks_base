package androidx.compose.material3;

import androidx.compose.foundation.gestures.Orientation;
import androidx.compose.material3.tokens.ColorSchemeKeyTokens;
import androidx.compose.material3.tokens.SliderTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.ui.geometry.CornerRadius;
import androidx.compose.ui.geometry.Rect;
import androidx.compose.ui.geometry.RectKt;
import androidx.compose.ui.geometry.RoundRect;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.geometry.SizeKt;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.Path;
import androidx.compose.ui.graphics.drawscope.DrawScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SliderDefaults {
    public static final SliderDefaults INSTANCE = new SliderDefaults();
    public static final float TickSize;
    public static final float TrackStopIndicatorSize;
    public static final AndroidPath trackPath;

    static {
        SliderTokens.INSTANCE.getClass();
        float f = SliderTokens.StopIndicatorSize;
        TrackStopIndicatorSize = f;
        TickSize = f;
        trackPath = AndroidPath_androidKt.Path();
    }

    private SliderDefaults() {
    }

    public static SliderColors colors(Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.SliderDefaults.colors (Slider.kt:1080)");
        }
        MaterialTheme.INSTANCE.getClass();
        SliderColors defaultSliderColors$material3_release = getDefaultSliderColors$material3_release(MaterialTheme.getColorScheme(composer));
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return defaultSliderColors$material3_release;
    }

    /* renamed from: drawStopIndicator-x3O1jOs, reason: not valid java name */
    public static void m292drawStopIndicatorx3O1jOs(DrawScope drawScope, long j, float f, long j2) {
        DrawScope.m532drawCircleVaOC9Bg$default(drawScope, j2, drawScope.mo57toPx0680j_4(f) / 2.0f, j, 0.0f, null, 0, 120);
    }

    /* renamed from: drawTrackPath-zXTsYAs, reason: not valid java name */
    public static void m293drawTrackPathzXTsYAs(DrawScope drawScope, Orientation orientation, long j, long j2, long j3, float f, float f2) {
        RoundRect roundRect;
        long floatToRawIntBits = (Float.floatToRawIntBits(f) << 32) | (Float.floatToRawIntBits(f) & 4294967295L);
        CornerRadius.Companion companion = CornerRadius.Companion;
        long floatToRawIntBits2 = (Float.floatToRawIntBits(f2) << 32) | (Float.floatToRawIntBits(f2) & 4294967295L);
        if (orientation == Orientation.Vertical) {
            Rect m411Recttz77jQw = RectKt.m411Recttz77jQw(j, SizeKt.Size(Size.m417getWidthimpl(j2), Size.m415getHeightimpl(j2)));
            roundRect = new RoundRect(m411Recttz77jQw.left, m411Recttz77jQw.top, m411Recttz77jQw.right, m411Recttz77jQw.bottom, floatToRawIntBits, floatToRawIntBits, floatToRawIntBits2, floatToRawIntBits2, null);
        } else {
            Rect m411Recttz77jQw2 = RectKt.m411Recttz77jQw(j, SizeKt.Size(Size.m417getWidthimpl(j2), Size.m415getHeightimpl(j2)));
            roundRect = new RoundRect(m411Recttz77jQw2.left, m411Recttz77jQw2.top, m411Recttz77jQw2.right, m411Recttz77jQw2.bottom, floatToRawIntBits, floatToRawIntBits2, floatToRawIntBits2, floatToRawIntBits, null);
        }
        AndroidPath androidPath = trackPath;
        Path.addRoundRect$default(androidPath, roundRect);
        DrawScope.m537drawPathLG529CI$default(drawScope, androidPath, j3, 60);
        androidPath.internalPath.rewind();
    }

    public static SliderColors getDefaultSliderColors$material3_release(ColorScheme colorScheme) {
        long Color;
        long Color2;
        long Color3;
        long Color4;
        long Color5;
        SliderColors sliderColors = colorScheme.defaultSliderColorsCached;
        if (sliderColors != null) {
            return sliderColors;
        }
        SliderTokens.INSTANCE.getClass();
        long fromToken = ColorSchemeKt.fromToken(colorScheme, SliderTokens.HandleColor);
        ColorSchemeKeyTokens colorSchemeKeyTokens = SliderTokens.ActiveTrackColor;
        long fromToken2 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens);
        ColorSchemeKeyTokens colorSchemeKeyTokens2 = SliderTokens.InactiveTrackColor;
        long fromToken3 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens2);
        long fromToken4 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens2);
        long fromToken5 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens);
        Color = ColorKt.Color(Color.m461getRedimpl(r14), Color.m460getGreenimpl(r14), Color.m458getBlueimpl(r14), SliderTokens.DisabledHandleOpacity, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, SliderTokens.DisabledHandleColor)));
        long m464compositeOverOWjLjI = ColorKt.m464compositeOverOWjLjI(Color, colorScheme.surface);
        ColorSchemeKeyTokens colorSchemeKeyTokens3 = SliderTokens.DisabledActiveTrackColor;
        long fromToken6 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens3);
        float f = SliderTokens.DisabledActiveTrackOpacity;
        Color2 = ColorKt.Color(Color.m461getRedimpl(fromToken6), Color.m460getGreenimpl(fromToken6), Color.m458getBlueimpl(fromToken6), f, Color.m459getColorSpaceimpl(fromToken6));
        ColorSchemeKeyTokens colorSchemeKeyTokens4 = SliderTokens.DisabledInactiveTrackColor;
        long fromToken7 = ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens4);
        float f2 = SliderTokens.DisabledInactiveTrackOpacity;
        Color3 = ColorKt.Color(Color.m461getRedimpl(fromToken7), Color.m460getGreenimpl(fromToken7), Color.m458getBlueimpl(fromToken7), f2, Color.m459getColorSpaceimpl(fromToken7));
        Color4 = ColorKt.Color(Color.m461getRedimpl(r1), Color.m460getGreenimpl(r1), Color.m458getBlueimpl(r1), f2, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens4)));
        Color5 = ColorKt.Color(Color.m461getRedimpl(r4), Color.m460getGreenimpl(r4), Color.m458getBlueimpl(r4), f, Color.m459getColorSpaceimpl(ColorSchemeKt.fromToken(colorScheme, colorSchemeKeyTokens3)));
        SliderColors sliderColors2 = new SliderColors(fromToken, fromToken2, fromToken3, fromToken4, fromToken5, m464compositeOverOWjLjI, Color2, Color3, Color4, Color5, null);
        colorScheme.defaultSliderColorsCached = sliderColors2;
        return sliderColors2;
    }

    /* JADX WARN: Removed duplicated region for block: B:118:0x01e9  */
    /* renamed from: DrawTrack-J0vdD74, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void m294DrawTrackJ0vdD74(final androidx.compose.material3.SliderState r23, final float r24, final androidx.compose.ui.Modifier r25, final boolean r26, final androidx.compose.material3.SliderColors r27, final kotlin.jvm.functions.Function2 r28, final kotlin.jvm.functions.Function3 r29, final float r30, final float r31, final boolean r32, androidx.compose.runtime.Composer r33, final int r34, final int r35) {
        /*
            Method dump skipped, instructions count: 532
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SliderDefaults.m294DrawTrackJ0vdD74(androidx.compose.material3.SliderState, float, androidx.compose.ui.Modifier, boolean, androidx.compose.material3.SliderColors, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function3, float, float, boolean, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00c1  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:39:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00d7  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x011b  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0133 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0172  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x012c  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00fe  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0069  */
    /* renamed from: Thumb-9LiSoMs, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void m295Thumb9LiSoMs(final androidx.compose.foundation.interaction.MutableInteractionSource r18, androidx.compose.ui.Modifier r19, androidx.compose.material3.SliderColors r20, boolean r21, long r22, androidx.compose.runtime.Composer r24, final int r25, final int r26) {
        /*
            Method dump skipped, instructions count: 418
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SliderDefaults.m295Thumb9LiSoMs(androidx.compose.foundation.interaction.MutableInteractionSource, androidx.compose.ui.Modifier, androidx.compose.material3.SliderColors, boolean, long, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:104:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0077  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x007d  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x01ce  */
    /* JADX WARN: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0154 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x01a5  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01c2  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01a8  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x011d  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x00bd  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x009e  */
    /* renamed from: Thumb-HwbPF3A, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void m296ThumbHwbPF3A(final androidx.compose.foundation.interaction.MutableInteractionSource r19, final androidx.compose.material3.SliderState r20, androidx.compose.ui.Modifier r21, androidx.compose.material3.SliderColors r22, boolean r23, long r24, androidx.compose.runtime.Composer r26, final int r27, final int r28) {
        /*
            Method dump skipped, instructions count: 476
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SliderDefaults.m296ThumbHwbPF3A(androidx.compose.foundation.interaction.MutableInteractionSource, androidx.compose.material3.SliderState, androidx.compose.ui.Modifier, androidx.compose.material3.SliderColors, boolean, long, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:92:0x01af, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L142;
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x01c6  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x01cb  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x004e  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0087  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00a5  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00c7  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0127  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0232  */
    /* JADX WARN: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01d9  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x016c  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01c1  */
    /* renamed from: Track-4EFweAY, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void m297Track4EFweAY(final androidx.compose.material3.SliderState r27, androidx.compose.ui.Modifier r28, boolean r29, androidx.compose.material3.SliderColors r30, kotlin.jvm.functions.Function2 r31, kotlin.jvm.functions.Function3 r32, float r33, float r34, androidx.compose.runtime.Composer r35, final int r36, final int r37) {
        /*
            Method dump skipped, instructions count: 574
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SliderDefaults.m297Track4EFweAY(androidx.compose.material3.SliderState, androidx.compose.ui.Modifier, boolean, androidx.compose.material3.SliderColors, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function3, float, float, androidx.compose.runtime.Composer, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:97:0x01d3, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L155;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x01ec  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x01f1  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x01fe  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x01e6  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0115  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x025c  */
    /* JADX WARN: Removed duplicated region for block: B:56:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x020a  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0245  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0193  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01a2  */
    /* renamed from: Track-mnvyFg4, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void m298TrackmnvyFg4(final androidx.compose.material3.SliderState r28, final float r29, androidx.compose.ui.Modifier r30, boolean r31, androidx.compose.material3.SliderColors r32, kotlin.jvm.functions.Function2 r33, kotlin.jvm.functions.Function3 r34, float r35, float r36, androidx.compose.runtime.Composer r37, final int r38, final int r39) {
        /*
            Method dump skipped, instructions count: 618
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SliderDefaults.m298TrackmnvyFg4(androidx.compose.material3.SliderState, float, androidx.compose.ui.Modifier, boolean, androidx.compose.material3.SliderColors, kotlin.jvm.functions.Function2, kotlin.jvm.functions.Function3, float, float, androidx.compose.runtime.Composer, int, int):void");
    }
}
