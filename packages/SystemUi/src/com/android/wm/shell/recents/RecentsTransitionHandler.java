package com.android.wm.shell.recents;

import android.animation.Animator;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.IApplicationThread;
import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.IntArray;
import android.util.Pair;
import android.util.Slog;
import android.view.IRecentsAnimationController;
import android.view.IRecentsAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.window.PictureInPictureSurfaceTransaction;
import android.window.TaskSnapshot;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.wm.shell.recents.RecentsTransitionHandler;
import com.android.wm.shell.splitscreen.SplitBackgroundController;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.DefaultMixedHandler;
import com.android.wm.shell.transition.MultiTaskingTransitionProvider;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.util.TransitionUtil;
import com.android.systemui.R;
import com.android.systemui.keyguard.KeyguardService$$ExternalSyntheticOutline0;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RecentsTransitionHandler implements Transitions.TransitionHandler {
    public final ShellExecutor mExecutor;
    public final MultiTaskingTransitionProvider mMultiTaskingTransitions;
    public final Transitions mTransitions;
    public IApplicationThread mAnimApp = null;
    public final ArrayList mControllers = new ArrayList();
    public final ArrayList mStateListeners = new ArrayList();
    public final ArrayList mForceHidingAnimators = new ArrayList();
    public final ArrayList mMixers = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RecentsController extends IRecentsAnimationController.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public boolean mIsDisplayChangeOnStart;
        public IRecentsAnimationRunner mListener;
        public Pair mPendingPauseSnapshotsForCancel;
        public Transitions.TransitionFinishCallback mFinishCB = null;
        public SurfaceControl.Transaction mFinishTransaction = null;
        public ArrayList mPausingTasks = null;
        public ArrayList mOpeningTasks = null;
        public WindowContainerToken mPipTask = null;
        public WindowContainerToken mRecentsTask = null;
        public TransitionInfo mInfo = null;
        public boolean mOpeningSeparateHome = false;
        public boolean mPausingSeparateHome = false;
        public ArrayMap mLeashMap = null;
        public PictureInPictureSurfaceTransaction mPipTransaction = null;
        public IBinder mTransition = null;
        public boolean mKeyguardLocked = false;
        public boolean mWillFinishToHome = false;
        public int mState = 0;
        public ArrayMap mTransferLeashMap = null;
        public final int mInstanceId = System.identityHashCode(this);
        public C4087x1263b67c mDeathHandler = new IBinder.DeathRecipient() { // from class: com.android.wm.shell.recents.RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda0
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                RecentsTransitionHandler.RecentsController recentsController = RecentsTransitionHandler.RecentsController.this;
                recentsController.getClass();
                if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 690469724, 1, "[%d] RecentsController.DeathRecipient: binder died", Long.valueOf(recentsController.mInstanceId));
                }
                recentsController.finish(recentsController.mWillFinishToHome, false);
            }
        };

        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.wm.shell.recents.RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda0] */
        public RecentsController(IRecentsAnimationRunner iRecentsAnimationRunner) {
            this.mListener = iRecentsAnimationRunner;
            try {
                iRecentsAnimationRunner.asBinder().linkToDeath(this.mDeathHandler, 0);
            } catch (RemoteException e) {
                Slog.e("RecentsTransitionHandler", "RecentsController: failed to link to death", e);
                this.mListener = null;
            }
        }

        public final void cancel(String str) {
            cancel(str, true, false);
        }

        public final void cleanUp() {
            if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -358280047, 1, "[%d] RecentsController.cleanup", Long.valueOf(this.mInstanceId));
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
            this.mFinishTransaction = null;
            this.mPausingTasks = null;
            this.mOpeningTasks = null;
            this.mInfo = null;
            this.mTransition = null;
            this.mPendingPauseSnapshotsForCancel = null;
            RecentsTransitionHandler.this.mControllers.remove(this);
            for (int i2 = 0; i2 < RecentsTransitionHandler.this.mStateListeners.size(); i2++) {
                ((RecentsTransitionStateListener) RecentsTransitionHandler.this.mStateListeners.get(i2)).onAnimationStateChanged(false);
            }
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE_TRANSFER && this.mTransferLeashMap != null) {
                for (int i3 = 0; i3 < this.mTransferLeashMap.size(); i3++) {
                    ((SurfaceControl) this.mTransferLeashMap.valueAt(i3)).release();
                }
                this.mTransferLeashMap = null;
            }
            if (CoreRune.FW_CUSTOM_SHELL_RECENTS_TRANSITION_WITH_DISPLAY_CHANGE) {
                this.mIsDisplayChangeOnStart = false;
            }
        }

        public final void detachNavigationBarFromApp(boolean z) {
            if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 1695981058, 1, "[%d] RecentsController.detachNavigationBarFromApp", Long.valueOf(this.mInstanceId));
            }
            ((HandlerExecutor) RecentsTransitionHandler.this.mExecutor).execute(new RecentsTransitionHandler$$ExternalSyntheticLambda1(this, 1));
        }

        public final void finish(final boolean z, final boolean z2) {
            ((HandlerExecutor) RecentsTransitionHandler.this.mExecutor).execute(new Runnable() { // from class: com.android.wm.shell.recents.RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    RecentsTransitionHandler.RecentsController.this.finishInner(z, z2);
                }
            });
        }

        public final void finishInner(boolean z, boolean z2) {
            WindowContainerToken windowContainerToken;
            WindowContainerToken windowContainerToken2;
            WindowContainerToken windowContainerToken3;
            StageCoordinator.RecentsTransitionCallback recentsTransitionCallback;
            if (this.mFinishCB == null) {
                Slog.e("RecentsTransitionHandler", "Duplicate call to finish");
                return;
            }
            if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -440544752, 509, "[%d] RecentsController.finishInner: toHome=%b userLeave=%b willFinishToHome=%b state=%d", Long.valueOf(this.mInstanceId), Boolean.valueOf(z), Boolean.valueOf(z2), Boolean.valueOf(this.mWillFinishToHome), Long.valueOf(this.mState));
            }
            Transitions.TransitionFinishCallback transitionFinishCallback = this.mFinishCB;
            this.mFinishCB = null;
            SurfaceControl.Transaction transaction = this.mFinishTransaction;
            WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
            if (CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION) {
                RecentsTransitionHandler recentsTransitionHandler = RecentsTransitionHandler.this;
                ArrayList arrayList = recentsTransitionHandler.mForceHidingAnimators;
                if (!arrayList.isEmpty()) {
                    Slog.d("RecentsTransitionHandler", "cancelForceHideAnimationsIfNeeded: animators=" + new ArrayList(arrayList) + ", Callers=" + Debug.getCallers(5));
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ((HandlerExecutor) recentsTransitionHandler.mTransitions.mAnimExecutor).execute(new RecentsTransitionHandler$$ExternalSyntheticLambda1((Animator) it.next(), 0));
                    }
                }
            }
            if (CoreRune.MW_MULTI_SPLIT_BACKGROUND && (recentsTransitionCallback = RecentsTransitionHandler.this.mTransitions.mRecentTransitionCallback) != null) {
                StageCoordinator stageCoordinator = StageCoordinator.this;
                SplitBackgroundController splitBackgroundController = stageCoordinator.mSplitBackgroundController;
                if (splitBackgroundController.mReparentedToTransitionRoot) {
                    splitBackgroundController.reparentToLeash(stageCoordinator.mRootTaskLeash, false, transaction);
                }
            }
            if (this.mKeyguardLocked && (windowContainerToken3 = this.mRecentsTask) != null) {
                if (z) {
                    windowContainerTransaction.reorder(windowContainerToken3, true);
                } else {
                    windowContainerTransaction.restoreTransientOrder(windowContainerToken3);
                }
            }
            if (!z && ((!this.mWillFinishToHome || this.mPausingSeparateHome) && this.mPausingTasks != null && this.mState == 0)) {
                if (this.mPausingSeparateHome) {
                    if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                        ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -1664932337, 0, "  returning to 3p home", null);
                    }
                } else if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 25777518, 0, "  returning to app", null);
                }
                for (int size = this.mPausingTasks.size() - 1; size >= 0; size--) {
                    windowContainerTransaction.reorder(((TaskState) this.mPausingTasks.get(size)).mToken, true);
                    transaction.show(((TaskState) this.mPausingTasks.get(size)).mTaskSurface);
                }
                if (!this.mKeyguardLocked && (windowContainerToken2 = this.mRecentsTask) != null) {
                    windowContainerTransaction.restoreTransientOrder(windowContainerToken2);
                }
            } else if (z && this.mOpeningSeparateHome && this.mPausingTasks != null) {
                if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 453029459, 0, "  3p launching home", null);
                }
                for (int i = 0; i < this.mOpeningTasks.size(); i++) {
                    TaskState taskState = (TaskState) this.mOpeningTasks.get(i);
                    if (taskState.mTaskInfo.topActivityType == 2) {
                        windowContainerTransaction.reorder(taskState.mToken, true);
                    }
                    transaction.show(taskState.mTaskSurface);
                }
                for (int size2 = this.mPausingTasks.size() - 1; size2 >= 0; size2--) {
                    transaction.hide(((TaskState) this.mPausingTasks.get(size2)).mTaskSurface);
                }
                if (!this.mKeyguardLocked && (windowContainerToken = this.mRecentsTask) != null) {
                    windowContainerTransaction.restoreTransientOrder(windowContainerToken);
                }
            } else {
                if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -223019508, 0, "  normal finish", null);
                }
                for (int i2 = 0; i2 < this.mOpeningTasks.size(); i2++) {
                    transaction.show(((TaskState) this.mOpeningTasks.get(i2)).mTaskSurface);
                }
                for (int i3 = 0; i3 < this.mPausingTasks.size(); i3++) {
                    if (!z2) {
                        if (((TaskState) this.mPausingTasks.get(i3)).mLeash != null) {
                            windowContainerTransaction.setDoNotPip(((TaskState) this.mPausingTasks.get(i3)).mToken);
                        }
                    }
                    transaction.hide(((TaskState) this.mPausingTasks.get(i3)).mTaskSurface);
                }
                WindowContainerToken windowContainerToken4 = this.mPipTask;
                if (windowContainerToken4 != null && this.mPipTransaction != null && z2) {
                    transaction.show(this.mInfo.getChange(windowContainerToken4).getLeash());
                    PictureInPictureSurfaceTransaction.apply(this.mPipTransaction, this.mInfo.getChange(this.mPipTask).getLeash(), transaction);
                    this.mPipTask = null;
                    this.mPipTransaction = null;
                }
            }
            cleanUp();
            if (windowContainerTransaction.isEmpty()) {
                windowContainerTransaction = null;
            }
            transitionFinishCallback.onTransitionFinished(windowContainerTransaction, null);
        }

        public final Pair getSnapshotsForPausingTasks() {
            int[] iArr;
            TaskSnapshot[] taskSnapshotArr;
            ArrayList arrayList = this.mPausingTasks;
            if (arrayList != null && arrayList.size() > 0) {
                iArr = new int[this.mPausingTasks.size()];
                taskSnapshotArr = new TaskSnapshot[this.mPausingTasks.size()];
                for (int i = 0; i < this.mPausingTasks.size(); i++) {
                    try {
                        TaskState taskState = (TaskState) this.mPausingTasks.get(0);
                        if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -917088632, 5, "[%d] RecentsController.sendCancel: Snapshotting task=%d", Long.valueOf(this.mInstanceId), Long.valueOf(taskState.mTaskInfo.taskId));
                        }
                        taskSnapshotArr[i] = ActivityTaskManager.getService().takeTaskSnapshot(taskState.mTaskInfo.taskId, true);
                    } catch (RemoteException unused) {
                    }
                }
                return new Pair(iArr, taskSnapshotArr);
            }
            iArr = null;
            taskSnapshotArr = null;
            return new Pair(iArr, taskSnapshotArr);
        }

        public final void merge(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, Transitions.TransitionFinishCallback transitionFinishCallback, IBinder iBinder) {
            SurfaceControl.Transaction transaction2;
            String str;
            boolean z;
            RemoteAnimationTarget[] remoteAnimationTargetArr;
            boolean z2;
            ActivityManager.RunningTaskInfo taskInfo;
            StageCoordinator.RecentsTransitionCallback recentsTransitionCallback;
            boolean z3;
            int findRootIndex;
            boolean z4;
            String str2;
            ArrayList arrayList;
            IntArray intArray;
            int i;
            String str3;
            boolean z5;
            IntArray intArray2;
            TransitionInfo transitionInfo2 = transitionInfo;
            boolean z6 = true;
            if (this.mFinishCB == null) {
                if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -929009263, 1, "[%d] RecentsController.merge: skip, no finish callback", Long.valueOf(this.mInstanceId));
                    return;
                }
                return;
            }
            if (transitionInfo.getType() == 12) {
                if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -133189084, 1, "[%d] RecentsController.merge: transit_sleep", Long.valueOf(this.mInstanceId));
                }
                cancel("transit_sleep");
                return;
            }
            if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 2122319845, 1, "[%d] RecentsController.merge", Long.valueOf(this.mInstanceId));
            }
            int i2 = 0;
            this.mOpeningSeparateHome = false;
            TransitionUtil.LeafTaskFilter leafTaskFilter = new TransitionUtil.LeafTaskFilter();
            int m136m = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo2, 1);
            while (true) {
                if (m136m < 0) {
                    z6 = false;
                    break;
                }
                TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m);
                if (change.hasFlags(32) && change.getMode() == 6) {
                    break;
                } else {
                    m136m--;
                }
            }
            ArrayList arrayList2 = null;
            TransitionInfo.Change change2 = null;
            IntArray intArray3 = null;
            boolean z7 = false;
            boolean z8 = false;
            ArrayList arrayList3 = null;
            boolean z9 = false;
            while (i2 < transitionInfo.getChanges().size()) {
                TransitionInfo.Change change3 = (TransitionInfo.Change) transitionInfo.getChanges().get(i2);
                ActivityManager.RunningTaskInfo taskInfo2 = change3.getTaskInfo();
                IntArray intArray4 = intArray3;
                if (taskInfo2 == null || !taskInfo2.configuration.windowConfiguration.isAlwaysOnTop()) {
                    boolean z10 = taskInfo2 != null && TransitionInfo.isIndependent(change3, transitionInfo2);
                    WindowContainerToken windowContainerToken = this.mRecentsTask;
                    boolean z11 = windowContainerToken != null && windowContainerToken.equals(change3.getContainer());
                    z8 = z8 || z10;
                    boolean test = leafTaskFilter.test(change3);
                    if (!TransitionUtil.isOpeningType(change3.getMode())) {
                        if (!TransitionUtil.isClosingType(change3.getMode())) {
                            z5 = z6;
                            if (change3.getMode() == 6) {
                                if (change3.hasFlags(32) && transitionInfo.getType() == 6) {
                                    cancel("display change", this.mWillFinishToHome, true);
                                    return;
                                }
                                boolean z12 = CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && test && !TransitionUtil.isHomeOrRecents(change3) && TransitionUtil.isOrderOnly(change3) && TaskState.indexOf(this.mPausingTasks, change3) > 0;
                                if (!TransitionUtil.isOrderOnly(change3) && test) {
                                    z9 = true;
                                } else if (test && ((taskInfo2.topActivityType == 2 || (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && z12)) && !z11)) {
                                    if (arrayList3 == null) {
                                        arrayList3 = new ArrayList();
                                        intArray3 = new IntArray();
                                    } else {
                                        intArray3 = intArray4;
                                    }
                                    arrayList3.add(change3);
                                    intArray3.add(1);
                                    i2++;
                                    z6 = z5;
                                    z9 = z9;
                                }
                            }
                        } else if (z11) {
                            z7 = true;
                            z5 = z6;
                        } else if (z10 || test) {
                            if (arrayList2 == null) {
                                arrayList2 = new ArrayList();
                            }
                            arrayList2.add(change3);
                        }
                        intArray3 = intArray4;
                        i2++;
                        z6 = z5;
                        z9 = z9;
                    } else if (z11) {
                        z5 = z6;
                        change2 = change3;
                        intArray3 = intArray4;
                        i2++;
                        z6 = z5;
                        z9 = z9;
                    } else if (z10 || test) {
                        if (test && taskInfo2.topActivityType == 2) {
                            this.mOpeningSeparateHome = true;
                        }
                        if (arrayList3 == null) {
                            arrayList3 = new ArrayList();
                            intArray2 = new IntArray();
                        } else {
                            intArray2 = intArray4;
                        }
                        arrayList3.add(change3);
                        intArray2.add(test ? 1 : 0);
                        intArray3 = intArray2;
                        z5 = z6;
                        i2++;
                        z6 = z5;
                        z9 = z9;
                    }
                } else {
                    if (taskInfo2.configuration.windowConfiguration.getWindowingMode() == 2) {
                        cancel("task #" + taskInfo2.taskId + " is always_on_top");
                        return;
                    }
                    if (!z6) {
                        return;
                    }
                    Slog.w("RecentsTransitionHandler", "merge: skip handling always-on-top task " + change3 + ", reason=display_change");
                }
                intArray3 = intArray4;
                z5 = z6;
                i2++;
                z6 = z5;
                z9 = z9;
            }
            IntArray intArray5 = intArray3;
            if (z9 && z7) {
                sendCancelWithSnapshots();
                ((HandlerExecutor) RecentsTransitionHandler.this.mExecutor).executeDelayed(0L, new RecentsTransitionHandler$$ExternalSyntheticLambda1(this, 2));
                return;
            }
            if (change2 != null) {
                if (this.mState == 0) {
                    Slog.e("RecentsTransitionHandler", "Returning to recents while recents is already idle.");
                }
                if (arrayList2 == null || arrayList2.size() == 0) {
                    Slog.e("RecentsTransitionHandler", "Returning to recents without closing any opening tasks.");
                }
                transaction2 = transaction;
                transaction2.show(change2.getLeash());
                transaction2.setAlpha(change2.getLeash(), 1.0f);
                this.mState = 0;
            } else {
                transaction2 = transaction;
            }
            String str4 = "leaf ";
            String str5 = "";
            if (arrayList2 != null) {
                int i3 = 0;
                z = false;
                while (i3 < arrayList2.size()) {
                    TransitionInfo.Change change4 = (TransitionInfo.Change) arrayList2.get(i3);
                    int indexOf = TaskState.indexOf(this.mPausingTasks, change4);
                    if (indexOf >= 0) {
                        this.mPausingTasks.remove(indexOf);
                        if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 86925348, 1, "  closing pausing taskId=%d", Long.valueOf(change4.getTaskInfo().taskId));
                        }
                        str3 = str4;
                    } else {
                        int indexOf2 = TaskState.indexOf(this.mOpeningTasks, change4);
                        if (indexOf2 < 0) {
                            Slog.w("RecentsTransitionHandler", "Closing a task that wasn't opening, this may be split or something unexpected: " + change4.getTaskInfo().taskId);
                            str3 = str4;
                            i3++;
                            str4 = str3;
                        } else {
                            TaskState taskState = (TaskState) this.mOpeningTasks.remove(indexOf2);
                            if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                                str3 = str4;
                                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 2054709849, 4, "  pausing opening %staskId=%d", taskState.mLeash != null ? str4 : "", Long.valueOf(taskState.mTaskInfo.taskId));
                            } else {
                                str3 = str4;
                            }
                            this.mPausingTasks.add(taskState);
                        }
                    }
                    z = true;
                    i3++;
                    str4 = str3;
                }
                str = str4;
            } else {
                str = "leaf ";
                z = false;
            }
            if (arrayList3 == null || arrayList3.size() <= 0) {
                remoteAnimationTargetArr = null;
            } else {
                int size = this.mInfo.getChanges().size() * 3;
                int i4 = 0;
                for (int i5 = 0; i5 < intArray5.size(); i5++) {
                    i4 += intArray5.get(i5);
                }
                IntArray intArray6 = intArray5;
                remoteAnimationTargetArr = i4 > 0 ? new RemoteAnimationTarget[i4] : null;
                int i6 = 0;
                int i7 = 0;
                while (i6 < arrayList3.size()) {
                    TransitionInfo.Change change5 = (TransitionInfo.Change) arrayList3.get(i6);
                    boolean z13 = intArray6.get(i6) == 1;
                    int indexOf3 = TaskState.indexOf(this.mPausingTasks, change5);
                    if (indexOf3 >= 0) {
                        if (z13) {
                            str2 = str5;
                            arrayList = arrayList3;
                            remoteAnimationTargetArr[i7] = TransitionUtil.newTarget(change5, size, ((TaskState) this.mPausingTasks.get(indexOf3)).mLeash, false);
                            i7++;
                        } else {
                            str2 = str5;
                            arrayList = arrayList3;
                        }
                        TaskState taskState2 = (TaskState) this.mPausingTasks.remove(indexOf3);
                        if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                            intArray = intArray6;
                            i = i7;
                            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 482946393, 4, "  opening pausing %staskId=%d", z13 ? str : str2, Long.valueOf(taskState2.mTaskInfo.taskId));
                        } else {
                            i = i7;
                            intArray = intArray6;
                        }
                        this.mOpeningTasks.add(taskState2);
                        transaction2.show(change5.getLeash());
                        transaction2.setAlpha(change5.getLeash(), 1.0f);
                        i7 = i;
                    } else {
                        str2 = str5;
                        arrayList = arrayList3;
                        intArray = intArray6;
                        if (z13) {
                            RemoteAnimationTarget newTarget = TransitionUtil.newTarget(change5, size, transitionInfo2, transaction2, this.mLeashMap);
                            int i8 = i7 + 1;
                            remoteAnimationTargetArr[i7] = newTarget;
                            transaction2.reparent(newTarget.leash, this.mInfo.getRoot(TransitionUtil.rootIndexFor(change5, this.mInfo)).getLeash());
                            transaction2.setLayer(newTarget.leash, size);
                            transaction2.hide(newTarget.leash);
                            if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 101310246, 1, "  opening new leaf taskId=%d", Long.valueOf(newTarget.taskId));
                            }
                            this.mOpeningTasks.add(new TaskState(change5, newTarget.leash));
                            i7 = i8;
                        } else {
                            if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -252777164, 1, "  opening new taskId=%d", Long.valueOf(change5.getTaskInfo().taskId));
                            }
                            transaction2.setLayer(change5.getLeash(), size);
                            transaction2.show(change5.getLeash());
                            this.mOpeningTasks.add(new TaskState(change5, null));
                        }
                    }
                    i6++;
                    transitionInfo2 = transitionInfo;
                    intArray6 = intArray;
                    str5 = str2;
                    arrayList3 = arrayList;
                }
                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_LATE_TRANSIENT_LAUNCH) {
                    int i9 = 0;
                    while (true) {
                        if (i9 >= transitionInfo.getChanges().size()) {
                            z4 = false;
                            break;
                        }
                        TransitionInfo.Change change6 = (TransitionInfo.Change) transitionInfo.getChanges().get(i9);
                        if (TransitionUtil.isOpeningType(change6.getMode()) && change6.hasFlags(QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY)) {
                            z4 = true;
                            break;
                        }
                        i9++;
                    }
                    if (z4) {
                        if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -1778580792, 0, "  adding late transient launch", null);
                        }
                        z = true;
                    }
                }
                this.mState = 1;
                z = true;
            }
            if (this.mPausingTasks.isEmpty() && ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 8391559, 1, "[%d] RecentsController.merge: empty pausing tasks", Long.valueOf(this.mInstanceId));
            }
            if (!z8) {
                Slog.d("RecentsTransitionHandler", "Got an activity only transition during recents, so apply directly");
                for (int i10 = 0; i10 < transitionInfo.getChanges().size(); i10++) {
                    TransitionInfo.Change change7 = (TransitionInfo.Change) transitionInfo.getChanges().get(i10);
                    if (TransitionUtil.isOpeningType(change7.getMode())) {
                        transaction2.show(change7.getLeash());
                        transaction2.setAlpha(change7.getLeash(), 1.0f);
                    } else if (TransitionUtil.isClosingType(change7.getMode())) {
                        transaction2.hide(change7.getLeash());
                    }
                }
            } else if (!z) {
                Slog.w("RecentsTransitionHandler", "Don't know how to merge this transition, foundRecentsClosing=" + z7);
                if (z7) {
                    this.mWillFinishToHome = false;
                    cancel("didn't merge", false, false);
                    return;
                }
                if (CoreRune.MW_PIP_SHELL_TRANSITION && transitionInfo.getType() == 1003) {
                    transaction.apply();
                    transitionFinishCallback.onTransitionFinished(null, null);
                    return;
                }
                if (CoreRune.MW_SPLIT_SHELL_TRANSITION && transitionInfo.getType() == 6) {
                    for (int i11 = 0; i11 < transitionInfo.getChanges().size(); i11++) {
                        TransitionInfo.Change change8 = (TransitionInfo.Change) transitionInfo.getChanges().get(i11);
                        if (change8.getMode() != 6 || (taskInfo = change8.getTaskInfo()) == null || taskInfo.getWindowingMode() != 6 || taskInfo.getConfiguration().windowConfiguration.getStageType() == 0) {
                            z2 = false;
                            break;
                        }
                    }
                    Slog.d("RecentsTransitionHandler", "When only split change, merge");
                    z2 = true;
                    if (z2) {
                        transaction.apply();
                        transitionFinishCallback.onTransitionFinished(null, null);
                        return;
                    }
                    return;
                }
                return;
            }
            transaction.apply();
            transitionInfo.releaseAnimSurfaces();
            if (remoteAnimationTargetArr != null) {
                if (CoreRune.MW_MULTI_SPLIT_BACKGROUND && iBinder != null && (recentsTransitionCallback = RecentsTransitionHandler.this.mTransitions.mRecentTransitionCallback) != null) {
                    TransitionInfo transitionInfo3 = this.mInfo;
                    StageCoordinator stageCoordinator = StageCoordinator.this;
                    if (stageCoordinator.mSplitTransitions.isPendingEnter(iBinder)) {
                        int length = remoteAnimationTargetArr.length - 1;
                        while (true) {
                            if (length < 0) {
                                z3 = false;
                                break;
                            }
                            RemoteAnimationTarget remoteAnimationTarget = remoteAnimationTargetArr[length];
                            ActivityManager.RunningTaskInfo runningTaskInfo = remoteAnimationTarget.taskInfo;
                            if (runningTaskInfo != null && runningTaskInfo.isSplitScreen() && remoteAnimationTarget.mode == 0) {
                                z3 = true;
                                break;
                            }
                            length--;
                        }
                        if (z3 && (findRootIndex = transitionInfo3.findRootIndex(stageCoordinator.mDisplayId)) >= 0) {
                            stageCoordinator.mSplitBackgroundController.reparentToLeash(transitionInfo3.getRoot(findRootIndex).getLeash(), true, null);
                        }
                    }
                }
                try {
                    if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                        ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -645411680, 1, "[%d] RecentsController.merge: calling onTasksAppeared", Long.valueOf(this.mInstanceId));
                    }
                    this.mListener.onTasksAppeared(remoteAnimationTargetArr);
                } catch (RemoteException e) {
                    Slog.e("RecentsTransitionHandler", "Error sending appeared tasks to recents animation", e);
                }
            }
            transitionFinishCallback.onTransitionFinished(null, null);
        }

        public final boolean removeTask(int i) {
            return false;
        }

        public final TaskSnapshot screenshotTask(int i) {
            try {
                if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 313798264, 5, "[%d] RecentsController.screenshotTask: taskId=%d", Long.valueOf(this.mInstanceId), Long.valueOf(i));
                }
                return ActivityTaskManager.getService().takeTaskSnapshot(i, true);
            } catch (RemoteException e) {
                Slog.e("RecentsTransitionHandler", "Failed to screenshot task", e);
                return null;
            }
        }

        public final boolean sendCancel(int[] iArr, TaskSnapshot[] taskSnapshotArr) {
            String str = taskSnapshotArr != null ? "with snapshots" : "";
            try {
                if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 163928195, 1, "[%d] RecentsController.cancel: calling onAnimationCanceled %s", Long.valueOf(this.mInstanceId), str);
                }
                this.mListener.onAnimationCanceled(iArr, taskSnapshotArr);
                return true;
            } catch (RemoteException e) {
                Slog.e("RecentsTransitionHandler", "Error canceling recents animation", e);
                return false;
            }
        }

        public final void sendCancelWithSnapshots() {
            Pair pair = this.mPendingPauseSnapshotsForCancel;
            if (pair == null) {
                pair = getSnapshotsForPausingTasks();
            }
            sendCancel((int[]) pair.first, (TaskSnapshot[]) pair.second);
        }

        public final void setFinishTaskTransaction(int i, PictureInPictureSurfaceTransaction pictureInPictureSurfaceTransaction, SurfaceControl surfaceControl) {
            if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -824650337, 5, "[%d] RecentsController.setFinishTaskTransaction: taskId=%d", Long.valueOf(this.mInstanceId), Long.valueOf(i));
            }
            ((HandlerExecutor) RecentsTransitionHandler.this.mExecutor).execute(new RecentsTransitionHandler$$ExternalSyntheticLambda2(1, this, pictureInPictureSurfaceTransaction));
        }

        public final void setInputConsumerEnabled(boolean z) {
            ((HandlerExecutor) RecentsTransitionHandler.this.mExecutor).execute(new RunnableC4088x1263b67d(this, z, 0));
        }

        public final void setWillFinishToHome(boolean z) {
            ((HandlerExecutor) RecentsTransitionHandler.this.mExecutor).execute(new RunnableC4088x1263b67d(this, z, 1));
        }

        public final void cancel(String str, boolean z, boolean z2) {
            if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -567615180, 13, "[%d] RecentsController.cancel: toHome=%b reason=%s", Long.valueOf(this.mInstanceId), Boolean.valueOf(z), String.valueOf(str));
            }
            if (this.mListener != null) {
                if (z2) {
                    sendCancelWithSnapshots();
                } else {
                    sendCancel(null, null);
                }
            }
            if (this.mFinishCB != null) {
                finishInner(z, false);
            } else {
                cleanUp();
            }
        }

        public final void animateNavigationBarToApp(long j) {
        }

        public final void setAnimationTargetsBehindSystemBars(boolean z) {
        }

        public final void cleanupScreenshot() {
        }

        public final void setDeferCancelUntilNextTransition(boolean z, boolean z2) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class TaskState {
        public final SurfaceControl mLeash;
        public final ActivityManager.RunningTaskInfo mTaskInfo;
        public final SurfaceControl mTaskSurface;
        public final WindowContainerToken mToken;

        public TaskState(TransitionInfo.Change change, SurfaceControl surfaceControl) {
            this.mToken = change.getContainer();
            this.mTaskInfo = change.getTaskInfo();
            this.mTaskSurface = change.getLeash();
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

    public RecentsTransitionHandler(ShellInit shellInit, final Transitions transitions, final RecentTasksController recentTasksController) {
        this.mTransitions = transitions;
        this.mExecutor = transitions.mMainExecutor;
        if (Transitions.ENABLE_SHELL_TRANSITIONS && recentTasksController != null) {
            shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.recents.RecentsTransitionHandler$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    RecentsTransitionHandler recentsTransitionHandler = RecentsTransitionHandler.this;
                    RecentTasksController recentTasksController2 = recentTasksController;
                    Transitions transitions2 = transitions;
                    recentsTransitionHandler.getClass();
                    recentTasksController2.mTransitionHandler = recentsTransitionHandler;
                    transitions2.addHandler(recentsTransitionHandler);
                }
            }, this);
            if (CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION) {
                this.mMultiTaskingTransitions = transitions.mMultiTaskingTransitProvider;
            }
        }
    }

    public final int findController(IBinder iBinder) {
        ArrayList arrayList = this.mControllers;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            if (((RecentsController) arrayList.get(size)).mTransition == iBinder) {
                return size;
            }
        }
        return -1;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        ArrayList arrayList = this.mControllers;
        if (arrayList.isEmpty()) {
            return null;
        }
        RecentsController recentsController = (RecentsController) arrayList.get(arrayList.size() - 1);
        recentsController.getClass();
        if (transitionRequestInfo.getType() == 6 && transitionRequestInfo.getDisplayChange() != null) {
            TransitionRequestInfo.DisplayChange displayChange = transitionRequestInfo.getDisplayChange();
            if (displayChange.getStartRotation() != displayChange.getEndRotation()) {
                if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -1063708666, 1, "[%d] RecentsController.prepareForMerge: Snapshot due to requested display change", Long.valueOf(recentsController.mInstanceId));
                }
                recentsController.mPendingPauseSnapshotsForCancel = recentsController.getSnapshotsForPausingTasks();
            }
        }
        return null;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        int findController = findController(iBinder2);
        if (findController < 0) {
            if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -134333442, 0, "RecentsTransitionHandler.mergeAnimation: no controller found", null);
            }
        } else {
            RecentsController recentsController = (RecentsController) this.mControllers.get(findController);
            if (CoreRune.MW_MULTI_SPLIT_BACKGROUND) {
                recentsController.merge(transitionInfo, transaction, transitionFinishCallback, iBinder);
            } else {
                recentsController.merge(transitionInfo, transaction, transitionFinishCallback, null);
            }
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
        ArrayList arrayList = this.mControllers;
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            ((RecentsController) arrayList.get(size)).cancel("onTransitionConsumed");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:114:0x02a3, code lost:
    
        if (r5 != false) goto L156;
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x045a, code lost:
    
        if (r11.topActivityType != 2) goto L170;
     */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        IApplicationThread iApplicationThread;
        boolean z;
        IApplicationThread iApplicationThread2;
        ArrayList arrayList;
        SurfaceControl surfaceControl;
        String str;
        TransitionUtil.LeafTaskFilter leafTaskFilter;
        int i;
        RemoteAnimationTarget newTarget;
        boolean z2;
        SurfaceControl surfaceControl2;
        boolean z3;
        int findController = findController(iBinder);
        if (findController < 0) {
            if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 1354959912, 0, "RecentsTransitionHandler.startAnimation: no controller found", null);
            }
            return false;
        }
        final RecentsController recentsController = (RecentsController) this.mControllers.get(findController);
        IApplicationThread iApplicationThread3 = this.mAnimApp;
        this.mAnimApp = null;
        recentsController.getClass();
        if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -1616732785, 1, "[%d] RecentsController.start", Long.valueOf(recentsController.mInstanceId));
        }
        if (recentsController.mListener == null || recentsController.mTransition == null) {
            iApplicationThread = iApplicationThread3;
            recentsController.cleanUp();
            z = false;
        } else {
            if (CoreRune.FW_CUSTOM_SHELL_RECENTS_TRANSITION_WITH_DISPLAY_CHANGE) {
                RecentsTransitionHandler.this.getClass();
                if (TransitionUtil.hasDisplayRotationChange(transitionInfo)) {
                    if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                        ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 200934091, 1, "[%d] RecentsController.start: defer to start animation due to display change", Long.valueOf(recentsController.mInstanceId));
                    }
                    recentsController.mIsDisplayChangeOnStart = true;
                    z = false;
                    iApplicationThread = iApplicationThread3;
                }
            }
            SurfaceControl surfaceControl3 = null;
            boolean z4 = false;
            for (int i2 = 0; i2 < transitionInfo.getChanges().size(); i2++) {
                TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(i2);
                if (!TransitionUtil.isWallpaper(change)) {
                    if (TransitionUtil.isClosingType(change.getMode())) {
                        z4 = true;
                    } else if (!CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX || !change.hasFlags(VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS)) {
                        ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
                        if (taskInfo != null && taskInfo.topActivityType == 3) {
                            recentsController.mRecentsTask = taskInfo.token;
                            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && surfaceControl3 == null) {
                                surfaceControl3 = change.getLeash();
                            }
                        } else if (taskInfo != null && taskInfo.topActivityType == 2) {
                            recentsController.mRecentsTask = taskInfo.token;
                            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && surfaceControl3 == null) {
                                surfaceControl3 = change.getLeash();
                            }
                        }
                    }
                }
            }
            String str2 = "RecentsTransitionHandler";
            if (recentsController.mRecentsTask != null || z4) {
                recentsController.mInfo = transitionInfo;
                recentsController.mFinishCB = transitionFinishCallback;
                recentsController.mFinishTransaction = transaction2;
                recentsController.mPausingTasks = new ArrayList();
                recentsController.mOpeningTasks = new ArrayList();
                recentsController.mLeashMap = new ArrayMap();
                recentsController.mKeyguardLocked = (transitionInfo.getFlags() & 64) != 0;
                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && recentsController.mIsDisplayChangeOnStart) {
                    recentsController.mIsDisplayChangeOnStart = false;
                    if (recentsController.mListener != null) {
                        recentsController.sendCancelWithSnapshots();
                    }
                    for (int i3 = 0; i3 < transitionInfo.getChanges().size(); i3++) {
                        TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(i3);
                        if (TransitionUtil.isClosingType(change2.getMode())) {
                            recentsController.mPausingTasks.add(new TaskState(change2, null));
                            transaction.hide(change2.getLeash());
                        }
                    }
                    transaction.addTransactionCommittedListener(RecentsTransitionHandler.this.mExecutor, new SurfaceControl.TransactionCommittedListener() { // from class: com.android.wm.shell.recents.RecentsTransitionHandler$RecentsController$$ExternalSyntheticLambda3
                        @Override // android.view.SurfaceControl.TransactionCommittedListener
                        public final void onTransactionCommitted() {
                            RecentsTransitionHandler.RecentsController.this.finish(true, false);
                        }
                    });
                    iApplicationThread = iApplicationThread3;
                    z = true;
                } else {
                    ArrayList arrayList2 = new ArrayList();
                    ArrayList arrayList3 = new ArrayList();
                    TransitionUtil.LeafTaskFilter leafTaskFilter2 = new TransitionUtil.LeafTaskFilter();
                    int size = transitionInfo.getChanges().size();
                    int size2 = transitionInfo.getChanges().size() * 2;
                    int size3 = transitionInfo.getChanges().size() * 3;
                    int i4 = 0;
                    while (i4 < transitionInfo.getChanges().size()) {
                        TransitionInfo.Change change3 = (TransitionInfo.Change) transitionInfo.getChanges().get(i4);
                        ActivityManager.RunningTaskInfo taskInfo2 = change3.getTaskInfo();
                        if (TransitionUtil.isWallpaper(change3)) {
                            RemoteAnimationTarget newTarget2 = TransitionUtil.newTarget(change3, size - i4, transitionInfo, transaction, recentsController.mLeashMap);
                            arrayList3.add(newTarget2);
                            transaction.setAlpha(newTarget2.leash, 1.0f);
                            iApplicationThread2 = iApplicationThread3;
                            arrayList = arrayList3;
                            surfaceControl = surfaceControl3;
                            str = str2;
                            leafTaskFilter = leafTaskFilter2;
                            i = size2;
                        } else {
                            if (leafTaskFilter2.test(change3)) {
                                if (CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION) {
                                    RecentsTransitionHandler recentsTransitionHandler = RecentsTransitionHandler.this;
                                    recentsTransitionHandler.getClass();
                                    int forceHidingTransit = change3.getForceHidingTransit();
                                    if (forceHidingTransit == 0) {
                                        iApplicationThread2 = iApplicationThread3;
                                        arrayList = arrayList3;
                                        surfaceControl = surfaceControl3;
                                        str = str2;
                                        leafTaskFilter = leafTaskFilter2;
                                        i = size2;
                                        z3 = false;
                                    } else {
                                        if (change3.getFreeformStashScale() != 1.0f) {
                                            iApplicationThread2 = iApplicationThread3;
                                            arrayList = arrayList3;
                                            surfaceControl = surfaceControl3;
                                            str = str2;
                                            leafTaskFilter = leafTaskFilter2;
                                            i = size2;
                                        } else {
                                            leafTaskFilter = leafTaskFilter2;
                                            SurfaceControl leash = change3.getLeash();
                                            iApplicationThread2 = iApplicationThread3;
                                            arrayList = arrayList3;
                                            String str3 = "leash=" + leash + ", " + MultiWindowManager.forceHidingTransitToString(forceHidingTransit);
                                            int i5 = forceHidingTransit == 1 ? R.anim.freeform_window_force_hide_enter : R.anim.freeform_window_force_hide_exit_delay;
                                            surfaceControl = surfaceControl3;
                                            Slog.d(str2, "buildForceHideAnimationIfNeeded: " + str3);
                                            Rect endAbsBounds = change3.getEndAbsBounds();
                                            Animation loadAnimationFromResources = recentsTransitionHandler.mMultiTaskingTransitions.loadAnimationFromResources(i5, endAbsBounds);
                                            loadAnimationFromResources.setInterpolator(InterpolatorUtils.SINE_OUT_60);
                                            str = str2;
                                            i = size2;
                                            Point point = new Point(endAbsBounds.left, endAbsBounds.top);
                                            Rect rect = new Rect(endAbsBounds);
                                            rect.offsetTo(0, 0);
                                            recentsTransitionHandler.mMultiTaskingTransitions.buildSurfaceAnimator(recentsTransitionHandler.mForceHidingAnimators, loadAnimationFromResources, leash, new RecentsTransitionHandler$$ExternalSyntheticLambda2(0, recentsTransitionHandler, str3), point, rect, true);
                                        }
                                        z3 = true;
                                    }
                                } else {
                                    iApplicationThread2 = iApplicationThread3;
                                    arrayList = arrayList3;
                                    surfaceControl = surfaceControl3;
                                    str = str2;
                                    leafTaskFilter = leafTaskFilter2;
                                    i = size2;
                                }
                                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE_TRANSFER) {
                                    int i6 = size - i4;
                                    ArrayMap arrayMap = recentsController.mLeashMap;
                                    ArrayMap arrayMap2 = recentsController.mTransferLeashMap;
                                    if (change3.getTaskInfo() == null || arrayMap2 == null || arrayMap2.isEmpty()) {
                                        surfaceControl2 = null;
                                    } else {
                                        Iterator it = arrayMap2.keySet().iterator();
                                        while (true) {
                                            if (!it.hasNext()) {
                                                surfaceControl2 = null;
                                                break;
                                            }
                                            SurfaceControl surfaceControl4 = (SurfaceControl) it.next();
                                            if (surfaceControl4.isSameSurface(change3.getLeash())) {
                                                surfaceControl2 = (SurfaceControl) arrayMap2.get(surfaceControl4);
                                                break;
                                            }
                                        }
                                        if (surfaceControl2 != null) {
                                            transaction.reparent(surfaceControl2, transitionInfo.getRoot(TransitionUtil.rootIndexFor(change3, transitionInfo)).getLeash());
                                            int size4 = transitionInfo.getChanges().size() - i6;
                                            boolean isOpeningType = TransitionUtil.isOpeningType(transitionInfo.getType());
                                            int size5 = transitionInfo.getChanges().size();
                                            int mode = change3.getMode();
                                            transaction.reparent(surfaceControl2, transitionInfo.getRoot(TransitionUtil.rootIndexFor(change3, transitionInfo)).getLeash());
                                            if (TransitionUtil.isOpeningType(mode)) {
                                                if (isOpeningType) {
                                                    transaction.setLayer(surfaceControl2, (transitionInfo.getChanges().size() + size5) - size4);
                                                } else {
                                                    transaction.setLayer(surfaceControl2, size5 - size4);
                                                }
                                            } else if (!TransitionUtil.isClosingType(mode)) {
                                                transaction.setLayer(surfaceControl2, (transitionInfo.getChanges().size() + size5) - size4);
                                            } else if (isOpeningType) {
                                                transaction.setLayer(surfaceControl2, size5 - size4);
                                            } else {
                                                transaction.setLayer(surfaceControl2, (transitionInfo.getChanges().size() + size5) - size4);
                                            }
                                            transaction.reparent(change3.getLeash(), surfaceControl2);
                                            transaction.setAlpha(change3.getLeash(), 1.0f);
                                            transaction.show(change3.getLeash());
                                            transaction.setPosition(change3.getLeash(), 0.0f, 0.0f);
                                            transaction.setLayer(change3.getLeash(), 0);
                                        }
                                    }
                                    if (surfaceControl2 == null) {
                                        surfaceControl2 = TransitionUtil.createLeash(transitionInfo, change3, i6, transaction);
                                    }
                                    if (arrayMap != null) {
                                        arrayMap.put(change3.getLeash(), surfaceControl2);
                                    }
                                    newTarget = TransitionUtil.newTarget(change3, i6, surfaceControl2, false);
                                } else {
                                    newTarget = TransitionUtil.newTarget(change3, size - i4, transitionInfo, transaction, recentsController.mLeashMap);
                                }
                                arrayList2.add(newTarget);
                                if (TransitionUtil.isClosingType(change3.getMode())) {
                                    recentsController.mPausingTasks.add(new TaskState(change3, newTarget.leash));
                                    if (taskInfo2.topActivityType == 2) {
                                        if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                                            z2 = true;
                                            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 612660725, 1, "  adding pausing leaf home taskId=%d", Long.valueOf(taskInfo2.taskId));
                                        } else {
                                            z2 = true;
                                        }
                                        recentsController.mPausingSeparateHome = z2;
                                    } else {
                                        int i7 = size3 - i4;
                                        if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                                            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 1857540542, 5, "  adding pausing leaf taskId=%d at layer=%d", Long.valueOf(taskInfo2.taskId), Long.valueOf(i7));
                                        }
                                        transaction.setLayer(newTarget.leash, i7);
                                    }
                                    PictureInPictureParams pictureInPictureParams = taskInfo2.pictureInPictureParams;
                                    if (pictureInPictureParams != null && pictureInPictureParams.isAutoEnterEnabled()) {
                                        recentsController.mPipTask = taskInfo2.token;
                                    }
                                } else {
                                    if (taskInfo2 != null && taskInfo2.topActivityType == 3) {
                                        int i8 = i - i4;
                                        if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                                            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 1908094340, 1, "  setting recents activity layer=%d", Long.valueOf(i8));
                                        }
                                        transaction.setLayer(newTarget.leash, i8);
                                    }
                                    if (TransitionUtil.isOpeningType(change3.getMode())) {
                                        if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                                            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -1086197727, 1, "  adding opening leaf taskId=%d", Long.valueOf(taskInfo2.taskId));
                                        }
                                        recentsController.mOpeningTasks.add(new TaskState(change3, newTarget.leash));
                                    }
                                }
                            } else {
                                iApplicationThread2 = iApplicationThread3;
                                arrayList = arrayList3;
                                surfaceControl = surfaceControl3;
                                str = str2;
                                leafTaskFilter = leafTaskFilter2;
                                i = size2;
                                if (taskInfo2 == null || !TransitionInfo.isIndependent(change3, transitionInfo)) {
                                    if (TransitionUtil.isDividerBar(change3)) {
                                        arrayList2.add(TransitionUtil.newTarget(change3, size - i4, transitionInfo, transaction, recentsController.mLeashMap));
                                    } else if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_TRANSIENT_LAUNCH_OVERLAY && TransitionUtil.isTransientLaunchOverlay(change3)) {
                                        RemoteAnimationTarget newTarget3 = TransitionUtil.newTarget(change3, size - i4, transitionInfo, transaction, recentsController.mLeashMap);
                                        SurfaceControl surfaceControl5 = newTarget3.leash;
                                        if (surfaceControl5 == null || surfaceControl == null) {
                                            surfaceControl3 = surfaceControl;
                                        } else {
                                            surfaceControl3 = surfaceControl;
                                            transaction.setRelativeLayer(surfaceControl5, surfaceControl3, 1);
                                        }
                                        arrayList2.add(newTarget3);
                                        i4++;
                                        leafTaskFilter2 = leafTaskFilter;
                                        iApplicationThread3 = iApplicationThread2;
                                        arrayList3 = arrayList;
                                        str2 = str;
                                        size2 = i;
                                    }
                                } else if (TransitionUtil.isClosingType(change3.getMode())) {
                                    int i9 = size3 - i4;
                                    if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                                        ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -1183965748, 5, "  adding pausing taskId=%d at layer=%d", Long.valueOf(taskInfo2.taskId), Long.valueOf(i9));
                                    }
                                    transaction.setLayer(change3.getLeash(), i9);
                                    recentsController.mPausingTasks.add(new TaskState(change3, null));
                                } else if (TransitionUtil.isOpeningType(change3.getMode())) {
                                    int i10 = size - i4;
                                    if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                                        ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -1340464399, 5, "  adding opening taskId=%d at layer=%d", Long.valueOf(taskInfo2.taskId), Long.valueOf(i10));
                                    }
                                    transaction.setLayer(change3.getLeash(), i10);
                                    recentsController.mOpeningTasks.add(new TaskState(change3, null));
                                }
                            }
                            surfaceControl3 = surfaceControl;
                            i4++;
                            leafTaskFilter2 = leafTaskFilter;
                            iApplicationThread3 = iApplicationThread2;
                            arrayList3 = arrayList;
                            str2 = str;
                            size2 = i;
                        }
                        surfaceControl3 = surfaceControl;
                        i4++;
                        leafTaskFilter2 = leafTaskFilter;
                        iApplicationThread3 = iApplicationThread2;
                        arrayList3 = arrayList;
                        str2 = str;
                        size2 = i;
                    }
                    iApplicationThread = iApplicationThread3;
                    ArrayList arrayList4 = arrayList3;
                    String str4 = str2;
                    transaction.apply();
                    try {
                        if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 1573301134, 1, "[%d] RecentsController.start: calling onAnimationStart", Long.valueOf(recentsController.mInstanceId));
                        }
                        recentsController.mListener.onAnimationStart(recentsController, (RemoteAnimationTarget[]) arrayList2.toArray(new RemoteAnimationTarget[arrayList2.size()]), (RemoteAnimationTarget[]) arrayList4.toArray(new RemoteAnimationTarget[arrayList4.size()]), new Rect(0, 0, 0, 0), new Rect());
                        for (int i11 = 0; i11 < RecentsTransitionHandler.this.mStateListeners.size(); i11++) {
                            ((RecentsTransitionStateListener) RecentsTransitionHandler.this.mStateListeners.get(i11)).onAnimationStateChanged(true);
                        }
                    } catch (RemoteException e) {
                        Slog.e(str4, "Error starting recents animation", e);
                        recentsController.cancel("onAnimationStart() failed");
                    }
                    z = true;
                }
            } else {
                Slog.e("RecentsTransitionHandler", "Tried to start recents while it is already running.");
                recentsController.cleanUp();
                z = false;
                iApplicationThread = iApplicationThread3;
            }
        }
        if (z) {
            Transitions.setRunningRemoteTransitionDelegate(iApplicationThread);
            return true;
        }
        if (!ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
            return false;
        }
        ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 1592050575, 0, "RecentsTransitionHandler.startAnimation: failed to start animation", null);
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0039, code lost:
    
        if (r0 != false) goto L14;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public IBinder startRecentsTransition(PendingIntent pendingIntent, Intent intent, Bundle bundle, IApplicationThread iApplicationThread, IRecentsAnimationRunner iRecentsAnimationRunner) {
        DefaultMixedHandler defaultMixedHandler;
        RecentsTransitionHandler recentsTransitionHandler;
        RecentsTransitionHandler recentsTransitionHandler2;
        if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -1343679460, 0, "RecentsTransitionHandler.startRecentsTransition", null);
        }
        this.mAnimApp = iApplicationThread;
        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
        windowContainerTransaction.sendPendingIntent(pendingIntent, intent, bundle);
        RecentsController recentsController = new RecentsController(iRecentsAnimationRunner);
        int i = 0;
        RecentsTransitionHandler recentsTransitionHandler3 = null;
        while (true) {
            ArrayList arrayList = this.mMixers;
            if (i >= arrayList.size()) {
                defaultMixedHandler = null;
                recentsTransitionHandler = recentsTransitionHandler3;
                break;
            }
            DefaultMixedHandler defaultMixedHandler2 = (DefaultMixedHandler) arrayList.get(i);
            if (defaultMixedHandler2.mRecentsHandler != null) {
                boolean isSplitScreenVisible = defaultMixedHandler2.mSplitHandler.isSplitScreenVisible();
                recentsTransitionHandler2 = defaultMixedHandler2;
            }
            recentsTransitionHandler2 = null;
            if (recentsTransitionHandler2 != null) {
                defaultMixedHandler = (DefaultMixedHandler) arrayList.get(i);
                recentsTransitionHandler = recentsTransitionHandler2;
                break;
            }
            i++;
            recentsTransitionHandler3 = recentsTransitionHandler2;
        }
        if (recentsTransitionHandler == null) {
            recentsTransitionHandler = this;
        }
        IBinder startTransition = this.mTransitions.startTransition(3, windowContainerTransaction, recentsTransitionHandler);
        if (defaultMixedHandler != null) {
            if (!defaultMixedHandler.mSplitHandler.isSplitScreenVisible()) {
                throw new IllegalStateException("Accepted a recents transition but don't know how to handle it");
            }
            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -449756535, 0, " Got a recents request while Split-Screen is foreground, so treat it as Mixed.", null);
            }
            DefaultMixedHandler.MixedTransition mixedTransition = new DefaultMixedHandler.MixedTransition(4, startTransition);
            mixedTransition.mLeftoversHandler = defaultMixedHandler.mRecentsHandler;
            defaultMixedHandler.mActiveTransitions.add(mixedTransition);
        }
        if (startTransition != null) {
            if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, -566822870, 1, "[%d] RecentsController.setTransition: id=%s", Long.valueOf(recentsController.mInstanceId), String.valueOf(startTransition));
            }
            recentsController.mTransition = startTransition;
            this.mControllers.add(recentsController);
        } else {
            recentsController.cancel("startRecentsTransition");
        }
        return startTransition;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void transferAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, WindowContainerTransaction windowContainerTransaction) {
        if (windowContainerTransaction == null) {
            return;
        }
        int findController = findController(iBinder);
        if (findController < 0) {
            if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 922426383, 0, "RecentsTransitionHandler.transferAnimation: no controller found", null);
                return;
            }
            return;
        }
        RecentsController recentsController = (RecentsController) this.mControllers.get(findController);
        ArrayMap arrayMap = windowContainerTransaction.mTransferLeashMap;
        if (arrayMap == null || arrayMap.isEmpty()) {
            return;
        }
        recentsController.mTransferLeashMap = windowContainerTransaction.mTransferLeashMap;
        int findRootIndex = transitionInfo.findRootIndex(0);
        if (findRootIndex < 0) {
            return;
        }
        SurfaceControl leash = transitionInfo.getRoot(findRootIndex).getLeash();
        int size = recentsController.mTransferLeashMap.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            }
            SurfaceControl surfaceControl = (SurfaceControl) recentsController.mTransferLeashMap.valueAt(size);
            if (surfaceControl == null || !surfaceControl.isValid()) {
                recentsController.mTransferLeashMap.removeAt(size);
                Slog.d("RecentsTransitionHandler", "Cannot transfer invalid leash=" + surfaceControl);
            } else {
                transaction.reparent(surfaceControl, leash);
            }
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void transitionReady(IBinder iBinder, TransitionInfo transitionInfo) {
        if (findController(iBinder) < 0) {
            if (ShellProtoLogCache.WM_SHELL_RECENTS_TRANSITION_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 1158245817, 0, "RecentsTransitionHandler.onTransitionReady: no controller found", null);
            }
        } else {
            IApplicationThread iApplicationThread = this.mAnimApp;
            if (iApplicationThread != null) {
                transitionInfo.setRemoteAppThread(iApplicationThread);
            }
        }
    }
}
