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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TextStyle {
    public static final Companion Companion = new Companion(null);
    public static final TextStyle Default = new TextStyle(0, 0, (FontWeight) null, (FontStyle) null, (FontSynthesis) null, (FontFamily) null, (String) null, 0, (BaselineShift) null, (TextGeometricTransform) null, (LocaleList) null, 0, (TextDecoration) null, (Shadow) null, (DrawStyle) null, 0, 0, 0, (TextIndent) null, (PlatformTextStyle) null, (LineHeightStyle) null, 0, 0, (TextMotion) null, 16777215, (DefaultConstructorMarker) null);
    public final ParagraphStyle paragraphStyle;
    public final PlatformTextStyle platformStyle;
    public final SpanStyle spanStyle;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public static TextStyle m754copyp1EtxEg$default(TextStyle textStyle, long j, long j2, FontWeight fontWeight, FontFamily fontFamily, long j3, int i, long j4, PlatformTextStyle platformTextStyle, LineHeightStyle lineHeightStyle, int i2, int i3) {
        TextForegroundStyle m810from8_81llA;
        long mo792getColor0d7_KjU = (i3 & 1) != 0 ? textStyle.spanStyle.textForegroundStyle.mo792getColor0d7_KjU() : j;
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
        long mo792getColor0d7_KjU2 = spanStyle.textForegroundStyle.mo792getColor0d7_KjU();
        Color.Companion companion = Color.Companion;
        if (ULong.m3427equalsimpl0(mo792getColor0d7_KjU, mo792getColor0d7_KjU2)) {
            m810from8_81llA = spanStyle.textForegroundStyle;
        } else {
            TextForegroundStyle.Companion.getClass();
            m810from8_81llA = TextForegroundStyle.Companion.m810from8_81llA(mo792getColor0d7_KjU);
        }
        return new TextStyle(new SpanStyle(m810from8_81llA, j5, fontWeight2, fontStyle, fontSynthesis, fontFamily2, str, j6, baselineShift, textGeometricTransform, localeList, j7, textDecoration, shadow, platformTextStyle2 != null ? platformTextStyle2.spanStyle : null, drawStyle, (DefaultConstructorMarker) null), new ParagraphStyle(i4, i5, j8, textIndent, platformTextStyle2 != null ? platformTextStyle2.paragraphStyle : null, lineHeightStyle2, i6, i8, textMotion, (DefaultConstructorMarker) null), platformTextStyle2);
    }

    /* renamed from: merge-dA7vx0o$default, reason: not valid java name */
    public static TextStyle m755mergedA7vx0o$default(TextStyle textStyle, long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontFamily fontFamily, long j3, TextDecoration textDecoration, int i, long j4, int i2) {
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
        SpanStyle m741fastMergedSHsh3o = SpanStyleKt.m741fastMergedSHsh3o(textStyle.spanStyle, j5, null, Float.NaN, j6, fontWeight2, fontStyle2, null, fontFamily2, null, j7, null, null, null, j9, textDecoration2, null, null, null);
        ParagraphStyle m739fastMergej5T8yCg = ParagraphStyleKt.m739fastMergej5T8yCg(textStyle.paragraphStyle, i3, i4, j8, null, null, null, 0, i5, null);
        return (textStyle.spanStyle == m741fastMergedSHsh3o && textStyle.paragraphStyle == m739fastMergej5T8yCg) ? textStyle : new TextStyle(m741fastMergedSHsh3o, m739fastMergej5T8yCg);
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
    public final long m756getColor0d7_KjU() {
        return this.spanStyle.textForegroundStyle.mo792getColor0d7_KjU();
    }

    public final boolean hasSameLayoutAffectingAttributes(TextStyle textStyle) {
        if (this != textStyle) {
            return Intrinsics.areEqual(this.paragraphStyle, textStyle.paragraphStyle) && this.spanStyle.hasSameLayoutAffectingAttributes$ui_text_release(textStyle.spanStyle);
        }
        return true;
    }

    public final int hashCode() {
        int hashCode = (this.paragraphStyle.hashCode() + (this.spanStyle.hashCode() * 31)) * 31;
        PlatformTextStyle platformTextStyle = this.platformStyle;
        return hashCode + (platformTextStyle != null ? platformTextStyle.hashCode() : 0);
    }

    public final TextStyle merge(TextStyle textStyle) {
        return (textStyle == null || textStyle.equals(Default)) ? this : new TextStyle(this.spanStyle.merge(textStyle.spanStyle), this.paragraphStyle.merge(textStyle.paragraphStyle));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("TextStyle(color=");
        sb.append((Object) Color.m462toStringimpl(m756getColor0d7_KjU()));
        sb.append(", brush=");
        SpanStyle spanStyle = this.spanStyle;
        sb.append(spanStyle.textForegroundStyle.getBrush());
        sb.append(", alpha=");
        sb.append(spanStyle.textForegroundStyle.getAlpha());
        sb.append(", fontSize=");
        sb.append((Object) TextUnit.m870toStringimpl(spanStyle.fontSize));
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
        sb.append((Object) TextUnit.m870toStringimpl(spanStyle.letterSpacing));
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
        sb.append((Object) TextAlign.m806toStringimpl(paragraphStyle.textAlign));
        sb.append(", textDirection=");
        sb.append((Object) TextDirection.m808toStringimpl(paragraphStyle.textDirection));
        sb.append(", lineHeight=");
        sb.append((Object) TextUnit.m870toStringimpl(paragraphStyle.lineHeight));
        sb.append(", textIndent=");
        sb.append(paragraphStyle.textIndent);
        sb.append(", platformStyle=");
        sb.append(this.platformStyle);
        sb.append(", lineHeightStyle=");
        sb.append(paragraphStyle.lineHeightStyle);
        sb.append(", lineBreak=");
        sb.append((Object) LineBreak.m796toStringimpl(paragraphStyle.lineBreak));
        sb.append(", hyphens=");
        sb.append((Object) Hyphens.m794toStringimpl(paragraphStyle.hyphens));
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
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TextStyle(long r25, long r27, androidx.compose.ui.text.font.FontWeight r29, androidx.compose.ui.text.font.FontStyle r30, androidx.compose.ui.text.font.FontSynthesis r31, androidx.compose.ui.text.font.FontFamily r32, java.lang.String r33, long r34, androidx.compose.ui.text.style.BaselineShift r36, androidx.compose.ui.text.style.TextGeometricTransform r37, androidx.compose.ui.text.intl.LocaleList r38, long r39, androidx.compose.ui.text.style.TextDecoration r41, androidx.compose.ui.graphics.Shadow r42, androidx.compose.ui.text.style.TextAlign r43, androidx.compose.ui.text.style.TextDirection r44, long r45, androidx.compose.ui.text.style.TextIndent r47, int r48, kotlin.jvm.internal.DefaultConstructorMarker r49) {
        /*
            Method dump skipped, instructions count: 232
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.TextStyle.<init>(long, long, androidx.compose.ui.text.font.FontWeight, androidx.compose.ui.text.font.FontStyle, androidx.compose.ui.text.font.FontSynthesis, androidx.compose.ui.text.font.FontFamily, java.lang.String, long, androidx.compose.ui.text.style.BaselineShift, androidx.compose.ui.text.style.TextGeometricTransform, androidx.compose.ui.text.intl.LocaleList, long, androidx.compose.ui.text.style.TextDecoration, androidx.compose.ui.graphics.Shadow, androidx.compose.ui.text.style.TextAlign, androidx.compose.ui.text.style.TextDirection, long, androidx.compose.ui.text.style.TextIndent, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private TextStyle(long r25, long r27, androidx.compose.ui.text.font.FontWeight r29, androidx.compose.ui.text.font.FontStyle r30, androidx.compose.ui.text.font.FontSynthesis r31, androidx.compose.ui.text.font.FontFamily r32, java.lang.String r33, long r34, androidx.compose.ui.text.style.BaselineShift r36, androidx.compose.ui.text.style.TextGeometricTransform r37, androidx.compose.ui.text.intl.LocaleList r38, long r39, androidx.compose.ui.text.style.TextDecoration r41, androidx.compose.ui.graphics.Shadow r42, androidx.compose.ui.text.style.TextAlign r43, androidx.compose.ui.text.style.TextDirection r44, long r45, androidx.compose.ui.text.style.TextIndent r47) {
        /*
            r24 = this;
            r0 = r43
            r1 = r44
            androidx.compose.ui.text.SpanStyle r2 = new androidx.compose.ui.text.SpanStyle
            r22 = 0
            r23 = 0
            r21 = 0
            r3 = r25
            r5 = r27
            r7 = r29
            r8 = r30
            r9 = r31
            r10 = r32
            r11 = r33
            r12 = r34
            r14 = r36
            r15 = r37
            r16 = r38
            r17 = r39
            r19 = r41
            r20 = r42
            r2.<init>(r3, r5, r7, r8, r9, r10, r11, r12, r14, r15, r16, r17, r19, r20, r21, r22, r23)
            androidx.compose.ui.text.ParagraphStyle r3 = new androidx.compose.ui.text.ParagraphStyle
            if (r0 == 0) goto L32
            int r0 = r0.value
            goto L39
        L32:
            androidx.compose.ui.text.style.TextAlign$Companion r0 = androidx.compose.ui.text.style.TextAlign.Companion
            r0.getClass()
            int r0 = androidx.compose.ui.text.style.TextAlign.Unspecified
        L39:
            if (r1 == 0) goto L3e
            int r1 = r1.value
            goto L45
        L3e:
            androidx.compose.ui.text.style.TextDirection$Companion r1 = androidx.compose.ui.text.style.TextDirection.Companion
            r1.getClass()
            int r1 = androidx.compose.ui.text.style.TextDirection.Unspecified
        L45:
            androidx.compose.ui.text.style.LineBreak$Companion r4 = androidx.compose.ui.text.style.LineBreak.Companion
            r4.getClass()
            androidx.compose.ui.text.style.Hyphens$Companion r4 = androidx.compose.ui.text.style.Hyphens.Companion
            r4.getClass()
            int r4 = androidx.compose.ui.text.style.Hyphens.Unspecified
            r5 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            r28 = r45
            r30 = r47
            r26 = r0
            r27 = r1
            r25 = r3
            r34 = r4
            r35 = r5
            r36 = r6
            r31 = r7
            r32 = r8
            r33 = r9
            r25.<init>(r26, r27, r28, r30, r31, r32, r33, r34, r35, r36)
            r0 = r25
            r1 = 0
            r3 = r24
            r3.<init>(r2, r0, r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.TextStyle.<init>(long, long, androidx.compose.ui.text.font.FontWeight, androidx.compose.ui.text.font.FontStyle, androidx.compose.ui.text.font.FontSynthesis, androidx.compose.ui.text.font.FontFamily, java.lang.String, long, androidx.compose.ui.text.style.BaselineShift, androidx.compose.ui.text.style.TextGeometricTransform, androidx.compose.ui.text.intl.LocaleList, long, androidx.compose.ui.text.style.TextDecoration, androidx.compose.ui.graphics.Shadow, androidx.compose.ui.text.style.TextAlign, androidx.compose.ui.text.style.TextDirection, long, androidx.compose.ui.text.style.TextIndent):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TextStyle(androidx.compose.ui.text.SpanStyle r4, androidx.compose.ui.text.ParagraphStyle r5) {
        /*
            r3 = this;
            androidx.compose.ui.text.PlatformSpanStyle r0 = r4.platformStyle
            androidx.compose.ui.text.PlatformParagraphStyle r1 = r5.platformStyle
            if (r0 != 0) goto La
            if (r1 != 0) goto La
            r0 = 0
            goto L10
        La:
            androidx.compose.ui.text.PlatformTextStyle r2 = new androidx.compose.ui.text.PlatformTextStyle
            r2.<init>(r0, r1)
            r0 = r2
        L10:
            r3.<init>(r4, r5, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.TextStyle.<init>(androidx.compose.ui.text.SpanStyle, androidx.compose.ui.text.ParagraphStyle):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TextStyle(long r27, long r29, androidx.compose.ui.text.font.FontWeight r31, androidx.compose.ui.text.font.FontStyle r32, androidx.compose.ui.text.font.FontSynthesis r33, androidx.compose.ui.text.font.FontFamily r34, java.lang.String r35, long r36, androidx.compose.ui.text.style.BaselineShift r38, androidx.compose.ui.text.style.TextGeometricTransform r39, androidx.compose.ui.text.intl.LocaleList r40, long r41, androidx.compose.ui.text.style.TextDecoration r43, androidx.compose.ui.graphics.Shadow r44, androidx.compose.ui.text.style.TextAlign r45, androidx.compose.ui.text.style.TextDirection r46, long r47, androidx.compose.ui.text.style.TextIndent r49, androidx.compose.ui.text.PlatformTextStyle r50, androidx.compose.ui.text.style.LineHeightStyle r51, int r52, kotlin.jvm.internal.DefaultConstructorMarker r53) {
        /*
            Method dump skipped, instructions count: 258
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.TextStyle.<init>(long, long, androidx.compose.ui.text.font.FontWeight, androidx.compose.ui.text.font.FontStyle, androidx.compose.ui.text.font.FontSynthesis, androidx.compose.ui.text.font.FontFamily, java.lang.String, long, androidx.compose.ui.text.style.BaselineShift, androidx.compose.ui.text.style.TextGeometricTransform, androidx.compose.ui.text.intl.LocaleList, long, androidx.compose.ui.text.style.TextDecoration, androidx.compose.ui.graphics.Shadow, androidx.compose.ui.text.style.TextAlign, androidx.compose.ui.text.style.TextDirection, long, androidx.compose.ui.text.style.TextIndent, androidx.compose.ui.text.PlatformTextStyle, androidx.compose.ui.text.style.LineHeightStyle, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private TextStyle(long r27, long r29, androidx.compose.ui.text.font.FontWeight r31, androidx.compose.ui.text.font.FontStyle r32, androidx.compose.ui.text.font.FontSynthesis r33, androidx.compose.ui.text.font.FontFamily r34, java.lang.String r35, long r36, androidx.compose.ui.text.style.BaselineShift r38, androidx.compose.ui.text.style.TextGeometricTransform r39, androidx.compose.ui.text.intl.LocaleList r40, long r41, androidx.compose.ui.text.style.TextDecoration r43, androidx.compose.ui.graphics.Shadow r44, androidx.compose.ui.text.style.TextAlign r45, androidx.compose.ui.text.style.TextDirection r46, long r47, androidx.compose.ui.text.style.TextIndent r49, androidx.compose.ui.text.PlatformTextStyle r50, androidx.compose.ui.text.style.LineHeightStyle r51) {
        /*
            r26 = this;
            r0 = r45
            r1 = r46
            r2 = r50
            androidx.compose.ui.text.SpanStyle r3 = new androidx.compose.ui.text.SpanStyle
            r25 = 0
            if (r2 == 0) goto L11
            androidx.compose.ui.text.PlatformSpanStyle r4 = r2.spanStyle
            r22 = r4
            goto L13
        L11:
            r22 = r25
        L13:
            r23 = 0
            r24 = 0
            r4 = r27
            r6 = r29
            r8 = r31
            r9 = r32
            r10 = r33
            r11 = r34
            r12 = r35
            r13 = r36
            r15 = r38
            r16 = r39
            r17 = r40
            r18 = r41
            r20 = r43
            r21 = r44
            r3.<init>(r4, r6, r8, r9, r10, r11, r12, r13, r15, r16, r17, r18, r20, r21, r22, r23, r24)
            androidx.compose.ui.text.ParagraphStyle r4 = new androidx.compose.ui.text.ParagraphStyle
            if (r0 == 0) goto L3d
            int r0 = r0.value
            goto L44
        L3d:
            androidx.compose.ui.text.style.TextAlign$Companion r0 = androidx.compose.ui.text.style.TextAlign.Companion
            r0.getClass()
            int r0 = androidx.compose.ui.text.style.TextAlign.Unspecified
        L44:
            if (r1 == 0) goto L49
            int r1 = r1.value
            goto L50
        L49:
            androidx.compose.ui.text.style.TextDirection$Companion r1 = androidx.compose.ui.text.style.TextDirection.Companion
            r1.getClass()
            int r1 = androidx.compose.ui.text.style.TextDirection.Unspecified
        L50:
            if (r2 == 0) goto L56
            androidx.compose.ui.text.PlatformParagraphStyle r5 = r2.paragraphStyle
            r25 = r5
        L56:
            androidx.compose.ui.text.style.LineBreak$Companion r5 = androidx.compose.ui.text.style.LineBreak.Companion
            r5.getClass()
            androidx.compose.ui.text.style.Hyphens$Companion r5 = androidx.compose.ui.text.style.Hyphens.Companion
            r5.getClass()
            int r5 = androidx.compose.ui.text.style.Hyphens.Unspecified
            r6 = 0
            r7 = 0
            r8 = 0
            r30 = r47
            r32 = r49
            r34 = r51
            r28 = r0
            r29 = r1
            r27 = r4
            r36 = r5
            r37 = r6
            r38 = r7
            r35 = r8
            r33 = r25
            r27.<init>(r28, r29, r30, r32, r33, r34, r35, r36, r37, r38)
            r0 = r26
            r1 = r27
            r0.<init>(r3, r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.TextStyle.<init>(long, long, androidx.compose.ui.text.font.FontWeight, androidx.compose.ui.text.font.FontStyle, androidx.compose.ui.text.font.FontSynthesis, androidx.compose.ui.text.font.FontFamily, java.lang.String, long, androidx.compose.ui.text.style.BaselineShift, androidx.compose.ui.text.style.TextGeometricTransform, androidx.compose.ui.text.intl.LocaleList, long, androidx.compose.ui.text.style.TextDecoration, androidx.compose.ui.graphics.Shadow, androidx.compose.ui.text.style.TextAlign, androidx.compose.ui.text.style.TextDirection, long, androidx.compose.ui.text.style.TextIndent, androidx.compose.ui.text.PlatformTextStyle, androidx.compose.ui.text.style.LineHeightStyle):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TextStyle(long r29, long r31, androidx.compose.ui.text.font.FontWeight r33, androidx.compose.ui.text.font.FontStyle r34, androidx.compose.ui.text.font.FontSynthesis r35, androidx.compose.ui.text.font.FontFamily r36, java.lang.String r37, long r38, androidx.compose.ui.text.style.BaselineShift r40, androidx.compose.ui.text.style.TextGeometricTransform r41, androidx.compose.ui.text.intl.LocaleList r42, long r43, androidx.compose.ui.text.style.TextDecoration r45, androidx.compose.ui.graphics.Shadow r46, androidx.compose.ui.text.style.TextAlign r47, androidx.compose.ui.text.style.TextDirection r48, long r49, androidx.compose.ui.text.style.TextIndent r51, androidx.compose.ui.text.PlatformTextStyle r52, androidx.compose.ui.text.style.LineHeightStyle r53, androidx.compose.ui.text.style.LineBreak r54, androidx.compose.ui.text.style.Hyphens r55, int r56, kotlin.jvm.internal.DefaultConstructorMarker r57) {
        /*
            Method dump skipped, instructions count: 284
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.TextStyle.<init>(long, long, androidx.compose.ui.text.font.FontWeight, androidx.compose.ui.text.font.FontStyle, androidx.compose.ui.text.font.FontSynthesis, androidx.compose.ui.text.font.FontFamily, java.lang.String, long, androidx.compose.ui.text.style.BaselineShift, androidx.compose.ui.text.style.TextGeometricTransform, androidx.compose.ui.text.intl.LocaleList, long, androidx.compose.ui.text.style.TextDecoration, androidx.compose.ui.graphics.Shadow, androidx.compose.ui.text.style.TextAlign, androidx.compose.ui.text.style.TextDirection, long, androidx.compose.ui.text.style.TextIndent, androidx.compose.ui.text.PlatformTextStyle, androidx.compose.ui.text.style.LineHeightStyle, androidx.compose.ui.text.style.LineBreak, androidx.compose.ui.text.style.Hyphens, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private TextStyle(long r30, long r32, androidx.compose.ui.text.font.FontWeight r34, androidx.compose.ui.text.font.FontStyle r35, androidx.compose.ui.text.font.FontSynthesis r36, androidx.compose.ui.text.font.FontFamily r37, java.lang.String r38, long r39, androidx.compose.ui.text.style.BaselineShift r41, androidx.compose.ui.text.style.TextGeometricTransform r42, androidx.compose.ui.text.intl.LocaleList r43, long r44, androidx.compose.ui.text.style.TextDecoration r46, androidx.compose.ui.graphics.Shadow r47, androidx.compose.ui.text.style.TextAlign r48, androidx.compose.ui.text.style.TextDirection r49, long r50, androidx.compose.ui.text.style.TextIndent r52, androidx.compose.ui.text.PlatformTextStyle r53, androidx.compose.ui.text.style.LineHeightStyle r54, androidx.compose.ui.text.style.LineBreak r55, androidx.compose.ui.text.style.Hyphens r56) {
        /*
            r29 = this;
            r0 = r48
            r1 = r49
            r2 = r53
            r3 = r55
            r4 = r56
            androidx.compose.ui.text.SpanStyle r5 = new androidx.compose.ui.text.SpanStyle
            r28 = 0
            if (r2 == 0) goto L15
            androidx.compose.ui.text.PlatformSpanStyle r6 = r2.spanStyle
            r24 = r6
            goto L17
        L15:
            r24 = r28
        L17:
            r26 = 32768(0x8000, float:4.5918E-41)
            r27 = 0
            r25 = 0
            r6 = r30
            r8 = r32
            r10 = r34
            r11 = r35
            r12 = r36
            r13 = r37
            r14 = r38
            r15 = r39
            r17 = r41
            r18 = r42
            r19 = r43
            r20 = r44
            r22 = r46
            r23 = r47
            r5.<init>(r6, r8, r10, r11, r12, r13, r14, r15, r17, r18, r19, r20, r22, r23, r24, r25, r26, r27)
            androidx.compose.ui.text.ParagraphStyle r6 = new androidx.compose.ui.text.ParagraphStyle
            if (r0 == 0) goto L44
            int r0 = r0.value
            goto L4b
        L44:
            androidx.compose.ui.text.style.TextAlign$Companion r0 = androidx.compose.ui.text.style.TextAlign.Companion
            r0.getClass()
            int r0 = androidx.compose.ui.text.style.TextAlign.Unspecified
        L4b:
            if (r1 == 0) goto L50
            int r1 = r1.value
            goto L57
        L50:
            androidx.compose.ui.text.style.TextDirection$Companion r1 = androidx.compose.ui.text.style.TextDirection.Companion
            r1.getClass()
            int r1 = androidx.compose.ui.text.style.TextDirection.Unspecified
        L57:
            if (r2 == 0) goto L5d
            androidx.compose.ui.text.PlatformParagraphStyle r7 = r2.paragraphStyle
            r28 = r7
        L5d:
            if (r3 == 0) goto L62
            int r3 = r3.mask
            goto L68
        L62:
            androidx.compose.ui.text.style.LineBreak$Companion r3 = androidx.compose.ui.text.style.LineBreak.Companion
            r3.getClass()
            r3 = 0
        L68:
            if (r4 == 0) goto L6d
            int r4 = r4.value
            goto L74
        L6d:
            androidx.compose.ui.text.style.Hyphens$Companion r4 = androidx.compose.ui.text.style.Hyphens.Companion
            r4.getClass()
            int r4 = androidx.compose.ui.text.style.Hyphens.Unspecified
        L74:
            r7 = 256(0x100, float:3.59E-43)
            r8 = 0
            r9 = 0
            r33 = r50
            r35 = r52
            r37 = r54
            r31 = r0
            r32 = r1
            r38 = r3
            r39 = r4
            r30 = r6
            r41 = r7
            r42 = r8
            r40 = r9
            r36 = r28
            r30.<init>(r31, r32, r33, r35, r36, r37, r38, r39, r40, r41, r42)
            r0 = r29
            r1 = r30
            r0.<init>(r5, r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.TextStyle.<init>(long, long, androidx.compose.ui.text.font.FontWeight, androidx.compose.ui.text.font.FontStyle, androidx.compose.ui.text.font.FontSynthesis, androidx.compose.ui.text.font.FontFamily, java.lang.String, long, androidx.compose.ui.text.style.BaselineShift, androidx.compose.ui.text.style.TextGeometricTransform, androidx.compose.ui.text.intl.LocaleList, long, androidx.compose.ui.text.style.TextDecoration, androidx.compose.ui.graphics.Shadow, androidx.compose.ui.text.style.TextAlign, androidx.compose.ui.text.style.TextDirection, long, androidx.compose.ui.text.style.TextIndent, androidx.compose.ui.text.PlatformTextStyle, androidx.compose.ui.text.style.LineHeightStyle, androidx.compose.ui.text.style.LineBreak, androidx.compose.ui.text.style.Hyphens):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TextStyle(long r31, long r33, androidx.compose.ui.text.font.FontWeight r35, androidx.compose.ui.text.font.FontStyle r36, androidx.compose.ui.text.font.FontSynthesis r37, androidx.compose.ui.text.font.FontFamily r38, java.lang.String r39, long r40, androidx.compose.ui.text.style.BaselineShift r42, androidx.compose.ui.text.style.TextGeometricTransform r43, androidx.compose.ui.text.intl.LocaleList r44, long r45, androidx.compose.ui.text.style.TextDecoration r47, androidx.compose.ui.graphics.Shadow r48, androidx.compose.ui.graphics.drawscope.DrawStyle r49, androidx.compose.ui.text.style.TextAlign r50, androidx.compose.ui.text.style.TextDirection r51, long r52, androidx.compose.ui.text.style.TextIndent r54, androidx.compose.ui.text.PlatformTextStyle r55, androidx.compose.ui.text.style.LineHeightStyle r56, androidx.compose.ui.text.style.LineBreak r57, androidx.compose.ui.text.style.Hyphens r58, androidx.compose.ui.text.style.TextMotion r59, int r60, kotlin.jvm.internal.DefaultConstructorMarker r61) {
        /*
            Method dump skipped, instructions count: 310
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.TextStyle.<init>(long, long, androidx.compose.ui.text.font.FontWeight, androidx.compose.ui.text.font.FontStyle, androidx.compose.ui.text.font.FontSynthesis, androidx.compose.ui.text.font.FontFamily, java.lang.String, long, androidx.compose.ui.text.style.BaselineShift, androidx.compose.ui.text.style.TextGeometricTransform, androidx.compose.ui.text.intl.LocaleList, long, androidx.compose.ui.text.style.TextDecoration, androidx.compose.ui.graphics.Shadow, androidx.compose.ui.graphics.drawscope.DrawStyle, androidx.compose.ui.text.style.TextAlign, androidx.compose.ui.text.style.TextDirection, long, androidx.compose.ui.text.style.TextIndent, androidx.compose.ui.text.PlatformTextStyle, androidx.compose.ui.text.style.LineHeightStyle, androidx.compose.ui.text.style.LineBreak, androidx.compose.ui.text.style.Hyphens, androidx.compose.ui.text.style.TextMotion, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private TextStyle(long r29, long r31, androidx.compose.ui.text.font.FontWeight r33, androidx.compose.ui.text.font.FontStyle r34, androidx.compose.ui.text.font.FontSynthesis r35, androidx.compose.ui.text.font.FontFamily r36, java.lang.String r37, long r38, androidx.compose.ui.text.style.BaselineShift r40, androidx.compose.ui.text.style.TextGeometricTransform r41, androidx.compose.ui.text.intl.LocaleList r42, long r43, androidx.compose.ui.text.style.TextDecoration r45, androidx.compose.ui.graphics.Shadow r46, androidx.compose.ui.graphics.drawscope.DrawStyle r47, androidx.compose.ui.text.style.TextAlign r48, androidx.compose.ui.text.style.TextDirection r49, long r50, androidx.compose.ui.text.style.TextIndent r52, androidx.compose.ui.text.PlatformTextStyle r53, androidx.compose.ui.text.style.LineHeightStyle r54, androidx.compose.ui.text.style.LineBreak r55, androidx.compose.ui.text.style.Hyphens r56, androidx.compose.ui.text.style.TextMotion r57) {
        /*
            r28 = this;
            r0 = r48
            r1 = r49
            r2 = r53
            r3 = r55
            r4 = r56
            androidx.compose.ui.text.SpanStyle r5 = new androidx.compose.ui.text.SpanStyle
            r27 = 0
            if (r2 == 0) goto L15
            androidx.compose.ui.text.PlatformSpanStyle r6 = r2.spanStyle
            r24 = r6
            goto L17
        L15:
            r24 = r27
        L17:
            r26 = 0
            r6 = r29
            r8 = r31
            r10 = r33
            r11 = r34
            r12 = r35
            r13 = r36
            r14 = r37
            r15 = r38
            r17 = r40
            r18 = r41
            r19 = r42
            r20 = r43
            r22 = r45
            r23 = r46
            r25 = r47
            r5.<init>(r6, r8, r10, r11, r12, r13, r14, r15, r17, r18, r19, r20, r22, r23, r24, r25, r26)
            androidx.compose.ui.text.ParagraphStyle r6 = new androidx.compose.ui.text.ParagraphStyle
            if (r0 == 0) goto L41
            int r0 = r0.value
            goto L48
        L41:
            androidx.compose.ui.text.style.TextAlign$Companion r0 = androidx.compose.ui.text.style.TextAlign.Companion
            r0.getClass()
            int r0 = androidx.compose.ui.text.style.TextAlign.Unspecified
        L48:
            if (r1 == 0) goto L4d
            int r1 = r1.value
            goto L54
        L4d:
            androidx.compose.ui.text.style.TextDirection$Companion r1 = androidx.compose.ui.text.style.TextDirection.Companion
            r1.getClass()
            int r1 = androidx.compose.ui.text.style.TextDirection.Unspecified
        L54:
            if (r2 == 0) goto L5a
            androidx.compose.ui.text.PlatformParagraphStyle r7 = r2.paragraphStyle
            r27 = r7
        L5a:
            if (r3 == 0) goto L5f
            int r3 = r3.mask
            goto L65
        L5f:
            androidx.compose.ui.text.style.LineBreak$Companion r3 = androidx.compose.ui.text.style.LineBreak.Companion
            r3.getClass()
            r3 = 0
        L65:
            if (r4 == 0) goto L6a
            int r4 = r4.value
            goto L71
        L6a:
            androidx.compose.ui.text.style.Hyphens$Companion r4 = androidx.compose.ui.text.style.Hyphens.Companion
            r4.getClass()
            int r4 = androidx.compose.ui.text.style.Hyphens.Unspecified
        L71:
            r7 = 0
            r32 = r50
            r34 = r52
            r36 = r54
            r39 = r57
            r30 = r0
            r31 = r1
            r37 = r3
            r38 = r4
            r29 = r6
            r40 = r7
            r35 = r27
            r29.<init>(r30, r31, r32, r34, r35, r36, r37, r38, r39, r40)
            r0 = r28
            r1 = r29
            r0.<init>(r5, r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.TextStyle.<init>(long, long, androidx.compose.ui.text.font.FontWeight, androidx.compose.ui.text.font.FontStyle, androidx.compose.ui.text.font.FontSynthesis, androidx.compose.ui.text.font.FontFamily, java.lang.String, long, androidx.compose.ui.text.style.BaselineShift, androidx.compose.ui.text.style.TextGeometricTransform, androidx.compose.ui.text.intl.LocaleList, long, androidx.compose.ui.text.style.TextDecoration, androidx.compose.ui.graphics.Shadow, androidx.compose.ui.graphics.drawscope.DrawStyle, androidx.compose.ui.text.style.TextAlign, androidx.compose.ui.text.style.TextDirection, long, androidx.compose.ui.text.style.TextIndent, androidx.compose.ui.text.PlatformTextStyle, androidx.compose.ui.text.style.LineHeightStyle, androidx.compose.ui.text.style.LineBreak, androidx.compose.ui.text.style.Hyphens, androidx.compose.ui.text.style.TextMotion):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TextStyle(long r31, long r33, androidx.compose.ui.text.font.FontWeight r35, androidx.compose.ui.text.font.FontStyle r36, androidx.compose.ui.text.font.FontSynthesis r37, androidx.compose.ui.text.font.FontFamily r38, java.lang.String r39, long r40, androidx.compose.ui.text.style.BaselineShift r42, androidx.compose.ui.text.style.TextGeometricTransform r43, androidx.compose.ui.text.intl.LocaleList r44, long r45, androidx.compose.ui.text.style.TextDecoration r47, androidx.compose.ui.graphics.Shadow r48, androidx.compose.ui.graphics.drawscope.DrawStyle r49, int r50, int r51, long r52, androidx.compose.ui.text.style.TextIndent r54, androidx.compose.ui.text.PlatformTextStyle r55, androidx.compose.ui.text.style.LineHeightStyle r56, int r57, int r58, androidx.compose.ui.text.style.TextMotion r59, int r60, kotlin.jvm.internal.DefaultConstructorMarker r61) {
        /*
            Method dump skipped, instructions count: 330
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.TextStyle.<init>(long, long, androidx.compose.ui.text.font.FontWeight, androidx.compose.ui.text.font.FontStyle, androidx.compose.ui.text.font.FontSynthesis, androidx.compose.ui.text.font.FontFamily, java.lang.String, long, androidx.compose.ui.text.style.BaselineShift, androidx.compose.ui.text.style.TextGeometricTransform, androidx.compose.ui.text.intl.LocaleList, long, androidx.compose.ui.text.style.TextDecoration, androidx.compose.ui.graphics.Shadow, androidx.compose.ui.graphics.drawscope.DrawStyle, int, int, long, androidx.compose.ui.text.style.TextIndent, androidx.compose.ui.text.PlatformTextStyle, androidx.compose.ui.text.style.LineHeightStyle, int, int, androidx.compose.ui.text.style.TextMotion, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private TextStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, DrawStyle drawStyle, int i, int i2, long j5, TextIndent textIndent, PlatformTextStyle platformTextStyle, LineHeightStyle lineHeightStyle, int i3, int i4, TextMotion textMotion) {
        this(new SpanStyle(j, j2, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j3, baselineShift, textGeometricTransform, localeList, j4, textDecoration, shadow, platformTextStyle != null ? platformTextStyle.spanStyle : null, drawStyle, (DefaultConstructorMarker) null), new ParagraphStyle(i, i2, j5, textIndent, platformTextStyle != null ? platformTextStyle.paragraphStyle : null, lineHeightStyle, i3, i4, textMotion, (DefaultConstructorMarker) null), platformTextStyle);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TextStyle(androidx.compose.ui.graphics.Brush r34, float r35, long r36, androidx.compose.ui.text.font.FontWeight r38, androidx.compose.ui.text.font.FontStyle r39, androidx.compose.ui.text.font.FontSynthesis r40, androidx.compose.ui.text.font.FontFamily r41, java.lang.String r42, long r43, androidx.compose.ui.text.style.BaselineShift r45, androidx.compose.ui.text.style.TextGeometricTransform r46, androidx.compose.ui.text.intl.LocaleList r47, long r48, androidx.compose.ui.text.style.TextDecoration r50, androidx.compose.ui.graphics.Shadow r51, androidx.compose.ui.graphics.drawscope.DrawStyle r52, int r53, int r54, long r55, androidx.compose.ui.text.style.TextIndent r57, androidx.compose.ui.text.PlatformTextStyle r58, androidx.compose.ui.text.style.LineHeightStyle r59, int r60, int r61, androidx.compose.ui.text.style.TextMotion r62, int r63, kotlin.jvm.internal.DefaultConstructorMarker r64) {
        /*
            Method dump skipped, instructions count: 282
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.TextStyle.<init>(androidx.compose.ui.graphics.Brush, float, long, androidx.compose.ui.text.font.FontWeight, androidx.compose.ui.text.font.FontStyle, androidx.compose.ui.text.font.FontSynthesis, androidx.compose.ui.text.font.FontFamily, java.lang.String, long, androidx.compose.ui.text.style.BaselineShift, androidx.compose.ui.text.style.TextGeometricTransform, androidx.compose.ui.text.intl.LocaleList, long, androidx.compose.ui.text.style.TextDecoration, androidx.compose.ui.graphics.Shadow, androidx.compose.ui.graphics.drawscope.DrawStyle, int, int, long, androidx.compose.ui.text.style.TextIndent, androidx.compose.ui.text.PlatformTextStyle, androidx.compose.ui.text.style.LineHeightStyle, int, int, androidx.compose.ui.text.style.TextMotion, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    private TextStyle(Brush brush, float f, long j, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j2, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j3, TextDecoration textDecoration, Shadow shadow, DrawStyle drawStyle, int i, int i2, long j4, TextIndent textIndent, PlatformTextStyle platformTextStyle, LineHeightStyle lineHeightStyle, int i3, int i4, TextMotion textMotion) {
        this(new SpanStyle(brush, f, j, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j2, baselineShift, textGeometricTransform, localeList, j3, textDecoration, shadow, platformTextStyle != null ? platformTextStyle.spanStyle : null, drawStyle, (DefaultConstructorMarker) null), new ParagraphStyle(i, i2, j4, textIndent, platformTextStyle != null ? platformTextStyle.paragraphStyle : null, lineHeightStyle, i3, i4, textMotion, (DefaultConstructorMarker) null), platformTextStyle);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TextStyle(androidx.compose.ui.graphics.Brush r34, float r35, long r36, androidx.compose.ui.text.font.FontWeight r38, androidx.compose.ui.text.font.FontStyle r39, androidx.compose.ui.text.font.FontSynthesis r40, androidx.compose.ui.text.font.FontFamily r41, java.lang.String r42, long r43, androidx.compose.ui.text.style.BaselineShift r45, androidx.compose.ui.text.style.TextGeometricTransform r46, androidx.compose.ui.text.intl.LocaleList r47, long r48, androidx.compose.ui.text.style.TextDecoration r50, androidx.compose.ui.graphics.Shadow r51, androidx.compose.ui.graphics.drawscope.DrawStyle r52, androidx.compose.ui.text.style.TextAlign r53, androidx.compose.ui.text.style.TextDirection r54, long r55, androidx.compose.ui.text.style.TextIndent r57, androidx.compose.ui.text.PlatformTextStyle r58, androidx.compose.ui.text.style.LineHeightStyle r59, androidx.compose.ui.text.style.LineBreak r60, androidx.compose.ui.text.style.Hyphens r61, androidx.compose.ui.text.style.TextMotion r62, int r63, kotlin.jvm.internal.DefaultConstructorMarker r64) {
        /*
            Method dump skipped, instructions count: 255
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.TextStyle.<init>(androidx.compose.ui.graphics.Brush, float, long, androidx.compose.ui.text.font.FontWeight, androidx.compose.ui.text.font.FontStyle, androidx.compose.ui.text.font.FontSynthesis, androidx.compose.ui.text.font.FontFamily, java.lang.String, long, androidx.compose.ui.text.style.BaselineShift, androidx.compose.ui.text.style.TextGeometricTransform, androidx.compose.ui.text.intl.LocaleList, long, androidx.compose.ui.text.style.TextDecoration, androidx.compose.ui.graphics.Shadow, androidx.compose.ui.graphics.drawscope.DrawStyle, androidx.compose.ui.text.style.TextAlign, androidx.compose.ui.text.style.TextDirection, long, androidx.compose.ui.text.style.TextIndent, androidx.compose.ui.text.PlatformTextStyle, androidx.compose.ui.text.style.LineHeightStyle, androidx.compose.ui.text.style.LineBreak, androidx.compose.ui.text.style.Hyphens, androidx.compose.ui.text.style.TextMotion, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private TextStyle(androidx.compose.ui.graphics.Brush r29, float r30, long r31, androidx.compose.ui.text.font.FontWeight r33, androidx.compose.ui.text.font.FontStyle r34, androidx.compose.ui.text.font.FontSynthesis r35, androidx.compose.ui.text.font.FontFamily r36, java.lang.String r37, long r38, androidx.compose.ui.text.style.BaselineShift r40, androidx.compose.ui.text.style.TextGeometricTransform r41, androidx.compose.ui.text.intl.LocaleList r42, long r43, androidx.compose.ui.text.style.TextDecoration r45, androidx.compose.ui.graphics.Shadow r46, androidx.compose.ui.graphics.drawscope.DrawStyle r47, androidx.compose.ui.text.style.TextAlign r48, androidx.compose.ui.text.style.TextDirection r49, long r50, androidx.compose.ui.text.style.TextIndent r52, androidx.compose.ui.text.PlatformTextStyle r53, androidx.compose.ui.text.style.LineHeightStyle r54, androidx.compose.ui.text.style.LineBreak r55, androidx.compose.ui.text.style.Hyphens r56, androidx.compose.ui.text.style.TextMotion r57) {
        /*
            r28 = this;
            r0 = r48
            r1 = r49
            r2 = r53
            r3 = r55
            r4 = r56
            androidx.compose.ui.text.SpanStyle r5 = new androidx.compose.ui.text.SpanStyle
            r27 = 0
            if (r2 == 0) goto L15
            androidx.compose.ui.text.PlatformSpanStyle r6 = r2.spanStyle
            r24 = r6
            goto L17
        L15:
            r24 = r27
        L17:
            r26 = 0
            r6 = r29
            r7 = r30
            r8 = r31
            r10 = r33
            r11 = r34
            r12 = r35
            r13 = r36
            r14 = r37
            r15 = r38
            r17 = r40
            r18 = r41
            r19 = r42
            r20 = r43
            r22 = r45
            r23 = r46
            r25 = r47
            r5.<init>(r6, r7, r8, r10, r11, r12, r13, r14, r15, r17, r18, r19, r20, r22, r23, r24, r25, r26)
            androidx.compose.ui.text.ParagraphStyle r6 = new androidx.compose.ui.text.ParagraphStyle
            if (r0 == 0) goto L43
            int r0 = r0.value
            goto L4a
        L43:
            androidx.compose.ui.text.style.TextAlign$Companion r0 = androidx.compose.ui.text.style.TextAlign.Companion
            r0.getClass()
            int r0 = androidx.compose.ui.text.style.TextAlign.Unspecified
        L4a:
            if (r1 == 0) goto L4f
            int r1 = r1.value
            goto L56
        L4f:
            androidx.compose.ui.text.style.TextDirection$Companion r1 = androidx.compose.ui.text.style.TextDirection.Companion
            r1.getClass()
            int r1 = androidx.compose.ui.text.style.TextDirection.Unspecified
        L56:
            if (r2 == 0) goto L5c
            androidx.compose.ui.text.PlatformParagraphStyle r7 = r2.paragraphStyle
            r27 = r7
        L5c:
            if (r3 == 0) goto L61
            int r3 = r3.mask
            goto L67
        L61:
            androidx.compose.ui.text.style.LineBreak$Companion r3 = androidx.compose.ui.text.style.LineBreak.Companion
            r3.getClass()
            r3 = 0
        L67:
            if (r4 == 0) goto L6c
            int r4 = r4.value
            goto L73
        L6c:
            androidx.compose.ui.text.style.Hyphens$Companion r4 = androidx.compose.ui.text.style.Hyphens.Companion
            r4.getClass()
            int r4 = androidx.compose.ui.text.style.Hyphens.Unspecified
        L73:
            r7 = 0
            r32 = r50
            r34 = r52
            r36 = r54
            r39 = r57
            r30 = r0
            r31 = r1
            r37 = r3
            r38 = r4
            r29 = r6
            r40 = r7
            r35 = r27
            r29.<init>(r30, r31, r32, r34, r35, r36, r37, r38, r39, r40)
            r0 = r28
            r1 = r29
            r0.<init>(r5, r1, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.TextStyle.<init>(androidx.compose.ui.graphics.Brush, float, long, androidx.compose.ui.text.font.FontWeight, androidx.compose.ui.text.font.FontStyle, androidx.compose.ui.text.font.FontSynthesis, androidx.compose.ui.text.font.FontFamily, java.lang.String, long, androidx.compose.ui.text.style.BaselineShift, androidx.compose.ui.text.style.TextGeometricTransform, androidx.compose.ui.text.intl.LocaleList, long, androidx.compose.ui.text.style.TextDecoration, androidx.compose.ui.graphics.Shadow, androidx.compose.ui.graphics.drawscope.DrawStyle, androidx.compose.ui.text.style.TextAlign, androidx.compose.ui.text.style.TextDirection, long, androidx.compose.ui.text.style.TextIndent, androidx.compose.ui.text.PlatformTextStyle, androidx.compose.ui.text.style.LineHeightStyle, androidx.compose.ui.text.style.LineBreak, androidx.compose.ui.text.style.Hyphens, androidx.compose.ui.text.style.TextMotion):void");
    }
}
