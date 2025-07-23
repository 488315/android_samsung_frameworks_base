package com.android.wm.shell.transition;

import android.view.SurfaceControl;
import android.window.TransitionInfo;
import android.window.WindowContainerTransaction;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.pip.PipUtils;
import com.android.wm.shell.keyguard.KeyguardTransitionHandler;
import com.android.wm.shell.pip.PipTransitionController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.transition.DefaultMixedHandler;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.function.Predicate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class MixedTransitionHelper {
    public static boolean animateEnterPipFromSplit(final DefaultMixedHandler.MixedTransition mixedTransition, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, final Transitions.TransitionFinishCallback transitionFinishCallback, Transitions transitions, MixedTransitionHandler mixedTransitionHandler, PipTransitionController pipTransitionController, final StageCoordinator stageCoordinator, boolean z) {
        boolean z2;
        PipTransitionController pipTransitionController2;
        SurfaceControl.Transaction transaction3;
        int i;
        int splitItemStage;
        Transitions.TransitionFinishCallback transitionFinishCallback2;
        int i2;
        int splitItemStage2;
        boolean z3;
        boolean z4 = true;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 3121430122282955888L, 0, null);
        }
        TransitionInfo subCopy = DefaultMixedHandler.subCopy(transitionInfo, 4, true);
        int m = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1);
        TransitionInfo.Change change = null;
        TransitionInfo.Change change2 = null;
        TransitionInfo.Change change3 = null;
        final boolean z5 = false;
        while (m >= 0) {
            TransitionInfo.Change change4 = (TransitionInfo.Change) transitionInfo.getChanges().get(m);
            if (!pipTransitionController.isEnteringPip$1(change4, transitionInfo.getType())) {
                if (change4.getTaskInfo() != null || change4.getParent() == null || change2 == null) {
                    z3 = z4;
                } else {
                    z3 = z4;
                    if (change4.getParent().equals(change2.getContainer())) {
                        subCopy.getChanges().remove(m);
                        change3 = change4;
                    }
                }
                if ((change4.getTaskInfo() != null && change4.getTaskInfo().getActivityType() == 2) || (change4.getTaskInfo() != null && change4.getTaskInfo().getActivityType() == 3)) {
                    z5 = z3;
                } else if ((change4.getFlags() & 2) != 0) {
                    change = change4;
                }
            } else {
                if (change2 != null) {
                    throw new IllegalStateException("More than 1 pip-entering changes in one transition? " + transitionInfo);
                }
                subCopy.getChanges().remove(m);
                z3 = z4;
                change2 = change4;
            }
            m--;
            z4 = z3;
        }
        boolean z6 = z4;
        if (change2 == null) {
            return false;
        }
        Transitions.TransitionFinishCallback transitionFinishCallback3 = new Transitions.TransitionFinishCallback() { // from class: com.android.wm.shell.transition.MixedTransitionHelper$$ExternalSyntheticLambda0
            @Override // com.android.wm.shell.transition.Transitions.TransitionFinishCallback
            public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction) {
                DefaultMixedHandler.MixedTransition mixedTransition2 = DefaultMixedHandler.MixedTransition.this;
                mixedTransition2.mInFlightSubAnimations--;
                mixedTransition2.joinFinishArgs(windowContainerTransaction);
                if (mixedTransition2.mInFlightSubAnimations > 0) {
                    return;
                }
                if (z5) {
                    stageCoordinator.onTransitionAnimationComplete();
                }
                transitionFinishCallback.onTransitionFinished(mixedTransition2.mFinishWCT);
            }
        };
        if (!z5 && stageCoordinator.getSplitItemPosition(change2.getLastParent()) == -1) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[z6 ? 1 : 0]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 7735013801377545332L, 0, null);
            }
            if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && !stageCoordinator.isMultiSplitActive() && stageCoordinator.mCellStage.mVisible) {
                stageCoordinator.prepareDismissAnimation(-1, 9, subCopy, new SurfaceControl.Transaction(), transaction2, true);
            }
            mixedTransition.mInFlightSubAnimations = z6 ? 1 : 0;
            pipTransitionController.startAnimation(mixedTransition.mTransition, transitionInfo, transaction, transaction2, transitionFinishCallback3);
            return z6;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[z6 ? 1 : 0]) {
            z2 = z5;
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1810151520396169018L, 0, null);
        } else {
            z2 = z5;
        }
        mixedTransition.mInFlightSubAnimations = 2;
        if (change != null) {
            transaction.show(change.getLeash()).setAlpha(change.getLeash(), 1.0f);
        }
        SurfaceControl.Transaction transaction4 = new SurfaceControl.Transaction();
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER && stageCoordinator.isMultiSplitScreenVisible()) {
            int m2 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1);
            while (true) {
                if (m2 < 0) {
                    i2 = -1;
                    break;
                }
                TransitionInfo.Change change5 = (TransitionInfo.Change) transitionInfo.getChanges().get(m2);
                if (change5 != change2 && (splitItemStage2 = stageCoordinator.getSplitItemStage(change5.getLastParent())) != -1) {
                    i2 = splitItemStage2;
                    break;
                }
                m2--;
            }
            transaction3 = transaction2;
            pipTransitionController2 = pipTransitionController;
            stageCoordinator.prepareDismissAnimation(i2, 9, subCopy, transaction4, transaction3, !z2);
        } else {
            pipTransitionController2 = pipTransitionController;
            if ((!stageCoordinator.isSplitScreenVisible() || z) && !(CoreRune.MW_PIP_SHELL_TRANSITION && mixedTransition.mClosingSplitScreenWithEnterPip)) {
                transaction3 = transaction2;
            } else {
                int m3 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1);
                while (true) {
                    if (m3 < 0) {
                        i = -1;
                        break;
                    }
                    TransitionInfo.Change change6 = (TransitionInfo.Change) transitionInfo.getChanges().get(m3);
                    if (change6 != change2 && (splitItemStage = stageCoordinator.getSplitItemStage(change6.getLastParent())) != -1) {
                        i = splitItemStage;
                        break;
                    }
                    m3--;
                }
                transaction3 = transaction2;
                stageCoordinator.prepareDismissAnimation(i, 9, subCopy, transaction4, transaction3, false);
            }
        }
        int m4 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(subCopy, 1);
        while (true) {
            if (m4 < 0) {
                break;
            }
            if ((((TransitionInfo.Change) subCopy.getChanges().get(m4)).getFlags() & 16777216) != 0) {
                subCopy.getChanges().remove(m4);
                break;
            }
            m4--;
        }
        if (CoreRune.MW_PIP_SHELL_TRANSITION) {
            pipTransitionController2.onStartEnterPipFromSplit(change2, subCopy);
            final int endDisplayId = change2.getEndDisplayId();
            TransitionInfo.Change findChange = subCopy.findChange(new Predicate() { // from class: com.android.wm.shell.transition.MixedTransitionHelper$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    TransitionInfo.Change change7 = (TransitionInfo.Change) obj;
                    return change7.hasFlags(VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) && change7.getEndDisplayId() == endDisplayId && change7.getSnapshot() != null;
                }
            });
            if (findChange != null && subCopy.getRootCount() > 0) {
                int findRootIndex = transitionInfo.findRootIndex(findChange.getEndDisplayId());
                if (findRootIndex < 0) {
                    findRootIndex = transitionInfo.findRootIndex(findChange.getStartDisplayId());
                }
                if (findRootIndex < 0) {
                    findRootIndex = 0;
                }
                transaction.reparent(findChange.getSnapshot(), transitionInfo.getRoot(findRootIndex).getLeash());
                transaction3.reparent(findChange.getSnapshot(), null);
                StringBuilder sb = new StringBuilder("animateEnterPipFromSplit: reparent ");
                sb.append(findChange.getSnapshot());
                sb.append(", t=");
                ExifInterface$$ExternalSyntheticOutline0.m(sb, transaction.mDebugName, "PipTaskOrganizer");
            }
        }
        pipTransitionController2.setEnterAnimationType(1);
        if (PipUtils.isPip2ExperimentEnabled()) {
            TransitionInfo subCopy2 = DefaultMixedHandler.subCopy(transitionInfo, 10, false);
            subCopy2.getChanges().add(change2);
            if (change3 != null) {
                subCopy2.getChanges().add(change3);
            }
            SurfaceControl.Transaction transaction5 = transaction3;
            pipTransitionController2.startAnimation(mixedTransition.mTransition, subCopy2, transaction, transaction5, transitionFinishCallback3);
            transaction3 = transaction5;
            transitionFinishCallback2 = transitionFinishCallback3;
        } else {
            transitionFinishCallback2 = transitionFinishCallback3;
            pipTransitionController2.startEnterAnimation(change2, transaction, transaction3, transitionFinishCallback2);
        }
        mixedTransition.mLeftoversHandler = transitions.dispatchTransition(mixedTransition.mTransition, subCopy, transaction4, transaction3, transitionFinishCallback2, mixedTransitionHandler, null);
        return true;
    }

    public static boolean animateKeyguard(DefaultMixedHandler.MixedTransition mixedTransition, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback, KeyguardTransitionHandler keyguardTransitionHandler, PipTransitionController pipTransitionController) {
        if (mixedTransition.mFinishT == null) {
            mixedTransition.mFinishT = transaction2;
            mixedTransition.mFinishCB = transitionFinishCallback;
        }
        if (pipTransitionController != null) {
            pipTransitionController.syncPipSurfaceState(transitionInfo, transaction, transaction2);
        }
        return mixedTransition.startSubAnimation(keyguardTransitionHandler, transitionInfo, transaction, transaction2);
    }
}
