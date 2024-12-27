package com.android.systemui.animation;

import android.os.IBinder;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.RemoteAnimationRunnerHelper;
import android.window.RemoteTransitionStub;
import android.window.TransitionInfo;

public abstract class RemoteAnimationRunnerCompat extends IRemoteAnimationRunner.Stub {
    public static final boolean FW_SHELL_TRANSITION_MERGE;

    /* renamed from: com.android.systemui.animation.RemoteAnimationRunnerCompat$1, reason: invalid class name */
    public final class AnonymousClass1 extends RemoteTransitionStub {
        public final ArrayMap mFinishRunnables = new ArrayMap();
        public ArrayMap mLeashMap = null;
        public final /* synthetic */ IRemoteAnimationRunner val$runner;

        public AnonymousClass1(IRemoteAnimationRunner iRemoteAnimationRunner) {
            this.val$runner = iRemoteAnimationRunner;
        }

        public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
            Runnable runnable;
            if (RemoteAnimationRunnerCompat.FW_SHELL_TRANSITION_MERGE && RemoteAnimationRunnerHelper.getInstance().mergeOrTransferAnimation(iBinder, transitionInfo, transaction, iBinder2, iRemoteTransitionFinishedCallback, this.mLeashMap)) {
                return;
            }
            synchronized (this.mFinishRunnables) {
                runnable = (Runnable) this.mFinishRunnables.remove(iBinder2);
            }
            transaction.close();
            transitionInfo.releaseAllSurfaces();
            if (runnable == null) {
                return;
            }
            this.val$runner.onAnimationCancelled();
            runnable.run();
        }

        /* JADX WARN: Removed duplicated region for block: B:89:0x0279 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void startAnimation(final android.os.IBinder r27, final android.window.TransitionInfo r28, android.view.SurfaceControl.Transaction r29, final android.window.IRemoteTransitionFinishedCallback r30) {
            /*
                Method dump skipped, instructions count: 658
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.animation.RemoteAnimationRunnerCompat.AnonymousClass1.startAnimation(android.os.IBinder, android.window.TransitionInfo, android.view.SurfaceControl$Transaction, android.window.IRemoteTransitionFinishedCallback):void");
        }
    }

    static {
        boolean z = SystemProperties.getBoolean("persist.wm.enable.custom.anim", true) && SystemProperties.getBoolean("persist.wm.debug.shell_transit", true) && SystemProperties.getBoolean("persist.wm.enable.merge.transit", true);
        FW_SHELL_TRANSITION_MERGE = z;
        if (z) {
            SystemProperties.getBoolean("persist.wm.enable.merge_transfer.transit", true);
        }
    }

    public abstract void onAnimationStart();

    public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        onAnimationStart();
    }
}
