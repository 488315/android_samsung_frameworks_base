package androidx.compose.foundation.text;

import androidx.compose.ui.unit.TextUnit;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.unit.TextUnitType;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: Access modifiers changed from: package-private */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AutoSizeStepBased implements TextAutoSize {
    public final long maxFontSize;
    public final long minFontSize;
    public final long stepSize;

    public /* synthetic */ AutoSizeStepBased(long j, long j2, long j3, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, j2, j3);
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof AutoSizeStepBased)) {
            return false;
        }
        AutoSizeStepBased autoSizeStepBased = (AutoSizeStepBased) obj;
        return TextUnit.m866equalsimpl0(autoSizeStepBased.minFontSize, this.minFontSize) && TextUnit.m866equalsimpl0(autoSizeStepBased.maxFontSize, this.maxFontSize) && TextUnit.m866equalsimpl0(autoSizeStepBased.stepSize, this.stepSize);
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x0082, code lost:
    
        if (r6 > 0) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0086, code lost:
    
        r4 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0043, code lost:
    
        if (r5.getDidOverflowHeight() == false) goto L32;
     */
    @Override // androidx.compose.foundation.text.TextAutoSize
    /* renamed from: getFontSize-Ci0_558, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final long mo190getFontSizeCi0_558(androidx.compose.foundation.text.modifiers.TextAutoSizeLayoutScope r10, long r11, androidx.compose.ui.text.AnnotatedString r13) {
        /*
            r9 = this;
            long r0 = r9.stepSize
            float r13 = r10.mo56toPxR2X_6o(r0)
            long r0 = r9.minFontSize
            float r0 = r10.mo56toPxR2X_6o(r0)
            long r1 = r9.maxFontSize
            float r9 = r10.mo56toPxR2X_6o(r1)
            float r1 = r0 + r9
            r2 = 2
            float r2 = (float) r2
            float r1 = r1 / r2
            r3 = r9
            r4 = r0
        L19:
            float r5 = r3 - r4
            int r5 = (r5 > r13 ? 1 : (r5 == r13 ? 0 : -1))
            if (r5 < 0) goto Laa
            long r5 = r10.mo60toSpkPz2Gy4(r1)
            androidx.compose.ui.text.TextLayoutResult r5 = r10.mo226performLayout5ZSfY2I(r11, r5)
            androidx.compose.ui.text.TextLayoutInput r6 = r5.layoutInput
            int r7 = r6.overflow
            androidx.compose.ui.text.style.TextOverflow$Companion r8 = androidx.compose.ui.text.style.TextOverflow.Companion
            r8.getClass()
            int r8 = androidx.compose.ui.text.style.TextOverflow.Clip
            if (r7 != r8) goto L35
            goto L39
        L35:
            int r8 = androidx.compose.ui.text.style.TextOverflow.Visible
            if (r7 != r8) goto L46
        L39:
            boolean r6 = r5.getDidOverflowWidth()
            if (r6 != 0) goto L84
            boolean r5 = r5.getDidOverflowHeight()
            if (r5 == 0) goto L86
            goto L84
        L46:
            int r8 = androidx.compose.ui.text.style.TextOverflow.StartEllipsis
            if (r7 != r8) goto L4b
            goto L54
        L4b:
            int r8 = androidx.compose.ui.text.style.TextOverflow.MiddleEllipsis
            if (r7 != r8) goto L50
            goto L54
        L50:
            int r8 = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            if (r7 != r8) goto L8b
        L54:
            r6 = 0
        L55:
            androidx.compose.ui.text.MultiParagraph r7 = r5.multiParagraph
            int r8 = r7.lineCount
            if (r6 >= r8) goto L82
            r7.requireLineIndexInRange(r6)
            java.util.List r8 = r7.paragraphInfoList
            int r8 = androidx.compose.ui.text.MultiParagraphKt.findParagraphByLineIndex(r6, r8)
            java.util.List r7 = r7.paragraphInfoList
            java.util.ArrayList r7 = (java.util.ArrayList) r7
            java.lang.Object r7 = r7.get(r8)
            androidx.compose.ui.text.ParagraphInfo r7 = (androidx.compose.ui.text.ParagraphInfo) r7
            androidx.compose.ui.text.Paragraph r7 = r7.paragraph
            androidx.compose.ui.text.AndroidParagraph r7 = (androidx.compose.ui.text.AndroidParagraph) r7
            androidx.compose.ui.text.android.TextLayout r7 = r7.layout
            android.text.Layout r7 = r7.layout
            androidx.compose.ui.text.android.TextAndroidCanvas r8 = androidx.compose.ui.text.android.TextLayout_androidKt.SharedTextAndroidCanvas
            int r7 = r7.getEllipsisCount(r6)
            if (r7 <= 0) goto L7f
            goto L82
        L7f:
            int r6 = r6 + 1
            goto L55
        L82:
            if (r6 <= 0) goto L86
        L84:
            r3 = r1
            goto L87
        L86:
            r4 = r1
        L87:
            float r1 = r4 + r3
            float r1 = r1 / r2
            goto L19
        L8b:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r11 = "TextOverflow type "
            r10.<init>(r11)
            int r11 = r6.overflow
            java.lang.String r11 = androidx.compose.ui.text.style.TextOverflow.m812toStringimpl(r11)
            r10.append(r11)
            java.lang.String r11 = " is not supported."
            r10.append(r11)
            java.lang.String r10 = r10.toString()
            r9.<init>(r10)
            throw r9
        Laa:
            float r4 = r4 - r0
            float r4 = r4 / r13
            double r1 = (double) r4
            double r1 = java.lang.Math.floor(r1)
            float r1 = (float) r1
            float r1 = r1 * r13
            float r1 = r1 + r0
            float r13 = r13 + r1
            int r9 = (r13 > r9 ? 1 : (r13 == r9 ? 0 : -1))
            if (r9 > 0) goto Lcf
            long r2 = r10.mo60toSpkPz2Gy4(r13)
            androidx.compose.ui.text.TextLayoutResult r9 = r10.mo226performLayout5ZSfY2I(r11, r2)
            boolean r11 = r9.getDidOverflowWidth()
            if (r11 != 0) goto Lcf
            boolean r9 = r9.getDidOverflowHeight()
            if (r9 == 0) goto Lce
            goto Lcf
        Lce:
            r1 = r13
        Lcf:
            long r9 = r10.mo60toSpkPz2Gy4(r1)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.text.AutoSizeStepBased.mo190getFontSizeCi0_558(androidx.compose.foundation.text.modifiers.TextAutoSizeLayoutScope, long, androidx.compose.ui.text.AnnotatedString):long");
    }

    @Override // androidx.compose.foundation.text.TextAutoSize
    public final int hashCode() {
        TextUnit.Companion companion = TextUnit.Companion;
        return Long.hashCode(this.stepSize) + MoveResult$$ExternalSyntheticOutline0.m(Long.hashCode(this.minFontSize) * 31, 31, this.maxFontSize);
    }

    private AutoSizeStepBased(long j, long j2, long j3) {
        this.minFontSize = j;
        this.maxFontSize = j2;
        this.stepSize = j3;
        TextUnit.Companion companion = TextUnit.Companion;
        companion.getClass();
        long j4 = TextUnit.Unspecified;
        if (TextUnit.m866equalsimpl0(j, j4)) {
            throw new IllegalArgumentException("AutoSize.StepBased: TextUnit.Unspecified is not a valid value for minFontSize. Try using other values e.g. 10.sp");
        }
        companion.getClass();
        if (TextUnit.m866equalsimpl0(j2, j4)) {
            throw new IllegalArgumentException("AutoSize.StepBased: TextUnit.Unspecified is not a valid value for maxFontSize. Try using other values e.g. 100.sp");
        }
        companion.getClass();
        if (TextUnit.m866equalsimpl0(j3, j4)) {
            throw new IllegalArgumentException("AutoSize.StepBased: TextUnit.Unspecified is not a valid value for stepSize. Try using other values e.g. 0.25.sp");
        }
        if (TextUnitType.m874equalsimpl0(TextUnit.m867getTypeUIouoOA(j), TextUnit.m867getTypeUIouoOA(j2))) {
            TextUnitKt.m872checkArithmeticNB67dxo(j, j2);
            if (Float.compare(TextUnit.m868getValueimpl(j), TextUnit.m868getValueimpl(j2)) > 0) {
                this.minFontSize = j2;
            }
        }
        long m867getTypeUIouoOA = TextUnit.m867getTypeUIouoOA(j3);
        TextUnitType.Companion.getClass();
        if (TextUnitType.m874equalsimpl0(m867getTypeUIouoOA, TextUnitType.Sp)) {
            long pack = TextUnitKt.pack(1.0E-4f, 4294967296L);
            TextUnitKt.m872checkArithmeticNB67dxo(j3, pack);
            if (Float.compare(TextUnit.m868getValueimpl(j3), TextUnit.m868getValueimpl(pack)) < 0) {
                throw new IllegalArgumentException("AutoSize.StepBased: stepSize must be greater than or equal to 0.0001f.sp");
            }
        }
        if (TextUnit.m868getValueimpl(this.minFontSize) < 0.0f) {
            throw new IllegalArgumentException("AutoSize.StepBased: minFontSize must not be negative");
        }
        if (TextUnit.m868getValueimpl(j2) < 0.0f) {
            throw new IllegalArgumentException("AutoSize.StepBased: maxFontSize must not be negative");
        }
    }
}
