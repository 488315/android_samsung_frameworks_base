package com.android.systemui.keyguard;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserManager;
import android.service.dreams.Flags;
import android.util.ArrayMap;
import android.util.Slog;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationDefinition;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.ViewGroup;
import android.view.WindowManagerPolicyConstants;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.RemoteTransitionStub;
import android.window.TransitionInfo;
import android.window.WindowContainerTransaction;
import androidx.compose.animation.core.ArcMode$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.core.view.OneShotPreDrawListener;
import com.android.internal.foldables.FoldGracePeriodProvider;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.policy.IKeyguardDrawnCallback;
import com.android.internal.policy.IKeyguardExitCallback;
import com.android.internal.policy.IKeyguardService;
import com.android.internal.policy.IKeyguardStateCallback;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.mediator.ScreenOnCoordinator;
import com.android.systemui.BootAnimationFinishedCacheImpl;
import com.android.systemui.LsRune;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.animation.RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0;
import com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0;
import com.android.systemui.deviceentry.data.repository.DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.KeyguardEnabledInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.ui.binder.KeyguardSurfaceBehindParamsApplier;
import com.android.systemui.keyguard.ui.viewmodel.KeyguardSurfaceBehindViewModel;
import com.android.systemui.keyguard.ui.viewmodel.WindowManagerLockscreenVisibilityViewModel;
import com.android.systemui.power.data.repository.PowerRepository;
import com.android.systemui.power.data.repository.PowerRepositoryImpl;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.shared.model.ScreenPowerState;
import com.android.systemui.power.shared.model.WakeSleepReason;
import com.android.systemui.power.shared.model.WakefulnessModel;
import com.android.systemui.power.shared.model.WakefulnessState;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.unfold.FoldAodAnimationController;
import com.android.systemui.unfold.FullscreenLightRevealAnimation;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.concurrency.PendingTasksContainer;
import com.android.wm.shell.shared.CounterRotator;
import com.android.wm.shell.shared.ShellTransitions;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.knox.accounts.HostAuth;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;
import dagger.Lazy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import kotlin.Unit;
import kotlin.jvm.internal.StringCompanionObject;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class KeyguardService extends Service {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass3 mBinder;
    public final DisplayTracker mDisplayTracker;
    public final AnonymousClass2 mFoldGracePeriodProvider;
    public final KeyguardEnabledInteractor mKeyguardEnabledInteractor;
    public final KeyguardInteractor mKeyguardInteractor;
    public final KeyguardLifecyclesDispatcher mKeyguardLifecyclesDispatcher;
    public final KeyguardViewMediator mKeyguardViewMediator;
    public final Executor mMainExecutor;
    public final PowerInteractor mPowerInteractor;
    public final ScreenOnCoordinator mScreenOnCoordinator;
    public final ShellTransitions mShellTransitions;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.keyguard.KeyguardService$1, reason: invalid class name */
    public final class AnonymousClass1 extends RemoteTransitionStub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final /* synthetic */ KeyguardViewMediator val$keyguardViewMediator;
        public final /* synthetic */ IRemoteAnimationRunner val$runner;
        public final ArrayMap mLeashMap = new ArrayMap();
        public final CounterRotator mCounterRotator = new CounterRotator();
        public final Map mFinishCallbacks = new WeakHashMap();

        public AnonymousClass1(KeyguardViewMediator keyguardViewMediator, IRemoteAnimationRunner iRemoteAnimationRunner) {
            this.val$keyguardViewMediator = keyguardViewMediator;
            this.val$runner = iRemoteAnimationRunner;
        }

        public final void finish(IBinder iBinder) {
            SurfaceControl.Transaction transaction;
            IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback;
            synchronized (this.mLeashMap) {
                try {
                    SurfaceControl surfaceControl = this.mCounterRotator.mSurface;
                    if (surfaceControl == null || !surfaceControl.isValid()) {
                        transaction = null;
                    } else {
                        transaction = new SurfaceControl.Transaction();
                        SurfaceControl surfaceControl2 = this.mCounterRotator.mSurface;
                        if (surfaceControl2 != null) {
                            transaction.remove(surfaceControl2);
                        }
                    }
                    this.mLeashMap.clear();
                    iRemoteTransitionFinishedCallback = (IRemoteTransitionFinishedCallback) ((WeakHashMap) this.mFinishCallbacks).remove(iBinder);
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (iRemoteTransitionFinishedCallback != null) {
                iRemoteTransitionFinishedCallback.onTransitionFinished((WindowContainerTransaction) null, transaction);
            } else if (transaction != null) {
                transaction.apply();
            }
        }

        public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
            if ((transitionInfo.getFlags() & 2048) != 0) {
                this.val$keyguardViewMediator.setPendingLock(true);
                this.val$keyguardViewMediator.cancelKeyguardExitAnimation();
            } else {
                try {
                    this.val$runner.onAnimationCancelled();
                    finish(iBinder2);
                } catch (RemoteException unused) {
                }
            }
        }

        public final void startAnimation(final IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
            RemoteAnimationTarget[] m1947$$Nest$smwrap;
            RemoteAnimationTarget[] m1947$$Nest$smwrap2;
            ActivityManager.RunningTaskInfo runningTaskInfo;
            Slog.d("KeyguardService", "Starts IRemoteAnimationRunner: info=" + transitionInfo);
            int i = 0;
            RemoteAnimationTarget[] remoteAnimationTargetArr = new RemoteAnimationTarget[0];
            synchronized (this.mLeashMap) {
                m1947$$Nest$smwrap = KeyguardService.m1947$$Nest$smwrap(transitionInfo, false, transaction, this.mLeashMap);
                m1947$$Nest$smwrap2 = KeyguardService.m1947$$Nest$smwrap(transitionInfo, true, transaction, this.mLeashMap);
                ((WeakHashMap) this.mFinishCallbacks).put(iBinder, iRemoteTransitionFinishedCallback);
            }
            for (TransitionInfo.Change change : transitionInfo.getChanges()) {
                if (TransitionInfo.isIndependent(change, transitionInfo)) {
                    transaction.setAlpha(change.getLeash(), 1.0f);
                }
            }
            if (!this.val$keyguardViewMediator.mHelper.initAlphaForAnimationTargets(this.val$runner, transaction, m1947$$Nest$smwrap, m1947$$Nest$smwrap2)) {
                for (RemoteAnimationTarget remoteAnimationTarget : m1947$$Nest$smwrap) {
                    if (remoteAnimationTarget.mode == 0) {
                        transaction.setAlpha(remoteAnimationTarget.leash, 0.0f);
                    }
                }
                for (RemoteAnimationTarget remoteAnimationTarget2 : m1947$$Nest$smwrap2) {
                    if (remoteAnimationTarget2.mode == 0) {
                        transaction.setAlpha(remoteAnimationTarget2.leash, 0.0f);
                    }
                }
            }
            boolean z = (transitionInfo.getFlags() & 2048) != 0;
            boolean z2 = (transitionInfo.getFlags() & 262144) != 0;
            if (z && !z2) {
                for (RemoteAnimationTarget remoteAnimationTarget3 : m1947$$Nest$smwrap) {
                    if (remoteAnimationTarget3.mode == 1) {
                        transaction.setAlpha(remoteAnimationTarget3.leash, 0.0f);
                    }
                }
            }
            if (Flags.dismissDreamOnKeyguardDismiss() && (transitionInfo.getFlags() & 256) != 0) {
                int length = m1947$$Nest$smwrap.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    RemoteAnimationTarget remoteAnimationTarget4 = m1947$$Nest$smwrap[i2];
                    ActivityManager.RunningTaskInfo runningTaskInfo2 = remoteAnimationTarget4.taskInfo;
                    if (runningTaskInfo2 != null && runningTaskInfo2.getActivityType() == 5 && remoteAnimationTarget4.mode == 1) {
                        transaction.hide(remoteAnimationTarget4.leash);
                        break;
                    }
                    i2++;
                }
            }
            transaction.apply();
            IRemoteAnimationRunner iRemoteAnimationRunner = this.val$runner;
            int type = transitionInfo.getType();
            int flags = transitionInfo.getFlags();
            if (type == 7 || (flags & 256) != 0) {
                i = m1947$$Nest$smwrap.length == 0 ? 21 : 20;
            } else if (type == 8) {
                i = (m1947$$Nest$smwrap.length <= 0 || (runningTaskInfo = m1947$$Nest$smwrap[0].taskInfo) == null || runningTaskInfo.topActivityType != 5) ? 22 : 33;
            } else if (type == 9) {
                i = 23;
            } else {
                Slog.d("KeyguardService", "Unexpected transit type: " + type);
            }
            iRemoteAnimationRunner.onAnimationStart(i, m1947$$Nest$smwrap, m1947$$Nest$smwrap2, remoteAnimationTargetArr, new IRemoteAnimationFinishedCallback.Stub() { // from class: com.android.systemui.keyguard.KeyguardService.1.1
                public final void onAnimationFinished() {
                    Slog.d("KeyguardService", "Finish IRemoteAnimationRunner.");
                    AnonymousClass1 anonymousClass1 = AnonymousClass1.this;
                    IBinder iBinder2 = iBinder;
                    int i3 = AnonymousClass1.$r8$clinit;
                    anonymousClass1.finish(iBinder2);
                }
            });
        }
    }

    /* renamed from: -$$Nest$smwrap, reason: not valid java name */
    public static RemoteAnimationTarget[] m1947$$Nest$smwrap(TransitionInfo transitionInfo, boolean z, SurfaceControl.Transaction transaction, ArrayMap arrayMap) {
        TransitionInfo.Change change;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < transitionInfo.getChanges().size(); i++) {
            if (z == ((((TransitionInfo.Change) transitionInfo.getChanges().get(i)).getFlags() & 2) != 0)) {
                TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
                int i2 = change2.getTaskInfo() != null ? change2.getTaskInfo().taskId : -1;
                if ((i2 == -1 || change2.getParent() == null || (change = transitionInfo.getChange(change2.getParent())) == null || change.getTaskInfo() == null) && (i2 >= 0 || z)) {
                    arrayList.add(TransitionUtil.newTarget(change2, RemoteAnimationRunnerCompat$1$$ExternalSyntheticOutline0.m(transitionInfo, i), (change2.getFlags() & 1) != 0, transitionInfo, transaction, arrayMap));
                }
            }
        }
        return (RemoteAnimationTarget[]) arrayList.toArray(new RemoteAnimationTarget[arrayList.size()]);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.keyguard.KeyguardService$3] */
    public KeyguardService(KeyguardViewMediator keyguardViewMediator, KeyguardLifecyclesDispatcher keyguardLifecyclesDispatcher, ScreenOnCoordinator screenOnCoordinator, ShellTransitions shellTransitions, DisplayTracker displayTracker, WindowManagerLockscreenVisibilityViewModel windowManagerLockscreenVisibilityViewModel, WindowManagerLockscreenVisibilityManager windowManagerLockscreenVisibilityManager, KeyguardSurfaceBehindViewModel keyguardSurfaceBehindViewModel, KeyguardSurfaceBehindParamsApplier keyguardSurfaceBehindParamsApplier, CoroutineScope coroutineScope, FeatureFlags featureFlags, PowerInteractor powerInteractor, WindowManagerOcclusionManager windowManagerOcclusionManager, Lazy lazy, Executor executor, KeyguardInteractor keyguardInteractor, KeyguardEnabledInteractor keyguardEnabledInteractor) {
        new Lazy(this) { // from class: com.android.systemui.keyguard.KeyguardService.2
            @Override // dagger.Lazy
            public final Object get() {
                return new FoldGracePeriodProvider();
            }
        };
        this.mBinder = new IKeyguardService.Stub() { // from class: com.android.systemui.keyguard.KeyguardService.3
            public static void trace(String str) {
                Log.i("KeyguardService", str);
                Trace.asyncTraceForTrackEnd(4096L, "IKeyguardService", 0);
                Trace.asyncTraceForTrackBegin(4096L, "IKeyguardService", str, 0);
            }

            public final void addStateMonitorCallback(IKeyguardStateCallback iKeyguardStateCallback) {
                trace("addStateMonitorCallback");
                KeyguardService.this.checkPermission();
                KeyguardService.this.mKeyguardViewMediator.addStateMonitorCallback(iKeyguardStateCallback);
            }

            public final void dismiss(IKeyguardDismissCallback iKeyguardDismissCallback, CharSequence charSequence) {
                trace("dismiss message=" + ((Object) charSequence));
                KeyguardService.this.checkPermission();
                KeyguardService.this.mKeyguardViewMediator.dismiss(iKeyguardDismissCallback, charSequence);
            }

            public final void dismissKeyguardToLaunch(Intent intent) {
                trace("dismissKeyguardToLaunch");
                KeyguardService.this.checkPermission();
                Slog.d("KeyguardService", "Ignoring dismissKeyguardToLaunch " + intent);
            }

            public final void doKeyguardTimeout(Bundle bundle) {
                trace("doKeyguardTimeout");
                KeyguardService.this.checkPermission();
                KeyguardService.this.mKeyguardViewMediator.doKeyguardTimeout(bundle);
            }

            public final void onBootCompleted() {
                trace("onBootCompleted");
                KeyguardService.this.checkPermission();
                KeyguardService.this.mKeyguardViewMediator.onBootCompleted();
            }

            public final void onDreamingStarted() {
                trace("onDreamingStarted");
                KeyguardService.this.checkPermission();
                KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) KeyguardService.this.mKeyguardInteractor.repository;
                keyguardRepositoryImpl.isDreaming.updateState(null, Boolean.TRUE);
                KeyguardService.this.mKeyguardViewMediator.onDreamingStarted();
            }

            public final void onDreamingStopped() {
                trace("onDreamingStopped");
                KeyguardService.this.checkPermission();
                KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) KeyguardService.this.mKeyguardInteractor.repository;
                keyguardRepositoryImpl.isDreaming.updateState(null, Boolean.FALSE);
                KeyguardService.this.mKeyguardViewMediator.onDreamingStopped();
            }

            public final void onFinishedBootAnim() {
                KeyguardService.this.checkPermission();
                final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardService.this.mKeyguardViewMediator.mHelper;
                if (!keyguardViewMediatorHelperImpl.firstKeyguardShown || keyguardViewMediatorHelperImpl.bootAnimationFinishedTrigger == null) {
                    return;
                }
                KeyguardViewMediatorHelperImpl.logD("BootAnimationFinished");
                Handler.getMain().post(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$onFinishedBootAnimation$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ((BootAnimationFinishedCacheImpl) KeyguardViewMediatorHelperImpl.this.bootAnimationFinishedTrigger).setBootAnimationFinished();
                    }
                });
                keyguardViewMediatorHelperImpl.firstKeyguardShown = false;
            }

            public final void onFinishedGoingToSleep(int i, boolean z) {
                boolean z2;
                trace("onFinishedGoingToSleep pmSleepReason=" + i + " cameraGestureTriggered=" + z);
                KeyguardService.this.checkPermission();
                KeyguardService.this.mKeyguardViewMediator.onFinishedGoingToSleep(WindowManagerPolicyConstants.translateSleepReasonToOffReason(i), z);
                PowerInteractor powerInteractor2 = KeyguardService.this.mPowerInteractor;
                if (z) {
                    powerInteractor2.getClass();
                } else if (!((WakefulnessModel) ((PowerRepositoryImpl) powerInteractor2.repository).wakefulness.$$delegate_0.getValue()).powerButtonLaunchGestureTriggered) {
                    z2 = false;
                    PowerRepository.updateWakefulness$default(powerInteractor2.repository, WakefulnessState.ASLEEP, null, null, z2, 6);
                    KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(7);
                }
                z2 = true;
                PowerRepository.updateWakefulness$default(powerInteractor2.repository, WakefulnessState.ASLEEP, null, null, z2, 6);
                KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(7);
            }

            public final void onFinishedWakingUp() {
                trace("onFinishedWakingUp");
                Trace.beginSection("KeyguardService.mBinder#onFinishedWakingUp");
                KeyguardService.this.checkPermission();
                PowerInteractor powerInteractor2 = KeyguardService.this.mPowerInteractor;
                powerInteractor2.getClass();
                PowerRepository.updateWakefulness$default(powerInteractor2.repository, WakefulnessState.AWAKE, null, null, false, 14);
                KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(5);
                KeyguardService.this.mKeyguardViewMediator.mHelper.getClass();
                boolean z = KeyguardViewMediatorHelperImplKt.DEBUG_DISABLE_REMOTE_UNLOCK_ANIMATION;
                Trace.endSection();
            }

            public final void onScreenTurnedOff() {
                trace("onScreenTurnedOff");
                KeyguardService.this.checkPermission();
                KeyguardService.this.mPowerInteractor.onScreenPowerStateUpdated(ScreenPowerState.SCREEN_OFF);
                KeyguardService.this.mKeyguardViewMediator.onScreenTurnedOff();
                KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(3);
                if (SafeUIState.isSysUiSafeModeEnabled()) {
                    KeyguardService.this.mScreenOnCoordinator.pendingTasks.reset();
                }
            }

            public final void onScreenTurnedOn() {
                final FoldAodAnimationController foldAodAnimationController;
                trace("onScreenTurnedOn");
                Trace.beginSection("KeyguardService.mBinder#onScreenTurnedOn");
                KeyguardService.this.checkPermission();
                KeyguardService.this.mPowerInteractor.onScreenPowerStateUpdated(ScreenPowerState.SCREEN_ON);
                KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(1);
                if (SafeUIState.isSysUiSafeModeEnabled() && (foldAodAnimationController = KeyguardService.this.mScreenOnCoordinator.foldAodAnimationController) != null) {
                    foldAodAnimationController.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.unfold.FoldAodAnimationController$onScreenTurnedOn$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            FoldAodAnimationController foldAodAnimationController2 = FoldAodAnimationController.this;
                            if (foldAodAnimationController2.shouldPlayAnimation) {
                                Runnable runnable = foldAodAnimationController2.cancelAnimation;
                                if (runnable != null) {
                                    runnable.run();
                                }
                                FoldAodAnimationController foldAodAnimationController3 = FoldAodAnimationController.this;
                                foldAodAnimationController3.cancelAnimation = foldAodAnimationController3.mainExecutor.executeDelayed(foldAodAnimationController3.startAnimationRunnable, 0L);
                                FoldAodAnimationController.this.shouldPlayAnimation = false;
                            }
                        }
                    });
                }
                Trace.endSection();
            }

            public final void onScreenTurningOff() {
                trace("onScreenTurningOff");
                KeyguardService.this.checkPermission();
                KeyguardService.this.mPowerInteractor.onScreenPowerStateUpdated(ScreenPowerState.SCREEN_TURNING_OFF);
                KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(2);
            }

            public final void onScreenTurningOn(final IKeyguardDrawnCallback iKeyguardDrawnCallback) {
                trace("onScreenTurningOn");
                Trace.beginSection("KeyguardService.mBinder#onScreenTurningOn");
                KeyguardService.this.checkPermission();
                KeyguardService.this.mPowerInteractor.onScreenPowerStateUpdated(ScreenPowerState.SCREEN_TURNING_ON);
                if (SafeUIState.isSysUiSafeModeEnabled()) {
                    KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(iKeyguardDrawnCallback);
                    final int identityHashCode = System.identityHashCode(iKeyguardDrawnCallback);
                    Trace.beginAsyncSection("Waiting for KeyguardDrawnCallback#onDrawn", identityHashCode);
                    final ScreenOnCoordinator screenOnCoordinator2 = KeyguardService.this.mScreenOnCoordinator;
                    final Runnable runnable = new Runnable(this) { // from class: com.android.systemui.keyguard.KeyguardService.3.1
                        public boolean mInvoked;

                        @Override // java.lang.Runnable
                        public final void run() {
                            if (iKeyguardDrawnCallback == null) {
                                return;
                            }
                            if (this.mInvoked) {
                                android.util.Log.w("KeyguardService", "KeyguardDrawnCallback#onDrawn() invoked > 1 times");
                                return;
                            }
                            this.mInvoked = true;
                            try {
                                Trace.endAsyncSection("Waiting for KeyguardDrawnCallback#onDrawn", identityHashCode);
                                iKeyguardDrawnCallback.onDrawn();
                            } catch (RemoteException e) {
                                android.util.Log.w("KeyguardService", "Exception calling onDrawn():", e);
                            }
                        }
                    };
                    screenOnCoordinator2.getClass();
                    Trace.beginSection("ScreenOnCoordinator#onScreenTurningOn");
                    PendingTasksContainer pendingTasksContainer = screenOnCoordinator2.pendingTasks;
                    pendingTasksContainer.reset();
                    final FoldAodAnimationController foldAodAnimationController = screenOnCoordinator2.foldAodAnimationController;
                    if (foldAodAnimationController != null) {
                        final Runnable registerTask = pendingTasksContainer.registerTask("fold-to-aod");
                        foldAodAnimationController.mainExecutor.execute(new Runnable() { // from class: com.android.systemui.unfold.FoldAodAnimationController$onScreenTurningOn$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                FoldAodAnimationController foldAodAnimationController2 = FoldAodAnimationController.this;
                                if (foldAodAnimationController2.shouldPlayAnimation) {
                                    if (foldAodAnimationController2.isScrimOpaque) {
                                        registerTask.run();
                                    } else {
                                        foldAodAnimationController2.pendingScrimReadyCallback = registerTask;
                                    }
                                } else if (foldAodAnimationController2.isFolded && !foldAodAnimationController2.isFoldHandled && foldAodAnimationController2.alwaysOnEnabled && ((Boolean) ((KeyguardInteractor) foldAodAnimationController2.keyguardInteractor.get()).isDozing.getValue()).booleanValue()) {
                                    FoldAodAnimationController.this.setAnimationState(true);
                                    FoldAodAnimationController.this.getShadeFoldAnimator$1().prepareFoldToAodAnimation();
                                    com.android.systemui.Flags.migrateClocksToBlueprint();
                                    ViewGroup view = FoldAodAnimationController.this.getShadeFoldAnimator$1().getView();
                                    if (view != null) {
                                        OneShotPreDrawListener.add(view, registerTask);
                                    }
                                } else {
                                    registerTask.run();
                                }
                                FoldAodAnimationController foldAodAnimationController3 = FoldAodAnimationController.this;
                                if (foldAodAnimationController3.isFolded) {
                                    foldAodAnimationController3.isFoldHandled = true;
                                }
                            }
                        });
                    }
                    Set<FullscreenLightRevealAnimation> set = screenOnCoordinator2.fullScreenLightRevealAnimations;
                    if (set != null) {
                        for (FullscreenLightRevealAnimation fullscreenLightRevealAnimation : set) {
                            fullscreenLightRevealAnimation.onScreenTurningOn(pendingTasksContainer.registerTask(fullscreenLightRevealAnimation.getClass().getSimpleName()));
                        }
                    }
                    pendingTasksContainer.onTasksComplete(new Runnable(runnable, screenOnCoordinator2) { // from class: com.android.keyguard.mediator.ScreenOnCoordinator$onScreenTurningOn$2
                        public final /* synthetic */ Runnable $onDrawn;

                        @Override // java.lang.Runnable
                        public final void run() {
                            com.android.systemui.Flags.FEATURE_FLAGS.getClass();
                            this.$onDrawn.run();
                        }
                    });
                    Trace.endSection();
                } else {
                    KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardService.this.mKeyguardViewMediator.mHelper;
                    keyguardViewMediatorHelperImpl.enableLooperLogController(3, 3000L);
                    keyguardViewMediatorHelperImpl.screenTuringOnTime = SystemClock.elapsedRealtime();
                    if (keyguardViewMediatorHelperImpl.fastUnlockController.isFastWakeAndUnlockMode() && !keyguardViewMediatorHelperImpl.fastUnlockController.needsBlankScreen) {
                        synchronized (keyguardViewMediatorHelperImpl.lock$delegate.getValue()) {
                            keyguardViewMediatorHelperImpl.drawnCallback = iKeyguardDrawnCallback;
                            Unit unit = Unit.INSTANCE;
                        }
                        iKeyguardDrawnCallback = null;
                    }
                    if (LsRune.KEYGUARD_PERFORMANCE_SCREEN_ON) {
                        ViewMediatorProvider viewMediatorProvider = keyguardViewMediatorHelperImpl.viewMediatorProvider;
                        if (viewMediatorProvider == null) {
                            viewMediatorProvider = null;
                        }
                        if (((Boolean) viewMediatorProvider.isBootCompleted.invoke()).booleanValue() && iKeyguardDrawnCallback != null) {
                            keyguardViewMediatorHelperImpl.notifyDrawn(iKeyguardDrawnCallback);
                            iKeyguardDrawnCallback = null;
                        }
                    }
                    Message obtainMessage = keyguardViewMediatorHelperImpl.getHandler$1().obtainMessage(1002, iKeyguardDrawnCallback);
                    boolean z = LsRune.KEYGUARD_SUB_DISPLAY_LOCK;
                    KeyguardFoldControllerImpl keyguardFoldControllerImpl = keyguardViewMediatorHelperImpl.foldControllerImpl;
                    if ((z && keyguardFoldControllerImpl.isUnlockOnFoldOpened()) || ((z || LsRune.KEYGUARD_SUB_DISPLAY_COVER) && keyguardViewMediatorHelperImpl.getHandler$1().hasMessages(1003) && keyguardFoldControllerImpl.isFoldOpened())) {
                        keyguardViewMediatorHelperImpl.getHandler$1().sendMessageAtFrontOfQueue(obtainMessage);
                    } else {
                        keyguardViewMediatorHelperImpl.getHandler$1().sendMessage(obtainMessage);
                    }
                    KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch((Object) null);
                }
                Trace.endSection();
            }

            public final void onShortPowerPressedGoHome() {
                trace("onShortPowerPressedGoHome");
                KeyguardService.this.checkPermission();
                KeyguardService.this.mKeyguardViewMediator.getClass();
            }

            public final void onStartedGoingToSleep(int i) {
                trace("onStartedGoingToSleep pmSleepReason=" + i);
                KeyguardService.this.checkPermission();
                KeyguardService.this.mKeyguardViewMediator.onStartedGoingToSleep(WindowManagerPolicyConstants.translateSleepReasonToOffReason(i));
                PowerInteractor powerInteractor2 = KeyguardService.this.mPowerInteractor;
                powerInteractor2.getClass();
                WakefulnessState wakefulnessState = WakefulnessState.STARTING_TO_SLEEP;
                WakeSleepReason.Companion.getClass();
                PowerRepository.updateWakefulness$default(powerInteractor2.repository, wakefulnessState, null, i != 4 ? i != 13 ? WakeSleepReason.OTHER : WakeSleepReason.FOLD : WakeSleepReason.POWER_BUTTON, false, 2);
                KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(6, i);
            }

            public final void onStartedWakingUp(int i, boolean z) {
                boolean z2;
                trace("onStartedWakingUp pmWakeReason=" + i + " cameraGestureTriggered=" + z);
                Trace.beginSection("KeyguardService.mBinder#onStartedWakingUp");
                KeyguardService.this.checkPermission();
                KeyguardService.this.mKeyguardViewMediator.onStartedWakingUp(i, z);
                PowerInteractor powerInteractor2 = KeyguardService.this.mPowerInteractor;
                if (z) {
                    powerInteractor2.getClass();
                } else if (!((WakefulnessModel) ((PowerRepositoryImpl) powerInteractor2.repository).wakefulness.$$delegate_0.getValue()).powerButtonLaunchGestureTriggered) {
                    z2 = false;
                    boolean z3 = z2;
                    WakefulnessState wakefulnessState = WakefulnessState.STARTING_TO_WAKE;
                    WakeSleepReason.Companion.getClass();
                    PowerRepository.updateWakefulness$default(powerInteractor2.repository, wakefulnessState, WakeSleepReason.Companion.fromPowerManagerWakeReason(i), null, z3, 4);
                    KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(4, i);
                    Trace.endSection();
                }
                z2 = true;
                boolean z32 = z2;
                WakefulnessState wakefulnessState2 = WakefulnessState.STARTING_TO_WAKE;
                WakeSleepReason.Companion.getClass();
                PowerRepository.updateWakefulness$default(powerInteractor2.repository, wakefulnessState2, WakeSleepReason.Companion.fromPowerManagerWakeReason(i), null, z32, 4);
                KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(4, i);
                Trace.endSection();
            }

            public final void onSystemKeyPressed(int i) {
                trace("onSystemKeyPressed keycode=" + i);
                KeyguardService.this.checkPermission();
                KeyguardService.this.mKeyguardViewMediator.getClass();
            }

            public final void onSystemReady() {
                trace("onSystemReady");
                Trace.beginSection("KeyguardService.mBinder#onSystemReady");
                KeyguardService.this.checkPermission();
                KeyguardService.this.mKeyguardViewMediator.onSystemReady();
                Trace.endSection();
            }

            public final void setCoverOccluded(boolean z) {
                Trace.beginSection("KeyguardService.mBinder#setCoverOccluded");
                KeyguardService.this.checkPermission();
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardService.this.mKeyguardViewMediator.mHelper;
                keyguardViewMediatorHelperImpl.getClass();
                Log.i("KeyguardViewMediator", "setCoverOccluded " + z);
                keyguardViewMediatorHelperImpl.removeMessage(1006);
                Handler handler$1 = keyguardViewMediatorHelperImpl.getHandler$1();
                handler$1.sendMessage(handler$1.obtainMessage(1006, z ? 1 : 0, 0));
                Trace.endSection();
            }

            public final void setCurrentUser(int i) {
                trace("Deprecated/NOT USED: setCurrentUser userId=" + i);
                KeyguardService.this.checkPermission();
                com.android.systemui.Flags.refactorGetCurrentUser();
                KeyguardService.this.mKeyguardViewMediator.setCurrentUser(i);
            }

            public final void setDexOccluded(boolean z) {
                Trace.beginSection("KeyguardService.mBinder#setDexOccluded");
                KeyguardService.this.checkPermission();
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardService.this.mKeyguardViewMediator.mHelper;
                keyguardViewMediatorHelperImpl.getClass();
                Log.i("KeyguardViewMediator", "setDexOccluded " + z);
                keyguardViewMediatorHelperImpl.removeMessage(1005);
                Handler handler$1 = keyguardViewMediatorHelperImpl.getHandler$1();
                handler$1.sendMessage(handler$1.obtainMessage(1005, z ? 1 : 0, 0));
                Trace.endSection();
            }

            public final void setKeyguardEnabled(boolean z) {
                trace("setKeyguardEnabled enabled" + z);
                KeyguardService.this.checkPermission();
                ((KeyguardRepositoryImpl) KeyguardService.this.mKeyguardEnabledInteractor.repository)._isKeyguardEnabled.updateState(null, Boolean.valueOf(z));
                KeyguardService.this.mKeyguardViewMediator.setKeyguardEnabled(z);
            }

            public final void setOccluded(boolean z, boolean z2) {
                trace("setOccluded isOccluded=" + z + " animate=" + z2);
                StringBuilder sb = new StringBuilder("setOccluded(");
                sb.append(z);
                sb.append(")");
                android.util.Log.d("KeyguardService", sb.toString());
                Trace.beginSection("KeyguardService.mBinder#setOccluded");
                KeyguardService.this.checkPermission();
                KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
                com.android.systemui.Flags.keyguardWmStateRefactor();
                KeyguardService.this.mKeyguardViewMediator.setOccluded(z, z2);
                Trace.endSection();
            }

            public final void setPendingIntentAfterUnlock(PendingIntent pendingIntent, Intent intent) {
                KeyguardService.this.checkPermission();
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardService.this.mKeyguardViewMediator.mHelper;
                keyguardViewMediatorHelperImpl.getClass();
                boolean booleanExtra = intent.getBooleanExtra("ignoreKeyguardState", false);
                ViewMediatorProvider viewMediatorProvider = keyguardViewMediatorHelperImpl.viewMediatorProvider;
                if (viewMediatorProvider == null) {
                    viewMediatorProvider = null;
                }
                boolean booleanValue = ((Boolean) viewMediatorProvider.isExternallyEnabled.invoke()).booleanValue();
                if (keyguardViewMediatorHelperImpl.isShowing$1() || !booleanValue || booleanExtra) {
                    Message obtainMessage = keyguardViewMediatorHelperImpl.getHandler$1().obtainMessage(1001);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("PI", pendingIntent);
                    bundle.putParcelable("FI", intent);
                    obtainMessage.setData(bundle);
                    keyguardViewMediatorHelperImpl.getHandler$1().sendMessage(obtainMessage);
                }
            }

            public final void setSwitchingUser(boolean z) {
                trace("setSwitchingUser switching=" + z);
                KeyguardService.this.checkPermission();
                KeyguardService.this.mKeyguardViewMediator.setSwitchingUser(z);
            }

            public final void showDismissibleKeyguard() {
                trace("showDismissibleKeyguard");
                KeyguardService.this.checkPermission();
                KeyguardService.this.mKeyguardViewMediator.showDismissibleKeyguard();
                int i = SceneContainerFlag.$r8$clinit;
                com.android.systemui.Flags.sceneContainer();
            }

            public final void startFingerprintAuthentication() {
                KeyguardService.this.checkPermission();
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardService.this.mKeyguardViewMediator.mHelper;
                keyguardViewMediatorHelperImpl.getClass();
                if (KeyguardViewMediatorHelperImplKt.IS_SAFE_MODE_ENABLED) {
                    return;
                }
                keyguardViewMediatorHelperImpl.updateMonitor.dispatchForceStartFingerprint();
            }

            public final void startKeyguardExitAnimation(long j, long j2) {
                StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m("startKeyguardExitAnimation startTime=", j, " fadeoutDuration=");
                m.append(j2);
                trace(m.toString());
                Trace.beginSection("KeyguardService.mBinder#startKeyguardExitAnimation");
                KeyguardService.this.checkPermission();
                KeyguardService.this.mKeyguardViewMediator.startKeyguardExitAnimation(j, j2);
                Trace.endSection();
            }

            public final void startedEarlyWakingUp(int i) {
                Trace.beginSection("KeyguardService.mBinder#startedEarlyWakingUp");
                KeyguardService.this.checkPermission();
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardService.this.mKeyguardViewMediator.mHelper;
                keyguardViewMediatorHelperImpl.getClass();
                if (!KeyguardViewMediatorHelperImplKt.IS_SAFE_MODE_ENABLED) {
                    KeyguardViewMediatorHelperImpl.logD("startedEarlyWakingUp");
                    Trace.beginSection("KeyguardViewMediator#startedEarlyWakingUp");
                    keyguardViewMediatorHelperImpl.enableLooperLogController(4, 3000L);
                    keyguardViewMediatorHelperImpl.updateMonitor.dispatchStartedEarlyWakingUp(i);
                    Trace.endSection();
                }
                Trace.endSection();
            }

            public final void verifyUnlock(IKeyguardExitCallback iKeyguardExitCallback) {
                trace("verifyUnlock");
                Trace.beginSection("KeyguardService.mBinder#verifyUnlock");
                KeyguardService.this.checkPermission();
                KeyguardService.this.mKeyguardViewMediator.verifyUnlock(iKeyguardExitCallback);
                Trace.endSection();
            }
        };
        this.mKeyguardViewMediator = keyguardViewMediator;
        this.mKeyguardLifecyclesDispatcher = keyguardLifecyclesDispatcher;
        this.mScreenOnCoordinator = screenOnCoordinator;
        this.mShellTransitions = shellTransitions;
        this.mDisplayTracker = displayTracker;
        this.mPowerInteractor = powerInteractor;
        this.mKeyguardInteractor = keyguardInteractor;
        this.mMainExecutor = executor;
        KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
        com.android.systemui.Flags.keyguardWmStateRefactor();
        this.mKeyguardEnabledInteractor = keyguardEnabledInteractor;
    }

    public final void checkPermission() {
        if (Binder.getCallingUid() == 1000 || getBaseContext().checkCallingOrSelfPermission("android.permission.CONTROL_KEYGUARD") == 0) {
            return;
        }
        android.util.Log.w("KeyguardService", "Caller needs permission 'android.permission.CONTROL_KEYGUARD' to call " + Debug.getCaller());
        throw new SecurityException("Access denied to process: " + Binder.getCallingPid() + ", must have permission android.permission.CONTROL_KEYGUARD");
    }

    @Override // android.app.Service
    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        String sb;
        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mKeyguardViewMediator.mHelper;
        keyguardViewMediatorHelperImpl.getClass();
        KeyguardUnlockInfo keyguardUnlockInfo = KeyguardUnlockInfo.INSTANCE;
        printWriter.println("KeyguardUnlockInfo");
        Queue queue = KeyguardUnlockInfo.history;
        synchronized (queue) {
            try {
                Iterator it = queue.iterator();
                while (it.hasNext()) {
                    printWriter.println("  " + ((String) it.next()));
                }
                Unit unit = Unit.INSTANCE;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK || LsRune.KEYGUARD_SUB_DISPLAY_COVER) {
            KeyguardFoldControllerImpl keyguardFoldControllerImpl = keyguardViewMediatorHelperImpl.foldControllerImpl;
            UdfpsKeyguardViewControllerLegacy$$ExternalSyntheticOutline0.m(printWriter, "First shown: ", keyguardFoldControllerImpl.initShowTime > 0 ? LogUtil.makeTimeStr(keyguardFoldControllerImpl.initShowTime) : "0");
        }
        int userId = ((UserTrackerImpl) keyguardViewMediatorHelperImpl.userTracker).getUserId();
        Iterator it2 = UserManager.get(keyguardViewMediatorHelperImpl.context).getUsers().iterator();
        while (true) {
            if (!it2.hasNext()) {
                break;
            }
            int identifier = ((UserInfo) it2.next()).getUserHandle().getIdentifier();
            printWriter.println(LogUtil.getMsg(ArcMode$$ExternalSyntheticOutline0.m("User ", identifier, userId == identifier ? '*' : ' '), new Object[0]));
            ViewMediatorProvider viewMediatorProvider = keyguardViewMediatorHelperImpl.viewMediatorProvider;
            DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("  lockTimeout: ", (viewMediatorProvider != null ? viewMediatorProvider : null).getLockTimeout.invoke(Integer.valueOf(identifier)), printWriter);
            ActiveUnlockConfig$$ExternalSyntheticOutline0.m("  lockInstantlyWithPowerKey: ", keyguardViewMediatorHelperImpl.lockPatternUtils.getPowerButtonInstantlyLocks(identifier) || !keyguardViewMediatorHelperImpl.lockPatternUtils.isSecure(identifier), printWriter);
            if (keyguardViewMediatorHelperImpl.lockPatternUtils.isSecure(identifier)) {
                StringBuilder sb2 = new StringBuilder();
                int keyguardStoredPasswordQuality = keyguardViewMediatorHelperImpl.lockPatternUtils.getKeyguardStoredPasswordQuality(identifier);
                if (keyguardStoredPasswordQuality == 65536) {
                    sb2.append("pattern");
                } else if (keyguardStoredPasswordQuality == 131072 || keyguardStoredPasswordQuality == 196608) {
                    sb2.append("pin");
                } else if (keyguardStoredPasswordQuality == 262144 || keyguardStoredPasswordQuality == 327680 || keyguardStoredPasswordQuality == 393216 || keyguardStoredPasswordQuality == 524288) {
                    sb2.append(HostAuth.PASSWORD);
                } else {
                    int i = StringCompanionObject.$r8$clinit;
                    sb2.append(String.format("0x%x", Arrays.copyOf(new Object[]{Integer.valueOf(keyguardStoredPasswordQuality)}, 1)));
                }
                int[] iArr = {1, 256};
                String[] strArr2 = {"fingerprints", "face"};
                for (int i2 = 0; i2 < 2; i2++) {
                    if (keyguardViewMediatorHelperImpl.lockPatternUtils.getBiometricState(iArr[i2], identifier) != 0) {
                        sb2.append(", ");
                        sb2.append(strArr2[i2]);
                    }
                }
                sb = sb2.toString();
            } else {
                sb = keyguardViewMediatorHelperImpl.lockPatternUtils.isLockScreenDisabled(identifier) ? SignalSeverity.NONE : "swipe";
            }
            String msg = LogUtil.getMsg("  lockTypeSummary=".concat(sb), new Object[0]);
            if (keyguardViewMediatorHelperImpl.updateMonitor.getUserCanSkipBouncer(identifier)) {
                msg = ((Object) msg) + " / canSkipBouncer=true";
            }
            printWriter.println(msg);
        }
        ViewMediatorProvider viewMediatorProvider2 = keyguardViewMediatorHelperImpl.viewMediatorProvider;
        DeviceEntryFaceAuthRepositoryImpl$$ExternalSyntheticOutline0.m("StateCallback count=", (viewMediatorProvider2 != null ? viewMediatorProvider2 : null).getStateCallbackCount.invoke(), printWriter);
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    @Override // android.app.Service
    public final void onCreate() {
        ((SystemUIApplication) getApplication()).startSystemUserServicesIfNeeded();
        if (this.mShellTransitions == null || !Transitions.ENABLE_SHELL_TRANSITIONS) {
            RemoteAnimationDefinition remoteAnimationDefinition = new RemoteAnimationDefinition();
            RemoteAnimationAdapter remoteAnimationAdapter = new RemoteAnimationAdapter(this.mKeyguardViewMediator.getExitAnimationRunner(), 0L, 0L);
            remoteAnimationDefinition.addRemoteAnimation(20, remoteAnimationAdapter);
            remoteAnimationDefinition.addRemoteAnimation(21, remoteAnimationAdapter);
            remoteAnimationDefinition.addRemoteAnimation(22, new RemoteAnimationAdapter(this.mKeyguardViewMediator.getOccludeAnimationRunner(), 0L, 0L));
            remoteAnimationDefinition.addRemoteAnimation(33, new RemoteAnimationAdapter(this.mKeyguardViewMediator.getOccludeByDreamAnimationRunner(), 0L, 0L));
            remoteAnimationDefinition.addRemoteAnimation(23, new RemoteAnimationAdapter(this.mKeyguardViewMediator.getUnoccludeAnimationRunner(), 0L, 0L));
            ActivityTaskManager activityTaskManager = ActivityTaskManager.getInstance();
            this.mDisplayTracker.getClass();
            activityTaskManager.registerRemoteAnimationsForDisplay(0, remoteAnimationDefinition);
        }
    }
}
