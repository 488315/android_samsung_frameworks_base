package com.android.systemui.media.mediaoutput.compose.widget;

import androidx.compose.material3.MaterialTheme;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.ui.geometry.OffsetKt;
import androidx.compose.ui.graphics.AndroidPath;
import androidx.compose.ui.graphics.AndroidPath_androidKt;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.StrokeCap;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import com.sec.ims.volte2.data.VolteConstants;

public final class AudioVisSeekbarDefaults {
    public static final AudioVisSeekbarDefaults INSTANCE = new AudioVisSeekbarDefaults();

    private AudioVisSeekbarDefaults() {
    }

    /* renamed from: access$drawTrack-u7Xjp3k, reason: not valid java name */
    public static final void m1975access$drawTracku7Xjp3k(AudioVisSeekbarDefaults audioVisSeekbarDefaults, DrawScope drawScope, int i, float f, float f2, float f3, double d, float f4, float f5, float f6, float f7, long j) {
        audioVisSeekbarDefaults.getClass();
        int i2 = i + 1;
        float f8 = i2 * f2;
        float f9 = f4 / 2;
        AndroidPath Path = AndroidPath_androidKt.Path();
        float f10 = -f9;
        Path.internalPath.moveTo(0.0f, f10);
        int i3 = (int) 0.0f;
        int i4 = (int) f;
        if (i3 <= i4) {
            while (true) {
                Path.internalPath.lineTo(i3, (((((float) Math.sin((f3 * r8) + f8)) * f6) - f7) * ((float) ((Math.sin(i3 * d) * (f7 / f5)) / i2))) - f9);
                if (i3 == i4) {
                    break;
                } else {
                    i3++;
                }
            }
        }
        Path.internalPath.lineTo(f, f10);
        Path.internalPath.close();
        DrawScope.m470drawPathLG529CI$default(drawScope, Path, j, null, 60);
        long Offset = OffsetKt.Offset(0.0f, 0.0f);
        long Offset2 = OffsetKt.Offset(f, 0.0f);
        StrokeCap.Companion.getClass();
        DrawScope.m468drawLineNGM6Ib0$default(drawScope, j, Offset, Offset2, f4, StrokeCap.Round, 0.0f, VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE);
    }

    public static WaveAnimationOptions animationOptions(boolean z, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1817607493);
        if ((i & 4) != 0) {
            z = true;
        }
        boolean z2 = z;
        OpaqueKey opaqueKey = ComposerKt.invocation;
        WaveAnimationOptions waveAnimationOptions = new WaveAnimationOptions(false, true, z2, 0, 8, null);
        composerImpl.end(false);
        return waveAnimationOptions;
    }

    /* renamed from: colors-q0g_0yA, reason: not valid java name */
    public static AudioVisSeekbarColors m1976colorsq0g_0yA(long j, long j2, long j3, long j4, long j5, Composer composer, int i) {
        long j6;
        long j7;
        long j8;
        long j9;
        long j10;
        long Color;
        long Color2;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(1031062057);
        if ((i & 1) != 0) {
            MaterialTheme.INSTANCE.getClass();
            j6 = MaterialTheme.getColorScheme(composerImpl).primary;
        } else {
            j6 = j;
        }
        if ((i & 2) != 0) {
            MaterialTheme.INSTANCE.getClass();
            j7 = MaterialTheme.getColorScheme(composerImpl).primary;
        } else {
            j7 = j2;
        }
        if ((i & 4) != 0) {
            MaterialTheme.INSTANCE.getClass();
            j8 = MaterialTheme.getColorScheme(composerImpl).secondary;
        } else {
            j8 = j3;
        }
        if ((i & 8) != 0) {
            MaterialTheme.INSTANCE.getClass();
            j9 = MaterialTheme.getColorScheme(composerImpl).primaryContainer;
        } else {
            j9 = j4;
        }
        if ((i & 16) != 0) {
            MaterialTheme.INSTANCE.getClass();
            j10 = MaterialTheme.getColorScheme(composerImpl).secondaryContainer;
        } else {
            j10 = j5;
        }
        MaterialTheme materialTheme = MaterialTheme.INSTANCE;
        materialTheme.getClass();
        long j11 = MaterialTheme.getColorScheme(composerImpl).secondaryContainer;
        materialTheme.getClass();
        long j12 = MaterialTheme.getColorScheme(composerImpl).secondaryContainer;
        materialTheme.getClass();
        long j13 = MaterialTheme.getColorScheme(composerImpl).secondaryContainer;
        materialTheme.getClass();
        Color = ColorKt.Color(Color.m391getRedimpl(r2), Color.m390getGreenimpl(r2), Color.m388getBlueimpl(r2), 0.5f, Color.m389getColorSpaceimpl(MaterialTheme.getColorScheme(composerImpl).primaryContainer));
        materialTheme.getClass();
        Color2 = ColorKt.Color(Color.m391getRedimpl(r1), Color.m390getGreenimpl(r1), Color.m388getBlueimpl(r1), 0.5f, Color.m389getColorSpaceimpl(MaterialTheme.getColorScheme(composerImpl).secondaryContainer));
        OpaqueKey opaqueKey = ComposerKt.invocation;
        AudioVisSeekbarColors audioVisSeekbarColors = new AudioVisSeekbarColors(j6, j7, j8, j9, j10, j11, j12, j13, Color, Color2, null);
        composerImpl.end(false);
        return audioVisSeekbarColors;
    }

    /* renamed from: Thumb-FJfuzF0, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void m1977ThumbFJfuzF0(final androidx.compose.runtime.State r23, androidx.compose.ui.Modifier r24, com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarColors r25, boolean r26, float r27, androidx.compose.runtime.Composer r28, final int r29, final int r30) {
        /*
            Method dump skipped, instructions count: 506
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarDefaults.m1977ThumbFJfuzF0(androidx.compose.runtime.State, androidx.compose.ui.Modifier, com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarColors, boolean, float, androidx.compose.runtime.Composer, int, int):void");
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void Track(final androidx.compose.material3.SliderState r30, final androidx.compose.runtime.State r31, androidx.compose.ui.Modifier r32, boolean r33, com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarColors r34, final com.android.systemui.media.mediaoutput.compose.widget.WaveAnimationOptions r35, final com.android.systemui.media.mediaoutput.compose.widget.WaveOptions r36, androidx.compose.runtime.Composer r37, final int r38, final int r39) {
        /*
            Method dump skipped, instructions count: 638
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarDefaults.Track(androidx.compose.material3.SliderState, androidx.compose.runtime.State, androidx.compose.ui.Modifier, boolean, com.android.systemui.media.mediaoutput.compose.widget.AudioVisSeekbarColors, com.android.systemui.media.mediaoutput.compose.widget.WaveAnimationOptions, com.android.systemui.media.mediaoutput.compose.widget.WaveOptions, androidx.compose.runtime.Composer, int, int):void");
    }
}
