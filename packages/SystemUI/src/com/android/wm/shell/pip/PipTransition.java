package com.android.wm.shell.pip;

import android.app.ActivityManager;
import android.app.PictureInPictureParams;
import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Debug;
import android.os.IBinder;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.RotationUtils;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.RemoteTransition;
import android.window.TaskSnapshot;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0;
import com.android.launcher3.icons.IconProvider;
import com.android.systemui.R;
import com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.ComponentUtils;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.common.pip.PipMenuController;
import com.android.wm.shell.common.pip.PipUtils;
import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.shared.pip.PipContentOverlay;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.CounterRotatorHelper;
import com.android.wm.shell.transition.DefaultMixedHandler;
import com.android.wm.shell.transition.DefaultMixedHandler$$ExternalSyntheticLambda4;
import com.android.wm.shell.transition.DefaultMixedHandler$$ExternalSyntheticLambda5;
import com.android.wm.shell.transition.DefaultMixedTransition;
import com.android.wm.shell.transition.HomeTransitionObserver;
import com.android.wm.shell.transition.RemoteTransitionHandler;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class PipTransition extends PipTransitionController {
    public IBinder mCleanupTransition;
    public final Context mContext;
    public WindowContainerToken mCurrentPipTaskToken;
    public int mEndFixedRotation;
    public int mEnterAnimationType;
    public final int mEnterExitAnimationDuration;
    public final Rect mExitDestinationBounds;
    public IBinder mExitTransition;
    public int mExitTransitionType;
    public Transitions.TransitionFinishCallback mFinishCallback;
    public SurfaceControl.Transaction mFinishTransaction;
    public int mFixedRotationState;
    public boolean mHasFadeOut;
    public final HomeTransitionObserver mHomeTransitionObserver;
    public boolean mInEnterPipFromSplit;
    public boolean mIsDisplayChangeInExiting;
    public TransitionInfo mLeftoverTransitionInfo;
    public IBinder mMoveToBackTransition;
    public final PipAnimationController mPipAnimationController;
    public final PipDisplayLayoutState mPipDisplayLayoutState;
    public final PipTransitionState mPipTransitionState;
    public WindowContainerToken mRequestedEnterTask;
    public IBinder mRequestedEnterTransition;
    public final Optional mSplitScreenOptional;
    public SurfaceControl.Transaction mStartTransactionForRemote;
    public final PipSurfaceTransactionHelper mSurfaceTransactionHelper;
    public final AnonymousClass1 mTransactionConsumer;

    /* JADX WARN: Type inference failed for: r9v2, types: [com.android.wm.shell.pip.PipTransition$1] */
    public PipTransition(Context context, ShellInit shellInit, ShellTaskOrganizer shellTaskOrganizer, Transitions transitions, PipBoundsState pipBoundsState, PipDisplayLayoutState pipDisplayLayoutState, PipTransitionState pipTransitionState, PipMenuController pipMenuController, PipBoundsAlgorithm pipBoundsAlgorithm, PipAnimationController pipAnimationController, PipSurfaceTransactionHelper pipSurfaceTransactionHelper, HomeTransitionObserver homeTransitionObserver, Optional<SplitScreenController> optional) {
        super(shellInit, shellTaskOrganizer, transitions, pipBoundsState, pipMenuController, pipBoundsAlgorithm);
        this.mEnterAnimationType = 0;
        this.mExitDestinationBounds = new Rect();
        this.mFixedRotationState = 0;
        this.mTransactionConsumer = new PipAnimationController.PipTransactionHandler(this) { // from class: com.android.wm.shell.pip.PipTransition.1
        };
        this.mContext = context;
        this.mPipTransitionState = pipTransitionState;
        this.mPipDisplayLayoutState = pipDisplayLayoutState;
        this.mPipAnimationController = pipAnimationController;
        this.mEnterExitAnimationDuration = context.getResources().getInteger(R.integer.config_pipResizeAnimationDuration);
        this.mSurfaceTransactionHelper = pipSurfaceTransactionHelper;
        this.mHomeTransitionObserver = homeTransitionObserver;
        this.mSplitScreenOptional = optional;
    }

    public static TransitionInfo.Change getPipChange(TransitionInfo transitionInfo) {
        for (int iM = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); iM >= 0; iM--) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(iM);
            if (change.getTaskInfo() != null && change.getTaskInfo().getWindowingMode() == 2) {
                return change;
            }
        }
        return null;
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final void augmentRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo, WindowContainerTransaction windowContainerTransaction) {
        if (transitionRequestInfo.getType() != 10) {
            throw new IllegalStateException("Called PiP augmentRequest when request has no PiP");
        }
        int i = this.mEnterAnimationType;
        PipBoundsAlgorithm pipBoundsAlgorithm = this.mPipBoundsAlgorithm;
        if (i == 1) {
            this.mRequestedEnterTransition = iBinder;
            this.mRequestedEnterTask = transitionRequestInfo.getTriggerTask().token;
            windowContainerTransaction.setActivityWindowingMode(transitionRequestInfo.getTriggerTask().token, 0);
            windowContainerTransaction.setBounds(transitionRequestInfo.getTriggerTask().token, pipBoundsAlgorithm.getEntryDestinationBounds());
            return;
        }
        if (transitionRequestInfo.getPipChange() != null) {
            int i2 = transitionRequestInfo.getPipChange().getTaskInfo().displayId;
            DesktopStateImpl.Companion.getClass();
            if (DesktopStateImpl.Companion.inDesktopWindowing(i2)) {
                windowContainerTransaction.movePipActivityToPinnedRootTask(transitionRequestInfo.getPipChange().getTaskFragmentToken(), pipBoundsAlgorithm.getEntryDestinationBounds());
            }
        }
    }

    public final void callFinishCallback(WindowContainerTransaction windowContainerTransaction) {
        boolean z;
        Transitions.TransitionFinishCallback transitionFinishCallback = this.mFinishCallback;
        this.mFinishCallback = null;
        if (CoreRune.MW_PIP_SHELL_TRANSITION && (z = this.mInEnterPipFromSplit)) {
            if (z) {
                this.mInEnterPipFromSplit = false;
                Log.d("PipTransition", "onFinishEnterPipFromSplit: " + windowContainerTransaction);
                this.mShellTaskOrganizer.applyTransaction(windowContainerTransaction);
            }
            this.mLeftoverTransitionInfo = null;
            windowContainerTransaction = null;
        }
        if (CoreRune.MW_PIP_REMOTE_TRANSITION) {
            this.mStartTransactionForRemote = null;
        }
        transitionFinishCallback.onTransitionFinished(windowContainerTransaction);
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final void dump$2(PrintWriter printWriter, String str) {
        String strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "  ");
        printWriter.println(str + "PipTransition");
        printWriter.println(strM + "mCurrentPipTaskToken=" + this.mCurrentPipTaskToken);
        printWriter.println(strM + "mFinishCallback=" + this.mFinishCallback);
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final void end(PipTaskOrganizer$$ExternalSyntheticLambda8 pipTaskOrganizer$$ExternalSyntheticLambda8) {
        PipAnimationController pipAnimationController = this.mPipAnimationController;
        PipAnimationController.PipTransitionAnimator pipTransitionAnimator = pipAnimationController.mCurrentAnimator;
        if (pipTransitionAnimator != null && pipTransitionAnimator.isRunning()) {
            pipAnimationController.mCurrentAnimator.end();
        }
        if (pipTaskOrganizer$$ExternalSyntheticLambda8 != null) {
            pipTaskOrganizer$$ExternalSyntheticLambda8.run();
        }
    }

    public final void fadeEnteredPipIfNeed(boolean z) {
        if (this.mPipTransitionState.hasEnteredPip()) {
            if (z && this.mHasFadeOut) {
                this.mTransitions.runOnIdle(new Runnable() { // from class: com.android.wm.shell.pip.PipTransition$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        PipTransition pipTransition = this.f$0;
                        if (pipTransition.mHasFadeOut && pipTransition.mPipTransitionState.hasEnteredPip()) {
                            pipTransition.fadeExistingPip$1(true);
                        }
                    }
                });
            } else {
                if (z || this.mHasFadeOut) {
                    return;
                }
                fadeExistingPip$1(false);
            }
        }
    }

    public final void fadeExistingPip$1(final boolean z) {
        PipTaskOrganizer pipTaskOrganizer = this.mPipOrganizer;
        SurfaceControl surfaceControl = pipTaskOrganizer.mLeash;
        ActivityManager.RunningTaskInfo runningTaskInfo = pipTaskOrganizer.mTaskInfo;
        if (surfaceControl == null || !surfaceControl.isValid() || runningTaskInfo == null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[3]) {
                ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -7332491239584809957L, 0, "PipTransition", String.valueOf(surfaceControl));
                return;
            }
            return;
        }
        float f = z ? 0.0f : 1.0f;
        float f2 = z ? 1.0f : 0.0f;
        PipAnimationController.PipTransactionHandler pipTransactionHandler = new PipAnimationController.PipTransactionHandler() { // from class: com.android.wm.shell.pip.PipTransition.3
            @Override // com.android.wm.shell.pip.PipAnimationController.PipTransactionHandler
            public final boolean handlePipTransaction(SurfaceControl surfaceControl2, SurfaceControl.Transaction transaction, Rect rect, float f3) {
                if (f3 != 0.0f) {
                    return false;
                }
                if (z) {
                    transaction.setPosition(surfaceControl2, rect.left, rect.top);
                    return false;
                }
                Rect displayBounds = PipTransition.this.mPipDisplayLayoutState.getDisplayBounds();
                float fMax = Math.max(displayBounds.width(), displayBounds.height());
                transaction.setPosition(surfaceControl2, fMax, fMax);
                return false;
            }
        };
        PipAnimationController.PipTransitionAnimator transitionDirection = this.mPipAnimationController.getAnimator(runningTaskInfo, surfaceControl, this.mPipBoundsState.getBounds(), f, f2).setTransitionDirection(1);
        transitionDirection.mPipTransactionHandler = pipTransactionHandler;
        transitionDirection.setDuration(this.mEnterExitAnimationDuration).start();
        this.mHasFadeOut = !z;
    }

    public final TransitionInfo.Change findCurrentPipTaskChange(TransitionInfo transitionInfo) {
        if (this.mCurrentPipTaskToken == null) {
            return null;
        }
        for (int iM = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); iM >= 0; iM--) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(iM);
            if (this.mCurrentPipTaskToken.equals(change.getContainer())) {
                return change;
            }
        }
        return null;
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final void forceFinishTransition(PipTaskOrganizer$$ExternalSyntheticLambda5 pipTaskOrganizer$$ExternalSyntheticLambda5) {
        this.mCurrentPipTaskToken = null;
        this.mFixedRotationState = 0;
        Transitions.TransitionFinishCallback transitionFinishCallback = this.mFinishCallback;
        if (transitionFinishCallback == null) {
            return;
        }
        transitionFinishCallback.onTransitionFinished(null);
        this.mFinishCallback = null;
        this.mFinishTransaction = null;
        if (!CoreRune.MW_PIP_SHELL_TRANSITION || pipTaskOrganizer$$ExternalSyntheticLambda5 == null) {
            return;
        }
        pipTaskOrganizer$$ExternalSyntheticLambda5.run();
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        if (transitionRequestInfo.getType() == 10) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 407213498667646561L, 0, "PipTransition");
            }
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            augmentRequest(iBinder, transitionRequestInfo, windowContainerTransaction);
            return windowContainerTransaction;
        }
        int type = transitionRequestInfo.getType();
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        if (type == 4 && transitionRequestInfo.getTriggerTask() != null && transitionRequestInfo.getTriggerTask().getWindowingMode() == 2) {
            if (CoreRune.MW_PIP_SHELL_TRANSITION && !transitionRequestInfo.getTriggerTask().isVisible) {
                Log.w("PipTransition", "[PipTaskOrganizer] abort handle TRANSIT_TO_BACK, triggerTask is not visible");
                return null;
            }
            this.mMoveToBackTransition = iBinder;
            pipTransitionState.setTransitionState(5);
            return new WindowContainerTransaction();
        }
        if (CoreRune.MW_PIP_SHELL_TRANSITION && transitionRequestInfo.getType() == 6 && transitionRequestInfo.getDisplayChange() != null && transitionRequestInfo.getDisplayChange().getStartRotation() != transitionRequestInfo.getDisplayChange().getEndRotation() && pipTransitionState.mState == 5) {
            this.mIsDisplayChangeInExiting = true;
        }
        return null;
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final boolean handleRotateDisplay(int i, int i2, WindowContainerTransaction windowContainerTransaction) throws Resources.NotFoundException {
        if (this.mRequestedEnterTransition == null || this.mEnterAnimationType != 1 || RotationUtils.deltaRotation(i, i2) == 0) {
            return false;
        }
        this.mPipDisplayLayoutState.rotateTo(i2);
        windowContainerTransaction.setBounds(this.mRequestedEnterTask, this.mPipBoundsAlgorithm.getEntryDestinationBounds());
        return true;
    }

    public final boolean isEnteringPip(TransitionInfo transitionInfo) {
        for (int iM = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); iM >= 0; iM--) {
            if (isEnteringPip$1((TransitionInfo.Change) transitionInfo.getChanges().get(iM), transitionInfo.getType())) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final boolean isEnteringPip$1(TransitionInfo.Change change, int i) {
        if (change.getTaskInfo() == null || change.getTaskInfo().getWindowingMode() != 2 || change.getContainer().equals(this.mCurrentPipTaskToken)) {
            return false;
        }
        if (i == 10 || i == 1 || i == 3 || i == 6) {
            return true;
        }
        if (CoreRune.MW_PIP_SHELL_TRANSITION && change.isEnteringPinnedMode()) {
            return true;
        }
        Slog.e("PipTransition", "Found new PIP in transition with mis-matched type=" + Transitions.transitTypeToString(i), new Throwable());
        return false;
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final boolean isInSwipePipToHomeTransition() {
        return this.mPipTransitionState.mInSwipePipToHomeTransition;
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final boolean isPackageActiveInPip(String str) {
        PipTaskOrganizer pipTaskOrganizer = this.mPipOrganizer;
        ActivityManager.RunningTaskInfo runningTaskInfo = pipTaskOrganizer.mTaskInfo;
        return str != null && runningTaskInfo != null && pipTaskOrganizer.isInPip() && str.equals(ComponentUtils.getPackageName(((TaskInfo) runningTaskInfo).baseIntent));
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        end(null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v4 */
    /* JADX WARN: Type inference failed for: r1v6, types: [android.view.SurfaceControl, android.view.SurfaceControl$Transaction] */
    /* JADX WARN: Type inference failed for: r1v7 */
    @Override // com.android.wm.shell.pip.PipTransitionController
    public final void onFinishResize(TaskInfo taskInfo, Rect rect, Point point, int i, SurfaceControl.Transaction transaction) {
        WindowContainerTransaction windowContainerTransaction;
        ?? r1;
        SurfaceControl.Transaction transaction2;
        int i2 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
        Log.d("PipTaskOrganizer", "[PipTransition] onFinishResize dest=" + rect + " direction=" + i);
        boolean zIsInPipDirection = PipAnimationController.isInPipDirection(i);
        if (zIsInPipDirection) {
            this.mPipTransitionState.setTransitionState(4);
        }
        if ((this.mExitTransition == null || this.mMoveToBackTransition == null || this.mFinishTransaction != null) && this.mFinishCallback != null) {
            SurfaceControl surfaceControl = this.mPipOrganizer.mLeash;
            boolean z = surfaceControl != null && surfaceControl.isValid();
            boolean zIsOutPipDirection = PipAnimationController.isOutPipDirection(i);
            PipAnimationController pipAnimationController = this.mPipAnimationController;
            PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
            if (zIsOutPipDirection) {
                if (this.mFixedRotationState != 2 && (transaction2 = this.mFinishTransaction) != null) {
                    transaction2.merge(transaction);
                    if (z) {
                        Rect rect2 = new Rect(rect);
                        rect2.offset(-point.x, -point.y);
                        this.mFinishTransaction.setCrop(surfaceControl, rect2).setPosition(surfaceControl, point.x, point.y);
                    }
                }
                windowContainerTransaction = null;
            } else {
                windowContainerTransaction = new WindowContainerTransaction();
                if (PipAnimationController.isInPipDirection(i)) {
                    windowContainerTransaction.setActivityWindowingMode(taskInfo.token, 0);
                    windowContainerTransaction.setBounds(taskInfo.token, rect);
                    PipAnimationController.PipTransitionAnimator pipTransitionAnimator = pipAnimationController.mCurrentAnimator;
                    if (pipTransitionAnimator != null && pipTransitionAnimator.getEndValue().equals(Float.valueOf(0.0f))) {
                        transaction.addTransactionCommittedListener(this.mTransitions.mMainExecutor, new SurfaceControl.TransactionCommittedListener() { // from class: com.android.wm.shell.pip.PipTransition$$ExternalSyntheticLambda0
                            @Override // android.view.SurfaceControl.TransactionCommittedListener
                            public final void onTransactionCommitted() {
                                this.f$0.fadeExistingPip$1(true);
                            }
                        });
                    }
                } else {
                    windowContainerTransaction.setBounds(taskInfo.token, (Rect) null);
                }
                if (z) {
                    pipSurfaceTransactionHelper.cropAndPosition(rect, transaction, surfaceControl);
                    pipSurfaceTransactionHelper.resetScale(rect, transaction, surfaceControl);
                    pipSurfaceTransactionHelper.round(transaction, surfaceControl, true);
                    PipTaskOrganizer pipTaskOrganizer = this.mPipOrganizer;
                    Rect rect3 = pipTaskOrganizer.mAppBounds;
                    if (pipTaskOrganizer.mPipOverlay != null && !rect3.isEmpty()) {
                        Rect rect4 = new Rect(rect);
                        int iMax = Math.max(Math.max(rect3.width(), rect3.height()), Math.max(rect.width(), rect.height())) + 1;
                        rect4.offsetTo((rect.width() - iMax) / 2, (rect.height() - iMax) / 2);
                        pipSurfaceTransactionHelper.resetScale(rect4, transaction, this.mPipOrganizer.mPipOverlay);
                    }
                }
                windowContainerTransaction.setBoundsChangeTransaction(taskInfo.token, transaction);
            }
            int displayRotation = taskInfo.getConfiguration().windowConfiguration.getDisplayRotation();
            if (zIsInPipDirection && this.mFixedRotationState == 2 && this.mEndFixedRotation != displayRotation && z) {
                PipAnimationController.PipTransitionAnimator pipTransitionAnimator2 = pipAnimationController.mCurrentAnimator;
                Rect displayBounds = this.mPipDisplayLayoutState.getDisplayBounds();
                Rect rect5 = new Rect(rect);
                RotationUtils.rotateBounds(rect5, displayBounds, this.mEndFixedRotation, displayRotation);
                if (!rect5.equals(pipTransitionAnimator2.getEndValue())) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[3]) {
                        ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -2953606837466350454L, 0, "PipTransition");
                    }
                    RotationUtils.rotateBounds(rect5, displayBounds, this.mEndFixedRotation, displayRotation);
                    pipSurfaceTransactionHelper.cropAndPosition(rect5, this.mFinishTransaction, surfaceControl);
                }
            }
            r1 = 0;
            this.mFinishTransaction = null;
            callFinishCallback(windowContainerTransaction);
        } else {
            r1 = 0;
        }
        this.mFixedRotationState = 0;
        PipMenuController pipMenuController = this.mPipMenuController;
        pipMenuController.movePipMenu(rect, r1, r1);
        pipMenuController.updateMenuBounds(rect);
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final void onFixedRotationFinished() {
        fadeEnteredPipIfNeed(true);
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final void onFixedRotationStarted() {
        if (this.mFixedRotationState == 0) {
            this.mFixedRotationState = 1;
        }
        fadeEnteredPipIfNeed(false);
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final void onInit() {
        if (PipUtils.isPip2ExperimentEnabled()) {
            return;
        }
        this.mTransitions.addHandler(this);
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final void onStartEnterPipFromSplit(TransitionInfo.Change change, TransitionInfo transitionInfo) {
        if (this.mInEnterPipFromSplit) {
            return;
        }
        this.mInEnterPipFromSplit = true;
        this.mLeftoverTransitionInfo = transitionInfo;
        Log.d("PipTransition", "onStartEnterPipFromSplit: " + change);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
        boolean z2;
        PipTransition pipTransition;
        this.mFixedRotationState = 0;
        if (iBinder == this.mExitTransition || iBinder == this.mMoveToBackTransition) {
            PipAnimationController pipAnimationController = this.mPipAnimationController;
            PipAnimationController.PipTransitionAnimator pipTransitionAnimator = pipAnimationController.mCurrentAnimator;
            if (pipTransitionAnimator != null) {
                pipTransitionAnimator.cancel();
                pipAnimationController.mCurrentAnimator = null;
                z2 = true;
            } else {
                z2 = false;
            }
            this.mExitTransition = null;
            this.mMoveToBackTransition = null;
            if (z2) {
                PipTaskOrganizer pipTaskOrganizer = this.mPipOrganizer;
                ActivityManager.RunningTaskInfo runningTaskInfo = pipTaskOrganizer.mTaskInfo;
                if (runningTaskInfo == null) {
                    pipTransition = this;
                } else {
                    if (z) {
                        sendOnPipTransitionFinished(3);
                        this.mPipOrganizer.onExitPipFinished(runningTaskInfo);
                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                        this.mPipOrganizer.applyWindowingModeChangeOnExit(windowContainerTransaction);
                        windowContainerTransaction.setBounds(runningTaskInfo.token, (Rect) null);
                        this.mPipOrganizer.applyFinishBoundsResize(3, windowContainerTransaction, false);
                    } else if (CoreRune.MW_PIP_SHELL_TRANSITION && this.mExitTransitionType == 1003) {
                        pipTaskOrganizer.onExitPipFinished(runningTaskInfo);
                    } else {
                        SurfaceControl surfaceControl = pipTaskOrganizer.mLeash;
                        PipBoundsState pipBoundsState = this.mPipBoundsState;
                        pipTransition = this;
                        pipTransition.startExpandAnimation(runningTaskInfo, surfaceControl, pipBoundsState.getBounds(), pipBoundsState.getBounds(), new Rect(this.mExitDestinationBounds), 0, null);
                    }
                    pipTransition = this;
                }
                pipTransition.mExitDestinationBounds.setEmpty();
                pipTransition.mCurrentPipTaskToken = null;
                if (CoreRune.MW_PIP_SHELL_TRANSITION) {
                    pipTransition.mExitTransitionType = 0;
                }
            }
        }
    }

    public final void removePipImmediately(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, Transitions.TransitionFinishCallback transitionFinishCallback, TaskInfo taskInfo) {
        transaction.apply();
        if (transitionInfo.getChanges().isEmpty()) {
            int i = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
            Log.e("PipTaskOrganizer", "info.getChanges is empty info=" + transitionInfo + " callers=" + Debug.getCallers(3));
        } else if (findCurrentPipTaskChange(transitionInfo) == null && ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[4]) {
            ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 4544289263338730413L, 0, null);
        }
        this.mPipOrganizer.onExitPipFinished(taskInfo);
        transitionFinishCallback.onTransitionFinished(null);
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final void setEnterAnimationType(int i) {
        this.mEnterAnimationType = i;
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final void setStartTransactionForRemote(SurfaceControl.Transaction transaction) {
        this.mStartTransactionForRemote = transaction;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:219:0x04d8  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x05a1  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x05b6  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x05bd  */
    /* JADX WARN: Removed duplicated region for block: B:260:0x05dd  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x0789  */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean startAnimation(IBinder iBinder, final TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, final SurfaceControl.Transaction transaction2, final Transitions.TransitionFinishCallback transitionFinishCallback) throws Resources.NotFoundException {
        String str;
        int i;
        WindowContainerToken windowContainerToken;
        char c;
        SurfaceControl leash;
        SurfaceControl surfaceControl;
        SurfaceControl leash2;
        Rect rect;
        SurfaceControl surfaceControl2;
        int i2;
        int i3;
        int i4;
        TransitionInfo.Change change;
        int i5;
        int i6;
        PipBoundsState pipBoundsState;
        String str2;
        TransitionInfo.Change change2;
        int i7;
        PipBoundsState pipBoundsState2;
        TransitionInfo.Change pipChange;
        TransitionInfo.Change change3;
        TransitionInfo.Change pipChange2;
        TransitionInfo.Change change4;
        PipTransition pipTransition = this;
        SurfaceControl.Transaction transaction3 = transaction;
        boolean z = CoreRune.MW_PIP_SHELL_TRANSITION;
        if (z && pipTransition.mExitTransition == iBinder && transitionInfo.isKeyguardGoingAway()) {
            Log.w("PipTransition", "handleKeyguardTransitionIfNeeded: force consume, " + transitionInfo);
            pipTransition.onTransitionConsumed(iBinder, true, transaction2);
            return false;
        }
        TransitionInfo.Change changeFindCurrentPipTaskChange = pipTransition.findCurrentPipTaskChange(transitionInfo);
        TransitionInfo.Change changeFindFixedRotationChange = PipTransitionController.findFixedRotationChange(transitionInfo);
        if (pipTransition.mFixedRotationState == 2) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[3]) {
                str = "PipTransition";
                i = -1;
                ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 7307082344896149609L, 0, str);
            } else {
                str = "PipTransition";
                i = -1;
            }
            pipTransition.mFixedRotationState = 0;
        } else {
            str = "PipTransition";
            i = -1;
        }
        int i8 = changeFindFixedRotationChange != null ? 2 : pipTransition.mFixedRotationState;
        pipTransition.mFixedRotationState = i8;
        pipTransition.mEndFixedRotation = i8 == 2 ? changeFindFixedRotationChange.getEndFixedRotation() : i;
        int type = transitionInfo.getType();
        boolean zEquals = iBinder.equals(pipTransition.mExitTransition);
        PipBoundsState pipBoundsState3 = pipTransition.mPipBoundsState;
        if (zEquals || iBinder.equals(pipTransition.mMoveToBackTransition) || iBinder.equals(pipTransition.mCleanupTransition)) {
            String str3 = str;
            pipTransition.mExitDestinationBounds.setEmpty();
            pipTransition.mExitTransition = null;
            if (z) {
                pipTransition.mExitTransitionType = 0;
            }
            pipTransition.mMoveToBackTransition = null;
            pipTransition.mCleanupTransition = null;
            pipTransition.mHasFadeOut = false;
            if (pipTransition.mFinishCallback != null) {
                pipTransition.callFinishCallback(null);
                pipTransition.mFinishTransaction = null;
                throw new RuntimeException("Previous callback not called, aborting exit PIP.");
            }
            ActivityManager.RunningTaskInfo taskInfo = changeFindCurrentPipTaskChange != null ? changeFindCurrentPipTaskChange.getTaskInfo() : pipTransition.mPipOrganizer.mTaskInfo;
            if (taskInfo == null) {
                throw new RuntimeException("Cannot find the pip task for exit-pip transition.");
            }
            if (type == 4) {
                windowContainerToken = null;
                pipTransition.removePipImmediately(transitionInfo, transaction3, transitionFinishCallback, taskInfo);
            } else if (type != 1019) {
                switch (type) {
                    case 1001:
                        if (pipTransition.mCurrentPipTaskToken == null) {
                            int i9 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
                            Log.w("PipTaskOrganizer", "[PipTransition] There is no existing PiP Task for TRANSIT_EXIT_PIP");
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[3]) {
                                c = 3;
                                ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 7179741450891310688L, 0, str3);
                            } else {
                                c = 3;
                            }
                        } else {
                            c = 3;
                            if (changeFindCurrentPipTaskChange == null) {
                                for (int iM = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); iM >= 0; iM--) {
                                    TransitionInfo.Change change5 = (TransitionInfo.Change) transitionInfo.getChanges().get(iM);
                                    if (pipTransition.mCurrentPipTaskToken.equals(change5.getLastParent())) {
                                        leash = change5.getLeash();
                                        changeFindCurrentPipTaskChange = change5;
                                        surfaceControl = pipTransition.mPipOrganizer.mPipOverlay;
                                        if (surfaceControl != null) {
                                            transaction3.remove(surfaceControl);
                                            PipTaskOrganizer pipTaskOrganizer = pipTransition.mPipOrganizer;
                                            pipTaskOrganizer.mPipOverlay = null;
                                            pipTaskOrganizer.mAppBounds.setEmpty();
                                        }
                                        if (pipTransition.mPipOrganizer.getOutPipWindowingMode() == 0) {
                                            pipTransition.mHomeTransitionObserver.notifyHomeVisibilityChanged(false);
                                        }
                                        if (changeFindCurrentPipTaskChange != null) {
                                            int i10 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
                                            Log.w("PipTaskOrganizer", "[PipTransition] No window of exiting PIP is found. Can't play expand animation");
                                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[c]) {
                                                ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 7093378717862999160L, 0, str3);
                                            }
                                            pipTransition.removePipImmediately(transitionInfo, transaction3, transitionFinishCallback, taskInfo);
                                        } else {
                                            TransitionInfo.Root root = transitionInfo.getRoot(TransitionUtil.rootIndexFor(changeFindCurrentPipTaskChange, transitionInfo));
                                            if (leash != null) {
                                                SurfaceControl leash3 = changeFindCurrentPipTaskChange.getLeash();
                                                leash2 = new SurfaceControl.Builder().setName(leash + "_pip-leash").setContainerLayer().setHidden(false).setParent(root.getLeash()).setCallsite("PipTransition.startExitAnimation").build();
                                                transaction3.reparent(leash3, leash2);
                                                Point endRelOffset = changeFindCurrentPipTaskChange.getEndRelOffset();
                                                transaction3.setPosition(leash, (float) endRelOffset.x, (float) endRelOffset.y);
                                            } else {
                                                leash2 = changeFindCurrentPipTaskChange.getLeash();
                                                transaction3.reparent(leash2, root.getLeash());
                                            }
                                            transaction3.setLayer(leash2, Integer.MAX_VALUE);
                                            Point offset = root.getOffset();
                                            Rect bounds = pipBoundsState3.getBounds();
                                            bounds.offset(-offset.x, -offset.y);
                                            transaction3.setPosition(leash2, bounds.left, bounds.top);
                                            SurfaceControl surfaceControl3 = leash;
                                            final WindowContainerToken container = changeFindCurrentPipTaskChange.getContainer();
                                            final boolean z2 = surfaceControl3 != null;
                                            final boolean zEquals2 = changeFindCurrentPipTaskChange.getEndAbsBounds().equals(pipBoundsState3.mPipDisplayLayoutState.getDisplayBounds());
                                            final ActivityManager.RunningTaskInfo runningTaskInfo = taskInfo;
                                            final SurfaceControl surfaceControl4 = leash2;
                                            this.mFinishCallback = new Transitions.TransitionFinishCallback() { // from class: com.android.wm.shell.pip.PipTransition$$ExternalSyntheticLambda3
                                                @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
                                                public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction) {
                                                    TaskInfo taskInfo2 = runningTaskInfo;
                                                    WindowContainerToken windowContainerToken2 = container;
                                                    SurfaceControl.Transaction transaction4 = transaction2;
                                                    SurfaceControl surfaceControl5 = surfaceControl4;
                                                    PipTransition pipTransition2 = this.f$0;
                                                    pipTransition2.mPipOrganizer.onExitPipFinished(taskInfo2);
                                                    if (!Transitions.SHELL_TRANSITIONS_ROTATION) {
                                                        boolean z3 = zEquals2;
                                                        if (!z3 && ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                                                            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 6254160326658898692L, 0, "PipTransition");
                                                        }
                                                        if (windowContainerTransaction == null) {
                                                            windowContainerTransaction = new WindowContainerTransaction();
                                                        }
                                                        int i11 = taskInfo2.displayId;
                                                        DesktopStateImpl.Companion.getClass();
                                                        if (!DesktopStateImpl.Companion.inDesktopWindowing(i11) || (z3 && windowContainerToken2 != null)) {
                                                            windowContainerTransaction.setBounds(windowContainerToken2, (Rect) null);
                                                        }
                                                        pipTransition2.mPipOrganizer.applyWindowingModeChangeOnExit(windowContainerTransaction);
                                                    }
                                                    if (z2) {
                                                        PipAnimationController pipAnimationController = pipTransition2.mPipAnimationController;
                                                        PipAnimationController.PipTransitionAnimator pipTransitionAnimator = pipAnimationController.mCurrentAnimator;
                                                        if (pipTransitionAnimator != null && pipTransitionAnimator.isRunning()) {
                                                            pipAnimationController.mCurrentAnimator.end();
                                                        }
                                                        pipAnimationController.mCurrentAnimator = null;
                                                        transaction4.remove(surfaceControl5);
                                                    }
                                                    int i12 = pipTransition2.mFixedRotationState;
                                                    Transitions.TransitionFinishCallback transitionFinishCallback2 = transitionFinishCallback;
                                                    if (i12 == 2) {
                                                        transitionFinishCallback2.onTransitionFinished(windowContainerTransaction);
                                                    } else {
                                                        pipTransition2.mCleanupTransition = pipTransition2.mTransitions.startTransition(1019, windowContainerTransaction, pipTransition2);
                                                        transitionFinishCallback2.onTransitionFinished(null);
                                                    }
                                                }
                                            };
                                            this.mFinishTransaction = transaction2;
                                            int i11 = 90;
                                            if (Transitions.SHELL_TRANSITIONS_ROTATION) {
                                                int iM2 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1);
                                                while (true) {
                                                    if (iM2 >= 0) {
                                                        TransitionInfo.Change change6 = (TransitionInfo.Change) transitionInfo.getChanges().get(iM2);
                                                        change = (change6.getMode() != 6 || (change6.getFlags() & 32) == 0 || change6.getStartRotation() == change6.getEndRotation()) ? null : change6;
                                                        iM2--;
                                                    }
                                                }
                                                if (change != null) {
                                                    int i12 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
                                                    Log.d("PipTaskOrganizer", "[PipTransition] startExpandAndRotationAnimation");
                                                    int iDeltaRotation = RotationUtils.deltaRotation(change.getStartRotation(), change.getEndRotation());
                                                    CounterRotatorHelper counterRotatorHelper = new CounterRotatorHelper();
                                                    counterRotatorHelper.handleClosingChanges(transitionInfo, transaction3, change);
                                                    TransitionInfo.Change change7 = change;
                                                    Rect rect2 = new Rect(changeFindCurrentPipTaskChange.getStartAbsBounds());
                                                    RotationUtils.rotateBounds(rect2, change7.getStartAbsBounds(), iDeltaRotation);
                                                    Rect endAbsBounds = ((TaskInfo) runningTaskInfo).topActivityMainWindowFrame;
                                                    if (endAbsBounds == null) {
                                                        endAbsBounds = changeFindCurrentPipTaskChange.getEndAbsBounds();
                                                    }
                                                    Rect rect3 = new Rect(endAbsBounds);
                                                    rect2.offset(-offset.x, -offset.y);
                                                    rect3.offset(-offset.x, -offset.y);
                                                    int iDeltaRotation2 = RotationUtils.deltaRotation(iDeltaRotation, 0);
                                                    if (iDeltaRotation2 == 1) {
                                                        i5 = rect2.right;
                                                        i6 = rect2.top;
                                                    } else {
                                                        i5 = rect2.left;
                                                        i6 = rect2.bottom;
                                                        i11 = -90;
                                                    }
                                                    this.mSurfaceTransactionHelper.rotateAndScaleWithCrop(transaction3, changeFindCurrentPipTaskChange.getLeash(), rect3, rect2, new Rect(), i11, i5, i6, true, iDeltaRotation2 == 3 ? 1 : 0, null);
                                                    transaction.apply();
                                                    counterRotatorHelper.cleanUp(transaction2);
                                                    this.mPipAnimationController.getAnimator(runningTaskInfo, changeFindCurrentPipTaskChange.getLeash(), rect2, rect2, rect3, null, 3, 0.0f, iDeltaRotation2, false).setTransitionDirection(3).setPipAnimationCallback(this.mPipAnimationCallback).setDuration(this.mEnterExitAnimationDuration).start();
                                                    pipTransition = this;
                                                } else {
                                                    TransitionInfo.Change change8 = changeFindCurrentPipTaskChange;
                                                    Rect rect4 = new Rect(change8.getEndAbsBounds());
                                                    rect4.offset(-offset.x, -offset.y);
                                                    if (this.mFixedRotationState == 2) {
                                                        int iDeltaRotation3 = RotationUtils.deltaRotation(change8.getStartRotation(), this.mEndFixedRotation);
                                                        Rect rect5 = new Rect(rect4);
                                                        RotationUtils.rotateBounds(rect5, rect4, iDeltaRotation3);
                                                        if (iDeltaRotation3 == 1) {
                                                            int i13 = rect4.right;
                                                            i2 = rect4.top;
                                                            i3 = i13;
                                                            i4 = 90;
                                                        } else {
                                                            int i14 = rect4.left;
                                                            i2 = rect4.bottom;
                                                            i3 = i14;
                                                            i4 = -90;
                                                        }
                                                        rect = rect4;
                                                        surfaceControl2 = surfaceControl4;
                                                        i = iDeltaRotation3;
                                                        this.mSurfaceTransactionHelper.rotateAndScaleWithCrop(transaction2, surfaceControl2, rect5, rect5, new Rect(), i4, i3, i2, true, iDeltaRotation3 == 3, null);
                                                    } else {
                                                        rect = rect4;
                                                        surfaceControl2 = surfaceControl4;
                                                    }
                                                    pipTransition = this;
                                                    pipTransition.startExpandAnimation(runningTaskInfo, surfaceControl2, bounds, bounds, rect, i, transaction);
                                                }
                                            }
                                        }
                                        windowContainerToken = null;
                                        break;
                                    }
                                }
                            }
                        }
                        leash = null;
                        surfaceControl = pipTransition.mPipOrganizer.mPipOverlay;
                        if (surfaceControl != null) {
                        }
                        if (pipTransition.mPipOrganizer.getOutPipWindowingMode() == 0) {
                        }
                        if (changeFindCurrentPipTaskChange != null) {
                        }
                        windowContainerToken = null;
                        break;
                    case 1002:
                        for (int iM3 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); iM3 >= 0; iM3--) {
                            TransitionInfo.Change change9 = (TransitionInfo.Change) transitionInfo.getChanges().get(iM3);
                            int mode = change9.getMode();
                            if ((mode != 6 || change9.getParent() == null) && TransitionUtil.isOpeningType(mode) && change9.getParent() == null) {
                                SurfaceControl leash4 = change9.getLeash();
                                Rect endAbsBounds2 = change9.getEndAbsBounds();
                                transaction3.show(leash4).setAlpha(leash4, 1.0f).setPosition(leash4, endAbsBounds2.left, endAbsBounds2.top).setWindowCrop(leash4, endAbsBounds2.width(), endAbsBounds2.height());
                            }
                        }
                        ((SplitScreenController) pipTransition.mSplitScreenOptional.get()).finishEnterSplitScreen(transaction2);
                        transaction3.apply();
                        pipTransition.mPipOrganizer.onExitPipFinished(taskInfo);
                        transitionFinishCallback.onTransitionFinished(null);
                        windowContainerToken = null;
                        break;
                    case 1003:
                        break;
                    default:
                        if (!z) {
                            throw new IllegalStateException("mExitTransition with unexpected transit type=" + Transitions.transitTypeToString(type));
                        }
                        Log.e(str3, "startAnimation: mExitTransition with unexpected transit type=" + Transitions.transitTypeToString(type) + ", pipTaskInfo=" + taskInfo + ", callers=" + Debug.getCallers(3));
                        pipTransition.removePipImmediately(transitionInfo, transaction3, transitionFinishCallback, taskInfo);
                        windowContainerToken = null;
                        break;
                }
            } else {
                transaction3.apply();
                windowContainerToken = null;
                transitionFinishCallback.onTransitionFinished(null);
            }
            pipTransition.mCurrentPipTaskToken = windowContainerToken;
            return true;
        }
        if (iBinder == pipTransition.mRequestedEnterTransition) {
            pipTransition.mRequestedEnterTransition = null;
            pipTransition.mRequestedEnterTask = null;
        }
        if (changeFindCurrentPipTaskChange == null || changeFindCurrentPipTaskChange.getTaskInfo().getWindowingMode() == 2) {
            pipBoundsState = pipBoundsState3;
            str2 = str;
        } else if (pipTransition.isEnteringPip(transitionInfo)) {
            SurfaceControl leash5 = changeFindCurrentPipTaskChange.getLeash();
            Rect endAbsBounds3 = changeFindCurrentPipTaskChange.getEndAbsBounds();
            Point endRelOffset2 = changeFindCurrentPipTaskChange.getEndRelOffset();
            endAbsBounds3.offset(-endRelOffset2.x, -endRelOffset2.y);
            transaction3.setWindowCrop(leash5, null);
            pipBoundsState = pipBoundsState3;
            str2 = str;
            transaction3.setMatrix(leash5, 1.0f, 0.0f, 0.0f, 1.0f);
            transaction3 = transaction3;
            transaction3.setCornerRadius(leash5, 0.0f);
            transaction3.setPosition(leash5, endAbsBounds3.left, endAbsBounds3.top);
            if (pipTransition.mHasFadeOut && changeFindCurrentPipTaskChange.getTaskInfo().isVisible()) {
                PipAnimationController.PipTransitionAnimator pipTransitionAnimator = pipTransition.mPipAnimationController.mCurrentAnimator;
                if (pipTransitionAnimator != null) {
                    pipTransitionAnimator.cancel();
                }
                transaction3.setAlpha(leash5, 1.0f);
            }
            pipTransition.mHasFadeOut = false;
            pipTransition.mCurrentPipTaskToken = null;
            PipTaskOrganizer pipTaskOrganizer2 = pipTransition.mPipOrganizer;
            if (pipTaskOrganizer2.mLeash == leash5) {
                pipTaskOrganizer2.onExitPipFinished(changeFindCurrentPipTaskChange.getTaskInfo());
            }
        } else {
            String str4 = str;
            Log.d(str4, "skip resetPrevPip");
            str2 = str4;
            pipBoundsState = pipBoundsState3;
        }
        int iM4 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1);
        while (true) {
            if (iM4 < 0) {
                change2 = null;
                break;
            }
            change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(iM4);
            if (change2.hasFlags(32)) {
                break;
            }
            iM4--;
        }
        if (CoreRune.MW_PIP_SHELL_TRANSITION && transitionInfo.getType() == 6 && pipTransition.mIsDisplayChangeInExiting && change2 != null) {
            int iM5 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1);
            while (true) {
                if (iM5 < 0) {
                    i7 = 2;
                    change4 = null;
                    break;
                }
                change4 = (TransitionInfo.Change) transitionInfo.getChanges().get(iM5);
                if (change4.getTaskInfo() != null) {
                    i7 = 2;
                    if (change4.getTaskInfo().getWindowingMode() == 2) {
                        break;
                    }
                }
                iM5--;
            }
            if (change4 != null) {
                Log.d(str2, "reset Pip leash crop in DisplayChange transition.");
                transaction3.setWindowCrop(change4.getLeash(), null);
                transaction2.setWindowCrop(change4.getLeash(), null);
            }
            pipTransition.mIsDisplayChangeInExiting = false;
        } else {
            i7 = 2;
        }
        if (pipTransition.isEnteringPip(transitionInfo)) {
            PipTransitionState pipTransitionState = pipTransition.mPipTransitionState;
            if (PipTransitionState.isInPip(pipTransitionState.mState) || !TransitionUtil.hasDisplayChange(transitionInfo)) {
                if (pipTransition.mFixedRotationState == 0 && TransitionUtil.hasDisplayChange(transitionInfo) && (pipChange2 = getPipChange(transitionInfo)) != null) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                        ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1382171360120524735L, 0, str2);
                    }
                    DefaultMixedHandler defaultMixedHandler = pipTransition.mMixedHandler;
                    defaultMixedHandler.getClass();
                    TransitionInfo transitionInfoSubCopy = transitionInfo.getType() != 6 ? DefaultMixedHandler.subCopy(transitionInfo, 6, true) : transitionInfo;
                    DefaultMixedTransition defaultMixedTransitionCreateDefaultMixedTransition = defaultMixedHandler.createDefaultMixedTransition(iBinder, 11);
                    defaultMixedHandler.mActiveTransitions.add(defaultMixedTransitionCreateDefaultMixedTransition);
                    defaultMixedTransitionCreateDefaultMixedTransition.mInFlightSubAnimations = i7;
                    DefaultMixedHandler$$ExternalSyntheticLambda4 defaultMixedHandler$$ExternalSyntheticLambda4 = new DefaultMixedHandler$$ExternalSyntheticLambda4(defaultMixedHandler, defaultMixedTransitionCreateDefaultMixedTransition, transitionFinishCallback, 5);
                    defaultMixedTransitionCreateDefaultMixedTransition.mLeftoversHandler = defaultMixedHandler.mPlayer.dispatchTransition(defaultMixedTransitionCreateDefaultMixedTransition.mTransition, transitionInfoSubCopy, transaction3, transaction2, defaultMixedHandler$$ExternalSyntheticLambda4, defaultMixedHandler.mPipHandler, null);
                    defaultMixedHandler.mPipHandler.startEnterAnimation(pipChange2, transaction3, new SurfaceControl.Transaction(), new DefaultMixedHandler$$ExternalSyntheticLambda5(1, defaultMixedHandler, defaultMixedHandler$$ExternalSyntheticLambda4));
                    defaultMixedHandler.mPipHandler.end();
                    defaultMixedHandler.mPipHandler.syncPipSurfaceState(transitionInfo, transaction3, transaction2);
                    return true;
                }
                if (CoreRune.MW_PIP_REMOTE_TRANSITION && (pipChange = getPipChange(transitionInfo)) != null && transitionInfo.getRemoteTransition() != null && !TransitionUtil.hasDisplayChange(transitionInfo) && pipTransition.mFixedRotationState == 0 && pipTransitionState.mInSwipePipToHomeTransition) {
                    Iterator it = transitionInfo.getChanges().iterator();
                    TransitionInfo.Change change10 = null;
                    while (true) {
                        if (!it.hasNext()) {
                            change3 = null;
                            break;
                        }
                        change3 = (TransitionInfo.Change) it.next();
                        ActivityManager.RunningTaskInfo taskInfo2 = change3.getTaskInfo();
                        if (pipChange != change3 && taskInfo2 != null) {
                            int activityType = taskInfo2.getActivityType();
                            if (change10 != null || !TransitionUtil.isOpeningMode(change3.getMode()) || activityType != 1) {
                                if (TransitionUtil.isClosingMode(change3.getMode()) && activityType == i7) {
                                    break;
                                }
                            } else {
                                change10 = change3;
                            }
                        }
                    }
                    if (change10 != null && change3 != null) {
                        Log.d(str2, "handleEnteringPipWithRemoteTransition: pipChange=" + pipChange + ", opening=" + change10 + ", closing=" + change3);
                        DefaultMixedHandler defaultMixedHandler2 = pipTransition.mMixedHandler;
                        RemoteTransition remoteTransition = transitionInfo.getRemoteTransition();
                        DefaultMixedTransition defaultMixedTransitionCreateDefaultMixedTransition2 = defaultMixedHandler2.createDefaultMixedTransition(iBinder, 3);
                        defaultMixedTransitionCreateDefaultMixedTransition2.mInFlightSubAnimations = i7;
                        defaultMixedHandler2.mActiveTransitions.add(defaultMixedTransitionCreateDefaultMixedTransition2);
                        DefaultMixedHandler$$ExternalSyntheticLambda4 defaultMixedHandler$$ExternalSyntheticLambda42 = new DefaultMixedHandler$$ExternalSyntheticLambda4(defaultMixedHandler2, defaultMixedTransitionCreateDefaultMixedTransition2, transitionFinishCallback, i);
                        SurfaceControl.Transaction transaction4 = new SurfaceControl.Transaction();
                        SurfaceControl.Transaction transaction5 = new SurfaceControl.Transaction();
                        SurfaceControl.Transaction transaction6 = new SurfaceControl.Transaction();
                        defaultMixedHandler2.mPipHandler.setStartTransactionForRemote(transaction3);
                        defaultMixedHandler2.mPipHandler.startEnterAnimation(pipChange, transaction4, transaction5, new DefaultMixedHandler$$ExternalSyntheticLambda5(i, defaultMixedHandler2, defaultMixedHandler$$ExternalSyntheticLambda42));
                        defaultMixedHandler2.mPipHandler.end();
                        defaultMixedHandler2.mPipHandler.syncPipSurfaceState(transitionInfo, transaction6, transaction2);
                        TransitionInfo transitionInfoSubCopy2 = DefaultMixedHandler.subCopy(transitionInfo, 3, true);
                        transitionInfoSubCopy2.getChanges().remove(pipChange);
                        Log.d("DefaultMixedHandler", "animateEnteringPipWithRemoteTransition: pipChange=" + pipChange + ", remote=" + transitionInfoSubCopy2);
                        RemoteTransitionHandler remoteTransitionHandler = defaultMixedHandler2.mPlayer.mRemoteTransitionHandler;
                        defaultMixedTransitionCreateDefaultMixedTransition2.mLeftoversHandler = remoteTransitionHandler;
                        if (remoteTransitionHandler.mRequestedRemotes.containsKey(iBinder)) {
                            Log.w("RemoteTransitionHandler", "requestRemoteTransition: exist " + iBinder + ", reason=enter_pip_remote");
                        } else {
                            remoteTransitionHandler.mRequestedRemotes.put(iBinder, remoteTransition);
                            Log.d("RemoteTransitionHandler", "requestRemoteTransition: " + iBinder + ", remote=" + remoteTransition + ", reason=enter_pip_remote");
                        }
                        SurfaceControl.Transaction transaction7 = transaction3;
                        if (remoteTransitionHandler.startAnimation(defaultMixedTransitionCreateDefaultMixedTransition2.mTransition, transitionInfoSubCopy2, transaction3, transaction2, defaultMixedHandler$$ExternalSyntheticLambda42)) {
                            return true;
                        }
                        Log.e("DefaultMixedHandler", "animateEnteringPipWithRemoteTransition: failed to start remote!");
                        transaction7.apply();
                        defaultMixedHandler$$ExternalSyntheticLambda42.onTransitionFinished(null);
                        return true;
                    }
                }
                SurfaceControl.Transaction transaction8 = transaction3;
                TransitionInfo.Change pipChange3 = getPipChange(transitionInfo);
                if (pipChange3 == null) {
                    throw new IllegalStateException("Trying to start PiP animation without a pipparticipant");
                }
                pipTransition.mSplitScreenOptional.ifPresent(new Consumer() { // from class: com.android.wm.shell.pip.PipTransition$$ExternalSyntheticLambda1
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        SplitScreenController splitScreenController = (SplitScreenController) obj;
                        if (Transitions.hasDuplicatedOpenTypeChanges(transitionInfo) && splitScreenController.isSplitScreenActive() && !splitScreenController.isSplitScreenVisible()) {
                            splitScreenController.setSplitVisible();
                        }
                    }
                });
                for (int size = transitionInfo.getChanges().size() - 1; size >= 0; size--) {
                    TransitionInfo.Change change11 = (TransitionInfo.Change) transitionInfo.getChanges().get(size);
                    if (change11 != pipChange3) {
                        if (TransitionUtil.isOpeningType(change11.getMode())) {
                            SurfaceControl leash6 = change11.getLeash();
                            transaction8.show(leash6).setAlpha(leash6, 1.0f);
                        } else if (TransitionUtil.isClosingType(change11.getMode())) {
                            transaction8.hide(change11.getLeash());
                        }
                    }
                }
                pipTransition.startEnterAnimation(pipChange3, transaction8, transaction2, transitionFinishCallback);
                return true;
            }
            TransitionInfo.Change pipChange4 = getPipChange(transitionInfo);
            if (pipChange4 != null) {
                pipTransition.updatePipForUnhandledTransition(pipChange4, transaction3, transaction2);
            }
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -535753279885985137L, 0, str2);
                return false;
            }
        } else {
            SurfaceControl.Transaction transaction9 = transaction3;
            if (changeFindCurrentPipTaskChange != null) {
                pipTransition.updatePipForUnhandledTransition(changeFindCurrentPipTaskChange, transaction9, transaction2);
            }
            int iM6 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1);
            while (iM6 >= 0) {
                TransitionInfo.Change change12 = (TransitionInfo.Change) transitionInfo.getChanges().get(iM6);
                boolean z3 = change12.getTaskInfo() != null;
                if (change12.getActivityComponent() != null) {
                    pipBoundsState2 = pipBoundsState;
                    boolean z4 = change12.getActivityComponent().equals(pipBoundsState2.mLastPipComponentName);
                    if (z3 && change12.getMode() == i7 && z4) {
                        pipBoundsState2.setLastPipComponentName(null);
                        return false;
                    }
                    iM6--;
                    pipBoundsState = pipBoundsState2;
                } else {
                    pipBoundsState2 = pipBoundsState;
                }
                if (z3) {
                }
                iM6--;
                pipBoundsState = pipBoundsState2;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x00ab  */
    @Override // com.android.wm.shell.pip.PipTransitionController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void startEnterAnimation(TransitionInfo.Change change, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) throws Resources.NotFoundException {
        Rect rect;
        Rect validSourceHintRect;
        Rect rect2;
        int i;
        PipBoundsAlgorithm pipBoundsAlgorithm;
        boolean z;
        PipSurfaceTransactionHelper pipSurfaceTransactionHelper;
        Rect rect3;
        String str;
        float f;
        Rect rect4;
        SurfaceControl.Transaction transaction3;
        PipAnimationController.PipTransitionAnimator animator;
        int i2;
        boolean z2;
        int i3;
        boolean z3;
        boolean z4;
        boolean z5;
        SurfaceControl.Transaction transaction4;
        Rect rect5;
        if (this.mFinishCallback != null) {
            callFinishCallback(null);
            this.mFinishTransaction = null;
            throw new RuntimeException("Previous callback not called, aborting entering PIP.");
        }
        int i4 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
        Log.d("PipTaskOrganizer", "[PipTransition] startEnterAnimation");
        this.mCurrentPipTaskToken = change.getContainer();
        this.mHasFadeOut = false;
        PipTransitionState pipTransitionState = this.mPipTransitionState;
        pipTransitionState.setTransitionState(3);
        this.mFinishCallback = transitionFinishCallback;
        this.mFinishTransaction = transaction2;
        ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
        final SurfaceControl leash = change.getLeash();
        int startRotation = change.getStartRotation();
        if (this.mFixedRotationState != 2) {
            int endFixedRotation = change.getEndFixedRotation();
            this.mEndFixedRotation = endFixedRotation;
            this.mFixedRotationState = endFixedRotation != -1 ? 2 : this.mFixedRotationState;
        }
        int endRotation = this.mFixedRotationState == 2 ? this.mEndFixedRotation : change.getEndRotation();
        ComponentName componentName = taskInfo.topActivity;
        PictureInPictureParams pictureInPictureParams = taskInfo.pictureInPictureParams;
        ActivityInfo activityInfo = taskInfo.topActivityInfo;
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        PipBoundsAlgorithm pipBoundsAlgorithm2 = this.mPipBoundsAlgorithm;
        pipBoundsState.setBoundsStateForEntry(componentName, activityInfo, pictureInPictureParams, pipBoundsAlgorithm2);
        this.mPipOrganizer.getClass();
        if (!pipBoundsState.mCachedLauncherShelfHeightKeepClearArea.isEmpty()) {
            pipBoundsState.setNamedUnrestrictedKeepClearArea(0, pipBoundsState.mCachedLauncherShelfHeightKeepClearArea);
        }
        Rect entryDestinationBounds = pipBoundsAlgorithm2.getEntryDestinationBounds();
        Rect startAbsBounds = change.getStartAbsBounds();
        Rect rect6 = taskInfo.topActivityMainWindowFrame;
        if (rect6 == null) {
            rect6 = startAbsBounds;
        }
        Rect rect7 = new Rect(rect6);
        rect7.offset(-startAbsBounds.left, -startAbsBounds.top);
        int iDeltaRotation = RotationUtils.deltaRotation(startRotation, endRotation);
        PipTaskOrganizer pipTaskOrganizer = this.mPipOrganizer;
        Rect rect8 = pipTaskOrganizer.mSwipeSourceRectHint;
        if (rect8 == null || rect8.isEmpty()) {
            rect = null;
        } else {
            rect = rect8;
            pipTaskOrganizer.mSwipeSourceRectHint = null;
            if (!pipTaskOrganizer.mPipTransitionState.mInSwipePipToHomeTransition) {
            }
        }
        if (rect == null) {
            validSourceHintRect = PipBoundsAlgorithm.getValidSourceHintRect(taskInfo.pictureInPictureParams, startAbsBounds);
            if (!PipBoundsAlgorithm.isSourceRectHintValidForEnterPip(validSourceHintRect, entryDestinationBounds)) {
                validSourceHintRect = null;
            }
        } else {
            validSourceHintRect = rect;
        }
        if (iDeltaRotation != 0) {
            PipDisplayLayoutState pipDisplayLayoutState = this.mPipDisplayLayoutState;
            rect2 = rect7;
            if (endRotation != pipDisplayLayoutState.mDisplayLayout.mRotation) {
                pipDisplayLayoutState.rotateTo(endRotation);
                pipBoundsState.updateMinMaxSize(pipBoundsState.mAspectRatio);
                Rect displayBounds = pipDisplayLayoutState.getDisplayBounds();
                entryDestinationBounds.set(pipBoundsAlgorithm2.getEntryDestinationBounds());
                if (this.mFixedRotationState == 2) {
                    RotationUtils.rotateBounds(entryDestinationBounds, displayBounds, endRotation, startRotation);
                }
                if (validSourceHintRect != null && (rect5 = ((TaskInfo) taskInfo).displayCutoutInsets) != null && iDeltaRotation == 3) {
                    validSourceHintRect.offset(rect5.left, rect5.top);
                }
                StringBuilder sb = new StringBuilder("[PipTransition] computeEnterPipRotatedBounds, currentBounds=");
                sb.append(startAbsBounds);
                sb.append(" destinationBounds=");
                sb.append(entryDestinationBounds);
                sb.append("startRotation=");
                KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(sb, startRotation, "endRotation=", endRotation, "PipTaskOrganizer");
            }
        } else {
            rect2 = rect7;
        }
        this.mPipOrganizer.getClass();
        ((HandlerExecutor) this.mTransitions.mMainExecutor).executeDelayed(new Runnable() { // from class: com.android.wm.shell.pip.PipTransition.2
            @Override // java.lang.Runnable
            public final void run() {
                PipTransition.this.mPipMenuController.attach(leash);
                PipTransition pipTransition = PipTransition.this;
                pipTransition.mPipMenuController.setSplitMenuEnabled(pipTransition.mPipOrganizer.shouldShowSplitMenu());
            }
        }, 0L);
        PictureInPictureParams pictureInPictureParams2 = taskInfo.pictureInPictureParams;
        AnonymousClass1 anonymousClass1 = this.mTransactionConsumer;
        if (pictureInPictureParams2 != null && pictureInPictureParams2.isAutoEnterEnabled() && pipTransitionState.mInSwipePipToHomeTransition) {
            if (this.mFixedRotationState == 2 && ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[3]) {
                ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1139199614939219922L, 4, "PipTransition", Long.valueOf(this.mEndFixedRotation));
            }
            SurfaceControl surfaceControl = this.mPipOrganizer.mPipOverlay;
            if (surfaceControl != null) {
                transaction.reparent(surfaceControl, leash).setLayer(surfaceControl, Integer.MAX_VALUE);
            }
            sendOnPipTransitionStarted$1(2);
            Rect bounds = surfaceControl == null ? taskInfo.configuration.windowConfiguration.getBounds() : this.mPipOrganizer.mAppBounds;
            Rect rect9 = new Rect(taskInfo.displayCutoutInsets);
            taskInfo.displayCutoutInsets.setEmpty();
            PipAnimationController.PipTransitionAnimator animator2 = this.mPipAnimationController.getAnimator(taskInfo, leash, bounds, bounds, entryDestinationBounds, validSourceHintRect, 2, 0.0f, 0, true);
            animator2.mPipTransactionHandler = anonymousClass1;
            PipAnimationController.PipTransitionAnimator transitionDirection = animator2.setTransitionDirection(2);
            transaction.merge(transaction2);
            transitionDirection.applySurfaceControlTransaction(leash, transaction, 1.0f);
            transaction.apply();
            taskInfo.displayCutoutInsets.set(rect9);
            if (CoreRune.MW_PIP_REMOTE_TRANSITION && (transaction4 = this.mStartTransactionForRemote) != null) {
                transitionDirection.applySurfaceControlTransaction(leash, transaction4, 1.0f);
                this.mStartTransactionForRemote = null;
            }
            pipBoundsState.setBounds(entryDestinationBounds);
            onFinishResize(taskInfo, entryDestinationBounds, new Point(transitionDirection.mLeashOffset), 2, new SurfaceControl.Transaction());
            sendOnPipTransitionFinished(2);
            if (surfaceControl == null) {
                z5 = false;
            } else if (CoreRune.MW_PIP_SHELL_TRANSITION) {
                this.mPipOrganizer.fadeOutAndRemoveOverlay(surfaceControl, true, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend);
                z5 = false;
            } else {
                z5 = false;
                this.mPipOrganizer.fadeOutAndRemoveOverlay(surfaceControl, false, -1);
            }
            pipTransitionState.mInSwipePipToHomeTransition = z5;
            return;
        }
        Rect rect10 = validSourceHintRect;
        pipTransitionState.mInSwipePipToHomeTransition = false;
        int i5 = this.mEnterAnimationType;
        if (i5 == 1) {
            transaction.setAlpha(leash, 0.0f);
        } else {
            transaction.setAlpha(leash, 1.0f);
        }
        transaction.apply();
        int i6 = this.mEnterExitAnimationDuration;
        if (i5 == 0) {
            i = iDeltaRotation;
            pipBoundsAlgorithm = pipBoundsAlgorithm2;
            PipAnimationController.PipTransitionAnimator animator3 = this.mPipAnimationController.getAnimator(taskInfo, leash, startAbsBounds, startAbsBounds, entryDestinationBounds, rect10, 2, 0.0f, i, false);
            if (rect10 == null) {
                ActivityInfo activityInfo2 = taskInfo.topActivityInfo;
                if (activityInfo2 == null || this.mFixedRotationState == 2) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[3]) {
                        ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -5093325440467976417L, 0, "PipTransition");
                    }
                    Context context = this.mContext;
                    animator3.getClass();
                    animator3.reattachContentOverlay(new PipContentOverlay.PipColorOverlay(context));
                } else {
                    Context context2 = this.mContext;
                    int i7 = pipBoundsState.mLauncherState.mAppIconSizePx;
                    animator3.getClass();
                    animator3.reattachContentOverlay(new PipContentOverlay.PipAppIconOverlay(context2, rect2, entryDestinationBounds, new IconProvider(context2).getIcon(activityInfo2), i7));
                }
            } else {
                TaskSnapshot taskSnapshot = PipUtils.getTaskSnapshot(taskInfo.launchIntoPipHostTaskId);
                if (taskSnapshot != null) {
                    animator3.getClass();
                    animator3.reattachContentOverlay(new PipContentOverlay.PipSnapshotOverlay(taskSnapshot, rect10));
                }
            }
            animator = animator3;
            str = "PipTransition";
            i3 = i6;
            rect4 = rect2;
            z3 = false;
            transaction3 = transaction2;
            f = 1.0f;
        } else {
            i = iDeltaRotation;
            pipBoundsAlgorithm = pipBoundsAlgorithm2;
            if (i5 != 1) {
                throw new RuntimeException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i5, "Unrecognized animation type: "));
            }
            boolean z6 = CoreRune.MW_PIP_SHELL_TRANSITION;
            PipSurfaceTransactionHelper pipSurfaceTransactionHelper2 = this.mSurfaceTransactionHelper;
            if (z6 && this.mInEnterPipFromSplit) {
                TransitionInfo transitionInfo = this.mLeftoverTransitionInfo;
                if (transitionInfo == null || !transitionInfo.getChanges().isEmpty()) {
                    z4 = false;
                } else {
                    transaction2.setAlpha(leash, 0.0f);
                    z4 = true;
                }
                Log.d("PipTransition", "startEnterAnimation: enterPipFromSplit, leash=" + leash + ", destinationBounds=" + entryDestinationBounds);
                animator = this.mPipAnimationController.getAnimator(taskInfo, leash, entryDestinationBounds, 0.0f, 1.0f);
                pipBoundsState.setBounds(entryDestinationBounds);
                animator.getClass();
                z = true;
                onFinishResize(taskInfo, entryDestinationBounds, new Point(animator.mLeashOffset), 2, new SurfaceControl.Transaction());
                pipSurfaceTransactionHelper2.getClass();
                transaction.setCrop(leash, entryDestinationBounds);
                pipSurfaceTransactionHelper = pipSurfaceTransactionHelper2;
                pipSurfaceTransactionHelper.round(transaction, leash, true);
                transaction3 = transaction2;
                rect3 = entryDestinationBounds;
                i2 = i6;
                z2 = z4;
                f = 1.0f;
                str = "PipTransition";
                rect4 = rect2;
            } else {
                z = true;
                pipSurfaceTransactionHelper = pipSurfaceTransactionHelper2;
                boolean z7 = i != 0 && this.mFixedRotationState == 1;
                rect3 = entryDestinationBounds;
                str = "PipTransition";
                f = 1.0f;
                rect4 = rect2;
                transaction3 = transaction2;
                animator = this.mPipAnimationController.getAnimator(taskInfo, leash, rect3, 0.0f, z7 ? 0.0f : 1.0f);
                i2 = z7 ? 0 : i6;
                z2 = false;
            }
            pipSurfaceTransactionHelper.cropAndPosition(rect3, transaction3, leash);
            pipSurfaceTransactionHelper.round(transaction3, leash, z);
            this.mEnterAnimationType = 0;
            i3 = i2;
            z3 = z2;
        }
        PipTaskOrganizer pipTaskOrganizer2 = this.mPipOrganizer;
        PipContentOverlay pipContentOverlay = animator.mContentOverlay;
        SurfaceControl surfaceControl2 = pipContentOverlay == null ? null : pipContentOverlay.mLeash;
        pipTaskOrganizer2.mPipOverlay = surfaceControl2;
        if (surfaceControl2 != null) {
            pipTaskOrganizer2.mAppBounds.set(rect4);
        } else {
            pipTaskOrganizer2.mAppBounds.setEmpty();
        }
        animator.setTransitionDirection(2).setPipAnimationCallback(this.mPipAnimationCallback).setDuration(i3);
        if (i != 0 && this.mFixedRotationState == 2) {
            animator.setDestinationBounds(pipBoundsAlgorithm.getEntryDestinationBounds());
        }
        if (CoreRune.MW_PIP_SHELL_TRANSITION && z3) {
            Log.d(str, "startEnterAnimation: skip to apply end fraction for " + leash);
        } else {
            animator.mPipTransactionHandler = anonymousClass1;
            animator.applySurfaceControlTransaction(leash, transaction3, f);
        }
        animator.mPipTransactionHandler = this.mPipOrganizer.mPipTransactionHandler;
        animator.start();
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final void startExitTransition(int i, WindowContainerTransaction windowContainerTransaction, Rect rect) {
        if (rect != null) {
            this.mExitDestinationBounds.set(rect);
        }
        PipAnimationController.PipTransitionAnimator pipTransitionAnimator = this.mPipAnimationController.mCurrentAnimator;
        if (pipTransitionAnimator != null && pipTransitionAnimator.isRunning()) {
            pipTransitionAnimator.cancel();
        }
        this.mExitTransition = this.mTransitions.startTransition(i, windowContainerTransaction, this);
        if (CoreRune.MW_PIP_SHELL_TRANSITION) {
            this.mExitTransitionType = i;
        }
    }

    public final void startExpandAnimation(TaskInfo taskInfo, SurfaceControl surfaceControl, Rect rect, Rect rect2, Rect rect3, int i, SurfaceControl.Transaction transaction) {
        Rect validSourceHintRect = PipBoundsAlgorithm.getValidSourceHintRect(taskInfo.pictureInPictureParams, rect3);
        int i2 = PipTaskOrganizer.EXTRA_CONTENT_OVERLAY_FADE_OUT_DELAY_MS;
        StringBuilder sb = new StringBuilder("[PipTransition] startExpandAnimation startBounds=");
        sb.append(rect2);
        sb.append(" endBounds=");
        sb.append(rect3);
        sb.append(" rotDelta=");
        RecyclerView$$ExternalSyntheticOutline0.m(i, "PipTaskOrganizer", sb);
        PipAnimationController.PipTransitionAnimator animator = this.mPipAnimationController.getAnimator(taskInfo, surfaceControl, rect, rect2, rect3, validSourceHintRect, 3, 0.0f, i, false);
        animator.setTransitionDirection(3).setDuration(this.mEnterExitAnimationDuration);
        if (transaction != null) {
            animator.mPipTransactionHandler = this.mTransactionConsumer;
            animator.applySurfaceControlTransaction(surfaceControl, transaction, 0.0f);
            transaction.apply();
        }
        PipAnimationController.PipTransitionAnimator pipAnimationCallback = animator.setPipAnimationCallback(this.mPipAnimationCallback);
        pipAnimationCallback.mPipTransactionHandler = this.mPipOrganizer.mPipTransactionHandler;
        pipAnimationCallback.start();
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final void syncPipSurfaceState(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        TransitionInfo.Change changeFindCurrentPipTaskChange = findCurrentPipTaskChange(transitionInfo);
        if (changeFindCurrentPipTaskChange == null) {
            return;
        }
        updatePipForUnhandledTransition(changeFindCurrentPipTaskChange, transaction, transaction2);
    }

    public final void updatePipForUnhandledTransition(TransitionInfo.Change change, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        SurfaceControl leash = change.getLeash();
        PipTaskOrganizer pipTaskOrganizer = this.mPipOrganizer;
        PipAnimationController.PipTransitionAnimator pipTransitionAnimator = pipTaskOrganizer.mPipAnimationController.mCurrentAnimator;
        Rect bounds = (pipTransitionAnimator == null || !pipTransitionAnimator.isRunning()) ? pipTaskOrganizer.mPipBoundsState.getBounds() : new Rect(pipTransitionAnimator.mDestinationBounds);
        boolean zIsInPip = PipTransitionState.isInPip(this.mPipTransitionState.mState);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -5137264551179897407L, 192, "PipTransition", String.valueOf(change), String.valueOf(bounds), Boolean.valueOf(zIsInPip));
        }
        PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
        pipSurfaceTransactionHelper.cropAndPosition(bounds, transaction, leash);
        pipSurfaceTransactionHelper.round(transaction, leash, zIsInPip);
        pipSurfaceTransactionHelper.shadow(transaction, leash, zIsInPip);
        pipSurfaceTransactionHelper.cropAndPosition(bounds, transaction2, leash);
        pipSurfaceTransactionHelper.round(transaction2, leash, zIsInPip);
        pipSurfaceTransactionHelper.shadow(transaction2, leash, zIsInPip);
        if (zIsInPip && this.mHasFadeOut) {
            transaction.setAlpha(leash, 0.0f);
            transaction2.setAlpha(leash, 0.0f);
        }
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final void end() {
        end(null);
    }
}
