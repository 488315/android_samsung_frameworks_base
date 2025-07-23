package androidx.compose.material3;

import androidx.compose.material3.tokens.SliderTokens;
import androidx.compose.ui.unit.Dp;
import androidx.compose.ui.unit.DpKt;
import androidx.compose.ui.util.MathHelpersKt;
import kotlin.ranges.IntProgressionIterator;
import kotlin.ranges.IntRange;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SliderKt {
    public static final long ThumbSize;
    public static final float ThumbTrackGapSize;
    public static final float ThumbWidth;
    public static final float TrackHeight;
    public static final float TrackInsideCornerSize;
    public static final long VerticalThumbSize;

    static {
        SliderTokens.INSTANCE.getClass();
        TrackHeight = SliderTokens.InactiveTrackHeight;
        float f = SliderTokens.HandleWidth;
        ThumbWidth = f;
        float f2 = SliderTokens.HandleHeight;
        ThumbSize = DpKt.m838DpSizeYgX7TsA(f, f2);
        VerticalThumbSize = DpKt.m838DpSizeYgX7TsA(f2, f);
        ThumbTrackGapSize = SliderTokens.ActiveHandleLeadingSpace;
        Dp.Companion companion = Dp.Companion;
        TrackInsideCornerSize = 2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:87:0x0261, code lost:
    
        if (r15 == androidx.compose.runtime.Composer.Companion.Empty) goto L191;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:102:0x01b4  */
    /* JADX WARN: Removed duplicated region for block: B:105:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x01e0  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x01e7  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x01f8  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x021c  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0205  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x01f4  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x00f8  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x00b8  */
    /* JADX WARN: Removed duplicated region for block: B:161:0x00ab  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0096  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00d3  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0113  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x02c2  */
    /* JADX WARN: Removed duplicated region for block: B:62:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0187  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x022a  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0239  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0247  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x025c  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x02b0  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x023c  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01aa  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void Slider(final float r25, final kotlin.jvm.functions.Function1 r26, androidx.compose.ui.Modifier r27, boolean r28, kotlin.jvm.functions.Function0 r29, androidx.compose.material3.SliderColors r30, androidx.compose.foundation.interaction.MutableInteractionSource r31, int r32, kotlin.jvm.functions.Function3 r33, kotlin.jvm.functions.Function3 r34, kotlin.ranges.ClosedFloatingPointRange r35, androidx.compose.runtime.Composer r36, final int r37, final int r38, final int r39) {
        /*
            Method dump skipped, instructions count: 716
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SliderKt.Slider(float, kotlin.jvm.functions.Function1, androidx.compose.ui.Modifier, boolean, kotlin.jvm.functions.Function0, androidx.compose.material3.SliderColors, androidx.compose.foundation.interaction.MutableInteractionSource, int, kotlin.jvm.functions.Function3, kotlin.jvm.functions.Function3, kotlin.ranges.ClosedFloatingPointRange, androidx.compose.runtime.Composer, int, int, int):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:69:0x00f1, code lost:
    
        if (r7 == androidx.compose.runtime.Composer.Companion.Empty) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x01be, code lost:
    
        if (r3 == androidx.compose.runtime.Composer.Companion.Empty) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0224, code lost:
    
        if (r7 == androidx.compose.runtime.Composer.Companion.Empty) goto L109;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SliderImpl(final androidx.compose.ui.Modifier r28, androidx.compose.material3.SliderState r29, final boolean r30, final androidx.compose.foundation.interaction.MutableInteractionSource r31, final kotlin.jvm.functions.Function3 r32, final kotlin.jvm.functions.Function3 r33, androidx.compose.runtime.Composer r34, final int r35) {
        /*
            Method dump skipped, instructions count: 787
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SliderKt.SliderImpl(androidx.compose.ui.Modifier, androidx.compose.material3.SliderState, boolean, androidx.compose.foundation.interaction.MutableInteractionSource, kotlin.jvm.functions.Function3, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:118:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0198  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x01a1  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x01f3  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x0170  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x017f  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x018c  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x00b8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void VerticalSlider(final androidx.compose.material3.SliderState r19, androidx.compose.ui.Modifier r20, boolean r21, boolean r22, androidx.compose.material3.SliderColors r23, androidx.compose.foundation.interaction.MutableInteractionSource r24, kotlin.jvm.functions.Function3 r25, kotlin.jvm.functions.Function3 r26, androidx.compose.runtime.Composer r27, final int r28, final int r29) {
        /*
            Method dump skipped, instructions count: 508
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SliderKt.VerticalSlider(androidx.compose.material3.SliderState, androidx.compose.ui.Modifier, boolean, boolean, androidx.compose.material3.SliderColors, androidx.compose.foundation.interaction.MutableInteractionSource, kotlin.jvm.functions.Function3, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int, int):void");
    }

    public static final float access$snapValueToTick(float f, float f2, float f3, float[] fArr) {
        Float valueOf;
        if (fArr.length == 0) {
            valueOf = null;
        } else {
            float f4 = fArr[0];
            int length = fArr.length - 1;
            if (length == 0) {
                valueOf = Float.valueOf(f4);
            } else {
                float abs = Math.abs(MathHelpersKt.lerp(f2, f3, f4) - f);
                IntProgressionIterator it = new IntRange(1, length).iterator();
                while (it.hasNext) {
                    float f5 = fArr[it.nextInt()];
                    float abs2 = Math.abs(MathHelpersKt.lerp(f2, f3, f5) - f);
                    if (Float.compare(abs, abs2) > 0) {
                        f4 = f5;
                        abs = abs2;
                    }
                }
                valueOf = Float.valueOf(f4);
            }
        }
        return valueOf != null ? MathHelpersKt.lerp(f2, f3, valueOf.floatValue()) : f;
    }

    /* JADX WARN: Removed duplicated region for block: B:106:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x014a  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x010a  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void Slider(final androidx.compose.material3.SliderState r16, androidx.compose.ui.Modifier r17, boolean r18, androidx.compose.material3.SliderColors r19, androidx.compose.foundation.interaction.MutableInteractionSource r20, kotlin.jvm.functions.Function3 r21, kotlin.jvm.functions.Function3 r22, androidx.compose.runtime.Composer r23, final int r24, final int r25) {
        /*
            Method dump skipped, instructions count: 441
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.material3.SliderKt.Slider(androidx.compose.material3.SliderState, androidx.compose.ui.Modifier, boolean, androidx.compose.material3.SliderColors, androidx.compose.foundation.interaction.MutableInteractionSource, kotlin.jvm.functions.Function3, kotlin.jvm.functions.Function3, androidx.compose.runtime.Composer, int, int):void");
    }
}
