package com.android.wm.shell.transition;

import android.animation.Animator;
import android.app.ActivityManager;
import android.app.WindowConfiguration;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.policy.TransitionAnimation;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.R;
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
import com.samsung.android.rune.CoreRune;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                DefaultTransitionHandler defaultTransitionHandler = DefaultTransitionHandler.this;
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
        SurfaceControl.Transaction acquire = this.mTransactionPool.acquire();
        WindowThumbnail createAndAttach = WindowThumbnail.createAndAttach(change.getLeash(), animationOptions.getThumbnail(), acquire);
        Animation createThumbnailAspectScaleAnimationLocked = this.mTransitionAnimation.createThumbnailAspectScaleAnimationLocked(change.getEndAbsBounds(), this.mInsets, animationOptions.getThumbnail(), this.mContext.getResources().getConfiguration().orientation, (Rect) null, animationOptions.getTransitionBounds(), animationOptions.getType() == 3);
        DefaultTransitionHandler$$ExternalSyntheticLambda6 defaultTransitionHandler$$ExternalSyntheticLambda6 = new DefaultTransitionHandler$$ExternalSyntheticLambda6(this, createAndAttach, acquire, defaultTransitionHandler$$ExternalSyntheticLambda2, 0);
        createThumbnailAspectScaleAnimationLocked.restrictDuration(3000L);
        createThumbnailAspectScaleAnimationLocked.scaleCurrentDuration(this.mTransitionAnimationScaleSetting);
        DefaultSurfaceAnimator.buildSurfaceAnimation(arrayList, createThumbnailAspectScaleAnimationLocked, createAndAttach.mSurfaceControl, defaultTransitionHandler$$ExternalSyntheticLambda6, this.mTransactionPool, this.mMainExecutor, change.getEndRelOffset(), f, change.getEndAbsBounds(), null);
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

    /* JADX WARN: Removed duplicated region for block: B:123:0x02e2  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x02e8  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x02ef  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.animation.Animation loadAnimation(int r18, android.window.TransitionInfo r19, android.window.TransitionInfo.Change r20, int r21, boolean r22) {
        /*
            Method dump skipped, instructions count: 802
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.transition.DefaultTransitionHandler.loadAnimation(int, android.window.TransitionInfo, android.window.TransitionInfo$Change, int, boolean):android.view.animation.Animation");
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

    /* JADX WARN: Code restructure failed: missing block: B:108:0x0242, code lost:
    
        r10 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x02dd, code lost:
    
        if (r1.getEndDisplayId() != 0) goto L185;
     */
    /* JADX WARN: Code restructure failed: missing block: B:206:0x044e, code lost:
    
        if (r0 != false) goto L280;
     */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x0408  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0438  */
    /* JADX WARN: Removed duplicated region for block: B:209:0x045f  */
    /* JADX WARN: Removed duplicated region for block: B:275:0x0647  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x067c  */
    /* JADX WARN: Removed duplicated region for block: B:361:0x083b  */
    /* JADX WARN: Removed duplicated region for block: B:366:0x0856  */
    /* JADX WARN: Removed duplicated region for block: B:372:0x087f  */
    /* JADX WARN: Removed duplicated region for block: B:379:0x08e2  */
    /* JADX WARN: Removed duplicated region for block: B:381:0x08e7  */
    /* JADX WARN: Removed duplicated region for block: B:383:0x090a A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:390:0x0921  */
    /* JADX WARN: Removed duplicated region for block: B:394:0x0994 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:398:0x09c9  */
    /* JADX WARN: Removed duplicated region for block: B:414:0x0aae  */
    /* JADX WARN: Removed duplicated region for block: B:467:0x0906  */
    /* JADX WARN: Removed duplicated region for block: B:468:0x08e4  */
    /* JADX WARN: Removed duplicated region for block: B:470:0x08a3  */
    /* JADX WARN: Removed duplicated region for block: B:503:0x05c9  */
    /* JADX WARN: Removed duplicated region for block: B:504:0x0452  */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean startAnimation(android.os.IBinder r44, android.window.TransitionInfo r45, android.view.SurfaceControl.Transaction r46, android.view.SurfaceControl.Transaction r47, com.android.wm.shell.transition.Transitions.TransitionFinishCallback r48) {
        /*
            Method dump skipped, instructions count: 3347
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.transition.DefaultTransitionHandler.startAnimation(android.os.IBinder, android.window.TransitionInfo, android.view.SurfaceControl$Transaction, android.view.SurfaceControl$Transaction, com.android.wm.shell.transition.Transitions$TransitionFinishCallback):boolean");
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
                        SurfaceControl.Transaction acquire = transactionPool.acquire();
                        if (screenRotationAnimation2.mAnimLeash.isValid()) {
                            acquire.remove(screenRotationAnimation2.mAnimLeash);
                        }
                        SurfaceControl surfaceControl = screenRotationAnimation2.mScreenshotLayer;
                        if (surfaceControl != null && surfaceControl.isValid()) {
                            acquire.remove(screenRotationAnimation2.mScreenshotLayer);
                        }
                        SurfaceControl surfaceControl2 = screenRotationAnimation2.mBackColorSurface;
                        if (surfaceControl2 != null && surfaceControl2.isValid()) {
                            acquire.remove(screenRotationAnimation2.mBackColorSurface);
                        }
                        SurfaceControl surfaceControl3 = screenRotationAnimation2.mBackEffectSurface;
                        if (surfaceControl3 != null && surfaceControl3.isValid()) {
                            acquire.remove(screenRotationAnimation2.mBackEffectSurface);
                        }
                        acquire.apply();
                        transactionPool.release(acquire);
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
                    iArr2[0] = R.anim.multi_window_display_change_fast_exit;
                    iArr2[1] = R.anim.multi_window_display_change_fast_enter;
                } else {
                    iArr2[0] = R.anim.multi_window_display_change_exit;
                    iArr2[1] = R.anim.multi_window_display_change_enter;
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
