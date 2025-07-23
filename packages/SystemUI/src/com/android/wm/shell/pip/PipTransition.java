package com.android.wm.shell.pip;

import android.app.ActivityManager;
import android.app.TaskInfo;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Debug;
import android.os.IBinder;
import android.util.Log;
import android.util.RotationUtils;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.R;
import com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.ComponentUtils;
import com.android.wm.shell.common.pip.PipBoundsAlgorithm;
import com.android.wm.shell.common.pip.PipBoundsState;
import com.android.wm.shell.common.pip.PipDisplayLayoutState;
import com.android.wm.shell.common.pip.PipMenuController;
import com.android.wm.shell.common.pip.PipUtils;
import com.android.wm.shell.pip.PipAnimationController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.HomeTransitionObserver;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        for (int m = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); m >= 0; m--) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m);
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
        String m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str, "  ");
        printWriter.println(str + "PipTransition");
        printWriter.println(m + "mCurrentPipTaskToken=" + this.mCurrentPipTaskToken);
        printWriter.println(m + "mFinishCallback=" + this.mFinishCallback);
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
                        PipTransition pipTransition = PipTransition.this;
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
                float max = Math.max(displayBounds.width(), displayBounds.height());
                transaction.setPosition(surfaceControl2, max, max);
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
        for (int m = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); m >= 0; m--) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m);
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
    public final boolean handleRotateDisplay(int i, int i2, WindowContainerTransaction windowContainerTransaction) {
        if (this.mRequestedEnterTransition == null || this.mEnterAnimationType != 1 || RotationUtils.deltaRotation(i, i2) == 0) {
            return false;
        }
        this.mPipDisplayLayoutState.rotateTo(i2);
        windowContainerTransaction.setBounds(this.mRequestedEnterTask, this.mPipBoundsAlgorithm.getEntryDestinationBounds());
        return true;
    }

    public final boolean isEnteringPip(TransitionInfo transitionInfo) {
        for (int m = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); m >= 0; m--) {
            if (isEnteringPip$1((TransitionInfo.Change) transitionInfo.getChanges().get(m), transitionInfo.getType())) {
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
        boolean isInPipDirection = PipAnimationController.isInPipDirection(i);
        if (isInPipDirection) {
            this.mPipTransitionState.setTransitionState(4);
        }
        if ((this.mExitTransition == null || this.mMoveToBackTransition == null || this.mFinishTransaction != null) && this.mFinishCallback != null) {
            SurfaceControl surfaceControl = this.mPipOrganizer.mLeash;
            boolean z = surfaceControl != null && surfaceControl.isValid();
            boolean isOutPipDirection = PipAnimationController.isOutPipDirection(i);
            PipAnimationController pipAnimationController = this.mPipAnimationController;
            PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
            if (isOutPipDirection) {
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
                                PipTransition.this.fadeExistingPip$1(true);
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
                        int max = Math.max(Math.max(rect3.width(), rect3.height()), Math.max(rect.width(), rect.height())) + 1;
                        rect4.offsetTo((rect.width() - max) / 2, (rect.height() - max) / 2);
                        pipSurfaceTransactionHelper.resetScale(rect4, transaction, this.mPipOrganizer.mPipOverlay);
                    }
                }
                windowContainerTransaction.setBoundsChangeTransaction(taskInfo.token, transaction);
            }
            int displayRotation = taskInfo.getConfiguration().windowConfiguration.getDisplayRotation();
            if (isInPipDirection && this.mFixedRotationState == 2 && this.mEndFixedRotation != displayRotation && z) {
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

    /* JADX WARN: Removed duplicated region for block: B:24:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onTransitionConsumed(android.os.IBinder r11, boolean r12, android.view.SurfaceControl.Transaction r13) {
        /*
            r10 = this;
            r13 = 0
            r10.mFixedRotationState = r13
            android.os.IBinder r0 = r10.mExitTransition
            if (r11 == r0) goto Ld
            android.os.IBinder r0 = r10.mMoveToBackTransition
            if (r11 == r0) goto Ld
            goto L7e
        Ld:
            com.android.wm.shell.pip.PipAnimationController r11 = r10.mPipAnimationController
            com.android.wm.shell.pip.PipAnimationController$PipTransitionAnimator r0 = r11.mCurrentAnimator
            r1 = 0
            if (r0 == 0) goto L1b
            r0.cancel()
            r11.mCurrentAnimator = r1
            r11 = 1
            goto L1c
        L1b:
            r11 = r13
        L1c:
            r10.mExitTransition = r1
            r10.mMoveToBackTransition = r1
            if (r11 != 0) goto L23
            goto L7e
        L23:
            com.android.wm.shell.pip.PipTaskOrganizer r11 = r10.mPipOrganizer
            android.app.ActivityManager$RunningTaskInfo r3 = r11.mTaskInfo
            if (r3 == 0) goto L48
            if (r12 == 0) goto L4a
            r11 = 3
            r10.sendOnPipTransitionFinished(r11)
            com.android.wm.shell.pip.PipTaskOrganizer r12 = r10.mPipOrganizer
            r12.onExitPipFinished(r3)
            android.window.WindowContainerTransaction r12 = new android.window.WindowContainerTransaction
            r12.<init>()
            com.android.wm.shell.pip.PipTaskOrganizer r0 = r10.mPipOrganizer
            r0.applyWindowingModeChangeOnExit(r12)
            android.window.WindowContainerToken r0 = r3.token
            r12.setBounds(r0, r1)
            com.android.wm.shell.pip.PipTaskOrganizer r0 = r10.mPipOrganizer
            r0.applyFinishBoundsResize(r11, r12, r13)
        L48:
            r2 = r10
            goto L71
        L4a:
            boolean r12 = com.samsung.android.rune.CoreRune.MW_PIP_SHELL_TRANSITION
            if (r12 == 0) goto L58
            int r12 = r10.mExitTransitionType
            r0 = 1003(0x3eb, float:1.406E-42)
            if (r12 != r0) goto L58
            r11.onExitPipFinished(r3)
            goto L48
        L58:
            android.view.SurfaceControl r4 = r11.mLeash
            com.android.wm.shell.common.pip.PipBoundsState r11 = r10.mPipBoundsState
            android.graphics.Rect r5 = r11.getBounds()
            android.graphics.Rect r6 = r11.getBounds()
            android.graphics.Rect r7 = new android.graphics.Rect
            android.graphics.Rect r11 = r10.mExitDestinationBounds
            r7.<init>(r11)
            r8 = 0
            r9 = 0
            r2 = r10
            r2.startExpandAnimation(r3, r4, r5, r6, r7, r8, r9)
        L71:
            android.graphics.Rect r10 = r2.mExitDestinationBounds
            r10.setEmpty()
            r2.mCurrentPipTaskToken = r1
            boolean r10 = com.samsung.android.rune.CoreRune.MW_PIP_SHELL_TRANSITION
            if (r10 == 0) goto L7e
            r2.mExitTransitionType = r13
        L7e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.pip.PipTransition.onTransitionConsumed(android.os.IBinder, boolean, android.view.SurfaceControl$Transaction):void");
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
    /* JADX WARN: Removed duplicated region for block: B:263:0x05a1  */
    /* JADX WARN: Removed duplicated region for block: B:266:0x05b6  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x05bd  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x05dd  */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean startAnimation(android.os.IBinder r34, final android.window.TransitionInfo r35, android.view.SurfaceControl.Transaction r36, final android.view.SurfaceControl.Transaction r37, final com.android.wm.shell.transition.Transitions.TransitionFinishCallback r38) {
        /*
            Method dump skipped, instructions count: 2094
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.pip.PipTransition.startAnimation(android.os.IBinder, android.window.TransitionInfo, android.view.SurfaceControl$Transaction, android.view.SurfaceControl$Transaction, com.android.wm.shell.transition.Transitions$TransitionFinishCallback):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x00a8, code lost:
    
        if (r9.mPipTransitionState.mInSwipePipToHomeTransition != false) goto L31;
     */
    @Override // com.android.wm.shell.pip.PipTransitionController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startEnterAnimation(android.window.TransitionInfo.Change r30, android.view.SurfaceControl.Transaction r31, android.view.SurfaceControl.Transaction r32, com.android.wm.shell.transition.Transitions.TransitionFinishCallback r33) {
        /*
            Method dump skipped, instructions count: 989
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.pip.PipTransition.startEnterAnimation(android.window.TransitionInfo$Change, android.view.SurfaceControl$Transaction, android.view.SurfaceControl$Transaction, com.android.wm.shell.transition.Transitions$TransitionFinishCallback):void");
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
        TransitionInfo.Change findCurrentPipTaskChange = findCurrentPipTaskChange(transitionInfo);
        if (findCurrentPipTaskChange == null) {
            return;
        }
        updatePipForUnhandledTransition(findCurrentPipTaskChange, transaction, transaction2);
    }

    public final void updatePipForUnhandledTransition(TransitionInfo.Change change, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        SurfaceControl leash = change.getLeash();
        PipTaskOrganizer pipTaskOrganizer = this.mPipOrganizer;
        PipAnimationController.PipTransitionAnimator pipTransitionAnimator = pipTaskOrganizer.mPipAnimationController.mCurrentAnimator;
        Rect bounds = (pipTransitionAnimator == null || !pipTransitionAnimator.isRunning()) ? pipTaskOrganizer.mPipBoundsState.getBounds() : new Rect(pipTransitionAnimator.mDestinationBounds);
        boolean isInPip = PipTransitionState.isInPip(this.mPipTransitionState.mState);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_PICTURE_IN_PICTURE_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -5137264551179897407L, 192, "PipTransition", String.valueOf(change), String.valueOf(bounds), Boolean.valueOf(isInPip));
        }
        PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
        pipSurfaceTransactionHelper.cropAndPosition(bounds, transaction, leash);
        pipSurfaceTransactionHelper.round(transaction, leash, isInPip);
        pipSurfaceTransactionHelper.shadow(transaction, leash, isInPip);
        pipSurfaceTransactionHelper.cropAndPosition(bounds, transaction2, leash);
        pipSurfaceTransactionHelper.round(transaction2, leash, isInPip);
        pipSurfaceTransactionHelper.shadow(transaction2, leash, isInPip);
        if (isInPip && this.mHasFadeOut) {
            transaction.setAlpha(leash, 0.0f);
            transaction2.setAlpha(leash, 0.0f);
        }
    }

    @Override // com.android.wm.shell.pip.PipTransitionController
    public final void end() {
        end(null);
    }
}
