package com.android.wm.shell.pip2.phone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.PointF;
import android.graphics.Rect;
import android.provider.DeviceConfig;
import android.view.IWindowManager;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowInsets;
import android.view.WindowManagerGlobal;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.animation.FloatProperties;
import com.android.wm.shell.animation.FloatProperties$Companion$RECT_X$1;
import com.android.wm.shell.back.BackAnimationController$$ExternalSyntheticOutline0;
import com.android.wm.shell.bubbles.DismissViewUtils;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.pip2.phone.PipDismissTargetHandler.AnonymousClass1;
import com.android.wm.shell.pip2.phone.PipMotionHelper;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.android.wm.shell.shared.bubbles.DismissCircleView;
import com.android.wm.shell.shared.bubbles.DismissView;
import com.android.wm.shell.shared.magnetictarget.MagnetizedObject;
import java.io.PrintWriter;
import java.util.function.BiConsumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function5;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PipTouchHandler$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ PipTouchHandler f$0;

    public /* synthetic */ PipTouchHandler$$ExternalSyntheticLambda1(PipTouchHandler pipTouchHandler, int i) {
        this.$r8$classId = i;
        this.f$0 = pipTouchHandler;
    }

    /* JADX WARN: Type inference failed for: r3v11, types: [com.android.wm.shell.pip2.phone.PipMotionHelper$1, com.android.wm.shell.shared.magnetictarget.MagnetizedObject] */
    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        final PipTouchHandler pipTouchHandler = this.f$0;
        switch (i) {
            case 0:
                pipTouchHandler.updateMovementBounds();
                break;
            case 1:
                Rect bounds = pipTouchHandler.mPipBoundsState.getBounds();
                boolean willResizeMenu = pipTouchHandler.willResizeMenu();
                PhonePipMenuController phonePipMenuController = pipTouchHandler.mMenuController;
                if (willResizeMenu && phonePipMenuController.isMenuVisible()) {
                    PipMenuView pipMenuView = phonePipMenuController.mPipMenuView;
                    pipMenuView.mMenuContainer.setAlpha(0.0f);
                    pipMenuView.mSettingsButton.setAlpha(0.0f);
                    pipMenuView.mDismissButton.setAlpha(0.0f);
                }
                phonePipMenuController.showMenuInternal(1, bounds, true, willResizeMenu, willResizeMenu);
                break;
            case 2:
                if (!pipTouchHandler.mIsImeShowing || pipTouchHandler.mImeHeight <= pipTouchHandler.mShelfHeight) {
                    PipBoundsState pipBoundsState = pipTouchHandler.mPipBoundsState;
                    boolean z = pipBoundsState.mHasUserMovedPip || pipBoundsState.mHasUserResizedPip;
                    int i2 = pipTouchHandler.mPipBoundsAlgorithm.getEntryDestinationBounds().top - pipBoundsState.getBounds().top;
                    if (!pipTouchHandler.mIsImeShowing && !z && i2 != 0) {
                        pipTouchHandler.mMotionHelper.animateToOffset(i2, pipBoundsState.getBounds());
                        break;
                    }
                }
                break;
            default:
                pipTouchHandler.mEnableResize = pipTouchHandler.mContext.getResources().getBoolean(R.bool.config_pipEnableResizeForMenu);
                Resources resources = pipTouchHandler.mContext.getResources();
                resources.getDimensionPixelSize(R.dimen.pip_bottom_offset_buffer);
                resources.getDimensionPixelSize(R.dimen.pip_ime_offset);
                final PipDismissTargetHandler pipDismissTargetHandler = pipTouchHandler.mPipDismissTargetHandler;
                pipDismissTargetHandler.updateMagneticTargetSize();
                pipTouchHandler.mShellCommandHandler.addDumpCallback(new BiConsumer() { // from class: com.android.wm.shell.pip2.phone.PipTouchHandler$$ExternalSyntheticLambda8
                    @Override // java.util.function.BiConsumer
                    public final void accept(Object obj, Object obj2) {
                        PipTouchHandler pipTouchHandler2 = PipTouchHandler.this;
                        PrintWriter printWriter = (PrintWriter) obj;
                        String str = (String) obj2;
                        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "  ");
                        printWriter.println(str + "PipTouchHandler");
                        printWriter.println(m + "mMenuState=" + pipTouchHandler2.mMenuState);
                        StringBuilder sb = new StringBuilder();
                        sb.append(m);
                        sb.append("mIsImeShowing=");
                        StringBuilder m2 = BackAnimationController$$ExternalSyntheticOutline0.m(sb, pipTouchHandler2.mIsImeShowing, printWriter, m, "mImeHeight=");
                        m2.append(pipTouchHandler2.mImeHeight);
                        printWriter.println(m2.toString());
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(m);
                        sb2.append("mIsShelfShowing=");
                        StringBuilder m3 = BackAnimationController$$ExternalSyntheticOutline0.m(sb2, pipTouchHandler2.mIsShelfShowing, printWriter, m, "mShelfHeight=");
                        m3.append(pipTouchHandler2.mShelfHeight);
                        printWriter.println(m3.toString());
                        printWriter.println(m + "mSavedSnapFraction=" + pipTouchHandler2.mSavedSnapFraction);
                        printWriter.println(m + "mMovementBoundsExtraOffsets=0");
                        pipTouchHandler2.mPipBoundsAlgorithm.dump(printWriter, m);
                        PipTouchState pipTouchState = pipTouchHandler2.mTouchState;
                        pipTouchState.getClass();
                        String str2 = m + "  ";
                        printWriter.println(m + "PipTouchState");
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append(str2);
                        sb3.append("mAllowTouches=");
                        StringBuilder m4 = BackAnimationController$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(sb3, pipTouchState.mAllowTouches, printWriter, str2, "mAllowInputEvents="), pipTouchState.mAllowInputEvents, printWriter, str2, "mActivePointerId=");
                        m4.append(pipTouchState.mActivePointerId);
                        printWriter.println(m4.toString());
                        printWriter.println(str2 + "mLastTouchDisplayId=" + pipTouchState.mLastTouchDisplayId);
                        printWriter.println(str2 + "mDownTouch=" + pipTouchState.mDownTouch);
                        printWriter.println(str2 + "mDownDelta=" + pipTouchState.mDownDelta);
                        printWriter.println(str2 + "mLastTouch=" + pipTouchState.mLastTouch);
                        printWriter.println(str2 + "mLastDelta=" + pipTouchState.mLastDelta);
                        printWriter.println(str2 + "mVelocity=" + pipTouchState.mVelocity);
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append(str2);
                        sb4.append("mIsUserInteracting=");
                        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(sb4, pipTouchState.mIsUserInteracting, printWriter, str2, "mIsDragging="), pipTouchState.mIsDragging, printWriter, str2, "mStartedDragging="), pipTouchState.mStartedDragging, printWriter, str2, "mAllowDraggingOffscreen="), pipTouchState.mAllowDraggingOffscreen, printWriter);
                        PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler2.mPipResizeGestureHandler;
                        if (pipResizeGestureHandler != null) {
                            String m5 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m, "  ");
                            printWriter.println(m + "PipResizeGestureHandler");
                            StringBuilder sb5 = new StringBuilder();
                            sb5.append(m5);
                            sb5.append("mAllowGesture=");
                            StringBuilder m6 = BackAnimationController$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(BackAnimationController$$ExternalSyntheticOutline0.m(sb5, pipResizeGestureHandler.mAllowGesture, printWriter, m5, "mIsAttached="), pipResizeGestureHandler.mIsAttached, printWriter, m5, "mIsEnabled="), pipResizeGestureHandler.mIsEnabled, printWriter, m5, "mEnablePinchResize="), pipResizeGestureHandler.mEnablePinchResize, printWriter, m5, "mEnableDragCornerResize="), pipResizeGestureHandler.mEnableDragCornerResize, printWriter, m5, "mThresholdCrossed=");
                            m6.append(pipResizeGestureHandler.mThresholdCrossed);
                            printWriter.println(m6.toString());
                            printWriter.println(m5 + "mOhmOffset=0");
                        }
                    }
                }, pipTouchHandler);
                PipMotionHelper pipMotionHelper = pipTouchHandler.mMotionHelper;
                Rect rect = pipMotionHelper.mPipBoundsState.mMotionBoundsState.mBoundsInMotion;
                PhysicsAnimator.Companion.getClass();
                pipMotionHelper.mTemporaryBoundsPhysicsAnimator = PhysicsAnimator.Companion.getInstance(rect);
                PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler.mPipResizeGestureHandler;
                PipDragToResizeHandler pipDragToResizeHandler = pipResizeGestureHandler.mPipDragToResizeHandler;
                pipDragToResizeHandler.mDelta = pipDragToResizeHandler.mContext.getResources().getDimensionPixelSize(R.dimen.pip_resize_edge_size);
                pipResizeGestureHandler.mTouchSlop = ViewConfiguration.get(pipResizeGestureHandler.mContext).getScaledTouchSlop();
                pipResizeGestureHandler.mEnablePinchResize = pipResizeGestureHandler.mContext.getResources().getBoolean(R.bool.config_pipEnablePinchResize);
                Resources resources2 = pipDismissTargetHandler.mContext.getResources();
                pipDismissTargetHandler.mEnableDismissDragToEdge = resources2.getBoolean(R.bool.config_pipEnableDismissDragToEdge);
                pipDismissTargetHandler.mDismissAreaHeight = resources2.getDimensionPixelSize(R.dimen.floating_dismiss_gradient_height);
                DismissView dismissView = pipDismissTargetHandler.mTargetViewContainer;
                if (dismissView != null && dismissView.getParent() != null) {
                    pipDismissTargetHandler.mWindowManager.removeViewImmediate(pipDismissTargetHandler.mTargetViewContainer);
                }
                DismissView dismissView2 = new DismissView(pipDismissTargetHandler.mContext);
                pipDismissTargetHandler.mTargetViewContainer = dismissView2;
                DismissViewUtils.setup(dismissView2);
                DismissView dismissView3 = pipDismissTargetHandler.mTargetViewContainer;
                pipDismissTargetHandler.mTargetView = dismissView3.circle;
                dismissView3.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() { // from class: com.android.wm.shell.pip2.phone.PipDismissTargetHandler$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnApplyWindowInsetsListener
                    public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                        PipDismissTargetHandler pipDismissTargetHandler2 = PipDismissTargetHandler.this;
                        if (!windowInsets.equals(pipDismissTargetHandler2.mWindowInsets)) {
                            pipDismissTargetHandler2.mWindowInsets = windowInsets;
                            pipDismissTargetHandler2.updateMagneticTargetSize();
                        }
                        return windowInsets;
                    }
                });
                PipMotionHelper pipMotionHelper2 = pipDismissTargetHandler.mMotionHelper;
                if (pipMotionHelper2.mMagnetizedPip == null) {
                    ?? anonymousClass1 = new MagnetizedObject(pipMotionHelper2, pipMotionHelper2.mContext, pipMotionHelper2.mPipBoundsState.mMotionBoundsState.mBoundsInMotion, FloatProperties.RECT_X, FloatProperties.RECT_Y) { // from class: com.android.wm.shell.pip2.phone.PipMotionHelper.1
                        public AnonymousClass1(PipMotionHelper pipMotionHelper22, Context context, Rect rect2, FloatPropertyCompat floatPropertyCompat, FloatPropertyCompat floatPropertyCompat2) {
                            super(context, rect2, floatPropertyCompat, floatPropertyCompat2);
                        }

                        @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject
                        public final float getHeight(Object obj) {
                            return ((Rect) obj).height();
                        }

                        @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject
                        public final void getLocationOnScreen(Object obj, int[] iArr) {
                            Rect rect2 = (Rect) obj;
                            iArr[0] = rect2.left;
                            iArr[1] = rect2.top;
                        }

                        @Override // com.android.wm.shell.shared.magnetictarget.MagnetizedObject
                        public final float getWidth(Object obj) {
                            return ((Rect) obj).width();
                        }
                    };
                    pipMotionHelper22.mMagnetizedPip = anonymousClass1;
                    anonymousClass1.flingToTargetEnabled = false;
                }
                PipMotionHelper.AnonymousClass1 anonymousClass12 = pipMotionHelper22.mMagnetizedPip;
                pipDismissTargetHandler.mMagnetizedPip = anonymousClass12;
                anonymousClass12.associatedTargets.clear();
                PipMotionHelper.AnonymousClass1 anonymousClass13 = pipDismissTargetHandler.mMagnetizedPip;
                DismissCircleView dismissCircleView = pipDismissTargetHandler.mTargetView;
                anonymousClass13.getClass();
                MagnetizedObject.MagneticTarget magneticTarget = new MagnetizedObject.MagneticTarget(dismissCircleView, 0);
                anonymousClass13.associatedTargets.add(magneticTarget);
                magneticTarget.updateLocationOnScreen();
                pipDismissTargetHandler.mMagneticTarget = magneticTarget;
                pipDismissTargetHandler.updateMagneticTargetSize();
                PipMotionHelper.AnonymousClass1 anonymousClass14 = pipDismissTargetHandler.mMagnetizedPip;
                anonymousClass14.animateStuckToTarget = new Function5() { // from class: com.android.wm.shell.pip2.phone.PipDismissTargetHandler$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function5
                    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
                        MagnetizedObject.MagneticTarget magneticTarget2 = (MagnetizedObject.MagneticTarget) obj;
                        Float f = (Float) obj2;
                        Float f2 = (Float) obj3;
                        Boolean bool = (Boolean) obj4;
                        Function0 function0 = (Function0) obj5;
                        PipDismissTargetHandler pipDismissTargetHandler2 = PipDismissTargetHandler.this;
                        if (pipDismissTargetHandler2.mEnableDismissDragToEdge) {
                            PipMotionHelper pipMotionHelper3 = pipDismissTargetHandler2.mMotionHelper;
                            float floatValue = f.floatValue();
                            float floatValue2 = f2.floatValue();
                            bool.getClass();
                            pipMotionHelper3.getClass();
                            PointF pointF = magneticTarget2.centerOnScreen;
                            float dimensionPixelSize = pipMotionHelper3.mContext.getResources().getDimensionPixelSize(R.dimen.dismiss_circle_size);
                            PipBoundsState pipBoundsState2 = pipMotionHelper3.mPipBoundsState;
                            float f3 = dimensionPixelSize * 0.85f;
                            float width = f3 / (pipBoundsState2.getBounds().width() / pipBoundsState2.getBounds().height());
                            float f4 = pointF.x - (f3 / 2.0f);
                            float f5 = pointF.y - (width / 2.0f);
                            PipBoundsState.MotionBoundsState motionBoundsState = pipBoundsState2.mMotionBoundsState;
                            if (!motionBoundsState.isInMotion()) {
                                motionBoundsState.setBoundsInMotion(pipBoundsState2.getBounds());
                            }
                            PhysicsAnimator physicsAnimator = pipMotionHelper3.mTemporaryBoundsPhysicsAnimator;
                            FloatProperties$Companion$RECT_X$1 floatProperties$Companion$RECT_X$1 = FloatProperties.RECT_X;
                            PhysicsAnimator.SpringConfig springConfig = pipMotionHelper3.mAnimateToDismissSpringConfig;
                            physicsAnimator.spring(floatProperties$Companion$RECT_X$1, f4, floatValue, springConfig);
                            physicsAnimator.spring(FloatProperties.RECT_Y, f5, floatValue2, springConfig);
                            physicsAnimator.spring(FloatProperties.RECT_WIDTH, f3, 0.0f, springConfig);
                            physicsAnimator.spring(FloatProperties.RECT_HEIGHT, width, 0.0f, springConfig);
                            physicsAnimator.withEndActions(function0);
                            pipMotionHelper3.startBoundsAnimator(f4, f5, null);
                        }
                        return Unit.INSTANCE;
                    }
                };
                anonymousClass14.magnetListener = pipDismissTargetHandler.new AnonymousClass1();
                IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
                PipDisplayLayoutState pipDisplayLayoutState = pipTouchHandler.mPipDisplayLayoutState;
                ShellExecutor shellExecutor = pipTouchHandler.mMainExecutor;
                PipInputConsumer pipInputConsumer = new PipInputConsumer(windowManagerService, "pip_input_consumer", pipDisplayLayoutState, shellExecutor);
                pipTouchHandler.mPipInputConsumer = pipInputConsumer;
                pipInputConsumer.mListener = new PipTouchHandler$$ExternalSyntheticLambda10(pipTouchHandler);
                pipInputConsumer.mRegistrationListener = new PipTouchHandler$$ExternalSyntheticLambda10(pipTouchHandler);
                pipInputConsumer.mMainExecutor.execute(new PipInputConsumer$$ExternalSyntheticLambda1(pipInputConsumer, 1));
                pipTouchHandler.mEnableStash = DeviceConfig.getBoolean("systemui", "pip_stashing", true);
                final int i3 = 0;
                DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip2.phone.PipTouchHandler$$ExternalSyntheticLambda11
                    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                        int i4 = i3;
                        PipTouchHandler pipTouchHandler2 = pipTouchHandler;
                        switch (i4) {
                            case 0:
                                int i5 = PipTouchHandler.$r8$clinit;
                                pipTouchHandler2.getClass();
                                if (properties.getKeyset().contains("pip_stashing")) {
                                    pipTouchHandler2.mEnableStash = properties.getBoolean("pip_stashing", true);
                                    break;
                                }
                                break;
                            default:
                                int i6 = PipTouchHandler.$r8$clinit;
                                pipTouchHandler2.getClass();
                                if (properties.getKeyset().contains("pip_velocity_threshold")) {
                                    pipTouchHandler2.mStashVelocityThreshold = properties.getFloat("pip_velocity_threshold", 18000.0f);
                                    break;
                                }
                                break;
                        }
                    }
                });
                pipTouchHandler.mStashVelocityThreshold = DeviceConfig.getFloat("systemui", "pip_velocity_threshold", 18000.0f);
                final int i4 = 1;
                DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip2.phone.PipTouchHandler$$ExternalSyntheticLambda11
                    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                        int i42 = i4;
                        PipTouchHandler pipTouchHandler2 = pipTouchHandler;
                        switch (i42) {
                            case 0:
                                int i5 = PipTouchHandler.$r8$clinit;
                                pipTouchHandler2.getClass();
                                if (properties.getKeyset().contains("pip_stashing")) {
                                    pipTouchHandler2.mEnableStash = properties.getBoolean("pip_stashing", true);
                                    break;
                                }
                                break;
                            default:
                                int i6 = PipTouchHandler.$r8$clinit;
                                pipTouchHandler2.getClass();
                                if (properties.getKeyset().contains("pip_velocity_threshold")) {
                                    pipTouchHandler2.mStashVelocityThreshold = properties.getFloat("pip_velocity_threshold", 18000.0f);
                                    break;
                                }
                                break;
                        }
                    }
                });
                break;
        }
    }
}
