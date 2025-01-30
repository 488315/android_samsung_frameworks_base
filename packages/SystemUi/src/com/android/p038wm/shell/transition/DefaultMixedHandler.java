package com.android.p038wm.shell.transition;

import android.animation.Animator;
import android.app.ActivityManager;
import android.graphics.Rect;
import android.os.IBinder;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.RemoteTransition;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import android.window.WindowContainerTransactionCallback;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.common.split.SplitLayout;
import com.android.p038wm.shell.keyguard.KeyguardTransitionHandler;
import com.android.p038wm.shell.pip.PipTransition;
import com.android.p038wm.shell.pip.PipTransitionController;
import com.android.p038wm.shell.pip.PipTransitionState;
import com.android.p038wm.shell.pip.phone.PipTouchHandler;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import com.android.p038wm.shell.recents.RecentsTransitionHandler;
import com.android.p038wm.shell.splitscreen.CellStage;
import com.android.p038wm.shell.splitscreen.MainStage;
import com.android.p038wm.shell.splitscreen.SideStage;
import com.android.p038wm.shell.splitscreen.SplitScreenController;
import com.android.p038wm.shell.splitscreen.SplitScreenTransitions;
import com.android.p038wm.shell.splitscreen.SplitScreenTransitions$$ExternalSyntheticLambda1;
import com.android.p038wm.shell.splitscreen.StageCoordinator;
import com.android.p038wm.shell.splitscreen.StageCoordinator$$ExternalSyntheticLambda16;
import com.android.p038wm.shell.splitscreen.StageTaskListener;
import com.android.p038wm.shell.sysui.ShellInit;
import com.android.p038wm.shell.taskview.TaskViewTaskController;
import com.android.p038wm.shell.taskview.TaskViewTransitions;
import com.android.p038wm.shell.transition.DefaultMixedHandler;
import com.android.p038wm.shell.transition.Transitions;
import com.android.p038wm.shell.unfold.UnfoldTransitionHandler;
import com.android.p038wm.shell.util.TransitionUtil;
import com.android.systemui.keyguard.KeyguardService$$ExternalSyntheticOutline0;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DefaultMixedHandler implements Transitions.TransitionHandler {
    public final ArrayList mActiveTransitions = new ArrayList();
    public final KeyguardTransitionHandler mKeyguardHandler;
    public PipTransitionController mPipHandler;
    public final Transitions mPlayer;
    public RecentsTransitionHandler mRecentsHandler;
    public StageCoordinator mSplitHandler;
    public final TaskViewTransitions mTaskViewTransitions;
    public UnfoldTransitionHandler mUnfoldHandler;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MixedTransition {
        public final IBinder mTransition;
        public final int mType;
        public int mAnimType = 0;
        public Transitions.TransitionHandler mLeftoversHandler = null;
        public WindowContainerTransaction mFinishWCT = null;
        public int mInFlightSubAnimations = 0;

        public MixedTransition(int i, IBinder iBinder) {
            this.mType = i;
            this.mTransition = iBinder;
        }

        public final void joinFinishArgs(WindowContainerTransaction windowContainerTransaction, WindowContainerTransactionCallback windowContainerTransactionCallback) {
            if (windowContainerTransactionCallback != null) {
                throw new IllegalArgumentException("Can't mix transitions that require finish sync callback");
            }
            if (windowContainerTransaction != null) {
                WindowContainerTransaction windowContainerTransaction2 = this.mFinishWCT;
                if (windowContainerTransaction2 == null) {
                    this.mFinishWCT = windowContainerTransaction;
                } else {
                    windowContainerTransaction2.merge(windowContainerTransaction, true);
                }
            }
        }
    }

    public DefaultMixedHandler(ShellInit shellInit, Transitions transitions, final Optional<SplitScreenController> optional, final Optional<PipTouchHandler> optional2, final Optional<RecentsTransitionHandler> optional3, KeyguardTransitionHandler keyguardTransitionHandler, final Optional<UnfoldTransitionHandler> optional4, TaskViewTransitions taskViewTransitions) {
        this.mPlayer = transitions;
        this.mKeyguardHandler = keyguardTransitionHandler;
        if (Transitions.ENABLE_SHELL_TRANSITIONS && optional2.isPresent() && optional.isPresent()) {
            shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.transition.DefaultMixedHandler$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DefaultMixedHandler defaultMixedHandler = DefaultMixedHandler.this;
                    Optional optional5 = optional2;
                    Optional optional6 = optional;
                    Optional optional7 = optional3;
                    Optional optional8 = optional4;
                    defaultMixedHandler.getClass();
                    defaultMixedHandler.mPipHandler = ((PipTouchHandler) optional5.get()).mPipTaskOrganizer.mPipTransitionController;
                    defaultMixedHandler.mSplitHandler = ((SplitScreenController) optional6.get()).getTransitionHandler();
                    defaultMixedHandler.mPlayer.addHandler(defaultMixedHandler);
                    StageCoordinator stageCoordinator = defaultMixedHandler.mSplitHandler;
                    if (stageCoordinator != null) {
                        stageCoordinator.mMixedHandler = defaultMixedHandler;
                    }
                    RecentsTransitionHandler recentsTransitionHandler = (RecentsTransitionHandler) optional7.orElse(null);
                    defaultMixedHandler.mRecentsHandler = recentsTransitionHandler;
                    if (recentsTransitionHandler != null) {
                        recentsTransitionHandler.mMixers.add(defaultMixedHandler);
                    }
                    defaultMixedHandler.mUnfoldHandler = (UnfoldTransitionHandler) optional8.orElse(null);
                }
            }, this);
        }
        this.mTaskViewTransitions = taskViewTransitions;
    }

    public static void excludeForceHidingChanges(TransitionInfo transitionInfo) {
        for (int m136m = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1); m136m >= 0; m136m--) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m);
            if (change.getForceHidingTransit() == 1) {
                Log.d("DefaultMixedHandler", "excludeForceHidingChanges: " + change + ", reason=animateKeyguard");
                transitionInfo.getChanges().remove(change);
            }
        }
    }

    public static TransitionInfo subCopy(TransitionInfo transitionInfo, int i, boolean z) {
        TransitionInfo transitionInfo2 = new TransitionInfo(i, z ? transitionInfo.getFlags() : 0);
        transitionInfo2.setTrack(transitionInfo.getTrack());
        transitionInfo2.setDebugId(transitionInfo.getDebugId());
        if (z) {
            for (int i2 = 0; i2 < transitionInfo.getChanges().size(); i2++) {
                transitionInfo2.getChanges().add((TransitionInfo.Change) transitionInfo.getChanges().get(i2));
            }
        }
        for (int i3 = 0; i3 < transitionInfo.getRootCount(); i3++) {
            transitionInfo2.addRoot(transitionInfo.getRoot(i3));
        }
        transitionInfo2.setAnimationOptions(transitionInfo.getAnimationOptions());
        return transitionInfo2;
    }

    public final boolean animateEnterPipFromSplit(final MixedTransition mixedTransition, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, final Transitions.TransitionFinishCallback transitionFinishCallback) {
        TransitionInfo.Change change;
        int i;
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 2124597812, 0, " Animating a mixed transition for entering PIP while Split-Screen is foreground.", null);
        }
        TransitionInfo subCopy = subCopy(transitionInfo, 4, true);
        TransitionInfo.Change change2 = null;
        TransitionInfo.Change change3 = null;
        final boolean z = false;
        for (int m136m = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1); m136m >= 0; m136m--) {
            TransitionInfo.Change change4 = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m);
            if (!this.mPipHandler.isEnteringPip(transitionInfo.getType(), change4)) {
                if (!(change4.getTaskInfo() != null && change4.getTaskInfo().getActivityType() == 2)) {
                    if (!(change4.getTaskInfo() != null && change4.getTaskInfo().getActivityType() == 3)) {
                        if ((2 & change4.getFlags()) != 0) {
                            change2 = change4;
                        }
                    }
                }
                z = true;
            } else {
                if (change3 != null) {
                    throw new IllegalStateException("More than 1 pip-entering changes in one transition? " + transitionInfo);
                }
                subCopy.getChanges().remove(m136m);
                change3 = change4;
            }
        }
        if (change3 == null) {
            this.mActiveTransitions.remove(mixedTransition);
            return false;
        }
        Transitions.TransitionFinishCallback transitionFinishCallback2 = new Transitions.TransitionFinishCallback() { // from class: com.android.wm.shell.transition.DefaultMixedHandler$$ExternalSyntheticLambda3
            @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
            public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, WindowContainerTransactionCallback windowContainerTransactionCallback) {
                DefaultMixedHandler defaultMixedHandler = DefaultMixedHandler.this;
                defaultMixedHandler.getClass();
                DefaultMixedHandler.MixedTransition mixedTransition2 = mixedTransition;
                mixedTransition2.mInFlightSubAnimations--;
                mixedTransition2.joinFinishArgs(windowContainerTransaction, windowContainerTransactionCallback);
                if (mixedTransition2.mInFlightSubAnimations > 0) {
                    return;
                }
                defaultMixedHandler.mActiveTransitions.remove(mixedTransition2);
                if (z) {
                    defaultMixedHandler.mSplitHandler.onTransitionAnimationComplete();
                }
                transitionFinishCallback.onTransitionFinished(mixedTransition2.mFinishWCT, windowContainerTransactionCallback);
            }
        };
        if (!z && this.mSplitHandler.getSplitItemPosition(change3.getLastParent()) == -1) {
            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 41497685, 0, "  Not leaving split, so just forward animation to Pip-Handler.", null);
            }
            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && !this.mSplitHandler.isMultiSplitActive() && this.mSplitHandler.mCellStageListener.mVisible) {
                this.mSplitHandler.prepareDismissAnimation(-1, 9, subCopy, new SurfaceControl.Transaction(), transaction2, true);
            }
            mixedTransition.mInFlightSubAnimations = 1;
            ((PipTransition) this.mPipHandler).startAnimation(mixedTransition.mTransition, transitionInfo, transaction, transaction2, transitionFinishCallback2);
            return true;
        }
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 535641167, 0, " Animation is actually mixed since entering-PiP caused us to leave split and return home.", null);
        }
        mixedTransition.mInFlightSubAnimations = 2;
        if (change2 != null) {
            transaction.show(change2.getLeash()).setAlpha(change2.getLeash(), 1.0f);
        }
        SurfaceControl.Transaction transaction3 = new SurfaceControl.Transaction();
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && this.mSplitHandler.isMultiSplitScreenVisible()) {
            int m136m2 = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1);
            while (true) {
                if (m136m2 < 0) {
                    i = -1;
                    break;
                }
                TransitionInfo.Change change5 = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m2);
                if (change5 != change3 && (i = this.mSplitHandler.getSplitItemStage(change5.getLastParent())) != -1) {
                    break;
                }
                m136m2--;
            }
            change = change3;
            this.mSplitHandler.prepareDismissAnimation(i, 9, subCopy, transaction3, transaction2, !z);
        } else {
            change = change3;
            if (this.mSplitHandler.isSplitScreenVisible()) {
                for (int m136m3 = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1); m136m3 >= 0; m136m3--) {
                    TransitionInfo.Change change6 = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m3);
                    if (change6 != change && (r10 = this.mSplitHandler.getSplitItemStage(change6.getLastParent())) != -1) {
                        break;
                    }
                }
            }
            int i2 = -1;
            this.mSplitHandler.prepareDismissAnimation(i2, 9, subCopy, transaction3, transaction2, false);
        }
        int m136m4 = KeyguardService$$ExternalSyntheticOutline0.m136m(subCopy, 1);
        while (true) {
            if (m136m4 < 0) {
                break;
            }
            if ((((TransitionInfo.Change) subCopy.getChanges().get(m136m4)).getFlags() & QuickStepContract.SYSUI_STATE_BACK_DISABLED) != 0) {
                subCopy.getChanges().remove(m136m4);
                break;
            }
            m136m4--;
        }
        if (CoreRune.MW_PIP_SHELL_TRANSITION) {
            this.mPipHandler.onStartEnterPipFromSplit(change);
            final int endDisplayId = change.getEndDisplayId();
            TransitionInfo.Change findChange = subCopy.findChange(new Predicate() { // from class: com.android.wm.shell.transition.DefaultMixedHandler$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    TransitionInfo.Change change7 = (TransitionInfo.Change) obj;
                    return change7.hasFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE) && change7.getEndDisplayId() == endDisplayId && change7.getSnapshot() != null;
                }
            });
            if (findChange != null && subCopy.getRootCount() > 0) {
                transaction.reparent(findChange.getSnapshot(), subCopy.getRoot(TransitionUtil.rootIndexFor(findChange, subCopy)).getLeash());
                transaction2.reparent(findChange.getSnapshot(), null);
                StringBuilder sb = new StringBuilder("animateEnterPipFromSplit: reparent ");
                sb.append(findChange.getSnapshot());
                sb.append(", t=");
                ExifInterface$$ExternalSyntheticOutline0.m35m(sb, transaction.mDebugName, "DefaultMixedHandler");
            }
        }
        this.mPipHandler.setEnterAnimationType(1);
        this.mPipHandler.startEnterAnimation(change, transaction, transaction2, transitionFinishCallback2);
        mixedTransition.mLeftoversHandler = this.mPlayer.dispatchTransition(mixedTransition.mTransition, subCopy, transaction3, transaction2, transitionFinishCallback2, this, null);
        return true;
    }

    public final boolean animateKeyguard(MixedTransition mixedTransition, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        DefaultMixedHandler$$ExternalSyntheticLambda1 defaultMixedHandler$$ExternalSyntheticLambda1 = new DefaultMixedHandler$$ExternalSyntheticLambda1(this, mixedTransition, transitionFinishCallback, 1);
        mixedTransition.mInFlightSubAnimations++;
        PipTransitionController pipTransitionController = this.mPipHandler;
        if (pipTransitionController != null) {
            pipTransitionController.syncPipSurfaceState(transitionInfo, transaction, transaction2);
        }
        if (CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION) {
            excludeForceHidingChanges(transitionInfo);
        }
        if (this.mKeyguardHandler.startAnimation(mixedTransition.mTransition, transitionInfo, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda1)) {
            return true;
        }
        mixedTransition.mInFlightSubAnimations--;
        return false;
    }

    public final boolean animatePendingSplitWithDisplayChange(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        boolean z;
        boolean z2;
        TransitionInfo subCopy = subCopy(transitionInfo, transitionInfo.getType(), true);
        TransitionInfo subCopy2 = subCopy(transitionInfo, 6, false);
        for (int m136m = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1); m136m >= 0; m136m--) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m);
            TransitionInfo.Change change2 = change;
            while (change2 != null) {
                if (change2.getTaskInfo() != null) {
                    z2 = true;
                    break;
                }
                if (change2.getParent() == null) {
                    break;
                }
                change2 = transitionInfo.getChange(change2.getParent());
            }
            z2 = false;
            if (!z2) {
                subCopy2.addChange(change);
                subCopy.getChanges().remove(m136m);
            }
        }
        if (subCopy2.getChanges().isEmpty()) {
            return false;
        }
        for (int i = 0; i < subCopy.getChanges().size(); i++) {
            TransitionInfo.Change change3 = (TransitionInfo.Change) subCopy.getChanges().get(i);
            if (change3.getParent() != null && subCopy.getChange(change3.getParent()) == null) {
                ((TransitionInfo.Change) subCopy.getChanges().get(i)).setParent((WindowContainerToken) null);
            }
        }
        if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION && subCopy2.hasCustomDisplayChangeTransition()) {
            subCopy.setSeparatedFromCustomDisplayChange(true);
        }
        MixedTransition mixedTransition = new MixedTransition(2, iBinder);
        this.mActiveTransitions.add(mixedTransition);
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 17393967, 0, " Animation is a mix of display change and split change.", null);
        }
        mixedTransition.mInFlightSubAnimations = 2;
        if (CoreRune.MW_PIP_SHELL_TRANSITION) {
            for (int m136m2 = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1); m136m2 >= 0; m136m2--) {
                if (this.mPipHandler.isEnteringPip(transitionInfo.getType(), (TransitionInfo.Change) transitionInfo.getChanges().get(m136m2))) {
                    mixedTransition.mInFlightSubAnimations++;
                    z = true;
                    break;
                }
            }
        }
        z = false;
        if (this.mSplitHandler.mSplitTransitions.isPendingDismiss(iBinder)) {
            for (int m136m3 = KeyguardService$$ExternalSyntheticOutline0.m136m(subCopy, 1); m136m3 >= 0; m136m3--) {
                TransitionInfo.Change change4 = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m3);
                SurfaceControl leash = change4.getLeash();
                if (leash != null && change4.getMode() == 4) {
                    transaction.hide(leash);
                }
            }
        }
        DefaultMixedHandler$$ExternalSyntheticLambda1 defaultMixedHandler$$ExternalSyntheticLambda1 = new DefaultMixedHandler$$ExternalSyntheticLambda1(this, mixedTransition, transitionFinishCallback, 6);
        mixedTransition.mLeftoversHandler = this.mPlayer.dispatchTransition(mixedTransition.mTransition, subCopy2, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda1, this.mSplitHandler, this.mPipHandler);
        this.mSplitHandler.startPendingAnimation(iBinder, subCopy, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda1);
        if (CoreRune.MW_PIP_SHELL_TRANSITION && z) {
            ((PipTransition) this.mPipHandler).startAnimation(iBinder, subCopy, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda1);
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x004b, code lost:
    
        if ((r10.getTopVisibleChildTaskId() == r5 ? r3.mSideStagePosition : r3.mMainStage.getTopVisibleChildTaskId() == r5 ? r3.getMainStagePosition() : -1) != (-1)) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x0064, code lost:
    
        if (r18.getTriggerTask().taskId == r11.getTopVisibleChildTaskId()) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x0081, code lost:
    
        if (r11.getChildCount() == 0) goto L38;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:113:0x01d2  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x008c  */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        boolean z;
        StageCoordinator stageCoordinator = this.mSplitHandler;
        MainStage mainStage = stageCoordinator.mMainStage;
        int i = -1;
        if (mainStage.mIsActive) {
            stageCoordinator.mMixedHandler.mPipHandler.getClass();
            if (transitionRequestInfo.getType() == 10) {
                ActivityManager.RunningTaskInfo triggerTask = transitionRequestInfo.getTriggerTask();
                SideStage sideStage = stageCoordinator.mSideStage;
                if (triggerTask != null) {
                    int i2 = transitionRequestInfo.getTriggerTask().taskId;
                }
                boolean z2 = CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER;
                CellStage cellStage = stageCoordinator.mCellStage;
                if (z2) {
                    if (transitionRequestInfo.getTriggerTask() != null) {
                    }
                }
                if (mainStage.getChildCount() != 0) {
                    if (sideStage.getChildCount() != 0) {
                        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
                            if (stageCoordinator.isMultiSplitActive()) {
                            }
                        }
                    }
                }
                z = true;
                ArrayList arrayList = this.mActiveTransitions;
                if (z) {
                    RemoteTransition remoteTransition = transitionRequestInfo.getRemoteTransition();
                    Transitions transitions = this.mPlayer;
                    if (remoteTransition != null && TransitionUtil.isOpeningType(transitionRequestInfo.getType()) && (transitionRequestInfo.getTriggerTask() == null || (transitionRequestInfo.getTriggerTask().topActivityType != 2 && transitionRequestInfo.getTriggerTask().topActivityType != 3))) {
                        Pair dispatchRequest = transitions.dispatchRequest(iBinder, transitionRequestInfo, this);
                        if (dispatchRequest == null) {
                            return null;
                        }
                        MixedTransition mixedTransition = new MixedTransition(3, iBinder);
                        mixedTransition.mLeftoversHandler = (Transitions.TransitionHandler) dispatchRequest.first;
                        arrayList.add(mixedTransition);
                        return (WindowContainerTransaction) dispatchRequest.second;
                    }
                    if (!this.mSplitHandler.isSplitScreenVisible() || !TransitionUtil.isOpeningType(transitionRequestInfo.getType()) || transitionRequestInfo.getTriggerTask() == null || transitionRequestInfo.getTriggerTask().getWindowingMode() != 1 || (transitionRequestInfo.getTriggerTask().getActivityType() != 2 && transitionRequestInfo.getTriggerTask().getActivityType() != 3)) {
                        if (this.mUnfoldHandler != null) {
                            int i3 = UnfoldTransitionHandler.$r8$clinit;
                            if (transitionRequestInfo.getType() == 6 && transitionRequestInfo.getDisplayChange() != null && transitionRequestInfo.getDisplayChange().isPhysicalDisplayChanged()) {
                                WindowContainerTransaction handleRequest = this.mUnfoldHandler.handleRequest(iBinder, transitionRequestInfo);
                                if (handleRequest != null) {
                                    MixedTransition mixedTransition2 = new MixedTransition(6, iBinder);
                                    mixedTransition2.mLeftoversHandler = this.mUnfoldHandler;
                                    arrayList.add(mixedTransition2);
                                }
                                return handleRequest;
                            }
                        }
                        return null;
                    }
                    if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                        ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -2136320567, 0, " Got a going-home request while Split-Screen is foreground, so treat it as Mixed.", null);
                    }
                    Pair dispatchRequest2 = transitions.dispatchRequest(iBinder, transitionRequestInfo, this);
                    if (dispatchRequest2 == null) {
                        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1219407198, 0, " Lean on the remote transition handler to fetch a proper remote via TransitionFilter", null);
                        }
                        dispatchRequest2 = new Pair(transitions.mRemoteTransitionHandler, new WindowContainerTransaction());
                    }
                    MixedTransition mixedTransition3 = new MixedTransition(4, iBinder);
                    mixedTransition3.mLeftoversHandler = (Transitions.TransitionHandler) dispatchRequest2.first;
                    arrayList.add(mixedTransition3);
                    return (WindowContainerTransaction) dispatchRequest2.second;
                }
                if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -141231070, 0, " Got a PiP-enter request while Split-Screen is active, so treat it as Mixed.", null);
                }
                if (transitionRequestInfo.getRemoteTransition() != null) {
                    throw new IllegalStateException("Unexpected remote transition inpip-enter-from-split request");
                }
                arrayList.add(new MixedTransition(1, iBinder));
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                this.mPipHandler.augmentRequest(iBinder, transitionRequestInfo, windowContainerTransaction);
                StageCoordinator stageCoordinator2 = this.mSplitHandler;
                stageCoordinator2.getClass();
                ActivityManager.RunningTaskInfo triggerTask2 = transitionRequestInfo.getTriggerTask();
                int i4 = stageCoordinator2.mDisplayId;
                if (triggerTask2 == null || triggerTask2.displayId == i4) {
                    int type = transitionRequestInfo.getType();
                    int stageOfTask = triggerTask2 != null ? stageCoordinator2.getStageOfTask(triggerTask2.taskId) : -1;
                    CellStage cellStage2 = stageCoordinator2.mCellStage;
                    SideStage sideStage2 = stageCoordinator2.mSideStage;
                    MainStage mainStage2 = stageCoordinator2.mMainStage;
                    boolean z3 = type == 10 && stageOfTask != -1 && ((stageOfTask == 0 && mainStage2.getChildCount() == 1) || ((stageOfTask == 1 && sideStage2.getChildCount() == 1) || (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageOfTask == 2 && cellStage2.getChildCount() == 1)));
                    if (mainStage2.mIsActive && !TransitionUtil.isOpeningType(type) && (mainStage2.getChildCount() == 0 || sideStage2.getChildCount() == 0 || ((CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageCoordinator2.isMultiSplitActive() && cellStage2.getChildCount() == 0) || z3))) {
                        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -536447312, 5, "  One of the splits became empty during a mixed transition (one not handled by split), so make sure split-screen state is cleaned-up. mainStageCount=%d sideStageCount=%d", Long.valueOf(mainStage2.getChildCount()), Long.valueOf(sideStage2.getChildCount()));
                        }
                        if (triggerTask2 != null) {
                            stageCoordinator2.mRecentTasks.ifPresent(new StageCoordinator$$ExternalSyntheticLambda16(triggerTask2, 0));
                        }
                        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageCoordinator2.isMultiSplitScreenVisible()) {
                            if (stageOfTask == -1) {
                                if (mainStage2.getChildCount() == 0) {
                                    stageOfTask = 0;
                                } else if (sideStage2.getChildCount() == 0) {
                                    stageOfTask = 1;
                                } else if (cellStage2.getChildCount() == 0) {
                                    stageOfTask = 2;
                                }
                            }
                            StageTaskListener stageTaskListener = cellStage2.mHost;
                            if (stageTaskListener != mainStage2) {
                                sideStage2 = mainStage2;
                            }
                            if (stageOfTask == 2) {
                                stageCoordinator2.prepareExitMultiSplitScreen(windowContainerTransaction, false);
                            } else if (stageOfTask == stageCoordinator2.getCellHostStageType()) {
                                stageCoordinator2.reparentCellToMainOrSide(windowContainerTransaction, stageTaskListener, true);
                            } else {
                                int cellHostStageType = stageCoordinator2.getCellHostStageType();
                                if (cellHostStageType == 0) {
                                    i = 1;
                                } else if (cellHostStageType == 1) {
                                    i = 0;
                                }
                                if (stageOfTask == i) {
                                    stageCoordinator2.reparentCellToMainOrSide(windowContainerTransaction, sideStage2, true);
                                }
                            }
                            windowContainerTransaction.setDisplayIdForChangeTransition(i4, "enter_pip_with_multi_split");
                        } else {
                            if (stageCoordinator2.isSplitScreenVisible()) {
                                if (mainStage2.getChildCount() != 0 && sideStage2.getChildCount() == 0) {
                                    i = 0;
                                } else if (sideStage2.getChildCount() != 0 && mainStage2.getChildCount() == 0) {
                                    i = 1;
                                }
                            }
                            stageCoordinator2.prepareExitSplitScreen(i, windowContainerTransaction, true);
                        }
                    }
                }
                return windowContainerTransaction;
            }
        }
        z = false;
        ArrayList arrayList2 = this.mActiveTransitions;
        if (z) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:70:0x014d, code lost:
    
        throw new java.lang.IllegalStateException(android.support.v4.media.AbstractC0000x2c234b15.m0m("Playing a mixed transition with unknown type? ", r3));
     */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        int i = 0;
        while (true) {
            ArrayList arrayList = this.mActiveTransitions;
            if (i >= arrayList.size()) {
                return;
            }
            if (((MixedTransition) arrayList.get(i)).mTransition == iBinder2) {
                MixedTransition mixedTransition = (MixedTransition) arrayList.get(i);
                if (mixedTransition.mInFlightSubAnimations <= 0) {
                    return;
                }
                int i2 = mixedTransition.mType;
                if (i2 != 2) {
                    boolean z = true;
                    if (i2 == 1) {
                        if (mixedTransition.mAnimType == 1) {
                            SplitScreenTransitions splitScreenTransitions = this.mSplitHandler.mSplitTransitions;
                            if (splitScreenTransitions.mActiveRemoteHandler != null) {
                                z = false;
                            } else {
                                ArrayList arrayList2 = splitScreenTransitions.mAnimations;
                                for (int size = arrayList2.size() - 1; size >= 0; size--) {
                                    Animator animator = (Animator) arrayList2.get(size);
                                    ShellExecutor shellExecutor = splitScreenTransitions.mTransitions.mAnimExecutor;
                                    Objects.requireNonNull(animator);
                                    ((HandlerExecutor) shellExecutor).execute(new SplitScreenTransitions$$ExternalSyntheticLambda1(animator, 0));
                                }
                            }
                            if (!z) {
                                return;
                            }
                            this.mPipHandler.end();
                            Transitions.TransitionHandler transitionHandler = mixedTransition.mLeftoversHandler;
                            if (transitionHandler != null) {
                                transitionHandler.mergeAnimation(iBinder, transitionInfo, transaction, iBinder2, transitionFinishCallback);
                            }
                        } else {
                            this.mPipHandler.end();
                        }
                    } else if (i2 == 3) {
                        this.mPipHandler.end();
                        Transitions.TransitionHandler transitionHandler2 = mixedTransition.mLeftoversHandler;
                        if (transitionHandler2 != null) {
                            transitionHandler2.mergeAnimation(iBinder, transitionInfo, transaction, iBinder2, transitionFinishCallback);
                        }
                    } else {
                        if (i2 == 4) {
                            if (this.mSplitHandler.mSplitTransitions.isPendingEnter(iBinder)) {
                                mixedTransition.mAnimType = 2;
                            }
                            mixedTransition.mLeftoversHandler.mergeAnimation(iBinder, transitionInfo, transaction, iBinder2, transitionFinishCallback);
                        } else if (i2 == 5) {
                            this.mKeyguardHandler.mergeAnimation(iBinder, transitionInfo, transaction, iBinder2, transitionFinishCallback);
                        } else if (i2 == 6) {
                            this.mUnfoldHandler.mergeAnimation(iBinder, transitionInfo, transaction, iBinder2, transitionFinishCallback);
                        } else if (CoreRune.MW_PIP_SHELL_TRANSITION && i2 == 100) {
                            Log.d("DefaultMixedHandler", "mergeAnimation: mixed for pip is running, queueing info=" + transitionInfo + ", mergeTarget=" + iBinder2);
                        } else if (CoreRune.FW_CUSTOM_SHELL_RECENTS_TRANSITION_WITH_DISPLAY_CHANGE && i2 == 101) {
                            Log.d("DefaultMixedHandler", "mergeAnimation: mixed for recents transition with display change, queueing info=" + transitionInfo + ", mergeTarget=" + iBinder2);
                        } else {
                            if (!CoreRune.FW_CUSTOM_SHELL_KEYGUARD_TRANSITION_WITH_DISPLAY_CHANGE || i2 != 102) {
                                break;
                            }
                            Log.d("DefaultMixedHandler", "mergeAnimation: mixed for keyguard transition with display change, queueing info=" + transitionInfo + ", mergeTarget=" + iBinder2);
                        }
                        i++;
                    }
                }
            }
            i++;
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
        MixedTransition mixedTransition;
        ArrayList arrayList = this.mActiveTransitions;
        int size = arrayList.size() - 1;
        while (true) {
            if (size < 0) {
                mixedTransition = null;
                break;
            } else {
                if (((MixedTransition) arrayList.get(size)).mTransition == iBinder) {
                    mixedTransition = (MixedTransition) arrayList.remove(size);
                    break;
                }
                size--;
            }
        }
        if (mixedTransition == null) {
            return;
        }
        int i = mixedTransition.mType;
        if (i == 1) {
            this.mPipHandler.onTransitionConsumed(iBinder, z, transaction);
            return;
        }
        if (i == 4) {
            mixedTransition.mLeftoversHandler.onTransitionConsumed(iBinder, z, transaction);
            return;
        }
        if (i == 3) {
            mixedTransition.mLeftoversHandler.onTransitionConsumed(iBinder, z, transaction);
        } else if (i == 5) {
            this.mKeyguardHandler.onTransitionConsumed(iBinder, z, transaction);
        } else if (i == 6) {
            this.mUnfoldHandler.getClass();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:194:0x0376, code lost:
    
        if (r0.startAnimation(r14.mTransition, r20, r21, r22, r9) != false) goto L187;
     */
    /* JADX WARN: Removed duplicated region for block: B:134:0x025b  */
    /* JADX WARN: Removed duplicated region for block: B:164:? A[RETURN, SYNTHETIC] */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, final SurfaceControl.Transaction transaction2, final Transitions.TransitionFinishCallback transitionFinishCallback) {
        final MixedTransition mixedTransition;
        SplitLayout splitLayout;
        SurfaceControl surfaceControl;
        int i;
        Transitions.TransitionHandler transitionHandler;
        boolean hasDisplayRotationChange;
        TransitionInfo.Change change;
        int i2;
        ArrayList arrayList = this.mActiveTransitions;
        int size = arrayList.size();
        while (true) {
            size--;
            if (size < 0) {
                mixedTransition = null;
                break;
            }
            if (((MixedTransition) arrayList.get(size)).mTransition == iBinder) {
                mixedTransition = (MixedTransition) arrayList.get(size);
                break;
            }
        }
        int i3 = 5;
        int i4 = 6;
        if (KeyguardTransitionHandler.handles(transitionInfo)) {
            if (mixedTransition == null || (i2 = mixedTransition.mType) == 5) {
                PipTransitionController pipTransitionController = this.mPipHandler;
                if (pipTransitionController != null) {
                    pipTransitionController.syncPipSurfaceState(transitionInfo, transaction, transaction2);
                }
                if (CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION) {
                    excludeForceHidingChanges(transitionInfo);
                }
            } else {
                MixedTransition mixedTransition2 = new MixedTransition(5, iBinder);
                arrayList.add(mixedTransition2);
                if (animateKeyguard(mixedTransition2, transitionInfo, transaction, transaction2, transitionFinishCallback)) {
                    if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                        ShellProtoLogImpl.m233w(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 1796557761, 0, "Converting mixed transition into a keyguard transition", null);
                    }
                    onTransitionConsumed(iBinder, false, null);
                    if (!CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY || i2 != 6 || !this.mSplitHandler.isSplitScreenVisible()) {
                        return true;
                    }
                    StageCoordinator stageCoordinator = this.mSplitHandler;
                    stageCoordinator.updateSurfaceBounds(stageCoordinator.mSplitLayout, transaction2, false);
                    stageCoordinator.mSplitLayout.update(transaction2);
                    return true;
                }
                i4 = 6;
                arrayList.remove(mixedTransition2);
            }
            if (CoreRune.FW_CUSTOM_SHELL_KEYGUARD_TRANSITION_WITH_DISPLAY_CHANGE && mixedTransition == null && TransitionUtil.hasDisplayRotationChange(transitionInfo) && transitionInfo.isKeyguardGoingAway()) {
                TransitionInfo subCopy = subCopy(transitionInfo, transitionInfo.getType(), true);
                TransitionInfo subCopy2 = subCopy(transitionInfo, i4, false);
                for (int m136m = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1); m136m >= 0; m136m--) {
                    TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m);
                    if (change2.hasFlags(32)) {
                        subCopy2.addChange(change2);
                    }
                    subCopy.getChanges().remove(m136m);
                }
                if (subCopy2.getChanges().isEmpty()) {
                    return false;
                }
                MixedTransition mixedTransition3 = new MixedTransition(102, iBinder);
                arrayList.add(mixedTransition3);
                if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1849959725, 0, " Animation is a mix of keyguard and display change", null);
                }
                DefaultMixedHandler$$ExternalSyntheticLambda1 defaultMixedHandler$$ExternalSyntheticLambda1 = new DefaultMixedHandler$$ExternalSyntheticLambda1(this, mixedTransition3, transitionFinishCallback, 5);
                if (!animateKeyguard(mixedTransition3, subCopy, new SurfaceControl.Transaction(), transaction2, defaultMixedHandler$$ExternalSyntheticLambda1)) {
                    return false;
                }
                mixedTransition3.mInFlightSubAnimations++;
                mixedTransition3.mLeftoversHandler = this.mPlayer.dispatchTransition(mixedTransition3.mTransition, subCopy2, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda1, this, this.mKeyguardHandler);
                return true;
            }
            i3 = 5;
        }
        if (mixedTransition != null) {
            int i5 = mixedTransition.mType;
            if (i5 == 1) {
                return animateEnterPipFromSplit(mixedTransition, transitionInfo, transaction, transaction2, transitionFinishCallback);
            }
            if (i5 == 2) {
                return false;
            }
            if (i5 == 3) {
                TransitionInfo.Change change3 = null;
                for (int m136m2 = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1); m136m2 >= 0; m136m2--) {
                    TransitionInfo.Change change4 = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m2);
                    if (this.mPipHandler.isEnteringPip(transitionInfo.getType(), change4)) {
                        if (change3 != null) {
                            throw new IllegalStateException("More than 1 pip-entering changes in one transition? " + transitionInfo);
                        }
                        transitionInfo.getChanges().remove(m136m2);
                        change3 = change4;
                    }
                }
                DefaultMixedHandler$$ExternalSyntheticLambda1 defaultMixedHandler$$ExternalSyntheticLambda12 = new DefaultMixedHandler$$ExternalSyntheticLambda1(this, mixedTransition, transitionFinishCallback, 3);
                if (change3 == null) {
                    Transitions.TransitionHandler transitionHandler2 = mixedTransition.mLeftoversHandler;
                    if (transitionHandler2 != null) {
                        mixedTransition.mInFlightSubAnimations = 1;
                    }
                    arrayList.remove(mixedTransition);
                    return false;
                }
                if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1310405008, 0, " Splitting PIP into a separate animation because remote-animation likely doesn't support it", null);
                }
                mixedTransition.mInFlightSubAnimations = 2;
                SurfaceControl.Transaction transaction3 = new SurfaceControl.Transaction();
                if (CoreRune.MW_PIP_SHELL_TRANSITION) {
                    PipTransitionState pipTransitionState = this.mPipHandler.mPipOrganizer.mPipTransitionState;
                    if (pipTransitionState.mInSwipePipToHomeTransition) {
                        if (pipTransitionState.mState == 1) {
                            SurfaceControl.Transaction transaction4 = new SurfaceControl.Transaction();
                            transaction3.addDebugName("PipStartTransaction");
                            transaction4.addDebugName("PipFinishTransaction");
                            transaction3.setAlpha(change3.getLeash(), 0.0f);
                            transaction2.setAlpha(change3.getLeash(), 1.0f);
                            Log.w("DefaultMixedHandler", "animateOpenIntentWithRemoteAndPip: new finishT, pipChange=" + change3 + ", inSwipeHome=true");
                            this.mPipHandler.startEnterAnimation(change3, transaction3, transaction4, defaultMixedHandler$$ExternalSyntheticLambda12);
                            transitionHandler = mixedTransition.mLeftoversHandler;
                            if (transitionHandler != null || !transitionHandler.startAnimation(mixedTransition.mTransition, transitionInfo, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda12)) {
                                mixedTransition.mLeftoversHandler = this.mPlayer.dispatchTransition(mixedTransition.mTransition, transitionInfo, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda12, this, null);
                            }
                        }
                    }
                }
                this.mPipHandler.startEnterAnimation(change3, transaction3, transaction2, defaultMixedHandler$$ExternalSyntheticLambda12);
                transitionHandler = mixedTransition.mLeftoversHandler;
                if (transitionHandler != null) {
                }
                mixedTransition.mLeftoversHandler = this.mPlayer.dispatchTransition(mixedTransition.mTransition, transitionInfo, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda12, this, null);
                return true;
            }
            if (i5 == 4) {
                for (int m136m3 = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1); m136m3 >= 0; m136m3--) {
                    TransitionInfo.Change change5 = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m3);
                    if (this.mPipHandler.isEnteringPip(transitionInfo.getType(), change5) && this.mSplitHandler.getSplitItemPosition(change5.getLastParent()) != -1) {
                        return animateEnterPipFromSplit(mixedTransition, transitionInfo, transaction, transaction2, transitionFinishCallback);
                    }
                }
                Transitions.TransitionFinishCallback transitionFinishCallback2 = new Transitions.TransitionFinishCallback() { // from class: com.android.wm.shell.transition.DefaultMixedHandler$$ExternalSyntheticLambda2
                    @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
                    public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, WindowContainerTransactionCallback windowContainerTransactionCallback) {
                        ActivityManager.RunningTaskInfo runningTaskInfo;
                        DefaultMixedHandler defaultMixedHandler = DefaultMixedHandler.this;
                        defaultMixedHandler.getClass();
                        DefaultMixedHandler.MixedTransition mixedTransition4 = mixedTransition;
                        mixedTransition4.mInFlightSubAnimations = 0;
                        defaultMixedHandler.mActiveTransitions.remove(mixedTransition4);
                        if (windowContainerTransaction == null) {
                            windowContainerTransaction = new WindowContainerTransaction();
                        }
                        if (mixedTransition4.mAnimType != 2) {
                            defaultMixedHandler.mSplitHandler.onRecentsInSplitAnimationFinish(windowContainerTransaction, transaction2);
                        } else {
                            StageCoordinator stageCoordinator2 = defaultMixedHandler.mSplitHandler;
                            ArrayList arrayList2 = stageCoordinator2.mPausingTasks;
                            int size2 = arrayList2.size();
                            while (true) {
                                size2--;
                                if (size2 < 0) {
                                    break;
                                }
                                int intValue = ((Integer) arrayList2.get(size2)).intValue();
                                MainStage mainStage = stageCoordinator2.mMainStage;
                                if (mainStage.containsTask(intValue)) {
                                    ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) mainStage.mChildrenTaskInfo.get(intValue);
                                    if (runningTaskInfo2 != null) {
                                        windowContainerTransaction.reparent(runningTaskInfo2.token, (WindowContainerToken) null, false);
                                    }
                                } else {
                                    SideStage sideStage = stageCoordinator2.mSideStage;
                                    if (sideStage.containsTask(intValue) && (runningTaskInfo = (ActivityManager.RunningTaskInfo) sideStage.mChildrenTaskInfo.get(intValue)) != null) {
                                        windowContainerTransaction.reparent(runningTaskInfo.token, (WindowContainerToken) null, false);
                                    }
                                }
                            }
                            if (stageCoordinator2.mSplitTransitions.mPendingEnter == null) {
                                arrayList2.clear();
                                stageCoordinator2.updateRecentTasksSplitPair();
                            }
                        }
                        defaultMixedHandler.mSplitHandler.onTransitionAnimationComplete();
                        transitionFinishCallback.onTransitionFinished(windowContainerTransaction, windowContainerTransactionCallback);
                    }
                };
                mixedTransition.mInFlightSubAnimations = 1;
                StageCoordinator stageCoordinator2 = this.mSplitHandler;
                if (stageCoordinator2.isSplitScreenVisible()) {
                    for (int i6 = 0; i6 < transitionInfo.getChanges().size(); i6++) {
                        TransitionInfo.Change change6 = (TransitionInfo.Change) transitionInfo.getChanges().get(i6);
                        if (TransitionUtil.isClosingType(change6.getMode()) && change6.getTaskInfo() != null && (stageCoordinator2.mMainStage.getTopVisibleChildTaskId() == (i = change6.getTaskInfo().taskId) || stageCoordinator2.mSideStage.getTopVisibleChildTaskId() == i || (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageCoordinator2.isMultiSplitScreenVisible() && stageCoordinator2.mCellStage.getTopVisibleChildTaskId() == i))) {
                            stageCoordinator2.mPausingTasks.add(Integer.valueOf(i));
                        }
                    }
                }
                if (!CoreRune.MW_SPLIT_SHELL_TRANSITION || stageCoordinator2.isSplitScreenVisible()) {
                    stageCoordinator2.addDividerBarToTransition(transitionInfo, false);
                } else {
                    Log.d("StageCoordinator", "onRecentsInSplitAnimationStart: skip divider, reason=split_invisible");
                }
                if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && stageCoordinator2.isMultiSplitScreenVisible()) {
                    stageCoordinator2.addCellDividerBarToTransition(transitionInfo, false);
                }
                stageCoordinator2.mSplitBackgroundController.updateBackgroundVisibility(false, false);
                if (CoreRune.MW_SPLIT_SHELL_TRANSITION) {
                    stageCoordinator2.mIsRecentsInSplitAnimating = true;
                }
                boolean startAnimation = mixedTransition.mLeftoversHandler.startAnimation(mixedTransition.mTransition, transitionInfo, transaction, transaction2, transitionFinishCallback2);
                if (!startAnimation) {
                    this.mSplitHandler.onRecentsInSplitAnimationCanceled();
                    arrayList.remove(mixedTransition);
                }
                return startAnimation;
            }
            if (i5 == i3) {
                return animateKeyguard(mixedTransition, transitionInfo, transaction, transaction2, transitionFinishCallback);
            }
            if (i5 != 6) {
                arrayList.remove(mixedTransition);
                throw new IllegalStateException(AbstractC0000x2c234b15.m0m("Starting mixed animation without a known mixed type? ", i5));
            }
            DefaultMixedHandler$$ExternalSyntheticLambda1 defaultMixedHandler$$ExternalSyntheticLambda13 = new DefaultMixedHandler$$ExternalSyntheticLambda1(this, mixedTransition, transitionFinishCallback, 0);
            boolean z = true;
            mixedTransition.mInFlightSubAnimations = 1;
            PipTransitionController pipTransitionController2 = this.mPipHandler;
            if (pipTransitionController2 != null) {
                pipTransitionController2.syncPipSurfaceState(transitionInfo, transaction, transaction2);
            }
            TaskViewTransitions taskViewTransitions = this.mTaskViewTransitions;
            if (taskViewTransitions != null) {
                int i7 = 0;
                while (i7 < transitionInfo.getChanges().size()) {
                    TransitionInfo.Change change7 = (TransitionInfo.Change) transitionInfo.getChanges().get(i7);
                    if (change7.getTaskInfo() != null && (change7.getMode() == 6 || change7.getMode() == 3)) {
                        TaskViewTaskController findTaskView = taskViewTransitions.findTaskView(change7.getTaskInfo());
                        ArrayMap arrayMap = taskViewTransitions.mTaskViews;
                        if (findTaskView == null || (surfaceControl = findTaskView.mSurfaceControl) == null || !surfaceControl.isValid() || arrayMap.get(findTaskView) == null || !((TaskViewTransitions.TaskViewRequestedState) arrayMap.get(findTaskView)).mVisible) {
                            z = false;
                        }
                        if (z) {
                            Rect rect = new Rect(((TaskViewTransitions.TaskViewRequestedState) arrayMap.get(findTaskView)).mBounds);
                            rect.offsetTo(0, 0);
                            transaction.reparent(change7.getLeash(), findTaskView.mSurfaceControl).setPosition(change7.getLeash(), 0.0f, 0.0f).setCrop(change7.getLeash(), rect);
                            transaction2.reparent(change7.getLeash(), findTaskView.mSurfaceControl).setPosition(change7.getLeash(), 0.0f, 0.0f);
                            Slog.d("TaskViewTransitions", "syncTaskViewSurfaceState: " + change7);
                        }
                    }
                    i7++;
                    z = true;
                }
            }
            StageCoordinator stageCoordinator3 = this.mSplitHandler;
            if (stageCoordinator3 != null && stageCoordinator3.mMainStage.mIsActive && CoreRune.MW_MULTI_SPLIT_ADJUST_FOR_IME && (splitLayout = stageCoordinator3.mSplitLayout) != null) {
                if (splitLayout.mImePositionProcessor.mYOffsetForIme != 0) {
                    stageCoordinator3.updateSurfaceBounds(splitLayout, transaction2, false);
                }
            }
            return this.mUnfoldHandler.startAnimation(mixedTransition.mTransition, transitionInfo, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda13);
        }
        if (CoreRune.MW_PIP_SHELL_TRANSITION) {
            boolean z2 = false;
            boolean z3 = false;
            for (int m136m4 = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1); m136m4 >= 0; m136m4--) {
                TransitionInfo.Change change8 = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m4);
                if (change8.isEnteringPinnedMode() && change8.getTaskInfo() != null && change8.getTaskInfo().getWindowingMode() == 2) {
                    z3 = true;
                }
                if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION && change8.getMinimizeAnimState() != 0) {
                    z2 = true;
                }
            }
            if (z2 && z3) {
                int size2 = transitionInfo.getChanges().size();
                while (true) {
                    size2--;
                    if (size2 < 0) {
                        change = null;
                        break;
                    }
                    change = (TransitionInfo.Change) transitionInfo.getChanges().get(size2);
                    if (change.isEnteringPinnedMode() && change.getTaskInfo() != null && change.getTaskInfo().getWindowingMode() == 2) {
                        break;
                    }
                }
                if (change == null) {
                    Log.w("DefaultMixedHandler", "animateEnterPipWithDefaultTransition: failed, cannot find pipChange");
                    return false;
                }
                TransitionInfo subCopy3 = subCopy(transitionInfo, transitionInfo.getType(), true);
                TransitionInfo subCopy4 = subCopy(transitionInfo, transitionInfo.getType(), false);
                subCopy3.getChanges().remove(change);
                subCopy4.addChange(change);
                if (subCopy3.getChanges().isEmpty()) {
                    Log.w("DefaultMixedHandler", "animateEnterPipWithDefaultTransition: failed, default part is empty");
                    return false;
                }
                if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION && subCopy3.hasCustomDisplayChangeTransition()) {
                    subCopy3.setSeparatedFromCustomDisplayChange(true);
                }
                MixedTransition mixedTransition4 = new MixedTransition(100, iBinder);
                arrayList.add(mixedTransition4);
                mixedTransition4.mInFlightSubAnimations = 2;
                Log.d("DefaultMixedHandler", "animateEnterPipWithDefaultTransition: enterPipPart=" + subCopy4 + ", defaultPart=" + subCopy3);
                DefaultMixedHandler$$ExternalSyntheticLambda1 defaultMixedHandler$$ExternalSyntheticLambda14 = new DefaultMixedHandler$$ExternalSyntheticLambda1(this, mixedTransition4, transitionFinishCallback, 4);
                mixedTransition4.mLeftoversHandler = this.mPlayer.dispatchTransition(mixedTransition4.mTransition, subCopy3, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda14, this.mPipHandler, this);
                ((PipTransition) this.mPipHandler).startAnimation(iBinder, subCopy4, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda14);
                return true;
            }
        }
        if (!CoreRune.FW_CUSTOM_SHELL_RECENTS_TRANSITION_WITH_DISPLAY_CHANGE) {
            return false;
        }
        RecentsTransitionHandler recentsTransitionHandler = this.mRecentsHandler;
        if (iBinder == null) {
            recentsTransitionHandler.getClass();
        } else if (recentsTransitionHandler.findController(iBinder) < 0) {
            hasDisplayRotationChange = false;
            if (hasDisplayRotationChange) {
                return false;
            }
            TransitionInfo subCopy5 = subCopy(transitionInfo, transitionInfo.getType(), true);
            TransitionInfo subCopy6 = subCopy(transitionInfo, 6, false);
            for (int m136m5 = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1); m136m5 >= 0; m136m5--) {
                TransitionInfo.Change change9 = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m5);
                if (change9.getTaskInfo() == null && !TransitionUtil.isWallpaper(change9) && !TransitionUtil.isDividerBar(change9) && !TransitionUtil.isTransientLaunchOverlay(change9)) {
                    subCopy6.addChange(change9);
                    subCopy5.getChanges().remove(m136m5);
                }
            }
            if (subCopy6.getChanges().isEmpty()) {
                return false;
            }
            subCopy6.overrideDisplayChangeBackColor(0);
            MixedTransition mixedTransition5 = new MixedTransition(101, iBinder);
            arrayList.add(mixedTransition5);
            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 2130644591, 0, " Animation is a mix of display change and recents", null);
            }
            mixedTransition5.mInFlightSubAnimations = 2;
            DefaultMixedHandler$$ExternalSyntheticLambda1 defaultMixedHandler$$ExternalSyntheticLambda15 = new DefaultMixedHandler$$ExternalSyntheticLambda1(this, mixedTransition5, transitionFinishCallback, 2);
            SurfaceControl.Transaction transaction5 = new SurfaceControl.Transaction();
            if (this.mRecentsHandler.startAnimation(mixedTransition5.mTransition, subCopy5, transaction5, transaction2, defaultMixedHandler$$ExternalSyntheticLambda15)) {
                transaction.merge(transaction5);
            } else {
                mixedTransition5.mInFlightSubAnimations--;
            }
            mixedTransition5.mLeftoversHandler = this.mPlayer.dispatchTransition(mixedTransition5.mTransition, subCopy6, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda15, this, this.mRecentsHandler);
            return true;
        }
        hasDisplayRotationChange = TransitionUtil.hasDisplayRotationChange(transitionInfo);
        if (hasDisplayRotationChange) {
        }
    }
}
