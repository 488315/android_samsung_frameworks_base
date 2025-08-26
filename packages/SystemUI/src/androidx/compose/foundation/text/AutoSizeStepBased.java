package androidx.compose.foundation.text;

import android.text.Layout;
import androidx.compose.foundation.text.modifiers.TextAutoSizeLayoutScope;
import androidx.compose.ui.text.AndroidParagraph;
import androidx.compose.ui.text.AnnotatedString;
import androidx.compose.ui.text.MultiParagraph;
import androidx.compose.ui.text.MultiParagraphKt;
import androidx.compose.ui.text.ParagraphInfo;
import androidx.compose.ui.text.TextLayoutInput;
import androidx.compose.ui.text.TextLayoutResult;
import androidx.compose.ui.text.android.TextAndroidCanvas;
import androidx.compose.ui.text.android.TextLayout_androidKt;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.unit.TextUnit;
import androidx.compose.ui.unit.TextUnitKt;
import androidx.compose.ui.unit.TextUnitType;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: Access modifiers changed from: package-private */
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
        return TextUnit.m868equalsimpl0(autoSizeStepBased.minFontSize, this.minFontSize) && TextUnit.m868equalsimpl0(autoSizeStepBased.maxFontSize, this.maxFontSize) && TextUnit.m868equalsimpl0(autoSizeStepBased.stepSize, this.stepSize);
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0086  */
    @Override // androidx.compose.foundation.text.TextAutoSize
    /* renamed from: getFontSize-Ci0_558, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final long mo191getFontSizeCi0_558(TextAutoSizeLayoutScope textAutoSizeLayoutScope, long j, AnnotatedString annotatedString) {
        float fMo57toPxR2X_6o = textAutoSizeLayoutScope.mo57toPxR2X_6o(this.stepSize);
        float fMo57toPxR2X_6o2 = textAutoSizeLayoutScope.mo57toPxR2X_6o(this.minFontSize);
        float fMo57toPxR2X_6o3 = textAutoSizeLayoutScope.mo57toPxR2X_6o(this.maxFontSize);
        float f = 2;
        float f2 = (fMo57toPxR2X_6o2 + fMo57toPxR2X_6o3) / f;
        float f3 = fMo57toPxR2X_6o3;
        float f4 = fMo57toPxR2X_6o2;
        while (f3 - f4 >= fMo57toPxR2X_6o) {
            TextLayoutResult textLayoutResultMo227performLayout5ZSfY2I = textAutoSizeLayoutScope.mo227performLayout5ZSfY2I(j, textAutoSizeLayoutScope.mo61toSpkPz2Gy4(f2));
            TextLayoutInput textLayoutInput = textLayoutResultMo227performLayout5ZSfY2I.layoutInput;
            int i = textLayoutInput.overflow;
            TextOverflow.Companion.getClass();
            if (i == TextOverflow.Clip || i == TextOverflow.Visible) {
                if (textLayoutResultMo227performLayout5ZSfY2I.getDidOverflowWidth() || textLayoutResultMo227performLayout5ZSfY2I.getDidOverflowHeight()) {
                }
            } else {
                if (i != TextOverflow.StartEllipsis && i != TextOverflow.MiddleEllipsis && i != TextOverflow.Ellipsis) {
                    throw new IllegalArgumentException("TextOverflow type " + ((Object) TextOverflow.m814toStringimpl(textLayoutInput.overflow)) + " is not supported.");
                }
                int i2 = 0;
                while (true) {
                    MultiParagraph multiParagraph = textLayoutResultMo227performLayout5ZSfY2I.multiParagraph;
                    if (i2 >= multiParagraph.lineCount) {
                        break;
                    }
                    multiParagraph.requireLineIndexInRange(i2);
                    Layout layout = ((AndroidParagraph) ((ParagraphInfo) ((ArrayList) multiParagraph.paragraphInfoList).get(MultiParagraphKt.findParagraphByLineIndex(i2, multiParagraph.paragraphInfoList))).paragraph).layout.layout;
                    TextAndroidCanvas textAndroidCanvas = TextLayout_androidKt.SharedTextAndroidCanvas;
                    if (layout.getEllipsisCount(i2) > 0) {
                        break;
                    }
                    i2++;
                }
                if (i2 > 0) {
                    f3 = f2;
                } else {
                    f4 = f2;
                }
            }
            f2 = (f4 + f3) / f;
        }
        float fFloor = (((float) Math.floor((f4 - fMo57toPxR2X_6o2) / fMo57toPxR2X_6o)) * fMo57toPxR2X_6o) + fMo57toPxR2X_6o2;
        float f5 = fMo57toPxR2X_6o + fFloor;
        if (f5 <= fMo57toPxR2X_6o3) {
            TextLayoutResult textLayoutResultMo227performLayout5ZSfY2I2 = textAutoSizeLayoutScope.mo227performLayout5ZSfY2I(j, textAutoSizeLayoutScope.mo61toSpkPz2Gy4(f5));
            if (!textLayoutResultMo227performLayout5ZSfY2I2.getDidOverflowWidth() && !textLayoutResultMo227performLayout5ZSfY2I2.getDidOverflowHeight()) {
                fFloor = f5;
            }
        }
        return textAutoSizeLayoutScope.mo61toSpkPz2Gy4(fFloor);
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
        if (TextUnit.m868equalsimpl0(j, j4)) {
            throw new IllegalArgumentException("AutoSize.StepBased: TextUnit.Unspecified is not a valid value for minFontSize. Try using other values e.g. 10.sp");
        }
        companion.getClass();
        if (TextUnit.m868equalsimpl0(j2, j4)) {
            throw new IllegalArgumentException("AutoSize.StepBased: TextUnit.Unspecified is not a valid value for maxFontSize. Try using other values e.g. 100.sp");
        }
        companion.getClass();
        if (TextUnit.m868equalsimpl0(j3, j4)) {
            throw new IllegalArgumentException("AutoSize.StepBased: TextUnit.Unspecified is not a valid value for stepSize. Try using other values e.g. 0.25.sp");
        }
        if (TextUnitType.m876equalsimpl0(TextUnit.m869getTypeUIouoOA(j), TextUnit.m869getTypeUIouoOA(j2))) {
            TextUnitKt.m874checkArithmeticNB67dxo(j, j2);
            if (Float.compare(TextUnit.m870getValueimpl(j), TextUnit.m870getValueimpl(j2)) > 0) {
                this.minFontSize = j2;
            }
        }
        long jM869getTypeUIouoOA = TextUnit.m869getTypeUIouoOA(j3);
        TextUnitType.Companion.getClass();
        if (TextUnitType.m876equalsimpl0(jM869getTypeUIouoOA, TextUnitType.Sp)) {
            long jPack = TextUnitKt.pack(1.0E-4f, 4294967296L);
            TextUnitKt.m874checkArithmeticNB67dxo(j3, jPack);
            if (Float.compare(TextUnit.m870getValueimpl(j3), TextUnit.m870getValueimpl(jPack)) < 0) {
                throw new IllegalArgumentException("AutoSize.StepBased: stepSize must be greater than or equal to 0.0001f.sp");
            }
        }
        if (TextUnit.m870getValueimpl(this.minFontSize) < 0.0f) {
            throw new IllegalArgumentException("AutoSize.StepBased: minFontSize must not be negative");
        }
        if (TextUnit.m870getValueimpl(j2) < 0.0f) {
            throw new IllegalArgumentException("AutoSize.StepBased: maxFontSize must not be negative");
        }
    }
}
