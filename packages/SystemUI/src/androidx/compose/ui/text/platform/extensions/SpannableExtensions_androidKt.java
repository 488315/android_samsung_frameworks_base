package androidx.compose.ui.text.platform.extensions;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LocaleSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.graphics.ShaderBrush;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.SolidColor;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.AnnotatedStringKt;
import androidx.compose.ui.text.PlatformSpanStyle;
import androidx.compose.ui.text.SpanStyle;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.android.style.BaselineShiftSpan;
import androidx.compose.ui.text.android.style.FontFeatureSpan;
import androidx.compose.ui.text.android.style.LetterSpacingSpanEm;
import androidx.compose.ui.text.android.style.LetterSpacingSpanPx;
import androidx.compose.ui.text.android.style.ShadowSpan;
import androidx.compose.ui.text.android.style.SkewXSpan;
import androidx.compose.ui.text.android.style.TextDecorationSpan;
import androidx.compose.ui.text.android.style.TypefaceSpan;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.intl.Locale;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.text.platform.style.DrawStyleSpan;
import androidx.compose.ui.text.platform.style.ShaderBrushSpan;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextForegroundStyle;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.TextUnit;
import androidx.compose.ui.unit.TextUnitType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.math.MathKt__MathJVMKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SpannableExtensions_androidKt {
    /* renamed from: resolveBulletTextUnitToPx-o2QH7mI, reason: not valid java name */
    public static final float m785resolveBulletTextUnitToPxo2QH7mI(long j, float f, Density density) {
        TextUnit.Companion.getClass();
        if (TextUnit.m866equalsimpl0(j, TextUnit.Unspecified)) {
            return f;
        }
        long m867getTypeUIouoOA = TextUnit.m867getTypeUIouoOA(j);
        TextUnitType.Companion companion = TextUnitType.Companion;
        companion.getClass();
        if (TextUnitType.m874equalsimpl0(m867getTypeUIouoOA, TextUnitType.Sp)) {
            return density.mo56toPxR2X_6o(j);
        }
        companion.getClass();
        if (TextUnitType.m874equalsimpl0(m867getTypeUIouoOA, TextUnitType.Em)) {
            return TextUnit.m868getValueimpl(j) * f;
        }
        return Float.NaN;
    }

    /* renamed from: resolveLineHeightInPx-o2QH7mI, reason: not valid java name */
    public static final float m786resolveLineHeightInPxo2QH7mI(long j, float f, Density density) {
        float m868getValueimpl;
        long m867getTypeUIouoOA = TextUnit.m867getTypeUIouoOA(j);
        TextUnitType.Companion.getClass();
        if (TextUnitType.m874equalsimpl0(m867getTypeUIouoOA, TextUnitType.Sp)) {
            if (density.getFontScale() <= 1.05d) {
                return density.mo56toPxR2X_6o(j);
            }
            m868getValueimpl = TextUnit.m868getValueimpl(j) / TextUnit.m868getValueimpl(density.mo60toSpkPz2Gy4(f));
        } else {
            if (!TextUnitType.m874equalsimpl0(m867getTypeUIouoOA, TextUnitType.Em)) {
                return Float.NaN;
            }
            m868getValueimpl = TextUnit.m868getValueimpl(j);
        }
        return m868getValueimpl * f;
    }

    /* renamed from: setColor-RPmYEkk, reason: not valid java name */
    public static final void m787setColorRPmYEkk(Spannable spannable, long j, int i, int i2) {
        if (j != 16) {
            setSpan(spannable, new ForegroundColorSpan(ColorKt.m467toArgb8_81llA(j)), i, i2);
        }
    }

    /* renamed from: setFontSize-KmRG4DE, reason: not valid java name */
    public static final void m788setFontSizeKmRG4DE(Spannable spannable, long j, Density density, int i, int i2) {
        long m867getTypeUIouoOA = TextUnit.m867getTypeUIouoOA(j);
        TextUnitType.Companion companion = TextUnitType.Companion;
        companion.getClass();
        if (TextUnitType.m874equalsimpl0(m867getTypeUIouoOA, TextUnitType.Sp)) {
            setSpan(spannable, new AbsoluteSizeSpan(MathKt__MathJVMKt.roundToInt(density.mo56toPxR2X_6o(j)), false), i, i2);
            return;
        }
        companion.getClass();
        if (TextUnitType.m874equalsimpl0(m867getTypeUIouoOA, TextUnitType.Em)) {
            setSpan(spannable, new RelativeSizeSpan(TextUnit.m868getValueimpl(j)), i, i2);
        }
    }

    public static final void setLocaleList(Spannable spannable, LocaleList localeList, int i, int i2) {
        if (localeList != null) {
            LocaleListHelperMethods.INSTANCE.getClass();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(localeList, 10));
            Iterator it = localeList.iterator();
            while (it.hasNext()) {
                arrayList.add(((Locale) it.next()).platformLocale);
            }
            java.util.Locale[] localeArr = (java.util.Locale[]) arrayList.toArray(new java.util.Locale[0]);
            setSpan(spannable, new LocaleSpan(new android.os.LocaleList((java.util.Locale[]) Arrays.copyOf(localeArr, localeArr.length))), i, i2);
        }
    }

    public static final void setSpan(Spannable spannable, Object obj, int i, int i2) {
        spannable.setSpan(obj, i, i2, 33);
    }

    public static final void setSpanStyles(final Spannable spannable, TextStyle textStyle, List list, Density density, final Function4 function4) {
        ArrayList arrayList;
        int i;
        int i2;
        int i3;
        int i4;
        ArrayList arrayList2 = new ArrayList(list.size());
        List list2 = list;
        int size = list2.size();
        int i5 = 0;
        for (int i6 = 0; i6 < size; i6++) {
            AnnotatedString.Range range = (AnnotatedString.Range) list.get(i6);
            Object obj = range.item;
            if (obj instanceof SpanStyle) {
                SpanStyle spanStyle = (SpanStyle) obj;
                if (spanStyle.fontFamily != null || spanStyle.fontStyle != null || spanStyle.fontWeight != null || ((SpanStyle) obj).fontSynthesis != null) {
                    arrayList2.add(range);
                }
            }
        }
        SpanStyle spanStyle2 = textStyle.spanStyle;
        FontFamily fontFamily = spanStyle2.fontFamily;
        SpanStyle spanStyle3 = ((fontFamily != null || spanStyle2.fontStyle != null || spanStyle2.fontWeight != null) || spanStyle2.fontSynthesis != null) ? new SpanStyle(0L, 0L, spanStyle2.fontWeight, spanStyle2.fontStyle, spanStyle2.fontSynthesis, fontFamily, (String) null, 0L, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0L, (TextDecoration) null, (Shadow) null, (PlatformSpanStyle) null, (DrawStyle) null, 65475, (DefaultConstructorMarker) null) : null;
        Function3 function3 = new Function3() { // from class: androidx.compose.ui.text.platform.extensions.SpannableExtensions_androidKt$setFontAttributes$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(3);
            }

            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj2, Object obj3, Object obj4) {
                int i7;
                int i8;
                SpanStyle spanStyle4 = (SpanStyle) obj2;
                int intValue = ((Number) obj3).intValue();
                int intValue2 = ((Number) obj4).intValue();
                Spannable spannable2 = spannable;
                Function4 function42 = function4;
                FontFamily fontFamily2 = spanStyle4.fontFamily;
                FontWeight fontWeight = spanStyle4.fontWeight;
                if (fontWeight == null) {
                    FontWeight.Companion.getClass();
                    fontWeight = FontWeight.Normal;
                }
                FontStyle fontStyle = spanStyle4.fontStyle;
                if (fontStyle != null) {
                    i7 = fontStyle.value;
                } else {
                    FontStyle.Companion.getClass();
                    i7 = 0;
                }
                FontStyle m764boximpl = FontStyle.m764boximpl(i7);
                FontSynthesis fontSynthesis = spanStyle4.fontSynthesis;
                if (fontSynthesis != null) {
                    i8 = fontSynthesis.value;
                } else {
                    FontSynthesis.Companion.getClass();
                    i8 = FontSynthesis.All;
                }
                spannable2.setSpan(new TypefaceSpan((Typeface) function42.invoke(fontFamily2, fontWeight, m764boximpl, FontSynthesis.m766boximpl(i8))), intValue, intValue2, 33);
                return Unit.INSTANCE;
            }
        };
        if (arrayList2.size() > 1) {
            int size2 = arrayList2.size();
            int i7 = size2 * 2;
            int[] iArr = new int[i7];
            int size3 = arrayList2.size();
            for (int i8 = 0; i8 < size3; i8++) {
                AnnotatedString.Range range2 = (AnnotatedString.Range) arrayList2.get(i8);
                iArr[i8] = range2.start;
                iArr[i8 + size2] = range2.end;
            }
            if (i7 > 1) {
                Arrays.sort(iArr);
            }
            if (i7 == 0) {
                throw new NoSuchElementException("Array is empty.");
            }
            int i9 = iArr[0];
            int i10 = 0;
            while (i10 < i7) {
                int i11 = iArr[i10];
                if (i11 == i9) {
                    arrayList = arrayList2;
                } else {
                    int size4 = arrayList2.size();
                    int i12 = i5;
                    SpanStyle spanStyle4 = spanStyle3;
                    while (i12 < size4) {
                        AnnotatedString.Range range3 = (AnnotatedString.Range) arrayList2.get(i12);
                        int i13 = range3.start;
                        ArrayList arrayList3 = arrayList2;
                        int i14 = range3.end;
                        if (i13 != i14 && AnnotatedStringKt.intersect(i9, i11, i13, i14)) {
                            SpanStyle spanStyle5 = (SpanStyle) range3.item;
                            if (spanStyle4 != null) {
                                spanStyle5 = spanStyle4.merge(spanStyle5);
                            }
                            spanStyle4 = spanStyle5;
                        }
                        i12++;
                        arrayList2 = arrayList3;
                    }
                    arrayList = arrayList2;
                    if (spanStyle4 != null) {
                        function3.invoke(spanStyle4, Integer.valueOf(i9), Integer.valueOf(i11));
                    }
                    i9 = i11;
                }
                i10++;
                arrayList2 = arrayList;
                i5 = 0;
            }
        } else if (!arrayList2.isEmpty()) {
            SpanStyle spanStyle6 = (SpanStyle) ((AnnotatedString.Range) arrayList2.get(0)).item;
            if (spanStyle3 != null) {
                spanStyle6 = spanStyle3.merge(spanStyle6);
            }
            function3.invoke(spanStyle6, Integer.valueOf(((AnnotatedString.Range) arrayList2.get(0)).start), Integer.valueOf(((AnnotatedString.Range) arrayList2.get(0)).end));
        }
        int size5 = list2.size();
        boolean z = false;
        for (int i15 = 0; i15 < size5; i15++) {
            AnnotatedString.Range range4 = (AnnotatedString.Range) list.get(i15);
            if ((range4.item instanceof SpanStyle) && (i3 = range4.start) >= 0 && i3 < spannable.length() && (i4 = range4.end) > i3 && i4 <= spannable.length()) {
                SpanStyle spanStyle7 = (SpanStyle) range4.item;
                BaselineShift baselineShift = spanStyle7.baselineShift;
                if (baselineShift != null) {
                    spannable.setSpan(new BaselineShiftSpan(baselineShift.multiplier), i3, i4, 33);
                }
                TextForegroundStyle textForegroundStyle = spanStyle7.textForegroundStyle;
                m787setColorRPmYEkk(spannable, textForegroundStyle.mo792getColor0d7_KjU(), i3, i4);
                Brush brush = textForegroundStyle.getBrush();
                float alpha = textForegroundStyle.getAlpha();
                if (brush != null) {
                    if (brush instanceof SolidColor) {
                        m787setColorRPmYEkk(spannable, ((SolidColor) brush).value, i3, i4);
                    } else {
                        spannable.setSpan(new ShaderBrushSpan((ShaderBrush) brush, alpha), i3, i4, 33);
                    }
                }
                TextDecoration textDecoration = spanStyle7.textDecoration;
                if (textDecoration != null) {
                    TextDecoration.Companion.getClass();
                    spannable.setSpan(new TextDecorationSpan(textDecoration.contains(TextDecoration.Underline), textDecoration.contains(TextDecoration.LineThrough)), i3, i4, 33);
                }
                m788setFontSizeKmRG4DE(spannable, spanStyle7.fontSize, density, i3, i4);
                String str = spanStyle7.fontFeatureSettings;
                if (str != null) {
                    spannable.setSpan(new FontFeatureSpan(str), i3, i4, 33);
                }
                TextGeometricTransform textGeometricTransform = spanStyle7.textGeometricTransform;
                if (textGeometricTransform != null) {
                    spannable.setSpan(new ScaleXSpan(textGeometricTransform.scaleX), i3, i4, 33);
                    spannable.setSpan(new SkewXSpan(textGeometricTransform.skewX), i3, i4, 33);
                }
                setLocaleList(spannable, spanStyle7.localeList, i3, i4);
                long j = spanStyle7.background;
                if (j != 16) {
                    setSpan(spannable, new BackgroundColorSpan(ColorKt.m467toArgb8_81llA(j)), i3, i4);
                }
                Shadow shadow = spanStyle7.shadow;
                if (shadow != null) {
                    int m467toArgb8_81llA = ColorKt.m467toArgb8_81llA(shadow.color);
                    long j2 = shadow.offset;
                    float intBitsToFloat = Float.intBitsToFloat((int) (j2 >> 32));
                    float intBitsToFloat2 = Float.intBitsToFloat((int) (j2 & 4294967295L));
                    float f = shadow.blurRadius;
                    if (f == 0.0f) {
                        f = Float.MIN_VALUE;
                    }
                    spannable.setSpan(new ShadowSpan(m467toArgb8_81llA, intBitsToFloat, intBitsToFloat2, f), i3, i4, 33);
                }
                DrawStyle drawStyle = spanStyle7.drawStyle;
                if (drawStyle != null) {
                    spannable.setSpan(new DrawStyleSpan(drawStyle), i3, i4, 33);
                }
                long m867getTypeUIouoOA = TextUnit.m867getTypeUIouoOA(spanStyle7.letterSpacing);
                TextUnitType.Companion.getClass();
                if (TextUnitType.m874equalsimpl0(m867getTypeUIouoOA, TextUnitType.Sp) || TextUnitType.m874equalsimpl0(TextUnit.m867getTypeUIouoOA(spanStyle7.letterSpacing), TextUnitType.Em)) {
                    z = true;
                }
            }
        }
        if (z) {
            int size6 = list2.size();
            for (int i16 = 0; i16 < size6; i16++) {
                AnnotatedString.Range range5 = (AnnotatedString.Range) list.get(i16);
                AnnotatedString.Annotation annotation = (AnnotatedString.Annotation) range5.item;
                if ((annotation instanceof SpanStyle) && (i = range5.start) >= 0 && i < spannable.length() && (i2 = range5.end) > i && i2 <= spannable.length()) {
                    long j3 = ((SpanStyle) annotation).letterSpacing;
                    long m867getTypeUIouoOA2 = TextUnit.m867getTypeUIouoOA(j3);
                    TextUnitType.Companion.getClass();
                    Object letterSpacingSpanPx = TextUnitType.m874equalsimpl0(m867getTypeUIouoOA2, TextUnitType.Sp) ? new LetterSpacingSpanPx(density.mo56toPxR2X_6o(j3)) : TextUnitType.m874equalsimpl0(m867getTypeUIouoOA2, TextUnitType.Em) ? new LetterSpacingSpanEm(TextUnit.m868getValueimpl(j3)) : null;
                    if (letterSpacingSpanPx != null) {
                        spannable.setSpan(letterSpacingSpanPx, i, i2, 33);
                    }
                }
            }
        }
    }
}
