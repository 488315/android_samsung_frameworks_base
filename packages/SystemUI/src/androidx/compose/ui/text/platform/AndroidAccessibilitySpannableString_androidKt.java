package androidx.compose.ui.text.platform;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.TtsSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.AnnotatedStringKt;
import androidx.compose.ui.text.LinkAnnotation;
import androidx.compose.ui.text.SpanStyle;
import androidx.compose.ui.text.TtsAnnotation;
import androidx.compose.ui.text.UrlAnnotation;
import androidx.compose.ui.text.VerbatimTtsAnnotation;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontFamilyResolverImpl;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.font.GenericFontFamily;
import androidx.compose.ui.text.platform.extensions.SpannableExtensions_androidKt;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextForegroundStyle;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.unit.Density;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.WeakHashMap;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ULong;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class AndroidAccessibilitySpannableString_androidKt {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [kotlin.collections.EmptyList] */
    /* JADX WARN: Type inference failed for: r5v1, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r5v5, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r5v6, types: [java.util.ArrayList] */
    public static final SpannableString toAccessibilitySpannableString(AnnotatedString annotatedString, Density density, FontFamily.Resolver resolver, URLSpanCache uRLSpanCache) {
        Collection arrayList;
        int i;
        EmptyList emptyList;
        int i2;
        int i3;
        int i4;
        SpannableString spannableString = new SpannableString(annotatedString.text);
        List list = annotatedString.spanStylesOrNull;
        if (list != null) {
            int size = list.size();
            for (int i5 = 0; i5 < size; i5++) {
                AnnotatedString.Range range = (AnnotatedString.Range) list.get(i5);
                SpanStyle spanStyle = (SpanStyle) range.item;
                long jMo794getColor0d7_KjU = spanStyle.textForegroundStyle.mo794getColor0d7_KjU();
                TextForegroundStyle textForegroundStyleM812from8_81llA = spanStyle.textForegroundStyle;
                long jMo794getColor0d7_KjU2 = textForegroundStyleM812from8_81llA.mo794getColor0d7_KjU();
                Color.Companion companion = Color.Companion;
                if (!ULong.m3446equalsimpl0(jMo794getColor0d7_KjU, jMo794getColor0d7_KjU2)) {
                    TextForegroundStyle.Companion.getClass();
                    textForegroundStyleM812from8_81llA = TextForegroundStyle.Companion.m812from8_81llA(jMo794getColor0d7_KjU);
                }
                SpanStyle spanStyle2 = new SpanStyle(textForegroundStyleM812from8_81llA, spanStyle.fontSize, spanStyle.fontWeight, spanStyle.fontStyle, spanStyle.fontSynthesis, (FontFamily) null, spanStyle.fontFeatureSettings, spanStyle.letterSpacing, spanStyle.baselineShift, spanStyle.textGeometricTransform, spanStyle.localeList, spanStyle.background, spanStyle.textDecoration, spanStyle.shadow, spanStyle.platformStyle, spanStyle.drawStyle, (DefaultConstructorMarker) null);
                long jMo794getColor0d7_KjU3 = spanStyle2.textForegroundStyle.mo794getColor0d7_KjU();
                int i6 = range.start;
                int i7 = range.end;
                SpannableExtensions_androidKt.m789setColorRPmYEkk(spannableString, jMo794getColor0d7_KjU3, i6, i7);
                SpannableExtensions_androidKt.m790setFontSizeKmRG4DE(spannableString, spanStyle2.fontSize, density, i6, i7);
                FontStyle fontStyle = spanStyle2.fontStyle;
                FontWeight fontWeight = spanStyle2.fontWeight;
                if (fontWeight != null || fontStyle != null) {
                    if (fontWeight == null) {
                        FontWeight.Companion.getClass();
                        fontWeight = FontWeight.Normal;
                    }
                    if (fontStyle != null) {
                        i2 = fontStyle.value;
                    } else {
                        FontStyle.Companion.getClass();
                        i2 = 0;
                    }
                    FontWeight.Companion.getClass();
                    boolean z = fontWeight.compareTo(FontWeight.W600) >= 0;
                    FontStyle.Companion.getClass();
                    boolean z2 = i2 == FontStyle.Italic;
                    spannableString.setSpan(new StyleSpan((z2 && z) ? 3 : z ? 1 : z2 ? 2 : 0), i6, i7, 33);
                }
                FontFamily fontFamily = spanStyle2.fontFamily;
                if (fontFamily != null) {
                    if (fontFamily instanceof GenericFontFamily) {
                        spannableString.setSpan(new TypefaceSpan(((GenericFontFamily) fontFamily).name), i6, i7, 33);
                    } else {
                        FontSynthesis fontSynthesis = spanStyle2.fontSynthesis;
                        if (fontSynthesis != null) {
                            i4 = fontSynthesis.value;
                        } else {
                            FontSynthesis.Companion.getClass();
                            i4 = FontSynthesis.All;
                        }
                        FontWeight.Companion.getClass();
                        FontWeight fontWeight2 = FontWeight.Normal;
                        FontStyle.Companion.getClass();
                        Typeface typeface = (Typeface) ((FontFamilyResolverImpl) resolver).m764resolveDPcqOEQ(fontFamily, fontWeight2, 0, i4).getValue();
                        Api28Impl.INSTANCE.getClass();
                        spannableString.setSpan(new TypefaceSpan(typeface), i6, i7, 33);
                    }
                }
                TextDecoration textDecoration = spanStyle2.textDecoration;
                if (textDecoration != null) {
                    TextDecoration.Companion.getClass();
                    if (textDecoration.contains(TextDecoration.Underline)) {
                        i3 = 33;
                        spannableString.setSpan(new UnderlineSpan(), i6, i7, 33);
                    } else {
                        i3 = 33;
                    }
                    if (textDecoration.contains(TextDecoration.LineThrough)) {
                        spannableString.setSpan(new StrikethroughSpan(), i6, i7, i3);
                    }
                } else {
                    i3 = 33;
                }
                TextGeometricTransform textGeometricTransform = spanStyle2.textGeometricTransform;
                if (textGeometricTransform != null) {
                    spannableString.setSpan(new ScaleXSpan(textGeometricTransform.scaleX), i6, i7, i3);
                }
                SpannableExtensions_androidKt.setLocaleList(spannableString, spanStyle2.localeList, i6, i7);
                long j = spanStyle2.background;
                if (j != 16) {
                    SpannableExtensions_androidKt.setSpan(spannableString, new BackgroundColorSpan(ColorKt.m469toArgb8_81llA(j)), i6, i7);
                }
            }
        }
        int length = annotatedString.text.length();
        List list2 = annotatedString.annotations;
        if (list2 != null) {
            arrayList = new ArrayList(list2.size());
            int size2 = list2.size();
            for (int i8 = 0; i8 < size2; i8++) {
                Object obj = list2.get(i8);
                AnnotatedString.Range range2 = (AnnotatedString.Range) obj;
                if ((range2.item instanceof TtsAnnotation) && AnnotatedStringKt.intersect(0, length, range2.start, range2.end)) {
                    arrayList.add(obj);
                }
            }
        } else {
            arrayList = EmptyList.INSTANCE;
        }
        int size3 = arrayList.size();
        for (int i9 = 0; i9 < size3; i9++) {
            AnnotatedString.Range range3 = (AnnotatedString.Range) arrayList.get(i9);
            TtsAnnotation ttsAnnotation = (TtsAnnotation) range3.item;
            if (!(ttsAnnotation instanceof VerbatimTtsAnnotation)) {
                throw new NoWhenBranchMatchedException();
            }
            spannableString.setSpan(new TtsSpan.VerbatimBuilder(((VerbatimTtsAnnotation) ttsAnnotation).verbatim).build(), range3.start, range3.end, 33);
        }
        int length2 = annotatedString.text.length();
        List list3 = annotatedString.annotations;
        if (list3 != null) {
            ?? arrayList2 = new ArrayList(list3.size());
            int size4 = list3.size();
            for (int i10 = 0; i10 < size4; i10++) {
                Object obj2 = list3.get(i10);
                AnnotatedString.Range range4 = (AnnotatedString.Range) obj2;
                if ((range4.item instanceof UrlAnnotation) && AnnotatedStringKt.intersect(0, length2, range4.start, range4.end)) {
                    arrayList2.add(obj2);
                }
            }
            i = 0;
            emptyList = arrayList2;
        } else {
            i = 0;
            emptyList = EmptyList.INSTANCE;
        }
        int size5 = emptyList.size();
        for (int i11 = i; i11 < size5; i11++) {
            AnnotatedString.Range range5 = (AnnotatedString.Range) emptyList.get(i11);
            UrlAnnotation urlAnnotation = (UrlAnnotation) range5.item;
            WeakHashMap weakHashMap = uRLSpanCache.spansByAnnotation;
            Object uRLSpan = weakHashMap.get(urlAnnotation);
            if (uRLSpan == null) {
                uRLSpan = new URLSpan(urlAnnotation.url);
                weakHashMap.put(urlAnnotation, uRLSpan);
            }
            spannableString.setSpan((URLSpan) uRLSpan, range5.start, range5.end, 33);
        }
        List linkAnnotations = annotatedString.getLinkAnnotations(annotatedString.text.length());
        int size6 = linkAnnotations.size();
        for (int i12 = i; i12 < size6; i12++) {
            AnnotatedString.Range range6 = (AnnotatedString.Range) linkAnnotations.get(i12);
            int i13 = range6.start;
            int i14 = range6.end;
            if (i13 != i14) {
                Object obj3 = range6.item;
                LinkAnnotation linkAnnotation = (LinkAnnotation) obj3;
                if (linkAnnotation instanceof LinkAnnotation.Url) {
                    linkAnnotation.getClass();
                    AnnotatedString.Range range7 = new AnnotatedString.Range((LinkAnnotation.Url) obj3, i13, i14);
                    WeakHashMap weakHashMap2 = uRLSpanCache.urlSpansByAnnotation;
                    Object uRLSpan2 = weakHashMap2.get(range7);
                    if (uRLSpan2 == null) {
                        uRLSpan2 = new URLSpan(((LinkAnnotation.Url) range7.item).url);
                        weakHashMap2.put(range7, uRLSpan2);
                    }
                    spannableString.setSpan((URLSpan) uRLSpan2, i13, i14, 33);
                } else {
                    WeakHashMap weakHashMap3 = uRLSpanCache.linkSpansWithListenerByAnnotation;
                    Object composeClickableSpan = weakHashMap3.get(range6);
                    if (composeClickableSpan == null) {
                        composeClickableSpan = new ComposeClickableSpan(linkAnnotation);
                        weakHashMap3.put(range6, composeClickableSpan);
                    }
                    spannableString.setSpan((ClickableSpan) composeClickableSpan, i13, i14, 33);
                }
            }
        }
        return spannableString;
    }
}
