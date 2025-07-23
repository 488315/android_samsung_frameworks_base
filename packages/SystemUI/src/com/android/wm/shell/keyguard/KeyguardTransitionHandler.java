package com.android.wm.shell.keyguard;

import android.app.ActivityManager;
import android.graphics.Rect;
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
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.KeyguardService;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.HandlerExecutor;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.TaskStackListenerCallback;
import com.android.wm.shell.common.TaskStackListenerImpl;
import com.android.wm.shell.keyguard.KeyguardTransitionHandler;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.sysui.KeyguardChangeListener;
import com.android.wm.shell.sysui.ShellController;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.FocusTransitionObserver;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class KeyguardTransitionHandler implements Transitions.TransitionHandler, KeyguardChangeListener, TaskStackListenerCallback {
    public WindowContainerToken mDreamToken;
    public final FocusTransitionObserver mFocusTransitionObserver;
    public boolean mIsLaunchingActivityOverLockscreen;
    public final ShellExecutor mMainExecutor;
    public final ShellController mShellController;
    public final TaskStackListenerImpl mTaskStackListener;
    public final Transitions mTransitions;
    public final ArrayMap mStartedTransitions = new ArrayMap();
    public KeyguardService.AnonymousClass1 mExitTransition = null;
    public KeyguardService.AnonymousClass1 mAppearTransition = null;
    public KeyguardService.AnonymousClass1 mOccludeTransition = null;
    public KeyguardService.AnonymousClass1 mOccludeByDreamTransition = null;
    public KeyguardService.AnonymousClass1 mUnoccludeTransition = null;
    public boolean mKeyguardShowing = true;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.wm.shell.keyguard.KeyguardTransitionHandler$1, reason: invalid class name */
    public class AnonymousClass1 extends IRemoteTransitionFinishedCallback.Stub {
        public final /* synthetic */ Transitions.TransitionFinishCallback val$finishCallback;
        public final /* synthetic */ SurfaceControl.Transaction val$finishTransaction;
        public final /* synthetic */ TransitionInfo val$info;
        public final /* synthetic */ IBinder val$transition;

        public AnonymousClass1(SurfaceControl.Transaction transaction, TransitionInfo transitionInfo, IBinder iBinder, Transitions.TransitionFinishCallback transitionFinishCallback) {
            this.val$finishTransaction = transaction;
            this.val$info = transitionInfo;
            this.val$transition = iBinder;
            this.val$finishCallback = transitionFinishCallback;
        }

        public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) {
            if (transaction != null) {
                this.val$finishTransaction.merge(transaction);
            }
            final WindowContainerTransaction windowContainerTransaction2 = new WindowContainerTransaction();
            if (windowContainerTransaction != null) {
                windowContainerTransaction2.merge(windowContainerTransaction, true);
            }
            KeyguardTransitionHandler keyguardTransitionHandler = KeyguardTransitionHandler.this;
            TransitionInfo transitionInfo = this.val$info;
            keyguardTransitionHandler.getClass();
            if ((transitionInfo.getFlags() & 4096) != 0) {
                int i = 0;
                while (true) {
                    if (i >= transitionInfo.getChanges().size()) {
                        break;
                    }
                    TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
                    ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
                    if (taskInfo == null || taskInfo.taskId == -1 || taskInfo.getWindowingMode() != 5 || !keyguardTransitionHandler.mFocusTransitionObserver.hasGlobalFocus(taskInfo) || change.getContainer() == null) {
                        i++;
                    } else {
                        int displayId = taskInfo.getDisplayId();
                        DesktopStateImpl.Companion.getClass();
                        if (DesktopStateImpl.Companion.inDesktopWindowing(displayId)) {
                            Log.d("KeyguardTransition", "skip change dw freeform to fullscreen when occluding the keyguard");
                        } else {
                            windowContainerTransaction2.setWindowingMode(change.getContainer(), 1);
                            windowContainerTransaction2.setBounds(change.getContainer(), (Rect) null);
                        }
                    }
                }
            }
            ShellExecutor shellExecutor = KeyguardTransitionHandler.this.mMainExecutor;
            final IBinder iBinder = this.val$transition;
            final Transitions.TransitionFinishCallback transitionFinishCallback = this.val$finishCallback;
            ((HandlerExecutor) shellExecutor).executeDelayed(new Runnable() { // from class: com.android.wm.shell.keyguard.KeyguardTransitionHandler$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardTransitionHandler.AnonymousClass1 anonymousClass1 = KeyguardTransitionHandler.AnonymousClass1.this;
                    IBinder iBinder2 = iBinder;
                    Transitions.TransitionFinishCallback transitionFinishCallback2 = transitionFinishCallback;
                    WindowContainerTransaction windowContainerTransaction3 = windowContainerTransaction2;
                    KeyguardTransitionHandler.this.mStartedTransitions.remove(iBinder2);
                    transitionFinishCallback2.onTransitionFinished(windowContainerTransaction3);
                }
            }, 0L);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class FakeFinishCallback extends IRemoteTransitionFinishedCallback.Stub {
        public /* synthetic */ FakeFinishCallback(int i) {
            this();
        }

        private FakeFinishCallback() {
        }

        public final void onTransitionFinished(WindowContainerTransaction windowContainerTransaction, SurfaceControl.Transaction transaction) {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class KeyguardTransitionsImpl implements KeyguardTransitions {
        public /* synthetic */ KeyguardTransitionsImpl(KeyguardTransitionHandler keyguardTransitionHandler, int i) {
            this();
        }

        @Override // com.android.wm.shell.keyguard.KeyguardTransitions
        public final void register(final KeyguardService.AnonymousClass1 anonymousClass1, final KeyguardService.AnonymousClass1 anonymousClass12, final KeyguardService.AnonymousClass1 anonymousClass13, final KeyguardService.AnonymousClass1 anonymousClass14, final KeyguardService.AnonymousClass1 anonymousClass15) {
            KeyguardTransitionHandler.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.keyguard.KeyguardTransitionHandler$KeyguardTransitionsImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardTransitionHandler.KeyguardTransitionsImpl keyguardTransitionsImpl = KeyguardTransitionHandler.KeyguardTransitionsImpl.this;
                    KeyguardService.AnonymousClass1 anonymousClass16 = anonymousClass1;
                    KeyguardService.AnonymousClass1 anonymousClass17 = anonymousClass12;
                    KeyguardService.AnonymousClass1 anonymousClass18 = anonymousClass13;
                    KeyguardService.AnonymousClass1 anonymousClass19 = anonymousClass14;
                    KeyguardService.AnonymousClass1 anonymousClass110 = anonymousClass15;
                    KeyguardTransitionHandler keyguardTransitionHandler = KeyguardTransitionHandler.this;
                    keyguardTransitionHandler.mExitTransition = anonymousClass16;
                    keyguardTransitionHandler.mAppearTransition = anonymousClass17;
                    keyguardTransitionHandler.mOccludeTransition = anonymousClass18;
                    keyguardTransitionHandler.mOccludeByDreamTransition = anonymousClass19;
                    keyguardTransitionHandler.mUnoccludeTransition = anonymousClass110;
                }
            });
        }

        @Override // com.android.wm.shell.keyguard.KeyguardTransitions
        public final void setLaunchingActivityOverLockscreen(final boolean z) {
            KeyguardTransitionHandler.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.keyguard.KeyguardTransitionHandler$KeyguardTransitionsImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardTransitionHandler.this.mIsLaunchingActivityOverLockscreen = z;
                }
            });
        }

        private KeyguardTransitionsImpl() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public KeyguardTransitionHandler(ShellInit shellInit, ShellController shellController, DisplayController displayController, Transitions transitions, TaskStackListenerImpl taskStackListenerImpl, Handler handler, ShellExecutor shellExecutor, FocusTransitionObserver focusTransitionObserver) {
        this.mTransitions = transitions;
        this.mShellController = shellController;
        this.mMainExecutor = shellExecutor;
        this.mTaskStackListener = taskStackListenerImpl;
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.keyguard.KeyguardTransitionHandler$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardTransitionHandler keyguardTransitionHandler = KeyguardTransitionHandler.this;
                keyguardTransitionHandler.mTransitions.addHandler(keyguardTransitionHandler);
                keyguardTransitionHandler.mShellController.addKeyguardChangeListener(keyguardTransitionHandler);
                keyguardTransitionHandler.mTaskStackListener.addListener(keyguardTransitionHandler);
            }
        }, this);
        this.mFocusTransitionObserver = focusTransitionObserver;
    }

    public static void finishAnimationImmediately(IBinder iBinder, StartedTransition startedTransition) {
        try {
            startedTransition.mPlayer.mergeAnimation(new Binder(), new TransitionInfo(12, 0), new SurfaceControl.Transaction(), iBinder, new FakeFinishCallback(0));
        } catch (RemoteException e) {
            Log.wtf("KeyguardTransition", "RemoteException thrown from KeyguardService transition", e);
        }
    }

    public static boolean handles(TransitionInfo transitionInfo) {
        return (transitionInfo.getType() != 11 || transitionInfo.isKeyguardGoingAway()) && (transitionInfo.getFlags() & 47360) != 0;
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        if ((transitionRequestInfo.getFlags() & 256) == 0 || this.mDreamToken == null) {
            return null;
        }
        return new WindowContainerTransaction().removeTask(this.mDreamToken);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, IBinder iBinder2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        StartedTransition startedTransition = (StartedTransition) this.mStartedTransitions.get(iBinder2);
        int i = 0;
        if (startedTransition == null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[4]) {
                ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 9140305169458870456L, 0, String.valueOf(iBinder2));
                return;
            }
            return;
        }
        if ((transitionInfo.getFlags() & 2048) == 0 || (startedTransition.mInfo.getFlags() & 256) == 0) {
            finishAnimationImmediately(iBinder2, startedTransition);
            return;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 8862483729354210657L, 0, String.valueOf(iBinder2));
        }
        startedTransition.mFinishT.merge(transaction);
        try {
            startedTransition.mPlayer.mergeAnimation(iBinder, transitionInfo, transaction, iBinder2, new FakeFinishCallback(i));
        } catch (RemoteException e) {
            Log.wtf("KeyguardTransition", "RemoteException thrown from KeyguardService transition", e);
        }
        transitionFinishCallback.onTransitionFinished(null);
    }

    @Override // com.android.wm.shell.sysui.KeyguardChangeListener
    public final void onKeyguardVisibilityChanged(boolean z, boolean z2, boolean z3) {
        this.mKeyguardShowing = z;
    }

    @Override // com.android.wm.shell.common.TaskStackListenerCallback
    public final void onTaskMovedToFront(ActivityManager.RunningTaskInfo runningTaskInfo) {
        this.mDreamToken = runningTaskInfo.getActivityType() == 5 ? runningTaskInfo.token : null;
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
        if (handles(transitionInfo)) {
            if ((transitionInfo.getFlags() & 256) != 0) {
                return startAnimation(this.mExitTransition, "going-away", iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback);
            }
            if ((transitionInfo.getFlags() & 2048) != 0 || (transitionInfo.getFlags() & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0) {
                return startAnimation(this.mAppearTransition, "appearing", iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback);
            }
            if (!this.mIsLaunchingActivityOverLockscreen) {
                if ((transitionInfo.getFlags() & 64) != 0) {
                    if ((transitionInfo.getFlags() & 4096) != 0) {
                        for (int m = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); m >= 0; m--) {
                            TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m);
                            if (TransitionUtil.isOpeningType(change.getMode()) && change.getTaskInfo() != null && change.getTaskInfo().getActivityType() == 5) {
                                return startAnimation(this.mOccludeByDreamTransition, "occlude-by-dream", iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback);
                            }
                        }
                        return startAnimation(this.mOccludeTransition, "occlude", iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback);
                    }
                    if ((transitionInfo.getFlags() & 8192) != 0) {
                        return startAnimation(this.mUnoccludeTransition, "unocclude", iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback);
                    }
                }
                Log.i("KeyguardTransition", "Refused to play keyguard transition: " + transitionInfo);
                return false;
            }
        }
        return false;
    }

    public final boolean startAnimation(KeyguardService.AnonymousClass1 anonymousClass1, String str, IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        if (anonymousClass1 == null) {
            if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[4]) {
                ProtoLogImpl_1771455215.e(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, -4569412050464721290L, 0, str);
            }
            return false;
        }
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_TRANSITIONS_enabled[1]) {
            ProtoLogImpl_1771455215.v(ShellProtoLogGroup.WM_SHELL_TRANSITIONS, 8206143507108738884L, 0, str, String.valueOf(transitionInfo));
        }
        try {
            this.mStartedTransitions.put(iBinder, new StartedTransition(this, transitionInfo, transaction2, anonymousClass1));
            anonymousClass1.startAnimation(iBinder, transitionInfo, transaction, new AnonymousClass1(transaction2, transitionInfo, iBinder, transitionFinishCallback));
            transaction.clear();
            return true;
        } catch (RemoteException e) {
            Log.wtf("KeyguardTransition", "RemoteException thrown from local IRemoteTransition", e);
            return false;
        }
    }
}
