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
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.KeyguardViewController;
import com.android.keyguard.SecLockIconViewController;
import com.android.keyguard.biometrics.KeyguardBiometricToastView;
import com.android.keyguard.logging.BiometricUnlockLogger;
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
import com.android.systemui.keyguard.VisibilityController;
import com.android.systemui.keyguard.WakefulnessLifecycle;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.DisplayTrackerImpl;
import com.android.systemui.shade.NotificationPanelViewController;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowState;
import com.android.systemui.statusbar.NotificationMediaManager;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.time.SystemClock;
import com.android.systemui.util.time.SystemClockImpl;
import com.android.systemui.vibrate.VibrationUtil;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.sdp.internal.SdpAuthenticator;
import com.sec.ims.configuration.DATA;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BiometricUnlockController extends KeyguardUpdateMonitorCallback implements Dumpable {
    public static final UiEventLogger UI_EVENT_LOGGER = new UiEventLoggerImpl();
    public BiometricSourceType mAuthenticatedBioSourceType;
    public KeyguardBiometricToastView mBiometricToastView;
    public BiometricSourceType mBiometricType;
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
    public KeyguardViewController mKeyguardViewController;
    public final KeyguardViewMediator mKeyguardViewMediator;
    public long mLastFpFailureUptimeMillis;
    public final LatencyTracker mLatencyTracker;
    public final BiometricUnlockLogger mLogger;
    public final NotificationMediaManager mMediaManager;
    public final MetricsLogger mMetricsLogger;
    public int mMode;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public int mNumConsecutiveFpFailures;
    public final PowerManager mPowerManager;
    public final C29974 mScreenObserver;
    public final ScreenOffAnimationController mScreenOffAnimationController;
    public final Lazy mSecLockIconViewControllerLazy;
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
    public final RunnableC29941 mReleaseBiometricWakeLockRunnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.BiometricUnlockController.1
        @Override // java.lang.Runnable
        public final void run() {
            BiometricUnlockLogger biometricUnlockLogger = BiometricUnlockController.this.mLogger;
            biometricUnlockLogger.getClass();
            LogBuffer.log$default(biometricUnlockLogger.logBuffer, "BiometricUnlockLogger", LogLevel.INFO, "biometric wakelock: TIMEOUT!!", null, 8, null);
            BiometricUnlockController.this.releaseBiometricWakeLock();
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.BiometricUnlockController$3 */
    public final class C29963 implements WakefulnessLifecycle.Observer {
        public C29963() {
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onFinishedGoingToSleep() {
            Trace.beginSection("BiometricUnlockController#onFinishedGoingToSleep");
            BiometricUnlockController biometricUnlockController = BiometricUnlockController.this;
            if (biometricUnlockController.mPendingAuthenticated != null) {
                LogBuffer.log$default(biometricUnlockController.mLogger.logBuffer, "BiometricUnlockLogger", LogLevel.DEBUG, "onFinishedGoingToSleep with pendingAuthenticated != null", null, 8, null);
                BiometricUnlockController biometricUnlockController2 = BiometricUnlockController.this;
                final PendingAuthenticated pendingAuthenticated = biometricUnlockController2.mPendingAuthenticated;
                biometricUnlockController2.mHandler.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.BiometricUnlockController$3$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        BiometricUnlockController.C29963 c29963 = BiometricUnlockController.C29963.this;
                        BiometricUnlockController.PendingAuthenticated pendingAuthenticated2 = pendingAuthenticated;
                        BiometricUnlockController.this.onBiometricAuthenticated(pendingAuthenticated2.userId, pendingAuthenticated2.biometricSourceType, pendingAuthenticated2.isStrongBiometric);
                    }
                });
                BiometricUnlockController.this.mPendingAuthenticated = null;
            }
            Trace.endSection();
        }

        @Override // com.android.systemui.keyguard.WakefulnessLifecycle.Observer
        public final void onStartedGoingToSleep() {
            BiometricUnlockController biometricUnlockController = BiometricUnlockController.this;
            UiEventLogger uiEventLogger = BiometricUnlockController.UI_EVENT_LOGGER;
            biometricUnlockController.resetMode();
            BiometricUnlockController biometricUnlockController2 = BiometricUnlockController.this;
            biometricUnlockController2.mFadedAwayAfterWakeAndUnlock = false;
            biometricUnlockController2.mPendingAuthenticated = null;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.phone.BiometricUnlockController$5 */
    public abstract /* synthetic */ class AbstractC29985 {
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
      (r11v0 com.android.systemui.statusbar.phone.BiometricUnlockController$BiometricUiEvent) from 0x00ae: INVOKE 
      (wrap:android.hardware.biometrics.BiometricSourceType:0x00a4: SGET  A[WRAPPED] (LINE:165) android.hardware.biometrics.BiometricSourceType.FINGERPRINT android.hardware.biometrics.BiometricSourceType)
      (r11v0 com.android.systemui.statusbar.phone.BiometricUnlockController$BiometricUiEvent)
      (wrap:android.hardware.biometrics.BiometricSourceType:0x00a6: SGET  A[WRAPPED] (LINE:167) android.hardware.biometrics.BiometricSourceType.FACE android.hardware.biometrics.BiometricSourceType)
      (r14v0 com.android.systemui.statusbar.phone.BiometricUnlockController$BiometricUiEvent)
      (wrap:android.hardware.biometrics.BiometricSourceType:0x00a8: SGET  A[WRAPPED] (LINE:169) android.hardware.biometrics.BiometricSourceType.IRIS android.hardware.biometrics.BiometricSourceType)
      (r9v0 com.android.systemui.statusbar.phone.BiometricUnlockController$BiometricUiEvent)
     STATIC call: java.util.Map.of(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object):java.util.Map A[MD:<K, V>:(K, V, K, V, K, V):java.util.Map<K, V> (c), WRAPPED] (LINE:175)
    	at jadx.core.utils.InsnRemover.removeSsaVar(InsnRemover.java:162)
    	at jadx.core.utils.InsnRemover.unbindResult(InsnRemover.java:127)
    	at jadx.core.utils.InsnRemover.lambda$unbindInsns$1(InsnRemover.java:99)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1597)
    	at jadx.core.utils.InsnRemover.unbindInsns(InsnRemover.java:98)
    	at jadx.core.utils.InsnRemover.removeAllAndUnbind(InsnRemover.java:252)
    	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:180)
    	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:100)
     */
    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            ERROR_EVENT_BY_SOURCE_TYPE = Map.of(BiometricSourceType.FINGERPRINT, r13, BiometricSourceType.FACE, r10, BiometricSourceType.IRIS, r7);
            SUCCESS_EVENT_BY_SOURCE_TYPE = Map.of(BiometricSourceType.FINGERPRINT, r11, BiometricSourceType.FACE, r14, BiometricSourceType.IRIS, r9);
            FAILURE_EVENT_BY_SOURCE_TYPE = Map.of(BiometricSourceType.FINGERPRINT, r12, BiometricSourceType.FACE, r15, BiometricSourceType.IRIS, r8);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.statusbar.phone.BiometricUnlockController$1] */
    /* JADX WARN: Type inference failed for: r3v0, types: [com.android.systemui.statusbar.phone.BiometricUnlockController$4, java.lang.Object] */
    public BiometricUnlockController(Lazy lazy, DozeScrimController dozeScrimController, KeyguardViewMediator keyguardViewMediator, NotificationShadeWindowController notificationShadeWindowController, KeyguardStateController keyguardStateController, Handler handler, KeyguardUpdateMonitor keyguardUpdateMonitor, Resources resources, KeyguardBypassController keyguardBypassController, MetricsLogger metricsLogger, DumpManager dumpManager, PowerManager powerManager, BiometricUnlockLogger biometricUnlockLogger, NotificationMediaManager notificationMediaManager, WakefulnessLifecycle wakefulnessLifecycle, ScreenLifecycle screenLifecycle, AuthController authController, StatusBarStateController statusBarStateController, SessionTracker sessionTracker, LatencyTracker latencyTracker, ScreenOffAnimationController screenOffAnimationController, VibratorHelper vibratorHelper, SystemClock systemClock, KeyguardFastBioUnlockController keyguardFastBioUnlockController, VibrationUtil vibrationUtil, WindowManager windowManager, Context context) {
        C29963 c29963 = new C29963();
        this.mWakefulnessObserver = c29963;
        ?? r3 = new ScreenLifecycle.Observer() { // from class: com.android.systemui.statusbar.phone.BiometricUnlockController.4
            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public final void onScreenTurnedOn() {
                if (((SettingsHelper) Dependency.get(SettingsHelper.class)).isEnabledWof() && BiometricUnlockController.this.mUpdateMonitor.isFingerprintDisabledWithBadQuality()) {
                    Toast.makeText(BiometricUnlockController.this.mContext, R.string.kg_finger_print_bad_quality_error_message, 1).show();
                    BiometricUnlockController.this.mUpdateMonitor.clearFingerBadQualityCounts();
                }
                BiometricUnlockController biometricUnlockController = BiometricUnlockController.this;
                UiEventLogger uiEventLogger = BiometricUnlockController.UI_EVENT_LOGGER;
                biometricUnlockController.getClass();
            }
        };
        this.mScreenObserver = r3;
        this.mPowerManager = powerManager;
        this.mUpdateMonitor = keyguardUpdateMonitor;
        this.mMediaManager = notificationMediaManager;
        this.mLatencyTracker = latencyTracker;
        wakefulnessLifecycle.addObserver(c29963);
        screenLifecycle.addObserver(r3);
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
        this.mSecLockIconViewControllerLazy = lazy;
        this.mVibrationUtil = vibrationUtil;
        this.mWindowManager = windowManager;
        this.mContext = context;
        this.mFastUnlockController = keyguardFastBioUnlockController;
        String name = BiometricUnlockController.class.getName();
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, name, this);
    }

    public static boolean isLargeCoverScreen() {
        return LsRune.SUPPORT_LARGE_FRONT_SUB_DISPLAY && !((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened;
    }

    public static int toSubtype(BiometricSourceType biometricSourceType) {
        int i = AbstractC29985.$SwitchMap$android$hardware$biometrics$BiometricSourceType[biometricSourceType.ordinal()];
        if (i == 1) {
            return 0;
        }
        if (i != 2) {
            return i != 3 ? 3 : 2;
        }
        return 1;
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
            ((SystemClockImpl) this.mSystemClock).getClass();
            printWriter.println(android.os.SystemClock.uptimeMillis() - this.mLastFpFailureUptimeMillis);
        }
    }

    public final void finishKeyguardFadingAway() {
        final boolean z = true;
        if (isWakeAndUnlock()) {
            this.mFadedAwayAfterWakeAndUnlock = true;
        }
        Log.m138d("BiometricUnlockCtrl", "finishKeyguardFadingAway");
        if (isBiometricUnlock()) {
            this.mUpdateMonitor.sendBiometricUnlockState(this.mAuthenticatedBioSourceType);
        }
        final KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = this.mKeyguardViewMediator.mHelper;
        Lazy lazy = keyguardViewMediatorHelperImpl.biometricUnlockControllerLazy;
        if (!((BiometricUnlockController) lazy.get()).isBiometricUnlock()) {
            BiometricUnlockController biometricUnlockController = (BiometricUnlockController) lazy.get();
            if (!(biometricUnlockController.isWakeAndUnlock() || biometricUnlockController.mFadedAwayAfterWakeAndUnlock) && ((BiometricUnlockController) lazy.get()).mMode != 7) {
                z = false;
            }
        }
        keyguardViewMediatorHelperImpl.uiBgExecutor.execute(new Runnable() { // from class: com.android.systemui.keyguard.KeyguardViewMediatorHelperImpl$onSdpUnlocked$1
            @Override // java.lang.Runnable
            public final void run() {
                KnoxStateMonitor knoxStateMonitor = KeyguardViewMediatorHelperImpl.this.knoxStateMonitor;
                boolean z2 = z;
                ((KnoxStateMonitorImpl) knoxStateMonitor).getClass();
                int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                if (!SemPersonaManager.isDoEnabled(currentUser) || !z2) {
                    ListPopupWindow$$ExternalSyntheticOutline0.m10m("unlockSdp :: Maybe keyguard hidden as user ", currentUser, "KnoxStateMonitorImpl");
                    return;
                }
                android.util.Log.d("KnoxStateMonitorImpl", "unlockSdp :: Device Owner has been authenticated with biometrics");
                try {
                    SdpAuthenticator.getInstance().onBiometricsAuthenticated(currentUser);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        resetMode();
    }

    public final boolean hasPendingAuthentication() {
        PendingAuthenticated pendingAuthenticated = this.mPendingAuthenticated;
        return pendingAuthenticated != null && this.mUpdateMonitor.isUnlockingWithBiometricAllowed(pendingAuthenticated.isStrongBiometric) && this.mPendingAuthenticated.userId == KeyguardUpdateMonitor.getCurrentUser();
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
        if (BiometricSourceType.FINGERPRINT != biometricSourceType || i == 0) {
            if (BiometricSourceType.FACE != biometricSourceType || i == 0) {
                Trace.beginSection("BiometricUnlockController#onBiometricAcquired");
                BiometricSourceType biometricSourceType2 = BiometricSourceType.FINGERPRINT;
                PowerManager powerManager = this.mPowerManager;
                if (biometricSourceType == biometricSourceType2) {
                    KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
                    if (!keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true)) {
                        android.util.Log.d("BiometricUnlockCtrl", "onBiometricAcquired - show bouncer!! )");
                        this.mKeyguardViewController.showPrimaryBouncer(true);
                        if (!keyguardUpdateMonitor.mDeviceInteractive) {
                            android.util.Log.i("BiometricUnlockCtrl", "onBiometricAcquired( fp wakelock: show bouncer and waking up... ) ");
                            powerManager.wakeUp(android.os.SystemClock.uptimeMillis(), 4, "android.policy:BIOMETRIC");
                        }
                        Trace.endSection();
                        return;
                    }
                }
                releaseBiometricWakeLock();
                if (this.mStatusBarStateController.isDozing()) {
                    LatencyTracker latencyTracker = this.mLatencyTracker;
                    if (latencyTracker.isEnabled()) {
                        latencyTracker.onActionStart(biometricSourceType == BiometricSourceType.FACE ? 7 : 2);
                    }
                    this.mWakeLock = powerManager.newWakeLock(1, "wake-and-unlock:wakelock");
                    Trace.beginSection("acquiring wake-and-unlock");
                    this.mWakeLock.acquire();
                    Trace.endSection();
                    BiometricUnlockLogger biometricUnlockLogger = this.mLogger;
                    biometricUnlockLogger.getClass();
                    LogBuffer.log$default(biometricUnlockLogger.logBuffer, "BiometricUnlockLogger", LogLevel.INFO, "biometric acquired, grabbing biometric wakelock", null, 8, null);
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
        int i = AbstractC29985.$SwitchMap$android$hardware$biometrics$BiometricSourceType[biometricSourceType.ordinal()];
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        if (i != 1) {
            if (i == 2) {
                if (isUpdatePossible()) {
                    this.mBiometricToastView.update(KeyguardBiometricToastView.ToastType.FaceAuthenticationFail, "", keyguardUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser()), keyguardStateControllerImpl.mCanDismissLockScreen, false);
                }
                vibrate(biometricSourceType, false);
            }
        } else if (!LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && isUpdatePossible()) {
            if (keyguardUpdateMonitor.getLockoutBiometricAttemptDeadline() > 0) {
                this.mBiometricToastView.update(KeyguardBiometricToastView.ToastType.FingerprintAuthenticationFail, "", false, false, z);
            } else {
                this.mBiometricToastView.update(KeyguardBiometricToastView.ToastType.FingerprintAuthenticationFail, "", keyguardUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser()), keyguardStateControllerImpl.mCanDismissLockScreen, z);
            }
        }
        this.mMetricsLogger.write(new LogMaker(1697).setType(11).setSubtype(toSubtype(biometricSourceType)));
        Optional.ofNullable((BiometricUiEvent) BiometricUiEvent.FAILURE_EVENT_BY_SOURCE_TYPE.get(biometricSourceType)).ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.phone.BiometricUnlockController$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BiometricUnlockController biometricUnlockController = BiometricUnlockController.this;
                BiometricUnlockController.UI_EVENT_LOGGER.log((BiometricUnlockController.BiometricUiEvent) obj, biometricUnlockController.mSessionTracker.getSessionId(1));
            }
        });
        LatencyTracker latencyTracker = this.mLatencyTracker;
        if (latencyTracker.isEnabled()) {
            latencyTracker.onActionCancel(biometricSourceType == BiometricSourceType.FACE ? 7 : 2);
        }
        boolean hasVibrator = this.mVibratorHelper.hasVibrator();
        BiometricUnlockLogger biometricUnlockLogger = this.mLogger;
        if (!hasVibrator && (!keyguardUpdateMonitor.mDeviceInteractive || keyguardUpdateMonitor.mIsDreaming)) {
            biometricUnlockLogger.getClass();
            LogBuffer.log$default(biometricUnlockLogger.logBuffer, "BiometricUnlockLogger", LogLevel.DEBUG, "wakeup device on authentication failure (device doesn't have a vibrator)", null, 8, null);
        } else if (biometricSourceType == BiometricSourceType.FINGERPRINT && keyguardUpdateMonitor.isUdfpsSupported()) {
            ((SystemClockImpl) this.mSystemClock).getClass();
            long uptimeMillis = android.os.SystemClock.uptimeMillis();
            if (uptimeMillis - this.mLastFpFailureUptimeMillis < this.mConsecutiveFpFailureThreshold) {
                this.mNumConsecutiveFpFailures++;
            } else {
                this.mNumConsecutiveFpFailures = 1;
            }
            this.mLastFpFailureUptimeMillis = uptimeMillis;
            int i2 = this.mNumConsecutiveFpFailures;
            if (i2 >= 3) {
                biometricUnlockLogger.logUdfpsAttemptThresholdMet(i2);
                startWakeAndUnlock(3);
                UI_EVENT_LOGGER.log(BiometricUiEvent.BIOMETRIC_BOUNCER_SHOWN, this.mSessionTracker.getSessionId(1));
                this.mNumConsecutiveFpFailures = 0;
            }
        }
        releaseBiometricWakeLock();
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x0178  */
    /* JADX WARN: Removed duplicated region for block: B:42:? A[RETURN, SYNTHETIC] */
    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
        Optional.ofNullable((BiometricUiEvent) BiometricUiEvent.SUCCESS_EVENT_BY_SOURCE_TYPE.get(biometricSourceType)).ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.phone.BiometricUnlockController$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BiometricUnlockController biometricUnlockController = BiometricUnlockController.this;
                BiometricUnlockController.UI_EVENT_LOGGER.log((BiometricUnlockController.BiometricUiEvent) obj, biometricUnlockController.mSessionTracker.getSessionId(1));
            }
        });
        this.mAuthenticatedBioSourceType = biometricSourceType;
        BiometricSourceType biometricSourceType2 = BiometricSourceType.FACE;
        KeyguardBypassController keyguardBypassController = this.mKeyguardBypassController;
        if (biometricSourceType == biometricSourceType2 && keyguardBypassController.getLockStayEnabled() && !this.mBouncer && !isLargeCoverScreen()) {
            android.util.Log.d("BiometricUnlockCtrl", "onBiometricAuthenticated : Lock stay is enabled.");
            KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl2 = keyguardViewMediator.mHelper;
            keyguardViewMediatorHelperImpl2.getViewMediatorProvider().playSound.invoke(Integer.valueOf(keyguardViewMediatorHelperImpl2.lockStaySoundId));
            keyguardUpdateMonitor.sendBiometricUnlockState(BiometricSourceType.FACE);
        }
        KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
        boolean z3 = keyguardStateControllerImpl.mOccluded || keyguardBypassController.onBiometricAuthenticated(biometricSourceType, z);
        KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.mFastUnlockController;
        if (!z3) {
            keyguardFastBioUnlockController.reset();
            biometricUnlockLogger.getClass();
            LogBuffer.log$default(biometricUnlockLogger.logBuffer, "BiometricUnlockLogger", LogLevel.DEBUG, "onBiometricUnlocked aborted by bypass controller", null, 8, null);
            return;
        }
        android.util.Log.d("BiometricUnlockCtrl", "onBiometricAuthenticated");
        KeyguardUnlockInfo.setAuthDetail(biometricSourceType);
        KeyguardUnlockInfo.setUnlockTriggerIfNotSet(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_BIO_UNLOCK);
        keyguardFastBioUnlockController.biometricSourceType = biometricSourceType;
        int i2 = AbstractC29985.$SwitchMap$android$hardware$biometrics$BiometricSourceType[biometricSourceType.ordinal()];
        if (i2 != 1) {
            if (i2 == 2) {
                KeyguardBiometricToastView keyguardBiometricToastView = this.mBiometricToastView;
                if (keyguardBiometricToastView != null && keyguardBiometricToastView.mIsShowing) {
                    keyguardBiometricToastView.dismiss(true);
                }
                sendSALog("1032", DATA.DM_FIELD_INDEX.PUBLIC_USER_ID);
                vibrate(biometricSourceType, true);
            }
        } else {
            if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class))).isMultifactorAuthEnforced()) {
                android.util.Log.d("BiometricUnlockCtrl", "the fingerprint is authenticated.");
                if (this.mKeyguardViewController.isBouncerShowing()) {
                    this.mKeyguardViewController.reset(false);
                } else {
                    this.mKeyguardViewController.showPrimaryBouncer(true);
                }
                if (!keyguardUpdateMonitor.mDeviceInteractive) {
                    android.util.Log.i("BiometricUnlockCtrl", "fp wakelock: Authenticated, waking up...");
                    this.mPowerManager.wakeUp(android.os.SystemClock.uptimeMillis(), 4, "android.policy:BIOMETRIC");
                }
                if (r8) {
                    startWakeAndUnlock(biometricSourceType, z);
                    return;
                }
                return;
            }
            if (!LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && isUpdatePossible() && keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true) && (keyguardStateControllerImpl.mOccluded || this.mDynamicLockMode == 1)) {
                this.mBiometricToastView.update(KeyguardBiometricToastView.ToastType.FingerprintAuthenticationSuccess, "", keyguardUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser()), keyguardStateControllerImpl.mCanDismissLockScreen, true);
                sendSALog("1033", "2");
            } else {
                sendSALog("1032", "2");
            }
            vibrate(biometricSourceType, true);
        }
        keyguardFastBioUnlockController.executor.submit(new KeyguardFastBioUnlockController.Task(new Runnable() { // from class: com.android.systemui.statusbar.phone.BiometricUnlockController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                BiometricUnlockController.this.mKeyguardViewMediator.userActivity();
            }
        }, "PowerManager#userActivity"));
        r8 = false;
        if (r8) {
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onBiometricDetected() {
        Trace.beginSection("BiometricUnlockController#onBiometricDetected");
        if (this.mUpdateMonitor.mGoingToSleep) {
            Trace.endSection();
        } else {
            startWakeAndUnlock(3);
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onBiometricError(int i, String str, BiometricSourceType biometricSourceType) {
        int i2 = AbstractC29985.$SwitchMap$android$hardware$biometrics$BiometricSourceType[biometricSourceType.ordinal()];
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        boolean z = true;
        if (i2 != 1) {
            if (i2 == 2 && isUpdatePossible() && i != 3 && !keyguardUpdateMonitor.isShortcutLaunchInProgress()) {
                this.mBiometricToastView.update(KeyguardBiometricToastView.ToastType.FaceAuthenticationError, str, keyguardUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser()), ((KeyguardStateControllerImpl) keyguardStateController).mCanDismissLockScreen, false);
            }
        } else if (!LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && isUpdatePossible() && i != 5 && i != 10) {
            KeyguardBiometricToastView keyguardBiometricToastView = this.mBiometricToastView;
            KeyguardBiometricToastView.ToastType toastType = KeyguardBiometricToastView.ToastType.FingerprintAuthenticationError;
            boolean userHasTrust = keyguardUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser());
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardStateController;
            boolean z2 = keyguardStateControllerImpl.mCanDismissLockScreen;
            if (!keyguardStateControllerImpl.mOccluded && this.mDynamicLockMode != 1) {
                z = false;
            }
            keyguardBiometricToastView.update(toastType, str, userHasTrust, z2, z);
        }
        this.mMetricsLogger.write(new LogMaker(1697).setType(15).setSubtype(toSubtype(biometricSourceType)).addTaggedData(1741, Integer.valueOf(i)));
        Optional.ofNullable((BiometricUiEvent) BiometricUiEvent.ERROR_EVENT_BY_SOURCE_TYPE.get(biometricSourceType)).ifPresent(new Consumer() { // from class: com.android.systemui.statusbar.phone.BiometricUnlockController$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BiometricUnlockController biometricUnlockController = BiometricUnlockController.this;
                BiometricUnlockController.UI_EVENT_LOGGER.log((BiometricUnlockController.BiometricUiEvent) obj, biometricUnlockController.mSessionTracker.getSessionId(1));
            }
        });
        BiometricSourceType biometricSourceType2 = BiometricSourceType.FINGERPRINT;
        releaseBiometricWakeLock();
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onBiometricHelp(int i, String str, BiometricSourceType biometricSourceType) {
        if (biometricSourceType.equals(BiometricSourceType.FINGERPRINT) && !TextUtils.isEmpty(str)) {
            String str2 = LsRune.VALUE_CONFIG_CARRIER_TEXT_POLICY;
            vibrate(biometricSourceType, false);
            Context context = this.mContext;
            if (i == 1) {
                str = context.getString(R.string.kg_fingerprint_acquired_partial);
            } else if (i == 2) {
                str = context.getString(R.string.kg_fingerprint_acquired_insufficient);
            } else if (i == 3) {
                str = context.getString(R.string.kg_fingerprint_acquired_image_dirty);
            } else if (i == 5) {
                str = context.getString(R.string.kg_fingerprint_acquired_too_fast);
            } else if (i == 1001) {
                str = context.getString(R.string.kg_fingerprint_acquired_too_wet);
            } else if (i == 1003) {
                str = context.getString(R.string.kg_fingerprint_acquired_light);
            } else if (i == 1004) {
                str = context.getString(R.string.kg_fingerprint_acquired_tsp_block);
            }
            String str3 = str;
            if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY || !isUpdatePossible() || i == -1) {
                return;
            }
            KeyguardBiometricToastView keyguardBiometricToastView = this.mBiometricToastView;
            KeyguardBiometricToastView.ToastType toastType = KeyguardBiometricToastView.ToastType.FingerprintAuthenticationHelp;
            boolean userHasTrust = this.mUpdateMonitor.getUserHasTrust(KeyguardUpdateMonitor.getCurrentUser());
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
            this.mHandler.postDelayed(new Runnable() { // from class: com.android.systemui.statusbar.phone.BiometricUnlockController$$ExternalSyntheticLambda7
                @Override // java.lang.Runnable
                public final void run() {
                    BiometricUnlockController.this.updateBackgroundAuthToastForBiometrics();
                }
            }, 1500L);
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
    public final void onRefreshBatteryInfo() {
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
            LogBuffer.log$default(biometricUnlockLogger.logBuffer, "BiometricUnlockLogger", LogLevel.INFO, "releasing biometric wakelock", null, 8, null);
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
        String str3 = this.mBouncer ? DATA.DM_FIELD_INDEX.VOLTE_DOMAIN_UI_SHOW : DATA.DM_FIELD_INDEX.UT_APN_NAME;
        if (LsRune.SECURITY_SUB_DISPLAY_COVER && !((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) {
            str3 = str3.concat("_S");
        }
        SystemUIAnalytics.sendEventLog(str3, str, str2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:37:0x009c, code lost:
    
        if (r3 != 6) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00cb, code lost:
    
        if ((com.android.systemui.LsRune.AOD_FULLSCREEN && r1.settingsHelper.isAODShown() && r1.aodAmbientWallpaperHelper.isAODFullScreenMode()) != false) goto L36;
     */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0215  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x022a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void startWakeAndUnlock(final int i) {
        Log.m141i("BiometricUnlockCtrl", LogUtil.getMsg("startWakeAndUnlock(%d)", Integer.valueOf(i)));
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        final boolean z = keyguardUpdateMonitor.mDeviceInteractive;
        this.mMode = i;
        NotificationShadeWindowController notificationShadeWindowController = this.mNotificationShadeWindowController;
        if (i == 2) {
            NotificationShadeWindowControllerImpl notificationShadeWindowControllerImpl = (NotificationShadeWindowControllerImpl) notificationShadeWindowController;
            NotificationShadeWindowState notificationShadeWindowState = notificationShadeWindowControllerImpl.mCurrentState;
            if (!notificationShadeWindowState.forceDozeBrightness) {
                notificationShadeWindowState.forceDozeBrightness = true;
                notificationShadeWindowControllerImpl.apply(notificationShadeWindowState);
            }
        }
        Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.BiometricUnlockController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                BiometricUnlockController biometricUnlockController = BiometricUnlockController.this;
                boolean z2 = z;
                int i2 = i;
                biometricUnlockController.getClass();
                if (!z2 || (i2 == 6 && biometricUnlockController.mUpdateMonitor.mIsDreaming)) {
                    if (LsRune.AOD_FULLSCREEN) {
                        KeyguardViewMediatorHelperImpl keyguardViewMediatorHelperImpl = biometricUnlockController.mKeyguardViewMediator.mHelper;
                        if (i2 != 1) {
                            keyguardViewMediatorHelperImpl.getClass();
                        } else if (keyguardViewMediatorHelperImpl.unlockedScreenOffAnimationController.isAnimationPlaying() && ((Boolean) keyguardViewMediatorHelperImpl.getViewMediatorProvider().hasPendingLock.invoke()).booleanValue()) {
                            keyguardViewMediatorHelperImpl.getViewMediatorProvider().resetPendingLock.invoke();
                        }
                    }
                    BiometricUnlockLogger biometricUnlockLogger = biometricUnlockController.mLogger;
                    biometricUnlockLogger.getClass();
                    LogBuffer.log$default(biometricUnlockLogger.logBuffer, "BiometricUnlockLogger", LogLevel.INFO, "bio wakelock: Authenticated, waking up...", null, 8, null);
                    ((SystemClockImpl) biometricUnlockController.mSystemClock).getClass();
                    biometricUnlockController.mPowerManager.wakeUp(android.os.SystemClock.uptimeMillis(), 17, "android.policy:BIOMETRIC");
                }
                Trace.beginSection("release wake-and-unlock");
                biometricUnlockController.releaseBiometricWakeLock();
                Trace.endSection();
            }
        };
        KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.mFastUnlockController;
        if (keyguardFastBioUnlockController.isEnabled()) {
            int i2 = this.mMode;
            boolean isKeyguardShowing = ((CentralSurfacesImpl) ((CentralSurfaces) keyguardFastBioUnlockController.centralSurfacesLazy.get())).isKeyguardShowing();
            boolean isOccluded = ((CentralSurfacesImpl) ((CentralSurfaces) keyguardFastBioUnlockController.centralSurfacesLazy.get())).isOccluded();
            boolean isEnabledBiometricUnlockVI = keyguardFastBioUnlockController.settingsHelper.isEnabledBiometricUnlockVI();
            if (((StatusBarStateControllerImpl) keyguardFastBioUnlockController.statusBarStateController).mLeaveOpenOnKeyguardHide) {
                KeyguardFastBioUnlockController.logD("leaveOpenOnKeyguardHide true");
            } else if (!((NotificationPanelViewController) ((CentralSurfacesImpl) ((CentralSurfaces) keyguardFastBioUnlockController.centralSurfacesLazy.get())).mShadeSurface).canBeCollapsed()) {
                Log.m142w("BioUnlock", "canBeCollapsed false");
            } else if (isKeyguardShowing && !isOccluded) {
                if (i2 != 1 && i2 != 2) {
                    if (i2 == 5) {
                        if (!isEnabledBiometricUnlockVI) {
                            keyguardFastBioUnlockController.setWakeAndUnlock(false);
                            keyguardFastBioUnlockController.curVisibilityController = keyguardFastBioUnlockController.surfaceVisibilityController;
                            keyguardFastBioUnlockController.isInvisibleAfterGoingAwayTransStarted = true;
                        }
                    }
                }
                String str = LsRune.VALUE_CONFIG_CARRIER_TEXT_POLICY;
                keyguardFastBioUnlockController.setWakeAndUnlock(true);
                boolean z2 = keyguardFastBioUnlockController.curIsAodBrighterThanNormal;
                if (z2) {
                }
                if (keyguardFastBioUnlockController.screenLifecycle.mScreenState != 0) {
                    keyguardFastBioUnlockController.curVisibilityController = keyguardFastBioUnlockController.surfaceVisibilityController;
                    keyguardFastBioUnlockController.isInvisibleAfterGoingAwayTransStarted = true;
                }
                keyguardFastBioUnlockController.curVisibilityController = keyguardFastBioUnlockController.windowVisibilityController;
                if (z2) {
                    keyguardFastBioUnlockController.needsBlankScreen = true;
                }
            }
            VisibilityController visibilityController = keyguardFastBioUnlockController.curVisibilityController;
            if (visibilityController != null) {
                KeyguardFastBioUnlockController.logD("current controller: ".concat(visibilityController.getClass().getSimpleName()));
            }
            boolean isFastWakeAndUnlockMode = keyguardFastBioUnlockController.isFastWakeAndUnlockMode();
            if (isFastWakeAndUnlockMode || keyguardFastBioUnlockController.isFastUnlockMode()) {
                KeyguardFastBioUnlockController.logD("waitGoingAwayTrans=" + keyguardFastBioUnlockController.isInvisibleAfterGoingAwayTransStarted + " needsBlank=" + (isFastWakeAndUnlockMode && keyguardFastBioUnlockController.needsBlankScreen) + " ssd=false");
            } else {
                StringBuilder m76m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m76m("not supported mode=", i2, ", animation=", isEnabledBiometricUnlockVI, ", showing=");
                m76m.append(isKeyguardShowing);
                m76m.append(", occluded=");
                m76m.append(isOccluded);
                KeyguardFastBioUnlockController.logD(m76m.toString());
                keyguardFastBioUnlockController.reset();
            }
            if (keyguardFastBioUnlockController.isBrightnessChangedCallbackRegistered) {
                KeyguardFastBioUnlockController.logD("unregisterBrightnessListener");
                ((DisplayTrackerImpl) keyguardFastBioUnlockController.displayTracker).removeCallback(keyguardFastBioUnlockController.brightnessChangedCallback);
                keyguardFastBioUnlockController.isBrightnessChangedCallbackRegistered = false;
            }
        }
        boolean z3 = this.mMode == 6 && this.mPowerManager.isInteractive();
        if (this.mMode != 0 && !z3) {
            runnable.run();
        }
        int i3 = this.mMode;
        if (i3 != 1 && i3 != 2) {
            if (i3 == 3) {
                Trace.beginSection("MODE_SHOW_BOUNCER");
                this.mKeyguardViewController.showPrimaryBouncer(true);
                Trace.endSection();
            } else if (i3 == 5) {
                Trace.beginSection("MODE_UNLOCK_COLLAPSING");
                if (keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true) && (this.mBouncer || this.mAuthenticatedBioSourceType != BiometricSourceType.FACE || !this.mKeyguardBypassController.getLockStayEnabled() || isLargeCoverScreen())) {
                    this.mKeyguardViewController.notifyKeyguardAuthenticated(false);
                }
                Trace.endSection();
            } else if (i3 != 6) {
                if (i3 == 7) {
                    Trace.beginSection("MODE_DISMISS_BOUNCER");
                    this.mKeyguardViewController.notifyKeyguardAuthenticated(false);
                    Trace.endSection();
                }
            }
            if (keyguardFastBioUnlockController.isFastWakeAndUnlockMode()) {
                int i4 = this.mMode;
                Iterator it = ((HashSet) this.mBiometricUnlockEventsListeners).iterator();
                while (it.hasNext()) {
                    ((BiometricUnlockEventsListener) it.next()).onModeChanged(i4);
                }
            } else {
                final int i5 = this.mMode;
                Runnable runnable2 = new Runnable() { // from class: com.android.systemui.statusbar.phone.BiometricUnlockController$$ExternalSyntheticLambda2
                    @Override // java.lang.Runnable
                    public final void run() {
                        BiometricUnlockController biometricUnlockController = BiometricUnlockController.this;
                        int i6 = i5;
                        Iterator it2 = ((HashSet) biometricUnlockController.mBiometricUnlockEventsListeners).iterator();
                        while (it2.hasNext()) {
                            ((BiometricUnlockController.BiometricUnlockEventsListener) it2.next()).onModeChanged(i6);
                        }
                    }
                };
                ArrayList arrayList = (ArrayList) keyguardFastBioUnlockController.pendingRunnableList;
                if (!arrayList.contains(runnable2)) {
                    arrayList.add(runnable2);
                }
            }
            keyguardFastBioUnlockController.logLapTime("startWakeAndUnlock end", new Object[0]);
            Trace.endSection();
        }
        if (i3 == 2) {
            Trace.beginSection("MODE_WAKE_AND_UNLOCK_PULSING");
            this.mMediaManager.updateMediaMetaData(false, true);
        } else if (i3 == 1) {
            Trace.beginSection("MODE_WAKE_AND_UNLOCK");
        } else {
            Trace.beginSection("MODE_WAKE_AND_UNLOCK_FROM_DREAM");
        }
        if (keyguardFastBioUnlockController.isFastWakeAndUnlockMode() && !keyguardFastBioUnlockController.isInvisibleAfterGoingAwayTransStarted && !keyguardFastBioUnlockController.needsBlankScreen) {
            keyguardFastBioUnlockController.setForceInvisible(null, false);
        }
        ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).setNotificationShadeFocusable(false);
        this.mKeyguardViewMediator.onWakeAndUnlocking(z3);
        Trace.endSection();
        if (keyguardFastBioUnlockController.isFastWakeAndUnlockMode()) {
        }
        keyguardFastBioUnlockController.logLapTime("startWakeAndUnlock end", new Object[0]);
        Trace.endSection();
    }

    public final void updateBackgroundAuthToast(boolean z) {
        KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("update biometric toast = ", z, "BiometricUnlockCtrl");
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
        if (this.mUpdateMonitor.isAuthenticatedWithBiometric(KeyguardUpdateMonitor.getCurrentUser())) {
            android.util.Log.d("BiometricUnlockCtrl", "Already unlocked by biometric");
            return;
        }
        KeyguardBiometricToastView keyguardBiometricToastView2 = this.mBiometricToastView;
        WindowManager windowManager = this.mWindowManager;
        if (keyguardBiometricToastView2 != null) {
            if (keyguardBiometricToastView2.mIsShowing) {
                android.util.Log.d("BiometricUnlockCtrl", "Biometric toast already showing");
                return;
            } else {
                windowManager.removeView(keyguardBiometricToastView2);
                this.mBiometricToastView = null;
            }
        }
        KeyguardBiometricToastView keyguardBiometricToastView3 = (KeyguardBiometricToastView) View.inflate(this.mContext, R.layout.keyguard_biometric_toast_view, null);
        this.mBiometricToastView = keyguardBiometricToastView3;
        keyguardBiometricToastView3.mBiometricToastViewStateUpdater = new Consumer() { // from class: com.android.systemui.statusbar.phone.BiometricUnlockController$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                BiometricUnlockController biometricUnlockController = BiometricUnlockController.this;
                boolean booleanValue = ((Boolean) obj).booleanValue();
                SecLockIconViewController secLockIconViewController = (SecLockIconViewController) biometricUnlockController.mSecLockIconViewControllerLazy.get();
                secLockIconViewController.mIsBiometricToastViewAnimating = booleanValue;
                secLockIconViewController.updateVisibility();
            }
        };
        windowManager.addView(keyguardBiometricToastView3, this.mWindowLp);
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface BiometricUnlockEventsListener {
        default void onModeChanged(int i) {
        }

        default void onResetMode() {
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
    public final void onKeyguardBouncerStateChanged(boolean z) {
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0059, code lost:
    
        if (r7.isUnlocked() != false) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x006e, code lost:
    
        if (r13.mSecure != false) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0090, code lost:
    
        if (r17.mKeyguardViewController.isBouncerShowing() == false) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00e2, code lost:
    
        if (r16 != false) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00e8, code lost:
    
        if (r16 != false) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0101, code lost:
    
        if (r16 != false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0124, code lost:
    
        if (r16 != false) goto L88;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void startWakeAndUnlock(BiometricSourceType biometricSourceType, boolean z) {
        boolean z2;
        KeyguardBypassController keyguardBypassController;
        int i;
        BiometricSourceType biometricSourceType2 = BiometricSourceType.FACE;
        DozeScrimController dozeScrimController = this.mDozeScrimController;
        BiometricUnlockLogger biometricUnlockLogger = this.mLogger;
        KeyguardStateController keyguardStateController = this.mKeyguardStateController;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mUpdateMonitor;
        if (biometricSourceType != biometricSourceType2 && biometricSourceType != BiometricSourceType.IRIS) {
            boolean isUnlockingWithBiometricAllowed = keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(z);
            boolean z3 = keyguardUpdateMonitor.mDeviceInteractive;
            KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) keyguardStateController;
            boolean z4 = keyguardStateControllerImpl.mShowing;
            boolean z5 = keyguardUpdateMonitor.mIsDreaming;
            if (isUnlockingWithBiometricAllowed) {
                biometricUnlockLogger.logCalculateModeForFingerprintUnlockingAllowed(z3, z4, z5);
            } else {
                this.mLogger.logCalculateModeForFingerprintUnlockingNotAllowed(keyguardUpdateMonitor.mStrongAuthTracker.getStrongAuthForUser(KeyguardUpdateMonitor.getCurrentUser()), z, keyguardUpdateMonitor.mStrongAuthTracker.isNonStrongBiometricAllowedAfterIdleTimeout(KeyguardUpdateMonitor.getCurrentUser()), z3, z4);
            }
            if (z3) {
                if (!isUnlockingWithBiometricAllowed || !z5) {
                    if (z4) {
                        if (!this.mKeyguardViewController.primaryBouncerIsOrWillBeShowing() || !isUnlockingWithBiometricAllowed) {
                            if (!isUnlockingWithBiometricAllowed) {
                            }
                            i = 5;
                        }
                        i = 7;
                    }
                    i = 0;
                }
                i = 6;
            } else {
                if (z4 || this.mScreenOffAnimationController.isKeyguardShowDelayed()) {
                    if (!(dozeScrimController.mPulseCallback != null) || !isUnlockingWithBiometricAllowed) {
                        if (!isUnlockingWithBiometricAllowed) {
                        }
                    }
                    i = 2;
                }
                i = 1;
            }
            i = 3;
        } else {
            boolean z6 = keyguardUpdateMonitor.mDeviceInteractive;
            KeyguardStateControllerImpl keyguardStateControllerImpl2 = (KeyguardStateControllerImpl) keyguardStateController;
            boolean z7 = keyguardStateControllerImpl2.mShowing;
            boolean isUnlockingWithBiometricAllowed2 = keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(z);
            boolean z8 = keyguardUpdateMonitor.mIsDreaming;
            KeyguardBypassController keyguardBypassController2 = this.mKeyguardBypassController;
            boolean z9 = !keyguardBypassController2.getLockStayEnabled() || isLargeCoverScreen();
            if (isUnlockingWithBiometricAllowed2) {
                biometricUnlockLogger.logCalculateModeForPassiveAuthUnlockingAllowed(z6, z7, z8, z9);
                z2 = z9;
                keyguardBypassController = keyguardBypassController2;
            } else {
                z2 = z9;
                keyguardBypassController = keyguardBypassController2;
                this.mLogger.logCalculateModeForPassiveAuthUnlockingNotAllowed(keyguardUpdateMonitor.mStrongAuthTracker.getStrongAuthForUser(KeyguardUpdateMonitor.getCurrentUser()), z, keyguardUpdateMonitor.mStrongAuthTracker.isNonStrongBiometricAllowedAfterIdleTimeout(KeyguardUpdateMonitor.getCurrentUser()), z6, z7, z2);
            }
            if (!z6) {
                if (z7) {
                    if (isUnlockingWithBiometricAllowed2) {
                        if (!(dozeScrimController.mPulseCallback != null)) {
                        }
                    }
                }
                i = 4;
            } else if (!isUnlockingWithBiometricAllowed2 || !z8 || z6) {
                if (!isUnlockingWithBiometricAllowed2 || !keyguardStateControllerImpl2.mOccluded) {
                    if (z7) {
                        if ((!this.mKeyguardViewController.primaryBouncerIsOrWillBeShowing() && !keyguardBypassController.altBouncerShowing) || !isUnlockingWithBiometricAllowed2) {
                            if (!isUnlockingWithBiometricAllowed2) {
                            }
                        }
                        i = 7;
                    }
                    i = 0;
                }
                i = 5;
            }
        }
        startWakeAndUnlock(i);
    }
}
