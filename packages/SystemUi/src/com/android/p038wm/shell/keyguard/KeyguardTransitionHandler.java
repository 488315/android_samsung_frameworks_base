package com.android.p038wm.shell.keyguard;

import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import android.view.SurfaceControl;
import android.window.IRemoteTransition;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.p038wm.shell.common.ShellExecutor;
import com.android.p038wm.shell.keyguard.KeyguardTransitionHandler;
import com.android.p038wm.shell.protolog.ShellProtoLogCache;
import com.android.p038wm.shell.protolog.ShellProtoLogGroup;
import com.android.p038wm.shell.protolog.ShellProtoLogImpl;
import com.android.p038wm.shell.sysui.ShellInit;
import com.android.p038wm.shell.transition.Transitions;
import com.android.p038wm.shell.util.TransitionUtil;
import com.android.systemui.keyguard.KeyguardService;
import com.android.systemui.keyguard.KeyguardService$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeyguardTransitionHandler implements Transitions.TransitionHandler {
    public final ShellExecutor mMainExecutor;
    public final Transitions mTransitions;
    public final ArrayMap mStartedTransitions = new ArrayMap();
    public IRemoteTransition mExitTransition = null;
    public IRemoteTransition mOccludeTransition = null;
    public IRemoteTransition mOccludeByDreamTransition = null;
    public IRemoteTransition mUnoccludeTransition = null;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.keyguard.KeyguardTransitionHandler$1 */
    public final class C40021 extends IRemoteTransitionFinishedCallback.Stub {
        public final /* synthetic */ Transitions.TransitionFinishCallback val$finishCallback;
        public final /* synthetic */ SurfaceControl.Transaction val$finishTransaction;
        public final /* synthetic */ IBinder val$transition;

        public C40021(SurfaceControl.Transaction transaction, IBinder iBinder, Transitions.TransitionFinishCallback transitionFinishCallback) {
            this.val$finishTransaction = transaction;
            this.val$transition = iBinder;
            this.val$finishCallback = transitionFinishCallback;
        }

        public final void onTransitionFinished(final WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) {
            if (transaction != null) {
                this.val$finishTransaction.merge(transaction);
            }
            ShellExecutor shellExecutor = KeyguardTransitionHandler.this.mMainExecutor;
            final IBinder iBinder = this.val$transition;
            final Transitions.TransitionFinishCallback transitionFinishCallback = this.val$finishCallback;
            ((HandlerExecutor) shellExecutor).executeDelayed(0L, new Runnable() { // from class: com.android.wm.shell.keyguard.KeyguardTransitionHandler$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardTransitionHandler.C40021 c40021 = KeyguardTransitionHandler.C40021.this;
                    IBinder iBinder2 = iBinder;
                    Transitions.TransitionFinishCallback transitionFinishCallback2 = transitionFinishCallback;
                    WindowContainerTransaction windowContainerTransaction2 = windowContainerTransaction;
                    KeyguardTransitionHandler.this.mStartedTransitions.remove(iBinder2);
                    transitionFinishCallback2.onTransitionFinished(windowContainerTransaction2, null);
                }
            });
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class FakeFinishCallback extends IRemoteTransitionFinishedCallback.Stub {
        public /* synthetic */ FakeFinishCallback(int i) {
            this();
        }

        private FakeFinishCallback() {
        }

        public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class KeyguardTransitionsImpl implements KeyguardTransitions {
        public /* synthetic */ KeyguardTransitionsImpl(KeyguardTransitionHandler keyguardTransitionHandler, int i) {
            this();
        }

        @Override // com.android.p038wm.shell.keyguard.KeyguardTransitions
        public final void register(final KeyguardService.C14691 c14691, final KeyguardService.C14691 c146912, final KeyguardService.C14691 c146913, final KeyguardService.C14691 c146914) {
            ((HandlerExecutor) KeyguardTransitionHandler.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.keyguard.KeyguardTransitionHandler$KeyguardTransitionsImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardTransitionHandler.KeyguardTransitionsImpl keyguardTransitionsImpl = KeyguardTransitionHandler.KeyguardTransitionsImpl.this;
                    IRemoteTransition iRemoteTransition = c14691;
                    IRemoteTransition iRemoteTransition2 = c146912;
                    IRemoteTransition iRemoteTransition3 = c146913;
                    IRemoteTransition iRemoteTransition4 = c146914;
                    KeyguardTransitionHandler keyguardTransitionHandler = KeyguardTransitionHandler.this;
                    keyguardTransitionHandler.mExitTransition = iRemoteTransition;
                    keyguardTransitionHandler.mOccludeTransition = iRemoteTransition2;
                    keyguardTransitionHandler.mOccludeByDreamTransition = iRemoteTransition3;
                    keyguardTransitionHandler.mUnoccludeTransition = iRemoteTransition4;
                }
            });
        }

        private KeyguardTransitionsImpl() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StartedTransition {
        public final SurfaceControl.Transaction mFinishT;
        public final TransitionInfo mInfo;
        public final IRemoteTransition mPlayer;

        public StartedTransition(KeyguardTransitionHandler keyguardTransitionHandler, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IRemoteTransition iRemoteTransition) {
            this.mInfo = transitionInfo;
            this.mFinishT = transaction;
            this.mPlayer = iRemoteTransition;
        }
    }

    public KeyguardTransitionHandler(ShellInit shellInit, Transitions transitions, Handler handler, ShellExecutor shellExecutor) {
        this.mTransitions = transitions;
        this.mMainExecutor = shellExecutor;
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.keyguard.KeyguardTransitionHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardTransitionHandler keyguardTransitionHandler = KeyguardTransitionHandler.this;
                keyguardTransitionHandler.mTransitions.addHandler(keyguardTransitionHandler);
            }
        }, this);
    }

    public static void finishAnimationImmediately(IBinder iBinder, StartedTransition startedTransition) {
        try {
            startedTransition.mPlayer.mergeAnimation(new Binder(), new TransitionInfo(12, 0), new SurfaceControl.Transaction(), iBinder, new FakeFinishCallback(0));
        } catch (RemoteException e) {
            Log.wtf("KeyguardTransition", "RemoteException thrown from KeyguardService transition", e);
        }
    }

    public static boolean handles(TransitionInfo transitionInfo) {
        return (transitionInfo.getFlags() & 14592) != 0;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        return null;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        StartedTransition startedTransition = (StartedTransition) this.mStartedTransitions.get(iBinder2);
        int i = 0;
        if (startedTransition == null) {
            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                ShellProtoLogImpl.m230e(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 312460249, 0, "unknown keyguard transition %s", String.valueOf(iBinder2));
                return;
            }
            return;
        }
        if ((transitionInfo.getFlags() & 2048) == 0 || (startedTransition.mInfo.getFlags() & 256) == 0) {
            if (transitionInfo.getType() != 12 && handles(transitionInfo)) {
                finishAnimationImmediately(iBinder2, startedTransition);
                return;
            }
            return;
        }
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -56086801, 0, "canceling keyguard exit transition %s", String.valueOf(iBinder2));
        }
        startedTransition.mFinishT.merge(transaction);
        try {
            startedTransition.mPlayer.mergeAnimation(iBinder, transitionInfo, transaction, iBinder2, new FakeFinishCallback(i));
        } catch (RemoteException e) {
            Log.wtf("KeyguardTransition", "RemoteException thrown from KeyguardService transition", e);
        }
        transitionFinishCallback.onTransitionFinished(null, null);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void onTransitionConsumed(IBinder iBinder, boolean z, SurfaceControl.Transaction transaction) {
        StartedTransition startedTransition = (StartedTransition) this.mStartedTransitions.remove(iBinder);
        if (startedTransition != null) {
            finishAnimationImmediately(iBinder, startedTransition);
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        boolean z = false;
        if (!handles(transitionInfo)) {
            return false;
        }
        if ((transitionInfo.getFlags() & 256) != 0) {
            return startAnimation(this.mExitTransition, "going-away", iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback);
        }
        if ((transitionInfo.getFlags() & 4096) == 0) {
            if ((transitionInfo.getFlags() & 8192) != 0) {
                return startAnimation(this.mUnoccludeTransition, "unocclude", iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback);
            }
            Log.i("KeyguardTransition", "Refused to play keyguard transition: " + transitionInfo);
            return false;
        }
        int m136m = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1);
        while (true) {
            if (m136m < 0) {
                break;
            }
            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m);
            if (TransitionUtil.isOpeningType(change.getMode()) && change.getTaskInfo() != null && change.getTaskInfo().getActivityType() == 5) {
                z = true;
                break;
            }
            m136m--;
        }
        return z ? startAnimation(this.mOccludeByDreamTransition, "occlude-by-dream", iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback) : startAnimation(this.mOccludeTransition, "occlude", iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback);
    }

    public final boolean startAnimation(IRemoteTransition iRemoteTransition, String str, IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        if (iRemoteTransition == null) {
            if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
                ShellProtoLogImpl.m230e(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 880722010, 0, "missing handler for keyguard %s transition", str);
            }
            return false;
        }
        if (ShellProtoLogCache.WM_SHELL_TRANSITIONS_enabled) {
            ShellProtoLogImpl.m232v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 292417572, 0, "start keyguard %s transition, info = %s", str, String.valueOf(transitionInfo));
        }
        try {
            this.mStartedTransitions.put(iBinder, new StartedTransition(this, transitionInfo, transaction2, iRemoteTransition));
            iRemoteTransition.startAnimation(iBinder, transitionInfo, transaction, new C40021(transaction2, iBinder, transitionFinishCallback));
            transaction.clear();
            return true;
        } catch (RemoteException e) {
            Log.wtf("KeyguardTransition", "RemoteException thrown from local IRemoteTransition", e);
            return false;
        }
    }
}
