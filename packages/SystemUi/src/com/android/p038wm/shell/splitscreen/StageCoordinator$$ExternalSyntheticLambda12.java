package com.android.p038wm.shell.splitscreen;

import android.os.RemoteException;
import android.util.Slog;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.WindowContainerTransaction;
import com.android.internal.util.ArrayUtils;
import com.android.p038wm.shell.splitscreen.SplitScreenTransitions;
import com.android.p038wm.shell.transition.LegacyTransitions$ILegacyTransition;
import com.android.p038wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class StageCoordinator$$ExternalSyntheticLambda12 implements LegacyTransitions$ILegacyTransition, SplitScreenTransitions.TransitionFinishedCallback {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ StageCoordinator f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ StageCoordinator$$ExternalSyntheticLambda12(StageCoordinator stageCoordinator, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = stageCoordinator;
        this.f$1 = obj;
    }

    @Override // com.android.p038wm.shell.transition.LegacyTransitions$ILegacyTransition
    public final void onAnimationStart(int i, final RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback, SurfaceControl.Transaction transaction) {
        int i2 = this.$r8$classId;
        int i3 = 0;
        final StageCoordinator stageCoordinator = this.f$0;
        Object obj = this.f$1;
        switch (i2) {
            case 0:
                RemoteAnimationAdapter remoteAnimationAdapter = (RemoteAnimationAdapter) obj;
                if (remoteAnimationTargetArr != null) {
                    stageCoordinator.getClass();
                    if (remoteAnimationTargetArr.length != 0) {
                        RemoteAnimationTarget[] remoteAnimationTargetArr4 = (RemoteAnimationTarget[]) ArrayUtils.appendElement(RemoteAnimationTarget.class, remoteAnimationTargetArr3, stageCoordinator.getDividerBarLegacyTarget());
                        while (i3 < remoteAnimationTargetArr.length) {
                            RemoteAnimationTarget remoteAnimationTarget = remoteAnimationTargetArr[i3];
                            if (remoteAnimationTarget.mode == 0) {
                                transaction.show(remoteAnimationTarget.leash);
                                transaction.setPosition(remoteAnimationTargetArr[i3].leash, 0.0f, 0.0f);
                            }
                            i3++;
                        }
                        stageCoordinator.setDividerVisibility(true, transaction);
                        transaction.apply();
                        IRemoteAnimationFinishedCallback.Stub stub = new IRemoteAnimationFinishedCallback.Stub() { // from class: com.android.wm.shell.splitscreen.StageCoordinator.5
                            public final void onAnimationFinished() {
                                StageCoordinator.this.onRemoteAnimationFinished(remoteAnimationTargetArr);
                                iRemoteAnimationFinishedCallback.onAnimationFinished();
                            }
                        };
                        Transitions.setRunningRemoteTransitionDelegate(remoteAnimationAdapter.getCallingApplication());
                        try {
                            remoteAnimationAdapter.getRunner().onAnimationStart(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr4, stub);
                            break;
                        } catch (RemoteException e) {
                            Slog.e("StageCoordinator", "Error starting remote animation", e);
                            return;
                        }
                    }
                }
                stageCoordinator.onRemoteAnimationFinished(remoteAnimationTargetArr);
                transaction.apply();
                try {
                    remoteAnimationAdapter.getRunner().onAnimationCancelled();
                    break;
                } catch (RemoteException e2) {
                    Slog.e("StageCoordinator", "Error starting remote animation", e2);
                    return;
                }
            default:
                RemoteAnimationAdapter remoteAnimationAdapter2 = (RemoteAnimationAdapter) obj;
                if (remoteAnimationTargetArr != null) {
                    stageCoordinator.getClass();
                    if (remoteAnimationTargetArr.length != 0) {
                        while (i3 < remoteAnimationTargetArr.length) {
                            RemoteAnimationTarget remoteAnimationTarget2 = remoteAnimationTargetArr[i3];
                            if (remoteAnimationTarget2.mode == 0) {
                                transaction.show(remoteAnimationTarget2.leash);
                            }
                            i3++;
                        }
                        transaction.apply();
                        try {
                            remoteAnimationAdapter2.getRunner().onAnimationStart(i, remoteAnimationTargetArr, remoteAnimationTargetArr2, remoteAnimationTargetArr3, iRemoteAnimationFinishedCallback);
                            break;
                        } catch (RemoteException e3) {
                            Slog.e("StageCoordinator", "Error starting remote animation", e3);
                            return;
                        }
                    }
                }
                stageCoordinator.onRemoteAnimationFinished(remoteAnimationTargetArr);
                transaction.apply();
                try {
                    remoteAnimationAdapter2.getRunner().onAnimationCancelled();
                    break;
                } catch (RemoteException e4) {
                    Slog.e("StageCoordinator", "Error starting remote animation", e4);
                }
        }
    }

    @Override // com.android.wm.shell.splitscreen.SplitScreenTransitions.TransitionFinishedCallback
    public final void onFinished(WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) {
        SplitScreenTransitions.DismissSession dismissSession = (SplitScreenTransitions.DismissSession) this.f$1;
        StageCoordinator stageCoordinator = this.f$0;
        stageCoordinator.getClass();
        if (!CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER || !dismissSession.mIsMultiSplitDismissed) {
            stageCoordinator.mMainStage.mSplitDecorManager.release(transaction);
            stageCoordinator.mSideStage.mSplitDecorManager.release(transaction);
        }
        if (CoreRune.MW_MULTI_SPLIT_TASK_ORGANIZER) {
            stageCoordinator.mCellStage.mSplitDecorManager.release(transaction);
        }
        if (CoreRune.MW_MULTI_SPLIT_RECENT_TASKS && dismissSession.mIsMultiSplitDismissed) {
            stageCoordinator.updateRecentTasksSplitPair();
        }
        windowContainerTransaction.setReparentLeafTaskIfRelaunch(stageCoordinator.mRootTaskInfo.token, false);
    }
}
