package androidx.compose.foundation.internal;

import androidx.compose.ui.graphics.Shadow;
import androidx.compose.ui.text.font.FontFamily;
import androidx.compose.ui.text.font.FontStyle;
import androidx.compose.ui.text.font.FontSynthesis;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.intl.LocaleList;
import androidx.compose.ui.text.style.BaselineShift;
import androidx.compose.ui.text.style.TextDecoration;
import androidx.compose.ui.text.style.TextGeometricTransform;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class MutableSpanStyle {
    public long background;
    public BaselineShift baselineShift;
    public long color;
    public final FontFamily fontFamily;
    public String fontFeatureSettings;
    public long fontSize;
    public FontStyle fontStyle;
    public FontSynthesis fontSynthesis;
    public FontWeight fontWeight;
    public long letterSpacing;
    public final LocaleList localeList;
    public Shadow shadow;
    public TextDecoration textDecoration;
    public TextGeometricTransform textGeometricTransform;

    public /* synthetic */ MutableSpanStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, fontWeight, fontStyle, fontSynthesis, fontFamily, str, j3, baselineShift, textGeometricTransform, localeList, j4, textDecoration, shadow);
    }

    private MutableSpanStyle(long j, long j2, FontWeight fontWeight, FontStyle fontStyle, FontSynthesis fontSynthesis, FontFamily fontFamily, String str, long j3, BaselineShift baselineShift, TextGeometricTransform textGeometricTransform, LocaleList localeList, long j4, TextDecoration textDecoration, Shadow shadow) {
        this.color = j;
        this.fontSize = j2;
        this.fontWeight = fontWeight;
        this.fontStyle = fontStyle;
        this.fontSynthesis = fontSynthesis;
        this.fontFamily = fontFamily;
        this.fontFeatureSettings = str;
        this.letterSpacing = j3;
        this.baselineShift = baselineShift;
        this.textGeometricTransform = textGeometricTransform;
        this.localeList = localeList;
        this.background = j4;
        this.textDecoration = textDecoration;
        this.shadow = shadow;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public MutableSpanStyle(long r20, long r22, androidx.compose.ui.text.font.FontWeight r24, androidx.compose.ui.text.font.FontStyle r25, androidx.compose.ui.text.font.FontSynthesis r26, androidx.compose.ui.text.font.FontFamily r27, java.lang.String r28, long r29, androidx.compose.ui.text.style.BaselineShift r31, androidx.compose.ui.text.style.TextGeometricTransform r32, androidx.compose.ui.text.intl.LocaleList r33, long r34, androidx.compose.ui.text.style.TextDecoration r36, androidx.compose.ui.graphics.Shadow r37, int r38, kotlin.jvm.internal.DefaultConstructorMarker r39) {
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
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.internal.MutableSpanStyle.<init>(long, long, androidx.compose.ui.text.font.FontWeight, androidx.compose.ui.text.font.FontStyle, androidx.compose.ui.text.font.FontSynthesis, androidx.compose.ui.text.font.FontFamily, java.lang.String, long, androidx.compose.ui.text.style.BaselineShift, androidx.compose.ui.text.style.TextGeometricTransform, androidx.compose.ui.text.intl.LocaleList, long, androidx.compose.ui.text.style.TextDecoration, androidx.compose.ui.graphics.Shadow, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
