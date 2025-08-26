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
import com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.desktopmode.compatui.SystemModalsTransitionHandler$handoverIfNeeded$1;
import com.android.wm.shell.keyguard.KeyguardTransitionHandler;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;

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
            IBinder iBinderAsBinder = this.val$remote.asBinder();
            Transitions.TransitionFinishCallback transitionFinishCallback = this.val$finishCallback;
            boolean z = RemoteTransitionHandler.SUPPORT_MINIMIZE_REMOTE_TRANSITION;
            remoteTransitionHandler.unhandleDeath(iBinderAsBinder, transitionFinishCallback);
            if (transaction != null) {
                this.val$finishTransaction.merge(transaction);
            }
            RemoteTransitionHandler.this.mMainExecutor.execute(new RemoteTransitionHandler$1$$ExternalSyntheticLambda0(this, this.val$transition, this.val$finishCallback, windowContainerTransaction));
        }
    }

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
        Parcel parcelObtain = Parcel.obtain();
        try {
            transaction.writeToParcel(parcelObtain, 0);
            parcelObtain.setDataPosition(0);
            return (SurfaceControl.Transaction) SurfaceControl.Transaction.CREATOR.createFromParcel(parcelObtain);
        } finally {
            parcelObtain.recycle();
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
    public final Transitions.TransitionHandler getHandlerForHandover(IBinder iBinder, TransitionInfo transitionInfo, Function function) {
        if (!Transitions.SHELL_TRANSITIONS_ROTATION && TransitionUtil.hasDisplayChange(transitionInfo)) {
            this.mRequestedRemotes.remove(iBinder);
            return null;
        }
        if (((RemoteTransition) this.mRequestedRemotes.get(iBinder)) == null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -9146248499991136778L, 0, String.valueOf(transitionInfo));
            }
            for (int size = this.mFilters.size() - 1; size >= 0; size--) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 7551344402092077994L, 0, String.valueOf(this.mFilters.get(size)));
                }
                if (((TransitionFilter) ((Pair) this.mFilters.get(size)).first).matches(transitionInfo)) {
                    if (function != null) {
                        if (((Boolean) ((SystemModalsTransitionHandler$handoverIfNeeded$1) function).apply(((RemoteTransition) ((Pair) this.mFilters.get(size)).second).getDebugName())).booleanValue()) {
                        }
                    }
                    Slog.d("RemoteTransitionHandler", "Found filter" + this.mFilters.get(size));
                    this.mRequestedRemotes.put(iBinder, (RemoteTransition) ((Pair) this.mFilters.get(size)).second);
                    return this;
                }
            }
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 1741514098865223203L, 1, Long.valueOf(transitionInfo.getDebugId()));
        }
        return null;
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

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        IBinder iBinder3;
        char c;
        RemoteTransition remoteTransition = (RemoteTransition) this.mRequestedRemotes.get(iBinder2);
        if (remoteTransition == null) {
            return;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -3133681306894255105L, 0, String.valueOf(remoteTransition));
        }
        IRemoteTransition remoteTransition2 = remoteTransition.getRemoteTransition();
        if (remoteTransition2 == null) {
            return;
        }
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(transitionInfo, (CoreRune.FW_SHELL_TRANSITION_MERGE_TRANSFER && CoreRune.FW_SURFACE_DEBUG_APPLY) ? transaction.mDebugName : null, transaction, iBinder2, transitionFinishCallback);
        try {
            SurfaceControl.Transaction transactionCopyIfLocal = copyIfLocal(transaction, remoteTransition2);
            TransitionInfo transitionInfoLocalRemoteCopy = transactionCopyIfLocal == transaction ? transitionInfo : transitionInfo.localRemoteCopy();
            if (CoreRune.FW_SHELL_TRANSITION_MERGE) {
                if (!CoreRune.FW_SHELL_TRANSITION_BUG_FIX || !KeyguardTransitionHandler.handles(transitionInfo)) {
                    TransitionInfo transitionInfo2 = (TransitionInfo) this.mRequestedInfoList.get(iBinder2);
                    if (transitionInfo2 != null) {
                        if (TransitionUtil.isOpeningType(transitionInfo.getType()) == TransitionUtil.isOpeningType(transitionInfo2.getType())) {
                            int size = transitionInfo.getChanges().size() - 1;
                            c = 1;
                            int i = 0;
                            int i2 = 0;
                            while (true) {
                                if (size < 0) {
                                    if (i2 != i || i2 <= 0) {
                                        break;
                                    }
                                } else {
                                    TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(size);
                                    if (!change.hasFlags(64) && change.getTaskInfo() == null) {
                                        break;
                                    }
                                    i2++;
                                    if (change.hasFlags(4)) {
                                        i++;
                                    }
                                    size--;
                                }
                            }
                        } else {
                            c = 1;
                        }
                        ArrayList mergeableTasks = null;
                        for (int size2 = transitionInfo.getChanges().size() - 1; size2 >= 0; size2--) {
                            final TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(size2);
                            if (change2.hasFlags(64) && change2.getTaskIdForActivity() != -1 && change2.getMode() != 6 && !change2.hasFixedRotationTransform()) {
                                if (mergeableTasks == null) {
                                    mergeableTasks = TransitionUtil.getMergeableTasks(transitionInfo2);
                                }
                                if (mergeableTasks.stream().anyMatch(new Predicate() { // from class: com.android.wm.shell.transition.RemoteTransitionHandler$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Predicate
                                    public final boolean test(Object obj) {
                                        TransitionInfo.Change change3 = change2;
                                        boolean z = RemoteTransitionHandler.SUPPORT_MINIMIZE_REMOTE_TRANSITION;
                                        return ((TransitionInfo.Change) obj).getTaskInfo().taskId == change3.getTaskIdForActivity();
                                    }
                                })) {
                                }
                            }
                            if (transitionInfo.canMergeAsNoAnimation()) {
                                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[c]) {
                                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 5187832162080602232L, 0, String.valueOf(remoteTransition));
                                }
                                transaction.apply();
                                transitionInfo.releaseAnimSurfaces();
                                try {
                                    anonymousClass2.onTransitionFinished(null, null);
                                    return;
                                } catch (RemoteException unused) {
                                    return;
                                }
                            }
                            prepareMergeOrTransferAnimationIfNeeded(iBinder, remoteTransition, transitionInfo, transitionInfo2);
                            iBinder3 = iBinder;
                        }
                        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[c]) {
                            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -5160684155544653374L, 0, String.valueOf(remoteTransition));
                        }
                        mergeAnimationIfNeeded(transitionInfo, transaction, anonymousClass2);
                        return;
                    }
                } else if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -7675690055976013271L, 0, null);
                }
                iBinder3 = iBinder;
            } else {
                iBinder3 = iBinder;
            }
            remoteTransition2.mergeAnimation(iBinder3, transitionInfoLocalRemoteCopy, transactionCopyIfLocal, iBinder2, anonymousClass2);
        } catch (RemoteException e) {
            Log.e("ShellTransitions", "Error attempting to merge remote transition.", e);
        }
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

    /* JADX WARN: Removed duplicated region for block: B:26:0x0073  */
    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        TransitionInfo.Change change;
        if (!Transitions.SHELL_TRANSITIONS_ROTATION && TransitionUtil.hasDisplayChange(transitionInfo)) {
            this.mRequestedRemotes.remove(iBinder);
            if (CoreRune.FW_SHELL_TRANSITION_MERGE) {
                this.mRequestedInfoList.remove(iBinder);
                return false;
            }
        } else if (!CoreRune.MW_FREEFORM_SHELL_TRANSITION || SUPPORT_MINIMIZE_REMOTE_TRANSITION) {
            RemoteTransition remoteTransition = (RemoteTransition) this.mRequestedRemotes.get(iBinder);
            if (remoteTransition == null) {
                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -9146248499991136778L, 0, String.valueOf(transitionInfo));
                }
                int size = this.mFilters.size() - 1;
                while (true) {
                    if (size < 0) {
                        break;
                    }
                    if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                        ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 7551344402092077994L, 0, String.valueOf(this.mFilters.get(size)));
                    }
                    if (((TransitionFilter) ((Pair) this.mFilters.get(size)).first).matches(transitionInfo)) {
                        Slog.d("RemoteTransitionHandler", "Found filter" + this.mFilters.get(size));
                        remoteTransition = (RemoteTransition) ((Pair) this.mFilters.get(size)).second;
                        this.mRequestedRemotes.put(iBinder, remoteTransition);
                        break;
                    }
                    size--;
                }
            }
            RemoteTransition remoteTransition2 = remoteTransition;
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -957464960872531856L, 1, Long.valueOf(transitionInfo.getDebugId()), String.valueOf(remoteTransition2));
            }
            if (remoteTransition2 != null) {
                if (CoreRune.FW_SHELL_TRANSITION_MERGE) {
                    this.mRequestedInfoList.put(iBinder, transitionInfo);
                }
                AnonymousClass1 anonymousClass1 = new AnonymousClass1(remoteTransition2, transitionFinishCallback, transaction2, iBinder);
                SurfaceControl.Transaction transactionCopyIfLocal = copyIfLocal(transaction, remoteTransition2.getRemoteTransition());
                TransitionInfo transitionInfoLocalRemoteCopy = transactionCopyIfLocal == transaction ? transitionInfo : transitionInfo.localRemoteCopy();
                try {
                    if (CoreRune.FW_SHELL_TRANSITION_REMOTE) {
                        for (int size2 = transitionInfoLocalRemoteCopy.getChanges().size() - 1; size2 >= 0; size2--) {
                            TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfoLocalRemoteCopy.getChanges().get(size2);
                            if ((CoreRune.FW_REMOTE_WALLPAPER_ANIM && TransitionUtil.isWallpaper(change2) && change2.getParent() != null) || (CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION && MultiTaskingTransitionProvider.buildForceHideAnimationIfNeeded("RemoteTransitionHandler", change2, this.mMultiTaskingTransitions))) {
                                transitionInfoLocalRemoteCopy.getChanges().remove(change2);
                                if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                                    ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 8970935360874034163L, 1, Long.valueOf(transitionInfo.getDebugId()), String.valueOf(change2));
                                }
                            }
                        }
                    }
                    handleDeath(remoteTransition2.asBinder(), transitionFinishCallback);
                    remoteTransition2.getRemoteTransition().startAnimation(iBinder, transitionInfoLocalRemoteCopy, transactionCopyIfLocal, anonymousClass1);
                    transaction.clear();
                    Transitions.setRunningRemoteTransitionDelegate(remoteTransition2.getAppThread());
                    return true;
                } catch (RemoteException e) {
                    Log.e("ShellTransitions", "Error running remote transition.", e);
                    if (transactionCopyIfLocal != transaction) {
                        transactionCopyIfLocal.close();
                    }
                    transaction.apply();
                    unhandleDeath(remoteTransition2.asBinder(), transitionFinishCallback);
                    this.mRequestedRemotes.remove(iBinder);
                    if (CoreRune.FW_SHELL_TRANSITION_MERGE) {
                        this.mRequestedInfoList.remove(iBinder);
                    }
                    this.mMainExecutor.execute(new RemoteTransitionHandler$$ExternalSyntheticLambda1(transitionFinishCallback, 0));
                    return true;
                }
            }
        } else {
            int iM = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1);
            while (true) {
                if (iM < 0) {
                    change = null;
                    break;
                }
                change = (TransitionInfo.Change) transitionInfo.getChanges().get(iM);
                if (change.getMinimizeAnimState() != 0) {
                    break;
                }
                iM--;
            }
            if (change != null) {
                Log.d("RemoteTransitionHandler", "startAnimation: skipped by minimize, transit=" + iBinder + ", minimizeChange=" + change);
                this.mRequestedRemotes.remove(iBinder);
                if (CoreRune.FW_SHELL_TRANSITION_MERGE) {
                    this.mRequestedInfoList.remove(iBinder);
                    return false;
                }
            }
        }
        return false;
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
