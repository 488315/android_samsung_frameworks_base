package com.android.systemui.statusbar.phone;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.keyguard.ActiveUnlockConfig;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dependency;
import com.android.systemui.Rune;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.doze.AODOverlayContainer;
import com.android.systemui.doze.DozeHost;
import com.android.systemui.doze.DozeLog;
import com.android.systemui.doze.DozeLogger;
import com.android.systemui.doze.DozeLogger$$ExternalSyntheticLambda3;
import com.android.systemui.doze.DozeUi;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.data.repository.KeyguardRepositoryImpl;
import com.android.systemui.keyguard.domain.interactor.DozeInteractor;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
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
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.uithreadmonitor.LooperSlowLogController;
import com.android.systemui.uithreadmonitor.LooperSlowLogControllerImpl;
import com.android.systemui.util.Assert;
import com.android.systemui.util.CopyOnLoopListenerSet;
import com.android.systemui.util.LogUtil;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.LongConsumer;

/* loaded from: classes3.dex */
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
    public CentralSurfacesImpl mCentralSurfaces;
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

    /* renamed from: com.android.systemui.statusbar.phone.DozeServiceHost$1, reason: invalid class name */
    public class AnonymousClass1 implements DozeHost.PulseCallback {
        public final /* synthetic */ DozeHost.PulseCallback val$callback;
        public final /* synthetic */ boolean val$passiveAuthInterrupt;

        public AnonymousClass1(DozeHost.PulseCallback pulseCallback, boolean z) {
            this.val$callback = pulseCallback;
            this.val$passiveAuthInterrupt = z;
        }

        @Override // com.android.systemui.doze.DozeHost.PulseCallback
        public final void onPulseFinished() {
            DozeServiceHost dozeServiceHost = DozeServiceHost.this;
            dozeServiceHost.mPulsing = false;
            this.val$callback.onPulseFinished();
            dozeServiceHost.mCentralSurfaces.updateNotificationPanelTouchState();
            dozeServiceHost.mScrimController.setWakeLockScreenSensorActive(false);
            setPulsing(false);
        }

        @Override // com.android.systemui.doze.DozeHost.PulseCallback
        public final void onPulseStarted() {
            this.val$callback.onPulseStarted();
            DozeServiceHost.this.mCentralSurfaces.updateNotificationPanelTouchState();
            setPulsing(true);
        }

        public final void setPulsing(boolean z) {
            DozeServiceHost.this.mStatusBarKeyguardViewManager.setPulsing(z);
            DozeServiceHost.this.mShadeLockscreenInteractor.setPulsing(z);
            StatusBarStateControllerImpl statusBarStateControllerImpl = (StatusBarStateControllerImpl) DozeServiceHost.this.mStatusBarStateController;
            if (statusBarStateControllerImpl.mPulsing != z) {
                statusBarStateControllerImpl.mPulsing = z;
                synchronized (statusBarStateControllerImpl.mListeners) {
                    try {
                        ArrayList arrayList = new ArrayList(statusBarStateControllerImpl.mListeners);
                        int size = arrayList.size();
                        int i = 0;
                        while (i < size) {
                            Object obj = arrayList.get(i);
                            i++;
                            ((SysuiStatusBarStateController.RankedListener) obj).mListener.onPulsingChanged(z);
                        }
                    } finally {
                    }
                }
            }
            DozeServiceHost dozeServiceHost = DozeServiceHost.this;
            dozeServiceHost.mIgnoreTouchWhilePulsing = false;
            KeyguardUpdateMonitor keyguardUpdateMonitor = dozeServiceHost.mKeyguardUpdateMonitor;
            if (keyguardUpdateMonitor != null && this.val$passiveAuthInterrupt) {
                KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = keyguardUpdateMonitor.mLogger;
                keyguardUpdateMonitorLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(23);
                LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
                ((LogMessageImpl) logMessageObtain).bool1 = z;
                logBuffer.commit(logMessageObtain);
                if (keyguardUpdateMonitor.mAuthInterruptActive != z) {
                    keyguardUpdateMonitor.mAuthInterruptActive = z;
                    keyguardUpdateMonitor.updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_ON_REACH_GESTURE_ON_AOD);
                    keyguardUpdateMonitor.requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin.WAKE, "onReach");
                }
            }
            DozeServiceHost.this.mCentralSurfaces.updateScrimController();
            DozeServiceHost dozeServiceHost2 = DozeServiceHost.this;
            dozeServiceHost2.mPulseExpansionHandler.mPulsing = z;
            NotificationWakeUpCoordinator notificationWakeUpCoordinator = dozeServiceHost2.mNotificationWakeUpCoordinator;
            notificationWakeUpCoordinator.pulsing = z;
            if (z) {
                notificationWakeUpCoordinator.updateNotificationVisibility(notificationWakeUpCoordinator.shouldAnimateVisibility(), false);
            }
        }
    }

    public interface HasPendingScreenOffCallbackChangeListener {
        void onHasPendingScreenOffCallbackChanged(boolean z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener, com.android.systemui.statusbar.phone.DozeServiceHost$2] */
    public DozeServiceHost(DozeLog dozeLog, PowerManager powerManager, WakefulnessLifecycle wakefulnessLifecycle, SysuiStatusBarStateController sysuiStatusBarStateController, DeviceProvisionedController deviceProvisionedController, HeadsUpManager headsUpManager, BatteryController batteryController, ScrimController scrimController, Lazy lazy, Lazy lazy2, DozeScrimController dozeScrimController, KeyguardUpdateMonitor keyguardUpdateMonitor, PulseExpansionHandler pulseExpansionHandler, NotificationShadeWindowController notificationShadeWindowController, NotificationWakeUpCoordinator notificationWakeUpCoordinator, AuthController authController, NotificationIconAreaController notificationIconAreaController, ShadeLockscreenInteractor shadeLockscreenInteractor, DozeInteractor dozeInteractor) {
        DozeServiceHost$$ExternalSyntheticLambda0 dozeServiceHost$$ExternalSyntheticLambda0 = new DozeServiceHost$$ExternalSyntheticLambda0();
        this.mDefaultHasPendingScreenOffCallbackChangeListener = dozeServiceHost$$ExternalSyntheticLambda0;
        this.mHasPendingScreenOffCallbackChangeListener = dozeServiceHost$$ExternalSyntheticLambda0;
        this.mWakeLockScreenPerformsAuth = SystemProperties.getBoolean("persist.sysui.wake_performs_auth", true);
        ?? r0 = new OnHeadsUpChangedListener() { // from class: com.android.systemui.statusbar.phone.DozeServiceHost.2
            @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
            public final void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
                DozeServiceHost dozeServiceHost = DozeServiceHost.this;
                boolean zIsDozing = dozeServiceHost.mStatusBarStateController.isDozing();
                DozeScrimController dozeScrimController2 = dozeServiceHost.mDozeScrimController;
                if (zIsDozing && z) {
                    notificationEntry.getClass();
                    DozeServiceHost$$ExternalSyntheticLambda3 dozeServiceHost$$ExternalSyntheticLambda3 = new DozeServiceHost$$ExternalSyntheticLambda3(dozeServiceHost, notificationEntry);
                    Assert.isMainThread();
                    Iterator<E> it = dozeServiceHost.mCallbacks.iterator();
                    while (it.hasNext()) {
                        ((DozeHost.Callback) it.next()).onNotificationAlerted(dozeServiceHost$$ExternalSyntheticLambda3);
                    }
                    if (dozeServiceHost.mPulsing) {
                        Handler handler = dozeScrimController2.mHandler;
                        handler.removeCallbacks(dozeScrimController2.mPulseOut);
                        handler.removeCallbacks(dozeScrimController2.mPulseOutExtended);
                    }
                }
                if (z || ((HeadsUpManagerImpl) dozeServiceHost.mHeadsUpManager).hasNotifications()) {
                    return;
                }
                dozeServiceHost.mPulsePending = false;
                dozeScrimController2.mPulseOut.run();
            }
        };
        this.mOnHeadsUpChangedListener = r0;
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
        ((HeadsUpManagerImpl) headsUpManager).addListener(r0);
        this.mDozeInteractor = dozeInteractor;
    }

    public final void extendPulse(int i) {
        if (i == 8) {
            this.mScrimController.setWakeLockScreenSensorActive(true);
        }
        DozeScrimController dozeScrimController = this.mDozeScrimController;
        if (dozeScrimController.mPulseCallback != null) {
            HeadsUpManagerImpl headsUpManagerImpl = (HeadsUpManagerImpl) this.mHeadsUpManager;
            if (headsUpManagerImpl.hasNotifications()) {
                headsUpManagerImpl.getClass();
                int i2 = SceneContainerFlag.$r8$clinit;
                HeadsUpManagerImpl.HeadsUpEntry topHeadsUpEntry = headsUpManagerImpl.getTopHeadsUpEntry();
                if (topHeadsUpEntry == null || topHeadsUpEntry.extended) {
                    return;
                }
                topHeadsUpEntry.extended = true;
                topHeadsUpEntry.updateEntry("extendPulse()", false, true);
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

    public final void pulseWhileDozing(DozeUi.AnonymousClass2 anonymousClass2, int i) throws PackageManager.NameNotFoundException {
        if (i == 5) {
            this.mPowerManager.wakeUp(SystemClock.uptimeMillis(), 4, "com.android.systemui:LONG_PRESS");
            ((AssistManager) this.mAssistManagerLazy.get()).startAssist(new Bundle());
            return;
        }
        if (i == 8) {
            this.mScrimController.setWakeLockScreenSensorActive(true);
        }
        boolean z = i == 8 && this.mWakeLockScreenPerformsAuth;
        this.mPulsing = true;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(anonymousClass2, z);
        DozeScrimController dozeScrimController = this.mDozeScrimController;
        if (dozeScrimController.mDozing && dozeScrimController.mPulseCallback == null) {
            int i2 = SceneContainerFlag.$r8$clinit;
            dozeScrimController.mPulseCallback = anonymousClass1;
            dozeScrimController.mPulseReason = i;
        } else {
            anonymousClass1.onPulseFinished();
            boolean z2 = dozeScrimController.mDozing;
            DozeLog dozeLog = dozeScrimController.mDozeLog;
            if (z2) {
                dozeLog.tracePulseDropped("pulse - already has pulse callback mPulseCallback=" + dozeScrimController.mPulseCallback);
            } else {
                dozeLog.tracePulseDropped("pulse - device isn't dozing");
            }
        }
        this.mCentralSurfaces.updateScrimController();
    }

    public final void setAodDimmingScrim(float f) {
        DozeLogger dozeLogger = this.mDozeLog.mLogger;
        dozeLogger.getClass();
        LogLevel logLevel = LogLevel.INFO;
        DozeLogger$$ExternalSyntheticLambda3 dozeLogger$$ExternalSyntheticLambda3 = new DozeLogger$$ExternalSyntheticLambda3(0);
        LogBuffer logBuffer = dozeLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("DozeLog", logLevel, dozeLogger$$ExternalSyntheticLambda3, null);
        ((LogMessageImpl) logMessageObtain).long1 = (long) f;
        logBuffer.commit(logMessageObtain);
        ScrimController scrimController = this.mScrimController;
        SecLsScrimControlHelper secLsScrimControlHelper = scrimController.mSecLsScrimControlHelper;
        secLsScrimControlHelper.getClass();
        if (secLsScrimControlHelper.mKeyguardFastBioUnlockController.isMode(KeyguardFastBioUnlockController.MODE_FLAG_ENABLED)) {
            Log.d("ScrimController", "setAodFrontScrimAlpha: alpha=" + f + " skip setAodFrontScrimAlpha");
            return;
        }
        if (scrimController.mInFrontAlpha != f) {
            if (scrimController.mState == ScrimState.AOD) {
                if (!scrimController.mDozeParameters.getAlwaysOn()) {
                    scrimController.mDockManager.getClass();
                    if (scrimController.mState == ScrimState.PULSING) {
                    }
                }
                scrimController.mInFrontAlpha = f;
                scrimController.updateScrims();
            } else if (scrimController.mState == ScrimState.PULSING) {
                scrimController.mInFrontAlpha = f;
                scrimController.updateScrims();
            }
        }
        ScrimState.AOD.mAodFrontScrimAlpha = f;
        ScrimState.PULSING.mAodFrontScrimAlpha = f;
    }

    public final void stopDozing() {
        if (this.mDozingRequested) {
            Log.d("DozeServiceHost", "stopDozing");
            this.mDozingRequested = false;
            updateDozing();
            this.mDozeLog.traceDozing(this.mStatusBarStateController.isDozing());
        }
    }

    public final String toString() {
        return "PSB.DozeServiceHost[mCallbacks=" + this.mCallbacks.size() + "]";
    }

    public final void updateDozing() {
        Assert.isMainThread();
        boolean z = Rune.SYSUI_UI_THREAD_MONITOR;
        int iStartTime = -1;
        if (z && ((LooperSlowLogControllerImpl) this.mLooperSlowLogController).isEnabled()) {
            iStartTime = LogUtil.startTime(-1);
        }
        int i = SceneContainerFlag.$r8$clinit;
        final boolean z2 = this.mDozingRequested && this.mStatusBarStateController.getState() == 1;
        if (((BiometricUnlockController) this.mBiometricUnlockControllerLazy.get()).mMode == 1 || ((KeyguardFastBioUnlockController) Dependency.sDependency.getDependencyInner(KeyguardFastBioUnlockController.class)).isFastWakeAndUnlockMode()) {
            z2 = false;
        }
        if (z2 && this.mWakefulnessLifecycle.mWakefulness == 1) {
            Log.d("DozeServiceHost", "updateDozing set dozing false (dozing true, setDozing to false)");
            z2 = false;
        }
        StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("updateDozing dozing = ", " / dozingRequested = ", z2);
        sbM.append(this.mDozingRequested);
        sbM.append(" / state = ");
        sbM.append(StatusBarState.toString(this.mStatusBarStateController.getState()));
        sbM.append(" mode = ");
        RecyclerView$$ExternalSyntheticOutline0.m(((BiometricUnlockController) this.mBiometricUnlockControllerLazy.get()).mMode, "DozeServiceHost", sbM);
        ((PluginAODManager) this.mPluginAODManagerLazy.get()).setIsDozing(z2, ((BiometricUnlockController) this.mBiometricUnlockControllerLazy.get()).mMode == 1);
        if (z && iStartTime >= 0) {
            LogUtil.lapTime(iStartTime, new LongConsumer() { // from class: com.android.systemui.statusbar.phone.DozeServiceHost$$ExternalSyntheticLambda1
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
                        ArrayList arrayList = new ArrayList(statusBarStateControllerImpl.mListeners);
                        int size = arrayList.size();
                        int i2 = 0;
                        while (i2 < size) {
                            Object obj = arrayList.get(i2);
                            i2++;
                            final SysuiStatusBarStateController.RankedListener rankedListener = (SysuiStatusBarStateController.RankedListener) obj;
                            LogUtil.execTime(new Runnable() { // from class: com.android.systemui.statusbar.StatusBarStateControllerImpl$$ExternalSyntheticLambda2
                                @Override // java.lang.Runnable
                                public final void run() {
                                    SysuiStatusBarStateController.RankedListener rankedListener2 = rankedListener;
                                    boolean z3 = z2;
                                    Comparator comparator = StatusBarStateControllerImpl.sComparator;
                                    rankedListener2.mListener.onDozingChanged(z3);
                                }
                            }, 10, "LooperSlow", "setIsDozing " + rankedListener, new Object[0]);
                        }
                    } finally {
                    }
                }
            } else {
                synchronized (statusBarStateControllerImpl.mListeners) {
                    try {
                        String strConcat = statusBarStateControllerImpl.getClass().getSimpleName().concat("#setIsDozing");
                        DejankUtils.startDetectingBlockingIpcs(strConcat);
                        ArrayList arrayList2 = new ArrayList(statusBarStateControllerImpl.mListeners);
                        int size2 = arrayList2.size();
                        int i3 = 0;
                        while (i3 < size2) {
                            Object obj2 = arrayList2.get(i3);
                            i3++;
                            ((SysuiStatusBarStateController.RankedListener) obj2).mListener.onDozingChanged(z2);
                        }
                        DejankUtils.stopDetectingBlockingIpcs(strConcat);
                    } finally {
                    }
                }
            }
        }
        if (!Rune.SYSUI_UI_THREAD_MONITOR || iStartTime < 0) {
            return;
        }
        LogUtil.endTime(iStartTime, 10, "LooperSlow", "updateDozing end", new Object[0]);
    }
}
