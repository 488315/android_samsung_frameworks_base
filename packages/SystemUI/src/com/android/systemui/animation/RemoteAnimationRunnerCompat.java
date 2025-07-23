package com.android.systemui.animation;

import android.os.IBinder;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.Log;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.RemoteAnimationRunnerHelper;
import android.window.RemoteTransitionStub;
import android.window.TransitionInfo;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class RemoteAnimationRunnerCompat extends IRemoteAnimationRunner.Stub {
    public static final boolean FW_SHELL_TRANSITION_MERGE;
    public static final boolean IS_SHELL_TRANSITION_ENABLED;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.animation.RemoteAnimationRunnerCompat$1, reason: invalid class name */
    public class AnonymousClass1 extends RemoteTransitionStub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final ArrayMap mFinishRunnables = new ArrayMap();
        public ArrayMap mLeashMap = null;
        public final /* synthetic */ IRemoteAnimationRunner val$runner;

        public AnonymousClass1(IRemoteAnimationRunner iRemoteAnimationRunner) {
            this.val$runner = iRemoteAnimationRunner;
        }

        public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
            TransitionInfo transitionInfo2;
            SurfaceControl.Transaction transaction2;
            IBinder iBinder3;
            Runnable runnable;
            if (RemoteAnimationRunnerCompat.FW_SHELL_TRANSITION_MERGE) {
                transitionInfo2 = transitionInfo;
                transaction2 = transaction;
                iBinder3 = iBinder2;
                if (RemoteAnimationRunnerHelper.getInstance().mergeOrTransferAnimation(iBinder, transitionInfo2, transaction2, iBinder3, iRemoteTransitionFinishedCallback, this.mLeashMap)) {
                    return;
                }
            } else {
                transitionInfo2 = transitionInfo;
                transaction2 = transaction;
                iBinder3 = iBinder2;
            }
            synchronized (this.mFinishRunnables) {
                runnable = (Runnable) this.mFinishRunnables.remove(iBinder3);
            }
            transaction2.close();
            transitionInfo2.releaseAllSurfaces();
            if (runnable == null) {
                return;
            }
            if (RemoteAnimationRunnerCompat.IS_SHELL_TRANSITION_ENABLED) {
                Log.i("RemoteAnimRunnerCompat", "mergeAnimation, calling Runner#onAnimationCancelled");
            }
            this.val$runner.onAnimationCancelled();
            runnable.run();
        }

        public final void onTransitionConsumed(IBinder iBinder, boolean z) {
            synchronized (this.mFinishRunnables) {
                this.mFinishRunnables.remove(iBinder);
            }
            if (RemoteAnimationRunnerCompat.FW_SHELL_TRANSITION_MERGE && RemoteAnimationRunnerHelper.getInstance().interceptTransitionConsumed(iBinder)) {
                return;
            }
            if (RemoteAnimationRunnerCompat.IS_SHELL_TRANSITION_ENABLED) {
                Log.i("RemoteAnimRunnerCompat", "onTransitionConsumed, calling Runner#onAnimationCancelled");
            }
            this.val$runner.onAnimationCancelled();
        }

        /* JADX WARN: Removed duplicated region for block: B:111:0x0300 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void startAnimation(final android.os.IBinder r26, final android.window.TransitionInfo r27, android.view.SurfaceControl.Transaction r28, final android.window.IRemoteTransitionFinishedCallback r29) {
            /*
                Method dump skipped, instructions count: 792
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.animation.RemoteAnimationRunnerCompat.AnonymousClass1.startAnimation(android.os.IBinder, android.window.TransitionInfo, android.view.SurfaceControl$Transaction, android.window.IRemoteTransitionFinishedCallback):void");
        }
    }

    static {
        boolean z = SystemProperties.getBoolean("persist.wm.debug.shell_transit", true);
        IS_SHELL_TRANSITION_ENABLED = z;
        boolean z2 = SystemProperties.getBoolean("persist.wm.enable.custom.anim", true) && z && SystemProperties.getBoolean("persist.wm.enable.merge.transit", true);
        FW_SHELL_TRANSITION_MERGE = z2;
        if (z2) {
            SystemProperties.getBoolean("persist.wm.enable.merge_transfer.transit", true);
        }
    }

    public abstract void onAnimationStart();

    public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        onAnimationStart();
    }
}
