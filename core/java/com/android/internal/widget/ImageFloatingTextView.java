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

    public ImageFloatingTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageFloatingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ImageFloatingTextView(
            Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mIndentLines = 0;
        this.mHasImage = false;
        this.mResolvedDirection = -1;
        this.mMaxLinesForHeight = -1;
        this.mLayoutMaxLines = -1;
        this.mStaticLayoutCreationCountInOnMeasure = 0;
        setHyphenationFrequency(4);
        setBreakStrategy(1);
        this.mMaxLineUpperLimit =
                getResources().getInteger(R.integer.config_notificationLongTextMaxLineCount);
    }

    @Override // android.widget.TextView
    protected Layout makeSingleLayout(
            int wantWidth,
            BoringLayout.Metrics boring,
            int ellipsisWidth,
            Layout.Alignment alignment,
            boolean shouldEllipsize,
            TextUtils.TruncateAt effectiveEllipsize,
            boolean useSaved) {
        int maxLines;
        if (TRACE_ONMEASURE) {
            Trace.beginSection("ImageFloatingTextView#makeSingleLayout");
            this.mStaticLayoutCreationCountInOnMeasure++;
        }
        TransformationMethod transformationMethod = getTransformationMethod();
        CharSequence text = getText();
        if (transformationMethod != null) {
            text = transformationMethod.getTransformation(text, this);
        }
        CharSequence text2 = text == null ? "" : text;
        StaticLayout.Builder builder =
                StaticLayout.Builder.obtain(text2, 0, text2.length(), getPaint(), wantWidth)
                        .setAlignment(alignment)
                        .setTextDirection(getTextDirectionHeuristic())
                        .setLineSpacing(getLineSpacingExtra(), getLineSpacingMultiplier())
                        .setIncludePad(getIncludeFontPadding())
                        .setUseLineSpacingFromFallbacks(true)
                        .setBreakStrategy(getBreakStrategy())
                        .setHyphenationFrequency(getHyphenationFrequency());
        if (this.mMaxLinesForHeight > 0) {
            maxLines = this.mMaxLinesForHeight;
        } else {
            int maxLines2 = getMaxLines();
            maxLines = maxLines2 >= 0 ? getMaxLines() : Integer.MAX_VALUE;
        }
        if (this.mMaxLineUpperLimit > 0) {
            maxLines = Math.min(maxLines, this.mMaxLineUpperLimit);
        }
        builder.setMaxLines(maxLines);
        this.mLayoutMaxLines = maxLines;
        if (shouldEllipsize) {
            builder.setEllipsize(effectiveEllipsize).setEllipsizedWidth(ellipsisWidth);
        }
        int[] margins = null;
        if (this.mHasImage && this.mIndentLines > 0) {
            margins = new int[this.mIndentLines + 1];
            for (int i = 0; i < this.mIndentLines; i++) {
                margins[i] = this.mImageEndMargin;
            }
        }
        int i2 = this.mResolvedDirection;
        if (i2 == 1) {
            builder.setIndents(margins, null);
        } else {
            builder.setIndents(null, margins);
        }
        StaticLayout result = builder.build();
        if (TRACE_ONMEASURE) {
            trackMaxLines();
            Trace.endSection();
        }
        return result;
    }

    @RemotableViewMethod
    public void setImageEndMargin(int imageEndMargin) {
        if (this.mImageEndMargin != imageEndMargin) {
            this.mImageEndMargin = imageEndMargin;
            invalidateTextIfIndenting();
        }
    }

    @RemotableViewMethod
    public void setImageEndMarginDp(float imageEndMarginDp) {
        setImageEndMargin((int) (getResources().getDisplayMetrics().density * imageEndMarginDp));
    }

    @Override // android.widget.TextView, android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (TRACE_ONMEASURE) {
            Trace.beginSection("ImageFloatingTextView#onMeasure");
        }
        this.mStaticLayoutCreationCountInOnMeasure = 0;
        int availableHeight =
                (View.MeasureSpec.getSize(heightMeasureSpec) - this.mPaddingTop)
                        - this.mPaddingBottom;
        if (getLayout() != null && getLayout().getHeight() != availableHeight) {
            this.mMaxLinesForHeight = -1;
            nullLayouts();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Layout layout = getLayout();
        if (layout.getHeight() > availableHeight) {
            int maxLines = layout.getLineCount();
            while (maxLines > 1 && layout.getLineBottom(maxLines - 1) > availableHeight) {
                maxLines--;
            }
            if (getMaxLines() > 0) {
                maxLines = Math.min(getMaxLines(), maxLines);
            }
            if (maxLines != this.mLayoutMaxLines) {
                this.mMaxLinesForHeight = maxLines;
                nullLayouts();
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }
        if (TRACE_ONMEASURE) {
            trackParameters();
            Trace.endSection();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        if (layoutDirection != this.mResolvedDirection && isLayoutDirectionResolved()) {
            this.mResolvedDirection = layoutDirection;
            invalidateTextIfIndenting();
        }
    }

    private void invalidateTextIfIndenting() {
        if (this.mHasImage && this.mIndentLines > 0) {
            nullLayouts();
            requestLayout();
        }
    }

    @RemotableViewMethod
    public void setHasImage(boolean hasImage) {
        setHasImageAndNumIndentLines(hasImage, this.mIndentLines);
    }

    @RemotableViewMethod
    public void setNumIndentLines(int lines) {
        setHasImageAndNumIndentLines(this.mHasImage, lines);
    }

    private void setHasImageAndNumIndentLines(boolean hasImage, int lines) {
        int oldEffectiveLines = this.mHasImage ? this.mIndentLines : 0;
        int newEffectiveLines = hasImage ? lines : 0;
        this.mIndentLines = lines;
        this.mHasImage = hasImage;
        if (oldEffectiveLines != newEffectiveLines) {
            nullLayouts();
            requestLayout();
        }
    }

    private void trackParameters() {
        if (!TRACE_ONMEASURE) {
            return;
        }
        Trace.setCounter(
                "ImageFloatingView#staticLayoutCreationCount",
                this.mStaticLayoutCreationCountInOnMeasure);
        Trace.setCounter("ImageFloatingView#isPrecomputedText", isTextAPrecomputedText());
    }

    private int isTextAPrecomputedText() {
        CharSequence text = getText();
        if (text == null || !(text instanceof PrecomputedText)) {
            return 0;
        }
        return 1;
    }

    private void trackMaxLines() {
        if (!TRACE_ONMEASURE) {
            return;
        }
        Trace.setCounter("ImageFloatingView#layoutMaxLines", this.mLayoutMaxLines);
    }
}
