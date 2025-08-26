package com.android.wm.shell.transition;

import android.R;
import android.animation.Animator;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.WindowConfiguration;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.hardware.HardwareBuffer;
import android.os.Handler;
import android.os.IBinder;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Log;
import android.util.RotationUtils;
import android.util.Slog;
import android.view.SurfaceControl;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.window.TransitionInfo;
import android.window.TransitionMetrics;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.internal.policy.TransitionAnimation;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayInsetsController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.TransactionPool;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.splitscreen.SplitScreenTransitions$$ExternalSyntheticLambda2;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.taskview.TaskViewTransitions;
import com.android.wm.shell.transition.DefaultTransitionHandler;
import com.android.wm.shell.transition.TransitionAnimationHelper;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.transition.change.ChangeTransitionProvider;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.rune.CoreRune;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class DefaultTransitionHandler implements Transitions.TransitionHandler {
    public final ShellExecutor mAnimExecutor;
    public final Handler mAnimHandler;
    public ChangeTransitionProvider mChangeTransitProvider;
    public final Context mContext;
    public final DevicePolicyManager mDevicePolicyManager;
    public DimTransitionProvider mDimTransitionProvider;
    public final DisplayController mDisplayController;
    public Drawable mEnterpriseThumbnailDrawable;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public final ShellExecutor mMainExecutor;
    public final Handler mMainHandler;
    public MultiTaskingTransitionProvider mMultiTaskingTransitProvider;
    public final RootTaskDisplayAreaOrganizer mRootTDAOrganizer;
    public final TransitionAnimationHelper.RoundedContentTracker mRoundedContentBounds;
    public boolean mSkipMergeAnimation;
    public final TransactionPool mTransactionPool;
    public final TransitionAnimation mTransitionAnimation;
    public final ArrayMap mAnimations = new ArrayMap();
    public final CounterRotatorHelper mRotator = new CounterRotatorHelper();
    public final Rect mInsets = new Rect(0, 0, 0, 0);
    public float mTransitionAnimationScaleSetting = 1.0f;
    public final AnonymousClass1 mEnterpriseResourceUpdatedReceiver = new BroadcastReceiver() { // from class: com.android.wm.shell.transition.DefaultTransitionHandler.1
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra("android.app.extra.RESOURCE_TYPE", -1) != 1) {
                return;
            }
            DefaultTransitionHandler defaultTransitionHandler = DefaultTransitionHandler.this;
            defaultTransitionHandler.mEnterpriseThumbnailDrawable = defaultTransitionHandler.mDevicePolicyManager.getResources().getDrawable("WORK_PROFILE_ICON", "OUTLINE", "PROFILE_SWITCH_ANIMATION", new DefaultTransitionHandler$$ExternalSyntheticLambda1(defaultTransitionHandler));
        }
    };

    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.wm.shell.transition.DefaultTransitionHandler$1] */
    public DefaultTransitionHandler(Context context, ShellInit shellInit, DisplayController displayController, DisplayInsetsController displayInsetsController, TransactionPool transactionPool, ShellExecutor shellExecutor, Handler handler, ShellExecutor shellExecutor2, Handler handler2, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, InteractionJankMonitor interactionJankMonitor) {
        this.mDisplayController = displayController;
        this.mTransactionPool = transactionPool;
        this.mContext = context;
        this.mMainHandler = handler;
        this.mMainExecutor = shellExecutor;
        this.mAnimExecutor = shellExecutor2;
        this.mAnimHandler = handler2;
        this.mTransitionAnimation = new TransitionAnimation(context, false, "ShellTransitions");
        UserHandle.myUserId();
        this.mDevicePolicyManager = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                DefaultTransitionHandler defaultTransitionHandler = this.f$0;
                defaultTransitionHandler.mEnterpriseThumbnailDrawable = defaultTransitionHandler.mDevicePolicyManager.getResources().getDrawable("WORK_PROFILE_ICON", "OUTLINE", "PROFILE_SWITCH_ANIMATION", new DefaultTransitionHandler$$ExternalSyntheticLambda1(defaultTransitionHandler));
                Context context2 = defaultTransitionHandler.mContext;
                DefaultTransitionHandler.AnonymousClass1 anonymousClass1 = defaultTransitionHandler.mEnterpriseResourceUpdatedReceiver;
                IntentFilter intentFilter = new IntentFilter("android.app.action.DEVICE_POLICY_RESOURCE_UPDATED");
                Handler handler3 = defaultTransitionHandler.mMainHandler;
                context2.registerReceiver(anonymousClass1, intentFilter, null, handler3);
                TransitionAnimation.initAttributeCache(defaultTransitionHandler.mContext, handler3);
                TransitionAnimationHelper.RoundedContentTracker roundedContentTracker = defaultTransitionHandler.mRoundedContentBounds;
                roundedContentTracker.mDisplayController.addDisplayWindowListener(roundedContentTracker, -1);
            }
        }, this);
        this.mRootTDAOrganizer = rootTaskDisplayAreaOrganizer;
        this.mRoundedContentBounds = new TransitionAnimationHelper.RoundedContentTracker(displayController, displayInsetsController);
        this.mInteractionJankMonitor = interactionJankMonitor;
    }

    public static int getRotationAnimationHint(TransitionInfo.Change change, TransitionInfo transitionInfo, DisplayController displayController) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -7541648108389319031L, 0, null);
        }
        if (change.getRotationAnimation() == 3) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -318194249311505831L, 0, null);
            }
            return 3;
        }
        int size = transitionInfo.getChanges().size();
        ActivityManager.RunningTaskInfo runningTaskInfo = null;
        int i = 0;
        boolean z = false;
        boolean z2 = false;
        int i2 = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
            if ((change2.getMode() == 6 || (CoreRune.FW_SHELL_TRANSITION && shouldStartRotationAnimation(change2))) && change2.getEndRotation() != change2.getStartRotation()) {
                if ((change2.getFlags() & 32) != 0) {
                    if ((change2.getFlags() & 128) != 0) {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 7261468683300714502L, 0, null);
                        }
                        z2 = true;
                    }
                } else if ((2 & change2.getFlags()) != 0) {
                    if (change2.getRotationAnimation() != 3) {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 5449828478871288392L, 0, null);
                        }
                        z2 = true;
                    }
                } else if (change2.getTaskInfo() != null) {
                    int rotationAnimation = change2.getRotationAnimation();
                    ActivityManager.RunningTaskInfo taskInfo = change2.getTaskInfo();
                    boolean z3 = runningTaskInfo == null;
                    if (z3) {
                        if (rotationAnimation != -1 && rotationAnimation != 3) {
                            i2 = rotationAnimation;
                        }
                        runningTaskInfo = taskInfo;
                    }
                    if (rotationAnimation != 3) {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 6117639397286231122L, 0, String.valueOf(taskInfo.taskId));
                        }
                        z = false;
                    } else if (z3) {
                        z = true;
                    }
                }
            }
            i++;
        }
        if (runningTaskInfo != null && runningTaskInfo.isAllowedSeamlessRotation && !WindowConfiguration.inMultiWindowMode(runningTaskInfo.getWindowingMode())) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -8899794523515766694L, 0, null);
            }
            return 3;
        }
        if (z && !z2) {
            DisplayLayout displayLayout = displayController.getDisplayLayout(runningTaskInfo.displayId);
            if (displayLayout.mAllowSeamlessRotationDespiteNavBarMoving) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 6706234276054184697L, 0, null);
                }
                return 3;
            }
            boolean z4 = displayLayout.mWidth > displayLayout.mHeight;
            if (displayLayout.mRotation % 2 != 0) {
                z4 = !z4;
            }
            int i3 = z4 ? displayLayout.mReverseDefaultRotation ? 3 : 1 : 2;
            if (change.getStartRotation() != i3 && change.getEndRotation() != i3) {
                if (displayLayout.mNavigationBarCanMove && displayLayout.mWidth != displayLayout.mHeight) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -2751141420127473180L, 0, null);
                    }
                    return 3;
                }
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 2597835766514531195L, 0, null);
                    return i2;
                }
            } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 8225236208583391558L, 0, null);
            }
        }
        return i2;
    }

    public static boolean isSupportedOverrideAnimation(TransitionInfo.AnimationOptions animationOptions) {
        int type = animationOptions.getType();
        return type == 1 || type == 2 || type == 3 || type == 4 || type == 11 || type == 12 || type == 14;
    }

    public static boolean shouldStartRotationAnimation(TransitionInfo.Change change) {
        if (change.isFadeInOutRotationNeeded()) {
            return true;
        }
        if (!CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION || (change.getFlags() & VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) == 0) {
            return CoreRune.FW_SHELL_TRANSITION_EXTENSION && change.getStartRotation() != change.getEndRotation();
        }
        return true;
    }

    public final void attachThumbnailAnimation(ArrayList arrayList, DefaultTransitionHandler$$ExternalSyntheticLambda2 defaultTransitionHandler$$ExternalSyntheticLambda2, TransitionInfo.Change change, TransitionInfo.AnimationOptions animationOptions, float f) {
        SurfaceControl.Transaction transactionAcquire = this.mTransactionPool.acquire();
        WindowThumbnail windowThumbnailCreateAndAttach = WindowThumbnail.createAndAttach(change.getLeash(), animationOptions.getThumbnail(), transactionAcquire);
        Animation animationCreateThumbnailAspectScaleAnimationLocked = this.mTransitionAnimation.createThumbnailAspectScaleAnimationLocked(change.getEndAbsBounds(), this.mInsets, animationOptions.getThumbnail(), this.mContext.getResources().getConfiguration().orientation, (Rect) null, animationOptions.getTransitionBounds(), animationOptions.getType() == 3);
        DefaultTransitionHandler$$ExternalSyntheticLambda6 defaultTransitionHandler$$ExternalSyntheticLambda6 = new DefaultTransitionHandler$$ExternalSyntheticLambda6(this, windowThumbnailCreateAndAttach, transactionAcquire, defaultTransitionHandler$$ExternalSyntheticLambda2, 0);
        animationCreateThumbnailAspectScaleAnimationLocked.restrictDuration(3000L);
        animationCreateThumbnailAspectScaleAnimationLocked.scaleCurrentDuration(this.mTransitionAnimationScaleSetting);
        DefaultSurfaceAnimator.buildSurfaceAnimation(arrayList, animationCreateThumbnailAspectScaleAnimationLocked, windowThumbnailCreateAndAttach.mSurfaceControl, defaultTransitionHandler$$ExternalSyntheticLambda6, this.mTransactionPool, this.mMainExecutor, change.getEndRelOffset(), f, change.getEndAbsBounds(), null);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void beforeMergeAnimation(IBinder iBinder, Transitions.TransitionHandler transitionHandler) {
        if (CoreRune.MW_SPLIT_SHELL_TRANSITION && (transitionHandler instanceof StageCoordinator)) {
            this.mSkipMergeAnimation = ((StageCoordinator) transitionHandler).mSplitTransitions.isPendingResize(iBinder);
        }
        if (transitionHandler instanceof TaskViewTransitions) {
            this.mSkipMergeAnimation = true;
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x02fa  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0300  */
    /* JADX WARN: Removed duplicated region for block: B:185:0x0307  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Animation loadAnimation(int i, TransitionInfo transitionInfo, TransitionInfo.Change change, int i2, boolean z) {
        Rect endAbsBounds;
        Rect rect;
        Animation animationCreateThumbnailEnterExitAnimationLocked;
        Rect rect2;
        TransitionInfo.Change change2;
        int flags = transitionInfo.getFlags();
        int mode = change.getMode();
        int flags2 = change.getFlags();
        boolean zIsOpeningType = TransitionUtil.isOpeningType(i);
        boolean zIsOpeningType2 = TransitionUtil.isOpeningType(mode);
        boolean z2 = true;
        boolean z3 = change.getTaskInfo() != null;
        TransitionInfo.AnimationOptions animationOptions = change.getAnimationOptions();
        int type = animationOptions != null ? animationOptions.getType() : 0;
        int userId = animationOptions != null ? animationOptions.getUserId() : -2;
        if (TransitionUtil.isClosingType(mode)) {
            CounterRotatorHelper counterRotatorHelper = this.mRotator;
            if (counterRotatorHelper.mLastRotationDelta == 0) {
                endAbsBounds = change.getEndAbsBounds();
            } else {
                Rect rect3 = new Rect(change.getEndAbsBounds());
                RotationUtils.rotateBounds(rect3, counterRotatorHelper.mLastDisplayBounds, counterRotatorHelper.mLastRotationDelta);
                endAbsBounds = rect3;
            }
        } else {
            endAbsBounds = change.getEndAbsBounds();
        }
        if (CoreRune.FW_LARGE_FLIP_TRANSITION && change.getEndDisplayId() == 1) {
            this.mTransitionAnimation.overrideDisplayId(1);
        }
        Animation animation = null;
        if (transitionInfo.isKeyguardGoingAway()) {
            animationCreateThumbnailEnterExitAnimationLocked = this.mTransitionAnimation.loadKeyguardExitAnimation(flags, (flags2 & 1) != 0);
        } else if (i == 9) {
            animationCreateThumbnailEnterExitAnimationLocked = this.mTransitionAnimation.loadKeyguardUnoccludeAnimation(userId);
        } else {
            if ((flags2 & 16) == 0) {
                if (mode == 6) {
                    if (!CoreRune.FW_SHELL_TRANSITION_RESUMED_AFFORDANCE || !change.getResumedAffordance()) {
                        if (CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION && change.getForceHidingTransit() != 0) {
                            animationCreateThumbnailEnterExitAnimationLocked = new AlphaAnimation(1.0f, 1.0f);
                            animationCreateThumbnailEnterExitAnimationLocked.setDuration(336L);
                        }
                        return null;
                    }
                    animationCreateThumbnailEnterExitAnimationLocked = this.mTransitionAnimation.loadResumeAffordanceAnimation();
                } else if (i == 5) {
                    animationCreateThumbnailEnterExitAnimationLocked = this.mTransitionAnimation.createRelaunchAnimation(endAbsBounds, this.mInsets, endAbsBounds);
                } else if (type == 1 && (!z3 || animationOptions.getOverrideTaskTransition())) {
                    animationCreateThumbnailEnterExitAnimationLocked = this.mTransitionAnimation.loadAnimationRes(animationOptions.getPackageName(), zIsOpeningType2 ? animationOptions.getEnterResId() : animationOptions.getExitResId(), userId);
                } else if (type == 12 && zIsOpeningType2) {
                    animationCreateThumbnailEnterExitAnimationLocked = this.mTransitionAnimation.loadCrossProfileAppEnterAnimation(userId);
                } else {
                    if (type == 11) {
                        rect2 = endAbsBounds;
                        animationCreateThumbnailEnterExitAnimationLocked = this.mTransitionAnimation.createClipRevealAnimationLocked(i, i2, zIsOpeningType2, rect2, endAbsBounds, animationOptions.getTransitionBounds());
                    } else {
                        Rect rect4 = endAbsBounds;
                        if (type == 2) {
                            rect2 = rect4;
                            animationCreateThumbnailEnterExitAnimationLocked = this.mTransitionAnimation.createScaleUpAnimationLocked(i, i2, zIsOpeningType2, rect2, animationOptions.getTransitionBounds());
                        } else {
                            endAbsBounds = rect4;
                            if (type != 3 && type != 4) {
                                if (((flags2 & 8) == 0 || !zIsOpeningType) && type != 5) {
                                    animationCreateThumbnailEnterExitAnimationLocked = (CoreRune.MT_NEW_DEX_RESUMED_AFFORDANCE_ANIMATION && i == 3 && change.getTaskInfo() != null && change.getConfiguration().isNewDexMode() && change.getResumedAffordance()) ? this.mTransitionAnimation.loadResumeAffordanceAnimation() : TransitionAnimationHelper.loadAttributeAnimation(i, transitionInfo, change, i2, this.mTransitionAnimation, z);
                                }
                                return null;
                            }
                            rect = endAbsBounds;
                            animationCreateThumbnailEnterExitAnimationLocked = this.mTransitionAnimation.createThumbnailEnterExitAnimationLocked(zIsOpeningType2, type == 3, rect, i, i2, animationOptions.getThumbnail(), animationOptions.getTransitionBounds());
                        }
                    }
                    rect = rect2;
                }
                if (CoreRune.MW_SHELL_TRANSITION) {
                    change2 = change;
                } else {
                    MultiTaskingTransitionProvider multiTaskingTransitionProvider = this.mMultiTaskingTransitProvider;
                    MultiTaskingTransitionState multiTaskingTransitionState = multiTaskingTransitionProvider.mState;
                    multiTaskingTransitionState.getClass();
                    multiTaskingTransitionState.mTransitionType = transitionInfo.getType();
                    multiTaskingTransitionState.mIsEnter = TransitionUtil.isOpeningType(change.getMode());
                    multiTaskingTransitionState.mConfiguration.setTo(change.getConfiguration());
                    change2 = change;
                    multiTaskingTransitionState.mChange = change2;
                    ActivityManager.RunningTaskInfo taskInfo = change2.getTaskInfo();
                    multiTaskingTransitionState.mTaskInfo = taskInfo;
                    multiTaskingTransitionState.mTaskId = taskInfo != null ? taskInfo.taskId : -1;
                    multiTaskingTransitionState.mDisplayId = taskInfo != null ? taskInfo.getDisplayId() : 0;
                    multiTaskingTransitionState.mWindowingMode = multiTaskingTransitionState.mConfiguration.windowConfiguration.getWindowingMode();
                    if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION) {
                        multiTaskingTransitionState.mHasCustomDisplayChangeTransition = transitionInfo.hasCustomDisplayChangeTransition();
                        multiTaskingTransitionState.mSeparatedFromCustomDisplayChange = transitionInfo.isSeparatedFromCustomDisplayChange();
                    }
                    multiTaskingTransitionState.mForceHidingTransit = change2.getForceHidingTransit();
                    if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
                        multiTaskingTransitionState.mMinimizeAnimState = change2.getMinimizeAnimState();
                        multiTaskingTransitionState.mMinimizePoint.set(change2.getMinimizePoint());
                    }
                    if (CoreRune.MW_CAPTION_FREEFORM_STASH && multiTaskingTransitionState.mWindowingMode == 5) {
                        multiTaskingTransitionState.mFreeformStashScale = change2.getFreeformStashScale();
                    }
                    if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                        TransitionInfo.Change changeForAppsEdgeActivity = transitionInfo.getChangeForAppsEdgeActivity();
                        multiTaskingTransitionState.mOpeningAppsEdgeTaskInfo = (changeForAppsEdgeActivity == null || changeForAppsEdgeActivity.getMode() != 1) ? null : changeForAppsEdgeActivity.getTaskInfo();
                    }
                    multiTaskingTransitionState.mIsPopOverAnimationNeeded = change2.getPopOverAnimationNeeded();
                    try {
                        if (!CoreRune.MW_RESUMED_AFFORDANCE_SHELL_TRANSITION || !change2.getResumedAffordance()) {
                            int size = multiTaskingTransitionProvider.mAnimationLoaderMap.size();
                            int i3 = 0;
                            while (true) {
                                if (i3 >= size) {
                                    animation = animationCreateThumbnailEnterExitAnimationLocked;
                                    break;
                                }
                                AnimationLoader animationLoader = (AnimationLoader) multiTaskingTransitionProvider.mAnimationLoaderMap.valueAt(i3);
                                if (animationLoader.isAvailable()) {
                                    animationLoader.loadAnimationIfPossible();
                                    MultiTaskingTransitionState multiTaskingTransitionState2 = animationLoader.mState;
                                    if (multiTaskingTransitionState2.mAnimationLoaded) {
                                        Animation animation2 = multiTaskingTransitionState2.mAnimation;
                                        if (animation2 != AnimationLoader.NO_ANIMATION) {
                                            z2 = false;
                                        }
                                        if (!z2) {
                                            animation = animation2;
                                        }
                                        Log.d("MultiTaskingTransitionProvider", "loadAnimation: " + animationLoader + multiTaskingTransitionState);
                                    }
                                }
                                i3++;
                            }
                        } else {
                            Log.d("MultiTaskingTransitionProvider", "loadAnimation: Use affordance" + multiTaskingTransitionState);
                            if ((multiTaskingTransitionState.mWindowingMode == 6 && WindowConfiguration.isSplitScreenWindowingMode(multiTaskingTransitionState.mConfiguration.windowConfiguration)) && animationCreateThumbnailEnterExitAnimationLocked != null && multiTaskingTransitionProvider.mAnimationLoaderMap.get(1) != null) {
                                animationCreateThumbnailEnterExitAnimationLocked.setRoundedCornerRadius(((AnimationLoader) multiTaskingTransitionProvider.mAnimationLoaderMap.get(1)).getCornerRadius(multiTaskingTransitionState.mDisplayController.getDisplayContext(multiTaskingTransitionState.mDisplayId)));
                            }
                            if (!(multiTaskingTransitionState.mWindowingMode == 5) || change2.getAffordanceTargetFreeformTask()) {
                                multiTaskingTransitionState.reset();
                                animation = animationCreateThumbnailEnterExitAnimationLocked;
                            }
                            animationCreateThumbnailEnterExitAnimationLocked = animation;
                        }
                        multiTaskingTransitionState.reset();
                        animationCreateThumbnailEnterExitAnimationLocked = animation;
                    } catch (Throwable th) {
                        multiTaskingTransitionState.reset();
                        throw th;
                    }
                }
                if (CoreRune.FW_LARGE_FLIP_TRANSITION) {
                    this.mTransitionAnimation.overrideDisplayId(-1);
                }
                if (animationCreateThumbnailEnterExitAnimationLocked != null) {
                    if (!animationCreateThumbnailEnterExitAnimationLocked.isInitialized()) {
                        Rect startAbsBounds = TransitionUtil.isClosingType(mode) ? change2.getStartAbsBounds() : change2.getEndAbsBounds();
                        animationCreateThumbnailEnterExitAnimationLocked.initialize(startAbsBounds.width(), startAbsBounds.height(), rect.width(), rect.height());
                    }
                    animationCreateThumbnailEnterExitAnimationLocked.restrictDuration(3000L);
                    animationCreateThumbnailEnterExitAnimationLocked.scaleCurrentDuration(this.mTransitionAnimationScaleSetting);
                }
                return animationCreateThumbnailEnterExitAnimationLocked;
            }
            animationCreateThumbnailEnterExitAnimationLocked = zIsOpeningType ? this.mTransitionAnimation.loadVoiceActivityOpenAnimation(zIsOpeningType2, userId) : this.mTransitionAnimation.loadVoiceActivityExitAnimation(zIsOpeningType2, userId);
        }
        rect = endAbsBounds;
        if (CoreRune.MW_SHELL_TRANSITION) {
        }
        if (CoreRune.FW_LARGE_FLIP_TRANSITION) {
        }
        if (animationCreateThumbnailEnterExitAnimationLocked != null) {
        }
        return animationCreateThumbnailEnterExitAnimationLocked;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        if (CoreRune.MW_SHELL_TRANSITION && this.mSkipMergeAnimation) {
            this.mSkipMergeAnimation = false;
            return;
        }
        ArrayList arrayList = (ArrayList) this.mAnimations.get(iBinder2);
        if (arrayList == null) {
            return;
        }
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            Animator animator = (Animator) arrayList.get(size);
            Objects.requireNonNull(animator);
            this.mAnimExecutor.execute(new SplitScreenTransitions$$ExternalSyntheticLambda2(animator, 0));
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
        this.mInteractionJankMonitor.cancel(128);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void setAnimScaleSetting(float f) {
        this.mTransitionAnimationScaleSetting = f;
        if (CoreRune.MW_SHELL_TRANSITION) {
            MultiTaskingTransitionProvider multiTaskingTransitionProvider = this.mMultiTaskingTransitProvider;
            if (multiTaskingTransitionProvider.mDurationScale != f) {
                Log.d("MultiTaskingTransitionProvider", "setAnimScaleSetting: " + multiTaskingTransitionProvider.mDurationScale + "->" + f);
                multiTaskingTransitionProvider.mDurationScale = f;
            }
        }
        if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
            ChangeTransitionProvider changeTransitionProvider = this.mChangeTransitProvider;
            if (changeTransitionProvider.mDurationScale != f) {
                Log.d("ChangeTransitionProvider", "setAnimScaleSetting: " + changeTransitionProvider.mDurationScale + "->" + f);
                changeTransitionProvider.mDurationScale = f;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:125:0x01e5  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x0448  */
    /* JADX WARN: Removed duplicated region for block: B:280:0x0478  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x0492  */
    /* JADX WARN: Removed duplicated region for block: B:286:0x04a0  */
    /* JADX WARN: Removed duplicated region for block: B:289:0x04b1  */
    /* JADX WARN: Removed duplicated region for block: B:292:0x04b6  */
    /* JADX WARN: Removed duplicated region for block: B:299:0x050b  */
    /* JADX WARN: Removed duplicated region for block: B:300:0x050d  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x0539  */
    /* JADX WARN: Removed duplicated region for block: B:312:0x0556  */
    /* JADX WARN: Removed duplicated region for block: B:314:0x055c  */
    /* JADX WARN: Removed duplicated region for block: B:315:0x0593  */
    /* JADX WARN: Removed duplicated region for block: B:317:0x05ae  */
    /* JADX WARN: Removed duplicated region for block: B:318:0x05b4  */
    /* JADX WARN: Removed duplicated region for block: B:460:0x0881  */
    /* JADX WARN: Removed duplicated region for block: B:467:0x0898  */
    /* JADX WARN: Removed duplicated region for block: B:469:0x089c  */
    /* JADX WARN: Removed duplicated region for block: B:473:0x08a6  */
    /* JADX WARN: Removed duplicated region for block: B:478:0x08c5  */
    /* JADX WARN: Removed duplicated region for block: B:484:0x08e9  */
    /* JADX WARN: Removed duplicated region for block: B:487:0x0928  */
    /* JADX WARN: Removed duplicated region for block: B:488:0x092a  */
    /* JADX WARN: Removed duplicated region for block: B:490:0x092d  */
    /* JADX WARN: Removed duplicated region for block: B:491:0x094c  */
    /* JADX WARN: Removed duplicated region for block: B:493:0x0950 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:499:0x0964  */
    /* JADX WARN: Removed duplicated region for block: B:501:0x0967  */
    /* JADX WARN: Removed duplicated region for block: B:504:0x09da A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:507:0x09e0  */
    /* JADX WARN: Removed duplicated region for block: B:510:0x0a0f  */
    /* JADX WARN: Removed duplicated region for block: B:541:0x0aea  */
    /* JADX WARN: Removed duplicated region for block: B:544:0x0af4  */
    /* JADX WARN: Removed duplicated region for block: B:571:0x0bc2  */
    /* JADX WARN: Removed duplicated region for block: B:580:0x0c27  */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        boolean z;
        boolean z2;
        boolean z3;
        int i;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer;
        CounterRotatorHelper counterRotatorHelper;
        int i2;
        boolean z8;
        char c;
        int i3;
        boolean z9;
        DefaultTransitionHandler defaultTransitionHandler;
        TransitionInfo.Change change;
        DefaultTransitionHandler$$ExternalSyntheticLambda2 defaultTransitionHandler$$ExternalSyntheticLambda2;
        boolean z10;
        boolean z11;
        SurfaceControl.Transaction transaction3;
        int i4;
        int i5;
        int i6;
        TransitionInfo.Change change2;
        TransitionInfo transitionInfo2;
        boolean z12;
        boolean z13;
        DefaultTransitionHandler$$ExternalSyntheticLambda2 defaultTransitionHandler$$ExternalSyntheticLambda22;
        int i7;
        SurfaceControl.Transaction transaction4;
        TransitionInfo.Change change3;
        int i8;
        int i9;
        TransitionInfo transitionInfo3;
        boolean z14;
        ArrayList arrayList;
        char c2;
        boolean z15;
        int i10;
        int endDisplayId;
        char c3;
        int i11;
        int i12;
        ArrayList arrayList2;
        Context context;
        Animation animation;
        float f;
        float windowCornerRadius;
        int i13;
        int i14;
        Rect rect;
        boolean z16;
        boolean z17;
        TransitionInfo.AnimationOptions animationOptions;
        Animation animation2;
        int i15;
        Animation animation3;
        TransitionInfo.Change change4;
        HardwareBuffer hardwareBufferCreateCrossProfileAppsThumbnail;
        Rect rect2;
        int backgroundColor;
        boolean z18;
        char c4;
        TransitionInfo transitionInfo4 = transitionInfo;
        SurfaceControl.Transaction transaction5 = transaction;
        int i16 = -1;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1860574116773740964L, 0, String.valueOf(transitionInfo4));
        }
        if (transitionInfo4.getType() == 11 && !transitionInfo4.isKeyguardGoingAway()) {
            transaction5.apply();
            transitionFinishCallback.onTransitionFinished(null);
            return true;
        }
        if (TransitionUtil.isClosingType(transitionInfo4.getType())) {
            z = false;
            break;
        }
        z = false;
        for (int iM = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo4, 1); iM >= 0; iM--) {
            TransitionInfo.Change change5 = (TransitionInfo.Change) transitionInfo4.getChanges().get(iM);
            if (!TransitionUtil.isClosingType(change5.getMode())) {
                if (!change5.hasFlags(262144)) {
                    if (!TransitionUtil.isOrderOnly(change5) && !change5.hasFlags(NetworkAnalyticsConstants.DataPoints.FLAG_UID)) {
                        z = false;
                        break;
                    }
                } else {
                    z = true;
                }
            }
        }
        if (!z) {
            int iM2 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo4, 1);
            while (true) {
                if (iM2 < 0) {
                    z2 = true;
                    break;
                }
                if (!TransitionUtil.isOrderOnly((TransitionInfo.Change) transitionInfo4.getChanges().get(iM2))) {
                    z2 = false;
                    break;
                }
                iM2--;
            }
            if (!z2 && (((transitionInfo4.getFlags() & 1024) == 0 || (CoreRune.MW_SHELL_TRANSITION_BUG_FIX && transitionInfo4.shouldAnimateDefaultDisplay())) && (!CoreRune.MW_SPLIT_SHELL_TRANSITION || !Transitions.hasDuplicatedOpenTypeChanges(transitionInfo4)))) {
                if (this.mAnimations.containsKey(iBinder)) {
                    throw new IllegalStateException("Got a duplicate startAnimation call for " + iBinder);
                }
                ArrayList arrayList3 = new ArrayList();
                this.mAnimations.put(iBinder, arrayList3);
                if (transitionInfo4.getChanges().size() != 2) {
                    z3 = false;
                } else {
                    int iM3 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo4, 1);
                    boolean z19 = false;
                    boolean z20 = false;
                    while (true) {
                        if (iM3 >= 0) {
                            TransitionInfo.Change change6 = (TransitionInfo.Change) transitionInfo4.getChanges().get(iM3);
                            if (change6.getTaskInfo() == null) {
                                break;
                            }
                            int mode = change6.getMode();
                            z19 |= mode == 1 || mode == 3;
                            z20 |= mode == 2 || mode == 4;
                            iM3--;
                        } else {
                            if (!z19 || !z20) {
                                break;
                            }
                            z3 = true;
                        }
                    }
                    z3 = false;
                }
                ArrayList arrayList4 = arrayList3;
                final DefaultTransitionHandler$$ExternalSyntheticLambda2 defaultTransitionHandler$$ExternalSyntheticLambda23 = new DefaultTransitionHandler$$ExternalSyntheticLambda2(this, arrayList3, z3, iBinder, transitionFinishCallback);
                final DefaultTransitionHandler defaultTransitionHandler2 = this;
                ArrayList arrayList5 = new ArrayList();
                int iM4 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo4, 1);
                boolean z21 = false;
                boolean z22 = false;
                boolean z23 = false;
                boolean z24 = false;
                boolean z25 = false;
                while (iM4 >= 0) {
                    int i17 = i16;
                    TransitionInfo.Change change7 = (TransitionInfo.Change) transitionInfo4.getChanges().get(iM4);
                    if ((change7.getFlags() & 1) == 0 && (change7.getFlags() & 2) == 0) {
                        if (CoreRune.FW_SHELL_TRANSITION_EXTENSION && change7.hasFlags(4)) {
                            if (TransitionUtil.isOrderOnly(change7) || TransitionUtil.isOpeningType(change7.getMode())) {
                                z24 = true;
                            }
                            if (CoreRune.MW_SHELL_CHANGE_TRANSITION && change7.getMode() == 6 && change7.getTaskInfo() != null && change7.getTaskInfo().isFreeform()) {
                                z25 = true;
                            }
                        }
                    } else if (TransitionUtil.isOpeningType(change7.getMode())) {
                        z21 = true;
                        z23 = true;
                    } else if (TransitionUtil.isClosingType(change7.getMode())) {
                        z22 = true;
                        z23 = true;
                    } else {
                        z23 = true;
                    }
                    iM4--;
                    i16 = i17;
                }
                int i18 = i16;
                if (z21 && z22) {
                    i = TransitionUtil.isOpeningType(transitionInfo4.getType()) ? 4 : 5;
                } else if (z21) {
                    i = (CoreRune.FW_SHELL_TRANSITION_EXTENSION && z24) ? 6 : (CoreRune.MW_SHELL_CHANGE_TRANSITION && transitionInfo4.getType() == 6 && z25) ? 0 : 2;
                } else if (z22) {
                    i = 3;
                } else if (z23) {
                    i = 1;
                }
                int iM5 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo4, 1);
                while (true) {
                    if (iM5 < 0) {
                        z4 = false;
                        break;
                    }
                    TransitionInfo.Change change8 = (TransitionInfo.Change) transitionInfo4.getChanges().get(iM5);
                    if (change8.getTaskInfo() != null && change8.getTaskInfo().topActivityType == 5) {
                        z4 = true;
                        break;
                    }
                    iM5--;
                }
                int iM6 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo4, 1);
                int i19 = 0;
                int i20 = 0;
                while (true) {
                    if (iM6 >= 0) {
                        TransitionInfo.Change change9 = (TransitionInfo.Change) transitionInfo4.getChanges().get(iM6);
                        if (change9.getMode() != 6) {
                            if (!change9.hasFlags(4)) {
                                break;
                            }
                            if (TransitionUtil.isOpeningType(change9.getMode())) {
                                i19++;
                            } else {
                                i20++;
                            }
                        }
                        iM6--;
                    } else {
                        z5 = i19 + i20 > 0;
                    }
                }
                int iM7 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo4, 1);
                while (true) {
                    if (iM7 < 0) {
                        z6 = true;
                        break;
                    }
                    if (((TransitionInfo.Change) transitionInfo4.getChanges().get(iM7)).getActivityComponent() == null) {
                        z6 = false;
                        break;
                    }
                    iM7--;
                }
                if (TransitionUtil.isOpeningType(transitionInfo4.getType())) {
                    for (int iM8 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo4, 1); iM8 >= 0; iM8--) {
                        TransitionInfo.Change change10 = (TransitionInfo.Change) transitionInfo4.getChanges().get(iM8);
                        if (TransitionUtil.isOpeningType(change10.getMode()) && (change10.getPopOverAnimationNeeded() || change10.getConfiguration().windowConfiguration.isPopOver())) {
                            z7 = true;
                            break;
                        }
                    }
                    z7 = false;
                } else {
                    z7 = false;
                }
                int iM9 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo4, 1);
                int i21 = Integer.MIN_VALUE;
                boolean z26 = false;
                int color = 0;
                while (true) {
                    rootTaskDisplayAreaOrganizer = defaultTransitionHandler2.mRootTDAOrganizer;
                    counterRotatorHelper = defaultTransitionHandler2.mRotator;
                    if (iM9 < 0) {
                        break;
                    }
                    ArrayList arrayList6 = arrayList5;
                    TransitionInfo.Change change11 = (TransitionInfo.Change) transitionInfo4.getChanges().get(iM9);
                    boolean z27 = CoreRune.MW_SHELL_TRANSITION_BUG_FIX;
                    DefaultTransitionHandler$$ExternalSyntheticLambda2 defaultTransitionHandler$$ExternalSyntheticLambda24 = defaultTransitionHandler$$ExternalSyntheticLambda23;
                    if (z27) {
                        z8 = z3;
                        if ((transitionInfo4.getFlags() & 1024) != 0 && transitionInfo4.shouldAnimateDefaultDisplay() && change11.getEndDisplayId() != 0) {
                            i5 = i21;
                            transitionInfo2 = transitionInfo4;
                            transaction3 = transaction5;
                            i3 = i;
                            z9 = z5;
                            defaultTransitionHandler$$ExternalSyntheticLambda23 = defaultTransitionHandler$$ExternalSyntheticLambda24;
                            c = 4;
                            i6 = iM9;
                            defaultTransitionHandler2 = this;
                            transitionInfo3 = transitionInfo2;
                            i11 = i6;
                            z14 = z4;
                            c2 = c;
                            z15 = z8;
                            i10 = i3;
                            endDisplayId = i5;
                            c3 = 6;
                            transaction5 = transaction3;
                            arrayList = arrayList4;
                            arrayList2 = arrayList6;
                        }
                        arrayList4 = arrayList;
                        i = i10;
                        transitionInfo4 = transitionInfo3;
                        z5 = z9;
                        arrayList5 = arrayList2;
                        z4 = z14;
                        i18 = -1;
                        iM9 = i11 - 1;
                        i21 = endDisplayId;
                        z3 = z15;
                    } else {
                        z8 = z3;
                    }
                    if (!change11.hasAllFlags(16896) || (CoreRune.MW_EMBED_ACTIVITY && change11.hasFlags(1024))) {
                        if (change11.hasFlags(65794)) {
                            if (z7 && change11.hasFlags(2)) {
                                c4 = 4;
                                if (change11.getMode() == 4) {
                                    transaction5.hide(change11.getLeash());
                                }
                            } else {
                                c4 = 4;
                            }
                            if (TransitionUtil.isWallpaper(change11) && TransitionUtil.isOpeningMode(change11.getMode())) {
                                transaction5.setAlpha(change11.getLeash(), 1.0f);
                            }
                            i5 = i21;
                            transitionInfo2 = transitionInfo4;
                            transaction3 = transaction5;
                            c = c4;
                            i3 = i;
                            z9 = z5;
                        } else {
                            c = 4;
                            boolean z28 = change11.getTaskInfo() != null;
                            boolean z29 = z28 && change11.getTaskInfo().isFreeform();
                            int mode2 = change11.getMode();
                            i3 = i;
                            DisplayController displayController = defaultTransitionHandler2.mDisplayController;
                            z9 = z5;
                            if (mode2 == 6 && change11.hasFlags(32)) {
                                ArrayList arrayList7 = MultiTaskingTransitionProvider.sForceHidingAnimators;
                                if (change11.getSnapshot() == null && change11.getMode() == 6 && change11.hasAllFlags(1048608) && change11.getStartRotation() == change11.getEndRotation() && change11.getStartAbsBounds().equals(change11.getEndAbsBounds())) {
                                    i5 = i21;
                                    transitionInfo2 = transitionInfo4;
                                    transaction3 = transaction5;
                                } else if (transitionInfo4.getType() == 6 || z9 || (CoreRune.FW_SHELL_TRANSITION && shouldStartRotationAnimation(change11))) {
                                    int i22 = iM9;
                                    int rotationAnimationHint = getRotationAnimationHint(change11, transitionInfo4, displayController);
                                    z10 = rotationAnimationHint == 3;
                                    if (z10) {
                                        defaultTransitionHandler = defaultTransitionHandler2;
                                    } else if (rotationAnimationHint != 2) {
                                        TransitionInfo transitionInfo5 = transitionInfo4;
                                        ArrayList arrayList8 = arrayList4;
                                        startRotationAnimation(transaction5, change11, transitionInfo5, rotationAnimationHint, i3 != 0 ? 1 : 0, arrayList8, defaultTransitionHandler$$ExternalSyntheticLambda24, transaction2);
                                        transitionInfo3 = transitionInfo;
                                        endDisplayId = change11.getEndDisplayId();
                                        defaultTransitionHandler2 = this;
                                        defaultTransitionHandler$$ExternalSyntheticLambda23 = defaultTransitionHandler$$ExternalSyntheticLambda24;
                                        i11 = i22;
                                        z14 = z4;
                                        arrayList = arrayList8;
                                        c2 = 4;
                                        arrayList2 = arrayList6;
                                        z15 = z8;
                                        i10 = i3;
                                        c3 = 6;
                                        transaction5 = transaction;
                                    } else {
                                        defaultTransitionHandler = this;
                                    }
                                    iM9 = i22;
                                    defaultTransitionHandler$$ExternalSyntheticLambda2 = defaultTransitionHandler$$ExternalSyntheticLambda24;
                                    change = change11;
                                    z11 = CoreRune.MW_SHELL_TRANSITION;
                                    if (z11) {
                                    }
                                    if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
                                    }
                                    z12 = CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION;
                                    if (z12) {
                                        if (z28) {
                                        }
                                    } else if (z28) {
                                        if (z28) {
                                        }
                                        if (!CoreRune.MW_EMBED_ACTIVITY_ANIMATION) {
                                            if (change2.getParent() != null) {
                                            }
                                            if (z10) {
                                            }
                                            defaultTransitionHandler2 = this;
                                            transitionInfo3 = transitionInfo2;
                                            z14 = z4;
                                            arrayList = arrayList4;
                                            c2 = 4;
                                            z15 = z8;
                                            i10 = i3;
                                            endDisplayId = i5;
                                            defaultTransitionHandler$$ExternalSyntheticLambda23 = defaultTransitionHandler$$ExternalSyntheticLambda22;
                                            c3 = 6;
                                            i11 = i9;
                                            arrayList2 = arrayList6;
                                        }
                                    }
                                } else {
                                    counterRotatorHelper.handleClosingChanges(transitionInfo4, transaction5, change11);
                                    defaultTransitionHandler = defaultTransitionHandler2;
                                    change = change11;
                                    defaultTransitionHandler$$ExternalSyntheticLambda2 = defaultTransitionHandler$$ExternalSyntheticLambda24;
                                    z10 = false;
                                    z11 = CoreRune.MW_SHELL_TRANSITION;
                                    if (z11) {
                                    }
                                    if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
                                    }
                                    z12 = CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION;
                                    if (z12) {
                                    }
                                }
                            } else {
                                defaultTransitionHandler = defaultTransitionHandler2;
                                change = change11;
                                defaultTransitionHandler$$ExternalSyntheticLambda2 = defaultTransitionHandler$$ExternalSyntheticLambda24;
                                z10 = false;
                                z11 = CoreRune.MW_SHELL_TRANSITION;
                                if (z11) {
                                    defaultTransitionHandler.mMultiTaskingTransitProvider.getClass();
                                    if (change.shouldSkipDefaultTransition()) {
                                        Log.d("MultiTaskingTransitionProvider", "canSkipDefaultTransition: c=" + change);
                                        z18 = true;
                                    } else {
                                        z18 = false;
                                    }
                                    if (z18) {
                                        transitionInfo2 = transitionInfo;
                                        transaction3 = transaction;
                                        i5 = i21;
                                        defaultTransitionHandler$$ExternalSyntheticLambda23 = defaultTransitionHandler$$ExternalSyntheticLambda2;
                                        i6 = iM9;
                                        defaultTransitionHandler2 = this;
                                        transitionInfo3 = transitionInfo2;
                                        i11 = i6;
                                        z14 = z4;
                                        c2 = c;
                                        z15 = z8;
                                        i10 = i3;
                                        endDisplayId = i5;
                                        c3 = 6;
                                        transaction5 = transaction3;
                                        arrayList = arrayList4;
                                        arrayList2 = arrayList6;
                                    }
                                }
                                if (CoreRune.MW_SHELL_CHANGE_TRANSITION) {
                                    ChangeTransitionProvider changeTransitionProvider = defaultTransitionHandler.mChangeTransitProvider;
                                    i4 = mode2;
                                    TransitionInfo.Change change12 = change;
                                    i5 = i21;
                                    DefaultTransitionHandler$$ExternalSyntheticLambda2 defaultTransitionHandler$$ExternalSyntheticLambda25 = defaultTransitionHandler$$ExternalSyntheticLambda2;
                                    i6 = iM9;
                                    boolean zBuildChangeTransitionAnimators = changeTransitionProvider.buildChangeTransitionAnimators(arrayList4, change12, defaultTransitionHandler$$ExternalSyntheticLambda25, transaction, transitionInfo);
                                    transaction3 = transaction;
                                    change2 = change12;
                                    defaultTransitionHandler$$ExternalSyntheticLambda23 = defaultTransitionHandler$$ExternalSyntheticLambda25;
                                    transitionInfo2 = transitionInfo;
                                    if (zBuildChangeTransitionAnimators) {
                                        defaultTransitionHandler2 = this;
                                        transitionInfo3 = transitionInfo2;
                                        i11 = i6;
                                        z14 = z4;
                                        c2 = c;
                                        z15 = z8;
                                        i10 = i3;
                                        endDisplayId = i5;
                                        c3 = 6;
                                        transaction5 = transaction3;
                                        arrayList = arrayList4;
                                        arrayList2 = arrayList6;
                                    }
                                } else {
                                    transaction3 = transaction;
                                    i4 = mode2;
                                    i5 = i21;
                                    defaultTransitionHandler$$ExternalSyntheticLambda23 = defaultTransitionHandler$$ExternalSyntheticLambda2;
                                    i6 = iM9;
                                    change2 = change;
                                    transitionInfo2 = transitionInfo;
                                }
                                z12 = CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION;
                                if ((z12 || change2.getForceHidingTransit() == 0) && i4 == 6) {
                                    if (!z28 || change2.getParent() == null || transitionInfo2.getChange(change2.getParent()).getTaskInfo() == null) {
                                        if (z28) {
                                            z13 = z12;
                                        } else {
                                            z13 = z12;
                                            if (change2.getTaskInfo().configuration.windowConfiguration.getWindowingMode() == 2) {
                                            }
                                            arrayList2 = arrayList6;
                                        }
                                        if (!CoreRune.MW_EMBED_ACTIVITY_ANIMATION && change2.hasFlags(512) && !change2.hasFlags(1024)) {
                                            defaultTransitionHandler$$ExternalSyntheticLambda22 = defaultTransitionHandler$$ExternalSyntheticLambda23;
                                            i7 = i6;
                                            z26 = true;
                                        } else if (change2.getParent() != null) {
                                            int iRootIndexFor = TransitionUtil.rootIndexFor(change2, transitionInfo2);
                                            defaultTransitionHandler$$ExternalSyntheticLambda22 = defaultTransitionHandler$$ExternalSyntheticLambda23;
                                            i7 = i6;
                                            transaction3.setPosition(change2.getLeash(), change2.getEndAbsBounds().left - transitionInfo2.getRoot(iRootIndexFor).getOffset().x, change2.getEndAbsBounds().top - transitionInfo2.getRoot(iRootIndexFor).getOffset().y);
                                        } else {
                                            defaultTransitionHandler$$ExternalSyntheticLambda22 = defaultTransitionHandler$$ExternalSyntheticLambda23;
                                            i7 = i6;
                                            transaction3.setPosition(change2.getLeash(), change2.getEndRelOffset().x, change2.getEndRelOffset().y);
                                        }
                                        if (z10) {
                                            if (z28 || (change2.hasFlags(512) && !change2.hasFlags(1024))) {
                                                transaction3.setWindowCrop(change2.getLeash(), change2.getEndAbsBounds().width(), change2.getEndAbsBounds().height());
                                            }
                                            if (change2.getParent() != null || change2.hasFlags(32) || change2.getStartRotation() == change2.getEndRotation()) {
                                                transaction4 = transaction2;
                                                transaction5 = transaction3;
                                                change3 = change2;
                                                i8 = i7;
                                            } else {
                                                i9 = i7;
                                                startRotationAnimation(transaction3, change2, transitionInfo2, 0, 0, arrayList4, defaultTransitionHandler$$ExternalSyntheticLambda22, null);
                                                transaction5 = transaction3;
                                            }
                                        } else {
                                            transaction5 = transaction3;
                                            i9 = i7;
                                        }
                                        defaultTransitionHandler2 = this;
                                        transitionInfo3 = transitionInfo2;
                                        z14 = z4;
                                        arrayList = arrayList4;
                                        c2 = 4;
                                        z15 = z8;
                                        i10 = i3;
                                        endDisplayId = i5;
                                        defaultTransitionHandler$$ExternalSyntheticLambda23 = defaultTransitionHandler$$ExternalSyntheticLambda22;
                                        c3 = 6;
                                        i11 = i9;
                                        arrayList2 = arrayList6;
                                    } else {
                                        Point point = change2.getTaskInfo().positionInParent;
                                        transaction3.setPosition(change2.getLeash(), point.x, point.y);
                                        if (!change2.getEndAbsBounds().equals(transitionInfo2.getChange(change2.getParent()).getEndAbsBounds())) {
                                            transaction3.setWindowCrop(change2.getLeash(), change2.getEndAbsBounds().width(), change2.getEndAbsBounds().height());
                                        }
                                    }
                                    defaultTransitionHandler2 = this;
                                    transitionInfo3 = transitionInfo2;
                                    i11 = i6;
                                    z14 = z4;
                                    c2 = c;
                                    z15 = z8;
                                    i10 = i3;
                                    endDisplayId = i5;
                                    c3 = 6;
                                    transaction5 = transaction3;
                                    arrayList = arrayList4;
                                    arrayList2 = arrayList6;
                                } else {
                                    transaction4 = transaction2;
                                    z13 = z12;
                                    defaultTransitionHandler$$ExternalSyntheticLambda22 = defaultTransitionHandler$$ExternalSyntheticLambda23;
                                    change3 = change2;
                                    i8 = i6;
                                    transaction5 = transaction3;
                                }
                                boolean z30 = z26;
                                if (CoreRune.MW_EMBED_ACTIVITY_ANIMATION && change3.hasFlags(512)) {
                                    i12 = i5;
                                    if (i12 != change3.getEndDisplayId()) {
                                        if (i4 != 6 && !change3.hasFlags(1024)) {
                                            if (TransitionUtil.isClosingType(i4) && z30) {
                                                transaction5.hide(change3.getLeash());
                                            }
                                            TransitionInfo.AnimationOptions animationOptions2 = change3.getAnimationOptions();
                                            if (animationOptions2 == null || !isSupportedOverrideAnimation(animationOptions2)) {
                                                defaultTransitionHandler2 = this;
                                                endDisplayId = i12;
                                                transitionInfo3 = transitionInfo2;
                                                z14 = z4;
                                                arrayList = arrayList4;
                                                arrayList2 = arrayList6;
                                                z15 = z8;
                                                i10 = i3;
                                                defaultTransitionHandler$$ExternalSyntheticLambda23 = defaultTransitionHandler$$ExternalSyntheticLambda22;
                                                c3 = 6;
                                                i11 = i8;
                                                c2 = 4;
                                                z26 = z30;
                                            }
                                        } else if (i4 == 3 && change3.hasFlags(1024)) {
                                            int i23 = i18;
                                            transaction5.setWindowCrop(change3.getLeash(), i23, i23);
                                        }
                                    }
                                } else {
                                    i12 = i5;
                                }
                                if (i12 == change3.getEndDisplayId()) {
                                    if (CoreRune.FW_SHELL_TRANSITION_EXTENSION && TransitionUtil.isOpeningType(i4)) {
                                        transaction5.setAlpha(change3.getLeash(), 1.0f);
                                    }
                                    if (TransitionUtil.isClosingType(i4)) {
                                        transaction5.hide(change3.getLeash());
                                    }
                                } else {
                                    if ((z13 && change3.getForceHidingTransit() != 0) || TransitionInfo.isIndependent(change3, transitionInfo2)) {
                                        int i24 = i12;
                                        int transitionTypeFromInfo = TransitionAnimationHelper.getTransitionTypeFromInfo(transitionInfo2);
                                        TransitionInfo transitionInfo6 = transitionInfo2;
                                        TransitionInfo.Change change13 = change3;
                                        defaultTransitionHandler2 = this;
                                        int i25 = i4;
                                        boolean z31 = z4;
                                        z15 = z8;
                                        endDisplayId = i24;
                                        int i26 = i8;
                                        arrayList2 = arrayList6;
                                        Animation animationLoadAnimation = defaultTransitionHandler2.loadAnimation(transitionTypeFromInfo, transitionInfo6, change13, i3, z31);
                                        z14 = z31;
                                        if (animationLoadAnimation != null) {
                                            Context displayContext = displayController.getDisplayContext(z28 ? change13.getTaskInfo().displayId : transitionInfo6.getRoot(TransitionUtil.rootIndexFor(change13, transitionInfo6)).getDisplayId());
                                            if (displayContext != null && displayContext.getResources().getConfiguration().isScreenRound()) {
                                                animationLoadAnimation.setHasRoundedCorners(true);
                                            }
                                            boolean z32 = CoreRune.FW_SHELL_TRANSITION_BUG_FIX;
                                            if (z32) {
                                                context = displayContext;
                                                animation = animationLoadAnimation;
                                                if (TransitionUtil.isClosingType(transitionInfo6.getType()) != TransitionUtil.isClosingType(transitionTypeFromInfo)) {
                                                    transaction5.setLayer(change13.getLeash(), Transitions.calculateAnimLayer(change13, i26, transitionInfo6.getChanges().size(), transitionTypeFromInfo));
                                                }
                                            } else {
                                                context = displayContext;
                                                animation = animationLoadAnimation;
                                            }
                                            if (z28) {
                                                if (!((change13.getFlags() & 4) != 0) && TransitionUtil.isOpenOrCloseMode(i25) && TransitionUtil.isOpenOrCloseMode(transitionInfo6.getType()) && i3 == 0) {
                                                    color = ActivityThread.currentActivityThread().getSystemUiContext().getColor(R.color.tab_indicator_text_material);
                                                }
                                                if ((i3 == 2 || (CoreRune.FW_SHELL_TRANSITION_EXTENSION && i3 == 6)) && TransitionUtil.isOpeningType(transitionInfo6.getType())) {
                                                    int size = transitionInfo6.getChanges().size();
                                                    int i27 = size + 1;
                                                    if (TransitionUtil.isOpeningType(i25)) {
                                                        transaction5.setLayer(change13.getLeash(), i27 - i26);
                                                    } else if (TransitionUtil.isClosingType(i25)) {
                                                        transaction5.setLayer(change13.getLeash(), (i27 + size) - i26);
                                                    }
                                                } else if (!TransitionAnimationHelper.isCoveredByOpaqueFullscreenChange(change13, transitionInfo6) && z29 && TransitionUtil.isOpeningMode(transitionTypeFromInfo) && change13.getMode() == 4) {
                                                    rootTaskDisplayAreaOrganizer.reparentToDisplayArea(change13.getTaskInfo().displayId, transaction5, change13.getLeash());
                                                } else if (z9 && TransitionUtil.isOpeningType(transitionInfo6.getType()) && TransitionUtil.isClosingType(i25)) {
                                                    int size2 = transitionInfo6.getChanges().size();
                                                    transaction5.setLayer(change13.getLeash(), ((size2 + 1) + size2) - i26);
                                                } else if (z32 && i3 == 3 && TransitionUtil.isClosingType(transitionInfo6.getType())) {
                                                    int size3 = transitionInfo6.getChanges().size();
                                                    int i28 = size3 + 1;
                                                    if (TransitionUtil.isHomeTask(change13)) {
                                                        transaction5.setLayer(change13.getLeash(), i28 - i26);
                                                    } else if (TransitionUtil.isClosingType(i25)) {
                                                        transaction5.setLayer(change13.getLeash(), (i28 * 2) - i26);
                                                    } else if (TransitionUtil.isOpeningType(i25)) {
                                                        transaction5.setLayer(change13.getLeash(), ((i28 * 2) + size3) - i26);
                                                    }
                                                }
                                            }
                                            if (z11 && animation.hasRoundedCornerRadius()) {
                                                windowCornerRadius = animation.getRoundedCornerRadius();
                                            } else if (animation.hasRoundedCorners()) {
                                                windowCornerRadius = context == null ? 0.0f : ScreenDecorationsUtils.getWindowCornerRadius(context);
                                            } else {
                                                f = 0.0f;
                                                if (animation.getShowBackdrop()) {
                                                    if (animation.getBackdropColor() != 0) {
                                                        backgroundColor = animation.getBackdropColor();
                                                    } else {
                                                        if (change13.getBackgroundColor() != 0) {
                                                            backgroundColor = change13.getBackgroundColor();
                                                        }
                                                        i13 = color;
                                                        if ((z28 || (CoreRune.FW_SHELL_TRANSITION && !animation.hasRoundedCorners())) && animation.getExtensionEdges() != 0) {
                                                            transaction5.setEdgeExtensionEffect(change13.getLeash(), animation.getExtensionEdges());
                                                            transaction4.setEdgeExtensionEffect(change13.getLeash(), 0);
                                                        }
                                                        if (TransitionUtil.isClosingType(i25)) {
                                                            if (counterRotatorHelper.mLastRotationDelta == 0) {
                                                                rect2 = change13.getEndAbsBounds();
                                                                i14 = i3;
                                                            } else {
                                                                rect2 = new Rect(change13.getEndAbsBounds());
                                                                i14 = i3;
                                                                RotationUtils.rotateBounds(rect2, counterRotatorHelper.mLastDisplayBounds, counterRotatorHelper.mLastRotationDelta);
                                                            }
                                                            rect = new Rect(rect2);
                                                        } else {
                                                            i14 = i3;
                                                            rect = new Rect(change13.getEndAbsBounds());
                                                        }
                                                        rect.offsetTo(0, 0);
                                                        TransitionInfo.Root root = transitionInfo6.getRoot(TransitionUtil.rootIndexFor(change13, transitionInfo6));
                                                        Point point2 = new Point(change13.getEndAbsBounds().left - root.getOffset().x, change13.getEndAbsBounds().top - root.getOffset().y);
                                                        z16 = change13.getActivityComponent() != null;
                                                        if (z16) {
                                                            z17 = z16;
                                                            point2.x = Math.max(point2.x, change13.getEndRelOffset().x);
                                                            point2.y = Math.max(point2.y, change13.getEndRelOffset().y);
                                                        } else {
                                                            z17 = z16;
                                                        }
                                                        if (z17 && !z6) {
                                                            if (!(counterRotatorHelper.mLastRotationDelta == 0 && counterRotatorHelper.mRotatorMap.containsKey(change13.getParent()))) {
                                                                int iCalculateAnimLayer = Transitions.calculateAnimLayer(change13, i26, transitionInfo.getChanges().size(), transitionInfo.getType());
                                                                SurfaceControl surfaceControlBuild = new SurfaceControl.Builder().setName("Transition ActivityWrap: " + change13.getActivityComponent().toShortString()).setParent(root.getLeash()).setContainerLayer().build();
                                                                transaction5.setCrop(surfaceControlBuild, rect);
                                                                transaction5.setPosition(surfaceControlBuild, (float) point2.x, (float) point2.y);
                                                                transaction5.setLayer(surfaceControlBuild, iCalculateAnimLayer);
                                                                transaction5.show(surfaceControlBuild);
                                                                transaction5.reparent(change13.getLeash(), surfaceControlBuild);
                                                                transaction5.setPosition(change13.getLeash(), 0.0f, 0.0f);
                                                                point2.set(0, 0);
                                                                transaction4.reparent(surfaceControlBuild, null);
                                                                surfaceControlBuild.release();
                                                            }
                                                        }
                                                        SurfaceControl leash = change13.getLeash();
                                                        TransitionAnimationHelper.RoundedContentPerDisplay roundedContentPerDisplay = (z28 || z17) ? (TransitionAnimationHelper.RoundedContentPerDisplay) defaultTransitionHandler2.mRoundedContentBounds.mPerDisplay.get(change13.getEndDisplayId()) : null;
                                                        float f2 = f;
                                                        Animation animation4 = animation;
                                                        DefaultTransitionHandler$$ExternalSyntheticLambda2 defaultTransitionHandler$$ExternalSyntheticLambda26 = defaultTransitionHandler$$ExternalSyntheticLambda22;
                                                        DefaultSurfaceAnimator.buildSurfaceAnimation(arrayList4, animation4, leash, defaultTransitionHandler$$ExternalSyntheticLambda26, defaultTransitionHandler2.mTransactionPool, defaultTransitionHandler2.mMainExecutor, point2, f2, rect, roundedContentPerDisplay);
                                                        defaultTransitionHandler$$ExternalSyntheticLambda23 = defaultTransitionHandler$$ExternalSyntheticLambda26;
                                                        animationOptions = change13.getAnimationOptions();
                                                        if (animationOptions == null) {
                                                            boolean zIsOpeningType = TransitionUtil.isOpeningType(change13.getMode());
                                                            boolean zIsClosingType = TransitionUtil.isClosingType(change13.getMode());
                                                            if (zIsOpeningType) {
                                                                if (animationOptions.getType() == 12) {
                                                                    Rect endAbsBounds = change13.getEndAbsBounds();
                                                                    Drawable drawable = change13.hasFlags(4096) ? defaultTransitionHandler2.mContext.getDrawable(R.drawable.ic_corp_badge_no_background) : change13.hasFlags(8192) ? defaultTransitionHandler2.mEnterpriseThumbnailDrawable : null;
                                                                    if (drawable != null && (hardwareBufferCreateCrossProfileAppsThumbnail = defaultTransitionHandler2.mTransitionAnimation.createCrossProfileAppsThumbnail(drawable, endAbsBounds)) != null) {
                                                                        SurfaceControl.Transaction transactionAcquire = defaultTransitionHandler2.mTransactionPool.acquire();
                                                                        WindowThumbnail windowThumbnailCreateAndAttach = WindowThumbnail.createAndAttach(change13.getLeash(), hardwareBufferCreateCrossProfileAppsThumbnail, transactionAcquire);
                                                                        Animation animationCreateCrossProfileAppsThumbnailAnimationLocked = defaultTransitionHandler2.mTransitionAnimation.createCrossProfileAppsThumbnailAnimationLocked(endAbsBounds);
                                                                        if (animationCreateCrossProfileAppsThumbnailAnimationLocked != null) {
                                                                            transitionInfo3 = transitionInfo;
                                                                            animation2 = animation4;
                                                                            i10 = i14;
                                                                            change4 = change13;
                                                                            DefaultTransitionHandler$$ExternalSyntheticLambda6 defaultTransitionHandler$$ExternalSyntheticLambda6 = new DefaultTransitionHandler$$ExternalSyntheticLambda6(defaultTransitionHandler2, windowThumbnailCreateAndAttach, transactionAcquire, defaultTransitionHandler$$ExternalSyntheticLambda23, 1);
                                                                            defaultTransitionHandler2 = defaultTransitionHandler2;
                                                                            defaultTransitionHandler$$ExternalSyntheticLambda23 = defaultTransitionHandler$$ExternalSyntheticLambda23;
                                                                            animationCreateCrossProfileAppsThumbnailAnimationLocked.restrictDuration(3000L);
                                                                            animationCreateCrossProfileAppsThumbnailAnimationLocked.scaleCurrentDuration(defaultTransitionHandler2.mTransitionAnimationScaleSetting);
                                                                            DefaultSurfaceAnimator.buildSurfaceAnimation(arrayList4, animationCreateCrossProfileAppsThumbnailAnimationLocked, windowThumbnailCreateAndAttach.mSurfaceControl, defaultTransitionHandler$$ExternalSyntheticLambda6, defaultTransitionHandler2.mTransactionPool, defaultTransitionHandler2.mMainExecutor, change4.getEndRelOffset(), f2, change4.getEndAbsBounds(), null);
                                                                        }
                                                                    }
                                                                    transitionInfo3 = transitionInfo;
                                                                    animation2 = animation4;
                                                                    i10 = i14;
                                                                    if (!CoreRune.FW_SHELL_TRANSITION || !animation2.isSystemAnimation()) {
                                                                        i15 = i13;
                                                                        i11 = i26;
                                                                        arrayList = arrayList4;
                                                                    } else if (CoreRune.FW_SHELL_TRANSITION_WITH_DIM && change13.isTransitionWithDim()) {
                                                                        DimTransitionProvider dimTransitionProvider = defaultTransitionHandler2.mDimTransitionProvider;
                                                                        Context context2 = defaultTransitionHandler2.mContext;
                                                                        float f3 = defaultTransitionHandler2.mTransitionAnimationScaleSetting;
                                                                        dimTransitionProvider.getClass();
                                                                        if (i10 != 0) {
                                                                            animation3 = null;
                                                                        } else {
                                                                            Animation animationLoadDimAnimation = TransitionAnimation.loadDimAnimation(context2, TransitionUtil.isOpeningType(transitionInfo3.getType()) ? 1 : 2);
                                                                            if (animationLoadDimAnimation != null) {
                                                                                animationLoadDimAnimation.scaleCurrentDuration(f3);
                                                                            }
                                                                            animation3 = animationLoadDimAnimation;
                                                                        }
                                                                        if (animation3 != null) {
                                                                            int iRootIndexFor2 = TransitionUtil.rootIndexFor(change13, transitionInfo3);
                                                                            DimTransitionProvider dimTransitionProvider2 = defaultTransitionHandler2.mDimTransitionProvider;
                                                                            SurfaceControl leash2 = transitionInfo3.getRoot(iRootIndexFor2).getLeash();
                                                                            SurfaceControl leash3 = change13.getLeash();
                                                                            dimTransitionProvider2.getClass();
                                                                            final SurfaceControl surfaceControlBuild2 = new SurfaceControl.Builder().setParent(leash2).setColorLayer().setName("DimTransitionLayer for " + leash3).setCallsite("DimTransitionProvider#attachDimTransitionSurface").build();
                                                                            transaction5.setRelativeLayer(surfaceControlBuild2, leash3, -1);
                                                                            transaction5.setAlpha(surfaceControlBuild2, 0.0f);
                                                                            transaction5.show(surfaceControlBuild2);
                                                                            if (surfaceControlBuild2.isValid()) {
                                                                                transaction4.reparent(surfaceControlBuild2, null);
                                                                            }
                                                                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                                                                                i15 = i13;
                                                                                i11 = i26;
                                                                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -7776487191538551671L, 0, String.valueOf(change13));
                                                                            } else {
                                                                                i15 = i13;
                                                                                i11 = i26;
                                                                            }
                                                                            DefaultSurfaceAnimator.buildSurfaceAnimation(arrayList4, animation3, surfaceControlBuild2, new Runnable() { // from class: com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda3
                                                                                @Override // java.lang.Runnable
                                                                                public final void run() {
                                                                                    SurfaceControl surfaceControl = surfaceControlBuild2;
                                                                                    DefaultTransitionHandler$$ExternalSyntheticLambda2 defaultTransitionHandler$$ExternalSyntheticLambda27 = defaultTransitionHandler$$ExternalSyntheticLambda23;
                                                                                    surfaceControl.release();
                                                                                    defaultTransitionHandler$$ExternalSyntheticLambda27.run();
                                                                                }
                                                                            }, defaultTransitionHandler2.mTransactionPool, defaultTransitionHandler2.mMainExecutor, null, 0.0f, null, null);
                                                                        }
                                                                        arrayList = arrayList4;
                                                                        if (CoreRune.FW_SHELL_TRANSITION_EXTENSION_APP_BACKGROUND_LAYER) {
                                                                            SurfaceControl surfaceControlBuild3 = new SurfaceControl.Builder().setName("app-background-layer").setParent(change13.getLeash()).setCallsite("DefaultTransitionHandler").setColorLayer().build();
                                                                            transaction5.setRelativeLayer(surfaceControlBuild3, change13.getLeash(), -1).setWindowCrop(surfaceControlBuild3, change13.getEndAbsBounds().width(), change13.getEndAbsBounds().height()).show(surfaceControlBuild3);
                                                                            transaction4.remove(surfaceControlBuild3);
                                                                        }
                                                                    } else {
                                                                        i15 = i13;
                                                                        i11 = i26;
                                                                        arrayList = arrayList4;
                                                                        if (CoreRune.FW_SHELL_TRANSITION_EXTENSION_APP_BACKGROUND_LAYER && change13.getAllowAppBackgroundLayer() && TransitionUtil.isOpeningType(transitionInfo3.getType()) == TransitionUtil.isOpeningType(change13.getMode())) {
                                                                            SurfaceControl surfaceControlBuild32 = new SurfaceControl.Builder().setName("app-background-layer").setParent(change13.getLeash()).setCallsite("DefaultTransitionHandler").setColorLayer().build();
                                                                            transaction5.setRelativeLayer(surfaceControlBuild32, change13.getLeash(), -1).setWindowCrop(surfaceControlBuild32, change13.getEndAbsBounds().width(), change13.getEndAbsBounds().height()).show(surfaceControlBuild32);
                                                                            transaction4.remove(surfaceControlBuild32);
                                                                        }
                                                                    }
                                                                    color = i15;
                                                                    z26 = z30;
                                                                    c2 = 4;
                                                                    c3 = 6;
                                                                } else {
                                                                    transitionInfo3 = transitionInfo;
                                                                    animation2 = animation4;
                                                                    i10 = i14;
                                                                    change4 = change13;
                                                                    if (animationOptions.getType() == 3) {
                                                                        change13 = change4;
                                                                        defaultTransitionHandler2.attachThumbnailAnimation(arrayList4, defaultTransitionHandler$$ExternalSyntheticLambda23, change13, animationOptions, f2);
                                                                        defaultTransitionHandler2 = this;
                                                                        if (!CoreRune.FW_SHELL_TRANSITION) {
                                                                            i15 = i13;
                                                                            i11 = i26;
                                                                            arrayList = arrayList4;
                                                                            color = i15;
                                                                            z26 = z30;
                                                                            c2 = 4;
                                                                            c3 = 6;
                                                                        }
                                                                    } else {
                                                                        defaultTransitionHandler2 = this;
                                                                    }
                                                                }
                                                                change13 = change4;
                                                                if (!CoreRune.FW_SHELL_TRANSITION) {
                                                                }
                                                            } else {
                                                                transitionInfo3 = transitionInfo;
                                                                animation2 = animation4;
                                                                i10 = i14;
                                                                if (zIsClosingType && animationOptions.getType() == 4) {
                                                                    defaultTransitionHandler2 = this;
                                                                    defaultTransitionHandler2.attachThumbnailAnimation(arrayList4, defaultTransitionHandler$$ExternalSyntheticLambda23, change13, animationOptions, f2);
                                                                } else {
                                                                    defaultTransitionHandler2 = this;
                                                                }
                                                                if (!CoreRune.FW_SHELL_TRANSITION) {
                                                                }
                                                            }
                                                        } else {
                                                            transitionInfo3 = transitionInfo;
                                                            animation2 = animation4;
                                                            i10 = i14;
                                                            if (!CoreRune.FW_SHELL_TRANSITION) {
                                                            }
                                                        }
                                                    }
                                                    i13 = backgroundColor;
                                                    if (z28) {
                                                        transaction5.setEdgeExtensionEffect(change13.getLeash(), animation.getExtensionEdges());
                                                        transaction4.setEdgeExtensionEffect(change13.getLeash(), 0);
                                                        if (TransitionUtil.isClosingType(i25)) {
                                                        }
                                                        rect.offsetTo(0, 0);
                                                        TransitionInfo.Root root2 = transitionInfo6.getRoot(TransitionUtil.rootIndexFor(change13, transitionInfo6));
                                                        Point point22 = new Point(change13.getEndAbsBounds().left - root2.getOffset().x, change13.getEndAbsBounds().top - root2.getOffset().y);
                                                        if (change13.getActivityComponent() != null) {
                                                        }
                                                        if (z16) {
                                                        }
                                                        if (z17) {
                                                        }
                                                        SurfaceControl leash4 = change13.getLeash();
                                                        if (z28) {
                                                        }
                                                    } else {
                                                        transaction5.setEdgeExtensionEffect(change13.getLeash(), animation.getExtensionEdges());
                                                        transaction4.setEdgeExtensionEffect(change13.getLeash(), 0);
                                                        if (TransitionUtil.isClosingType(i25)) {
                                                        }
                                                        rect.offsetTo(0, 0);
                                                        TransitionInfo.Root root22 = transitionInfo6.getRoot(TransitionUtil.rootIndexFor(change13, transitionInfo6));
                                                        Point point222 = new Point(change13.getEndAbsBounds().left - root22.getOffset().x, change13.getEndAbsBounds().top - root22.getOffset().y);
                                                        if (change13.getActivityComponent() != null) {
                                                        }
                                                        if (z16) {
                                                        }
                                                        if (z17) {
                                                            if (counterRotatorHelper.mLastRotationDelta == 0) {
                                                                if (!(counterRotatorHelper.mLastRotationDelta == 0 && counterRotatorHelper.mRotatorMap.containsKey(change13.getParent()))) {
                                                                }
                                                            }
                                                        }
                                                        SurfaceControl leash42 = change13.getLeash();
                                                        if (z28) {
                                                            float f22 = f;
                                                            Animation animation42 = animation;
                                                            DefaultTransitionHandler$$ExternalSyntheticLambda2 defaultTransitionHandler$$ExternalSyntheticLambda262 = defaultTransitionHandler$$ExternalSyntheticLambda22;
                                                            DefaultSurfaceAnimator.buildSurfaceAnimation(arrayList4, animation42, leash42, defaultTransitionHandler$$ExternalSyntheticLambda262, defaultTransitionHandler2.mTransactionPool, defaultTransitionHandler2.mMainExecutor, point222, f22, rect, roundedContentPerDisplay);
                                                            defaultTransitionHandler$$ExternalSyntheticLambda23 = defaultTransitionHandler$$ExternalSyntheticLambda262;
                                                            animationOptions = change13.getAnimationOptions();
                                                            if (animationOptions == null) {
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    i13 = color;
                                                    if (z28) {
                                                    }
                                                }
                                            }
                                            f = windowCornerRadius;
                                            if (animation.getShowBackdrop()) {
                                            }
                                        } else {
                                            transitionInfo3 = transitionInfo6;
                                            i10 = i3;
                                            arrayList = arrayList4;
                                            defaultTransitionHandler$$ExternalSyntheticLambda23 = defaultTransitionHandler$$ExternalSyntheticLambda22;
                                            i11 = i26;
                                            if (CoreRune.FW_SHELL_TRANSITION_EXTENSION) {
                                                c3 = 6;
                                                c2 = 4;
                                                if (i10 == 6 && change13.hasFlags(4)) {
                                                    int size4 = transitionInfo3.getChanges().size();
                                                    int i29 = ((size4 + 1) + size4) - i11;
                                                    if (TransitionUtil.isOpeningType(i25)) {
                                                        transaction5.setAlpha(change13.getLeash(), 1.0f);
                                                    } else if (TransitionUtil.isOrderOnly(change13)) {
                                                        transaction5.setLayer(change13.getLeash(), i29);
                                                    }
                                                }
                                            } else {
                                                c2 = 4;
                                                c3 = 6;
                                            }
                                        }
                                    }
                                    z26 = z30;
                                }
                                defaultTransitionHandler2 = this;
                                endDisplayId = i12;
                                transitionInfo3 = transitionInfo2;
                                z14 = z4;
                                arrayList = arrayList4;
                                arrayList2 = arrayList6;
                                z15 = z8;
                                i10 = i3;
                                defaultTransitionHandler$$ExternalSyntheticLambda23 = defaultTransitionHandler$$ExternalSyntheticLambda22;
                                c3 = 6;
                                i11 = i8;
                                c2 = 4;
                                z26 = z30;
                            }
                            arrayList4 = arrayList;
                            i = i10;
                            transitionInfo4 = transitionInfo3;
                            z5 = z9;
                            arrayList5 = arrayList2;
                            z4 = z14;
                            i18 = -1;
                            iM9 = i11 - 1;
                            i21 = endDisplayId;
                            z3 = z15;
                        }
                        defaultTransitionHandler$$ExternalSyntheticLambda23 = defaultTransitionHandler$$ExternalSyntheticLambda24;
                        i6 = iM9;
                        defaultTransitionHandler2 = this;
                        transitionInfo3 = transitionInfo2;
                        i11 = i6;
                        z14 = z4;
                        c2 = c;
                        z15 = z8;
                        i10 = i3;
                        endDisplayId = i5;
                        c3 = 6;
                        transaction5 = transaction3;
                        arrayList = arrayList4;
                        arrayList2 = arrayList6;
                        arrayList4 = arrayList;
                        i = i10;
                        transitionInfo4 = transitionInfo3;
                        z5 = z9;
                        arrayList5 = arrayList2;
                        z4 = z14;
                        i18 = -1;
                        iM9 = i11 - 1;
                        i21 = endDisplayId;
                        z3 = z15;
                    } else {
                        if (z27 && TransitionUtil.isOpeningMode(change11.getMode())) {
                            transaction5.setAlpha(change11.getLeash(), 1.0f);
                        }
                        i5 = i21;
                        transitionInfo2 = transitionInfo4;
                        transaction3 = transaction5;
                        i3 = i;
                        z9 = z5;
                        defaultTransitionHandler$$ExternalSyntheticLambda23 = defaultTransitionHandler$$ExternalSyntheticLambda24;
                        c = 4;
                        i6 = iM9;
                        defaultTransitionHandler2 = this;
                        transitionInfo3 = transitionInfo2;
                        i11 = i6;
                        z14 = z4;
                        c2 = c;
                        z15 = z8;
                        i10 = i3;
                        endDisplayId = i5;
                        c3 = 6;
                        transaction5 = transaction3;
                        arrayList = arrayList4;
                        arrayList2 = arrayList6;
                        arrayList4 = arrayList;
                        i = i10;
                        transitionInfo4 = transitionInfo3;
                        z5 = z9;
                        arrayList5 = arrayList2;
                        z4 = z14;
                        i18 = -1;
                        iM9 = i11 - 1;
                        i21 = endDisplayId;
                        z3 = z15;
                    }
                }
                ArrayList arrayList9 = arrayList5;
                final boolean z33 = z3;
                final TransitionInfo transitionInfo7 = transitionInfo4;
                final ArrayList arrayList10 = arrayList4;
                if (color != 0) {
                    Color colorValueOf = Color.valueOf(color);
                    i2 = 0;
                    float[] fArr = {colorValueOf.red(), colorValueOf.green(), colorValueOf.blue()};
                    for (int i30 = 0; i30 < transitionInfo7.getRootCount(); i30++) {
                        int displayId = transitionInfo7.getRoot(i30).getDisplayId();
                        SurfaceControl.Builder colorLayer = new SurfaceControl.Builder().setName("animation-background").setCallsite("DefaultTransitionHandler").setColorLayer();
                        if (transitionInfo7.getChanges().stream().anyMatch(new DefaultTransitionHandler$$ExternalSyntheticLambda5())) {
                            rootTaskDisplayAreaOrganizer.attachToDisplayArea(displayId, colorLayer);
                        } else {
                            colorLayer.setParent(transitionInfo7.getRootLeash());
                        }
                        SurfaceControl surfaceControlBuild4 = colorLayer.build();
                        transaction5.setColor(surfaceControlBuild4, fArr).setLayer(surfaceControlBuild4, -1).show(surfaceControlBuild4);
                        transaction2.remove(surfaceControlBuild4);
                    }
                } else {
                    i2 = 0;
                }
                int i31 = 1;
                if (arrayList9.size() > 0) {
                    transaction5.apply(true);
                    int size5 = arrayList9.size();
                    int i32 = i2;
                    while (i32 < size5) {
                        Object obj = arrayList9.get(i32);
                        i32 += i31;
                        ((Consumer) obj).accept(transaction5);
                        i31 = 1;
                    }
                }
                transaction5.apply();
                defaultTransitionHandler2.mAnimExecutor.execute(new Runnable() { // from class: com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        DefaultTransitionHandler defaultTransitionHandler3 = this.f$0;
                        boolean z34 = z33;
                        TransitionInfo transitionInfo8 = transitionInfo7;
                        ArrayList arrayList11 = arrayList10;
                        if (z34) {
                            defaultTransitionHandler3.mInteractionJankMonitor.begin(transitionInfo8.getRoot(0).getLeash(), defaultTransitionHandler3.mContext, defaultTransitionHandler3.mAnimHandler, 128);
                        } else {
                            defaultTransitionHandler3.getClass();
                        }
                        for (int i33 = 0; i33 < arrayList11.size(); i33++) {
                            ((Animator) arrayList11.get(i33)).start();
                        }
                    }
                });
                counterRotatorHelper.cleanUp(transaction2);
                TransitionMetrics.getInstance().reportAnimationStart(iBinder);
                defaultTransitionHandler$$ExternalSyntheticLambda23.run();
                return true;
            }
        }
        transaction5.apply();
        transitionFinishCallback.onTransitionFinished(null);
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda7] */
    public final void startRotationAnimation(SurfaceControl.Transaction transaction, TransitionInfo.Change change, TransitionInfo transitionInfo, int i, int i2, final ArrayList arrayList, final DefaultTransitionHandler$$ExternalSyntheticLambda2 defaultTransitionHandler$$ExternalSyntheticLambda2, SurfaceControl.Transaction transaction2) {
        final ScreenRotationAnimation screenRotationAnimation = new ScreenRotationAnimation(this.mContext, this.mTransactionPool, transaction, change, transitionInfo.getRoot(TransitionUtil.rootIndexFor(change, transitionInfo)).getLeash(), i, i2);
        final ArrayList arrayList2 = new ArrayList(3);
        final ArrayList arrayList3 = new ArrayList(3);
        ?? r4 = new Runnable() { // from class: com.android.wm.shell.transition.DefaultTransitionHandler$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                ArrayList arrayList4 = arrayList2;
                ScreenRotationAnimation screenRotationAnimation2 = screenRotationAnimation;
                ArrayList arrayList5 = arrayList;
                ArrayList arrayList6 = arrayList3;
                DefaultTransitionHandler$$ExternalSyntheticLambda2 defaultTransitionHandler$$ExternalSyntheticLambda22 = defaultTransitionHandler$$ExternalSyntheticLambda2;
                if (arrayList4.isEmpty()) {
                    screenRotationAnimation2.mFadeInOutAnimationNeeded = false;
                    if (!CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION || screenRotationAnimation2.mFinishTransaction == null) {
                        TransactionPool transactionPool = screenRotationAnimation2.mTransactionPool;
                        SurfaceControl.Transaction transactionAcquire = transactionPool.acquire();
                        if (screenRotationAnimation2.mAnimLeash.isValid()) {
                            transactionAcquire.remove(screenRotationAnimation2.mAnimLeash);
                        }
                        SurfaceControl surfaceControl = screenRotationAnimation2.mScreenshotLayer;
                        if (surfaceControl != null && surfaceControl.isValid()) {
                            transactionAcquire.remove(screenRotationAnimation2.mScreenshotLayer);
                        }
                        SurfaceControl surfaceControl2 = screenRotationAnimation2.mBackColorSurface;
                        if (surfaceControl2 != null && surfaceControl2.isValid()) {
                            transactionAcquire.remove(screenRotationAnimation2.mBackColorSurface);
                        }
                        SurfaceControl surfaceControl3 = screenRotationAnimation2.mBackEffectSurface;
                        if (surfaceControl3 != null && surfaceControl3.isValid()) {
                            transactionAcquire.remove(screenRotationAnimation2.mBackEffectSurface);
                        }
                        transactionAcquire.apply();
                        transactionPool.release(transactionAcquire);
                    } else {
                        Slog.d("ShellTransitions", "kill " + screenRotationAnimation2 + " through finishT=" + screenRotationAnimation2.mFinishTransaction);
                        if (screenRotationAnimation2.mAnimLeash.isValid()) {
                            screenRotationAnimation2.mFinishTransaction.remove(screenRotationAnimation2.mAnimLeash);
                        }
                        SurfaceControl surfaceControl4 = screenRotationAnimation2.mScreenshotLayer;
                        if (surfaceControl4 != null && surfaceControl4.isValid()) {
                            screenRotationAnimation2.mFinishTransaction.remove(screenRotationAnimation2.mScreenshotLayer);
                        }
                        SurfaceControl surfaceControl5 = screenRotationAnimation2.mBackColorSurface;
                        if (surfaceControl5 != null && surfaceControl5.isValid()) {
                            screenRotationAnimation2.mFinishTransaction.remove(screenRotationAnimation2.mBackColorSurface);
                        }
                        SurfaceControl surfaceControl6 = screenRotationAnimation2.mBackEffectSurface;
                        if (surfaceControl6 != null && surfaceControl6.isValid()) {
                            screenRotationAnimation2.mFinishTransaction.remove(screenRotationAnimation2.mBackEffectSurface);
                        }
                        screenRotationAnimation2.mFinishTransaction = null;
                    }
                    arrayList5.removeAll(arrayList6);
                    defaultTransitionHandler$$ExternalSyntheticLambda22.run();
                }
            }
        };
        boolean z = CoreRune.FW_SHELL_TRANSITION;
        int[] iArr = null;
        if (z) {
            if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION && (change.getFlags() & VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) != 0) {
                int[] iArr2 = new int[2];
                if ((change.getFlags() & 1073741824) != 0) {
                    iArr2[0] = com.android.systemui.R.anim.multi_window_display_change_fast_exit;
                    iArr2[1] = com.android.systemui.R.anim.multi_window_display_change_fast_enter;
                } else {
                    iArr2[0] = com.android.systemui.R.anim.multi_window_display_change_exit;
                    iArr2[1] = com.android.systemui.R.anim.multi_window_display_change_enter;
                }
                iArr = iArr2;
            } else if (CoreRune.FW_SHELL_TRANSITION_DISPLAY_CHANGE && change.getAnimationOptions() != null && change.getAnimationOptions().getType() == 15) {
                iArr = new int[]{change.getAnimationOptions().getExitResId(), change.getAnimationOptions().getEnterResId()};
            }
        }
        if (!z || iArr == null) {
            screenRotationAnimation.buildAnimation(arrayList2, r4, this.mTransitionAnimationScaleSetting, this.mMainExecutor, -1, -1);
        } else {
            int[] iArr3 = iArr;
            screenRotationAnimation.buildAnimation(arrayList2, r4, this.mTransitionAnimationScaleSetting, this.mMainExecutor, iArr3[0], iArr3[1]);
        }
        for (int size = arrayList2.size() - 1; size >= 0; size--) {
            Animator animator = (Animator) arrayList2.get(size);
            arrayList3.add(animator);
            arrayList.add(animator);
        }
        if ((!CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION || transaction2 == null || (change.getFlags() & VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) == 0) && (!transitionInfo.isAnimatePendingSplitWithDisplayChange() || (i2 & 1) == 0)) {
            return;
        }
        screenRotationAnimation.mFinishTransaction = transaction2;
    }
}
