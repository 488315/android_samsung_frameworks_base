package com.android.wm.shell.pip.phone;

import android.app.ActivityTaskManager;
import android.graphics.Rect;
import android.os.RemoteException;
import android.window.DisplayAreaInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.common.DisplayChangeController;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.common.pip.PipSnapAlgorithm;
import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.PipTransitionState;
import com.android.wm.shell.protolog.ShellProtoLogGroup;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PipController$$ExternalSyntheticLambda2 implements DisplayChangeController.OnDisplayChangingListener {
    public final /* synthetic */ PipController f$0;

    public /* synthetic */ PipController$$ExternalSyntheticLambda2(PipController pipController) {
        this.f$0 = pipController;
    }

    @Override // com.android.wm.shell.common.DisplayChangeController.OnDisplayChangingListener
    public final void onDisplayChange(int i, int i2, int i3, DisplayAreaInfo displayAreaInfo, WindowContainerTransaction windowContainerTransaction) {
        PipController pipController = this.f$0;
        if (i2 == i3) {
            int i4 = PipController.$r8$clinit;
            return;
        }
        if (pipController.mPipTransitionController.handleRotateDisplay(i2, i3, windowContainerTransaction)) {
            return;
        }
        PipBoundsState pipBoundsState = pipController.mPipBoundsState;
        if (pipBoundsState.mPipDisplayLayoutState.getDisplayLayout().mRotation == i3) {
            pipBoundsState.mBoundsScale = Math.min(pipBoundsState.mBounds.width() / pipBoundsState.mMaxSize.x, 1.0f);
            pipController.updateMovementBounds(null, false, false, false, windowContainerTransaction);
            return;
        }
        PipTransitionState pipTransitionState = pipController.mPipTransitionState;
        boolean z = pipTransitionState.mInSwipePipToHomeTransition;
        PipTaskOrganizer pipTaskOrganizer = pipController.mPipTaskOrganizer;
        if ((z && pipTransitionState.mState == 3) || !pipTaskOrganizer.isInPip() || pipTaskOrganizer.mPipTransitionState.mState == 2) {
            pipController.mPipDisplayLayoutState.rotateTo(i3);
            pipController.updateMovementBounds(pipBoundsState.mNormalBounds, true, false, false, windowContainerTransaction);
            if (pipTaskOrganizer.mPipTransitionState.mState == 2) {
                pipTaskOrganizer.enterPipWithAlphaAnimation(pipTaskOrganizer.mPipBoundsAlgorithm.getEntryDestinationBounds(), pipTaskOrganizer.mEnterAnimationDuration);
                return;
            }
            return;
        }
        PipAnimationController.PipTransitionAnimator pipTransitionAnimator = pipTaskOrganizer.mPipAnimationController.mCurrentAnimator;
        Rect bounds = (pipTransitionAnimator == null || !pipTransitionAnimator.isRunning()) ? pipTaskOrganizer.mPipBoundsState.getBounds() : new Rect(pipTransitionAnimator.mDestinationBounds);
        Rect rect = new Rect();
        Rect rect2 = pipController.mTmpInsetBounds;
        PipDisplayLayoutState pipDisplayLayoutState = pipController.mPipDisplayLayoutState;
        if (i != pipDisplayLayoutState.mDisplayId || i2 == i3) {
            return;
        }
        try {
            ActivityTaskManager.RootTaskInfo rootTaskInfo = ActivityTaskManager.getService().getRootTaskInfo(2, 0);
            if (rootTaskInfo == null) {
                return;
            }
            PipBoundsAlgorithm pipBoundsAlgorithm = pipController.mPipBoundsAlgorithm;
            PipSnapAlgorithm pipSnapAlgorithm = pipBoundsAlgorithm.mSnapAlgorithm;
            Rect rect3 = new Rect(bounds);
            float snapFraction = pipSnapAlgorithm.getSnapFraction(pipBoundsState.mStashedState, rect3, pipBoundsAlgorithm.getMovementBounds(rect3, true));
            pipDisplayLayoutState.rotateTo(i3);
            float f = pipBoundsState.mAspectRatio;
            PipTouchHandler pipTouchHandler = pipController.mTouchHandler;
            pipTouchHandler.updatePipSizeConstraints(pipTouchHandler.mPipBoundsState.mNormalBounds, f);
            rect3.set(0, 0, Math.round(pipBoundsState.mMaxSize.x * pipBoundsState.mBoundsScale), Math.round(pipBoundsState.mMaxSize.y * pipBoundsState.mBoundsScale));
            PipSnapAlgorithm.applySnapFraction(rect3, pipBoundsAlgorithm.getMovementBounds(rect3, false), snapFraction, pipBoundsState.mStashedState, pipBoundsState.mStashOffset, pipDisplayLayoutState.getDisplayBounds(), pipDisplayLayoutState.getDisplayLayout().mStableInsets);
            pipBoundsAlgorithm.getInsetBounds(rect2);
            rect.set(rect3);
            windowContainerTransaction.setBounds(rootTaskInfo.token, rect);
            Rect bounds2 = pipBoundsState.getBounds();
            Rect rect4 = pipController.mTmpInsetBounds;
            Rect rect5 = new Rect();
            pipTouchHandler.mPipBoundsAlgorithm.getClass();
            PipBoundsAlgorithm.getMovementBounds(rect, rect4, rect5, 0);
            if ((pipTouchHandler.mPipBoundsState.mMovementBounds.bottom - pipTouchHandler.mMovementBoundsExtraOffsets) - pipTouchHandler.mBottomOffsetBufferPx <= bounds2.top) {
                rect.offsetTo(rect.left, rect5.bottom);
            }
            if (!pipController.mIsInFixedRotation) {
                pipBoundsState.setImeVisibility(0, false);
                pipTouchHandler.mIsShelfShowing = false;
                pipTouchHandler.mShelfHeight = 0;
                pipTouchHandler.mIsImeShowing = false;
                pipTouchHandler.mImeHeight = 0;
            }
            pipController.updateMovementBounds(rect, true, false, false, windowContainerTransaction);
            if (pipController.mIsKeyguardShowingOrAnimating) {
                return;
            }
            pipController.mMenuController.attachPipMenuView();
        } catch (RemoteException e) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[4]) {
                ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1384159325629332473L, 0, "PipController", String.valueOf(e));
            }
        }
    }
}
