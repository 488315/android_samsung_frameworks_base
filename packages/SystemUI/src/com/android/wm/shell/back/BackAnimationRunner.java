package com.android.wm.shell.back;

import android.content.Context;
import android.os.Handler;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.IOnBackInvokedCallback;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.wm.shell.back.BackAnimationRunner;
import java.lang.ref.WeakReference;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BackAnimationRunner {
    public boolean mAnimationCancelled;
    public RemoteAnimationTarget[] mApps;
    public final IOnBackInvokedCallback mCallback;
    public final Context mContext;
    public final int mCujType;
    public BackAnimationController$$ExternalSyntheticLambda2 mFinishedCallback;
    public final Handler mHandler;
    public RemoteAnimationFinishedStub mRemoteCallback;
    public final IRemoteAnimationRunner mRunner;
    public boolean mWaitingAnimation;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class RemoteAnimationFinishedStub extends IRemoteAnimationFinishedCallback.Stub {
        public boolean mAbandoned;
        public final WeakReference mRunnerRef;

        public /* synthetic */ RemoteAnimationFinishedStub(BackAnimationRunner backAnimationRunner, int i) {
            this(backAnimationRunner);
        }

        public final void onAnimationFinished() {
            synchronized (this) {
                try {
                    if (this.mAbandoned) {
                        return;
                    }
                    final BackAnimationRunner backAnimationRunner = (BackAnimationRunner) this.mRunnerRef.get();
                    if (backAnimationRunner == null) {
                        return;
                    }
                    backAnimationRunner.mHandler.post(new Runnable() { // from class: com.android.wm.shell.back.BackAnimationRunner$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            BackAnimationRunner backAnimationRunner2 = BackAnimationRunner.this;
                            BackAnimationRunner.RemoteAnimationFinishedStub remoteAnimationFinishedStub = this;
                            BackAnimationRunner.RemoteAnimationFinishedStub remoteAnimationFinishedStub2 = backAnimationRunner2.mRemoteCallback;
                            if (remoteAnimationFinishedStub2 == null || remoteAnimationFinishedStub == remoteAnimationFinishedStub2) {
                                if (backAnimationRunner2.shouldMonitorCUJ(backAnimationRunner2.mApps)) {
                                    InteractionJankMonitor.getInstance().end(backAnimationRunner2.mCujType);
                                }
                                backAnimationRunner2.mFinishedCallback.run();
                                for (int length = backAnimationRunner2.mApps.length - 1; length >= 0; length--) {
                                    SurfaceControl surfaceControl = backAnimationRunner2.mApps[length].leash;
                                    if (surfaceControl != null && surfaceControl.isValid()) {
                                        surfaceControl.release();
                                    }
                                }
                                backAnimationRunner2.mApps = null;
                                backAnimationRunner2.mFinishedCallback = null;
                                backAnimationRunner2.mRemoteCallback = null;
                            }
                        }
                    });
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        private RemoteAnimationFinishedStub(BackAnimationRunner backAnimationRunner) {
            this.mRunnerRef = new WeakReference(backAnimationRunner);
        }
    }

    public BackAnimationRunner(IOnBackInvokedCallback iOnBackInvokedCallback, IRemoteAnimationRunner iRemoteAnimationRunner, Context context, int i, Handler handler) {
        this.mCallback = iOnBackInvokedCallback;
        this.mRunner = iRemoteAnimationRunner;
        this.mCujType = i;
        this.mContext = context;
        this.mHandler = handler;
    }

    public boolean shouldMonitorCUJ(RemoteAnimationTarget[] remoteAnimationTargetArr) {
        return remoteAnimationTargetArr.length > 0 && this.mCujType != -1;
    }

    public BackAnimationRunner(IOnBackInvokedCallback iOnBackInvokedCallback, IRemoteAnimationRunner iRemoteAnimationRunner, Context context, Handler handler) {
        this(iOnBackInvokedCallback, iRemoteAnimationRunner, context, -1, handler);
    }

    @Deprecated
    public BackAnimationRunner(IOnBackInvokedCallback iOnBackInvokedCallback, IRemoteAnimationRunner iRemoteAnimationRunner, Context context) {
        this(iOnBackInvokedCallback, iRemoteAnimationRunner, context, -1, context.getMainThreadHandler());
    }
}
