package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.IApplicationThread;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.IRemoteTransition;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.TransitionInfo;
import android.window.WindowContainerTransaction;
import com.android.systemui.keyguard.KeyguardService$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class RemoteAnimationAdapterCompat {
    private final RemoteAnimationAdapter mWrapped;

    public RemoteAnimationAdapterCompat(RemoteAnimationRunnerCompat remoteAnimationRunnerCompat, long j, long j2, IApplicationThread iApplicationThread) {
        this.mWrapped = new RemoteAnimationAdapter(wrapRemoteAnimationRunner(remoteAnimationRunnerCompat), j, j2);
    }

    public static IRemoteAnimationRunner.Stub wrapRemoteAnimationRunner(final RemoteAnimationRunnerCompat remoteAnimationRunnerCompat) {
        return new IRemoteAnimationRunner.Stub() { // from class: com.samsung.android.desktopsystemui.sharedlib.system.RemoteAnimationAdapterCompat.1
            public void onAnimationCancelled() {
                RemoteAnimationRunnerCompat.this.onAnimationCancelled();
            }

            public void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, final IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
                RemoteAnimationRunnerCompat.this.onAnimationStart(i, RemoteAnimationTargetCompat.wrap(remoteAnimationTargetArr), RemoteAnimationTargetCompat.wrap(remoteAnimationTargetArr2), RemoteAnimationTargetCompat.wrap(remoteAnimationTargetArr3), new Runnable() { // from class: com.samsung.android.desktopsystemui.sharedlib.system.RemoteAnimationAdapterCompat.1.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            iRemoteAnimationFinishedCallback.onAnimationFinished();
                        } catch (RemoteException e) {
                            Log.e("ActivityOptionsCompat", "Failed to call app controlled animation finished callback", e);
                        }
                    }
                });
            }
        };
    }

    private static IRemoteTransition.Stub wrapRemoteTransition(final RemoteAnimationRunnerCompat remoteAnimationRunnerCompat) {
        return new IRemoteTransition.Stub() { // from class: com.samsung.android.desktopsystemui.sharedlib.system.RemoteAnimationAdapterCompat.2
            public void startAnimation(IBinder iBinder, final TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, final IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
                final ArrayMap arrayMap = new ArrayMap();
                RemoteAnimationTargetCompat[] wrap = RemoteAnimationTargetCompat.wrap(transitionInfo, false, transaction, arrayMap);
                RemoteAnimationTargetCompat[] wrap2 = RemoteAnimationTargetCompat.wrap(transitionInfo, true, transaction, arrayMap);
                RemoteAnimationTargetCompat[] remoteAnimationTargetCompatArr = new RemoteAnimationTargetCompat[0];
                for (int m136m = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1); m136m >= 0; m136m--) {
                    TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m);
                    if (change.getTaskInfo() == null || change.getTaskInfo().getActivityType() != 2) {
                        change.getFlags();
                    } else {
                        if (change.getMode() != 1) {
                            change.getMode();
                        }
                        transitionInfo.getChanges().size();
                    }
                    if (change.getParent() == null && change.getEndRotation() >= 0 && change.getEndRotation() != change.getStartRotation()) {
                        change.getEndRotation();
                        change.getStartRotation();
                        change.getEndAbsBounds().width();
                        change.getEndAbsBounds().height();
                    }
                }
                transaction.apply();
                RemoteAnimationRunnerCompat.this.onAnimationStart(0, wrap, wrap2, remoteAnimationTargetCompatArr, new Runnable() { // from class: com.samsung.android.desktopsystemui.sharedlib.system.RemoteAnimationAdapterCompat.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        SurfaceControl.Transaction transaction2 = new SurfaceControl.Transaction();
                        int size = transitionInfo.getChanges().size();
                        while (true) {
                            size--;
                            if (size < 0) {
                                break;
                            } else {
                                ((TransitionInfo.Change) transitionInfo.getChanges().get(size)).getLeash().release();
                            }
                        }
                        int size2 = arrayMap.size();
                        while (true) {
                            size2--;
                            if (size2 < 0) {
                                try {
                                    iRemoteTransitionFinishedCallback.onTransitionFinished((WindowContainerTransaction) null, transaction2);
                                    return;
                                } catch (RemoteException e) {
                                    Log.e("ActivityOptionsCompat", "Failed to call app controlled animation finished callback", e);
                                    return;
                                }
                            }
                            ((SurfaceControl) arrayMap.valueAt(size2)).release();
                        }
                    }
                });
            }

            public void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
            }
        };
    }

    public RemoteAnimationAdapter getWrapped() {
        return this.mWrapped;
    }
}
