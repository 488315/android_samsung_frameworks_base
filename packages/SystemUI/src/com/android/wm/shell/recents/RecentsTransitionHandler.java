package com.android.wm.shell.recents;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.IApplicationThread;
import android.app.PictureInPictureParams;
import android.content.Context;
import android.content.pm.ActivityInfo;
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
import android.window.DesktopModeFlags;
import android.window.PictureInPictureSurfaceTransaction;
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
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.recents.IRecentsAnimationController;
import com.android.wm.shell.recents.RecentsTransitionHandler;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.HomeTransitionObserver;
import com.android.wm.shell.transition.MultiTaskingTransitionProvider;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class RecentsTransitionHandler implements Transitions.TransitionHandler, Transitions.TransitionObserver {
    public static final IBinder SYNTHETIC_TRANSITION = new Binder();
    public Color mBackgroundColor;
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                RecentsTransitionHandler.RecentsController recentsController = RecentsTransitionHandler.RecentsController.this;
                RecentsTransitionHandler.this.mExecutor.execute(new RecentsTransitionHandler$$ExternalSyntheticLambda0(recentsController, 1));
            }
        };

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.wm.shell.recents.RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda5] */
        public RecentsController(IRecentsAnimationRunner iRecentsAnimationRunner) {
            this.mListener = iRecentsAnimationRunner;
            try {
                iRecentsAnimationRunner.asBinder().linkToDeath(this.mDeathHandler, 0);
            } catch (RemoteException e) {
                Slog.e("RecentsTransitionHandler", "RecentsController: failed to link to death", e);
                this.mListener = null;
            }
        }

        public static void setCornerRadiusForFreeformTasks(Context context, SurfaceControl.Transaction transaction, ArrayList arrayList) {
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

        public final void cancel(String str) {
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

        /* JADX WARN: Removed duplicated region for block: B:102:0x040b  */
        /* JADX WARN: Removed duplicated region for block: B:137:0x0486  */
        /* JADX WARN: Removed duplicated region for block: B:140:0x04a7 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:151:? A[RETURN, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:152:0x01d6  */
        /* JADX WARN: Removed duplicated region for block: B:29:0x0085  */
        /* JADX WARN: Removed duplicated region for block: B:36:0x00c2  */
        /* JADX WARN: Removed duplicated region for block: B:45:0x00ee  */
        /* JADX WARN: Removed duplicated region for block: B:48:0x00fb  */
        /* JADX WARN: Removed duplicated region for block: B:53:0x010d  */
        /* JADX WARN: Removed duplicated region for block: B:68:0x014f  */
        /* JADX WARN: Removed duplicated region for block: B:72:0x015f  */
        /* JADX WARN: Removed duplicated region for block: B:75:0x0168  */
        /* JADX WARN: Removed duplicated region for block: B:78:0x016e  */
        /* JADX WARN: Removed duplicated region for block: B:79:0x0172  */
        /* JADX WARN: Removed duplicated region for block: B:81:0x0177  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void finishInner(boolean r23, boolean r24, com.android.internal.os.IResultReceiver r25, java.lang.String r26) {
            /*
                Method dump skipped, instructions count: 1340
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.recents.RecentsTransitionHandler.RecentsController.finishInner(boolean, boolean, com.android.internal.os.IResultReceiver, java.lang.String):void");
        }

        public final Pair getSnapshotsForPausingTasks() {
            TaskSnapshot[] taskSnapshotArr;
            ArrayList arrayList = this.mPausingTasks;
            int[] iArr = null;
            if (arrayList != null && arrayList.size() > 0) {
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
                return new Pair(iArr, taskSnapshotArr);
            }
            taskSnapshotArr = null;
            return new Pair(iArr, taskSnapshotArr);
        }

        public final void merge(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
            boolean z;
            String str;
            int i;
            boolean z2;
            RemoteAnimationTarget[] remoteAnimationTargetArr;
            int i2;
            ActivityManager.RunningTaskInfo taskInfo;
            TransitionInfo transitionInfo2;
            StageCoordinator.RecentsTransitionCallback recentsTransitionCallback;
            RemoteAnimationTarget[] remoteAnimationTargetArr2;
            int i3;
            String str2;
            ArrayList arrayList;
            IntArray intArray;
            float f;
            int i4;
            String str3;
            int i5;
            ActivityManager.RunningTaskInfo taskInfo2;
            int i6;
            ArrayList arrayList2;
            char c;
            ArrayList arrayList3;
            TransitionInfo.Change change;
            ActivityManager.RunningTaskInfo taskInfo3;
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
            int m = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo3, 1);
            while (true) {
                if (m < 0) {
                    z = false;
                    break;
                }
                TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo3.getChanges().get(m);
                if (change2.hasFlags(32) && change2.getMode() == 6) {
                    z = true;
                    break;
                }
                m--;
            }
            int i7 = 0;
            boolean z3 = false;
            boolean z4 = false;
            boolean z5 = false;
            ArrayList arrayList4 = null;
            TransitionInfo.Change change3 = null;
            ArrayList arrayList5 = null;
            IntArray intArray2 = null;
            boolean z6 = z;
            while (true) {
                String str4 = "RecentsTransitionHandler";
                if (i7 >= transitionInfo3.getChanges().size()) {
                    ArrayList arrayList6 = arrayList5;
                    if (z3 && z4) {
                        Pair pair = this.mPendingPauseSnapshotsForCancel;
                        if (pair == null) {
                            pair = getSnapshotsForPausingTasks();
                        }
                        sendCancel((int[]) pair.first, (TaskSnapshot[]) pair.second);
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
                    boolean z7 = false;
                    if (arrayList4 != null) {
                        int i8 = 0;
                        while (i8 < arrayList4.size()) {
                            TransitionInfo.Change change4 = (TransitionInfo.Change) arrayList4.get(i8);
                            int indexOf = TaskState.indexOf(this.mPausingTasks, change4);
                            if (indexOf >= 0) {
                                this.mClosingTasks.add((TaskState) this.mPausingTasks.remove(indexOf));
                                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                    str3 = str5;
                                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -8004858446668286751L, 1, Long.valueOf(change4.getTaskInfo().taskId));
                                } else {
                                    str3 = str5;
                                }
                                i5 = i8;
                            } else {
                                str3 = str5;
                                int indexOf2 = TaskState.indexOf(this.mOpeningTasks, change4);
                                if (indexOf2 < 0) {
                                    Slog.w("RecentsTransitionHandler", "Closing a task that wasn't opening, this may be split or something unexpected: " + change4.getTaskInfo().taskId);
                                    i5 = i8;
                                    i8 = i5 + 1;
                                    str5 = str3;
                                } else {
                                    TaskState taskState = (TaskState) this.mOpeningTasks.remove(indexOf2);
                                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                        i5 = i8;
                                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 1357141655006297500L, 4, taskState.mLeash != null ? "leaf " : str3, Long.valueOf(taskState.mTaskInfo.taskId));
                                    } else {
                                        i5 = i8;
                                    }
                                    this.mPausingTasks.add(taskState);
                                }
                            }
                            z7 = true;
                            i8 = i5 + 1;
                            str5 = str3;
                        }
                    }
                    String str6 = str5;
                    if (arrayList6 == null || arrayList6.size() <= 0) {
                        str = "RecentsTransitionHandler";
                        i = 1;
                        z2 = z7;
                        remoteAnimationTargetArr = null;
                    } else {
                        int size = this.mInfo.getChanges().size() * 3;
                        int i9 = 0;
                        for (int i10 = 0; i10 < intArray2.size(); i10++) {
                            i9 += intArray2.get(i10);
                        }
                        IntArray intArray3 = intArray2;
                        RemoteAnimationTarget[] remoteAnimationTargetArr3 = i9 > 0 ? new RemoteAnimationTarget[i9] : null;
                        boolean z8 = true;
                        int i11 = 0;
                        int i12 = 0;
                        while (i11 < arrayList6.size()) {
                            TransitionInfo.Change change5 = (TransitionInfo.Change) arrayList6.get(i11);
                            boolean z9 = z8;
                            String str7 = str4;
                            boolean z10 = intArray3.get(i11) == 1;
                            int indexOf3 = TaskState.indexOf(this.mClosingTasks, change5);
                            boolean z11 = z10;
                            if (indexOf3 >= 0) {
                                this.mClosingTasks.remove(indexOf3);
                            }
                            int indexOf4 = TaskState.indexOf(this.mPausingTasks, change5);
                            if (indexOf4 >= 0) {
                                if (z11) {
                                    remoteAnimationTargetArr3[i12] = TransitionUtil.newTarget(change5, size, ((TaskState) this.mPausingTasks.get(indexOf4)).mLeash, false);
                                    i12++;
                                }
                                TaskState taskState2 = (TaskState) this.mPausingTasks.remove(indexOf4);
                                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                    remoteAnimationTargetArr2 = remoteAnimationTargetArr3;
                                    i3 = i11;
                                    i4 = size;
                                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 251565571246734032L, 4, z11 ? "leaf " : str6, Long.valueOf(taskState2.mTaskInfo.taskId));
                                } else {
                                    i4 = size;
                                    remoteAnimationTargetArr2 = remoteAnimationTargetArr3;
                                    i3 = i11;
                                }
                                this.mOpeningTasks.add(taskState2);
                                transaction.show(change5.getLeash());
                                transaction.setAlpha(change5.getLeash(), 1.0f);
                                transitionInfo3 = transitionInfo;
                                z8 = z9;
                                str2 = str7;
                                size = i4;
                                arrayList = arrayList6;
                                intArray = intArray3;
                            } else {
                                int i13 = size;
                                remoteAnimationTargetArr2 = remoteAnimationTargetArr3;
                                i3 = i11;
                                if (z11) {
                                    transitionInfo3 = transitionInfo;
                                    str2 = str7;
                                    size = i13;
                                    RemoteAnimationTarget newTarget = TransitionUtil.newTarget(change5, size, false, transitionInfo3, transaction, this.mLeashMap);
                                    int i14 = i12 + 1;
                                    remoteAnimationTargetArr2[i12] = newTarget;
                                    TransitionInfo transitionInfo4 = this.mInfo;
                                    TransitionInfo.Root root = transitionInfo4.getRoot(TransitionUtil.rootIndexFor(change5, transitionInfo4));
                                    boolean z12 = indexOf3 >= 0;
                                    transaction.reparent(newTarget.leash, root.getLeash());
                                    boolean z13 = z12;
                                    arrayList = arrayList6;
                                    transaction.setPosition(newTarget.leash, change5.getStartAbsBounds().left - root.getOffset().x, change5.getStartAbsBounds().top - root.getOffset().y);
                                    transaction.setLayer(newTarget.leash, size);
                                    if (z13) {
                                        transaction.show(newTarget.leash);
                                        if (CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER && CoreRune.FW_WORKAROUND_RESPONSE_SPEED) {
                                            f = 1.0f;
                                        } else {
                                            f = 1.0f;
                                            transaction.setAlpha(newTarget.leash, 1.0f);
                                        }
                                        transaction.setAlpha(change5.getLeash(), f);
                                    } else {
                                        transaction.hide(newTarget.leash);
                                    }
                                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                        intArray = intArray3;
                                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -3925432882013980076L, 13, Long.valueOf(newTarget.taskId), Boolean.valueOf(z13));
                                    } else {
                                        intArray = intArray3;
                                    }
                                    this.mOpeningTasks.add(new TaskState(change5, newTarget.leash));
                                    i12 = i14;
                                } else {
                                    transitionInfo3 = transitionInfo;
                                    str2 = str7;
                                    size = i13;
                                    arrayList = arrayList6;
                                    intArray = intArray3;
                                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 381285223370931163L, 1, Long.valueOf(change5.getTaskInfo().taskId));
                                    }
                                    transaction.setLayer(change5.getLeash(), size);
                                    transaction.show(change5.getLeash());
                                    this.mOpeningTasks.add(new TaskState(change5, null));
                                }
                                z8 = false;
                            }
                            i11 = i3 + 1;
                            str4 = str2;
                            intArray3 = intArray;
                            remoteAnimationTargetArr3 = remoteAnimationTargetArr2;
                            arrayList6 = arrayList;
                        }
                        RemoteAnimationTarget[] remoteAnimationTargetArr4 = remoteAnimationTargetArr3;
                        str = str4;
                        i = 1;
                        if (!z8) {
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
                        for (int i15 = 0; i15 < transitionInfo3.getChanges().size(); i15++) {
                            TransitionInfo.Change change6 = (TransitionInfo.Change) transitionInfo3.getChanges().get(i15);
                            if (TransitionUtil.isOpeningType(change6.getMode())) {
                                transaction.show(change6.getLeash());
                                transaction.setAlpha(change6.getLeash(), 1.0f);
                            } else if (TransitionUtil.isClosingType(change6.getMode())) {
                                transaction.hide(change6.getLeash());
                            }
                        }
                    } else if (!z2) {
                        StringBuilder m2 = RowView$$ExternalSyntheticOutline0.m("Don't know how to merge this transition, foundRecentsClosing=", " recentsTaskId=", z4);
                        m2.append(this.mRecentsTaskId);
                        Slog.w(str, m2.toString());
                        if (z4 || this.mRecentsTaskId < 0) {
                            this.mWillFinishToHome = false;
                            cancel("didn't merge", false, false);
                            return;
                        }
                        if (CoreRune.MW_SHELL_TRANSITION) {
                            if (CoreRune.MW_PIP_SHELL_TRANSITION && transitionInfo3.getType() == 1003) {
                                Slog.d(str, "isAllowedToMergeTransition: reason=remove_pip");
                            } else {
                                if (CoreRune.MW_SPLIT_SHELL_TRANSITION && transitionInfo3.getType() == 6) {
                                    while (i2 < transitionInfo3.getChanges().size()) {
                                        TransitionInfo.Change change7 = (TransitionInfo.Change) transitionInfo3.getChanges().get(i2);
                                        i2 = (change7.getMode() == 6 && (taskInfo = change7.getTaskInfo()) != null && taskInfo.getWindowingMode() == 6 && taskInfo.getConfiguration().windowConfiguration.getStageType() != 0) ? i2 + 1 : 0;
                                    }
                                    Slog.d(str, "When only split change, merge");
                                    Slog.d(str, "isAllowedToMergeTransition: reason=split_change_only");
                                }
                                if (!CoreRune.MW_FREEFORM_SHELL_TRANSITION || transitionInfo3.getType() != 4 || !transitionInfo3.getChanges().stream().allMatch(new RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda0(1))) {
                                    return;
                                } else {
                                    Slog.d(str, "isAllowedToMergeTransition: reason=freeform_minimize_only");
                                }
                            }
                            this.mMergedTransitionInfo = transitionInfo3;
                            transaction.apply();
                            transitionFinishCallback.onTransitionFinished(null);
                            return;
                        }
                        return;
                    }
                    boolean z14 = CoreRune.FW_SHELL_TRANSITION_RECENTS_BUG_FIX;
                    if (z14) {
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
                    if (z14 && this.mListener == null) {
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 1985428988526997718L, 1, Long.valueOf(this.mInstanceId));
                            return;
                        }
                        return;
                    }
                    boolean isTrue = desktopModeFlags.isTrue();
                    if (remoteAnimationTargetArr != null) {
                        if (CoreRune.MW_MULTI_SPLIT_BACKGROUND && iBinder != null && (recentsTransitionCallback = RecentsTransitionHandler.this.mTransitions.mRecentTransitionCallback) != null) {
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
                                        int findRootIndex = transitionInfo5.findRootIndex(stageCoordinator.mDisplayId);
                                        if (findRootIndex >= 0) {
                                            transitionInfo2 = null;
                                            stageCoordinator.mSplitBackgroundController.reparentToLeash(null, transitionInfo5.getRoot(findRootIndex).getLeash(), true);
                                        }
                                    } else {
                                        length--;
                                    }
                                }
                            }
                        }
                        transitionInfo2 = null;
                        try {
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 833931798120511255L, 1, Long.valueOf(this.mInstanceId));
                            }
                            IRecentsAnimationRunner iRecentsAnimationRunner = this.mListener;
                            if (isTrue) {
                                transitionInfo2 = transitionInfo3;
                            }
                            IRecentsAnimationRunner$Stub$Proxy iRecentsAnimationRunner$Stub$Proxy = (IRecentsAnimationRunner$Stub$Proxy) iRecentsAnimationRunner;
                            Parcel obtain = Parcel.obtain(iRecentsAnimationRunner$Stub$Proxy.mRemote);
                            try {
                                obtain.writeInterfaceToken("com.android.wm.shell.recents.IRecentsAnimationRunner");
                                obtain.writeTypedArray(remoteAnimationTargetArr, 0);
                                obtain.writeTypedObject(transitionInfo2, 0);
                                iRecentsAnimationRunner$Stub$Proxy.mRemote.transact(4, obtain, null, 1);
                                obtain.recycle();
                                if (CoreRune.FW_SHELL_TRANSITION_MERGE && CoreRune.FW_WORKAROUND_RESPONSE_SPEED) {
                                    updateActiveRecents(transitionInfo3);
                                    return;
                                }
                                return;
                            } catch (Throwable th) {
                                obtain.recycle();
                                throw th;
                            }
                        } catch (RemoteException e) {
                            Slog.e(str, "Error sending appeared tasks to recents animation", e);
                            return;
                        }
                    }
                    return;
                }
                TransitionInfo.Change change8 = (TransitionInfo.Change) transitionInfo3.getChanges().get(i7);
                taskInfo2 = change8.getTaskInfo();
                boolean z15 = z6;
                if (taskInfo2 != null && taskInfo2.configuration.windowConfiguration.isAlwaysOnTop()) {
                    if (!CoreRune.MW_SHELL_TRANSITION || (taskInfo3 = change8.getTaskInfo()) == null) {
                        break;
                    }
                    if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
                        runningTaskInfo = taskInfo3;
                        i6 = i7;
                        if (change8.getMinimizeAnimState() == 2) {
                            break;
                        }
                    } else {
                        runningTaskInfo = taskInfo3;
                        i6 = i7;
                    }
                    if (runningTaskInfo.getWindowingMode() != 6 && (!CoreRune.MW_FREEFORM_SHELL_TRANSITION || !runningTaskInfo.isFreeform())) {
                        break;
                    }
                    if (!z15) {
                        return;
                    }
                    Slog.w("RecentsTransitionHandler", "merge: skip handling always-on-top task " + change8 + ", reason=display_change");
                } else {
                    i6 = i7;
                    if (TransitionUtil.isClosingType(change8.getMode()) && taskInfo2 != null && taskInfo2.lastParentTaskIdBeforePip > 0) {
                        cancel(ReorderTile$$ExternalSyntheticOutline0.m(taskInfo2.taskId, " is removed with its original parent", new StringBuilder("task #")), false, false);
                        return;
                    }
                    boolean z16 = taskInfo2 != null && TransitionInfo.isIndependent(change8, transitionInfo3);
                    WindowContainerToken windowContainerToken = this.mRecentsTask;
                    boolean z17 = windowContainerToken != null && windowContainerToken.equals(change8.getContainer());
                    z5 = z5 || z16;
                    boolean test = leafTaskFilter.test(change8);
                    if (TransitionUtil.isOpeningType(change8.getMode()) || TransitionUtil.isOrderOnly(change8)) {
                        arrayList2 = arrayList5;
                        c = ' ';
                        if (z17) {
                            change3 = change8;
                        } else if (z16 || test) {
                            if (test && taskInfo2.topActivityType == 2) {
                                this.mOpeningSeparateHome = true;
                            }
                            if (arrayList2 == null) {
                                ArrayList arrayList7 = new ArrayList();
                                intArray2 = new IntArray();
                                arrayList2 = arrayList7;
                            }
                            IntArray intArray4 = intArray2;
                            arrayList2.add(change8);
                            intArray4.add(test ? 1 : 0);
                            intArray2 = intArray4;
                        }
                    } else if (!TransitionUtil.isClosingType(change8.getMode())) {
                        if (change8.getMode() == 6) {
                            if (change8.hasFlags(32) && transitionInfo3.getType() == 6) {
                                this.mForceEnterPip = true;
                                cancel("display change", this.mWillFinishToHome, true);
                                return;
                            }
                            if (TransitionUtil.isOrderOnly(change8) || !test) {
                                arrayList2 = arrayList5;
                                if (test && taskInfo2.topActivityType == 2 && !z17) {
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
                                c = ' ';
                            } else {
                                if ((change8.getFlags() & 1048576) == 0 || taskInfo2 == null || taskInfo2.getWindowingMode() != 1) {
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
                        arrayList2 = arrayList5;
                        c = ' ';
                    } else if (z17) {
                        z4 = true;
                    } else {
                        if (z16 || test) {
                            if (arrayList4 == null) {
                                arrayList4 = new ArrayList();
                            }
                            arrayList4.add(change8);
                        }
                        arrayList2 = arrayList5;
                        c = ' ';
                    }
                    arrayList5 = arrayList2;
                    i7 = i6 + 1;
                    z6 = z15;
                }
                c = ' ';
                i7 = i6 + 1;
                z6 = z15;
            }
            cancel(ReorderTile$$ExternalSyntheticOutline0.m(taskInfo2.taskId, " is always_on_top", new StringBuilder("task #")), false, false);
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

        public final void cancel(String str, boolean z, boolean z2) {
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
                    Pair pair = this.mPendingPauseSnapshotsForCancel;
                    if (pair == null) {
                        pair = getSnapshotsForPausingTasks();
                    }
                    sendCancel((int[]) pair.first, (TaskSnapshot[]) pair.second);
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            if (!((TransitionInfo.Change) transitionInfo.getChanges().get(i)).hasFlags(33554432)) {
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
        RecentsController recentsController = (RecentsController) AlertController$$ExternalSyntheticOutline0.m(this.mControllers, 1);
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
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        RecentsController findController = findController(iBinder2);
        if (findController == null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -9101066424534024548L, 0, null);
            }
        } else if (CoreRune.MW_MULTI_SPLIT_BACKGROUND || CoreRune.FW_SHELL_TRANSITION_RECENTS_BUG_FIX) {
            findController.merge(iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback);
        } else {
            findController.merge(null, transitionInfo, transaction, transaction2, transitionFinishCallback);
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
        for (int size = this.mControllers.size() - 1; size >= 0; size--) {
            ((RecentsController) this.mControllers.get(size)).cancel("onTransitionConsumed");
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        RecentsController findController = findController(SYNTHETIC_TRANSITION);
        if (findController != null) {
            findController.cancel("incoming_transition");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r26v2 */
    /* JADX WARN: Type inference failed for: r26v3 */
    /* JADX WARN: Type inference failed for: r26v9 */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        Transitions.TransitionHandler transitionHandler;
        RecentsController recentsController;
        int i;
        int i2;
        int i3;
        ArrayList arrayList;
        TransitionUtil.LeafTaskFilter leafTaskFilter;
        RemoteAnimationTarget newTarget;
        int i4;
        boolean z;
        int i5;
        ArrayList arrayList2;
        SurfaceControl surfaceControl;
        boolean z2;
        final TransitionInfo transitionInfo2 = transitionInfo;
        final SurfaceControl.Transaction transaction3 = transaction;
        final RecentsController findController = findController(iBinder);
        int i6 = 0;
        boolean z3 = true;
        if (findController == null) {
            if (!ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                return false;
            }
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 9121280089055901232L, 0, null);
            return false;
        }
        IApplicationThread iApplicationThread = this.mAnimApp;
        this.mAnimApp = null;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 1280866729087811123L, 1, Long.valueOf(findController.mInstanceId));
        }
        findController.mForceEnterPip = false;
        if (findController.mListener == null || findController.mTransition == null) {
            StringBuilder sb = new StringBuilder("Missing listener or transition, hasListener=");
            sb.append(findController.mListener != null);
            sb.append(" hasTransition=");
            sb.append(findController.mTransition != null);
            Slog.e("RecentsTransitionHandler", sb.toString());
            StringBuilder sb2 = new StringBuilder("No listener (");
            sb2.append(findController.mListener == null);
            sb2.append(") or no transition (");
            findController.cancel(MoveResult$$ExternalSyntheticOutline0.m(sb2, findController.mTransition == null, ")"));
        } else {
            SurfaceControl surfaceControl2 = null;
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
                        findController.mRecentsTask = taskInfo.token;
                        findController.mRecentsTaskId = taskInfo.taskId;
                        if (CoreRune.FW_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && surfaceControl2 == null) {
                            surfaceControl2 = change.getLeash();
                        }
                        if (CoreRune.FW_SHELL_TRANSITION_RECENTS_BUG_FIX) {
                            findController.mRecentsTaskState = new TaskState(change, null);
                        }
                    } else if (taskInfo != null && taskInfo.topActivityType == 2) {
                        findController.mRecentsTask = taskInfo.token;
                        findController.mRecentsTaskId = taskInfo.taskId;
                        if (CoreRune.FW_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && surfaceControl2 == null) {
                            surfaceControl2 = change.getLeash();
                        }
                        if (CoreRune.FW_SHELL_TRANSITION_RECENTS_BUG_FIX) {
                            findController.mRecentsTaskState = new TaskState(change, null);
                        }
                    }
                }
                i7++;
                z3 = z2;
            }
            boolean z5 = z3;
            if (findController.mRecentsTask != null || z4) {
                if (CoreRune.FW_SHELL_TRANSITION_RECENTS_BUG_FIX) {
                    findController.mFinishTransactions.add(transaction2);
                }
                findController.mInfo = transitionInfo2;
                findController.mFinishCB = transitionFinishCallback;
                findController.mFinishTransaction = transaction2;
                findController.mPausingTasks = new ArrayList();
                findController.mClosingTasks = new ArrayList();
                findController.mOpeningTasks = new ArrayList();
                findController.mLeashMap = new ArrayMap();
                findController.mKeyguardLocked = (transitionInfo2.getFlags() & 64) != 0 ? z5 ? 1 : 0 : false;
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
                            RecentsTransitionHandler.RecentsController recentsController2 = RecentsTransitionHandler.RecentsController.this;
                            SurfaceControl.Transaction transaction4 = transaction3;
                            int i8 = size2;
                            SurfaceControl surfaceControl3 = (SurfaceControl) obj;
                            if (RecentsTransitionHandler.this.mBackgroundColor == null) {
                                return;
                            }
                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 812426231714299700L, 1, Long.valueOf(i8));
                            }
                            SurfaceControl build = new SurfaceControl.Builder().setName("recents_background").setColorLayer().setOpaque(true).setParent(surfaceControl3).build();
                            Color color = RecentsTransitionHandler.this.mBackgroundColor;
                            transaction4.setColor(build, new float[]{color.red(), color.green(), color.blue()});
                            transaction4.setLayer(build, i8);
                            transaction4.setAlpha(build, 1.0f);
                            transaction4.show(build);
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
                            RemoteAnimationTarget newTarget2 = TransitionUtil.newTarget(change2, size - i9, false, transitionInfo2, transaction3, findController.mLeashMap);
                            arrayList5.add(newTarget2);
                            transaction3.setAlpha(newTarget2.leash, 1.0f);
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
                                    ArrayMap arrayMap = findController.mLeashMap;
                                    ArrayMap arrayMap2 = findController.mTransferLeashMap;
                                    if (change2.getTaskInfo() == null || arrayMap2 == null || arrayMap2.isEmpty()) {
                                        arrayList2 = arrayList6;
                                        surfaceControl = null;
                                    } else {
                                        Iterator it = arrayMap2.entrySet().iterator();
                                        while (true) {
                                            if (!it.hasNext()) {
                                                arrayList2 = arrayList6;
                                                surfaceControl = null;
                                                break;
                                            }
                                            SurfaceControl surfaceControl3 = (SurfaceControl) ((Map.Entry) it.next()).getKey();
                                            arrayList2 = arrayList6;
                                            if (surfaceControl3.isSameSurface(change2.getLeash())) {
                                                surfaceControl = (SurfaceControl) arrayMap2.get(surfaceControl3);
                                                break;
                                            }
                                            arrayList6 = arrayList2;
                                        }
                                        if (surfaceControl != null) {
                                            transaction3.reparent(surfaceControl, transitionInfo2.getRoot(TransitionUtil.rootIndexFor(change2, transitionInfo2)).getLeash());
                                            int size4 = transitionInfo2.getChanges().size() - i10;
                                            boolean isOpeningType = TransitionUtil.isOpeningType(transitionInfo2.getType());
                                            int size5 = transitionInfo2.getChanges().size();
                                            int mode = change2.getMode();
                                            transaction3.reparent(surfaceControl, transitionInfo2.getRoot(TransitionUtil.rootIndexFor(change2, transitionInfo2)).getLeash());
                                            if (TransitionUtil.isOpeningType(mode)) {
                                                if (isOpeningType) {
                                                    transaction3.setLayer(surfaceControl, (transitionInfo2.getChanges().size() + size5) - size4);
                                                } else {
                                                    transaction3.setLayer(surfaceControl, size5 - size4);
                                                }
                                            } else if (!TransitionUtil.isClosingType(mode)) {
                                                transaction3.setLayer(surfaceControl, (transitionInfo2.getChanges().size() + size5) - size4);
                                            } else if (isOpeningType) {
                                                transaction3.setLayer(surfaceControl, size5 - size4);
                                            } else {
                                                transaction3.setLayer(surfaceControl, (transitionInfo2.getChanges().size() + size5) - size4);
                                            }
                                            transaction3.reparent(change2.getLeash(), surfaceControl);
                                            transaction3.setAlpha(change2.getLeash(), 1.0f);
                                            transaction3.show(change2.getLeash());
                                            transaction3.setPosition(change2.getLeash(), 0.0f, 0.0f);
                                            transaction3.setLayer(change2.getLeash(), 0);
                                        }
                                    }
                                    if (surfaceControl == null) {
                                        surfaceControl = TransitionUtil.createLeash(transitionInfo2, change2, i10, transaction3);
                                    }
                                    if (arrayMap != null) {
                                        arrayMap.put(change2.getLeash(), surfaceControl);
                                    }
                                    newTarget = TransitionUtil.newTarget(change2, i10, surfaceControl, false);
                                    arrayList = arrayList2;
                                } else {
                                    arrayList = arrayList6;
                                    newTarget = TransitionUtil.newTarget(change2, size - i2, false, transitionInfo2, transaction3, findController.mLeashMap);
                                }
                                arrayList3.add(newTarget);
                                if (TransitionUtil.isClosingType(change2.getMode())) {
                                    findController.mPausingTasks.add(new TaskState(change2, newTarget.leash));
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
                                        findController.mPausingSeparateHome = z;
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
                                        transaction3.setLayer(newTarget.leash, i12);
                                    }
                                    PictureInPictureParams pictureInPictureParams = taskInfo2.pictureInPictureParams;
                                    if (pictureInPictureParams != null && pictureInPictureParams.isAutoEnterEnabled()) {
                                        findController.mPipTask = taskInfo2.token;
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
                                        transaction3.setLayer(newTarget.leash, i13);
                                    } else if (taskInfo2 == null || taskInfo2.topActivityType != 2) {
                                        if (TransitionUtil.isOpeningType(change2.getMode())) {
                                            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 5439651840349387650L, 1, Long.valueOf(taskInfo2.taskId));
                                            }
                                            findController.mOpeningTasks.add(new TaskState(change2, newTarget.leash));
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
                                        arrayList3.add(TransitionUtil.newTarget(change2, size - i2, false, transitionInfo2, transaction3, findController.mLeashMap));
                                    } else if (CoreRune.FW_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && TransitionUtil.isTransientLaunchOverlay(change2)) {
                                        RemoteAnimationTarget newTarget3 = TransitionUtil.newTarget(change2, size - i2, false, transitionInfo, transaction3, findController.mLeashMap);
                                        SurfaceControl surfaceControl4 = newTarget3.leash;
                                        if (surfaceControl4 != null && surfaceControl2 != null) {
                                            transaction3.setRelativeLayer(surfaceControl4, surfaceControl2, 1);
                                        }
                                        arrayList3.add(newTarget3);
                                    } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -8477821708665405557L, 1, Long.valueOf(taskInfo2 != null ? taskInfo2.taskId : -1L));
                                    }
                                } else if (TransitionUtil.isClosingType(change2.getMode())) {
                                    int i14 = size3 - i2;
                                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -3712724663189422270L, 5, Long.valueOf(taskInfo2.taskId), Long.valueOf(i14));
                                    }
                                    transaction3.setLayer(change2.getLeash(), i14);
                                    findController.mPausingTasks.add(new TaskState(change2, null));
                                } else if (TransitionUtil.isOpeningType(change2.getMode())) {
                                    int i15 = size - i2;
                                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -1232595227583365651L, 5, Long.valueOf(taskInfo2.taskId), Long.valueOf(i15));
                                    }
                                    transaction3.setLayer(change2.getLeash(), i15);
                                    findController.mOpeningTasks.add(new TaskState(change2, null));
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
                IBinder iBinder2 = findController.mTransition;
                ArrayList arrayList8 = transitions.mHandlers;
                int size6 = arrayList8.size();
                int i17 = 0;
                while (true) {
                    if (i17 >= size6) {
                        transitionHandler = null;
                        break;
                    }
                    Object obj = arrayList8.get(i17);
                    i17++;
                    transitionHandler = ((Transitions.TransitionHandler) obj).getHandlerForTakeover(iBinder2, transitionInfo2);
                    if (transitionHandler != null) {
                        break;
                    }
                }
                findController.mTakeoverHandler = transitionHandler;
                Bundle bundle = new Bundle(2);
                bundle.putParcelable("key_SplitBounds", RecentsTransitionHandler.this.mRecentTasksController.getSplitBoundsForTaskId(i16));
                bundle.putBoolean("extra_shell_can_hand_off_animation", findController.mTakeoverHandler != null);
                try {
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 4637593205120250810L, 5, Long.valueOf(findController.mInstanceId), Long.valueOf(arrayList3.size()));
                    }
                    recentsController = findController;
                } catch (RemoteException e) {
                    e = e;
                    recentsController = findController;
                }
                try {
                    ((IRecentsAnimationRunner$Stub$Proxy) findController.mListener).onAnimationStart(recentsController, (RemoteAnimationTarget[]) arrayList3.toArray(new RemoteAnimationTarget[arrayList3.size()]), (RemoteAnimationTarget[]) arrayList7.toArray(new RemoteAnimationTarget[arrayList7.size()]), new Rect(0, 0, 0, 0), new Rect(), bundle, transitionInfo);
                    for (int i18 = 0; i18 < RecentsTransitionHandler.this.mStateListeners.size(); i18++) {
                        ((RecentsTransitionStateListener) RecentsTransitionHandler.this.mStateListeners.get(i18)).onTransitionStateChanged(3);
                    }
                } catch (RemoteException e2) {
                    e = e2;
                    Slog.e("RecentsTransitionHandler", "Error starting recents animation", e);
                    recentsController.cancel("onAnimationStart() failed");
                    Transitions.setRunningRemoteTransitionDelegate(iApplicationThread);
                    return true;
                }
                Transitions.setRunningRemoteTransitionDelegate(iApplicationThread);
                return true;
            }
            Slog.e("RecentsTransitionHandler", "Tried to start recents while it is already running.");
            findController.cancel("No recents task and no pausing tasks");
        }
        if (!ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
            return false;
        }
        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -8594519572043583178L, 0, null);
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x01a4 A[LOOP:2: B:44:0x014b->B:53:0x01a4, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x019a A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.os.IBinder startRecentsTransition(android.app.PendingIntent r17, android.content.Intent r18, android.os.Bundle r19, android.app.IApplicationThread r20, com.android.wm.shell.recents.IRecentsAnimationRunner r21) {
        /*
            Method dump skipped, instructions count: 492
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.recents.RecentsTransitionHandler.startRecentsTransition(android.app.PendingIntent, android.content.Intent, android.os.Bundle, android.app.IApplicationThread, com.android.wm.shell.recents.IRecentsAnimationRunner):android.os.IBinder");
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void transferAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, WindowContainerTransaction windowContainerTransaction) {
        if (windowContainerTransaction == null) {
            return;
        }
        RecentsController findController = findController(iBinder);
        if (findController == null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 662544798542868933L, 0, null);
                return;
            }
            return;
        }
        if (windowContainerTransaction.getTransferLeashMap().isEmpty()) {
            return;
        }
        findController.mTransferLeashMap = windowContainerTransaction.getTransferLeashMap();
        int findRootIndex = transitionInfo.findRootIndex(0);
        if (findRootIndex < 0) {
            return;
        }
        SurfaceControl leash = transitionInfo.getRoot(findRootIndex).getLeash();
        for (int size = findController.mTransferLeashMap.size() - 1; size >= 0; size--) {
            SurfaceControl surfaceControl = (SurfaceControl) findController.mTransferLeashMap.valueAt(size);
            if (surfaceControl == null || !surfaceControl.isValid()) {
                findController.mTransferLeashMap.removeAt(size);
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
