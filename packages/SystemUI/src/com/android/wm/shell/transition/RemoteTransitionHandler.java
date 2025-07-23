package com.android.wm.shell.transition;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.view.SurfaceControl;
import android.window.IRemoteTransition;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.RemoteTransition;
import android.window.TransitionFilter;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowAnimationState;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class RemoteTransitionHandler implements Transitions.TransitionHandler {
    public static final boolean SUPPORT_MINIMIZE_REMOTE_TRANSITION = SystemProperties.getBoolean("persist.mt.debug.minimize_remote_transition", false);
    public ShellExecutor mAnimExecutor;
    public final ShellExecutor mMainExecutor;
    public MultiTaskingTransitionProvider mMultiTaskingTransitions;
    public final ArrayMap mRequestedRemotes = new ArrayMap();
    public final ArrayList mFilters = new ArrayList();
    public final ArrayList mTakeoverFilters = new ArrayList();
    public final ArrayMap mDeathHandlers = new ArrayMap();
    public final ArrayMap mRequestedInfoList = new ArrayMap();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.transition.RemoteTransitionHandler$1, reason: invalid class name */
    public class AnonymousClass1 extends IRemoteTransitionFinishedCallback.Stub {
        public final /* synthetic */ Transitions.TransitionFinishCallback val$finishCallback;
        public final /* synthetic */ SurfaceControl.Transaction val$finishTransaction;
        public final /* synthetic */ RemoteTransition val$remote;
        public final /* synthetic */ IBinder val$transition;

        public AnonymousClass1(RemoteTransition remoteTransition, Transitions.TransitionFinishCallback transitionFinishCallback, SurfaceControl.Transaction transaction, IBinder iBinder) {
            this.val$remote = remoteTransition;
            this.val$finishCallback = transitionFinishCallback;
            this.val$finishTransaction = transaction;
            this.val$transition = iBinder;
        }

        public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) {
            RemoteTransitionHandler remoteTransitionHandler = RemoteTransitionHandler.this;
            IBinder asBinder = this.val$remote.asBinder();
            Transitions.TransitionFinishCallback transitionFinishCallback = this.val$finishCallback;
            boolean z = RemoteTransitionHandler.SUPPORT_MINIMIZE_REMOTE_TRANSITION;
            remoteTransitionHandler.unhandleDeath(asBinder, transitionFinishCallback);
            if (transaction != null) {
                this.val$finishTransaction.merge(transaction);
            }
            RemoteTransitionHandler.this.mMainExecutor.execute(new RemoteTransitionHandler$1$$ExternalSyntheticLambda0(this, this.val$transition, this.val$finishCallback, windowContainerTransaction));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.transition.RemoteTransitionHandler$2, reason: invalid class name */
    public class AnonymousClass2 extends IRemoteTransitionFinishedCallback.Stub {
        public final /* synthetic */ Transitions.TransitionFinishCallback val$finishCallback;
        public final /* synthetic */ TransitionInfo val$info;
        public final /* synthetic */ IBinder val$mergeTarget;
        public final /* synthetic */ SurfaceControl.Transaction val$startT;
        public final /* synthetic */ String val$startTransactionName;

        public AnonymousClass2(TransitionInfo transitionInfo, String str, SurfaceControl.Transaction transaction, IBinder iBinder, Transitions.TransitionFinishCallback transitionFinishCallback) {
            this.val$info = transitionInfo;
            this.val$startTransactionName = str;
            this.val$startT = transaction;
            this.val$mergeTarget = iBinder;
            this.val$finishCallback = transitionFinishCallback;
        }

        public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) {
            if (!CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER || !this.val$info.canTransferAnimation()) {
                this.val$startT.clear();
            } else if (CoreRune.FW_SURFACE_DEBUG_APPLY && !TextUtils.isEmpty(this.val$startTransactionName)) {
                this.val$startT.addDebugName(this.val$startTransactionName);
            }
            RemoteTransitionHandler.this.mMainExecutor.execute(new RemoteTransitionHandler$1$$ExternalSyntheticLambda0(this, this.val$mergeTarget, this.val$finishCallback, windowContainerTransaction));
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RemoteDeathHandler implements IBinder.DeathRecipient {
        public final IBinder mRemote;
        public final ArrayList mPendingFinishCallbacks = new ArrayList();
        public int mUsers = 0;

        public RemoteDeathHandler(IBinder iBinder) {
            this.mRemote = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            RemoteTransitionHandler.this.mMainExecutor.execute(new RemoteTransitionHandler$$ExternalSyntheticLambda1(this, 1));
        }
    }

    public RemoteTransitionHandler(ShellExecutor shellExecutor) {
        this.mMainExecutor = shellExecutor;
    }

    public static SurfaceControl.Transaction copyIfLocal(SurfaceControl.Transaction transaction, IRemoteTransition iRemoteTransition) {
        if (iRemoteTransition.asBinder().queryLocalInterface("android.window.IRemoteTransition") == null) {
            return transaction;
        }
        Parcel obtain = Parcel.obtain();
        try {
            transaction.writeToParcel(obtain, 0);
            obtain.setDataPosition(0);
            return (SurfaceControl.Transaction) SurfaceControl.Transaction.CREATOR.createFromParcel(obtain);
        } finally {
            obtain.recycle();
        }
    }

    public static void dumpRemote(PrintWriter printWriter, String str, RemoteTransition remoteTransition) {
        printWriter.print(str);
        printWriter.print(remoteTransition.getDebugName());
        printWriter.println(" (" + Integer.toHexString(System.identityHashCode(remoteTransition)) + ")");
    }

    public static void mergeAnimationIfNeeded(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, AnonymousClass2 anonymousClass2) {
        for (int i = 0; i < transitionInfo.getChanges().size(); i++) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
            if (TransitionUtil.isClosingType(change.getMode()) || change.getTaskInfo() != null) {
                transaction.hide(change.getLeash());
            } else if (TransitionUtil.isOpeningType(change.getMode())) {
                transaction.show(change.getLeash());
                transaction.setAlpha(change.getLeash(), 1.0f);
            }
        }
        transaction.apply();
        transitionInfo.releaseAnimSurfaces();
        try {
            anonymousClass2.onTransitionFinished(null, null);
        } catch (RemoteException unused) {
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final Transitions.TransitionHandler getHandlerForTakeover(IBinder iBinder, TransitionInfo transitionInfo) {
        ArrayList arrayList = this.mTakeoverFilters;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            Pair pair = (Pair) obj;
            if (((TransitionFilter) pair.first).matches(transitionInfo)) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 4200872868630966360L, 1, Long.valueOf(transitionInfo.getDebugId()));
                }
                OneShotRemoteHandler oneShotRemoteHandler = new OneShotRemoteHandler(this.mMainExecutor, (RemoteTransition) pair.second);
                oneShotRemoteHandler.mTransition = iBinder;
                return oneShotRemoteHandler;
            }
        }
        if (!ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
            return null;
        }
        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 1741514098865223203L, 1, Long.valueOf(transitionInfo.getDebugId()));
        return null;
    }

    public final void handleDeath(IBinder iBinder, Transitions.TransitionFinishCallback transitionFinishCallback) {
        synchronized (this.mDeathHandlers) {
            try {
                RemoteDeathHandler remoteDeathHandler = (RemoteDeathHandler) this.mDeathHandlers.get(iBinder);
                if (remoteDeathHandler == null) {
                    remoteDeathHandler = new RemoteDeathHandler(iBinder);
                    try {
                        iBinder.linkToDeath(remoteDeathHandler, 0);
                        this.mDeathHandlers.put(iBinder, remoteDeathHandler);
                    } catch (RemoteException unused) {
                        Slog.e("RemoteTransitionHandler", "Failed to link to death");
                        return;
                    }
                }
                if (transitionFinishCallback != null) {
                    remoteDeathHandler.mPendingFinishCallbacks.add(transitionFinishCallback);
                }
                remoteDeathHandler.mUsers++;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        RemoteTransition remoteTransition = transitionRequestInfo.getRemoteTransition();
        if (remoteTransition == null) {
            return null;
        }
        this.mRequestedRemotes.put(iBinder, remoteTransition);
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 4921319494350418912L, 1, Long.valueOf(transitionRequestInfo.getDebugId()), String.valueOf(iBinder), String.valueOf(remoteTransition));
        }
        return new WindowContainerTransaction();
    }

    /* JADX WARN: Removed duplicated region for block: B:77:0x015e A[Catch: RemoteException -> 0x0179, TryCatch #1 {RemoteException -> 0x0179, blocks: (B:15:0x004c, B:18:0x0058, B:20:0x005c, B:22:0x0060, B:24:0x0066, B:26:0x006c, B:27:0x007e, B:29:0x0088, B:32:0x00da, B:34:0x00e7, B:36:0x00f7, B:38:0x00ff, B:40:0x0107, B:43:0x0110, B:44:0x0114, B:46:0x0123, B:48:0x0126, B:50:0x012c, B:52:0x0132, B:53:0x0145, B:63:0x0152, B:65:0x0175, B:75:0x0158, B:77:0x015e, B:78:0x0171, B:80:0x009f, B:82:0x00ae, B:84:0x00be, B:87:0x00c4, B:89:0x00cd, B:91:0x00cf, B:98:0x0054), top: B:14:0x004c }] */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void mergeAnimation(android.os.IBinder r18, android.window.TransitionInfo r19, android.view.SurfaceControl.Transaction r20, android.view.SurfaceControl.Transaction r21, android.os.IBinder r22, com.android.wm.shell.transition.Transitions.TransitionFinishCallback r23) {
        /*
            Method dump skipped, instructions count: 386
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.transition.RemoteTransitionHandler.mergeAnimation(android.os.IBinder, android.window.TransitionInfo, android.view.SurfaceControl$Transaction, android.view.SurfaceControl$Transaction, android.os.IBinder, com.android.wm.shell.transition.Transitions$TransitionFinishCallback):void");
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
        RemoteTransition remoteTransition = (RemoteTransition) this.mRequestedRemotes.remove(iBinder);
        if (CoreRune.FW_SHELL_TRANSITION_MERGE) {
            this.mRequestedInfoList.remove(iBinder);
        }
        if (remoteTransition == null) {
            return;
        }
        try {
            remoteTransition.getRemoteTransition().onTransitionConsumed(iBinder, z);
        } catch (RemoteException e) {
            Log.e("RemoteTransitionHandler", "Error delegating onTransitionConsumed()", e);
        }
    }

    public final void prepareMergeOrTransferAnimationIfNeeded(IBinder iBinder, RemoteTransition remoteTransition, TransitionInfo transitionInfo, TransitionInfo transitionInfo2) {
        if (CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER && (transitionInfo.getFlags() & 128) != 0 && remoteTransition.getAppThread() != null && transitionInfo.getRemoteAppThread() != null && remoteTransition.getAppThread().asBinder() == transitionInfo.getRemoteAppThread().asBinder()) {
            transitionInfo.setCanTransferAnimation();
            return;
        }
        RemoteTransition remoteTransition2 = (RemoteTransition) this.mRequestedRemotes.get(iBinder);
        if (remoteTransition2 == null) {
            int size = this.mFilters.size() - 1;
            while (true) {
                if (size < 0) {
                    break;
                }
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 8755635846379877584L, 0, String.valueOf(this.mFilters.get(size)));
                }
                if (((TransitionFilter) ((Pair) this.mFilters.get(size)).first).matches(transitionInfo)) {
                    Slog.d("RemoteTransitionHandler", "findRemoteTransition, Found filter" + this.mFilters.get(size));
                    remoteTransition2 = (RemoteTransition) ((Pair) this.mFilters.get(size)).second;
                    break;
                }
                size--;
            }
        } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -6282310706905927214L, 0, String.valueOf(remoteTransition2));
        }
        if (remoteTransition2 == null || remoteTransition2.getAppThread() == null || remoteTransition.getAppThread() == null || remoteTransition.getAppThread().asBinder() != remoteTransition2.getAppThread().asBinder()) {
            return;
        }
        ArrayList mergeableTasks = TransitionUtil.getMergeableTasks(transitionInfo2);
        ArrayList mergeableTasks2 = TransitionUtil.getMergeableTasks(transitionInfo);
        if (mergeableTasks.isEmpty() || mergeableTasks2.isEmpty() || mergeableTasks.size() != mergeableTasks2.size()) {
            return;
        }
        for (int i = 0; i < mergeableTasks.size(); i++) {
            TransitionInfo.Change change = (TransitionInfo.Change) mergeableTasks.get(i);
            TransitionInfo.Change change2 = (TransitionInfo.Change) mergeableTasks2.get(i);
            if (change.getTaskInfo().taskId != change2.getTaskInfo().taskId) {
                return;
            }
            SurfaceControl leash = change.getLeash();
            SurfaceControl leash2 = change2.getLeash();
            if (leash == null || leash2 == null || !leash.isSameSurface(leash2)) {
                return;
            }
        }
        transitionInfo.setCanMergeAnimation();
    }

    public final void removeFiltered(RemoteTransition remoteTransition) {
        boolean z = false;
        for (ArrayList arrayList : Arrays.asList(this.mFilters, this.mTakeoverFilters)) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (((RemoteTransition) ((Pair) arrayList.get(size)).second).asBinder().equals(remoteTransition.asBinder())) {
                    arrayList.remove(size);
                    z = true;
                }
            }
        }
        if (z) {
            unhandleDeath(remoteTransition.asBinder(), null);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:69:0x0185 A[Catch: RemoteException -> 0x01a1, TryCatch #0 {RemoteException -> 0x01a1, blocks: (B:56:0x0142, B:58:0x0146, B:60:0x0151, B:62:0x015f, B:64:0x0165, B:67:0x0178, B:69:0x0185, B:71:0x01a3, B:73:0x016c, B:75:0x0170, B:80:0x01a6), top: B:55:0x0142 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01a3 A[SYNTHETIC] */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean startAnimation(android.os.IBinder r17, android.window.TransitionInfo r18, android.view.SurfaceControl.Transaction r19, android.view.SurfaceControl.Transaction r20, com.android.wm.shell.transition.Transitions.TransitionFinishCallback r21) {
        /*
            Method dump skipped, instructions count: 495
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.transition.RemoteTransitionHandler.startAnimation(android.os.IBinder, android.window.TransitionInfo, android.view.SurfaceControl$Transaction, android.view.SurfaceControl$Transaction, com.android.wm.shell.transition.Transitions$TransitionFinishCallback):boolean");
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean takeOverAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, Transitions.TransitionFinishCallback transitionFinishCallback, WindowAnimationState[] windowAnimationStateArr) {
        Transitions.TransitionHandler handlerForTakeover = getHandlerForTakeover(iBinder, transitionInfo);
        if (handlerForTakeover != null) {
            ((OneShotRemoteHandler) handlerForTakeover).mTransition = iBinder;
            return ((OneShotRemoteHandler) handlerForTakeover).takeOverAnimation(iBinder, transitionInfo, transaction, transitionFinishCallback, windowAnimationStateArr);
        }
        if (!ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
            return false;
        }
        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 663903325472569099L, 1, Long.valueOf(transitionInfo.getDebugId()));
        return false;
    }

    public final void unhandleDeath(IBinder iBinder, Transitions.TransitionFinishCallback transitionFinishCallback) {
        synchronized (this.mDeathHandlers) {
            try {
                RemoteDeathHandler remoteDeathHandler = (RemoteDeathHandler) this.mDeathHandlers.get(iBinder);
                if (remoteDeathHandler == null) {
                    return;
                }
                if (transitionFinishCallback != null) {
                    remoteDeathHandler.mPendingFinishCallbacks.remove(transitionFinishCallback);
                }
                int i = remoteDeathHandler.mUsers - 1;
                remoteDeathHandler.mUsers = i;
                if (i == 0) {
                    if (!remoteDeathHandler.mPendingFinishCallbacks.isEmpty()) {
                        throw new IllegalStateException("Unhandling death for binder that still has pending finishCallback(s).");
                    }
                    iBinder.unlinkToDeath(remoteDeathHandler, 0);
                    this.mDeathHandlers.remove(iBinder);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
