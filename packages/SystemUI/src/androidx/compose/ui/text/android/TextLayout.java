package androidx.compose.ui.text.android;

import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Trace;
import android.text.BoringLayout;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextPaint;
import android.text.TextUtils;
import androidx.compose.ui.text.android.selection.WordIterator;
import androidx.compose.ui.text.android.style.BaselineShiftSpan;
import androidx.compose.ui.text.android.style.IndentationFixSpan_androidKt;
import androidx.compose.ui.text.android.style.LineHeightStyleSpan;
import androidx.compose.ui.text.internal.InlineClassHelperKt;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class TextLayout {
    public LayoutHelper backingLayoutHelper;
    public WordIterator backingWordIterator;
    public final int bottomPadding;
    public final boolean didExceedMaxLines;
    public final TextUtils.TruncateAt ellipsize;
    public final boolean fallbackLineSpacing;
    public final boolean includePadding;
    public final boolean isBoringLayout;
    public final int lastLineExtra;
    public final Paint.FontMetricsInt lastLineFontMetrics;
    public final Layout layout;
    public final float leftPadding;
    public final int lineCount;
    public final LineHeightStyleSpan[] lineHeightSpans;
    public final Rect rect;
    public final float rightPadding;
    public final TextPaint textPaint;
    public final int topPadding;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0193  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public TextLayout(CharSequence charSequence, float f, TextPaint textPaint, int i, TextUtils.TruncateAt truncateAt, int i2, float f2, float f3, boolean z, boolean z2, int i3, int i4, int i5, int i6, int i7, int i8, int[] iArr, int[] iArr2, LayoutIntrinsics layoutIntrinsics) {
        int i9;
        int i10;
        int i11;
        Layout layoutCreate;
        long j;
        long j2;
        boolean zIsFallbackLineSpacingEnabled;
        this.textPaint = textPaint;
        this.ellipsize = truncateAt;
        this.includePadding = z;
        this.fallbackLineSpacing = z2;
        this.rect = new Rect();
        int length = charSequence.length();
        TextDirectionHeuristic textDirectionHeuristic = TextLayout_androidKt.getTextDirectionHeuristic(i2);
        TextAlignmentAdapter.INSTANCE.getClass();
        Layout.Alignment alignment = i != 0 ? i != 1 ? i != 2 ? i != 3 ? i != 4 ? Layout.Alignment.ALIGN_NORMAL : TextAlignmentAdapter.ALIGN_RIGHT_FRAMEWORK : TextAlignmentAdapter.ALIGN_LEFT_FRAMEWORK : Layout.Alignment.ALIGN_CENTER : Layout.Alignment.ALIGN_OPPOSITE : Layout.Alignment.ALIGN_NORMAL;
        boolean z3 = (charSequence instanceof Spanned) && ((Spanned) charSequence).nextSpanTransition(-1, length, BaselineShiftSpan.class) < length;
        Trace.beginSection("TextLayout:initLayout");
        try {
            BoringLayout.Metrics boringMetrics = layoutIntrinsics.getBoringMetrics();
            double d = f;
            int iCeil = (int) Math.ceil(d);
            if (boringMetrics == null || layoutIntrinsics.getMaxIntrinsicWidth() > f || z3) {
                this.isBoringLayout = false;
                StaticLayoutFactory staticLayoutFactory = StaticLayoutFactory.INSTANCE;
                staticLayoutFactory.getClass();
                i9 = i3;
                i10 = 1;
                i11 = 0;
                layoutCreate = StaticLayoutFactory.create(charSequence, textPaint, iCeil, charSequence.length(), textDirectionHeuristic, alignment, i9, truncateAt, (int) Math.ceil(d), f2, f3, i8, z, z2, i4, i5, i6, i7, iArr, iArr2);
                textDirectionHeuristic = textDirectionHeuristic;
            } else {
                this.isBoringLayout = true;
                BoringLayoutFactory.INSTANCE.getClass();
                if (iCeil < 0) {
                    InlineClassHelperKt.throwIllegalArgumentException("negative width");
                }
                if (iCeil < 0) {
                    InlineClassHelperKt.throwIllegalArgumentException("negative ellipsized width");
                }
                int i12 = BoringLayoutFactory33.$r8$clinit;
                Layout boringLayout = new BoringLayout(charSequence, textPaint, iCeil, alignment, 1.0f, 0.0f, boringMetrics, z, truncateAt, iCeil, z2);
                i9 = i3;
                layoutCreate = boringLayout;
                i10 = 1;
                i11 = 0;
            }
            this.layout = layoutCreate;
            Trace.endSection();
            int iMin = Math.min(layoutCreate.getLineCount(), i9);
            this.lineCount = iMin;
            int i13 = iMin - 1;
            this.didExceedMaxLines = (iMin >= i9 && (layoutCreate.getEllipsisCount(i13) > 0 || layoutCreate.getLineEnd(i13) != charSequence.length())) ? i10 : i11;
            if (z) {
                j = TextLayout_androidKt.ZeroVerticalPadding;
            } else {
                if (this.isBoringLayout) {
                    BoringLayoutFactory.INSTANCE.getClass();
                    int i14 = BoringLayoutFactory33.$r8$clinit;
                    zIsFallbackLineSpacingEnabled = ((BoringLayout) layoutCreate).isFallbackLineSpacingEnabled();
                } else {
                    StaticLayoutFactory.INSTANCE.getClass();
                    StaticLayoutFactory.delegate.getClass();
                    int i15 = StaticLayoutFactory33.$r8$clinit;
                    zIsFallbackLineSpacingEnabled = ((StaticLayout) layoutCreate).isFallbackLineSpacingEnabled();
                }
                if (!zIsFallbackLineSpacingEnabled) {
                    TextPaint paint = layoutCreate.getPaint();
                    CharSequence text = layoutCreate.getText();
                    Rect charSequenceBounds = PaintExtensions_androidKt.getCharSequenceBounds(paint, text, layoutCreate.getLineStart(i11), layoutCreate.getLineEnd(i11));
                    int lineAscent = layoutCreate.getLineAscent(i11);
                    int i16 = charSequenceBounds.top;
                    int topPadding = i16 < lineAscent ? lineAscent - i16 : layoutCreate.getTopPadding();
                    charSequenceBounds = iMin != i10 ? PaintExtensions_androidKt.getCharSequenceBounds(paint, text, layoutCreate.getLineStart(i13), layoutCreate.getLineEnd(i13)) : charSequenceBounds;
                    int lineDescent = layoutCreate.getLineDescent(i13);
                    int i17 = charSequenceBounds.bottom;
                    int bottomPadding = i17 > lineDescent ? i17 - lineDescent : layoutCreate.getBottomPadding();
                    if (topPadding != 0 || bottomPadding != 0) {
                        j = (topPadding << 32) | (bottomPadding & 4294967295L);
                    }
                }
            }
            Paint.FontMetricsInt fontMetricsInt = null;
            LineHeightStyleSpan[] lineHeightStyleSpanArr = ((layoutCreate.getText() instanceof Spanned) && (SpannedExtensions_androidKt.hasSpan((Spanned) layoutCreate.getText(), LineHeightStyleSpan.class) || layoutCreate.getText().length() <= 0)) ? (LineHeightStyleSpan[]) ((Spanned) layoutCreate.getText()).getSpans(i11, layoutCreate.getText().length(), LineHeightStyleSpan.class) : null;
            this.lineHeightSpans = lineHeightStyleSpanArr;
            if (lineHeightStyleSpanArr != null) {
                int length2 = lineHeightStyleSpanArr.length;
                int iMax = i11;
                int iMax2 = iMax;
                for (int i18 = iMax2; i18 < length2; i18++) {
                    LineHeightStyleSpan lineHeightStyleSpan = lineHeightStyleSpanArr[i18];
                    int i19 = lineHeightStyleSpan.firstAscentDiff;
                    iMax = i19 < 0 ? Math.max(iMax, Math.abs(i19)) : iMax;
                    int i20 = lineHeightStyleSpan.lastDescentDiff;
                    if (i20 < 0) {
                        iMax2 = Math.max(iMax, Math.abs(i20));
                    }
                }
                j2 = (iMax == 0 && iMax2 == 0) ? TextLayout_androidKt.ZeroVerticalPadding : (iMax << 32) | (iMax2 & 4294967295L);
            } else {
                j2 = TextLayout_androidKt.ZeroVerticalPadding;
            }
            this.topPadding = Math.max((int) (j >> 32), (int) (j2 >> 32));
            this.bottomPadding = Math.max((int) (j & 4294967295L), (int) (j2 & 4294967295L));
            TextPaint textPaint2 = this.textPaint;
            LineHeightStyleSpan[] lineHeightStyleSpanArr2 = this.lineHeightSpans;
            int i21 = this.lineCount - 1;
            if (this.layout.getLineStart(i21) == this.layout.getLineEnd(i21) && lineHeightStyleSpanArr2 != null && lineHeightStyleSpanArr2.length != 0) {
                SpannableString spannableString = new SpannableString("\u200b");
                LineHeightStyleSpan lineHeightStyleSpan2 = (LineHeightStyleSpan) ArraysKt___ArraysKt.first(lineHeightStyleSpanArr2);
                int length3 = spannableString.length();
                int i22 = (i21 == 0 || !lineHeightStyleSpan2.trimLastLineBottom) ? lineHeightStyleSpan2.trimLastLineBottom : i11;
                lineHeightStyleSpan2.getClass();
                spannableString.setSpan(new LineHeightStyleSpan(lineHeightStyleSpan2.lineHeight, 0, length3, i22, lineHeightStyleSpan2.trimLastLineBottom, lineHeightStyleSpan2.topRatio, lineHeightStyleSpan2.preserveMinimumHeight), i11, spannableString.length(), 33);
                StaticLayoutFactory staticLayoutFactory2 = StaticLayoutFactory.INSTANCE;
                int length4 = spannableString.length();
                LayoutCompat.INSTANCE.getClass();
                Layout.Alignment alignment2 = LayoutCompat.DEFAULT_LAYOUT_ALIGNMENT;
                boolean z4 = this.includePadding;
                boolean z5 = this.fallbackLineSpacing;
                staticLayoutFactory2.getClass();
                StaticLayout staticLayoutCreate = StaticLayoutFactory.create(spannableString, textPaint2, Integer.MAX_VALUE, length4, textDirectionHeuristic, alignment2, Integer.MAX_VALUE, null, Integer.MAX_VALUE, 1.0f, 0.0f, 0, z4, z5, 0, 0, 0, 0, null, null);
                fontMetricsInt = new Paint.FontMetricsInt();
                fontMetricsInt.ascent = staticLayoutCreate.getLineAscent(i11);
                fontMetricsInt.descent = staticLayoutCreate.getLineDescent(i11);
                fontMetricsInt.top = staticLayoutCreate.getLineTop(i11);
                fontMetricsInt.bottom = staticLayoutCreate.getLineBottom(i11);
            }
            this.lastLineExtra = fontMetricsInt != null ? fontMetricsInt.bottom - ((int) (getLineBottom(i13) - getLineTop(i13))) : i11;
            this.lastLineFontMetrics = fontMetricsInt;
            Layout layout = this.layout;
            this.leftPadding = IndentationFixSpan_androidKt.getEllipsizedLeftPadding(layout, i13, layout.getPaint());
            Layout layout2 = this.layout;
            this.rightPadding = IndentationFixSpan_androidKt.getEllipsizedRightPadding(layout2, i13, layout2.getPaint());
        } catch (Throwable th) {
            Trace.endSection();
            throw th;
        }
    }

    public final void fillBoundingBoxes(int i, int i2, int i3, float[] fArr) {
        float f;
        float f2;
        TextLayout textLayout = this;
        int length = textLayout.layout.getText().length();
        if (i < 0) {
            InlineClassHelperKt.throwIllegalArgumentException("startOffset must be > 0");
        }
        if (i >= length) {
            InlineClassHelperKt.throwIllegalArgumentException("startOffset must be less than text length");
        }
        if (i2 <= i) {
            InlineClassHelperKt.throwIllegalArgumentException("endOffset must be greater than startOffset");
        }
        if (i2 > length) {
            InlineClassHelperKt.throwIllegalArgumentException("endOffset must be smaller or equal to text length");
        }
        if (fArr.length - i3 < (i2 - i) * 4) {
            InlineClassHelperKt.throwIllegalArgumentException("array.size - arrayStart must be greater or equal than (endOffset - startOffset) * 4");
        }
        int lineForOffset = textLayout.layout.getLineForOffset(i);
        int lineForOffset2 = textLayout.layout.getLineForOffset(i2 - 1);
        HorizontalPositionCache horizontalPositionCache = new HorizontalPositionCache(textLayout);
        if (lineForOffset > lineForOffset2) {
            return;
        }
        int i4 = lineForOffset;
        int i5 = i3;
        while (true) {
            int lineStart = textLayout.layout.getLineStart(i4);
            int lineEnd = textLayout.getLineEnd(i4);
            int iMax = Math.max(i, lineStart);
            int iMin = Math.min(i2, lineEnd);
            float lineTop = textLayout.getLineTop(i4);
            float lineBottom = textLayout.getLineBottom(i4);
            boolean z = false;
            boolean z2 = textLayout.layout.getParagraphDirection(i4) == 1;
            while (iMax < iMin) {
                boolean zIsRtlCharAt = textLayout.layout.isRtlCharAt(iMax);
                if (z2 && !zIsRtlCharAt) {
                    f = horizontalPositionCache.get(iMax, z, z, true);
                    f2 = horizontalPositionCache.get(iMax + 1, true, true, true);
                    z = false;
                } else if (z2 && zIsRtlCharAt) {
                    z = false;
                    float f3 = horizontalPositionCache.get(iMax, false, false, false);
                    f = horizontalPositionCache.get(iMax + 1, true, true, false);
                    f2 = f3;
                } else {
                    z = false;
                    if (z2 || !zIsRtlCharAt) {
                        f = horizontalPositionCache.get(iMax, false, false, false);
                        f2 = horizontalPositionCache.get(iMax + 1, true, true, false);
                    } else {
                        f2 = horizontalPositionCache.get(iMax, false, false, true);
                        f = horizontalPositionCache.get(iMax + 1, true, true, true);
                    }
                }
                fArr[i5] = f;
                fArr[i5 + 1] = lineTop;
                fArr[i5 + 2] = f2;
                fArr[i5 + 3] = lineBottom;
                i5 += 4;
                iMax++;
                textLayout = this;
            }
            if (i4 == lineForOffset2) {
                return;
            }
            i4++;
            textLayout = this;
        }
    }

    public final int getHeight() {
        return (this.didExceedMaxLines ? this.layout.getLineBottom(this.lineCount - 1) : this.layout.getHeight()) + this.topPadding + this.bottomPadding + this.lastLineExtra;
    }

    public final float getHorizontalPadding(int i) {
        if (i == this.lineCount - 1) {
            return this.leftPadding + this.rightPadding;
        }
        return 0.0f;
    }

    public final LayoutHelper getLayoutHelper() {
        LayoutHelper layoutHelper = this.backingLayoutHelper;
        if (layoutHelper != null) {
            return layoutHelper;
        }
        LayoutHelper layoutHelper2 = new LayoutHelper(this.layout);
        this.backingLayoutHelper = layoutHelper2;
        return layoutHelper2;
    }

    public final float getLineBaseline(int i) {
        return this.topPadding + ((i != this.lineCount + (-1) || this.lastLineFontMetrics == null) ? this.layout.getLineBaseline(i) : getLineTop(i) - this.lastLineFontMetrics.ascent);
    }

    public final float getLineBottom(int i) {
        int i2 = this.lineCount;
        if (i != i2 - 1 || this.lastLineFontMetrics == null) {
            return this.topPadding + this.layout.getLineBottom(i) + (i == i2 + (-1) ? this.bottomPadding : 0);
        }
        return this.layout.getLineBottom(i - 1) + this.lastLineFontMetrics.bottom;
    }

    public final int getLineEnd(int i) {
        Layout layout = this.layout;
        TextAndroidCanvas textAndroidCanvas = TextLayout_androidKt.SharedTextAndroidCanvas;
        return (layout.getEllipsisCount(i) <= 0 || this.ellipsize != TextUtils.TruncateAt.END) ? this.layout.getLineEnd(i) : this.layout.getText().length();
    }

    public final float getLineTop(int i) {
        return this.layout.getLineTop(i) + (i == 0 ? 0 : this.topPadding);
    }

    public final float getPrimaryHorizontal(int i, boolean z) {
        return getHorizontalPadding(this.layout.getLineForOffset(i)) + getLayoutHelper().getHorizontalPosition(i, true, z);
    }

    public final float getSecondaryHorizontal(int i, boolean z) {
        return getHorizontalPadding(this.layout.getLineForOffset(i)) + getLayoutHelper().getHorizontalPosition(i, false, z);
    }

    public final WordIterator getWordIterator() {
        WordIterator wordIterator = this.backingWordIterator;
        if (wordIterator != null) {
            return wordIterator;
        }
        WordIterator wordIterator2 = new WordIterator(this.layout.getText(), 0, this.layout.getText().length(), this.textPaint.getTextLocale());
        this.backingWordIterator = wordIterator2;
        return wordIterator2;
    }

    public /* synthetic */ TextLayout(CharSequence charSequence, float f, TextPaint textPaint, int i, TextUtils.TruncateAt truncateAt, int i2, float f2, float f3, boolean z, boolean z2, int i3, int i4, int i5, int i6, int i7, int i8, int[] iArr, int[] iArr2, LayoutIntrinsics layoutIntrinsics, int i9, DefaultConstructorMarker defaultConstructorMarker) {
        CharSequence charSequence2;
        TextPaint textPaint2;
        LayoutIntrinsics layoutIntrinsics2;
        int i10 = (i9 & 8) != 0 ? 0 : i;
        TextUtils.TruncateAt truncateAt2 = (i9 & 16) != 0 ? null : truncateAt;
        int i11 = (i9 & 32) != 0 ? 2 : i2;
        float f4 = (i9 & 64) != 0 ? 1.0f : f2;
        float f5 = (i9 & 128) != 0 ? 0.0f : f3;
        boolean z3 = (i9 & 256) != 0 ? false : z;
        boolean z4 = (i9 & 512) != 0 ? true : z2;
        int i12 = (i9 & 1024) != 0 ? Integer.MAX_VALUE : i3;
        int i13 = (i9 & 2048) != 0 ? 0 : i4;
        int i14 = (i9 & 4096) != 0 ? 0 : i5;
        int i15 = (i9 & 8192) != 0 ? 0 : i6;
        int i16 = (i9 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? 0 : i7;
        int i17 = (32768 & i9) != 0 ? 0 : i8;
        int[] iArr3 = (65536 & i9) != 0 ? null : iArr;
        int[] iArr4 = (131072 & i9) != 0 ? null : iArr2;
        if ((i9 & 262144) != 0) {
            charSequence2 = charSequence;
            textPaint2 = textPaint;
            layoutIntrinsics2 = new LayoutIntrinsics(charSequence2, textPaint2, i11);
        } else {
            charSequence2 = charSequence;
            textPaint2 = textPaint;
            layoutIntrinsics2 = layoutIntrinsics;
        }
        this(charSequence2, f, textPaint2, i10, truncateAt2, i11, f4, f5, z3, z4, i12, i13, i14, i15, i16, i17, iArr3, iArr4, layoutIntrinsics2);
    }
}
