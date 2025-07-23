package com.android.wm.shell.transition;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import androidx.slice.widget.ActionRow$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.common.DisplayController;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class FreeformAnimationLoader extends AnimationLoader {
    public FreeformAnimationLoader(MultiTaskingTransitionState multiTaskingTransitionState) {
        super(multiTaskingTransitionState);
    }

    @Override // com.android.wm.shell.transition.AnimationLoader
    public final float getCornerRadius(Context context) {
        if (context == null) {
            return 0.0f;
        }
        return (int) ActionRow$$ExternalSyntheticOutline0.m(context, 1, 14);
    }

    @Override // com.android.wm.shell.transition.AnimationLoader
    public final boolean isAvailable() {
        return this.mState.mWindowingMode == 5;
    }

    @Override // com.android.wm.shell.transition.AnimationLoader
    public final void loadAnimationIfPossible() {
        Animation loadAnimationFromResources;
        int i;
        ActivityManager.RunningTaskInfo runningTaskInfo;
        Animation animation;
        boolean z = CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION;
        MultiTaskingTransitionState multiTaskingTransitionState = this.mState;
        if (z) {
            int i2 = multiTaskingTransitionState.mMinimizeAnimState;
            DisplayController displayController = multiTaskingTransitionState.mDisplayController;
            if (i2 == 1) {
                Rect rect = new Rect(multiTaskingTransitionState.getBounds());
                boolean z2 = false;
                PointF pointF = new PointF(rect.centerX(), rect.centerY());
                if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER) {
                    pointF.set(multiTaskingTransitionState.mMinimizePoint);
                }
                if (CoreRune.MW_CAPTION_FREEFORM_STASH) {
                    Rect rect2 = new Rect();
                    displayController.getDisplayLayout(multiTaskingTransitionState.mDisplayId).getStableBounds(rect2, false);
                    if (rect.left < rect2.left && multiTaskingTransitionState.mFreeformStashScale < 1.0f) {
                        z2 = true;
                    }
                }
                animation = multiTaskingTransitionState.createMinimizeAnimation(false, pointF, rect, multiTaskingTransitionState.mFreeformStashScale, z2);
                animation.setAnimationListener(new Animation.AnimationListener(multiTaskingTransitionState, multiTaskingTransitionState.mTaskId, pointF) { // from class: com.android.wm.shell.transition.MultiTaskingTransitionState.1
                    public final /* synthetic */ PointF val$targetPoint;
                    public final /* synthetic */ int val$taskId;

                    public AnonymousClass1(MultiTaskingTransitionState multiTaskingTransitionState2, int i3, PointF pointF2) {
                        this.val$taskId = i3;
                        this.val$targetPoint = pointF2;
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
                boolean z3 = false;
                if (i2 == 2) {
                    Rect rect3 = new Rect(multiTaskingTransitionState2.getBounds());
                    PointF pointF2 = new PointF();
                    if (CoreRune.MW_FREEFORM_MINIMIZE_CONTAINER) {
                        pointF2.set(multiTaskingTransitionState2.mMinimizePoint);
                    } else {
                        pointF2.set(rect3.centerX(), rect3.centerY());
                    }
                    if (CoreRune.MW_CAPTION_FREEFORM_STASH) {
                        Rect rect4 = new Rect();
                        displayController.getDisplayLayout(multiTaskingTransitionState2.mDisplayId).getStableBounds(rect4, false);
                        if (rect3.left < rect4.left && multiTaskingTransitionState2.mFreeformStashScale < 1.0f) {
                            z3 = true;
                        }
                    }
                    animation = multiTaskingTransitionState2.createMinimizeAnimation(true, pointF2, rect3, multiTaskingTransitionState2.mFreeformStashScale, z3);
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
        if (!CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION || (i = multiTaskingTransitionState2.mForceHidingTransit) == 0) {
            boolean z4 = multiTaskingTransitionState2.mIsEnter;
            int i3 = multiTaskingTransitionState2.isOpeningTransitionType() ? z4 ? R.anim.freeform_open_enter : R.anim.freeform_open_exit : multiTaskingTransitionState2.isClosingTransitionType() ? z4 ? R.anim.freeform_close_enter : R.anim.freeform_close_exit : -1;
            if (i3 == -1 || (loadAnimationFromResources = multiTaskingTransitionState2.loadAnimationFromResources(i3)) == null) {
                return;
            }
            if (((multiTaskingTransitionState2.isOpeningTransitionType() && z4) || (multiTaskingTransitionState2.isClosingTransitionType() && !z4)) && (loadAnimationFromResources instanceof AnimationSet)) {
                addRoundedClipAnimation(multiTaskingTransitionState2.getBounds(), (AnimationSet) loadAnimationFromResources);
            }
            multiTaskingTransitionState2.setAnimation(loadAnimationFromResources);
            return;
        }
        if (CoreRune.MW_CAPTION_FREEFORM_STASH && multiTaskingTransitionState2.mFreeformStashScale != 1.0f) {
            multiTaskingTransitionState2.setAnimation(AnimationLoader.NO_ANIMATION);
            return;
        }
        if (i == 4 || i == 3 || !((runningTaskInfo = multiTaskingTransitionState2.mTaskInfo) == null || runningTaskInfo.isRunning || multiTaskingTransitionState2.mChange.getMode() != 2)) {
            multiTaskingTransitionState2.setAnimation(AnimationLoader.NO_ANIMATION);
            return;
        }
        Animation loadAnimationFromResources2 = multiTaskingTransitionState2.loadAnimationFromResources(multiTaskingTransitionState2.mForceHidingTransit == 1 ? R.anim.freeform_window_force_hide_enter : R.anim.freeform_window_force_hide_exit);
        if (loadAnimationFromResources2 != null) {
            loadAnimationFromResources2.setInterpolator(InterpolatorUtils.SINE_OUT_60);
            multiTaskingTransitionState2.setAnimation(loadAnimationFromResources2);
        }
    }

    public final String toString() {
        return "FreeformAnimationLoader";
    }
}
