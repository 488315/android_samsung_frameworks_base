package com.android.p038wm.shell.transition;

import android.graphics.PointF;
import android.graphics.Rect;
import android.util.TypedValue;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import com.android.p038wm.shell.common.DisplayController;
import com.android.systemui.R;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class FreeformAnimationLoader extends AnimationLoader {
    public FreeformAnimationLoader(MultiTaskingTransitionState multiTaskingTransitionState) {
        super(multiTaskingTransitionState);
    }

    @Override // com.android.p038wm.shell.transition.AnimationLoader
    public final float getCornerRadius() {
        MultiTaskingTransitionState multiTaskingTransitionState = this.mState;
        if (multiTaskingTransitionState.mDisplayController.getDisplayContext(multiTaskingTransitionState.mDisplayId) == null) {
            return 0.0f;
        }
        return (int) TypedValue.applyDimension(1, 14, r2.getResources().getDisplayMetrics());
    }

    @Override // com.android.p038wm.shell.transition.AnimationLoader
    public final boolean isAvailable() {
        return this.mState.mWindowingMode == 5;
    }

    @Override // com.android.p038wm.shell.transition.AnimationLoader
    public final void loadAnimationIfPossible() {
        Animation animation;
        boolean z = CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION;
        MultiTaskingTransitionState multiTaskingTransitionState = this.mState;
        if (z) {
            int i = multiTaskingTransitionState.mMinimizeAnimState;
            boolean z2 = i == 1;
            PointF pointF = multiTaskingTransitionState.mMinimizePoint;
            DisplayController displayController = multiTaskingTransitionState.mDisplayController;
            if (z2) {
                Rect rect = new Rect(multiTaskingTransitionState.getBounds());
                PointF pointF2 = new PointF(rect.centerX(), rect.centerY());
                if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER) {
                    pointF2.set(pointF);
                }
                Rect rect2 = new Rect();
                displayController.getDisplayLayout(multiTaskingTransitionState.mDisplayId).getStableBounds(rect2, false);
                animation = multiTaskingTransitionState.createMinimizeAnimation(false, pointF2, rect, multiTaskingTransitionState.mFreeformStashScale, rect.left < rect2.left && multiTaskingTransitionState.mFreeformStashScale < 1.0f);
                animation.setAnimationListener(new Animation.AnimationListener(multiTaskingTransitionState, multiTaskingTransitionState.mTaskId, pointF2) { // from class: com.android.wm.shell.transition.MultiTaskingTransitionState.1
                    public final /* synthetic */ PointF val$targetPoint;
                    public final /* synthetic */ int val$taskId;

                    public AnimationAnimationListenerC41451(MultiTaskingTransitionState multiTaskingTransitionState2, int i2, PointF pointF22) {
                        this.val$taskId = i2;
                        this.val$targetPoint = pointF22;
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public final void onAnimationEnd(Animation animation2) {
                        MultiWindowManager.getInstance().notifyFreeformMinimizeAnimationEnd(this.val$taskId, this.val$targetPoint);
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public final void onAnimationRepeat(Animation animation2) {
                    }

                    @Override // android.view.animation.Animation.AnimationListener
                    public final void onAnimationStart(Animation animation2) {
                    }
                });
            } else {
                if (i == 2) {
                    Rect rect3 = new Rect(multiTaskingTransitionState2.getBounds());
                    PointF pointF3 = new PointF();
                    if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER) {
                        pointF3.set(pointF);
                    } else {
                        pointF3.set(rect3.centerX(), rect3.centerY());
                    }
                    Rect rect4 = new Rect();
                    displayController.getDisplayLayout(multiTaskingTransitionState2.mDisplayId).getStableBounds(rect4, false);
                    animation = multiTaskingTransitionState2.createMinimizeAnimation(true, pointF3, rect3, multiTaskingTransitionState2.mFreeformStashScale, rect3.left < rect4.left && multiTaskingTransitionState2.mFreeformStashScale < 1.0f);
                } else {
                    animation = null;
                }
            }
            if (animation != null) {
                multiTaskingTransitionState2.setAnimation(animation);
            }
            if (multiTaskingTransitionState2.mAnimationLoaded) {
                return;
            }
        }
        if (CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION) {
            int i2 = multiTaskingTransitionState2.mForceHidingTransit;
            if (i2 != 0) {
                if (multiTaskingTransitionState2.mFreeformStashScale != 1.0f) {
                    multiTaskingTransitionState2.setAnimation(AnimationLoader.NO_ANIMATION);
                    return;
                }
                int i3 = i2 == 1 ? R.anim.freeform_window_force_hide_enter : R.anim.freeform_window_force_hide_exit;
                Animation loadAnimationFromResources = multiTaskingTransitionState2.loadAnimationFromResources(i3);
                loadAnimationFromResources.setInterpolator(InterpolatorUtils.SINE_OUT_60);
                if ((i3 == R.anim.freeform_open_enter || i3 == R.anim.freeform_close_exit) && (loadAnimationFromResources instanceof AnimationSet)) {
                    addRoundedClipAnimation(multiTaskingTransitionState2.getBounds(), (AnimationSet) loadAnimationFromResources);
                }
                multiTaskingTransitionState2.setAnimation(loadAnimationFromResources);
                return;
            }
        }
        if (CoreRune.MT_NEW_DEX_SHELL_TRANSITION && multiTaskingTransitionState2.mIsFreeformMovingBehindSplitScreen) {
            multiTaskingTransitionState2.setAnimation(new AlphaAnimation(0.0f, 0.0f));
            return;
        }
        boolean z3 = multiTaskingTransitionState2.mIsEnter;
        int i4 = multiTaskingTransitionState2.mTransitionType;
        int i5 = i4 == 1 || i4 == 3 ? z3 ? R.anim.freeform_open_enter : R.anim.freeform_open_exit : multiTaskingTransitionState2.isClosingTransitionType() ? z3 ? R.anim.freeform_close_enter : R.anim.freeform_close_exit : -1;
        if (i5 != -1) {
            Animation loadAnimationFromResources2 = multiTaskingTransitionState2.loadAnimationFromResources(i5);
            if ((i5 == R.anim.freeform_open_enter || i5 == R.anim.freeform_close_exit) && (loadAnimationFromResources2 instanceof AnimationSet)) {
                addRoundedClipAnimation(multiTaskingTransitionState2.getBounds(), (AnimationSet) loadAnimationFromResources2);
            }
            multiTaskingTransitionState2.setAnimation(loadAnimationFromResources2);
        }
    }

    public final String toString() {
        return "FreeformAnimationLoader";
    }
}
