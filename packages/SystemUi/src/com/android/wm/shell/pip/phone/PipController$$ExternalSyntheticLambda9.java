package com.android.wm.shell.pip.phone;

import android.app.ActivityManager;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.InputEvent;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.accessibility.AccessibilityManager;
import com.android.keyguard.KeyguardCarrierViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.DismissButtonManager;
import com.android.wm.shell.common.DismissButtonView;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.naturalswitching.NaturalSwitchingDropTargetController;
import com.android.wm.shell.pip.PipBoundsAlgorithm;
import com.android.wm.shell.pip.PipBoundsState;
import com.android.wm.shell.pip.PipSnapAlgorithm;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.PipUiEventLogger;
import com.android.wm.shell.pip.phone.PipTouchHandler;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PipController$$ExternalSyntheticLambda9 {
    public final /* synthetic */ PipTouchHandler f$0;

    /* JADX WARN: Code restructure failed: missing block: B:173:0x0265, code lost:
    
        if ((r5.mPipNaturalSwitchingHandler.mState == 2) != false) goto L168;
     */
    /* JADX WARN: Code restructure failed: missing block: B:206:0x05d6, code lost:
    
        if (r6 == false) goto L341;
     */
    /* JADX WARN: Code restructure failed: missing block: B:324:0x0509, code lost:
    
        if (r14.getBounds().width() > ((r5.x + r14.mMinSize.x) / 2)) goto L311;
     */
    /* JADX WARN: Removed duplicated region for block: B:133:0x0651  */
    /* JADX WARN: Removed duplicated region for block: B:144:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:249:0x0430  */
    /* JADX WARN: Removed duplicated region for block: B:309:0x0514  */
    /* JADX WARN: Removed duplicated region for block: B:314:0x0561  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0168  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onInputEvent(InputEvent inputEvent) {
        MotionEvent motionEvent;
        boolean z;
        PhonePipMenuController phonePipMenuController;
        boolean z2;
        char c;
        boolean z3;
        boolean z4;
        boolean z5;
        PipTouchHandler pipTouchHandler = this.f$0;
        pipTouchHandler.getClass();
        if (!(inputEvent instanceof MotionEvent)) {
            return;
        }
        PipTouchState pipTouchState = pipTouchHandler.mTouchState;
        if (!pipTouchState.mAllowInputEvents) {
            Log.d("PipTouchHandler", "pip input event not allowed");
            return;
        }
        MotionEvent motionEvent2 = (MotionEvent) inputEvent;
        if (CoreRune.MW_NATURAL_SWITCHING_PIP) {
            PipNaturalSwitchingHandler pipNaturalSwitchingHandler = pipTouchHandler.mPipNaturalSwitchingHandler;
            pipNaturalSwitchingHandler.getClass();
            int action = motionEvent2.getAction();
            PipTouchState pipTouchState2 = pipNaturalSwitchingHandler.mPipTouchState;
            NaturalSwitchingDropTargetController naturalSwitchingDropTargetController = pipNaturalSwitchingHandler.mNsController;
            if (action == 0) {
                if (pipNaturalSwitchingHandler.mState == 0) {
                    PipTaskOrganizer pipTaskOrganizer = pipNaturalSwitchingHandler.mPipTaskOrganizer;
                    ActivityManager.RunningTaskInfo runningTaskInfo = pipTaskOrganizer.mTaskInfo;
                    SurfaceControl surfaceControl = pipTaskOrganizer.mLeash;
                    if (runningTaskInfo == null || surfaceControl == null || !surfaceControl.isValid()) {
                        Log.d("PipNaturalSwitchingHandler", "startNaturalSwitchingIfPossible: failed, leash=" + surfaceControl);
                    } else if (pipTaskOrganizer.shouldShowSplitMenu()) {
                        boolean isStashed = pipNaturalSwitchingHandler.mPipBoundsState.isStashed();
                        boolean z6 = !pipTouchState2.mAllowTouches;
                        boolean z7 = pipTouchState2.mIsWaitingForDoubleTap;
                        boolean allowInterceptTouch = naturalSwitchingDropTargetController.allowInterceptTouch(pipNaturalSwitchingHandler.mTaskInfo);
                        if (isStashed || z6 || z7 || allowInterceptTouch) {
                            KeyguardCarrierViewController$$ExternalSyntheticOutline0.m63m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("startNaturalSwitchingIfPossible: failed, st=", isStashed, ", tb=", z6, ", wd="), z7, ", ib=", allowInterceptTouch, "PipNaturalSwitchingHandler");
                        } else {
                            naturalSwitchingDropTargetController.onInterceptTouchEvent(motionEvent2, runningTaskInfo.taskId);
                            pipNaturalSwitchingHandler.mTaskInfo = runningTaskInfo;
                            pipNaturalSwitchingHandler.mTaskId = runningTaskInfo.taskId;
                            pipNaturalSwitchingHandler.mLeash = surfaceControl;
                            Log.d("PipNaturalSwitchingHandler", "startNaturalSwitchingIfPossible: " + pipNaturalSwitchingHandler);
                            pipNaturalSwitchingHandler.setState(1);
                        }
                    }
                } else {
                    Log.e("PipNaturalSwitchingHandler", "startNaturalSwitchingIfPossible: failed, already running, " + pipNaturalSwitchingHandler);
                }
            } else {
                if (!(pipNaturalSwitchingHandler.mState == 0)) {
                    if (((pipNaturalSwitchingHandler.mScaleDownAnimator == null && pipNaturalSwitchingHandler.mScaleUpPhysicsAnimator == null) ? false : true) && pipTouchState2.mIsDragging) {
                        pipNaturalSwitchingHandler.clearAllAnimations();
                    }
                    boolean onInterceptTouchEvent = naturalSwitchingDropTargetController.onInterceptTouchEvent(motionEvent2, pipNaturalSwitchingHandler.mTaskId);
                    boolean z8 = action == 1 && naturalSwitchingDropTargetController.mLayoutChanged;
                    boolean z9 = action == 3 || (action == 1 && !z8);
                    if (z8) {
                        pipNaturalSwitchingHandler.updateWaitingForTaskVanished("dropped", true);
                    } else if (z9) {
                        pipNaturalSwitchingHandler.setState(0);
                    } else if (onInterceptTouchEvent) {
                        if (!(pipNaturalSwitchingHandler.mState == 2)) {
                            pipNaturalSwitchingHandler.setState(2);
                        }
                    }
                }
            }
        }
        PipBoundsState pipBoundsState = pipTouchHandler.mPipBoundsState;
        if (!pipBoundsState.isStashed()) {
            PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler.mPipResizeGestureHandler;
            if (pipResizeGestureHandler.mIsSysUiStateValid) {
                int actionMasked = motionEvent2.getActionMasked();
                if (actionMasked != 0) {
                    if (actionMasked == 5 && pipResizeGestureHandler.mEnablePinchResize && motionEvent2.getPointerCount() == 2) {
                        pipResizeGestureHandler.onPinchResize(motionEvent2);
                        z5 = pipResizeGestureHandler.mAllowGesture;
                        pipResizeGestureHandler.mOngoingPinchToResize = z5;
                        if (z5) {
                            pipTouchState.onTouchEvent(motionEvent2);
                            pipTouchState.reset();
                            if (pipTouchHandler.mPipResizeGestureHandler.mEnablePinchResize) {
                                pipTouchHandler.updatePinchResizeSizeConstraints(pipBoundsState.mAspectRatio);
                                return;
                            }
                            return;
                        }
                    }
                } else if (pipResizeGestureHandler.isWithinDragResizeRegion((int) motionEvent2.getRawX(), (int) motionEvent2.getRawY())) {
                    z5 = true;
                    if (z5) {
                    }
                }
            }
            z5 = false;
            if (z5) {
            }
        }
        PipResizeGestureHandler pipResizeGestureHandler2 = pipTouchHandler.mPipResizeGestureHandler;
        boolean z10 = pipResizeGestureHandler2.mCtrlType != 0 || pipResizeGestureHandler2.mOngoingPinchToResize;
        PipDismissButtonView pipDismissButtonView = pipTouchHandler.mDismissButtonView;
        PipDismissTargetHandler pipDismissTargetHandler = pipTouchHandler.mPipDismissTargetHandler;
        if (z10) {
            pipDismissTargetHandler.getClass();
            pipDismissButtonView.hideDismissTargetMaybe();
            Log.d("PipTouchHandler", "block touch event for resize gesture");
            return;
        }
        if (motionEvent2.getAction() == 0 || pipTouchState.mIsUserInteracting) {
            pipDismissTargetHandler.getClass();
        }
        pipTouchState.onTouchEvent(motionEvent2);
        boolean z11 = pipTouchHandler.mMenuState != 0;
        int action2 = motionEvent2.getAction();
        PhonePipMenuController phonePipMenuController2 = pipTouchHandler.mMenuController;
        PipTouchHandler.DefaultPipTouchGesture defaultPipTouchGesture = pipTouchHandler.mGesture;
        if (action2 != 0) {
            if (action2 != 1) {
                if (action2 == 2) {
                    defaultPipTouchGesture.getClass();
                    if (pipTouchState.mIsUserInteracting) {
                        boolean z12 = pipTouchState.mStartedDragging;
                        PointF pointF = pipTouchState.mLastTouch;
                        PipTouchHandler pipTouchHandler2 = PipTouchHandler.this;
                        if (z12) {
                            pipTouchHandler2.mSavedSnapFraction = -1.0f;
                            pipTouchHandler2.mPipDismissTargetHandler.getClass();
                            if (CoreRune.MW_NATURAL_SWITCHING_PIP) {
                            }
                            Insets insets = Insets.NONE;
                            PipBoundsState pipBoundsState2 = pipTouchHandler2.mPipBoundsState;
                            pipBoundsState2.getDisplayLayout();
                            Insets.of(pipBoundsState2.getDisplayLayout().stableInsets(false));
                            DismissButtonManager dismissButtonManager = pipTouchHandler2.mDismissButtonView.mDismissButtonManager;
                            if (!dismissButtonManager.isAttachedToWindow()) {
                                dismissButtonManager.createDismissButtonView();
                                dismissButtonManager.createOrUpdateWrapper();
                            }
                            dismissButtonManager.setVisibility(0);
                            dismissButtonManager.show();
                            dismissButtonManager.updateDismissTargetView(pointF);
                        }
                        if (pipTouchState.mIsDragging) {
                            pipTouchHandler2.mPipBoundsState.mHasUserMovedPip = true;
                            PointF pointF2 = pipTouchState.mLastDelta;
                            Point point = defaultPipTouchGesture.mStartPosition;
                            float f = point.x;
                            PointF pointF3 = defaultPipTouchGesture.mDelta;
                            float f2 = pointF3.x;
                            float f3 = f + f2;
                            float f4 = point.y;
                            float f5 = pointF3.y;
                            float f6 = f4 + f5;
                            float f7 = pointF2.x + f3;
                            float f8 = pointF2.y + f6;
                            pointF3.x = (f7 - f3) + f2;
                            pointF3.y = (f8 - f6) + f5;
                            Rect possiblyMotionBounds = pipTouchHandler2.getPossiblyMotionBounds();
                            Rect rect = pipTouchHandler2.mTmpBounds;
                            rect.set(possiblyMotionBounds);
                            rect.offsetTo((int) f7, (int) f8);
                            pipTouchHandler2.mMotionHelper.movePip(rect, true);
                            DismissButtonManager dismissButtonManager2 = pipTouchHandler2.mDismissButtonView.mDismissButtonManager;
                            if (dismissButtonManager2.mView != null) {
                                dismissButtonManager2.updateDismissTargetView(pointF);
                            }
                            if (pipTouchHandler2.mMovementWithinDismiss) {
                                pipTouchHandler2.mMovementWithinDismiss = pointF.y >= ((float) pipTouchHandler2.mPipBoundsState.mMovementBounds.bottom);
                            }
                            r6 = true;
                        }
                    }
                    if (!r6) {
                        z2 = !pipTouchState.mIsDragging;
                        motionEvent = motionEvent2;
                        phonePipMenuController = phonePipMenuController2;
                    }
                } else if (action2 != 3) {
                    if (action2 != 7) {
                        AccessibilityManager accessibilityManager = pipTouchHandler.mAccessibilityManager;
                        if (action2 != 9) {
                            if (action2 == 10) {
                                if (!accessibilityManager.isTouchExplorationEnabled()) {
                                    pipTouchState.scheduleHoverExitTimeoutCallback();
                                }
                                if (!z11 && pipTouchHandler.mSendingHoverAccessibilityEvents) {
                                    pipTouchHandler.sendAccessibilityHoverEvent(256);
                                    pipTouchHandler.mSendingHoverAccessibilityEvents = false;
                                }
                            }
                        } else if (!accessibilityManager.isTouchExplorationEnabled() && (pipTouchHandler.mPipResizeGestureHandler.getLastResizeBounds().isEmpty() || pipTouchHandler.mPipResizeGestureHandler.getLastResizeBounds().equals(pipBoundsState.getBounds()))) {
                            ((HandlerExecutor) pipTouchState.mMainExecutor).removeCallbacks(pipTouchState.mHoverExitTimeoutCallback);
                            phonePipMenuController2.showMenuWithPossibleDelay(pipBoundsState.getBounds(), pipTouchHandler.willResizeMenu(), pipTouchHandler.mPipTaskOrganizer.shouldShowSplitMenu());
                        }
                    }
                    if (!z11 && !pipTouchHandler.mSendingHoverAccessibilityEvents) {
                        pipTouchHandler.sendAccessibilityHoverEvent(128);
                        pipTouchHandler.mSendingHoverAccessibilityEvents = true;
                    }
                } else {
                    motionEvent = motionEvent2;
                    phonePipMenuController = phonePipMenuController2;
                    z2 = (pipTouchState.mStartedDragging || pipTouchState.mIsDragging) ? false : true;
                    pipTouchState.reset();
                    Log.d("PipTouchHandler", "ACTION_CANCEL");
                    pipDismissButtonView.hideDismissTargetMaybe();
                }
                motionEvent = motionEvent2;
                z = z11;
                phonePipMenuController = phonePipMenuController2;
            } else {
                pipTouchHandler.updateMovementBounds();
                PipTouchHandler pipTouchHandler3 = PipTouchHandler.this;
                pipTouchHandler3.mPipDismissTargetHandler.getClass();
                pipTouchHandler3.mPipDismissTargetHandler.getClass();
                PipDismissButtonView pipDismissButtonView2 = pipTouchHandler3.mDismissButtonView;
                DismissButtonView dismissButtonView = pipDismissButtonView2.mDismissButtonManager.mView;
                if ((dismissButtonView != null) && dismissButtonView.mIsEnterDismissButton) {
                    pipTouchHandler3.mMotionHelper.dismissPip();
                    pipDismissButtonView2.hideDismissTargetMaybe();
                    r6 = true;
                } else {
                    pipDismissButtonView2.hideDismissTargetMaybe();
                    if (pipTouchState.mIsUserInteracting) {
                        PointF pointF4 = pipTouchState.mVelocity;
                        boolean z13 = pipTouchState.mIsDragging;
                        PipTouchState pipTouchState3 = pipTouchHandler3.mTouchState;
                        PipUiEventLogger pipUiEventLogger = pipTouchHandler3.mPipUiEventLogger;
                        PipTaskOrganizer pipTaskOrganizer2 = pipTouchHandler3.mPipTaskOrganizer;
                        PipBoundsState pipBoundsState3 = pipTouchHandler3.mPipBoundsState;
                        if (z13) {
                            int i = pipTouchHandler3.mMenuState;
                            if (i != 0) {
                                pipTouchHandler3.mMenuController.showMenu(i, pipBoundsState3.getBounds(), true, pipTouchHandler3.willResizeMenu(), pipTaskOrganizer2.shouldShowSplitMenu());
                            }
                            defaultPipTouchGesture.mShouldHideMenuAfterFling = pipTouchHandler3.mMenuState == 0;
                            pipTouchState3.reset();
                            if (CoreRune.MW_NATURAL_SWITCHING_PIP && pipTouchHandler3.mPipNaturalSwitchingHandler.mWaitingForTaskVanished) {
                                Log.d("PipTouchHandler", "onUp: skip to fling, reason=ns_pip_processing");
                                motionEvent = motionEvent2;
                                z = z11;
                                phonePipMenuController = phonePipMenuController2;
                            } else {
                                if (pipTouchHandler3.mEnableStash) {
                                    Rect possiblyMotionBounds2 = pipTouchHandler3.getPossiblyMotionBounds();
                                    float f9 = pointF4.x;
                                    float f10 = pipTouchHandler3.mStashVelocityThreshold;
                                    boolean z14 = f9 < (-f10);
                                    boolean z15 = f9 > f10;
                                    int width = possiblyMotionBounds2.width() / 2;
                                    z = z11;
                                    phonePipMenuController = phonePipMenuController2;
                                    boolean z16 = possiblyMotionBounds2.left < pipBoundsState3.getDisplayBounds().left - width;
                                    motionEvent = motionEvent2;
                                    boolean z17 = possiblyMotionBounds2.right > pipBoundsState3.getDisplayBounds().right + width;
                                    DisplayCutout displayCutout = pipBoundsState3.getDisplayLayout().mCutout;
                                    if (displayCutout == null || ((z14 || z16) && !displayCutout.getBoundingRectLeft().isEmpty() ? !Rect.intersects(possiblyMotionBounds2, displayCutout.getBoundingRectLeft()) : !((z15 || z17) && !displayCutout.getBoundingRectRight().isEmpty() && Rect.intersects(possiblyMotionBounds2, displayCutout.getBoundingRectRight())))) {
                                        boolean z18 = (z14 && pipBoundsState3.mStashedState != 2) || (z15 && pipBoundsState3.mStashedState != 1);
                                        boolean z19 = z16 || z17;
                                        if (z18 || z19) {
                                            z4 = true;
                                            if (z4) {
                                                PipMotionHelper pipMotionHelper = pipTouchHandler3.mMotionHelper;
                                                float f11 = pointF4.x;
                                                float f12 = pointF4.y;
                                                PipTouchHandler$$ExternalSyntheticLambda1 pipTouchHandler$$ExternalSyntheticLambda1 = new PipTouchHandler$$ExternalSyntheticLambda1(defaultPipTouchGesture, 1);
                                                if (pipMotionHelper.mPipBoundsState.mStashedState == 0) {
                                                    f12 = 0.0f;
                                                }
                                                pipMotionHelper.movetoTarget(f11, f12, pipTouchHandler$$ExternalSyntheticLambda1, true);
                                            }
                                        }
                                    }
                                    z4 = false;
                                    if (z4) {
                                    }
                                } else {
                                    motionEvent = motionEvent2;
                                    z = z11;
                                    phonePipMenuController = phonePipMenuController2;
                                }
                                if (pipBoundsState3.isStashed()) {
                                    pipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_STASH_UNSTASHED);
                                    z3 = false;
                                    pipBoundsState3.setStashed(0, false);
                                } else {
                                    z3 = false;
                                }
                                pipTouchHandler3.mMotionHelper.movetoTarget(pointF4.x, pointF4.y, new PipTouchHandler$$ExternalSyntheticLambda1(defaultPipTouchGesture, 2), z3);
                            }
                        } else {
                            motionEvent = motionEvent2;
                            z = z11;
                            phonePipMenuController = phonePipMenuController2;
                            if (!pipTouchState3.mIsDoubleTap || pipBoundsState3.isStashed()) {
                                if (pipTouchHandler3.mMenuState != 1) {
                                    boolean isStashed2 = pipBoundsState3.isStashed();
                                    ShellExecutor shellExecutor = pipTouchState3.mMainExecutor;
                                    Runnable runnable = pipTouchState3.mDoubleTapTimeoutCallback;
                                    if (isStashed2) {
                                        pipTouchHandler3.animateToUnStashedState();
                                        pipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_STASH_UNSTASHED);
                                        pipBoundsState3.setStashed(0, false);
                                        pipTouchState3.mIsWaitingForDoubleTap = false;
                                        ((HandlerExecutor) shellExecutor).removeCallbacks(runnable);
                                    } else {
                                        boolean z20 = pipTouchState3.mIsWaitingForDoubleTap;
                                        if (!z20) {
                                            pipTouchHandler3.mMenuController.showMenu(1, pipBoundsState3.getBounds(), true, pipTouchHandler3.willResizeMenu(), pipTaskOrganizer2.shouldShowSplitMenu());
                                        } else if (z20) {
                                            long doubleTapTimeoutCallbackDelay = pipTouchState3.getDoubleTapTimeoutCallbackDelay();
                                            HandlerExecutor handlerExecutor = (HandlerExecutor) shellExecutor;
                                            handlerExecutor.removeCallbacks(runnable);
                                            handlerExecutor.executeDelayed(doubleTapTimeoutCallbackDelay, runnable);
                                        }
                                    }
                                }
                            } else if (pipTouchHandler3.mPipResizeGestureHandler.mEnablePinchResize) {
                                int width2 = pipBoundsState3.getBounds().width();
                                Point point2 = pipBoundsState3.mMaxSize;
                                if (width2 < point2.x) {
                                    pipBoundsState3.getBounds().height();
                                }
                                PhonePipMenuController phonePipMenuController3 = pipTouchHandler3.mMenuController;
                                if (phonePipMenuController3.isMenuVisible()) {
                                    phonePipMenuController3.hideMenu(1);
                                }
                                Rect rect2 = pipTouchHandler3.mPipResizeGestureHandler.mUserResizeBounds;
                                boolean z21 = pipBoundsState3.getBounds().width() == point2.x;
                                int width3 = pipBoundsState3.getBounds().width();
                                Rect rect3 = pipBoundsState3.mNormalBounds;
                                boolean z22 = width3 == rect3.width() && pipBoundsState3.getBounds().height() == rect3.height();
                                if (!z22 || rect2.width() != rect3.width()) {
                                    if (!z21 || rect2.width() != point2.x) {
                                        if (z22 || z21) {
                                            c = 2;
                                            if (c == 1) {
                                                pipTouchHandler3.mPipResizeGestureHandler.setUserResizeBounds(pipBoundsState3.getBounds());
                                                Rect rect4 = new Rect();
                                                Rect rect5 = new Rect(0, 0, point2.x, point2.y);
                                                int i2 = pipTouchHandler3.mIsImeShowing ? pipTouchHandler3.mImeHeight : 0;
                                                pipTouchHandler3.mPipBoundsAlgorithm.getClass();
                                                PipBoundsAlgorithm.getMovementBounds(rect5, pipTouchHandler3.mInsetBounds, rect4, i2);
                                                PipMotionHelper pipMotionHelper2 = pipTouchHandler3.mMotionHelper;
                                                pipMotionHelper2.getClass();
                                                float snapFraction = pipMotionHelper2.mSnapAlgorithm.getSnapFraction(0, new Rect(pipMotionHelper2.getBounds()), pipBoundsState3.mMovementBounds);
                                                PipSnapAlgorithm.applySnapFraction(snapFraction, rect5, rect4);
                                                pipMotionHelper2.mPostPipTransitionCallback = null;
                                                pipMotionHelper2.resizeAndAnimatePipUnchecked(rect5);
                                                pipTouchHandler3.mSavedSnapFraction = snapFraction;
                                            } else if (c == 0) {
                                                pipTouchHandler3.mPipResizeGestureHandler.setUserResizeBounds(pipBoundsState3.getBounds());
                                                pipTouchHandler3.animateToNormalSize(null);
                                            } else {
                                                pipTouchHandler3.animateToUnexpandedState(pipTouchHandler3.mPipResizeGestureHandler.mUserResizeBounds);
                                            }
                                        }
                                    }
                                    c = 0;
                                    if (c == 1) {
                                    }
                                }
                                c = 1;
                                if (c == 1) {
                                }
                            } else {
                                pipTouchState3.mAllowTouches = false;
                                if (pipTouchState3.mIsUserInteracting) {
                                    pipTouchState3.reset();
                                }
                                pipTouchHandler3.mMotionHelper.expandLeavePip(false, false);
                            }
                        }
                        r6 = true;
                    }
                }
                motionEvent = motionEvent2;
                z = z11;
                phonePipMenuController = phonePipMenuController2;
            }
            if (!(!pipBoundsState.isStashed()) || !z2) {
                MotionEvent obtain = MotionEvent.obtain(motionEvent);
                if (pipTouchState.mStartedDragging) {
                    obtain.setAction(3);
                    phonePipMenuController.pokeMenu();
                }
                PhonePipMenuController phonePipMenuController4 = phonePipMenuController;
                if (phonePipMenuController4.mPipMenuView != null) {
                    if (obtain.isTouchEvent()) {
                        phonePipMenuController4.mPipMenuView.dispatchTouchEvent(obtain);
                    } else {
                        phonePipMenuController4.mPipMenuView.dispatchGenericMotionEvent(obtain);
                    }
                }
                obtain.recycle();
                return;
            }
            return;
        }
        motionEvent = motionEvent2;
        z = z11;
        phonePipMenuController = phonePipMenuController2;
        defaultPipTouchGesture.getClass();
        if (pipTouchState.mIsUserInteracting) {
            PipTouchHandler pipTouchHandler4 = PipTouchHandler.this;
            Rect possiblyMotionBounds3 = pipTouchHandler4.getPossiblyMotionBounds();
            defaultPipTouchGesture.mDelta.set(0.0f, 0.0f);
            defaultPipTouchGesture.mStartPosition.set(possiblyMotionBounds3.left, possiblyMotionBounds3.top);
            float f13 = pipTouchState.mDownTouch.y;
            PipBoundsState pipBoundsState4 = pipTouchHandler4.mPipBoundsState;
            pipTouchHandler4.mMovementWithinDismiss = f13 >= ((float) pipBoundsState4.mMovementBounds.bottom);
            pipTouchHandler4.mMotionHelper.mSpringingToTouch = false;
            SurfaceControl surfaceControl2 = pipTouchHandler4.mPipTaskOrganizer.mLeash;
            pipTouchHandler4.mPipDismissTargetHandler.getClass();
            if (pipTouchHandler4.mMenuState != 0 && !pipBoundsState4.isStashed()) {
                pipTouchHandler4.mMenuController.pokeMenu();
            }
        }
        z2 = z;
        if (!((pipBoundsState.isStashed() ^ true) & z2)) {
        }
    }
}
