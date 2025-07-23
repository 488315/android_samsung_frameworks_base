package com.android.wm.shell.pip.phone;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.provider.DeviceConfig;
import android.util.Log;
import android.util.Size;
import android.view.ViewConfiguration;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.R;
import com.android.wm.shell.common.FloatingContentCoordinator;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipPerfHintController;
import com.android.wm.shell.common.pip.PipSnapAlgorithm;
import com.android.wm.shell.common.pip.PipUiEventLogger;
import com.android.wm.shell.common.pip.PipUtils;
import com.android.wm.shell.common.pip.SizeSpecSource;
import com.android.wm.shell.naturalswitching.NaturalSwitchingDropTargetController;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.animation.PhysicsAnimator;
import com.android.wm.shell.sysui.ShellInit;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipTouchHandler {
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
    public final PipPerfHintController mPipPerfHintController;
    public PipResizeGestureHandler mPipResizeGestureHandler;
    public final PipTaskOrganizer mPipTaskOrganizer;
    public final PipUiEventLogger mPipUiEventLogger;
    public boolean mSendingHoverAccessibilityEvents;
    public int mShelfHeight;
    public final SizeSpecSource mSizeSpecSource;
    public float mStashVelocityThreshold;
    public final PipTouchState mTouchState;
    public boolean mEnableStash = true;
    public final Rect mInsetBounds = new Rect();
    public int mDeferResizeToNormalBoundsUntilRotation = -1;
    public int mMenuState = 0;
    public float mSavedSnapFraction = -1.0f;
    public final Rect mTmpBounds = new Rect();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class PipMenuListener {
        public /* synthetic */ PipMenuListener(PipTouchHandler pipTouchHandler, int i) {
            this();
        }

        private PipMenuListener() {
        }
    }

    public PipTouchHandler(Context context, ShellInit shellInit, PhonePipMenuController phonePipMenuController, PipBoundsAlgorithm pipBoundsAlgorithm, PipBoundsState pipBoundsState, SizeSpecSource sizeSpecSource, PipTaskOrganizer pipTaskOrganizer, PipMotionHelper pipMotionHelper, FloatingContentCoordinator floatingContentCoordinator, PipUiEventLogger pipUiEventLogger, ShellExecutor shellExecutor, Optional<PipPerfHintController> optional, NaturalSwitchingDropTargetController naturalSwitchingDropTargetController) {
        int i = 0;
        this.mContext = context;
        this.mMainExecutor = shellExecutor;
        PipPerfHintController orElse = optional.orElse(null);
        this.mPipPerfHintController = orElse;
        this.mAccessibilityManager = (AccessibilityManager) context.getSystemService(AccessibilityManager.class);
        this.mPipBoundsAlgorithm = pipBoundsAlgorithm;
        this.mPipBoundsState = pipBoundsState;
        this.mSizeSpecSource = sizeSpecSource;
        this.mPipTaskOrganizer = pipTaskOrganizer;
        this.mMenuController = phonePipMenuController;
        this.mPipUiEventLogger = pipUiEventLogger;
        this.mFloatingContentCoordinator = floatingContentCoordinator;
        PipMenuListener pipMenuListener = new PipMenuListener(this, i);
        if (!phonePipMenuController.mListeners.contains(pipMenuListener)) {
            phonePipMenuController.mListeners.add(pipMenuListener);
        }
        this.mGesture = new DefaultPipTouchGesture(this, i);
        this.mMotionHelper = pipMotionHelper;
        PipDismissTargetHandler pipDismissTargetHandler = new PipDismissTargetHandler(context, pipUiEventLogger, pipMotionHelper, shellExecutor);
        this.mPipDismissTargetHandler = pipDismissTargetHandler;
        final int i2 = 0;
        PipTouchState pipTouchState = new PipTouchState(ViewConfiguration.get(context), new Runnable(this) { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda0
            public final /* synthetic */ PipTouchHandler f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i3 = i2;
                final PipTouchHandler pipTouchHandler = this.f$0;
                switch (i3) {
                    case 0:
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
                        pipTouchHandler.updateMovementBounds();
                        break;
                    case 2:
                        pipTouchHandler.animateToUnStashedState();
                        break;
                    case 3:
                        pipTouchHandler.mDismissButtonView.hideDismissTargetMaybe();
                        break;
                    default:
                        pipTouchHandler.mEnableResize = pipTouchHandler.mContext.getResources().getBoolean(R.bool.config_pipEnableResizeForMenu);
                        Resources resources = pipTouchHandler.mContext.getResources();
                        pipTouchHandler.mBottomOffsetBufferPx = resources.getDimensionPixelSize(R.dimen.pip_bottom_offset_buffer);
                        pipTouchHandler.mImeOffset = resources.getDimensionPixelSize(R.dimen.pip_ime_offset);
                        PipDismissTargetHandler pipDismissTargetHandler2 = pipTouchHandler.mPipDismissTargetHandler;
                        pipDismissTargetHandler2.getClass();
                        PipMotionHelper pipMotionHelper2 = pipTouchHandler.mMotionHelper;
                        Rect rect = pipMotionHelper2.mPipBoundsState.mMotionBoundsState.mBoundsInMotion;
                        PhysicsAnimator.Companion.getClass();
                        pipMotionHelper2.mTemporaryBoundsPhysicsAnimator = PhysicsAnimator.Companion.getInstance(rect);
                        PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler.mPipResizeGestureHandler;
                        pipResizeGestureHandler.mContext.getDisplay().getRealSize(pipResizeGestureHandler.mMaxSize);
                        pipResizeGestureHandler.reloadResources();
                        pipResizeGestureHandler.mEnablePinchResize = pipResizeGestureHandler.mContext.getResources().getBoolean(R.bool.config_pipEnablePinchResize);
                        pipDismissTargetHandler2.getClass();
                        pipTouchHandler.mEnableStash = DeviceConfig.getBoolean("systemui", "pip_stashing", true);
                        final int i4 = 0;
                        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda9
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                int i5 = i4;
                                PipTouchHandler pipTouchHandler2 = pipTouchHandler;
                                pipTouchHandler2.getClass();
                                switch (i5) {
                                    case 0:
                                        if (properties.getKeyset().contains("pip_stashing")) {
                                            pipTouchHandler2.mEnableStash = properties.getBoolean("pip_stashing", true);
                                            break;
                                        }
                                        break;
                                    default:
                                        if (properties.getKeyset().contains("pip_velocity_threshold")) {
                                            pipTouchHandler2.mStashVelocityThreshold = properties.getFloat("pip_velocity_threshold", 18000.0f);
                                            break;
                                        }
                                        break;
                                }
                            }
                        };
                        ShellExecutor shellExecutor2 = pipTouchHandler.mMainExecutor;
                        DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, onPropertiesChangedListener);
                        pipTouchHandler.mStashVelocityThreshold = DeviceConfig.getFloat("systemui", "pip_velocity_threshold", 18000.0f);
                        final int i5 = 1;
                        DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda9
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                int i52 = i5;
                                PipTouchHandler pipTouchHandler2 = pipTouchHandler;
                                pipTouchHandler2.getClass();
                                switch (i52) {
                                    case 0:
                                        if (properties.getKeyset().contains("pip_stashing")) {
                                            pipTouchHandler2.mEnableStash = properties.getBoolean("pip_stashing", true);
                                            break;
                                        }
                                        break;
                                    default:
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
        }, new PipTouchHandler$$ExternalSyntheticLambda1(phonePipMenuController, 0), shellExecutor);
        this.mTouchState = pipTouchState;
        final int i3 = 1;
        this.mPipResizeGestureHandler = new PipResizeGestureHandler(context, pipBoundsAlgorithm, pipBoundsState, this.mMotionHelper, pipTouchState, pipTaskOrganizer, pipDismissTargetHandler, new Function() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                PipTouchHandler pipTouchHandler = PipTouchHandler.this;
                Rect rect = (Rect) obj;
                Rect rect2 = new Rect();
                Rect rect3 = pipTouchHandler.mInsetBounds;
                int i4 = pipTouchHandler.mIsImeShowing ? pipTouchHandler.mImeHeight : 0;
                pipTouchHandler.mPipBoundsAlgorithm.getClass();
                PipBoundsAlgorithm.getMovementBounds(rect, rect3, rect2, i4);
                return rect2;
            }
        }, new Runnable(this) { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda0
            public final /* synthetic */ PipTouchHandler f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i32 = i3;
                final PipTouchHandler pipTouchHandler = this.f$0;
                switch (i32) {
                    case 0:
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
                        pipTouchHandler.updateMovementBounds();
                        break;
                    case 2:
                        pipTouchHandler.animateToUnStashedState();
                        break;
                    case 3:
                        pipTouchHandler.mDismissButtonView.hideDismissTargetMaybe();
                        break;
                    default:
                        pipTouchHandler.mEnableResize = pipTouchHandler.mContext.getResources().getBoolean(R.bool.config_pipEnableResizeForMenu);
                        Resources resources = pipTouchHandler.mContext.getResources();
                        pipTouchHandler.mBottomOffsetBufferPx = resources.getDimensionPixelSize(R.dimen.pip_bottom_offset_buffer);
                        pipTouchHandler.mImeOffset = resources.getDimensionPixelSize(R.dimen.pip_ime_offset);
                        PipDismissTargetHandler pipDismissTargetHandler2 = pipTouchHandler.mPipDismissTargetHandler;
                        pipDismissTargetHandler2.getClass();
                        PipMotionHelper pipMotionHelper2 = pipTouchHandler.mMotionHelper;
                        Rect rect = pipMotionHelper2.mPipBoundsState.mMotionBoundsState.mBoundsInMotion;
                        PhysicsAnimator.Companion.getClass();
                        pipMotionHelper2.mTemporaryBoundsPhysicsAnimator = PhysicsAnimator.Companion.getInstance(rect);
                        PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler.mPipResizeGestureHandler;
                        pipResizeGestureHandler.mContext.getDisplay().getRealSize(pipResizeGestureHandler.mMaxSize);
                        pipResizeGestureHandler.reloadResources();
                        pipResizeGestureHandler.mEnablePinchResize = pipResizeGestureHandler.mContext.getResources().getBoolean(R.bool.config_pipEnablePinchResize);
                        pipDismissTargetHandler2.getClass();
                        pipTouchHandler.mEnableStash = DeviceConfig.getBoolean("systemui", "pip_stashing", true);
                        final int i4 = 0;
                        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda9
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                int i52 = i4;
                                PipTouchHandler pipTouchHandler2 = pipTouchHandler;
                                pipTouchHandler2.getClass();
                                switch (i52) {
                                    case 0:
                                        if (properties.getKeyset().contains("pip_stashing")) {
                                            pipTouchHandler2.mEnableStash = properties.getBoolean("pip_stashing", true);
                                            break;
                                        }
                                        break;
                                    default:
                                        if (properties.getKeyset().contains("pip_velocity_threshold")) {
                                            pipTouchHandler2.mStashVelocityThreshold = properties.getFloat("pip_velocity_threshold", 18000.0f);
                                            break;
                                        }
                                        break;
                                }
                            }
                        };
                        ShellExecutor shellExecutor2 = pipTouchHandler.mMainExecutor;
                        DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, onPropertiesChangedListener);
                        pipTouchHandler.mStashVelocityThreshold = DeviceConfig.getFloat("systemui", "pip_velocity_threshold", 18000.0f);
                        final int i5 = 1;
                        DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda9
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                int i52 = i5;
                                PipTouchHandler pipTouchHandler2 = pipTouchHandler;
                                pipTouchHandler2.getClass();
                                switch (i52) {
                                    case 0:
                                        if (properties.getKeyset().contains("pip_stashing")) {
                                            pipTouchHandler2.mEnableStash = properties.getBoolean("pip_stashing", true);
                                            break;
                                        }
                                        break;
                                    default:
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
        }, pipUiEventLogger, phonePipMenuController, shellExecutor, orElse);
        PipMotionHelper pipMotionHelper2 = this.mMotionHelper;
        PipSnapAlgorithm pipSnapAlgorithm = pipBoundsAlgorithm.mSnapAlgorithm;
        PipTouchHandler$$ExternalSyntheticLambda4 pipTouchHandler$$ExternalSyntheticLambda4 = new PipTouchHandler$$ExternalSyntheticLambda4(this);
        final int i4 = 1;
        Runnable runnable = new Runnable(this) { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda0
            public final /* synthetic */ PipTouchHandler f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i32 = i4;
                final PipTouchHandler pipTouchHandler = this.f$0;
                switch (i32) {
                    case 0:
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
                        pipTouchHandler.updateMovementBounds();
                        break;
                    case 2:
                        pipTouchHandler.animateToUnStashedState();
                        break;
                    case 3:
                        pipTouchHandler.mDismissButtonView.hideDismissTargetMaybe();
                        break;
                    default:
                        pipTouchHandler.mEnableResize = pipTouchHandler.mContext.getResources().getBoolean(R.bool.config_pipEnableResizeForMenu);
                        Resources resources = pipTouchHandler.mContext.getResources();
                        pipTouchHandler.mBottomOffsetBufferPx = resources.getDimensionPixelSize(R.dimen.pip_bottom_offset_buffer);
                        pipTouchHandler.mImeOffset = resources.getDimensionPixelSize(R.dimen.pip_ime_offset);
                        PipDismissTargetHandler pipDismissTargetHandler2 = pipTouchHandler.mPipDismissTargetHandler;
                        pipDismissTargetHandler2.getClass();
                        PipMotionHelper pipMotionHelper22 = pipTouchHandler.mMotionHelper;
                        Rect rect = pipMotionHelper22.mPipBoundsState.mMotionBoundsState.mBoundsInMotion;
                        PhysicsAnimator.Companion.getClass();
                        pipMotionHelper22.mTemporaryBoundsPhysicsAnimator = PhysicsAnimator.Companion.getInstance(rect);
                        PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler.mPipResizeGestureHandler;
                        pipResizeGestureHandler.mContext.getDisplay().getRealSize(pipResizeGestureHandler.mMaxSize);
                        pipResizeGestureHandler.reloadResources();
                        pipResizeGestureHandler.mEnablePinchResize = pipResizeGestureHandler.mContext.getResources().getBoolean(R.bool.config_pipEnablePinchResize);
                        pipDismissTargetHandler2.getClass();
                        pipTouchHandler.mEnableStash = DeviceConfig.getBoolean("systemui", "pip_stashing", true);
                        final int i42 = 0;
                        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda9
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                int i52 = i42;
                                PipTouchHandler pipTouchHandler2 = pipTouchHandler;
                                pipTouchHandler2.getClass();
                                switch (i52) {
                                    case 0:
                                        if (properties.getKeyset().contains("pip_stashing")) {
                                            pipTouchHandler2.mEnableStash = properties.getBoolean("pip_stashing", true);
                                            break;
                                        }
                                        break;
                                    default:
                                        if (properties.getKeyset().contains("pip_velocity_threshold")) {
                                            pipTouchHandler2.mStashVelocityThreshold = properties.getFloat("pip_velocity_threshold", 18000.0f);
                                            break;
                                        }
                                        break;
                                }
                            }
                        };
                        ShellExecutor shellExecutor2 = pipTouchHandler.mMainExecutor;
                        DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, onPropertiesChangedListener);
                        pipTouchHandler.mStashVelocityThreshold = DeviceConfig.getFloat("systemui", "pip_velocity_threshold", 18000.0f);
                        final int i5 = 1;
                        DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda9
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                int i52 = i5;
                                PipTouchHandler pipTouchHandler2 = pipTouchHandler;
                                pipTouchHandler2.getClass();
                                switch (i52) {
                                    case 0:
                                        if (properties.getKeyset().contains("pip_stashing")) {
                                            pipTouchHandler2.mEnableStash = properties.getBoolean("pip_stashing", true);
                                            break;
                                        }
                                        break;
                                    default:
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
        };
        final int i5 = 2;
        this.mConnection = new PipAccessibilityInteractionConnection(context, pipBoundsState, pipMotionHelper2, pipTaskOrganizer, pipSnapAlgorithm, pipTouchHandler$$ExternalSyntheticLambda4, runnable, new Runnable(this) { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda0
            public final /* synthetic */ PipTouchHandler f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i32 = i5;
                final PipTouchHandler pipTouchHandler = this.f$0;
                switch (i32) {
                    case 0:
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
                        pipTouchHandler.updateMovementBounds();
                        break;
                    case 2:
                        pipTouchHandler.animateToUnStashedState();
                        break;
                    case 3:
                        pipTouchHandler.mDismissButtonView.hideDismissTargetMaybe();
                        break;
                    default:
                        pipTouchHandler.mEnableResize = pipTouchHandler.mContext.getResources().getBoolean(R.bool.config_pipEnableResizeForMenu);
                        Resources resources = pipTouchHandler.mContext.getResources();
                        pipTouchHandler.mBottomOffsetBufferPx = resources.getDimensionPixelSize(R.dimen.pip_bottom_offset_buffer);
                        pipTouchHandler.mImeOffset = resources.getDimensionPixelSize(R.dimen.pip_ime_offset);
                        PipDismissTargetHandler pipDismissTargetHandler2 = pipTouchHandler.mPipDismissTargetHandler;
                        pipDismissTargetHandler2.getClass();
                        PipMotionHelper pipMotionHelper22 = pipTouchHandler.mMotionHelper;
                        Rect rect = pipMotionHelper22.mPipBoundsState.mMotionBoundsState.mBoundsInMotion;
                        PhysicsAnimator.Companion.getClass();
                        pipMotionHelper22.mTemporaryBoundsPhysicsAnimator = PhysicsAnimator.Companion.getInstance(rect);
                        PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler.mPipResizeGestureHandler;
                        pipResizeGestureHandler.mContext.getDisplay().getRealSize(pipResizeGestureHandler.mMaxSize);
                        pipResizeGestureHandler.reloadResources();
                        pipResizeGestureHandler.mEnablePinchResize = pipResizeGestureHandler.mContext.getResources().getBoolean(R.bool.config_pipEnablePinchResize);
                        pipDismissTargetHandler2.getClass();
                        pipTouchHandler.mEnableStash = DeviceConfig.getBoolean("systemui", "pip_stashing", true);
                        final int i42 = 0;
                        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda9
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                int i52 = i42;
                                PipTouchHandler pipTouchHandler2 = pipTouchHandler;
                                pipTouchHandler2.getClass();
                                switch (i52) {
                                    case 0:
                                        if (properties.getKeyset().contains("pip_stashing")) {
                                            pipTouchHandler2.mEnableStash = properties.getBoolean("pip_stashing", true);
                                            break;
                                        }
                                        break;
                                    default:
                                        if (properties.getKeyset().contains("pip_velocity_threshold")) {
                                            pipTouchHandler2.mStashVelocityThreshold = properties.getFloat("pip_velocity_threshold", 18000.0f);
                                            break;
                                        }
                                        break;
                                }
                            }
                        };
                        ShellExecutor shellExecutor2 = pipTouchHandler.mMainExecutor;
                        DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, onPropertiesChangedListener);
                        pipTouchHandler.mStashVelocityThreshold = DeviceConfig.getFloat("systemui", "pip_velocity_threshold", 18000.0f);
                        final int i52 = 1;
                        DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda9
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                int i522 = i52;
                                PipTouchHandler pipTouchHandler2 = pipTouchHandler;
                                pipTouchHandler2.getClass();
                                switch (i522) {
                                    case 0:
                                        if (properties.getKeyset().contains("pip_stashing")) {
                                            pipTouchHandler2.mEnableStash = properties.getBoolean("pip_stashing", true);
                                            break;
                                        }
                                        break;
                                    default:
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
        }, shellExecutor);
        PipTouchHandler$$ExternalSyntheticLambda6 pipTouchHandler$$ExternalSyntheticLambda6 = new PipTouchHandler$$ExternalSyntheticLambda6(this, 0);
        if (!((ArrayList) pipBoundsState.mOnAspectRatioChangedCallbacks).contains(pipTouchHandler$$ExternalSyntheticLambda6)) {
            ((ArrayList) pipBoundsState.mOnAspectRatioChangedCallbacks).add(pipTouchHandler$$ExternalSyntheticLambda6);
            pipTouchHandler$$ExternalSyntheticLambda6.accept(Float.valueOf(pipBoundsState.mAspectRatio));
        }
        this.mDismissButtonView = new PipDismissButtonView(context);
        if (CoreRune.MW_NATURAL_SWITCHING_PIP) {
            final int i6 = 3;
            this.mPipNaturalSwitchingHandler = new PipNaturalSwitchingHandler(context, shellExecutor, pipTaskOrganizer, pipBoundsState, pipTouchState, phonePipMenuController, naturalSwitchingDropTargetController, new Runnable(this) { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda0
                public final /* synthetic */ PipTouchHandler f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    int i32 = i6;
                    final PipTouchHandler pipTouchHandler = this.f$0;
                    switch (i32) {
                        case 0:
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
                            pipTouchHandler.updateMovementBounds();
                            break;
                        case 2:
                            pipTouchHandler.animateToUnStashedState();
                            break;
                        case 3:
                            pipTouchHandler.mDismissButtonView.hideDismissTargetMaybe();
                            break;
                        default:
                            pipTouchHandler.mEnableResize = pipTouchHandler.mContext.getResources().getBoolean(R.bool.config_pipEnableResizeForMenu);
                            Resources resources = pipTouchHandler.mContext.getResources();
                            pipTouchHandler.mBottomOffsetBufferPx = resources.getDimensionPixelSize(R.dimen.pip_bottom_offset_buffer);
                            pipTouchHandler.mImeOffset = resources.getDimensionPixelSize(R.dimen.pip_ime_offset);
                            PipDismissTargetHandler pipDismissTargetHandler2 = pipTouchHandler.mPipDismissTargetHandler;
                            pipDismissTargetHandler2.getClass();
                            PipMotionHelper pipMotionHelper22 = pipTouchHandler.mMotionHelper;
                            Rect rect = pipMotionHelper22.mPipBoundsState.mMotionBoundsState.mBoundsInMotion;
                            PhysicsAnimator.Companion.getClass();
                            pipMotionHelper22.mTemporaryBoundsPhysicsAnimator = PhysicsAnimator.Companion.getInstance(rect);
                            PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler.mPipResizeGestureHandler;
                            pipResizeGestureHandler.mContext.getDisplay().getRealSize(pipResizeGestureHandler.mMaxSize);
                            pipResizeGestureHandler.reloadResources();
                            pipResizeGestureHandler.mEnablePinchResize = pipResizeGestureHandler.mContext.getResources().getBoolean(R.bool.config_pipEnablePinchResize);
                            pipDismissTargetHandler2.getClass();
                            pipTouchHandler.mEnableStash = DeviceConfig.getBoolean("systemui", "pip_stashing", true);
                            final int i42 = 0;
                            DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda9
                                public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                    int i522 = i42;
                                    PipTouchHandler pipTouchHandler2 = pipTouchHandler;
                                    pipTouchHandler2.getClass();
                                    switch (i522) {
                                        case 0:
                                            if (properties.getKeyset().contains("pip_stashing")) {
                                                pipTouchHandler2.mEnableStash = properties.getBoolean("pip_stashing", true);
                                                break;
                                            }
                                            break;
                                        default:
                                            if (properties.getKeyset().contains("pip_velocity_threshold")) {
                                                pipTouchHandler2.mStashVelocityThreshold = properties.getFloat("pip_velocity_threshold", 18000.0f);
                                                break;
                                            }
                                            break;
                                    }
                                }
                            };
                            ShellExecutor shellExecutor2 = pipTouchHandler.mMainExecutor;
                            DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, onPropertiesChangedListener);
                            pipTouchHandler.mStashVelocityThreshold = DeviceConfig.getFloat("systemui", "pip_velocity_threshold", 18000.0f);
                            final int i52 = 1;
                            DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda9
                                public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                    int i522 = i52;
                                    PipTouchHandler pipTouchHandler2 = pipTouchHandler;
                                    pipTouchHandler2.getClass();
                                    switch (i522) {
                                        case 0:
                                            if (properties.getKeyset().contains("pip_stashing")) {
                                                pipTouchHandler2.mEnableStash = properties.getBoolean("pip_stashing", true);
                                                break;
                                            }
                                            break;
                                        default:
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
            });
        }
        if (PipUtils.isPip2ExperimentEnabled()) {
            return;
        }
        final int i7 = 4;
        shellInit.addInitCallback(new Runnable(this) { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda0
            public final /* synthetic */ PipTouchHandler f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                int i32 = i7;
                final PipTouchHandler pipTouchHandler = this.f$0;
                switch (i32) {
                    case 0:
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
                        pipTouchHandler.updateMovementBounds();
                        break;
                    case 2:
                        pipTouchHandler.animateToUnStashedState();
                        break;
                    case 3:
                        pipTouchHandler.mDismissButtonView.hideDismissTargetMaybe();
                        break;
                    default:
                        pipTouchHandler.mEnableResize = pipTouchHandler.mContext.getResources().getBoolean(R.bool.config_pipEnableResizeForMenu);
                        Resources resources = pipTouchHandler.mContext.getResources();
                        pipTouchHandler.mBottomOffsetBufferPx = resources.getDimensionPixelSize(R.dimen.pip_bottom_offset_buffer);
                        pipTouchHandler.mImeOffset = resources.getDimensionPixelSize(R.dimen.pip_ime_offset);
                        PipDismissTargetHandler pipDismissTargetHandler2 = pipTouchHandler.mPipDismissTargetHandler;
                        pipDismissTargetHandler2.getClass();
                        PipMotionHelper pipMotionHelper22 = pipTouchHandler.mMotionHelper;
                        Rect rect = pipMotionHelper22.mPipBoundsState.mMotionBoundsState.mBoundsInMotion;
                        PhysicsAnimator.Companion.getClass();
                        pipMotionHelper22.mTemporaryBoundsPhysicsAnimator = PhysicsAnimator.Companion.getInstance(rect);
                        PipResizeGestureHandler pipResizeGestureHandler = pipTouchHandler.mPipResizeGestureHandler;
                        pipResizeGestureHandler.mContext.getDisplay().getRealSize(pipResizeGestureHandler.mMaxSize);
                        pipResizeGestureHandler.reloadResources();
                        pipResizeGestureHandler.mEnablePinchResize = pipResizeGestureHandler.mContext.getResources().getBoolean(R.bool.config_pipEnablePinchResize);
                        pipDismissTargetHandler2.getClass();
                        pipTouchHandler.mEnableStash = DeviceConfig.getBoolean("systemui", "pip_stashing", true);
                        final int i42 = 0;
                        DeviceConfig.OnPropertiesChangedListener onPropertiesChangedListener = new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda9
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                int i522 = i42;
                                PipTouchHandler pipTouchHandler2 = pipTouchHandler;
                                pipTouchHandler2.getClass();
                                switch (i522) {
                                    case 0:
                                        if (properties.getKeyset().contains("pip_stashing")) {
                                            pipTouchHandler2.mEnableStash = properties.getBoolean("pip_stashing", true);
                                            break;
                                        }
                                        break;
                                    default:
                                        if (properties.getKeyset().contains("pip_velocity_threshold")) {
                                            pipTouchHandler2.mStashVelocityThreshold = properties.getFloat("pip_velocity_threshold", 18000.0f);
                                            break;
                                        }
                                        break;
                                }
                            }
                        };
                        ShellExecutor shellExecutor2 = pipTouchHandler.mMainExecutor;
                        DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, onPropertiesChangedListener);
                        pipTouchHandler.mStashVelocityThreshold = DeviceConfig.getFloat("systemui", "pip_velocity_threshold", 18000.0f);
                        final int i52 = 1;
                        DeviceConfig.addOnPropertiesChangedListener("systemui", shellExecutor2, new DeviceConfig.OnPropertiesChangedListener() { // from class: com.android.wm.shell.pip.phone.PipTouchHandler$$ExternalSyntheticLambda9
                            public final void onPropertiesChanged(DeviceConfig.Properties properties) {
                                int i522 = i52;
                                PipTouchHandler pipTouchHandler2 = pipTouchHandler;
                                pipTouchHandler2.getClass();
                                switch (i522) {
                                    case 0:
                                        if (properties.getKeyset().contains("pip_stashing")) {
                                            pipTouchHandler2.mEnableStash = properties.getBoolean("pip_stashing", true);
                                            break;
                                        }
                                        break;
                                    default:
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
        }, this);
    }

    public final void animateToNormalSize(PipMenuView$$ExternalSyntheticLambda0 pipMenuView$$ExternalSyntheticLambda0) {
        PipResizeGestureHandler pipResizeGestureHandler = this.mPipResizeGestureHandler;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        pipResizeGestureHandler.setUserResizeBounds(pipBoundsState.getBounds());
        Rect adjustNormalBoundsToFitMenu = this.mPipBoundsAlgorithm.adjustNormalBoundsToFitMenu(pipBoundsState.mNormalBounds, this.mMenuController.getEstimatedMinMenuSize());
        if (!pipBoundsState.getBounds().isEmpty()) {
            float width = pipBoundsState.getBounds().width() / pipBoundsState.getBounds().height();
            float f = pipBoundsState.mAspectRatio;
            if ((width >= 1.0f && f < 1.0f) || (width < 1.0f && f >= 1.0f)) {
                Log.d("PipTouchHandler", "[PipTaskOrganizer] animateToNormalSize setUserResizeBounds=" + adjustNormalBoundsToFitMenu + " reason=ratio_change");
                this.mPipResizeGestureHandler.setUserResizeBounds(adjustNormalBoundsToFitMenu);
            }
        }
        Rect rect = new Rect();
        PipBoundsAlgorithm.getMovementBounds(adjustNormalBoundsToFitMenu, this.mInsetBounds, rect, this.mIsImeShowing ? this.mImeHeight : 0);
        PipMotionHelper pipMotionHelper = this.mMotionHelper;
        Rect rect2 = pipBoundsState.mMovementBounds;
        pipMotionHelper.getClass();
        float snapFraction = pipMotionHelper.mSnapAlgorithm.getSnapFraction(0, new Rect(pipMotionHelper.mPipBoundsState.getBounds()), rect2);
        PipSnapAlgorithm.applySnapFraction(adjustNormalBoundsToFitMenu, rect, snapFraction);
        pipMotionHelper.mPostPipTransitionCallback = pipMenuView$$ExternalSyntheticLambda0;
        pipMotionHelper.resizeAndAnimatePipUnchecked$1(adjustNormalBoundsToFitMenu);
        this.mSavedSnapFraction = snapFraction;
    }

    public final void animateToUnStashedState() {
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        Rect bounds = pipBoundsState.getBounds();
        boolean z = bounds.left < pipBoundsState.mPipDisplayLayoutState.getDisplayBounds().left;
        Rect rect = new Rect(0, bounds.top, 0, bounds.bottom);
        rect.left = z ? this.mInsetBounds.left : this.mInsetBounds.right - bounds.width();
        rect.right = z ? bounds.width() + this.mInsetBounds.left : this.mInsetBounds.right;
        this.mMotionHelper.resizeAndAnimatePipUnchecked$1(rect);
    }

    public final void animateToUnexpandedState(Rect rect) {
        Rect rect2 = new Rect();
        Rect rect3 = this.mInsetBounds;
        int i = this.mIsImeShowing ? this.mImeHeight : 0;
        this.mPipBoundsAlgorithm.getClass();
        PipBoundsAlgorithm.getMovementBounds(rect, rect3, rect2, i);
        this.mMotionHelper.animateToUnexpandedState(rect, this.mSavedSnapFraction, rect2, this.mPipBoundsState.mMovementBounds, false);
        this.mSavedSnapFraction = -1.0f;
    }

    public PipResizeGestureHandler getPipResizeGestureHandler() {
        return this.mPipResizeGestureHandler;
    }

    public final Rect getPossiblyMotionBounds() {
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        return pipBoundsState.mMotionBoundsState.isInMotion() ? pipBoundsState.mMotionBoundsState.mBoundsInMotion : pipBoundsState.getBounds();
    }

    public final void onRegistrationChanged(boolean z) {
        if (z) {
            this.mAccessibilityManager.setPictureInPictureActionReplacingConnection(this.mConnection.mConnectionImpl);
        } else {
            this.mAccessibilityManager.setPictureInPictureActionReplacingConnection(null);
        }
        if (z || !this.mTouchState.mIsUserInteracting) {
            return;
        }
        this.mPipDismissTargetHandler.getClass();
    }

    public final void sendAccessibilityHoverEvent(int i) {
        if (this.mAccessibilityManager.isEnabled()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain(i);
            obtain.setImportantForAccessibility(true);
            obtain.setSourceNodeId(AccessibilityNodeInfo.ROOT_NODE_ID);
            obtain.setWindowId(-3);
            this.mAccessibilityManager.sendAccessibilityEvent(obtain);
        }
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
        Rect rect = this.mInsetBounds;
        Rect rect2 = pipBoundsState.mMovementBounds;
        int i = this.mIsImeShowing ? this.mImeHeight : 0;
        this.mPipBoundsAlgorithm.getClass();
        PipBoundsAlgorithm.getMovementBounds(bounds, rect, rect2, i);
        PipMotionHelper pipMotionHelper = this.mMotionHelper;
        PipBoundsState pipBoundsState2 = pipMotionHelper.mPipBoundsState;
        Rect rect3 = pipBoundsState2.mMovementBounds;
        pipMotionHelper.mFlingConfigX = new PhysicsAnimator.FlingConfig(1.9f, rect3.left, rect3.right);
        Rect rect4 = pipBoundsState2.mMovementBounds;
        pipMotionHelper.mFlingConfigY = new PhysicsAnimator.FlingConfig(1.9f, rect4.top, rect4.bottom);
        pipBoundsState2.mPipDisplayLayoutState.getDisplayLayout();
        pipMotionHelper.mStashConfigX = new PhysicsAnimator.FlingConfig(1.9f, (pipBoundsState2.mStashOffset - pipBoundsState2.getBounds().width()) + pipBoundsState2.getStashInsets().left, (r0.getDisplayBounds().right - pipBoundsState2.mStashOffset) - pipBoundsState2.getStashInsets().right);
        pipMotionHelper.mFloatingAllowedArea.set(pipBoundsState2.mMovementBounds);
        Rect rect5 = pipMotionHelper.mFloatingAllowedArea;
        rect5.right = pipBoundsState2.getBounds().width() + rect5.right;
        Rect rect6 = pipMotionHelper.mFloatingAllowedArea;
        rect6.bottom = pipBoundsState2.getBounds().height() + rect6.bottom;
    }

    public final void updatePipSizeConstraints(Rect rect, float f) {
        PipResizeGestureHandler pipResizeGestureHandler = this.mPipResizeGestureHandler;
        boolean z = pipResizeGestureHandler.mEnablePinchResize;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        if (!z) {
            pipResizeGestureHandler.updateMinSize(rect.width(), rect.height());
            this.mPipResizeGestureHandler.updateMaxSize(pipBoundsState.mExpandedBounds.width(), pipBoundsState.mExpandedBounds.height());
            return;
        }
        pipBoundsState.updateMinMaxSize(f);
        PipResizeGestureHandler pipResizeGestureHandler2 = this.mPipResizeGestureHandler;
        Point point = pipBoundsState.mMinSize;
        pipResizeGestureHandler2.updateMinSize(point.x, point.y);
        PipResizeGestureHandler pipResizeGestureHandler3 = this.mPipResizeGestureHandler;
        Point point2 = pipBoundsState.mMaxSize;
        pipResizeGestureHandler3.updateMaxSize(point2.x, point2.y);
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
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[5]) {
            ProtoLogImpl_1771455215.wtf(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 4319316146978633493L, 0, "PipTouchHandler");
        }
        return false;
    }
}
