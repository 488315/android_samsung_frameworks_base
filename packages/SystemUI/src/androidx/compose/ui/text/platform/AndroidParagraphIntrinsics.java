package androidx.compose.ui.text.platform;

import android.graphics.Typeface;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.LeadingMarginSpan;
import androidx.compose.runtime.State;
import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.Bullet;
import androidx.compose.ui.text.EmojiSupportMatch;
import androidx.compose.ui.text.ParagraphIntrinsics;
import androidx.compose.ui.text.ParagraphStyle;
import androidx.compose.ui.text.Placeholder;
import androidx.compose.ui.text.PlaceholderVerticalAlign;
import androidx.compose.ui.text.PlatformParagraphStyle;
import androidx.compose.ui.text.PlatformSpanStyle;
import androidx.compose.ui.text.PlatformTextStyle;
import androidx.compose.ui.text.SpanStyle;
import androidx.compose.ui.text.TextStyle;
import androidx.compose.ui.text.android.CharSequenceCharacterIterator;
import androidx.compose.ui.text.android.LayoutIntrinsics;
import androidx.compose.ui.text.android.LayoutIntrinsics$$ExternalSyntheticLambda0;
import androidx.compose.ui.text.android.style.LineHeightSpan;
import androidx.compose.ui.text.android.style.LineHeightStyleSpan;
import androidx.compose.ui.text.android.style.PlaceholderSpan;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontFamilyResolverImpl;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.font.TypefaceResult;
import androidx.compose.ui.text.intl.AndroidLocaleDelegateAPI24;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.text.intl.PlatformLocaleKt;
import androidx.compose.ui.text.platform.extensions.LocaleListHelperMethods;
import androidx.compose.ui.text.platform.extensions.PlaceholderExtensions_androidKt;
import androidx.compose.ui.text.platform.extensions.SpannableExtensions_androidKt;
import androidx.compose.ui.text.platform.style.CustomBulletSpan;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.LineHeightStyle;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextDirection;
import androidx.compose.ui.text.style.TextForegroundStyle;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.text.style.TextIndent;
import androidx.compose.ui.text.style.TextMotion;
import androidx.compose.ui.unit.Density;
import androidx.compose.ui.unit.TextUnit;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.unit.TextUnitType;
import androidx.emoji2.text.EmojiCompat;
import androidx.emoji2.text.EmojiSpan;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import kotlin.Pair;
import kotlin.ULong;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt___StringsKt;

/* loaded from: classes.dex */
public final class AndroidParagraphIntrinsics implements ParagraphIntrinsics {
    public final List annotations;
    public final CharSequence charSequence;
    public final Density density;
    public final boolean emojiCompatProcessed;
    public final FontFamily.Resolver fontFamilyResolver;
    public final LayoutIntrinsics layoutIntrinsics;
    public final List placeholders;
    public TypefaceDirtyTrackerLinkedList resolvedTypefaces;
    public final TextStyle style;
    public final String text;
    public final int textDirectionHeuristic;
    public final AndroidTextPaint textPaint;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:158:0x031b  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x006c  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0417  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x041d  */
    /* JADX WARN: Removed duplicated region for block: B:212:0x0420  */
    /* JADX WARN: Removed duplicated region for block: B:215:0x0437  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x044c  */
    /* JADX WARN: Removed duplicated region for block: B:230:0x0475  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x047b  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x048e  */
    /* JADX WARN: Removed duplicated region for block: B:258:0x04e8  */
    /* JADX WARN: Removed duplicated region for block: B:287:0x0588  */
    /* JADX WARN: Removed duplicated region for block: B:290:0x0594  */
    /* JADX WARN: Removed duplicated region for block: B:298:0x05bf  */
    /* JADX WARN: Removed duplicated region for block: B:301:0x05cb  */
    /* JADX WARN: Removed duplicated region for block: B:311:0x0618  */
    /* JADX WARN: Removed duplicated region for block: B:315:0x062b  */
    /* JADX WARN: Type inference failed for: r25v1 */
    /* JADX WARN: Type inference failed for: r25v2, types: [boolean] */
    /* JADX WARN: Type inference failed for: r25v3 */
    /* JADX WARN: Type inference failed for: r26v1 */
    /* JADX WARN: Type inference failed for: r26v2, types: [boolean] */
    /* JADX WARN: Type inference failed for: r26v3 */
    /* JADX WARN: Type inference failed for: r28v0 */
    /* JADX WARN: Type inference failed for: r28v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r28v2 */
    /* JADX WARN: Type inference failed for: r4v21, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r4v8, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r4v9, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r9v28 */
    /* JADX WARN: Type inference failed for: r9v29, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r9v30, types: [java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r9v31 */
    /* JADX WARN: Type inference failed for: r9v32, types: [android.text.Spannable, java.lang.CharSequence] */
    /* JADX WARN: Type inference failed for: r9v50 */
    /* JADX WARN: Type inference failed for: r9v51 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public AndroidParagraphIntrinsics(String str, TextStyle textStyle, List<? extends AnnotatedString.Range<? extends AnnotatedString.Annotation>> list, List<AnnotatedString.Range<Placeholder>> list2, FontFamily.Resolver resolver, Density density) {
        boolean zBooleanValue;
        Locale locale;
        int i;
        AnnotatedString.Range<? extends AnnotatedString.Annotation> range;
        int i2;
        int i3;
        int i4;
        float f;
        int i5;
        SpanStyle spanStyle;
        ?? arrayList;
        ?? spannableString;
        long j;
        TextDecoration textDecoration;
        boolean z;
        ParagraphStyle paragraphStyle;
        long j2;
        LineHeightStyle lineHeightStyle;
        float fM788resolveLineHeightInPxo2QH7mI;
        int i6;
        TextIndent textIndent;
        List list3;
        int i7;
        float f2;
        List list4;
        TextIndent textIndent2;
        float fMo57toPxR2X_6o;
        int size;
        int i8;
        int size2;
        int i9;
        int i10;
        int i11;
        Density density2;
        float fM870getValueimpl;
        PlatformParagraphStyle platformParagraphStyle;
        PlatformParagraphStyle platformParagraphStyle2;
        LocaleList localeList;
        this.text = str;
        this.style = textStyle;
        this.annotations = list;
        this.placeholders = list2;
        this.fontFamilyResolver = resolver;
        this.density = density;
        AndroidTextPaint androidTextPaint = new AndroidTextPaint(1, density.getDensity());
        this.textPaint = androidTextPaint;
        if (AndroidParagraphIntrinsics_androidKt.access$getHasEmojiCompat(textStyle)) {
            EmojiCompatStatus.INSTANCE.getClass();
            DefaultImpl defaultImpl = (DefaultImpl) EmojiCompatStatus.delegate;
            State fontLoadState = defaultImpl.loadState;
            if (fontLoadState == null) {
                if (EmojiCompat.isConfigured()) {
                    fontLoadState = defaultImpl.getFontLoadState();
                    defaultImpl.loadState = fontLoadState;
                } else {
                    fontLoadState = EmojiCompatStatus_androidKt.Falsey;
                }
            }
            zBooleanValue = ((Boolean) fontLoadState.getValue()).booleanValue();
        } else {
            zBooleanValue = false;
        }
        this.emojiCompatProcessed = zBooleanValue;
        int i12 = textStyle.paragraphStyle.textDirection;
        LocaleList localeList2 = textStyle.spanStyle.localeList;
        TextDirection.Companion.getClass();
        if (i12 != TextDirection.ContentOrLtr) {
            if (i12 != TextDirection.ContentOrRtl) {
                if (i12 == TextDirection.Ltr) {
                    i = 0;
                } else if (i12 == TextDirection.Rtl) {
                    i = 1;
                } else {
                    if (i12 != TextDirection.Content && i12 != TextDirection.Unspecified) {
                        throw new IllegalStateException("Invalid TextDirection.");
                    }
                    int layoutDirectionFromLocale = TextUtils.getLayoutDirectionFromLocale((localeList2 == null || (locale = ((androidx.compose.ui.text.intl.Locale) localeList2.localeList.get(0)).platformLocale) == null) ? Locale.getDefault() : locale);
                    i = (layoutDirectionFromLocale == 0 || layoutDirectionFromLocale != 1) ? 2 : 3;
                }
            }
        }
        this.textDirectionHeuristic = i;
        Function4 function4 = new Function4() { // from class: androidx.compose.ui.text.platform.AndroidParagraphIntrinsics$resolveTypeface$1
            {
                super(4);
            }

            @Override // kotlin.jvm.functions.Function4
            public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
                int i13 = ((FontStyle) obj3).value;
                int i14 = ((FontSynthesis) obj4).value;
                TypefaceResult typefaceResultM764resolveDPcqOEQ = ((FontFamilyResolverImpl) this.this$0.fontFamilyResolver).m764resolveDPcqOEQ((FontFamily) obj, (FontWeight) obj2, i13, i14);
                if (typefaceResultM764resolveDPcqOEQ instanceof TypefaceResult.Immutable) {
                    return (Typeface) ((TypefaceResult.Immutable) typefaceResultM764resolveDPcqOEQ).value;
                }
                TypefaceDirtyTrackerLinkedList typefaceDirtyTrackerLinkedList = new TypefaceDirtyTrackerLinkedList(typefaceResultM764resolveDPcqOEQ, this.this$0.resolvedTypefaces);
                this.this$0.resolvedTypefaces = typefaceDirtyTrackerLinkedList;
                return (Typeface) typefaceDirtyTrackerLinkedList.initial;
            }
        };
        TextMotion textMotion = textStyle.paragraphStyle.textMotion;
        if (textMotion == null) {
            TextMotion.Companion.getClass();
            textMotion = TextMotion.Static;
        }
        androidTextPaint.setFlags(textMotion.subpixelTextPositioning ? androidTextPaint.getFlags() | 128 : androidTextPaint.getFlags() & (-129));
        TextMotion.Linearity.Companion.getClass();
        int i13 = TextMotion.Linearity.Linear;
        int i14 = textMotion.linearity;
        if (i14 == i13) {
            androidTextPaint.setFlags(androidTextPaint.getFlags() | 64);
            androidTextPaint.setHinting(0);
        } else if (i14 == TextMotion.Linearity.FontHinting) {
            androidTextPaint.getFlags();
            androidTextPaint.setHinting(1);
        } else if (i14 == TextMotion.Linearity.None) {
            androidTextPaint.getFlags();
            androidTextPaint.setHinting(0);
        } else {
            androidTextPaint.getFlags();
        }
        SpanStyle spanStyle2 = textStyle.spanStyle;
        int size3 = list.size();
        int i15 = 0;
        while (true) {
            if (i15 >= size3) {
                range = null;
                break;
            }
            range = list.get(i15);
            if (range.item instanceof SpanStyle) {
                break;
            } else {
                i15++;
            }
        }
        boolean z2 = range != null;
        long jM869getTypeUIouoOA = TextUnit.m869getTypeUIouoOA(spanStyle2.fontSize);
        TextUnitType.Companion.getClass();
        boolean zM876equalsimpl0 = TextUnitType.m876equalsimpl0(jM869getTypeUIouoOA, TextUnitType.Sp);
        long j3 = spanStyle2.fontSize;
        if (zM876equalsimpl0) {
            androidTextPaint.setTextSize(density.mo57toPxR2X_6o(j3));
        } else if (TextUnitType.m876equalsimpl0(jM869getTypeUIouoOA, TextUnitType.Em)) {
            androidTextPaint.setTextSize(TextUnit.m870getValueimpl(j3) * androidTextPaint.getTextSize());
        }
        if (spanStyle2.fontFamily != null || spanStyle2.fontStyle != null || spanStyle2.fontWeight != null) {
            FontWeight fontWeight = spanStyle2.fontWeight;
            if (fontWeight == null) {
                FontWeight.Companion.getClass();
                fontWeight = FontWeight.Normal;
            }
            FontStyle fontStyle = spanStyle2.fontStyle;
            if (fontStyle != null) {
                i2 = fontStyle.value;
            } else {
                FontStyle.Companion.getClass();
                i2 = 0;
            }
            FontStyle fontStyleM766boximpl = FontStyle.m766boximpl(i2);
            FontSynthesis fontSynthesis = spanStyle2.fontSynthesis;
            if (fontSynthesis != null) {
                i3 = fontSynthesis.value;
            } else {
                FontSynthesis.Companion.getClass();
                i3 = FontSynthesis.All;
            }
            androidTextPaint.setTypeface((Typeface) function4.invoke(spanStyle2.fontFamily, fontWeight, fontStyleM766boximpl, FontSynthesis.m768boximpl(i3)));
        }
        LocaleList localeList3 = spanStyle2.localeList;
        if (localeList3 != null) {
            LocaleList.Companion.getClass();
            AndroidLocaleDelegateAPI24 androidLocaleDelegateAPI24 = PlatformLocaleKt.platformLocaleDelegate;
            androidLocaleDelegateAPI24.getClass();
            android.os.LocaleList localeList4 = android.os.LocaleList.getDefault();
            synchronized (androidLocaleDelegateAPI24.lock) {
                localeList = androidLocaleDelegateAPI24.lastLocaleList;
                if (localeList == null || localeList4 != androidLocaleDelegateAPI24.lastPlatformLocaleList) {
                    int size4 = localeList4.size();
                    ArrayList arrayList2 = new ArrayList(size4);
                    i4 = 1;
                    for (int i16 = 0; i16 < size4; i16++) {
                        arrayList2.add(new androidx.compose.ui.text.intl.Locale(localeList4.get(i16)));
                    }
                    localeList = new LocaleList(arrayList2);
                    androidLocaleDelegateAPI24.lastPlatformLocaleList = localeList4;
                    androidLocaleDelegateAPI24.lastLocaleList = localeList;
                } else {
                    i4 = 1;
                }
            }
            if (!localeList3.equals(localeList)) {
                LocaleListHelperMethods.INSTANCE.getClass();
                ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(localeList3, 10));
                Iterator it = localeList3.localeList.iterator();
                while (it.hasNext()) {
                    arrayList3.add(((androidx.compose.ui.text.intl.Locale) it.next()).platformLocale);
                }
                Locale[] localeArr = (Locale[]) arrayList3.toArray(new Locale[0]);
                androidTextPaint.setTextLocales(new android.os.LocaleList((Locale[]) Arrays.copyOf(localeArr, localeArr.length)));
            }
        } else {
            i4 = 1;
        }
        String str2 = spanStyle2.fontFeatureSettings;
        if (str2 != null && !str2.equals("")) {
            androidTextPaint.setFontFeatureSettings(str2);
        }
        TextGeometricTransform textGeometricTransform = spanStyle2.textGeometricTransform;
        if (textGeometricTransform != null) {
            TextGeometricTransform.Companion.getClass();
            if (!textGeometricTransform.equals(TextGeometricTransform.None)) {
                androidTextPaint.setTextScaleX(androidTextPaint.getTextScaleX() * textGeometricTransform.scaleX);
                androidTextPaint.setTextSkewX(androidTextPaint.getTextSkewX() + textGeometricTransform.skewX);
            }
        }
        TextForegroundStyle textForegroundStyle = spanStyle2.textForegroundStyle;
        androidTextPaint.m785setColor8_81llA(textForegroundStyle.mo794getColor0d7_KjU());
        Brush brush = textForegroundStyle.getBrush();
        Size.Companion.getClass();
        androidTextPaint.m784setBrush12SF9DM(brush, Size.Unspecified, textForegroundStyle.getAlpha());
        androidTextPaint.setShadow(spanStyle2.shadow);
        androidTextPaint.setTextDecoration(spanStyle2.textDecoration);
        androidTextPaint.setDrawStyle(spanStyle2.drawStyle);
        long j4 = spanStyle2.letterSpacing;
        long jM869getTypeUIouoOA2 = TextUnit.m869getTypeUIouoOA(j4);
        TextUnitType.Companion.getClass();
        long j5 = TextUnitType.Sp;
        if (!TextUnitType.m876equalsimpl0(jM869getTypeUIouoOA2, j5) || TextUnit.m870getValueimpl(j4) == 0.0f) {
            f = 0.0f;
            if (TextUnitType.m876equalsimpl0(TextUnit.m869getTypeUIouoOA(j4), TextUnitType.Em)) {
                androidTextPaint.setLetterSpacing(TextUnit.m870getValueimpl(j4));
            }
        } else {
            float textScaleX = androidTextPaint.getTextScaleX() * androidTextPaint.getTextSize();
            float fMo57toPxR2X_6o2 = density.mo57toPxR2X_6o(j4);
            if (textScaleX != 0.0f) {
                androidTextPaint.setLetterSpacing(fMo57toPxR2X_6o2 / textScaleX);
            }
            f = 0.0f;
        }
        int i17 = (z2 && TextUnitType.m876equalsimpl0(TextUnit.m869getTypeUIouoOA(j4), j5) && TextUnit.m870getValueimpl(j4) != f) ? i4 : 0;
        Color.Companion.getClass();
        long j6 = Color.Unspecified;
        long j7 = spanStyle2.background;
        int i18 = (ULong.m3446equalsimpl0(j7, j6) || ULong.m3446equalsimpl0(j7, Color.Transparent)) ? 0 : i4;
        BaselineShift baselineShift = spanStyle2.baselineShift;
        if (baselineShift != null) {
            BaselineShift.Companion.getClass();
            i5 = Float.compare(baselineShift.multiplier, f) == 0 ? 0 : i4;
        }
        if (i17 == 0 && i18 == 0 && i5 == 0) {
            spanStyle = null;
        } else {
            if (i17 == 0) {
                TextUnit.Companion.getClass();
                j4 = TextUnit.Unspecified;
            }
            spanStyle = new SpanStyle(0L, 0L, (FontWeight) null, (FontStyle) null, (FontSynthesis) null, (FontFamily) null, (String) null, j4, i5 != 0 ? baselineShift : null, (TextGeometricTransform) null, (LocaleList) null, i18 != 0 ? j7 : j6, (TextDecoration) null, (Shadow) null, (PlatformSpanStyle) null, (DrawStyle) null, 63103, (DefaultConstructorMarker) null);
        }
        if (spanStyle != null) {
            int size5 = this.annotations.size() + 1;
            arrayList = new ArrayList(size5);
            int i19 = 0;
            while (i19 < size5) {
                arrayList.add(i19 == 0 ? new AnnotatedString.Range(spanStyle, 0, this.text.length()) : (AnnotatedString.Range) this.annotations.get(i19 - 1));
                i19++;
            }
        } else {
            arrayList = this.annotations;
        }
        String str3 = this.text;
        float textSize = this.textPaint.getTextSize();
        TextStyle textStyle2 = this.style;
        List list5 = this.placeholders;
        Density density3 = this.density;
        boolean z3 = this.emojiCompatProcessed;
        AndroidParagraphHelper_androidKt$NoopSpan$1 androidParagraphHelper_androidKt$NoopSpan$1 = AndroidParagraphHelper_androidKt.NoopSpan;
        if (z3 && EmojiCompat.isConfigured()) {
            PlatformTextStyle platformTextStyle = textStyle2.platformStyle;
            EmojiSupportMatch emojiSupportMatchM731boximpl = (platformTextStyle == null || (platformParagraphStyle2 = platformTextStyle.paragraphStyle) == null) ? null : EmojiSupportMatch.m731boximpl(platformParagraphStyle2.emojiSupportMatch);
            EmojiSupportMatch.Companion.getClass();
            CharSequence charSequenceProcess = EmojiCompat.get().process(0, str3.length(), (emojiSupportMatchM731boximpl != null && emojiSupportMatchM731boximpl.value == EmojiSupportMatch.All) ? i4 : 0, str3);
            charSequenceProcess.getClass();
            spannableString = charSequenceProcess;
        } else {
            spannableString = str3;
        }
        if (arrayList.isEmpty() && list5.isEmpty()) {
            TextIndent textIndent3 = textStyle2.paragraphStyle.textIndent;
            TextIndent.Companion.getClass();
            if (Intrinsics.areEqual(textIndent3, TextIndent.None)) {
                j = 0;
                long j8 = textStyle2.paragraphStyle.lineHeight;
                TextUnit.Companion companion = TextUnit.Companion;
                if ((j8 & 1095216660480L) != 0) {
                }
            }
            if (!(spannableString instanceof Spannable)) {
            }
            textDecoration = textStyle2.spanStyle.textDecoration;
            TextDecoration.Companion.getClass();
            if (Intrinsics.areEqual(textDecoration, TextDecoration.Underline)) {
            }
            PlatformTextStyle platformTextStyle2 = textStyle2.platformStyle;
            if (platformTextStyle2 != null) {
                paragraphStyle = textStyle2.paragraphStyle;
                if (z) {
                    j2 = 1095216660480L;
                    lineHeightStyle = paragraphStyle.lineHeightStyle;
                    if (lineHeightStyle == null) {
                    }
                    fM788resolveLineHeightInPxo2QH7mI = SpannableExtensions_androidKt.m788resolveLineHeightInPxo2QH7mI(paragraphStyle.lineHeight, textSize, density3);
                    if (Float.isNaN(fM788resolveLineHeightInPxo2QH7mI)) {
                        i6 = 0;
                    }
                    textIndent = paragraphStyle.textIndent;
                    if (textIndent != null) {
                    }
                    f2 = textSize;
                    list4 = list3;
                    SpannableExtensions_androidKt.setSpanStyles(spannableString, textStyle2, list4, density3, function4);
                    textIndent2 = paragraphStyle.textIndent;
                    if (textIndent2 != null) {
                    }
                    size = list4.size();
                    i8 = i7;
                    while (i8 < size) {
                    }
                    Density density4 = density3;
                    size2 = list5.size();
                    while (i9 < size2) {
                    }
                }
            }
        } else {
            j = 0;
            spannableString = !(spannableString instanceof Spannable) ? (Spannable) spannableString : new SpannableString(spannableString);
            textDecoration = textStyle2.spanStyle.textDecoration;
            TextDecoration.Companion.getClass();
            if (Intrinsics.areEqual(textDecoration, TextDecoration.Underline)) {
                SpannableExtensions_androidKt.setSpan(spannableString, AndroidParagraphHelper_androidKt.NoopSpan, 0, str3.length());
            }
            PlatformTextStyle platformTextStyle22 = textStyle2.platformStyle;
            z = (platformTextStyle22 != null || (platformParagraphStyle = platformTextStyle22.paragraphStyle) == null) ? false : platformParagraphStyle.includeFontPadding;
            paragraphStyle = textStyle2.paragraphStyle;
            if (z || paragraphStyle.lineHeightStyle != null) {
                j2 = 1095216660480L;
                lineHeightStyle = paragraphStyle.lineHeightStyle;
                if (lineHeightStyle == null) {
                    LineHeightStyle.Companion.getClass();
                    lineHeightStyle = LineHeightStyle.Default;
                }
                fM788resolveLineHeightInPxo2QH7mI = SpannableExtensions_androidKt.m788resolveLineHeightInPxo2QH7mI(paragraphStyle.lineHeight, textSize, density3);
                if (Float.isNaN(fM788resolveLineHeightInPxo2QH7mI)) {
                    int length = (spannableString.length() == 0 || StringsKt___StringsKt.last(spannableString) == '\n') ? spannableString.length() + 1 : spannableString.length();
                    int i20 = lineHeightStyle.trim;
                    ?? r25 = (i20 & 1) > 0 ? i4 : 0;
                    ?? r26 = (i20 & 16) > 0 ? i4 : 0;
                    LineHeightStyle.Mode.Companion.getClass();
                    i6 = 0;
                    spannableString.setSpan(new LineHeightStyleSpan(fM788resolveLineHeightInPxo2QH7mI, 0, length, r25, r26, lineHeightStyle.alignment, lineHeightStyle.mode == LineHeightStyle.Mode.Minimum ? i4 : 0), 0, spannableString.length(), 33);
                }
                textIndent = paragraphStyle.textIndent;
                if (textIndent != null) {
                    long sp = TextUnitKt.getSp(i6);
                    list3 = arrayList;
                    long j9 = textIndent.firstLine;
                    boolean zM868equalsimpl0 = TextUnit.m868equalsimpl0(j9, sp);
                    int i21 = i6;
                    long j10 = textIndent.restLine;
                    if (zM868equalsimpl0 && TextUnit.m868equalsimpl0(j10, TextUnitKt.getSp(i21))) {
                        i7 = 0;
                    } else if ((j9 & j2) == j || (j10 & j2) == j) {
                        list4 = list3;
                        f2 = textSize;
                        i7 = 0;
                        SpannableExtensions_androidKt.setSpanStyles(spannableString, textStyle2, list4, density3, function4);
                        textIndent2 = paragraphStyle.textIndent;
                        if (textIndent2 != null) {
                        }
                        size = list4.size();
                        i8 = i7;
                        while (i8 < size) {
                        }
                        Density density42 = density3;
                        size2 = list5.size();
                        while (i9 < size2) {
                        }
                    } else {
                        long jM869getTypeUIouoOA3 = TextUnit.m869getTypeUIouoOA(j9);
                        TextUnitType.Companion.getClass();
                        long j11 = TextUnitType.Sp;
                        if (TextUnitType.m876equalsimpl0(jM869getTypeUIouoOA3, j11)) {
                            fM870getValueimpl = density3.mo57toPxR2X_6o(j9);
                            f2 = textSize;
                        } else {
                            f2 = textSize;
                            fM870getValueimpl = TextUnitType.m876equalsimpl0(jM869getTypeUIouoOA3, TextUnitType.Em) ? TextUnit.m870getValueimpl(j9) * f2 : 0.0f;
                        }
                        long jM869getTypeUIouoOA4 = TextUnit.m869getTypeUIouoOA(j10);
                        float fMo57toPxR2X_6o3 = TextUnitType.m876equalsimpl0(jM869getTypeUIouoOA4, j11) ? density3.mo57toPxR2X_6o(j10) : TextUnitType.m876equalsimpl0(jM869getTypeUIouoOA4, TextUnitType.Em) ? TextUnit.m870getValueimpl(j10) * f2 : 0.0f;
                        i7 = 0;
                        spannableString.setSpan(new LeadingMarginSpan.Standard((int) Math.ceil(fM870getValueimpl), (int) Math.ceil(fMo57toPxR2X_6o3)), 0, spannableString.length(), 33);
                        list4 = list3;
                        SpannableExtensions_androidKt.setSpanStyles(spannableString, textStyle2, list4, density3, function4);
                        textIndent2 = paragraphStyle.textIndent;
                        if (textIndent2 != null) {
                            long j12 = textIndent2.firstLine;
                            long jM869getTypeUIouoOA5 = TextUnit.m869getTypeUIouoOA(j12);
                            TextUnitType.Companion.getClass();
                            fMo57toPxR2X_6o = TextUnitType.m876equalsimpl0(jM869getTypeUIouoOA5, TextUnitType.Sp) ? density3.mo57toPxR2X_6o(j12) : TextUnitType.m876equalsimpl0(jM869getTypeUIouoOA5, TextUnitType.Em) ? TextUnit.m870getValueimpl(j12) * f2 : 0.0f;
                        } else {
                            fMo57toPxR2X_6o = 0.0f;
                        }
                        size = list4.size();
                        i8 = i7;
                        while (i8 < size) {
                            AnnotatedString.Range range2 = (AnnotatedString.Range) list4.get(i8);
                            Object obj = range2.item;
                            Bullet bullet = obj instanceof Bullet ? (Bullet) obj : null;
                            if (bullet != null) {
                                float fM787resolveBulletTextUnitToPxo2QH7mI = SpannableExtensions_androidKt.m787resolveBulletTextUnitToPxo2QH7mI(bullet.size, f2, density3);
                                float fM787resolveBulletTextUnitToPxo2QH7mI2 = SpannableExtensions_androidKt.m787resolveBulletTextUnitToPxo2QH7mI(bullet.padding, f2, density3);
                                if (Float.isNaN(fM787resolveBulletTextUnitToPxo2QH7mI) || Float.isNaN(fM787resolveBulletTextUnitToPxo2QH7mI2)) {
                                    density2 = density3;
                                } else {
                                    density2 = density3;
                                    SpannableExtensions_androidKt.setSpan(spannableString, new CustomBulletSpan(bullet.shape, fM787resolveBulletTextUnitToPxo2QH7mI, fM787resolveBulletTextUnitToPxo2QH7mI, fM787resolveBulletTextUnitToPxo2QH7mI2, bullet.brush, bullet.alpha, bullet.drawStyle, density2, fMo57toPxR2X_6o), range2.start, range2.end);
                                }
                            }
                            i8++;
                            density3 = density2;
                        }
                        Density density422 = density3;
                        size2 = list5.size();
                        for (i9 = i7; i9 < size2; i9++) {
                            AnnotatedString.Range range3 = (AnnotatedString.Range) list5.get(i9);
                            Placeholder placeholder = (Placeholder) range3.item;
                            int i22 = range3.start;
                            int i23 = range3.end;
                            Object[] spans = spannableString.getSpans(i22, i23, EmojiSpan.class);
                            int length2 = spans.length;
                            for (int i24 = i7; i24 < length2; i24++) {
                                spannableString.removeSpan((EmojiSpan) spans[i24]);
                            }
                            float fM870getValueimpl2 = TextUnit.m870getValueimpl(placeholder.width);
                            int iM786getSpanUnitR2X_6o = PlaceholderExtensions_androidKt.m786getSpanUnitR2X_6o(placeholder.width);
                            long j13 = placeholder.height;
                            float fM870getValueimpl3 = TextUnit.m870getValueimpl(j13);
                            int iM786getSpanUnitR2X_6o2 = PlaceholderExtensions_androidKt.m786getSpanUnitR2X_6o(j13);
                            float density5 = density422.getDensity() * density422.getFontScale();
                            PlaceholderVerticalAlign.Companion.getClass();
                            int i25 = PlaceholderVerticalAlign.AboveBaseline;
                            int i26 = placeholder.placeholderVerticalAlign;
                            if (i26 == i25) {
                                i11 = i7;
                            } else if (i26 == PlaceholderVerticalAlign.Top) {
                                i11 = i4;
                            } else if (i26 == PlaceholderVerticalAlign.Bottom) {
                                i11 = 2;
                            } else if (i26 == PlaceholderVerticalAlign.Center) {
                                i11 = 3;
                            } else {
                                if (i26 == PlaceholderVerticalAlign.TextTop) {
                                    i10 = 4;
                                } else if (i26 == PlaceholderVerticalAlign.TextBottom) {
                                    i10 = 5;
                                } else {
                                    if (i26 != PlaceholderVerticalAlign.TextCenter) {
                                        throw new IllegalStateException("Invalid PlaceholderVerticalAlign");
                                    }
                                    i10 = 6;
                                }
                                i11 = i10;
                            }
                            spannableString.setSpan(new PlaceholderSpan(fM870getValueimpl2, iM786getSpanUnitR2X_6o, fM870getValueimpl3, iM786getSpanUnitR2X_6o2, density5, i11), i22, i23, 33);
                        }
                    }
                } else {
                    list3 = arrayList;
                    i7 = i6;
                }
                f2 = textSize;
                list4 = list3;
                SpannableExtensions_androidKt.setSpanStyles(spannableString, textStyle2, list4, density3, function4);
                textIndent2 = paragraphStyle.textIndent;
                if (textIndent2 != null) {
                }
                size = list4.size();
                i8 = i7;
                while (i8 < size) {
                }
                Density density4222 = density3;
                size2 = list5.size();
                while (i9 < size2) {
                }
            } else {
                j2 = 1095216660480L;
                float fM788resolveLineHeightInPxo2QH7mI2 = SpannableExtensions_androidKt.m788resolveLineHeightInPxo2QH7mI(paragraphStyle.lineHeight, textSize, density3);
                if (!Float.isNaN(fM788resolveLineHeightInPxo2QH7mI2)) {
                    spannableString.setSpan(new LineHeightSpan(fM788resolveLineHeightInPxo2QH7mI2), 0, spannableString.length(), 33);
                }
            }
            i6 = 0;
            textIndent = paragraphStyle.textIndent;
            if (textIndent != null) {
            }
            f2 = textSize;
            list4 = list3;
            SpannableExtensions_androidKt.setSpanStyles(spannableString, textStyle2, list4, density3, function4);
            textIndent2 = paragraphStyle.textIndent;
            if (textIndent2 != null) {
            }
            size = list4.size();
            i8 = i7;
            while (i8 < size) {
            }
            Density density42222 = density3;
            size2 = list5.size();
            while (i9 < size2) {
            }
        }
        this.charSequence = spannableString;
        this.layoutIntrinsics = new LayoutIntrinsics(spannableString, this.textPaint, this.textDirectionHeuristic);
    }

    @Override // androidx.compose.ui.text.ParagraphIntrinsics
    public final boolean getHasStaleResolvedFonts() {
        TypefaceDirtyTrackerLinkedList typefaceDirtyTrackerLinkedList = this.resolvedTypefaces;
        if (typefaceDirtyTrackerLinkedList != null ? typefaceDirtyTrackerLinkedList.isStaleResolvedFont() : false) {
            return true;
        }
        if (!this.emojiCompatProcessed && AndroidParagraphIntrinsics_androidKt.access$getHasEmojiCompat(this.style)) {
            EmojiCompatStatus.INSTANCE.getClass();
            DefaultImpl defaultImpl = (DefaultImpl) EmojiCompatStatus.delegate;
            State fontLoadState = defaultImpl.loadState;
            if (fontLoadState == null) {
                if (EmojiCompat.isConfigured()) {
                    fontLoadState = defaultImpl.getFontLoadState();
                    defaultImpl.loadState = fontLoadState;
                } else {
                    fontLoadState = EmojiCompatStatus_androidKt.Falsey;
                }
            }
            if (((Boolean) fontLoadState.getValue()).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.compose.ui.text.ParagraphIntrinsics
    public final float getMaxIntrinsicWidth() {
        return this.layoutIntrinsics.getMaxIntrinsicWidth();
    }

    @Override // androidx.compose.ui.text.ParagraphIntrinsics
    public final float getMinIntrinsicWidth() {
        float f;
        LayoutIntrinsics layoutIntrinsics = this.layoutIntrinsics;
        if (!Float.isNaN(layoutIntrinsics._minIntrinsicWidth)) {
            return layoutIntrinsics._minIntrinsicWidth;
        }
        BreakIterator lineInstance = BreakIterator.getLineInstance(layoutIntrinsics.textPaint.getTextLocale());
        CharSequence charSequence = layoutIntrinsics.charSequence;
        int i = 0;
        lineInstance.setText(new CharSequenceCharacterIterator(charSequence, 0, charSequence.length()));
        PriorityQueue priorityQueue = new PriorityQueue(10, new LayoutIntrinsics$$ExternalSyntheticLambda0());
        int next = lineInstance.next();
        while (true) {
            int i2 = i;
            i = next;
            if (i == -1) {
                break;
            }
            if (priorityQueue.size() < 10) {
                priorityQueue.add(new Pair(Integer.valueOf(i2), Integer.valueOf(i)));
            } else {
                Pair pair = (Pair) priorityQueue.peek();
                if (pair != null && ((Number) pair.getSecond()).intValue() - ((Number) pair.getFirst()).intValue() < i - i2) {
                    priorityQueue.poll();
                    priorityQueue.add(new Pair(Integer.valueOf(i2), Integer.valueOf(i)));
                }
            }
            next = lineInstance.next();
        }
        if (priorityQueue.isEmpty()) {
            f = 0.0f;
        } else {
            Iterator it = priorityQueue.iterator();
            if (!it.hasNext()) {
                throw new NoSuchElementException();
            }
            Pair pair2 = (Pair) it.next();
            float desiredWidth = Layout.getDesiredWidth(layoutIntrinsics.getCharSequenceForIntrinsicWidth(), ((Number) pair2.component1()).intValue(), ((Number) pair2.component2()).intValue(), layoutIntrinsics.textPaint);
            while (it.hasNext()) {
                Pair pair3 = (Pair) it.next();
                desiredWidth = Math.max(desiredWidth, Layout.getDesiredWidth(layoutIntrinsics.getCharSequenceForIntrinsicWidth(), ((Number) pair3.component1()).intValue(), ((Number) pair3.component2()).intValue(), layoutIntrinsics.textPaint));
            }
            f = desiredWidth;
        }
        layoutIntrinsics._minIntrinsicWidth = f;
        return f;
    }
}
