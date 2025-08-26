package androidx.compose.ui.text;

import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.graphics.drawscope.Fill;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.intl.AndroidLocaleDelegateAPI24;
import androidx.compose.ui.text.intl.Locale;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.text.intl.PlatformLocaleKt;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.Hyphens;
import androidx.compose.ui.text.style.LineBreak;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextDirection;
import androidx.compose.ui.text.style.TextForegroundStyle;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.text.style.TextIndent;
import androidx.compose.ui.text.style.TextMotion;
import androidx.compose.ui.unit.LayoutDirection;
import androidx.compose.ui.unit.TextUnit;
import java.util.ArrayList;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class TextStyleKt {

    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LayoutDirection.values().length];
            try {
                iArr[LayoutDirection.Ltr.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LayoutDirection.Rtl.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final TextStyle resolveDefaults(TextStyle textStyle, LayoutDirection layoutDirection) {
        int i;
        int i2;
        float f;
        TextForegroundStyle textForegroundStyle;
        long j;
        FontWeight fontWeight;
        int i3;
        SpanStyle spanStyle = textStyle.spanStyle;
        TextForegroundStyle textForegroundStyle2 = SpanStyleKt.DefaultColorForegroundStyle;
        TextForegroundStyle textForegroundStyle3 = spanStyle.textForegroundStyle;
        SpanStyleKt$resolveSpanStyleDefaults$1 spanStyleKt$resolveSpanStyleDefaults$1 = SpanStyleKt$resolveSpanStyleDefaults$1.INSTANCE;
        textForegroundStyle3.getClass();
        if (textForegroundStyle3.equals(TextForegroundStyle.Unspecified.INSTANCE)) {
            textForegroundStyle3 = (TextForegroundStyle) spanStyleKt$resolveSpanStyleDefaults$1.invoke();
        }
        TextForegroundStyle textForegroundStyle4 = textForegroundStyle3;
        TextUnit.Companion companion = TextUnit.Companion;
        long j2 = spanStyle.fontSize;
        if ((j2 & 1095216660480L) == 0) {
            j2 = SpanStyleKt.DefaultFontSize;
        }
        long j3 = j2;
        FontWeight fontWeight2 = spanStyle.fontWeight;
        if (fontWeight2 == null) {
            FontWeight.Companion.getClass();
            fontWeight2 = FontWeight.Normal;
        }
        FontWeight fontWeight3 = fontWeight2;
        FontStyle fontStyle = spanStyle.fontStyle;
        if (fontStyle != null) {
            i = fontStyle.value;
        } else {
            FontStyle.Companion.getClass();
            i = 0;
        }
        FontStyle fontStyleM766boximpl = FontStyle.m766boximpl(i);
        FontSynthesis fontSynthesis = spanStyle.fontSynthesis;
        if (fontSynthesis != null) {
            i2 = fontSynthesis.value;
        } else {
            FontSynthesis.Companion.getClass();
            i2 = FontSynthesis.All;
        }
        FontSynthesis fontSynthesisM768boximpl = FontSynthesis.m768boximpl(i2);
        FontFamily fontFamily = spanStyle.fontFamily;
        if (fontFamily == null) {
            FontFamily.Companion.getClass();
            fontFamily = FontFamily.Default;
        }
        FontFamily fontFamily2 = fontFamily;
        String str = spanStyle.fontFeatureSettings;
        if (str == null) {
            str = "";
        }
        String str2 = str;
        long j4 = spanStyle.letterSpacing;
        if ((j4 & 1095216660480L) == 0) {
            j4 = SpanStyleKt.DefaultLetterSpacing;
        }
        BaselineShift baselineShift = spanStyle.baselineShift;
        if (baselineShift != null) {
            f = baselineShift.multiplier;
        } else {
            BaselineShift.Companion.getClass();
            f = 0.0f;
        }
        BaselineShift baselineShiftM793boximpl = BaselineShift.m793boximpl(f);
        TextGeometricTransform textGeometricTransform = spanStyle.textGeometricTransform;
        if (textGeometricTransform == null) {
            TextGeometricTransform.Companion.getClass();
            textGeometricTransform = TextGeometricTransform.None;
        }
        TextGeometricTransform textGeometricTransform2 = textGeometricTransform;
        LocaleList localeList = spanStyle.localeList;
        if (localeList == null) {
            LocaleList.Companion.getClass();
            AndroidLocaleDelegateAPI24 androidLocaleDelegateAPI24 = PlatformLocaleKt.platformLocaleDelegate;
            androidLocaleDelegateAPI24.getClass();
            android.os.LocaleList localeList2 = android.os.LocaleList.getDefault();
            i3 = 1;
            synchronized (androidLocaleDelegateAPI24.lock) {
                textForegroundStyle = textForegroundStyle4;
                LocaleList localeList3 = androidLocaleDelegateAPI24.lastLocaleList;
                if (localeList3 == null || localeList2 != androidLocaleDelegateAPI24.lastPlatformLocaleList) {
                    int size = localeList2.size();
                    j = j3;
                    ArrayList arrayList = new ArrayList(size);
                    int i4 = 0;
                    while (i4 < size) {
                        arrayList.add(new Locale(localeList2.get(i4)));
                        i4++;
                        size = size;
                        fontWeight3 = fontWeight3;
                    }
                    fontWeight = fontWeight3;
                    LocaleList localeList4 = new LocaleList(arrayList);
                    androidLocaleDelegateAPI24.lastPlatformLocaleList = localeList2;
                    androidLocaleDelegateAPI24.lastLocaleList = localeList4;
                    localeList = localeList4;
                } else {
                    fontWeight = fontWeight3;
                    localeList = localeList3;
                    j = j3;
                }
            }
        } else {
            textForegroundStyle = textForegroundStyle4;
            j = j3;
            fontWeight = fontWeight3;
            i3 = 1;
        }
        LocaleList localeList5 = localeList;
        long j5 = spanStyle.background;
        if (j5 == 16) {
            j5 = SpanStyleKt.DefaultBackgroundColor;
        }
        TextDecoration textDecoration = spanStyle.textDecoration;
        if (textDecoration == null) {
            TextDecoration.Companion.getClass();
            textDecoration = TextDecoration.None;
        }
        Shadow shadow = spanStyle.shadow;
        if (shadow == null) {
            Shadow.Companion.getClass();
            shadow = Shadow.None;
        }
        DrawStyle drawStyle = spanStyle.drawStyle;
        if (drawStyle == null) {
            drawStyle = Fill.INSTANCE;
        }
        FontWeight fontWeight4 = fontWeight;
        Shadow shadow2 = shadow;
        SpanStyle spanStyle2 = new SpanStyle(textForegroundStyle, j, fontWeight4, fontStyleM766boximpl, fontSynthesisM768boximpl, fontFamily2, str2, j4, baselineShiftM793boximpl, textGeometricTransform2, localeList5, j5, textDecoration, shadow2, spanStyle.platformStyle, drawStyle, (DefaultConstructorMarker) null);
        int i5 = ParagraphStyleKt.$r8$clinit;
        ParagraphStyle paragraphStyle = textStyle.paragraphStyle;
        int i6 = paragraphStyle.textAlign;
        TextAlign.Companion.getClass();
        int i7 = i6 == TextAlign.Unspecified ? TextAlign.Start : paragraphStyle.textAlign;
        TextDirection.Companion.getClass();
        int i8 = TextDirection.Content;
        int i9 = paragraphStyle.textDirection;
        if (i9 == i8) {
            int i10 = WhenMappings.$EnumSwitchMapping$0[layoutDirection.ordinal()];
            if (i10 == i3) {
                i9 = TextDirection.ContentOrLtr;
            } else {
                if (i10 != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                i9 = TextDirection.ContentOrRtl;
            }
        } else if (i9 == TextDirection.Unspecified) {
            int i11 = WhenMappings.$EnumSwitchMapping$0[layoutDirection.ordinal()];
            if (i11 == 1) {
                i9 = TextDirection.Ltr;
            } else {
                if (i11 != 2) {
                    throw new NoWhenBranchMatchedException();
                }
                i9 = TextDirection.Rtl;
            }
        }
        int i12 = i9;
        long j6 = paragraphStyle.lineHeight;
        if ((j6 & 1095216660480L) == 0) {
            j6 = ParagraphStyleKt.DefaultLineHeight;
        }
        long j7 = j6;
        TextIndent textIndent = paragraphStyle.textIndent;
        if (textIndent == null) {
            TextIndent.Companion.getClass();
            textIndent = TextIndent.None;
        }
        TextIndent textIndent2 = textIndent;
        LineBreak.Companion.getClass();
        int i13 = paragraphStyle.lineBreak;
        if (i13 == 0) {
            i13 = LineBreak.Simple;
        }
        int i14 = i13;
        Hyphens.Companion.getClass();
        int i15 = Hyphens.Unspecified;
        int i16 = paragraphStyle.hyphens;
        if (i16 == i15) {
            i16 = Hyphens.None;
        }
        int i17 = i16;
        TextMotion textMotion = paragraphStyle.textMotion;
        if (textMotion == null) {
            TextMotion.Companion.getClass();
            textMotion = TextMotion.Static;
        }
        return new TextStyle(spanStyle2, new ParagraphStyle(i7, i12, j7, textIndent2, paragraphStyle.platformStyle, paragraphStyle.lineHeightStyle, i14, i17, textMotion, (DefaultConstructorMarker) null), textStyle.platformStyle);
    }
}
