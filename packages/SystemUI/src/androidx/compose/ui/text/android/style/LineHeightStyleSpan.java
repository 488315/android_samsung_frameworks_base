package androidx.compose.ui.text.android.style;

import android.graphics.Paint;
import androidx.compose.ui.text.internal.InlineClassHelperKt;

/* loaded from: classes.dex */
public final class LineHeightStyleSpan implements android.text.style.LineHeightSpan {
    public final int endIndex;
    public int firstAscentDiff;
    public int lastDescentDiff;
    public final float lineHeight;
    public final boolean preserveMinimumHeight;
    public final int startIndex;
    public final float topRatio;
    public final boolean trimFirstLineTop;
    public final boolean trimLastLineBottom;
    public int firstAscent = Integer.MIN_VALUE;
    public int ascent = Integer.MIN_VALUE;
    public int descent = Integer.MIN_VALUE;
    public int lastDescent = Integer.MIN_VALUE;

    public LineHeightStyleSpan(float f, int i, int i2, boolean z, boolean z2, float f2, boolean z3) {
        this.lineHeight = f;
        this.startIndex = i;
        this.endIndex = i2;
        this.trimFirstLineTop = z;
        this.trimLastLineBottom = z2;
        this.topRatio = f2;
        this.preserveMinimumHeight = z3;
        if ((0.0f <= f2 && f2 <= 1.0f) || f2 == -1.0f) {
            return;
        }
        InlineClassHelperKt.throwIllegalStateException("topRatio should be in [0..1] range or -1");
    }

    @Override // android.text.style.LineHeightSpan
    public final void chooseHeight(CharSequence charSequence, int i, int i2, int i3, int i4, Paint.FontMetricsInt fontMetricsInt) {
        int i5 = fontMetricsInt.descent;
        int i6 = fontMetricsInt.ascent;
        if (i5 - i6 <= 0) {
            return;
        }
        boolean z = i == this.startIndex;
        boolean z2 = i2 == this.endIndex;
        if (z && z2 && this.trimFirstLineTop && this.trimLastLineBottom) {
            return;
        }
        if (this.firstAscent == Integer.MIN_VALUE) {
            int iCeil = (int) Math.ceil(this.lineHeight);
            int i7 = iCeil - (i5 - i6);
            if (!this.preserveMinimumHeight || i7 > 0) {
                float fAbs = this.topRatio;
                if (fAbs == -1.0f) {
                    fAbs = Math.abs(fontMetricsInt.ascent) / (fontMetricsInt.descent - fontMetricsInt.ascent);
                }
                int iCeil2 = (int) (i7 <= 0 ? Math.ceil(i7 * fAbs) : Math.ceil((1.0f - fAbs) * i7));
                int i8 = fontMetricsInt.descent;
                int i9 = iCeil2 + i8;
                this.descent = i9;
                int i10 = i9 - iCeil;
                this.ascent = i10;
                if (this.trimFirstLineTop) {
                    i10 = fontMetricsInt.ascent;
                }
                this.firstAscent = i10;
                if (this.trimLastLineBottom) {
                    i9 = i8;
                }
                this.lastDescent = i9;
                this.firstAscentDiff = fontMetricsInt.ascent - i10;
                this.lastDescentDiff = i9 - i8;
            } else {
                int i11 = fontMetricsInt.ascent;
                this.ascent = i11;
                int i12 = fontMetricsInt.descent;
                this.descent = i12;
                this.firstAscent = i11;
                this.lastDescent = i12;
                this.firstAscentDiff = 0;
                this.lastDescentDiff = 0;
            }
        }
        fontMetricsInt.ascent = z ? this.firstAscent : this.ascent;
        fontMetricsInt.descent = z2 ? this.lastDescent : this.descent;
    }
}
