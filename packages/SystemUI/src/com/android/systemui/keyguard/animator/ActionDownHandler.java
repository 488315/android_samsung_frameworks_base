package com.android.systemui.keyguard.animator;

import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.statusbar.LockscreenShadeTransitionController;
import java.util.ArrayList;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ActionDownHandler extends ActionHandlerType {
    public ActionDownHandler(KeyguardTouchAnimator keyguardTouchAnimator) {
        super(keyguardTouchAnimator);
    }

    @Override // com.android.systemui.keyguard.animator.ActionHandlerType
    public final boolean handleMotionEvent(MotionEvent motionEvent) {
        Job job;
        KeyguardTouchAnimator keyguardTouchAnimator = this.parent;
        keyguardTouchAnimator.setTouch(true);
        LockscreenShadeTransitionController lockscreenShadeTransitionController = keyguardTouchAnimator.lockscreenShadeTransitionController;
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("ActionDownHandler isKeyguardAnimatorStarted = ", "KeyguardTouchAnimator", lockscreenShadeTransitionController.isKeyguardAnimatorStarted);
        lockscreenShadeTransitionController.isKeyguardAnimatorStarted = false;
        keyguardTouchAnimator.setDraggingDownStarted(false);
        keyguardTouchAnimator.hasDozeAmount = !(keyguardTouchAnimator.dozeAmount == 0.0f);
        keyguardTouchAnimator.isMultiTouch = false;
        FullScreenViewController fullScreenViewController = keyguardTouchAnimator.fullScreenViewController;
        fullScreenViewController.isFullScreenModeShown = false;
        keyguardTouchAnimator.distance = 0.0f;
        keyguardTouchAnimator.updateDistanceCount = 0;
        PointF pointF = keyguardTouchAnimator.touchDownPos;
        pointF.x = motionEvent.getRawX();
        pointF.y = motionEvent.getRawY();
        ((ArrayList) keyguardTouchAnimator.tapAffordanceViewController.restoreSpringAnimationList).clear();
        keyguardTouchAnimator.pivotViewController.setChildViewPivot$frameworks__base__packages__SystemUI__android_common__SystemUI_core();
        if (keyguardTouchAnimator.hasDozeAmount) {
            return true;
        }
        boolean canBeEditMode = ((KeyguardEditModeControllerImpl) keyguardTouchAnimator.keyguardEditModeController).canBeEditMode(keyguardTouchAnimator.context);
        KeyguardEditModeAnimatorController keyguardEditModeAnimatorController = keyguardTouchAnimator.editModeAnimatorController;
        if (canBeEditMode && keyguardTouchAnimator.canLongPressArea(motionEvent) && !keyguardEditModeAnimatorController.animatorSet.isRunning()) {
            Log.d("KeyguardEditModeAnimatorController", "actionDown editMode(" + keyguardEditModeAnimatorController.isEditMode() + ")");
            if (!keyguardEditModeAnimatorController.isEditMode()) {
                Job job2 = keyguardEditModeAnimatorController.longPressJob;
                if (job2 != null && job2.isActive() && (job = keyguardEditModeAnimatorController.longPressJob) != null) {
                    job.cancel(null);
                }
                DefaultScheduler defaultScheduler = Dispatchers.Default;
                keyguardEditModeAnimatorController.longPressJob = BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(MainDispatcherLoader.dispatcher), null, null, new KeyguardEditModeAnimatorController$actionDown$1(keyguardEditModeAnimatorController, null), 3);
            }
        } else if (keyguardTouchAnimator.canLongPressArea(motionEvent) && !fullScreenViewController.isFullscreenModeEnabled && !fullScreenViewController.fullScreenAnimatorSet.isRunning() && !keyguardEditModeAnimatorController.isLongPressed$frameworks__base__packages__SystemUI__android_common__SystemUI_core()) {
            fullScreenViewController.keyguardTouchAnimator.getParentView$frameworks__base__packages__SystemUI__android_common__SystemUI_core().postDelayed(fullScreenViewController.longPressCallback, 500L);
        }
        return true;
    }
}
