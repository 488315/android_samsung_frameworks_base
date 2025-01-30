package com.android.systemui.shared.system;

import android.os.Build;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Log;
import android.util.secutil.Slog;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.IRemoteTransition;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.RemoteAnimationRunnerHelper;
import android.window.TransitionInfo;
import android.window.WindowContainerTransaction;
import com.android.p038wm.shell.util.CounterRotator;
import com.android.p038wm.shell.util.TransitionUtil;
import com.android.systemui.keyguard.KeyguardService$$ExternalSyntheticOutline0;
import com.samsung.android.rune.CoreRune;
import java.util.HashMap;
import java.util.function.Predicate;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class RemoteAnimationRunnerCompat extends IRemoteAnimationRunner.Stub {
    public static final boolean ONE_UI_6_1;
    public static final HashMap sAnimCallbacks;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.shared.system.RemoteAnimationRunnerCompat$1 */
    public final class C24951 extends IRemoteTransition.Stub {
        public final ArrayMap mFinishRunnables = new ArrayMap();
        public ArrayMap mLeashMap = null;
        public final /* synthetic */ IRemoteAnimationRunner val$runner;

        public C24951(IRemoteAnimationRunner iRemoteAnimationRunner) {
            this.val$runner = iRemoteAnimationRunner;
        }

        public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
            Runnable runnable;
            if (RemoteAnimationRunnerCompat.ONE_UI_6_1) {
                if (RemoteAnimationRunnerHelper.mergeAnimation(iBinder, transitionInfo, transaction, iBinder2, iRemoteTransitionFinishedCallback, RemoteAnimationRunnerCompat.sAnimCallbacks, this.mLeashMap)) {
                    return;
                }
            } else if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE && transitionInfo.canMergeAnimation() && RemoteAnimationRunnerCompat.sAnimCallbacks.get(1) != null) {
                for (int i = 0; i < transitionInfo.getChanges().size(); i++) {
                    TransitionInfo.Change change = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
                    if (change.getParent() == null || (change.getFlags() & 2) == 0) {
                        transaction.show(change.getLeash());
                        transaction.setAlpha(change.getLeash(), 1.0f);
                    }
                }
                transaction.apply();
                transitionInfo.releaseAnimSurfaces();
                ((Runnable) RemoteAnimationRunnerCompat.sAnimCallbacks.get(1)).run();
                iRemoteTransitionFinishedCallback.onTransitionFinished((WindowContainerTransaction) null, (SurfaceControl.Transaction) null);
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

        /* JADX WARN: Removed duplicated region for block: B:100:0x02a2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void startAnimation(final IBinder iBinder, final TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, final IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
            CounterRotator counterRotator;
            RemoteAnimationTarget[] remoteAnimationTargetArr;
            RemoteAnimationTarget[] remoteAnimationTargetArr2;
            TransitionInfo.Change change;
            float f;
            final CounterRotator counterRotator2;
            int i;
            final ArrayMap arrayMap = new ArrayMap();
            if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_MERGE) {
                this.mLeashMap = arrayMap;
            }
            RemoteAnimationTarget[] wrap = RemoteAnimationTargetCompat.wrap(transitionInfo, transaction, arrayMap, new TransitionUtil.LeafTaskFilter());
            final boolean z = true;
            RemoteAnimationTarget[] wrap2 = RemoteAnimationTargetCompat.wrap(transitionInfo, transaction, arrayMap, new Predicate() { // from class: com.android.systemui.shared.system.RemoteAnimationTargetCompat$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    TransitionInfo.Change change2 = (TransitionInfo.Change) obj;
                    return z ? TransitionUtil.isWallpaper(change2) : TransitionUtil.isNonApp(change2);
                }
            });
            final boolean z2 = false;
            RemoteAnimationTarget[] wrap3 = RemoteAnimationTargetCompat.wrap(transitionInfo, transaction, arrayMap, new Predicate() { // from class: com.android.systemui.shared.system.RemoteAnimationTargetCompat$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    TransitionInfo.Change change2 = (TransitionInfo.Change) obj;
                    return z2 ? TransitionUtil.isWallpaper(change2) : TransitionUtil.isNonApp(change2);
                }
            });
            TransitionInfo.Change change2 = null;
            TransitionInfo.Change change3 = null;
            int i2 = 0;
            boolean z3 = false;
            boolean z4 = false;
            int i3 = 0;
            float f2 = 0.0f;
            float f3 = 0.0f;
            for (int m136m = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1); m136m >= 0; m136m--) {
                TransitionInfo.Change change4 = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m);
                if (arrayMap.containsKey(change4.getLeash())) {
                    if (change4.getTaskInfo() != null && change4.getTaskInfo().getActivityType() == 2) {
                        z3 = change4.getMode() == 1 || change4.getMode() == 3;
                        i2 = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, m136m);
                        change2 = change4;
                    } else if ((2 & change4.getFlags()) != 0) {
                        change3 = change4;
                    }
                    if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && TransitionUtil.isHomeOrRecents(change4) && TransitionUtil.isClosingType(change4.getMode())) {
                        Slog.d("RemoteAnimRunnerCompat", "go to home from ty=" + change4.getTaskInfo().getActivityType() + ". ignore isReturnToHome if set");
                        z4 = true;
                    }
                    if (change4.getParent() == null && change4.getEndRotation() >= 0 && change4.getEndRotation() != change4.getStartRotation()) {
                        i3 = change4.getEndRotation() - change4.getStartRotation();
                        f2 = change4.getEndAbsBounds().width();
                        f3 = change4.getEndAbsBounds().height();
                    }
                }
            }
            final CounterRotator counterRotator3 = new CounterRotator();
            CounterRotator counterRotator4 = new CounterRotator();
            if (change2 == null || i3 == 0 || change2.getParent() == null) {
                counterRotator = counterRotator4;
                remoteAnimationTargetArr = wrap;
                remoteAnimationTargetArr2 = wrap3;
                change = change3;
                f = 0.0f;
            } else {
                TransitionInfo.Change change5 = transitionInfo.getChange(change2.getParent());
                if (change5 != null) {
                    f = 0.0f;
                    counterRotator = counterRotator4;
                    remoteAnimationTargetArr2 = wrap3;
                    i = i2;
                    remoteAnimationTargetArr = wrap;
                    change = change3;
                    counterRotator3.setup(f2, f3, i3, transaction, change5.getLeash());
                } else {
                    counterRotator = counterRotator4;
                    remoteAnimationTargetArr = wrap;
                    remoteAnimationTargetArr2 = wrap3;
                    i = i2;
                    change = change3;
                    f = 0.0f;
                    Log.e("RemoteAnimRunnerCompat", "Malformed: " + change2 + " has parent=" + change2.getParent() + " but it's not in info.");
                }
                SurfaceControl surfaceControl = counterRotator3.mSurface;
                if (surfaceControl != null) {
                    transaction.setLayer(surfaceControl, i);
                }
            }
            float f4 = f;
            if (!z3 || z4) {
                if (change2 != null) {
                    SurfaceControl surfaceControl2 = (SurfaceControl) arrayMap.get(change2.getLeash());
                    SurfaceControl surfaceControl3 = counterRotator3.mSurface;
                    if (surfaceControl3 != null) {
                        transaction.reparent(surfaceControl2, surfaceControl3);
                    }
                }
                if (change != null && i3 != 0 && change.getParent() != null) {
                    TransitionInfo.Change change6 = transitionInfo.getChange(change.getParent());
                    if (change6 != null) {
                        counterRotator.setup(f2, f3, i3, transaction, change6.getLeash());
                    } else {
                        Log.e("RemoteAnimRunnerCompat", "Malformed: " + change + " has parent=" + change.getParent() + " but it's not in info.");
                    }
                    counterRotator2 = counterRotator;
                    SurfaceControl surfaceControl4 = counterRotator2.mSurface;
                    if (surfaceControl4 != null) {
                        transaction.setLayer(surfaceControl4, -1);
                        SurfaceControl surfaceControl5 = (SurfaceControl) arrayMap.get(change.getLeash());
                        SurfaceControl surfaceControl6 = counterRotator2.mSurface;
                        if (surfaceControl6 != null) {
                            transaction.reparent(surfaceControl5, surfaceControl6);
                        }
                    }
                    transaction.apply();
                    final Runnable runnable = new Runnable() { // from class: com.android.systemui.shared.system.RemoteAnimationRunnerCompat$1$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            CounterRotator counterRotator5 = CounterRotator.this;
                            CounterRotator counterRotator6 = counterRotator2;
                            TransitionInfo transitionInfo2 = transitionInfo;
                            ArrayMap arrayMap2 = arrayMap;
                            IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback2 = iRemoteTransitionFinishedCallback;
                            SurfaceControl.Transaction transaction2 = new SurfaceControl.Transaction();
                            SurfaceControl surfaceControl7 = counterRotator5.mSurface;
                            if (surfaceControl7 != null) {
                                transaction2.remove(surfaceControl7);
                            }
                            SurfaceControl surfaceControl8 = counterRotator6.mSurface;
                            if (surfaceControl8 != null) {
                                transaction2.remove(surfaceControl8);
                            }
                            transitionInfo2.releaseAllSurfaces();
                            arrayMap2.clear();
                            try {
                                iRemoteTransitionFinishedCallback2.onTransitionFinished((WindowContainerTransaction) null, transaction2);
                                transaction2.close();
                            } catch (RemoteException e) {
                                Log.e("RemoteAnimRunnerCompat", "Failed to call app controlled animation finished callback", e);
                            }
                        }
                    };
                    synchronized (this.mFinishRunnables) {
                        this.mFinishRunnables.put(iBinder, runnable);
                    }
                    this.val$runner.onAnimationStart(0, remoteAnimationTargetArr, wrap2, remoteAnimationTargetArr2, new IRemoteAnimationFinishedCallback() { // from class: com.android.systemui.shared.system.RemoteAnimationRunnerCompat.1.1
                        public final IBinder asBinder() {
                            return null;
                        }

                        public final void onAnimationFinished() {
                            synchronized (C24951.this.mFinishRunnables) {
                                if (C24951.this.mFinishRunnables.remove(iBinder) == null) {
                                    return;
                                }
                                runnable.run();
                            }
                        }
                    });
                    return;
                }
            } else {
                SurfaceControl surfaceControl7 = counterRotator3.mSurface;
                if (surfaceControl7 != null) {
                    transaction.setLayer(surfaceControl7, transitionInfo.getChanges().size() * 3);
                }
                for (int m136m2 = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, 1); m136m2 >= 0; m136m2--) {
                    TransitionInfo.Change change7 = (TransitionInfo.Change) transitionInfo.getChanges().get(m136m2);
                    SurfaceControl surfaceControl8 = (SurfaceControl) arrayMap.get(change7.getLeash());
                    if (surfaceControl8 != null) {
                        int mode = ((TransitionInfo.Change) transitionInfo.getChanges().get(m136m2)).getMode();
                        if (TransitionInfo.isIndependent(change7, transitionInfo)) {
                            if (mode == 2 || mode == 4) {
                                transaction.setLayer(surfaceControl8, (transitionInfo.getChanges().size() * 3) - m136m2);
                                SurfaceControl surfaceControl9 = counterRotator3.mSurface;
                                if (surfaceControl9 != null) {
                                    transaction.reparent(surfaceControl8, surfaceControl9);
                                }
                            } else if (CoreRune.FW_CUSTOM_SHELL_TRANSITION_BUG_FIX && change7.getTaskInfo() != null && change7.getTaskInfo().getActivityType() == 2 && change == null) {
                                transaction.setAlpha(surfaceControl8, f4);
                            }
                        }
                    }
                }
                for (int length = wrap2.length - 1; length >= 0; length--) {
                    transaction.show(wrap2[length].leash);
                    transaction.setAlpha(wrap2[length].leash, 1.0f);
                }
            }
            counterRotator2 = counterRotator;
            transaction.apply();
            final Runnable runnable2 = new Runnable() { // from class: com.android.systemui.shared.system.RemoteAnimationRunnerCompat$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CounterRotator counterRotator5 = CounterRotator.this;
                    CounterRotator counterRotator6 = counterRotator2;
                    TransitionInfo transitionInfo2 = transitionInfo;
                    ArrayMap arrayMap2 = arrayMap;
                    IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback2 = iRemoteTransitionFinishedCallback;
                    SurfaceControl.Transaction transaction2 = new SurfaceControl.Transaction();
                    SurfaceControl surfaceControl72 = counterRotator5.mSurface;
                    if (surfaceControl72 != null) {
                        transaction2.remove(surfaceControl72);
                    }
                    SurfaceControl surfaceControl82 = counterRotator6.mSurface;
                    if (surfaceControl82 != null) {
                        transaction2.remove(surfaceControl82);
                    }
                    transitionInfo2.releaseAllSurfaces();
                    arrayMap2.clear();
                    try {
                        iRemoteTransitionFinishedCallback2.onTransitionFinished((WindowContainerTransaction) null, transaction2);
                        transaction2.close();
                    } catch (RemoteException e) {
                        Log.e("RemoteAnimRunnerCompat", "Failed to call app controlled animation finished callback", e);
                    }
                }
            };
            synchronized (this.mFinishRunnables) {
            }
        }
    }

    static {
        ONE_UI_6_1 = Build.VERSION.SEM_PLATFORM_INT >= 150100;
        sAnimCallbacks = new HashMap();
    }

    public abstract void onAnimationStart();

    public final void onAnimationStart(int i, RemoteAnimationTarget[] remoteAnimationTargetArr, RemoteAnimationTarget[] remoteAnimationTargetArr2, RemoteAnimationTarget[] remoteAnimationTargetArr3, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback) {
        onAnimationStart();
    }
}
