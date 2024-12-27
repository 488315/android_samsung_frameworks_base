package com.android.systemui.statusbar.phone;

import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.Flags;
import com.android.systemui.Rune;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.doze.AODOverlayContainer;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.DozeLog;
import com.android.systemui.doze.DozeReceiver;
import com.android.systemui.doze.DozeUi;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.DozeInteractor;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.shade.NotificationShadeWindowView;
import com.android.systemui.shade.NotificationShadeWindowViewController;
import com.android.systemui.shade.ShadeViewController;
import com.android.systemui.shade.domain.interactor.ShadeLockscreenInteractor;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.PulseExpansionHandler;
import com.android.systemui.statusbar.StatusBarState;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.NotificationWakeUpCoordinator;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.shared.NotificationsHeadsUpRefactor;
import com.android.systemui.statusbar.phone.HeadsUpManagerPhone;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.uithreadmonitor.LooperSlowLogController;
import com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl;
import com.android.systemui.util.Assert;
import com.android.systemui.util.CopyOnLoopListenerSet;
import com.android.systemui.util.LogUtil;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.LongConsumer;

public final class DozeServiceHost implements DozeHost {
    public AODOverlayContainer mAODOverlayContainer;
    public boolean mAlwaysOnSuppressed;
    public View mAmbientIndicationContainer;
    public boolean mAnimateWakeup;
    public final Lazy mAssistManagerLazy;
    public final AuthController mAuthController;
    public final BatteryController mBatteryController;
    public final Lazy mBiometricUnlockControllerLazy;
    public final CopyOnLoopListenerSet mCallbacks = new CopyOnLoopListenerSet();
    public CentralSurfaces mCentralSurfaces;
    public final DozeServiceHost$$ExternalSyntheticLambda0 mDefaultHasPendingScreenOffCallbackChangeListener;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public final DozeInteractor mDozeInteractor;
    public final DozeLog mDozeLog;
    public final DozeScrimController mDozeScrimController;
    public boolean mDozingRequested;
    public HasPendingScreenOffCallbackChangeListener mHasPendingScreenOffCallbackChangeListener;
    public final HeadsUpManager mHeadsUpManager;
    public boolean mIgnoreTouchWhilePulsing;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public LooperSlowLogController mLooperSlowLogController;
    public final NotificationIconAreaController mNotificationIconAreaController;
    public ShadeViewController mNotificationPanelViewController;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public NotificationShadeWindowViewController mNotificationShadeWindowViewController;
    public final NotificationWakeUpCoordinator mNotificationWakeUpCoordinator;
    public final AnonymousClass2 mOnHeadsUpChangedListener;
    public Runnable mPendingScreenOffCallback;
    public Lazy mPluginAODManagerLazy;
    public final PowerManager mPowerManager;
    public final PulseExpansionHandler mPulseExpansionHandler;
    public boolean mPulsePending;
    public boolean mPulsing;
    public final ScrimController mScrimController;
    public final ShadeLockscreenInteractor mShadeLockscreenInteractor;
    public StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final SysuiStatusBarStateController mStatusBarStateController;
    boolean mWakeLockScreenPerformsAuth;
    public final WakefulnessLifecycle mWakefulnessLifecycle;

    public interface HasPendingScreenOffCallbackChangeListener {
        void onHasPendingScreenOffCallbackChanged(boolean z);
    }

    public DozeServiceHost(DozeLog dozeLog, PowerManager powerManager, WakefulnessLifecycle wakefulnessLifecycle, SysuiStatusBarStateController sysuiStatusBarStateController, DeviceProvisionedController deviceProvisionedController, HeadsUpManager headsUpManager, BatteryController batteryController, ScrimController scrimController, Lazy lazy, Lazy lazy2, DozeScrimController dozeScrimController, KeyguardUpdateMonitor keyguardUpdateMonitor, PulseExpansionHandler pulseExpansionHandler, NotificationShadeWindowController notificationShadeWindowController, NotificationWakeUpCoordinator notificationWakeUpCoordinator, AuthController authController, NotificationIconAreaController notificationIconAreaController, ShadeLockscreenInteractor shadeLockscreenInteractor, DozeInteractor dozeInteractor) {
        DozeServiceHost$$ExternalSyntheticLambda0 dozeServiceHost$$ExternalSyntheticLambda0 = new DozeServiceHost$$ExternalSyntheticLambda0();
        this.mDefaultHasPendingScreenOffCallbackChangeListener = dozeServiceHost$$ExternalSyntheticLambda0;
        this.mHasPendingScreenOffCallbackChangeListener = dozeServiceHost$$ExternalSyntheticLambda0;
        this.mWakeLockScreenPerformsAuth = SystemProperties.getBoolean("persist.sysui.wake_performs_auth", true);
        OnHeadsUpChangedListener onHeadsUpChangedListener = new OnHeadsUpChangedListener() { // from class: com.android.systemui.statusbar.phone.DozeServiceHost.2
            @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
            public final void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
                DozeServiceHost dozeServiceHost = DozeServiceHost.this;
                boolean z2 = ((StatusBarStateControllerImpl) dozeServiceHost.mStatusBarStateController).mIsDozing;
                DozeScrimController dozeScrimController2 = dozeServiceHost.mDozeScrimController;
                if (z2 && z) {
                    notificationEntry.mPulseSupressed = false;
                    DozeServiceHost$$ExternalSyntheticLambda2 dozeServiceHost$$ExternalSyntheticLambda2 = new DozeServiceHost$$ExternalSyntheticLambda2(dozeServiceHost, notificationEntry);
                    Assert.isMainThread();
                    Iterator<E> it = dozeServiceHost.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((DozeHost.Callback) it.next()).onNotificationAlerted(dozeServiceHost$$ExternalSyntheticLambda2);
                    }
                    if (dozeServiceHost.mPulsing) {
                        Handler handler = dozeScrimController2.mHandler;
                        handler.removeCallbacks(dozeScrimController2.mPulseOut);
                        handler.removeCallbacks(dozeScrimController2.mPulseOutExtended);
                    }
                }
                if (z || ((BaseHeadsUpManager) dozeServiceHost.mHeadsUpManager).hasNotifications()) {
                    return;
                }
                dozeServiceHost.mPulsePending = false;
                dozeScrimController2.mPulseOut.run();
            }
        };
        this.mDozeLog = dozeLog;
        this.mPowerManager = powerManager;
        this.mWakefulnessLifecycle = wakefulnessLifecycle;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        this.mDeviceProvisionedController = deviceProvisionedController;
        this.mHeadsUpManager = headsUpManager;
        this.mBatteryController = batteryController;
        this.mScrimController = scrimController;
        this.mBiometricUnlockControllerLazy = lazy;
        this.mAssistManagerLazy = lazy2;
        this.mDozeScrimController = dozeScrimController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mPulseExpansionHandler = pulseExpansionHandler;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mNotificationWakeUpCoordinator = notificationWakeUpCoordinator;
        this.mAuthController = authController;
        this.mNotificationIconAreaController = notificationIconAreaController;
        this.mShadeLockscreenInteractor = shadeLockscreenInteractor;
        ((BaseHeadsUpManager) headsUpManager).addListener(onHeadsUpChangedListener);
        this.mDozeInteractor = dozeInteractor;
    }

    public final void dozeTimeTick() {
        KeyguardRepositoryImpl keyguardRepositoryImpl = (KeyguardRepositoryImpl) this.mDozeInteractor.keyguardRepository;
        keyguardRepositoryImpl._dozeTimeTick.updateState(null, Long.valueOf(keyguardRepositoryImpl.systemClock.uptimeMillis()));
        this.mShadeLockscreenInteractor.dozeTimeTick();
        this.mAuthController.dozeTimeTick();
        KeyEvent.Callback callback = this.mAmbientIndicationContainer;
        if (callback instanceof DozeReceiver) {
            ((DozeReceiver) callback).dozeTimeTick();
        }
    }

    public final void extendPulse(int i) {
        if (i == 8) {
            this.mScrimController.setWakeLockScreenSensorActive(true);
        }
        DozeScrimController dozeScrimController = this.mDozeScrimController;
        if (dozeScrimController.mPulseCallback != null) {
            HeadsUpManager headsUpManager = this.mHeadsUpManager;
            if (((BaseHeadsUpManager) headsUpManager).hasNotifications()) {
                HeadsUpManagerPhone headsUpManagerPhone = (HeadsUpManagerPhone) headsUpManager;
                headsUpManagerPhone.getClass();
                NotificationsHeadsUpRefactor notificationsHeadsUpRefactor = NotificationsHeadsUpRefactor.INSTANCE;
                Flags.notificationsHeadsUpRefactor();
                HeadsUpManagerPhone.HeadsUpEntryPhone headsUpEntryPhone = (HeadsUpManagerPhone.HeadsUpEntryPhone) headsUpManagerPhone.getTopHeadsUpEntry();
                if (headsUpEntryPhone == null || headsUpEntryPhone.extended) {
                    return;
                }
                headsUpEntryPhone.extended = true;
                headsUpEntryPhone.updateEntry("extendPulse()", false);
                return;
            }
        }
        dozeScrimController.mHandler.removeCallbacks(dozeScrimController.mPulseOut);
    }

    public final NotificationShadeWindowView getNotificationPanelView() {
        NotificationShadeWindowView notificationShadeWindowView;
        NotificationShadeWindowViewController notificationShadeWindowViewController = this.mNotificationShadeWindowViewController;
        if (notificationShadeWindowViewController != null && (notificationShadeWindowView = notificationShadeWindowViewController.mView) != null) {
            return notificationShadeWindowView;
        }
        Log.d("DozeServiceHost", "getNotificationPanelView NotificationPanel is null");
        return null;
    }

    public final void pulseWhileDozing(final DozeUi.AnonymousClass2 anonymousClass2, int i) {
        if (i == 5) {
            this.mPowerManager.wakeUp(SystemClock.uptimeMillis(), 4, "com.android.systemui:LONG_PRESS");
            ((AssistManager) this.mAssistManagerLazy.get()).startAssist(new Bundle());
            return;
        }
        if (i == 8) {
            this.mScrimController.setWakeLockScreenSensorActive(true);
        }
        final boolean z = i == 8 && this.mWakeLockScreenPerformsAuth;
        this.mPulsing = true;
        DozeHost.PulseCallback pulseCallback = new DozeHost.PulseCallback() { // from class: com.android.systemui.statusbar.phone.DozeServiceHost.1
            @Override // com.android.systemui.doze.DozeHost.PulseCallback
            public final void onPulseFinished() {
                DozeServiceHost dozeServiceHost = DozeServiceHost.this;
                dozeServiceHost.mPulsing = false;
                anonymousClass2.onPulseFinished();
                ((CentralSurfacesImpl) dozeServiceHost.mCentralSurfaces).updateNotificationPanelTouchState();
                dozeServiceHost.mScrimController.setWakeLockScreenSensorActive(false);
                setPulsing(false);
            }

            @Override // com.android.systemui.doze.DozeHost.PulseCallback
            public final void onPulseStarted() {
                anonymousClass2.onPulseStarted();
                ((CentralSurfacesImpl) DozeServiceHost.this.mCentralSurfaces).updateNotificationPanelTouchState();
                setPulsing(true);
            }

            public final void setPulsing(boolean z2) {
                DozeServiceHost.this.mStatusBarKeyguardViewManager.setPulsing(z2);
                DozeServiceHost.this.mShadeLockscreenInteractor.setPulsing(z2);
                StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) DozeServiceHost.this.mStatusBarStateController;
                if (statusBarStateControllerImpl.mPulsing != z2) {
                    statusBarStateControllerImpl.mPulsing = z2;
                    synchronized (statusBarStateControllerImpl.mListeners) {
                        try {
                            Iterator it = new ArrayList(statusBarStateControllerImpl.mListeners).iterator();
                            while (it.hasNext()) {
                                ((SysuiStatusBarStateController.RankedListener) it.next()).mListener.onPulsingChanged(z2);
                            }
                        } finally {
                        }
                    }
                }
                DozeServiceHost dozeServiceHost = DozeServiceHost.this;
                dozeServiceHost.mIgnoreTouchWhilePulsing = false;
                KeyguardUpdateMonitor keyguardUpdateMonitor = dozeServiceHost.mKeyguardUpdateMonitor;
                if (keyguardUpdateMonitor != null && z) {
                    keyguardUpdateMonitor.mLogger.logAuthInterruptDetected(z2);
                    if (keyguardUpdateMonitor.mAuthInterruptActive != z2) {
                        keyguardUpdateMonitor.mAuthInterruptActive = z2;
                        keyguardUpdateMonitor.updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_ON_REACH_GESTURE_ON_AOD);
                        keyguardUpdateMonitor.requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin.WAKE, "onReach");
                    }
                }
                ((CentralSurfacesImpl) DozeServiceHost.this.mCentralSurfaces).updateScrimController();
                DozeServiceHost dozeServiceHost2 = DozeServiceHost.this;
                dozeServiceHost2.mPulseExpansionHandler.mPulsing = z2;
                NotificationWakeUpCoordinator notificationWakeUpCoordinator = dozeServiceHost2.mNotificationWakeUpCoordinator;
                notificationWakeUpCoordinator.pulsing = z2;
                if (z2) {
                    notificationWakeUpCoordinator.updateNotificationVisibility(notificationWakeUpCoordinator.shouldAnimateVisibility(), false);
                }
            }
        };
        DozeScrimController dozeScrimController = this.mDozeScrimController;
        if (dozeScrimController.mDozing && dozeScrimController.mPulseCallback == null) {
            dozeScrimController.mPulseCallback = pulseCallback;
            dozeScrimController.mPulseReason = i;
        } else {
            pulseCallback.onPulseFinished();
            boolean z2 = dozeScrimController.mDozing;
            DozeLog dozeLog = dozeScrimController.mDozeLog;
            if (z2) {
                dozeLog.tracePulseDropped("pulse - already has pulse callback mPulseCallback=" + dozeScrimController.mPulseCallback);
            } else {
                dozeLog.tracePulseDropped("pulse - device isn't dozing");
            }
        }
        ((CentralSurfacesImpl) this.mCentralSurfaces).updateScrimController();
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x004d, code lost:
    
        if (r2.mState == com.android.systemui.statusbar.phone.ScrimState.PULSING) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setAodDimmingScrim(float r3) {
        /*
            r2 = this;
            com.android.systemui.doze.DozeLog r0 = r2.mDozeLog
            r0.traceSetAodDimmingScrim(r3)
            com.android.systemui.statusbar.phone.ScrimController r2 = r2.mScrimController
            com.android.systemui.statusbar.phone.SecLsScrimControlHelper r0 = r2.mSecLsScrimControlHelper
            r0.getClass()
            int r1 = com.android.systemui.keyguard.KeyguardFastBioUnlockController.MODE_FLAG_ENABLED
            com.android.systemui.keyguard.KeyguardFastBioUnlockController r0 = r0.mKeyguardFastBioUnlockController
            boolean r0 = r0.isMode(r1)
            if (r0 == 0) goto L30
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r0 = "setAodFrontScrimAlpha: alpha="
            r2.<init>(r0)
            r2.append(r3)
            java.lang.String r3 = " skip setAodFrontScrimAlpha"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "ScrimController"
            android.util.Log.d(r3, r2)
            goto L5c
        L30:
            float r0 = r2.mInFrontAlpha
            int r0 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r0 == 0) goto L54
            com.android.systemui.statusbar.phone.ScrimState r0 = r2.mState
            com.android.systemui.statusbar.phone.ScrimState r1 = com.android.systemui.statusbar.phone.ScrimState.AOD
            if (r0 != r1) goto L49
            com.android.systemui.statusbar.phone.DozeParameters r0 = r2.mDozeParameters
            boolean r0 = r0.getAlwaysOn()
            if (r0 != 0) goto L4f
            com.android.systemui.dock.DockManager r0 = r2.mDockManager
            r0.getClass()
        L49:
            com.android.systemui.statusbar.phone.ScrimState r0 = r2.mState
            com.android.systemui.statusbar.phone.ScrimState r1 = com.android.systemui.statusbar.phone.ScrimState.PULSING
            if (r0 != r1) goto L54
        L4f:
            r2.mInFrontAlpha = r3
            r2.updateScrims()
        L54:
            com.android.systemui.statusbar.phone.ScrimState r2 = com.android.systemui.statusbar.phone.ScrimState.AOD
            r2.mAodFrontScrimAlpha = r3
            com.android.systemui.statusbar.phone.ScrimState r2 = com.android.systemui.statusbar.phone.ScrimState.PULSING
            r2.mAodFrontScrimAlpha = r3
        L5c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.DozeServiceHost.setAodDimmingScrim(float):void");
    }

    public final void stopDozing() {
        if (this.mDozingRequested) {
            Log.d("DozeServiceHost", "stopDozing");
            this.mDozingRequested = false;
            updateDozing();
            this.mDozeLog.traceDozing(((StatusBarStateControllerImpl) this.mStatusBarStateController).mIsDozing);
        }
    }

    public final String toString() {
        return "PSB.DozeServiceHost[mCallbacks=" + this.mCallbacks.size() + "]";
    }

    public final void updateDozing() {
        Assert.isMainThread();
        boolean z = Rune.SYSUI_UI_THREAD_MONITOR;
        int i = -1;
        if (z && ((LooperSlowLogControllerImpl) this.mLooperSlowLogController).isEnabled()) {
            i = LogUtil.startTime(-1);
        }
        int i2 = SceneContainerFlag.$r8$clinit;
        Flags.sceneContainer();
        final boolean z2 = this.mDozingRequested && ((StatusBarStateControllerImpl) this.mStatusBarStateController).mState == 1;
        if (((BiometricUnlockController) this.mBiometricUnlockControllerLazy.get()).mMode == 1 || ((KeyguardFastBioUnlockController) Dependency.sDependency.getDependencyInner(KeyguardFastBioUnlockController.class)).isFastWakeAndUnlockMode()) {
            z2 = false;
        }
        if (z2 && this.mWakefulnessLifecycle.mWakefulness == 1) {
            Log.d("DozeServiceHost", "updateDozing set dozing false (dozing true, setDozing to false)");
            z2 = false;
        }
        StringBuilder m = RowView$$ExternalSyntheticOutline0.m("updateDozing dozing = ", " / dozingRequested = ", z2);
        m.append(this.mDozingRequested);
        m.append(" / state = ");
        m.append(StatusBarState.toString(((StatusBarStateControllerImpl) this.mStatusBarStateController).mState));
        m.append(" mode = ");
        RecyclerView$$ExternalSyntheticOutline0.m(((BiometricUnlockController) this.mBiometricUnlockControllerLazy.get()).mMode, "DozeServiceHost", m);
        ((PluginAODManager) this.mPluginAODManagerLazy.get()).setIsDozing(z2, ((BiometricUnlockController) this.mBiometricUnlockControllerLazy.get()).mMode == 1);
        if (z && i >= 0) {
            LogUtil.lapTime(i, new LongConsumer() { // from class: com.android.systemui.statusbar.phone.DozeServiceHost$$ExternalSyntheticLambda1
                @Override // java.util.function.LongConsumer
                public final void accept(long j) {
                    boolean z3 = z2;
                    if (j >= 10) {
                        LogUtil.w("LooperSlow", "updateDozing %s AOD %dms ", Boolean.valueOf(z3), Long.valueOf(j));
                    }
                }
            });
        }
        Iterator<E> it = this.mCallbacks.iterator();
        while (it.hasNext()) {
            ((DozeHost.Callback) it.next()).getClass();
        }
        ((KeyguardRepositoryImpl) this.mDozeInteractor.keyguardRepository)._isDozing.updateState(null, Boolean.valueOf(z2));
        StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) this.mStatusBarStateController;
        if (statusBarStateControllerImpl.mIsDozing != z2) {
            statusBarStateControllerImpl.mIsDozing = z2;
            if (Rune.SYSUI_UI_THREAD_MONITOR && ((LooperSlowLogControllerImpl) statusBarStateControllerImpl.mLooperSlowLogController).isEnabled()) {
                synchronized (statusBarStateControllerImpl.mListeners) {
                    try {
                        Iterator it2 = new ArrayList(statusBarStateControllerImpl.mListeners).iterator();
                        while (it2.hasNext()) {
                            final SysuiStatusBarStateController.RankedListener rankedListener = (SysuiStatusBarStateController.RankedListener) it2.next();
                            LogUtil.execTime(new Runnable() { // from class: com.android.systemui.statusbar.StatusBarStateControllerImpl$$ExternalSyntheticLambda3
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SysuiStatusBarStateController.RankedListener rankedListener2 = SysuiStatusBarStateController.RankedListener.this;
                                    rankedListener2.mListener.onDozingChanged(z2);
                                }
                            }, 10, "LooperSlow", "setIsDozing " + rankedListener, new Object[0]);
                        }
                    } finally {
                    }
                }
            } else {
                synchronized (statusBarStateControllerImpl.mListeners) {
                    try {
                        DejankUtils.startDetectingBlockingIpcs("StatusBarStateControllerImpl#setIsDozing");
                        Iterator it3 = new ArrayList(statusBarStateControllerImpl.mListeners).iterator();
                        while (it3.hasNext()) {
                            ((SysuiStatusBarStateController.RankedListener) it3.next()).mListener.onDozingChanged(z2);
                        }
                        DejankUtils.stopDetectingBlockingIpcs("StatusBarStateControllerImpl#setIsDozing");
                    } finally {
                    }
                }
            }
        }
        if (!Rune.SYSUI_UI_THREAD_MONITOR || i < 0) {
            return;
        }
        LogUtil.endTime(i, 10, "LooperSlow", "updateDozing end", new Object[0]);
    }
}
