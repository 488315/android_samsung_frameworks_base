package androidx.compose.ui.text;

import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.internal.InlineClassHelperKt;
import androidx.compose.ui.text.style.Hyphens;
import androidx.compose.ui.text.style.LineBreak;
import androidx.compose.ui.text.style.LineHeightStyle;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextDirection;
import androidx.compose.ui.text.style.TextIndent;
import androidx.compose.ui.text.style.TextMotion;
import androidx.compose.ui.unit.TextUnit;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ParagraphStyle implements AnnotatedString.Annotation {
    public final int hyphens;
    public final int lineBreak;
    public final long lineHeight;
    public final LineHeightStyle lineHeightStyle;
    public final PlatformParagraphStyle platformStyle;
    public final int textAlign;
    public final int textDirection;
    public final TextIndent textIndent;
    public final TextMotion textMotion;

    public /* synthetic */ ParagraphStyle(int i, int i2, long j, TextIndent textIndent, PlatformParagraphStyle platformParagraphStyle, LineHeightStyle lineHeightStyle, int i3, int i4, TextMotion textMotion, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, j, textIndent, platformParagraphStyle, lineHeightStyle, i3, i4, textMotion);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ParagraphStyle)) {
            return false;
        }
        ParagraphStyle paragraphStyle = (ParagraphStyle) obj;
        int i = paragraphStyle.textAlign;
        TextAlign.Companion companion = TextAlign.Companion;
        if (this.textAlign == i) {
            int i2 = paragraphStyle.textDirection;
            TextDirection.Companion companion2 = TextDirection.Companion;
            if (this.textDirection != i2 || !TextUnit.m866equalsimpl0(this.lineHeight, paragraphStyle.lineHeight) || !Intrinsics.areEqual(this.textIndent, paragraphStyle.textIndent) || !Intrinsics.areEqual(this.platformStyle, paragraphStyle.platformStyle) || !Intrinsics.areEqual(this.lineHeightStyle, paragraphStyle.lineHeightStyle)) {
                return false;
            }
            int i3 = paragraphStyle.lineBreak;
            LineBreak.Companion companion3 = LineBreak.Companion;
            if (this.lineBreak == i3) {
                int i4 = paragraphStyle.hyphens;
                Hyphens.Companion companion4 = Hyphens.Companion;
                return this.hyphens == i4 && Intrinsics.areEqual(this.textMotion, paragraphStyle.textMotion);
            }
        }
        return false;
    }

    public final int hashCode() {
        TextAlign.Companion companion = TextAlign.Companion;
        int hashCode = Integer.hashCode(this.textAlign) * 31;
        TextDirection.Companion companion2 = TextDirection.Companion;
        int m = ReorderTile$$ExternalSyntheticOutline0.m(this.textDirection, hashCode, 31);
        TextUnit.Companion companion3 = TextUnit.Companion;
        int m2 = MoveResult$$ExternalSyntheticOutline0.m(m, 31, this.lineHeight);
        TextIndent textIndent = this.textIndent;
        int hashCode2 = (m2 + (textIndent != null ? textIndent.hashCode() : 0)) * 31;
        PlatformParagraphStyle platformParagraphStyle = this.platformStyle;
        int hashCode3 = (hashCode2 + (platformParagraphStyle != null ? platformParagraphStyle.hashCode() : 0)) * 31;
        LineHeightStyle lineHeightStyle = this.lineHeightStyle;
        int hashCode4 = (hashCode3 + (lineHeightStyle != null ? lineHeightStyle.hashCode() : 0)) * 31;
        LineBreak.Companion companion4 = LineBreak.Companion;
        int m3 = ReorderTile$$ExternalSyntheticOutline0.m(this.lineBreak, hashCode4, 31);
        Hyphens.Companion companion5 = Hyphens.Companion;
        int m4 = ReorderTile$$ExternalSyntheticOutline0.m(this.hyphens, m3, 31);
        TextMotion textMotion = this.textMotion;
        return m4 + (textMotion != null ? textMotion.hashCode() : 0);
    }

    public final ParagraphStyle merge(ParagraphStyle paragraphStyle) {
        if (paragraphStyle == null) {
            return this;
        }
        return ParagraphStyleKt.m739fastMergej5T8yCg(this, paragraphStyle.textAlign, paragraphStyle.textDirection, paragraphStyle.lineHeight, paragraphStyle.textIndent, paragraphStyle.platformStyle, paragraphStyle.lineHeightStyle, paragraphStyle.lineBreak, paragraphStyle.hyphens, paragraphStyle.textMotion);
    }

    public final String toString() {
        return "ParagraphStyle(textAlign=" + ((Object) TextAlign.m806toStringimpl(this.textAlign)) + ", textDirection=" + ((Object) TextDirection.m808toStringimpl(this.textDirection)) + ", lineHeight=" + ((Object) TextUnit.m870toStringimpl(this.lineHeight)) + ", textIndent=" + this.textIndent + ", platformStyle=" + this.platformStyle + ", lineHeightStyle=" + this.lineHeightStyle + ", lineBreak=" + ((Object) LineBreak.m796toStringimpl(this.lineBreak)) + ", hyphens=" + ((Object) Hyphens.m794toStringimpl(this.hyphens)) + ", textMotion=" + this.textMotion + ')';
    }

    public /* synthetic */ ParagraphStyle(TextAlign textAlign, TextDirection textDirection, long j, TextIndent textIndent, PlatformParagraphStyle platformParagraphStyle, LineHeightStyle lineHeightStyle, LineBreak lineBreak, Hyphens hyphens, TextMotion textMotion, DefaultConstructorMarker defaultConstructorMarker) {
        this(textAlign, textDirection, j, textIndent, platformParagraphStyle, lineHeightStyle, lineBreak, hyphens, textMotion);
    }

    public /* synthetic */ ParagraphStyle(TextAlign textAlign, TextDirection textDirection, long j, TextIndent textIndent, PlatformParagraphStyle platformParagraphStyle, LineHeightStyle lineHeightStyle, LineBreak lineBreak, Hyphens hyphens, DefaultConstructorMarker defaultConstructorMarker) {
        this(textAlign, textDirection, j, textIndent, platformParagraphStyle, lineHeightStyle, lineBreak, hyphens);
    }

    public /* synthetic */ ParagraphStyle(TextAlign textAlign, TextDirection textDirection, long j, TextIndent textIndent, PlatformParagraphStyle platformParagraphStyle, LineHeightStyle lineHeightStyle, DefaultConstructorMarker defaultConstructorMarker) {
        this(textAlign, textDirection, j, textIndent, platformParagraphStyle, lineHeightStyle);
    }

    public /* synthetic */ ParagraphStyle(TextAlign textAlign, TextDirection textDirection, long j, TextIndent textIndent, DefaultConstructorMarker defaultConstructorMarker) {
        this(textAlign, textDirection, j, textIndent);
    }

    private ParagraphStyle(int i, int i2, long j, TextIndent textIndent, PlatformParagraphStyle platformParagraphStyle, LineHeightStyle lineHeightStyle, int i3, int i4, TextMotion textMotion) {
        this.textAlign = i;
        this.textDirection = i2;
        this.lineHeight = j;
        this.textIndent = textIndent;
        this.platformStyle = platformParagraphStyle;
        this.lineHeightStyle = lineHeightStyle;
        this.lineBreak = i3;
        this.hyphens = i4;
        this.textMotion = textMotion;
        TextUnit.Companion.getClass();
        if (TextUnit.m866equalsimpl0(j, TextUnit.Unspecified)) {
            return;
        }
        if (TextUnit.m868getValueimpl(j) >= 0.0f) {
            return;
        }
        InlineClassHelperKt.throwIllegalStateException("lineHeight can't be negative (" + TextUnit.m868getValueimpl(j) + ')');
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ParagraphStyle(int r12, int r13, long r14, androidx.compose.ui.text.style.TextIndent r16, androidx.compose.ui.text.PlatformParagraphStyle r17, androidx.compose.ui.text.style.LineHeightStyle r18, int r19, int r20, androidx.compose.ui.text.style.TextMotion r21, int r22, kotlin.jvm.internal.DefaultConstructorMarker r23) {
        /*
            r11 = this;
            r0 = r22
            r1 = r0 & 1
            if (r1 == 0) goto Le
            androidx.compose.ui.text.style.TextAlign$Companion r1 = androidx.compose.ui.text.style.TextAlign.Companion
            r1.getClass()
            int r1 = androidx.compose.ui.text.style.TextAlign.Unspecified
            goto Lf
        Le:
            r1 = r12
        Lf:
            r2 = r0 & 2
            if (r2 == 0) goto L1b
            androidx.compose.ui.text.style.TextDirection$Companion r2 = androidx.compose.ui.text.style.TextDirection.Companion
            r2.getClass()
            int r2 = androidx.compose.ui.text.style.TextDirection.Unspecified
            goto L1c
        L1b:
            r2 = r13
        L1c:
            r3 = r0 & 4
            if (r3 == 0) goto L28
            androidx.compose.ui.unit.TextUnit$Companion r3 = androidx.compose.ui.unit.TextUnit.Companion
            r3.getClass()
            long r3 = androidx.compose.ui.unit.TextUnit.Unspecified
            goto L29
        L28:
            r3 = r14
        L29:
            r5 = r0 & 8
            r6 = 0
            if (r5 == 0) goto L30
            r5 = r6
            goto L32
        L30:
            r5 = r16
        L32:
            r7 = r0 & 16
            if (r7 == 0) goto L38
            r7 = r6
            goto L3a
        L38:
            r7 = r17
        L3a:
            r8 = r0 & 32
            if (r8 == 0) goto L40
            r8 = r6
            goto L42
        L40:
            r8 = r18
        L42:
            r9 = r0 & 64
            if (r9 == 0) goto L4d
            androidx.compose.ui.text.style.LineBreak$Companion r9 = androidx.compose.ui.text.style.LineBreak.Companion
            r9.getClass()
            r9 = 0
            goto L4f
        L4d:
            r9 = r19
        L4f:
            r10 = r0 & 128(0x80, float:1.8E-43)
            if (r10 == 0) goto L5b
            androidx.compose.ui.text.style.Hyphens$Companion r10 = androidx.compose.ui.text.style.Hyphens.Companion
            r10.getClass()
            int r10 = androidx.compose.ui.text.style.Hyphens.Unspecified
            goto L5d
        L5b:
            r10 = r20
        L5d:
            r0 = r0 & 256(0x100, float:3.59E-43)
            if (r0 == 0) goto L62
            goto L64
        L62:
            r6 = r21
        L64:
            r0 = 0
            r12 = r11
            r23 = r0
            r13 = r1
            r14 = r2
            r15 = r3
            r17 = r5
            r22 = r6
            r18 = r7
            r19 = r8
            r20 = r9
            r21 = r10
            r12.<init>(r13, r14, r15, r17, r18, r19, r20, r21, r22, r23)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.ParagraphStyle.<init>(int, int, long, androidx.compose.ui.text.style.TextIndent, androidx.compose.ui.text.PlatformParagraphStyle, androidx.compose.ui.text.style.LineHeightStyle, int, int, androidx.compose.ui.text.style.TextMotion, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ParagraphStyle(androidx.compose.ui.text.style.TextAlign r2, androidx.compose.ui.text.style.TextDirection r3, long r4, androidx.compose.ui.text.style.TextIndent r6, androidx.compose.ui.text.PlatformParagraphStyle r7, androidx.compose.ui.text.style.LineHeightStyle r8, androidx.compose.ui.text.style.LineBreak r9, androidx.compose.ui.text.style.Hyphens r10, androidx.compose.ui.text.style.TextMotion r11, int r12, kotlin.jvm.internal.DefaultConstructorMarker r13) {
        /*
            r1 = this;
            r13 = r12 & 1
            r0 = 0
            if (r13 == 0) goto L6
            r2 = r0
        L6:
            r13 = r12 & 2
            if (r13 == 0) goto Lb
            r3 = r0
        Lb:
            r13 = r12 & 4
            if (r13 == 0) goto L16
            androidx.compose.ui.unit.TextUnit$Companion r4 = androidx.compose.ui.unit.TextUnit.Companion
            r4.getClass()
            long r4 = androidx.compose.ui.unit.TextUnit.Unspecified
        L16:
            r13 = r12 & 8
            if (r13 == 0) goto L1b
            r6 = r0
        L1b:
            r13 = r12 & 16
            if (r13 == 0) goto L20
            r7 = r0
        L20:
            r13 = r12 & 32
            if (r13 == 0) goto L25
            r8 = r0
        L25:
            r13 = r12 & 64
            if (r13 == 0) goto L2a
            r9 = r0
        L2a:
            r13 = r12 & 128(0x80, float:1.8E-43)
            if (r13 == 0) goto L2f
            r10 = r0
        L2f:
            r12 = r12 & 256(0x100, float:3.59E-43)
            if (r12 == 0) goto L34
            r11 = r0
        L34:
            r12 = 0
            r1.<init>(r2, r3, r4, r6, r7, r8, r9, r10, r11, r12)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.ParagraphStyle.<init>(androidx.compose.ui.text.style.TextAlign, androidx.compose.ui.text.style.TextDirection, long, androidx.compose.ui.text.style.TextIndent, androidx.compose.ui.text.PlatformParagraphStyle, androidx.compose.ui.text.style.LineHeightStyle, androidx.compose.ui.text.style.LineBreak, androidx.compose.ui.text.style.Hyphens, androidx.compose.ui.text.style.TextMotion, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private ParagraphStyle(androidx.compose.ui.text.style.TextAlign r13, androidx.compose.ui.text.style.TextDirection r14, long r15, androidx.compose.ui.text.style.TextIndent r17, androidx.compose.ui.text.PlatformParagraphStyle r18, androidx.compose.ui.text.style.LineHeightStyle r19, androidx.compose.ui.text.style.LineBreak r20, androidx.compose.ui.text.style.Hyphens r21, androidx.compose.ui.text.style.TextMotion r22) {
        /*
            r12 = this;
            r0 = r20
            r1 = r21
            if (r13 == 0) goto L9
            int r13 = r13.value
            goto L10
        L9:
            androidx.compose.ui.text.style.TextAlign$Companion r13 = androidx.compose.ui.text.style.TextAlign.Companion
            r13.getClass()
            int r13 = androidx.compose.ui.text.style.TextAlign.Unspecified
        L10:
            if (r14 == 0) goto L16
            int r14 = r14.value
        L14:
            r2 = r14
            goto L1e
        L16:
            androidx.compose.ui.text.style.TextDirection$Companion r14 = androidx.compose.ui.text.style.TextDirection.Companion
            r14.getClass()
            int r14 = androidx.compose.ui.text.style.TextDirection.Unspecified
            goto L14
        L1e:
            if (r0 == 0) goto L24
            int r14 = r0.mask
        L22:
            r8 = r14
            goto L2b
        L24:
            androidx.compose.ui.text.style.LineBreak$Companion r14 = androidx.compose.ui.text.style.LineBreak.Companion
            r14.getClass()
            r14 = 0
            goto L22
        L2b:
            if (r1 == 0) goto L31
            int r14 = r1.value
        L2f:
            r9 = r14
            goto L39
        L31:
            androidx.compose.ui.text.style.Hyphens$Companion r14 = androidx.compose.ui.text.style.Hyphens.Companion
            r14.getClass()
            int r14 = androidx.compose.ui.text.style.Hyphens.Unspecified
            goto L2f
        L39:
            r11 = 0
            r0 = r12
            r1 = r13
            r3 = r15
            r5 = r17
            r6 = r18
            r7 = r19
            r10 = r22
            r0.<init>(r1, r2, r3, r5, r6, r7, r8, r9, r10, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.ParagraphStyle.<init>(androidx.compose.ui.text.style.TextAlign, androidx.compose.ui.text.style.TextDirection, long, androidx.compose.ui.text.style.TextIndent, androidx.compose.ui.text.PlatformParagraphStyle, androidx.compose.ui.text.style.LineHeightStyle, androidx.compose.ui.text.style.LineBreak, androidx.compose.ui.text.style.Hyphens, androidx.compose.ui.text.style.TextMotion):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ParagraphStyle(androidx.compose.ui.text.style.TextAlign r2, androidx.compose.ui.text.style.TextDirection r3, long r4, androidx.compose.ui.text.style.TextIndent r6, int r7, kotlin.jvm.internal.DefaultConstructorMarker r8) {
        /*
            r1 = this;
            r8 = r7 & 1
            r0 = 0
            if (r8 == 0) goto L6
            r2 = r0
        L6:
            r8 = r7 & 2
            if (r8 == 0) goto Lb
            r3 = r0
        Lb:
            r8 = r7 & 4
            if (r8 == 0) goto L16
            androidx.compose.ui.unit.TextUnit$Companion r4 = androidx.compose.ui.unit.TextUnit.Companion
            r4.getClass()
            long r4 = androidx.compose.ui.unit.TextUnit.Unspecified
        L16:
            r7 = r7 & 8
            if (r7 == 0) goto L1b
            r6 = r0
        L1b:
            r7 = 0
            r1.<init>(r2, r3, r4, r6, r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.ParagraphStyle.<init>(androidx.compose.ui.text.style.TextAlign, androidx.compose.ui.text.style.TextDirection, long, androidx.compose.ui.text.style.TextIndent, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private ParagraphStyle(androidx.compose.ui.text.style.TextAlign r13, androidx.compose.ui.text.style.TextDirection r14, long r15, androidx.compose.ui.text.style.TextIndent r17) {
        /*
            r12 = this;
            if (r13 == 0) goto L6
            int r13 = r13.value
        L4:
            r1 = r13
            goto Le
        L6:
            androidx.compose.ui.text.style.TextAlign$Companion r13 = androidx.compose.ui.text.style.TextAlign.Companion
            r13.getClass()
            int r13 = androidx.compose.ui.text.style.TextAlign.Unspecified
            goto L4
        Le:
            if (r14 == 0) goto L14
            int r13 = r14.value
        L12:
            r2 = r13
            goto L1c
        L14:
            androidx.compose.ui.text.style.TextDirection$Companion r13 = androidx.compose.ui.text.style.TextDirection.Companion
            r13.getClass()
            int r13 = androidx.compose.ui.text.style.TextDirection.Unspecified
            goto L12
        L1c:
            androidx.compose.ui.text.style.LineBreak$Companion r13 = androidx.compose.ui.text.style.LineBreak.Companion
            r13.getClass()
            androidx.compose.ui.text.style.Hyphens$Companion r13 = androidx.compose.ui.text.style.Hyphens.Companion
            r13.getClass()
            int r9 = androidx.compose.ui.text.style.Hyphens.Unspecified
            r10 = 0
            r11 = 0
            r6 = 0
            r7 = 0
            r8 = 0
            r0 = r12
            r3 = r15
            r5 = r17
            r0.<init>(r1, r2, r3, r5, r6, r7, r8, r9, r10, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.ParagraphStyle.<init>(androidx.compose.ui.text.style.TextAlign, androidx.compose.ui.text.style.TextDirection, long, androidx.compose.ui.text.style.TextIndent):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ParagraphStyle(androidx.compose.ui.text.style.TextAlign r2, androidx.compose.ui.text.style.TextDirection r3, long r4, androidx.compose.ui.text.style.TextIndent r6, androidx.compose.ui.text.PlatformParagraphStyle r7, androidx.compose.ui.text.style.LineHeightStyle r8, int r9, kotlin.jvm.internal.DefaultConstructorMarker r10) {
        /*
            r1 = this;
            r10 = r9 & 1
            r0 = 0
            if (r10 == 0) goto L6
            r2 = r0
        L6:
            r10 = r9 & 2
            if (r10 == 0) goto Lb
            r3 = r0
        Lb:
            r10 = r9 & 4
            if (r10 == 0) goto L16
            androidx.compose.ui.unit.TextUnit$Companion r4 = androidx.compose.ui.unit.TextUnit.Companion
            r4.getClass()
            long r4 = androidx.compose.ui.unit.TextUnit.Unspecified
        L16:
            r10 = r9 & 8
            if (r10 == 0) goto L1b
            r6 = r0
        L1b:
            r10 = r9 & 16
            if (r10 == 0) goto L20
            r7 = r0
        L20:
            r9 = r9 & 32
            if (r9 == 0) goto L25
            r8 = r0
        L25:
            r9 = 0
            r1.<init>(r2, r3, r4, r6, r7, r8, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.ParagraphStyle.<init>(androidx.compose.ui.text.style.TextAlign, androidx.compose.ui.text.style.TextDirection, long, androidx.compose.ui.text.style.TextIndent, androidx.compose.ui.text.PlatformParagraphStyle, androidx.compose.ui.text.style.LineHeightStyle, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private ParagraphStyle(androidx.compose.ui.text.style.TextAlign r13, androidx.compose.ui.text.style.TextDirection r14, long r15, androidx.compose.ui.text.style.TextIndent r17, androidx.compose.ui.text.PlatformParagraphStyle r18, androidx.compose.ui.text.style.LineHeightStyle r19) {
        /*
            r12 = this;
            if (r13 == 0) goto L6
            int r13 = r13.value
        L4:
            r1 = r13
            goto Le
        L6:
            androidx.compose.ui.text.style.TextAlign$Companion r13 = androidx.compose.ui.text.style.TextAlign.Companion
            r13.getClass()
            int r13 = androidx.compose.ui.text.style.TextAlign.Unspecified
            goto L4
        Le:
            if (r14 == 0) goto L14
            int r13 = r14.value
        L12:
            r2 = r13
            goto L1c
        L14:
            androidx.compose.ui.text.style.TextDirection$Companion r13 = androidx.compose.ui.text.style.TextDirection.Companion
            r13.getClass()
            int r13 = androidx.compose.ui.text.style.TextDirection.Unspecified
            goto L12
        L1c:
            androidx.compose.ui.text.style.LineBreak$Companion r13 = androidx.compose.ui.text.style.LineBreak.Companion
            r13.getClass()
            androidx.compose.ui.text.style.Hyphens$Companion r13 = androidx.compose.ui.text.style.Hyphens.Companion
            r13.getClass()
            int r9 = androidx.compose.ui.text.style.Hyphens.Unspecified
            r10 = 0
            r11 = 0
            r8 = 0
            r0 = r12
            r3 = r15
            r5 = r17
            r6 = r18
            r7 = r19
            r0.<init>(r1, r2, r3, r5, r6, r7, r8, r9, r10, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.ParagraphStyle.<init>(androidx.compose.ui.text.style.TextAlign, androidx.compose.ui.text.style.TextDirection, long, androidx.compose.ui.text.style.TextIndent, androidx.compose.ui.text.PlatformParagraphStyle, androidx.compose.ui.text.style.LineHeightStyle):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ParagraphStyle(androidx.compose.ui.text.style.TextAlign r2, androidx.compose.ui.text.style.TextDirection r3, long r4, androidx.compose.ui.text.style.TextIndent r6, androidx.compose.ui.text.PlatformParagraphStyle r7, androidx.compose.ui.text.style.LineHeightStyle r8, androidx.compose.ui.text.style.LineBreak r9, androidx.compose.ui.text.style.Hyphens r10, int r11, kotlin.jvm.internal.DefaultConstructorMarker r12) {
        /*
            r1 = this;
            r12 = r11 & 1
            r0 = 0
            if (r12 == 0) goto L6
            r2 = r0
        L6:
            r12 = r11 & 2
            if (r12 == 0) goto Lb
            r3 = r0
        Lb:
            r12 = r11 & 4
            if (r12 == 0) goto L16
            androidx.compose.ui.unit.TextUnit$Companion r4 = androidx.compose.ui.unit.TextUnit.Companion
            r4.getClass()
            long r4 = androidx.compose.ui.unit.TextUnit.Unspecified
        L16:
            r12 = r11 & 8
            if (r12 == 0) goto L1b
            r6 = r0
        L1b:
            r12 = r11 & 16
            if (r12 == 0) goto L20
            r7 = r0
        L20:
            r12 = r11 & 32
            if (r12 == 0) goto L25
            r8 = r0
        L25:
            r12 = r11 & 64
            if (r12 == 0) goto L2a
            r9 = r0
        L2a:
            r11 = r11 & 128(0x80, float:1.8E-43)
            if (r11 == 0) goto L2f
            r10 = r0
        L2f:
            r11 = 0
            r1.<init>(r2, r3, r4, r6, r7, r8, r9, r10, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.ParagraphStyle.<init>(androidx.compose.ui.text.style.TextAlign, androidx.compose.ui.text.style.TextDirection, long, androidx.compose.ui.text.style.TextIndent, androidx.compose.ui.text.PlatformParagraphStyle, androidx.compose.ui.text.style.LineHeightStyle, androidx.compose.ui.text.style.LineBreak, androidx.compose.ui.text.style.Hyphens, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private ParagraphStyle(androidx.compose.ui.text.style.TextAlign r13, androidx.compose.ui.text.style.TextDirection r14, long r15, androidx.compose.ui.text.style.TextIndent r17, androidx.compose.ui.text.PlatformParagraphStyle r18, androidx.compose.ui.text.style.LineHeightStyle r19, androidx.compose.ui.text.style.LineBreak r20, androidx.compose.ui.text.style.Hyphens r21) {
        /*
            r12 = this;
            r0 = r20
            r1 = r21
            if (r13 == 0) goto L9
            int r13 = r13.value
            goto L10
        L9:
            androidx.compose.ui.text.style.TextAlign$Companion r13 = androidx.compose.ui.text.style.TextAlign.Companion
            r13.getClass()
            int r13 = androidx.compose.ui.text.style.TextAlign.Unspecified
        L10:
            if (r14 == 0) goto L16
            int r14 = r14.value
        L14:
            r2 = r14
            goto L1e
        L16:
            androidx.compose.ui.text.style.TextDirection$Companion r14 = androidx.compose.ui.text.style.TextDirection.Companion
            r14.getClass()
            int r14 = androidx.compose.ui.text.style.TextDirection.Unspecified
            goto L14
        L1e:
            if (r0 == 0) goto L24
            int r14 = r0.mask
        L22:
            r8 = r14
            goto L2b
        L24:
            androidx.compose.ui.text.style.LineBreak$Companion r14 = androidx.compose.ui.text.style.LineBreak.Companion
            r14.getClass()
            r14 = 0
            goto L22
        L2b:
            if (r1 == 0) goto L31
            int r14 = r1.value
        L2f:
            r9 = r14
            goto L39
        L31:
            androidx.compose.ui.text.style.Hyphens$Companion r14 = androidx.compose.ui.text.style.Hyphens.Companion
            r14.getClass()
            int r14 = androidx.compose.ui.text.style.Hyphens.Unspecified
            goto L2f
        L39:
            r10 = 0
            r11 = 0
            r0 = r12
            r1 = r13
            r3 = r15
            r5 = r17
            r6 = r18
            r7 = r19
            r0.<init>(r1, r2, r3, r5, r6, r7, r8, r9, r10, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.ParagraphStyle.<init>(androidx.compose.ui.text.style.TextAlign, androidx.compose.ui.text.style.TextDirection, long, androidx.compose.ui.text.style.TextIndent, androidx.compose.ui.text.PlatformParagraphStyle, androidx.compose.ui.text.style.LineHeightStyle, androidx.compose.ui.text.style.LineBreak, androidx.compose.ui.text.style.Hyphens):void");
    }
}
