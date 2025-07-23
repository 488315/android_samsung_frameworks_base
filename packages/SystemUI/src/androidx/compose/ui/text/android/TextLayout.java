package androidx.compose.ui.text.android;

import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;
import androidx.compose.ui.text.android.selection.WordIterator;
import androidx.compose.ui.text.android.style.LineHeightStyleSpan;
import androidx.compose.ui.text.internal.InlineClassHelperKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    /* JADX WARN: Removed duplicated region for block: B:61:0x01d3  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0232 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x02d9  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x02e8  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0203  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public TextLayout(java.lang.CharSequence r35, float r36, android.text.TextPaint r37, int r38, android.text.TextUtils.TruncateAt r39, int r40, float r41, float r42, boolean r43, boolean r44, int r45, int r46, int r47, int r48, int r49, int r50, int[] r51, int[] r52, androidx.compose.ui.text.android.LayoutIntrinsics r53) {
        /*
            Method dump skipped, instructions count: 779
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.android.TextLayout.<init>(java.lang.CharSequence, float, android.text.TextPaint, int, android.text.TextUtils$TruncateAt, int, float, float, boolean, boolean, int, int, int, int, int, int, int[], int[], androidx.compose.ui.text.android.LayoutIntrinsics):void");
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
            int max = Math.max(i, lineStart);
            int min = Math.min(i2, lineEnd);
            float lineTop = textLayout.getLineTop(i4);
            float lineBottom = textLayout.getLineBottom(i4);
            boolean z = false;
            boolean z2 = textLayout.layout.getParagraphDirection(i4) == 1;
            while (max < min) {
                boolean isRtlCharAt = textLayout.layout.isRtlCharAt(max);
                if (z2 && !isRtlCharAt) {
                    f = horizontalPositionCache.get(max, z, z, true);
                    f2 = horizontalPositionCache.get(max + 1, true, true, true);
                    z = false;
                } else if (z2 && isRtlCharAt) {
                    z = false;
                    float f3 = horizontalPositionCache.get(max, false, false, false);
                    f = horizontalPositionCache.get(max + 1, true, true, false);
                    f2 = f3;
                } else {
                    z = false;
                    if (z2 || !isRtlCharAt) {
                        f = horizontalPositionCache.get(max, false, false, false);
                        f2 = horizontalPositionCache.get(max + 1, true, true, false);
                    } else {
                        f2 = horizontalPositionCache.get(max, false, false, true);
                        f = horizontalPositionCache.get(max + 1, true, true, true);
                    }
                }
                fArr[i5] = f;
                fArr[i5 + 1] = lineTop;
                fArr[i5 + 2] = f2;
                fArr[i5 + 3] = lineBottom;
                i5 += 4;
                max++;
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

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ TextLayout(java.lang.CharSequence r24, float r25, android.text.TextPaint r26, int r27, android.text.TextUtils.TruncateAt r28, int r29, float r30, float r31, boolean r32, boolean r33, int r34, int r35, int r36, int r37, int r38, int r39, int[] r40, int[] r41, androidx.compose.ui.text.android.LayoutIntrinsics r42, int r43, kotlin.jvm.internal.DefaultConstructorMarker r44) {
        /*
            Method dump skipped, instructions count: 174
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.text.android.TextLayout.<init>(java.lang.CharSequence, float, android.text.TextPaint, int, android.text.TextUtils$TruncateAt, int, float, float, boolean, boolean, int, int, int, int, int, int, int[], int[], androidx.compose.ui.text.android.LayoutIntrinsics, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
