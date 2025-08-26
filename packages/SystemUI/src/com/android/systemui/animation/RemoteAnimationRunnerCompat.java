package com.android.systemui.animation;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.Log;
import android.util.secutil.Slog;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.window.DesktopModeFlags;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.RemoteAnimationRunnerHelper;
import android.window.RemoteTransitionStub;
import android.window.TransitionInfo;
import android.window.WindowContainerTransaction;
import com.android.internal.util.Preconditions;
import com.android.systemui.animation.RemoteAnimationRunnerCompat;
import com.android.wm.shell.shared.CounterRotator;
import com.android.wm.shell.shared.TransitionUtil;
import com.samsung.android.rune.CoreRune;
import java.util.function.Predicate;

/* loaded from: classes.dex */
public abstract class RemoteAnimationRunnerCompat extends IRemoteAnimationRunner.Stub {
    public static final boolean FW_SHELL_TRANSITION_MERGE;
    public static final boolean IS_SHELL_TRANSITION_ENABLED;

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

        /* JADX WARN: Removed duplicated region for block: B:132:0x0300 A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:65:0x01a5  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void startAnimation(final IBinder iBinder, final TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, final IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
            CounterRotator counterRotator;
            TransitionInfo.Change change;
            int i;
            float f;
            float f2;
            CounterRotator counterRotator2;
            TransitionInfo.Change change2;
            final CounterRotator counterRotator3;
            CounterRotator counterRotator4;
            TransitionInfo.Change change3;
            final ArrayMap arrayMap = new ArrayMap();
            if (RemoteAnimationRunnerCompat.FW_SHELL_TRANSITION_MERGE) {
                this.mLeashMap = arrayMap;
            }
            final TransitionUtil.LeafTaskFilter leafTaskFilter = new TransitionUtil.LeafTaskFilter();
            RemoteAnimationTarget[] remoteAnimationTargetArrWrap = RemoteAnimationTargetCompat.wrap(transitionInfo, transaction, arrayMap, new Predicate() { // from class: com.android.systemui.animation.RemoteAnimationTargetCompat$$ExternalSyntheticLambda1
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    TransitionUtil.LeafTaskFilter leafTaskFilter2 = leafTaskFilter;
                    TransitionInfo.Change change4 = (TransitionInfo.Change) obj;
                    if (change4.getActivityComponent() != null) {
                        return true;
                    }
                    return leafTaskFilter2.test(change4);
                }
            });
            final boolean z = true;
            RemoteAnimationTarget[] remoteAnimationTargetArrWrap2 = RemoteAnimationTargetCompat.wrap(transitionInfo, transaction, arrayMap, new Predicate() { // from class: com.android.systemui.animation.RemoteAnimationTargetCompat$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean z2 = z;
                    TransitionInfo.Change change4 = (TransitionInfo.Change) obj;
                    if (change4.getActivityComponent() != null) {
                        return false;
                    }
                    return z2 ? TransitionUtil.isWallpaper(change4) : TransitionUtil.isNonApp(change4);
                }
            });
            final boolean z2 = false;
            RemoteAnimationTarget[] remoteAnimationTargetArrWrap3 = RemoteAnimationTargetCompat.wrap(transitionInfo, transaction, arrayMap, new Predicate() { // from class: com.android.systemui.animation.RemoteAnimationTargetCompat$$ExternalSyntheticLambda0
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    boolean z22 = z2;
                    TransitionInfo.Change change4 = (TransitionInfo.Change) obj;
                    if (change4.getActivityComponent() != null) {
                        return false;
                    }
                    return z22 ? TransitionUtil.isWallpaper(change4) : TransitionUtil.isNonApp(change4);
                }
            });
            TransitionInfo.Change change4 = null;
            int endRotation = 0;
            int iM = 0;
            boolean z3 = false;
            boolean z4 = false;
            TransitionInfo.Change change5 = null;
            float fWidth = 0.0f;
            float fHeight = 0.0f;
            for (int iM2 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); iM2 >= 0; iM2--) {
                TransitionInfo.Change change6 = (TransitionInfo.Change) transitionInfo.getChanges().get(iM2);
                if (arrayMap.containsKey(change6.getLeash())) {
                    if (change6.getTaskInfo() != null) {
                        change3 = change6;
                        if (change6.getTaskInfo().getActivityType() == 2) {
                            z3 = change3.getMode() == 1 || change3.getMode() == 3;
                            iM = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, iM2);
                            if (CoreRune.FW_SHELL_TRANSITION_BUG_FIX && (change3.getMode() == 2 || change3.getMode() == 4)) {
                                Slog.d("RemoteAnimRunnerCompat", "go to home from home. ignore isReturnToHome if set");
                                change4 = change3;
                                z4 = true;
                            } else {
                                change4 = change3;
                            }
                        }
                        if (change3.getParent() != null && change3.getEndRotation() >= 0 && change3.getEndRotation() != change3.getStartRotation()) {
                            endRotation = change3.getEndRotation() - change3.getStartRotation();
                            fWidth = change3.getEndAbsBounds().width();
                            fHeight = change3.getEndAbsBounds().height();
                        }
                    } else {
                        change3 = change6;
                    }
                    if ((change3.getFlags() & 2) != 0) {
                        change5 = change3;
                    }
                    if (change3.getParent() != null) {
                    }
                }
            }
            CounterRotator counterRotator5 = new CounterRotator();
            CounterRotator counterRotator6 = new CounterRotator();
            if (change4 == null || endRotation == 0 || change4.getParent() == null) {
                counterRotator = counterRotator5;
                change = change4;
                i = endRotation;
                f = fWidth;
                f2 = fHeight;
                counterRotator2 = counterRotator6;
                change2 = change5;
            } else {
                TransitionInfo.Change change7 = transitionInfo.getChange(change4.getParent());
                if (change7 != null) {
                    SurfaceControl leash = change7.getLeash();
                    counterRotator = counterRotator5;
                    change = change4;
                    float f3 = fHeight;
                    counterRotator2 = counterRotator6;
                    change2 = change5;
                    int i2 = endRotation;
                    float f4 = fWidth;
                    f2 = f3;
                    counterRotator.setup(transaction, leash, i2, f4, f2);
                    i = i2;
                    f = f4;
                } else {
                    float f5 = fHeight;
                    counterRotator2 = counterRotator6;
                    change2 = change5;
                    int i3 = endRotation;
                    float f6 = fWidth;
                    f2 = f5;
                    counterRotator = counterRotator5;
                    change = change4;
                    i = i3;
                    f = f6;
                    Log.e("RemoteAnimRunnerCompat", "Malformed: " + change + " has parent=" + change.getParent() + " but it's not in info.");
                }
                SurfaceControl surfaceControl = counterRotator.mSurface;
                if (surfaceControl != null) {
                    transaction.setLayer(surfaceControl, iM);
                }
            }
            if (!z3 || (CoreRune.FW_SHELL_TRANSITION_BUG_FIX && z4)) {
                if (change != null) {
                    SurfaceControl surfaceControl2 = (SurfaceControl) arrayMap.get(change.getLeash());
                    SurfaceControl surfaceControl3 = counterRotator.mSurface;
                    if (surfaceControl3 != null) {
                        transaction.reparent(surfaceControl2, surfaceControl3);
                    }
                }
                if (change2 != null && i != 0 && change2.getParent() != null) {
                    TransitionInfo.Change change8 = transitionInfo.getChange(change2.getParent());
                    if (change8 != null) {
                        SurfaceControl leash2 = change8.getLeash();
                        counterRotator3 = counterRotator;
                        counterRotator4 = counterRotator2;
                        counterRotator4.setup(transaction, leash2, i, f, f2);
                    } else {
                        counterRotator3 = counterRotator;
                        counterRotator4 = counterRotator2;
                        Log.e("RemoteAnimRunnerCompat", "Malformed: " + change2 + " has parent=" + change2.getParent() + " but it's not in info.");
                    }
                    SurfaceControl surfaceControl4 = counterRotator4.mSurface;
                    if (surfaceControl4 != null) {
                        transaction.setLayer(surfaceControl4, -1);
                        SurfaceControl surfaceControl5 = (SurfaceControl) arrayMap.get(change2.getLeash());
                        SurfaceControl surfaceControl6 = counterRotator4.mSurface;
                        if (surfaceControl6 != null) {
                            transaction.reparent(surfaceControl5, surfaceControl6);
                        }
                    }
                }
                transaction.apply();
                final CounterRotator counterRotator7 = counterRotator4;
                final Runnable runnable = new Runnable() { // from class: com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        CounterRotator counterRotator8 = counterRotator3;
                        CounterRotator counterRotator9 = counterRotator7;
                        TransitionInfo transitionInfo2 = transitionInfo;
                        ArrayMap arrayMap2 = arrayMap;
                        IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback2 = iRemoteTransitionFinishedCallback;
                        int i4 = RemoteAnimationRunnerCompat.AnonymousClass1.$r8$clinit;
                        SurfaceControl.Transaction transaction2 = new SurfaceControl.Transaction();
                        SurfaceControl surfaceControl7 = counterRotator8.mSurface;
                        if (surfaceControl7 != null) {
                            transaction2.remove(surfaceControl7);
                        }
                        SurfaceControl surfaceControl8 = counterRotator9.mSurface;
                        if (surfaceControl8 != null) {
                            transaction2.remove(surfaceControl8);
                        }
                        transitionInfo2.releaseAllSurfaces();
                        arrayMap2.clear();
                        if (RemoteAnimationRunnerCompat.FW_SHELL_TRANSITION_MERGE) {
                            RemoteAnimationRunnerHelper.getInstance().clear();
                        }
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
                this.val$runner.onAnimationStart(0, remoteAnimationTargetArrWrap, remoteAnimationTargetArrWrap2, remoteAnimationTargetArrWrap3, new IRemoteAnimationFinishedCallback() { // from class: com.android.systemui.animation.RemoteAnimationRunnerCompat.1.1
                    public final IBinder asBinder() {
                        return null;
                    }

                    public final void onAnimationFinished() {
                        synchronized (AnonymousClass1.this.mFinishRunnables) {
                            try {
                                if (AnonymousClass1.this.mFinishRunnables.remove(iBinder) == null) {
                                    return;
                                }
                                runnable.run();
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                });
                return;
            }
            SurfaceControl surfaceControl7 = counterRotator.mSurface;
            if (surfaceControl7 != null) {
                transaction.setLayer(surfaceControl7, transitionInfo.getChanges().size() * 3);
            }
            for (int iM3 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1); iM3 >= 0; iM3--) {
                TransitionInfo.Change change9 = (TransitionInfo.Change) transitionInfo.getChanges().get(iM3);
                SurfaceControl surfaceControl8 = (SurfaceControl) arrayMap.get(change9.getLeash());
                if (surfaceControl8 != null) {
                    int mode = ((TransitionInfo.Change) transitionInfo.getChanges().get(iM3)).getMode();
                    if (TransitionInfo.isIndependent(change9, transitionInfo)) {
                        if (mode == 2 || mode == 4) {
                            transaction.setLayer(surfaceControl8, (transitionInfo.getChanges().size() * 3) - iM3);
                            SurfaceControl surfaceControl9 = counterRotator.mSurface;
                            if (surfaceControl9 != null) {
                                transaction.reparent(surfaceControl8, surfaceControl9);
                            }
                        }
                    }
                }
            }
            for (int length = remoteAnimationTargetArrWrap2.length - 1; length >= 0; length--) {
                transaction.show(remoteAnimationTargetArrWrap2[length].leash);
                transaction.setAlpha(remoteAnimationTargetArrWrap2[length].leash, 1.0f);
            }
            if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_EXIT_TRANSITIONS_BUGFIX.isTrue()) {
                Preconditions.checkArgument(TransitionUtil.isOpeningMode(change.getMode()));
                if (TransitionUtil.isClosingType(transitionInfo.getType()) || DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_EXIT_BY_MINIMIZE_TRANSITION_BUGFIX.isTrue()) {
                    int iM4 = RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, 1);
                    while (true) {
                        if (iM4 < 0) {
                            break;
                        }
                        TransitionInfo.Change change10 = (TransitionInfo.Change) transitionInfo.getChanges().get(iM4);
                        if (arrayMap.containsKey(change10.getLeash()) && change10.getTaskInfo() != null && ((change10.getTaskInfo().isFreeform() || change10.hasFlags(67108864)) && TransitionUtil.isClosingMode(change10.getMode()))) {
                            transaction.setAlpha((SurfaceControl) arrayMap.get(change.getLeash()), 0.0f);
                            break;
                        }
                        iM4--;
                    }
                }
            }
            counterRotator3 = counterRotator;
            counterRotator4 = counterRotator2;
            transaction.apply();
            final CounterRotator counterRotator72 = counterRotator4;
            final Runnable runnable2 = new Runnable() { // from class: com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    CounterRotator counterRotator8 = counterRotator3;
                    CounterRotator counterRotator9 = counterRotator72;
                    TransitionInfo transitionInfo2 = transitionInfo;
                    ArrayMap arrayMap2 = arrayMap;
                    IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback2 = iRemoteTransitionFinishedCallback;
                    int i4 = RemoteAnimationRunnerCompat.AnonymousClass1.$r8$clinit;
                    SurfaceControl.Transaction transaction2 = new SurfaceControl.Transaction();
                    SurfaceControl surfaceControl72 = counterRotator8.mSurface;
                    if (surfaceControl72 != null) {
                        transaction2.remove(surfaceControl72);
                    }
                    SurfaceControl surfaceControl82 = counterRotator9.mSurface;
                    if (surfaceControl82 != null) {
                        transaction2.remove(surfaceControl82);
                    }
                    transitionInfo2.releaseAllSurfaces();
                    arrayMap2.clear();
                    if (RemoteAnimationRunnerCompat.FW_SHELL_TRANSITION_MERGE) {
                        RemoteAnimationRunnerHelper.getInstance().clear();
                    }
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
