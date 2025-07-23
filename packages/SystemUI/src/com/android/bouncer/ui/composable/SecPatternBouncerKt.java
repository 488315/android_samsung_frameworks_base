package com.android.bouncer.ui.composable;

import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.compose.ui.geometry.Offset;
import com.android.systemui.bouncer.ui.viewmodel.PatternDotViewModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SecPatternBouncerKt {
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0369, code lost:
    
        if (r2 == androidx.compose.runtime.Composer.Companion.Empty) goto L126;
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x05c7  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x05e6  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x068f  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x061b  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0383  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0391 A[LOOP:0: B:122:0x038b->B:124:0x0391, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:129:0x031b  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x032a A[LOOP:1: B:131:0x0324->B:133:0x032a, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:138:0x02af  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x02be A[LOOP:2: B:140:0x02b8->B:142:0x02be, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0241  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x0250 A[LOOP:3: B:149:0x024a->B:151:0x0250, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:160:0x01d0 A[LOOP:4: B:158:0x01ca->B:160:0x01d0, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:165:0x0152  */
    /* JADX WARN: Removed duplicated region for block: B:169:0x0161 A[LOOP:5: B:167:0x015b->B:169:0x0161, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0699  */
    /* JADX WARN: Removed duplicated region for block: B:21:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0091  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0367  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x03be  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x04ed  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0501  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0514  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0532  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x056f  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0581  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x059a  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x05ac  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final void SecPatternBouncer(com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel r38, final boolean r39, androidx.compose.ui.Modifier r40, androidx.compose.runtime.Composer r41, final int r42, final int r43) {
        /*
            Method dump skipped, instructions count: 1703
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.bouncer.ui.composable.SecPatternBouncerKt.SecPatternBouncer(com.android.systemui.bouncer.ui.viewmodel.PatternBouncerViewModel, boolean, androidx.compose.ui.Modifier, androidx.compose.runtime.Composer, int, int):void");
    }

    public static final long pixelOffset(PatternDotViewModel patternDotViewModel, float f, float f2, float f3) {
        float f4 = f / 2;
        float f5 = (patternDotViewModel.x * f) + f4 + f2;
        float m = DrawerArrowDrawable$$ExternalSyntheticOutline0.m(patternDotViewModel.y, f, f4, f3);
        long floatToRawIntBits = (Float.floatToRawIntBits(f5) << 32) | (4294967295L & Float.floatToRawIntBits(m));
        Offset.Companion companion = Offset.Companion;
        return floatToRawIntBits;
    }
}
