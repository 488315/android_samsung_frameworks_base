package com.android.internal.widget;

import android.content.Context;
import android.os.Build;
import android.os.Trace;
import android.text.BoringLayout;
import android.text.Layout;
import android.text.PrecomputedText;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.android.internal.R;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public class ImageFloatingTextView extends TextView {
    private static final boolean TRACE_ONMEASURE = Build.isDebuggable();
    private boolean mHasImage;
    private int mImageEndMargin;
    private int mIndentLines;
    private int mLayoutMaxLines;
    private final int mMaxLineUpperLimit;
    private int mMaxLinesForHeight;
    private int mResolvedDirection;
    private int mStaticLayoutCreationCountInOnMeasure;

    public ImageFloatingTextView(Context context) {
        this(context, null);
    }

    public ImageFloatingTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ImageFloatingTextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ImageFloatingTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mIndentLines = 0;
        this.mHasImage = false;
        this.mResolvedDirection = -1;
        this.mMaxLinesForHeight = -1;
        this.mLayoutMaxLines = -1;
        this.mStaticLayoutCreationCountInOnMeasure = 0;
        setHyphenationFrequency(4);
        setBreakStrategy(1);
        this.mMaxLineUpperLimit = getResources().getInteger(R.integer.config_notificationLongTextMaxLineCount);
    }

    @Override // android.widget.TextView
    protected Layout makeSingleLayout(int i, BoringLayout.Metrics metrics, int i2, Layout.Alignment alignment, boolean z, TextUtils.TruncateAt truncateAt, boolean z2) {
        int[] iArr;
        int i3;
        if (TRACE_ONMEASURE) {
            Trace.beginSection("ImageFloatingTextView#makeSingleLayout");
            this.mStaticLayoutCreationCountInOnMeasure++;
        }
        TransformationMethod transformationMethod = getTransformationMethod();
        CharSequence text = getText();
        if (transformationMethod != null) {
            text = transformationMethod.getTransformation(text, this);
        }
        if (text == null) {
            text = "";
        }
        StaticLayout.Builder hyphenationFrequency = StaticLayout.Builder.obtain(text, 0, text.length(), getPaint(), i).setAlignment(alignment).setTextDirection(getTextDirectionHeuristic()).setLineSpacing(getLineSpacingExtra(), getLineSpacingMultiplier()).setIncludePad(getIncludeFontPadding()).setUseLineSpacingFromFallbacks(true).setBreakStrategy(getBreakStrategy()).setHyphenationFrequency(getHyphenationFrequency());
        int i4 = this.mMaxLinesForHeight;
        if (i4 <= 0) {
            i4 = getMaxLines() >= 0 ? getMaxLines() : Integer.MAX_VALUE;
        }
        int i5 = this.mMaxLineUpperLimit;
        if (i5 > 0) {
            i4 = Math.min(i4, i5);
        }
        hyphenationFrequency.setMaxLines(i4);
        this.mLayoutMaxLines = i4;
        if (z) {
            hyphenationFrequency.setEllipsize(truncateAt).setEllipsizedWidth(i2);
        }
        if (!this.mHasImage || (i3 = this.mIndentLines) <= 0) {
            iArr = null;
        } else {
            iArr = new int[i3 + 1];
            for (int i6 = 0; i6 < this.mIndentLines; i6++) {
                iArr[i6] = this.mImageEndMargin;
            }
        }
        if (this.mResolvedDirection == 1) {
            hyphenationFrequency.setIndents(iArr, null);
        } else {
            hyphenationFrequency.setIndents(null, iArr);
        }
        StaticLayout build = hyphenationFrequency.build();
        if (TRACE_ONMEASURE) {
            trackMaxLines();
            Trace.endSection();
        }
        return build;
    }

    @RemotableViewMethod
    public void setImageEndMargin(int i) {
        if (this.mImageEndMargin != i) {
            this.mImageEndMargin = i;
            invalidateTextIfIndenting();
        }
    }

    @RemotableViewMethod
    public void setImageEndMarginDp(float f) {
        setImageEndMargin((int) (f * getResources().getDisplayMetrics().density));
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int i, int i2) {
        if (TRACE_ONMEASURE) {
            Trace.beginSection("ImageFloatingTextView#onMeasure");
        }
        this.mStaticLayoutCreationCountInOnMeasure = 0;
        int size = (View.MeasureSpec.getSize(i2) - this.mPaddingTop) - this.mPaddingBottom;
        if (getLayout() != null && getLayout().getHeight() != size) {
            this.mMaxLinesForHeight = -1;
            nullLayouts();
        }
        super.onMeasure(i, i2);
        Layout layout = getLayout();
        if (layout.getHeight() > size) {
            int lineCount = layout.getLineCount();
            while (lineCount > 1 && layout.getLineBottom(lineCount - 1) > size) {
                lineCount--;
            }
            if (getMaxLines() > 0) {
                lineCount = Math.min(getMaxLines(), lineCount);
            }
            if (lineCount != this.mLayoutMaxLines) {
                this.mMaxLinesForHeight = lineCount;
                nullLayouts();
                super.onMeasure(i, i2);
            }
        }
        if (TRACE_ONMEASURE) {
            trackParameters();
            Trace.endSection();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        if (i == this.mResolvedDirection || !isLayoutDirectionResolved()) {
            return;
        }
        this.mResolvedDirection = i;
        invalidateTextIfIndenting();
    }

    private void invalidateTextIfIndenting() {
        if (!this.mHasImage || this.mIndentLines <= 0) {
            return;
        }
        nullLayouts();
        requestLayout();
    }

    @RemotableViewMethod
    public void setHasImage(boolean z) {
        setHasImageAndNumIndentLines(z, this.mIndentLines);
    }

    @RemotableViewMethod
    public void setNumIndentLines(int i) {
        setHasImageAndNumIndentLines(this.mHasImage, i);
    }

    private void setHasImageAndNumIndentLines(boolean z, int i) {
        int i2 = this.mHasImage ? this.mIndentLines : 0;
        int i3 = z ? i : 0;
        this.mIndentLines = i;
        this.mHasImage = z;
        if (i2 != i3) {
            nullLayouts();
            requestLayout();
        }
    }

    private void trackParameters() {
        if (TRACE_ONMEASURE) {
            Trace.setCounter("ImageFloatingView#staticLayoutCreationCount", this.mStaticLayoutCreationCountInOnMeasure);
            Trace.setCounter("ImageFloatingView#isPrecomputedText", isTextAPrecomputedText());
        }
    }

    private int isTextAPrecomputedText() {
        CharSequence text = getText();
        return (text != null && (text instanceof PrecomputedText)) ? 1 : 0;
    }

    private void trackMaxLines() {
        if (TRACE_ONMEASURE) {
            Trace.setCounter("ImageFloatingView#layoutMaxLines", this.mLayoutMaxLines);
        }
    }
}
