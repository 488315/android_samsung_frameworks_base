package com.android.wm.shell.common.pip;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.Size;
import com.android.systemui.R;

/* loaded from: classes3.dex */
public final class PhoneSizeSpecSource implements SizeSpecSource {
    public final Context context;
    public int mDefaultMinSize;
    public int mDefaultMinWidth;
    public float mOptimizedAspectRatio;
    public int mOverridableMinSize;
    public Size mOverrideMinSize;
    public final PipDisplayLayoutState pipDisplayLayoutState;
    public float mSystemPreferredDefaultSizePercent = 0.6f;
    public float mSystemPreferredMinimumSizePercent = 0.5f;
    public float mSquareDisplayThresholdForSystemPreferredSize = 0.95f;
    public float mSystemPreferredDefaultSizePercentForSquareDisplay = 0.5f;
    public float mSystemPreferredMinimumSizePercentForSquareDisplay = 0.4f;

    public PhoneSizeSpecSource(Context context, PipDisplayLayoutState pipDisplayLayoutState) {
        this.context = context;
        this.pipDisplayLayoutState = pipDisplayLayoutState;
        reloadResources();
    }

    public final Size adjustOverrideMinSizeToAspectRatio(float f) {
        Size overrideMinSize = getOverrideMinSize();
        if (overrideMinSize == null) {
            return null;
        }
        return ((float) overrideMinSize.getWidth()) / ((float) overrideMinSize.getHeight()) > f ? new Size(overrideMinSize.getWidth(), (int) (overrideMinSize.getWidth() / f)) : new Size((int) (overrideMinSize.getHeight() * f), overrideMinSize.getHeight());
    }

    public final Size getDefaultSize(float f) {
        Size minSize = getMinSize(f);
        if (this.mOverrideMinSize != null) {
            return minSize;
        }
        int iMax = Math.max(Math.round((getMIsSquareDisplay() ? this.mSystemPreferredDefaultSizePercentForSquareDisplay : this.mSystemPreferredDefaultSizePercent) * getMaxSize(f).getWidth()), minSize.getWidth());
        return new Size(iMax, Math.round(iMax / f));
    }

    public final boolean getMIsSquareDisplay() {
        PipDisplayLayoutState pipDisplayLayoutState = this.pipDisplayLayoutState;
        return ((float) Math.min(pipDisplayLayoutState.getDisplayLayout().mWidth, pipDisplayLayoutState.getDisplayLayout().mHeight)) / ((float) Math.max(pipDisplayLayoutState.getDisplayLayout().mWidth, pipDisplayLayoutState.getDisplayLayout().mHeight)) > this.mSquareDisplayThresholdForSystemPreferredSize;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0052  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Size getMaxSize(float f) {
        int iRound;
        PipDisplayLayoutState pipDisplayLayoutState = this.pipDisplayLayoutState;
        Rect insetBounds = pipDisplayLayoutState.getInsetBounds();
        Rect displayBounds = pipDisplayLayoutState.getDisplayBounds();
        int iMin = Math.min(displayBounds.width() - ((displayBounds.width() - insetBounds.right) + insetBounds.left), displayBounds.height() - ((displayBounds.height() - insetBounds.bottom) + insetBounds.top));
        float f2 = this.mOptimizedAspectRatio;
        if (f >= f2) {
            float f3 = 1;
            if (f <= f3 / f2) {
                float f4 = iMin;
                iMin = Math.min(Math.round((((f - f2) * f4) / (f3 + f)) + (f2 * f4)), iMin);
                iRound = Math.round(iMin / f);
            } else if (f > 1.0f) {
                iRound = Math.round(iMin / f);
            } else {
                iMin = Math.round(iMin * f);
                iRound = iMin;
            }
        }
        return new Size(iMin, iRound);
    }

    public final Size getMinSize(float f) {
        int iRound;
        int iMax;
        if (this.mOverrideMinSize != null) {
            Size sizeAdjustOverrideMinSizeToAspectRatio = adjustOverrideMinSizeToAspectRatio(f);
            sizeAdjustOverrideMinSizeToAspectRatio.getClass();
            return sizeAdjustOverrideMinSizeToAspectRatio;
        }
        Size maxSize = getMaxSize(f);
        int iRound2 = Math.round((getMIsSquareDisplay() ? this.mSystemPreferredMinimumSizePercentForSquareDisplay : this.mSystemPreferredMinimumSizePercent) * maxSize.getWidth());
        int iRound3 = Math.round((getMIsSquareDisplay() ? this.mSystemPreferredMinimumSizePercentForSquareDisplay : this.mSystemPreferredMinimumSizePercent) * maxSize.getHeight());
        if (f > 1.0f) {
            iMax = Math.max(iRound3, this.mDefaultMinSize);
            iRound = Math.round(iMax * f);
        } else {
            int iMax2 = Math.max(iRound2, this.mDefaultMinSize);
            int iRound4 = Math.round(iMax2 / f);
            iRound = iMax2;
            iMax = iRound4;
        }
        return new Size(iRound, iMax);
    }

    public final Size getOverrideMinSize() {
        Size size = this.mOverrideMinSize;
        if (size == null) {
            return null;
        }
        if (size.getWidth() >= this.mOverridableMinSize && size.getHeight() >= this.mOverridableMinSize) {
            return size;
        }
        int i = this.mOverridableMinSize;
        return new Size(i, i);
    }

    public final Size getSizeForAspectRatio(float f, Size size) {
        int i;
        if (size.equals(this.mOverrideMinSize)) {
            Size sizeAdjustOverrideMinSizeToAspectRatio = adjustOverrideMinSizeToAspectRatio(f);
            sizeAdjustOverrideMinSizeToAspectRatio.getClass();
            return sizeAdjustOverrideMinSizeToAspectRatio;
        }
        float width = size.getWidth() / getMaxSize(size.getWidth() / size.getHeight()).getWidth();
        Size maxSize = getMaxSize(f);
        int iRound = Math.round(maxSize.getWidth() * width);
        int iRound2 = Math.round(maxSize.getHeight() * width);
        int overrideMinEdgeSize = this.mOverrideMinSize == null ? this.mDefaultMinSize : getOverrideMinEdgeSize();
        if (iRound < overrideMinEdgeSize && f <= 1.0f) {
            iRound2 = Math.round(overrideMinEdgeSize / f);
            iRound = overrideMinEdgeSize;
        } else if (iRound2 < overrideMinEdgeSize && f > 1.0f) {
            iRound = Math.round(overrideMinEdgeSize * f);
            iRound2 = overrideMinEdgeSize;
        }
        if (f <= 1.0f && iRound < (i = this.mDefaultMinWidth)) {
            iRound2 = Math.round(i / f);
            iRound = i;
        }
        PipDisplayLayoutState pipDisplayLayoutState = this.pipDisplayLayoutState;
        Rect displayBounds = pipDisplayLayoutState.getDisplayBounds();
        Rect insetBounds = pipDisplayLayoutState.getInsetBounds();
        int iHeight = (displayBounds.height() - insetBounds.top) - (displayBounds.height() - insetBounds.bottom);
        if (iHeight > 0 && iRound2 > iHeight) {
            iRound2 = maxSize.getHeight();
            iRound = maxSize.getWidth();
        }
        return new Size(iRound, iRound2);
    }

    public final void reloadResources() {
        Resources resources = this.context.getResources();
        this.mDefaultMinSize = resources.getDimensionPixelSize(R.dimen.default_minimal_size_pip_resizable_task);
        this.mOverridableMinSize = resources.getDimensionPixelSize(R.dimen.overridable_minimal_size_pip_resizable_task);
        this.mSystemPreferredDefaultSizePercent = resources.getFloat(R.dimen.config_pipSystemPreferredDefaultSizePercent);
        this.mSystemPreferredMinimumSizePercent = resources.getFloat(R.dimen.config_pipSystemPreferredMinimumSizePercent);
        this.mSquareDisplayThresholdForSystemPreferredSize = resources.getFloat(R.dimen.config_pipSquareDisplayThresholdForSystemPreferredSize);
        this.mSystemPreferredDefaultSizePercentForSquareDisplay = resources.getFloat(R.dimen.config_pipSystemPreferredDefaultSizePercentForSquareDisplay);
        this.mSystemPreferredMinimumSizePercentForSquareDisplay = resources.getFloat(R.dimen.config_pipSystemPreferredMinimumSizePercentForSquareDisplay);
        float f = resources.getFloat(R.dimen.config_pipLargeScreenOptimizedAspectRatio);
        if (f > 1.0f) {
            f = 0.5625f;
        }
        this.mOptimizedAspectRatio = f;
        this.mDefaultMinWidth = resources.getDimensionPixelSize(R.dimen.pip_min_width);
    }
}
