package com.android.wm.shell.pip.phone;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.media.session.MediaSessionManager;
import android.os.Debug;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.internal.util.function.TriConsumer;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.accessibility.MagnificationImpl$$ExternalSyntheticOutline0;
import com.android.wm.shell.WindowManagerShellWrapper;
import com.android.wm.shell.common.DismissViewManager;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.TabletopModeController;
import com.android.wm.shell.common.TaskStackListenerCallback;
import com.android.wm.shell.common.pip.PipAppOpsListener;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.common.pip.PipMediaController;
import com.android.wm.shell.common.pip.PipMediaController$mSessionsChangedListener$1;
import com.android.wm.shell.common.pip.PipUtils;
import com.android.wm.shell.pip.PinnedStackListenerForwarder;
import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.pip.PipParamsChangedForwarder;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.PipTransitionState;
import com.android.wm.shell.pip.phone.PipController;
import com.android.wm.shell.pip.phone.PipController.AnonymousClass3;
import com.android.wm.shell.pip.phone.PipController.AnonymousClass4;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.sysui.ShellController;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public final /* synthetic */ class PipController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PipController f$0;

    public /* synthetic */ PipController$$ExternalSyntheticLambda3(PipController pipController, int i) {
        this.$r8$classId = i;
        this.f$0 = pipController;
    }

    @Override // java.lang.Runnable
    public final void run() throws Resources.NotFoundException {
        int i = 2;
        int i2 = 3;
        int i3 = 1;
        int i4 = 0;
        switch (this.$r8$classId) {
            case 0:
                final PipController pipController = this.f$0;
                int i5 = PipController.$r8$clinit;
                pipController.mShellCommandHandler.addDumpCallback(new BiConsumer() { // from class: com.android.wm.shell.pip.phone.PipController$$ExternalSyntheticLambda5
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        PipController pipController2 = pipController;
                        PrintWriter printWriter = (PrintWriter) obj;
                        int i6 = PipController.$r8$clinit;
                        printWriter.println("PipController");
                        if (pipController2.mIsKeyguardShowingOrAnimating) {
                            printWriter.println("  mIsKeyguardShowingOrAnimating=true");
                        }
                        if (pipController2.mIsInFixedRotation) {
                            printWriter.println("  mIsInFixedRotation=true");
                        }
                        PhonePipMenuController phonePipMenuController = pipController2.mMenuController;
                        phonePipMenuController.getClass();
                        printWriter.println("  PhonePipMenuController");
                        StringBuilder sbM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("    mMenuState="), phonePipMenuController.mMenuState, printWriter, "    mPipMenuView=");
                        sbM.append(phonePipMenuController.mPipMenuView);
                        printWriter.println(sbM.toString());
                        printWriter.println("    mListeners=" + phonePipMenuController.mListeners.size());
                        PipTouchHandler pipTouchHandler = pipController2.mTouchHandler;
                        pipTouchHandler.getClass();
                        printWriter.println("  PipTouchHandler");
                        MagnificationImpl$$ExternalSyntheticOutline0.m(MagnificationImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("    mMenuState="), pipTouchHandler.mMenuState, printWriter, "    mIsImeShowing="), pipTouchHandler.mIsImeShowing, printWriter, "    mImeHeight="), pipTouchHandler.mImeHeight, printWriter, "    mIsShelfShowing="), pipTouchHandler.mIsShelfShowing, printWriter, "    mShelfHeight="), pipTouchHandler.mShelfHeight, printWriter, "    mSavedSnapFraction="), pipTouchHandler.mSavedSnapFraction, printWriter, "    mMovementBoundsExtraOffsets="), pipTouchHandler.mMovementBoundsExtraOffsets, printWriter);
                        pipTouchHandler.mPipBoundsAlgorithm.dump(printWriter, "    ");
                        PipTouchState pipTouchState = pipTouchHandler.mTouchState;
                        pipTouchState.getClass();
                        printWriter.println("    PipTouchState");
                        StringBuilder sbM2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("      mAllowTouches="), pipTouchState.mAllowTouches, printWriter, "      mAllowInputEvents="), pipTouchState.mAllowInputEvents, printWriter, "      mActivePointerId="), pipTouchState.mActivePointerId, printWriter, "      mLastTouchDisplayId="), pipTouchState.mLastTouchDisplayId, printWriter, "      mDownTouch=");
                        sbM2.append(pipTouchState.mDownTouch);
                        printWriter.println(sbM2.toString());
                        printWriter.println("      mDownDelta=" + pipTouchState.mDownDelta);
                        printWriter.println("      mLastTouch=" + pipTouchState.mLastTouch);
                        printWriter.println("      mLastDelta=" + pipTouchState.mLastDelta);
                        printWriter.println("      mVelocity=" + pipTouchState.mVelocity);
                        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("      mIsUserInteracting="), pipTouchState.mIsUserInteracting, printWriter, "      mIsDragging="), pipTouchState.mIsDragging, printWriter, "      mStartedDragging="), pipTouchState.mStartedDragging, printWriter, "      mAllowDraggingOffscreen="), pipTouchState.mAllowDraggingOffscreen, printWriter);
                        PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler.mPipResizeGestureHandler;
                        if (pipResizeGestureHandler != null) {
                            StringBuilder sbM3 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "    PipResizeGestureHandler", "      mAllowGesture="), pipResizeGestureHandler.mAllowGesture, printWriter, "      mIsAttached="), pipResizeGestureHandler.mIsAttached, printWriter, "      mIsEnabled="), pipResizeGestureHandler.mIsEnabled, printWriter, "      mEnablePinchResize="), pipResizeGestureHandler.mEnablePinchResize, printWriter, "      mThresholdCrossed="), pipResizeGestureHandler.mThresholdCrossed, printWriter, "      mOhmOffset="), pipResizeGestureHandler.mOhmOffset, printWriter, "      mMinSize=");
                            sbM3.append(pipResizeGestureHandler.mMinSize);
                            printWriter.println(sbM3.toString());
                            printWriter.println("      mMaxSize=" + pipResizeGestureHandler.mMaxSize);
                            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("      mCtrlType="), pipResizeGestureHandler.mCtrlType, printWriter, "      mOngoingPinchToResize="), pipResizeGestureHandler.mOngoingPinchToResize, printWriter);
                        }
                        pipController2.mPipBoundsAlgorithm.dump(printWriter, "  ");
                        pipController2.mPipTaskOrganizer.dump$2(printWriter, "  ");
                        pipController2.mPipBoundsState.dump(printWriter);
                        PipInputConsumer pipInputConsumer = pipController2.mPipInputConsumer;
                        pipInputConsumer.getClass();
                        printWriter.println("  PipInputConsumer");
                        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("    registered="), pipInputConsumer.mInputEventReceiver != null, printWriter);
                        pipController2.mPipDisplayLayoutState.dump(printWriter);
                    }
                }, pipController);
                IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
                ShellExecutor shellExecutor = pipController.mMainExecutor;
                pipController.mPipInputConsumer = new PipInputConsumer(windowManagerService, "pip_input_consumer", shellExecutor);
                ((HashMap) pipController.mPipTransitionController.mPipTransitionCallbacks).put(pipController, shellExecutor);
                pipController.mPipTaskOrganizer.mOnDisplayIdChangeCallback = new PipController$$ExternalSyntheticLambda8(pipController, i4);
                ((ArrayList) pipController.mPipTransitionState.mOnPipTransitionStateChangedListeners).add(new PipController$$ExternalSyntheticLambda9(pipController));
                PipController$$ExternalSyntheticLambda3 pipController$$ExternalSyntheticLambda3 = new PipController$$ExternalSyntheticLambda3(pipController, i);
                PipBoundsState pipBoundsState = pipController.mPipBoundsState;
                pipBoundsState.mOnMinimalSizeChangeCallback = pipController$$ExternalSyntheticLambda3;
                new TriConsumer() { // from class: com.android.wm.shell.pip.phone.PipController$$ExternalSyntheticLambda11
                    public final void accept(Object obj, Object obj2, Object obj3) {
                        PipController pipController2 = pipController;
                        int i6 = PipController.$r8$clinit;
                        pipController2.getClass();
                        boolean zBooleanValue = ((Boolean) obj).booleanValue();
                        int iIntValue = ((Integer) obj2).intValue();
                        PipTouchHandler pipTouchHandler = pipController2.mTouchHandler;
                        pipTouchHandler.mIsShelfShowing = zBooleanValue;
                        pipTouchHandler.mShelfHeight = iIntValue;
                        if (((Boolean) obj3).booleanValue()) {
                            pipController2.updateMovementBounds(pipController2.mPipBoundsState.getBounds(), false, false, true, null);
                        }
                    }
                };
                pipBoundsState.getClass();
                pipBoundsState.mOnPipTaskAppearedCallback = new PipController$$ExternalSyntheticLambda3(pipController, i2);
                pipBoundsState.mOnPipStashCallback = new PipController$$ExternalSyntheticLambda8(pipController, i3);
                PipTouchHandler pipTouchHandler = pipController.mTouchHandler;
                if (pipTouchHandler != null) {
                    PipInputConsumer pipInputConsumer = pipController.mPipInputConsumer;
                    pipInputConsumer.mListener = new PipController$$ExternalSyntheticLambda14(pipTouchHandler);
                    pipInputConsumer.mRegistrationListener = new PipController$$ExternalSyntheticLambda14(pipTouchHandler);
                    pipInputConsumer.mMainExecutor.execute(new PipInputConsumer$$ExternalSyntheticLambda1(pipInputConsumer, i4));
                }
                DisplayController displayController = pipController.mDisplayController;
                displayController.addDisplayChangingController(pipController.mRotationController);
                displayController.addDisplayWindowListener(pipController.mDisplaysChangedListener, -1);
                int displayId = pipController.mContext.getDisplayId();
                PipDisplayLayoutState pipDisplayLayoutState = pipController.mPipDisplayLayoutState;
                pipDisplayLayoutState.mDisplayId = displayId;
                Context context = pipController.mContext;
                pipDisplayLayoutState.mDisplayLayout.set(new DisplayLayout(context, context.getDisplay()));
                try {
                    WindowManagerShellWrapper windowManagerShellWrapper = pipController.mWindowManagerShellWrapper;
                    PipController.PipControllerPinnedTaskListener pipControllerPinnedTaskListener = pipController.mPinnedTaskListener;
                    PinnedStackListenerForwarder pinnedStackListenerForwarder = windowManagerShellWrapper.mPinnedStackListenerForwarder;
                    pinnedStackListenerForwarder.mListeners.add(pipControllerPinnedTaskListener);
                    WindowManagerGlobal.getWindowManagerService().registerPinnedTaskListener(0, pinnedStackListenerForwarder.mListenerImpl);
                } catch (RemoteException e) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[4]) {
                        ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -3110474237468854425L, 0, "PipController", String.valueOf(e));
                    }
                }
                try {
                    if (ActivityTaskManager.getService().getRootTaskInfo(2, 0) != null) {
                        pipController.mPipInputConsumer.registerInputConsumer();
                    }
                } catch (RemoteException | UnsupportedOperationException e2) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[4]) {
                        ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -3110474237468854425L, 0, "PipController", String.valueOf(e2));
                    }
                    e2.printStackTrace();
                }
                pipController.mAppOpsListener.mCallback = pipTouchHandler.mMotionHelper;
                pipController.mTaskStackListener.addListener(new TaskStackListenerCallback() { // from class: com.android.wm.shell.pip.phone.PipController.2
                    public AnonymousClass2() {
                    }

                    @Override // com.android.wm.shell.common.TaskStackListenerCallback
                    public final void onActivityPinned(int i6, String str) {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -26415654613861651L, 0, String.valueOf(str));
                        }
                        PipController pipController2 = PipController.this;
                        PipTouchHandler pipTouchHandler2 = pipController2.mTouchHandler;
                        pipTouchHandler2.mPipDismissTargetHandler.getClass();
                        PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler2.mPipResizeGestureHandler;
                        pipResizeGestureHandler.mIsAttached = true;
                        pipResizeGestureHandler.updateIsEnabled();
                        PipTouchState pipTouchState = pipResizeGestureHandler.mPipTouchState;
                        pipTouchState.mAllowTouches = true;
                        if (pipTouchState.mIsUserInteracting) {
                            pipTouchState.reset();
                        }
                        pipResizeGestureHandler.resetState();
                        pipTouchHandler2.mFloatingContentCoordinator.onContentAdded(pipTouchHandler2.mMotionHelper);
                        PipMediaController pipMediaController = pipController2.mMediaController;
                        MediaSessionManager mediaSessionManager = pipMediaController.mMediaSessionManager;
                        mediaSessionManager.getClass();
                        pipMediaController.resolveActiveMediaController(mediaSessionManager.getActiveSessionsForUser(null, new UserHandle(i6)));
                        PipAppOpsListener pipAppOpsListener = pipController2.mAppOpsListener;
                        pipAppOpsListener.mAppOpsManager.startWatchingMode(67, str, pipAppOpsListener.mAppOpsChangedListener);
                        pipController2.mPipInputConsumer.registerInputConsumer();
                    }

                    @Override // com.android.wm.shell.common.TaskStackListenerCallback
                    public final void onActivityRestartAttempt(ActivityManager.RunningTaskInfo runningTaskInfo, boolean z, boolean z2) {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 2590835325021008847L, 12, String.valueOf(runningTaskInfo.topActivity), Boolean.valueOf(z2));
                        }
                        PipController pipController2 = PipController.this;
                        PipTaskOrganizer pipTaskOrganizer = pipController2.mPipTaskOrganizer;
                        ComponentName componentName = pipTaskOrganizer.mPipBoundsState.mLastPipComponentName;
                        if (runningTaskInfo.getWindowingMode() != 2) {
                            PipTransitionState pipTransitionState = pipTaskOrganizer.mPipTransitionState;
                            if (pipTransitionState.mState == 3 && pipTransitionState.mInSwipePipToHomeTransition && (componentName == null || componentName.equals(runningTaskInfo.topActivity))) {
                                Log.d("PipTaskOrganizer", "onActivityRestartAttempt: force exit pip");
                                pipTaskOrganizer.cancelCurrentAnimator();
                                pipTaskOrganizer.onExitPipFinished(runningTaskInfo);
                            }
                        }
                        if (runningTaskInfo.getWindowingMode() == 2 && z2) {
                            if (CoreRune.MW_PIP_SHELL_TRANSITION) {
                                PipTransitionState pipTransitionState2 = pipController2.mPipTransitionState;
                                int i6 = pipTransitionState2.mState;
                                if (i6 == 1) {
                                    Log.w("PipController", "onActivityRestartAttempt: Skip expandLeavePip, t #" + runningTaskInfo.taskId + ", reason=exit_before_enterAnimation");
                                    return;
                                }
                                if (pipController2.mIsInFixedRotation && i6 == 3) {
                                    Log.w("PipController", "onActivityRestartAttempt: Skip expandLeavePip, t #" + runningTaskInfo.taskId + ", reason=entering_with_fixed_rot");
                                    return;
                                }
                                if (pipTransitionState2.mInSwipePipToHomeTransition) {
                                    Log.w("PipController", "onActivityRestartAttempt: Skip expandLeavePip, t #" + runningTaskInfo.taskId + ", reason=entering_with_SwipePipToHomeTransition");
                                    return;
                                }
                            }
                            PipTaskOrganizer pipTaskOrganizer2 = pipController2.mPipTaskOrganizer;
                            if (pipTaskOrganizer2.mSplitScreenOptional.isPresent() && ((SplitScreenController) pipTaskOrganizer2.mSplitScreenOptional.get()).isLaunchToSplit(runningTaskInfo)) {
                                pipController2.mTouchHandler.mMotionHelper.expandLeavePip$1(false, true);
                            } else {
                                pipController2.mTouchHandler.mMotionHelper.expandLeavePip$1(z, false);
                            }
                        }
                    }

                    @Override // com.android.wm.shell.common.TaskStackListenerCallback
                    public final void onActivityUnpinned() {
                        PipController pipController2 = PipController.this;
                        ComponentName componentName = (ComponentName) PipUtils.getTopPipActivity(pipController2.mContext).first;
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 6066697896891529186L, 0, String.valueOf(componentName));
                        }
                        PipTouchHandler pipTouchHandler2 = pipController2.mTouchHandler;
                        if (componentName == null) {
                            pipTouchHandler2.mPipDismissTargetHandler.getClass();
                            DismissViewManager dismissViewManager = pipTouchHandler2.mDismissButtonView.mDismissViewManager;
                            if (dismissViewManager.mView != null) {
                                dismissViewManager.cleanUpDismissTarget();
                            }
                            ((HashMap) pipTouchHandler2.mFloatingContentCoordinator.allContentBounds).remove(pipTouchHandler2.mMotionHelper);
                        }
                        PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler2.mPipResizeGestureHandler;
                        pipResizeGestureHandler.mIsAttached = false;
                        pipResizeGestureHandler.mUserResizeBounds.setEmpty();
                        pipResizeGestureHandler.updateIsEnabled();
                        pipResizeGestureHandler.mLastResizeBounds.setEmpty();
                        PipTouchState pipTouchState = pipResizeGestureHandler.mPipTouchState;
                        pipTouchState.mAllowTouches = true;
                        if (pipTouchState.mIsUserInteracting) {
                            pipTouchState.reset();
                        }
                        pipResizeGestureHandler.resetState();
                        PipAppOpsListener pipAppOpsListener = pipController2.mAppOpsListener;
                        pipAppOpsListener.mAppOpsManager.stopWatchingMode(pipAppOpsListener.mAppOpsChangedListener);
                        PipInputConsumer pipInputConsumer2 = pipController2.mPipInputConsumer;
                        if (pipInputConsumer2.mInputEventReceiver == null) {
                            return;
                        }
                        try {
                            pipInputConsumer2.mWindowManager.destroyInputConsumer(pipInputConsumer2.mToken, 0);
                        } catch (RemoteException e3) {
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[4]) {
                                ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -2654699908319398243L, 0, "PipInputConsumer", String.valueOf(e3));
                            }
                        }
                        pipInputConsumer2.mInputEventReceiver.dispose();
                        pipInputConsumer2.mInputEventReceiver = null;
                        pipInputConsumer2.mMainExecutor.execute(new PipInputConsumer$$ExternalSyntheticLambda1(pipInputConsumer2, 1));
                    }
                });
                PipController.AnonymousClass3 anonymousClass3 = pipController.new AnonymousClass3();
                PipParamsChangedForwarder pipParamsChangedForwarder = pipController.mPipParamsChangedForwarder;
                if (!((ArrayList) pipParamsChangedForwarder.mPipParamsChangedListeners).contains(anonymousClass3)) {
                    ((ArrayList) pipParamsChangedForwarder.mPipParamsChangedListeners).add(anonymousClass3);
                }
                pipController.mDisplayInsetsController.addInsetsChangedListener(pipDisplayLayoutState.mDisplayId, pipController.new AnonymousClass4());
                PipController$$ExternalSyntheticLambda16 pipController$$ExternalSyntheticLambda16 = new PipController$$ExternalSyntheticLambda16(pipController);
                TabletopModeController tabletopModeController = pipController.mTabletopModeController;
                if (!((ArrayList) tabletopModeController.mListeners).contains(pipController$$ExternalSyntheticLambda16)) {
                    ((ArrayList) tabletopModeController.mListeners).add(pipController$$ExternalSyntheticLambda16);
                    pipController$$ExternalSyntheticLambda16.onTabletopModeChanged(tabletopModeController.isInTabletopMode());
                }
                pipController.mOneHandedController.ifPresent(new PipController$$ExternalSyntheticLambda6(pipController, i4));
                PipMediaController pipMediaController = pipController.mMediaController;
                MediaSessionManager mediaSessionManager = pipMediaController.mMediaSessionManager;
                mediaSessionManager.getClass();
                PipMediaController$mSessionsChangedListener$1 pipMediaController$mSessionsChangedListener$1 = pipMediaController.mSessionsChangedListener;
                mediaSessionManager.removeOnActiveSessionsChangedListener(pipMediaController$mSessionsChangedListener$1);
                pipMediaController.mMediaSessionManager.addOnActiveSessionsChangedListener(null, UserHandle.CURRENT, pipMediaController.mHandlerExecutor, pipMediaController$mSessionsChangedListener$1);
                ShellController shellController = pipController.mShellController;
                shellController.addConfigurationChangeListener(pipController);
                shellController.addKeyguardChangeListener(pipController);
                shellController.addUserChangeListener(pipController);
                shellController.addExternalInterface("com.android.wm.shell.common.pip.IPip", new Supplier() { // from class: com.android.wm.shell.pip.phone.PipController$$ExternalSyntheticLambda7
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        PipController pipController2 = pipController;
                        int i6 = PipController.$r8$clinit;
                        pipController2.getClass();
                        return new PipController.IPipImpl(pipController2);
                    }
                }, pipController);
                break;
            case 1:
                PipController pipController2 = this.f$0;
                if (!pipController2.mIsKeyguardShowingOrAnimating && !pipController2.mPipBoundsState.isStashed()) {
                    PipAnimationController.PipTransitionAnimator pipTransitionAnimator = pipController2.mPipAnimationController.mCurrentAnimator;
                    if (pipTransitionAnimator != null && pipTransitionAnimator.isRunning()) {
                        HandlerExecutor handlerExecutor = (HandlerExecutor) pipController2.mMainExecutor;
                        PipController$$ExternalSyntheticLambda3 pipController$$ExternalSyntheticLambda32 = pipController2.mMovePipInResponseToKeepClearAreasChangeCallback;
                        handlerExecutor.removeCallbacks(pipController$$ExternalSyntheticLambda32);
                        handlerExecutor.executeDelayed(pipController$$ExternalSyntheticLambda32, PipController.PIP_KEEP_CLEAR_AREAS_DELAY);
                        break;
                    } else {
                        pipController2.updatePipPositionForKeepClearAreas();
                        break;
                    }
                }
                break;
            case 2:
                PipController pipController3 = this.f$0;
                int i6 = PipController.$r8$clinit;
                pipController3.updateMovementBounds(null, false, false, false, null);
                break;
            case 3:
                PipController pipController4 = this.f$0;
                int i7 = PipController.$r8$clinit;
                StringBuilder sb = new StringBuilder();
                StringBuilder sb2 = new StringBuilder("prev=");
                PipBoundsState pipBoundsState2 = pipController4.mPipBoundsState;
                sb2.append(pipBoundsState2);
                sb.append(sb2.toString());
                pipController4.updateMovementBounds(null, false, false, false, null);
                sb.append(", next=" + pipBoundsState2);
                Log.d("PipController", "onPipTaskAppeared: " + sb.toString());
                break;
            case 4:
                PipController pipController5 = this.f$0;
                PipTaskOrganizer pipTaskOrganizer = pipController5.mPipTaskOrganizer;
                boolean z = pipTaskOrganizer.mNeedToCheckRotation;
                PipTouchHandler pipTouchHandler2 = pipController5.mTouchHandler;
                PipBoundsState pipBoundsState3 = pipController5.mPipBoundsState;
                if (!z) {
                    int i8 = pipBoundsState3.getBounds().top;
                    int i9 = pipBoundsState3.mMovementBounds.bottom;
                    if (i8 > i9) {
                        PipMotionHelper pipMotionHelper = pipTouchHandler2.mMotionHelper;
                        Rect bounds = pipBoundsState3.getBounds();
                        int i10 = i9 - i8;
                        pipMotionHelper.getClass();
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -5833957059391898973L, 0, "PipMotionHelper", String.valueOf(bounds), String.valueOf(i10), String.valueOf(Debug.getCallers(5, "    ")));
                        }
                        pipMotionHelper.cancelPhysicsAnimation$1();
                        PipTaskOrganizer pipTaskOrganizer2 = pipMotionHelper.mPipTaskOrganizer;
                        PipTransitionState pipTransitionState = pipTaskOrganizer2.mPipTransitionState;
                        int i11 = pipTransitionState.mState;
                        if (i11 >= 3 && i11 != 5 && !pipTransitionState.mInSwipePipToHomeTransition) {
                            if (!pipTaskOrganizer2.mWaitForFixedRotation) {
                                if (i11 != 3) {
                                    if (pipTaskOrganizer2.mTaskInfo != null) {
                                        Rect rect = new Rect(bounds);
                                        rect.offset(0, i10);
                                        pipTaskOrganizer2.animateResizePip(bounds, rect, null, 1, 300, 0.0f);
                                    } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[3]) {
                                        ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -6507529459632686448L, 0, "PipTaskOrganizer");
                                    }
                                    Rect rect2 = new Rect(bounds);
                                    rect2.offset(0, i10);
                                    PipMotionHelper$$ExternalSyntheticLambda0 pipMotionHelper$$ExternalSyntheticLambda0 = pipMotionHelper.mUpdateBoundsCallback;
                                    if (pipMotionHelper$$ExternalSyntheticLambda0 != null) {
                                        pipMotionHelper$$ExternalSyntheticLambda0.accept(rect2);
                                        break;
                                    }
                                } else {
                                    Log.d("PipTaskOrganizer", "skip offset pip mState=ENTERING_PIP");
                                    break;
                                }
                            } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 3384126671658565527L, 0, "PipTaskOrganizer");
                                break;
                            }
                        }
                    }
                } else {
                    pipTaskOrganizer.mNeedToCheckRotation = false;
                    int i12 = pipTaskOrganizer.mCurrentRotation;
                    int i13 = pipBoundsState3.mPipDisplayLayoutState.getDisplayLayout().mRotation;
                    if (i12 != i13) {
                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                        pipController5.mRotationController.onDisplayChange(pipController5.mPipDisplayLayoutState.mDisplayId, i13, i12, null, windowContainerTransaction);
                        pipTaskOrganizer.applyFinishBoundsResize(1, windowContainerTransaction, false);
                        pipTouchHandler2.mMotionHelper.moveToBounds(pipBoundsState3.getBounds());
                        break;
                    }
                }
                break;
            default:
                PipTouchState pipTouchState = this.f$0.mTouchHandler.mTouchState;
                pipTouchState.mAllowTouches = true;
                if (pipTouchState.mIsUserInteracting) {
                    pipTouchState.reset();
                    break;
                }
                break;
        }
    }
}
