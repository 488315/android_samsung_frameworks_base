package androidx.compose.ui.text.android.style;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.style.ReplacementSpan;
import androidx.compose.ui.text.internal.InlineClassHelperKt;
import kotlin.KotlinNothingValueException;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PlaceholderSpan extends ReplacementSpan {
    public Paint.FontMetricsInt fontMetrics;
    public final float height;
    public int heightPx;
    public final int heightUnit;
    public boolean isLaidOut;
    public final float pxPerSp;
    public final int verticalAlign;
    public final float width;
    public int widthPx;
    public final int widthUnit;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public PlaceholderSpan(float f, int i, float f2, int i2, float f3, int i3) {
        this.width = f;
        this.widthUnit = i;
        this.height = f2;
        this.heightUnit = i2;
        this.pxPerSp = f3;
        this.verticalAlign = i3;
    }

    public final int getHeightPx() {
        if (!this.isLaidOut) {
            InlineClassHelperKt.throwIllegalStateException("PlaceholderSpan is not laid out yet.");
        }
        return this.heightPx;
    }

    @Override // android.text.style.ReplacementSpan
    public final int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
        float f;
        double ceil;
        this.isLaidOut = true;
        float textSize = paint.getTextSize();
        Paint.FontMetricsInt fontMetricsInt2 = paint.getFontMetricsInt();
        this.fontMetrics = fontMetricsInt2;
        int i3 = (fontMetricsInt2 != null ? fontMetricsInt2 : null).descent;
        if (fontMetricsInt2 == null) {
            fontMetricsInt2 = null;
        }
        if (i3 <= fontMetricsInt2.ascent) {
            InlineClassHelperKt.throwIllegalArgumentException("Invalid fontMetrics: line height can not be negative.");
        }
        int i4 = this.widthUnit;
        if (i4 == 0) {
            f = this.width * this.pxPerSp;
        } else {
            if (i4 != 1) {
                InlineClassHelperKt.throwIllegalArgumentExceptionForNullCheck("Unsupported unit.");
                throw new KotlinNothingValueException();
            }
            f = this.width * textSize;
        }
        this.widthPx = (int) Math.ceil(f);
        int i5 = this.heightUnit;
        if (i5 == 0) {
            ceil = Math.ceil(this.height * this.pxPerSp);
        } else {
            if (i5 != 1) {
                InlineClassHelperKt.throwIllegalArgumentExceptionForNullCheck("Unsupported unit.");
                throw new KotlinNothingValueException();
            }
            ceil = Math.ceil(this.height * textSize);
        }
        this.heightPx = (int) ceil;
        if (fontMetricsInt != null) {
            Paint.FontMetricsInt fontMetricsInt3 = this.fontMetrics;
            int i6 = (fontMetricsInt3 != null ? fontMetricsInt3 : null).ascent;
            fontMetricsInt.ascent = i6;
            int i7 = (fontMetricsInt3 != null ? fontMetricsInt3 : null).descent;
            fontMetricsInt.descent = i7;
            if (fontMetricsInt3 == null) {
                fontMetricsInt3 = null;
            }
            fontMetricsInt.leading = fontMetricsInt3.leading;
            switch (this.verticalAlign) {
                case 0:
                    if (i6 > (-getHeightPx())) {
                        fontMetricsInt.ascent = -getHeightPx();
                        break;
                    }
                    break;
                case 1:
                case 4:
                    if (getHeightPx() + i6 > fontMetricsInt.descent) {
                        fontMetricsInt.descent = getHeightPx() + fontMetricsInt.ascent;
                        break;
                    }
                    break;
                case 2:
                case 5:
                    if (i6 > i7 - getHeightPx()) {
                        fontMetricsInt.ascent = fontMetricsInt.descent - getHeightPx();
                        break;
                    }
                    break;
                case 3:
                case 6:
                    if (i7 - i6 < getHeightPx()) {
                        int heightPx = fontMetricsInt.ascent - ((getHeightPx() - (fontMetricsInt.descent - fontMetricsInt.ascent)) / 2);
                        fontMetricsInt.ascent = heightPx;
                        fontMetricsInt.descent = getHeightPx() + heightPx;
                        break;
                    }
                    break;
                default:
                    InlineClassHelperKt.throwIllegalArgumentException("Unknown verticalAlign.");
                    break;
            }
            Paint.FontMetricsInt fontMetricsInt4 = this.fontMetrics;
            if (fontMetricsInt4 == null) {
                fontMetricsInt4 = null;
            }
            fontMetricsInt.top = Math.min(fontMetricsInt4.top, fontMetricsInt.ascent);
            Paint.FontMetricsInt fontMetricsInt5 = this.fontMetrics;
            fontMetricsInt.bottom = Math.max((fontMetricsInt5 != null ? fontMetricsInt5 : null).bottom, fontMetricsInt.descent);
        }
        if (!this.isLaidOut) {
            InlineClassHelperKt.throwIllegalStateException("PlaceholderSpan is not laid out yet.");
        }
        return this.widthPx;
    }

    @Override // android.text.style.ReplacementSpan
    public final void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
    }
}
