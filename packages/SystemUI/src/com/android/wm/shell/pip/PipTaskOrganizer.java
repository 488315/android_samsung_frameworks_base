package com.android.wm.shell.pip;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.PictureInPictureParams;
import android.app.RemoteAction;
import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.RotationUtils;
import android.util.Size;
import android.view.Choreographer;
import android.view.SurfaceControl;
import android.window.DesktopModeFlags;
import android.window.DisplayAreaInfo;
import android.window.TaskAppearedInfo;
import android.window.TaskSnapshot;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.ViewPager$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.keyguard.KeyguardCarrierViewController$2$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.launcher3.icons.IconProvider;
import com.android.systemui.R;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ScreenshotUtils;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.common.pip.PhoneSizeSpecSource;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.common.pip.PipMenuController;
import com.android.wm.shell.common.pip.PipPerfHintController;
import com.android.wm.shell.common.pip.PipUiEventLogger;
import com.android.wm.shell.common.pip.PipUtils;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.pip.PipSurfaceTransactionHelper;
import com.android.wm.shell.pip.PipTaskOrganizer;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.pip.phone.PhonePipMenuController;
import com.android.wm.shell.pip.phone.PipController;
import com.android.wm.shell.pip.phone.PipController$$ExternalSyntheticLambda8;
import com.android.wm.shell.pip.phone.PipMenuView;
import com.android.wm.shell.pip.phone.PipMotionHelper$$ExternalSyntheticLambda0;
import com.android.wm.shell.pip.phone.PipNaturalSwitchingHandler$$ExternalSyntheticLambda1;
import com.android.wm.shell.pip.phone.PipResizeGestureHandler$$ExternalSyntheticLambda0;
import com.android.wm.shell.pip.phone.PipResizeGestureHandler$$ExternalSyntheticLambda1;
import com.android.wm.shell.pip.phone.PipTouchHandler;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.animation.Interpolators;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.shared.pip.PipContentOverlay;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.WeakHashMap;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipTaskOrganizer implements ShellTaskOrganizer.TaskListener, DisplayController.OnDisplaysChangedListener {
    public static final int EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS = SystemProperties.getInt("persist.wm.debug.extra_content_overlay_fade_out_delay_ms", 400);
    public final Rect mAppBounds;
    public final Context mContext;
    public final int mCrossFadeAnimationDuration;
    public int mCurrentRotation;
    public SurfaceControl.Transaction mDeferredAnimEndTransaction;
    public ActivityManager.RunningTaskInfo mDeferredTaskInfo;
    public final Optional mDesktopUserRepositoriesOptional;
    public final DisplayController mDisplayController;
    public final int mEnterAnimationDuration;
    public final int mExitAnimationDuration;
    public boolean mHasFadeOut;
    public boolean mIsInSecureFolder;
    public SurfaceControl mLeash;
    public final ShellExecutor mMainExecutor;
    public int mNextRotation;
    public PipController$$ExternalSyntheticLambda8 mOnDisplayIdChangeCallback;
    public PictureInPictureParams mPictureInPictureParams;
    public final PipAnimationController mPipAnimationController;
    public final PipBoundsAlgorithm mPipBoundsAlgorithm;
    public final PipBoundsState mPipBoundsState;
    public final PipDisplayLayoutState mPipDisplayLayoutState;
    public PipResizeGestureHandler$$ExternalSyntheticLambda1 mPipFinishResizeWCTRunnable;
    public final PipMenuController mPipMenuController;
    public SurfaceControl mPipOverlay;
    public final PipParamsChangedForwarder mPipParamsChangedForwarder;
    public final PipPerfHintController mPipPerfHintController;
    public final AnonymousClass3 mPipTransactionHandler;
    final PipTransitionController.PipTransitionCallback mPipTransitionCallback;
    public final PipTransitionController mPipTransitionController;
    public final PipTransitionState mPipTransitionState;
    public final PipUiEventLogger mPipUiEventLoggerLogger;
    public final RootTaskDisplayAreaOrganizer mRootTaskDisplayAreaOrganizer;
    public final Optional mSplitScreenOptional;
    public PipContentOverlay.PipColorOverlay mStashDimOverlay;
    public PipSurfaceTransactionHelper.SurfaceControlTransactionFactory mSurfaceControlTransactionFactory;
    public final PipSurfaceTransactionHelper mSurfaceTransactionHelper;
    public Rect mSwipeSourceRectHint;
    public final PipTaskOrganizer$$ExternalSyntheticLambda0 mSwipingPipTimeout;
    public final SyncTransactionQueue mSyncTransactionQueue;
    public ActivityManager.RunningTaskInfo mTaskInfo;
    public final ShellTaskOrganizer mTaskOrganizer;
    public PipNaturalSwitchingHandler$$ExternalSyntheticLambda1 mTaskVanishedCallback;
    public WindowContainerToken mToken;
    public boolean mWaitForFixedRotation;
    public final float[] mTmpFloat9 = new float[9];
    public final LinkedList mPipLogHistory = new LinkedList();
    public final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public boolean mNeedToCheckRotation = false;
    public int mSwipingPipTaskId = -1;
    public final AnonymousClass1 mPipAnimationCallback = new AnonymousClass1();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.pip.PipTaskOrganizer$1, reason: invalid class name */
    public class AnonymousClass1 extends PipAnimationController.PipAnimationCallback {
        public boolean mIsCancelled;
        public PipPerfHintController.PipHighPerfSession mPipHighPerfSession;

        public AnonymousClass1() {
        }

        @Override // com.android.wm.shell.pip.PipAnimationController.PipAnimationCallback
        public final void onPipAnimationCancel(TaskInfo taskInfo, PipAnimationController.PipTransitionAnimator pipTransitionAnimator) {
            SurfaceControl surfaceControl;
            int transitionDirection = pipTransitionAnimator.getTransitionDirection();
            this.mIsCancelled = true;
            int i = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
            Log.d("PipTaskOrganizer", "onPipAnimationCancel direction=" + transitionDirection);
            boolean isInPipDirection = PipAnimationController.isInPipDirection(transitionDirection);
            PipTaskOrganizer pipTaskOrganizer = PipTaskOrganizer.this;
            if (isInPipDirection && (surfaceControl = pipTaskOrganizer.mPipOverlay) != null) {
                pipTaskOrganizer.fadeOutAndRemoveOverlay(surfaceControl, true, -1);
            }
            pipTaskOrganizer.mPipTransitionController.sendOnPipTransitionCancelled$1(transitionDirection);
        }

        @Override // com.android.wm.shell.pip.PipAnimationController.PipAnimationCallback
        public final void onPipAnimationEnd(TaskInfo taskInfo, final SurfaceControl.Transaction transaction, PipAnimationController.PipTransitionAnimator pipTransitionAnimator) {
            SurfaceControl surfaceControl;
            int i = 1;
            PipPerfHintController.PipHighPerfSession pipHighPerfSession = this.mPipHighPerfSession;
            if (pipHighPerfSession != null) {
                pipHighPerfSession.close();
                this.mPipHighPerfSession = null;
            }
            final int transitionDirection = pipTransitionAnimator.getTransitionDirection();
            boolean z = this.mIsCancelled;
            final PipTaskOrganizer pipTaskOrganizer = PipTaskOrganizer.this;
            if (z) {
                pipTaskOrganizer.sendOnPipTransitionFinished(transitionDirection);
                PipResizeGestureHandler$$ExternalSyntheticLambda1 pipResizeGestureHandler$$ExternalSyntheticLambda1 = pipTaskOrganizer.mPipFinishResizeWCTRunnable;
                if (pipResizeGestureHandler$$ExternalSyntheticLambda1 != null) {
                    pipResizeGestureHandler$$ExternalSyntheticLambda1.run();
                    pipTaskOrganizer.mPipFinishResizeWCTRunnable = null;
                    return;
                }
                return;
            }
            final int animationType = pipTransitionAnimator.getAnimationType();
            final Rect rect = pipTransitionAnimator.mDestinationBounds;
            int i2 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
            StringBuilder m = MutableObjectList$$ExternalSyntheticOutline0.m(transitionDirection, animationType, "onPipAnimationEnd direction=", " type", " mState=");
            m.append(pipTaskOrganizer.mPipTransitionState.mState);
            Log.d("PipTaskOrganizer", m.toString());
            if (PipAnimationController.isInPipDirection(transitionDirection) && (surfaceControl = pipTaskOrganizer.mPipOverlay) != null) {
                pipTaskOrganizer.fadeOutAndRemoveOverlay(surfaceControl, true, -1);
            }
            if (pipTaskOrganizer.mWaitForFixedRotation && animationType == 0 && transitionDirection == 2) {
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                windowContainerTransaction.scheduleFinishEnterPip(pipTaskOrganizer.mToken, rect);
                pipTaskOrganizer.mTaskOrganizer.applyTransaction(windowContainerTransaction);
                pipTaskOrganizer.mSurfaceTransactionHelper.round(transaction, pipTaskOrganizer.mLeash, pipTaskOrganizer.isInPip());
                pipTaskOrganizer.mDeferredAnimEndTransaction = transaction;
                return;
            }
            boolean z2 = PipAnimationController.isOutPipDirection(transitionDirection) || transitionDirection == 5;
            if (pipTaskOrganizer.mPipTransitionState.mState != 5 || z2) {
                transaction.addTransactionCommittedListener(pipTaskOrganizer.mMainExecutor, new SurfaceControl.TransactionCommittedListener() { // from class: com.android.wm.shell.pip.PipTaskOrganizer$1$$ExternalSyntheticLambda1
                    @Override // android.view.SurfaceControl.TransactionCommittedListener
                    public final void onTransactionCommitted() {
                        PipTaskOrganizer pipTaskOrganizer2 = PipTaskOrganizer.this;
                        int i3 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
                        PipResizeGestureHandler$$ExternalSyntheticLambda1 pipResizeGestureHandler$$ExternalSyntheticLambda12 = pipTaskOrganizer2.mPipFinishResizeWCTRunnable;
                        if (pipResizeGestureHandler$$ExternalSyntheticLambda12 != null) {
                            pipResizeGestureHandler$$ExternalSyntheticLambda12.run();
                            pipTaskOrganizer2.mPipFinishResizeWCTRunnable = null;
                        }
                    }
                });
                Runnable runnable = new Runnable() { // from class: com.android.wm.shell.pip.PipTaskOrganizer$1$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        PipTaskOrganizer.AnonymousClass1 anonymousClass1 = PipTaskOrganizer.AnonymousClass1.this;
                        SurfaceControl.Transaction transaction2 = transaction;
                        Rect rect2 = rect;
                        int i3 = transitionDirection;
                        int i4 = animationType;
                        anonymousClass1.getClass();
                        int i5 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
                        PipTaskOrganizer pipTaskOrganizer2 = PipTaskOrganizer.this;
                        pipTaskOrganizer2.finishResize(transaction2, rect2, i3, i4);
                        pipTaskOrganizer2.sendOnPipTransitionFinished(i3);
                    }
                };
                if (pipTaskOrganizer.mPipMenuController.isMenuVisible()) {
                    Choreographer.getInstance().postCallback(4, new PipTaskOrganizer$$ExternalSyntheticLambda15(pipTaskOrganizer, runnable, i), null);
                } else {
                    runnable.run();
                }
            }
        }

        @Override // com.android.wm.shell.pip.PipAnimationController.PipAnimationCallback
        public final void onPipAnimationStart(PipAnimationController.PipTransitionAnimator pipTransitionAnimator) {
            PipTaskOrganizer pipTaskOrganizer = PipTaskOrganizer.this;
            PipPerfHintController pipPerfHintController = pipTaskOrganizer.mPipPerfHintController;
            if (pipPerfHintController != null) {
                this.mPipHighPerfSession = pipPerfHintController.startSession(new PipTaskOrganizer$$ExternalSyntheticLambda3(this, 1), "PipTaskOrganizer::mPipAnimationCallback");
            }
            int transitionDirection = pipTransitionAnimator.getTransitionDirection();
            this.mIsCancelled = false;
            int i = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
            ListPopupWindow$$ExternalSyntheticOutline0.m(transitionDirection, "onPipAnimationStart direction=", "PipTaskOrganizer");
            if (transitionDirection == 2) {
                pipTaskOrganizer.mPipTransitionState.setTransitionState(3);
            }
            pipTaskOrganizer.mPipTransitionController.sendOnPipTransitionStarted$1(transitionDirection);
        }
    }

    /* JADX WARN: Type inference failed for: r4v5, types: [com.android.wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r6v0, types: [com.android.wm.shell.pip.PipTaskOrganizer$3] */
    public PipTaskOrganizer(Context context, SyncTransactionQueue syncTransactionQueue, PipTransitionState pipTransitionState, PipBoundsState pipBoundsState, PipDisplayLayoutState pipDisplayLayoutState, PipBoundsAlgorithm pipBoundsAlgorithm, PipMenuController pipMenuController, PipAnimationController pipAnimationController, PipSurfaceTransactionHelper pipSurfaceTransactionHelper, PipTransitionController pipTransitionController, PipParamsChangedForwarder pipParamsChangedForwarder, Optional<SplitScreenController> optional, Optional<PipPerfHintController> optional2, Optional<DesktopUserRepositories> optional3, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, DisplayController displayController, PipUiEventLogger pipUiEventLogger, ShellTaskOrganizer shellTaskOrganizer, ShellExecutor shellExecutor) {
        final int i = 0;
        this.mSwipingPipTimeout = new Runnable(this) { // from class: com.android.wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda0
            public final /* synthetic */ PipTaskOrganizer f$0;

            {
                this.f$0 = this;
            }

            @Override // java.lang.Runnable
            public final void run() {
                TaskAppearedInfo taskAppearedInfo;
                int i2 = i;
                PipTaskOrganizer pipTaskOrganizer = this.f$0;
                switch (i2) {
                    case 0:
                        ShellTaskOrganizer shellTaskOrganizer2 = pipTaskOrganizer.mTaskOrganizer;
                        int i3 = pipTaskOrganizer.mSwipingPipTaskId;
                        synchronized (shellTaskOrganizer2.mLock) {
                            taskAppearedInfo = (TaskAppearedInfo) shellTaskOrganizer2.mTasks.get(i3);
                        }
                        if (taskAppearedInfo == null) {
                            Log.w("PipTaskOrganizer", "onSwipingPipTaskTimeout: failed, cannot find info, task=" + pipTaskOrganizer.mSwipingPipTaskId);
                            return;
                        }
                        if (taskAppearedInfo.getTaskInfo().isVisible()) {
                            Log.w("PipTaskOrganizer", "onSwipingPipTaskTimeout: failed, task is visible, task=" + pipTaskOrganizer.mSwipingPipTaskId);
                            return;
                        }
                        PipTransitionState pipTransitionState2 = pipTaskOrganizer.mPipTransitionState;
                        int i4 = pipTransitionState2.mState;
                        if (i4 != 0 && i4 != 3) {
                            Log.w("PipTaskOrganizer", "onSwipingPipTaskTimeout: failed, wrong state, state=" + pipTransitionState2.mState + " task=" + pipTaskOrganizer.mSwipingPipTaskId);
                            return;
                        }
                        pipTaskOrganizer.setSwipingPipTaskId(-1, "timeout");
                        SurfaceControl leash = taskAppearedInfo.getLeash();
                        SurfaceControl.Transaction transaction = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) pipTaskOrganizer.mSurfaceControlTransactionFactory).getTransaction();
                        Log.d("PipTaskOrganizer", "onSwipingPipTaskTimeout: reset " + leash + ", " + pipTaskOrganizer.getDebuggingString());
                        transaction.setShadowRadius(leash, 0.0f);
                        transaction.apply();
                        pipTaskOrganizer.onExitPipFinished(taskAppearedInfo.getTaskInfo());
                        return;
                    default:
                        pipTaskOrganizer.mTaskOrganizer.addListenerForType(pipTaskOrganizer, -4);
                        return;
                }
            }
        };
        PipTransitionController.PipTransitionCallback pipTransitionCallback = new PipTransitionController.PipTransitionCallback() { // from class: com.android.wm.shell.pip.PipTaskOrganizer.2
            @Override // com.android.wm.shell.pip.PipTransitionController.PipTransitionCallback
            public final void onPipTransitionFinished(int i2) {
                PipTaskOrganizer pipTaskOrganizer;
                ActivityManager.RunningTaskInfo runningTaskInfo;
                if (i2 != 2 || (runningTaskInfo = (pipTaskOrganizer = PipTaskOrganizer.this).mDeferredTaskInfo) == null) {
                    return;
                }
                pipTaskOrganizer.onTaskInfoChanged(runningTaskInfo);
                pipTaskOrganizer.mDeferredTaskInfo = null;
            }

            @Override // com.android.wm.shell.pip.PipTransitionController.PipTransitionCallback
            public final void onPipTransitionCanceled(int i2) {
            }

            @Override // com.android.wm.shell.pip.PipTransitionController.PipTransitionCallback
            public final void onPipTransitionStarted(int i2, Rect rect) {
            }
        };
        this.mPipTransitionCallback = pipTransitionCallback;
        this.mPipTransactionHandler = new PipAnimationController.PipTransactionHandler() { // from class: com.android.wm.shell.pip.PipTaskOrganizer.3
            @Override // com.android.wm.shell.pip.PipAnimationController.PipTransactionHandler
            public final boolean handlePipTransaction(SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, Rect rect, float f) {
                PipTaskOrganizer pipTaskOrganizer = PipTaskOrganizer.this;
                if (!pipTaskOrganizer.mPipMenuController.isMenuVisible()) {
                    return false;
                }
                pipTaskOrganizer.mPipMenuController.movePipMenu(rect, transaction, surfaceControl);
                return true;
            }
        };
        this.mAppBounds = new Rect();
        this.mContext = context;
        this.mSyncTransactionQueue = syncTransactionQueue;
        this.mPipTransitionState = pipTransitionState;
        this.mPipBoundsState = pipBoundsState;
        this.mPipDisplayLayoutState = pipDisplayLayoutState;
        this.mPipBoundsAlgorithm = pipBoundsAlgorithm;
        this.mPipMenuController = pipMenuController;
        this.mPipTransitionController = pipTransitionController;
        this.mPipParamsChangedForwarder = pipParamsChangedForwarder;
        this.mEnterAnimationDuration = context.getResources().getInteger(R.integer.config_pipEnterAnimationDuration);
        this.mExitAnimationDuration = context.getResources().getInteger(R.integer.config_pipExitAnimationDuration);
        this.mCrossFadeAnimationDuration = context.getResources().getInteger(R.integer.config_pipCrossfadeAnimationDuration);
        this.mSurfaceTransactionHelper = pipSurfaceTransactionHelper;
        this.mPipAnimationController = pipAnimationController;
        this.mPipUiEventLoggerLogger = pipUiEventLogger;
        this.mSurfaceControlTransactionFactory = new PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory();
        this.mSplitScreenOptional = optional;
        this.mPipPerfHintController = optional2.orElse(null);
        this.mDesktopUserRepositoriesOptional = optional3;
        this.mRootTaskDisplayAreaOrganizer = rootTaskDisplayAreaOrganizer;
        this.mDisplayController = displayController;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mMainExecutor = shellExecutor;
        if (!PipUtils.isPip2ExperimentEnabled()) {
            final int i2 = 1;
            shellExecutor.execute(new Runnable(this) { // from class: com.android.wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda0
                public final /* synthetic */ PipTaskOrganizer f$0;

                {
                    this.f$0 = this;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    TaskAppearedInfo taskAppearedInfo;
                    int i22 = i2;
                    PipTaskOrganizer pipTaskOrganizer = this.f$0;
                    switch (i22) {
                        case 0:
                            ShellTaskOrganizer shellTaskOrganizer2 = pipTaskOrganizer.mTaskOrganizer;
                            int i3 = pipTaskOrganizer.mSwipingPipTaskId;
                            synchronized (shellTaskOrganizer2.mLock) {
                                taskAppearedInfo = (TaskAppearedInfo) shellTaskOrganizer2.mTasks.get(i3);
                            }
                            if (taskAppearedInfo == null) {
                                Log.w("PipTaskOrganizer", "onSwipingPipTaskTimeout: failed, cannot find info, task=" + pipTaskOrganizer.mSwipingPipTaskId);
                                return;
                            }
                            if (taskAppearedInfo.getTaskInfo().isVisible()) {
                                Log.w("PipTaskOrganizer", "onSwipingPipTaskTimeout: failed, task is visible, task=" + pipTaskOrganizer.mSwipingPipTaskId);
                                return;
                            }
                            PipTransitionState pipTransitionState2 = pipTaskOrganizer.mPipTransitionState;
                            int i4 = pipTransitionState2.mState;
                            if (i4 != 0 && i4 != 3) {
                                Log.w("PipTaskOrganizer", "onSwipingPipTaskTimeout: failed, wrong state, state=" + pipTransitionState2.mState + " task=" + pipTaskOrganizer.mSwipingPipTaskId);
                                return;
                            }
                            pipTaskOrganizer.setSwipingPipTaskId(-1, "timeout");
                            SurfaceControl leash = taskAppearedInfo.getLeash();
                            SurfaceControl.Transaction transaction = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) pipTaskOrganizer.mSurfaceControlTransactionFactory).getTransaction();
                            Log.d("PipTaskOrganizer", "onSwipingPipTaskTimeout: reset " + leash + ", " + pipTaskOrganizer.getDebuggingString());
                            transaction.setShadowRadius(leash, 0.0f);
                            transaction.apply();
                            pipTaskOrganizer.onExitPipFinished(taskAppearedInfo.getTaskInfo());
                            return;
                        default:
                            pipTaskOrganizer.mTaskOrganizer.addListenerForType(pipTaskOrganizer, -4);
                            return;
                    }
                }
            });
            pipTransitionController.mPipOrganizer = this;
            displayController.addDisplayWindowListener(this, -1);
            ((HashMap) pipTransitionController.mPipTransitionCallbacks).put(pipTransitionCallback, shellExecutor);
        }
        ShellTaskOrganizer.MultiWindowCoreStateChangeListener multiWindowCoreStateChangeListener = new ShellTaskOrganizer.MultiWindowCoreStateChangeListener() { // from class: com.android.wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda2
            @Override // com.android.wm.shell.ShellTaskOrganizer.MultiWindowCoreStateChangeListener
            public final boolean onMultiWindowCoreStateChanged(int i3) {
                int i4 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
                return (!PipTaskOrganizer.this.isInPip() || (i3 & 1) == 0 || MultiWindowCoreState.MW_ENABLED) ? false : true;
            }
        };
        shellTaskOrganizer.mMultiWindowCoreStateChangeListeners.remove(multiWindowCoreStateChangeListener);
        shellTaskOrganizer.mMultiWindowCoreStateChangeListeners.add(multiWindowCoreStateChangeListener);
        pipBoundsState.mPipTransitionState = pipTransitionState;
    }

    public static boolean isHomeVisible() {
        for (ActivityManager.RunningTaskInfo runningTaskInfo : MultiWindowManager.getInstance().getVisibleTasks()) {
            if (runningTaskInfo.getWindowingMode() == 1 && (runningTaskInfo.getActivityType() == 2 || runningTaskInfo.getActivityType() == 3)) {
                return true;
            }
        }
        return false;
    }

    public static void logRemoteActions$1(PictureInPictureParams pictureInPictureParams) {
        StringJoiner stringJoiner = new StringJoiner("|", "[", "]");
        if (pictureInPictureParams.hasSetActions()) {
            pictureInPictureParams.getActions().forEach(new PipTaskOrganizer$$ExternalSyntheticLambda3(stringJoiner, 0));
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -348090866542953596L, 0, "PipTaskOrganizer", String.valueOf(stringJoiner.toString()));
        }
    }

    public final PipAnimationController.PipTransitionAnimator animateResizePip(Rect rect, Rect rect2, Rect rect3, int i, int i2, float f) {
        Rect rect4;
        Rect rect5;
        if (this.mToken == null || this.mLeash == null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[3]) {
                ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 5014614599801648599L, 0, "PipTaskOrganizer");
            }
            return null;
        }
        if (PipAnimationController.isInPipDirection(i)) {
            rect4 = rect3;
            if (!PipBoundsAlgorithm.isSourceRectHintValidForEnterPip(rect4, rect2)) {
                rect4 = null;
            }
        } else {
            rect4 = rect3;
        }
        int deltaRotation = this.mWaitForFixedRotation ? RotationUtils.deltaRotation(this.mCurrentRotation, this.mNextRotation) : 0;
        PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        if (deltaRotation != 0) {
            if (i == 2) {
                this.mPipDisplayLayoutState.rotateTo(this.mNextRotation);
                Rect displayBounds = pipBoundsState.mPipDisplayLayoutState.getDisplayBounds();
                rect2.set(pipBoundsAlgorithm.getEntryDestinationBounds());
                RotationUtils.rotateBounds(rect2, displayBounds, this.mNextRotation, this.mCurrentRotation);
                if (rect4 != null && (rect5 = this.mTaskInfo.displayCutoutInsets) != null && deltaRotation == 3) {
                    rect4.offset(rect5.left, rect5.top);
                }
            } else if (i == 3) {
                Rect rect6 = new Rect(rect2);
                RotationUtils.rotateBounds(rect6, pipBoundsState.mPipDisplayLayoutState.getDisplayBounds(), deltaRotation);
                rect4 = PipBoundsAlgorithm.getValidSourceHintRect(this.mPictureInPictureParams, rect6);
            }
        }
        Rect rect7 = rect4;
        Rect bounds = i == 6 ? pipBoundsState.getBounds() : rect;
        PipAnimationController pipAnimationController = this.mPipAnimationController;
        PipAnimationController.PipTransitionAnimator pipTransitionAnimator = pipAnimationController.mCurrentAnimator;
        boolean z = pipTransitionAnimator != null && pipTransitionAnimator.isRunning();
        PipAnimationController.PipAnimationCallback pipAnimationCallback = z ? pipAnimationController.mCurrentAnimator.mPipAnimationCallback : null;
        PipAnimationController.PipTransitionAnimator pipTransitionAnimator2 = pipAnimationController.mCurrentAnimator;
        int transitionDirection = pipTransitionAnimator2 != null ? pipTransitionAnimator2.getTransitionDirection() : 0;
        if (z && PipAnimationController.isInPipDirection(transitionDirection)) {
            pipAnimationController.mCurrentAnimator.cancel();
        }
        PipAnimationController.PipAnimationCallback pipAnimationCallback2 = pipAnimationCallback;
        PipAnimationController.PipTransitionAnimator animator = this.mPipAnimationController.getAnimator(this.mTaskInfo, this.mLeash, bounds, rect, rect2, rect7, i, f, deltaRotation, true, pipBoundsAlgorithm.getDefaultBounds());
        PipAnimationController.PipTransitionAnimator transitionDirection2 = animator.setTransitionDirection(i);
        transitionDirection2.mPipTransactionHandler = this.mPipTransactionHandler;
        transitionDirection2.setDuration(i2);
        AnonymousClass1 anonymousClass1 = this.mPipAnimationCallback;
        if (!z) {
            animator.setPipAnimationCallback(anonymousClass1);
        }
        if (z) {
            Log.w("PipTaskOrganizer", "animateResizePip: existingAnimatorRunning, existingAnimatorCallback=" + pipAnimationCallback2);
            if (pipAnimationCallback2 == null) {
                animator.setPipAnimationCallback(anonymousClass1);
            }
            if (PipAnimationController.isInPipDirection(transitionDirection)) {
                animator.setPipAnimationCallback(anonymousClass1);
            }
        }
        if (PipAnimationController.isInPipDirection(i)) {
            if (rect7 == null) {
                ActivityInfo activityInfo = this.mTaskInfo.topActivityInfo;
                if (activityInfo != null) {
                    Context context = this.mContext;
                    animator.reattachContentOverlay(new PipContentOverlay.PipAppIconOverlay(context, rect, rect2, new IconProvider(context).getIcon(activityInfo), pipBoundsState.mLauncherState.mAppIconSizePx));
                } else {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[3]) {
                        ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 5787572776858302708L, 0, "PipTaskOrganizer");
                    }
                    animator.reattachContentOverlay(new PipContentOverlay.PipColorOverlay(this.mContext));
                }
            } else {
                TaskSnapshot taskSnapshot = PipUtils.getTaskSnapshot(this.mTaskInfo.launchIntoPipHostTaskId);
                if (taskSnapshot != null) {
                    animator.reattachContentOverlay(new PipContentOverlay.PipSnapshotOverlay(taskSnapshot, rect7));
                }
            }
            PipContentOverlay pipContentOverlay = animator.mContentOverlay;
            this.mPipOverlay = pipContentOverlay == null ? null : pipContentOverlay.mLeash;
            if (deltaRotation != 0) {
                animator.setDestinationBounds(pipBoundsAlgorithm.getEntryDestinationBounds());
            }
        }
        animator.start();
        return animator;
    }

    public final void applyEnterPipSyncTransaction(Rect rect, Runnable runnable, SurfaceControl.Transaction transaction) {
        this.mPipMenuController.attach(this.mLeash);
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.setActivityWindowingMode(this.mToken, 0);
        windowContainerTransaction.setBounds(this.mToken, rect);
        if (transaction != null) {
            windowContainerTransaction.setBoundsChangeTransaction(this.mToken, transaction);
        }
        SyncTransactionQueue syncTransactionQueue = this.mSyncTransactionQueue;
        syncTransactionQueue.queue(windowContainerTransaction);
        syncTransactionQueue.runInSync(new PipTaskOrganizer$$ExternalSyntheticLambda11(runnable, 0));
    }

    public final void applyFinishBoundsResize(int i, final WindowContainerTransaction windowContainerTransaction, final boolean z) {
        if (i == 4) {
            this.mSplitScreenOptional.ifPresent(new Consumer() { // from class: com.android.wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda10
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    PipTaskOrganizer pipTaskOrganizer = PipTaskOrganizer.this;
                    boolean z2 = z;
                    ((SplitScreenController) obj).moveToStage(pipTaskOrganizer.mTaskInfo.taskId, !z2 ? 1 : 0, windowContainerTransaction);
                }
            });
        } else {
            this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
        }
    }

    public final void applyWindowingModeChangeOnExit(WindowContainerTransaction windowContainerTransaction) {
        windowContainerTransaction.setWindowingMode(this.mToken, getOutPipWindowingMode());
        windowContainerTransaction.setActivityWindowingMode(this.mToken, 0);
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void attachChildSurfaceToTask(int i, SurfaceControl.Builder builder) {
        builder.setParent(findTaskSurface$2(i));
    }

    public final void cancelCurrentAnimator() {
        PipAnimationController pipAnimationController = this.mPipAnimationController;
        PipAnimationController.PipTransitionAnimator pipTransitionAnimator = pipAnimationController.mCurrentAnimator;
        SurfaceControl surfaceControl = this.mPipOverlay;
        if (surfaceControl != null) {
            removeContentOverlay(surfaceControl, null);
        }
        if (pipTransitionAnimator != null) {
            if (pipTransitionAnimator.mBackgroundColorApplied && this.mLeash != null) {
                Log.d("PipTaskOrganizer", "cancelCurrentAnimator unsetColor, mBackgroundColorApplied");
                pipTransitionAnimator.mBackgroundColorApplied = false;
                ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction().unsetColor(this.mLeash).apply();
            }
            pipTransitionAnimator.cancel();
            pipAnimationController.mCurrentAnimator = null;
        }
    }

    public final void clearStashDimOverlay() {
        PipContentOverlay.PipColorOverlay pipColorOverlay = this.mStashDimOverlay;
        if (pipColorOverlay != null) {
            pipColorOverlay.detach(((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction());
            this.mStashDimOverlay = null;
        }
    }

    public final SurfaceControl.Transaction createFinishResizeSurfaceTransaction(Rect rect) {
        SurfaceControl.Transaction transaction = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction();
        SurfaceControl surfaceControl = this.mLeash;
        PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
        pipSurfaceTransactionHelper.cropAndPosition(rect, transaction, surfaceControl);
        pipSurfaceTransactionHelper.resetScale(rect, transaction, this.mLeash);
        pipSurfaceTransactionHelper.round(transaction, this.mLeash, PipTransitionState.isInPip(this.mPipTransitionState.mState));
        StringBuilder sb = new StringBuilder("createFinishResizeSurfaceTransaction: destination=");
        sb.append(rect);
        sb.append(", ");
        sb.append(getDebuggingString());
        sb.append(", Callers=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(10, "PipTaskOrganizer", sb);
        return transaction;
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void dump$2(PrintWriter printWriter, String str) {
        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "  ");
        printWriter.println(str + "PipTaskOrganizer");
        printWriter.println(m + "mTaskInfo=" + this.mTaskInfo);
        StringBuilder sb = new StringBuilder();
        sb.append(m);
        sb.append("mToken=");
        sb.append(this.mToken);
        sb.append(" binder=");
        WindowContainerToken windowContainerToken = this.mToken;
        sb.append(windowContainerToken != null ? windowContainerToken.asBinder() : null);
        printWriter.println(sb.toString());
        printWriter.println(m + "mLeash=" + this.mLeash);
        printWriter.println(m + "mPipOverlay=" + this.mPipOverlay);
        StringBuilder sb2 = new StringBuilder();
        sb2.append(m);
        sb2.append("mState=");
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        sb2.append(pipTransitionState.mState);
        printWriter.println(sb2.toString());
        printWriter.println(m + "mPictureInPictureParams=" + this.mPictureInPictureParams);
        this.mPipTransitionController.dump$2(printWriter, m);
        if (this.mPipPerfHintController != null) {
            String m2 = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(m, "  ");
            printWriter.println(m + "PipPerfHintController");
            printWriter.println(m2 + "activeSessionCount=" + ((WeakHashMap) PipPerfHintController.PipHighPerfSession.sActiveSessions).size());
        }
        long j = pipTransitionState.mTaskAppearedTime;
        if (j > 0) {
            StringBuilder m3 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m, "mTaskAppearedTime=");
            m3.append(System.currentTimeMillis() - j);
            m3.append("ms");
            printWriter.println(m3.toString());
        }
        StringBuilder m4 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(m, "mPipLogHistory=");
        m4.append(this.mPipLogHistory);
        printWriter.println(m4.toString());
    }

    public void enterPipWithAlphaAnimation(final Rect rect, final long j) {
        SurfaceControl.Transaction transaction = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction();
        transaction.setAlpha(this.mLeash, 0.0f);
        transaction.apply();
        SurfaceControl.Transaction transaction2 = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction();
        SurfaceControl surfaceControl = this.mLeash;
        PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
        pipSurfaceTransactionHelper.cropAndPosition(rect, transaction2, surfaceControl);
        pipSurfaceTransactionHelper.round(transaction2, this.mLeash, true);
        this.mPipTransitionState.setTransitionState(2);
        applyEnterPipSyncTransaction(rect, new Runnable() { // from class: com.android.wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                PipTaskOrganizer pipTaskOrganizer = PipTaskOrganizer.this;
                Rect rect2 = rect;
                long j2 = j;
                PipAnimationController.PipTransitionAnimator pipAnimationCallback = pipTaskOrganizer.mPipAnimationController.getAnimator(pipTaskOrganizer.mTaskInfo, pipTaskOrganizer.mLeash, rect2, 0.0f, 1.0f).setTransitionDirection(2).setPipAnimationCallback(pipTaskOrganizer.mPipAnimationCallback);
                pipAnimationCallback.mPipTransactionHandler = pipTaskOrganizer.mPipTransactionHandler;
                pipAnimationCallback.setDuration(j2).start();
                pipTaskOrganizer.mPipTransitionState.setTransitionState(3);
            }
        }, transaction2);
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01e2  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x02ce  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x032f  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01f7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void exitPip(final int r31, boolean r32) {
        /*
            Method dump skipped, instructions count: 935
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.pip.PipTaskOrganizer.exitPip(int, boolean):void");
    }

    public final void fadeExistingPip(boolean z) {
        SurfaceControl surfaceControl = this.mLeash;
        if (surfaceControl == null || !surfaceControl.isValid()) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[3]) {
                ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -3294132111576646088L, 0, "PipTaskOrganizer", String.valueOf(this.mLeash));
            }
        } else {
            PipAnimationController.PipTransitionAnimator transitionDirection = this.mPipAnimationController.getAnimator(this.mTaskInfo, this.mLeash, this.mPipBoundsState.getBounds(), z ? 0.0f : 1.0f, z ? 1.0f : 0.0f).setTransitionDirection(1);
            transitionDirection.mPipTransactionHandler = this.mPipTransactionHandler;
            transitionDirection.setDuration(z ? this.mEnterAnimationDuration : this.mExitAnimationDuration).start();
            this.mHasFadeOut = !z;
        }
    }

    public final void fadeOutAndRemoveOverlay(final SurfaceControl surfaceControl, boolean z, int i) {
        if (surfaceControl == null || !surfaceControl.isValid()) {
            return;
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        ofFloat.setDuration(this.mCrossFadeAnimationDuration);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda12
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                PipTaskOrganizer pipTaskOrganizer = PipTaskOrganizer.this;
                SurfaceControl surfaceControl2 = surfaceControl;
                if (pipTaskOrganizer.mPipTransitionState.mState == 0) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 3979649941011313715L, 0, "PipTaskOrganizer");
                    }
                    PipAnimationController.quietCancel(valueAnimator);
                } else if (surfaceControl2.isValid()) {
                    float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    SurfaceControl.Transaction transaction = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) pipTaskOrganizer.mSurfaceControlTransactionFactory).getTransaction();
                    transaction.setAlpha(surfaceControl2, floatValue);
                    transaction.apply();
                }
            }
        });
        final Runnable runnable = null;
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.pip.PipTaskOrganizer.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                PipTaskOrganizer.this.removeContentOverlay(surfaceControl, runnable);
            }
        });
        if (CoreRune.MW_PIP_SHELL_TRANSITION && z && i != -1) {
            ofFloat.setStartDelay(i);
        } else {
            ofFloat.setStartDelay(z ? 500L : EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS);
        }
        ofFloat.start();
    }

    public final SurfaceControl findTaskSurface$2(int i) {
        SurfaceControl surfaceControl;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
        if (runningTaskInfo == null || (surfaceControl = this.mLeash) == null || runningTaskInfo.taskId != i) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "There is no surface for taskId="));
        }
        return surfaceControl;
    }

    public final void finishResize(SurfaceControl.Transaction transaction, Rect rect, int i, int i2) {
        PictureInPictureParams pictureInPictureParams;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        final Rect rect2 = new Rect(pipBoundsState.getBounds());
        pipBoundsState.setBounds(rect);
        StringBuilder sb = new StringBuilder("finishResize destinationBounds=");
        sb.append(rect);
        sb.append(" direction=");
        ViewPager$$ExternalSyntheticOutline0.m(sb, i, " type=", i2, " Callers=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(10, "PipTaskOrganizer", sb);
        boolean z = false;
        if (i == 5) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -2824616701459812156L, 0, String.valueOf(this.mTaskInfo.topActivity), String.valueOf(this.mPipTransitionState));
            }
            if (Transitions.ENABLE_SHELL_TRANSITIONS) {
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                windowContainerTransaction.setBounds(this.mToken, (Rect) null);
                windowContainerTransaction.setWindowingMode(this.mToken, getOutPipWindowingMode());
                windowContainerTransaction.reorder(this.mToken, false);
                this.mPipTransitionController.startExitTransition(1003, windowContainerTransaction, null);
                return;
            }
            try {
                WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
                windowContainerTransaction2.setBounds(this.mToken, (Rect) null);
                this.mTaskOrganizer.applyTransaction(windowContainerTransaction2);
                ActivityTaskManager.getService().removeRootTasksInWindowingModes(new int[]{2});
                return;
            } catch (RemoteException e) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[4]) {
                    ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -6126564667458047953L, 0, "PipTaskOrganizer", String.valueOf(e));
                    return;
                }
                return;
            }
        }
        if (PipAnimationController.isInPipDirection(i) && i2 == 1) {
            finishResizeForMenu(rect);
            return;
        }
        WindowContainerTransaction windowContainerTransaction3 = new WindowContainerTransaction();
        prepareFinishResizeTransaction(rect, i, transaction, windowContainerTransaction3);
        SyncTransactionQueue syncTransactionQueue = this.mSyncTransactionQueue;
        if ((i != 7 && i != 6 && i != 8) || (pictureInPictureParams = this.mPictureInPictureParams) == null || pictureInPictureParams.isSeamlessResizeEnabled()) {
            if (this.mSplitScreenOptional.isPresent() && ((SplitScreenController) this.mSplitScreenOptional.get()).getActivateSplitPosition(this.mTaskInfo) == 0) {
                z = true;
            }
            applyFinishBoundsResize(i, windowContainerTransaction3, z);
            if (i == 4) {
                syncTransactionQueue.runInSync(new PipTaskOrganizer$$ExternalSyntheticLambda11(transaction, 1));
            }
        } else {
            rect2.offsetTo(0, 0);
            final Rect rect3 = new Rect(0, 0, rect.width(), rect.height());
            SurfaceControl.Transaction transaction2 = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction();
            SurfaceControl surfaceControl = this.mLeash;
            final SurfaceControl takeScreenshot = ScreenshotUtils.takeScreenshot(transaction2, surfaceControl, surfaceControl, rect2, 2147483645);
            if (takeScreenshot != null) {
                syncTransactionQueue.queue(windowContainerTransaction3);
                syncTransactionQueue.runInSync(new SyncTransactionQueue.TransactionRunnable() { // from class: com.android.wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda13
                    @Override // com.android.wm.shell.common.SyncTransactionQueue.TransactionRunnable
                    public final void runWithTransaction(SurfaceControl.Transaction transaction3) {
                        SurfaceControl surfaceControl2 = takeScreenshot;
                        Rect rect4 = rect2;
                        Rect rect5 = rect3;
                        int i3 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
                        PipTaskOrganizer pipTaskOrganizer = PipTaskOrganizer.this;
                        PipResizeGestureHandler$$ExternalSyntheticLambda1 pipResizeGestureHandler$$ExternalSyntheticLambda1 = pipTaskOrganizer.mPipFinishResizeWCTRunnable;
                        if (pipResizeGestureHandler$$ExternalSyntheticLambda1 != null) {
                            pipResizeGestureHandler$$ExternalSyntheticLambda1.run();
                            pipTaskOrganizer.mPipFinishResizeWCTRunnable = null;
                        }
                        PipSurfaceTransactionHelper pipSurfaceTransactionHelper = pipTaskOrganizer.mSurfaceTransactionHelper;
                        pipSurfaceTransactionHelper.mTmpDestinationRectF.set(rect5);
                        pipSurfaceTransactionHelper.scale(transaction3, surfaceControl2, rect4, pipSurfaceTransactionHelper.mTmpDestinationRectF, 0.0f, true);
                        pipTaskOrganizer.fadeOutAndRemoveOverlay(surfaceControl2, false, -1);
                    }
                });
            } else {
                applyFinishBoundsResize(i, windowContainerTransaction3, false);
            }
        }
        finishResizeForMenu(rect);
    }

    public final void finishResizeForMenu(Rect rect) {
        if (isInPip()) {
            PipMenuController pipMenuController = this.mPipMenuController;
            pipMenuController.movePipMenu(rect, null, null);
            pipMenuController.updateMenuBounds(rect);
        }
    }

    public final DesktopRepository getCurrentRepo() {
        return (DesktopRepository) this.mDesktopUserRepositoriesOptional.map(new PipTaskOrganizer$$ExternalSyntheticLambda4()).orElse(null);
    }

    public final String getDebuggingString() {
        String str;
        String m;
        StringBuilder sb = new StringBuilder("PipTaskOrganizer{mState=");
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        sb.append(pipTransitionState.mState);
        sb.append(", mTaskInfo=");
        if (this.mTaskInfo == null) {
            m = "null";
        } else {
            StringBuilder sb2 = new StringBuilder("TaskInfo(taskId=");
            sb2.append(this.mTaskInfo.taskId);
            sb2.append(", isVisible=");
            sb2.append(this.mTaskInfo.isVisible);
            if (this.mTaskInfo.realActivity != null) {
                str = ", " + this.mTaskInfo.realActivity.getPackageName();
            } else {
                str = "";
            }
            m = TransitionKt$$ExternalSyntheticOutline0.m(sb2, str, ")");
        }
        sb.append(m);
        sb.append(", mLeash=");
        sb.append(this.mLeash);
        sb.append(", mWaitForFixedRotation=");
        sb.append(this.mWaitForFixedRotation);
        sb.append(", inSwipePipToHome=");
        sb.append(pipTransitionState.mInSwipePipToHomeTransition);
        sb.append(", mPictureInPictureParams=");
        sb.append(this.mPictureInPictureParams);
        sb.append("}");
        return sb.toString();
    }

    public final int getOutPipWindowingMode() {
        if (!isPipExitingToDesktopMode()) {
            return 0;
        }
        DisplayAreaInfo displayAreaInfo = this.mRootTaskDisplayAreaOrganizer.getDisplayAreaInfo(this.mTaskInfo.displayId);
        return (displayAreaInfo == null || displayAreaInfo.configuration.windowConfiguration.getWindowingMode() != 5) ? 5 : 0;
    }

    public final boolean isInPip() {
        return PipTransitionState.isInPip(this.mPipTransitionState.mState);
    }

    public final boolean isPipExitingToDesktopMode() {
        DesktopRepository currentRepo = getCurrentRepo();
        if (!DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_PIP.isTrue() || currentRepo == null) {
            return false;
        }
        if (currentRepo.isAnyDeskActive(this.mTaskInfo.displayId)) {
            return true;
        }
        DisplayAreaInfo displayAreaInfo = this.mRootTaskDisplayAreaOrganizer.getDisplayAreaInfo(this.mTaskInfo.displayId);
        return displayAreaInfo != null && displayAreaInfo.configuration.windowConfiguration.getWindowingMode() == 5;
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onDisplayConfigurationChanged(int i, Configuration configuration) {
        this.mCurrentRotation = configuration.windowConfiguration.getRotation();
    }

    public final void onEndOfSwipePipToHomeTransition() {
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            return;
        }
        Rect bounds = this.mPipBoundsState.getBounds();
        SurfaceControl surfaceControl = this.mPipOverlay;
        SurfaceControl.Transaction transaction = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction();
        SurfaceControl surfaceControl2 = this.mLeash;
        PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
        pipSurfaceTransactionHelper.resetScale(bounds, transaction, surfaceControl2);
        pipSurfaceTransactionHelper.cropAndPosition(bounds, transaction, this.mLeash);
        pipSurfaceTransactionHelper.round(transaction, this.mLeash, isInPip());
        applyEnterPipSyncTransaction(bounds, new PipTaskOrganizer$$ExternalSyntheticLambda5(this, bounds, surfaceControl), transaction);
        this.mPipTransitionState.mInSwipePipToHomeTransition = false;
        this.mPipOverlay = null;
    }

    public final void onExitPipFinished(TaskInfo taskInfo) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 7149452265929981864L, 0, String.valueOf(taskInfo.topActivity), String.valueOf(this.mPipTransitionState), String.valueOf(this.mLeash));
        }
        onExitPipFinished(taskInfo, false);
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onFixedRotationFinished(int i) {
        boolean z = ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0];
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        if (z) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 8640602037472934694L, 0, String.valueOf(this.mTaskInfo), String.valueOf(pipTransitionState));
        }
        Log.d("PipTaskOrganizer", "onFixedRotationFinished: " + getDebuggingString());
        if (this.mWaitForFixedRotation) {
            if (Transitions.ENABLE_SHELL_TRANSITIONS) {
                if (this.mNextRotation != this.mCurrentRotation) {
                    this.mNeedToCheckRotation = true;
                }
                this.mPipTransitionController.onFixedRotationFinished();
                this.mWaitForFixedRotation = false;
                this.mDeferredAnimEndTransaction = null;
                return;
            }
            int i2 = pipTransitionState.mState;
            if (i2 == 1) {
                if (pipTransitionState.mInSwipePipToHomeTransition) {
                    onEndOfSwipePipToHomeTransition();
                } else {
                    enterPipWithAlphaAnimation(this.mPipBoundsAlgorithm.getEntryDestinationBounds(), this.mEnterAnimationDuration);
                }
            } else if (i2 == 4 && this.mHasFadeOut) {
                fadeExistingPip(true);
            } else if (i2 == 3 && this.mDeferredAnimEndTransaction != null) {
                Rect rect = this.mPipAnimationController.mCurrentAnimator.mDestinationBounds;
                this.mPipBoundsState.setBounds(rect);
                applyEnterPipSyncTransaction(rect, new PipTaskOrganizer$$ExternalSyntheticLambda15(this, rect, 0), this.mDeferredAnimEndTransaction);
            }
            this.mWaitForFixedRotation = false;
            this.mDeferredAnimEndTransaction = null;
        }
    }

    @Override // com.android.wm.shell.common.DisplayController.OnDisplaysChangedListener
    public final void onFixedRotationStarted(int i, int i2) {
        boolean z = ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0];
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        if (z) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -356536982456617445L, 0, String.valueOf(this.mTaskInfo), String.valueOf(pipTransitionState));
        }
        this.mNextRotation = i2;
        this.mWaitForFixedRotation = true;
        this.mNeedToCheckRotation = false;
        StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i2, "onFixedRotationStarted: rot=", ", ");
        m.append(getDebuggingString());
        Log.d("PipTaskOrganizer", m.toString());
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            this.mPipTransitionController.onFixedRotationStarted();
        } else if (PipTransitionState.isInPip(pipTransitionState.mState)) {
            fadeExistingPip(false);
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskAppeared(ActivityManager.RunningTaskInfo runningTaskInfo, SurfaceControl surfaceControl) {
        PipController$$ExternalSyntheticLambda8 pipController$$ExternalSyntheticLambda8;
        Objects.requireNonNull(runningTaskInfo, "Requires RunningTaskInfo");
        this.mTaskInfo = runningTaskInfo;
        this.mToken = runningTaskInfo.token;
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        pipTransitionState.setTransitionState(1);
        this.mLeash = surfaceControl;
        ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mTaskInfo;
        PictureInPictureParams pictureInPictureParams = runningTaskInfo2.pictureInPictureParams;
        this.mPictureInPictureParams = pictureInPictureParams;
        ComponentName componentName = runningTaskInfo2.topActivity;
        ActivityInfo activityInfo = runningTaskInfo2.topActivityInfo;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
        pipBoundsState.setBoundsStateForEntry(componentName, activityInfo, pictureInPictureParams, pipBoundsAlgorithm);
        PictureInPictureParams pictureInPictureParams2 = this.mPictureInPictureParams;
        if (pictureInPictureParams2 != null) {
            List<RemoteAction> actions = pictureInPictureParams2.getActions();
            RemoteAction closeAction = this.mPictureInPictureParams.getCloseAction();
            PipParamsChangedForwarder pipParamsChangedForwarder = this.mPipParamsChangedForwarder;
            pipParamsChangedForwarder.notifyActionsChanged(actions, closeAction);
            CharSequence title = this.mPictureInPictureParams.getTitle();
            if (title != null) {
                title.toString();
            }
            ArrayList arrayList = (ArrayList) pipParamsChangedForwarder.mPipParamsChangedListeners;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((PipController.AnonymousClass3) obj).getClass();
            }
            CharSequence subtitle = this.mPictureInPictureParams.getSubtitle();
            if (subtitle != null) {
                subtitle.toString();
            }
            ArrayList arrayList2 = (ArrayList) pipParamsChangedForwarder.mPipParamsChangedListeners;
            int size2 = arrayList2.size();
            int i2 = 0;
            while (i2 < size2) {
                Object obj2 = arrayList2.get(i2);
                i2++;
                ((PipController.AnonymousClass3) obj2).getClass();
            }
            logRemoteActions$1(this.mPictureInPictureParams);
        }
        ActivityManager.RunningTaskInfo runningTaskInfo3 = this.mTaskInfo;
        PipUiEventLogger pipUiEventLogger = this.mPipUiEventLoggerLogger;
        pipUiEventLogger.setTaskInfo(runningTaskInfo3);
        int i3 = runningTaskInfo.displayId;
        if (i3 != this.mPipDisplayLayoutState.mDisplayId && (pipController$$ExternalSyntheticLambda8 = this.mOnDisplayIdChangeCallback) != null) {
            pipController$$ExternalSyntheticLambda8.accept(i3);
        }
        PictureInPictureParams pictureInPictureParams3 = this.mPictureInPictureParams;
        pipUiEventLogger.log((pictureInPictureParams3 == null || !pictureInPictureParams3.isLaunchIntoPip()) ? pipTransitionState.mInSwipePipToHomeTransition ? PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_AUTO_ENTER : PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_ENTER : PipUiEventLogger.PipUiEventEnum.PICTURE_IN_PICTURE_ENTER_CONTENT_PIP);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 3303783365144426993L, 0, String.valueOf(this.mTaskInfo.topActivity), String.valueOf(pipTransitionState), String.valueOf(this.mTaskInfo.taskId));
        }
        String str = "onTaskAppeared: " + getDebuggingString();
        Log.d("PipTaskOrganizer", str);
        if (this.mPipLogHistory.size() == 20) {
            this.mPipLogHistory.removeFirst();
        }
        this.mPipLogHistory.add("\n\n(" + this.mSimpleDateFormat.format(Long.valueOf(System.currentTimeMillis())) + ") " + str);
        this.mIsInSecureFolder = SemPersonaManager.isSecureFolderId(runningTaskInfo.userId);
        pipBoundsState.setStashed(0, false);
        if (CoreRune.MW_PIP_SHELL_TRANSITION) {
            setSwipingPipTaskId(-1, "appeared");
        }
        int i4 = runningTaskInfo.displayId;
        DesktopStateImpl.Companion.getClass();
        if (DesktopStateImpl.Companion.inDesktopWindowing(i4)) {
            getCurrentRepo();
            Log.w("PipTaskOrganizer", "onTaskAppeared desktopRepository is null");
        }
        if (pipTransitionState.mInSwipePipToHomeTransition) {
            if (!this.mWaitForFixedRotation) {
                onEndOfSwipePipToHomeTransition();
                return;
            }
            Log.d("PipTaskOrganizer", "Defer onTaskAppeared-SwipePipToHome until end of fixed rotation.");
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -4076966838573842892L, 0, "PipTaskOrganizer");
                return;
            }
            return;
        }
        PipAnimationController pipAnimationController = this.mPipAnimationController;
        int i5 = pipAnimationController.mOneShotAnimationType;
        if (i5 == 1) {
            pipAnimationController.mOneShotAnimationType = 0;
            if (SystemClock.uptimeMillis() - pipAnimationController.mLastOneShotAlphaAnimationTime > 800) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                    ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -4268084010746694349L, 0, null);
                }
                i5 = 0;
            }
        }
        if (Transitions.ENABLE_SHELL_TRANSITIONS) {
            this.mPipTransitionController.setEnterAnimationType(i5);
            return;
        }
        if (!this.mWaitForFixedRotation) {
            Rect entryDestinationBounds = pipBoundsAlgorithm.getEntryDestinationBounds();
            Rect bounds = this.mTaskInfo.configuration.windowConfiguration.getBounds();
            if (i5 == 0) {
                this.mPipMenuController.attach(this.mLeash);
                scheduleAnimateResizePip(bounds, entryDestinationBounds, 0.0f, PipBoundsAlgorithm.getValidSourceHintRect(runningTaskInfo.pictureInPictureParams, bounds), 2, this.mEnterAnimationDuration, null);
                pipTransitionState.setTransitionState(3);
                return;
            } else {
                if (i5 != 1) {
                    throw new RuntimeException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i5, "Unrecognized animation type: "));
                }
                enterPipWithAlphaAnimation(entryDestinationBounds, this.mEnterAnimationDuration);
                return;
            }
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1124617534213078549L, 16, String.valueOf(this.mTaskInfo.topActivity), String.valueOf(pipTransitionState), Long.valueOf(i5));
        }
        if (i5 != 1) {
            Rect bounds2 = this.mTaskInfo.configuration.windowConfiguration.getBounds();
            animateResizePip(bounds2, pipBoundsAlgorithm.getEntryDestinationBounds(), PipBoundsAlgorithm.getValidSourceHintRect(this.mPictureInPictureParams, bounds2), 2, this.mEnterAnimationDuration, 0.0f);
            pipTransitionState.setTransitionState(3);
        } else {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1784174170070791161L, 0, "PipTaskOrganizer");
            }
            SurfaceControl.Transaction transaction = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction();
            transaction.setAlpha(this.mLeash, 0.0f);
            transaction.show(this.mLeash);
            transaction.apply();
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskInfoChanged(ActivityManager.RunningTaskInfo runningTaskInfo) {
        Objects.requireNonNull(this.mToken, "onTaskInfoChanged requires valid existing mToken");
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        int i = pipTransitionState.mState;
        if (i != 4 && i != 5) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 2914282123698584728L, 4, "PipTaskOrganizer", Long.valueOf(i));
            }
            this.mDeferredTaskInfo = runningTaskInfo;
            return;
        }
        ComponentName componentName = runningTaskInfo.topActivity;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        pipBoundsState.setLastPipComponentName(componentName);
        ActivityInfo activityInfo = runningTaskInfo.topActivityInfo;
        PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
        pipBoundsState.setOverrideMinSize(pipBoundsAlgorithm.getMinimalSize(activityInfo));
        PictureInPictureParams pictureInPictureParams = runningTaskInfo.pictureInPictureParams;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 746359617845803761L, 0, String.valueOf(this.mTaskInfo.topActivity), String.valueOf(pipTransitionState), String.valueOf(this.mPictureInPictureParams), String.valueOf(pictureInPictureParams));
        }
        if (pictureInPictureParams == null || this.mPictureInPictureParams == null) {
            return;
        }
        if (this.mDeferredTaskInfo == runningTaskInfo) {
            this.mDeferredTaskInfo = null;
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(3, "PipTaskOrganizer", new StringBuilder("onTaskInfoChanged: clear deferredTaskInfo, Callers="));
        }
        if (!pictureInPictureParams.equals(this.mPictureInPictureParams)) {
            Log.d("PipTaskOrganizer", "onTaskInfoChanged: tid=" + runningTaskInfo.taskId + ", oldParams=" + this.mPictureInPictureParams + ", newParams=" + pictureInPictureParams);
        }
        float aspectRatioFloat = pictureInPictureParams.getAspectRatioFloat();
        float aspectRatioFloat2 = this.mPictureInPictureParams.getAspectRatioFloat();
        PipUtils pipUtils = PipUtils.INSTANCE;
        boolean z = Math.abs(aspectRatioFloat - aspectRatioFloat2) > 0.05f;
        PipParamsChangedForwarder pipParamsChangedForwarder = this.mPipParamsChangedForwarder;
        if (z) {
            if (pipBoundsAlgorithm.isValidPictureInPictureAspectRatio(pictureInPictureParams.getAspectRatioFloat())) {
                float aspectRatioFloat3 = pictureInPictureParams.getAspectRatioFloat();
                ArrayList arrayList = (ArrayList) pipParamsChangedForwarder.mPipParamsChangedListeners;
                int size = arrayList.size();
                int i2 = 0;
                while (i2 < size) {
                    Object obj = arrayList.get(i2);
                    i2++;
                    PipController.AnonymousClass3 anonymousClass3 = (PipController.AnonymousClass3) obj;
                    PipController pipController = PipController.this;
                    pipController.mPipBoundsState.setAspectRatio(aspectRatioFloat3);
                    PipBoundsState pipBoundsState2 = pipController.mPipBoundsState;
                    Rect transformBoundsToAspectRatioIfValid = pipController.mPipBoundsAlgorithm.transformBoundsToAspectRatioIfValid(pipBoundsState2.mAspectRatio, pipBoundsState2.getBounds(), true, false);
                    boolean equals = transformBoundsToAspectRatioIfValid.equals(pipBoundsState2.getBounds());
                    PipTouchHandler pipTouchHandler = pipController.mTouchHandler;
                    if (equals) {
                        pipTouchHandler.updatePipSizeConstraints(pipTouchHandler.mPipBoundsState.mNormalBounds, aspectRatioFloat3);
                    } else {
                        pipController.mPipTaskOrganizer.scheduleAnimateResizePip(pipController.mEnterAnimationDuration, 0, transformBoundsToAspectRatioIfValid);
                        PhonePipMenuController phonePipMenuController = pipController.mMenuController;
                        PipMenuView pipMenuView = phonePipMenuController.mPipMenuView;
                        if (pipMenuView != null) {
                            List list = phonePipMenuController.mAppActions;
                            pipMenuView.setActions(transformBoundsToAspectRatioIfValid, (list == null || list.size() <= 0) ? phonePipMenuController.mMediaActions : phonePipMenuController.mAppActions, phonePipMenuController.mCloseAction);
                        }
                        pipTouchHandler.mPipResizeGestureHandler.mUserResizeBounds.setEmpty();
                        PipController.this.updateMovementBounds(null, false, false, false, null);
                    }
                }
            } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1532372524740561787L, 44, "PipTaskOrganizer", Boolean.valueOf(pictureInPictureParams.hasSetAspectRatio()), Double.valueOf(pictureInPictureParams.getAspectRatioFloat()));
            }
        }
        if (PipUtils.remoteActionsChanged(pictureInPictureParams.getActions(), this.mPictureInPictureParams.getActions()) || !PipUtils.remoteActionsMatch(pictureInPictureParams.getCloseAction(), this.mPictureInPictureParams.getCloseAction())) {
            pipParamsChangedForwarder.notifyActionsChanged(pictureInPictureParams.getActions(), pictureInPictureParams.getCloseAction());
        }
        this.mPictureInPictureParams = pictureInPictureParams;
        logRemoteActions$1(pictureInPictureParams);
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void onTaskVanished(ActivityManager.RunningTaskInfo runningTaskInfo) {
        SurfaceControl surfaceControl;
        PipNaturalSwitchingHandler$$ExternalSyntheticLambda1 pipNaturalSwitchingHandler$$ExternalSyntheticLambda1;
        boolean z = ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0];
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        if (z) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1012745480904164740L, 0, String.valueOf(this.mTaskInfo.topActivity), String.valueOf(pipTransitionState));
        }
        if (CoreRune.MW_NATURAL_SWITCHING_PIP && (pipNaturalSwitchingHandler$$ExternalSyntheticLambda1 = this.mTaskVanishedCallback) != null) {
            pipNaturalSwitchingHandler$$ExternalSyntheticLambda1.accept(runningTaskInfo);
        }
        int i = pipTransitionState.mState;
        if (i == 0) {
            return;
        }
        boolean z2 = Transitions.ENABLE_SHELL_TRANSITIONS;
        if (z2 && i == 5) {
            return;
        }
        WindowContainerToken windowContainerToken = runningTaskInfo.token;
        Objects.requireNonNull(windowContainerToken, "Requires valid WindowContainerToken");
        if (windowContainerToken.asBinder() != this.mToken.asBinder()) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[5]) {
                ProtoLogImpl_1771455215.wtf(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -6968527793433002429L, 0, "PipTaskOrganizer", String.valueOf(windowContainerToken));
                return;
            }
            return;
        }
        boolean z3 = CoreRune.MW_PIP_SHELL_TRANSITION;
        PipTaskOrganizer$$ExternalSyntheticLambda5 pipTaskOrganizer$$ExternalSyntheticLambda5 = (z3 && (surfaceControl = this.mLeash) != null && surfaceControl.isValid() && runningTaskInfo.numActivities == 0 && pipTransitionState.mState == 3 && this.mTaskOrganizer.getRunningTaskInfo(runningTaskInfo.taskId) == null) ? new PipTaskOrganizer$$ExternalSyntheticLambda5(this, this.mLeash, runningTaskInfo) : null;
        cancelCurrentAnimator();
        onExitPipFinished(runningTaskInfo, true);
        if (z2) {
            this.mPipTransitionController.forceFinishTransition(z3 ? pipTaskOrganizer$$ExternalSyntheticLambda5 : null);
        }
    }

    public final void prepareFinishResizeTransaction(Rect rect, int i, SurfaceControl.Transaction transaction, WindowContainerTransaction windowContainerTransaction) {
        SurfaceControl surfaceControl = this.mLeash;
        if (surfaceControl == null || !surfaceControl.isValid()) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[3]) {
                ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 5269185598020532283L, 0, "PipTaskOrganizer", String.valueOf(this.mLeash));
                return;
            }
            return;
        }
        if (PipAnimationController.isInPipDirection(i)) {
            windowContainerTransaction.setActivityWindowingMode(this.mToken, 0);
        } else if (PipAnimationController.isOutPipDirection(i)) {
            applyWindowingModeChangeOnExit(windowContainerTransaction);
            rect = null;
        }
        this.mSurfaceTransactionHelper.round(transaction, this.mLeash, isInPip());
        windowContainerTransaction.setBounds(this.mToken, rect);
        if (i != 4) {
            windowContainerTransaction.setBoundsChangeTransaction(this.mToken, transaction);
        }
    }

    public final void removeContentOverlay(SurfaceControl surfaceControl, Runnable runnable) {
        boolean z = ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[3];
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        if (z) {
            ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -120396860233338433L, 0, String.valueOf(this.mTaskInfo), String.valueOf(pipTransitionState), String.valueOf(surfaceControl));
        }
        StringBuilder sb = new StringBuilder("removeContentOverlay task=");
        sb.append(this.mTaskInfo);
        sb.append(" mState=");
        sb.append(pipTransitionState.mState);
        sb.append(" surface=");
        sb.append(surfaceControl);
        sb.append(" caller=");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(5, "PipTaskOrganizer", sb);
        SurfaceControl surfaceControl2 = this.mPipOverlay;
        if (surfaceControl2 != null) {
            if (surfaceControl2 != surfaceControl && ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[3]) {
                ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 8642786175402916205L, 0, "PipTaskOrganizer", String.valueOf(surfaceControl), String.valueOf(this.mPipOverlay));
            }
            this.mPipOverlay = null;
            this.mAppBounds.setEmpty();
        }
        if (surfaceControl != null && surfaceControl.isValid()) {
            SurfaceControl.Transaction transaction = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction();
            transaction.remove(surfaceControl);
            transaction.apply();
            if (runnable != null) {
                runnable.run();
                return;
            }
            return;
        }
        Log.d("PipTaskOrganizer", "trying to remove invalid content overlay surface=" + surfaceControl);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[3]) {
            ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -7547412454914050634L, 0, "PipTaskOrganizer", String.valueOf(surfaceControl));
        }
    }

    public final void removePip() {
        SurfaceControl surfaceControl;
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        if (!PipTransitionState.isInPip(pipTransitionState.mState) || this.mToken == null || (surfaceControl = this.mLeash) == null) {
            Log.wtf("PipTaskOrganizer", "Not allowed to removePip in current state mState=" + pipTransitionState.mState + " mToken=" + this.mToken);
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[5]) {
                ProtoLogImpl_1771455215.wtf(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -6380143430162039061L, 4, "PipTaskOrganizer", Long.valueOf(pipTransitionState.mState), String.valueOf(this.mToken), String.valueOf(this.mLeash));
                return;
            }
            return;
        }
        PipAnimationController.PipTransitionAnimator transitionDirection = this.mPipAnimationController.getAnimator(this.mTaskInfo, surfaceControl, this.mPipBoundsState.getBounds(), 1.0f, 0.0f).setTransitionDirection(5);
        transitionDirection.mPipTransactionHandler = this.mPipTransactionHandler;
        PipAnimationController.PipTransitionAnimator pipAnimationCallback = transitionDirection.setPipAnimationCallback(this.mPipAnimationCallback);
        pipAnimationCallback.setDuration(this.mExitAnimationDuration);
        pipAnimationCallback.setInterpolator(Interpolators.ALPHA_OUT);
        pipAnimationCallback.start();
        pipTransitionState.setTransitionState(5);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 7643269468650160002L, 0, String.valueOf(this.mTaskInfo.topActivity), String.valueOf(pipTransitionState));
        }
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final void reparentChildSurfaceToTask(int i, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        transaction.reparent(surfaceControl, findTaskSurface$2(i));
    }

    public final void scheduleAnimateResizePip(Rect rect, Rect rect2, float f, Rect rect3, int i, int i2, PipResizeGestureHandler$$ExternalSyntheticLambda0 pipResizeGestureHandler$$ExternalSyntheticLambda0) {
        if (PipTransitionState.isInPip(this.mPipTransitionState.mState)) {
            animateResizePip(rect, rect2, rect3, i, i2, f);
            if (pipResizeGestureHandler$$ExternalSyntheticLambda0 != null) {
                pipResizeGestureHandler$$ExternalSyntheticLambda0.accept(rect2);
            }
        }
    }

    public final void scheduleFinishResizePip(Rect rect, int i, Consumer consumer) {
        SurfaceControl surfaceControl = this.mLeash;
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        if (surfaceControl == null) {
            Log.w("PipTaskOrganizer", "scheduleFinishResizePip: failed, leash is null, state=" + pipTransitionState.mState);
            return;
        }
        int i2 = pipTransitionState.mState;
        if (i2 < 3 || i2 == 5) {
            return;
        }
        if (surfaceControl == null || !surfaceControl.isValid()) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 259530626753245214L, 4, "PipTaskOrganizer", Long.valueOf(pipTransitionState.mState));
                return;
            }
            return;
        }
        finishResize(createFinishResizeSurfaceTransaction(rect), rect, i, -1);
        if (consumer != null) {
            consumer.accept(rect);
        }
    }

    public final void scheduleUserResizePip(Rect rect, Rect rect2, float f, PipMotionHelper$$ExternalSyntheticLambda0 pipMotionHelper$$ExternalSyntheticLambda0) {
        if (this.mToken == null || this.mLeash == null) {
            Log.w("PipTaskOrganizer", "Abort animation, invalid leash");
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[3]) {
                ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 5014614599801648599L, 0, "PipTaskOrganizer");
                return;
            }
            return;
        }
        if (rect.isEmpty() || rect2.isEmpty()) {
            Log.w("PipTaskOrganizer", "Attempted to user resize PIP to or from empty bounds, aborting.");
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[3]) {
                ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -3648754248347208168L, 0, "PipTaskOrganizer");
                return;
            }
            return;
        }
        SurfaceControl.Transaction transaction = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction();
        SurfaceControl surfaceControl = this.mLeash;
        PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
        pipSurfaceTransactionHelper.mTmpDestinationRectF.set(rect2);
        pipSurfaceTransactionHelper.scale(transaction, surfaceControl, rect, pipSurfaceTransactionHelper.mTmpDestinationRectF, f, true);
        pipSurfaceTransactionHelper.round(transaction, this.mLeash, rect, rect2);
        PipMenuController pipMenuController = this.mPipMenuController;
        if (pipMenuController.isMenuVisible()) {
            pipMenuController.movePipMenu(rect2, transaction, this.mLeash);
        } else {
            transaction.apply();
        }
        if (pipMotionHelper$$ExternalSyntheticLambda0 != null) {
            pipMotionHelper$$ExternalSyntheticLambda0.accept(rect2);
        }
    }

    public void sendOnPipTransitionFinished(int i) {
        if (i == 2) {
            this.mPipTransitionState.setTransitionState(4);
        }
        this.mPipTransitionController.sendOnPipTransitionFinished(i);
    }

    public final void setPipVisibility(boolean z) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ActivityManager.RunningTaskInfo runningTaskInfo = this.mTaskInfo;
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1416117663524534113L, 0, String.valueOf(runningTaskInfo != null ? runningTaskInfo.topActivity : null), String.valueOf(this.mPipTransitionState), String.valueOf(z));
        }
        if (isInPip()) {
            SurfaceControl surfaceControl = this.mLeash;
            if (surfaceControl == null || !surfaceControl.isValid()) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[3]) {
                    ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 5221631017727243831L, 0, "PipTaskOrganizer", String.valueOf(this.mLeash));
                }
            } else {
                SurfaceControl.Transaction transaction = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction();
                SurfaceControl surfaceControl2 = this.mLeash;
                float f = z ? 1.0f : 0.0f;
                this.mSurfaceTransactionHelper.getClass();
                transaction.setAlpha(surfaceControl2, f);
                transaction.apply();
            }
        }
    }

    public final void setStashDimOverlayAlpha(float f) {
        if (this.mStashDimOverlay == null && this.mLeash != null) {
            clearStashDimOverlay();
            this.mStashDimOverlay = new PipContentOverlay.PipColorOverlay(this.mContext);
            Color valueOf = Color.valueOf(this.mContext.getColor(R.color.pip_stash_dim_overlay));
            PipContentOverlay.PipColorOverlay pipColorOverlay = this.mStashDimOverlay;
            SurfaceControl.Transaction transaction = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction();
            SurfaceControl surfaceControl = this.mLeash;
            pipColorOverlay.getClass();
            Log.d("PipTaskOrganizer", "attachDimOverlay");
            transaction.show(pipColorOverlay.mLeash);
            transaction.setLayer(pipColorOverlay.mLeash, Integer.MAX_VALUE);
            transaction.setColor(pipColorOverlay.mLeash, valueOf.getComponents());
            transaction.setAlpha(pipColorOverlay.mLeash, valueOf.alpha());
            transaction.reparent(pipColorOverlay.mLeash, surfaceControl);
            transaction.apply();
        }
        if (this.mLeash != null) {
            PipContentOverlay.PipColorOverlay pipColorOverlay2 = this.mStashDimOverlay;
            SurfaceControl.Transaction transaction2 = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction();
            transaction2.setAlpha(pipColorOverlay2.mLeash, f);
            transaction2.apply();
        }
    }

    public void setSurfaceControlTransactionFactory(PipSurfaceTransactionHelper.SurfaceControlTransactionFactory surfaceControlTransactionFactory) {
        this.mSurfaceControlTransactionFactory = surfaceControlTransactionFactory;
    }

    public final void setSwipingPipTaskId(int i, String str) {
        if (this.mSwipingPipTaskId != i) {
            this.mSwipingPipTaskId = i;
            KeyguardCarrierViewController$2$$ExternalSyntheticOutline0.m(i, "setSwipingPipTaskId: ", ", reason=", str, "PipTaskOrganizer");
            HandlerExecutor handlerExecutor = (HandlerExecutor) this.mMainExecutor;
            PipTaskOrganizer$$ExternalSyntheticLambda0 pipTaskOrganizer$$ExternalSyntheticLambda0 = this.mSwipingPipTimeout;
            handlerExecutor.removeCallbacks(pipTaskOrganizer$$ExternalSyntheticLambda0);
            if (i != -1) {
                handlerExecutor.executeDelayed(pipTaskOrganizer$$ExternalSyntheticLambda0, 5000L);
            }
        }
    }

    public final boolean shouldShowSplitMenu() {
        ActivityManager.RunningTaskInfo runningTaskInfo;
        ActivityManager.RunningTaskInfo runningTaskInfo2;
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.pip_min_width);
        float f = this.mPipBoundsState.mAspectRatio;
        PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
        Size sizeForAspectRatio = ((PhoneSizeSpecSource) pipBoundsAlgorithm.mSizeSpecSource).getSizeForAspectRatio(f, ((PhoneSizeSpecSource) pipBoundsAlgorithm.mSizeSpecSource).getDefaultSize(f));
        if (sizeForAspectRatio.getWidth() < dimensionPixelSize) {
            Log.d("PipTaskOrganizer", "PIP split menu does not show. estimatedSize w=" + sizeForAspectRatio.getWidth() + " h=" + sizeForAspectRatio.getHeight());
            return false;
        }
        for (ActivityManager.RunningTaskInfo runningTaskInfo3 : MultiWindowManager.getInstance().getVisibleTasks()) {
            if (runningTaskInfo3.supportsMultiWindow && (runningTaskInfo3.getWindowingMode() == 1 || runningTaskInfo3.isSplitScreen())) {
                if ((this.mSplitScreenOptional.isPresent() && ((SplitScreenController) this.mSplitScreenOptional.get()).mSplitState.isSplitStashed()) || (runningTaskInfo = this.mTaskInfo) == null || runningTaskInfo.launchIntoPipHostTaskId != -1) {
                    return false;
                }
                int i = runningTaskInfo.lastParentTaskIdBeforePip;
                if (i == -1 || (runningTaskInfo2 = this.mTaskOrganizer.getRunningTaskInfo(i)) == null || (!runningTaskInfo2.isVisible && ((!runningTaskInfo2.isFreeform() && runningTaskInfo2.supportsMultiWindow) || runningTaskInfo2.isVisible))) {
                    return !this.mTaskInfo.supportsPipOnly;
                }
                return false;
            }
        }
        return false;
    }

    @Override // com.android.wm.shell.ShellTaskOrganizer.TaskListener
    public final boolean supportCompatUI() {
        return false;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("PipTaskOrganizer:");
        int i = ShellTaskOrganizer.$r8$clinit;
        sb.append("TASK_LISTENER_TYPE_PIP");
        return sb.toString();
    }

    public final void updateAnimatorBounds(Rect rect) {
        PipAnimationController.PipTransitionAnimator pipTransitionAnimator = this.mPipAnimationController.mCurrentAnimator;
        if (pipTransitionAnimator == null || !pipTransitionAnimator.isRunning()) {
            return;
        }
        if (pipTransitionAnimator.getAnimationType() == 0) {
            if (this.mWaitForFixedRotation) {
                Rect displayBounds = this.mPipBoundsState.mPipDisplayLayoutState.getDisplayBounds();
                Rect rect2 = new Rect(rect);
                RotationUtils.rotateBounds(rect2, displayBounds, this.mNextRotation, this.mCurrentRotation);
                pipTransitionAnimator.updateEndValue(rect2);
            } else {
                pipTransitionAnimator.updateEndValue(rect);
            }
        }
        pipTransitionAnimator.setDestinationBounds(rect);
    }

    public final void onExitPipFinished(TaskInfo taskInfo, boolean z) {
        SurfaceControl surfaceControl;
        PipController$$ExternalSyntheticLambda8 pipController$$ExternalSyntheticLambda8;
        PipController$$ExternalSyntheticLambda8 pipController$$ExternalSyntheticLambda82;
        SurfaceControl surfaceControl2;
        SurfaceControl surfaceControl3 = this.mLeash;
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        if (surfaceControl3 == null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[3]) {
                ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 3082465182618566532L, 0, null);
            }
            if (pipTransitionState.mState != 3) {
                return;
            } else {
                Log.d("PipTaskOrganizer", "onExitPipFinished: Re-set the PIP state");
            }
        }
        this.mWaitForFixedRotation = false;
        this.mDeferredAnimEndTransaction = null;
        SurfaceControl surfaceControl4 = this.mPipOverlay;
        if (surfaceControl4 != null) {
            removeContentOverlay(surfaceControl4, null);
            this.mPipOverlay = null;
        }
        if (pipTransitionState.mState != 0 && (surfaceControl2 = this.mLeash) != null && surfaceControl2.isValid()) {
            SurfaceControl.Transaction transaction = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction();
            transaction.setShadowRadius(this.mLeash, 0.0f);
            transaction.apply();
        }
        pipTransitionState.mInSwipePipToHomeTransition = false;
        this.mPictureInPictureParams = null;
        pipTransitionState.setTransitionState(0);
        Rect rect = new Rect();
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        pipBoundsState.setBounds(rect);
        this.mPipUiEventLoggerLogger.setTaskInfo(null);
        this.mPipMenuController.detach();
        Log.d("PipTaskOrganizer", "onExitPipFinished:" + getDebuggingString());
        this.mIsInSecureFolder = false;
        if (this.mStashDimOverlay != null) {
            clearStashDimOverlay();
            pipBoundsState.setStashed(0, z);
        }
        if (this.mLeash == null) {
            return;
        }
        if ((!taskInfo.isVisible || ((taskInfo.configuration.windowConfiguration.getWindowingMode() != 2 && taskInfo.configuration.windowConfiguration.getWindowingMode() != 5) || taskInfo.displayId != 0)) && (surfaceControl = this.mLeash) != null && surfaceControl.isValid() && !taskInfo.configuration.isDesktopModeEnabled()) {
            int i = taskInfo.displayId;
            DesktopStateImpl.Companion.getClass();
            if (!DesktopStateImpl.Companion.inDesktopWindowing(i)) {
                Log.d("PipTaskOrganizer", "onExitPipFinished: reset surface state with WCT");
                SurfaceControl.Transaction transaction2 = ((PipSurfaceTransactionHelper.VsyncSurfaceControlTransactionFactory) this.mSurfaceControlTransactionFactory).getTransaction();
                transaction2.setCornerRadius(this.mLeash, 0.0f);
                transaction2.setCrop(this.mLeash, null);
                transaction2.setMatrix(this.mLeash, Matrix.IDENTITY_MATRIX, this.mTmpFloat9);
                transaction2.addDebugName("ResetPip");
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                windowContainerTransaction.setBounds(this.mToken, (Rect) null);
                windowContainerTransaction.setBoundsChangeTransaction(this.mToken, transaction2);
                this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
            }
        }
        this.mLeash = null;
        if (taskInfo.displayId != 0 && (pipController$$ExternalSyntheticLambda82 = this.mOnDisplayIdChangeCallback) != null) {
            pipController$$ExternalSyntheticLambda82.accept(0);
        } else {
            if (this.mPipDisplayLayoutState.mDisplayId == 0 || (pipController$$ExternalSyntheticLambda8 = this.mOnDisplayIdChangeCallback) == null) {
                return;
            }
            pipController$$ExternalSyntheticLambda8.accept(0);
        }
    }

    public final void scheduleAnimateResizePip(int i, int i2, Rect rect) {
        if (this.mWaitForFixedRotation) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 3575265343742099329L, 0, "PipTaskOrganizer");
                return;
            }
            return;
        }
        scheduleAnimateResizePip(this.mPipBoundsState.getBounds(), rect, 0.0f, null, i2, i, null);
    }
}
