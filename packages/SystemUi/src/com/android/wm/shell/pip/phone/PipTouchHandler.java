package com.android.wm.shell.pip.phone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.hardware.input.InputManager;
import android.os.SystemProperties;
import android.provider.DeviceConfig;
import android.util.Log;
import android.util.Size;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.wm.shell.animation.PhysicsAnimator;
import com.android.wm.shell.common.FloatingContentCoordinator;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.naturalswitching.NaturalSwitchingDropTargetController;
import com.android.wm.shell.pip.PipBoundsAlgorithm;
import com.android.wm.shell.pip.PipBoundsState;
import com.android.wm.shell.pip.PipSnapAlgorithm;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.PipUiEventLogger;
import com.android.wm.shell.pip.phone.PipSizeSpecHandler;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.wm.shell.sysui.ShellInit;
import com.android.systemui.R;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PipTouchHandler {
    public final AccessibilityManager mAccessibilityManager;
    public int mBottomOffsetBufferPx;
    public final PipAccessibilityInteractionConnection mConnection;
    public final Context mContext;
    public final PipDismissButtonView mDismissButtonView;
    public int mDisplayRotation;
    public boolean mEnableResize;
    public final FloatingContentCoordinator mFloatingContentCoordinator;
    public final DefaultPipTouchGesture mGesture;
    public int mImeHeight;
    public int mImeOffset;
    public boolean mIsImeShowing;
    public boolean mIsShelfShowing;
    public final ShellExecutor mMainExecutor;
    public final PhonePipMenuController mMenuController;
    public PipMotionHelper mMotionHelper;
    public int mMovementBoundsExtraOffsets;
    public boolean mMovementWithinDismiss;
    public final PipBoundsAlgorithm mPipBoundsAlgorithm;
    public final PipBoundsState mPipBoundsState;
    public final PipDismissTargetHandler mPipDismissTargetHandler;
    public final PipNaturalSwitchingHandler mPipNaturalSwitchingHandler;
    public PipResizeGestureHandler mPipResizeGestureHandler;
    public final PipSizeSpecHandler mPipSizeSpecHandler;
    public final PipTaskOrganizer mPipTaskOrganizer;
    public final PipUiEventLogger mPipUiEventLogger;
    public boolean mSendingHoverAccessibilityEvents;
    public int mShelfHeight;
    public float mStashVelocityThreshold;
    public final PipTouchState mTouchState;
    public boolean mEnablePipKeepClearAlgorithm = SystemProperties.getBoolean("persist.wm.debug.enable_pip_keep_clear_algorithm", true);
    public boolean mEnableStash = true;
    public final Rect mInsetBounds = new Rect();
    public int mDeferResizeToNormalBoundsUntilRotation = -1;
    public int mMenuState = 0;
    public float mSavedSnapFraction = -1.0f;
    public final Rect mTmpBounds = new Rect();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DefaultPipTouchGesture extends PipTouchGesture {
        public final PointF mDelta;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class PipMenuListener {
        public /* synthetic */ PipMenuListener(PipTouchHandler pipTouchHandler, int i) {
            this();
        }

        private PipMenuListener() {
        }
    }

    public PipTouchHandler(Context context, ShellInit shellInit, PhonePipMenuController phonePipMenuController, PipBoundsAlgorithm pipBoundsAlgorithm, PipBoundsState pipBoundsState, PipSizeSpecHandler pipSizeSpecHandler, PipTaskOrganizer pipTaskOrganizer, PipMotionHelper pipMotionHelper, FloatingContentCoordinator floatingContentCoordinator, PipUiEventLogger pipUiEventLogger, ShellExecutor shellExecutor, NaturalSwitchingDropTargetController naturalSwitchingDropTargetController) {
        final int i = 1;
        final int i2 = 0;
        this.mContext = context;
        this.mMainExecutor = shellExecutor;
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mPipBoundsAlgorithm = pipBoundsAlgorithm;
        this.mPipBoundsState = pipBoundsState;
        this.mPipSizeSpecHandler = pipSizeSpecHandler;
        this.mPipTaskOrganizer = pipTaskOrganizer;
        this.mMenuController = phonePipMenuController;
        this.mPipUiEventLogger = pipUiEventLogger;
        this.mFloatingContentCoordinator = floatingContentCoordinator;
        PipMenuListener pipMenuListener = new PipMenuListener(this, i2);
        ArrayList arrayList = phonePipMenuController.mListeners;
        if (!arrayList.contains(pipMenuListener)) {
            arrayList.add(pipMenuListener);
        }
        this.mGesture = new DefaultPipTouchGesture(this, i2);
        this.mMotionHelper = pipMotionHelper;
        PipDismissTargetHandler pipDismissTargetHandler = new PipDismissTargetHandler(context, pipUiEventLogger, pipMotionHelper, shellExecutor);
        this.mPipDismissTargetHandler = pipDismissTargetHandler;
        PipTouchState pipTouchState = new PipTouchState(ViewConfiguration.get(context), new Runnable(this) { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda0
            public final /* synthetic */ PipTouchHandler f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                final int i3 = 0;
                switch (i2) {
                    case 0:
                        PipTouchHandler pipTouchHandler = this.f$0;
                        PipBoundsState pipBoundsState2 = pipTouchHandler.mPipBoundsState;
                        if (!pipBoundsState2.isStashed()) {
                            pipTouchHandler.mMenuController.showMenuWithPossibleDelay(pipBoundsState2.getBounds(), pipTouchHandler.willResizeMenu(), pipTouchHandler.mPipTaskOrganizer.shouldShowSplitMenu());
                            break;
                        } else {
                            pipTouchHandler.animateToUnStashedState();
                            pipTouchHandler.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_STASH_UNSTASHED);
                            pipBoundsState2.setStashed(0, false);
                            break;
                        }
                    case 1:
                        this.f$0.updateMovementBounds();
                        break;
                    case 2:
                        this.f$0.updateMovementBounds();
                        break;
                    case 3:
                        this.f$0.animateToUnStashedState();
                        break;
                    case 4:
                        this.f$0.mDismissButtonView.hideDismissTargetMaybe();
                        break;
                    default:
                        final PipTouchHandler pipTouchHandler2 = this.f$0;
                        pipTouchHandler2.mEnableResize = pipTouchHandler2.mContext.getResources().getBoolean(R.bool.config_pipEnableResizeForMenu);
                        pipTouchHandler2.reloadResources();
                        PipMotionHelper pipMotionHelper2 = pipTouchHandler2.mMotionHelper;
                        pipMotionHelper2.mTemporaryBoundsPhysicsAnimator = PhysicsAnimator.getInstance(pipMotionHelper2.mPipBoundsState.mMotionBoundsState.mBoundsInMotion);
                        final PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler2.mPipResizeGestureHandler;
                        Context context2 = pipResizeGestureHandler.mContext;
                        context2.getDisplay().getRealSize(pipResizeGestureHandler.mMaxSize);
                        pipResizeGestureHandler.reloadResources();
                        final int i4 = 1;
                        pipResizeGestureHandler.mEnablePinchResize = DeviceConfig.getBoolean("systemui", "pip_pinch_resize", true);
                        DeviceConfig.addOnPropertiesChangedListener("systemui", pipResizeGestureHandler.mMainExecutor, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipResizeGestureHandler.1
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                if (properties.getKeyset().contains("pip_pinch_resize")) {
                                    PipResizeGestureHandler.this.mEnablePinchResize = properties.getBoolean("pip_pinch_resize", true);
                                }
                            }
                        });
                        pipResizeGestureHandler.mInputManager = (InputManager) context2.getSystemService(InputManager.class);
                        pipTouchHandler2.mPipDismissTargetHandler.getClass();
                        pipTouchHandler2.mEnableStash = DeviceConfig.getBoolean("systemui", "pip_stashing", true);
                        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda4
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                switch (i3) {
                                    case 0:
                                        PipTouchHandler pipTouchHandler3 = pipTouchHandler2;
                                        pipTouchHandler3.getClass();
                                        if (properties.getKeyset().contains("pip_stashing")) {
                                            pipTouchHandler3.mEnableStash = properties.getBoolean("pip_stashing", true);
                                            break;
                                        }
                                        break;
                                    default:
                                        PipTouchHandler pipTouchHandler4 = pipTouchHandler2;
                                        pipTouchHandler4.getClass();
                                        if (properties.getKeyset().contains("pip_velocity_threshold")) {
                                            pipTouchHandler4.mStashVelocityThreshold = properties.getFloat("pip_velocity_threshold", 18000.0f);
                                            break;
                                        }
                                        break;
                                }
                            }
                        };
                        ShellExecutor shellExecutor2 = pipTouchHandler2.mMainExecutor;
                        DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, onPropertiesChangedListener);
                        pipTouchHandler2.mStashVelocityThreshold = DeviceConfig.getFloat("systemui", "pip_velocity_threshold", 18000.0f);
                        DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda4
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                switch (i4) {
                                    case 0:
                                        PipTouchHandler pipTouchHandler3 = pipTouchHandler2;
                                        pipTouchHandler3.getClass();
                                        if (properties.getKeyset().contains("pip_stashing")) {
                                            pipTouchHandler3.mEnableStash = properties.getBoolean("pip_stashing", true);
                                            break;
                                        }
                                        break;
                                    default:
                                        PipTouchHandler pipTouchHandler4 = pipTouchHandler2;
                                        pipTouchHandler4.getClass();
                                        if (properties.getKeyset().contains("pip_velocity_threshold")) {
                                            pipTouchHandler4.mStashVelocityThreshold = properties.getFloat("pip_velocity_threshold", 18000.0f);
                                            break;
                                        }
                                        break;
                                }
                            }
                        });
                        break;
                }
            }
        }, new PipTouchHandler$$ExternalSyntheticLambda1(phonePipMenuController, i2), shellExecutor);
        this.mTouchState = pipTouchState;
        this.mPipResizeGestureHandler = new PipResizeGestureHandler(context, pipBoundsAlgorithm, pipBoundsState, this.mMotionHelper, pipTouchState, pipTaskOrganizer, pipDismissTargetHandler, new Function() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                PipTouchHandler pipTouchHandler = PipTouchHandler.this;
                Rect rect = (Rect) obj;
                pipTouchHandler.getClass();
                Rect rect2 = new Rect();
                int i3 = pipTouchHandler.mIsImeShowing ? pipTouchHandler.mImeHeight : 0;
                pipTouchHandler.mPipBoundsAlgorithm.getClass();
                PipBoundsAlgorithm.getMovementBounds(rect, pipTouchHandler.mInsetBounds, rect2, i3);
                return rect2;
            }
        }, new Runnable(this) { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda0
            public final /* synthetic */ PipTouchHandler f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                final int i3 = 0;
                switch (i) {
                    case 0:
                        PipTouchHandler pipTouchHandler = this.f$0;
                        PipBoundsState pipBoundsState2 = pipTouchHandler.mPipBoundsState;
                        if (!pipBoundsState2.isStashed()) {
                            pipTouchHandler.mMenuController.showMenuWithPossibleDelay(pipBoundsState2.getBounds(), pipTouchHandler.willResizeMenu(), pipTouchHandler.mPipTaskOrganizer.shouldShowSplitMenu());
                            break;
                        } else {
                            pipTouchHandler.animateToUnStashedState();
                            pipTouchHandler.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_STASH_UNSTASHED);
                            pipBoundsState2.setStashed(0, false);
                            break;
                        }
                    case 1:
                        this.f$0.updateMovementBounds();
                        break;
                    case 2:
                        this.f$0.updateMovementBounds();
                        break;
                    case 3:
                        this.f$0.animateToUnStashedState();
                        break;
                    case 4:
                        this.f$0.mDismissButtonView.hideDismissTargetMaybe();
                        break;
                    default:
                        final PipTouchHandler pipTouchHandler2 = this.f$0;
                        pipTouchHandler2.mEnableResize = pipTouchHandler2.mContext.getResources().getBoolean(R.bool.config_pipEnableResizeForMenu);
                        pipTouchHandler2.reloadResources();
                        PipMotionHelper pipMotionHelper2 = pipTouchHandler2.mMotionHelper;
                        pipMotionHelper2.mTemporaryBoundsPhysicsAnimator = PhysicsAnimator.getInstance(pipMotionHelper2.mPipBoundsState.mMotionBoundsState.mBoundsInMotion);
                        final PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler2.mPipResizeGestureHandler;
                        Context context2 = pipResizeGestureHandler.mContext;
                        context2.getDisplay().getRealSize(pipResizeGestureHandler.mMaxSize);
                        pipResizeGestureHandler.reloadResources();
                        final int i4 = 1;
                        pipResizeGestureHandler.mEnablePinchResize = DeviceConfig.getBoolean("systemui", "pip_pinch_resize", true);
                        DeviceConfig.addOnPropertiesChangedListener("systemui", pipResizeGestureHandler.mMainExecutor, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipResizeGestureHandler.1
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                if (properties.getKeyset().contains("pip_pinch_resize")) {
                                    PipResizeGestureHandler.this.mEnablePinchResize = properties.getBoolean("pip_pinch_resize", true);
                                }
                            }
                        });
                        pipResizeGestureHandler.mInputManager = (InputManager) context2.getSystemService(InputManager.class);
                        pipTouchHandler2.mPipDismissTargetHandler.getClass();
                        pipTouchHandler2.mEnableStash = DeviceConfig.getBoolean("systemui", "pip_stashing", true);
                        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda4
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                switch (i3) {
                                    case 0:
                                        PipTouchHandler pipTouchHandler3 = pipTouchHandler2;
                                        pipTouchHandler3.getClass();
                                        if (properties.getKeyset().contains("pip_stashing")) {
                                            pipTouchHandler3.mEnableStash = properties.getBoolean("pip_stashing", true);
                                            break;
                                        }
                                        break;
                                    default:
                                        PipTouchHandler pipTouchHandler4 = pipTouchHandler2;
                                        pipTouchHandler4.getClass();
                                        if (properties.getKeyset().contains("pip_velocity_threshold")) {
                                            pipTouchHandler4.mStashVelocityThreshold = properties.getFloat("pip_velocity_threshold", 18000.0f);
                                            break;
                                        }
                                        break;
                                }
                            }
                        };
                        ShellExecutor shellExecutor2 = pipTouchHandler2.mMainExecutor;
                        DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, onPropertiesChangedListener);
                        pipTouchHandler2.mStashVelocityThreshold = DeviceConfig.getFloat("systemui", "pip_velocity_threshold", 18000.0f);
                        DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda4
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                switch (i4) {
                                    case 0:
                                        PipTouchHandler pipTouchHandler3 = pipTouchHandler2;
                                        pipTouchHandler3.getClass();
                                        if (properties.getKeyset().contains("pip_stashing")) {
                                            pipTouchHandler3.mEnableStash = properties.getBoolean("pip_stashing", true);
                                            break;
                                        }
                                        break;
                                    default:
                                        PipTouchHandler pipTouchHandler4 = pipTouchHandler2;
                                        pipTouchHandler4.getClass();
                                        if (properties.getKeyset().contains("pip_velocity_threshold")) {
                                            pipTouchHandler4.mStashVelocityThreshold = properties.getFloat("pip_velocity_threshold", 18000.0f);
                                            break;
                                        }
                                        break;
                                }
                            }
                        });
                        break;
                }
            }
        }, pipUiEventLogger, phonePipMenuController, shellExecutor);
        PipMotionHelper pipMotionHelper2 = this.mMotionHelper;
        PipSnapAlgorithm pipSnapAlgorithm = pipBoundsAlgorithm.mSnapAlgorithm;
        PipTouchHandler$$ExternalSyntheticLambda3 pipTouchHandler$$ExternalSyntheticLambda3 = new PipTouchHandler$$ExternalSyntheticLambda3(this);
        final int i3 = 2;
        Runnable runnable = new Runnable(this) { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda0
            public final /* synthetic */ PipTouchHandler f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                final int i32 = 0;
                switch (i3) {
                    case 0:
                        PipTouchHandler pipTouchHandler = this.f$0;
                        PipBoundsState pipBoundsState2 = pipTouchHandler.mPipBoundsState;
                        if (!pipBoundsState2.isStashed()) {
                            pipTouchHandler.mMenuController.showMenuWithPossibleDelay(pipBoundsState2.getBounds(), pipTouchHandler.willResizeMenu(), pipTouchHandler.mPipTaskOrganizer.shouldShowSplitMenu());
                            break;
                        } else {
                            pipTouchHandler.animateToUnStashedState();
                            pipTouchHandler.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_STASH_UNSTASHED);
                            pipBoundsState2.setStashed(0, false);
                            break;
                        }
                    case 1:
                        this.f$0.updateMovementBounds();
                        break;
                    case 2:
                        this.f$0.updateMovementBounds();
                        break;
                    case 3:
                        this.f$0.animateToUnStashedState();
                        break;
                    case 4:
                        this.f$0.mDismissButtonView.hideDismissTargetMaybe();
                        break;
                    default:
                        final PipTouchHandler pipTouchHandler2 = this.f$0;
                        pipTouchHandler2.mEnableResize = pipTouchHandler2.mContext.getResources().getBoolean(R.bool.config_pipEnableResizeForMenu);
                        pipTouchHandler2.reloadResources();
                        PipMotionHelper pipMotionHelper22 = pipTouchHandler2.mMotionHelper;
                        pipMotionHelper22.mTemporaryBoundsPhysicsAnimator = PhysicsAnimator.getInstance(pipMotionHelper22.mPipBoundsState.mMotionBoundsState.mBoundsInMotion);
                        final PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler2.mPipResizeGestureHandler;
                        Context context2 = pipResizeGestureHandler.mContext;
                        context2.getDisplay().getRealSize(pipResizeGestureHandler.mMaxSize);
                        pipResizeGestureHandler.reloadResources();
                        final int i4 = 1;
                        pipResizeGestureHandler.mEnablePinchResize = DeviceConfig.getBoolean("systemui", "pip_pinch_resize", true);
                        DeviceConfig.addOnPropertiesChangedListener("systemui", pipResizeGestureHandler.mMainExecutor, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipResizeGestureHandler.1
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                if (properties.getKeyset().contains("pip_pinch_resize")) {
                                    PipResizeGestureHandler.this.mEnablePinchResize = properties.getBoolean("pip_pinch_resize", true);
                                }
                            }
                        });
                        pipResizeGestureHandler.mInputManager = (InputManager) context2.getSystemService(InputManager.class);
                        pipTouchHandler2.mPipDismissTargetHandler.getClass();
                        pipTouchHandler2.mEnableStash = DeviceConfig.getBoolean("systemui", "pip_stashing", true);
                        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda4
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                switch (i32) {
                                    case 0:
                                        PipTouchHandler pipTouchHandler3 = pipTouchHandler2;
                                        pipTouchHandler3.getClass();
                                        if (properties.getKeyset().contains("pip_stashing")) {
                                            pipTouchHandler3.mEnableStash = properties.getBoolean("pip_stashing", true);
                                            break;
                                        }
                                        break;
                                    default:
                                        PipTouchHandler pipTouchHandler4 = pipTouchHandler2;
                                        pipTouchHandler4.getClass();
                                        if (properties.getKeyset().contains("pip_velocity_threshold")) {
                                            pipTouchHandler4.mStashVelocityThreshold = properties.getFloat("pip_velocity_threshold", 18000.0f);
                                            break;
                                        }
                                        break;
                                }
                            }
                        };
                        ShellExecutor shellExecutor2 = pipTouchHandler2.mMainExecutor;
                        DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, onPropertiesChangedListener);
                        pipTouchHandler2.mStashVelocityThreshold = DeviceConfig.getFloat("systemui", "pip_velocity_threshold", 18000.0f);
                        DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda4
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                switch (i4) {
                                    case 0:
                                        PipTouchHandler pipTouchHandler3 = pipTouchHandler2;
                                        pipTouchHandler3.getClass();
                                        if (properties.getKeyset().contains("pip_stashing")) {
                                            pipTouchHandler3.mEnableStash = properties.getBoolean("pip_stashing", true);
                                            break;
                                        }
                                        break;
                                    default:
                                        PipTouchHandler pipTouchHandler4 = pipTouchHandler2;
                                        pipTouchHandler4.getClass();
                                        if (properties.getKeyset().contains("pip_velocity_threshold")) {
                                            pipTouchHandler4.mStashVelocityThreshold = properties.getFloat("pip_velocity_threshold", 18000.0f);
                                            break;
                                        }
                                        break;
                                }
                            }
                        });
                        break;
                }
            }
        };
        final int i4 = 3;
        this.mConnection = new PipAccessibilityInteractionConnection(context, pipBoundsState, pipMotionHelper2, pipTaskOrganizer, pipSnapAlgorithm, pipTouchHandler$$ExternalSyntheticLambda3, runnable, new Runnable(this) { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda0
            public final /* synthetic */ PipTouchHandler f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                final int i32 = 0;
                switch (i4) {
                    case 0:
                        PipTouchHandler pipTouchHandler = this.f$0;
                        PipBoundsState pipBoundsState2 = pipTouchHandler.mPipBoundsState;
                        if (!pipBoundsState2.isStashed()) {
                            pipTouchHandler.mMenuController.showMenuWithPossibleDelay(pipBoundsState2.getBounds(), pipTouchHandler.willResizeMenu(), pipTouchHandler.mPipTaskOrganizer.shouldShowSplitMenu());
                            break;
                        } else {
                            pipTouchHandler.animateToUnStashedState();
                            pipTouchHandler.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_STASH_UNSTASHED);
                            pipBoundsState2.setStashed(0, false);
                            break;
                        }
                    case 1:
                        this.f$0.updateMovementBounds();
                        break;
                    case 2:
                        this.f$0.updateMovementBounds();
                        break;
                    case 3:
                        this.f$0.animateToUnStashedState();
                        break;
                    case 4:
                        this.f$0.mDismissButtonView.hideDismissTargetMaybe();
                        break;
                    default:
                        final PipTouchHandler pipTouchHandler2 = this.f$0;
                        pipTouchHandler2.mEnableResize = pipTouchHandler2.mContext.getResources().getBoolean(R.bool.config_pipEnableResizeForMenu);
                        pipTouchHandler2.reloadResources();
                        PipMotionHelper pipMotionHelper22 = pipTouchHandler2.mMotionHelper;
                        pipMotionHelper22.mTemporaryBoundsPhysicsAnimator = PhysicsAnimator.getInstance(pipMotionHelper22.mPipBoundsState.mMotionBoundsState.mBoundsInMotion);
                        final PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler2.mPipResizeGestureHandler;
                        Context context2 = pipResizeGestureHandler.mContext;
                        context2.getDisplay().getRealSize(pipResizeGestureHandler.mMaxSize);
                        pipResizeGestureHandler.reloadResources();
                        final int i42 = 1;
                        pipResizeGestureHandler.mEnablePinchResize = DeviceConfig.getBoolean("systemui", "pip_pinch_resize", true);
                        DeviceConfig.addOnPropertiesChangedListener("systemui", pipResizeGestureHandler.mMainExecutor, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipResizeGestureHandler.1
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                if (properties.getKeyset().contains("pip_pinch_resize")) {
                                    PipResizeGestureHandler.this.mEnablePinchResize = properties.getBoolean("pip_pinch_resize", true);
                                }
                            }
                        });
                        pipResizeGestureHandler.mInputManager = (InputManager) context2.getSystemService(InputManager.class);
                        pipTouchHandler2.mPipDismissTargetHandler.getClass();
                        pipTouchHandler2.mEnableStash = DeviceConfig.getBoolean("systemui", "pip_stashing", true);
                        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda4
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                switch (i32) {
                                    case 0:
                                        PipTouchHandler pipTouchHandler3 = pipTouchHandler2;
                                        pipTouchHandler3.getClass();
                                        if (properties.getKeyset().contains("pip_stashing")) {
                                            pipTouchHandler3.mEnableStash = properties.getBoolean("pip_stashing", true);
                                            break;
                                        }
                                        break;
                                    default:
                                        PipTouchHandler pipTouchHandler4 = pipTouchHandler2;
                                        pipTouchHandler4.getClass();
                                        if (properties.getKeyset().contains("pip_velocity_threshold")) {
                                            pipTouchHandler4.mStashVelocityThreshold = properties.getFloat("pip_velocity_threshold", 18000.0f);
                                            break;
                                        }
                                        break;
                                }
                            }
                        };
                        ShellExecutor shellExecutor2 = pipTouchHandler2.mMainExecutor;
                        DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, onPropertiesChangedListener);
                        pipTouchHandler2.mStashVelocityThreshold = DeviceConfig.getFloat("systemui", "pip_velocity_threshold", 18000.0f);
                        DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda4
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                switch (i42) {
                                    case 0:
                                        PipTouchHandler pipTouchHandler3 = pipTouchHandler2;
                                        pipTouchHandler3.getClass();
                                        if (properties.getKeyset().contains("pip_stashing")) {
                                            pipTouchHandler3.mEnableStash = properties.getBoolean("pip_stashing", true);
                                            break;
                                        }
                                        break;
                                    default:
                                        PipTouchHandler pipTouchHandler4 = pipTouchHandler2;
                                        pipTouchHandler4.getClass();
                                        if (properties.getKeyset().contains("pip_velocity_threshold")) {
                                            pipTouchHandler4.mStashVelocityThreshold = properties.getFloat("pip_velocity_threshold", 18000.0f);
                                            break;
                                        }
                                        break;
                                }
                            }
                        });
                        break;
                }
            }
        }, shellExecutor);
        this.mDismissButtonView = new PipDismissButtonView(context);
        if (CoreRune.MW_NATURAL_SWITCHING_PIP) {
            final int i5 = 4;
            this.mPipNaturalSwitchingHandler = new PipNaturalSwitchingHandler(context, shellExecutor, pipTaskOrganizer, pipBoundsState, pipTouchState, phonePipMenuController, naturalSwitchingDropTargetController, new Runnable(this) { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda0
                public final /* synthetic */ PipTouchHandler f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    final int i32 = 0;
                    switch (i5) {
                        case 0:
                            PipTouchHandler pipTouchHandler = this.f$0;
                            PipBoundsState pipBoundsState2 = pipTouchHandler.mPipBoundsState;
                            if (!pipBoundsState2.isStashed()) {
                                pipTouchHandler.mMenuController.showMenuWithPossibleDelay(pipBoundsState2.getBounds(), pipTouchHandler.willResizeMenu(), pipTouchHandler.mPipTaskOrganizer.shouldShowSplitMenu());
                                break;
                            } else {
                                pipTouchHandler.animateToUnStashedState();
                                pipTouchHandler.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_STASH_UNSTASHED);
                                pipBoundsState2.setStashed(0, false);
                                break;
                            }
                        case 1:
                            this.f$0.updateMovementBounds();
                            break;
                        case 2:
                            this.f$0.updateMovementBounds();
                            break;
                        case 3:
                            this.f$0.animateToUnStashedState();
                            break;
                        case 4:
                            this.f$0.mDismissButtonView.hideDismissTargetMaybe();
                            break;
                        default:
                            final PipTouchHandler pipTouchHandler2 = this.f$0;
                            pipTouchHandler2.mEnableResize = pipTouchHandler2.mContext.getResources().getBoolean(R.bool.config_pipEnableResizeForMenu);
                            pipTouchHandler2.reloadResources();
                            PipMotionHelper pipMotionHelper22 = pipTouchHandler2.mMotionHelper;
                            pipMotionHelper22.mTemporaryBoundsPhysicsAnimator = PhysicsAnimator.getInstance(pipMotionHelper22.mPipBoundsState.mMotionBoundsState.mBoundsInMotion);
                            final PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler2.mPipResizeGestureHandler;
                            Context context2 = pipResizeGestureHandler.mContext;
                            context2.getDisplay().getRealSize(pipResizeGestureHandler.mMaxSize);
                            pipResizeGestureHandler.reloadResources();
                            final int i42 = 1;
                            pipResizeGestureHandler.mEnablePinchResize = DeviceConfig.getBoolean("systemui", "pip_pinch_resize", true);
                            DeviceConfig.addOnPropertiesChangedListener("systemui", pipResizeGestureHandler.mMainExecutor, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipResizeGestureHandler.1
                                public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                    if (properties.getKeyset().contains("pip_pinch_resize")) {
                                        PipResizeGestureHandler.this.mEnablePinchResize = properties.getBoolean("pip_pinch_resize", true);
                                    }
                                }
                            });
                            pipResizeGestureHandler.mInputManager = (InputManager) context2.getSystemService(InputManager.class);
                            pipTouchHandler2.mPipDismissTargetHandler.getClass();
                            pipTouchHandler2.mEnableStash = DeviceConfig.getBoolean("systemui", "pip_stashing", true);
                            DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda4
                                public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                    switch (i32) {
                                        case 0:
                                            PipTouchHandler pipTouchHandler3 = pipTouchHandler2;
                                            pipTouchHandler3.getClass();
                                            if (properties.getKeyset().contains("pip_stashing")) {
                                                pipTouchHandler3.mEnableStash = properties.getBoolean("pip_stashing", true);
                                                break;
                                            }
                                            break;
                                        default:
                                            PipTouchHandler pipTouchHandler4 = pipTouchHandler2;
                                            pipTouchHandler4.getClass();
                                            if (properties.getKeyset().contains("pip_velocity_threshold")) {
                                                pipTouchHandler4.mStashVelocityThreshold = properties.getFloat("pip_velocity_threshold", 18000.0f);
                                                break;
                                            }
                                            break;
                                    }
                                }
                            };
                            ShellExecutor shellExecutor2 = pipTouchHandler2.mMainExecutor;
                            DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, onPropertiesChangedListener);
                            pipTouchHandler2.mStashVelocityThreshold = DeviceConfig.getFloat("systemui", "pip_velocity_threshold", 18000.0f);
                            DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda4
                                public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                    switch (i42) {
                                        case 0:
                                            PipTouchHandler pipTouchHandler3 = pipTouchHandler2;
                                            pipTouchHandler3.getClass();
                                            if (properties.getKeyset().contains("pip_stashing")) {
                                                pipTouchHandler3.mEnableStash = properties.getBoolean("pip_stashing", true);
                                                break;
                                            }
                                            break;
                                        default:
                                            PipTouchHandler pipTouchHandler4 = pipTouchHandler2;
                                            pipTouchHandler4.getClass();
                                            if (properties.getKeyset().contains("pip_velocity_threshold")) {
                                                pipTouchHandler4.mStashVelocityThreshold = properties.getFloat("pip_velocity_threshold", 18000.0f);
                                                break;
                                            }
                                            break;
                                    }
                                }
                            });
                            break;
                    }
                }
            });
        }
        final int i6 = 5;
        shellInit.addInitCallback(new Runnable(this) { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda0
            public final /* synthetic */ PipTouchHandler f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                final int i32 = 0;
                switch (i6) {
                    case 0:
                        PipTouchHandler pipTouchHandler = this.f$0;
                        PipBoundsState pipBoundsState2 = pipTouchHandler.mPipBoundsState;
                        if (!pipBoundsState2.isStashed()) {
                            pipTouchHandler.mMenuController.showMenuWithPossibleDelay(pipBoundsState2.getBounds(), pipTouchHandler.willResizeMenu(), pipTouchHandler.mPipTaskOrganizer.shouldShowSplitMenu());
                            break;
                        } else {
                            pipTouchHandler.animateToUnStashedState();
                            pipTouchHandler.mPipUiEventLogger.log(PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_STASH_UNSTASHED);
                            pipBoundsState2.setStashed(0, false);
                            break;
                        }
                    case 1:
                        this.f$0.updateMovementBounds();
                        break;
                    case 2:
                        this.f$0.updateMovementBounds();
                        break;
                    case 3:
                        this.f$0.animateToUnStashedState();
                        break;
                    case 4:
                        this.f$0.mDismissButtonView.hideDismissTargetMaybe();
                        break;
                    default:
                        final PipTouchHandler pipTouchHandler2 = this.f$0;
                        pipTouchHandler2.mEnableResize = pipTouchHandler2.mContext.getResources().getBoolean(R.bool.config_pipEnableResizeForMenu);
                        pipTouchHandler2.reloadResources();
                        PipMotionHelper pipMotionHelper22 = pipTouchHandler2.mMotionHelper;
                        pipMotionHelper22.mTemporaryBoundsPhysicsAnimator = PhysicsAnimator.getInstance(pipMotionHelper22.mPipBoundsState.mMotionBoundsState.mBoundsInMotion);
                        final PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler2.mPipResizeGestureHandler;
                        Context context2 = pipResizeGestureHandler.mContext;
                        context2.getDisplay().getRealSize(pipResizeGestureHandler.mMaxSize);
                        pipResizeGestureHandler.reloadResources();
                        final int i42 = 1;
                        pipResizeGestureHandler.mEnablePinchResize = DeviceConfig.getBoolean("systemui", "pip_pinch_resize", true);
                        DeviceConfig.addOnPropertiesChangedListener("systemui", pipResizeGestureHandler.mMainExecutor, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipResizeGestureHandler.1
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                if (properties.getKeyset().contains("pip_pinch_resize")) {
                                    PipResizeGestureHandler.this.mEnablePinchResize = properties.getBoolean("pip_pinch_resize", true);
                                }
                            }
                        });
                        pipResizeGestureHandler.mInputManager = (InputManager) context2.getSystemService(InputManager.class);
                        pipTouchHandler2.mPipDismissTargetHandler.getClass();
                        pipTouchHandler2.mEnableStash = DeviceConfig.getBoolean("systemui", "pip_stashing", true);
                        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda4
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                switch (i32) {
                                    case 0:
                                        PipTouchHandler pipTouchHandler3 = pipTouchHandler2;
                                        pipTouchHandler3.getClass();
                                        if (properties.getKeyset().contains("pip_stashing")) {
                                            pipTouchHandler3.mEnableStash = properties.getBoolean("pip_stashing", true);
                                            break;
                                        }
                                        break;
                                    default:
                                        PipTouchHandler pipTouchHandler4 = pipTouchHandler2;
                                        pipTouchHandler4.getClass();
                                        if (properties.getKeyset().contains("pip_velocity_threshold")) {
                                            pipTouchHandler4.mStashVelocityThreshold = properties.getFloat("pip_velocity_threshold", 18000.0f);
                                            break;
                                        }
                                        break;
                                }
                            }
                        };
                        ShellExecutor shellExecutor2 = pipTouchHandler2.mMainExecutor;
                        DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, onPropertiesChangedListener);
                        pipTouchHandler2.mStashVelocityThreshold = DeviceConfig.getFloat("systemui", "pip_velocity_threshold", 18000.0f);
                        DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda4
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                switch (i42) {
                                    case 0:
                                        PipTouchHandler pipTouchHandler3 = pipTouchHandler2;
                                        pipTouchHandler3.getClass();
                                        if (properties.getKeyset().contains("pip_stashing")) {
                                            pipTouchHandler3.mEnableStash = properties.getBoolean("pip_stashing", true);
                                            break;
                                        }
                                        break;
                                    default:
                                        PipTouchHandler pipTouchHandler4 = pipTouchHandler2;
                                        pipTouchHandler4.getClass();
                                        if (properties.getKeyset().contains("pip_velocity_threshold")) {
                                            pipTouchHandler4.mStashVelocityThreshold = properties.getFloat("pip_velocity_threshold", 18000.0f);
                                            break;
                                        }
                                        break;
                                }
                            }
                        });
                        break;
                }
            }
        }, this);
    }

    public final void animateToNormalSize(Runnable runnable) {
        int height;
        int round;
        PipResizeGestureHandler pipResizeGestureHandler = this.mPipResizeGestureHandler;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        pipResizeGestureHandler.setUserResizeBounds(pipBoundsState.getBounds());
        Size estimatedMinMenuSize = this.mMenuController.getEstimatedMinMenuSize();
        PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
        pipBoundsAlgorithm.getClass();
        Rect rect = pipBoundsState.mNormalBounds;
        if (estimatedMinMenuSize != null && (rect.width() < estimatedMinMenuSize.getWidth() || rect.height() < estimatedMinMenuSize.getHeight())) {
            Rect rect2 = new Rect();
            boolean z = estimatedMinMenuSize.getWidth() > rect.width();
            boolean z2 = estimatedMinMenuSize.getHeight() > rect.height();
            PipBoundsState pipBoundsState2 = pipBoundsAlgorithm.mPipBoundsState;
            if (z && z2) {
                if (estimatedMinMenuSize.getWidth() / rect.width() > estimatedMinMenuSize.getHeight() / rect.height()) {
                    round = estimatedMinMenuSize.getWidth();
                    height = Math.round(round / pipBoundsState2.mAspectRatio);
                } else {
                    height = estimatedMinMenuSize.getHeight();
                    round = Math.round(height * pipBoundsState2.mAspectRatio);
                }
            } else if (z) {
                round = estimatedMinMenuSize.getWidth();
                height = Math.round(round / pipBoundsState2.mAspectRatio);
            } else {
                height = estimatedMinMenuSize.getHeight();
                round = Math.round(height * pipBoundsState2.mAspectRatio);
            }
            rect2.set(0, 0, round, height);
            pipBoundsAlgorithm.transformBoundsToAspectRatio(rect2, pipBoundsState2.mAspectRatio, true, true);
            rect = rect2;
        }
        if (!pipBoundsState.getBounds().isEmpty()) {
            float width = pipBoundsState.getBounds().width() / pipBoundsState.getBounds().height();
            float f = pipBoundsState.mAspectRatio;
            if ((width >= 1.0f && f < 1.0f) || (width < 1.0f && f >= 1.0f)) {
                Log.d("PipTouchHandler", "[PipTaskOrganizer] animateToNormalSize setUserResizeBounds=" + rect + " reason=ratio_change");
                this.mPipResizeGestureHandler.setUserResizeBounds(rect);
            }
        }
        Rect rect3 = new Rect();
        PipBoundsAlgorithm.getMovementBounds(rect, this.mInsetBounds, rect3, this.mIsImeShowing ? this.mImeHeight : 0);
        PipMotionHelper pipMotionHelper = this.mMotionHelper;
        pipMotionHelper.getClass();
        float snapFraction = pipMotionHelper.mSnapAlgorithm.getSnapFraction(0, new Rect(pipMotionHelper.getBounds()), pipBoundsState.mMovementBounds);
        PipSnapAlgorithm.applySnapFraction(snapFraction, rect, rect3);
        pipMotionHelper.mPostPipTransitionCallback = runnable;
        pipMotionHelper.resizeAndAnimatePipUnchecked(rect);
        this.mSavedSnapFraction = snapFraction;
    }

    public final void animateToUnStashedState() {
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        Rect bounds = pipBoundsState.getBounds();
        boolean z = bounds.left < pipBoundsState.getDisplayBounds().left;
        Rect rect = new Rect(0, bounds.top, 0, bounds.bottom);
        Rect rect2 = this.mInsetBounds;
        rect.left = z ? rect2.left : rect2.right - bounds.width();
        rect.right = z ? bounds.width() + rect2.left : rect2.right;
        this.mMotionHelper.resizeAndAnimatePipUnchecked(rect);
    }

    public final void animateToUnexpandedState(Rect rect) {
        Rect rect2 = new Rect();
        int i = this.mIsImeShowing ? this.mImeHeight : 0;
        this.mPipBoundsAlgorithm.getClass();
        PipBoundsAlgorithm.getMovementBounds(rect, this.mInsetBounds, rect2, i);
        this.mMotionHelper.animateToUnexpandedState(rect, this.mSavedSnapFraction, rect2, this.mPipBoundsState.mMovementBounds, false);
        this.mSavedSnapFraction = -1.0f;
    }

    public PipResizeGestureHandler getPipResizeGestureHandler() {
        return this.mPipResizeGestureHandler;
    }

    public final Rect getPossiblyMotionBounds() {
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        return pipBoundsState.mMotionBoundsState.mBoundsInMotion.isEmpty() ^ true ? pipBoundsState.mMotionBoundsState.mBoundsInMotion : pipBoundsState.getBounds();
    }

    public final void onRegistrationChanged(boolean z) {
        AccessibilityManager accessibilityManager = this.mAccessibilityManager;
        if (z) {
            accessibilityManager.setPictureInPictureActionReplacingConnection(this.mConnection.mConnectionImpl);
        } else {
            accessibilityManager.setPictureInPictureActionReplacingConnection(null);
        }
        if (z || !this.mTouchState.mIsUserInteracting) {
            return;
        }
        this.mPipDismissTargetHandler.getClass();
    }

    public final void reloadResources() {
        Resources resources = this.mContext.getResources();
        this.mBottomOffsetBufferPx = resources.getDimensionPixelSize(R.dimen.pip_bottom_offset_buffer);
        this.mImeOffset = resources.getDimensionPixelSize(R.dimen.pip_ime_offset);
        this.mPipDismissTargetHandler.getClass();
    }

    public final void sendAccessibilityHoverEvent(int i) {
        AccessibilityManager accessibilityManager = this.mAccessibilityManager;
        if (accessibilityManager.isEnabled()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain(i);
            obtain.setImportantForAccessibility(true);
            obtain.setSourceNodeId(AccessibilityNodeInfo.ROOT_NODE_ID);
            obtain.setWindowId(-3);
            accessibilityManager.sendAccessibilityEvent(obtain);
        }
    }

    public void setEnablePipKeepClearAlgorithm(boolean z) {
        this.mEnablePipKeepClearAlgorithm = z;
    }

    public void setPipMotionHelper(PipMotionHelper pipMotionHelper) {
        this.mMotionHelper = pipMotionHelper;
    }

    public void setPipResizeGestureHandler(PipResizeGestureHandler pipResizeGestureHandler) {
        this.mPipResizeGestureHandler = pipResizeGestureHandler;
    }

    public final void updateMovementBounds() {
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        Rect bounds = pipBoundsState.getBounds();
        int i = this.mIsImeShowing ? this.mImeHeight : 0;
        this.mPipBoundsAlgorithm.getClass();
        PipBoundsAlgorithm.getMovementBounds(bounds, this.mInsetBounds, pipBoundsState.mMovementBounds, i);
        PipMotionHelper pipMotionHelper = this.mMotionHelper;
        PipBoundsState pipBoundsState2 = pipMotionHelper.mPipBoundsState;
        Rect rect = pipBoundsState2.mMovementBounds;
        pipMotionHelper.mFlingConfigX = new PhysicsAnimator.FlingConfig(1.9f, rect.left, rect.right);
        Rect rect2 = pipBoundsState2.mMovementBounds;
        pipMotionHelper.mFlingConfigY = new PhysicsAnimator.FlingConfig(1.9f, rect2.top, rect2.bottom);
        pipBoundsState2.getDisplayLayout().stableInsets(false);
        pipMotionHelper.mStashConfigX = new PhysicsAnimator.FlingConfig(1.9f, (pipBoundsState2.mStashOffset - pipBoundsState2.getBounds().width()) + pipBoundsState2.getStashInsets().left, (pipBoundsState2.getDisplayBounds().right - pipBoundsState2.mStashOffset) - pipBoundsState2.getStashInsets().right);
        Rect rect3 = pipMotionHelper.mFloatingAllowedArea;
        rect3.set(rect2);
        rect3.right = pipMotionHelper.getBounds().width() + rect3.right;
        rect3.bottom = pipMotionHelper.getBounds().height() + rect3.bottom;
    }

    public final void updatePinchResizeSizeConstraints(float f) {
        PipSizeSpecHandler pipSizeSpecHandler = this.mPipSizeSpecHandler;
        int width = pipSizeSpecHandler.mSizeSpecSourceImpl.getMinSize(f).getWidth();
        PipSizeSpecHandler.SizeSpecSource sizeSpecSource = pipSizeSpecHandler.mSizeSpecSourceImpl;
        int height = sizeSpecSource.getMinSize(f).getHeight();
        int width2 = sizeSpecSource.getMaxSize(f).getWidth();
        int height2 = sizeSpecSource.getMaxSize(f).getHeight();
        this.mPipResizeGestureHandler.updateMinSize(width, height);
        this.mPipResizeGestureHandler.updateMaxSize(width2, height2);
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        pipBoundsState.mMaxSize.set(width2, height2);
        pipBoundsState.mMinSize.set(width, height);
    }

    public final void updatePipSizeConstraints(Rect rect, float f) {
        PipResizeGestureHandler pipResizeGestureHandler = this.mPipResizeGestureHandler;
        if (pipResizeGestureHandler.mEnablePinchResize) {
            updatePinchResizeSizeConstraints(f);
            return;
        }
        pipResizeGestureHandler.updateMinSize(rect.width(), rect.height());
        PipResizeGestureHandler pipResizeGestureHandler2 = this.mPipResizeGestureHandler;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        pipResizeGestureHandler2.updateMaxSize(pipBoundsState.mExpandedBounds.width(), pipBoundsState.mExpandedBounds.height());
    }

    public final boolean willResizeMenu() {
        if (!this.mEnableResize) {
            return false;
        }
        Size estimatedMinMenuSize = this.mMenuController.getEstimatedMinMenuSize();
        if (estimatedMinMenuSize != null) {
            Rect bounds = this.mPipBoundsState.getBounds();
            return estimatedMinMenuSize.getWidth() - bounds.width() > 2 || estimatedMinMenuSize.getHeight() - bounds.height() > 2;
        }
        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
            ShellProtoLogImpl.wtf(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -520008159, 0, "%s: Failed to get estimated menu size", "PipTouchHandler");
        }
        return false;
    }
}
