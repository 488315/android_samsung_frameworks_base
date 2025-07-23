package androidx.compose.ui.text;

import androidx.compose.foundation.OverscrollConfiguration$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.Brush;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.graphics.drawscope.DrawStyle;
import androidx.compose.ui.text.AnnotatedString;
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
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.ULong;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SpanStyle implements AnnotatedString.Annotation {
    public final long background;
    public final BaselineShift baselineShift;
    public final DrawStyle drawStyle;
    public final FontFamily fontFamily;
    public final String fontFeatureSettings;
    public final long fontSize;
    public final FontStyle fontStyle;
    public final FontSynthesis fontSynthesis;
    public final FontWeight fontWeight;
    public final long letterSpacing;
    public final LocaleList localeList;
    public final PlatformSpanStyle platformStyle;
    public final Shadow shadow;
    public final TextDecoration textDecoration;
    public final TextForegroundStyle textForegroundStyle;
    public final TextGeometricTransform textGeometricTransform;

    public /* synthetic */ SpanStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, PlatformSpanStyle platformSpanStyle, DrawStyle drawStyle, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j3, baselineShift, textGeometricTransform, localeList, j4, textDecoration, shadow, platformSpanStyle, drawStyle);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SpanStyle)) {
            return false;
        }
        SpanStyle spanStyle = (SpanStyle) obj;
        return hasSameLayoutAffectingAttributes$ui_text_release(spanStyle) && hasSameNonLayoutAttributes$ui_text_release(spanStyle);
    }

    public final boolean hasSameLayoutAffectingAttributes$ui_text_release(SpanStyle spanStyle) {
        if (this == spanStyle) {
            return true;
        }
        if (!TextUnit.m866equalsimpl0(this.fontSize, spanStyle.fontSize) || !Intrinsics.areEqual(this.fontWeight, spanStyle.fontWeight) || !Intrinsics.areEqual(this.fontStyle, spanStyle.fontStyle) || !Intrinsics.areEqual(this.fontSynthesis, spanStyle.fontSynthesis) || !Intrinsics.areEqual(this.fontFamily, spanStyle.fontFamily) || !Intrinsics.areEqual(this.fontFeatureSettings, spanStyle.fontFeatureSettings) || !TextUnit.m866equalsimpl0(this.letterSpacing, spanStyle.letterSpacing) || !Intrinsics.areEqual(this.baselineShift, spanStyle.baselineShift) || !Intrinsics.areEqual(this.textGeometricTransform, spanStyle.textGeometricTransform) || !Intrinsics.areEqual(this.localeList, spanStyle.localeList)) {
            return false;
        }
        Color.Companion companion = Color.Companion;
        return ULong.m3427equalsimpl0(this.background, spanStyle.background) && Intrinsics.areEqual(this.platformStyle, spanStyle.platformStyle);
    }

    public final boolean hasSameNonLayoutAttributes$ui_text_release(SpanStyle spanStyle) {
        return Intrinsics.areEqual(this.textForegroundStyle, spanStyle.textForegroundStyle) && Intrinsics.areEqual(this.textDecoration, spanStyle.textDecoration) && Intrinsics.areEqual(this.shadow, spanStyle.shadow) && Intrinsics.areEqual(this.drawStyle, spanStyle.drawStyle);
    }

    public final int hashCode() {
        TextForegroundStyle textForegroundStyle = this.textForegroundStyle;
        long mo792getColor0d7_KjU = textForegroundStyle.mo792getColor0d7_KjU();
        Color.Companion companion = Color.Companion;
        int i = ULong.$r8$clinit;
        int hashCode = Long.hashCode(mo792getColor0d7_KjU) * 31;
        Brush brush = textForegroundStyle.getBrush();
        int hashCode2 = (Float.hashCode(textForegroundStyle.getAlpha()) + ((hashCode + (brush != null ? brush.hashCode() : 0)) * 31)) * 31;
        TextUnit.Companion companion2 = TextUnit.Companion;
        int m = MoveResult$$ExternalSyntheticOutline0.m(hashCode2, 31, this.fontSize);
        FontWeight fontWeight = this.fontWeight;
        int i2 = (m + (fontWeight != null ? fontWeight.weight : 0)) * 31;
        FontStyle fontStyle = this.fontStyle;
        int hashCode3 = (i2 + (fontStyle != null ? Integer.hashCode(fontStyle.value) : 0)) * 31;
        FontSynthesis fontSynthesis = this.fontSynthesis;
        int hashCode4 = (hashCode3 + (fontSynthesis != null ? Integer.hashCode(fontSynthesis.value) : 0)) * 31;
        FontFamily fontFamily = this.fontFamily;
        int hashCode5 = (hashCode4 + (fontFamily != null ? fontFamily.hashCode() : 0)) * 31;
        String str = this.fontFeatureSettings;
        int m2 = MoveResult$$ExternalSyntheticOutline0.m((hashCode5 + (str != null ? str.hashCode() : 0)) * 31, 31, this.letterSpacing);
        BaselineShift baselineShift = this.baselineShift;
        int hashCode6 = (m2 + (baselineShift != null ? Float.hashCode(baselineShift.multiplier) : 0)) * 31;
        TextGeometricTransform textGeometricTransform = this.textGeometricTransform;
        int hashCode7 = (hashCode6 + (textGeometricTransform != null ? textGeometricTransform.hashCode() : 0)) * 31;
        LocaleList localeList = this.localeList;
        int m3 = MoveResult$$ExternalSyntheticOutline0.m((hashCode7 + (localeList != null ? localeList.localeList.hashCode() : 0)) * 31, 31, this.background);
        TextDecoration textDecoration = this.textDecoration;
        int i3 = (m3 + (textDecoration != null ? textDecoration.mask : 0)) * 31;
        Shadow shadow = this.shadow;
        int hashCode8 = (i3 + (shadow != null ? shadow.hashCode() : 0)) * 31;
        PlatformSpanStyle platformSpanStyle = this.platformStyle;
        int hashCode9 = (hashCode8 + (platformSpanStyle != null ? platformSpanStyle.hashCode() : 0)) * 31;
        DrawStyle drawStyle = this.drawStyle;
        return hashCode9 + (drawStyle != null ? drawStyle.hashCode() : 0);
    }

    public final SpanStyle merge(SpanStyle spanStyle) {
        if (spanStyle == null) {
            return this;
        }
        TextForegroundStyle textForegroundStyle = spanStyle.textForegroundStyle;
        return SpanStyleKt.m741fastMergedSHsh3o(this, textForegroundStyle.mo792getColor0d7_KjU(), textForegroundStyle.getBrush(), textForegroundStyle.getAlpha(), spanStyle.fontSize, spanStyle.fontWeight, spanStyle.fontStyle, spanStyle.fontSynthesis, spanStyle.fontFamily, spanStyle.fontFeatureSettings, spanStyle.letterSpacing, spanStyle.baselineShift, spanStyle.textGeometricTransform, spanStyle.localeList, spanStyle.background, spanStyle.textDecoration, spanStyle.shadow, spanStyle.platformStyle, spanStyle.drawStyle);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("SpanStyle(color=");
        TextForegroundStyle textForegroundStyle = this.textForegroundStyle;
        sb.append((Object) Color.m462toStringimpl(textForegroundStyle.mo792getColor0d7_KjU()));
        sb.append(", brush=");
        sb.append(textForegroundStyle.getBrush());
        sb.append(", alpha=");
        sb.append(textForegroundStyle.getAlpha());
        sb.append(", fontSize=");
        sb.append((Object) TextUnit.m870toStringimpl(this.fontSize));
        sb.append(", fontWeight=");
        sb.append(this.fontWeight);
        sb.append(", fontStyle=");
        sb.append(this.fontStyle);
        sb.append(", fontSynthesis=");
        sb.append(this.fontSynthesis);
        sb.append(", fontFamily=");
        sb.append(this.fontFamily);
        sb.append(", fontFeatureSettings=");
        sb.append(this.fontFeatureSettings);
        sb.append(", letterSpacing=");
        sb.append((Object) TextUnit.m870toStringimpl(this.letterSpacing));
        sb.append(", baselineShift=");
        sb.append(this.baselineShift);
        sb.append(", textGeometricTransform=");
        sb.append(this.textGeometricTransform);
        sb.append(", localeList=");
        sb.append(this.localeList);
        sb.append(", background=");
        OverscrollConfiguration$$ExternalSyntheticOutline0.m(this.background, ", textDecoration=", sb);
        sb.append(this.textDecoration);
        sb.append(", shadow=");
        sb.append(this.shadow);
        sb.append(", platformStyle=");
        sb.append(this.platformStyle);
        sb.append(", drawStyle=");
        sb.append(this.drawStyle);
        sb.append(')');
        return sb.toString();
    }

    public /* synthetic */ SpanStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, PlatformSpanStyle platformSpanStyle, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j3, baselineShift, textGeometricTransform, localeList, j4, textDecoration, shadow, platformSpanStyle);
    }

    public /* synthetic */ SpanStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j3, baselineShift, textGeometricTransform, localeList, j4, textDecoration, shadow);
    }

    public /* synthetic */ SpanStyle(Brush brush, float f, long j, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j2, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j3, TextDecoration textDecoration, Shadow shadow, PlatformSpanStyle platformSpanStyle, DrawStyle drawStyle, DefaultConstructorMarker defaultConstructorMarker) {
        this(brush, f, j, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j2, baselineShift, textGeometricTransform, localeList, j3, textDecoration, shadow, platformSpanStyle, drawStyle);
    }

    public /* synthetic */ SpanStyle(TextForegroundStyle textForegroundStyle, long j, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j2, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j3, TextDecoration textDecoration, Shadow shadow, PlatformSpanStyle platformSpanStyle, DrawStyle drawStyle, DefaultConstructorMarker defaultConstructorMarker) {
        this(textForegroundStyle, j, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j2, baselineShift, textGeometricTransform, localeList, j3, textDecoration, shadow, platformSpanStyle, drawStyle);
    }

    private SpanStyle(TextForegroundStyle textForegroundStyle, long j, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j2, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j3, TextDecoration textDecoration, Shadow shadow, PlatformSpanStyle platformSpanStyle, DrawStyle drawStyle) {
        this.textForegroundStyle = textForegroundStyle;
        this.fontSize = j;
        this.fontWeight = fontWeight;
        this.fontStyle = fontStyle;
        this.fontSynthesis = fontSynthesis;
        this.fontFamily = fontFamily;
        this.fontFeatureSettings = str;
        this.letterSpacing = j2;
        this.baselineShift = baselineShift;
        this.textGeometricTransform = textGeometricTransform;
        this.localeList = localeList;
        this.background = j3;
        this.textDecoration = textDecoration;
        this.shadow = shadow;
        this.platformStyle = platformSpanStyle;
        this.drawStyle = drawStyle;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SpanStyle(androidx.compose.ui.text.style.TextForegroundStyle r25, long r26, androidx.compose.ui.text.font.FontWeight r28, androidx.compose.ui.text.font.FontStyle r29, androidx.compose.ui.text.font.FontSynthesis r30, androidx.compose.ui.text.font.FontFamily r31, java.lang.String r32, long r33, androidx.compose.ui.text.style.BaselineShift r35, androidx.compose.ui.text.style.TextGeometricTransform r36, androidx.compose.ui.text.intl.LocaleList r37, long r38, androidx.compose.ui.text.style.TextDecoration r40, androidx.compose.ui.graphics.Shadow r41, androidx.compose.ui.text.PlatformSpanStyle r42, androidx.compose.ui.graphics.drawscope.DrawStyle r43, int r44, kotlin.jvm.internal.DefaultConstructorMarker r45) {
        /*
            r24 = this;
            r0 = r44
            r1 = r0 & 2
            if (r1 == 0) goto Lf
            androidx.compose.ui.unit.TextUnit$Companion r1 = androidx.compose.ui.unit.TextUnit.Companion
            r1.getClass()
            long r1 = androidx.compose.ui.unit.TextUnit.Unspecified
            r5 = r1
            goto L11
        Lf:
            r5 = r26
        L11:
            r1 = r0 & 4
            r2 = 0
            if (r1 == 0) goto L18
            r7 = r2
            goto L1a
        L18:
            r7 = r28
        L1a:
            r1 = r0 & 8
            if (r1 == 0) goto L20
            r8 = r2
            goto L22
        L20:
            r8 = r29
        L22:
            r1 = r0 & 16
            if (r1 == 0) goto L28
            r9 = r2
            goto L2a
        L28:
            r9 = r30
        L2a:
            r1 = r0 & 32
            if (r1 == 0) goto L30
            r10 = r2
            goto L32
        L30:
            r10 = r31
        L32:
            r1 = r0 & 64
            if (r1 == 0) goto L38
            r11 = r2
            goto L3a
        L38:
            r11 = r32
        L3a:
            r1 = r0 & 128(0x80, float:1.8E-43)
            if (r1 == 0) goto L47
            androidx.compose.ui.unit.TextUnit$Companion r1 = androidx.compose.ui.unit.TextUnit.Companion
            r1.getClass()
            long r3 = androidx.compose.ui.unit.TextUnit.Unspecified
            r12 = r3
            goto L49
        L47:
            r12 = r33
        L49:
            r1 = r0 & 256(0x100, float:3.59E-43)
            if (r1 == 0) goto L4f
            r14 = r2
            goto L51
        L4f:
            r14 = r35
        L51:
            r1 = r0 & 512(0x200, float:7.17E-43)
            if (r1 == 0) goto L57
            r15 = r2
            goto L59
        L57:
            r15 = r36
        L59:
            r1 = r0 & 1024(0x400, float:1.435E-42)
            if (r1 == 0) goto L60
            r16 = r2
            goto L62
        L60:
            r16 = r37
        L62:
            r1 = r0 & 2048(0x800, float:2.87E-42)
            if (r1 == 0) goto L70
            androidx.compose.ui.graphics.Color$Companion r1 = androidx.compose.ui.graphics.Color.Companion
            r1.getClass()
            long r3 = androidx.compose.ui.graphics.Color.Unspecified
            r17 = r3
            goto L72
        L70:
            r17 = r38
        L72:
            r1 = r0 & 4096(0x1000, float:5.74E-42)
            if (r1 == 0) goto L79
            r19 = r2
            goto L7b
        L79:
            r19 = r40
        L7b:
            r1 = r0 & 8192(0x2000, float:1.148E-41)
            if (r1 == 0) goto L82
            r20 = r2
            goto L84
        L82:
            r20 = r41
        L84:
            r1 = r0 & 16384(0x4000, float:2.2959E-41)
            if (r1 == 0) goto L8b
            r21 = r2
            goto L8d
        L8b:
            r21 = r42
        L8d:
            r1 = 32768(0x8000, float:4.5918E-41)
            r0 = r0 & r1
            if (r0 == 0) goto L96
            r22 = r2
            goto L98
        L96:
            r22 = r43
        L98:
            r23 = 0
            r3 = r24
            r4 = r25
            r3.<init>(r4, r5, r7, r8, r9, r10, r11, r12, r14, r15, r16, r17, r19, r20, r21, r22, r23)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.SpanStyle.<init>(androidx.compose.ui.text.style.TextForegroundStyle, long, androidx.compose.ui.text.font.FontWeight, androidx.compose.ui.text.font.FontStyle, androidx.compose.ui.text.font.FontSynthesis, androidx.compose.ui.text.font.FontFamily, java.lang.String, long, androidx.compose.ui.text.style.BaselineShift, androidx.compose.ui.text.style.TextGeometricTransform, androidx.compose.ui.text.intl.LocaleList, long, androidx.compose.ui.text.style.TextDecoration, androidx.compose.ui.graphics.Shadow, androidx.compose.ui.text.PlatformSpanStyle, androidx.compose.ui.graphics.drawscope.DrawStyle, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SpanStyle(long r20, long r22, androidx.compose.ui.text.font.FontWeight r24, androidx.compose.ui.text.font.FontStyle r25, androidx.compose.ui.text.font.FontSynthesis r26, androidx.compose.ui.text.font.FontFamily r27, java.lang.String r28, long r29, androidx.compose.ui.text.style.BaselineShift r31, androidx.compose.ui.text.style.TextGeometricTransform r32, androidx.compose.ui.text.intl.LocaleList r33, long r34, androidx.compose.ui.text.style.TextDecoration r36, androidx.compose.ui.graphics.Shadow r37, int r38, kotlin.jvm.internal.DefaultConstructorMarker r39) {
        /*
            r19 = this;
            r0 = r38
            r1 = r0 & 1
            if (r1 == 0) goto Le
            androidx.compose.ui.graphics.Color$Companion r1 = androidx.compose.ui.graphics.Color.Companion
            r1.getClass()
            long r1 = androidx.compose.ui.graphics.Color.Unspecified
            goto L10
        Le:
            r1 = r20
        L10:
            r3 = r0 & 2
            if (r3 == 0) goto L1c
            androidx.compose.ui.unit.TextUnit$Companion r3 = androidx.compose.ui.unit.TextUnit.Companion
            r3.getClass()
            long r3 = androidx.compose.ui.unit.TextUnit.Unspecified
            goto L1e
        L1c:
            r3 = r22
        L1e:
            r5 = r0 & 4
            if (r5 == 0) goto L24
            r5 = 0
            goto L26
        L24:
            r5 = r24
        L26:
            r7 = r0 & 8
            if (r7 == 0) goto L2c
            r7 = 0
            goto L2e
        L2c:
            r7 = r25
        L2e:
            r8 = r0 & 16
            if (r8 == 0) goto L34
            r8 = 0
            goto L36
        L34:
            r8 = r26
        L36:
            r9 = r0 & 32
            if (r9 == 0) goto L3c
            r9 = 0
            goto L3e
        L3c:
            r9 = r27
        L3e:
            r10 = r0 & 64
            if (r10 == 0) goto L44
            r10 = 0
            goto L46
        L44:
            r10 = r28
        L46:
            r11 = r0 & 128(0x80, float:1.8E-43)
            if (r11 == 0) goto L52
            androidx.compose.ui.unit.TextUnit$Companion r11 = androidx.compose.ui.unit.TextUnit.Companion
            r11.getClass()
            long r11 = androidx.compose.ui.unit.TextUnit.Unspecified
            goto L54
        L52:
            r11 = r29
        L54:
            r13 = r0 & 256(0x100, float:3.59E-43)
            if (r13 == 0) goto L5a
            r13 = 0
            goto L5c
        L5a:
            r13 = r31
        L5c:
            r14 = r0 & 512(0x200, float:7.17E-43)
            if (r14 == 0) goto L62
            r14 = 0
            goto L64
        L62:
            r14 = r32
        L64:
            r15 = r0 & 1024(0x400, float:1.435E-42)
            if (r15 == 0) goto L6a
            r15 = 0
            goto L6c
        L6a:
            r15 = r33
        L6c:
            r6 = r0 & 2048(0x800, float:2.87E-42)
            if (r6 == 0) goto L78
            androidx.compose.ui.graphics.Color$Companion r6 = androidx.compose.ui.graphics.Color.Companion
            r6.getClass()
            long r16 = androidx.compose.ui.graphics.Color.Unspecified
            goto L7a
        L78:
            r16 = r34
        L7a:
            r6 = r0 & 4096(0x1000, float:5.74E-42)
            if (r6 == 0) goto L80
            r6 = 0
            goto L82
        L80:
            r6 = r36
        L82:
            r0 = r0 & 8192(0x2000, float:1.148E-41)
            if (r0 == 0) goto L88
            r0 = 0
            goto L8a
        L88:
            r0 = r37
        L8a:
            r18 = 0
            r20 = r19
            r38 = r0
            r21 = r1
            r23 = r3
            r25 = r5
            r37 = r6
            r26 = r7
            r27 = r8
            r28 = r9
            r29 = r10
            r30 = r11
            r32 = r13
            r33 = r14
            r34 = r15
            r35 = r16
            r39 = r18
            r20.<init>(r21, r23, r25, r26, r27, r28, r29, r30, r32, r33, r34, r35, r37, r38, r39)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.SpanStyle.<init>(long, long, androidx.compose.ui.text.font.FontWeight, androidx.compose.ui.text.font.FontStyle, androidx.compose.ui.text.font.FontSynthesis, androidx.compose.ui.text.font.FontFamily, java.lang.String, long, androidx.compose.ui.text.style.BaselineShift, androidx.compose.ui.text.style.TextGeometricTransform, androidx.compose.ui.text.intl.LocaleList, long, androidx.compose.ui.text.style.TextDecoration, androidx.compose.ui.graphics.Shadow, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    private SpanStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow) {
        this(TextForegroundStyle.Companion.m810from8_81llA(j), j2, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j3, baselineShift, textGeometricTransform, localeList, j4, textDecoration, shadow, (PlatformSpanStyle) null, (DrawStyle) null, NetworkAnalyticsConstants.DataPoints.FLAG_UID, (DefaultConstructorMarker) null);
        TextForegroundStyle.Companion.getClass();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SpanStyle(long r21, long r23, androidx.compose.ui.text.font.FontWeight r25, androidx.compose.ui.text.font.FontStyle r26, androidx.compose.ui.text.font.FontSynthesis r27, androidx.compose.ui.text.font.FontFamily r28, java.lang.String r29, long r30, androidx.compose.ui.text.style.BaselineShift r32, androidx.compose.ui.text.style.TextGeometricTransform r33, androidx.compose.ui.text.intl.LocaleList r34, long r35, androidx.compose.ui.text.style.TextDecoration r37, androidx.compose.ui.graphics.Shadow r38, androidx.compose.ui.text.PlatformSpanStyle r39, int r40, kotlin.jvm.internal.DefaultConstructorMarker r41) {
        /*
            Method dump skipped, instructions count: 187
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.SpanStyle.<init>(long, long, androidx.compose.ui.text.font.FontWeight, androidx.compose.ui.text.font.FontStyle, androidx.compose.ui.text.font.FontSynthesis, androidx.compose.ui.text.font.FontFamily, java.lang.String, long, androidx.compose.ui.text.style.BaselineShift, androidx.compose.ui.text.style.TextGeometricTransform, androidx.compose.ui.text.intl.LocaleList, long, androidx.compose.ui.text.style.TextDecoration, androidx.compose.ui.graphics.Shadow, androidx.compose.ui.text.PlatformSpanStyle, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    private SpanStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, PlatformSpanStyle platformSpanStyle) {
        this(TextForegroundStyle.Companion.m810from8_81llA(j), j2, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j3, baselineShift, textGeometricTransform, localeList, j4, textDecoration, shadow, platformSpanStyle, (DrawStyle) null, NetworkAnalyticsConstants.DataPoints.FLAG_UID, (DefaultConstructorMarker) null);
        TextForegroundStyle.Companion.getClass();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SpanStyle(long r22, long r24, androidx.compose.ui.text.font.FontWeight r26, androidx.compose.ui.text.font.FontStyle r27, androidx.compose.ui.text.font.FontSynthesis r28, androidx.compose.ui.text.font.FontFamily r29, java.lang.String r30, long r31, androidx.compose.ui.text.style.BaselineShift r33, androidx.compose.ui.text.style.TextGeometricTransform r34, androidx.compose.ui.text.intl.LocaleList r35, long r36, androidx.compose.ui.text.style.TextDecoration r38, androidx.compose.ui.graphics.Shadow r39, androidx.compose.ui.text.PlatformSpanStyle r40, androidx.compose.ui.graphics.drawscope.DrawStyle r41, int r42, kotlin.jvm.internal.DefaultConstructorMarker r43) {
        /*
            Method dump skipped, instructions count: 201
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.SpanStyle.<init>(long, long, androidx.compose.ui.text.font.FontWeight, androidx.compose.ui.text.font.FontStyle, androidx.compose.ui.text.font.FontSynthesis, androidx.compose.ui.text.font.FontFamily, java.lang.String, long, androidx.compose.ui.text.style.BaselineShift, androidx.compose.ui.text.style.TextGeometricTransform, androidx.compose.ui.text.intl.LocaleList, long, androidx.compose.ui.text.style.TextDecoration, androidx.compose.ui.graphics.Shadow, androidx.compose.ui.text.PlatformSpanStyle, androidx.compose.ui.graphics.drawscope.DrawStyle, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    private SpanStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, PlatformSpanStyle platformSpanStyle, DrawStyle drawStyle) {
        this(TextForegroundStyle.Companion.m810from8_81llA(j), j2, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j3, baselineShift, textGeometricTransform, localeList, j4, textDecoration, shadow, platformSpanStyle, drawStyle, (DefaultConstructorMarker) null);
        TextForegroundStyle.Companion.getClass();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SpanStyle(androidx.compose.ui.graphics.Brush r25, float r26, long r27, androidx.compose.ui.text.font.FontWeight r29, androidx.compose.ui.text.font.FontStyle r30, androidx.compose.ui.text.font.FontSynthesis r31, androidx.compose.ui.text.font.FontFamily r32, java.lang.String r33, long r34, androidx.compose.ui.text.style.BaselineShift r36, androidx.compose.ui.text.style.TextGeometricTransform r37, androidx.compose.ui.text.intl.LocaleList r38, long r39, androidx.compose.ui.text.style.TextDecoration r41, androidx.compose.ui.graphics.Shadow r42, androidx.compose.ui.text.PlatformSpanStyle r43, androidx.compose.ui.graphics.drawscope.DrawStyle r44, int r45, kotlin.jvm.internal.DefaultConstructorMarker r46) {
        /*
            r24 = this;
            r0 = r45
            r1 = r0 & 2
            if (r1 == 0) goto La
            r1 = 2143289344(0x7fc00000, float:NaN)
            r4 = r1
            goto Lc
        La:
            r4 = r26
        Lc:
            r1 = r0 & 4
            if (r1 == 0) goto L19
            androidx.compose.ui.unit.TextUnit$Companion r1 = androidx.compose.ui.unit.TextUnit.Companion
            r1.getClass()
            long r1 = androidx.compose.ui.unit.TextUnit.Unspecified
            r5 = r1
            goto L1b
        L19:
            r5 = r27
        L1b:
            r1 = r0 & 8
            r2 = 0
            if (r1 == 0) goto L22
            r7 = r2
            goto L24
        L22:
            r7 = r29
        L24:
            r1 = r0 & 16
            if (r1 == 0) goto L2a
            r8 = r2
            goto L2c
        L2a:
            r8 = r30
        L2c:
            r1 = r0 & 32
            if (r1 == 0) goto L32
            r9 = r2
            goto L34
        L32:
            r9 = r31
        L34:
            r1 = r0 & 64
            if (r1 == 0) goto L3a
            r10 = r2
            goto L3c
        L3a:
            r10 = r32
        L3c:
            r1 = r0 & 128(0x80, float:1.8E-43)
            if (r1 == 0) goto L42
            r11 = r2
            goto L44
        L42:
            r11 = r33
        L44:
            r1 = r0 & 256(0x100, float:3.59E-43)
            if (r1 == 0) goto L50
            androidx.compose.ui.unit.TextUnit$Companion r1 = androidx.compose.ui.unit.TextUnit.Companion
            r1.getClass()
            long r12 = androidx.compose.ui.unit.TextUnit.Unspecified
            goto L52
        L50:
            r12 = r34
        L52:
            r1 = r0 & 512(0x200, float:7.17E-43)
            if (r1 == 0) goto L58
            r14 = r2
            goto L5a
        L58:
            r14 = r36
        L5a:
            r1 = r0 & 1024(0x400, float:1.435E-42)
            if (r1 == 0) goto L60
            r15 = r2
            goto L62
        L60:
            r15 = r37
        L62:
            r1 = r0 & 2048(0x800, float:2.87E-42)
            if (r1 == 0) goto L69
            r16 = r2
            goto L6b
        L69:
            r16 = r38
        L6b:
            r1 = r0 & 4096(0x1000, float:5.74E-42)
            if (r1 == 0) goto L77
            androidx.compose.ui.graphics.Color$Companion r1 = androidx.compose.ui.graphics.Color.Companion
            r1.getClass()
            long r17 = androidx.compose.ui.graphics.Color.Unspecified
            goto L79
        L77:
            r17 = r39
        L79:
            r1 = r0 & 8192(0x2000, float:1.148E-41)
            if (r1 == 0) goto L80
            r19 = r2
            goto L82
        L80:
            r19 = r41
        L82:
            r1 = r0 & 16384(0x4000, float:2.2959E-41)
            if (r1 == 0) goto L89
            r20 = r2
            goto L8b
        L89:
            r20 = r42
        L8b:
            r1 = 32768(0x8000, float:4.5918E-41)
            r1 = r1 & r0
            if (r1 == 0) goto L94
            r21 = r2
            goto L96
        L94:
            r21 = r43
        L96:
            r1 = 65536(0x10000, float:9.1835E-41)
            r0 = r0 & r1
            if (r0 == 0) goto L9e
            r22 = r2
            goto La0
        L9e:
            r22 = r44
        La0:
            r23 = 0
            r2 = r24
            r3 = r25
            r2.<init>(r3, r4, r5, r7, r8, r9, r10, r11, r12, r14, r15, r16, r17, r19, r20, r21, r22, r23)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.SpanStyle.<init>(androidx.compose.ui.graphics.Brush, float, long, androidx.compose.ui.text.font.FontWeight, androidx.compose.ui.text.font.FontStyle, androidx.compose.ui.text.font.FontSynthesis, androidx.compose.ui.text.font.FontFamily, java.lang.String, long, androidx.compose.ui.text.style.BaselineShift, androidx.compose.ui.text.style.TextGeometricTransform, androidx.compose.ui.text.intl.LocaleList, long, androidx.compose.ui.text.style.TextDecoration, androidx.compose.ui.graphics.Shadow, androidx.compose.ui.text.PlatformSpanStyle, androidx.compose.ui.graphics.drawscope.DrawStyle, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    private SpanStyle(Brush brush, float f, long j, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j2, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j3, TextDecoration textDecoration, Shadow shadow, PlatformSpanStyle platformSpanStyle, DrawStyle drawStyle) {
        this(TextForegroundStyle.Companion.from(f, brush), j, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j2, baselineShift, textGeometricTransform, localeList, j3, textDecoration, shadow, platformSpanStyle, drawStyle, (DefaultConstructorMarker) null);
        TextForegroundStyle.Companion.getClass();
    }
}
