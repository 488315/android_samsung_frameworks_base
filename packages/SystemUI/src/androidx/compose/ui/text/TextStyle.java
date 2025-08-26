package androidx.compose.ui.text;

import androidx.compose.foundation.OverscrollConfiguration$$ExternalSyntheticOutline0;
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
import androidx.compose.ui.text.style.Hyphens;
import androidx.compose.ui.text.style.LineBreak;
import androidx.compose.ui.text.style.LineHeightStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextDirection;
import androidx.compose.ui.text.style.TextForegroundStyle;
import androidx.compose.ui.text.style.TextGeometricTransform;
import androidx.compose.ui.text.style.TextIndent;
import androidx.compose.ui.text.style.TextMotion;
import androidx.compose.ui.unit.TextUnit;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class TextStyle {
    public static final Companion Companion = new Companion(null);
    public static final TextStyle Default = new TextStyle(0, 0, (FontWeight) null, (FontStyle) null, (FontSynthesis) null, (FontFamily) null, (String) null, 0, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0, (TextDecoration) null, (Shadow) null, (DrawStyle) null, 0, 0, 0, (TextIndent) null, (PlatformTextStyle) null, (LineHeightStyle) null, 0, 0, (TextMotion) null, 16777215, (DefaultConstructorMarker) null);
    public final ParagraphStyle paragraphStyle;
    public final PlatformTextStyle platformStyle;
    public final SpanStyle spanStyle;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public /* synthetic */ TextStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, DrawStyle drawStyle, int i, int i2, long j5, TextIndent textIndent, PlatformTextStyle platformTextStyle, LineHeightStyle lineHeightStyle, int i3, int i4, TextMotion textMotion, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j3, baselineShift, textGeometricTransform, localeList, j4, textDecoration, shadow, drawStyle, i, i2, j5, textIndent, platformTextStyle, lineHeightStyle, i3, i4, textMotion);
    }

    /* renamed from: copy-p1EtxEg$default, reason: not valid java name */
    public static TextStyle m756copyp1EtxEg$default(TextStyle textStyle, long j, long j2, FontWeight fontWeight, FontFamily fontFamily, long j3, int i, long j4, PlatformTextStyle platformTextStyle, LineHeightStyle lineHeightStyle, int i2, int i3) {
        TextForegroundStyle textForegroundStyleM812from8_81llA;
        long jMo794getColor0d7_KjU = (i3 & 1) != 0 ? textStyle.spanStyle.textForegroundStyle.mo794getColor0d7_KjU() : j;
        long j5 = (i3 & 2) != 0 ? textStyle.spanStyle.fontSize : j2;
        FontWeight fontWeight2 = (i3 & 4) != 0 ? textStyle.spanStyle.fontWeight : fontWeight;
        SpanStyle spanStyle = textStyle.spanStyle;
        FontStyle fontStyle = spanStyle.fontStyle;
        FontSynthesis fontSynthesis = spanStyle.fontSynthesis;
        FontFamily fontFamily2 = (i3 & 32) != 0 ? spanStyle.fontFamily : fontFamily;
        String str = spanStyle.fontFeatureSettings;
        long j6 = (i3 & 128) != 0 ? spanStyle.letterSpacing : j3;
        BaselineShift baselineShift = spanStyle.baselineShift;
        TextGeometricTransform textGeometricTransform = spanStyle.textGeometricTransform;
        LocaleList localeList = spanStyle.localeList;
        long j7 = spanStyle.background;
        TextDecoration textDecoration = spanStyle.textDecoration;
        Shadow shadow = spanStyle.shadow;
        DrawStyle drawStyle = spanStyle.drawStyle;
        int i4 = (i3 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0 ? textStyle.paragraphStyle.textAlign : i;
        ParagraphStyle paragraphStyle = textStyle.paragraphStyle;
        int i5 = paragraphStyle.textDirection;
        long j8 = (i3 & 131072) != 0 ? paragraphStyle.lineHeight : j4;
        TextIndent textIndent = paragraphStyle.textIndent;
        PlatformTextStyle platformTextStyle2 = (i3 & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0 ? textStyle.platformStyle : platformTextStyle;
        LineHeightStyle lineHeightStyle2 = (i3 & 1048576) != 0 ? paragraphStyle.lineHeightStyle : lineHeightStyle;
        int i6 = paragraphStyle.lineBreak;
        int i7 = (i3 & 4194304) != 0 ? paragraphStyle.hyphens : i2;
        TextMotion textMotion = paragraphStyle.textMotion;
        int i8 = i7;
        long jMo794getColor0d7_KjU2 = spanStyle.textForegroundStyle.mo794getColor0d7_KjU();
        Color.Companion companion = Color.Companion;
        if (ULong.m3447equalsimpl0(jMo794getColor0d7_KjU, jMo794getColor0d7_KjU2)) {
            textForegroundStyleM812from8_81llA = spanStyle.textForegroundStyle;
        } else {
            TextForegroundStyle.Companion.getClass();
            textForegroundStyleM812from8_81llA = TextForegroundStyle.Companion.m812from8_81llA(jMo794getColor0d7_KjU);
        }
        return new TextStyle(new SpanStyle(textForegroundStyleM812from8_81llA, j5, fontWeight2, fontStyle, fontSynthesis, fontFamily2, str, j6, baselineShift, textGeometricTransform, localeList, j7, textDecoration, shadow, platformTextStyle2 != null ? platformTextStyle2.spanStyle : null, drawStyle, (DefaultConstructorMarker) null), new ParagraphStyle(i4, i5, j8, textIndent, platformTextStyle2 != null ? platformTextStyle2.paragraphStyle : null, lineHeightStyle2, i6, i8, textMotion, (DefaultConstructorMarker) null), platformTextStyle2);
    }

    /* renamed from: merge-dA7vx0o$default, reason: not valid java name */
    public static TextStyle m757mergedA7vx0o$default(TextStyle textStyle, long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontFamily fontFamily, long j3, TextDecoration textDecoration, int i, long j4, int i2) {
        long j5;
        long j6;
        long j7;
        int i3;
        long j8;
        if ((i2 & 1) != 0) {
            Color.Companion.getClass();
            j5 = Color.Unspecified;
        } else {
            j5 = j;
        }
        if ((i2 & 2) != 0) {
            TextUnit.Companion.getClass();
            j6 = TextUnit.Unspecified;
        } else {
            j6 = j2;
        }
        FontWeight fontWeight2 = (i2 & 4) != 0 ? null : fontWeight;
        FontStyle fontStyle2 = (i2 & 8) != 0 ? null : fontStyle;
        FontFamily fontFamily2 = (i2 & 32) != 0 ? null : fontFamily;
        if ((i2 & 128) != 0) {
            TextUnit.Companion.getClass();
            j7 = TextUnit.Unspecified;
        } else {
            j7 = j3;
        }
        Color.Companion.getClass();
        long j9 = Color.Unspecified;
        TextDecoration textDecoration2 = (i2 & 4096) != 0 ? null : textDecoration;
        if ((32768 & i2) != 0) {
            TextAlign.Companion.getClass();
            i3 = TextAlign.Unspecified;
        } else {
            i3 = i;
        }
        TextDirection.Companion.getClass();
        int i4 = TextDirection.Unspecified;
        if ((i2 & 131072) != 0) {
            TextUnit.Companion.getClass();
            j8 = TextUnit.Unspecified;
        } else {
            j8 = j4;
        }
        LineBreak.Companion.getClass();
        Hyphens.Companion.getClass();
        int i5 = Hyphens.Unspecified;
        SpanStyle spanStyleM743fastMergedSHsh3o = SpanStyleKt.m743fastMergedSHsh3o(textStyle.spanStyle, j5, null, Float.NaN, j6, fontWeight2, fontStyle2, null, fontFamily2, null, j7, null, null, null, j9, textDecoration2, null, null, null);
        ParagraphStyle paragraphStyleM741fastMergej5T8yCg = ParagraphStyleKt.m741fastMergej5T8yCg(textStyle.paragraphStyle, i3, i4, j8, null, null, null, 0, i5, null);
        return (textStyle.spanStyle == spanStyleM743fastMergedSHsh3o && textStyle.paragraphStyle == paragraphStyleM741fastMergej5T8yCg) ? textStyle : new TextStyle(spanStyleM743fastMergedSHsh3o, paragraphStyleM741fastMergej5T8yCg);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TextStyle)) {
            return false;
        }
        TextStyle textStyle = (TextStyle) obj;
        return Intrinsics.areEqual(this.spanStyle, textStyle.spanStyle) && Intrinsics.areEqual(this.paragraphStyle, textStyle.paragraphStyle) && Intrinsics.areEqual(this.platformStyle, textStyle.platformStyle);
    }

    /* renamed from: getColor-0d7_KjU, reason: not valid java name */
    public final long m758getColor0d7_KjU() {
        return this.spanStyle.textForegroundStyle.mo794getColor0d7_KjU();
    }

    public final boolean hasSameLayoutAffectingAttributes(TextStyle textStyle) {
        if (this != textStyle) {
            return Intrinsics.areEqual(this.paragraphStyle, textStyle.paragraphStyle) && this.spanStyle.hasSameLayoutAffectingAttributes$ui_text_release(textStyle.spanStyle);
        }
        return true;
    }

    public final int hashCode() {
        int iHashCode = (this.paragraphStyle.hashCode() + (this.spanStyle.hashCode() * 31)) * 31;
        PlatformTextStyle platformTextStyle = this.platformStyle;
        return iHashCode + (platformTextStyle != null ? platformTextStyle.hashCode() : 0);
    }

    public final TextStyle merge(TextStyle textStyle) {
        return (textStyle == null || textStyle.equals(Default)) ? this : new TextStyle(this.spanStyle.merge(textStyle.spanStyle), this.paragraphStyle.merge(textStyle.paragraphStyle));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("TextStyle(color=");
        sb.append((Object) Color.m464toStringimpl(m758getColor0d7_KjU()));
        sb.append(", brush=");
        SpanStyle spanStyle = this.spanStyle;
        sb.append(spanStyle.textForegroundStyle.getBrush());
        sb.append(", alpha=");
        sb.append(spanStyle.textForegroundStyle.getAlpha());
        sb.append(", fontSize=");
        sb.append((Object) TextUnit.m872toStringimpl(spanStyle.fontSize));
        sb.append(", fontWeight=");
        sb.append(spanStyle.fontWeight);
        sb.append(", fontStyle=");
        sb.append(spanStyle.fontStyle);
        sb.append(", fontSynthesis=");
        sb.append(spanStyle.fontSynthesis);
        sb.append(", fontFamily=");
        sb.append(spanStyle.fontFamily);
        sb.append(", fontFeatureSettings=");
        sb.append(spanStyle.fontFeatureSettings);
        sb.append(", letterSpacing=");
        sb.append((Object) TextUnit.m872toStringimpl(spanStyle.letterSpacing));
        sb.append(", baselineShift=");
        sb.append(spanStyle.baselineShift);
        sb.append(", textGeometricTransform=");
        sb.append(spanStyle.textGeometricTransform);
        sb.append(", localeList=");
        sb.append(spanStyle.localeList);
        sb.append(", background=");
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(spanStyle.background, ", textDecoration=", sb);
        sb.append(spanStyle.textDecoration);
        sb.append(", shadow=");
        sb.append(spanStyle.shadow);
        sb.append(", drawStyle=");
        sb.append(spanStyle.drawStyle);
        sb.append(", textAlign=");
        ParagraphStyle paragraphStyle = this.paragraphStyle;
        sb.append((Object) TextAlign.m808toStringimpl(paragraphStyle.textAlign));
        sb.append(", textDirection=");
        sb.append((Object) TextDirection.m810toStringimpl(paragraphStyle.textDirection));
        sb.append(", lineHeight=");
        sb.append((Object) TextUnit.m872toStringimpl(paragraphStyle.lineHeight));
        sb.append(", textIndent=");
        sb.append(paragraphStyle.textIndent);
        sb.append(", platformStyle=");
        sb.append(this.platformStyle);
        sb.append(", lineHeightStyle=");
        sb.append(paragraphStyle.lineHeightStyle);
        sb.append(", lineBreak=");
        sb.append((Object) LineBreak.m798toStringimpl(paragraphStyle.lineBreak));
        sb.append(", hyphens=");
        sb.append((Object) Hyphens.m796toStringimpl(paragraphStyle.hyphens));
        sb.append(", textMotion=");
        sb.append(paragraphStyle.textMotion);
        sb.append(')');
        return sb.toString();
    }

    public /* synthetic */ TextStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, DrawStyle drawStyle, TextAlign textAlign, TextDirection textDirection, long j5, TextIndent textIndent, PlatformTextStyle platformTextStyle, LineHeightStyle lineHeightStyle, LineBreak lineBreak, Hyphens hyphens, TextMotion textMotion, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j3, baselineShift, textGeometricTransform, localeList, j4, textDecoration, shadow, drawStyle, textAlign, textDirection, j5, textIndent, platformTextStyle, lineHeightStyle, lineBreak, hyphens, textMotion);
    }

    public /* synthetic */ TextStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, TextAlign textAlign, TextDirection textDirection, long j5, TextIndent textIndent, PlatformTextStyle platformTextStyle, LineHeightStyle lineHeightStyle, LineBreak lineBreak, Hyphens hyphens, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j3, baselineShift, textGeometricTransform, localeList, j4, textDecoration, shadow, textAlign, textDirection, j5, textIndent, platformTextStyle, lineHeightStyle, lineBreak, hyphens);
    }

    public /* synthetic */ TextStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, TextAlign textAlign, TextDirection textDirection, long j5, TextIndent textIndent, PlatformTextStyle platformTextStyle, LineHeightStyle lineHeightStyle, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j3, baselineShift, textGeometricTransform, localeList, j4, textDecoration, shadow, textAlign, textDirection, j5, textIndent, platformTextStyle, lineHeightStyle);
    }

    public /* synthetic */ TextStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, TextAlign textAlign, TextDirection textDirection, long j5, TextIndent textIndent, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j3, baselineShift, textGeometricTransform, localeList, j4, textDecoration, shadow, textAlign, textDirection, j5, textIndent);
    }

    public /* synthetic */ TextStyle(Brush brush, float f, long j, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j2, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j3, TextDecoration textDecoration, Shadow shadow, DrawStyle drawStyle, int i, int i2, long j4, TextIndent textIndent, PlatformTextStyle platformTextStyle, LineHeightStyle lineHeightStyle, int i3, int i4, TextMotion textMotion, DefaultConstructorMarker defaultConstructorMarker) {
        this(brush, f, j, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j2, baselineShift, textGeometricTransform, localeList, j3, textDecoration, shadow, drawStyle, i, i2, j4, textIndent, platformTextStyle, lineHeightStyle, i3, i4, textMotion);
    }

    public /* synthetic */ TextStyle(Brush brush, float f, long j, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j2, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j3, TextDecoration textDecoration, Shadow shadow, DrawStyle drawStyle, TextAlign textAlign, TextDirection textDirection, long j4, TextIndent textIndent, PlatformTextStyle platformTextStyle, LineHeightStyle lineHeightStyle, LineBreak lineBreak, Hyphens hyphens, TextMotion textMotion, DefaultConstructorMarker defaultConstructorMarker) {
        this(brush, f, j, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j2, baselineShift, textGeometricTransform, localeList, j3, textDecoration, shadow, drawStyle, textAlign, textDirection, j4, textIndent, platformTextStyle, lineHeightStyle, lineBreak, hyphens, textMotion);
    }

    public TextStyle(SpanStyle spanStyle, ParagraphStyle paragraphStyle, PlatformTextStyle platformTextStyle) {
        this.spanStyle = spanStyle;
        this.paragraphStyle = paragraphStyle;
        this.platformStyle = platformTextStyle;
    }

    public /* synthetic */ TextStyle(SpanStyle spanStyle, ParagraphStyle paragraphStyle, PlatformTextStyle platformTextStyle, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(spanStyle, paragraphStyle, (i & 4) != 0 ? null : platformTextStyle);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public TextStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, TextAlign textAlign, TextDirection textDirection, long j5, TextIndent textIndent, int i, DefaultConstructorMarker defaultConstructorMarker) {
        long j6;
        long j7;
        long j8;
        long j9;
        long j10;
        if ((i & 1) != 0) {
            Color.Companion.getClass();
            j6 = Color.Unspecified;
        } else {
            j6 = j;
        }
        if ((i & 2) != 0) {
            TextUnit.Companion.getClass();
            j7 = TextUnit.Unspecified;
        } else {
            j7 = j2;
        }
        FontWeight fontWeight2 = (i & 4) != 0 ? null : fontWeight;
        FontStyle fontStyle2 = (i & 8) != 0 ? null : fontStyle;
        FontSynthesis fontSynthesis2 = (i & 16) != 0 ? null : fontSynthesis;
        FontFamily fontFamily2 = (i & 32) != 0 ? null : fontFamily;
        String str2 = (i & 64) != 0 ? null : str;
        if ((i & 128) != 0) {
            TextUnit.Companion.getClass();
            j8 = TextUnit.Unspecified;
        } else {
            j8 = j3;
        }
        BaselineShift baselineShift2 = (i & 256) != 0 ? null : baselineShift;
        TextGeometricTransform textGeometricTransform2 = (i & 512) != 0 ? null : textGeometricTransform;
        LocaleList localeList2 = (i & 1024) != 0 ? null : localeList;
        if ((i & 2048) != 0) {
            Color.Companion.getClass();
            j9 = Color.Unspecified;
        } else {
            j9 = j4;
        }
        TextDecoration textDecoration2 = (i & 4096) != 0 ? null : textDecoration;
        long j11 = j6;
        Shadow shadow2 = (i & 8192) != 0 ? null : shadow;
        TextAlign textAlign2 = (i & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? null : textAlign;
        TextDirection textDirection2 = (i & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0 ? null : textDirection;
        if ((i & 65536) != 0) {
            TextUnit.Companion.getClass();
            j10 = TextUnit.Unspecified;
        } else {
            j10 = j5;
        }
        long j12 = j7;
        FontWeight fontWeight3 = fontWeight2;
        TextDecoration textDecoration3 = textDecoration2;
        FontStyle fontStyle3 = fontStyle2;
        FontSynthesis fontSynthesis3 = fontSynthesis2;
        FontFamily fontFamily3 = fontFamily2;
        String str3 = str2;
        long j13 = j8;
        BaselineShift baselineShift3 = baselineShift2;
        TextGeometricTransform textGeometricTransform3 = textGeometricTransform2;
        LocaleList localeList3 = localeList2;
        long j14 = j9;
        this(j11, j12, fontWeight3, fontStyle3, fontSynthesis3, fontFamily3, str3, j13, baselineShift3, textGeometricTransform3, localeList3, j14, textDecoration3, shadow2, textAlign2, textDirection2, j10, (i & 131072) != 0 ? null : textIndent, null);
    }

    private TextStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, TextAlign textAlign, TextDirection textDirection, long j5, TextIndent textIndent) {
        int i;
        int i2;
        SpanStyle spanStyle = new SpanStyle(j, j2, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j3, baselineShift, textGeometricTransform, localeList, j4, textDecoration, shadow, (PlatformSpanStyle) null, (DrawStyle) null, (DefaultConstructorMarker) null);
        if (textAlign != null) {
            i = textAlign.value;
        } else {
            TextAlign.Companion.getClass();
            i = TextAlign.Unspecified;
        }
        if (textDirection != null) {
            i2 = textDirection.value;
        } else {
            TextDirection.Companion.getClass();
            i2 = TextDirection.Unspecified;
        }
        LineBreak.Companion.getClass();
        Hyphens.Companion.getClass();
        int i3 = i;
        int i4 = i2;
        this(spanStyle, new ParagraphStyle(i3, i4, j5, textIndent, (PlatformParagraphStyle) null, (LineHeightStyle) null, 0, Hyphens.Unspecified, (TextMotion) null, (DefaultConstructorMarker) null), null);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public TextStyle(SpanStyle spanStyle, ParagraphStyle paragraphStyle) {
        PlatformSpanStyle platformSpanStyle = spanStyle.platformStyle;
        PlatformParagraphStyle platformParagraphStyle = paragraphStyle.platformStyle;
        this(spanStyle, paragraphStyle, (platformSpanStyle == null && platformParagraphStyle == null) ? null : new PlatformTextStyle(platformSpanStyle, platformParagraphStyle));
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public TextStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, TextAlign textAlign, TextDirection textDirection, long j5, TextIndent textIndent, PlatformTextStyle platformTextStyle, LineHeightStyle lineHeightStyle, int i, DefaultConstructorMarker defaultConstructorMarker) {
        long j6;
        long j7;
        long j8;
        long j9;
        long j10;
        if ((i & 1) != 0) {
            Color.Companion.getClass();
            j6 = Color.Unspecified;
        } else {
            j6 = j;
        }
        if ((i & 2) != 0) {
            TextUnit.Companion.getClass();
            j7 = TextUnit.Unspecified;
        } else {
            j7 = j2;
        }
        FontWeight fontWeight2 = (i & 4) != 0 ? null : fontWeight;
        FontStyle fontStyle2 = (i & 8) != 0 ? null : fontStyle;
        FontSynthesis fontSynthesis2 = (i & 16) != 0 ? null : fontSynthesis;
        FontFamily fontFamily2 = (i & 32) != 0 ? null : fontFamily;
        String str2 = (i & 64) != 0 ? null : str;
        if ((i & 128) != 0) {
            TextUnit.Companion.getClass();
            j8 = TextUnit.Unspecified;
        } else {
            j8 = j3;
        }
        BaselineShift baselineShift2 = (i & 256) != 0 ? null : baselineShift;
        TextGeometricTransform textGeometricTransform2 = (i & 512) != 0 ? null : textGeometricTransform;
        LocaleList localeList2 = (i & 1024) != 0 ? null : localeList;
        if ((i & 2048) != 0) {
            Color.Companion.getClass();
            j9 = Color.Unspecified;
        } else {
            j9 = j4;
        }
        TextDecoration textDecoration2 = (i & 4096) != 0 ? null : textDecoration;
        long j11 = j6;
        Shadow shadow2 = (i & 8192) != 0 ? null : shadow;
        TextAlign textAlign2 = (i & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? null : textAlign;
        TextDirection textDirection2 = (i & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0 ? null : textDirection;
        if ((i & 65536) != 0) {
            TextUnit.Companion.getClass();
            j10 = TextUnit.Unspecified;
        } else {
            j10 = j5;
        }
        TextIndent textIndent2 = (i & 131072) != 0 ? null : textIndent;
        PlatformTextStyle platformTextStyle2 = (i & 262144) != 0 ? null : platformTextStyle;
        long j12 = j7;
        FontWeight fontWeight3 = fontWeight2;
        TextDecoration textDecoration3 = textDecoration2;
        FontStyle fontStyle3 = fontStyle2;
        FontSynthesis fontSynthesis3 = fontSynthesis2;
        FontFamily fontFamily3 = fontFamily2;
        String str3 = str2;
        long j13 = j8;
        BaselineShift baselineShift3 = baselineShift2;
        TextGeometricTransform textGeometricTransform3 = textGeometricTransform2;
        LocaleList localeList3 = localeList2;
        long j14 = j9;
        this(j11, j12, fontWeight3, fontStyle3, fontSynthesis3, fontFamily3, str3, j13, baselineShift3, textGeometricTransform3, localeList3, j14, textDecoration3, shadow2, textAlign2, textDirection2, j10, textIndent2, platformTextStyle2, (i & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0 ? null : lineHeightStyle, null);
    }

    private TextStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, TextAlign textAlign, TextDirection textDirection, long j5, TextIndent textIndent, PlatformTextStyle platformTextStyle, LineHeightStyle lineHeightStyle) {
        int i;
        int i2;
        SpanStyle spanStyle = new SpanStyle(j, j2, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j3, baselineShift, textGeometricTransform, localeList, j4, textDecoration, shadow, platformTextStyle != null ? platformTextStyle.spanStyle : null, (DrawStyle) null, (DefaultConstructorMarker) null);
        if (textAlign != null) {
            i = textAlign.value;
        } else {
            TextAlign.Companion.getClass();
            i = TextAlign.Unspecified;
        }
        if (textDirection != null) {
            i2 = textDirection.value;
        } else {
            TextDirection.Companion.getClass();
            i2 = TextDirection.Unspecified;
        }
        PlatformParagraphStyle platformParagraphStyle = platformTextStyle != null ? platformTextStyle.paragraphStyle : null;
        LineBreak.Companion.getClass();
        Hyphens.Companion.getClass();
        this(spanStyle, new ParagraphStyle(i, i2, j5, textIndent, platformParagraphStyle, lineHeightStyle, 0, Hyphens.Unspecified, (TextMotion) null, (DefaultConstructorMarker) null), platformTextStyle);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public TextStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, TextAlign textAlign, TextDirection textDirection, long j5, TextIndent textIndent, PlatformTextStyle platformTextStyle, LineHeightStyle lineHeightStyle, LineBreak lineBreak, Hyphens hyphens, int i, DefaultConstructorMarker defaultConstructorMarker) {
        long j6;
        long j7;
        long j8;
        long j9;
        long j10;
        if ((i & 1) != 0) {
            Color.Companion.getClass();
            j6 = Color.Unspecified;
        } else {
            j6 = j;
        }
        if ((i & 2) != 0) {
            TextUnit.Companion.getClass();
            j7 = TextUnit.Unspecified;
        } else {
            j7 = j2;
        }
        FontWeight fontWeight2 = (i & 4) != 0 ? null : fontWeight;
        FontStyle fontStyle2 = (i & 8) != 0 ? null : fontStyle;
        FontSynthesis fontSynthesis2 = (i & 16) != 0 ? null : fontSynthesis;
        FontFamily fontFamily2 = (i & 32) != 0 ? null : fontFamily;
        String str2 = (i & 64) != 0 ? null : str;
        if ((i & 128) != 0) {
            TextUnit.Companion.getClass();
            j8 = TextUnit.Unspecified;
        } else {
            j8 = j3;
        }
        BaselineShift baselineShift2 = (i & 256) != 0 ? null : baselineShift;
        TextGeometricTransform textGeometricTransform2 = (i & 512) != 0 ? null : textGeometricTransform;
        LocaleList localeList2 = (i & 1024) != 0 ? null : localeList;
        if ((i & 2048) != 0) {
            Color.Companion.getClass();
            j9 = Color.Unspecified;
        } else {
            j9 = j4;
        }
        TextDecoration textDecoration2 = (i & 4096) != 0 ? null : textDecoration;
        long j11 = j6;
        Shadow shadow2 = (i & 8192) != 0 ? null : shadow;
        TextAlign textAlign2 = (i & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? null : textAlign;
        TextDirection textDirection2 = (i & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0 ? null : textDirection;
        if ((i & 65536) != 0) {
            TextUnit.Companion.getClass();
            j10 = TextUnit.Unspecified;
        } else {
            j10 = j5;
        }
        TextIndent textIndent2 = (i & 131072) != 0 ? null : textIndent;
        PlatformTextStyle platformTextStyle2 = (i & 262144) != 0 ? null : platformTextStyle;
        LineHeightStyle lineHeightStyle2 = (i & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0 ? null : lineHeightStyle;
        LineBreak lineBreak2 = (i & 1048576) != 0 ? null : lineBreak;
        long j12 = j7;
        FontWeight fontWeight3 = fontWeight2;
        TextDecoration textDecoration3 = textDecoration2;
        FontStyle fontStyle3 = fontStyle2;
        FontSynthesis fontSynthesis3 = fontSynthesis2;
        FontFamily fontFamily3 = fontFamily2;
        String str3 = str2;
        long j13 = j8;
        BaselineShift baselineShift3 = baselineShift2;
        TextGeometricTransform textGeometricTransform3 = textGeometricTransform2;
        LocaleList localeList3 = localeList2;
        long j14 = j9;
        this(j11, j12, fontWeight3, fontStyle3, fontSynthesis3, fontFamily3, str3, j13, baselineShift3, textGeometricTransform3, localeList3, j14, textDecoration3, shadow2, textAlign2, textDirection2, j10, textIndent2, platformTextStyle2, lineHeightStyle2, lineBreak2, (i & 2097152) != 0 ? null : hyphens, null);
    }

    private TextStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, TextAlign textAlign, TextDirection textDirection, long j5, TextIndent textIndent, PlatformTextStyle platformTextStyle, LineHeightStyle lineHeightStyle, LineBreak lineBreak, Hyphens hyphens) {
        int i;
        int i2;
        int i3;
        int i4;
        SpanStyle spanStyle = new SpanStyle(j, j2, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j3, baselineShift, textGeometricTransform, localeList, j4, textDecoration, shadow, platformTextStyle != null ? platformTextStyle.spanStyle : null, (DrawStyle) null, NetworkAnalyticsConstants.DataPoints.FLAG_UID, (DefaultConstructorMarker) null);
        if (textAlign != null) {
            i = textAlign.value;
        } else {
            TextAlign.Companion.getClass();
            i = TextAlign.Unspecified;
        }
        if (textDirection != null) {
            i2 = textDirection.value;
        } else {
            TextDirection.Companion.getClass();
            i2 = TextDirection.Unspecified;
        }
        PlatformParagraphStyle platformParagraphStyle = platformTextStyle != null ? platformTextStyle.paragraphStyle : null;
        if (lineBreak != null) {
            i3 = lineBreak.mask;
        } else {
            LineBreak.Companion.getClass();
            i3 = 0;
        }
        if (hyphens != null) {
            i4 = hyphens.value;
        } else {
            Hyphens.Companion.getClass();
            i4 = Hyphens.Unspecified;
        }
        this(spanStyle, new ParagraphStyle(i, i2, j5, textIndent, platformParagraphStyle, lineHeightStyle, i3, i4, (TextMotion) null, 256, (DefaultConstructorMarker) null), platformTextStyle);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public TextStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, DrawStyle drawStyle, TextAlign textAlign, TextDirection textDirection, long j5, TextIndent textIndent, PlatformTextStyle platformTextStyle, LineHeightStyle lineHeightStyle, LineBreak lineBreak, Hyphens hyphens, TextMotion textMotion, int i, DefaultConstructorMarker defaultConstructorMarker) {
        long j6;
        long j7;
        long j8;
        long j9;
        long j10;
        if ((i & 1) != 0) {
            Color.Companion.getClass();
            j6 = Color.Unspecified;
        } else {
            j6 = j;
        }
        if ((i & 2) != 0) {
            TextUnit.Companion.getClass();
            j7 = TextUnit.Unspecified;
        } else {
            j7 = j2;
        }
        FontWeight fontWeight2 = (i & 4) != 0 ? null : fontWeight;
        FontStyle fontStyle2 = (i & 8) != 0 ? null : fontStyle;
        FontSynthesis fontSynthesis2 = (i & 16) != 0 ? null : fontSynthesis;
        FontFamily fontFamily2 = (i & 32) != 0 ? null : fontFamily;
        String str2 = (i & 64) != 0 ? null : str;
        if ((i & 128) != 0) {
            TextUnit.Companion.getClass();
            j8 = TextUnit.Unspecified;
        } else {
            j8 = j3;
        }
        BaselineShift baselineShift2 = (i & 256) != 0 ? null : baselineShift;
        TextGeometricTransform textGeometricTransform2 = (i & 512) != 0 ? null : textGeometricTransform;
        LocaleList localeList2 = (i & 1024) != 0 ? null : localeList;
        if ((i & 2048) != 0) {
            Color.Companion.getClass();
            j9 = Color.Unspecified;
        } else {
            j9 = j4;
        }
        TextDecoration textDecoration2 = (i & 4096) != 0 ? null : textDecoration;
        long j11 = j6;
        Shadow shadow2 = (i & 8192) != 0 ? null : shadow;
        DrawStyle drawStyle2 = (i & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? null : drawStyle;
        TextAlign textAlign2 = (i & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0 ? null : textAlign;
        TextDirection textDirection2 = (i & 65536) != 0 ? null : textDirection;
        if ((i & 131072) != 0) {
            TextUnit.Companion.getClass();
            j10 = TextUnit.Unspecified;
        } else {
            j10 = j5;
        }
        TextIndent textIndent2 = (i & 262144) != 0 ? null : textIndent;
        PlatformTextStyle platformTextStyle2 = (i & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0 ? null : platformTextStyle;
        LineHeightStyle lineHeightStyle2 = (i & 1048576) != 0 ? null : lineHeightStyle;
        LineBreak lineBreak2 = (i & 2097152) != 0 ? null : lineBreak;
        Hyphens hyphens2 = (i & 4194304) != 0 ? null : hyphens;
        long j12 = j7;
        FontWeight fontWeight3 = fontWeight2;
        TextDecoration textDecoration3 = textDecoration2;
        FontStyle fontStyle3 = fontStyle2;
        FontSynthesis fontSynthesis3 = fontSynthesis2;
        FontFamily fontFamily3 = fontFamily2;
        String str3 = str2;
        long j13 = j8;
        BaselineShift baselineShift3 = baselineShift2;
        TextGeometricTransform textGeometricTransform3 = textGeometricTransform2;
        LocaleList localeList3 = localeList2;
        long j14 = j9;
        this(j11, j12, fontWeight3, fontStyle3, fontSynthesis3, fontFamily3, str3, j13, baselineShift3, textGeometricTransform3, localeList3, j14, textDecoration3, shadow2, drawStyle2, textAlign2, textDirection2, j10, textIndent2, platformTextStyle2, lineHeightStyle2, lineBreak2, hyphens2, (i & 8388608) != 0 ? null : textMotion, (DefaultConstructorMarker) null);
    }

    private TextStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, DrawStyle drawStyle, TextAlign textAlign, TextDirection textDirection, long j5, TextIndent textIndent, PlatformTextStyle platformTextStyle, LineHeightStyle lineHeightStyle, LineBreak lineBreak, Hyphens hyphens, TextMotion textMotion) {
        int i;
        int i2;
        int i3;
        int i4;
        SpanStyle spanStyle = new SpanStyle(j, j2, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j3, baselineShift, textGeometricTransform, localeList, j4, textDecoration, shadow, platformTextStyle != null ? platformTextStyle.spanStyle : null, drawStyle, (DefaultConstructorMarker) null);
        if (textAlign != null) {
            i = textAlign.value;
        } else {
            TextAlign.Companion.getClass();
            i = TextAlign.Unspecified;
        }
        if (textDirection != null) {
            i2 = textDirection.value;
        } else {
            TextDirection.Companion.getClass();
            i2 = TextDirection.Unspecified;
        }
        PlatformParagraphStyle platformParagraphStyle = platformTextStyle != null ? platformTextStyle.paragraphStyle : null;
        if (lineBreak != null) {
            i3 = lineBreak.mask;
        } else {
            LineBreak.Companion.getClass();
            i3 = 0;
        }
        if (hyphens != null) {
            i4 = hyphens.value;
        } else {
            Hyphens.Companion.getClass();
            i4 = Hyphens.Unspecified;
        }
        this(spanStyle, new ParagraphStyle(i, i2, j5, textIndent, platformParagraphStyle, lineHeightStyle, i3, i4, textMotion, (DefaultConstructorMarker) null), platformTextStyle);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public TextStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, DrawStyle drawStyle, int i, int i2, long j5, TextIndent textIndent, PlatformTextStyle platformTextStyle, LineHeightStyle lineHeightStyle, int i3, int i4, TextMotion textMotion, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        long j6;
        long j7;
        long j8;
        long j9;
        int i6;
        int i7;
        long j10;
        int i8;
        int i9;
        if ((i5 & 1) != 0) {
            Color.Companion.getClass();
            j6 = Color.Unspecified;
        } else {
            j6 = j;
        }
        if ((i5 & 2) != 0) {
            TextUnit.Companion.getClass();
            j7 = TextUnit.Unspecified;
        } else {
            j7 = j2;
        }
        FontWeight fontWeight2 = (i5 & 4) != 0 ? null : fontWeight;
        FontStyle fontStyle2 = (i5 & 8) != 0 ? null : fontStyle;
        FontSynthesis fontSynthesis2 = (i5 & 16) != 0 ? null : fontSynthesis;
        FontFamily fontFamily2 = (i5 & 32) != 0 ? null : fontFamily;
        String str2 = (i5 & 64) != 0 ? null : str;
        if ((i5 & 128) != 0) {
            TextUnit.Companion.getClass();
            j8 = TextUnit.Unspecified;
        } else {
            j8 = j3;
        }
        BaselineShift baselineShift2 = (i5 & 256) != 0 ? null : baselineShift;
        TextGeometricTransform textGeometricTransform2 = (i5 & 512) != 0 ? null : textGeometricTransform;
        LocaleList localeList2 = (i5 & 1024) != 0 ? null : localeList;
        if ((i5 & 2048) != 0) {
            Color.Companion.getClass();
            j9 = Color.Unspecified;
        } else {
            j9 = j4;
        }
        TextDecoration textDecoration2 = (i5 & 4096) != 0 ? null : textDecoration;
        long j11 = j6;
        Shadow shadow2 = (i5 & 8192) != 0 ? null : shadow;
        DrawStyle drawStyle2 = (i5 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? null : drawStyle;
        if ((i5 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0) {
            TextAlign.Companion.getClass();
            i6 = TextAlign.Unspecified;
        } else {
            i6 = i;
        }
        if ((i5 & 65536) != 0) {
            TextDirection.Companion.getClass();
            i7 = TextDirection.Unspecified;
        } else {
            i7 = i2;
        }
        if ((i5 & 131072) != 0) {
            TextUnit.Companion.getClass();
            j10 = TextUnit.Unspecified;
        } else {
            j10 = j5;
        }
        TextIndent textIndent2 = (i5 & 262144) != 0 ? null : textIndent;
        PlatformTextStyle platformTextStyle2 = (i5 & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0 ? null : platformTextStyle;
        LineHeightStyle lineHeightStyle2 = (i5 & 1048576) != 0 ? null : lineHeightStyle;
        if ((i5 & 2097152) != 0) {
            LineBreak.Companion.getClass();
            i8 = 0;
        } else {
            i8 = i3;
        }
        if ((i5 & 4194304) != 0) {
            Hyphens.Companion.getClass();
            i9 = Hyphens.Unspecified;
        } else {
            i9 = i4;
        }
        long j12 = j7;
        FontWeight fontWeight3 = fontWeight2;
        TextDecoration textDecoration3 = textDecoration2;
        FontStyle fontStyle3 = fontStyle2;
        FontSynthesis fontSynthesis3 = fontSynthesis2;
        FontFamily fontFamily3 = fontFamily2;
        String str3 = str2;
        long j13 = j8;
        BaselineShift baselineShift3 = baselineShift2;
        TextGeometricTransform textGeometricTransform3 = textGeometricTransform2;
        LocaleList localeList3 = localeList2;
        long j14 = j9;
        this(j11, j12, fontWeight3, fontStyle3, fontSynthesis3, fontFamily3, str3, j13, baselineShift3, textGeometricTransform3, localeList3, j14, textDecoration3, shadow2, drawStyle2, i6, i7, j10, textIndent2, platformTextStyle2, lineHeightStyle2, i8, i9, (i5 & 8388608) != 0 ? null : textMotion, (DefaultConstructorMarker) null);
    }

    private TextStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, DrawStyle drawStyle, int i, int i2, long j5, TextIndent textIndent, PlatformTextStyle platformTextStyle, LineHeightStyle lineHeightStyle, int i3, int i4, TextMotion textMotion) {
        this(new SpanStyle(j, j2, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j3, baselineShift, textGeometricTransform, localeList, j4, textDecoration, shadow, platformTextStyle != null ? platformTextStyle.spanStyle : null, drawStyle, (DefaultConstructorMarker) null), new ParagraphStyle(i, i2, j5, textIndent, platformTextStyle != null ? platformTextStyle.paragraphStyle : null, lineHeightStyle, i3, i4, textMotion, (DefaultConstructorMarker) null), platformTextStyle);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public TextStyle(Brush brush, float f, long j, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j2, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j3, TextDecoration textDecoration, Shadow shadow, DrawStyle drawStyle, int i, int i2, long j4, TextIndent textIndent, PlatformTextStyle platformTextStyle, LineHeightStyle lineHeightStyle, int i3, int i4, TextMotion textMotion, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        long j5;
        long j6;
        long j7;
        int i6;
        int i7;
        long j8;
        int i8;
        int i9;
        float f2 = (i5 & 2) != 0 ? Float.NaN : f;
        if ((i5 & 4) != 0) {
            TextUnit.Companion.getClass();
            j5 = TextUnit.Unspecified;
        } else {
            j5 = j;
        }
        FontWeight fontWeight2 = (i5 & 8) != 0 ? null : fontWeight;
        FontStyle fontStyle2 = (i5 & 16) != 0 ? null : fontStyle;
        FontSynthesis fontSynthesis2 = (i5 & 32) != 0 ? null : fontSynthesis;
        FontFamily fontFamily2 = (i5 & 64) != 0 ? null : fontFamily;
        String str2 = (i5 & 128) != 0 ? null : str;
        if ((i5 & 256) != 0) {
            TextUnit.Companion.getClass();
            j6 = TextUnit.Unspecified;
        } else {
            j6 = j2;
        }
        BaselineShift baselineShift2 = (i5 & 512) != 0 ? null : baselineShift;
        TextGeometricTransform textGeometricTransform2 = (i5 & 1024) != 0 ? null : textGeometricTransform;
        LocaleList localeList2 = (i5 & 2048) != 0 ? null : localeList;
        if ((i5 & 4096) != 0) {
            Color.Companion.getClass();
            j7 = Color.Unspecified;
        } else {
            j7 = j3;
        }
        TextDecoration textDecoration2 = (i5 & 8192) != 0 ? null : textDecoration;
        Shadow shadow2 = (i5 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? null : shadow;
        DrawStyle drawStyle2 = (32768 & i5) != 0 ? null : drawStyle;
        if ((65536 & i5) != 0) {
            TextAlign.Companion.getClass();
            i6 = TextAlign.Unspecified;
        } else {
            i6 = i;
        }
        if ((131072 & i5) != 0) {
            TextDirection.Companion.getClass();
            i7 = TextDirection.Unspecified;
        } else {
            i7 = i2;
        }
        if ((262144 & i5) != 0) {
            TextUnit.Companion.getClass();
            j8 = TextUnit.Unspecified;
        } else {
            j8 = j4;
        }
        TextIndent textIndent2 = (524288 & i5) != 0 ? null : textIndent;
        PlatformTextStyle platformTextStyle2 = (1048576 & i5) != 0 ? null : platformTextStyle;
        LineHeightStyle lineHeightStyle2 = (2097152 & i5) != 0 ? null : lineHeightStyle;
        if ((4194304 & i5) != 0) {
            LineBreak.Companion.getClass();
            i8 = 0;
        } else {
            i8 = i3;
        }
        if ((8388608 & i5) != 0) {
            Hyphens.Companion.getClass();
            i9 = Hyphens.Unspecified;
        } else {
            i9 = i4;
        }
        this(brush, f2, j5, fontWeight2, fontStyle2, fontSynthesis2, fontFamily2, str2, j6, baselineShift2, textGeometricTransform2, localeList2, j7, textDecoration2, shadow2, drawStyle2, i6, i7, j8, textIndent2, platformTextStyle2, lineHeightStyle2, i8, i9, (i5 & 16777216) != 0 ? null : textMotion, (DefaultConstructorMarker) null);
    }

    private TextStyle(Brush brush, float f, long j, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j2, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j3, TextDecoration textDecoration, Shadow shadow, DrawStyle drawStyle, int i, int i2, long j4, TextIndent textIndent, PlatformTextStyle platformTextStyle, LineHeightStyle lineHeightStyle, int i3, int i4, TextMotion textMotion) {
        this(new SpanStyle(brush, f, j, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j2, baselineShift, textGeometricTransform, localeList, j3, textDecoration, shadow, platformTextStyle != null ? platformTextStyle.spanStyle : null, drawStyle, (DefaultConstructorMarker) null), new ParagraphStyle(i, i2, j4, textIndent, platformTextStyle != null ? platformTextStyle.paragraphStyle : null, lineHeightStyle, i3, i4, textMotion, (DefaultConstructorMarker) null), platformTextStyle);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public TextStyle(Brush brush, float f, long j, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j2, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j3, TextDecoration textDecoration, Shadow shadow, DrawStyle drawStyle, TextAlign textAlign, TextDirection textDirection, long j4, TextIndent textIndent, PlatformTextStyle platformTextStyle, LineHeightStyle lineHeightStyle, LineBreak lineBreak, Hyphens hyphens, TextMotion textMotion, int i, DefaultConstructorMarker defaultConstructorMarker) {
        long j5;
        long j6;
        long j7;
        long j8;
        float f2 = (i & 2) != 0 ? Float.NaN : f;
        if ((i & 4) != 0) {
            TextUnit.Companion.getClass();
            j5 = TextUnit.Unspecified;
        } else {
            j5 = j;
        }
        FontWeight fontWeight2 = (i & 8) != 0 ? null : fontWeight;
        FontStyle fontStyle2 = (i & 16) != 0 ? null : fontStyle;
        FontSynthesis fontSynthesis2 = (i & 32) != 0 ? null : fontSynthesis;
        FontFamily fontFamily2 = (i & 64) != 0 ? null : fontFamily;
        String str2 = (i & 128) != 0 ? null : str;
        if ((i & 256) != 0) {
            TextUnit.Companion.getClass();
            j6 = TextUnit.Unspecified;
        } else {
            j6 = j2;
        }
        BaselineShift baselineShift2 = (i & 512) != 0 ? null : baselineShift;
        TextGeometricTransform textGeometricTransform2 = (i & 1024) != 0 ? null : textGeometricTransform;
        LocaleList localeList2 = (i & 2048) != 0 ? null : localeList;
        if ((i & 4096) != 0) {
            Color.Companion.getClass();
            j7 = Color.Unspecified;
        } else {
            j7 = j3;
        }
        TextDecoration textDecoration2 = (i & 8192) != 0 ? null : textDecoration;
        Shadow shadow2 = (i & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? null : shadow;
        DrawStyle drawStyle2 = (32768 & i) != 0 ? null : drawStyle;
        TextAlign textAlign2 = (65536 & i) != 0 ? null : textAlign;
        TextDirection textDirection2 = (131072 & i) != 0 ? null : textDirection;
        if ((262144 & i) != 0) {
            TextUnit.Companion.getClass();
            j8 = TextUnit.Unspecified;
        } else {
            j8 = j4;
        }
        this(brush, f2, j5, fontWeight2, fontStyle2, fontSynthesis2, fontFamily2, str2, j6, baselineShift2, textGeometricTransform2, localeList2, j7, textDecoration2, shadow2, drawStyle2, textAlign2, textDirection2, j8, (524288 & i) != 0 ? null : textIndent, (1048576 & i) != 0 ? null : platformTextStyle, (2097152 & i) != 0 ? null : lineHeightStyle, (4194304 & i) != 0 ? null : lineBreak, (8388608 & i) != 0 ? null : hyphens, (i & 16777216) != 0 ? null : textMotion, (DefaultConstructorMarker) null);
    }

    private TextStyle(Brush brush, float f, long j, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j2, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j3, TextDecoration textDecoration, Shadow shadow, DrawStyle drawStyle, TextAlign textAlign, TextDirection textDirection, long j4, TextIndent textIndent, PlatformTextStyle platformTextStyle, LineHeightStyle lineHeightStyle, LineBreak lineBreak, Hyphens hyphens, TextMotion textMotion) {
        int i;
        int i2;
        int i3;
        int i4;
        SpanStyle spanStyle = new SpanStyle(brush, f, j, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j2, baselineShift, textGeometricTransform, localeList, j3, textDecoration, shadow, platformTextStyle != null ? platformTextStyle.spanStyle : null, drawStyle, (DefaultConstructorMarker) null);
        if (textAlign != null) {
            i = textAlign.value;
        } else {
            TextAlign.Companion.getClass();
            i = TextAlign.Unspecified;
        }
        if (textDirection != null) {
            i2 = textDirection.value;
        } else {
            TextDirection.Companion.getClass();
            i2 = TextDirection.Unspecified;
        }
        PlatformParagraphStyle platformParagraphStyle = platformTextStyle != null ? platformTextStyle.paragraphStyle : null;
        if (lineBreak != null) {
            i3 = lineBreak.mask;
        } else {
            LineBreak.Companion.getClass();
            i3 = 0;
        }
        if (hyphens != null) {
            i4 = hyphens.value;
        } else {
            Hyphens.Companion.getClass();
            i4 = Hyphens.Unspecified;
        }
        this(spanStyle, new ParagraphStyle(i, i2, j4, textIndent, platformParagraphStyle, lineHeightStyle, i3, i4, textMotion, (DefaultConstructorMarker) null), platformTextStyle);
    }
}
