package com.android.wm.shell.transition;

import android.app.PendingIntent;
import android.os.IBinder;
import android.util.Log;
import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0;
import com.android.wm.shell.activityembedding.ActivityEmbeddingController;
import com.android.wm.shell.common.ComponentUtils;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.keyguard.KeyguardTransitionHandler;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.recents.RecentsTransitionHandler;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.taskview.TaskViewTransitions;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.unfold.UnfoldTransitionHandler;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Optional;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DefaultMixedHandler implements MixedTransitionHandler, Transitions.TransitionHandler {
    public final ArrayList mActiveTransitions = new ArrayList();
    public ActivityEmbeddingController mActivityEmbeddingController;
    public DesktopTasksController mDesktopTasksController;
    public final KeyguardTransitionHandler mKeyguardHandler;
    public PipTransitionController mPipHandler;
    public final Transitions mPlayer;
    public RecentsTransitionHandler mRecentsHandler;
    public StageCoordinator mSplitHandler;
    public TaskViewTransitions mTaskViewTransitions;
    public UnfoldTransitionHandler mUnfoldHandler;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract class MixedTransition {
        public boolean mClosingSplitScreenWithEnterPip;
        public boolean mHasRequestToRemote;
        public final KeyguardTransitionHandler mKeyguardHandler;
        public final MixedTransitionHandler mMixedHandler;
        public final PipTransitionController mPipHandler;
        public final Transitions mPlayer;
        public final StageCoordinator mSplitHandler;
        public TaskViewTransitions mTaskViewTransitions;
        public final IBinder mTransition;
        public final int mType;
        public int mAnimType = 0;
        public Transitions.TransitionHandler mLeftoversHandler = null;
        public TransitionInfo mInfo = null;
        public WindowContainerTransaction mFinishWCT = null;
        public SurfaceControl.Transaction mFinishT = null;
        public Transitions.TransitionFinishCallback mFinishCB = null;
        public int mInFlightSubAnimations = 0;

        public MixedTransition(int i, IBinder iBinder, Transitions transitions, MixedTransitionHandler mixedTransitionHandler, PipTransitionController pipTransitionController, StageCoordinator stageCoordinator, KeyguardTransitionHandler keyguardTransitionHandler) {
            this.mType = i;
            this.mTransition = iBinder;
            this.mPlayer = transitions;
            this.mMixedHandler = mixedTransitionHandler;
            this.mPipHandler = pipTransitionController;
            this.mSplitHandler = stageCoordinator;
            this.mKeyguardHandler = keyguardTransitionHandler;
        }

        public final void joinFinishArgs(WindowContainerTransaction windowContainerTransaction) {
            if (windowContainerTransaction != null) {
                WindowContainerTransaction windowContainerTransaction2 = this.mFinishWCT;
                if (windowContainerTransaction2 == null) {
                    this.mFinishWCT = windowContainerTransaction;
                } else {
                    windowContainerTransaction2.merge(windowContainerTransaction, true);
                }
            }
        }

        public abstract void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback);

        public abstract void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction);

        public abstract boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback);

        public final boolean startSubAnimation(Transitions.TransitionHandler transitionHandler, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
            if (this.mInfo != null && ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 2342603963533375943L, 5, Long.valueOf(r0.getDebugId()), Long.valueOf(transitionInfo.getDebugId()));
            }
            this.mInFlightSubAnimations++;
            if (transitionHandler.startAnimation(this.mTransition, transitionInfo, transaction, transaction2, new DefaultMixedHandler$$ExternalSyntheticLambda5(2, this, transitionInfo))) {
                return true;
            }
            this.mInFlightSubAnimations--;
            return false;
        }
    }

    public DefaultMixedHandler(ShellInit shellInit, Transitions transitions, final Optional<SplitScreenController> optional, final PipTransitionController pipTransitionController, final Optional<RecentsTransitionHandler> optional2, KeyguardTransitionHandler keyguardTransitionHandler, final Optional<DesktopTasksController> optional3, final Optional<UnfoldTransitionHandler> optional4, final Optional<ActivityEmbeddingController> optional5, final TaskViewTransitions taskViewTransitions) {
        this.mPlayer = transitions;
        this.mKeyguardHandler = keyguardTransitionHandler;
        if (Transitions.ENABLE_SHELL_TRANSITIONS && pipTransitionController != null && optional.isPresent()) {
            shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.transition.DefaultMixedHandler$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    DefaultMixedHandler defaultMixedHandler = DefaultMixedHandler.this;
                    PipTransitionController pipTransitionController2 = pipTransitionController;
                    Optional optional6 = optional;
                    Optional optional7 = optional2;
                    Optional optional8 = optional3;
                    Optional optional9 = optional4;
                    Optional optional10 = optional5;
                    TaskViewTransitions taskViewTransitions2 = taskViewTransitions;
                    defaultMixedHandler.mPipHandler = pipTransitionController2;
                    pipTransitionController2.mMixedHandler = defaultMixedHandler;
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
                    defaultMixedHandler.mDesktopTasksController = (DesktopTasksController) optional8.orElse(null);
                    defaultMixedHandler.mUnfoldHandler = (UnfoldTransitionHandler) optional9.orElse(null);
                    defaultMixedHandler.mActivityEmbeddingController = (ActivityEmbeddingController) optional10.orElse(null);
                    defaultMixedHandler.mTaskViewTransitions = taskViewTransitions2;
                    if (taskViewTransitions2 != null) {
                        taskViewTransitions2.mMixedHandler = defaultMixedHandler;
                    }
                }
            }, this);
        }
    }

    public static void excludeForceHidingChanges(TransitionInfo transitionInfo) {
        for (int m = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); m >= 0; m--) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m);
            if (change.isForceHidingEnter()) {
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
        return transitionInfo2;
    }

    public final boolean animatePendingEnterPipFromSplit(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback, boolean z, boolean z2) {
        DefaultMixedTransition createDefaultMixedTransition = createDefaultMixedTransition(iBinder, z ? 10 : 1);
        this.mActiveTransitions.add(createDefaultMixedTransition);
        DefaultMixedHandler$$ExternalSyntheticLambda4 defaultMixedHandler$$ExternalSyntheticLambda4 = new DefaultMixedHandler$$ExternalSyntheticLambda4(this, createDefaultMixedTransition, transitionFinishCallback, 3);
        if (CoreRune.MW_PIP_SHELL_TRANSITION && z2 && !z) {
            createDefaultMixedTransition.mClosingSplitScreenWithEnterPip = true;
        }
        return createDefaultMixedTransition.startAnimation(iBinder, transitionInfo, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda4);
    }

    public final boolean animatePendingSplitWithDisplayChange(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        boolean z;
        TransitionInfo subCopy = subCopy(transitionInfo, transitionInfo.getType(), true);
        TransitionInfo subCopy2 = subCopy(transitionInfo, 6, false);
        for (int m = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); m >= 0; m--) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m);
            TransitionInfo.Change change2 = change;
            while (change2 != null) {
                if (change2.getTaskInfo() == null) {
                    if (change2.getParent() == null) {
                        break;
                    }
                    change2 = transitionInfo.getChange(change2.getParent());
                }
            }
            subCopy2.addChange(change);
            subCopy.getChanges().remove(m);
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
        if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION) {
            if (subCopy2.hasCustomDisplayChangeTransition()) {
                subCopy.setSeparatedFromCustomDisplayChange(true);
            }
            subCopy2.setAnimatePendingSplitWithDisplayChange(true);
        }
        DefaultMixedTransition createDefaultMixedTransition = createDefaultMixedTransition(iBinder, 2);
        this.mActiveTransitions.add(createDefaultMixedTransition);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -3728697233680287407L, 0, null);
        }
        createDefaultMixedTransition.mInFlightSubAnimations = 2;
        if (CoreRune.MW_PIP_SHELL_TRANSITION) {
            for (int m2 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); m2 >= 0; m2--) {
                if (this.mPipHandler.isEnteringPip$1((TransitionInfo.Change) transitionInfo.getChanges().get(m2), transitionInfo.getType())) {
                    createDefaultMixedTransition.mInFlightSubAnimations++;
                    z = true;
                    break;
                }
            }
        }
        z = false;
        if (this.mSplitHandler.mSplitTransitions.isPendingDismiss(iBinder)) {
            for (int m3 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(subCopy, 1); m3 >= 0; m3--) {
                TransitionInfo.Change change4 = (TransitionInfo.Change) transitionInfo.getChanges().get(m3);
                SurfaceControl leash = change4.getLeash();
                if (leash != null && change4.getMode() == 4) {
                    transaction.hide(leash);
                }
            }
        }
        DefaultMixedHandler$$ExternalSyntheticLambda4 defaultMixedHandler$$ExternalSyntheticLambda4 = new DefaultMixedHandler$$ExternalSyntheticLambda4(this, createDefaultMixedTransition, transitionFinishCallback, 1);
        createDefaultMixedTransition.mLeftoversHandler = this.mPlayer.dispatchTransition(createDefaultMixedTransition.mTransition, subCopy2, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda4, this.mSplitHandler, this.mPipHandler);
        if (!CoreRune.MW_SHELL_TRANSITION_BUG_FIX) {
            this.mSplitHandler.startPendingAnimation(iBinder, subCopy, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda4);
        } else if (!this.mSplitHandler.startPendingAnimation(iBinder, subCopy, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda4)) {
            defaultMixedHandler$$ExternalSyntheticLambda4.onTransitionFinished(null);
        }
        if (CoreRune.MW_PIP_SHELL_TRANSITION && z) {
            this.mPipHandler.startAnimation(iBinder, subCopy, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda4);
        }
        return true;
    }

    public final DefaultMixedTransition createDefaultMixedTransition(IBinder iBinder, int i) {
        return new DefaultMixedTransition(i, iBinder, this.mPlayer, this, this.mPipHandler, this.mSplitHandler, this.mKeyguardHandler, this.mUnfoldHandler, this.mActivityEmbeddingController, this.mDesktopTasksController);
    }

    public final RecentsMixedTransition createRecentsMixedTransition(IBinder iBinder, int i) {
        return new RecentsMixedTransition(i, iBinder, this.mPlayer, this, this.mPipHandler, this.mSplitHandler, this.mKeyguardHandler, this.mRecentsHandler, this.mDesktopTasksController);
    }

    /* JADX WARN: Code restructure failed: missing block: B:105:0x0069, code lost:
    
        if (r19.getTriggerTask().taskId == r12.getTopVisibleChildTaskId()) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x0086, code lost:
    
        if (r12.getChildCount() == 0) goto L34;
     */
    /* JADX WARN: Removed duplicated region for block: B:182:0x035b  */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.window.WindowContainerTransaction handleRequest(android.os.IBinder r18, android.window.TransitionRequestInfo r19) {
        /*
            Method dump skipped, instructions count: 996
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.transition.DefaultMixedHandler.handleRequest(android.os.IBinder, android.window.TransitionRequestInfo):android.window.WindowContainerTransaction");
    }

    public final boolean isIntentInPip(PendingIntent pendingIntent) {
        PipTransitionController pipTransitionController = this.mPipHandler;
        if (pipTransitionController != null) {
            return pipTransitionController.isPackageActiveInPip(ComponentUtils.getPackageName(pendingIntent.getIntent()));
        }
        return false;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        IBinder iBinder3;
        TransitionInfo transitionInfo2;
        SurfaceControl.Transaction transaction3;
        SurfaceControl.Transaction transaction4;
        IBinder iBinder4;
        Transitions.TransitionFinishCallback transitionFinishCallback2;
        int i = 0;
        while (i < this.mActiveTransitions.size()) {
            if (((MixedTransition) this.mActiveTransitions.get(i)).mTransition != iBinder2) {
                iBinder3 = iBinder;
                transitionInfo2 = transitionInfo;
                transaction3 = transaction;
                transaction4 = transaction2;
                iBinder4 = iBinder2;
                transitionFinishCallback2 = transitionFinishCallback;
            } else {
                MixedTransition mixedTransition = (MixedTransition) this.mActiveTransitions.get(i);
                if (mixedTransition.mInFlightSubAnimations <= 0) {
                    return;
                }
                iBinder3 = iBinder;
                transitionInfo2 = transitionInfo;
                transaction3 = transaction;
                transaction4 = transaction2;
                iBinder4 = iBinder2;
                transitionFinishCallback2 = transitionFinishCallback;
                mixedTransition.mergeAnimation(iBinder3, transitionInfo2, transaction3, transaction4, iBinder4, transitionFinishCallback2);
            }
            i++;
            iBinder = iBinder3;
            transitionInfo = transitionInfo2;
            transaction = transaction3;
            transaction2 = transaction4;
            iBinder2 = iBinder4;
            transitionFinishCallback = transitionFinishCallback2;
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
        MixedTransition mixedTransition;
        int size = this.mActiveTransitions.size() - 1;
        while (true) {
            if (size < 0) {
                mixedTransition = null;
                break;
            } else {
                if (((MixedTransition) this.mActiveTransitions.get(size)).mTransition == iBinder) {
                    mixedTransition = (MixedTransition) this.mActiveTransitions.remove(size);
                    break;
                }
                size--;
            }
        }
        if (mixedTransition != null) {
            mixedTransition.onTransitionConsumed(iBinder, z, transaction);
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        TransitionInfo.Change change;
        MixedTransition mixedTransition;
        int i;
        int size = this.mActiveTransitions.size() - 1;
        while (true) {
            change = null;
            if (size < 0) {
                mixedTransition = null;
                break;
            }
            if (((MixedTransition) this.mActiveTransitions.get(size)).mTransition == iBinder) {
                mixedTransition = (MixedTransition) this.mActiveTransitions.get(size);
                break;
            }
            size--;
        }
        if (KeyguardTransitionHandler.handles(transitionInfo)) {
            if (mixedTransition == null || (i = mixedTransition.mType) == 5) {
                PipTransitionController pipTransitionController = this.mPipHandler;
                if (pipTransitionController != null) {
                    pipTransitionController.syncPipSurfaceState(transitionInfo, transaction, transaction2);
                }
            } else {
                DefaultMixedTransition createDefaultMixedTransition = createDefaultMixedTransition(iBinder, 5);
                this.mActiveTransitions.add(createDefaultMixedTransition);
                DefaultMixedHandler$$ExternalSyntheticLambda4 defaultMixedHandler$$ExternalSyntheticLambda4 = new DefaultMixedHandler$$ExternalSyntheticLambda4(this, createDefaultMixedTransition, transitionFinishCallback, 6);
                if (CoreRune.MW_SPLIT_CONTINUITY_MODE && i == 102 && this.mSplitHandler.shouldkeyguardUnlockWithUpdateSplit(transitionInfo.getFlags())) {
                    Log.d("DefaultMixedHandler", "update split surface before going away");
                    StageCoordinator stageCoordinator = this.mSplitHandler;
                    stageCoordinator.updateSurfaceBounds(stageCoordinator.mSplitLayout, transaction, false);
                }
                PipTransitionController pipTransitionController2 = this.mPipHandler;
                if (createDefaultMixedTransition.mFinishT == null) {
                    createDefaultMixedTransition.mFinishT = transaction2;
                    createDefaultMixedTransition.mFinishCB = defaultMixedHandler$$ExternalSyntheticLambda4;
                }
                if (pipTransitionController2 != null) {
                    pipTransitionController2.syncPipSurfaceState(transitionInfo, transaction, transaction2);
                }
                if (CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION) {
                    excludeForceHidingChanges(transitionInfo);
                }
                if (createDefaultMixedTransition.startSubAnimation(this.mKeyguardHandler, transitionInfo, transaction, transaction2)) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[3]) {
                        ProtoLogImpl_1771455215.w(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 2769322071729320114L, 0, null);
                    }
                    this.mActiveTransitions.remove(mixedTransition);
                    mixedTransition.onTransitionConsumed(iBinder, false, null);
                    if (CoreRune.MW_MULTI_SPLIT_FOLDING_POLICY && i == 8 && this.mSplitHandler.isSplitScreenVisible()) {
                        StageCoordinator stageCoordinator2 = this.mSplitHandler;
                        stageCoordinator2.updateSurfaceBounds(stageCoordinator2.mSplitLayout, transaction2, false);
                        stageCoordinator2.mSplitLayout.update(transaction2, true);
                    }
                    return true;
                }
                this.mActiveTransitions.remove(createDefaultMixedTransition);
            }
            if (CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION) {
                excludeForceHidingChanges(transitionInfo);
            }
        }
        if (mixedTransition != null) {
            MixedTransition mixedTransition2 = mixedTransition;
            boolean startAnimation = mixedTransition2.startAnimation(iBinder, transitionInfo, transaction, transaction2, new DefaultMixedHandler$$ExternalSyntheticLambda4(this, mixedTransition, transitionFinishCallback, 7));
            if (!startAnimation) {
                this.mActiveTransitions.remove(mixedTransition2);
            }
            return startAnimation;
        }
        if (CoreRune.MW_PIP_SHELL_TRANSITION && CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
            boolean z = false;
            boolean z2 = false;
            for (int m = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); m >= 0; m--) {
                TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(m);
                if (change2.isEnteringPinnedMode() && change2.getTaskInfo() != null && change2.getTaskInfo().getWindowingMode() == 2) {
                    z2 = true;
                }
                if (change2.getMinimizeAnimState() != 0) {
                    z = true;
                }
            }
            if (z && z2) {
                int m2 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1);
                while (true) {
                    if (m2 < 0) {
                        break;
                    }
                    TransitionInfo.Change change3 = (TransitionInfo.Change) transitionInfo.getChanges().get(m2);
                    if (change3.isEnteringPinnedMode() && change3.getTaskInfo() != null && change3.getTaskInfo().getWindowingMode() == 2) {
                        change = change3;
                        break;
                    }
                    m2--;
                }
                if (change == null) {
                    Log.w("DefaultMixedHandler", "animateEnterPipWithDefaultTransition: failed, cannot find pipChange");
                    return false;
                }
                TransitionInfo subCopy = subCopy(transitionInfo, transitionInfo.getType(), true);
                TransitionInfo subCopy2 = subCopy(transitionInfo, transitionInfo.getType(), false);
                subCopy.getChanges().remove(change);
                subCopy2.addChange(change);
                if (subCopy.getChanges().isEmpty()) {
                    Log.w("DefaultMixedHandler", "animateEnterPipWithDefaultTransition: failed, default part is empty");
                    return false;
                }
                if (CoreRune.MW_SHELL_DISPLAY_CHANGE_TRANSITION && subCopy.hasCustomDisplayChangeTransition()) {
                    subCopy.setSeparatedFromCustomDisplayChange(true);
                }
                DefaultMixedTransition createDefaultMixedTransition2 = createDefaultMixedTransition(iBinder, 100);
                this.mActiveTransitions.add(createDefaultMixedTransition2);
                createDefaultMixedTransition2.mInFlightSubAnimations = 2;
                Log.d("DefaultMixedHandler", "animateEnterPipWithDefaultTransition: enterPipPart=" + subCopy2 + ", defaultPart=" + subCopy);
                DefaultMixedHandler$$ExternalSyntheticLambda4 defaultMixedHandler$$ExternalSyntheticLambda42 = new DefaultMixedHandler$$ExternalSyntheticLambda4(this, createDefaultMixedTransition2, transitionFinishCallback, 2);
                createDefaultMixedTransition2.mLeftoversHandler = this.mPlayer.dispatchTransition(createDefaultMixedTransition2.mTransition, subCopy, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda42, this.mPipHandler, this);
                this.mPipHandler.startAnimation(iBinder, subCopy2, transaction, transaction2, defaultMixedHandler$$ExternalSyntheticLambda42);
                return true;
            }
        }
        return false;
    }
}
