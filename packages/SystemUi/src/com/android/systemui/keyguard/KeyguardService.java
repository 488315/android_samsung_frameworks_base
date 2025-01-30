package com.android.systemui.keyguard;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
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
import android.util.ArrayMap;
import android.util.RotationUtils;
import android.view.IRemoteAnimationFinishedCallback;
import android.view.IRemoteAnimationRunner;
import android.view.RemoteAnimationAdapter;
import android.view.RemoteAnimationDefinition;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;
import android.view.WindowManagerPolicyConstants;
import android.window.IRemoteTransition;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.TransitionInfo;
import android.window.WindowContainerTransaction;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.view.OneShotPreDrawListener;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.policy.IKeyguardDrawnCallback;
import com.android.internal.policy.IKeyguardExitCallback;
import com.android.internal.policy.IKeyguardService;
import com.android.internal.policy.IKeyguardStateCallback;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.ActiveUnlockConfig$$ExternalSyntheticOutline0;
import com.android.keyguard.mediator.ScreenOnCoordinator;
import com.android.wm.shell.transition.ShellTransitions;
import com.android.wm.shell.transition.Transitions;
import com.android.wm.shell.util.CounterRotator;
import com.android.wm.shell.util.TransitionUtil;
import com.android.systemui.BootAnimationFinishedCacheImpl;
import com.android.systemui.LsRune;
import com.android.systemui.SystemUIApplication;
import com.android.systemui.coverlauncher.utils.CoverLauncherWidgetUtils;
import com.android.systemui.coverlauncher.widget.CoverLauncherWidgetViewController;
import com.android.systemui.coverlauncher.widget.CoverLauncherWidgetViewController.RunnableC12122;
import com.android.systemui.log.LogLevel;
import com.android.systemui.settings.DisplayTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.unfold.FoldAodAnimationController;
import com.android.systemui.unfold.UnfoldLightRevealOverlayAnimation;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.concurrency.PendingTasksContainer;
import com.samsung.android.knox.accounts.HostAuth;
import com.sec.ims.configuration.DATA;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class KeyguardService extends Service {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final C14702 mBinder = new IKeyguardService.Stub() { // from class: com.android.systemui.keyguard.KeyguardService.2
        public static void trace(String str) {
            Log.m141i("KeyguardService", str);
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
            KeyguardService.this.mKeyguardViewMediator.dismissKeyguardToLaunch();
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
            KeyguardService.this.mKeyguardViewMediator.onDreamingStarted();
        }

        public final void onDreamingStopped() {
            trace("onDreamingStopped");
            KeyguardService.this.checkPermission();
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
            trace("onFinishedGoingToSleep pmSleepReason=" + i + " cameraGestureTriggered=" + z);
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.onFinishedGoingToSleep(WindowManagerPolicyConstants.translateSleepReasonToOffReason(i), z);
            KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(7);
        }

        public final void onFinishedWakingUp() {
            trace("onFinishedWakingUp");
            Trace.beginSection("KeyguardService.mBinder#onFinishedWakingUp");
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(5);
            KeyguardService.this.mKeyguardViewMediator.mHelper.getClass();
            if (!KeyguardViewMediatorHelperImplKt.IS_SAFE_MODE_ENABLED) {
                String str = LsRune.VALUE_CONFIG_CARRIER_TEXT_POLICY;
            }
            Trace.endSection();
        }

        public final void onScreenTurnedOff() {
            trace("onScreenTurnedOff");
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.onScreenTurnedOff();
            KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(3);
            if (SafeUIState.isSysUiSafeModeEnabled()) {
                PendingTasksContainer pendingTasksContainer = KeyguardService.this.mScreenOnCoordinator.pendingTasks;
                pendingTasksContainer.getClass();
                pendingTasksContainer.completionCallback = new AtomicReference();
                pendingTasksContainer.pendingTasksCount = new AtomicInteger(0);
            }
        }

        public final void onScreenTurnedOn() {
            final FoldAodAnimationController foldAodAnimationController;
            trace("onScreenTurnedOn");
            Trace.beginSection("KeyguardService.mBinder#onScreenTurnedOn");
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(1);
            if (SafeUIState.isSysUiSafeModeEnabled() && (foldAodAnimationController = KeyguardService.this.mScreenOnCoordinator.foldAodAnimationController) != null) {
                ((ExecutorImpl) foldAodAnimationController.mainExecutor).execute(new Runnable() { // from class: com.android.systemui.unfold.FoldAodAnimationController$onScreenTurnedOn$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        FoldAodAnimationController foldAodAnimationController2 = FoldAodAnimationController.this;
                        if (foldAodAnimationController2.shouldPlayAnimation) {
                            Runnable runnable = foldAodAnimationController2.cancelAnimation;
                            if (runnable != null) {
                                runnable.run();
                            }
                            FoldAodAnimationController foldAodAnimationController3 = FoldAodAnimationController.this;
                            foldAodAnimationController3.cancelAnimation = foldAodAnimationController3.mainExecutor.executeDelayed(0L, foldAodAnimationController3.startAnimationRunnable);
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
            KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.util.concurrency.PendingTasksContainer$registerTask$1] */
        public final void onScreenTurningOn(final IKeyguardDrawnCallback iKeyguardDrawnCallback) {
            Runnable runnable;
            trace("onScreenTurningOn");
            Trace.beginSection("KeyguardService.mBinder#onScreenTurningOn");
            KeyguardService.this.checkPermission();
            boolean z = false;
            if (SafeUIState.isSysUiSafeModeEnabled()) {
                KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(iKeyguardDrawnCallback);
                final int identityHashCode = System.identityHashCode(iKeyguardDrawnCallback);
                Trace.beginAsyncSection("Waiting for KeyguardDrawnCallback#onDrawn", identityHashCode);
                final ScreenOnCoordinator screenOnCoordinator = KeyguardService.this.mScreenOnCoordinator;
                final Runnable runnable2 = new Runnable(this) { // from class: com.android.systemui.keyguard.KeyguardService.2.1
                    public boolean mInvoked;

                    @Override // java.lang.Runnable
                    public final void run() {
                        if (iKeyguardDrawnCallback == null) {
                            return;
                        }
                        if (this.mInvoked) {
                            Log.m142w("KeyguardService", "KeyguardDrawnCallback#onDrawn() invoked > 1 times");
                            return;
                        }
                        this.mInvoked = true;
                        try {
                            Trace.endAsyncSection("Waiting for KeyguardDrawnCallback#onDrawn", identityHashCode);
                            iKeyguardDrawnCallback.onDrawn();
                        } catch (RemoteException e) {
                            android.util.Log.w("KeyguardService", "Exception calling onDrawn():", e);
                            KeyguardDumpLog.log("KeyguardService", LogLevel.WARNING, "Exception calling onDrawn():", null);
                        }
                    }
                };
                screenOnCoordinator.getClass();
                Trace.beginSection("ScreenOnCoordinator#onScreenTurningOn");
                PendingTasksContainer pendingTasksContainer = screenOnCoordinator.pendingTasks;
                pendingTasksContainer.getClass();
                pendingTasksContainer.completionCallback = new AtomicReference();
                pendingTasksContainer.pendingTasksCount = new AtomicInteger(0);
                UnfoldLightRevealOverlayAnimation unfoldLightRevealOverlayAnimation = screenOnCoordinator.unfoldLightRevealAnimation;
                if (unfoldLightRevealOverlayAnimation != 0) {
                    final PendingTasksContainer pendingTasksContainer2 = screenOnCoordinator.pendingTasks;
                    final String str = "unfold-reveal";
                    pendingTasksContainer2.pendingTasksCount.incrementAndGet();
                    unfoldLightRevealOverlayAnimation.onScreenTurningOn(new Runnable(str) { // from class: com.android.systemui.util.concurrency.PendingTasksContainer$registerTask$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Runnable runnable3;
                            if (PendingTasksContainer.this.pendingTasksCount.decrementAndGet() != 0 || (runnable3 = (Runnable) PendingTasksContainer.this.completionCallback.getAndSet(null)) == null) {
                                return;
                            }
                            runnable3.run();
                        }
                    });
                    throw null;
                }
                final FoldAodAnimationController foldAodAnimationController = screenOnCoordinator.foldAodAnimationController;
                if (foldAodAnimationController != null) {
                    final PendingTasksContainer pendingTasksContainer3 = screenOnCoordinator.pendingTasks;
                    final String str2 = "fold-to-aod";
                    pendingTasksContainer3.pendingTasksCount.incrementAndGet();
                    final Runnable runnable3 = new Runnable(str2) { // from class: com.android.systemui.util.concurrency.PendingTasksContainer$registerTask$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            Runnable runnable32;
                            if (PendingTasksContainer.this.pendingTasksCount.decrementAndGet() != 0 || (runnable32 = (Runnable) PendingTasksContainer.this.completionCallback.getAndSet(null)) == null) {
                                return;
                            }
                            runnable32.run();
                        }
                    };
                    ((ExecutorImpl) foldAodAnimationController.mainExecutor).execute(new Runnable() { // from class: com.android.systemui.unfold.FoldAodAnimationController$onScreenTurningOn$1
                        @Override // java.lang.Runnable
                        public final void run() {
                            FoldAodAnimationController foldAodAnimationController2 = FoldAodAnimationController.this;
                            if (foldAodAnimationController2.shouldPlayAnimation) {
                                if (foldAodAnimationController2.isScrimOpaque) {
                                    runnable3.run();
                                } else {
                                    foldAodAnimationController2.pendingScrimReadyCallback = runnable3;
                                }
                            } else if (foldAodAnimationController2.isFolded && !foldAodAnimationController2.isFoldHandled && foldAodAnimationController2.alwaysOnEnabled && foldAodAnimationController2.isDozing) {
                                foldAodAnimationController2.setAnimationState(true);
                                FoldAodAnimationController.this.getShadeFoldAnimator().prepareFoldToAodAnimation();
                                OneShotPreDrawListener.add(NotificationPanelViewController.this.mView, runnable3);
                            } else {
                                runnable3.run();
                            }
                            FoldAodAnimationController foldAodAnimationController3 = FoldAodAnimationController.this;
                            if (foldAodAnimationController3.isFolded) {
                                foldAodAnimationController3.isFoldHandled = true;
                            }
                        }
                    });
                }
                PendingTasksContainer pendingTasksContainer4 = screenOnCoordinator.pendingTasks;
                pendingTasksContainer4.completionCallback.set(new Runnable() { // from class: com.android.keyguard.mediator.ScreenOnCoordinator$onScreenTurningOn$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        Handler handler = ScreenOnCoordinator.this.mainHandler;
                        final Runnable runnable4 = runnable2;
                        handler.post(new Runnable() { // from class: com.android.keyguard.mediator.ScreenOnCoordinator$onScreenTurningOn$1.1
                            @Override // java.lang.Runnable
                            public final void run() {
                                runnable4.run();
                            }
                        });
                    }
                });
                if (pendingTasksContainer4.pendingTasksCount.get() == 0 && (runnable = (Runnable) pendingTasksContainer4.completionCallback.getAndSet(null)) != null) {
                    runnable.run();
                }
                Trace.endSection();
            } else {
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardService.this.mKeyguardViewMediator.mHelper;
                keyguardViewMediatorHelperImpl.enableLooperLogController(3, 3000L);
                keyguardViewMediatorHelperImpl.screenTuringOnTime = SystemClock.elapsedRealtime();
                if (keyguardViewMediatorHelperImpl.isFastWakeAndUnlockMode() && !keyguardViewMediatorHelperImpl.fastUnlockController.needsBlankScreen) {
                    synchronized (keyguardViewMediatorHelperImpl.getLock()) {
                        keyguardViewMediatorHelperImpl.drawnCallback = iKeyguardDrawnCallback;
                        Unit unit = Unit.INSTANCE;
                    }
                    iKeyguardDrawnCallback = null;
                }
                if (LsRune.KEYGUARD_PERFORMANCE_SCREEN_ON && ((Boolean) keyguardViewMediatorHelperImpl.getViewMediatorProvider().isBootCompleted.invoke()).booleanValue() && iKeyguardDrawnCallback != null) {
                    keyguardViewMediatorHelperImpl.notifyDrawn(iKeyguardDrawnCallback);
                    iKeyguardDrawnCallback = null;
                }
                Message obtainMessage = keyguardViewMediatorHelperImpl.getHandler().obtainMessage(1002, iKeyguardDrawnCallback);
                boolean z2 = LsRune.KEYGUARD_SUB_DISPLAY_LOCK;
                KeyguardFoldControllerImpl keyguardFoldControllerImpl = keyguardViewMediatorHelperImpl.foldControllerImpl;
                if ((z2 && keyguardFoldControllerImpl.isUnlockOnFoldOpened()) || ((z2 || LsRune.KEYGUARD_SUB_DISPLAY_COVER) && keyguardViewMediatorHelperImpl.getHandler().hasMessages(1003) && keyguardFoldControllerImpl.isFoldOpened())) {
                    z = true;
                }
                if (z) {
                    keyguardViewMediatorHelperImpl.getHandler().sendMessageAtFrontOfQueue(obtainMessage);
                } else {
                    keyguardViewMediatorHelperImpl.getHandler().sendMessage(obtainMessage);
                }
                KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch((Object) null);
            }
            Trace.endSection();
        }

        public final void onShortPowerPressedGoHome() {
            trace("onShortPowerPressedGoHome");
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.onShortPowerPressedGoHome();
        }

        public final void onStartedGoingToSleep(int i) {
            trace("onStartedGoingToSleep pmSleepReason=" + i);
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.onStartedGoingToSleep(WindowManagerPolicyConstants.translateSleepReasonToOffReason(i));
            KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(6, i);
        }

        public final void onStartedWakingUp(int i, boolean z) {
            trace("onStartedWakingUp pmWakeReason=" + i + " cameraGestureTriggered=" + z);
            Trace.beginSection("KeyguardService.mBinder#onStartedWakingUp");
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.onStartedWakingUp(i, z);
            KeyguardService.this.mKeyguardLifecyclesDispatcher.dispatch(4, i);
            Trace.endSection();
        }

        public final void onSystemKeyPressed(int i) {
            trace("onSystemKeyPressed keycode=" + i);
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.onSystemKeyPressed();
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
            Log.m141i("KeyguardViewMediator", "setCoverOccluded " + z);
            keyguardViewMediatorHelperImpl.removeMessage(1006);
            Handler handler = keyguardViewMediatorHelperImpl.getHandler();
            handler.sendMessage(handler.obtainMessage(1006, z ? 1 : 0, 0));
            Trace.endSection();
        }

        public final void setCurrentUser(int i) {
            trace("setCurrentUser userId=" + i);
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.setCurrentUser(i);
        }

        public final void setDexOccluded(boolean z) {
            Trace.beginSection("KeyguardService.mBinder#setDexOccluded");
            KeyguardService.this.checkPermission();
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardService.this.mKeyguardViewMediator.mHelper;
            keyguardViewMediatorHelperImpl.getClass();
            Log.m141i("KeyguardViewMediator", "setDexOccluded " + z);
            keyguardViewMediatorHelperImpl.removeMessage(1005);
            Handler handler = keyguardViewMediatorHelperImpl.getHandler();
            handler.sendMessage(handler.obtainMessage(1005, z ? 1 : 0, 0));
            Trace.endSection();
        }

        public final void setKeyguardEnabled(boolean z) {
            trace("setKeyguardEnabled enabled" + z);
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.setKeyguardEnabled(z);
        }

        public final void setOccluded(boolean z, boolean z2) {
            trace("setOccluded isOccluded=" + z + " animate=" + z2);
            StringBuilder sb = new StringBuilder("setOccluded(");
            sb.append(z);
            sb.append(")");
            Log.m138d("KeyguardService", sb.toString());
            Trace.beginSection("KeyguardService.mBinder#setOccluded");
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.setOccluded(z, z2);
            Trace.endSection();
        }

        public final void setPendingIntentAfterUnlock(PendingIntent pendingIntent, Intent intent) {
            KeyguardService.this.checkPermission();
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = KeyguardService.this.mKeyguardViewMediator.mHelper;
            keyguardViewMediatorHelperImpl.getClass();
            boolean booleanExtra = intent.getBooleanExtra("ignoreKeyguardState", false);
            boolean booleanValue = ((Boolean) keyguardViewMediatorHelperImpl.getViewMediatorProvider().isExternallyEnabled.invoke()).booleanValue();
            if (keyguardViewMediatorHelperImpl.isShowing() || !booleanValue || booleanExtra) {
                Message obtainMessage = keyguardViewMediatorHelperImpl.getHandler().obtainMessage(1001);
                Bundle bundle = new Bundle();
                bundle.putParcelable("PI", pendingIntent);
                bundle.putParcelable("FI", intent);
                obtainMessage.setData(bundle);
                keyguardViewMediatorHelperImpl.getHandler().sendMessage(obtainMessage);
            }
        }

        public final void setSwitchingUser(boolean z) {
            trace("setSwitchingUser switching=" + z);
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.setSwitchingUser(z);
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
            StringBuilder m17m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("startKeyguardExitAnimation startTime=", j, " fadeoutDuration=");
            m17m.append(j2);
            trace(m17m.toString());
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

        public final void updateCoverLauncherAppWidget() {
            KeyguardService.this.checkPermission();
            CoverLauncherWidgetViewController coverLauncherWidgetViewController = CoverLauncherWidgetViewController.getInstance(KeyguardService.this.mKeyguardViewMediator.mHelper.context);
            Context context = coverLauncherWidgetViewController.mContext;
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            for (Class cls : CoverLauncherWidgetUtils.sWidgetClassArray) {
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, (Class<?>) cls));
                if (appWidgetIds.length > 0) {
                    new Thread(coverLauncherWidgetViewController.new RunnableC12122(appWidgetIds, appWidgetManager)).start();
                    Intent intent = new Intent(context, (Class<?>) cls);
                    intent.setAction("com.samsung.settings.ACTION_UPDATE_WIDGET");
                    intent.putExtra("appWidgetIds", appWidgetIds);
                    context.sendBroadcast(intent);
                }
            }
        }

        public final void verifyUnlock(IKeyguardExitCallback iKeyguardExitCallback) {
            trace("verifyUnlock");
            Trace.beginSection("KeyguardService.mBinder#verifyUnlock");
            KeyguardService.this.checkPermission();
            KeyguardService.this.mKeyguardViewMediator.verifyUnlock(iKeyguardExitCallback);
            Trace.endSection();
        }
    };
    public final DisplayTracker mDisplayTracker;
    public final KeyguardLifecyclesDispatcher mKeyguardLifecyclesDispatcher;
    public final KeyguardViewMediator mKeyguardViewMediator;
    public final ScreenOnCoordinator mScreenOnCoordinator;
    public final ShellTransitions mShellTransitions;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.keyguard.KeyguardService$1 */
    public final class C14691 extends IRemoteTransition.Stub {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final /* synthetic */ KeyguardViewMediator val$keyguardViewMediator;
        public final /* synthetic */ boolean val$lockscreenLiveWallpaperEnabled;
        public final /* synthetic */ IRemoteAnimationRunner val$runner;
        public final ArrayMap mLeashMap = new ArrayMap();
        public final CounterRotator mCounterRotator = new CounterRotator();
        public final Map mFinishCallbacks = new WeakHashMap();

        public C14691(KeyguardViewMediator keyguardViewMediator, IRemoteAnimationRunner iRemoteAnimationRunner, boolean z) {
            this.val$keyguardViewMediator = keyguardViewMediator;
            this.val$runner = iRemoteAnimationRunner;
            this.val$lockscreenLiveWallpaperEnabled = z;
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
            RemoteAnimationTarget[] m1545$$Nest$smwrap;
            RemoteAnimationTarget[] m1545$$Nest$smwrap2;
            boolean z;
            ActivityManager.RunningTaskInfo runningTaskInfo;
            String str = "Starts IRemoteAnimationRunner: info=" + transitionInfo;
            android.util.Slog.d("KeyguardService", str);
            KeyguardDumpLog.log("KeyguardService", LogLevel.DEBUG, str, null);
            int i = 0;
            RemoteAnimationTarget[] remoteAnimationTargetArr = new RemoteAnimationTarget[0];
            synchronized (this.mLeashMap) {
                m1545$$Nest$smwrap = KeyguardService.m1545$$Nest$smwrap(transitionInfo, false, transaction, this.mLeashMap, this.mCounterRotator);
                m1545$$Nest$smwrap2 = KeyguardService.m1545$$Nest$smwrap(transitionInfo, true, transaction, this.mLeashMap, this.mCounterRotator);
                ((WeakHashMap) this.mFinishCallbacks).put(iBinder, iRemoteTransitionFinishedCallback);
            }
            for (TransitionInfo.Change change : transitionInfo.getChanges()) {
                if (TransitionInfo.isIndependent(change, transitionInfo)) {
                    transaction.setAlpha(change.getLeash(), 1.0f);
                }
            }
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.val$keyguardViewMediator.mHelper;
            if (Intrinsics.areEqual(this.val$runner, keyguardViewMediatorHelperImpl.exitAnimationRunner)) {
                KeyguardFastBioUnlockController keyguardFastBioUnlockController = keyguardViewMediatorHelperImpl.fastUnlockController;
                if (keyguardFastBioUnlockController.isFastWakeAndUnlockMode()) {
                    if (keyguardFastBioUnlockController.isInvisibleAfterGoingAwayTransStarted) {
                        ((KeyguardSurfaceControllerImpl) ((KeyguardSurfaceController) keyguardViewMediatorHelperImpl.surfaceControllerLazy.get())).setKeyguardSurfaceVisible(transaction);
                        keyguardViewMediatorHelperImpl.wallpaperController.hideLockOnlyLiveWallpaperImmediately();
                    }
                    z = true;
                } else {
                    z = false;
                }
                if (z) {
                    ArrayList arrayList = new ArrayList();
                    for (RemoteAnimationTarget remoteAnimationTarget : m1545$$Nest$smwrap) {
                        if (remoteAnimationTarget.mode == 0) {
                            arrayList.add(remoteAnimationTarget);
                        }
                    }
                    ArrayList arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList, 10));
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        arrayList2.add(((RemoteAnimationTarget) it.next()).leash);
                    }
                    Iterator it2 = arrayList2.iterator();
                    while (it2.hasNext()) {
                        transaction.setAlpha((SurfaceControl) it2.next(), 1.0f);
                    }
                }
            } else {
                z = false;
            }
            if (!z) {
                for (RemoteAnimationTarget remoteAnimationTarget2 : m1545$$Nest$smwrap) {
                    if (remoteAnimationTarget2.mode == 0) {
                        transaction.setAlpha(remoteAnimationTarget2.leash, 0.0f);
                    }
                }
            }
            if (this.val$lockscreenLiveWallpaperEnabled) {
                for (RemoteAnimationTarget remoteAnimationTarget3 : m1545$$Nest$smwrap2) {
                    if (remoteAnimationTarget3.mode == 0) {
                        transaction.setAlpha(remoteAnimationTarget3.leash, 0.0f);
                    }
                }
            }
            transaction.apply();
            IRemoteAnimationRunner iRemoteAnimationRunner = this.val$runner;
            int type = transitionInfo.getType();
            int flags = transitionInfo.getFlags();
            if (type == 7 || (flags & 256) != 0) {
                i = m1545$$Nest$smwrap.length == 0 ? 21 : 20;
            } else if (type == 8) {
                if (m1545$$Nest$smwrap.length > 0 && (runningTaskInfo = m1545$$Nest$smwrap[0].taskInfo) != null && runningTaskInfo.topActivityType == 5) {
                    i = 1;
                }
                i = i != 0 ? 33 : 22;
            } else if (type == 9) {
                i = 23;
            } else {
                String str2 = "Unexpected transit type: " + type;
                android.util.Slog.d("KeyguardService", str2);
                KeyguardDumpLog.log("KeyguardService", LogLevel.DEBUG, str2, null);
            }
            iRemoteAnimationRunner.onAnimationStart(i, m1545$$Nest$smwrap, m1545$$Nest$smwrap2, remoteAnimationTargetArr, new IRemoteAnimationFinishedCallback.Stub() { // from class: com.android.systemui.keyguard.KeyguardService.1.1
                public final void onAnimationFinished() {
                    android.util.Slog.d("KeyguardService", "Finish IRemoteAnimationRunner.");
                    KeyguardDumpLog.log("KeyguardService", LogLevel.DEBUG, "Finish IRemoteAnimationRunner.", null);
                    C14691 c14691 = C14691.this;
                    IBinder iBinder2 = iBinder;
                    int i2 = C14691.$r8$clinit;
                    c14691.finish(iBinder2);
                }
            });
        }
    }

    /* renamed from: -$$Nest$smwrap, reason: not valid java name */
    public static RemoteAnimationTarget[] m1545$$Nest$smwrap(TransitionInfo transitionInfo, boolean z, SurfaceControl.Transaction transaction, ArrayMap arrayMap, CounterRotator counterRotator) {
        int deltaRotation;
        TransitionInfo.Change change;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < transitionInfo.getChanges().size(); i++) {
            boolean z2 = (((TransitionInfo.Change) transitionInfo.getChanges().get(i)).getFlags() & 2) != 0;
            if (z == z2) {
                TransitionInfo.Change change2 = (TransitionInfo.Change) transitionInfo.getChanges().get(i);
                int i2 = change2.getTaskInfo() != null ? change2.getTaskInfo().taskId : -1;
                if ((i2 == -1 || change2.getParent() == null || (change = transitionInfo.getChange(change2.getParent())) == null || change.getTaskInfo() == null) && (i2 >= 0 || z)) {
                    int m136m = KeyguardService$$ExternalSyntheticOutline0.m136m(transitionInfo, i);
                    boolean z3 = (change2.getFlags() & 1) != 0;
                    SurfaceControl createLeash = TransitionUtil.createLeash(transitionInfo, change2, m136m, transaction);
                    if (arrayMap != null) {
                        arrayMap.put(change2.getLeash(), createLeash);
                    }
                    RemoteAnimationTarget newTarget = TransitionUtil.newTarget(change2, m136m, createLeash, z3);
                    if (z2 && (deltaRotation = RotationUtils.deltaRotation(change2.getStartRotation(), change2.getEndRotation())) != 0 && change2.getParent() != null && change2.getMode() == 4) {
                        TransitionInfo.Change change3 = transitionInfo.getChange(change2.getParent());
                        if (change3 != null) {
                            counterRotator.setup(change3.getEndAbsBounds().width(), change3.getEndAbsBounds().height(), deltaRotation, transaction, change3.getLeash());
                        }
                        SurfaceControl surfaceControl = counterRotator.mSurface;
                        if (surfaceControl != null) {
                            transaction.setLayer(surfaceControl, -1);
                            SurfaceControl surfaceControl2 = (SurfaceControl) arrayMap.get(change2.getLeash());
                            SurfaceControl surfaceControl3 = counterRotator.mSurface;
                            if (surfaceControl3 != null) {
                                transaction.reparent(surfaceControl2, surfaceControl3);
                            }
                        }
                    }
                    arrayList.add(newTarget);
                }
            }
        }
        return (RemoteAnimationTarget[]) arrayList.toArray(new RemoteAnimationTarget[arrayList.size()]);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.systemui.keyguard.KeyguardService$2] */
    public KeyguardService(KeyguardViewMediator keyguardViewMediator, KeyguardLifecyclesDispatcher keyguardLifecyclesDispatcher, ScreenOnCoordinator screenOnCoordinator, ShellTransitions shellTransitions, DisplayTracker displayTracker) {
        this.mKeyguardViewMediator = keyguardViewMediator;
        this.mKeyguardLifecyclesDispatcher = keyguardLifecyclesDispatcher;
        this.mScreenOnCoordinator = screenOnCoordinator;
        this.mShellTransitions = shellTransitions;
        this.mDisplayTracker = displayTracker;
    }

    public final void checkPermission() {
        if (Binder.getCallingUid() == 1000 || getBaseContext().checkCallingOrSelfPermission("android.permission.CONTROL_KEYGUARD") == 0) {
            return;
        }
        Log.m142w("KeyguardService", "Caller needs permission 'android.permission.CONTROL_KEYGUARD' to call " + Debug.getCaller());
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
            Iterator it = queue.iterator();
            while (it.hasNext()) {
                printWriter.println("  " + ((String) it.next()));
            }
            Unit unit = Unit.INSTANCE;
        }
        if (LsRune.KEYGUARD_SUB_DISPLAY_LOCK || LsRune.KEYGUARD_SUB_DISPLAY_COVER) {
            KeyguardFoldControllerImpl keyguardFoldControllerImpl = keyguardViewMediatorHelperImpl.foldControllerImpl;
            printWriter.println("First shown: ".concat(keyguardFoldControllerImpl.initShowTime > 0 ? LogUtil.makeTimeStr(keyguardFoldControllerImpl.initShowTime) : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN));
        }
        int userId = ((UserTrackerImpl) keyguardViewMediatorHelperImpl.userTracker).getUserId();
        Iterator it2 = UserManager.get(keyguardViewMediatorHelperImpl.context).getUsers().iterator();
        while (it2.hasNext()) {
            int identifier = ((UserInfo) it2.next()).getUserHandle().getIdentifier();
            printWriter.println(LogUtil.getMsg("User " + identifier + (userId == identifier ? '*' : ' '), new Object[0]));
            printWriter.println("  lockTimeout: " + keyguardViewMediatorHelperImpl.getViewMediatorProvider().getLockTimeout.invoke(Integer.valueOf(identifier)));
            ActiveUnlockConfig$$ExternalSyntheticOutline0.m53m("  lockInstantlyWithPowerKey: ", keyguardViewMediatorHelperImpl.lockPatternUtils.getPowerButtonInstantlyLocks(identifier) || !keyguardViewMediatorHelperImpl.lockPatternUtils.isSecure(identifier), printWriter);
            LockPatternUtils lockPatternUtils = keyguardViewMediatorHelperImpl.lockPatternUtils;
            if (lockPatternUtils.isSecure(identifier)) {
                StringBuilder sb2 = new StringBuilder();
                int keyguardStoredPasswordQuality = lockPatternUtils.getKeyguardStoredPasswordQuality(identifier);
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
                    if (lockPatternUtils.getBiometricState(iArr[i2], identifier) != 0) {
                        sb2.append(", ");
                        sb2.append(strArr2[i2]);
                    }
                }
                sb = sb2.toString();
            } else {
                sb = lockPatternUtils.isLockScreenDisabled(identifier) ? "none" : "swipe";
            }
            String msg = LogUtil.getMsg("  lockTypeSummary=".concat(sb), new Object[0]);
            if (keyguardViewMediatorHelperImpl.updateMonitor.getUserCanSkipBouncer(identifier)) {
                msg = ((Object) msg) + " / canSkipBouncer=true";
            }
            printWriter.println(msg);
        }
        printWriter.println("StateCallback count=" + keyguardViewMediatorHelperImpl.getViewMediatorProvider().getStateCallbackCount.invoke());
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    @Override // android.app.Service
    public final void onCreate() {
        ((SystemUIApplication) getApplication()).startServicesIfNeeded();
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
