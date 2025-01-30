package com.android.wm.shell.pip.phone;

import android.app.ActivityTaskManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.RemoteException;
import android.window.DisplayAreaInfo;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.common.DisplayChangeController;
import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.pip.PipBoundsAlgorithm;
import com.android.wm.shell.pip.PipBoundsState;
import com.android.wm.shell.pip.PipDisplayLayoutState;
import com.android.wm.shell.pip.PipSnapAlgorithm;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.PipTransitionState;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PipController$$ExternalSyntheticLambda2 implements DisplayChangeController.OnDisplayChangingListener {
    public final /* synthetic */ PipController f$0;

    public /* synthetic */ PipController$$ExternalSyntheticLambda2(PipController pipController) {
        this.f$0 = pipController;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:52:? A[RETURN, SYNTHETIC] */
    @Override // com.android.wm.shell.common.DisplayChangeController.OnDisplayChangingListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onDisplayChange(int i, int i2, int i3, DisplayAreaInfo displayAreaInfo, WindowContainerTransaction windowContainerTransaction) {
        boolean z;
        ActivityTaskManager.RootTaskInfo rootTaskInfo;
        PipController pipController = this.f$0;
        if (pipController.mPipTransitionController.handleRotateDisplay(i2, i3, windowContainerTransaction)) {
            return;
        }
        PipBoundsState pipBoundsState = pipController.mPipBoundsState;
        if (pipBoundsState.getDisplayLayout().mRotation == i3) {
            pipController.updateMovementBounds(null, false, false, false, windowContainerTransaction);
            return;
        }
        PipTaskOrganizer pipTaskOrganizer = pipController.mPipTaskOrganizer;
        boolean isInPip = pipTaskOrganizer.isInPip();
        PipDisplayLayoutState pipDisplayLayoutState = pipController.mPipDisplayLayoutState;
        PipTransitionState pipTransitionState = pipTaskOrganizer.mPipTransitionState;
        if (isInPip) {
            if (!(pipTransitionState.mState == 2)) {
                PipAnimationController.PipTransitionAnimator pipTransitionAnimator = pipTaskOrganizer.mPipAnimationController.mCurrentAnimator;
                Rect bounds = (pipTransitionAnimator == null || !pipTransitionAnimator.isRunning()) ? pipTaskOrganizer.mPipBoundsState.getBounds() : new Rect(pipTransitionAnimator.mDestinationBounds);
                Rect rect = new Rect();
                int i4 = pipDisplayLayoutState.mDisplayId;
                Rect rect2 = pipController.mTmpInsetBounds;
                if (i == i4 && i2 != i3) {
                    try {
                        rootTaskInfo = ActivityTaskManager.getService().getRootTaskInfo(2, 0);
                    } catch (RemoteException e) {
                        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                            ShellProtoLogImpl.m230e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1027114398, 0, "%s: Failed to get RootTaskInfo for pinned task, %s", "PipController", String.valueOf(e));
                        }
                    }
                    if (rootTaskInfo != null) {
                        PipBoundsAlgorithm pipBoundsAlgorithm = pipController.mPipBoundsAlgorithm;
                        PipSnapAlgorithm pipSnapAlgorithm = pipBoundsAlgorithm.mSnapAlgorithm;
                        Rect rect3 = new Rect(bounds);
                        float snapFraction = pipSnapAlgorithm.getSnapFraction(pipBoundsState.mStashedState, rect3, pipBoundsAlgorithm.getMovementBounds(rect3, true));
                        pipDisplayLayoutState.rotateTo(i3);
                        PipSnapAlgorithm.applySnapFraction(rect3, pipBoundsAlgorithm.getMovementBounds(rect3, false), snapFraction, pipBoundsState.mStashedState, pipBoundsState.mStashOffset, pipDisplayLayoutState.getDisplayBounds(), pipDisplayLayoutState.getDisplayLayout().stableInsets(false));
                        pipBoundsAlgorithm.getInsetBounds(rect2);
                        rect.set(rect3);
                        if (!pipBoundsState.isStashed() && (rect.right > rect2.right || rect.bottom > rect2.bottom)) {
                            Point point = pipBoundsState.mMaxSize;
                            if (point.x == bounds.width() && point.y == bounds.height()) {
                                float f = pipBoundsState.mAspectRatio;
                                PipSizeSpecHandler pipSizeSpecHandler = pipController.mPipSizeSpecHandler;
                                int width = pipSizeSpecHandler.mSizeSpecSourceImpl.getMaxSize(f).getWidth();
                                int height = pipSizeSpecHandler.mSizeSpecSourceImpl.getMaxSize(pipBoundsState.mAspectRatio).getHeight();
                                int i5 = rect.left;
                                int i6 = rect.top;
                                rect.set(i5, i6, width + i5, height + i6);
                            }
                            int i7 = rect.right;
                            int i8 = rect2.right;
                            if (i7 > i8) {
                                rect.offset(i8 - i7, 0);
                            }
                            int i9 = rect.bottom;
                            int i10 = rect2.bottom;
                            if (i9 > i10) {
                                rect.offset(0, i10 - i9);
                            }
                        }
                        windowContainerTransaction.setBounds(rootTaskInfo.token, rect);
                        z = true;
                        if (z) {
                            return;
                        }
                        Rect bounds2 = pipBoundsState.getBounds();
                        PipTouchHandler pipTouchHandler = pipController.mTouchHandler;
                        pipTouchHandler.getClass();
                        Rect rect4 = new Rect();
                        pipTouchHandler.mPipBoundsAlgorithm.getClass();
                        PipBoundsAlgorithm.getMovementBounds(rect, rect2, rect4, 0);
                        if ((pipTouchHandler.mPipBoundsState.mMovementBounds.bottom - pipTouchHandler.mMovementBoundsExtraOffsets) - pipTouchHandler.mBottomOffsetBufferPx <= bounds2.top) {
                            rect.offsetTo(rect.left, rect4.bottom);
                        }
                        if (!pipController.mIsInFixedRotation) {
                            pipBoundsState.setShelfVisibility(0, false, false);
                            pipBoundsState.mIsImeShowing = false;
                            pipBoundsState.mImeHeight = 0;
                            pipTouchHandler.mIsShelfShowing = false;
                            pipTouchHandler.mShelfHeight = 0;
                            pipTouchHandler.mIsImeShowing = false;
                            pipTouchHandler.mImeHeight = 0;
                        }
                        pipController.updateMovementBounds(rect, true, false, false, windowContainerTransaction);
                        return;
                    }
                }
                z = false;
                if (z) {
                }
            }
        }
        pipDisplayLayoutState.rotateTo(i3);
        pipController.updateMovementBounds(pipBoundsState.mNormalBounds, true, false, false, windowContainerTransaction);
        if (pipTransitionState.mState == 2) {
            pipTaskOrganizer.enterPipWithAlphaAnimation(pipTaskOrganizer.mPipBoundsAlgorithm.getEntryDestinationBounds(), pipTaskOrganizer.mEnterAnimationDuration);
        }
    }
}
