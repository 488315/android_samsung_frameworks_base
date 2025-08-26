package com.android.wm.shell.pip2.phone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.Size;
import android.view.Choreographer;
import android.view.InputChannel;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.FloatingContentCoordinator;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PhoneSizeSpecSource;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDesktopState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.common.pip.PipPerfHintController;
import com.android.wm.shell.common.pip.PipSnapAlgorithm;
import com.android.wm.shell.common.pip.PipUiEventLogger;
import com.android.wm.shell.common.pip.PipUtils;
import com.android.wm.shell.common.pip.SizeSpecSource;
import com.android.wm.shell.pip2.phone.PipInputConsumer.InputEventReceiver;
import com.android.wm.shell.pip2.phone.PipTransitionState;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellInit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

/* loaded from: classes3.dex */
public class PipTouchHandler implements PipTransitionState.PipTransitionStateChangedListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public static final long PIP_KEEP_CLEAR_AREAS_DELAY = SystemProperties.getLong("persist.wm.debug.pip_keep_clear_areas_delay", 200);
    public final AccessibilityManager mAccessibilityManager;
    public final Context mContext;
    public boolean mEnableResize;
    public final FloatingContentCoordinator mFloatingContentCoordinator;
    public final DefaultPipTouchGesture mGesture;
    public int mImeHeight;
    public boolean mIsImeShowing;
    public boolean mIsShelfShowing;
    public final ShellExecutor mMainExecutor;
    public final PhonePipMenuController mMenuController;
    public PipMotionHelper mMotionHelper;
    public final PipTouchHandler$$ExternalSyntheticLambda1 mMoveOnShelVisibilityChanged;
    public boolean mMovementWithinDismiss;
    public final PipBoundsAlgorithm mPipBoundsAlgorithm;
    public final PipBoundsState mPipBoundsState;
    public final PipDismissTargetHandler mPipDismissTargetHandler;
    public final PipDisplayLayoutState mPipDisplayLayoutState;
    public PipInputConsumer mPipInputConsumer;
    public final PipPerfHintController mPipPerfHintController;
    public PipResizeGestureHandler mPipResizeGestureHandler;
    public final PipTransitionState mPipTransitionState;
    public final PipUiEventLogger mPipUiEventLogger;
    public boolean mSendingHoverAccessibilityEvents;
    public int mShelfHeight;
    public final ShellCommandHandler mShellCommandHandler;
    public final SizeSpecSource mSizeSpecSource;
    public float mStashVelocityThreshold;
    public final PipTouchState mTouchState;
    public boolean mEnableStash = true;
    public int mDeferResizeToNormalBoundsUntilRotation = -1;
    public int mMenuState = 0;
    public float mSavedSnapFraction = -1.0f;
    public final Rect mTmpBounds = new Rect();

    public class DefaultPipTouchGesture extends PipTouchGesture {
        public final PointF mDelta;
        public PipPerfHintController.PipHighPerfSession mPipHighPerfSession;
        public boolean mShouldHideMenuAfterFling;
        public final Point mStartPosition;

        public /* synthetic */ DefaultPipTouchGesture(PipTouchHandler pipTouchHandler, int i) {
            this();
        }

        private DefaultPipTouchGesture() {
            this.mStartPosition = new Point();
            this.mDelta = new PointF();
        }
    }

    public class PipMenuListener {
        public /* synthetic */ PipMenuListener(PipTouchHandler pipTouchHandler, int i) {
            this();
        }

        private PipMenuListener() {
        }
    }

    public PipTouchHandler(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, PhonePipMenuController phonePipMenuController, PipBoundsAlgorithm pipBoundsAlgorithm, PipBoundsState pipBoundsState, PipTransitionState pipTransitionState, PipScheduler pipScheduler, SizeSpecSource sizeSpecSource, PipDisplayLayoutState pipDisplayLayoutState, PipDesktopState pipDesktopState, DisplayController displayController, PipMotionHelper pipMotionHelper, FloatingContentCoordinator floatingContentCoordinator, PipUiEventLogger pipUiEventLogger, ShellExecutor shellExecutor, Optional<PipPerfHintController> optional) {
        int i = 0;
        this.mContext = context;
        this.mShellCommandHandler = shellCommandHandler;
        this.mMainExecutor = shellExecutor;
        PipPerfHintController pipPerfHintControllerOrElse = optional.orElse(null);
        this.mPipPerfHintController = pipPerfHintControllerOrElse;
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mPipBoundsAlgorithm = pipBoundsAlgorithm;
        this.mPipBoundsState = pipBoundsState;
        this.mPipTransitionState = pipTransitionState;
        pipTransitionState.addPipTransitionStateChangedListener(new PipTransitionState.PipTransitionStateChangedListener() { // from class: com.android.wm.shell.pip2.phone.PipTouchHandler$$ExternalSyntheticLambda0
            @Override // com.android.wm.shell.pip2.phone.PipTransitionState.PipTransitionStateChangedListener
            public final void onPipTransitionStateChanged(int i2, int i3, Bundle bundle) {
                this.f$0.onPipTransitionStateChanged(i2, i3, bundle);
            }
        });
        this.mSizeSpecSource = sizeSpecSource;
        this.mPipDisplayLayoutState = pipDisplayLayoutState;
        this.mMenuController = phonePipMenuController;
        this.mPipUiEventLogger = pipUiEventLogger;
        this.mFloatingContentCoordinator = floatingContentCoordinator;
        PipMenuListener pipMenuListener = new PipMenuListener(this, i);
        if (!phonePipMenuController.mListeners.contains(pipMenuListener)) {
            phonePipMenuController.mListeners.add(pipMenuListener);
        }
        this.mGesture = new DefaultPipTouchGesture(this, i);
        this.mMotionHelper = pipMotionHelper;
        pipScheduler.mUpdateMovementBoundsRunnable = new PipTouchHandler$$ExternalSyntheticLambda1(this, 0);
        this.mPipDismissTargetHandler = new PipDismissTargetHandler(context, pipUiEventLogger, pipMotionHelper, pipDisplayLayoutState, displayController, shellExecutor);
        PipTouchState pipTouchState = new PipTouchState(ViewConfiguration.get(context), new PipTouchHandler$$ExternalSyntheticLambda1(this, 1), new PipTouchHandler$$ExternalSyntheticLambda3(phonePipMenuController, 0), shellExecutor);
        this.mTouchState = pipTouchState;
        this.mPipResizeGestureHandler = new PipResizeGestureHandler(context, pipBoundsAlgorithm, pipBoundsState, pipTouchState, pipScheduler, pipTransitionState, pipUiEventLogger, phonePipMenuController, new Function() { // from class: com.android.wm.shell.pip2.phone.PipTouchHandler$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                int i2 = PipTouchHandler.$r8$clinit;
                return this.f$0.getMovementBounds((Rect) obj);
            }
        }, pipDisplayLayoutState, pipDesktopState, shellExecutor, pipPerfHintControllerOrElse);
        PipTouchHandler$$ExternalSyntheticLambda5 pipTouchHandler$$ExternalSyntheticLambda5 = new PipTouchHandler$$ExternalSyntheticLambda5(this, 0);
        if (!((ArrayList) pipBoundsState.mOnAspectRatioChangedCallbacks).contains(pipTouchHandler$$ExternalSyntheticLambda5)) {
            ((ArrayList) pipBoundsState.mOnAspectRatioChangedCallbacks).add(pipTouchHandler$$ExternalSyntheticLambda5);
            pipTouchHandler$$ExternalSyntheticLambda5.accept(Float.valueOf(pipBoundsState.mAspectRatio));
        }
        this.mMoveOnShelVisibilityChanged = new PipTouchHandler$$ExternalSyntheticLambda1(this, 2);
        if (PipUtils.isPip2ExperimentEnabled()) {
            shellInit.addInitCallback(new PipTouchHandler$$ExternalSyntheticLambda1(this, 3), this);
        }
    }

    public final void animateToNormalSize(PipMenuView$$ExternalSyntheticLambda0 pipMenuView$$ExternalSyntheticLambda0) throws Resources.NotFoundException {
        PipResizeGestureHandler pipResizeGestureHandler = this.mPipResizeGestureHandler;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        pipResizeGestureHandler.mUserResizeBounds.set(pipBoundsState.getBounds());
        Rect adjustedNormalBounds = getAdjustedNormalBounds();
        PipMotionHelper pipMotionHelper = this.mMotionHelper;
        Rect movementBounds = getMovementBounds(pipBoundsState.getBounds());
        Rect movementBounds2 = getMovementBounds(adjustedNormalBounds);
        pipMotionHelper.getClass();
        float snapFraction = pipMotionHelper.mSnapAlgorithm.getSnapFraction(0, new Rect(pipMotionHelper.mPipBoundsState.getBounds()), movementBounds);
        PipSnapAlgorithm.applySnapFraction(adjustedNormalBounds, movementBounds2, snapFraction);
        pipMotionHelper.mPostPipTransitionCallback = pipMenuView$$ExternalSyntheticLambda0;
        pipMotionHelper.resizeAndAnimatePipUnchecked(adjustedNormalBounds);
        this.mSavedSnapFraction = snapFraction;
    }

    public final void animateToUnexpandedState(Rect rect) {
        PipMotionHelper pipMotionHelper = this.mMotionHelper;
        float snapFraction = this.mSavedSnapFraction;
        Rect movementBounds = getMovementBounds(rect);
        Rect movementBounds2 = getMovementBounds(this.mPipBoundsState.getBounds());
        PipBoundsState pipBoundsState = pipMotionHelper.mPipBoundsState;
        PipSnapAlgorithm pipSnapAlgorithm = pipMotionHelper.mSnapAlgorithm;
        if (snapFraction < 0.0f) {
            snapFraction = pipSnapAlgorithm.getSnapFraction(pipBoundsState.mStashedState, new Rect(pipBoundsState.getBounds()), movementBounds2);
        }
        float f = snapFraction;
        int i = pipBoundsState.mStashedState;
        int i2 = pipBoundsState.mStashOffset;
        PipDisplayLayoutState pipDisplayLayoutState = pipBoundsState.mPipDisplayLayoutState;
        Rect displayBounds = pipDisplayLayoutState.getDisplayBounds();
        Rect rect2 = pipDisplayLayoutState.getDisplayLayout().mStableInsets;
        pipSnapAlgorithm.getClass();
        PipSnapAlgorithm.applySnapFraction(rect, movementBounds, f, i, i2, displayBounds, rect2);
        pipMotionHelper.resizeAndAnimatePipUnchecked(rect);
        this.mSavedSnapFraction = -1.0f;
    }

    public final Rect getAdjustedNormalBounds() throws Resources.NotFoundException {
        Size estimatedMinMenuSize = this.mMenuController.getEstimatedMinMenuSize();
        Size defaultSize = ((PhoneSizeSpecSource) this.mSizeSpecSource).getDefaultSize(this.mPipBoundsState.mAspectRatio);
        return this.mPipBoundsAlgorithm.adjustNormalBoundsToFitMenu(new Rect(0, 0, defaultSize.getWidth(), defaultSize.getHeight()), estimatedMinMenuSize);
    }

    public final Rect getMovementBounds(Rect rect) {
        Rect rect2 = new Rect();
        Rect rect3 = new Rect();
        this.mPipBoundsAlgorithm.getInsetBounds(rect3);
        PipBoundsAlgorithm.getMovementBounds(rect, rect3, rect2, this.mIsImeShowing ? this.mImeHeight : 0);
        return rect2;
    }

    public PipResizeGestureHandler getPipResizeGestureHandler() {
        return this.mPipResizeGestureHandler;
    }

    public final Rect getPossiblyMotionBounds() {
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        return pipBoundsState.mMotionBoundsState.isInMotion() ? pipBoundsState.mMotionBoundsState.mBoundsInMotion : pipBoundsState.getBounds();
    }

    @Override // com.android.wm.shell.pip2.phone.PipTransitionState.PipTransitionStateChangedListener
    public final void onPipTransitionStateChanged(int i, int i2, Bundle bundle) {
        FloatingContentCoordinator floatingContentCoordinator = this.mFloatingContentCoordinator;
        PipDismissTargetHandler pipDismissTargetHandler = this.mPipDismissTargetHandler;
        PipTouchState pipTouchState = this.mTouchState;
        if (i2 == 3) {
            pipDismissTargetHandler.createOrUpdateDismissTarget();
            PipResizeGestureHandler pipResizeGestureHandler = this.mPipResizeGestureHandler;
            pipResizeGestureHandler.mIsAttached = true;
            pipResizeGestureHandler.updateIsEnabled();
            pipResizeGestureHandler.mEnableDragCornerResize = pipResizeGestureHandler.mPipDesktopState.isPipInDesktopMode();
            floatingContentCoordinator.onContentAdded(this.mMotionHelper);
            final PipInputConsumer pipInputConsumer = this.mPipInputConsumer;
            if (pipInputConsumer.mInputEventReceiver == null) {
                final InputChannel inputChannel = new InputChannel();
                try {
                    int i3 = pipInputConsumer.mPipDisplayLayoutState.mDisplayId;
                    pipInputConsumer.mWindowManager.destroyInputConsumer(pipInputConsumer.mToken, i3);
                    pipInputConsumer.mWindowManager.createInputConsumer(pipInputConsumer.mToken, pipInputConsumer.mName, i3, inputChannel);
                } catch (RemoteException e) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[4]) {
                        ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -2700829650412097031L, 0, "PipInputConsumer", String.valueOf(e));
                    }
                }
                pipInputConsumer.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.pip2.phone.PipInputConsumer$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        PipInputConsumer pipInputConsumer2 = pipInputConsumer;
                        InputChannel inputChannel2 = inputChannel;
                        pipInputConsumer2.getClass();
                        pipInputConsumer2.mInputEventReceiver = pipInputConsumer2.new InputEventReceiver(inputChannel2, Looper.myLooper(), Choreographer.getInstance());
                    }
                });
            }
            updateMovementBounds();
            pipTouchState.mAllowInputEvents = true;
            pipTouchState.reset();
            return;
        }
        if (i2 == 4) {
            pipTouchState.mAllowInputEvents = false;
            return;
        }
        if (i2 == 6) {
            pipTouchState.mAllowInputEvents = true;
            pipTouchState.reset();
            return;
        }
        if (i2 != 8) {
            return;
        }
        pipTouchState.mAllowInputEvents = false;
        if (pipDismissTargetHandler.mTargetViewContainer.getParent() != null) {
            pipDismissTargetHandler.mWindowManager.removeViewImmediate(pipDismissTargetHandler.mTargetViewContainer);
        }
        ((HashMap) floatingContentCoordinator.allContentBounds).remove(this.mMotionHelper);
        PipResizeGestureHandler pipResizeGestureHandler2 = this.mPipResizeGestureHandler;
        pipResizeGestureHandler2.mIsAttached = false;
        pipResizeGestureHandler2.mUserResizeBounds.setEmpty();
        pipResizeGestureHandler2.updateIsEnabled();
        PipInputConsumer pipInputConsumer2 = this.mPipInputConsumer;
        if (pipInputConsumer2.mInputEventReceiver != null) {
            try {
                pipInputConsumer2.mWindowManager.destroyInputConsumer(pipInputConsumer2.mToken, pipInputConsumer2.mPipDisplayLayoutState.mDisplayId);
            } catch (RemoteException e2) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[4]) {
                    ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 3552531324387735237L, 0, "PipInputConsumer", String.valueOf(e2));
                }
            }
            pipInputConsumer2.mInputEventReceiver.dispose();
            pipInputConsumer2.mInputEventReceiver = null;
            pipInputConsumer2.mMainExecutor.execute(new PipInputConsumer$$ExternalSyntheticLambda1(pipInputConsumer2, 0));
        }
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        pipBoundsState.mHasUserMovedPip = false;
        pipBoundsState.mHasUserResizedPip = false;
    }

    public final void sendAccessibilityHoverEvent(int i) {
        if (this.mAccessibilityManager.isEnabled()) {
            AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(i);
            accessibilityEventObtain.setImportantForAccessibility(true);
            accessibilityEventObtain.setSourceNodeId(AccessibilityNodeInfo.ROOT_NODE_ID);
            accessibilityEventObtain.setWindowId(-3);
            this.mAccessibilityManager.sendAccessibilityEvent(accessibilityEventObtain);
        }
    }

    public void setPipMotionHelper(PipMotionHelper pipMotionHelper) {
        this.mMotionHelper = pipMotionHelper;
    }

    public void setPipResizeGestureHandler(PipResizeGestureHandler pipResizeGestureHandler) {
        this.mPipResizeGestureHandler = pipResizeGestureHandler;
    }

    public final void updateMovementBounds() {
        Rect rect = new Rect();
        this.mPipBoundsAlgorithm.getInsetBounds(rect);
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        PipBoundsAlgorithm.getMovementBounds(pipBoundsState.getBounds(), rect, pipBoundsState.mMovementBounds, this.mIsImeShowing ? this.mImeHeight : 0);
        PipMotionHelper pipMotionHelper = this.mMotionHelper;
        PipBoundsState pipBoundsState2 = pipMotionHelper.mPipBoundsState;
        Rect rect2 = pipBoundsState2.mMovementBounds;
        pipMotionHelper.mFlingConfigX = new PhysicsAnimator.FlingConfig(1.9f, rect2.left, rect2.right);
        Rect rect3 = pipBoundsState2.mMovementBounds;
        pipMotionHelper.mFlingConfigY = new PhysicsAnimator.FlingConfig(1.9f, rect3.top, rect3.bottom);
        Rect rect4 = pipBoundsState2.mPipDisplayLayoutState.getDisplayLayout().mStableInsets;
        pipMotionHelper.mStashConfigX = new PhysicsAnimator.FlingConfig(1.9f, (pipBoundsState2.mStashOffset - pipBoundsState2.getBounds().width()) + rect4.left, (r1.getDisplayBounds().right - pipBoundsState2.mStashOffset) - rect4.right);
        pipMotionHelper.mFloatingAllowedArea.set(pipBoundsState2.mMovementBounds);
        Rect rect5 = pipMotionHelper.mFloatingAllowedArea;
        rect5.right = pipBoundsState2.getBounds().width() + rect5.right;
        Rect rect6 = pipMotionHelper.mFloatingAllowedArea;
        rect6.bottom = pipBoundsState2.getBounds().height() + rect6.bottom;
        if (this.mPipResizeGestureHandler.mUserResizeBounds.isEmpty()) {
            this.mPipResizeGestureHandler.mUserResizeBounds.set(getAdjustedNormalBounds());
        }
    }

    public final boolean willResizeMenu() throws Resources.NotFoundException {
        if (!this.mEnableResize) {
            return false;
        }
        Size estimatedMinMenuSize = this.mMenuController.getEstimatedMinMenuSize();
        if (estimatedMinMenuSize != null) {
            Rect bounds = this.mPipBoundsState.getBounds();
            return bounds.width() < estimatedMinMenuSize.getWidth() || bounds.height() < estimatedMinMenuSize.getHeight();
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[5]) {
            ProtoLogImpl_1771455215.wtf(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 3285037411188224571L, 0, "PipTouchHandler");
        }
        return false;
    }
}
