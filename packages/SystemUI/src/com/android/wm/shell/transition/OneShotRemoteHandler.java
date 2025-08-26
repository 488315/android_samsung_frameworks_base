package com.android.wm.shell.transition;

import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.SurfaceControl;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.RemoteTransition;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowAnimationState;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.splitscreen.SplitScreenTransitions$TransitSession$$ExternalSyntheticLambda0;
import com.android.wm.shell.transition.OneShotRemoteHandler;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public class OneShotRemoteHandler implements Transitions.TransitionHandler {
    public ShellExecutor mAnimExecutor;
    public SplitScreenTransitions$TransitSession$$ExternalSyntheticLambda0 mFinishedCallbackForSplitScreen;
    public final ShellExecutor mMainExecutor;
    public MultiTaskingTransitions mMultiTaskingTransitions;
    public RemoteTransition mRemote;
    public SplitScreenTransitions$TransitSession$$ExternalSyntheticLambda0 mStartedCallbackForSplitScreen;
    public IBinder mTransition = null;

    /* renamed from: com.android.wm.shell.transition.OneShotRemoteHandler$1, reason: invalid class name */
    public class AnonymousClass1 extends IRemoteTransitionFinishedCallback.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final /* synthetic */ Transitions.TransitionFinishCallback val$finishCallback;
        public final /* synthetic */ TransitionInfo val$info;
        public final /* synthetic */ SurfaceControl.Transaction val$startT;

        public AnonymousClass1(TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, Transitions.TransitionFinishCallback transitionFinishCallback) {
            this.val$info = transitionInfo;
            this.val$startT = transaction;
            this.val$finishCallback = transitionFinishCallback;
        }

        public final void onTransitionFinished(final WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -705170416625176567L, 4, String.valueOf(OneShotRemoteHandler.this.mRemote), Long.valueOf(this.val$info.getDebugId()));
            }
            this.val$startT.clear();
            ShellExecutor shellExecutor = OneShotRemoteHandler.this.mMainExecutor;
            final Transitions.TransitionFinishCallback transitionFinishCallback = this.val$finishCallback;
            shellExecutor.execute(new Runnable() { // from class: com.android.wm.shell.transition.OneShotRemoteHandler$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    Transitions.TransitionFinishCallback transitionFinishCallback2 = transitionFinishCallback;
                    WindowContainerTransaction windowContainerTransaction2 = windowContainerTransaction;
                    int i = OneShotRemoteHandler.AnonymousClass1.$r8$clinit;
                    transitionFinishCallback2.onTransitionFinished(windowContainerTransaction2);
                }
            });
        }
    }

    /* renamed from: com.android.wm.shell.transition.OneShotRemoteHandler$2, reason: invalid class name */
    public class AnonymousClass2 extends IRemoteTransitionFinishedCallback.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final /* synthetic */ Transitions.TransitionFinishCallback val$finishCallback;
        public final /* synthetic */ SurfaceControl.Transaction val$finishTransaction;
        public final /* synthetic */ TransitionInfo val$info;
        public final /* synthetic */ IBinder.DeathRecipient val$remoteDied;

        public AnonymousClass2(TransitionInfo transitionInfo, IBinder.DeathRecipient deathRecipient, SurfaceControl.Transaction transaction, Transitions.TransitionFinishCallback transitionFinishCallback) {
            this.val$info = transitionInfo;
            this.val$remoteDied = deathRecipient;
            this.val$finishTransaction = transaction;
            this.val$finishCallback = transitionFinishCallback;
        }

        public final void onTransitionFinished(final WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
                ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -1920116838089606609L, 4, String.valueOf(OneShotRemoteHandler.this.mRemote), Long.valueOf(this.val$info.getDebugId()));
            }
            if (OneShotRemoteHandler.this.mRemote.asBinder() != null) {
                OneShotRemoteHandler.this.mRemote.asBinder().unlinkToDeath(this.val$remoteDied, 0);
            }
            SurfaceControl.Transaction transaction2 = this.val$finishTransaction;
            if (transaction2 != null && transaction != null) {
                transaction2.merge(transaction);
            }
            ShellExecutor shellExecutor = OneShotRemoteHandler.this.mMainExecutor;
            final Transitions.TransitionFinishCallback transitionFinishCallback = this.val$finishCallback;
            shellExecutor.execute(new Runnable() { // from class: com.android.wm.shell.transition.OneShotRemoteHandler$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    OneShotRemoteHandler.AnonymousClass2 anonymousClass2 = this.f$0;
                    Transitions.TransitionFinishCallback transitionFinishCallback2 = transitionFinishCallback;
                    WindowContainerTransaction windowContainerTransaction2 = windowContainerTransaction;
                    int i = OneShotRemoteHandler.AnonymousClass2.$r8$clinit;
                    if (CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION) {
                        MultiTaskingTransitionProvider.cancelForceHideAnimationsIfNeeded("ShellTransitions", OneShotRemoteHandler.this.mAnimExecutor);
                    }
                    SplitScreenTransitions$TransitSession$$ExternalSyntheticLambda0 splitScreenTransitions$TransitSession$$ExternalSyntheticLambda0 = OneShotRemoteHandler.this.mFinishedCallbackForSplitScreen;
                    if (splitScreenTransitions$TransitSession$$ExternalSyntheticLambda0 != null) {
                        splitScreenTransitions$TransitSession$$ExternalSyntheticLambda0.run();
                        OneShotRemoteHandler.this.mFinishedCallbackForSplitScreen = null;
                    }
                    transitionFinishCallback2.onTransitionFinished(windowContainerTransaction2);
                    OneShotRemoteHandler.this.mRemote = null;
                }
            });
        }
    }

    public OneShotRemoteHandler(ShellExecutor shellExecutor, RemoteTransition remoteTransition) {
        this.mMainExecutor = shellExecutor;
        this.mRemote = remoteTransition;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        RemoteTransition remoteTransition = transitionRequestInfo.getRemoteTransition();
        if ((remoteTransition != null ? remoteTransition.getRemoteTransition() : null) != this.mRemote.getRemoteTransition()) {
            return null;
        }
        this.mTransition = iBinder;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 7971331547267873781L, 0, String.valueOf(iBinder), String.valueOf(remoteTransition));
        }
        return new WindowContainerTransaction();
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -8058552917378778392L, 4, String.valueOf(this.mRemote), Long.valueOf(transitionInfo.getDebugId()));
        }
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(transitionInfo, transaction, transitionFinishCallback);
        try {
            SurfaceControl.Transaction transactionCopyIfLocal = RemoteTransitionHandler.copyIfLocal(transaction, this.mRemote.getRemoteTransition());
            if (transactionCopyIfLocal != transaction) {
                transitionInfo = transitionInfo.localRemoteCopy();
            }
            this.mRemote.getRemoteTransition().mergeAnimation(iBinder, transitionInfo, transactionCopyIfLocal, iBinder2, anonymousClass1);
        } catch (RemoteException e) {
            Log.e("ShellTransitions", "Error merging remote transition.", e);
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
        try {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[0]) {
                ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -3477481236100005912L, 0, String.valueOf(this.mRemote));
            }
            this.mRemote.getRemoteTransition().onTransitionConsumed(iBinder, z);
        } catch (RemoteException e) {
            Log.e("ShellTransitions", "Error calling onTransitionConsumed()", e);
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        for (int iM = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); iM >= 0; iM--) {
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(iM);
            if (change.getMode() == 6 && change.hasFlags(32)) {
                int endDisplayId = change.getEndDisplayId();
                DesktopStateImpl.Companion.getClass();
                if (DesktopStateImpl.Companion.inDesktopWindowing(endDisplayId)) {
                    Log.d("ShellTransitions", "Skip OnShotRemoteHandler by display change.");
                    return false;
                }
            }
        }
        if (this.mTransition != iBinder) {
            return false;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 4224371256409735028L, 4, String.valueOf(this.mRemote), Long.valueOf(transitionInfo.getDebugId()));
        }
        OneShotRemoteHandler$$ExternalSyntheticLambda0 oneShotRemoteHandler$$ExternalSyntheticLambda0 = new OneShotRemoteHandler$$ExternalSyntheticLambda0(this, transitionFinishCallback);
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(transitionInfo, oneShotRemoteHandler$$ExternalSyntheticLambda0, transaction2, transitionFinishCallback);
        Transitions.setRunningRemoteTransitionDelegate(this.mRemote.getAppThread());
        try {
            SplitScreenTransitions$TransitSession$$ExternalSyntheticLambda0 splitScreenTransitions$TransitSession$$ExternalSyntheticLambda0 = this.mStartedCallbackForSplitScreen;
            if (splitScreenTransitions$TransitSession$$ExternalSyntheticLambda0 != null) {
                splitScreenTransitions$TransitSession$$ExternalSyntheticLambda0.run();
                this.mStartedCallbackForSplitScreen = null;
            }
            if (CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION && this.mMultiTaskingTransitions != null) {
                for (int size = transitionInfo.getChanges().size() - 1; size >= 0; size--) {
                    TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(size);
                    if (MultiTaskingTransitionProvider.buildForceHideAnimationIfNeeded("ShellTransitions", change2, this.mMultiTaskingTransitions)) {
                        transitionInfo.getChanges().remove(change2);
                        Log.d("ShellTransitions", "startAnimation: remove from remoteInfo, " + change2);
                    }
                }
            }
            if (this.mRemote.asBinder() != null) {
                this.mRemote.asBinder().linkToDeath(oneShotRemoteHandler$$ExternalSyntheticLambda0, 0);
            }
            SurfaceControl.Transaction transactionCopyIfLocal = RemoteTransitionHandler.copyIfLocal(transaction, this.mRemote.getRemoteTransition());
            if (transactionCopyIfLocal != transaction) {
                transitionInfo = transitionInfo.localRemoteCopy();
            }
            this.mRemote.getRemoteTransition().startAnimation(iBinder, transitionInfo, transactionCopyIfLocal, anonymousClass2);
            transaction.clear();
            return true;
        } catch (RemoteException e) {
            Log.e("ShellTransitions", "Error running remote transition.", e);
            if (this.mRemote.asBinder() != null) {
                this.mRemote.asBinder().unlinkToDeath(oneShotRemoteHandler$$ExternalSyntheticLambda0, 0);
            }
            transitionFinishCallback.onTransitionFinished(null);
            this.mRemote = null;
            return true;
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean takeOverAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, Transitions.TransitionFinishCallback transitionFinishCallback, WindowAnimationState[] windowAnimationStateArr) throws RemoteException {
        if (this.mTransition != iBinder) {
            return false;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_RECENTS_TRANSITION_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_RECENTS_TRANSITION, 1449980378740176196L, 4, String.valueOf(this.mRemote), Long.valueOf(transitionInfo.getDebugId()));
        }
        OneShotRemoteHandler$$ExternalSyntheticLambda0 oneShotRemoteHandler$$ExternalSyntheticLambda0 = new OneShotRemoteHandler$$ExternalSyntheticLambda0(this, transitionFinishCallback);
        AnonymousClass2 anonymousClass2 = new AnonymousClass2(transitionInfo, oneShotRemoteHandler$$ExternalSyntheticLambda0, null, transitionFinishCallback);
        Transitions.setRunningRemoteTransitionDelegate(this.mRemote.getAppThread());
        try {
            if (this.mRemote.asBinder() != null) {
                this.mRemote.asBinder().linkToDeath(oneShotRemoteHandler$$ExternalSyntheticLambda0, 0);
            }
            SurfaceControl.Transaction transactionCopyIfLocal = RemoteTransitionHandler.copyIfLocal(transaction, this.mRemote.getRemoteTransition());
            this.mRemote.getRemoteTransition().takeOverAnimation(iBinder, transactionCopyIfLocal == transaction ? transitionInfo : transitionInfo.localRemoteCopy(), transactionCopyIfLocal, anonymousClass2, windowAnimationStateArr);
            transaction.clear();
            return true;
        } catch (RemoteException e) {
            Log.e("ShellTransitions", "Error running remote transition takeover.", e);
            if (this.mRemote.asBinder() != null) {
                this.mRemote.asBinder().unlinkToDeath(oneShotRemoteHandler$$ExternalSyntheticLambda0, 0);
            }
            transitionFinishCallback.onTransitionFinished(null);
            this.mRemote = null;
            return false;
        }
    }

    public final String toString() {
        return "OneShotRemoteHandler:" + this.mRemote.getDebugName() + ":" + this.mRemote.getRemoteTransition();
    }
}
