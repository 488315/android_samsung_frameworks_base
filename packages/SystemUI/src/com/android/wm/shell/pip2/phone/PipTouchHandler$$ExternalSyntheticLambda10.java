package com.android.wm.shell.pip2.phone;

import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.DisplayCutout;
import android.view.InputEvent;
import android.view.MotionEvent;
import com.android.wm.shell.animation.FloatProperties;
import com.android.wm.shell.animation.FloatProperties$Companion$RECT_WIDTH$1;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.common.pip.PipDoubleTapHelper;
import com.android.wm.shell.common.pip.PipPerfHintController;
import com.android.wm.shell.common.pip.PipSnapAlgorithm;
import com.android.wm.shell.common.pip.PipUiEventLogger;
import com.android.wm.shell.pip2.phone.PipTouchHandler;
import com.android.wm.shell.shared.animation.PhysicsAnimator;

/* loaded from: classes3.dex */
public final /* synthetic */ class PipTouchHandler$$ExternalSyntheticLambda10 {
    public final /* synthetic */ PipTouchHandler f$0;

    public /* synthetic */ PipTouchHandler$$ExternalSyntheticLambda10(PipTouchHandler pipTouchHandler) {
        this.f$0 = pipTouchHandler;
    }

    /* JADX WARN: Removed duplicated region for block: B:247:0x04b5  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x007f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onInputEvent(InputEvent inputEvent) throws Resources.NotFoundException {
        PipBoundsState pipBoundsState;
        boolean z;
        boolean z2;
        boolean z3;
        PipTouchHandler pipTouchHandler = this.f$0;
        if (inputEvent instanceof MotionEvent) {
            PipTouchState pipTouchState = pipTouchHandler.mTouchState;
            if (pipTouchState.mAllowInputEvents) {
                MotionEvent motionEvent = (MotionEvent) inputEvent;
                PipBoundsState pipBoundsState2 = pipTouchHandler.mPipBoundsState;
                if (!pipBoundsState2.isStashed()) {
                    PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler.mPipResizeGestureHandler;
                    pipResizeGestureHandler.getClass();
                    int actionMasked = motionEvent.getActionMasked();
                    if (actionMasked != 0) {
                        if (actionMasked == 5 && pipResizeGestureHandler.mEnablePinchResize && motionEvent.getPointerCount() == 2) {
                            PointF pointF = pipResizeGestureHandler.mDownPoint;
                            PointF pointF2 = pipResizeGestureHandler.mDownSecondPoint;
                            Rect rect = pipResizeGestureHandler.mDownBounds;
                            PointF pointF3 = pipResizeGestureHandler.mLastPoint;
                            PointF pointF4 = pipResizeGestureHandler.mLastSecondPoint;
                            Rect rect2 = pipResizeGestureHandler.mLastResizeBounds;
                            float f = pipResizeGestureHandler.mTouchSlop;
                            PipBoundsState pipBoundsState3 = pipResizeGestureHandler.mPipBoundsState;
                            pipResizeGestureHandler.mPipPinchToResizeHandler.onPinchResize(motionEvent, pointF, pointF2, rect, pointF3, pointF4, rect2, f, pipBoundsState3.mMinSize, pipBoundsState3.mMaxSize);
                            z3 = pipResizeGestureHandler.mAllowGesture;
                            pipResizeGestureHandler.mOngoingPinchToResize = z3;
                        } else {
                            z3 = false;
                        }
                        if (z3) {
                            pipTouchState.onTouchEvent(motionEvent);
                            pipTouchState.reset();
                            return;
                        }
                    } else if (pipResizeGestureHandler.mEnableDragCornerResize) {
                        if (pipResizeGestureHandler.mPipDragToResizeHandler.isWithinDragResizeRegion((int) motionEvent.getRawX(), (int) motionEvent.getRawY())) {
                            z3 = true;
                        }
                        if (z3) {
                        }
                    }
                }
                PipResizeGestureHandler pipResizeGestureHandler2 = pipTouchHandler.mPipResizeGestureHandler;
                boolean z4 = pipResizeGestureHandler2.mCtrlType != 0 || pipResizeGestureHandler2.mOngoingPinchToResize;
                PipTouchHandler.DefaultPipTouchGesture defaultPipTouchGesture = pipTouchHandler.mGesture;
                PipDismissTargetHandler pipDismissTargetHandler = pipTouchHandler.mPipDismissTargetHandler;
                if (z4) {
                    PipPerfHintController.PipHighPerfSession pipHighPerfSession = defaultPipTouchGesture.mPipHighPerfSession;
                    if (pipHighPerfSession != null) {
                        pipHighPerfSession.close();
                        defaultPipTouchGesture.mPipHighPerfSession = null;
                    }
                    if (pipDismissTargetHandler.mEnableDismissDragToEdge) {
                        pipDismissTargetHandler.mTargetViewContainer.hide();
                        return;
                    }
                    return;
                }
                if ((motionEvent.getAction() == 0 || pipTouchState.mIsUserInteracting) && pipDismissTargetHandler.mMagnetizedPip.maybeConsumeMotionEvent(motionEvent)) {
                    if (motionEvent.getAction() == 0) {
                        pipTouchState.onTouchEvent(motionEvent);
                    }
                    pipTouchState.addMovementToVelocityTracker(motionEvent);
                    return;
                }
                pipTouchState.onTouchEvent(motionEvent);
                boolean z5 = pipTouchHandler.mMenuState != 0;
                int action = motionEvent.getAction();
                if (action == 0) {
                    pipBoundsState = pipBoundsState2;
                    defaultPipTouchGesture.getClass();
                    if (pipTouchState.mIsUserInteracting) {
                        PipTouchHandler pipTouchHandler2 = PipTouchHandler.this;
                        PipPerfHintController pipPerfHintController = pipTouchHandler2.mPipPerfHintController;
                        if (pipPerfHintController != null) {
                            defaultPipTouchGesture.mPipHighPerfSession = pipPerfHintController.startSession(new PipTouchHandler$$ExternalSyntheticLambda5(defaultPipTouchGesture, 1), "DefaultPipTouchGesture#onDown");
                        }
                        Rect possiblyMotionBounds = pipTouchHandler2.getPossiblyMotionBounds();
                        defaultPipTouchGesture.mDelta.set(0.0f, 0.0f);
                        defaultPipTouchGesture.mStartPosition.set(possiblyMotionBounds.left, possiblyMotionBounds.top);
                        float f2 = pipTouchState.mDownTouch.y;
                        PipBoundsState pipBoundsState4 = pipTouchHandler2.mPipBoundsState;
                        pipTouchHandler2.mMovementWithinDismiss = f2 >= ((float) pipBoundsState4.mMovementBounds.bottom);
                        pipTouchHandler2.mMotionHelper.mSpringingToTouch = false;
                        pipTouchHandler2.mPipDismissTargetHandler.mTaskLeash = pipTouchHandler2.mPipTransitionState.mPinnedTaskLeash;
                        if (pipTouchHandler2.mMenuState != 0 && !pipBoundsState4.isStashed()) {
                            PhonePipMenuController phonePipMenuController = pipTouchHandler2.mMenuController;
                            if (phonePipMenuController.isMenuVisible()) {
                                PipMenuView pipMenuView = phonePipMenuController.mPipMenuView;
                                ((HandlerExecutor) pipMenuView.mMainExecutor).removeCallbacks(pipMenuView.mHideMenuRunnable);
                            }
                        }
                    }
                } else if (action != 1) {
                    if (action == 2) {
                        defaultPipTouchGesture.getClass();
                        if (pipTouchState.mIsUserInteracting) {
                            boolean z6 = pipTouchState.mStartedDragging;
                            PipTouchHandler pipTouchHandler3 = PipTouchHandler.this;
                            if (z6) {
                                pipTouchHandler3.mSavedSnapFraction = -1.0f;
                                pipTouchHandler3.mPipDismissTargetHandler.showDismissTargetMaybe();
                            }
                            if (pipTouchState.mIsDragging) {
                                PipBoundsState pipBoundsState5 = pipTouchHandler3.mPipBoundsState;
                                pipBoundsState5.mHasUserMovedPip = true;
                                if (pipBoundsState5.mIsImeShowing) {
                                    pipBoundsState5.mRestoreBounds.setEmpty();
                                }
                                PointF pointF5 = pipTouchState.mLastDelta;
                                Point point = defaultPipTouchGesture.mStartPosition;
                                float f3 = point.x;
                                PointF pointF6 = defaultPipTouchGesture.mDelta;
                                float f4 = pointF6.x;
                                float f5 = f3 + f4;
                                float f6 = point.y;
                                float f7 = pointF6.y;
                                float f8 = f6 + f7;
                                float f9 = pointF5.x + f5;
                                float f10 = pointF5.y + f8;
                                pointF6.x = (f9 - f5) + f4;
                                pointF6.y = (f10 - f8) + f7;
                                pipTouchHandler3.mTmpBounds.set(pipTouchHandler3.getPossiblyMotionBounds());
                                pipTouchHandler3.mTmpBounds.offsetTo((int) f9, (int) f10);
                                PipMotionHelper pipMotionHelper = pipTouchHandler3.mMotionHelper;
                                Rect rect3 = pipTouchHandler3.mTmpBounds;
                                boolean z7 = pipMotionHelper.mSpringingToTouch;
                                PipBoundsState pipBoundsState6 = pipMotionHelper.mPipBoundsState;
                                if (z7) {
                                    PhysicsAnimator physicsAnimator = pipMotionHelper.mTemporaryBoundsPhysicsAnimator;
                                    FloatProperties$Companion$RECT_WIDTH$1 floatProperties$Companion$RECT_WIDTH$1 = FloatProperties.RECT_WIDTH;
                                    float fWidth = pipBoundsState6.getBounds().width();
                                    PhysicsAnimator.SpringConfig springConfig = pipMotionHelper.mCatchUpSpringConfig;
                                    physicsAnimator.spring(floatProperties$Companion$RECT_WIDTH$1, fWidth, 0.0f, springConfig);
                                    physicsAnimator.spring(FloatProperties.RECT_HEIGHT, pipBoundsState6.getBounds().height(), 0.0f, springConfig);
                                    physicsAnimator.spring(FloatProperties.RECT_X, rect3.left, 0.0f, springConfig);
                                    physicsAnimator.spring(FloatProperties.RECT_Y, rect3.top, 0.0f, springConfig);
                                    pipMotionHelper.startBoundsAnimator(rect3.left, rect3.top, null);
                                } else {
                                    pipMotionHelper.cancelPhysicsAnimation();
                                    pipBoundsState6.mMotionBoundsState.setBoundsInMotion(rect3);
                                    pipMotionHelper.mPipScheduler.scheduleUserResizePip(rect3, 0.0f);
                                }
                                PointF pointF7 = pipTouchState.mLastTouch;
                                if (pipTouchHandler3.mMovementWithinDismiss) {
                                    pipTouchHandler3.mMovementWithinDismiss = pointF7.y >= ((float) pipTouchHandler3.mPipBoundsState.mMovementBounds.bottom);
                                }
                                z2 = true;
                            } else {
                                z2 = false;
                            }
                        } else {
                            z2 = false;
                        }
                        if (!z2) {
                            z5 = !pipTouchState.mIsDragging;
                        }
                    } else if (action == 3) {
                        pipBoundsState = pipBoundsState2;
                        z5 = pipTouchState.mStartedDragging && !pipTouchState.mIsDragging;
                        pipTouchState.reset();
                    } else if (action != 7) {
                        ShellExecutor shellExecutor = pipTouchState.mMainExecutor;
                        if (action == 9) {
                            if (!pipTouchHandler.mAccessibilityManager.isTouchExplorationEnabled()) {
                                ((HandlerExecutor) shellExecutor).removeCallbacks(pipTouchState.mHoverExitTimeoutCallback);
                                pipTouchHandler.mMenuController.showMenuInternal(1, pipBoundsState2.getBounds(), false, false, false);
                            }
                            if (!z5 && !pipTouchHandler.mSendingHoverAccessibilityEvents) {
                                pipTouchHandler.sendAccessibilityHoverEvent(128);
                                pipTouchHandler.mSendingHoverAccessibilityEvents = true;
                            }
                        } else if (action == 10) {
                            if (!pipTouchHandler.mAccessibilityManager.isTouchExplorationEnabled()) {
                                HandlerExecutor handlerExecutor = (HandlerExecutor) shellExecutor;
                                handlerExecutor.removeCallbacks(pipTouchState.mHoverExitTimeoutCallback);
                                handlerExecutor.executeDelayed(pipTouchState.mHoverExitTimeoutCallback, 50L);
                            }
                            if (!z5 && pipTouchHandler.mSendingHoverAccessibilityEvents) {
                                pipTouchHandler.sendAccessibilityHoverEvent(256);
                                pipTouchHandler.mSendingHoverAccessibilityEvents = false;
                            }
                        }
                    } else if (!z5) {
                        pipTouchHandler.sendAccessibilityHoverEvent(128);
                        pipTouchHandler.mSendingHoverAccessibilityEvents = true;
                    }
                    pipBoundsState = pipBoundsState2;
                } else {
                    pipTouchHandler.updateMovementBounds();
                    PipTouchHandler pipTouchHandler4 = PipTouchHandler.this;
                    PipDismissTargetHandler pipDismissTargetHandler2 = pipTouchHandler4.mPipDismissTargetHandler;
                    if (pipDismissTargetHandler2.mEnableDismissDragToEdge) {
                        pipDismissTargetHandler2.mTargetViewContainer.hide();
                    }
                    pipTouchHandler4.mPipDismissTargetHandler.mTaskLeash = null;
                    if (pipTouchState.mIsUserInteracting) {
                        PointF pointF8 = pipTouchState.mVelocity;
                        boolean z8 = pipTouchState.mIsDragging;
                        PipUiEventLogger pipUiEventLogger = pipTouchHandler4.mPipUiEventLogger;
                        PipTouchState pipTouchState2 = pipTouchHandler4.mTouchState;
                        PipBoundsState pipBoundsState7 = pipTouchHandler4.mPipBoundsState;
                        if (z8) {
                            int i = pipTouchHandler4.mMenuState;
                            if (i != 0) {
                                pipTouchHandler4.mMenuController.showMenuInternal(i, pipBoundsState7.getBounds(), true, pipTouchHandler4.willResizeMenu(), false);
                            }
                            defaultPipTouchGesture.mShouldHideMenuAfterFling = pipTouchHandler4.mMenuState == 0;
                            pipTouchState2.reset();
                            if (pipTouchHandler4.mEnableStash) {
                                Rect possiblyMotionBounds2 = pipTouchHandler4.getPossiblyMotionBounds();
                                float f11 = pointF8.x;
                                float f12 = pipTouchHandler4.mStashVelocityThreshold;
                                boolean z9 = f11 < (-f12);
                                boolean z10 = f11 > f12;
                                int iWidth = possiblyMotionBounds2.width() / 2;
                                boolean z11 = possiblyMotionBounds2.left < pipBoundsState7.mPipDisplayLayoutState.getDisplayBounds().left - iWidth;
                                int i2 = possiblyMotionBounds2.right;
                                PipDisplayLayoutState pipDisplayLayoutState = pipBoundsState7.mPipDisplayLayoutState;
                                pipBoundsState = pipBoundsState2;
                                boolean z12 = i2 > pipDisplayLayoutState.getDisplayBounds().right + iWidth;
                                DisplayCutout displayCutout = pipDisplayLayoutState.getDisplayLayout().mCutout;
                                if (displayCutout == null || (((!z9 && !z11) || displayCutout.getBoundingRectLeft().isEmpty()) && ((!z10 && !z12) || displayCutout.getBoundingRectRight().isEmpty()))) {
                                    boolean z13 = (z9 && pipBoundsState7.mStashedState != 2) || (z10 && pipBoundsState7.mStashedState != 1);
                                    boolean z14 = z11 || z12;
                                    if (z13 || z14) {
                                        PipMotionHelper pipMotionHelper2 = pipTouchHandler4.mMotionHelper;
                                        pipMotionHelper2.movetoTarget(pointF8.x, pipMotionHelper2.mPipBoundsState.mStashedState == 0 ? 0.0f : pointF8.y, null, true);
                                    }
                                }
                            } else {
                                pipBoundsState = pipBoundsState2;
                            }
                            if (pipBoundsState7.isStashed()) {
                                pipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_STASH_UNSTASHED);
                                pipBoundsState7.setStashed(0, false);
                            }
                            pipTouchHandler4.mMotionHelper.movetoTarget(pointF8.x, pointF8.y, new PipTouchHandler$$ExternalSyntheticLambda3(defaultPipTouchGesture, 1), false);
                        } else {
                            pipBoundsState = pipBoundsState2;
                            if (!pipTouchState2.mIsDoubleTap || pipBoundsState7.isStashed() || pipTouchHandler4.mMenuState == 1) {
                                if (pipTouchHandler4.mMenuState != 1) {
                                    boolean zIsStashed = pipBoundsState7.isStashed();
                                    ShellExecutor shellExecutor2 = pipTouchState2.mMainExecutor;
                                    if (zIsStashed) {
                                        Rect bounds = pipBoundsState7.getBounds();
                                        boolean z15 = bounds.left < pipBoundsState7.mPipDisplayLayoutState.getDisplayBounds().left;
                                        Rect rect4 = new Rect(0, bounds.top, 0, bounds.bottom);
                                        Rect rect5 = new Rect();
                                        pipTouchHandler4.mPipBoundsAlgorithm.getInsetBounds(rect5);
                                        rect4.left = z15 ? rect5.left : rect5.right - bounds.width();
                                        rect4.right = z15 ? bounds.width() + rect5.left : rect5.right;
                                        pipTouchHandler4.mMotionHelper.resizeAndAnimatePipUnchecked(rect4);
                                        pipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_STASH_UNSTASHED);
                                        pipBoundsState7.setStashed(0, false);
                                        pipTouchState2.mIsWaitingForDoubleTap = false;
                                        ((HandlerExecutor) shellExecutor2).removeCallbacks(pipTouchState2.mDoubleTapTimeoutCallback);
                                    } else {
                                        boolean z16 = pipTouchState2.mIsWaitingForDoubleTap;
                                        if (!z16) {
                                            pipTouchHandler4.mMenuController.showMenuInternal(1, pipBoundsState7.getBounds(), true, pipTouchHandler4.willResizeMenu(), false);
                                        } else if (z16) {
                                            long jMax = z16 ? Math.max(0L, PipTouchState.DOUBLE_TAP_TIMEOUT - (pipTouchState2.mUpTouchTime - pipTouchState2.mDownTouchTime)) : -1L;
                                            HandlerExecutor handlerExecutor2 = (HandlerExecutor) shellExecutor2;
                                            handlerExecutor2.removeCallbacks(pipTouchState2.mDoubleTapTimeoutCallback);
                                            handlerExecutor2.executeDelayed(pipTouchState2.mDoubleTapTimeoutCallback, jMax);
                                        }
                                    }
                                }
                            } else if (pipTouchHandler4.mPipResizeGestureHandler.mEnablePinchResize) {
                                PhonePipMenuController phonePipMenuController2 = pipTouchHandler4.mMenuController;
                                if (phonePipMenuController2.isMenuVisible()) {
                                    phonePipMenuController2.hideMenu(0);
                                }
                                pipBoundsState7.mNormalBounds.set(pipTouchHandler4.getAdjustedNormalBounds());
                                int iNextSizeSpec = PipDoubleTapHelper.nextSizeSpec(pipBoundsState7, pipTouchHandler4.mPipResizeGestureHandler.mUserResizeBounds);
                                if (iNextSizeSpec == 1) {
                                    pipTouchHandler4.mPipResizeGestureHandler.mUserResizeBounds.set(pipBoundsState7.getBounds());
                                    Point point2 = pipBoundsState7.mMaxSize;
                                    Rect rect6 = new Rect(0, 0, point2.x, point2.y);
                                    PipMotionHelper pipMotionHelper3 = pipTouchHandler4.mMotionHelper;
                                    Rect movementBounds = pipTouchHandler4.getMovementBounds(pipBoundsState7.getBounds());
                                    Rect movementBounds2 = pipTouchHandler4.getMovementBounds(rect6);
                                    pipMotionHelper3.getClass();
                                    float snapFraction = pipMotionHelper3.mSnapAlgorithm.getSnapFraction(0, new Rect(pipMotionHelper3.mPipBoundsState.getBounds()), movementBounds);
                                    PipSnapAlgorithm.applySnapFraction(rect6, movementBounds2, snapFraction);
                                    pipMotionHelper3.mPostPipTransitionCallback = null;
                                    pipMotionHelper3.resizeAndAnimatePipUnchecked(rect6);
                                    pipTouchHandler4.mSavedSnapFraction = snapFraction;
                                } else if (iNextSizeSpec == 0) {
                                    pipTouchHandler4.mPipResizeGestureHandler.mUserResizeBounds.set(pipBoundsState7.getBounds());
                                    pipTouchHandler4.animateToNormalSize(null);
                                } else {
                                    pipTouchHandler4.animateToUnexpandedState(pipTouchHandler4.mPipResizeGestureHandler.mUserResizeBounds);
                                }
                                pipBoundsState7.setHasUserResizedPip();
                            } else {
                                pipTouchState2.mAllowTouches = false;
                                if (pipTouchState2.mIsUserInteracting) {
                                    pipTouchState2.reset();
                                }
                                PipMotionHelper pipMotionHelper4 = pipTouchHandler4.mMotionHelper;
                                pipMotionHelper4.cancelPhysicsAnimation();
                                pipMotionHelper4.mMenuController.hideMenu(0);
                                PipScheduler pipScheduler = pipMotionHelper4.mPipScheduler;
                                pipScheduler.getClass();
                                pipScheduler.mMainExecutor.execute(new PipScheduler$$ExternalSyntheticLambda4(pipScheduler));
                            }
                        }
                        PipPerfHintController.PipHighPerfSession pipHighPerfSession2 = defaultPipTouchGesture.mPipHighPerfSession;
                        if (pipHighPerfSession2 != null) {
                            pipHighPerfSession2.close();
                            defaultPipTouchGesture.mPipHighPerfSession = null;
                        }
                        z = true;
                    } else {
                        pipBoundsState = pipBoundsState2;
                        z = false;
                    }
                    if (!z) {
                        if (pipTouchState.mStartedDragging) {
                            pipTouchState.reset();
                        }
                    }
                }
                if ((!pipBoundsState.isStashed()) && z5) {
                    MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
                    boolean z17 = pipTouchState.mStartedDragging;
                    PhonePipMenuController phonePipMenuController3 = pipTouchHandler.mMenuController;
                    if (z17) {
                        motionEventObtain.setAction(3);
                        if (phonePipMenuController3.isMenuVisible()) {
                            PipMenuView pipMenuView2 = phonePipMenuController3.mPipMenuView;
                            ((HandlerExecutor) pipMenuView2.mMainExecutor).removeCallbacks(pipMenuView2.mHideMenuRunnable);
                        }
                    }
                    if (phonePipMenuController3.mPipMenuView != null) {
                        if (motionEventObtain.isTouchEvent()) {
                            phonePipMenuController3.mPipMenuView.dispatchTouchEvent(motionEventObtain);
                        } else {
                            phonePipMenuController3.mPipMenuView.dispatchGenericMotionEvent(motionEventObtain);
                        }
                    }
                    motionEventObtain.recycle();
                }
            }
        }
    }
}
