package com.android.wm.shell.pip.phone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.SystemProperties;
import android.util.Size;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.pip.PipDisplayLayoutState;
import com.android.wm.shell.pip.PipUtils;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PipSizeSpecHandler {
    public final Context mContext;
    public int mDefaultMinSize;
    public int mDefaultMinWidth;
    public float mMaxAspectRatioForMinSize;
    public float mMinAspectRatioForMinSize;
    public int mOverridableMinSize;
    public Size mOverrideMinSize;
    public final PipDisplayLayoutState mPipDisplayLayoutState;
    public Point mScreenEdgeInsets;
    public final SizeSpecSource mSizeSpecSourceImpl;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SizeSpecDefaultImpl implements SizeSpecSource {
        public float mDefaultSizePercent;
        public float mMinimumSizePercent;

        public /* synthetic */ SizeSpecDefaultImpl(PipSizeSpecHandler pipSizeSpecHandler, int i) {
            this();
        }

        @Override // com.android.wm.shell.pip.phone.PipSizeSpecHandler.SizeSpecSource
        public final Size getDefaultSize(float f) {
            int round;
            int round2;
            PipSizeSpecHandler pipSizeSpecHandler = PipSizeSpecHandler.this;
            if (pipSizeSpecHandler.mOverrideMinSize != null) {
                return getMinSize(f);
            }
            int max = (int) Math.max(pipSizeSpecHandler.getMinEdgeSize(), Math.min(PipSizeSpecHandler.m2755$$Nest$mgetDisplayBounds(pipSizeSpecHandler).width(), PipSizeSpecHandler.m2755$$Nest$mgetDisplayBounds(pipSizeSpecHandler).height()) * this.mDefaultSizePercent);
            if (f > pipSizeSpecHandler.mMinAspectRatioForMinSize) {
                float f2 = pipSizeSpecHandler.mMaxAspectRatioForMinSize;
                if (f <= f2) {
                    float f3 = max;
                    float length = PointF.length(f2 * f3, f3);
                    max = (int) Math.round(Math.sqrt((length * length) / ((f * f) + 1.0f)));
                    round = Math.round(max * f);
                    int i = round;
                    round2 = max;
                    max = i;
                    return new Size(max, round2);
                }
            }
            if (f <= 1.0f) {
                round2 = Math.round(max / f);
                return new Size(max, round2);
            }
            round = Math.round(max * f);
            int i2 = round;
            round2 = max;
            max = i2;
            return new Size(max, round2);
        }

        @Override // com.android.wm.shell.pip.phone.PipSizeSpecHandler.SizeSpecSource
        public final Size getMaxSize(float f) {
            int i;
            int i2;
            PipSizeSpecHandler pipSizeSpecHandler = PipSizeSpecHandler.this;
            int width = PipSizeSpecHandler.m2755$$Nest$mgetDisplayBounds(pipSizeSpecHandler).width();
            PipDisplayLayoutState pipDisplayLayoutState = pipSizeSpecHandler.mPipDisplayLayoutState;
            int min = Math.min(width, pipDisplayLayoutState.getDisplayBounds().height());
            int width2 = (pipDisplayLayoutState.getDisplayBounds().width() - pipSizeSpecHandler.getInsetBounds().right) + pipSizeSpecHandler.getInsetBounds().left;
            int height = (pipDisplayLayoutState.getDisplayBounds().height() - pipSizeSpecHandler.getInsetBounds().bottom) + pipSizeSpecHandler.getInsetBounds().top;
            if (f > 1.0f) {
                i2 = Math.max(getDefaultSize(f).getWidth(), min - width2);
                i = (int) (i2 / f);
            } else {
                int max = Math.max(getDefaultSize(f).getHeight(), min - height);
                int i3 = (int) (max * f);
                i = max;
                i2 = i3;
            }
            return new Size(i2, i);
        }

        @Override // com.android.wm.shell.pip.phone.PipSizeSpecHandler.SizeSpecSource
        public final Size getMinSize(float f) {
            int i;
            int i2;
            PipSizeSpecHandler pipSizeSpecHandler = PipSizeSpecHandler.this;
            if (pipSizeSpecHandler.mOverrideMinSize != null) {
                return pipSizeSpecHandler.adjustOverrideMinSizeToAspectRatio(f);
            }
            int min = Math.min(PipSizeSpecHandler.m2755$$Nest$mgetDisplayBounds(pipSizeSpecHandler).width(), PipSizeSpecHandler.m2755$$Nest$mgetDisplayBounds(pipSizeSpecHandler).height());
            if (f > 1.0f) {
                i2 = (int) Math.min(getDefaultSize(f).getWidth(), min * this.mMinimumSizePercent);
                i = (int) (i2 / f);
            } else {
                int min2 = (int) Math.min(getDefaultSize(f).getHeight(), min * this.mMinimumSizePercent);
                int i3 = (int) (min2 * f);
                i = min2;
                i2 = i3;
            }
            return new Size(i2, i);
        }

        @Override // com.android.wm.shell.pip.phone.PipSizeSpecHandler.SizeSpecSource
        public final Size getSizeForAspectRatio(Size size, float f) {
            int i;
            int min = Math.min(size.getWidth(), size.getHeight());
            PipSizeSpecHandler pipSizeSpecHandler = PipSizeSpecHandler.this;
            int max = Math.max(pipSizeSpecHandler.getMinEdgeSize(), min);
            if (f <= 1.0f) {
                int i2 = pipSizeSpecHandler.mDefaultMinWidth;
                if (i2 > max) {
                    max = i2;
                }
                i = Math.round(max / f);
            } else {
                max = Math.round(max * f);
                i = max;
            }
            return new Size(max, i);
        }

        @Override // com.android.wm.shell.pip.phone.PipSizeSpecHandler.SizeSpecSource
        public final void reloadResources() {
            PipSizeSpecHandler pipSizeSpecHandler = PipSizeSpecHandler.this;
            Resources resources = pipSizeSpecHandler.mContext.getResources();
            pipSizeSpecHandler.mMaxAspectRatioForMinSize = resources.getFloat(R.dimen.config_pictureInPictureAspectRatioLimitForMinSize);
            pipSizeSpecHandler.mMinAspectRatioForMinSize = 1.0f / pipSizeSpecHandler.mMaxAspectRatioForMinSize;
            this.mDefaultSizePercent = resources.getFloat(R.dimen.config_pictureInPictureDefaultSizePercent);
            this.mMinimumSizePercent = resources.getFraction(R.fraction.config_pipShortestEdgePercent, 1, 1);
        }

        private SizeSpecDefaultImpl() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SizeSpecLargeScreenOptimizedImpl implements SizeSpecSource {
        public final float mDefaultSizePercent;
        public final float mMinimumSizePercent;
        public float mOptimizedAspectRatio;

        public /* synthetic */ SizeSpecLargeScreenOptimizedImpl(PipSizeSpecHandler pipSizeSpecHandler, int i) {
            this();
        }

        @Override // com.android.wm.shell.pip.phone.PipSizeSpecHandler.SizeSpecSource
        public final Size getDefaultSize(float f) {
            Size minSize = getMinSize(f);
            if (PipSizeSpecHandler.this.mOverrideMinSize != null) {
                return minSize;
            }
            int max = Math.max(Math.round(getMaxSize(f).getWidth() * this.mDefaultSizePercent), minSize.getWidth());
            return new Size(max, Math.round(max / f));
        }

        @Override // com.android.wm.shell.pip.phone.PipSizeSpecHandler.SizeSpecSource
        public final Size getMaxSize(float f) {
            int i;
            PipSizeSpecHandler pipSizeSpecHandler = PipSizeSpecHandler.this;
            int min = (int) (Math.min(PipSizeSpecHandler.m2755$$Nest$mgetDisplayBounds(pipSizeSpecHandler).width() - ((PipSizeSpecHandler.m2755$$Nest$mgetDisplayBounds(pipSizeSpecHandler).width() - pipSizeSpecHandler.getInsetBounds().right) + pipSizeSpecHandler.getInsetBounds().left), PipSizeSpecHandler.m2755$$Nest$mgetDisplayBounds(pipSizeSpecHandler).height() - ((PipSizeSpecHandler.m2755$$Nest$mgetDisplayBounds(pipSizeSpecHandler).height() - pipSizeSpecHandler.getInsetBounds().bottom) + pipSizeSpecHandler.getInsetBounds().top)) * 1.0f);
            float f2 = this.mOptimizedAspectRatio;
            if (f >= f2 && f <= 1.0f / f2) {
                float f3 = min;
                min = (int) ((((f - f2) * f3) / (1.0f + f)) + (f2 * f3));
                i = Math.round(min / f);
            } else if (f > 1.0f) {
                i = Math.round(min / f);
            } else {
                min = Math.round(min * f);
                i = min;
            }
            return new Size(min, i);
        }

        @Override // com.android.wm.shell.pip.phone.PipSizeSpecHandler.SizeSpecSource
        public final Size getMinSize(float f) {
            int i;
            int i2;
            PipSizeSpecHandler pipSizeSpecHandler = PipSizeSpecHandler.this;
            if (pipSizeSpecHandler.mOverrideMinSize != null) {
                return pipSizeSpecHandler.adjustOverrideMinSizeToAspectRatio(f);
            }
            float width = getMaxSize(f).getWidth();
            float f2 = this.mMinimumSizePercent;
            int round = Math.round(width * f2);
            int round2 = Math.round(r1.getHeight() * f2);
            if (f > 1.0f) {
                i2 = Math.max(round2, pipSizeSpecHandler.mDefaultMinSize);
                i = Math.round(i2 * f);
            } else {
                int max = Math.max(round, pipSizeSpecHandler.mDefaultMinSize);
                int round3 = Math.round(max / f);
                i = max;
                i2 = round3;
            }
            return new Size(i, i2);
        }

        @Override // com.android.wm.shell.pip.phone.PipSizeSpecHandler.SizeSpecSource
        public final Size getSizeForAspectRatio(Size size, float f) {
            int i;
            float width = size.getWidth() / getMaxSize(size.getWidth() / size.getHeight()).getWidth();
            Size maxSize = getMaxSize(f);
            int round = Math.round(maxSize.getWidth() * width);
            int round2 = Math.round(maxSize.getHeight() * width);
            PipSizeSpecHandler pipSizeSpecHandler = PipSizeSpecHandler.this;
            if (round < pipSizeSpecHandler.getMinEdgeSize() && f <= 1.0f) {
                round = pipSizeSpecHandler.getMinEdgeSize();
                round2 = Math.round(round / f);
            } else if (round2 < pipSizeSpecHandler.getMinEdgeSize() && f > 1.0f) {
                round2 = pipSizeSpecHandler.getMinEdgeSize();
                round = Math.round(round2 * f);
            }
            if (f <= 1.0f && round < (i = pipSizeSpecHandler.mDefaultMinWidth)) {
                round2 = Math.round(i / f);
                round = i;
            }
            PipDisplayLayoutState pipDisplayLayoutState = pipSizeSpecHandler.mPipDisplayLayoutState;
            int height = (pipDisplayLayoutState.getDisplayBounds().height() - pipSizeSpecHandler.getInsetBounds().top) - (pipDisplayLayoutState.getDisplayBounds().height() - pipSizeSpecHandler.getInsetBounds().bottom);
            if (height > 0 && round2 > height) {
                round2 = maxSize.getHeight();
                round = maxSize.getWidth();
            }
            return new Size(round, round2);
        }

        @Override // com.android.wm.shell.pip.phone.PipSizeSpecHandler.SizeSpecSource
        public final void reloadResources() {
            float f = PipSizeSpecHandler.this.mContext.getResources().getFloat(R.dimen.config_pipLargeScreenOptimizedAspectRatio);
            this.mOptimizedAspectRatio = f;
            if (f > 1.0f) {
                this.mOptimizedAspectRatio = 0.5625f;
            }
        }

        private SizeSpecLargeScreenOptimizedImpl() {
            this.mDefaultSizePercent = Float.parseFloat(SystemProperties.get("com.android.wm.shell.pip.phone.def_percentage", "0.6"));
            this.mMinimumSizePercent = Float.parseFloat(SystemProperties.get("com.android.wm.shell.pip.phone.min_percentage", "0.5"));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface SizeSpecSource {
        Size getDefaultSize(float f);

        Size getMaxSize(float f);

        Size getMinSize(float f);

        Size getSizeForAspectRatio(Size size, float f);

        void reloadResources();
    }

    /* renamed from: -$$Nest$mgetDisplayBounds, reason: not valid java name */
    public static Rect m2755$$Nest$mgetDisplayBounds(PipSizeSpecHandler pipSizeSpecHandler) {
        return pipSizeSpecHandler.mPipDisplayLayoutState.getDisplayBounds();
    }

    public PipSizeSpecHandler(Context context, PipDisplayLayoutState pipDisplayLayoutState) {
        this.mContext = context;
        this.mPipDisplayLayoutState = pipDisplayLayoutState;
        int i = 0;
        if (supportsPipSizeLargeScreen()) {
            this.mSizeSpecSourceImpl = new SizeSpecLargeScreenOptimizedImpl(this, i);
        } else {
            this.mSizeSpecSourceImpl = new SizeSpecDefaultImpl(this, i);
        }
        reloadResources();
    }

    public Size adjustOverrideMinSizeToAspectRatio(float f) {
        if (this.mOverrideMinSize == null) {
            return null;
        }
        Size overrideMinSize = getOverrideMinSize();
        return ((float) overrideMinSize.getWidth()) / ((float) overrideMinSize.getHeight()) > f ? new Size(overrideMinSize.getWidth(), (int) (overrideMinSize.getWidth() / f)) : new Size((int) (overrideMinSize.getHeight() * f), overrideMinSize.getHeight());
    }

    public final Rect getInsetBounds() {
        Rect rect = new Rect();
        DisplayLayout displayLayout = this.mPipDisplayLayoutState.getDisplayLayout();
        Rect stableInsets = displayLayout.stableInsets(false);
        int i = stableInsets.left;
        Point point = this.mScreenEdgeInsets;
        int i2 = point.x;
        int i3 = stableInsets.top;
        int i4 = point.y;
        rect.set(i + i2, i3 + i4, (displayLayout.mWidth - stableInsets.right) - i2, (displayLayout.mHeight - stableInsets.bottom) - i4);
        return rect;
    }

    public final int getMinEdgeSize() {
        return this.mOverrideMinSize == null ? this.mDefaultMinSize : getOverrideMinEdgeSize();
    }

    public final int getOverrideMinEdgeSize() {
        if (this.mOverrideMinSize == null) {
            return 0;
        }
        return Math.min(getOverrideMinSize().getWidth(), getOverrideMinSize().getHeight());
    }

    public final Size getOverrideMinSize() {
        Size size = this.mOverrideMinSize;
        if (size == null || (size.getWidth() >= this.mOverridableMinSize && this.mOverrideMinSize.getHeight() >= this.mOverridableMinSize)) {
            return this.mOverrideMinSize;
        }
        int i = this.mOverridableMinSize;
        return new Size(i, i);
    }

    public final void reloadResources() {
        Resources resources = this.mContext.getResources();
        this.mDefaultMinSize = resources.getDimensionPixelSize(R.dimen.default_minimal_size_pip_resizable_task);
        this.mOverridableMinSize = resources.getDimensionPixelSize(R.dimen.overridable_minimal_size_pip_resizable_task);
        String string = resources.getString(R.string.config_defaultPictureInPictureScreenEdgeInsets);
        this.mScreenEdgeInsets = (!string.isEmpty() ? Size.parseSize(string) : null) == null ? new Point() : new Point(PipUtils.dpToPx(resources.getDisplayMetrics(), r1.getWidth()), PipUtils.dpToPx(resources.getDisplayMetrics(), r1.getHeight()));
        this.mSizeSpecSourceImpl.reloadResources();
        this.mDefaultMinWidth = resources.getDimensionPixelSize(R.dimen.pip_min_width);
    }

    public boolean supportsPipSizeLargeScreen() {
        return SystemProperties.getBoolean("persist.wm.debug.enable_pip_size_large_screen", true) && !this.mContext.getPackageManager().hasSystemFeature("android.software.leanback");
    }
}
