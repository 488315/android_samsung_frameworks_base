package com.android.wm.shell.recents;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.IApplicationThread;
import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.IntArray;
import android.util.Pair;
import android.util.Slog;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.DesktopExperienceFlags;
import android.window.DesktopModeFlags;
import android.window.PictureInPictureSurfaceTransaction;
import android.window.RemoteTransition;
import android.window.TaskSnapshot;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.os.IResultReceiver;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.R;
import com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.pip.PipUtils;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.DesktopTasksController$$ExternalSyntheticLambda3;
import com.android.wm.shell.desktopmode.multidesks.RootTaskDesksOrganizer;
import com.android.wm.shell.keyguard.KeyguardTransitionHandler;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.recents.IRecentsAnimationController;
import com.android.wm.shell.recents.RecentsTransitionHandler;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.splitscreen.SplitBackgroundController;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.DefaultMixedHandler;
import com.android.wm.shell.transition.HomeTransitionObserver;
import com.android.wm.shell.transition.MultiTaskingTransitionProvider;
import com.android.wm.shell.transition.RecentsMixedTransition;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

/* loaded from: classes3.dex */
public class RecentsTransitionHandler implements Transitions.TransitionHandler, Transitions.TransitionObserver {
    public static final IBinder SYNTHETIC_TRANSITION = new Binder();
    public Color mBackgroundColor;
    public DesktopTasksController mDesktopTasksController;
    public final ShellExecutor mExecutor;
    public final HomeTransitionObserver mHomeTransitionObserver;
    public MultiTaskingTransitionProvider mMultiTaskingTransitions;
    public final RecentTasksController mRecentTasksController;
    public final ShellTaskOrganizer mShellTaskOrganizer;
    public final Transitions mTransitions;
    public IApplicationThread mAnimApp = null;
    public final ArrayList mControllers = new ArrayList();
    public final ArrayList mStateListeners = new ArrayList();
    public final ArrayList mMixers = new ArrayList();

    class RecentsController extends IRecentsAnimationController.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public IRecentsAnimationRunner mListener;
        public TransitionInfo mMergedTransitionInfo;
        public Pair mPendingPauseSnapshotsForCancel;
        public IResultReceiver mPendingRunnerFinishCb;
        public Transitions.TransitionFinishCallback mFinishCB = null;
        public SurfaceControl.Transaction mFinishTransaction = null;
        public final ArrayList mFinishTransactions = new ArrayList();
        public ArrayList mPausingTasks = null;
        public ArrayList mClosingTasks = null;
        public ArrayList mOpeningTasks = null;
        public WindowContainerToken mPipTask = null;
        public int mPipTaskId = -1;
        public WindowContainerToken mRecentsTask = null;
        public int mRecentsTaskId = -1;
        public TransitionInfo mInfo = null;
        public boolean mOpeningSeparateHome = false;
        public boolean mPausingSeparateHome = false;
        public ArrayMap mLeashMap = null;
        public PictureInPictureSurfaceTransaction mPipTransaction = null;
        public IBinder mTransition = null;
        public boolean mKeyguardLocked = false;
        public boolean mWillFinishToHome = false;
        public Transitions.TransitionHandler mTakeoverHandler = null;
        public TaskState mRecentsTaskState = null;
        public boolean mWillForceFinishToHome = false;
        public int mState = 0;
        public boolean mForceEnterPip = false;
        public ArrayMap mTransferLeashMap = null;
        public final int mInstanceId = System.identityHashCode(this);
        public RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda5 mDeathHandler = new IBinder.DeathRecipient() { // from class: com.android.wm.shell.recents.RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda5
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                RecentsTransitionHandler.RecentsController recentsController = this.f$0;
                RecentsTransitionHandler.this.mExecutor.execute(new RecentsTransitionHandler$$ExternalSyntheticLambda0(recentsController, 1));
            }
        };

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.wm.shell.recents.RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda5] */
        public RecentsController(IRecentsAnimationRunner iRecentsAnimationRunner) throws RemoteException {
            this.mListener = iRecentsAnimationRunner;
            try {
                iRecentsAnimationRunner.asBinder().linkToDeath(this.mDeathHandler, 0);
            } catch (RemoteException e) {
                Slog.e("RecentsTransitionHandler", "RecentsController: failed to link to death", e);
                this.mListener = null;
            }
        }

        public static void setCornerRadiusForFreeformTasks(Context context, SurfaceControl.Transaction transaction, ArrayList arrayList) throws Resources.NotFoundException {
            if (DesktopModeFlags.ENABLE_DESKTOP_RECENTS_TRANSITIONS_CORNERS_BUGFIX.isTrue()) {
                int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.desktop_windowing_freeform_rounded_corner_radius);
                for (int i = 0; i < arrayList.size(); i++) {
                    TaskState taskState = (TaskState) arrayList.get(i);
                    ActivityManager.RunningTaskInfo runningTaskInfo = taskState.mTaskInfo;
                    if (runningTaskInfo != null && runningTaskInfo.isFreeform()) {
                        transaction.setCornerRadius(taskState.mTaskSurface, dimensionPixelSize);
                    }
                }
            }
        }

        public final void cancel(String str) throws Resources.NotFoundException {
            cancel(str, true, false);
        }

        public final void cleanUp() {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 3856935944170387493L, 1, Long.valueOf(this.mInstanceId));
            }
            IRecentsAnimationRunner iRecentsAnimationRunner = this.mListener;
            if (iRecentsAnimationRunner != null && this.mDeathHandler != null) {
                iRecentsAnimationRunner.asBinder().unlinkToDeath(this.mDeathHandler, 0);
                this.mDeathHandler = null;
            }
            this.mListener = null;
            this.mFinishCB = null;
            if (this.mLeashMap != null) {
                for (int i = 0; i < this.mLeashMap.size(); i++) {
                    ((SurfaceControl) this.mLeashMap.valueAt(i)).release();
                }
                this.mLeashMap = null;
            }
            if (CoreRune.FW_SHELL_TRANSITION_RECENTS_BUG_FIX) {
                this.mFinishTransactions.clear();
            }
            this.mFinishTransaction = null;
            this.mPausingTasks = null;
            this.mClosingTasks = null;
            this.mOpeningTasks = null;
            this.mInfo = null;
            this.mTransition = null;
            this.mPendingPauseSnapshotsForCancel = null;
            this.mPipTaskId = -1;
            this.mPipTask = null;
            this.mPipTransaction = null;
            this.mPendingRunnerFinishCb = null;
            RecentsTransitionHandler.this.mControllers.remove(this);
            for (int i2 = 0; i2 < RecentsTransitionHandler.this.mStateListeners.size(); i2++) {
                ((RecentsTransitionStateListener) RecentsTransitionHandler.this.mStateListeners.get(i2)).onTransitionStateChanged(1);
            }
            if (!CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER || this.mTransferLeashMap == null) {
                return;
            }
            for (int i3 = 0; i3 < this.mTransferLeashMap.size(); i3++) {
                ((SurfaceControl) this.mTransferLeashMap.valueAt(i3)).release();
            }
            this.mTransferLeashMap = null;
        }

        public final void cleanUpPausingOrClosingTask(TaskState taskState, WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction, boolean z) {
            ActivityManager.RunningTaskInfo runningTaskInfo;
            ActivityInfo activityInfo;
            if (!z && taskState.mLeash != null) {
                if (!this.mForceEnterPip || (runningTaskInfo = taskState.mTaskInfo) == null || (activityInfo = runningTaskInfo.topActivityInfo) == null || !activityInfo.supportsPictureInPicture()) {
                    windowContainerTransaction.setDoNotPip(taskState.mToken);
                } else {
                    Slog.d("PipTaskOrganizer", "recents transition is canceled but will go to pip");
                }
            }
            transaction.hide(taskState.mTaskSurface);
        }

        /* JADX WARN: Removed duplicated region for block: B:42:0x0076  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x0078  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void finishInner(boolean z, boolean z2, IResultReceiver iResultReceiver, String str) throws Resources.NotFoundException {
            ArrayList arrayList;
            TransitionInfo.Change change;
            SurfaceControl leash;
            boolean z3;
            WindowContainerToken windowContainerToken;
            TaskState taskState;
            Integer activeDeskId;
            DesktopTasksController$$ExternalSyntheticLambda3 desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp;
            WindowContainerToken windowContainerToken2;
            TaskState taskState2;
            WindowContainerToken windowContainerToken3;
            StageCoordinator.RecentsTransitionCallback recentsTransitionCallback;
            TransitionInfo transitionInfo;
            ArrayList arrayList2;
            if (this.mTransition == RecentsTransitionHandler.SYNTHETIC_TRANSITION) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -1388540859255603550L, 1, Long.valueOf(this.mInstanceId), str);
                }
                if (iResultReceiver != null) {
                    try {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 4783697728749450411L, 1, Long.valueOf(this.mInstanceId));
                        }
                        iResultReceiver.send(0, (Bundle) null);
                    } catch (RemoteException e) {
                        Slog.e("RecentsTransitionHandler", "Failed to report transition finished", e);
                    }
                }
                cleanUp();
                return;
            }
            if (this.mFinishCB == null) {
                Slog.e("RecentsTransitionHandler", "Duplicate call to finish");
                if (iResultReceiver != null) {
                    try {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 4783697728749450411L, 1, Long.valueOf(this.mInstanceId));
                        }
                        iResultReceiver.send(0, (Bundle) null);
                        return;
                    } catch (RemoteException e2) {
                        Slog.e("RecentsTransitionHandler", "Failed to report transition finished", e2);
                        return;
                    }
                }
                return;
            }
            boolean z4 = (z || this.mWillFinishToHome || this.mPausingTasks == null || this.mState != 0) ? false : true;
            if (z4 && (arrayList2 = this.mPausingTasks) != null) {
                for (int size = arrayList2.size() - 1; size >= 0; size--) {
                    if (!((TaskState) arrayList2.get(size)).mIsTranslucent) {
                        if (z) {
                            if (!z) {
                            }
                        }
                    }
                }
                RecentsTransitionHandler.this.mHomeTransitionObserver.notifyHomeVisibilityChanged(true);
            } else if (z && this.mState == 1 && (arrayList = this.mOpeningTasks) != null) {
                for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                    if (!((TaskState) arrayList.get(size2)).mIsTranslucent) {
                        if (!z) {
                        }
                    }
                }
            } else if (!z) {
                RecentsTransitionHandler.this.mHomeTransitionObserver.notifyHomeVisibilityChanged(false);
            }
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 8378224957991956577L, 3581, Long.valueOf(this.mInstanceId), Boolean.valueOf(z), Boolean.valueOf(z2), Boolean.valueOf(this.mWillFinishToHome), Long.valueOf(this.mState), Boolean.valueOf(this.mPausingTasks != null), str);
            }
            SurfaceControl.Transaction transaction = (!CoreRune.FW_SHELL_TRANSITION_RECENTS_BUG_FIX || !this.mWillForceFinishToHome || this.mFinishTransactions.isEmpty() || this.mFinishTransactions.size() == 1) ? this.mFinishTransaction : (SurfaceControl.Transaction) this.mFinishTransactions.getLast();
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            if (CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION) {
                MultiTaskingTransitionProvider.cancelForceHideAnimationsIfNeeded("RecentsTransitionHandler", RecentsTransitionHandler.this.mTransitions.mAnimExecutor);
            }
            if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION && (transitionInfo = this.mMergedTransitionInfo) != null) {
                for (TransitionInfo.Change change2 : transitionInfo.getChanges()) {
                    if (change2.getTaskInfo() != null && change2.getMinimizeAnimState() == 1) {
                        Slog.d("RecentsTransitionHandler", "notifyFreeformMinimizeFinished: #" + change2.getTaskInfo().taskId);
                        MultiWindowManager.getInstance().notifyFreeformMinimizeAnimationEnd(change2.getTaskInfo().taskId, change2.getMinimizePoint());
                    }
                }
                this.mMergedTransitionInfo = null;
            }
            if (CoreRune.MW_MULTI_SPLIT_BACKGROUND && (recentsTransitionCallback = RecentsTransitionHandler.this.mTransitions.mRecentTransitionCallback) != null) {
                StageCoordinator stageCoordinator = StageCoordinator.this;
                SplitBackgroundController splitBackgroundController = stageCoordinator.mSplitBackgroundController;
                if (splitBackgroundController.mReparentedToTransitionRoot) {
                    splitBackgroundController.reparentToLeash(transaction, stageCoordinator.mRootTaskLeash, false);
                }
            }
            if (this.mKeyguardLocked && (windowContainerToken3 = this.mRecentsTask) != null) {
                if (z) {
                    windowContainerTransaction.reorder(windowContainerToken3, true);
                } else {
                    windowContainerTransaction.restoreTransientOrder(windowContainerToken3);
                }
            }
            if (z4) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -2795398877882993822L, 0, null);
                }
                for (int size3 = this.mPausingTasks.size() - 1; size3 >= 0; size3--) {
                    windowContainerTransaction.reorder(((TaskState) this.mPausingTasks.get(size3)).mToken, true);
                    transaction.show(((TaskState) this.mPausingTasks.get(size3)).mTaskSurface);
                }
                setCornerRadiusForFreeformTasks(RecentsTransitionHandler.this.mRecentTasksController.mContext, transaction, this.mPausingTasks);
                if (!this.mKeyguardLocked && (windowContainerToken2 = this.mRecentsTask) != null) {
                    windowContainerTransaction.restoreTransientOrder(windowContainerToken2);
                    if (CoreRune.FW_SHELL_TRANSITION_RECENTS_BUG_FIX && this.mPausingSeparateHome && (taskState2 = this.mRecentsTaskState) != null) {
                        transaction.hide(taskState2.mTaskSurface);
                    }
                }
            } else if (z && this.mOpeningSeparateHome && this.mPausingTasks != null) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 7705607535415475525L, 0, null);
                }
                for (int i = 0; i < this.mOpeningTasks.size(); i++) {
                    TaskState taskState3 = (TaskState) this.mOpeningTasks.get(i);
                    if (taskState3.mTaskInfo.topActivityType == 2) {
                        windowContainerTransaction.reorder(taskState3.mToken, true);
                    }
                    transaction.show(taskState3.mTaskSurface);
                }
                for (int size4 = this.mPausingTasks.size() - 1; size4 >= 0; size4--) {
                    transaction.hide(((TaskState) this.mPausingTasks.get(size4)).mTaskSurface);
                }
                if (!this.mKeyguardLocked && (windowContainerToken = this.mRecentsTask) != null) {
                    windowContainerTransaction.restoreTransientOrder(windowContainerToken);
                    if (CoreRune.FW_SHELL_TRANSITION_RECENTS_BUG_FIX && (taskState = this.mRecentsTaskState) != null) {
                        transaction.hide(taskState.mTaskSurface);
                    }
                }
            } else {
                if (this.mPausingSeparateHome) {
                    if (this.mOpeningTasks.isEmpty()) {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -7724966684610905687L, 0, null);
                        }
                    } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 5024705213560797545L, 0, null);
                    }
                }
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 7501267451241772263L, 0, null);
                }
                if (CoreRune.FW_SHELL_TRANSITION_RECENTS_BUG_FIX && this.mWillForceFinishToHome && this.mRecentsTask != null && !this.mOpeningTasks.isEmpty()) {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 2238673465288372355L, 0, null);
                    }
                    windowContainerTransaction.reorder(this.mRecentsTask, true);
                    TaskState taskState4 = this.mRecentsTaskState;
                    if (taskState4 != null) {
                        transaction.show(taskState4.mTaskSurface);
                    }
                    this.mClosingTasks.addAll(this.mOpeningTasks);
                    this.mOpeningTasks.clear();
                }
                if (RecentsTransitionHandler.this.mDesktopTasksController != null) {
                    int i2 = 0;
                    while (true) {
                        if (i2 >= this.mPausingTasks.size()) {
                            i2 = -1;
                            break;
                        }
                        ActivityManager.RunningTaskInfo runningTaskInfo = ((TaskState) this.mPausingTasks.get(i2)).mTaskInfo;
                        if (runningTaskInfo != null && runningTaskInfo.topActivityType == 2) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                    int i3 = 0;
                    while (true) {
                        if (i3 >= this.mOpeningTasks.size()) {
                            z3 = false;
                            break;
                        } else {
                            if (RecentsTransitionHandler.m3276$$Nest$misDeskRootTask(RecentsTransitionHandler.this, ((TaskState) this.mOpeningTasks.get(i3)).mTaskInfo)) {
                                z3 = true;
                                break;
                            }
                            i3++;
                        }
                    }
                    if (i2 != -1 && z3) {
                        transaction.show(((TaskState) this.mPausingTasks.remove(i2)).mTaskSurface);
                    }
                }
                for (int i4 = 0; i4 < this.mOpeningTasks.size(); i4++) {
                    transaction.show(((TaskState) this.mOpeningTasks.get(i4)).mTaskSurface);
                }
                setCornerRadiusForFreeformTasks(RecentsTransitionHandler.this.mRecentTasksController.mContext, transaction, this.mOpeningTasks);
                for (int i5 = 0; i5 < this.mPausingTasks.size(); i5++) {
                    cleanUpPausingOrClosingTask((TaskState) this.mPausingTasks.get(i5), windowContainerTransaction, transaction, z2);
                }
                for (int i6 = 0; i6 < this.mClosingTasks.size(); i6++) {
                    cleanUpPausingOrClosingTask((TaskState) this.mClosingTasks.get(i6), windowContainerTransaction, transaction, z2);
                }
                if (this.mPipTransaction != null && z2) {
                    WindowContainerToken windowContainerToken4 = this.mPipTask;
                    if (windowContainerToken4 != null) {
                        change = this.mInfo.getChange(windowContainerToken4);
                        leash = change.getLeash();
                    } else if (this.mPipTaskId != -1) {
                        TransitionInfo.Change change3 = null;
                        SurfaceControl leash2 = null;
                        for (TransitionInfo.Change change4 : this.mInfo.getChanges()) {
                            if (change4.getTaskInfo() != null && change4.getTaskInfo().taskId == this.mPipTaskId) {
                                leash2 = change4.getLeash();
                                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -4811508400070969094L, 1, Long.valueOf(this.mPipTaskId));
                                }
                                change3 = change4;
                            }
                        }
                        change = change3;
                        leash = leash2;
                    } else {
                        change = null;
                        leash = null;
                    }
                    if (leash != null) {
                        transaction.show(leash);
                        PictureInPictureSurfaceTransaction.apply(this.mPipTransaction, leash, transaction);
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 1735868815195455144L, 0, String.valueOf(this.mPipTransaction));
                        }
                        if (PipUtils.isPip2ExperimentEnabled()) {
                            windowContainerTransaction.merge((WindowContainerTransaction) RecentsTransitionHandler.this.mTransitions.dispatchRequest(this.mTransition, new TransitionRequestInfo(10, (ActivityManager.RunningTaskInfo) null, change.getTaskInfo(), (RemoteTransition) null, (TransitionRequestInfo.DisplayChange) null, 0), null).second, true);
                            RecentsTransitionHandler.this.mTransitions.startTransition(10, windowContainerTransaction, null);
                            windowContainerTransaction.clear();
                        }
                    } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 9022094119540111634L, 16, String.valueOf(this.mPipTransaction), String.valueOf(this.mPipTask), Long.valueOf(this.mPipTaskId));
                    }
                }
            }
            for (int i7 = 0; i7 < RecentsTransitionHandler.this.mMixers.size(); i7++) {
                DefaultMixedHandler defaultMixedHandler = (DefaultMixedHandler) RecentsTransitionHandler.this.mMixers.get(i7);
                if (defaultMixedHandler.mRecentsHandler != null) {
                    for (int size5 = defaultMixedHandler.mActiveTransitions.size() - 1; size5 >= 0; size5--) {
                        DefaultMixedHandler.MixedTransition mixedTransition = (DefaultMixedHandler.MixedTransition) defaultMixedHandler.mActiveTransitions.get(size5);
                        int i8 = mixedTransition.mType;
                        if (i8 == 4) {
                            RecentsMixedTransition recentsMixedTransition = (RecentsMixedTransition) mixedTransition;
                            if (recentsMixedTransition.mAnimType != 1) {
                                recentsMixedTransition.mSplitHandler.onRecentsInSplitAnimationFinishing(z4, windowContainerTransaction, transaction);
                            }
                        } else if (i8 == 7) {
                            RecentsMixedTransition recentsMixedTransition2 = (RecentsMixedTransition) mixedTransition;
                            IBinder iBinder = recentsMixedTransition2.mTransition;
                            DesktopTasksController desktopTasksController = recentsMixedTransition2.mDesktopTasksController;
                            desktopTasksController.getClass();
                            if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                                DesktopTasksController.logV$1("onRecentsInDesktopAnimationFinishing returnToApp=%b", Boolean.valueOf(z4));
                                if (!z4 && !CoreRune.DW_MULTIPLE_DESKS && (activeDeskId = desktopTasksController.taskRepository.getActiveDeskId(0)) != null && (desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp = desktopTasksController.performDesktopExitCleanUp(windowContainerTransaction, activeDeskId, 0, true, true, true)) != null) {
                                    desktopTasksController$$ExternalSyntheticLambda3PerformDesktopExitCleanUp.mo781invoke(iBinder);
                                }
                            }
                        }
                    }
                }
            }
            this.mPendingRunnerFinishCb = iResultReceiver;
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 8994155724944389193L, 1, Long.valueOf(this.mInstanceId));
            }
            Transitions.TransitionFinishCallback transitionFinishCallback = this.mFinishCB;
            IResultReceiver iResultReceiver2 = this.mPendingRunnerFinishCb;
            cleanUp();
            transitionFinishCallback.onTransitionFinished(windowContainerTransaction);
            if (iResultReceiver2 != null) {
                try {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 4783697728749450411L, 1, Long.valueOf(this.mInstanceId));
                    }
                    iResultReceiver2.send(0, (Bundle) null);
                } catch (RemoteException e3) {
                    Slog.e("RecentsTransitionHandler", "Failed to report transition finished", e3);
                }
            }
        }

        public final Pair getSnapshotsForPausingTasks() {
            TaskSnapshot[] taskSnapshotArr;
            ArrayList arrayList = this.mPausingTasks;
            int[] iArr = null;
            if (arrayList == null || arrayList.size() <= 0) {
                taskSnapshotArr = null;
            } else {
                int[] iArr2 = new int[this.mPausingTasks.size()];
                taskSnapshotArr = new TaskSnapshot[this.mPausingTasks.size()];
                for (int i = 0; i < this.mPausingTasks.size(); i++) {
                    try {
                        TaskState taskState = (TaskState) this.mPausingTasks.get(0);
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -564434088454636251L, 5, Long.valueOf(this.mInstanceId), Long.valueOf(taskState.mTaskInfo.taskId));
                        }
                        taskSnapshotArr[i] = ActivityTaskManager.getService().takeTaskSnapshot(taskState.mTaskInfo.taskId, true);
                    } catch (RemoteException unused) {
                    }
                }
                iArr = iArr2;
            }
            return new Pair(iArr, taskSnapshotArr);
        }

        /* JADX WARN: Code restructure failed: missing block: B:61:0x0130, code lost:
        
            cancel(defpackage.ReorderTile$$ExternalSyntheticOutline0.m(r10.taskId, " is always_on_top", new java.lang.StringBuilder("task #")), false, false);
         */
        /* JADX WARN: Code restructure failed: missing block: B:62:0x0141, code lost:
        
            return;
         */
        /* JADX WARN: Removed duplicated region for block: B:399:0x0821 A[EDGE_INSN: B:455:0x0821->B:399:0x0821 BREAK  A[LOOP:6: B:387:0x07f2->B:398:0x081d]] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void merge(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) throws Resources.NotFoundException {
            boolean z;
            String str;
            int i;
            boolean z2;
            RemoteAnimationTarget[] remoteAnimationTargetArr;
            ActivityManager.RunningTaskInfo taskInfo;
            TransitionInfo transitionInfo2;
            StageCoordinator.RecentsTransitionCallback recentsTransitionCallback;
            RemoteAnimationTarget[] remoteAnimationTargetArr2;
            int i2;
            String str2;
            ArrayList arrayList;
            IntArray intArray;
            float f;
            int i3;
            String str3;
            int i4;
            int i5;
            ArrayList arrayList2;
            char c;
            ArrayList arrayList3;
            TransitionInfo.Change change;
            ActivityManager.RunningTaskInfo taskInfo2;
            ActivityManager.RunningTaskInfo runningTaskInfo;
            TransitionInfo transitionInfo3 = transitionInfo;
            if (this.mFinishCB == null) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -7300394669171000003L, 1, Long.valueOf(this.mInstanceId));
                    return;
                }
                return;
            }
            if (transitionInfo3.getType() == 12) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -4265385298039391224L, 1, Long.valueOf(this.mInstanceId));
                }
                cancel("transit_sleep");
                return;
            }
            if (this.mKeyguardLocked || (transitionInfo3.getFlags() & 47360) != 0) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 1951248095232933372L, 1, Long.valueOf(this.mInstanceId));
                }
                cancel("keyguard_locked", true, false);
                return;
            }
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 1317693307849553962L, 1, Long.valueOf(this.mInstanceId));
            }
            this.mOpeningSeparateHome = false;
            TransitionUtil.LeafTaskFilter leafTaskFilter = new TransitionUtil.LeafTaskFilter();
            int iM = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo3, 1);
            while (true) {
                if (iM < 0) {
                    z = false;
                    break;
                }
                TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo3.getChanges().get(iM);
                if (change2.hasFlags(32) && change2.getMode() == 6) {
                    z = true;
                    break;
                }
                iM--;
            }
            int i6 = 0;
            boolean z3 = false;
            boolean z4 = false;
            boolean z5 = false;
            ArrayList arrayList4 = null;
            TransitionInfo.Change change3 = null;
            ArrayList arrayList5 = null;
            IntArray intArray2 = null;
            while (true) {
                String str4 = "RecentsTransitionHandler";
                if (i6 >= transitionInfo3.getChanges().size()) {
                    ArrayList arrayList6 = arrayList5;
                    if (z3 && z4) {
                        Pair snapshotsForPausingTasks = this.mPendingPauseSnapshotsForCancel;
                        if (snapshotsForPausingTasks == null) {
                            snapshotsForPausingTasks = getSnapshotsForPausingTasks();
                        }
                        sendCancel((int[]) snapshotsForPausingTasks.first, (TaskSnapshot[]) snapshotsForPausingTasks.second);
                        ((HandlerExecutor) RecentsTransitionHandler.this.mExecutor).executeDelayed(new RecentsTransitionHandler$$ExternalSyntheticLambda0(this, 2), 0L);
                        return;
                    }
                    if (change3 != null) {
                        if (this.mState == 0) {
                            Slog.e("RecentsTransitionHandler", "Returning to recents while recents is already idle.");
                        }
                        if (arrayList4 == null || arrayList4.size() == 0) {
                            Slog.e("RecentsTransitionHandler", "Returning to recents without closing any opening tasks.");
                        }
                        transaction.show(change3.getLeash());
                        transaction.setAlpha(change3.getLeash(), 1.0f);
                        this.mState = 0;
                    }
                    String str5 = "";
                    boolean z6 = false;
                    if (arrayList4 != null) {
                        int i7 = 0;
                        while (i7 < arrayList4.size()) {
                            TransitionInfo.Change change4 = (TransitionInfo.Change) arrayList4.get(i7);
                            int iIndexOf = TaskState.indexOf(this.mPausingTasks, change4);
                            if (iIndexOf >= 0) {
                                this.mClosingTasks.add((TaskState) this.mPausingTasks.remove(iIndexOf));
                                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                    str3 = str5;
                                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -8004858446668286751L, 1, Long.valueOf(change4.getTaskInfo().taskId));
                                } else {
                                    str3 = str5;
                                }
                                i4 = i7;
                            } else {
                                str3 = str5;
                                int iIndexOf2 = TaskState.indexOf(this.mOpeningTasks, change4);
                                if (iIndexOf2 < 0) {
                                    Slog.w("RecentsTransitionHandler", "Closing a task that wasn't opening, this may be split or something unexpected: " + change4.getTaskInfo().taskId);
                                    i4 = i7;
                                    i7 = i4 + 1;
                                    str5 = str3;
                                } else {
                                    TaskState taskState = (TaskState) this.mOpeningTasks.remove(iIndexOf2);
                                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                        i4 = i7;
                                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 1357141655006297500L, 4, taskState.mLeash != null ? "leaf " : str3, Long.valueOf(taskState.mTaskInfo.taskId));
                                    } else {
                                        i4 = i7;
                                    }
                                    this.mPausingTasks.add(taskState);
                                }
                            }
                            z6 = true;
                            i7 = i4 + 1;
                            str5 = str3;
                        }
                    }
                    String str6 = str5;
                    if (arrayList6 == null || arrayList6.size() <= 0) {
                        str = "RecentsTransitionHandler";
                        i = 1;
                        z2 = z6;
                        remoteAnimationTargetArr = null;
                    } else {
                        int size = this.mInfo.getChanges().size() * 3;
                        int i8 = 0;
                        for (int i9 = 0; i9 < intArray2.size(); i9++) {
                            i8 += intArray2.get(i9);
                        }
                        IntArray intArray3 = intArray2;
                        RemoteAnimationTarget[] remoteAnimationTargetArr3 = i8 > 0 ? new RemoteAnimationTarget[i8] : null;
                        boolean z7 = true;
                        int i10 = 0;
                        int i11 = 0;
                        while (i10 < arrayList6.size()) {
                            TransitionInfo.Change change5 = (TransitionInfo.Change) arrayList6.get(i10);
                            boolean z8 = z7;
                            String str7 = str4;
                            boolean z9 = intArray3.get(i10) == 1;
                            int iIndexOf3 = TaskState.indexOf(this.mClosingTasks, change5);
                            boolean z10 = z9;
                            if (iIndexOf3 >= 0) {
                                this.mClosingTasks.remove(iIndexOf3);
                            }
                            int iIndexOf4 = TaskState.indexOf(this.mPausingTasks, change5);
                            if (iIndexOf4 >= 0) {
                                if (z10) {
                                    remoteAnimationTargetArr3[i11] = TransitionUtil.newTarget(change5, size, ((TaskState) this.mPausingTasks.get(iIndexOf4)).mLeash, false);
                                    i11++;
                                }
                                TaskState taskState2 = (TaskState) this.mPausingTasks.remove(iIndexOf4);
                                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                    remoteAnimationTargetArr2 = remoteAnimationTargetArr3;
                                    i2 = i10;
                                    i3 = size;
                                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 251565571246734032L, 4, z10 ? "leaf " : str6, Long.valueOf(taskState2.mTaskInfo.taskId));
                                } else {
                                    i3 = size;
                                    remoteAnimationTargetArr2 = remoteAnimationTargetArr3;
                                    i2 = i10;
                                }
                                this.mOpeningTasks.add(taskState2);
                                transaction.show(change5.getLeash());
                                transaction.setAlpha(change5.getLeash(), 1.0f);
                                transitionInfo3 = transitionInfo;
                                z7 = z8;
                                str2 = str7;
                                size = i3;
                                arrayList = arrayList6;
                                intArray = intArray3;
                            } else {
                                int i12 = size;
                                remoteAnimationTargetArr2 = remoteAnimationTargetArr3;
                                i2 = i10;
                                if (z10) {
                                    transitionInfo3 = transitionInfo;
                                    str2 = str7;
                                    size = i12;
                                    RemoteAnimationTarget remoteAnimationTargetNewTarget = TransitionUtil.newTarget(change5, size, false, transitionInfo3, transaction, this.mLeashMap);
                                    int i13 = i11 + 1;
                                    remoteAnimationTargetArr2[i11] = remoteAnimationTargetNewTarget;
                                    TransitionInfo transitionInfo4 = this.mInfo;
                                    TransitionInfo.Root root = transitionInfo4.getRoot(TransitionUtil.rootIndexFor(change5, transitionInfo4));
                                    boolean z11 = iIndexOf3 >= 0;
                                    transaction.reparent(remoteAnimationTargetNewTarget.leash, root.getLeash());
                                    boolean z12 = z11;
                                    arrayList = arrayList6;
                                    transaction.setPosition(remoteAnimationTargetNewTarget.leash, change5.getStartAbsBounds().left - root.getOffset().x, change5.getStartAbsBounds().top - root.getOffset().y);
                                    if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX) {
                                        transaction.setLayer(remoteAnimationTargetNewTarget.leash, size - i2);
                                    } else {
                                        transaction.setLayer(remoteAnimationTargetNewTarget.leash, size);
                                    }
                                    if (z12) {
                                        transaction.show(remoteAnimationTargetNewTarget.leash);
                                        if (CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER && CoreRune.FW_WORKAROUND_RESPONSE_SPEED) {
                                            f = 1.0f;
                                        } else {
                                            f = 1.0f;
                                            transaction.setAlpha(remoteAnimationTargetNewTarget.leash, 1.0f);
                                        }
                                        transaction.setAlpha(change5.getLeash(), f);
                                    } else {
                                        transaction.hide(remoteAnimationTargetNewTarget.leash);
                                    }
                                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                        intArray = intArray3;
                                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -3925432882013980076L, 13, Long.valueOf(remoteAnimationTargetNewTarget.taskId), Boolean.valueOf(z12));
                                    } else {
                                        intArray = intArray3;
                                    }
                                    this.mOpeningTasks.add(new TaskState(change5, remoteAnimationTargetNewTarget.leash));
                                    i11 = i13;
                                } else {
                                    transitionInfo3 = transitionInfo;
                                    str2 = str7;
                                    size = i12;
                                    arrayList = arrayList6;
                                    intArray = intArray3;
                                    if (TransitionUtil.isOpeningType(change5.getMode()) && RecentsTransitionHandler.m3276$$Nest$misDeskRootTask(RecentsTransitionHandler.this, change5.getTaskInfo()) && remoteAnimationTargetArr2 == null) {
                                        remoteAnimationTargetArr2 = new RemoteAnimationTarget[0];
                                    }
                                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 381285223370931163L, 1, Long.valueOf(change5.getTaskInfo().taskId));
                                    }
                                    transaction.setLayer(change5.getLeash(), size);
                                    transaction.show(change5.getLeash());
                                    this.mOpeningTasks.add(new TaskState(change5, null));
                                }
                                z7 = false;
                            }
                            i10 = i2 + 1;
                            str4 = str2;
                            intArray3 = intArray;
                            remoteAnimationTargetArr3 = remoteAnimationTargetArr2;
                            arrayList6 = arrayList;
                        }
                        RemoteAnimationTarget[] remoteAnimationTargetArr4 = remoteAnimationTargetArr3;
                        str = str4;
                        i = 1;
                        if (!z7) {
                            this.mState = 1;
                        }
                        z2 = true;
                        remoteAnimationTargetArr = remoteAnimationTargetArr4;
                    }
                    if (this.mPausingTasks.isEmpty() && ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[i]) {
                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -1544826506645848243L, i, Long.valueOf(this.mInstanceId));
                    }
                    if (!z5) {
                        Slog.d(str, "Got an activity only transition during recents, so apply directly");
                        for (int i14 = 0; i14 < transitionInfo3.getChanges().size(); i14++) {
                            TransitionInfo.Change change6 = (TransitionInfo.Change) transitionInfo3.getChanges().get(i14);
                            if (TransitionUtil.isOpeningType(change6.getMode())) {
                                transaction.show(change6.getLeash());
                                transaction.setAlpha(change6.getLeash(), 1.0f);
                            } else if (TransitionUtil.isClosingType(change6.getMode())) {
                                transaction.hide(change6.getLeash());
                            }
                        }
                    } else if (!z2) {
                        StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("Don't know how to merge this transition, foundRecentsClosing=", " recentsTaskId=", z4);
                        sbM.append(this.mRecentsTaskId);
                        Slog.w(str, sbM.toString());
                        if (z4 || this.mRecentsTaskId < 0) {
                            this.mWillFinishToHome = false;
                            cancel("didn't merge", false, false);
                            return;
                        }
                        if (CoreRune.MW_SHELL_TRANSITION) {
                            if (CoreRune.MW_PIP_SHELL_TRANSITION && transitionInfo3.getType() == 1003) {
                                Slog.d(str, "isAllowedToMergeTransition: reason=remove_pip");
                            } else if (CoreRune.MW_SPLIT_SHELL_TRANSITION && transitionInfo3.getType() == 6) {
                                for (int i15 = 0; i15 < transitionInfo3.getChanges().size(); i15++) {
                                    TransitionInfo.Change change7 = (TransitionInfo.Change) transitionInfo3.getChanges().get(i15);
                                    if (change7.getMode() != 6 || (taskInfo = change7.getTaskInfo()) == null || taskInfo.getWindowingMode() != 6 || taskInfo.getConfiguration().windowConfiguration.getStageType() == 0) {
                                        if (CoreRune.MW_FREEFORM_SHELL_TRANSITION) {
                                            return;
                                        } else {
                                            return;
                                        }
                                    }
                                }
                                Slog.d(str, "When only split change, merge");
                                Slog.d(str, "isAllowedToMergeTransition: reason=split_change_only");
                            } else if (CoreRune.MW_FREEFORM_SHELL_TRANSITION || transitionInfo3.getType() != 4 || !transitionInfo3.getChanges().stream().allMatch(new RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda0(1))) {
                                return;
                            } else {
                                Slog.d(str, "isAllowedToMergeTransition: reason=freeform_minimize_only");
                            }
                            this.mMergedTransitionInfo = transitionInfo3;
                            transaction.apply();
                            transitionFinishCallback.onTransitionFinished(null);
                            return;
                        }
                        return;
                    }
                    boolean z13 = CoreRune.FW_SHELL_TRANSITION_RECENTS_BUG_FIX;
                    if (z13) {
                        Transitions.ActiveTransition activeTransition = (Transitions.ActiveTransition) RecentsTransitionHandler.this.mTransitions.mKnownTransitions.get(iBinder);
                        SurfaceControl.Transaction transaction3 = activeTransition != null ? activeTransition.mFinishT : null;
                        if (transaction3 != null) {
                            this.mFinishTransactions.add(transaction3);
                        }
                    }
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -7456572021788229708L, 1, Long.valueOf(this.mInstanceId));
                    }
                    transaction.apply();
                    this.mFinishTransaction = transaction2;
                    DesktopModeFlags desktopModeFlags = DesktopModeFlags.ENABLE_DESKTOP_RECENTS_TRANSITIONS_CORNERS_BUGFIX;
                    if (!desktopModeFlags.isTrue()) {
                        transitionInfo3.releaseAnimSurfaces();
                    }
                    transitionFinishCallback.onTransitionFinished(null);
                    if (z13 && this.mListener == null) {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 1985428988526997718L, 1, Long.valueOf(this.mInstanceId));
                            return;
                        }
                        return;
                    }
                    boolean zIsTrue = desktopModeFlags.isTrue();
                    if (remoteAnimationTargetArr != null) {
                        if (!CoreRune.MW_MULTI_SPLIT_BACKGROUND || iBinder == null || (recentsTransitionCallback = RecentsTransitionHandler.this.mTransitions.mRecentTransitionCallback) == null) {
                            transitionInfo2 = null;
                        } else {
                            TransitionInfo transitionInfo5 = this.mInfo;
                            StageCoordinator stageCoordinator = StageCoordinator.this;
                            if (stageCoordinator.mSplitTransitions.isPendingEnter(iBinder)) {
                                int length = remoteAnimationTargetArr.length - 1;
                                while (true) {
                                    if (length < 0) {
                                        break;
                                    }
                                    RemoteAnimationTarget remoteAnimationTarget = remoteAnimationTargetArr[length];
                                    ActivityManager.RunningTaskInfo runningTaskInfo2 = remoteAnimationTarget.taskInfo;
                                    if (runningTaskInfo2 != null && runningTaskInfo2.isSplitScreen() && remoteAnimationTarget.mode == 0) {
                                        int iFindRootIndex = transitionInfo5.findRootIndex(stageCoordinator.mDisplayId);
                                        if (iFindRootIndex < 0) {
                                            break;
                                        }
                                        transitionInfo2 = null;
                                        stageCoordinator.mSplitBackgroundController.reparentToLeash(null, transitionInfo5.getRoot(iFindRootIndex).getLeash(), true);
                                    } else {
                                        length--;
                                    }
                                }
                                transitionInfo2 = null;
                            }
                        }
                        try {
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 833931798120511255L, 1, Long.valueOf(this.mInstanceId));
                            }
                            IRecentsAnimationRunner iRecentsAnimationRunner = this.mListener;
                            if (zIsTrue) {
                                transitionInfo2 = transitionInfo3;
                            }
                            IRecentsAnimationRunner$Stub$Proxy iRecentsAnimationRunner$Stub$Proxy = (IRecentsAnimationRunner$Stub$Proxy) iRecentsAnimationRunner;
                            Parcel parcelObtain = Parcel.obtain(iRecentsAnimationRunner$Stub$Proxy.mRemote);
                            try {
                                parcelObtain.writeInterfaceToken("com.android.wm.shell.recents.IRecentsAnimationRunner");
                                parcelObtain.writeTypedArray(remoteAnimationTargetArr, 0);
                                parcelObtain.writeTypedObject(transitionInfo2, 0);
                                iRecentsAnimationRunner$Stub$Proxy.mRemote.transact(4, parcelObtain, null, 1);
                                parcelObtain.recycle();
                                if (CoreRune.FW_SHELL_TRANSITION_MERGE && CoreRune.FW_WORKAROUND_RESPONSE_SPEED) {
                                    updateActiveRecents(transitionInfo3);
                                    return;
                                }
                                return;
                            } catch (Throwable th) {
                                parcelObtain.recycle();
                                throw th;
                            }
                        } catch (RemoteException e) {
                            Slog.e(str, "Error sending appeared tasks to recents animation", e);
                            return;
                        }
                    }
                    return;
                }
                TransitionInfo.Change change8 = (TransitionInfo.Change) transitionInfo3.getChanges().get(i6);
                ActivityManager.RunningTaskInfo taskInfo3 = change8.getTaskInfo();
                boolean z14 = z;
                if (taskInfo3 == null || !taskInfo3.configuration.windowConfiguration.isAlwaysOnTop()) {
                    i5 = i6;
                    if (TransitionUtil.isClosingType(change8.getMode()) && taskInfo3 != null && taskInfo3.lastParentTaskIdBeforePip > 0) {
                        cancel(ReorderTile$$ExternalSyntheticOutline0.m(taskInfo3.taskId, " is removed with its original parent", new StringBuilder("task #")), false, false);
                        return;
                    }
                    boolean z15 = taskInfo3 != null && TransitionInfo.isIndependent(change8, transitionInfo3);
                    WindowContainerToken windowContainerToken = this.mRecentsTask;
                    boolean z16 = windowContainerToken != null && windowContainerToken.equals(change8.getContainer());
                    z5 = z5 || z15;
                    int i16 = (!leafTaskFilter.test(change8) || RecentsTransitionHandler.m3276$$Nest$misDeskRootTask(RecentsTransitionHandler.this, taskInfo3)) ? 0 : 1;
                    if (TransitionUtil.isOpeningType(change8.getMode()) || TransitionUtil.isOrderOnly(change8)) {
                        arrayList2 = arrayList5;
                        c = ' ';
                        if (z16) {
                            change3 = change8;
                        } else if (z15 || i16 != 0) {
                            if (i16 != 0 && taskInfo3.topActivityType == 2) {
                                this.mOpeningSeparateHome = true;
                            }
                            if (arrayList2 == null) {
                                ArrayList arrayList7 = new ArrayList();
                                intArray2 = new IntArray();
                                arrayList2 = arrayList7;
                            }
                            IntArray intArray4 = intArray2;
                            arrayList2.add(change8);
                            intArray4.add(i16);
                            intArray2 = intArray4;
                        }
                    } else if (!TransitionUtil.isClosingType(change8.getMode())) {
                        if (change8.getMode() != 6) {
                            arrayList2 = arrayList5;
                        } else {
                            if (change8.hasFlags(32) && transitionInfo3.getType() == 6) {
                                this.mForceEnterPip = true;
                                cancel("display change", this.mWillFinishToHome, true);
                                return;
                            }
                            if (TransitionUtil.isOrderOnly(change8) || i16 == 0) {
                                arrayList2 = arrayList5;
                                if (i16 != 0 && taskInfo3.topActivityType == 2 && !z16) {
                                    if (arrayList2 == null) {
                                        ArrayList arrayList8 = new ArrayList();
                                        intArray2 = new IntArray();
                                        arrayList2 = arrayList8;
                                    }
                                    IntArray intArray5 = intArray2;
                                    arrayList2.add(change8);
                                    intArray5.add(1);
                                    intArray2 = intArray5;
                                    arrayList5 = arrayList2;
                                }
                            } else {
                                if ((change8.getFlags() & 1048576) == 0 || taskInfo3 == null || taskInfo3.getWindowingMode() != 1) {
                                    arrayList3 = arrayList5;
                                } else {
                                    arrayList3 = arrayList5;
                                    if (arrayList3 == null) {
                                        ArrayList arrayList9 = new ArrayList();
                                        intArray2 = new IntArray();
                                        arrayList3 = arrayList9;
                                    }
                                    IntArray intArray6 = intArray2;
                                    arrayList3.add(change8);
                                    intArray6.add(1);
                                    intArray2 = intArray6;
                                }
                                arrayList5 = arrayList3;
                                if (CoreRune.MW_SPLIT_SHELL_TRANSITION && change8.getTaskInfo() != null && change8.getParent() != null && change8.getParent() != change8.getLastParent() && TaskState.indexOf(this.mPausingTasks, change8) >= 0 && (change = transitionInfo3.getChange(change8.getParent())) != null && TransitionUtil.isOpeningType(change.getMode()) && change.getTaskInfo() != null && change.getTaskInfo().isSplitScreen()) {
                                    if (arrayList5 == null) {
                                        arrayList5 = new ArrayList();
                                        intArray2 = new IntArray();
                                    }
                                    ArrayList arrayList10 = arrayList5;
                                    IntArray intArray7 = intArray2;
                                    arrayList10.add(change8);
                                    intArray7.add(1);
                                    arrayList5 = arrayList10;
                                    intArray2 = intArray7;
                                }
                                z3 = true;
                            }
                        }
                        c = ' ';
                    } else if (z16) {
                        z4 = true;
                    } else {
                        if (z15 || i16 != 0) {
                            if (arrayList4 == null) {
                                arrayList4 = new ArrayList();
                            }
                            arrayList4.add(change8);
                        }
                        arrayList2 = arrayList5;
                        c = ' ';
                    }
                    arrayList5 = arrayList2;
                    i6 = i5 + 1;
                    z = z14;
                } else {
                    if (!CoreRune.MW_SHELL_TRANSITION || (taskInfo2 = change8.getTaskInfo()) == null) {
                        break;
                    }
                    if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
                        runningTaskInfo = taskInfo2;
                        i5 = i6;
                        if (change8.getMinimizeAnimState() != 2) {
                            break;
                            break;
                        }
                        break;
                    }
                    runningTaskInfo = taskInfo2;
                    i5 = i6;
                    if (runningTaskInfo.getWindowingMode() != 6 && (!CoreRune.MW_FREEFORM_SHELL_TRANSITION || !runningTaskInfo.isFreeform())) {
                        break;
                    }
                    if (!z14) {
                        return;
                    }
                    Slog.w("RecentsTransitionHandler", "merge: skip handling always-on-top task " + change8 + ", reason=display_change");
                }
                c = ' ';
                i6 = i5 + 1;
                z = z14;
            }
        }

        public final boolean sendCancel(int[] iArr, TaskSnapshot[] taskSnapshotArr) {
            String str = taskSnapshotArr != null ? "with snapshots" : "";
            try {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -9022140943350351976L, 1, Long.valueOf(this.mInstanceId), str);
                }
                ((IRecentsAnimationRunner$Stub$Proxy) this.mListener).onAnimationCanceled(iArr, taskSnapshotArr);
                return true;
            } catch (RemoteException e) {
                Slog.e("RecentsTransitionHandler", "Error canceling recents animation", e);
                return false;
            }
        }

        public final void updateActiveRecents(TransitionInfo transitionInfo) {
            for (int i = 0; i < transitionInfo.getChanges().size(); i++) {
                TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
                ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
                if (taskInfo != null && !TransitionUtil.isHomeOrRecents(change)) {
                    try {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -4814129587613254375L, 5, Long.valueOf(this.mInstanceId), Long.valueOf(taskInfo.taskId));
                        }
                        ActivityTaskManager.getService().updateActiveRecents(taskInfo.taskId);
                        return;
                    } catch (RemoteException e) {
                        Slog.e("RecentsTransitionHandler", "Failed to update active recents for input consumer", e);
                    }
                }
            }
        }

        public final void cancel(String str, boolean z, boolean z2) throws Resources.NotFoundException {
            if (this.mTransition == RecentsTransitionHandler.SYNTHETIC_TRANSITION) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 7929383390866127656L, 1, Long.valueOf(this.mInstanceId), String.valueOf(str));
                }
                try {
                    ((IRecentsAnimationRunner$Stub$Proxy) this.mListener).onAnimationCanceled(new int[0], new TaskSnapshot[0]);
                } catch (RemoteException e) {
                    Slog.e("RecentsTransitionHandler", "Error canceling previous recents animation", e);
                }
                cleanUp();
                return;
            }
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -9089009824156010496L, 13, Long.valueOf(this.mInstanceId), Boolean.valueOf(z), String.valueOf(str));
            }
            if (this.mListener != null) {
                if (z2) {
                    Pair snapshotsForPausingTasks = this.mPendingPauseSnapshotsForCancel;
                    if (snapshotsForPausingTasks == null) {
                        snapshotsForPausingTasks = getSnapshotsForPausingTasks();
                    }
                    sendCancel((int[]) snapshotsForPausingTasks.first, (TaskSnapshot[]) snapshotsForPausingTasks.second);
                } else {
                    sendCancel(null, null);
                }
            }
            if (this.mFinishCB != null) {
                finishInner(z, false, null, "cancel");
            } else {
                cleanUp();
            }
        }
    }

    public class TaskState {
        public final boolean mIsTranslucent;
        public final SurfaceControl mLeash;
        public final ActivityManager.RunningTaskInfo mTaskInfo;
        public final SurfaceControl mTaskSurface;
        public final WindowContainerToken mToken;

        public TaskState(TransitionInfo.Change change, SurfaceControl surfaceControl) {
            this.mToken = change.getContainer();
            this.mTaskInfo = change.getTaskInfo();
            this.mTaskSurface = change.getLeash();
            this.mIsTranslucent = (change.getFlags() & 4) != 0;
            this.mLeash = surfaceControl;
        }

        public static int indexOf(ArrayList arrayList, TransitionInfo.Change change) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (((TaskState) arrayList.get(size)).mToken.equals(change.getContainer())) {
                    return size;
                }
            }
            return -1;
        }

        public final String toString() {
            return "" + this.mToken + " : " + this.mLeash;
        }
    }

    /* renamed from: -$$Nest$misDeskRootTask, reason: not valid java name */
    public static boolean m3276$$Nest$misDeskRootTask(RecentsTransitionHandler recentsTransitionHandler, ActivityManager.RunningTaskInfo runningTaskInfo) {
        DesktopTasksController desktopTasksController = recentsTransitionHandler.mDesktopTasksController;
        if (desktopTasksController == null || runningTaskInfo == null) {
            return false;
        }
        return ((RootTaskDesksOrganizer) desktopTasksController.desksOrganizer).deskRootsByDeskId.contains(runningTaskInfo.taskId);
    }

    public RecentsTransitionHandler(ShellInit shellInit, ShellTaskOrganizer shellTaskOrganizer, Transitions transitions, RecentTasksController recentTasksController, HomeTransitionObserver homeTransitionObserver) {
        this.mShellTaskOrganizer = shellTaskOrganizer;
        this.mTransitions = transitions;
        this.mExecutor = transitions.mMainExecutor;
        this.mRecentTasksController = recentTasksController;
        this.mHomeTransitionObserver = homeTransitionObserver;
        if (Transitions.ENABLE_SHELL_TRANSITIONS && recentTasksController != null) {
            shellInit.addInitCallback(new RecentsTransitionHandler$$ExternalSyntheticLambda0(this, 0), this);
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean canMergeAbortedTransition(TransitionInfo transitionInfo) {
        int i = 0;
        boolean z = false;
        while (i < transitionInfo.getChanges().size()) {
            if (!((TransitionInfo.Change) transitionInfo.getChanges().get(i)).hasFlags(64)) {
                return false;
            }
            i++;
            z = true;
        }
        return z;
    }

    public RecentsController findController(IBinder iBinder) {
        for (int size = this.mControllers.size() - 1; size >= 0; size--) {
            RecentsController recentsController = (RecentsController) this.mControllers.get(size);
            if (recentsController.mTransition == iBinder) {
                return recentsController;
            }
        }
        return null;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        if (this.mControllers.isEmpty()) {
            return null;
        }
        RecentsController recentsController = (RecentsController) AlertController$$ExternalSyntheticOutline0.m(1, this.mControllers);
        recentsController.getClass();
        if (transitionRequestInfo.getType() == 6 && transitionRequestInfo.getDisplayChange() != null) {
            TransitionRequestInfo.DisplayChange displayChange = transitionRequestInfo.getDisplayChange();
            if (displayChange.getStartRotation() != displayChange.getEndRotation()) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 2330586253165148821L, 1, Long.valueOf(recentsController.mInstanceId));
                }
                recentsController.mPendingPauseSnapshotsForCancel = recentsController.getSnapshotsForPausingTasks();
            }
        }
        return null;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) throws Resources.NotFoundException {
        RecentsController recentsControllerFindController = findController(iBinder2);
        if (recentsControllerFindController == null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -9101066424534024548L, 0, null);
            }
        } else if (CoreRune.MW_MULTI_SPLIT_BACKGROUND || CoreRune.FW_SHELL_TRANSITION_RECENTS_BUG_FIX) {
            recentsControllerFindController.merge(iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback);
        } else {
            recentsControllerFindController.merge(null, transitionInfo, transaction, transaction2, transitionFinishCallback);
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) throws Resources.NotFoundException {
        for (int size = this.mControllers.size() - 1; size >= 0; size--) {
            ((RecentsController) this.mControllers.get(size)).cancel("onTransitionConsumed");
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) throws Resources.NotFoundException {
        RecentsController recentsControllerFindController = findController(SYNTHETIC_TRANSITION);
        if (recentsControllerFindController != null) {
            recentsControllerFindController.cancel("incoming_transition");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r26v2 */
    /* JADX WARN: Type inference failed for: r26v3 */
    /* JADX WARN: Type inference failed for: r26v9 */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) throws Resources.NotFoundException {
        Transitions.TransitionHandler handlerForTakeover;
        RecentsController recentsController;
        int i;
        int i2;
        int i3;
        ArrayList arrayList;
        TransitionUtil.LeafTaskFilter leafTaskFilter;
        RemoteAnimationTarget remoteAnimationTargetNewTarget;
        int i4;
        boolean z;
        int i5;
        ArrayList arrayList2;
        SurfaceControl surfaceControlCreateLeash;
        boolean z2;
        final TransitionInfo transitionInfo2 = transitionInfo;
        final SurfaceControl.Transaction transaction3 = transaction;
        final RecentsController recentsControllerFindController = findController(iBinder);
        int i6 = 0;
        boolean z3 = true;
        if (recentsControllerFindController == null) {
            if (!ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                return false;
            }
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 9121280089055901232L, 0, null);
            return false;
        }
        IApplicationThread iApplicationThread = this.mAnimApp;
        this.mAnimApp = null;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 1280866729087811123L, 1, Long.valueOf(recentsControllerFindController.mInstanceId));
        }
        recentsControllerFindController.mForceEnterPip = false;
        if (recentsControllerFindController.mListener == null || recentsControllerFindController.mTransition == null) {
            StringBuilder sb = new StringBuilder("Missing listener or transition, hasListener=");
            sb.append(recentsControllerFindController.mListener != null);
            sb.append(" hasTransition=");
            sb.append(recentsControllerFindController.mTransition != null);
            Slog.e("RecentsTransitionHandler", sb.toString());
            StringBuilder sb2 = new StringBuilder("No listener (");
            sb2.append(recentsControllerFindController.mListener == null);
            sb2.append(") or no transition (");
            recentsControllerFindController.cancel(MoveResult$$ExternalSyntheticOutline0.m(sb2, recentsControllerFindController.mTransition == null, ")"));
        } else {
            SurfaceControl leash = null;
            int i7 = 0;
            boolean z4 = false;
            while (i7 < transitionInfo2.getChanges().size()) {
                TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo2.getChanges().get(i7);
                if (TransitionUtil.isWallpaper(change)) {
                    z2 = z3;
                } else if (TransitionUtil.isClosingType(change.getMode())) {
                    z2 = z3;
                    z4 = z2;
                } else {
                    ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
                    z2 = z3;
                    if (taskInfo != null && taskInfo.topActivityType == 3) {
                        recentsControllerFindController.mRecentsTask = taskInfo.token;
                        recentsControllerFindController.mRecentsTaskId = taskInfo.taskId;
                        if (CoreRune.FW_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && leash == null) {
                            leash = change.getLeash();
                        }
                        if (CoreRune.FW_SHELL_TRANSITION_RECENTS_BUG_FIX) {
                            recentsControllerFindController.mRecentsTaskState = new TaskState(change, null);
                        }
                    } else if (taskInfo != null && taskInfo.topActivityType == 2) {
                        recentsControllerFindController.mRecentsTask = taskInfo.token;
                        recentsControllerFindController.mRecentsTaskId = taskInfo.taskId;
                        if (CoreRune.FW_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && leash == null) {
                            leash = change.getLeash();
                        }
                        if (CoreRune.FW_SHELL_TRANSITION_RECENTS_BUG_FIX) {
                            recentsControllerFindController.mRecentsTaskState = new TaskState(change, null);
                        }
                    }
                }
                i7++;
                z3 = z2;
            }
            boolean z5 = z3;
            if (recentsControllerFindController.mRecentsTask != null || z4) {
                if (CoreRune.FW_SHELL_TRANSITION_RECENTS_BUG_FIX) {
                    recentsControllerFindController.mFinishTransactions.add(transaction2);
                }
                recentsControllerFindController.mInfo = transitionInfo2;
                recentsControllerFindController.mFinishCB = transitionFinishCallback;
                recentsControllerFindController.mFinishTransaction = transaction2;
                recentsControllerFindController.mPausingTasks = new ArrayList();
                recentsControllerFindController.mClosingTasks = new ArrayList();
                recentsControllerFindController.mOpeningTasks = new ArrayList();
                recentsControllerFindController.mLeashMap = new ArrayMap();
                recentsControllerFindController.mKeyguardLocked = (transitionInfo2.getFlags() & 64) != 0 ? z5 ? 1 : 0 : false;
                ArrayList arrayList3 = new ArrayList();
                ArrayList arrayList4 = new ArrayList();
                TransitionUtil.LeafTaskFilter leafTaskFilter2 = new TransitionUtil.LeafTaskFilter();
                int size = transitionInfo2.getChanges().size();
                final int size2 = transitionInfo2.getChanges().size() * 2;
                int size3 = transitionInfo2.getChanges().size() * 3;
                if (RecentsTransitionHandler.this.mBackgroundColor != null) {
                    transitionInfo2.getChanges().stream().mapToInt(new ToIntFunction() { // from class: com.android.wm.shell.recents.RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda13
                        @Override // java.util.function.ToIntFunction
                        public final int applyAsInt(Object obj) {
                            TransitionInfo transitionInfo3 = transitionInfo2;
                            int i8 = RecentsTransitionHandler.RecentsController.$r8$clinit;
                            return TransitionUtil.rootIndexFor((TransitionInfo.Change) obj, transitionInfo3);
                        }
                    }).distinct().mapToObj(new IntFunction() { // from class: com.android.wm.shell.recents.RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda14
                        @Override // java.util.function.IntFunction
                        public final Object apply(int i8) {
                            TransitionInfo transitionInfo3 = transitionInfo2;
                            int i9 = RecentsTransitionHandler.RecentsController.$r8$clinit;
                            return transitionInfo3.getRoot(i8).getLeash();
                        }
                    }).forEach(new Consumer() { // from class: com.android.wm.shell.recents.RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda15
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            RecentsTransitionHandler.RecentsController recentsController2 = recentsControllerFindController;
                            SurfaceControl.Transaction transaction4 = transaction3;
                            int i8 = size2;
                            SurfaceControl surfaceControl = (SurfaceControl) obj;
                            if (RecentsTransitionHandler.this.mBackgroundColor == null) {
                                return;
                            }
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 812426231714299700L, 1, Long.valueOf(i8));
                            }
                            SurfaceControl surfaceControlBuild = new SurfaceControl.Builder().setName("recents_background").setColorLayer().setOpaque(true).setParent(surfaceControl).build();
                            Color color = RecentsTransitionHandler.this.mBackgroundColor;
                            transaction4.setColor(surfaceControlBuild, new float[]{color.red(), color.green(), color.blue()});
                            transaction4.setLayer(surfaceControlBuild, i8);
                            transaction4.setAlpha(surfaceControlBuild, 1.0f);
                            transaction4.show(surfaceControlBuild);
                        }
                    });
                }
                int i8 = -1;
                int i9 = 0;
                char c = z5;
                while (i9 < transitionInfo2.getChanges().size()) {
                    TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo2.getChanges().get(i9);
                    if (!CoreRune.FW_SHELL_TRANSITION_REMOTE || ((!CoreRune.FW_REMOTE_WALLPAPER_ANIM || !TransitionUtil.isWallpaper(change2) || change2.getParent() == null) && (!CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION || !leafTaskFilter2.test(change2) || !MultiTaskingTransitionProvider.buildForceHideAnimationIfNeeded("RecentsTransitionHandler", change2, RecentsTransitionHandler.this.mMultiTaskingTransitions)))) {
                        ArrayList arrayList5 = arrayList4;
                        ActivityManager.RunningTaskInfo taskInfo2 = change2.getTaskInfo();
                        if (TransitionUtil.isWallpaper(change2)) {
                            TransitionUtil.LeafTaskFilter leafTaskFilter3 = leafTaskFilter2;
                            i3 = i8;
                            i = size2;
                            i2 = i9;
                            RemoteAnimationTarget remoteAnimationTargetNewTarget2 = TransitionUtil.newTarget(change2, size - i9, false, transitionInfo2, transaction3, recentsControllerFindController.mLeashMap);
                            arrayList5.add(remoteAnimationTargetNewTarget2);
                            transaction3.setAlpha(remoteAnimationTargetNewTarget2.leash, 1.0f);
                            arrayList = arrayList5;
                            leafTaskFilter = leafTaskFilter3;
                        } else {
                            TransitionUtil.LeafTaskFilter leafTaskFilter4 = leafTaskFilter2;
                            i = size2;
                            i2 = i9;
                            ArrayList arrayList6 = arrayList5;
                            i3 = i8;
                            if (leafTaskFilter4.test(change2)) {
                                if (CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER) {
                                    int i10 = size - i2;
                                    ArrayMap arrayMap = recentsControllerFindController.mLeashMap;
                                    ArrayMap arrayMap2 = recentsControllerFindController.mTransferLeashMap;
                                    if (change2.getTaskInfo() == null || arrayMap2 == null || arrayMap2.isEmpty()) {
                                        arrayList2 = arrayList6;
                                        surfaceControlCreateLeash = null;
                                    } else {
                                        Iterator it = arrayMap2.entrySet().iterator();
                                        while (true) {
                                            if (!it.hasNext()) {
                                                arrayList2 = arrayList6;
                                                surfaceControlCreateLeash = null;
                                                break;
                                            }
                                            SurfaceControl surfaceControl = (SurfaceControl) ((Map.Entry) it.next()).getKey();
                                            arrayList2 = arrayList6;
                                            if (surfaceControl.isSameSurface(change2.getLeash())) {
                                                surfaceControlCreateLeash = (SurfaceControl) arrayMap2.get(surfaceControl);
                                                break;
                                            }
                                            arrayList6 = arrayList2;
                                        }
                                        if (surfaceControlCreateLeash != null) {
                                            transaction3.reparent(surfaceControlCreateLeash, transitionInfo2.getRoot(TransitionUtil.rootIndexFor(change2, transitionInfo2)).getLeash());
                                            int size4 = transitionInfo2.getChanges().size() - i10;
                                            boolean zIsOpeningType = TransitionUtil.isOpeningType(transitionInfo2.getType());
                                            int size5 = transitionInfo2.getChanges().size();
                                            int mode = change2.getMode();
                                            transaction3.reparent(surfaceControlCreateLeash, transitionInfo2.getRoot(TransitionUtil.rootIndexFor(change2, transitionInfo2)).getLeash());
                                            if (TransitionUtil.isOpeningType(mode)) {
                                                if (zIsOpeningType) {
                                                    transaction3.setLayer(surfaceControlCreateLeash, (transitionInfo2.getChanges().size() + size5) - size4);
                                                } else {
                                                    transaction3.setLayer(surfaceControlCreateLeash, size5 - size4);
                                                }
                                            } else if (TransitionUtil.isClosingType(mode) && zIsOpeningType) {
                                                transaction3.setLayer(surfaceControlCreateLeash, size5 - size4);
                                            } else {
                                                transaction3.setLayer(surfaceControlCreateLeash, (transitionInfo2.getChanges().size() + size5) - size4);
                                            }
                                            transaction3.reparent(change2.getLeash(), surfaceControlCreateLeash);
                                            transaction3.setAlpha(change2.getLeash(), 1.0f);
                                            transaction3.show(change2.getLeash());
                                            transaction3.setPosition(change2.getLeash(), 0.0f, 0.0f);
                                            transaction3.setLayer(change2.getLeash(), 0);
                                        }
                                    }
                                    if (surfaceControlCreateLeash == null) {
                                        surfaceControlCreateLeash = TransitionUtil.createLeash(transitionInfo2, change2, i10, transaction3);
                                    }
                                    if (arrayMap != null) {
                                        arrayMap.put(change2.getLeash(), surfaceControlCreateLeash);
                                    }
                                    remoteAnimationTargetNewTarget = TransitionUtil.newTarget(change2, i10, surfaceControlCreateLeash, false);
                                    arrayList = arrayList2;
                                } else {
                                    arrayList = arrayList6;
                                    remoteAnimationTargetNewTarget = TransitionUtil.newTarget(change2, size - i2, false, transitionInfo2, transaction3, recentsControllerFindController.mLeashMap);
                                }
                                arrayList3.add(remoteAnimationTargetNewTarget);
                                if (TransitionUtil.isClosingType(change2.getMode())) {
                                    recentsControllerFindController.mPausingTasks.add(new TaskState(change2, remoteAnimationTargetNewTarget.leash));
                                    int i11 = change2.getTaskInfo().taskId;
                                    if (taskInfo2.topActivityType == 2) {
                                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[c]) {
                                            i5 = i11;
                                            z = c;
                                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 5813780011434128264L, z ? 1 : 0, Long.valueOf(taskInfo2.taskId));
                                        } else {
                                            z = c;
                                            i5 = i11;
                                        }
                                        recentsControllerFindController.mPausingSeparateHome = z;
                                        i4 = i5;
                                        leafTaskFilter = leafTaskFilter4;
                                    } else {
                                        int i12 = size3 - i2;
                                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[c]) {
                                            leafTaskFilter = leafTaskFilter4;
                                            i4 = i11;
                                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 3698060777107697665L, 5, Long.valueOf(taskInfo2.taskId), Long.valueOf(i12));
                                        } else {
                                            i4 = i11;
                                            leafTaskFilter = leafTaskFilter4;
                                        }
                                        transaction3.setLayer(remoteAnimationTargetNewTarget.leash, i12);
                                    }
                                    PictureInPictureParams pictureInPictureParams = taskInfo2.pictureInPictureParams;
                                    if (pictureInPictureParams != null && pictureInPictureParams.isAutoEnterEnabled()) {
                                        recentsControllerFindController.mPipTask = taskInfo2.token;
                                    }
                                    i8 = i4;
                                    i9 = i2 + 1;
                                    transaction3 = transaction;
                                    arrayList4 = arrayList;
                                    size2 = i;
                                    leafTaskFilter2 = leafTaskFilter;
                                    c = 1;
                                    i6 = 0;
                                } else {
                                    leafTaskFilter = leafTaskFilter4;
                                    if (taskInfo2 != null && taskInfo2.topActivityType == 3) {
                                        int i13 = i - i2;
                                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -4244189887162074998L, 1, Long.valueOf(i13));
                                        }
                                        transaction3.setLayer(remoteAnimationTargetNewTarget.leash, i13);
                                    } else if (taskInfo2 == null || taskInfo2.topActivityType != 2) {
                                        if (TransitionUtil.isOpeningType(change2.getMode())) {
                                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 5439651840349387650L, 1, Long.valueOf(taskInfo2.taskId));
                                            }
                                            recentsControllerFindController.mOpeningTasks.add(new TaskState(change2, remoteAnimationTargetNewTarget.leash));
                                        }
                                    } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -7244179815734298458L, 1, Long.valueOf(taskInfo2.taskId));
                                    }
                                }
                            } else {
                                arrayList = arrayList6;
                                leafTaskFilter = leafTaskFilter4;
                                if (taskInfo2 == null || !TransitionInfo.isIndependent(change2, transitionInfo2)) {
                                    if (TransitionUtil.isDividerBar(change2) || TransitionUtil.isDimLayer(change2)) {
                                        transitionInfo2 = transitionInfo;
                                        arrayList3.add(TransitionUtil.newTarget(change2, size - i2, false, transitionInfo2, transaction3, recentsControllerFindController.mLeashMap));
                                    } else if (CoreRune.FW_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && TransitionUtil.isTransientLaunchOverlay(change2)) {
                                        RemoteAnimationTarget remoteAnimationTargetNewTarget3 = TransitionUtil.newTarget(change2, size - i2, false, transitionInfo, transaction3, recentsControllerFindController.mLeashMap);
                                        SurfaceControl surfaceControl2 = remoteAnimationTargetNewTarget3.leash;
                                        if (surfaceControl2 != null && leash != null) {
                                            transaction3.setRelativeLayer(surfaceControl2, leash, 1);
                                        }
                                        arrayList3.add(remoteAnimationTargetNewTarget3);
                                    } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -8477821708665405557L, 1, Long.valueOf(taskInfo2 != null ? taskInfo2.taskId : -1L));
                                    }
                                } else if (TransitionUtil.isClosingType(change2.getMode())) {
                                    int i14 = size3 - i2;
                                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -3712724663189422270L, 5, Long.valueOf(taskInfo2.taskId), Long.valueOf(i14));
                                    }
                                    transaction3.setLayer(change2.getLeash(), i14);
                                    recentsControllerFindController.mPausingTasks.add(new TaskState(change2, null));
                                } else if (TransitionUtil.isOpeningType(change2.getMode())) {
                                    int i15 = size - i2;
                                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -1232595227583365651L, 5, Long.valueOf(taskInfo2.taskId), Long.valueOf(i15));
                                    }
                                    transaction3.setLayer(change2.getLeash(), i15);
                                    recentsControllerFindController.mOpeningTasks.add(new TaskState(change2, null));
                                } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -5178238118308399519L, 1, Long.valueOf(taskInfo2.taskId));
                                }
                                transitionInfo2 = transitionInfo;
                            }
                        }
                    } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[c]) {
                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -1370672644722117083L, i6, String.valueOf(change2));
                        arrayList = arrayList4;
                        leafTaskFilter = leafTaskFilter2;
                        i = size2;
                        i3 = i8;
                        i2 = i9;
                    } else {
                        leafTaskFilter = leafTaskFilter2;
                        i = size2;
                        i3 = i8;
                        i2 = i9;
                        arrayList = arrayList4;
                    }
                    i8 = i3;
                    i9 = i2 + 1;
                    transaction3 = transaction;
                    arrayList4 = arrayList;
                    size2 = i;
                    leafTaskFilter2 = leafTaskFilter;
                    c = 1;
                    i6 = 0;
                }
                int i16 = i8;
                ArrayList arrayList7 = arrayList4;
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -9075571057287285555L, 1, Long.valueOf(transaction.getId()));
                }
                transaction.apply();
                Transitions transitions = RecentsTransitionHandler.this.mTransitions;
                IBinder iBinder2 = recentsControllerFindController.mTransition;
                ArrayList arrayList8 = transitions.mHandlers;
                int size6 = arrayList8.size();
                int i17 = 0;
                while (true) {
                    if (i17 >= size6) {
                        handlerForTakeover = null;
                        break;
                    }
                    Object obj = arrayList8.get(i17);
                    i17++;
                    handlerForTakeover = ((Transitions.TransitionHandler) obj).getHandlerForTakeover(iBinder2, transitionInfo2);
                    if (handlerForTakeover != null) {
                        break;
                    }
                }
                recentsControllerFindController.mTakeoverHandler = handlerForTakeover;
                Bundle bundle = new Bundle(2);
                bundle.putParcelable("key_SplitBounds", RecentsTransitionHandler.this.mRecentTasksController.getSplitBoundsForTaskId(i16));
                bundle.putBoolean("extra_shell_can_hand_off_animation", recentsControllerFindController.mTakeoverHandler != null);
                try {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 4637593205120250810L, 5, Long.valueOf(recentsControllerFindController.mInstanceId), Long.valueOf(arrayList3.size()));
                    }
                    recentsController = recentsControllerFindController;
                    try {
                        ((IRecentsAnimationRunner$Stub$Proxy) recentsControllerFindController.mListener).onAnimationStart(recentsController, (RemoteAnimationTarget[]) arrayList3.toArray(new RemoteAnimationTarget[arrayList3.size()]), (RemoteAnimationTarget[]) arrayList7.toArray(new RemoteAnimationTarget[arrayList7.size()]), new Rect(0, 0, 0, 0), new Rect(), bundle, transitionInfo);
                        for (int i18 = 0; i18 < RecentsTransitionHandler.this.mStateListeners.size(); i18++) {
                            ((RecentsTransitionStateListener) RecentsTransitionHandler.this.mStateListeners.get(i18)).onTransitionStateChanged(3);
                        }
                    } catch (RemoteException e) {
                        e = e;
                        Slog.e("RecentsTransitionHandler", "Error starting recents animation", e);
                        recentsController.cancel("onAnimationStart() failed");
                        Transitions.setRunningRemoteTransitionDelegate(iApplicationThread);
                        return true;
                    }
                } catch (RemoteException e2) {
                    e = e2;
                    recentsController = recentsControllerFindController;
                }
                Transitions.setRunningRemoteTransitionDelegate(iApplicationThread);
                return true;
            }
            Slog.e("RecentsTransitionHandler", "Tried to start recents while it is already running.");
            recentsControllerFindController.cancel("No recents task and no pausing tasks");
        }
        if (!ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
            return false;
        }
        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -8594519572043583178L, 0, null);
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:61:0x01a4 A[LOOP:2: B:40:0x014b->B:61:0x01a4, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x019a A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public IBinder startRecentsTransition(PendingIntent pendingIntent, Intent intent, Bundle bundle, IApplicationThread iApplicationThread, IRecentsAnimationRunner iRecentsAnimationRunner) throws Resources.NotFoundException {
        Consumer consumer;
        this.mAnimApp = iApplicationThread;
        for (int i = 0; i < this.mStateListeners.size(); i++) {
            ((RecentsTransitionStateListener) this.mStateListeners.get(i)).onTransitionStateChanged(2);
        }
        Transitions.TransitionHandler transitionHandler = null;
        if (bundle.getBoolean("is_synthetic_recents_transition", false)) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -8258308780561580817L, 0, null);
            }
            RecentsController recentsController = !this.mControllers.isEmpty() ? (RecentsController) this.mControllers.getLast() : null;
            if (recentsController != null) {
                recentsController.cancel(recentsController.mTransition == SYNTHETIC_TRANSITION ? "existing_running_synthetic_transition" : "existing_running_transition");
                return null;
            }
            boolean z = CoreRune.MW_SHELL_TRANSITION_BUG_FIX;
            RecentsController recentsController2 = new RecentsController(iRecentsAnimationRunner);
            recentsController2.mTransition = SYNTHETIC_TRANSITION;
            ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) RecentsTransitionHandler.this.mShellTaskOrganizer.getRunningTasks(0).stream().filter(new RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda0(0)).findFirst().get();
            RemoteAnimationTarget remoteAnimationTargetNewSyntheticTarget = TransitionUtil.newSyntheticTarget(runningTaskInfo, RecentsTransitionHandler.this.mShellTaskOrganizer.getHomeTaskSurface(), 1);
            RemoteAnimationTarget remoteAnimationTargetNewSyntheticTarget2 = TransitionUtil.newSyntheticTarget(runningTaskInfo, RecentsTransitionHandler.this.mShellTaskOrganizer.getHomeTaskSurface(), 2);
            ArrayList arrayList = new ArrayList();
            arrayList.add(remoteAnimationTargetNewSyntheticTarget);
            arrayList.add(remoteAnimationTargetNewSyntheticTarget2);
            try {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 4637593205120250810L, 5, Long.valueOf(recentsController2.mInstanceId), Long.valueOf(arrayList.size()));
                }
                ((IRecentsAnimationRunner$Stub$Proxy) recentsController2.mListener).onAnimationStart(recentsController2, (RemoteAnimationTarget[]) arrayList.toArray(new RemoteAnimationTarget[arrayList.size()]), new RemoteAnimationTarget[0], new Rect(0, 0, 0, 0), new Rect(), new Bundle(), null);
                for (int i2 = 0; i2 < RecentsTransitionHandler.this.mStateListeners.size(); i2++) {
                    ((RecentsTransitionStateListener) RecentsTransitionHandler.this.mStateListeners.get(i2)).onTransitionStateChanged(3);
                }
            } catch (RemoteException e) {
                Slog.e("RecentsTransitionHandler", "Error starting recents animation", e);
                recentsController2.cancel("startSynthetricTransition() failed");
            }
            this.mControllers.add(recentsController2);
            return SYNTHETIC_TRANSITION;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -2264622259801542509L, 0, null);
        }
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.sendPendingIntent(pendingIntent, intent, bundle);
        int i3 = 0;
        Consumer consumer2 = null;
        while (true) {
            if (i3 >= this.mMixers.size()) {
                break;
            }
            final DefaultMixedHandler defaultMixedHandler = (DefaultMixedHandler) this.mMixers.get(i3);
            if (defaultMixedHandler.mRecentsHandler == null) {
                consumer2 = null;
                if (consumer2 != null) {
                    transitionHandler = (DefaultMixedHandler) this.mMixers.get(i3);
                    break;
                }
                i3++;
            } else {
                if (defaultMixedHandler.mSplitHandler.isSplitScreenVisible()) {
                    final int i4 = 0;
                    consumer = new Consumer() { // from class: com.android.wm.shell.transition.DefaultMixedHandler$$ExternalSyntheticLambda1
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            int i5 = i4;
                            DefaultMixedHandler defaultMixedHandler2 = defaultMixedHandler;
                            IBinder iBinder = (IBinder) obj;
                            switch (i5) {
                                case 0:
                                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 2427705020513597812L, 0, null);
                                    }
                                    defaultMixedHandler2.mActiveTransitions.add(defaultMixedHandler2.createRecentsMixedTransition(iBinder, 4));
                                    break;
                                case 1:
                                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -7902460613169563333L, 0, null);
                                    }
                                    defaultMixedHandler2.mActiveTransitions.add(defaultMixedHandler2.createRecentsMixedTransition(iBinder, 6));
                                    break;
                                default:
                                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 417213042499073320L, 0, null);
                                    }
                                    defaultMixedHandler2.mActiveTransitions.add(defaultMixedHandler2.createRecentsMixedTransition(iBinder, 7));
                                    break;
                            }
                        }
                    };
                } else {
                    KeyguardTransitionHandler keyguardTransitionHandler = defaultMixedHandler.mKeyguardHandler;
                    if (keyguardTransitionHandler.mKeyguardShowing && keyguardTransitionHandler.mStartedTransitions.isEmpty()) {
                        final int i5 = 1;
                        consumer = new Consumer() { // from class: com.android.wm.shell.transition.DefaultMixedHandler$$ExternalSyntheticLambda1
                            @Override // java.util.function.Consumer
                            public final void accept(Object obj) {
                                int i52 = i5;
                                DefaultMixedHandler defaultMixedHandler2 = defaultMixedHandler;
                                IBinder iBinder = (IBinder) obj;
                                switch (i52) {
                                    case 0:
                                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 2427705020513597812L, 0, null);
                                        }
                                        defaultMixedHandler2.mActiveTransitions.add(defaultMixedHandler2.createRecentsMixedTransition(iBinder, 4));
                                        break;
                                    case 1:
                                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -7902460613169563333L, 0, null);
                                        }
                                        defaultMixedHandler2.mActiveTransitions.add(defaultMixedHandler2.createRecentsMixedTransition(iBinder, 6));
                                        break;
                                    default:
                                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 417213042499073320L, 0, null);
                                        }
                                        defaultMixedHandler2.mActiveTransitions.add(defaultMixedHandler2.createRecentsMixedTransition(iBinder, 7));
                                        break;
                                }
                            }
                        };
                    } else {
                        DesktopTasksController desktopTasksController = defaultMixedHandler.mDesktopTasksController;
                        if (desktopTasksController != null && desktopTasksController.taskRepository.isAnyDeskActive(0)) {
                            final int i6 = 2;
                            consumer = new Consumer() { // from class: com.android.wm.shell.transition.DefaultMixedHandler$$ExternalSyntheticLambda1
                                @Override // java.util.function.Consumer
                                public final void accept(Object obj) {
                                    int i52 = i6;
                                    DefaultMixedHandler defaultMixedHandler2 = defaultMixedHandler;
                                    IBinder iBinder = (IBinder) obj;
                                    switch (i52) {
                                        case 0:
                                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 2427705020513597812L, 0, null);
                                            }
                                            defaultMixedHandler2.mActiveTransitions.add(defaultMixedHandler2.createRecentsMixedTransition(iBinder, 4));
                                            break;
                                        case 1:
                                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -7902460613169563333L, 0, null);
                                            }
                                            defaultMixedHandler2.mActiveTransitions.add(defaultMixedHandler2.createRecentsMixedTransition(iBinder, 6));
                                            break;
                                        default:
                                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 417213042499073320L, 0, null);
                                            }
                                            defaultMixedHandler2.mActiveTransitions.add(defaultMixedHandler2.createRecentsMixedTransition(iBinder, 7));
                                            break;
                                    }
                                }
                            };
                        }
                        consumer2 = null;
                        if (consumer2 != null) {
                        }
                    }
                }
                consumer2 = consumer;
                if (consumer2 != null) {
                }
            }
        }
        IBinder iBinderStartTransition = this.mTransitions.startTransition(3, windowContainerTransaction, transitionHandler == null ? this : transitionHandler);
        if (transitionHandler != null) {
            consumer2.accept(iBinderStartTransition);
        }
        RecentsController recentsController3 = new RecentsController(iRecentsAnimationRunner);
        if (iBinderStartTransition == null) {
            recentsController3.cancel("startRecentsTransition");
            return iBinderStartTransition;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -254510363185693942L, 1, Long.valueOf(recentsController3.mInstanceId), String.valueOf(iBinderStartTransition));
        }
        recentsController3.mTransition = iBinderStartTransition;
        this.mControllers.add(recentsController3);
        return iBinderStartTransition;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void transferAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, WindowContainerTransaction windowContainerTransaction) {
        if (windowContainerTransaction == null) {
            return;
        }
        RecentsController recentsControllerFindController = findController(iBinder);
        if (recentsControllerFindController == null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 662544798542868933L, 0, null);
                return;
            }
            return;
        }
        if (windowContainerTransaction.getTransferLeashMap().isEmpty()) {
            return;
        }
        recentsControllerFindController.mTransferLeashMap = windowContainerTransaction.getTransferLeashMap();
        int iFindRootIndex = transitionInfo.findRootIndex(0);
        if (iFindRootIndex < 0) {
            return;
        }
        SurfaceControl leash = transitionInfo.getRoot(iFindRootIndex).getLeash();
        for (int size = recentsControllerFindController.mTransferLeashMap.size() - 1; size >= 0; size--) {
            SurfaceControl surfaceControl = (SurfaceControl) recentsControllerFindController.mTransferLeashMap.valueAt(size);
            if (surfaceControl == null || !surfaceControl.isValid()) {
                recentsControllerFindController.mTransferLeashMap.removeAt(size);
                Slog.d("RecentsTransitionHandler", "Cannot transfer invalid leash=" + surfaceControl);
            } else {
                transaction.reparent(surfaceControl, leash);
            }
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void transitionReady(IBinder iBinder, TransitionInfo transitionInfo) {
        if (findController(iBinder) == null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 2145544636936567916L, 0, null);
            }
        } else {
            IApplicationThread iApplicationThread = this.mAnimApp;
            if (iApplicationThread != null) {
                transitionInfo.setRemoteAppThread(iApplicationThread);
            }
        }
    }
}
