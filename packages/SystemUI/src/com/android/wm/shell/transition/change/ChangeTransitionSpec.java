package com.android.wm.shell.transition.change;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.SystemProperties;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.window.TransitionInfo;
import androidx.slice.widget.ActionRow$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.shared.animation.Interpolators;

/* loaded from: classes3.dex */
public abstract class ChangeTransitionSpec {
    public Animation mBoundsChangeAnimation;
    public TransitionInfo.Change mChange;
    public Context mContext;
    public DisplayLayout mDisplayLayout;
    public Animation mSnapshotAnimation;
    public TransitionInfo mTransitionInfo;
    public final AnimationAttributePolicy mAnimationAttributePolicy = new AnimationAttributePolicy();
    public float mDurationScale = 1.0f;
    public final Rect mStartBounds = new Rect();
    public final Rect mStartOutsets = new Rect();
    public final Point mRootOffsets = new Point();
    public final Rect mEndBounds = new Rect();
    public final Rect mEndOutsets = new Rect();

    public class AnimationAttributePolicy {
        public static final Interpolator ONE_EASING;
        public static final int SNAPSHOT_ALPHA_ANIM_START_OFFSET;
        public static final boolean USE_LEGACY_VI;
        public final Interpolator mDefaultInterpolator;

        static {
            boolean z = SystemProperties.getBoolean("persist.debug.change.anim.legacy", true);
            USE_LEGACY_VI = z;
            ONE_EASING = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
            SNAPSHOT_ALPHA_ANIM_START_OFFSET = z ? 50 : 0;
        }

        public AnimationAttributePolicy() {
            this.mDefaultInterpolator = USE_LEGACY_VI ? ONE_EASING : Interpolators.FAST_OUT_SLOW_IN;
        }
    }

    public static int dipToPixel(int i, Context context) {
        return (int) ActionRow$$ExternalSyntheticOutline0.m(context, 1, i);
    }

    public abstract Animation createBoundsChangeAnimation();

    public abstract Animation createSnapshotAnimation();

    public final long getAnimationDuration() {
        float f = this.mDurationScale;
        this.mAnimationAttributePolicy.getClass();
        return (long) (f * 400);
    }

    public final Rect getDisplayFrame() {
        DisplayLayout displayLayout = this.mDisplayLayout;
        return new Rect(0, 0, displayLayout.mWidth, displayLayout.mHeight);
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
