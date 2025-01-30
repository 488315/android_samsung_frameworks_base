package com.android.wm.shell.transition;

import android.animation.Animator;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Debug;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.util.Slog;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.RemoteTransition;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.protolog.ShellProtoLogCache;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.protolog.ShellProtoLogImpl;
import com.android.wm.shell.transition.OneShotRemoteHandler;
import com.android.wm.shell.transition.Transitions;
import com.android.systemui.R;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class OneShotRemoteHandler implements Transitions.TransitionHandler {
    public ShellExecutor mAnimExecutor;
    public Runnable mFinishedCallbackForSplitScreen;
    public final ShellExecutor mMainExecutor;
    public MultiTaskingTransitionProvider mMultiTaskingTransitions;
    public final RemoteTransition mRemote;
    public Runnable mStartedCallbackForSplitScreen;
    public IBinder mTransition = null;
    public final ArrayList mForceHidingAnimators = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.transition.OneShotRemoteHandler$1 */
    public final class C41461 extends IRemoteTransitionFinishedCallback.Stub {
        public final /* synthetic */ Transitions.TransitionFinishCallback val$finishCallback;
        public final /* synthetic */ SurfaceControl.Transaction val$finishTransaction;
        public final /* synthetic */ IBinder.DeathRecipient val$remoteDied;

        public C41461(IBinder.DeathRecipient deathRecipient, SurfaceControl.Transaction transaction, Transitions.TransitionFinishCallback transitionFinishCallback) {
            this.val$remoteDied = deathRecipient;
            this.val$finishTransaction = transaction;
            this.val$finishCallback = transitionFinishCallback;
        }

        public final void onTransitionFinished(final WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) {
            if (OneShotRemoteHandler.this.mRemote.asBinder() != null) {
                OneShotRemoteHandler.this.mRemote.asBinder().unlinkToDeath(this.val$remoteDied, 0);
            }
            if (transaction != null) {
                this.val$finishTransaction.merge(transaction);
            }
            ShellExecutor shellExecutor = OneShotRemoteHandler.this.mMainExecutor;
            final Transitions.TransitionFinishCallback transitionFinishCallback = this.val$finishCallback;
            ((HandlerExecutor) shellExecutor).execute(new Runnable() { // from class: com.android.wm.shell.transition.OneShotRemoteHandler$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    OneShotRemoteHandler.C41461 c41461 = OneShotRemoteHandler.C41461.this;
                    Transitions.TransitionFinishCallback transitionFinishCallback2 = transitionFinishCallback;
                    WindowContainerTransaction windowContainerTransaction2 = windowContainerTransaction;
                    c41461.getClass();
                    if (CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION) {
                        OneShotRemoteHandler oneShotRemoteHandler = OneShotRemoteHandler.this;
                        ArrayList arrayList = oneShotRemoteHandler.mForceHidingAnimators;
                        if (!arrayList.isEmpty()) {
                            Slog.d("ShellTransitions", "cancelForceHideAnimationsIfNeeded: animators=" + new ArrayList(arrayList) + ", Callers=" + Debug.getCallers(5));
                            Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                ((HandlerExecutor) oneShotRemoteHandler.mAnimExecutor).execute(new OneShotRemoteHandler$$ExternalSyntheticLambda2((Animator) it.next(), 1));
                            }
                        }
                    }
                    Runnable runnable = OneShotRemoteHandler.this.mFinishedCallbackForSplitScreen;
                    if (runnable != null) {
                        runnable.run();
                        OneShotRemoteHandler.this.mFinishedCallbackForSplitScreen = null;
                    }
                    transitionFinishCallback2.onTransitionFinished(windowContainerTransaction2, null);
                }
            });
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.transition.OneShotRemoteHandler$2 */
    public final class C41472 extends IRemoteTransitionFinishedCallback.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final /* synthetic */ Transitions.TransitionFinishCallback val$finishCallback;
        public final /* synthetic */ SurfaceControl.Transaction val$t;

        public C41472(SurfaceControl.Transaction transaction, Transitions.TransitionFinishCallback transitionFinishCallback) {
            this.val$t = transaction;
            this.val$finishCallback = transitionFinishCallback;
        }

        public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) {
            this.val$t.clear();
            HandlerExecutor handlerExecutor = (HandlerExecutor) OneShotRemoteHandler.this.mMainExecutor;
            handlerExecutor.execute(new OneShotRemoteHandler$$ExternalSyntheticLambda1(1, this.val$finishCallback, windowContainerTransaction));
        }
    }

    public OneShotRemoteHandler(ShellExecutor shellExecutor, RemoteTransition remoteTransition) {
        this.mMainExecutor = shellExecutor;
        this.mRemote = remoteTransition;
    }

    public final boolean buildForceHideAnimationIfNeeded(TransitionInfo.Change change) {
        int forceHidingTransit = change.getForceHidingTransit();
        int i = 0;
        if (forceHidingTransit == 0) {
            return false;
        }
        if (change.getFreeformStashScale() != 1.0f) {
            return true;
        }
        SurfaceControl leash = change.getLeash();
        String str = "leash=" + leash + ", " + MultiWindowManager.forceHidingTransitToString(forceHidingTransit);
        int i2 = forceHidingTransit == 1 ? R.anim.freeform_window_force_hide_enter : R.anim.freeform_window_force_hide_exit_delay;
        Slog.d("ShellTransitions", "buildForceHideAnimationIfNeeded: " + str);
        Rect endAbsBounds = change.getEndAbsBounds();
        Animation loadAnimationFromResources = this.mMultiTaskingTransitions.loadAnimationFromResources(i2, endAbsBounds);
        loadAnimationFromResources.setInterpolator(InterpolatorUtils.SINE_OUT_60);
        Point point = new Point(endAbsBounds.left, endAbsBounds.top);
        Rect rect = new Rect(endAbsBounds);
        rect.offsetTo(0, 0);
        this.mMultiTaskingTransitions.buildSurfaceAnimator(this.mForceHidingAnimators, loadAnimationFromResources, leash, new OneShotRemoteHandler$$ExternalSyntheticLambda1(i, this, str), point, rect, true);
        return true;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        RemoteTransition remoteTransition = transitionRequestInfo.getRemoteTransition();
        if ((remoteTransition != null ? remoteTransition.getRemoteTransition() : null) != this.mRemote.getRemoteTransition()) {
            return null;
        }
        this.mTransition = iBinder;
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 967375804, 0, "RemoteTransition directly requested for %s: %s", String.valueOf(iBinder), String.valueOf(remoteTransition));
        }
        return new WindowContainerTransaction();
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        RemoteTransition remoteTransition = this.mRemote;
        C41472 c41472 = new C41472(transaction, transitionFinishCallback);
        try {
            SurfaceControl.Transaction copyIfLocal = RemoteTransitionHandler.copyIfLocal(transaction, remoteTransition.getRemoteTransition());
            if (copyIfLocal != transaction) {
                transitionInfo = transitionInfo.localRemoteCopy();
            }
            remoteTransition.getRemoteTransition().mergeAnimation(iBinder, transitionInfo, copyIfLocal, iBinder2, c41472);
        } catch (RemoteException e) {
            Log.e("ShellTransitions", "Error merging remote transition.", e);
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, final Transitions.TransitionFinishCallback transitionFinishCallback) {
        if (this.mTransition != iBinder) {
            return false;
        }
        boolean z = ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled;
        RemoteTransition remoteTransition = this.mRemote;
        if (z) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 66677149, 4, "Using registered One-shot remote transition %s for #%d.", String.valueOf(remoteTransition), Long.valueOf(transitionInfo.getDebugId()));
        }
        IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() { // from class: com.android.wm.shell.transition.OneShotRemoteHandler$$ExternalSyntheticLambda0
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                OneShotRemoteHandler oneShotRemoteHandler = OneShotRemoteHandler.this;
                Transitions.TransitionFinishCallback transitionFinishCallback2 = transitionFinishCallback;
                oneShotRemoteHandler.getClass();
                Log.e("ShellTransitions", "Remote transition died, finishing");
                ((HandlerExecutor) oneShotRemoteHandler.mMainExecutor).execute(new OneShotRemoteHandler$$ExternalSyntheticLambda2(transitionFinishCallback2, 0));
            }
        };
        C41461 c41461 = new C41461(deathRecipient, transaction2, transitionFinishCallback);
        Transitions.setRunningRemoteTransitionDelegate(remoteTransition.getAppThread());
        try {
            Runnable runnable = this.mStartedCallbackForSplitScreen;
            if (runnable != null) {
                runnable.run();
                this.mStartedCallbackForSplitScreen = null;
            }
            if (CoreRune.MW_FREEFORM_FORCE_HIDING_TRANSITION) {
                for (int size = transitionInfo.getChanges().size() - 1; size >= 0; size--) {
                    TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(size);
                    if (buildForceHideAnimationIfNeeded(change)) {
                        transitionInfo.getChanges().remove(change);
                        Log.d("ShellTransitions", "startAnimation: remove from remoteInfo, " + change);
                    }
                }
            }
            if (remoteTransition.asBinder() != null) {
                remoteTransition.asBinder().linkToDeath(deathRecipient, 0);
            }
            SurfaceControl.Transaction copyIfLocal = RemoteTransitionHandler.copyIfLocal(transaction, remoteTransition.getRemoteTransition());
            if (copyIfLocal != transaction) {
                transitionInfo = transitionInfo.localRemoteCopy();
            }
            remoteTransition.getRemoteTransition().startAnimation(iBinder, transitionInfo, copyIfLocal, c41461);
            transaction.clear();
        } catch (RemoteException e) {
            Log.e("ShellTransitions", "Error running remote transition.", e);
            if (remoteTransition.asBinder() != null) {
                remoteTransition.asBinder().unlinkToDeath(deathRecipient, 0);
            }
            transitionFinishCallback.onTransitionFinished(null, null);
        }
        return true;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("OneShotRemoteHandler:");
        RemoteTransition remoteTransition = this.mRemote;
        sb.append(remoteTransition.getDebugName());
        sb.append(":");
        sb.append(remoteTransition.getRemoteTransition());
        return sb.toString();
    }
}
