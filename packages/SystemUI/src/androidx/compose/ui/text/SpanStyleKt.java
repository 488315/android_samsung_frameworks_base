package androidx.compose.ui.text;

import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.text.style.TextForegroundStyle;
import androidx.compose.ui.unit.TextUnit;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.util.MathHelpersKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SpanStyleKt {
    public static final long DefaultBackgroundColor;
    public static final TextForegroundStyle DefaultColorForegroundStyle;
    public static final long DefaultFontSize = TextUnitKt.getSp(14);
    public static final long DefaultLetterSpacing = TextUnitKt.getSp(0);

    static {
        Color.Companion.getClass();
        DefaultBackgroundColor = Color.Transparent;
        long j = Color.Black;
        TextForegroundStyle.Companion.getClass();
        DefaultColorForegroundStyle = TextForegroundStyle.Companion.m810from8_81llA(j);
    }

    /* JADX WARN: Code restructure failed: missing block: B:104:0x00f2, code lost:
    
        if (kotlin.ULong.m3427equalsimpl0(r41, r24.background) != false) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0055, code lost:
    
        if (kotlin.ULong.m3427equalsimpl0(r25, r14) != false) goto L21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x00c0, code lost:
    
        if (r15.equals(r24.baselineShift) != false) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x00cf, code lost:
    
        if (r39.equals(r24.textGeometricTransform) == false) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x00de, code lost:
    
        if (r14.equals(r24.localeList) != false) goto L62;
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x011c  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0139  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0145  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0151  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0159  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0163  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0170  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x017f  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0191  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0194  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0182  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0177  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0128  */
    /* renamed from: fastMerge-dSHsh3o, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final androidx.compose.ui.text.SpanStyle m741fastMergedSHsh3o(androidx.compose.ui.text.SpanStyle r24, long r25, androidx.compose.ui.graphics.Brush r27, float r28, long r29, androidx.compose.ui.text.font.FontWeight r31, androidx.compose.ui.text.font.FontStyle r32, androidx.compose.ui.text.font.FontSynthesis r33, androidx.compose.ui.text.font.FontFamily r34, java.lang.String r35, long r36, androidx.compose.ui.text.style.BaselineShift r38, androidx.compose.ui.text.style.TextGeometricTransform r39, androidx.compose.ui.text.intl.LocaleList r40, long r41, androidx.compose.ui.text.style.TextDecoration r43, androidx.compose.ui.graphics.Shadow r44, androidx.compose.ui.text.PlatformSpanStyle r45, androidx.compose.ui.graphics.drawscope.DrawStyle r46) {
        /*
            Method dump skipped, instructions count: 442
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.SpanStyleKt.m741fastMergedSHsh3o(androidx.compose.ui.text.SpanStyle, long, androidx.compose.ui.graphics.Brush, float, long, androidx.compose.ui.text.font.FontWeight, androidx.compose.ui.text.font.FontStyle, androidx.compose.ui.text.font.FontSynthesis, androidx.compose.ui.text.font.FontFamily, java.lang.String, long, androidx.compose.ui.text.style.BaselineShift, androidx.compose.ui.text.style.TextGeometricTransform, androidx.compose.ui.text.intl.LocaleList, long, androidx.compose.ui.text.style.TextDecoration, androidx.compose.ui.graphics.Shadow, androidx.compose.ui.text.PlatformSpanStyle, androidx.compose.ui.graphics.drawscope.DrawStyle):androidx.compose.ui.text.SpanStyle");
    }

    public static final Object lerpDiscrete(float f, Object obj, Object obj2) {
        return ((double) f) < 0.5d ? obj : obj2;
    }

    /* renamed from: lerpTextUnitInheritable-C3pnCVY, reason: not valid java name */
    public static final long m742lerpTextUnitInheritableC3pnCVY(long j, long j2, float f) {
        TextUnit.Companion companion = TextUnit.Companion;
        long j3 = j & 1095216660480L;
        if (j3 == 0 || (1095216660480L & j2) == 0) {
            return ((TextUnit) lerpDiscrete(f, TextUnit.m865boximpl(j), TextUnit.m865boximpl(j2))).packedValue;
        }
        TextUnitKt.m872checkArithmeticNB67dxo(j, j2);
        return TextUnitKt.pack(MathHelpersKt.lerp(TextUnit.m868getValueimpl(j), TextUnit.m868getValueimpl(j2), f), j3);
    }
}
