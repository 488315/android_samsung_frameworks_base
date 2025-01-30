package com.android.p038wm.shell.transition.change;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.window.TransitionInfo;
import com.android.p038wm.shell.common.DisplayLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class ChangeTransitionSpec {
    public static final Interpolator ANIMATION_INTERPOLATOR = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
    public Animation mBoundsChangeAnimation;
    public TransitionInfo.Change mChange;
    public Context mContext;
    public DisplayLayout mDisplayLayout;
    public Animation mSnapshotAnimation;
    public float mDurationScale = 1.0f;
    public final Rect mStartBounds = new Rect();
    public final Rect mStartOutsets = new Rect();
    public final Point mRootOffsets = new Point();
    public final Rect mEndBounds = new Rect();
    public final Rect mEndOutsets = new Rect();

    public static int dipToPixel(int i, Context context) {
        return (int) TypedValue.applyDimension(1, i, context.getResources().getDisplayMetrics());
    }

    public abstract Animation createBoundsChangeAnimation();

    public abstract Animation createSnapshotAnimation();

    public final long getAnimationDuration() {
        return (long) (this.mDurationScale * 400.0f);
    }

    public final Rect getDisplayFrame() {
        DisplayLayout displayLayout = this.mDisplayLayout;
        return new Rect(0, 0, displayLayout.mWidth, displayLayout.mHeight);
    }

    public long getSnapshotAlphaAnimationDuration() {
        return (long) (this.mDurationScale * 100.0f);
    }

    public boolean isRootOffsetNeeded() {
        return this instanceof DismissChangeTransitionSpec;
    }

    public abstract void setupChangeTransitionHierarchy(TransitionInfo.Change change, SurfaceControl.Transaction transaction);

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder("{mBoundsChangeAnimation=");
        sb.append(this.mBoundsChangeAnimation);
        sb.append(", mSnapshotAnimation=");
        sb.append(this.mSnapshotAnimation);
        sb.append(", mDurationScale=");
        sb.append(this.mDurationScale);
        sb.append(", mStartBounds=");
        sb.append(this.mStartBounds);
        sb.append(", mEndBounds=");
        sb.append(this.mEndBounds);
        if (isRootOffsetNeeded()) {
            str = ", mRootOffsets=" + this.mRootOffsets;
        } else {
            str = "";
        }
        sb.append(str);
        sb.append(", mChange=");
        sb.append(this.mChange);
        sb.append('}');
        return sb.toString();
    }

    public void reduceDurationScaleIfNeeded(TransitionInfo transitionInfo) {
    }
}
