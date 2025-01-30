package com.android.server.wm;

import android.os.Process;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class PopOverController {
  static final float POP_OVER_DIM_AMOUNT_FOR_CHILD = 0.13f;
  static final float POP_OVER_DIM_AMOUNT_FOR_NIGHT = 0.5f;
  static final long POP_OVER_DIM_DURATION = 150;
  public final DisplayContent mDisplayContent;

  public PopOverController(DisplayContent displayContent) {
    this.mDisplayContent = displayContent;
  }

  public void addPopOverWindowLw(WindowState windowState) {
    WindowState findMainWindow;
    if (!isChildDimmingDialog(windowState)
        || (findMainWindow = windowState.mActivityRecord.findMainWindow(false)) == null) {
      return;
    }
    if (findMainWindow.mChildDimmingDialogs.isEmpty()) {
      WindowManager.LayoutParams layoutParams = findMainWindow.mAttrs;
      findMainWindow.mOriginalDimBehind = layoutParams.flags & 2;
      findMainWindow.mOriginalDimAmount = layoutParams.dimAmount;
      findMainWindow.mOriginalDimDuration = layoutParams.dimDuration;
    }
    findMainWindow.mChildDimmingDialogs.add(windowState);
    updatePopOverDimAttributesLw(findMainWindow, findMainWindow.mAttrs);
  }

  public void removePopOverWindowLw(WindowState windowState) {
    WindowState findMainWindow = windowState.mActivityRecord.findMainWindow(false);
    if (findMainWindow == null || !findMainWindow.mChildDimmingDialogs.contains(windowState)) {
      return;
    }
    findMainWindow.mChildDimmingDialogs.remove(windowState);
    updatePopOverDimAttributesLw(findMainWindow, findMainWindow.mAttrs);
  }

  public final boolean isChildDimmingDialog(WindowState windowState) {
    WindowManager.LayoutParams layoutParams;
    int i;
    return (windowState.mAttrs.isFullscreen()
            || (i = (layoutParams = windowState.mAttrs).type) == 1
            || i == 3
            || (layoutParams.flags & 2) == 0)
        ? false
        : true;
  }

  public void updatePopOverDimAttributesLw(
      WindowState windowState, WindowManager.LayoutParams layoutParams) {
    windowState.mPopOverDimmerNeeded = false;
    if (windowState.mOriginalDimBehind == 2) {
      return;
    }
    if ((this.mDisplayContent.getConfiguration().uiMode & 32) != 0) {
      layoutParams.flags |= 2;
      layoutParams.dimAmount = POP_OVER_DIM_AMOUNT_FOR_NIGHT;
      layoutParams.dimDuration = POP_OVER_DIM_DURATION;
    } else if (!windowState.mChildDimmingDialogs.isEmpty()) {
      layoutParams.flags |= 2;
      layoutParams.dimAmount = POP_OVER_DIM_AMOUNT_FOR_CHILD;
      layoutParams.dimDuration = POP_OVER_DIM_DURATION;
    } else {
      layoutParams.flags &= -3;
      layoutParams.dimAmount = windowState.mOriginalDimAmount;
      layoutParams.dimDuration = windowState.mOriginalDimDuration;
    }
    if ((layoutParams.flags & 2) != 0) {
      windowState.mPopOverDimmerNeeded = true;
    }
  }

  public int getDistanceToTopForPopOver(WindowState windowState, int i, int i2) {
    if (windowState.isDesktopModeEnabled() || windowState.inFreeformWindowingMode()) {
      i -= windowState.getCaptionHeight();
      if (windowState.inFreeformWindowingMode()) {
        i -= windowState.getFreeformThickness();
      }
      if (windowState.inFullscreenWindowingMode()) {
        i -=
            PopOverBoundsCalculator.getPopOverShadow(
                this.mDisplayContent.mWmService.mContext.getResources());
      }
    }
    return Math.max(Math.max(i, 0) - i2, 0);
  }

  public void updateTransparency(final WindowState windowState, final boolean z) {
    if (windowState.getTask() == null || !windowState.isVisible()) {
      return;
    }
    final boolean[] zArr = {false};
    windowState
        .getTask()
        .forAllActivities(
            new Predicate() { // from class:
                              // com.android.server.wm.PopOverController$$ExternalSyntheticLambda0
              @Override // java.util.function.Predicate
              public final boolean test(Object obj) {
                boolean lambda$updateTransparency$0;
                lambda$updateTransparency$0 =
                    PopOverController.this.lambda$updateTransparency$0(
                        zArr, z, windowState, (ActivityRecord) obj);
                return lambda$updateTransparency$0;
              }
            });
    if (zArr[0]) {
      this.mDisplayContent.mWmService.mPolicy.performHapticFeedback(
          Process.myUid(),
          this.mDisplayContent.mWmService.mContext.getOpPackageName(),
          0,
          false,
          "Pop-over transparent mode");
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public /* synthetic */ boolean lambda$updateTransparency$0(
      boolean[] zArr, boolean z, WindowState windowState, ActivityRecord activityRecord) {
    WindowState findMainWindow;
    if (!activityRecord.mPopOverState.isActivated()
        || !activityRecord.isVisible()
        || (findMainWindow = activityRecord.findMainWindow()) == null
        || findMainWindow.mWinAnimator == null) {
      return true;
    }
    zArr[0] = z;
    if (findMainWindow == windowState) {
      return false;
    }
    if (findMainWindow.isAnimating(0, 16)) {
      findMainWindow.cancelAnimation();
    }
    startTransparentAnimation(findMainWindow, z);
    return false;
  }

  public final void startTransparentAnimation(final WindowState windowState, final boolean z) {
    float f = z ? 1.0f : 0.001f;
    final float f2 = z ? 0.001f : 1.0f;
    AlphaAnimation alphaAnimation = new AlphaAnimation(f, f2);
    alphaAnimation.setDuration(z ? 100L : 200L);
    alphaAnimation.setStartOffset(z ? 0L : 100L);
    alphaAnimation.setAnimationListener(
        new Animation.AnimationListener() { // from class: com.android.server.wm.PopOverController.1
          @Override // android.view.animation.Animation.AnimationListener
          public void onAnimationRepeat(Animation animation) {}

          @Override // android.view.animation.Animation.AnimationListener
          public void onAnimationStart(Animation animation) {
            if (z) {
              return;
            }
            WindowManagerGlobalLock windowManagerGlobalLock =
                PopOverController.this.mDisplayContent.mWmService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
              try {
                windowState.mWinAnimator.mPopOverAlpha = -1.0f;
                PopOverController.this.mDisplayContent.scheduleAnimation();
              } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
              }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
          }

          @Override // android.view.animation.Animation.AnimationListener
          public void onAnimationEnd(Animation animation) {
            WindowManagerGlobalLock windowManagerGlobalLock =
                PopOverController.this.mDisplayContent.mWmService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
              try {
                windowState.mWinAnimator.mPopOverAlpha = z ? f2 : -1.0f;
                PopOverController.this.mDisplayContent.scheduleAnimation();
              } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
              }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
          }
        });
    windowState.startAnimation(alphaAnimation);
  }
}
