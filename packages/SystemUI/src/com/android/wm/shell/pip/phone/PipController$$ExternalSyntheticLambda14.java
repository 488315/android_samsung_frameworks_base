package com.android.wm.shell.pip.phone;

import android.app.ActivityManager;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.InputEvent;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.DismissView;
import com.android.wm.shell.common.DismissViewManager;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.common.pip.PipDoubleTapHelper;
import com.android.wm.shell.common.pip.PipPerfHintController;
import com.android.wm.shell.common.pip.PipSnapAlgorithm;
import com.android.wm.shell.common.pip.PipUiEventLogger;
import com.android.wm.shell.naturalswitching.NaturalSwitchingDropTargetController;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.phone.PipTouchHandler;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public final /* synthetic */ class PipController$$ExternalSyntheticLambda14 {
    public final /* synthetic */ PipTouchHandler f$0;

    public /* synthetic */ PipController$$ExternalSyntheticLambda14(PipTouchHandler pipTouchHandler) {
        this.f$0 = pipTouchHandler;
    }

    /* JADX WARN: Removed duplicated region for block: B:164:0x028e  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x02af  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x035c  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x049d  */
    /* JADX WARN: Removed duplicated region for block: B:297:0x04bd  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x04c3  */
    /* JADX WARN: Removed duplicated region for block: B:338:0x05d2  */
    /* JADX WARN: Removed duplicated region for block: B:342:0x05dd A[PHI: r7 r18 r20
      0x05dd: PHI (r7v25 boolean) = (r7v23 boolean), (r7v47 boolean) binds: [B:340:0x05d9, B:161:0x0280] A[DONT_GENERATE, DONT_INLINE]
      0x05dd: PHI (r18v7 android.view.MotionEvent) = (r18v4 android.view.MotionEvent), (r18v9 android.view.MotionEvent) binds: [B:340:0x05d9, B:161:0x0280] A[DONT_GENERATE, DONT_INLINE]
      0x05dd: PHI (r20v12 com.android.wm.shell.common.pip.PipBoundsState) = (r20v9 com.android.wm.shell.common.pip.PipBoundsState), (r20v14 com.android.wm.shell.common.pip.PipBoundsState) binds: [B:340:0x05d9, B:161:0x0280] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0164  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0167  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onInputEvent(InputEvent inputEvent) throws Resources.NotFoundException {
        MotionEvent motionEvent;
        PipBoundsState pipBoundsState;
        boolean z;
        PipPerfHintController.PipHighPerfSession pipHighPerfSession;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        PipTouchHandler pipTouchHandler = this.f$0;
        pipTouchHandler.getClass();
        if (inputEvent instanceof MotionEvent) {
            PipTouchState pipTouchState = pipTouchHandler.mTouchState;
            if (!pipTouchState.mAllowInputEvents) {
                Log.d("PipTouchHandler", "pip input event not allowed");
                return;
            }
            MotionEvent motionEvent2 = (MotionEvent) inputEvent;
            boolean z6 = CoreRune.MW_NATURAL_SWITCHING_PIP;
            if (z6) {
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
                            boolean zIsStashed = pipNaturalSwitchingHandler.mPipBoundsState.isStashed();
                            boolean z7 = pipTouchState2.mAllowTouches;
                            boolean z8 = !z7;
                            boolean z9 = pipTouchState2.mIsWaitingForDoubleTap;
                            boolean zHasEnteredPip = pipTaskOrganizer.mPipTransitionState.hasEnteredPip();
                            boolean zAllowInterceptTouch = naturalSwitchingDropTargetController.allowInterceptTouch(pipNaturalSwitchingHandler.mTaskInfo);
                            if (zIsStashed || !z7 || z9 || zAllowInterceptTouch || !zHasEnteredPip) {
                                StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("startNaturalSwitchingIfPossible: failed, st=", ", tb=", ", wd=", zIsStashed, z8);
                                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, z9, ", ib=", zAllowInterceptTouch, ", ip=");
                                ActionBarContextView$$ExternalSyntheticOutline0.m(sbM, zHasEnteredPip, "PipNaturalSwitchingHandler");
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
                } else if (pipNaturalSwitchingHandler.mState != 0) {
                    if ((pipNaturalSwitchingHandler.mScaleDownAnimator != null || pipNaturalSwitchingHandler.mScaleUpPhysicsAnimator != null) && pipTouchState2.mIsDragging) {
                        pipNaturalSwitchingHandler.clearAllAnimations();
                    }
                    boolean zOnInterceptTouchEvent = naturalSwitchingDropTargetController.onInterceptTouchEvent(motionEvent2, pipNaturalSwitchingHandler.mTaskId);
                    boolean z10 = action == 1 && naturalSwitchingDropTargetController.mLayoutChanged;
                    boolean z11 = action == 3 || (action == 1 && !z10);
                    if (z10) {
                        pipNaturalSwitchingHandler.updateWaitingForTaskVanished("dropped", true);
                    } else if (z11) {
                        pipNaturalSwitchingHandler.setState(0);
                    } else if (zOnInterceptTouchEvent && pipNaturalSwitchingHandler.mState != 2) {
                        pipNaturalSwitchingHandler.setState(2);
                    }
                }
            }
            PipBoundsState pipBoundsState2 = pipTouchHandler.mPipBoundsState;
            if (!pipBoundsState2.isStashed()) {
                PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler.mPipResizeGestureHandler;
                if (pipResizeGestureHandler.mIsSysUiStateValid) {
                    int actionMasked = motionEvent2.getActionMasked();
                    if (actionMasked != 0) {
                        if (actionMasked == 5 && pipResizeGestureHandler.mEnablePinchResize && motionEvent2.getPointerCount() == 2) {
                            pipResizeGestureHandler.onPinchResize(motionEvent2);
                            z5 = pipResizeGestureHandler.mAllowGesture;
                            pipResizeGestureHandler.mOngoingPinchToResize = z5;
                        }
                        if (z5) {
                        }
                    } else {
                        if (pipResizeGestureHandler.isWithinDragResizeRegion((int) motionEvent2.getRawX(), (int) motionEvent2.getRawY())) {
                            z5 = true;
                        }
                        if (z5) {
                        }
                    }
                } else {
                    z5 = false;
                    if (z5) {
                        pipTouchState.onTouchEvent(motionEvent2);
                        pipTouchState.reset();
                        if (pipTouchHandler.mPipResizeGestureHandler.mEnablePinchResize) {
                            pipBoundsState2.updateMinMaxSize(pipBoundsState2.mAspectRatio);
                            PipResizeGestureHandler pipResizeGestureHandler2 = pipTouchHandler.mPipResizeGestureHandler;
                            Point point = pipBoundsState2.mMinSize;
                            pipResizeGestureHandler2.updateMinSize(point.x, point.y);
                            PipResizeGestureHandler pipResizeGestureHandler3 = pipTouchHandler.mPipResizeGestureHandler;
                            Point point2 = pipBoundsState2.mMaxSize;
                            pipResizeGestureHandler3.updateMaxSize(point2.x, point2.y);
                            return;
                        }
                        return;
                    }
                }
            }
            PipResizeGestureHandler pipResizeGestureHandler4 = pipTouchHandler.mPipResizeGestureHandler;
            boolean z12 = pipResizeGestureHandler4.mCtrlType != 0 || pipResizeGestureHandler4.mOngoingPinchToResize;
            PipTouchHandler.DefaultPipTouchGesture defaultPipTouchGesture = pipTouchHandler.mGesture;
            PipDismissButtonView pipDismissButtonView = pipTouchHandler.mDismissButtonView;
            PipDismissTargetHandler pipDismissTargetHandler = pipTouchHandler.mPipDismissTargetHandler;
            if (z12) {
                PipPerfHintController.PipHighPerfSession pipHighPerfSession2 = defaultPipTouchGesture.mPipHighPerfSession;
                if (pipHighPerfSession2 != null) {
                    pipHighPerfSession2.close();
                    defaultPipTouchGesture.mPipHighPerfSession = null;
                }
                pipDismissTargetHandler.getClass();
                pipDismissButtonView.hideDismissTargetMaybe();
                Log.d("PipTouchHandler", "block touch event for resize gesture");
                return;
            }
            if (motionEvent2.getAction() == 0 || pipTouchState.mIsUserInteracting) {
                pipDismissTargetHandler.getClass();
            }
            boolean z13 = pipTouchState.mIsUserInteracting;
            PipTaskOrganizer pipTaskOrganizer2 = pipTouchHandler.mPipTaskOrganizer;
            if (!z13) {
                if (pipTaskOrganizer2.mPipTransitionState.mState == 2) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[5]) {
                        ProtoLogImpl_1771455215.wtf(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 8100108414880986319L, 0, "PipTouchHandler");
                        return;
                    }
                    return;
                }
            }
            pipTouchState.onTouchEvent(motionEvent2);
            boolean z14 = pipTouchHandler.mMenuState != 0;
            int action2 = motionEvent2.getAction();
            PhonePipMenuController phonePipMenuController = pipTouchHandler.mMenuController;
            if (action2 == 0) {
                motionEvent = motionEvent2;
                pipBoundsState = pipBoundsState2;
                defaultPipTouchGesture.getClass();
                if (pipTouchState.mIsUserInteracting) {
                    PipTouchHandler pipTouchHandler2 = PipTouchHandler.this;
                    PipPerfHintController pipPerfHintController = pipTouchHandler2.mPipPerfHintController;
                    if (pipPerfHintController != null) {
                        defaultPipTouchGesture.mPipHighPerfSession = pipPerfHintController.startSession(new PipTouchHandler$$ExternalSyntheticLambda6(defaultPipTouchGesture, 1), "DefaultPipTouchGesture#onDown");
                    }
                    Rect possiblyMotionBounds = pipTouchHandler2.getPossiblyMotionBounds();
                    defaultPipTouchGesture.mDelta.set(0.0f, 0.0f);
                    defaultPipTouchGesture.mStartPosition.set(possiblyMotionBounds.left, possiblyMotionBounds.top);
                    float f = pipTouchState.mDownTouch.y;
                    PipBoundsState pipBoundsState3 = pipTouchHandler2.mPipBoundsState;
                    pipTouchHandler2.mMovementWithinDismiss = f >= ((float) pipBoundsState3.mMovementBounds.bottom);
                    pipTouchHandler2.mMotionHelper.getClass();
                    SurfaceControl surfaceControl2 = pipTouchHandler2.mPipTaskOrganizer.mLeash;
                    pipTouchHandler2.mPipDismissTargetHandler.getClass();
                    if (pipTouchHandler2.mMenuState != 0 && !pipBoundsState3.isStashed()) {
                        pipTouchHandler2.mMenuController.pokeMenu();
                    }
                }
            } else if (action2 != 1) {
                if (action2 == 2) {
                    defaultPipTouchGesture.getClass();
                    if (pipTouchState.mIsUserInteracting) {
                        boolean z15 = pipTouchState.mStartedDragging;
                        PipTouchHandler pipTouchHandler3 = PipTouchHandler.this;
                        if (z15) {
                            pipTouchHandler3.mSavedSnapFraction = -1.0f;
                            pipTouchHandler3.mPipDismissTargetHandler.getClass();
                            if (z6) {
                                if (!(pipTouchHandler3.mPipNaturalSwitchingHandler.mState == 2)) {
                                }
                            } else {
                                Insets insets = Insets.NONE;
                                PipBoundsState pipBoundsState4 = pipTouchHandler3.mPipBoundsState;
                                pipBoundsState4.mPipDisplayLayoutState.getDisplayLayout();
                                Insets.of(pipBoundsState4.mPipDisplayLayoutState.getDisplayLayout().mStableInsets);
                                PointF pointF = pipTouchState.mLastTouch;
                                DismissViewManager dismissViewManager = pipTouchHandler3.mDismissButtonView.mDismissViewManager;
                                if (!dismissViewManager.isAttachedToWindow()) {
                                    dismissViewManager.createDismissView();
                                    dismissViewManager.createOrUpdateWrapper();
                                }
                                if (dismissViewManager.getVisibility() != 0) {
                                    dismissViewManager.show();
                                }
                                dismissViewManager.updateDismissTargetView(pointF);
                            }
                        }
                        if (pipTouchState.mIsDragging) {
                            PipBoundsState pipBoundsState5 = pipTouchHandler3.mPipBoundsState;
                            pipBoundsState5.mHasUserMovedPip = true;
                            if (pipBoundsState5.mIsImeShowing) {
                                pipBoundsState5.mRestoreBounds.setEmpty();
                            }
                            PointF pointF2 = pipTouchState.mLastDelta;
                            Point point3 = defaultPipTouchGesture.mStartPosition;
                            float f2 = point3.x;
                            PointF pointF3 = defaultPipTouchGesture.mDelta;
                            float f3 = pointF3.x;
                            float f4 = f2 + f3;
                            float f5 = point3.y;
                            float f6 = pointF3.y;
                            float f7 = f5 + f6;
                            float f8 = pointF2.x + f4;
                            float f9 = pointF2.y + f7;
                            pointF3.x = (f8 - f4) + f3;
                            pointF3.y = (f9 - f7) + f6;
                            pipTouchHandler3.mTmpBounds.set(pipTouchHandler3.getPossiblyMotionBounds());
                            pipTouchHandler3.mTmpBounds.offsetTo((int) f8, (int) f9);
                            pipTouchHandler3.mMotionHelper.movePip(pipTouchHandler3.mTmpBounds, true);
                            PointF pointF4 = pipTouchState.mLastTouch;
                            DismissViewManager dismissViewManager2 = pipTouchHandler3.mDismissButtonView.mDismissViewManager;
                            if (dismissViewManager2.mView != null) {
                                dismissViewManager2.updateDismissTargetView(pointF4);
                            }
                            PointF pointF5 = pipTouchState.mLastTouch;
                            if (pipTouchHandler3.mMovementWithinDismiss) {
                                pipTouchHandler3.mMovementWithinDismiss = pointF5.y >= ((float) pipTouchHandler3.mPipBoundsState.mMovementBounds.bottom);
                            }
                            z4 = true;
                        }
                        if (!z4) {
                        }
                    } else {
                        z4 = false;
                        if (!z4) {
                            z14 = !pipTouchState.mIsDragging;
                        }
                    }
                } else if (action2 == 3) {
                    motionEvent = motionEvent2;
                    pipBoundsState = pipBoundsState2;
                    z = false;
                    if (!pipTouchState.mStartedDragging && !pipTouchState.mIsDragging) {
                        z = true;
                    }
                    pipTouchState.reset();
                    Log.d("PipTouchHandler", "ACTION_CANCEL");
                    pipDismissButtonView.hideDismissTargetMaybe();
                    z14 = z;
                } else if (action2 == 7) {
                    if (!z14 && !pipTouchHandler.mSendingHoverAccessibilityEvents) {
                        pipTouchHandler.sendAccessibilityHoverEvent(128);
                        pipTouchHandler.mSendingHoverAccessibilityEvents = true;
                    }
                } else if (action2 == 9) {
                    if (!pipTouchHandler.mAccessibilityManager.isTouchExplorationEnabled() && (pipTouchHandler.mPipResizeGestureHandler.getLastResizeBounds().isEmpty() || pipTouchHandler.mPipResizeGestureHandler.getLastResizeBounds().equals(pipBoundsState2.getBounds()))) {
                        ((HandlerExecutor) pipTouchState.mMainExecutor).removeCallbacks(pipTouchState.mHoverExitTimeoutCallback);
                        phonePipMenuController.showMenuWithPossibleDelay(pipBoundsState2.getBounds(), pipTouchHandler.willResizeMenu(), pipTaskOrganizer2.shouldShowSplitMenu());
                    }
                    if (!z14) {
                        pipTouchHandler.sendAccessibilityHoverEvent(128);
                        pipTouchHandler.mSendingHoverAccessibilityEvents = true;
                    }
                } else if (action2 == 10) {
                    if (!pipTouchHandler.mAccessibilityManager.isTouchExplorationEnabled()) {
                        pipTouchState.scheduleHoverExitTimeoutCallback();
                    }
                    if (!z14 && pipTouchHandler.mSendingHoverAccessibilityEvents) {
                        pipTouchHandler.sendAccessibilityHoverEvent(256);
                        pipTouchHandler.mSendingHoverAccessibilityEvents = false;
                    }
                }
                motionEvent = motionEvent2;
                pipBoundsState = pipBoundsState2;
            } else {
                pipTouchHandler.updateMovementBounds();
                PipTouchHandler pipTouchHandler4 = PipTouchHandler.this;
                pipTouchHandler4.mPipDismissTargetHandler.getClass();
                pipTouchHandler4.mPipDismissTargetHandler.getClass();
                PipDismissButtonView pipDismissButtonView2 = pipTouchHandler4.mDismissButtonView;
                DismissView dismissView = pipDismissButtonView2.mDismissViewManager.mView;
                if ((dismissView != null) && dismissView.mIsEnterDismissButton) {
                    pipTouchHandler4.mMotionHelper.dismissPip();
                    pipDismissButtonView2.hideDismissTargetMaybe();
                    motionEvent = motionEvent2;
                    pipBoundsState = pipBoundsState2;
                    z2 = true;
                } else {
                    pipDismissButtonView2.hideDismissTargetMaybe();
                    if (pipTouchState.mIsUserInteracting) {
                        PointF pointF6 = pipTouchState.mVelocity;
                        boolean z16 = pipTouchState.mIsDragging;
                        PipTouchState pipTouchState3 = pipTouchHandler4.mTouchState;
                        PhonePipMenuController phonePipMenuController2 = pipTouchHandler4.mMenuController;
                        PipUiEventLogger pipUiEventLogger = pipTouchHandler4.mPipUiEventLogger;
                        PipTaskOrganizer pipTaskOrganizer3 = pipTouchHandler4.mPipTaskOrganizer;
                        motionEvent = motionEvent2;
                        PipBoundsState pipBoundsState6 = pipTouchHandler4.mPipBoundsState;
                        if (z16) {
                            if (pipTouchHandler4.mMenuState != 0) {
                                z3 = z6;
                                pipBoundsState = pipBoundsState2;
                                phonePipMenuController2.showMenuInternal(pipBoundsState6.getBounds(), pipTouchHandler4.willResizeMenu(), false, pipTaskOrganizer3.shouldShowSplitMenu());
                            } else {
                                z3 = z6;
                                pipBoundsState = pipBoundsState2;
                            }
                            defaultPipTouchGesture.mShouldHideMenuAfterFling = pipTouchHandler4.mMenuState == 0;
                            pipTouchState3.reset();
                            if (z3 && pipTouchHandler4.mPipNaturalSwitchingHandler.mWaitingForTaskVanished) {
                                Log.d("PipTouchHandler", "onUp: skip to fling, reason=ns_pip_processing");
                            } else if (pipTouchHandler4.mEnableStash) {
                                Rect possiblyMotionBounds2 = pipTouchHandler4.getPossiblyMotionBounds();
                                float f10 = pointF6.x;
                                float f11 = pipTouchHandler4.mStashVelocityThreshold;
                                boolean z17 = f10 < (-f11);
                                boolean z18 = f10 > f11;
                                int iWidth = possiblyMotionBounds2.width() / 2;
                                boolean z19 = possiblyMotionBounds2.left < pipBoundsState6.mPipDisplayLayoutState.getDisplayBounds().left - iWidth;
                                int i = possiblyMotionBounds2.right;
                                boolean z20 = z18;
                                PipDisplayLayoutState pipDisplayLayoutState = pipBoundsState6.mPipDisplayLayoutState;
                                boolean z21 = i > pipDisplayLayoutState.getDisplayBounds().right + iWidth;
                                DisplayCutout displayCutout = pipDisplayLayoutState.getDisplayLayout().mCutout;
                                if (displayCutout == null || ((z17 || z19) && !displayCutout.getBoundingRectLeft().isEmpty() ? !Rect.intersects(possiblyMotionBounds2, displayCutout.getBoundingRectLeft()) : !((z20 || z21) && !displayCutout.getBoundingRectRight().isEmpty() && Rect.intersects(possiblyMotionBounds2, displayCutout.getBoundingRectRight())))) {
                                    boolean z22 = (z17 && pipBoundsState6.mStashedState != 2) || (z20 && pipBoundsState6.mStashedState != 1);
                                    boolean z23 = z19 || z21;
                                    if (!z22 && z23) {
                                        int i2 = pipBoundsState6.mStashedState;
                                        if ((z19 && i2 == 1) || (z21 && i2 == 2)) {
                                            ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(i2, "Prevented PiP from being stashed to the opposite side. stashedState=", "PipTouchHandler");
                                        }
                                        if (pipBoundsState6.isStashed()) {
                                        }
                                        pipTouchHandler4.mMotionHelper.movetoTarget$1(pointF6.x, pointF6.y, new PipTouchHandler$$ExternalSyntheticLambda1(defaultPipTouchGesture, 2), false);
                                    } else if (z22 || z23) {
                                        PipMotionHelper pipMotionHelper = pipTouchHandler4.mMotionHelper;
                                        pipMotionHelper.movetoTarget$1(pointF6.x, pipMotionHelper.mPipBoundsState.mStashedState == 0 ? 0.0f : pointF6.y, new PipTouchHandler$$ExternalSyntheticLambda1(defaultPipTouchGesture, 1), true);
                                    } else {
                                        if (pipBoundsState6.isStashed()) {
                                            pipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_STASH_UNSTASHED);
                                            pipBoundsState6.setStashed(0, false);
                                        }
                                        pipTouchHandler4.mMotionHelper.movetoTarget$1(pointF6.x, pointF6.y, new PipTouchHandler$$ExternalSyntheticLambda1(defaultPipTouchGesture, 2), false);
                                    }
                                }
                            }
                        } else {
                            pipBoundsState = pipBoundsState2;
                            if (!pipTouchState3.mIsDoubleTap || pipBoundsState6.isStashed()) {
                                if (pipTouchHandler4.mMenuState != 1) {
                                    boolean zIsStashed2 = pipBoundsState6.isStashed();
                                    ShellExecutor shellExecutor = pipTouchState3.mMainExecutor;
                                    if (zIsStashed2) {
                                        pipTouchHandler4.animateToUnStashedState();
                                        pipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_STASH_UNSTASHED);
                                        pipBoundsState6.setStashed(0, false);
                                        pipTouchState3.mIsWaitingForDoubleTap = false;
                                        ((HandlerExecutor) shellExecutor).removeCallbacks(pipTouchState3.mDoubleTapTimeoutCallback);
                                    } else {
                                        boolean z24 = pipTouchState3.mIsWaitingForDoubleTap;
                                        if (z24) {
                                            z = false;
                                            if (z24) {
                                                long doubleTapTimeoutCallbackDelay = pipTouchState3.getDoubleTapTimeoutCallbackDelay();
                                                HandlerExecutor handlerExecutor = (HandlerExecutor) shellExecutor;
                                                handlerExecutor.removeCallbacks(pipTouchState3.mDoubleTapTimeoutCallback);
                                                handlerExecutor.executeDelayed(pipTouchState3.mDoubleTapTimeoutCallback, doubleTapTimeoutCallbackDelay);
                                            }
                                        } else {
                                            z = false;
                                            phonePipMenuController2.showMenuInternal(pipBoundsState6.getBounds(), pipTouchHandler4.willResizeMenu(), false, pipTaskOrganizer3.shouldShowSplitMenu());
                                        }
                                        pipHighPerfSession = defaultPipTouchGesture.mPipHighPerfSession;
                                        if (pipHighPerfSession != null) {
                                            pipHighPerfSession.close();
                                            defaultPipTouchGesture.mPipHighPerfSession = null;
                                        }
                                        z2 = true;
                                        if (!z2) {
                                            if (!pipTouchState.mStartedDragging) {
                                                z = true;
                                            }
                                            pipTouchState.reset();
                                            Log.d("PipTouchHandler", "ACTION_CANCEL");
                                            pipDismissButtonView.hideDismissTargetMaybe();
                                            z14 = z;
                                        }
                                    }
                                }
                            } else if (pipTouchHandler4.mPipResizeGestureHandler.mEnablePinchResize) {
                                if (phonePipMenuController2.isMenuVisible()) {
                                    phonePipMenuController2.hideMenu(1);
                                }
                                int iNextSizeSpec = PipDoubleTapHelper.nextSizeSpec(pipBoundsState6, pipTouchHandler4.mPipResizeGestureHandler.mUserResizeBounds);
                                if (iNextSizeSpec == 1) {
                                    pipTouchHandler4.mPipResizeGestureHandler.setUserResizeBounds(pipBoundsState6.getBounds());
                                    Rect rect = new Rect();
                                    Point point4 = pipBoundsState6.mMaxSize;
                                    Rect rect2 = new Rect(0, 0, point4.x, point4.y);
                                    Rect rect3 = pipTouchHandler4.mInsetBounds;
                                    int i3 = pipTouchHandler4.mIsImeShowing ? pipTouchHandler4.mImeHeight : 0;
                                    pipTouchHandler4.mPipBoundsAlgorithm.getClass();
                                    PipBoundsAlgorithm.getMovementBounds(rect2, rect3, rect, i3);
                                    PipMotionHelper pipMotionHelper2 = pipTouchHandler4.mMotionHelper;
                                    Rect rect4 = pipBoundsState6.mMovementBounds;
                                    pipMotionHelper2.getClass();
                                    float snapFraction = pipMotionHelper2.mSnapAlgorithm.getSnapFraction(0, new Rect(pipMotionHelper2.mPipBoundsState.getBounds()), rect4);
                                    PipSnapAlgorithm.applySnapFraction(rect2, rect, snapFraction);
                                    pipMotionHelper2.mPostPipTransitionCallback = null;
                                    pipMotionHelper2.resizeAndAnimatePipUnchecked$1(rect2);
                                    pipTouchHandler4.mSavedSnapFraction = snapFraction;
                                } else if (iNextSizeSpec == 0) {
                                    pipTouchHandler4.mPipResizeGestureHandler.setUserResizeBounds(pipBoundsState6.getBounds());
                                    pipTouchHandler4.animateToNormalSize(null);
                                } else {
                                    pipTouchHandler4.animateToUnexpandedState(pipTouchHandler4.mPipResizeGestureHandler.mUserResizeBounds);
                                }
                                pipBoundsState6.setHasUserResizedPip();
                            } else {
                                pipTouchState3.mAllowTouches = false;
                                if (pipTouchState3.mIsUserInteracting) {
                                    pipTouchState3.reset();
                                }
                                pipTouchHandler4.mMotionHelper.expandLeavePip$1(false, false);
                            }
                        }
                        z = false;
                        pipHighPerfSession = defaultPipTouchGesture.mPipHighPerfSession;
                        if (pipHighPerfSession != null) {
                        }
                        z2 = true;
                        if (!z2) {
                        }
                    } else {
                        motionEvent = motionEvent2;
                        pipBoundsState = pipBoundsState2;
                        z2 = false;
                    }
                }
                z = false;
                if (!z2) {
                }
            }
            if ((!pipBoundsState.isStashed()) && z14) {
                MotionEvent motionEventObtain = MotionEvent.obtain(motionEvent);
                if (pipTouchState.mStartedDragging) {
                    motionEventObtain.setAction(3);
                    phonePipMenuController.pokeMenu();
                }
                if (phonePipMenuController.mPipMenuView != null) {
                    if (motionEventObtain.isTouchEvent()) {
                        phonePipMenuController.mPipMenuView.dispatchTouchEvent(motionEventObtain);
                    } else {
                        phonePipMenuController.mPipMenuView.dispatchGenericMotionEvent(motionEventObtain);
                    }
                }
                motionEventObtain.recycle();
            }
        }
    }
}
