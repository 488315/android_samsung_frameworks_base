package androidx.compose.ui.text;

import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextForegroundStyle;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.unit.TextUnit;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.util.MathHelpersKt;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
        DefaultColorForegroundStyle = TextForegroundStyle.Companion.m812from8_81llA(j);
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0057  */
    /* renamed from: fastMerge-dSHsh3o, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final SpanStyle m743fastMergedSHsh3o(SpanStyle spanStyle, long j, Brush brush, float f, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, PlatformSpanStyle platformSpanStyle, DrawStyle drawStyle) {
        BaselineShift baselineShift2;
        LocaleList localeList2;
        Shadow shadow2;
        PlatformSpanStyle platformSpanStyle2;
        DrawStyle drawStyle2;
        TextForegroundStyle textForegroundStyleM812from8_81llA;
        TextGeometricTransform textGeometricTransform2;
        long j5;
        long j6;
        FontWeight fontWeight2 = fontWeight;
        FontStyle fontStyle2 = fontStyle;
        FontSynthesis fontSynthesis2 = fontSynthesis;
        FontFamily fontFamily2 = fontFamily;
        String str2 = str;
        long j7 = j3;
        TextUnit.Companion companion = TextUnit.Companion;
        long j8 = j2 & 1095216660480L;
        if ((j8 == 0) || TextUnit.m868equalsimpl0(j2, spanStyle.fontSize)) {
            if (brush != null || j == 16) {
                if ((fontStyle2 == null || fontStyle2.equals(spanStyle.fontStyle)) && ((fontWeight2 == null || fontWeight2.equals(spanStyle.fontWeight)) && ((fontFamily2 == null || fontFamily2 == spanStyle.fontFamily) && (((j7 & 1095216660480L) == 0 || TextUnit.m868equalsimpl0(j7, spanStyle.letterSpacing)) && ((textDecoration == null || textDecoration.equals(spanStyle.textDecoration)) && Intrinsics.areEqual(brush, spanStyle.textForegroundStyle.getBrush()) && ((brush == null || f == spanStyle.textForegroundStyle.getAlpha()) && ((fontSynthesis2 == null || fontSynthesis2.equals(spanStyle.fontSynthesis)) && (str2 == null || str2.equals(spanStyle.fontFeatureSettings))))))))) {
                    if (baselineShift != null) {
                        baselineShift2 = baselineShift;
                        if (baselineShift2.equals(spanStyle.baselineShift)) {
                        }
                    } else {
                        baselineShift2 = baselineShift;
                    }
                    if (textGeometricTransform == null || textGeometricTransform.equals(spanStyle.textGeometricTransform)) {
                        if (localeList != null) {
                            localeList2 = localeList;
                            if (localeList2.equals(spanStyle.localeList)) {
                            }
                        } else {
                            localeList2 = localeList;
                        }
                        if (j4 != 16) {
                            Color.Companion companion2 = Color.Companion;
                            if (ULong.m3447equalsimpl0(j4, spanStyle.background)) {
                            }
                            platformSpanStyle2 = platformSpanStyle;
                            drawStyle2 = drawStyle;
                        }
                        shadow2 = shadow;
                        if (shadow2 == null || shadow2.equals(spanStyle.shadow)) {
                            platformSpanStyle2 = platformSpanStyle;
                            if (platformSpanStyle2 == null || platformSpanStyle2.equals(spanStyle.platformStyle)) {
                                drawStyle2 = drawStyle;
                                if (drawStyle2 == null || drawStyle2.equals(spanStyle.drawStyle)) {
                                    return spanStyle;
                                }
                            }
                        } else {
                            platformSpanStyle2 = platformSpanStyle;
                        }
                        drawStyle2 = drawStyle;
                    }
                    shadow2 = shadow;
                    platformSpanStyle2 = platformSpanStyle;
                    drawStyle2 = drawStyle;
                } else {
                    baselineShift2 = baselineShift;
                }
                localeList2 = localeList;
                shadow2 = shadow;
                platformSpanStyle2 = platformSpanStyle;
                drawStyle2 = drawStyle;
            } else {
                long jMo794getColor0d7_KjU = spanStyle.textForegroundStyle.mo794getColor0d7_KjU();
                Color.Companion companion3 = Color.Companion;
                if (ULong.m3447equalsimpl0(j, jMo794getColor0d7_KjU)) {
                }
            }
        }
        if (brush != null) {
            TextForegroundStyle.Companion.getClass();
            textForegroundStyleM812from8_81llA = TextForegroundStyle.Companion.from(f, brush);
        } else {
            TextForegroundStyle.Companion.getClass();
            textForegroundStyleM812from8_81llA = TextForegroundStyle.Companion.m812from8_81llA(j);
        }
        TextForegroundStyle textForegroundStyleMerge = spanStyle.textForegroundStyle.merge(textForegroundStyleM812from8_81llA);
        if (fontFamily2 == null) {
            fontFamily2 = spanStyle.fontFamily;
        }
        long j9 = j8 == 0 ? spanStyle.fontSize : j2;
        if (fontWeight2 == null) {
            fontWeight2 = spanStyle.fontWeight;
        }
        if (fontStyle2 == null) {
            fontStyle2 = spanStyle.fontStyle;
        }
        if (fontSynthesis2 == null) {
            fontSynthesis2 = spanStyle.fontSynthesis;
        }
        if (str2 == null) {
            str2 = spanStyle.fontFeatureSettings;
        }
        if ((j7 & 1095216660480L) == 0) {
            j7 = spanStyle.letterSpacing;
        }
        if (baselineShift2 == null) {
            baselineShift2 = spanStyle.baselineShift;
        }
        TextGeometricTransform textGeometricTransform3 = textGeometricTransform == null ? spanStyle.textGeometricTransform : textGeometricTransform;
        if (localeList2 == null) {
            localeList2 = spanStyle.localeList;
        }
        if (j4 != 16) {
            textGeometricTransform2 = textGeometricTransform3;
            j5 = j9;
            j6 = j4;
        } else {
            textGeometricTransform2 = textGeometricTransform3;
            j5 = j9;
            j6 = spanStyle.background;
        }
        TextDecoration textDecoration2 = textDecoration == null ? spanStyle.textDecoration : textDecoration;
        if (shadow2 == null) {
            shadow2 = spanStyle.shadow;
        }
        long j10 = j6;
        PlatformSpanStyle platformSpanStyle3 = spanStyle.platformStyle;
        if (platformSpanStyle3 == null) {
            platformSpanStyle3 = platformSpanStyle2;
        }
        return new SpanStyle(textForegroundStyleMerge, j5, fontWeight2, fontStyle2, fontSynthesis2, fontFamily2, str2, j7, baselineShift2, textGeometricTransform2, localeList2, j10, textDecoration2, shadow2, platformSpanStyle3, drawStyle2 == null ? spanStyle.drawStyle : drawStyle2, (DefaultConstructorMarker) null);
    }

    public static final Object lerpDiscrete(float f, Object obj, Object obj2) {
        return ((double) f) < 0.5d ? obj : obj2;
    }

    /* renamed from: lerpTextUnitInheritable-C3pnCVY, reason: not valid java name */
    public static final long m744lerpTextUnitInheritableC3pnCVY(long j, long j2, float f) {
        TextUnit.Companion companion = TextUnit.Companion;
        long j3 = j & 1095216660480L;
        if (j3 == 0 || (1095216660480L & j2) == 0) {
            return ((TextUnit) lerpDiscrete(f, TextUnit.m867boximpl(j), TextUnit.m867boximpl(j2))).packedValue;
        }
        TextUnitKt.m874checkArithmeticNB67dxo(j, j2);
        return TextUnitKt.pack(MathHelpersKt.lerp(TextUnit.m870getValueimpl(j), TextUnit.m870getValueimpl(j2), f), j3);
    }
}
