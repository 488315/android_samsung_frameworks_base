package com.android.p038wm.shell.pip;

import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.Size;
import android.view.Gravity;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.pip.PipBoundsState;
import com.android.p038wm.shell.pip.phone.PipSizeSpecHandler;
import com.android.systemui.R;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class PipBoundsAlgorithm {
    public float mDefaultAspectRatio;
    public int mDefaultStackGravity;
    public float mMaxAspectRatio;
    public float mMinAspectRatio;
    public final PipBoundsState mPipBoundsState;
    public final PipKeepClearAlgorithmInterface mPipKeepClearAlgorithm;
    public final PipSizeSpecHandler mPipSizeSpecHandler;
    public final PipSnapAlgorithm mSnapAlgorithm;

    public PipBoundsAlgorithm(Context context, PipBoundsState pipBoundsState, PipSnapAlgorithm pipSnapAlgorithm, PipKeepClearAlgorithmInterface pipKeepClearAlgorithmInterface, PipSizeSpecHandler pipSizeSpecHandler) {
        this.mPipBoundsState = pipBoundsState;
        this.mSnapAlgorithm = pipSnapAlgorithm;
        this.mPipKeepClearAlgorithm = pipKeepClearAlgorithmInterface;
        this.mPipSizeSpecHandler = pipSizeSpecHandler;
        reloadResources(context);
        pipBoundsState.mAspectRatio = this.mDefaultAspectRatio;
    }

    public static Rect getValidSourceHintRect(PictureInPictureParams pictureInPictureParams, Rect rect) {
        Rect sourceRectHint = (pictureInPictureParams == null || !pictureInPictureParams.hasSourceBoundsHint()) ? null : pictureInPictureParams.getSourceRectHint();
        if (sourceRectHint == null || !rect.contains(sourceRectHint)) {
            return null;
        }
        return sourceRectHint;
    }

    public final void dump(PrintWriter printWriter, String str) {
        String m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "  ");
        printWriter.println(str + "PipBoundsAlgorithm");
        printWriter.println(m14m + "mDefaultAspectRatio=" + this.mDefaultAspectRatio);
        printWriter.println(m14m + "mMinAspectRatio=" + this.mMinAspectRatio);
        printWriter.println(m14m + "mMaxAspectRatio=" + this.mMaxAspectRatio);
        printWriter.println(m14m + "mDefaultStackGravity=" + this.mDefaultStackGravity);
        printWriter.println(m14m + "mSnapAlgorithm" + this.mSnapAlgorithm);
    }

    public Rect getAdjustedDestinationBounds(Rect rect, float f) {
        Rect rect2 = new Rect(rect);
        if (Float.compare(this.mMinAspectRatio, f) <= 0 && Float.compare(f, this.mMaxAspectRatio) <= 0) {
            transformBoundsToAspectRatio(rect2, f, true, false);
        }
        return rect2;
    }

    public final Rect getDefaultBounds(Size size, float f) {
        Rect rect = new Rect();
        PipSnapAlgorithm pipSnapAlgorithm = this.mSnapAlgorithm;
        if (f != -1.0f && size != null) {
            rect.set(0, 0, size.getWidth(), size.getHeight());
            Rect movementBounds = getMovementBounds(rect, true);
            pipSnapAlgorithm.getClass();
            PipSnapAlgorithm.applySnapFraction(f, rect, movementBounds);
            return rect;
        }
        Rect rect2 = new Rect();
        getInsetBounds(rect2);
        Size defaultSize = this.mPipSizeSpecHandler.mSizeSpecSourceImpl.getDefaultSize(this.mDefaultAspectRatio);
        if (f != -1.0f) {
            rect.set(0, 0, defaultSize.getWidth(), defaultSize.getHeight());
            Rect movementBounds2 = getMovementBounds(rect, true);
            pipSnapAlgorithm.getClass();
            PipSnapAlgorithm.applySnapFraction(f, rect, movementBounds2);
        } else {
            int i = this.mDefaultStackGravity;
            int width = defaultSize.getWidth();
            int height = defaultSize.getHeight();
            PipBoundsState pipBoundsState = this.mPipBoundsState;
            Gravity.apply(i, width, height, rect2, 0, Math.max(pipBoundsState.mIsImeShowing ? pipBoundsState.mImeHeight : 0, pipBoundsState.mIsShelfShowing ? pipBoundsState.mShelfHeight : 0), rect);
        }
        return rect;
    }

    public Rect getEntryDestinationBounds() {
        Rect entryDestinationBoundsIgnoringKeepClearAreas = getEntryDestinationBoundsIgnoringKeepClearAreas();
        Rect rect = new Rect();
        getInsetBounds(rect);
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        return this.mPipKeepClearAlgorithm.findUnoccludedPosition(entryDestinationBoundsIgnoringKeepClearAreas, pipBoundsState.mRestrictedKeepClearAreas, pipBoundsState.getUnrestrictedKeepClearAreas(), rect);
    }

    public final Rect getEntryDestinationBoundsIgnoringKeepClearAreas() {
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        PipBoundsState.PipReentryState pipReentryState = pipBoundsState.mPipReentryState;
        Rect defaultBounds = pipReentryState != null ? getDefaultBounds(pipReentryState.mSize, pipReentryState.mSnapFraction) : getDefaultBounds(null, -1.0f);
        boolean z = (pipReentryState == null || pipReentryState.mSize == null || defaultBounds.width() <= this.mPipSizeSpecHandler.mDefaultMinWidth) ? false : true;
        float f = pipBoundsState.mAspectRatio;
        Rect rect = new Rect(defaultBounds);
        if (Float.compare(this.mMinAspectRatio, f) <= 0 && Float.compare(f, this.mMaxAspectRatio) <= 0) {
            transformBoundsToAspectRatio(rect, f, false, z);
        }
        return rect;
    }

    public final void getInsetBounds(Rect rect) {
        rect.set(this.mPipSizeSpecHandler.getInsetBounds());
    }

    public final Size getMinimalSize(ActivityInfo activityInfo) {
        ActivityInfo.WindowLayout windowLayout;
        if (activityInfo == null || (windowLayout = activityInfo.windowLayout) == null || windowLayout.minWidth <= 0 || windowLayout.minHeight <= 0) {
            return null;
        }
        int i = windowLayout.minWidth;
        PipSizeSpecHandler pipSizeSpecHandler = this.mPipSizeSpecHandler;
        return new Size(Math.max(i, pipSizeSpecHandler.getOverrideMinEdgeSize()), Math.max(windowLayout.minHeight, pipSizeSpecHandler.getOverrideMinEdgeSize()));
    }

    public final Rect getMovementBounds(Rect rect, boolean z) {
        int i;
        Rect rect2 = new Rect();
        getInsetBounds(rect2);
        if (z) {
            PipBoundsState pipBoundsState = this.mPipBoundsState;
            if (pipBoundsState.mIsImeShowing) {
                i = pipBoundsState.mImeHeight;
                getMovementBounds(rect, rect2, rect2, i);
                return rect2;
            }
        }
        i = 0;
        getMovementBounds(rect, rect2, rect2, i);
        return rect2;
    }

    public void onConfigurationChanged(Context context) {
        reloadResources(context);
    }

    public final void reloadResources(Context context) {
        Resources resources = context.getResources();
        this.mDefaultAspectRatio = resources.getFloat(R.dimen.config_pictureInPictureDefaultAspectRatio);
        this.mDefaultStackGravity = resources.getInteger(R.integer.config_defaultPictureInPictureGravity);
        String string = resources.getString(R.string.config_defaultPictureInPictureScreenEdgeInsets);
        if (!string.isEmpty()) {
            Size.parseSize(string);
        }
        this.mMinAspectRatio = resources.getFloat(android.R.dimen.config_letterboxHorizontalPositionMultiplier);
        this.mMaxAspectRatio = resources.getFloat(android.R.dimen.config_letterboxDefaultMinAspectRatioForUnresizableApps);
    }

    public final void transformBoundsToAspectRatio(Rect rect, float f, boolean z, boolean z2) {
        Size adjustOverrideMinSizeToAspectRatio;
        float snapFraction = this.mSnapAlgorithm.getSnapFraction(this.mPipBoundsState.mStashedState, rect, getMovementBounds(rect, true));
        PipSizeSpecHandler pipSizeSpecHandler = this.mPipSizeSpecHandler;
        if (z || z2) {
            Size size = new Size(rect.width(), rect.height());
            adjustOverrideMinSizeToAspectRatio = size.equals(pipSizeSpecHandler.mOverrideMinSize) ? pipSizeSpecHandler.adjustOverrideMinSizeToAspectRatio(f) : pipSizeSpecHandler.mSizeSpecSourceImpl.getSizeForAspectRatio(size, f);
        } else {
            adjustOverrideMinSizeToAspectRatio = pipSizeSpecHandler.mSizeSpecSourceImpl.getDefaultSize(f);
        }
        int centerX = (int) (rect.centerX() - (adjustOverrideMinSizeToAspectRatio.getWidth() / 2.0f));
        int centerY = (int) (rect.centerY() - (adjustOverrideMinSizeToAspectRatio.getHeight() / 2.0f));
        rect.set(centerX, centerY, adjustOverrideMinSizeToAspectRatio.getWidth() + centerX, adjustOverrideMinSizeToAspectRatio.getHeight() + centerY);
        PipSnapAlgorithm.applySnapFraction(snapFraction, rect, getMovementBounds(rect, true));
    }

    public static void getMovementBounds(Rect rect, Rect rect2, Rect rect3, int i) {
        rect3.set(rect2);
        rect3.right = Math.max(rect2.left, rect2.right - rect.width());
        rect3.bottom = Math.max(rect2.top, rect2.bottom - rect.height()) - i;
    }
}
