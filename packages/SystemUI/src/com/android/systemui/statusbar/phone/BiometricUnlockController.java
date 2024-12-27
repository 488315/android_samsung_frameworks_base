package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.biometrics.BiometricSourceType;
import android.metrics.LogMaker;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Trace;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.logging.UiEventLoggerImpl;
import com.android.internal.util.LatencyTracker;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.keyguard.biometrics.KeyguardBiometricToastView;
import com.android.keyguard.logging.BiometricUnlockLogger;
import com.android.settingslib.fuelgauge.BatteryStatus;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.KeyguardViewMediator;
import com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl;
import com.android.systemui.keyguard.Log;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.ViewMediatorProvider;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.keyguard.domain.interactor.BiometricUnlockInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.BiometricUnlockSource;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.kotlin.JavaAdapter;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.vibrate.VibrationUtil;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.sdp.internal.SdpAuthenticator;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public class BiometricUnlockController extends KeyguardUpdateMonitorCallback implements Dumpable {
    public static final UiEventLogger UI_EVENT_LOGGER = new UiEventLoggerImpl();
    public final AuthController mAuthController;
    public BiometricSourceType mAuthenticatedBioSourceType;
    public KeyguardBiometricToastView mBiometricToastView;
    public BiometricSourceType mBiometricType;
    public final BiometricUnlockInteractor mBiometricUnlockInteractor;
    public boolean mBouncer;
    public final int mConsecutiveFpFailureThreshold;
    public final Context mContext;
    public final DozeScrimController mDozeScrimController;
    public int mDynamicLockMode;
    public boolean mFadedAwayAfterWakeAndUnlock;
    public final KeyguardFastBioUnlockController mFastUnlockController;
    public final Handler mHandler;
    public boolean mIsStartedGoingToSleep;
    public final KeyguardBypassController mKeyguardBypassController;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardTransitionInteractor mKeyguardTransitionInteractor;
    public KeyguardViewController mKeyguardViewController;
    public final KeyguardViewMediator mKeyguardViewMediator;
    public long mLastFpFailureUptimeMillis;
    public final LatencyTracker mLatencyTracker;
    public final BiometricUnlockLogger mLogger;
    public final MetricsLogger mMetricsLogger;
    public int mMode;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public int mNumConsecutiveFpFailures;
    public final boolean mOrderUnlockAndWake;
    public final PowerManager mPowerManager;
    public final AnonymousClass4 mScreenObserver;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public final Lazy mSecLockIconViewControllerLazy;
    public final Lazy mSelectedUserInteractor;
    public final SessionTracker mSessionTracker;
    public final StatusBarStateController mStatusBarStateController;
    public final SystemClock mSystemClock;
    public final KeyguardUpdateMonitor mUpdateMonitor;
    public final VibrationUtil mVibrationUtil;
    public final VibratorHelper mVibratorHelper;
    public PowerManager.WakeLock mWakeLock;
    final WakefulnessLifecycle.Observer mWakefulnessObserver;
    public WindowManager.LayoutParams mWindowLp;
    public final WindowManager mWindowManager;
    public PendingAuthenticated mPendingAuthenticated = null;
    public final Set mBiometricUnlockEventsListeners = new HashSet();
    public final AnonymousClass1 mReleaseBiometricWakeLockRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.BiometricUnlockController.1
        @Override // java.lang.Runnable
        public final void run() {
            BiometricUnlockLogger biometricUnlockLogger = BiometricUnlockController.this.mLogger;
            biometricUnlockLogger.getClass();
            biometricUnlockLogger.logBuffer.log("BiometricUnlockLogger", LogLevel.INFO, "biometric wakelock: TIMEOUT!!", null);
            BiometricUnlockController.this.releaseBiometricWakeLock();
        }
    };

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.phone.BiometricUnlockController$3, reason: invalid class name */
    public final class AnonymousClass3 implements WakefulnessLifecycle.Observer {
        public AnonymousClass3() {
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedGoingToSleep() {
            Trace.beginSection("BiometricUnlockController#onFinishedGoingToSleep");
            BiometricUnlockController biometricUnlockController = BiometricUnlockController.this;
            if (biometricUnlockController.mPendingAuthenticated != null) {
                BiometricUnlockLogger biometricUnlockLogger = biometricUnlockController.mLogger;
                biometricUnlockLogger.getClass();
                biometricUnlockLogger.logBuffer.log("BiometricUnlockLogger", LogLevel.DEBUG, "onFinishedGoingToSleep with pendingAuthenticated != null", null);
                final PendingAuthenticated pendingAuthenticated = biometricUnlockController.mPendingAuthenticated;
                biometricUnlockController.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.BiometricUnlockController$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BiometricUnlockController.AnonymousClass3 anonymousClass3 = BiometricUnlockController.AnonymousClass3.this;
                        BiometricUnlockController.PendingAuthenticated pendingAuthenticated2 = pendingAuthenticated;
                        anonymousClass3.getClass();
                        BiometricUnlockController.this.onBiometricAuthenticated(pendingAuthenticated2.userId, pendingAuthenticated2.biometricSourceType, pendingAuthenticated2.isStrongBiometric);
                    }
                });
                biometricUnlockController.mPendingAuthenticated = null;
            }
            Trace.endSection();
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedGoingToSleep() {
            UiEventLogger uiEventLogger = BiometricUnlockController.UI_EVENT_LOGGER;
            BiometricUnlockController biometricUnlockController = BiometricUnlockController.this;
            biometricUnlockController.resetMode();
            biometricUnlockController.mFadedAwayAfterWakeAndUnlock = false;
            biometricUnlockController.mPendingAuthenticated = null;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.statusbar.phone.BiometricUnlockController$5, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass5 {
        public static final /* synthetic */ int[] $SwitchMap$android$hardware$biometrics$BiometricSourceType;

        static {
            int[] iArr = new int[BiometricSourceType.values().length];
            $SwitchMap$android$hardware$biometrics$BiometricSourceType = iArr;
            try {
                iArr[BiometricSourceType.FINGERPRINT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$android$hardware$biometrics$BiometricSourceType[BiometricSourceType.FACE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$android$hardware$biometrics$BiometricSourceType[BiometricSourceType.IRIS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    /* JADX WARN: Enum visitor error
    jadx.core.utils.exceptions.JadxRuntimeException: Can't remove SSA var: r11v0 com.android.systemui.statusbar.phone.BiometricUnlockController$BiometricUiEvent, still in use, count: 1, list:
      (r11v0 com.android.systemui.statusbar.phone.BiometricUnlockController$BiometricUiEvent) from 0x00b2: INVOKE 
      (r9v2 android.hardware.biometrics.BiometricSourceType)
      (r11v0 com.android.systemui.statusbar.phone.BiometricUnlockController$BiometricUiEvent)
      (r10v2 android.hardware.biometrics.BiometricSourceType)
      (r14v0 com.android.systemui.statusbar.phone.BiometricUnlockController$BiometricUiEvent)
      (r16v1 android.hardware.biometrics.BiometricSourceType)
      (r9v0 com.android.systemui.statusbar.phone.BiometricUnlockController$BiometricUiEvent)
     STATIC call: java.util.Map.of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):java.util.Map A[MD:<K, V>:(K, V, K, V, K, V):java.util.Map<K, V> (c), WRAPPED] (LINE:179)
    	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
    	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
    	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1511)
    	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
    	at jadx.core.utils.InsnRemover.removeAllAndUnbind(InsnRemover.java:252)
    	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:180)
    	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
     */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class BiometricUiEvent implements UiEventLogger.UiEventEnum {
        /* JADX INFO: Fake field, exist only in values array */
        BIOMETRIC_FINGERPRINT_SUCCESS(396),
        /* JADX INFO: Fake field, exist only in values array */
        BIOMETRIC_FINGERPRINT_FAILURE(397),
        /* JADX INFO: Fake field, exist only in values array */
        BIOMETRIC_FINGERPRINT_ERROR(398),
        /* JADX INFO: Fake field, exist only in values array */
        BIOMETRIC_FACE_SUCCESS(399),
        /* JADX INFO: Fake field, exist only in values array */
        BIOMETRIC_FACE_FAILURE(400),
        /* JADX INFO: Fake field, exist only in values array */
        BIOMETRIC_FACE_ERROR(401),
        /* JADX INFO: Fake field, exist only in values array */
        BIOMETRIC_IRIS_SUCCESS(402),
        /* JADX INFO: Fake field, exist only in values array */
        BIOMETRIC_IRIS_FAILURE(403),
        /* JADX INFO: Fake field, exist only in values array */
        BIOMETRIC_IRIS_ERROR(404),
        BIOMETRIC_BOUNCER_SHOWN(916),
        STARTED_WAKING_UP(1378);

        public static final Map ERROR_EVENT_BY_SOURCE_TYPE;
        public static final Map FAILURE_EVENT_BY_SOURCE_TYPE;
        public static final Map SUCCESS_EVENT_BY_SOURCE_TYPE;
        private final int mId;

        static {
            BiometricSourceType biometricSourceType = BiometricSourceType.FINGERPRINT;
            BiometricSourceType biometricSourceType2 = BiometricSourceType.FACE;
            BiometricSourceType biometricSourceType3 = BiometricSourceType.IRIS;
            ERROR_EVENT_BY_SOURCE_TYPE = Map.of(biometricSourceType, r13, biometricSourceType2, r10, biometricSourceType3, r7);
            SUCCESS_EVENT_BY_SOURCE_TYPE = Map.of(biometricSourceType, r11, biometricSourceType2, r14, biometricSourceType3, r9);
            FAILURE_EVENT_BY_SOURCE_TYPE = Map.of(biometricSourceType, r12, biometricSourceType2, r15, biometricSourceType3, r8);
        }

        private BiometricUiEvent(int i) {
            this.mId = i;
        }

        public static BiometricUiEvent valueOf(String str) {
            return (BiometricUiEvent) Enum.valueOf(BiometricUiEvent.class, str);
        }

        public static BiometricUiEvent[] values() {
            return (BiometricUiEvent[]) $VALUES.clone();
        }

        public final int getId() {
            return this.mId;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class PendingAuthenticated {
        public final BiometricSourceType biometricSourceType;
        public final boolean isStrongBiometric;
        public final int userId;

        public PendingAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
            this.userId = i;
            this.biometricSourceType = biometricSourceType;
            this.isStrongBiometric = z;
        }
    }

    /* JADX WARN: Type inference failed for: r4v2, types: [com.android.systemui.statusbar.phone.BiometricUnlockController$1] */
    public BiometricUnlockController(Lazy lazy, DozeScrimController dozeScrimController, KeyguardViewMediator keyguardViewMediator, NotificationShadeWindowController notificationShadeWindowController, KeyguardStateController keyguardStateController, Handler handler, KeyguardUpdateMonitor keyguardUpdateMonitor, Resources resources, KeyguardBypassController keyguardBypassController, MetricsLogger metricsLogger, DumpManager dumpManager, PowerManager powerManager, BiometricUnlockLogger biometricUnlockLogger, NotificationMediaManager notificationMediaManager, WakefulnessLifecycle wakefulnessLifecycle, ScreenLifecycle screenLifecycle, AuthController authController, StatusBarStateController statusBarStateController, SessionTracker sessionTracker, LatencyTracker latencyTracker, ScreenOffAnimationController screenOffAnimationController, VibratorHelper vibratorHelper, SystemClock systemClock, Lazy lazy2, BiometricUnlockInteractor biometricUnlockInteractor, JavaAdapter javaAdapter, KeyguardTransitionInteractor keyguardTransitionInteractor, KeyguardFastBioUnlockController keyguardFastBioUnlockController, VibrationUtil vibrationUtil, WindowManager windowManager, Context context) {
        AnonymousClass3 anonymousClass3 = new AnonymousClass3();
        this.mWakefulnessObserver = anonymousClass3;
        ScreenLifecycle.Observer observer = new ScreenLifecycle.Observer() { // from class: com.android.systemui.statusbar.phone.BiometricUnlockController.4
            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public final void onScreenTurnedOn() {
                boolean isEnabledWof = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnabledWof();
                BiometricUnlockController biometricUnlockController = BiometricUnlockController.this;
                if (isEnabledWof && biometricUnlockController.mUpdateMonitor.isFingerprintDisabledWithBadQuality()) {
                    Toast.makeText(biometricUnlockController.mContext, R.string.kg_finger_print_bad_quality_error_message, 1).show();
                    biometricUnlockController.mUpdateMonitor.clearFingerBadQualityCounts();
                }
                UiEventLogger uiEventLogger = BiometricUnlockController.UI_EVENT_LOGGER;
                biometricUnlockController.getClass();
            }
        };
        this.mPowerManager = powerManager;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mLatencyTracker = latencyTracker;
        wakefulnessLifecycle.addObserver(anonymousClass3);
        screenLifecycle.addObserver(observer);
        this.mBiometricUnlockInteractor = biometricUnlockInteractor;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mDozeScrimController = dozeScrimController;
        this.mKeyguardViewMediator = keyguardViewMediator;
        this.mKeyguardStateController = keyguardStateController;
        this.mHandler = handler;
        this.mConsecutiveFpFailureThreshold = resources.getInteger(R.integer.fp_consecutive_failure_time_ms);
        this.mKeyguardBypassController = keyguardBypassController;
        keyguardBypassController.unlockController = this;
        this.mMetricsLogger = metricsLogger;
        this.mStatusBarStateController = statusBarStateController;
        this.mSessionTracker = sessionTracker;
        this.mScreenOffAnimationController = screenOffAnimationController;
        this.mVibratorHelper = vibratorHelper;
        this.mLogger = biometricUnlockLogger;
        this.mSystemClock = systemClock;
        this.mOrderUnlockAndWake = resources.getBoolean(android.R.bool.config_profcollectReportUploaderEnabled);
        this.mSelectedUserInteractor = lazy2;
        this.mKeyguardTransitionInteractor = keyguardTransitionInteractor;
        javaAdapter.alwaysCollectFlow(keyguardTransitionInteractor.startedKeyguardTransitionStep, new BiometricUnlockController$$ExternalSyntheticLambda0(this, 0));
        this.mSecLockIconViewControllerLazy = lazy;
        this.mVibrationUtil = vibrationUtil;
        this.mWindowManager = windowManager;
        this.mContext = context;
        this.mFastUnlockController = keyguardFastBioUnlockController;
        dumpManager.registerDumpable(this);
    }

    public static boolean isLargeCoverScreen() {
        return LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY && !((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened;
    }

    public static int toSubtype(BiometricSourceType biometricSourceType) {
        int i = AnonymousClass5.$SwitchMap$android$hardware$biometrics$BiometricSourceType[biometricSourceType.ordinal()];
        if (i == 1) {
            return 0;
        }
        if (i != 2) {
            return i != 3 ? 3 : 2;
        }
        return 1;
    }

    public void consumeTransitionStepOnStartedKeyguardState(TransitionStep transitionStep) {
        if (transitionStep.from == KeyguardState.GONE) {
            this.mBiometricUnlockInteractor.setBiometricUnlockState(0, null);
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println(" BiometricUnlockController:");
        printWriter.print("   mMode=");
        printWriter.println(this.mMode);
        printWriter.print("   mWakeLock=");
        printWriter.println(this.mWakeLock);
        if (this.mUpdateMonitor.isUdfpsSupported()) {
            printWriter.print("   mNumConsecutiveFpFailures=");
            printWriter.println(this.mNumConsecutiveFpFailures);
            printWriter.print("   time since last failure=");
            printWriter.println(this.mSystemClock.uptimeMillis() - this.mLastFpFailureUptimeMillis);
        }
    }

    public final void finishKeyguardFadingAway() {
        final boolean z = true;
        if (isWakeAndUnlock()) {
            this.mFadedAwayAfterWakeAndUnlock = true;
        }
        Log.d("BiometricUnlockCtrl", "finishKeyguardFadingAway");
        if (isBiometricUnlock()) {
            this.mUpdateMonitor.sendBiometricUnlockState(this.mAuthenticatedBioSourceType);
        }
        final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mKeyguardViewMediator.mHelper;
        Lazy lazy = keyguardViewMediatorHelperImpl.biometricUnlockControllerLazy;
        if (!((BiometricUnlockController) lazy.get()).isBiometricUnlock()) {
            BiometricUnlockController biometricUnlockController = (BiometricUnlockController) lazy.get();
            if (!biometricUnlockController.isWakeAndUnlock() && !biometricUnlockController.mFadedAwayAfterWakeAndUnlock && ((BiometricUnlockController) lazy.get()).mMode != 7) {
                z = false;
            }
        }
        keyguardViewMediatorHelperImpl.uiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$onSdpUnlocked$1
            @Override // java.lang.Runnable
            public final void run() {
                KnoxStateMonitor knoxStateMonitor = KeyguardViewMediatorHelperImpl.this.knoxStateMonitor;
                boolean z2 = z;
                int selectedUserId = ((KnoxStateMonitorImpl) knoxStateMonitor).mSelectedUserInteractor.getSelectedUserId();
                if (!SemPersonaManager.isDoEnabled(selectedUserId) || !z2) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m(selectedUserId, "unlockSdp :: Maybe keyguard hidden as user ", "KnoxStateMonitorImpl");
                    return;
                }
                android.util.Log.d("KnoxStateMonitorImpl", "unlockSdp :: Device Owner has been authenticated with biometrics");
                try {
                    SdpAuthenticator.getInstance().onBiometricsAuthenticated(selectedUserId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        resetMode();
    }

    public final boolean hasPendingAuthentication() {
        PendingAuthenticated pendingAuthenticated = this.mPendingAuthenticated;
        return pendingAuthenticated != null && this.mUpdateMonitor.isUnlockingWithBiometricAllowed(pendingAuthenticated.isStrongBiometric) && this.mPendingAuthenticated.userId == ((SelectedUserInteractor) this.mSelectedUserInteractor.get()).getSelectedUserId();
    }

    public final boolean isBiometricUnlock() {
        return isWakeAndUnlock() || this.mMode == 5;
    }

    public final boolean isUpdatePossible() {
        return (this.mBiometricToastView == null || this.mKeyguardViewController.isBouncerShowing() || !this.mUpdateMonitor.mDeviceInteractive) ? false : true;
    }

    public final boolean isWakeAndUnlock() {
        int i = this.mMode;
        return i == 1 || i == 2 || i == 6;
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onBiometricAcquired(BiometricSourceType biometricSourceType, int i) {
        BiometricSourceType biometricSourceType2 = BiometricSourceType.FINGERPRINT;
        if (biometricSourceType2 != biometricSourceType || i == 0) {
            BiometricSourceType biometricSourceType3 = BiometricSourceType.FACE;
            if (biometricSourceType3 != biometricSourceType || i == 0) {
                Trace.beginSection("BiometricUnlockController#onBiometricAcquired");
                if (biometricSourceType == biometricSourceType2) {
                    KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
                    if (!keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true)) {
                        android.util.Log.d("BiometricUnlockCtrl", "onBiometricAcquired - show bouncer!! )");
                        this.mKeyguardViewController.showPrimaryBouncer(true);
                        if (!keyguardUpdateMonitor.mDeviceInteractive) {
                            android.util.Log.i("BiometricUnlockCtrl", "onBiometricAcquired( fp wakelock: show bouncer and waking up... ) ");
                            this.mPowerManager.wakeUp(android.os.SystemClock.uptimeMillis(), 4, "android.policy:BIOMETRIC");
                        }
                        Trace.endSection();
                        return;
                    }
                }
                releaseBiometricWakeLock();
                if (this.mStatusBarStateController.isDozing()) {
                    if (this.mLatencyTracker.isEnabled()) {
                        this.mLatencyTracker.onActionStart(biometricSourceType == biometricSourceType3 ? 7 : 2);
                    }
                    this.mWakeLock = this.mPowerManager.newWakeLock(1, "wake-and-unlock:wakelock");
                    Trace.beginSection("acquiring wake-and-unlock");
                    this.mWakeLock.acquire();
                    Trace.endSection();
                    BiometricUnlockLogger biometricUnlockLogger = this.mLogger;
                    biometricUnlockLogger.getClass();
                    biometricUnlockLogger.logBuffer.log("BiometricUnlockLogger", LogLevel.INFO, "biometric acquired, grabbing biometric wakelock", null);
                    this.mHandler.postDelayed(this.mReleaseBiometricWakeLockRunnable, 15000L);
                }
                Trace.endSection();
            }
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z = keyguardStateControllerImpl.mOccluded || this.mDynamicLockMode == 1;
        int i = AnonymousClass5.$SwitchMap$android$hardware$biometrics$BiometricSourceType[biometricSourceType.ordinal()];
        Lazy lazy = this.mSelectedUserInteractor;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        if (i != 1) {
            if (i == 2) {
                if (isUpdatePossible()) {
                    KeyguardBiometricToastView keyguardBiometricToastView = this.mBiometricToastView;
                    KeyguardBiometricToastView.ToastType toastType = KeyguardBiometricToastView.ToastType.FaceAuthenticationFail;
                    boolean userHasTrust = keyguardUpdateMonitor.getUserHasTrust(((SelectedUserInteractor) lazy.get()).getSelectedUserId(false));
                    boolean z2 = keyguardStateControllerImpl.mCanDismissLockScreen;
                    keyguardBiometricToastView.mVibrationUtil = this.mVibrationUtil;
                    keyguardBiometricToastView.update(toastType, "", userHasTrust, z2, false);
                } else {
                    vibrate(biometricSourceType, false);
                }
            }
        } else if (!LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && isUpdatePossible()) {
            if (keyguardUpdateMonitor.getLockoutBiometricAttemptDeadline() > 0) {
                this.mBiometricToastView.update(KeyguardBiometricToastView.ToastType.FingerprintAuthenticationFail, "", false, false, z);
            } else {
                this.mBiometricToastView.update(KeyguardBiometricToastView.ToastType.FingerprintAuthenticationFail, "", keyguardUpdateMonitor.getUserHasTrust(((SelectedUserInteractor) lazy.get()).getSelectedUserId(false)), keyguardStateControllerImpl.mCanDismissLockScreen, z);
            }
        }
        this.mMetricsLogger.write(new LogMaker(1697).setType(11).setSubtype(toSubtype(biometricSourceType)));
        Optional.ofNullable((BiometricUiEvent) BiometricUiEvent.FAILURE_EVENT_BY_SOURCE_TYPE.get(biometricSourceType)).ifPresent(new BiometricUnlockController$$ExternalSyntheticLambda0(this, 4));
        if (this.mLatencyTracker.isEnabled()) {
            this.mLatencyTracker.onActionCancel(biometricSourceType == BiometricSourceType.FACE ? 7 : 2);
        }
        boolean z3 = !keyguardUpdateMonitor.mDeviceInteractive;
        boolean hasVibrator = this.mVibratorHelper.hasVibrator();
        BiometricUnlockLogger biometricUnlockLogger = this.mLogger;
        if (!hasVibrator && (z3 || keyguardUpdateMonitor.mIsDreaming)) {
            biometricUnlockLogger.getClass();
            biometricUnlockLogger.logBuffer.log("BiometricUnlockLogger", LogLevel.DEBUG, "wakeup device on authentication failure (device doesn't have a vibrator)", null);
            BiometricUnlockSource.Companion.getClass();
            startWakeAndUnlock(4, BiometricUnlockSource.Companion.fromBiometricSourceType(biometricSourceType));
        } else if (biometricSourceType == BiometricSourceType.FINGERPRINT && keyguardUpdateMonitor.isUdfpsSupported()) {
            long uptimeMillis = this.mSystemClock.uptimeMillis();
            if (uptimeMillis - this.mLastFpFailureUptimeMillis < this.mConsecutiveFpFailureThreshold) {
                this.mNumConsecutiveFpFailures++;
            } else {
                this.mNumConsecutiveFpFailures = 1;
            }
            this.mLastFpFailureUptimeMillis = uptimeMillis;
            int i2 = this.mNumConsecutiveFpFailures;
            if (i2 >= 3) {
                biometricUnlockLogger.logUdfpsAttemptThresholdMet(i2);
                BiometricUnlockSource.Companion.getClass();
                startWakeAndUnlock(3, BiometricUnlockSource.Companion.fromBiometricSourceType(biometricSourceType));
                UI_EVENT_LOGGER.log(BiometricUiEvent.BIOMETRIC_BOUNCER_SHOWN, this.mSessionTracker.getSessionId(1));
                this.mNumConsecutiveFpFailures = 0;
            }
        }
        releaseBiometricWakeLock();
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
        Trace.beginSection("BiometricUnlockController#onBiometricUnlocked");
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        boolean z2 = keyguardUpdateMonitor.mGoingToSleep;
        KeyguardViewMediator keyguardViewMediator = this.mKeyguardViewMediator;
        BiometricUnlockLogger biometricUnlockLogger = this.mLogger;
        if (z2) {
            biometricUnlockLogger.deferringAuthenticationDueToSleep(i, biometricSourceType, this.mPendingAuthenticated != null);
            this.mPendingAuthenticated = new PendingAuthenticated(i, biometricSourceType, z);
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = keyguardViewMediator.mHelper;
            keyguardViewMediatorHelperImpl.getClass();
            if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                keyguardViewMediatorHelperImpl.removeShowMsg();
            }
            Trace.endSection();
            return;
        }
        this.mBiometricType = biometricSourceType;
        this.mMetricsLogger.write(new LogMaker(1697).setType(10).setSubtype(toSubtype(biometricSourceType)));
        Optional.ofNullable((BiometricUiEvent) BiometricUiEvent.SUCCESS_EVENT_BY_SOURCE_TYPE.get(biometricSourceType)).ifPresent(new BiometricUnlockController$$ExternalSyntheticLambda0(this, 1));
        this.mAuthenticatedBioSourceType = biometricSourceType;
        BiometricSourceType biometricSourceType2 = BiometricSourceType.FACE;
        KeyguardBypassController keyguardBypassController = this.mKeyguardBypassController;
        if (biometricSourceType != biometricSourceType2 || !keyguardBypassController.getLockStayEnabled() || this.mBouncer || isLargeCoverScreen()) {
            BiometricSourceType biometricSourceType3 = this.mAuthenticatedBioSourceType;
            BiometricSourceType biometricSourceType4 = BiometricSourceType.FINGERPRINT;
            if (biometricSourceType3 == biometricSourceType4 && keyguardBypassController.getFpLockStayEnabled() && !this.mBouncer && !isLargeCoverScreen()) {
                android.util.Log.d("BiometricUnlockCtrl", "onBiometricAuthenticated : Fingerpirnt Lock stay is enabled.");
                KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = keyguardViewMediator.mHelper;
                ViewMediatorProvider viewMediatorProvider = keyguardViewMediatorHelperImpl2.viewMediatorProvider;
                if (viewMediatorProvider == null) {
                    viewMediatorProvider = null;
                }
                viewMediatorProvider.playSound.invoke(Integer.valueOf(keyguardViewMediatorHelperImpl2.lockStaySoundId));
                keyguardUpdateMonitor.sendBiometricUnlockState(biometricSourceType4);
            }
        } else {
            android.util.Log.d("BiometricUnlockCtrl", "onBiometricAuthenticated : Lock stay is enabled.");
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl3 = keyguardViewMediator.mHelper;
            ViewMediatorProvider viewMediatorProvider2 = keyguardViewMediatorHelperImpl3.viewMediatorProvider;
            if (viewMediatorProvider2 == null) {
                viewMediatorProvider2 = null;
            }
            viewMediatorProvider2.playSound.invoke(Integer.valueOf(keyguardViewMediatorHelperImpl3.lockStaySoundId));
            keyguardUpdateMonitor.sendBiometricUnlockState(biometricSourceType2);
        }
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z3 = keyguardStateControllerImpl.mOccluded;
        KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.mFastUnlockController;
        if (!z3 && !keyguardBypassController.onBiometricAuthenticated(biometricSourceType, z)) {
            keyguardFastBioUnlockController.reset();
            biometricUnlockLogger.getClass();
            biometricUnlockLogger.logBuffer.log("BiometricUnlockLogger", LogLevel.DEBUG, "onBiometricUnlocked aborted by bypass controller", null);
            return;
        }
        android.util.Log.d("BiometricUnlockCtrl", "onBiometricAuthenticated");
        KeyguardUnlockInfo.setAuthDetail(biometricSourceType);
        KeyguardUnlockInfo.setUnlockTriggerIfNotSet(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_BIO_UNLOCK);
        keyguardFastBioUnlockController.biometricSourceType = biometricSourceType;
        int i2 = AnonymousClass5.$SwitchMap$android$hardware$biometrics$BiometricSourceType[biometricSourceType.ordinal()];
        if (i2 != 1) {
            if (i2 == 2) {
                KeyguardBiometricToastView keyguardBiometricToastView = this.mBiometricToastView;
                if (keyguardBiometricToastView != null && keyguardBiometricToastView.mIsShowing) {
                    keyguardBiometricToastView.dismiss(true);
                }
                sendSALog(SystemUIAnalytics.EID_UNLOCK_BOUNCER, "3");
                vibrate(biometricSourceType, true);
            }
        } else {
            if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isMultifactorAuthEnforced()) {
                android.util.Log.d("BiometricUnlockCtrl", "the fingerprint is authenticated.");
                if (this.mKeyguardViewController.isBouncerShowing()) {
                    this.mKeyguardViewController.reset(false);
                } else {
                    this.mKeyguardViewController.showPrimaryBouncer(true);
                }
                if (keyguardUpdateMonitor.mDeviceInteractive) {
                    return;
                }
                android.util.Log.i("BiometricUnlockCtrl", "fp wakelock: Authenticated, waking up...");
                this.mPowerManager.wakeUp(android.os.SystemClock.uptimeMillis(), 4, "android.policy:BIOMETRIC");
                return;
            }
            if (!LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && isUpdatePossible() && keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true) && (keyguardStateControllerImpl.mOccluded || this.mDynamicLockMode == 1)) {
                this.mBiometricToastView.update(KeyguardBiometricToastView.ToastType.FingerprintAuthenticationSuccess, "", keyguardUpdateMonitor.getUserHasTrust(((SelectedUserInteractor) this.mSelectedUserInteractor.get()).getSelectedUserId(false)), keyguardStateControllerImpl.mCanDismissLockScreen, true);
                sendSALog(SystemUIAnalytics.EID_BACKGROUND_UNLOCK, "2");
            } else {
                sendSALog(SystemUIAnalytics.EID_UNLOCK_BOUNCER, "2");
            }
            vibrate(biometricSourceType, true);
        }
        keyguardFastBioUnlockController.executor.submit(new KeyguardFastBioUnlockController.Task(new BiometricUnlockController$$ExternalSyntheticLambda7(this, 0), "PowerManager#userActivity"));
        startWakeAndUnlock(biometricSourceType, z);
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onBiometricDetected(int i, BiometricSourceType biometricSourceType, boolean z) {
        Trace.beginSection("BiometricUnlockController#onBiometricDetected");
        if (this.mUpdateMonitor.mGoingToSleep) {
            Trace.endSection();
        } else {
            BiometricUnlockSource.Companion.getClass();
            startWakeAndUnlock(3, BiometricUnlockSource.Companion.fromBiometricSourceType(biometricSourceType));
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onBiometricError(int i, String str, BiometricSourceType biometricSourceType) {
        int i2 = AnonymousClass5.$SwitchMap$android$hardware$biometrics$BiometricSourceType[biometricSourceType.ordinal()];
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        Lazy lazy = this.mSelectedUserInteractor;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        if (i2 != 1) {
            if (i2 == 2 && isUpdatePossible() && i != 3 && !keyguardUpdateMonitor.isShortcutLaunchInProgress()) {
                if (i == 1006) {
                    android.util.Log.d("BiometricUnlockCtrl", "onBiometricError : FACE_ERROR_ON_MASK");
                    KeyguardBiometricToastView keyguardBiometricToastView = this.mBiometricToastView;
                    KeyguardBiometricToastView.ToastType toastType = KeyguardBiometricToastView.ToastType.FaceAuthenticationError;
                    boolean userHasTrust = keyguardUpdateMonitor.getUserHasTrust(((SelectedUserInteractor) lazy.get()).getSelectedUserId(false));
                    boolean z = ((KeyguardStateControllerImpl) keyguardStateController).mCanDismissLockScreen;
                    keyguardBiometricToastView.mVibrationUtil = this.mVibrationUtil;
                    keyguardBiometricToastView.update(toastType, str, userHasTrust, z, false);
                } else {
                    this.mBiometricToastView.update(KeyguardBiometricToastView.ToastType.FaceAuthenticationError, str, keyguardUpdateMonitor.getUserHasTrust(((SelectedUserInteractor) lazy.get()).getSelectedUserId(false)), ((KeyguardStateControllerImpl) keyguardStateController).mCanDismissLockScreen, false);
                }
            }
        } else if (!LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && isUpdatePossible() && i != 5 && i != 10) {
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardStateController;
            this.mBiometricToastView.update(KeyguardBiometricToastView.ToastType.FingerprintAuthenticationError, str, keyguardUpdateMonitor.getUserHasTrust(((SelectedUserInteractor) lazy.get()).getSelectedUserId(false)), keyguardStateControllerImpl.mCanDismissLockScreen, keyguardStateControllerImpl.mOccluded || this.mDynamicLockMode == 1);
        }
        this.mMetricsLogger.write(new LogMaker(1697).setType(15).setSubtype(toSubtype(biometricSourceType)).addTaggedData(1741, Integer.valueOf(i)));
        Optional.ofNullable((BiometricUiEvent) BiometricUiEvent.ERROR_EVENT_BY_SOURCE_TYPE.get(biometricSourceType)).ifPresent(new BiometricUnlockController$$ExternalSyntheticLambda0(this, 2));
        BiometricSourceType biometricSourceType2 = BiometricSourceType.FINGERPRINT;
        releaseBiometricWakeLock();
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onBiometricHelp(int i, String str, BiometricSourceType biometricSourceType) {
        if (biometricSourceType.equals(BiometricSourceType.FINGERPRINT) && !TextUtils.isEmpty(str)) {
            String str2 = LsRune.VALUE_SUB_DISPLAY_POLICY;
            vibrate(biometricSourceType, false);
            if (i == 1) {
                str = this.mContext.getString(R.string.kg_fingerprint_acquired_partial);
            } else if (i == 2) {
                str = this.mContext.getString(R.string.kg_fingerprint_acquired_insufficient);
            } else if (i == 3) {
                str = this.mContext.getString(R.string.kg_fingerprint_acquired_image_dirty);
            } else if (i == 5) {
                str = this.mContext.getString(R.string.kg_fingerprint_acquired_too_fast);
            } else if (i == 1001) {
                str = this.mContext.getString(R.string.kg_fingerprint_acquired_too_wet);
            } else if (i == 1003) {
                str = this.mContext.getString(R.string.kg_fingerprint_acquired_light);
            } else if (i == 1004) {
                str = this.mContext.getString(R.string.kg_fingerprint_acquired_tsp_block);
            }
            String str3 = str;
            if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY || !isUpdatePossible() || i == -1) {
                return;
            }
            KeyguardBiometricToastView keyguardBiometricToastView = this.mBiometricToastView;
            KeyguardBiometricToastView.ToastType toastType = KeyguardBiometricToastView.ToastType.FingerprintAuthenticationHelp;
            boolean userHasTrust = this.mUpdateMonitor.getUserHasTrust(((SelectedUserInteractor) this.mSelectedUserInteractor.get()).getSelectedUserId(false));
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
            keyguardBiometricToastView.update(toastType, str3, userHasTrust, keyguardStateControllerImpl.mCanDismissLockScreen, keyguardStateControllerImpl.mOccluded || this.mDynamicLockMode == 1);
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onDlsViewModeChanged(int i) {
        int i2 = this.mDynamicLockMode;
        this.mDynamicLockMode = i;
        if (i2 != 1 || i != 0) {
            updateBackgroundAuthToastForBiometrics();
        } else {
            this.mHandler.postDelayed(new BiometricUnlockController$$ExternalSyntheticLambda7(this, 1), 1500L);
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onKeyguardBouncerFullyShowingChanged(boolean z) {
        this.mBouncer = z;
        updateBackgroundAuthToastForBiometrics();
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onKeyguardVisibilityChanged(boolean z) {
        updateBackgroundAuthToastForBiometrics();
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onRefreshBatteryInfo(BatteryStatus batteryStatus) {
        this.mUpdateMonitor.getKeyguardBatteryStatus().getClass();
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onStartedGoingToSleep(int i) {
        this.mIsStartedGoingToSleep = true;
        updateBackgroundAuthToastForBiometrics();
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onStartedWakingUp() {
        this.mIsStartedGoingToSleep = false;
        updateBackgroundAuthToastForBiometrics();
    }

    public final void releaseBiometricWakeLock() {
        if (this.mWakeLock != null) {
            this.mHandler.removeCallbacks(this.mReleaseBiometricWakeLockRunnable);
            BiometricUnlockLogger biometricUnlockLogger = this.mLogger;
            biometricUnlockLogger.getClass();
            biometricUnlockLogger.logBuffer.log("BiometricUnlockLogger", LogLevel.INFO, "releasing biometric wakelock", null);
            this.mWakeLock.release();
            this.mWakeLock = null;
        }
    }

    public final void resetMode() {
        this.mMode = 0;
        this.mBiometricType = null;
        NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController;
        NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
        if (notificationShadeWindowState.forceDozeBrightness) {
            notificationShadeWindowState.forceDozeBrightness = false;
            notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
        }
        Iterator it = ((HashSet) this.mBiometricUnlockEventsListeners).iterator();
        while (it.hasNext()) {
            ((BiometricUnlockEventsListener) it.next()).onResetMode();
        }
        this.mNumConsecutiveFpFailures = 0;
        this.mLastFpFailureUptimeMillis = 0L;
    }

    public final void sendSALog(String str, String str2) {
        String str3 = this.mBouncer ? "102" : "101";
        if (LsRune.SECURITY_SUB_DISPLAY_COVER && !((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) {
            str3 = str3.concat("_S");
        }
        SystemUIAnalytics.sendEventLog(str3, str, str2);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x011e  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0133  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startWakeAndUnlock(final int r12, final com.android.systemui.keyguard.shared.model.BiometricUnlockSource r13) {
        /*
            Method dump skipped, instructions count: 348
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.BiometricUnlockController.startWakeAndUnlock(int, com.android.systemui.keyguard.shared.model.BiometricUnlockSource):void");
    }

    public final void updateBackgroundAuthToast(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("update biometric toast = ", "BiometricUnlockCtrl", z);
        if (this.mWindowLp == null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-2, -2, 2228, 16778008, -3);
            this.mWindowLp = layoutParams;
            layoutParams.gravity = 49;
            layoutParams.privateFlags |= 16;
            layoutParams.samsungFlags |= 131072;
            layoutParams.setTitle("KeyguardBiometricToastView");
        }
        if (!z) {
            KeyguardBiometricToastView keyguardBiometricToastView = this.mBiometricToastView;
            if (keyguardBiometricToastView == null || !keyguardBiometricToastView.mIsShowing) {
                return;
            }
            keyguardBiometricToastView.dismiss(true);
            return;
        }
        if (this.mUpdateMonitor.isAuthenticatedWithBiometric(((SelectedUserInteractor) this.mSelectedUserInteractor.get()).getSelectedUserId(false))) {
            android.util.Log.d("BiometricUnlockCtrl", "Already unlocked by biometric");
            return;
        }
        KeyguardBiometricToastView keyguardBiometricToastView2 = this.mBiometricToastView;
        if (keyguardBiometricToastView2 != null) {
            if (keyguardBiometricToastView2.mIsShowing) {
                android.util.Log.d("BiometricUnlockCtrl", "Biometric toast already showing");
                return;
            } else {
                this.mWindowManager.removeView(keyguardBiometricToastView2);
                this.mBiometricToastView = null;
            }
        }
        KeyguardBiometricToastView keyguardBiometricToastView3 = (KeyguardBiometricToastView) View.inflate(this.mContext, R.layout.keyguard_biometric_toast_view, null);
        this.mBiometricToastView = keyguardBiometricToastView3;
        keyguardBiometricToastView3.mBiometricToastViewStateUpdater = new BiometricUnlockController$$ExternalSyntheticLambda0(this, 3);
        this.mWindowManager.addView(keyguardBiometricToastView3, this.mWindowLp);
    }

    public final void updateBackgroundAuthToastForBiometrics() {
        KeyguardBiometricToastView keyguardBiometricToastView;
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        if (keyguardStateController == null) {
            return;
        }
        if ((this.mBouncer || this.mIsStartedGoingToSleep || !(((KeyguardStateControllerImpl) keyguardStateController).mShowing || this.mDynamicLockMode == 1)) && (keyguardBiometricToastView = this.mBiometricToastView) != null && keyguardBiometricToastView.mIsShowing) {
            updateBackgroundAuthToast(false);
            return;
        }
        if (((KeyguardStateControllerImpl) keyguardStateController).mShowing) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
            if (keyguardUpdateMonitor.isFingerprintDetectionRunning() || keyguardUpdateMonitor.isFaceDetectionRunning()) {
                updateBackgroundAuthToast(true);
            }
        }
    }

    public final void vibrate(BiometricSourceType biometricSourceType, boolean z) {
        VibrationUtil vibrationUtil = this.mVibrationUtil;
        if (z) {
            vibrationUtil.playVibration(1);
        } else {
            vibrationUtil.playVibration(biometricSourceType.equals(BiometricSourceType.FINGERPRINT) ? 114 : 5);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface BiometricUnlockEventsListener {
        default void onResetMode() {
        }

        default void onModeChanged(int i) {
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onKeyguardBouncerStateChanged(boolean z) {
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0094, code lost:
    
        if (r19 != false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0096, code lost:
    
        r10 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00a3, code lost:
    
        if (r19 != false) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00a5, code lost:
    
        r10 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00b3, code lost:
    
        if (r19 != false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00ba, code lost:
    
        if (r19 != false) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00bc, code lost:
    
        r10 = 6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00d9, code lost:
    
        if (r21.mKeyguardViewController.isBouncerShowing() == false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x013a, code lost:
    
        if (r18 != false) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0140, code lost:
    
        if (r18 != false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0155, code lost:
    
        if (r18 != false) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x017d, code lost:
    
        if (r18 != false) goto L35;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void startWakeAndUnlock(android.hardware.biometrics.BiometricSourceType r22, boolean r23) {
        /*
            Method dump skipped, instructions count: 398
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.phone.BiometricUnlockController.startWakeAndUnlock(android.hardware.biometrics.BiometricSourceType, boolean):void");
    }
}
