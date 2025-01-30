package com.android.wm.shell.transition;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.window.IRemoteTransition;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.RemoteTransition;
import android.window.TransitionFilter;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.util.TransitionUtil;
import com.android.systemui.R;
import com.android.systemui.keyguard.KeyguardService$$ExternalSyntheticOutline0;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RemoteTransitionHandler implements Transitions.TransitionHandler {
    public static final boolean SUPPORT_MINIMIZE_REMOTE_TRANSITION = SystemProperties.getBoolean("persist.mt.debug.minimize_remote_transition", false);
    public ShellExecutor mAnimExecutor;
    public final ShellExecutor mMainExecutor;
    public MultiTaskingTransitionProvider mMultiTaskingTransitions;
    public final ArrayMap mRequestedRemotes = new ArrayMap();
    public final ArrayList mFilters = new ArrayList();
    public final ArrayMap mDeathHandlers = new ArrayMap();
    public final ArrayMap mRequestedInfos = new ArrayMap();
    public final ArrayList mForceHidingAnimators = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.transition.RemoteTransitionHandler$1 */
    public final class C41481 extends IRemoteTransitionFinishedCallback.Stub {
        public final /* synthetic */ Transitions.TransitionFinishCallback val$finishCallback;
        public final /* synthetic */ SurfaceControl.Transaction val$finishTransaction;
        public final /* synthetic */ RemoteTransition val$remote;
        public final /* synthetic */ IBinder val$transition;

        public C41481(RemoteTransition remoteTransition, Transitions.TransitionFinishCallback transitionFinishCallback, SurfaceControl.Transaction transaction, IBinder iBinder) {
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
            ((HandlerExecutor) RemoteTransitionHandler.this.mMainExecutor).execute(new RemoteTransitionHandler$1$$ExternalSyntheticLambda0(this, this.val$transition, this.val$finishCallback, windowContainerTransaction, 0));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.transition.RemoteTransitionHandler$2 */
    public final class C41492 extends IRemoteTransitionFinishedCallback.Stub {
        public final /* synthetic */ Transitions.TransitionFinishCallback val$finishCallback;
        public final /* synthetic */ TransitionInfo val$info;
        public final /* synthetic */ IBinder val$mergeTarget;
        public final /* synthetic */ SurfaceControl.Transaction val$t;

        public C41492(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder, Transitions.TransitionFinishCallback transitionFinishCallback) {
            this.val$info = transitionInfo;
            this.val$t = transaction;
            this.val$mergeTarget = iBinder;
            this.val$finishCallback = transitionFinishCallback;
        }

        public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) {
            if (!CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE_TRANSFER || !this.val$info.canTransferAnimation()) {
                this.val$t.clear();
            }
            ((HandlerExecutor) RemoteTransitionHandler.this.mMainExecutor).execute(new RemoteTransitionHandler$1$$ExternalSyntheticLambda0(this, this.val$mergeTarget, this.val$finishCallback, windowContainerTransaction, 1));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class RemoteDeathHandler implements IBinder.DeathRecipient {
        public final IBinder mRemote;
        public final ArrayList mPendingFinishCallbacks = new ArrayList();
        public int mUsers = 0;

        public RemoteDeathHandler(IBinder iBinder) {
            this.mRemote = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            ((HandlerExecutor) RemoteTransitionHandler.this.mMainExecutor).execute(new RemoteTransitionHandler$$ExternalSyntheticLambda0(this, 2));
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

    public static void mergeActivityOnly(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, C41492 c41492) {
        for (int i = 0; i < transitionInfo.getChanges().size(); i++) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
            if (TransitionUtil.isOpeningType(change.getMode())) {
                transaction.show(change.getLeash());
                transaction.setAlpha(change.getLeash(), 1.0f);
            } else if (TransitionUtil.isClosingType(change.getMode())) {
                transaction.hide(change.getLeash());
            }
        }
        transaction.apply();
        transitionInfo.releaseAnimSurfaces();
        try {
            c41492.onTransitionFinished(null, null);
        } catch (RemoteException unused) {
        }
    }

    public final boolean buildForceHideAnimationIfNeeded(TransitionInfo.Change change) {
        int forceHidingTransit = change.getForceHidingTransit();
        if (forceHidingTransit == 0) {
            return false;
        }
        if (change.getFreeformStashScale() != 1.0f) {
            return true;
        }
        SurfaceControl leash = change.getLeash();
        final String str = "leash=" + leash + ", " + MultiWindowManager.forceHidingTransitToString(forceHidingTransit);
        int i = forceHidingTransit == 1 ? R.anim.freeform_window_force_hide_enter : R.anim.freeform_window_force_hide_exit_delay;
        Slog.d("RemoteTransitionHandler", "buildForceHideAnimationIfNeeded: " + str);
        Rect endAbsBounds = change.getEndAbsBounds();
        Animation loadAnimationFromResources = this.mMultiTaskingTransitions.loadAnimationFromResources(i, endAbsBounds);
        loadAnimationFromResources.setInterpolator(InterpolatorUtils.SINE_OUT_60);
        Point point = new Point(endAbsBounds.left, endAbsBounds.top);
        Rect rect = new Rect(endAbsBounds);
        rect.offsetTo(0, 0);
        this.mMultiTaskingTransitions.buildSurfaceAnimator(this.mForceHidingAnimators, loadAnimationFromResources, leash, new Runnable() { // from class: com.android.wm.shell.transition.RemoteTransitionHandler$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                RemoteTransitionHandler remoteTransitionHandler = RemoteTransitionHandler.this;
                String str2 = str;
                remoteTransitionHandler.getClass();
                Slog.d("RemoteTransitionHandler", "onForceHideAnimationFinished: " + str2 + ", num_remains=" + remoteTransitionHandler.mForceHidingAnimators.size());
            }
        }, point, rect, true);
        return true;
    }

    public final void handleDeath(IBinder iBinder, Transitions.TransitionFinishCallback transitionFinishCallback) {
        synchronized (this.mDeathHandlers) {
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
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        RemoteTransition remoteTransition = transitionRequestInfo.getRemoteTransition();
        if (remoteTransition == null) {
            return null;
        }
        this.mRequestedRemotes.put(iBinder, remoteTransition);
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 214412327, 0, "RemoteTransition directly requested for %s: %s", String.valueOf(iBinder), String.valueOf(remoteTransition));
        }
        return new WindowContainerTransaction();
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        TransitionInfo transitionInfo2;
        boolean z;
        RemoteTransition remoteTransition = (RemoteTransition) this.mRequestedRemotes.get(iBinder2);
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && remoteTransition == null) {
            return;
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE && transitionInfo.canSkipMergeAnimation()) {
            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -123874232, 0, "   Skip merge into remote: %s", String.valueOf(remoteTransition));
                return;
            }
            return;
        }
        IRemoteTransition remoteTransition2 = remoteTransition.getRemoteTransition();
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -138583547, 0, "   Merge into remote: %s", String.valueOf(remoteTransition));
        }
        if (remoteTransition2 == null) {
            return;
        }
        C41492 c41492 = new C41492(transitionInfo, transaction, iBinder2, transitionFinishCallback);
        try {
            SurfaceControl.Transaction copyIfLocal = copyIfLocal(transaction, remoteTransition2);
            TransitionInfo localRemoteCopy = copyIfLocal == transaction ? transitionInfo : transitionInfo.localRemoteCopy();
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX) {
                int size = transitionInfo.getChanges().size() - 1;
                int i = 0;
                int i2 = 0;
                while (true) {
                    if (size >= 0) {
                        TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(size);
                        if (change.getMode() == 6 || !change.hasFlags(QuickStepContract.SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED)) {
                            break;
                        }
                        i++;
                        if (change.hasFlags(4)) {
                            i2++;
                        }
                        size--;
                    } else if (i == i2 && i > 0) {
                        z = true;
                    }
                }
                z = false;
                if (z) {
                    if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                        ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 1907644622, 0, "   Merge translucent activity transit into remote: %s", String.valueOf(remoteTransition));
                    }
                    mergeActivityOnly(transitionInfo, transaction, c41492);
                    return;
                }
            }
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE && (transitionInfo2 = (TransitionInfo) this.mRequestedInfos.get(iBinder2)) != null) {
                prepareMergeOrTransferAnimationIfNeeded(iBinder, remoteTransition, transitionInfo, transitionInfo2);
            }
            if (!(CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE_TRANSFER && transitionInfo.canTransferAnimation()) && (!CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE || transitionInfo.canMergeAnimation())) {
                remoteTransition2.mergeAnimation(iBinder, localRemoteCopy, copyIfLocal, iBinder2, c41492);
            } else {
                remoteTransition2.mergeAnimation(iBinder, localRemoteCopy, new SurfaceControl.Transaction(), iBinder2, c41492);
            }
        } catch (RemoteException e) {
            Log.e("ShellTransitions", "Error attempting to merge remote transition.", e);
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
        this.mRequestedRemotes.remove(iBinder);
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE) {
            this.mRequestedInfos.remove(iBinder);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:64:0x0130, code lost:
    
        r11.setCanMergeAnimation();
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0133, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void prepareMergeOrTransferAnimationIfNeeded(IBinder iBinder, RemoteTransition remoteTransition, TransitionInfo transitionInfo, TransitionInfo transitionInfo2) {
        boolean z = false;
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE_TRANSFER) {
            if (((transitionInfo.getFlags() & 128) == 0 || remoteTransition.getAppThread() == null || transitionInfo.getRemoteAppThread() == null || remoteTransition.getAppThread().asBinder() != transitionInfo.getRemoteAppThread().asBinder()) ? false : true) {
                transitionInfo.setCanTransferAnimation();
                return;
            }
        }
        ArrayList mergeableTasks = TransitionUtil.getMergeableTasks(transitionInfo2);
        ArrayList mergeableTasks2 = TransitionUtil.getMergeableTasks(transitionInfo);
        if (!mergeableTasks.isEmpty() && !mergeableTasks2.isEmpty() && mergeableTasks.size() == mergeableTasks2.size()) {
            int i = 0;
            while (true) {
                if (i < mergeableTasks.size()) {
                    TransitionInfo.Change change = (TransitionInfo.Change) mergeableTasks.get(i);
                    TransitionInfo.Change change2 = (TransitionInfo.Change) mergeableTasks2.get(i);
                    if (change.getTaskInfo().taskId == change2.getTaskInfo().taskId) {
                        SurfaceControl leash = change.getLeash();
                        SurfaceControl leash2 = change2.getLeash();
                        if (leash == null || leash2 == null || !leash.isSameSurface(leash2)) {
                            break;
                        } else {
                            i++;
                        }
                    } else {
                        break;
                    }
                } else {
                    RemoteTransition remoteTransition2 = (RemoteTransition) this.mRequestedRemotes.get(iBinder);
                    if (remoteTransition2 == null) {
                        ArrayList arrayList = this.mFilters;
                        int size = arrayList.size() - 1;
                        while (true) {
                            if (size < 0) {
                                break;
                            }
                            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 71682279, 0, "canMergeAnimationIfNeeded, Checking filter %s", String.valueOf(arrayList.get(size)));
                            }
                            if (((TransitionFilter) ((Pair) arrayList.get(size)).first).matches(transitionInfo)) {
                                Slog.d("RemoteTransitionHandler", "canMergeAnimationIfNeeded, Found filter" + arrayList.get(size));
                                remoteTransition2 = (RemoteTransition) ((Pair) arrayList.get(size)).second;
                                break;
                            }
                            size--;
                        }
                    } else if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                        ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1002355739, 0, "canMergeAnimationIfNeeded, Request to merge transit=%s", String.valueOf(remoteTransition2));
                    }
                    if (remoteTransition.getAppThread() != null && remoteTransition2 != null && remoteTransition2.getAppThread() != null && remoteTransition.getAppThread().asBinder() == remoteTransition2.getAppThread().asBinder()) {
                        z = true;
                    }
                }
            }
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        TransitionInfo.Change change;
        TransitionInfo transitionInfo2 = transitionInfo;
        boolean z = Transitions.SHELL_TRANSITIONS_ROTATION;
        ArrayMap arrayMap = this.mRequestedInfos;
        ArrayMap arrayMap2 = this.mRequestedRemotes;
        if (!z && TransitionUtil.hasDisplayChange(transitionInfo)) {
            arrayMap2.remove(iBinder);
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE) {
                arrayMap.remove(iBinder);
            }
            return false;
        }
        if (CoreRune.MW_FREEFORM_SHELL_TRANSITION && !SUPPORT_MINIMIZE_REMOTE_TRANSITION) {
            int m136m = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo2, 1);
            while (true) {
                if (m136m < 0) {
                    change = null;
                    break;
                }
                change = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m);
                if ((!CoreRune.MT_NEW_DEX_SHELL_TRANSITION || !change.getConfiguration().isNewDexMode() || change.getTaskInfo() == null || change.getTaskInfo().getWindowingMode() != 1) && change.getMinimizeAnimState() != 0) {
                    break;
                }
                m136m--;
            }
            if (change != null) {
                Log.d("RemoteTransitionHandler", "startAnimation: skipped by minimize, transit=" + iBinder + ", minimizeChange=" + change);
                arrayMap2.remove(iBinder);
                if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE) {
                    arrayMap.remove(iBinder);
                }
                return false;
            }
        }
        RemoteTransition remoteTransition = (RemoteTransition) arrayMap2.get(iBinder);
        if (remoteTransition == null) {
            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 1707345146, 0, "Transition doesn't have explicit remote, search filters for match for %s", String.valueOf(transitionInfo));
            }
            ArrayList arrayList = this.mFilters;
            int size = arrayList.size() - 1;
            while (true) {
                if (size < 0) {
                    break;
                }
                if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                    ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 990371881, 0, " Checking filter %s", String.valueOf(arrayList.get(size)));
                }
                if (((TransitionFilter) ((Pair) arrayList.get(size)).first).matches(transitionInfo2)) {
                    Slog.d("RemoteTransitionHandler", "Found filter" + arrayList.get(size));
                    remoteTransition = (RemoteTransition) ((Pair) arrayList.get(size)).second;
                    arrayMap2.put(iBinder, remoteTransition);
                    break;
                }
                size--;
            }
        }
        RemoteTransition remoteTransition2 = remoteTransition;
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -757785518, 1, " Delegate animation for #%d to %s", Long.valueOf(transitionInfo.getDebugId()), String.valueOf(remoteTransition2));
        }
        if (remoteTransition2 == null) {
            return false;
        }
        if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE) {
            arrayMap.put(iBinder, transitionInfo2);
        }
        C41481 c41481 = new C41481(remoteTransition2, transitionFinishCallback, transaction2, iBinder);
        SurfaceControl.Transaction copyIfLocal = copyIfLocal(transaction, remoteTransition2.getRemoteTransition());
        if (copyIfLocal != transaction) {
            transitionInfo2 = transitionInfo.localRemoteCopy();
        }
        try {
            if (CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION) {
                for (int size2 = transitionInfo2.getChanges().size() - 1; size2 >= 0; size2--) {
                    TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo2.getChanges().get(size2);
                    if (buildForceHideAnimationIfNeeded(change2)) {
                        transitionInfo2.getChanges().remove(change2);
                        Log.d("RemoteTransitionHandler", "startAnimation: remove from remoteInfo, " + change2);
                    }
                }
            }
            handleDeath(remoteTransition2.asBinder(), transitionFinishCallback);
            remoteTransition2.getRemoteTransition().startAnimation(iBinder, transitionInfo2, copyIfLocal, c41481);
            transaction.clear();
            Transitions.setRunningRemoteTransitionDelegate(remoteTransition2.getAppThread());
            return true;
        } catch (RemoteException e) {
            Log.e("ShellTransitions", "Error running remote transition.", e);
            if (copyIfLocal != transaction) {
                copyIfLocal.close();
            }
            transaction.apply();
            unhandleDeath(remoteTransition2.asBinder(), transitionFinishCallback);
            arrayMap2.remove(iBinder);
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE) {
                arrayMap.remove(iBinder);
            }
            ((HandlerExecutor) this.mMainExecutor).execute(new RemoteTransitionHandler$$ExternalSyntheticLambda0(transitionFinishCallback, 0));
            return true;
        }
    }

    public final void unhandleDeath(IBinder iBinder, Transitions.TransitionFinishCallback transitionFinishCallback) {
        synchronized (this.mDeathHandlers) {
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
        }
    }
}
