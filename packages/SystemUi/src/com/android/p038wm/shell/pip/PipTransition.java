package com.android.p038wm.shell.pip;

import android.app.ActivityManager;
import android.app.PictureInPictureParams;
import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Debug;
import android.os.IBinder;
import android.os.SystemProperties;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.Log;
import android.util.RotationUtils;
import android.util.Slog;
import android.view.SurfaceControl;
import android.view.WindowManager;
import android.window.TaskSnapshot;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import android.window.WindowContainerTransactionCallback;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.launcher3.icons.IconProvider;
import com.android.p038wm.shell.ShellTaskOrganizer;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.pip.PipAnimationController;
import com.android.p038wm.shell.pip.PipContentOverlay;
import com.android.p038wm.shell.pip.p039tv.TvPipTaskOrganizer;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import com.android.p038wm.shell.splitscreen.SplitScreenController;
import com.android.p038wm.shell.sysui.ShellInit;
import com.android.p038wm.shell.transition.CounterRotatorHelper;
import com.android.p038wm.shell.transition.Transitions;
import com.android.p038wm.shell.util.TransitionUtil;
import com.android.systemui.R;
import com.android.systemui.keyguard.KeyguardService$$ExternalSyntheticOutline0;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class PipTransition extends PipTransitionController {
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
    public boolean mHasFadeOut;
    public boolean mInEnterPipFromSplit;
    public boolean mInFixedRotation;
    public IBinder mMoveToBackTransition;
    public final PipDisplayLayoutState mPipDisplayLayoutState;
    public final PipTransitionState mPipTransitionState;
    public WindowContainerToken mRequestedEnterTask;
    public IBinder mRequestedEnterTransition;
    public final Optional mSplitScreenOptional;
    public final PipSurfaceTransactionHelper mSurfaceTransactionHelper;
    public final C40441 mTransactionConsumer;

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.wm.shell.pip.PipTransition$1] */
    public PipTransition(Context context, ShellInit shellInit, ShellTaskOrganizer shellTaskOrganizer, Transitions transitions, PipBoundsState pipBoundsState, PipDisplayLayoutState pipDisplayLayoutState, PipTransitionState pipTransitionState, PipMenuController pipMenuController, PipBoundsAlgorithm pipBoundsAlgorithm, PipAnimationController pipAnimationController, PipSurfaceTransactionHelper pipSurfaceTransactionHelper, Optional<SplitScreenController> optional) {
        super(shellInit, shellTaskOrganizer, transitions, pipBoundsState, pipMenuController, pipBoundsAlgorithm, pipAnimationController);
        this.mEnterAnimationType = 0;
        this.mExitDestinationBounds = new Rect();
        this.mTransactionConsumer = new PipAnimationController.PipTransactionHandler(this) { // from class: com.android.wm.shell.pip.PipTransition.1
        };
        this.mContext = context;
        this.mPipTransitionState = pipTransitionState;
        this.mPipDisplayLayoutState = pipDisplayLayoutState;
        this.mEnterExitAnimationDuration = context.getResources().getInteger(R.integer.config_pipResizeAnimationDuration);
        this.mSurfaceTransactionHelper = pipSurfaceTransactionHelper;
        this.mSplitScreenOptional = optional;
    }

    @Override // com.android.p038wm.shell.pip.PipTransitionController
    public final void augmentRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo, WindowContainerTransaction windowContainerTransaction) {
        if (!(transitionRequestInfo.getType() == 10)) {
            throw new IllegalStateException("Called PiP augmentRequest when request has no PiP");
        }
        if (this.mEnterAnimationType == 1) {
            this.mRequestedEnterTransition = iBinder;
            this.mRequestedEnterTask = transitionRequestInfo.getTriggerTask().token;
            windowContainerTransaction.setActivityWindowingMode(transitionRequestInfo.getTriggerTask().token, 0);
            windowContainerTransaction.setBounds(transitionRequestInfo.getTriggerTask().token, this.mPipBoundsAlgorithm.getEntryDestinationBounds());
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
            windowContainerTransaction = null;
        }
        transitionFinishCallback.onTransitionFinished(windowContainerTransaction, null);
    }

    @Override // com.android.p038wm.shell.pip.PipTransitionController
    public final void dump(PrintWriter printWriter, String str) {
        String m14m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m14m(str, "  ");
        printWriter.println(str + "PipTransition");
        printWriter.println(m14m + "mCurrentPipTaskToken=" + this.mCurrentPipTaskToken);
        printWriter.println(m14m + "mFinishCallback=" + this.mFinishCallback);
    }

    @Override // com.android.p038wm.shell.pip.PipTransitionController
    public final void end() {
        PipAnimationController.PipTransitionAnimator pipTransitionAnimator = this.mPipAnimationController.mCurrentAnimator;
        if (pipTransitionAnimator == null || !pipTransitionAnimator.isRunning()) {
            return;
        }
        pipTransitionAnimator.end();
    }

    public final void fadeEnteredPipIfNeed(boolean z) {
        if (this.mPipTransitionState.mState == 4) {
            if (!z || !this.mHasFadeOut) {
                if (z || this.mHasFadeOut) {
                    return;
                }
                fadeExistingPip(false);
                return;
            }
            Runnable runnable = new Runnable() { // from class: com.android.wm.shell.pip.PipTransition$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    PipTransition pipTransition = PipTransition.this;
                    if (pipTransition.mHasFadeOut) {
                        if (pipTransition.mPipTransitionState.mState == 4) {
                            pipTransition.fadeExistingPip(true);
                        }
                    }
                }
            };
            Transitions transitions = this.mTransitions;
            if (transitions.mPendingTransitions.isEmpty() && !transitions.isAnimating()) {
                runnable.run();
            } else {
                transitions.mRunWhenIdleQueue.add(runnable);
            }
        }
    }

    public final void fadeExistingPip(final boolean z) {
        PipTaskOrganizer pipTaskOrganizer = this.mPipOrganizer;
        SurfaceControl surfaceControl = pipTaskOrganizer.mLeash;
        ActivityManager.RunningTaskInfo runningTaskInfo = pipTaskOrganizer.mTaskInfo;
        if (surfaceControl == null || !surfaceControl.isValid() || runningTaskInfo == null) {
            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.m233w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1714592462, 0, "%s: Invalid leash on fadeExistingPip: %s", "PipTransition", String.valueOf(surfaceControl));
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
        TransitionInfo.Change change;
        if (this.mCurrentPipTaskToken == null) {
            return null;
        }
        int size = transitionInfo.getChanges().size();
        do {
            size--;
            if (size < 0) {
                return null;
            }
            change = (TransitionInfo.Change) transitionInfo.getChanges().get(size);
        } while (!this.mCurrentPipTaskToken.equals(change.getContainer()));
        return change;
    }

    @Override // com.android.p038wm.shell.pip.PipTransitionController
    public final void forceFinishTransition(PipTaskOrganizer$$ExternalSyntheticLambda3 pipTaskOrganizer$$ExternalSyntheticLambda3) {
        this.mCurrentPipTaskToken = null;
        Transitions.TransitionFinishCallback transitionFinishCallback = this.mFinishCallback;
        if (transitionFinishCallback == null) {
            return;
        }
        transitionFinishCallback.onTransitionFinished(null, null);
        this.mFinishCallback = null;
        this.mFinishTransaction = null;
        if (!CoreRune.MW_PIP_SHELL_TRANSITION || pipTaskOrganizer$$ExternalSyntheticLambda3 == null) {
            return;
        }
        pipTaskOrganizer$$ExternalSyntheticLambda3.run();
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        if (transitionRequestInfo.getType() == 10) {
            if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, -1560132950, 0, "%s: handle PiP enter request", "PipTransition");
            }
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            augmentRequest(iBinder, transitionRequestInfo, windowContainerTransaction);
            return windowContainerTransaction;
        }
        if (transitionRequestInfo.getType() != 4 || transitionRequestInfo.getTriggerTask() == null || transitionRequestInfo.getTriggerTask().getWindowingMode() != 2) {
            return null;
        }
        if (CoreRune.MW_PIP_SHELL_TRANSITION && !transitionRequestInfo.getTriggerTask().isVisible) {
            Log.w("PipTransition", "[PipTaskOrganizer] abort handle TRANSIT_TO_BACK, triggerTask is not visible");
            return null;
        }
        this.mMoveToBackTransition = iBinder;
        this.mPipTransitionState.setTransitionState(5);
        return new WindowContainerTransaction();
    }

    @Override // com.android.p038wm.shell.pip.PipTransitionController
    public final boolean handleRotateDisplay(int i, int i2, WindowContainerTransaction windowContainerTransaction) {
        if (this.mRequestedEnterTransition == null || this.mEnterAnimationType != 1 || RotationUtils.deltaRotation(i, i2) == 0) {
            return false;
        }
        this.mPipDisplayLayoutState.rotateTo(i2);
        windowContainerTransaction.setBounds(this.mRequestedEnterTask, this.mPipBoundsAlgorithm.getEntryDestinationBounds());
        return true;
    }

    public final boolean isEnteringPip(TransitionInfo transitionInfo) {
        for (int m136m = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1); m136m >= 0; m136m--) {
            if (isEnteringPip(transitionInfo.getType(), (TransitionInfo.Change) transitionInfo.getChanges().get(m136m))) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        if (CoreRune.MW_PIP_SHELL_TRANSITION && transitionInfo.getType() == 3 && this.mPipTransitionState.mState == 3 && this.mInFixedRotation) {
            this.mPipOrganizer.mNeedToCheckRotation = true;
            Log.d("PipTaskOrganizer", "mergeAnimation in fixed rotation");
        }
        end();
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0035, code lost:
    
        if ((r9.mFinishTransaction != null) != false) goto L12;
     */
    @Override // com.android.p038wm.shell.pip.PipTransitionController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onFinishResize(TaskInfo taskInfo, Rect rect, int i, SurfaceControl.Transaction transaction) {
        WindowContainerTransaction windowContainerTransaction;
        SurfaceControl.Transaction transaction2;
        Log.d("PipTaskOrganizer", "[PipTransition] onFinishResize dest=" + rect + " direction=" + i);
        boolean isInPipDirection = PipAnimationController.isInPipDirection(i);
        if (isInPipDirection) {
            this.mPipTransitionState.setTransitionState(4);
        }
        if (this.mExitTransition != null) {
        }
        if (this.mFinishCallback != null) {
            SurfaceControl surfaceControl = this.mPipOrganizer.mLeash;
            boolean z = surfaceControl != null && surfaceControl.isValid();
            boolean isOutPipDirection = PipAnimationController.isOutPipDirection(i);
            PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
            if (isOutPipDirection) {
                if (!this.mInFixedRotation && (transaction2 = this.mFinishTransaction) != null) {
                    transaction2.merge(transaction);
                }
                windowContainerTransaction = null;
            } else {
                windowContainerTransaction = new WindowContainerTransaction();
                if (PipAnimationController.isInPipDirection(i)) {
                    windowContainerTransaction.setActivityWindowingMode(taskInfo.token, 0);
                    windowContainerTransaction.setBounds(taskInfo.token, rect);
                } else {
                    windowContainerTransaction.setBounds(taskInfo.token, (Rect) null);
                }
                if (z) {
                    pipSurfaceTransactionHelper.crop(rect, transaction, surfaceControl);
                    pipSurfaceTransactionHelper.resetScale(rect, transaction, surfaceControl);
                    pipSurfaceTransactionHelper.round(surfaceControl, true, transaction);
                }
                windowContainerTransaction.setBoundsChangeTransaction(taskInfo.token, transaction);
            }
            int displayRotation = taskInfo.getConfiguration().windowConfiguration.getDisplayRotation();
            if (isInPipDirection && this.mInFixedRotation && this.mEndFixedRotation != displayRotation && z) {
                PipAnimationController.PipTransitionAnimator pipTransitionAnimator = this.mPipAnimationController.mCurrentAnimator;
                Rect displayBounds = this.mPipDisplayLayoutState.getDisplayBounds();
                Rect rect2 = new Rect(rect);
                RotationUtils.rotateBounds(rect2, displayBounds, this.mEndFixedRotation, displayRotation);
                if (!rect2.equals(pipTransitionAnimator.getEndValue())) {
                    if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                        ShellProtoLogImpl.m233w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 2010866566, 0, "%s: Destination bounds were changed during animation", "PipTransition");
                    }
                    RotationUtils.rotateBounds(rect2, displayBounds, this.mEndFixedRotation, displayRotation);
                    pipSurfaceTransactionHelper.crop(rect2, this.mFinishTransaction, surfaceControl);
                }
            }
            this.mFinishTransaction = null;
            callFinishCallback(windowContainerTransaction);
        }
        PipMenuController pipMenuController = this.mPipMenuController;
        pipMenuController.movePipMenu(null, null, rect, -1.0f);
        pipMenuController.updateMenuBounds(rect);
    }

    @Override // com.android.p038wm.shell.pip.PipTransitionController
    public final void onFixedRotationFinished() {
        fadeEnteredPipIfNeed(true);
    }

    @Override // com.android.p038wm.shell.pip.PipTransitionController
    public final void onFixedRotationStarted() {
        fadeEnteredPipIfNeed(false);
    }

    @Override // com.android.p038wm.shell.pip.PipTransitionController
    public final void onStartEnterPipFromSplit(TransitionInfo.Change change) {
        if (this.mInEnterPipFromSplit) {
            return;
        }
        this.mInEnterPipFromSplit = true;
        Log.d("PipTransition", "onStartEnterPipFromSplit: " + change);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
        boolean z2;
        if (iBinder != this.mExitTransition) {
            return;
        }
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
        if (z2) {
            PipTaskOrganizer pipTaskOrganizer = this.mPipOrganizer;
            ActivityManager.RunningTaskInfo runningTaskInfo = pipTaskOrganizer.mTaskInfo;
            Rect rect = this.mExitDestinationBounds;
            if (runningTaskInfo != null) {
                if (z) {
                    sendOnPipTransitionFinished(3);
                    this.mPipOrganizer.onExitPipFinished(runningTaskInfo);
                    WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                    PipTaskOrganizer pipTaskOrganizer2 = this.mPipOrganizer;
                    windowContainerTransaction.setWindowingMode(pipTaskOrganizer2.mToken, pipTaskOrganizer2.getOutPipWindowingMode());
                    windowContainerTransaction.setActivityWindowingMode(pipTaskOrganizer2.mToken, 0);
                    windowContainerTransaction.setBounds(runningTaskInfo.token, (Rect) null);
                    this.mPipOrganizer.applyFinishBoundsResize(3, windowContainerTransaction, false);
                } else if (CoreRune.MW_PIP_SHELL_TRANSITION && this.mExitTransitionType == 1003) {
                    pipTaskOrganizer.onExitPipFinished(runningTaskInfo);
                } else {
                    SurfaceControl surfaceControl = pipTaskOrganizer.mLeash;
                    PipBoundsState pipBoundsState = this.mPipBoundsState;
                    startExpandAnimation(runningTaskInfo, surfaceControl, pipBoundsState.getBounds(), pipBoundsState.getBounds(), new Rect(rect), 0, null);
                }
            }
            rect.setEmpty();
            this.mCurrentPipTaskToken = null;
            if (CoreRune.MW_PIP_SHELL_TRANSITION) {
                this.mExitTransitionType = 0;
            }
        }
    }

    public final void removePipImmediately(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback, TaskInfo taskInfo) {
        transaction.apply();
        if (transitionInfo.getChanges().isEmpty()) {
            Log.e("PipTaskOrganizer", "info.getChanges is empty info=" + transitionInfo + " callers=" + Debug.getCallers(3));
        } else {
            transaction2.setWindowCrop(((TransitionInfo.Change) transitionInfo.getChanges().get(0)).getLeash(), this.mPipDisplayLayoutState.getDisplayBounds());
        }
        this.mPipOrganizer.onExitPipFinished(taskInfo);
        transitionFinishCallback.onTransitionFinished(null, null);
    }

    @Override // com.android.p038wm.shell.pip.PipTransitionController
    public final void setEnterAnimationType(int i) {
        this.mEnterAnimationType = i;
    }

    /* JADX WARN: Removed duplicated region for block: B:120:0x0260  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0287  */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean startAnimation(IBinder iBinder, final TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, final SurfaceControl.Transaction transaction2, final Transitions.TransitionFinishCallback transitionFinishCallback) {
        TransitionInfo.Change change;
        TransitionInfo.Change change2;
        boolean z;
        boolean z2;
        SurfaceControl leash;
        TransitionInfo.Change change3;
        SurfaceControl surfaceControl;
        SurfaceControl surfaceControl2;
        boolean z3;
        int i;
        int i2;
        int i3;
        TransitionInfo.Change change4;
        int i4;
        int i5;
        TransitionInfo.Change findCurrentPipTaskChange = findCurrentPipTaskChange(transitionInfo);
        int size = transitionInfo.getChanges().size() - 1;
        while (true) {
            change = null;
            if (size < 0) {
                change2 = null;
                break;
            }
            change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(size);
            if (change2.getEndFixedRotation() != -1) {
                break;
            }
            size--;
        }
        boolean z4 = change2 != null;
        this.mInFixedRotation = z4;
        this.mEndFixedRotation = z4 ? change2.getEndFixedRotation() : -1;
        int type = transitionInfo.getType();
        boolean equals = iBinder.equals(this.mExitTransition);
        PipBoundsState pipBoundsState = this.mPipBoundsState;
        Optional optional = this.mSplitScreenOptional;
        if (!equals && !iBinder.equals(this.mMoveToBackTransition)) {
            if (iBinder == this.mRequestedEnterTransition) {
                this.mRequestedEnterTransition = null;
                this.mRequestedEnterTask = null;
            }
            if (findCurrentPipTaskChange != null && findCurrentPipTaskChange.getTaskInfo().getWindowingMode() != 2) {
                if (isEnteringPip(transitionInfo)) {
                    SurfaceControl leash2 = findCurrentPipTaskChange.getLeash();
                    transaction.remove(leash2);
                    this.mHasFadeOut = false;
                    this.mCurrentPipTaskToken = null;
                    PipTaskOrganizer pipTaskOrganizer = this.mPipOrganizer;
                    if (pipTaskOrganizer.mLeash == leash2) {
                        pipTaskOrganizer.onExitPipFinished(findCurrentPipTaskChange.getTaskInfo());
                    }
                } else {
                    Log.d("PipTransition", "skip resetPrevPip");
                }
            }
            if (!isEnteringPip(transitionInfo)) {
                if (findCurrentPipTaskChange != null) {
                    if (!findCurrentPipTaskChange.getEndAbsBounds().isEmpty()) {
                        pipBoundsState.setBounds(findCurrentPipTaskChange.getEndAbsBounds());
                    }
                    updatePipForUnhandledTransition(findCurrentPipTaskChange, transaction, transaction2);
                }
                return false;
            }
            for (int size2 = transitionInfo.getChanges().size() - 1; size2 >= 0; size2--) {
                TransitionInfo.Change change5 = (TransitionInfo.Change) transitionInfo.getChanges().get(size2);
                if (change5.getTaskInfo() != null && change5.getTaskInfo().getWindowingMode() == 2) {
                    change = change5;
                }
            }
            if (change == null) {
                throw new IllegalStateException("Trying to start PiP animation without a pipparticipant");
            }
            optional.ifPresent(new Consumer() { // from class: com.android.wm.shell.pip.PipTransition$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    SplitScreenController splitScreenController = (SplitScreenController) obj;
                    if (Transitions.hasDuplicatedOpenTypeChanges(transitionInfo) && splitScreenController.isSplitScreenActive() && !splitScreenController.isSplitScreenVisible()) {
                        splitScreenController.setSplitVisible();
                    }
                }
            });
            for (int size3 = transitionInfo.getChanges().size() - 1; size3 >= 0; size3--) {
                TransitionInfo.Change change6 = (TransitionInfo.Change) transitionInfo.getChanges().get(size3);
                if (change6 != change) {
                    if (TransitionUtil.isOpeningType(change6.getMode())) {
                        SurfaceControl leash3 = change6.getLeash();
                        transaction.show(leash3).setAlpha(leash3, 1.0f);
                    } else if (TransitionUtil.isClosingType(change6.getMode())) {
                        transaction.hide(change6.getLeash());
                    }
                }
            }
            startEnterAnimation(change, transaction, transaction2, transitionFinishCallback);
            return true;
        }
        this.mExitDestinationBounds.setEmpty();
        this.mExitTransition = null;
        if (CoreRune.MW_PIP_SHELL_TRANSITION) {
            this.mExitTransitionType = 0;
        }
        this.mMoveToBackTransition = null;
        this.mHasFadeOut = false;
        if (this.mFinishCallback != null) {
            callFinishCallback(null);
            this.mFinishTransaction = null;
            throw new RuntimeException("Previous callback not called, aborting exit PIP.");
        }
        final ActivityManager.RunningTaskInfo taskInfo = findCurrentPipTaskChange != null ? findCurrentPipTaskChange.getTaskInfo() : this.mPipOrganizer.mTaskInfo;
        if (taskInfo == null) {
            throw new RuntimeException("Cannot find the pip task for exit-pip transition.");
        }
        if (type != 4) {
            switch (type) {
                case 1001:
                    if (this.mCurrentPipTaskToken == null) {
                        Log.w("PipTaskOrganizer", "[PipTransition] There is no existing PiP Task for TRANSIT_EXIT_PIP");
                        if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                            ShellProtoLogImpl.m233w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 2102498210, 0, "%s: There is no existing PiP Task for TRANSIT_EXIT_PIP", "PipTransition");
                        }
                    } else if (findCurrentPipTaskChange == null) {
                        for (int m136m = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1); m136m >= 0; m136m--) {
                            TransitionInfo.Change change7 = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m);
                            if (this.mCurrentPipTaskToken.equals(change7.getLastParent())) {
                                leash = change7.getLeash();
                                change3 = change7;
                                if (change3 == null) {
                                    TransitionInfo.Root root = transitionInfo.getRoot(TransitionUtil.rootIndexFor(change3, transitionInfo));
                                    if (leash != null) {
                                        SurfaceControl leash4 = change3.getLeash();
                                        SurfaceControl build = new SurfaceControl.Builder().setName(leash + "_pip-leash").setContainerLayer().setHidden(false).setParent(root.getLeash()).build();
                                        transaction.reparent(leash4, build);
                                        Point endRelOffset = change3.getEndRelOffset();
                                        transaction.setPosition(leash, (float) endRelOffset.x, (float) endRelOffset.y);
                                        surfaceControl = build;
                                    } else {
                                        SurfaceControl leash5 = change3.getLeash();
                                        transaction.reparent(leash5, root.getLeash());
                                        surfaceControl = leash5;
                                    }
                                    transaction.setLayer(surfaceControl, Integer.MAX_VALUE);
                                    Point offset = root.getOffset();
                                    Rect bounds = pipBoundsState.getBounds();
                                    bounds.offset(-offset.x, -offset.y);
                                    transaction.setPosition(surfaceControl, bounds.left, bounds.top);
                                    final WindowContainerToken container = change3.getContainer();
                                    final boolean z5 = leash != null;
                                    final boolean equals2 = change3.getEndAbsBounds().equals(pipBoundsState.getDisplayBounds());
                                    TransitionInfo.Change change8 = change3;
                                    final SurfaceControl surfaceControl3 = surfaceControl;
                                    this.mFinishCallback = new Transitions.TransitionFinishCallback() { // from class: com.android.wm.shell.pip.PipTransition$$ExternalSyntheticLambda1
                                        @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
                                        public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, WindowContainerTransactionCallback windowContainerTransactionCallback) {
                                            PipTransition pipTransition = PipTransition.this;
                                            pipTransition.mPipOrganizer.onExitPipFinished(taskInfo);
                                            boolean z6 = false;
                                            if (!Transitions.SHELL_TRANSITIONS_ROTATION) {
                                                if (!equals2 && ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                                                    ShellProtoLogImpl.m229d(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1139121952, 0, "%s: startExitAnimation() not exiting to fullscreen", "PipTransition");
                                                }
                                                if (windowContainerTransaction == null) {
                                                    windowContainerTransaction = new WindowContainerTransaction();
                                                }
                                                windowContainerTransaction.setBounds(container, (Rect) null);
                                                PipTaskOrganizer pipTaskOrganizer2 = pipTransition.mPipOrganizer;
                                                windowContainerTransaction.setWindowingMode(pipTaskOrganizer2.mToken, pipTaskOrganizer2.getOutPipWindowingMode());
                                                windowContainerTransaction.setActivityWindowingMode(pipTaskOrganizer2.mToken, 0);
                                            }
                                            if (z5) {
                                                PipAnimationController pipAnimationController = pipTransition.mPipAnimationController;
                                                PipAnimationController.PipTransitionAnimator pipTransitionAnimator = pipAnimationController.mCurrentAnimator;
                                                if (pipTransitionAnimator != null && pipTransitionAnimator.isRunning()) {
                                                    z6 = true;
                                                }
                                                if (z6) {
                                                    pipAnimationController.mCurrentAnimator.end();
                                                }
                                                pipAnimationController.mCurrentAnimator = null;
                                                transaction2.remove(surfaceControl3);
                                            }
                                            transitionFinishCallback.onTransitionFinished(windowContainerTransaction, windowContainerTransactionCallback);
                                        }
                                    };
                                    this.mFinishTransaction = transaction2;
                                    int i6 = 90;
                                    if (Transitions.SHELL_TRANSITIONS_ROTATION) {
                                        int m136m2 = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1);
                                        while (true) {
                                            if (m136m2 >= 0) {
                                                change4 = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m2);
                                                if (change4.getMode() != 6 || (change4.getFlags() & 32) == 0 || change4.getStartRotation() == change4.getEndRotation()) {
                                                    m136m2--;
                                                }
                                            } else {
                                                change4 = null;
                                            }
                                        }
                                        if (change4 != null) {
                                            Log.d("PipTaskOrganizer", "[PipTransition] startExpandAndRotationAnimation");
                                            int deltaRotation = RotationUtils.deltaRotation(change4.getStartRotation(), change4.getEndRotation());
                                            CounterRotatorHelper counterRotatorHelper = new CounterRotatorHelper();
                                            counterRotatorHelper.handleClosingChanges(transaction, change4, transitionInfo);
                                            Rect rect = new Rect(change8.getStartAbsBounds());
                                            RotationUtils.rotateBounds(rect, change4.getStartAbsBounds(), deltaRotation);
                                            Rect rect2 = new Rect(change8.getEndAbsBounds());
                                            rect.offset(-offset.x, -offset.y);
                                            rect2.offset(-offset.x, -offset.y);
                                            int deltaRotation2 = RotationUtils.deltaRotation(deltaRotation, 0);
                                            if (deltaRotation2 == 1) {
                                                i5 = rect.right;
                                                i4 = rect.top;
                                            } else {
                                                int i7 = rect.left;
                                                i4 = rect.bottom;
                                                i5 = i7;
                                                i6 = -90;
                                            }
                                            this.mSurfaceTransactionHelper.rotateAndScaleWithCrop(transaction, change8.getLeash(), rect2, rect, new Rect(), i6, i5, i4, true, deltaRotation2 == 3);
                                            transaction.apply();
                                            counterRotatorHelper.cleanUp(transaction2);
                                            this.mPipAnimationController.getAnimator(taskInfo, change8.getLeash(), rect, rect, rect2, null, 3, 0.0f, deltaRotation2).setTransitionDirection(3).setPipAnimationCallback(this.mPipAnimationCallback).setDuration(this.mEnterExitAnimationDuration).start();
                                        }
                                    }
                                    Rect rect3 = new Rect(change8.getEndAbsBounds());
                                    rect3.offset(-offset.x, -offset.y);
                                    if (this.mInFixedRotation) {
                                        int deltaRotation3 = RotationUtils.deltaRotation(change8.getStartRotation(), this.mEndFixedRotation);
                                        Rect rect4 = new Rect(rect3);
                                        RotationUtils.rotateBounds(rect4, rect3, deltaRotation3);
                                        z3 = true;
                                        if (deltaRotation3 == 1) {
                                            i3 = rect3.right;
                                            i2 = rect3.top;
                                        } else {
                                            int i8 = rect3.left;
                                            i2 = rect3.bottom;
                                            i3 = i8;
                                            i6 = -90;
                                        }
                                        surfaceControl2 = surfaceControl;
                                        this.mSurfaceTransactionHelper.rotateAndScaleWithCrop(transaction2, surfaceControl, rect4, rect4, new Rect(), i6, i3, i2, true, deltaRotation3 == 3);
                                        i = deltaRotation3;
                                    } else {
                                        surfaceControl2 = surfaceControl;
                                        z3 = true;
                                        i = 0;
                                    }
                                    startExpandAnimation(taskInfo, surfaceControl2, bounds, bounds, rect3, i, transaction);
                                    z2 = z3;
                                    this.mCurrentPipTaskToken = null;
                                    return z2;
                                }
                                Log.w("PipTaskOrganizer", "[PipTransition] No window of exiting PIP is found. Can't play expand animation");
                                if (ShellProtoLogCache.WM_SHELL_PICTURE_IN_PICTURE_enabled) {
                                    ShellProtoLogImpl.m233w(ShellProtoLogGroup.WM_SHELL_PICTURE_IN_PICTURE, 1746143857, 0, "%s: No window of exiting PIP is found. Can't play expand animation", "PipTransition");
                                }
                                removePipImmediately(transitionInfo, transaction, transaction2, transitionFinishCallback, taskInfo);
                                z2 = true;
                                this.mCurrentPipTaskToken = null;
                                return z2;
                            }
                        }
                    }
                    leash = null;
                    change3 = findCurrentPipTaskChange;
                    if (change3 == null) {
                    }
                    z2 = true;
                    this.mCurrentPipTaskToken = null;
                    return z2;
                case 1002:
                    for (int size4 = transitionInfo.getChanges().size() - 1; size4 >= 0; size4--) {
                        TransitionInfo.Change change9 = (TransitionInfo.Change) transitionInfo.getChanges().get(size4);
                        int mode = change9.getMode();
                        if ((mode != 6 || change9.getParent() == null) && TransitionUtil.isOpeningType(mode) && change9.getParent() == null) {
                            SurfaceControl leash6 = change9.getLeash();
                            Rect endAbsBounds = change9.getEndAbsBounds();
                            transaction.show(leash6).setAlpha(leash6, 1.0f).setPosition(leash6, endAbsBounds.left, endAbsBounds.top).setWindowCrop(leash6, endAbsBounds.width(), endAbsBounds.height());
                        }
                    }
                    ((SplitScreenController) optional.get()).finishEnterSplitScreen(transaction2);
                    transaction.apply();
                    this.mPipOrganizer.onExitPipFinished(taskInfo);
                    transitionFinishCallback.onTransitionFinished(null, null);
                    z2 = true;
                    this.mCurrentPipTaskToken = null;
                    return z2;
                case 1003:
                    break;
                default:
                    z = true;
                    if (!CoreRune.MW_PIP_SHELL_TRANSITION) {
                        throw new IllegalStateException("mExitTransition with unexpected transit type=" + WindowManager.transitTypeToString(type));
                    }
                    Log.e("PipTransition", "startAnimation: mExitTransition with unexpected transit type=" + WindowManager.transitTypeToString(type) + ", pipTaskInfo=" + taskInfo + ", callers=" + Debug.getCallers(3));
                    removePipImmediately(transitionInfo, transaction, transaction2, transitionFinishCallback, taskInfo);
                    z2 = z;
                    this.mCurrentPipTaskToken = null;
                    return z2;
            }
        }
        z = true;
        removePipImmediately(transitionInfo, transaction, transaction2, transitionFinishCallback, taskInfo);
        z2 = z;
        this.mCurrentPipTaskToken = null;
        return z2;
    }

    /* JADX WARN: Removed duplicated region for block: B:105:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0099  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01b1  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x01c0  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x025f  */
    @Override // com.android.p038wm.shell.pip.PipTransitionController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void startEnterAnimation(TransitionInfo.Change change, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        PipBoundsState pipBoundsState;
        boolean z;
        Rect rect;
        PipTaskOrganizer pipTaskOrganizer;
        PictureInPictureParams pictureInPictureParams;
        C40441 c40441;
        int i;
        int i2;
        PipBoundsAlgorithm pipBoundsAlgorithm;
        SurfaceControl surfaceControl;
        Rect rect2;
        PipAnimationController.PipTransitionAnimator animator;
        PipAnimationController.PipTransitionAnimator pipTransitionAnimator;
        SurfaceControl.Transaction transaction3;
        boolean z2;
        Rect rect3;
        if (this.mFinishCallback != null) {
            callFinishCallback(null);
            this.mFinishTransaction = null;
            throw new RuntimeException("Previous callback not called, aborting entering PIP.");
        }
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
        if (!this.mInFixedRotation) {
            int endFixedRotation = change.getEndFixedRotation();
            this.mEndFixedRotation = endFixedRotation;
            this.mInFixedRotation = endFixedRotation != -1;
        }
        int endRotation = this.mInFixedRotation ? this.mEndFixedRotation : change.getEndRotation();
        ComponentName componentName = taskInfo.topActivity;
        PictureInPictureParams pictureInPictureParams2 = taskInfo.pictureInPictureParams;
        ActivityInfo activityInfo = taskInfo.topActivityInfo;
        PipBoundsState pipBoundsState2 = this.mPipBoundsState;
        PipBoundsAlgorithm pipBoundsAlgorithm2 = this.mPipBoundsAlgorithm;
        pipBoundsState2.setBoundsStateForEntry(componentName, activityInfo, pictureInPictureParams2, pipBoundsAlgorithm2);
        PipTaskOrganizer pipTaskOrganizer2 = this.mPipOrganizer;
        pipTaskOrganizer2.getClass();
        if (pipTaskOrganizer2 instanceof TvPipTaskOrganizer) {
            this.mPipMenuController.attach(leash);
        }
        Rect entryDestinationBounds = pipBoundsAlgorithm2.getEntryDestinationBounds();
        Rect startAbsBounds = change.getStartAbsBounds();
        int deltaRotation = RotationUtils.deltaRotation(startRotation, endRotation);
        Rect validSourceHintRect = PipBoundsAlgorithm.getValidSourceHintRect(taskInfo.pictureInPictureParams, startAbsBounds);
        if (validSourceHintRect != null) {
            pipBoundsState = pipBoundsState2;
            if (validSourceHintRect.width() > entryDestinationBounds.width() && validSourceHintRect.height() > entryDestinationBounds.height()) {
                z = true;
                if (!z) {
                    validSourceHintRect = null;
                }
                rect = validSourceHintRect;
                if (deltaRotation != 0 && this.mInFixedRotation) {
                    PipDisplayLayoutState pipDisplayLayoutState = this.mPipDisplayLayoutState;
                    pipDisplayLayoutState.rotateTo(endRotation);
                    Rect displayBounds = pipDisplayLayoutState.getDisplayBounds();
                    entryDestinationBounds.set(pipBoundsAlgorithm2.getEntryDestinationBounds());
                    RotationUtils.rotateBounds(entryDestinationBounds, displayBounds, endRotation, startRotation);
                    if (rect != null && (rect3 = ((TaskInfo) taskInfo).displayCutoutInsets) != null && deltaRotation == 3) {
                        rect.offset(rect3.left, rect3.top);
                    }
                    StringBuilder sb = new StringBuilder("[PipTransition] computeEnterPipRotatedBounds, currentBounds=");
                    sb.append(startAbsBounds);
                    sb.append(" destinationBounds=");
                    sb.append(entryDestinationBounds);
                    sb.append("startRotation=");
                    KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m79m(sb, startRotation, "endRotation=", endRotation, "PipTaskOrganizer");
                }
                pipTaskOrganizer = this.mPipOrganizer;
                pipTaskOrganizer.getClass();
                if (!(pipTaskOrganizer instanceof TvPipTaskOrganizer)) {
                    ((HandlerExecutor) this.mTransitions.mMainExecutor).executeDelayed(0L, new Runnable() { // from class: com.android.wm.shell.pip.PipTransition.2
                        @Override // java.lang.Runnable
                        public final void run() {
                            PipTransition.this.mPipMenuController.attach(leash);
                            PipTransition pipTransition = PipTransition.this;
                            pipTransition.mPipMenuController.setSplitMenuEnabled(pipTransition.mPipOrganizer.shouldShowSplitMenu());
                        }
                    });
                }
                pictureInPictureParams = taskInfo.pictureInPictureParams;
                c40441 = this.mTransactionConsumer;
                if (pictureInPictureParams == null && pictureInPictureParams.isAutoEnterEnabled() && pipTransitionState.mInSwipePipToHomeTransition) {
                    if (this.mInFixedRotation && ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                        ShellProtoLogImpl.m233w(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 1500106685, 4, "%s: SwipePipToHome should not use fixed rotation %d", "PipTransition", Long.valueOf(this.mEndFixedRotation));
                    }
                    SurfaceControl surfaceControl2 = this.mPipOrganizer.mSwipePipToHomeOverlay;
                    if (surfaceControl2 != null) {
                        transaction.reparent(surfaceControl2, leash).setLayer(surfaceControl2, Integer.MAX_VALUE);
                        this.mPipOrganizer.mSwipePipToHomeOverlay = null;
                    }
                    Rect bounds = taskInfo.configuration.windowConfiguration.getBounds();
                    PipAnimationController.PipTransitionAnimator animator2 = this.mPipAnimationController.getAnimator(taskInfo, leash, bounds, bounds, entryDestinationBounds, rect, 2, 0.0f, 0);
                    animator2.mPipTransactionHandler = c40441;
                    PipAnimationController.PipTransitionAnimator transitionDirection = animator2.setTransitionDirection(2);
                    transaction.merge(transaction2);
                    transitionDirection.applySurfaceControlTransaction(1.0f, transaction, leash);
                    transaction.apply();
                    pipBoundsState.setBounds(entryDestinationBounds);
                    onFinishResize(taskInfo, entryDestinationBounds, 2, new SurfaceControl.Transaction());
                    sendOnPipTransitionFinished(2);
                    if (surfaceControl2 != null) {
                        if (!CoreRune.MW_PIP_SHELL_TRANSITION) {
                            z2 = false;
                            this.mPipOrganizer.fadeOutAndRemoveOverlay(surfaceControl2, null, false, -1);
                            pipTransitionState.mInSwipePipToHomeTransition = z2;
                            return;
                        }
                        this.mPipOrganizer.fadeOutAndRemoveOverlay(surfaceControl2, null, true, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend);
                    }
                    z2 = false;
                    pipTransitionState.mInSwipePipToHomeTransition = z2;
                    return;
                }
                PipBoundsState pipBoundsState3 = pipBoundsState;
                i = this.mEnterAnimationType;
                if (i != 1) {
                    transaction.setAlpha(leash, 0.0f);
                } else {
                    transaction.setAlpha(leash, 1.0f);
                }
                transaction.apply();
                if (i != 0) {
                    i2 = deltaRotation;
                    pipBoundsAlgorithm = pipBoundsAlgorithm2;
                    pipTransitionAnimator = this.mPipAnimationController.getAnimator(taskInfo, leash, startAbsBounds, startAbsBounds, entryDestinationBounds, rect, 2, 0.0f, i2);
                    if (rect == null) {
                        boolean z3 = taskInfo.topActivityInfo != null;
                        if (!z3 && ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                            ShellProtoLogImpl.m233w(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 846227226, 0, "%s: TaskInfo.topActivityInfo is null", "PipTransition");
                        }
                        if (SystemProperties.getBoolean("persist.wm.debug.enable_pip_app_icon_overlay", true) && z3) {
                            Context context = this.mContext;
                            ActivityInfo activityInfo2 = taskInfo.topActivityInfo;
                            int i3 = pipBoundsState3.mLauncherState.mAppIconSizePx;
                            pipTransitionAnimator.getClass();
                            IconProvider iconProvider = new IconProvider(context);
                            pipTransitionAnimator.reattachContentOverlay(new PipContentOverlay.PipAppIconOverlay(context, startAbsBounds, entryDestinationBounds, iconProvider.getIcon(iconProvider.mContext.getResources().getConfiguration().densityDpi, activityInfo2), i3));
                        } else {
                            pipTransitionAnimator.getClass();
                            pipTransitionAnimator.reattachContentOverlay(new PipContentOverlay.PipColorOverlay(this.mContext));
                        }
                    } else {
                        TaskSnapshot taskSnapshot = PipUtils.getTaskSnapshot(taskInfo.launchIntoPipHostTaskId);
                        if (taskSnapshot != null) {
                            pipTransitionAnimator.getClass();
                            pipTransitionAnimator.reattachContentOverlay(new PipContentOverlay.PipSnapshotOverlay(taskSnapshot, rect));
                        }
                    }
                    transaction3 = transaction2;
                    surfaceControl = leash;
                } else {
                    i2 = deltaRotation;
                    pipBoundsAlgorithm = pipBoundsAlgorithm2;
                    surfaceControl = leash;
                    if (i != 1) {
                        throw new RuntimeException(AbstractC0000x2c234b15.m0m("Unrecognized animation type: ", i));
                    }
                    boolean z4 = CoreRune.MW_PIP_SHELL_TRANSITION;
                    PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
                    if (z4 && this.mInEnterPipFromSplit) {
                        StringBuilder sb2 = new StringBuilder("startEnterAnimation: enterPipFromSplit, leash=");
                        sb2.append(surfaceControl);
                        sb2.append(", destinationBounds=");
                        rect2 = entryDestinationBounds;
                        sb2.append(rect2);
                        Log.d("PipTransition", sb2.toString());
                        pipBoundsState3.setBounds(rect2);
                        onFinishResize(taskInfo, rect2, 2, new SurfaceControl.Transaction());
                        pipSurfaceTransactionHelper.crop(rect2, transaction, surfaceControl);
                        pipSurfaceTransactionHelper.round(surfaceControl, true, transaction);
                        animator = this.mPipAnimationController.getAnimator(taskInfo, surfaceControl, rect2, 0.9f, 1.0f);
                    } else {
                        rect2 = entryDestinationBounds;
                        animator = this.mPipAnimationController.getAnimator(taskInfo, surfaceControl, rect2, 0.0f, 1.0f);
                    }
                    pipTransitionAnimator = animator;
                    transaction3 = transaction2;
                    pipSurfaceTransactionHelper.crop(rect2, transaction3, surfaceControl);
                    pipSurfaceTransactionHelper.round(surfaceControl, true, transaction3);
                }
                pipTransitionAnimator.setTransitionDirection(2).setPipAnimationCallback(this.mPipAnimationCallback).setDuration(this.mEnterExitAnimationDuration);
                if (i2 != 0 && this.mInFixedRotation) {
                    pipTransitionAnimator.setDestinationBounds(pipBoundsAlgorithm.getEntryDestinationBounds());
                }
                pipTransitionAnimator.mPipTransactionHandler = c40441;
                pipTransitionAnimator.applySurfaceControlTransaction(1.0f, transaction3, surfaceControl);
                pipTransitionAnimator.mPipTransactionHandler = this.mPipOrganizer.mPipTransactionHandler;
                pipTransitionAnimator.start();
            }
        } else {
            pipBoundsState = pipBoundsState2;
        }
        z = false;
        if (!z) {
        }
        rect = validSourceHintRect;
        if (deltaRotation != 0) {
            PipDisplayLayoutState pipDisplayLayoutState2 = this.mPipDisplayLayoutState;
            pipDisplayLayoutState2.rotateTo(endRotation);
            Rect displayBounds2 = pipDisplayLayoutState2.getDisplayBounds();
            entryDestinationBounds.set(pipBoundsAlgorithm2.getEntryDestinationBounds());
            RotationUtils.rotateBounds(entryDestinationBounds, displayBounds2, endRotation, startRotation);
            if (rect != null) {
                rect.offset(rect3.left, rect3.top);
            }
            StringBuilder sb3 = new StringBuilder("[PipTransition] computeEnterPipRotatedBounds, currentBounds=");
            sb3.append(startAbsBounds);
            sb3.append(" destinationBounds=");
            sb3.append(entryDestinationBounds);
            sb3.append("startRotation=");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m79m(sb3, startRotation, "endRotation=", endRotation, "PipTaskOrganizer");
        }
        pipTaskOrganizer = this.mPipOrganizer;
        pipTaskOrganizer.getClass();
        if (!(pipTaskOrganizer instanceof TvPipTaskOrganizer)) {
        }
        pictureInPictureParams = taskInfo.pictureInPictureParams;
        c40441 = this.mTransactionConsumer;
        if (pictureInPictureParams == null) {
        }
        PipBoundsState pipBoundsState32 = pipBoundsState;
        i = this.mEnterAnimationType;
        if (i != 1) {
        }
        transaction.apply();
        if (i != 0) {
        }
        pipTransitionAnimator.setTransitionDirection(2).setPipAnimationCallback(this.mPipAnimationCallback).setDuration(this.mEnterExitAnimationDuration);
        if (i2 != 0) {
            pipTransitionAnimator.setDestinationBounds(pipBoundsAlgorithm.getEntryDestinationBounds());
        }
        pipTransitionAnimator.mPipTransactionHandler = c40441;
        pipTransitionAnimator.applySurfaceControlTransaction(1.0f, transaction3, surfaceControl);
        pipTransitionAnimator.mPipTransactionHandler = this.mPipOrganizer.mPipTransactionHandler;
        pipTransitionAnimator.start();
    }

    @Override // com.android.p038wm.shell.pip.PipTransitionController
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
        StringBuilder sb = new StringBuilder("[PipTransition] startExpandAnimation startBounds=");
        sb.append(rect2);
        sb.append(" endBounds=");
        sb.append(rect3);
        sb.append(" rotDelta=");
        RecyclerView$$ExternalSyntheticOutline0.m46m(sb, i, "PipTaskOrganizer");
        PipAnimationController.PipTransitionAnimator animator = this.mPipAnimationController.getAnimator(taskInfo, surfaceControl, rect, rect2, rect3, validSourceHintRect, 3, 0.0f, i);
        animator.setTransitionDirection(3).setDuration(this.mEnterExitAnimationDuration);
        if (transaction != null) {
            animator.mPipTransactionHandler = this.mTransactionConsumer;
            animator.applySurfaceControlTransaction(0.0f, transaction, surfaceControl);
            transaction.apply();
        }
        PipAnimationController.PipTransitionAnimator pipAnimationCallback = animator.setPipAnimationCallback(this.mPipAnimationCallback);
        pipAnimationCallback.mPipTransactionHandler = this.mPipOrganizer.mPipTransactionHandler;
        pipAnimationCallback.start();
    }

    @Override // com.android.p038wm.shell.pip.PipTransitionController
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
        boolean isInPip = this.mPipTransitionState.isInPip();
        PipSurfaceTransactionHelper pipSurfaceTransactionHelper = this.mSurfaceTransactionHelper;
        pipSurfaceTransactionHelper.crop(bounds, transaction, leash);
        pipSurfaceTransactionHelper.round(leash, isInPip, transaction);
        pipSurfaceTransactionHelper.shadow(leash, isInPip, transaction);
        pipSurfaceTransactionHelper.crop(bounds, transaction2, leash);
        pipSurfaceTransactionHelper.round(leash, isInPip, transaction2);
        pipSurfaceTransactionHelper.shadow(leash, isInPip, transaction2);
        if (isInPip && this.mHasFadeOut) {
            transaction.setAlpha(leash, 0.0f);
            transaction2.setAlpha(leash, 0.0f);
        }
    }

    @Override // com.android.p038wm.shell.pip.PipTransitionController
    public final boolean isEnteringPip(int i, TransitionInfo.Change change) {
        if (change.getTaskInfo() == null || change.getTaskInfo().getWindowingMode() != 2 || change.getContainer().equals(this.mCurrentPipTaskToken)) {
            return false;
        }
        boolean z = CoreRune.MW_PIP_SHELL_TRANSITION;
        if (z && this.mPipTransitionState.mState == 0) {
            return false;
        }
        if (i == 10 || i == 1 || i == 6) {
            return true;
        }
        if (z && change.isEnteringPinnedMode()) {
            return true;
        }
        Slog.e("PipTransition", "Found new PIP in transition with mis-matched type=" + WindowManager.transitTypeToString(i), new Throwable());
        return false;
    }
}
