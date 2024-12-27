package com.android.server.wm;

import android.os.Process;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import com.android.server.policy.PhoneWindowManager;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PopOverController {
    static final float POP_OVER_DIM_AMOUNT_FOR_CHILD = 0.13f;
    static final float POP_OVER_DIM_AMOUNT_FOR_NIGHT = 0.5f;
    static final long POP_OVER_DIM_DURATION = 150;
    public final DisplayContent mDisplayContent;

    public PopOverController(DisplayContent displayContent) {
        this.mDisplayContent = displayContent;
    }

    public final void updatePopOverDimAttributesLw(WindowState windowState, WindowManager.LayoutParams layoutParams) {
        windowState.mPopOverDimmerNeeded = false;
        if (windowState.mOriginalDimBehind == 2) {
            return;
        }
        if ((this.mDisplayContent.getConfiguration().uiMode & 32) != 0) {
            layoutParams.flags |= 2;
            layoutParams.dimAmount = POP_OVER_DIM_AMOUNT_FOR_NIGHT;
            layoutParams.dimDuration = POP_OVER_DIM_DURATION;
        } else if (windowState.mChildDimmingDialogs.isEmpty()) {
            layoutParams.flags &= -3;
            layoutParams.dimAmount = windowState.mOriginalDimAmount;
            layoutParams.dimDuration = windowState.mOriginalDimDuration;
        } else {
            layoutParams.flags |= 2;
            layoutParams.dimAmount = POP_OVER_DIM_AMOUNT_FOR_CHILD;
            layoutParams.dimDuration = POP_OVER_DIM_DURATION;
        }
        if ((layoutParams.flags & 2) != 0) {
            windowState.mPopOverDimmerNeeded = true;
        }
    }

    public final void updateTransparency(final WindowState windowState, final boolean z) {
        if (windowState.getTask() == null || !windowState.isVisible()) {
            return;
        }
        final boolean[] zArr = {false};
        windowState.getTask().forAllActivities(new Predicate() { // from class: com.android.server.wm.PopOverController$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                final WindowState findMainWindow;
                final PopOverController popOverController = PopOverController.this;
                boolean[] zArr2 = zArr;
                final boolean z2 = z;
                WindowState windowState2 = windowState;
                ActivityRecord activityRecord = (ActivityRecord) obj;
                popOverController.getClass();
                boolean z3 = true;
                if (activityRecord.mPopOverState.mIsActivated && activityRecord.mVisible && (findMainWindow = activityRecord.findMainWindow(true)) != null && findMainWindow.mWinAnimator != null) {
                    z3 = false;
                    zArr2[0] = z2;
                    if (findMainWindow != windowState2) {
                        if (findMainWindow.isAnimating(0, 16)) {
                            findMainWindow.cancelAnimation();
                        }
                        float f = z2 ? 1.0f : 0.001f;
                        final float f2 = z2 ? 0.001f : 1.0f;
                        AlphaAnimation alphaAnimation = new AlphaAnimation(f, f2);
                        alphaAnimation.setDuration(z2 ? 100L : 200L);
                        alphaAnimation.setStartOffset(z2 ? 0L : 100L);
                        alphaAnimation.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.server.wm.PopOverController.1
                            @Override // android.view.animation.Animation.AnimationListener
                            public final void onAnimationEnd(Animation animation) {
                                WindowManagerGlobalLock windowManagerGlobalLock = PopOverController.this.mDisplayContent.mWmService.mGlobalLock;
                                WindowManagerService.boostPriorityForLockedSection();
                                synchronized (windowManagerGlobalLock) {
                                    try {
                                        findMainWindow.mWinAnimator.mPopOverAlpha = z2 ? f2 : -1.0f;
                                        PopOverController.this.mDisplayContent.scheduleAnimation();
                                    } catch (Throwable th) {
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                        throw th;
                                    }
                                }
                                WindowManagerService.resetPriorityAfterLockedSection();
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public final void onAnimationRepeat(Animation animation) {
                            }

                            @Override // android.view.animation.Animation.AnimationListener
                            public final void onAnimationStart(Animation animation) {
                                if (z2) {
                                    return;
                                }
                                WindowManagerGlobalLock windowManagerGlobalLock = PopOverController.this.mDisplayContent.mWmService.mGlobalLock;
                                WindowManagerService.boostPriorityForLockedSection();
                                synchronized (windowManagerGlobalLock) {
                                    try {
                                        findMainWindow.mWinAnimator.mPopOverAlpha = -1.0f;
                                        PopOverController.this.mDisplayContent.scheduleAnimation();
                                    } catch (Throwable th) {
                                        WindowManagerService.resetPriorityAfterLockedSection();
                                        throw th;
                                    }
                                }
                                WindowManagerService.resetPriorityAfterLockedSection();
                            }
                        });
                        findMainWindow.startAnimation(alphaAnimation);
                    }
                }
                return z3;
            }
        });
        if (zArr[0]) {
            DisplayContent displayContent = this.mDisplayContent;
            ((PhoneWindowManager) displayContent.mWmService.mPolicy).mExt.performHapticFeedback(Process.myUid(), 0, displayContent.mWmService.mContext.getOpPackageName(), "Pop-over transparent mode", false, false);
        }
    }
}
