package com.android.keyguard;

import android.app.ActivityTaskManager;
import android.app.IActivityTaskManager;
import android.app.admin.DevicePolicyManager;
import android.app.trust.TrustManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.hardware.SensorPrivacyManager;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.hardware.face.IFaceAuthenticatorsRegisteredCallback;
import android.hardware.fingerprint.FingerprintAuthenticateOptions;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.hardware.fingerprint.IFingerprintAuthenticatorsRegisteredCallback;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.dreams.IDreamManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.CarrierConfigManager;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyCallback;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import com.android.compose.animation.scene.ObservableTransitionState;
import com.android.internal.foldables.FoldGracePeriodProvider;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.ActiveUnlockConfig;
import com.android.keyguard.KeyguardActiveUnlockModel;
import com.android.keyguard.KeyguardFingerprintListenModel;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3;
import com.android.keyguard.logging.SimLogger;
import com.android.keyguard.logging.SimLogger$$ExternalSyntheticLambda0;
import com.android.settingslib.WirelessUtils;
import com.android.settingslib.fuelgauge.BatteryStatus;
import com.android.systemui.CoreStartable;
import com.android.systemui.CscRune;
import com.android.systemui.DejankUtils;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.deviceentry.data.repository.FaceWakeUpTriggersConfig;
import com.android.systemui.deviceentry.data.repository.FaceWakeUpTriggersConfigImpl;
import com.android.systemui.deviceentry.shared.FaceAuthReasonKt;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardWmStateRefactor;
import com.android.systemui.keyguard.shared.constants.TrustAgentUiEvent;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.scene.shared.flag.SceneContainerFlag;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.SettingsHelper;
import com.google.android.collect.Lists;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.knox.net.vpn.VpnErrorValues;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.volte2.data.VolteConstants;
import dalvik.annotation.optimization.NeverCompile;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.inject.Provider;
import kotlin.NoWhenBranchMatchedException;

/* loaded from: classes.dex */
public abstract class KeyguardUpdateMonitor implements TrustManager.TrustListener, CoreStartable, KeyguardSecUpdateMonitor {
    public static final int BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED = -1;
    protected static final int BIOMETRIC_STATE_CANCELLING = 2;
    protected static final int BIOMETRIC_STATE_CANCELLING_RESTARTING = 3;
    protected static final int BIOMETRIC_STATE_STOPPED = 0;
    protected static final int DEFAULT_CANCEL_SIGNAL_TIMEOUT = 3000;
    protected static final int HAL_POWER_PRESS_TIMEOUT = 50;
    public static int sCurrentUser;
    public int mActiveMobileDataSubscription;
    public final ActiveUnlockConfig mActiveUnlockConfig;
    public final KeyguardActiveUnlockModel.Buffer mActiveUnlockTriggerBuffer;
    public final IActivityTaskManager mActivityTaskManager;
    public boolean mAllowFingerprintOnCurrentOccludingActivity;
    public final Set mAllowFingerprintOnOccludingActivitiesFromPackage;
    public boolean mAlternateBouncerShowing;
    public boolean mAssistantVisible;
    public final AuthController mAuthController;
    public boolean mAuthInterruptActive;
    public final Executor mBackgroundExecutor;
    BatteryStatus mBatteryStatus;
    public final AnonymousClass2 mBiometricEnabledCallback;
    public final SparseBooleanArray mBiometricEnabledForUser;
    public final BiometricManager mBiometricManager;
    public boolean mBiometricPromptShowing;
    protected final BroadcastReceiver mBroadcastAllReceiver;
    public final BroadcastDispatcher mBroadcastDispatcher;
    protected final BroadcastReceiver mBroadcastReceiver;
    public final ArrayList mCallbacks;
    public final CarrierConfigManager mCarrierConfigManager;
    public boolean mCommunalShowing;
    protected int mConfigFaceAuthSupportedPosture;
    public final Context mContext;
    public boolean mCredentialAttempted;
    public boolean mDeviceInteractive;
    public final DevicePolicyManager mDevicePolicyManager;
    public final DevicePostureController mDevicePostureController;
    public boolean mDeviceProvisioned;
    public AnonymousClass22 mDeviceProvisionedObserver;
    public final IDreamManager mDreamManager;
    public final Set mFaceAcquiredInfoIgnoreList;
    final FaceManager.AuthenticationCallback mFaceAuthenticationCallback;
    public final KeyguardUpdateMonitor$$ExternalSyntheticLambda4 mFaceCancelNotReceived;
    CancellationSignal mFaceCancelSignal;
    public boolean mFaceLockedOutPermanent;
    public final AnonymousClass11 mFaceLockoutResetCallback;
    public final FaceManager mFaceManager;
    public int mFaceRunningState;
    public List mFaceSensorProperties;
    public final FaceWakeUpTriggersConfig mFaceWakeUpTriggersConfig;
    final FingerprintManager.AuthenticationCallback mFingerprintAuthenticationCallback;
    CancellationSignal mFingerprintCancelSignal;
    public boolean mFingerprintDetectRunning;
    public final AnonymousClass13 mFingerprintDetectionCallback;
    public final KeyguardFingerprintListenModel.Buffer mFingerprintListenBuffer;
    public boolean mFingerprintLockedOut;
    public boolean mFingerprintLockedOutPermanent;
    public final AnonymousClass10 mFingerprintLockoutResetCallback;
    protected int mFingerprintRunningState;
    public List mFingerprintSensorProperties;
    protected FoldGracePeriodProvider mFoldGracePeriodProvider;
    public boolean mForceIsDismissible;
    protected final Runnable mFpCancelNotReceived;
    public final FingerprintManager mFpm;
    public boolean mGoingToSleep;
    public final AnonymousClass16 mHandler;
    public int mHardwareFaceUnavailableRetryCount;
    public int mHardwareFingerprintUnavailableRetryCount;
    boolean mIncompatibleCharger;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public boolean mIsDreaming;
    public final boolean mIsSystemUser;
    public KeyguardBypassController mKeyguardBypassController;
    public boolean mKeyguardGoingAway;
    public boolean mKeyguardOccluded;
    public boolean mKeyguardShowing;
    public final LatencyTracker mLatencyTracker;
    public final LockPatternUtils mLockPatternUtils;
    public final KeyguardUpdateMonitorLogger mLogger;
    public final Executor mMainExecutor;
    public boolean mNeedsSlowUnlockTransition;
    public boolean mOccludingAppRequestingFp;
    public final PackageManager mPackageManager;
    public int mPhoneState;
    public TelephonyCallback.ActiveDataSubscriptionIdListener mPhoneStateListener;
    final DevicePostureController.Callback mPostureCallback;
    public int mPostureState;
    public boolean mPrimaryBouncerFullyShown;
    public boolean mPrimaryBouncerIsOrWillBeShowing;
    public final AnonymousClass6 mRetryFaceAuthentication;
    public final AnonymousClass5 mRetryFingerprintAuthenticationAfterHwUnavailable;
    public final Map mSecondaryLockscreenRequirement;
    public boolean mSecureCameraLaunched;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final SensorPrivacyManager mSensorPrivacyManager;
    public final HashMap mServiceStates;
    public final Provider mSessionTrackerProvider;
    public final Object mSimDataLockObject;
    public final HashMap mSimDatasBySlotId;
    public final SimLogger mSimLogger;
    public int mStatusBarState;
    public final StatusBarStateController mStatusBarStateController;
    public final AnonymousClass1 mStatusBarStateControllerListener;
    public StrongAuthTracker mStrongAuthTracker;
    public List mSubscriptionInfo;
    final SubscriptionManager.OnSubscriptionsChangedListener mSubscriptionListener;
    public final SubscriptionManager mSubscriptionManager;
    public boolean mSwitchingUser;
    public final TaskStackChangeListeners mTaskStackChangeListeners;
    public final AnonymousClass23 mTaskStackListener;
    protected boolean mTelephonyCapable;
    public final TelephonyListenerManager mTelephonyListenerManager;
    public final TelephonyManager mTelephonyManager;
    public final AnonymousClass17 mTimeFormatChangeObserver;
    public final TrustManager mTrustManager;
    public final UiEventLogger mUiEventLogger;
    public final UserTracker.Callback mUserChangedCallback;
    SparseArray<BiometricAuthenticated> mUserFaceAuthenticated;
    SparseArray<BiometricAuthenticated> mUserFingerprintAuthenticated;
    public final SparseBooleanArray mUserHasTrust;
    public final SparseBooleanArray mUserIsUnlocked;
    public final UserManager mUserManager;
    public final UserTracker mUserTracker;
    public final SparseBooleanArray mUserTrustIsManaged;
    public final SparseBooleanArray mUserTrustIsUsuallyManaged;
    public static final ComponentName FALLBACK_HOME_COMPONENT = new ComponentName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.FallbackHome");
    public static final List ABSENT_SIM_STATE_LIST = Arrays.asList(1, 0, 6);

    /* renamed from: com.android.keyguard.KeyguardUpdateMonitor$2, reason: invalid class name */
    public class AnonymousClass2 extends IBiometricEnabledOnKeyguardCallback.Stub {
        public AnonymousClass2() {
        }

        public final void onChanged(boolean z, int i, int i2) {
            post(new KeyguardUpdateMonitor$$ExternalSyntheticLambda6(this, i2, i, z));
        }
    }

    /* renamed from: com.android.keyguard.KeyguardUpdateMonitor$20, reason: invalid class name */
    public class AnonymousClass20 implements AuthController.Callback {
        public AnonymousClass20() {
        }

        @Override // com.android.systemui.biometrics.AuthController.Callback
        public final void onAllAuthenticatorsRegistered(int i) {
            KeyguardUpdateMonitor.this.mMainExecutor.execute(new KeyguardUpdateMonitor$2$$ExternalSyntheticLambda1(this, 1));
        }

        @Override // com.android.systemui.biometrics.AuthController.Callback
        public final void onBiometricPromptDismissed() {
            KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
            keyguardUpdateMonitor.mBiometricPromptShowing = false;
            keyguardUpdateMonitor.updateFingerprintListeningState(0);
        }

        @Override // com.android.systemui.biometrics.AuthController.Callback
        public final void onBiometricPromptShown() {
            KeyguardUpdateMonitor.this.mBiometricPromptShowing = true;
        }

        @Override // com.android.systemui.biometrics.AuthController.Callback
        public final void onEnrollmentsChanged(int i) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
            keyguardUpdateMonitor.mHandler.obtainMessage(348, i, 0).sendToTarget();
            keyguardUpdateMonitor.mMainExecutor.execute(new KeyguardUpdateMonitor$2$$ExternalSyntheticLambda1(this, 2));
        }
    }

    /* renamed from: com.android.keyguard.KeyguardUpdateMonitor$24, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass24 {
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
        }
    }

    class BiometricAuthenticated {
        public final boolean mAuthenticated;
        public final boolean mIsStrongBiometric;

        public BiometricAuthenticated(boolean z, boolean z2) {
            this.mAuthenticated = z;
            this.mIsStrongBiometric = z2;
        }
    }

    public class SimData {
        public int simState;
        public int slotId;
        public int subId;

        public SimData(int i, int i2, int i3) {
            this.simState = i;
            this.slotId = i2;
            this.subId = i3;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("SimData{state=");
            sb.append(this.simState);
            sb.append(",slotId=");
            sb.append(this.slotId);
            sb.append(",subId=");
            return ReorderTile$$ExternalSyntheticOutline0.m(this.subId, "}", sb);
        }
    }

    public class StrongAuthTracker extends LockPatternUtils.StrongAuthTracker {
        public StrongAuthTracker(Context context) {
            super(context);
        }

        public final boolean hasUserAuthenticatedSinceBoot() {
            return (getStrongAuthForUser(KeyguardUpdateMonitor.this.mSelectedUserInteractor.getSelectedUserId()) & 1) == 0;
        }

        public final void onIsNonStrongBiometricAllowedChanged(int i) {
            KeyguardUpdateMonitor.this.notifyNonStrongBiometricAllowedChanged(i);
        }

        public final void onStrongAuthRequiredChanged(int i) {
            KeyguardUpdateMonitor.this.notifyStrongAuthAllowedChanged(i);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.keyguard.KeyguardUpdateMonitor$1, com.android.systemui.plugins.statusbar.StatusBarStateController$StateListener] */
    /* JADX WARN: Type inference failed for: r3v16, types: [com.android.keyguard.KeyguardUpdateMonitor$5] */
    /* JADX WARN: Type inference failed for: r3v17, types: [com.android.keyguard.KeyguardUpdateMonitor$6] */
    /* JADX WARN: Type inference failed for: r3v21, types: [com.android.keyguard.KeyguardUpdateMonitor$10] */
    /* JADX WARN: Type inference failed for: r3v22, types: [com.android.keyguard.KeyguardUpdateMonitor$11] */
    /* JADX WARN: Type inference failed for: r3v24, types: [com.android.keyguard.KeyguardUpdateMonitor$13] */
    /* JADX WARN: Type inference failed for: r3v29, types: [com.android.keyguard.KeyguardUpdateMonitor$23] */
    /* JADX WARN: Type inference failed for: r6v24, types: [android.os.Handler, com.android.keyguard.KeyguardUpdateMonitor$16] */
    /* JADX WARN: Type inference failed for: r7v3, types: [com.android.keyguard.KeyguardUpdateMonitor$17] */
    public KeyguardUpdateMonitor(FaceManager faceManager, Context context, UserTracker userTracker, Looper looper, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, Executor executor, Executor executor2, StatusBarStateController statusBarStateController, LockPatternUtils lockPatternUtils, AuthController authController, TelephonyListenerManager telephonyListenerManager, InteractionJankMonitor interactionJankMonitor, LatencyTracker latencyTracker, ActiveUnlockConfig activeUnlockConfig, KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger, SimLogger simLogger, UiEventLogger uiEventLogger, Provider provider, TrustManager trustManager, SubscriptionManager subscriptionManager, UserManager userManager, IDreamManager iDreamManager, DevicePolicyManager devicePolicyManager, SensorPrivacyManager sensorPrivacyManager, TelephonyManager telephonyManager, PackageManager packageManager, FingerprintManager fingerprintManager, BiometricManager biometricManager, FaceWakeUpTriggersConfig faceWakeUpTriggersConfig, CarrierConfigManager carrierConfigManager, DevicePostureController devicePostureController, Optional<Object> optional, TaskStackChangeListeners taskStackChangeListeners, SelectedUserInteractor selectedUserInteractor, IActivityTaskManager iActivityTaskManager, Provider provider2, Provider provider3, Provider provider4, Provider provider5, Provider provider6) {
        ?? r0 = new StatusBarStateController.StateListener() { // from class: com.android.keyguard.KeyguardUpdateMonitor.1
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onExpandedChanged(boolean z) {
                int i = 0;
                while (true) {
                    KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                    if (i >= keyguardUpdateMonitor.mCallbacks.size()) {
                        return;
                    }
                    KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor.mCallbacks.get(i)).get();
                    if (keyguardUpdateMonitorCallback != null) {
                        keyguardUpdateMonitorCallback.onShadeExpandedChanged(z);
                    }
                    i++;
                }
            }

            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public final void onStateChanged(int i) {
                KeyguardUpdateMonitor.this.mStatusBarState = i;
            }
        };
        this.mStatusBarStateControllerListener = r0;
        this.mSimDataLockObject = new Object();
        new HashMap();
        this.mSimDatasBySlotId = new HashMap();
        this.mServiceStates = new HashMap();
        this.mBatteryStatus = null;
        this.mCallbacks = Lists.newArrayList();
        this.mFoldGracePeriodProvider = new FoldGracePeriodProvider();
        this.mFingerprintRunningState = 0;
        this.mFaceRunningState = 0;
        this.mActiveMobileDataSubscription = -1;
        this.mPostureState = 0;
        this.mHardwareFingerprintUnavailableRetryCount = 0;
        this.mHardwareFaceUnavailableRetryCount = 0;
        this.mFpCancelNotReceived = new KeyguardUpdateMonitor$$ExternalSyntheticLambda4(this, 0);
        this.mFaceCancelNotReceived = new KeyguardUpdateMonitor$$ExternalSyntheticLambda4(this, 13);
        this.mBiometricEnabledCallback = new AnonymousClass2();
        this.mPhoneStateListener = new TelephonyCallback.ActiveDataSubscriptionIdListener() { // from class: com.android.keyguard.KeyguardUpdateMonitor.3
            @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
            public final void onActiveDataSubscriptionIdChanged(int i) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                keyguardUpdateMonitor.mActiveMobileDataSubscription = i;
                keyguardUpdateMonitor.mHandler.sendEmptyMessage(328);
            }
        };
        this.mSubscriptionListener = new SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.keyguard.KeyguardUpdateMonitor.4
            @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
            public final void onSubscriptionsChanged() {
                sendEmptyMessage(328);
            }
        };
        this.mUserIsUnlocked = new SparseBooleanArray();
        this.mUserHasTrust = new SparseBooleanArray();
        this.mUserTrustIsManaged = new SparseBooleanArray();
        this.mUserTrustIsUsuallyManaged = new SparseBooleanArray();
        this.mBiometricEnabledForUser = new SparseBooleanArray();
        this.mSecondaryLockscreenRequirement = new HashMap();
        this.mFingerprintListenBuffer = new KeyguardFingerprintListenModel.Buffer();
        this.mActiveUnlockTriggerBuffer = new KeyguardActiveUnlockModel.Buffer();
        this.mUserFingerprintAuthenticated = new SparseArray<>();
        this.mUserFaceAuthenticated = new SparseArray<>();
        this.mRetryFingerprintAuthenticationAfterHwUnavailable = new Runnable() { // from class: com.android.keyguard.KeyguardUpdateMonitor.5
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger2 = keyguardUpdateMonitor.mLogger;
                int i = keyguardUpdateMonitor.mHardwareFingerprintUnavailableRetryCount;
                keyguardUpdateMonitorLogger2.getClass();
                LogLevel logLevel = LogLevel.WARNING;
                KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(11);
                LogBuffer logBuffer = keyguardUpdateMonitorLogger2.logBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
                ((LogMessageImpl) logMessageObtain).int1 = i;
                logBuffer.commit(logMessageObtain);
                if (!KeyguardUpdateMonitor.this.mFingerprintSensorProperties.isEmpty()) {
                    KeyguardUpdateMonitor.this.updateFingerprintListeningState(2);
                    return;
                }
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = KeyguardUpdateMonitor.this;
                int i2 = keyguardUpdateMonitor2.mHardwareFingerprintUnavailableRetryCount;
                if (i2 < 20) {
                    keyguardUpdateMonitor2.mHardwareFingerprintUnavailableRetryCount = i2 + 1;
                    keyguardUpdateMonitor2.mHandler.postDelayed(keyguardUpdateMonitor2.mRetryFingerprintAuthenticationAfterHwUnavailable, 500L);
                }
            }
        };
        this.mRetryFaceAuthentication = new Runnable() { // from class: com.android.keyguard.KeyguardUpdateMonitor.6
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger2 = keyguardUpdateMonitor.mLogger;
                int i = keyguardUpdateMonitor.mHardwareFaceUnavailableRetryCount;
                keyguardUpdateMonitorLogger2.getClass();
                LogLevel logLevel = LogLevel.WARNING;
                KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(7);
                LogBuffer logBuffer = keyguardUpdateMonitorLogger2.logBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
                ((LogMessageImpl) logMessageObtain).int1 = i;
                logBuffer.commit(logMessageObtain);
                KeyguardUpdateMonitor.this.updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_RETRY_AFTER_HW_UNAVAILABLE);
            }
        };
        new Object(this) { // from class: com.android.keyguard.KeyguardUpdateMonitor.7
        };
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.keyguard.KeyguardUpdateMonitor.8
            /* JADX WARN: Removed duplicated region for block: B:40:0x00f6  */
            /* JADX WARN: Removed duplicated region for block: B:71:0x015c  */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onReceive(Context context2, Intent intent) {
                int i;
                String action = intent.getAction();
                KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger2 = KeyguardUpdateMonitor.this.mLogger;
                keyguardUpdateMonitorLogger2.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(20);
                LogBuffer logBuffer = keyguardUpdateMonitorLogger2.logBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
                ((LogMessageImpl) logMessageObtain).str1 = action;
                logBuffer.commit(logMessageObtain);
                if ("android.intent.action.TIME_TICK".equals(action) || "android.intent.action.TIME_SET".equals(action)) {
                    sendEmptyMessage(301);
                    return;
                }
                if ("android.intent.action.TIMEZONE_CHANGED".equals(action)) {
                    sendMessage(obtainMessage(339, intent.getStringExtra("time-zone")));
                    return;
                }
                if ("android.intent.action.BATTERY_CHANGED".equals(action)) {
                    sendMessage(KeyguardUpdateMonitor.this.getKeyguardBatteryMessage(intent));
                    return;
                }
                if ("com.samsung.server.BatteryService.action.SEC_BATTERY_REMAINING_CHARGING_TIME_CHANGED".equals(action)) {
                    KeyguardUpdateMonitor.this.updateBatteryRemainTime(intent);
                    return;
                }
                if ("android.hardware.usb.action.USB_PORT_COMPLIANCE_CHANGED".equals(action)) {
                    KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                    keyguardUpdateMonitor.mHandler.sendMessage(keyguardUpdateMonitor.getKeyguardBatteryMessage(intent));
                    return;
                }
                if (!"android.intent.action.SIM_STATE_CHANGED".equals(action)) {
                    if ("android.intent.action.PHONE_STATE".equals(action)) {
                        String stringExtra = intent.getStringExtra("state");
                        AnonymousClass16 anonymousClass16 = KeyguardUpdateMonitor.this.mHandler;
                        anonymousClass16.sendMessage(anonymousClass16.obtainMessage(VpnErrorValues.ERROR_STOPPING_CONNECTION_BEFORE_REMOVING, stringExtra));
                        return;
                    }
                    if ("android.telephony.action.SERVICE_PROVIDERS_UPDATED".equals(action)) {
                        obtainMessage(347, intent).sendToTarget();
                        return;
                    }
                    if ("android.intent.action.AIRPLANE_MODE".equals(action)) {
                        sendEmptyMessage(329);
                        return;
                    }
                    if (CscRune.SECURITY_DISABLE_EMERGENCY_CALL_WHEN_OFFLINE || !"android.intent.action.SERVICE_STATE".equals(action)) {
                        if ("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED".equals(action)) {
                            sendEmptyMessage(328);
                            return;
                        } else {
                            if ("android.intent.action.LOCALE_CHANGED".equals(action)) {
                                sendEmptyMessage(VolteConstants.ErrorCode.RTP_TIME_OUT);
                                return;
                            }
                            return;
                        }
                    }
                    ServiceState serviceStateNewFromBundle = ServiceState.newFromBundle(intent.getExtras());
                    int intExtra = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                    SimLogger simLogger2 = KeyguardUpdateMonitor.this.mSimLogger;
                    simLogger2.getClass();
                    LogLevel logLevel2 = LogLevel.VERBOSE;
                    SimLogger$$ExternalSyntheticLambda0 simLogger$$ExternalSyntheticLambda0 = new SimLogger$$ExternalSyntheticLambda0(5);
                    LogBuffer logBuffer2 = simLogger2.logBuffer;
                    LogMessage logMessageObtain2 = logBuffer2.obtain("SimLog", logLevel2, simLogger$$ExternalSyntheticLambda0, null);
                    LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain2;
                    logMessageImpl.str1 = action;
                    logMessageImpl.str2 = String.valueOf(serviceStateNewFromBundle);
                    logMessageImpl.int1 = intExtra;
                    logBuffer2.commit(logMessageObtain2);
                    AnonymousClass16 anonymousClass162 = KeyguardUpdateMonitor.this.mHandler;
                    anonymousClass162.sendMessage(anonymousClass162.obtainMessage(330, intExtra, 0, serviceStateNewFromBundle));
                    return;
                }
                if (!"android.intent.action.SIM_STATE_CHANGED".equals(intent.getAction())) {
                    throw new IllegalArgumentException("only handles intent ACTION_SIM_STATE_CHANGED");
                }
                String stringExtra2 = intent.getStringExtra(ImsProfile.SERVICE_SS);
                int intExtra2 = intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1);
                int intExtra3 = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                if ("ABSENT".equals(stringExtra2)) {
                    i = 1;
                } else if ("LOCKED".equals(stringExtra2)) {
                    String stringExtra3 = intent.getStringExtra("reason");
                    if ("PIN".equals(stringExtra3)) {
                        i = 2;
                    } else if ("PUK".equals(stringExtra3)) {
                        i = 3;
                    } else if (!"NETWORK".equals(stringExtra3)) {
                        if ("PERM_DISABLED".equals(stringExtra3)) {
                            i = 7;
                        } else if (LsRune.SECURITY_SIM_PERSO_LOCK && "PERSO".equals(stringExtra3)) {
                            i = 12;
                        } else {
                            if (!"PERM_DISABLED".equals(stringExtra3)) {
                                i = "NETWORK".equals(stringExtra3) ? 4 : 0;
                            }
                            i = 7;
                        }
                    }
                } else if ("CARD_IO_ERROR".equals(stringExtra2)) {
                    i = 8;
                } else if ("CARD_RESTRICTED".equals(stringExtra2)) {
                    i = 9;
                } else if ("NOT_READY".equals(stringExtra2)) {
                    i = 6;
                } else if ("READY".equals(stringExtra2) || "LOADED".equals(stringExtra2) || "IMSI".equals(stringExtra2)) {
                    i = 5;
                }
                SimData simData = new SimData(i, intExtra2, intExtra3);
                if (intent.getBooleanExtra("rebroadcastOnUnlock", false)) {
                    if (simData.simState == 1) {
                        obtainMessage(338, Boolean.TRUE).sendToTarget();
                        return;
                    }
                    return;
                }
                SimLogger simLogger3 = KeyguardUpdateMonitor.this.mSimLogger;
                String stringExtra4 = intent.getStringExtra(ImsProfile.SERVICE_SS);
                int i2 = simData.slotId;
                int i3 = simData.subId;
                simLogger3.getClass();
                LogLevel logLevel3 = LogLevel.VERBOSE;
                SimLogger$$ExternalSyntheticLambda0 simLogger$$ExternalSyntheticLambda02 = new SimLogger$$ExternalSyntheticLambda0(4);
                LogBuffer logBuffer3 = simLogger3.logBuffer;
                LogMessage logMessageObtain3 = logBuffer3.obtain("SimLog", logLevel3, simLogger$$ExternalSyntheticLambda02, null);
                LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain3;
                logMessageImpl2.str1 = action;
                logMessageImpl2.str2 = stringExtra4;
                logMessageImpl2.int1 = i2;
                logMessageImpl2.int2 = i3;
                logBuffer3.commit(logMessageObtain3);
                int i4 = simData.slotId;
                if (i4 == -1) {
                    return;
                }
                KeyguardUpdateMonitor.this.resetSimPinPassed(i4);
                if (LsRune.SECURITY_ESIM) {
                    KeyguardUpdateMonitor.this.clearESimRemoved();
                }
                obtainMessage(304, simData.subId, simData.slotId, Integer.valueOf(simData.simState)).sendToTarget();
            }
        };
        this.mBroadcastAllReceiver = new BroadcastReceiver() { // from class: com.android.keyguard.KeyguardUpdateMonitor.9
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if ("android.app.action.NEXT_ALARM_CLOCK_CHANGED".equals(action)) {
                    sendEmptyMessage(301);
                    return;
                }
                if ("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED".equals(action)) {
                    int selectedUserId = KeyguardUpdateMonitor.this.mSelectedUserInteractor.getSelectedUserId();
                    if (selectedUserId == -1) {
                        selectedUserId = KeyguardUpdateMonitor.sCurrentUser;
                    }
                    AnonymousClass16 anonymousClass16 = KeyguardUpdateMonitor.this.mHandler;
                    anonymousClass16.sendMessage(anonymousClass16.obtainMessage(309, selectedUserId, 0));
                    return;
                }
                if ("android.intent.action.USER_UNLOCKED".equals(action)) {
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                    ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(intExtra, "ACTION_USER_UNLOCKED. userId:", "KeyguardUpdateMonitor");
                    AnonymousClass16 anonymousClass162 = KeyguardUpdateMonitor.this.mHandler;
                    anonymousClass162.sendMessage(anonymousClass162.obtainMessage(334, intExtra, 0));
                    return;
                }
                if ("android.intent.action.USER_STOPPED".equals(action)) {
                    AnonymousClass16 anonymousClass163 = KeyguardUpdateMonitor.this.mHandler;
                    anonymousClass163.sendMessage(anonymousClass163.obtainMessage(340, intent.getIntExtra("android.intent.extra.user_handle", -1), 0));
                } else if ("android.intent.action.USER_REMOVED".equals(action)) {
                    AnonymousClass16 anonymousClass164 = KeyguardUpdateMonitor.this.mHandler;
                    anonymousClass164.sendMessage(anonymousClass164.obtainMessage(341, intent.getIntExtra("android.intent.extra.user_handle", -1), 0));
                } else if ("android.nfc.action.REQUIRE_UNLOCK_FOR_NFC".equals(action)) {
                    sendEmptyMessage(345);
                }
            }
        };
        this.mFingerprintLockoutResetCallback = new FingerprintManager.LockoutResetCallback() { // from class: com.android.keyguard.KeyguardUpdateMonitor.10
            public final void onLockoutReset(int i) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                int i2 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                keyguardUpdateMonitor.handleFingerprintLockoutReset(0);
            }
        };
        this.mFaceLockoutResetCallback = new FaceManager.LockoutResetCallback() { // from class: com.android.keyguard.KeyguardUpdateMonitor.11
            public final void onLockoutReset(int i) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                int i2 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                keyguardUpdateMonitor.handleFaceLockoutReset(0);
            }
        };
        this.mFingerprintAuthenticationCallback = new FingerprintManager.AuthenticationCallback() { // from class: com.android.keyguard.KeyguardUpdateMonitor.12
            public final void onAuthenticationAcquired(int i) {
                Trace.beginSection("KeyguardUpdateMonitor#onAuthenticationAcquired");
                KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger2 = KeyguardUpdateMonitor.this.mLogger;
                keyguardUpdateMonitorLogger2.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(29);
                LogBuffer logBuffer = keyguardUpdateMonitorLogger2.logBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("KeyguardFingerprintLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
                ((LogMessageImpl) logMessageObtain).int1 = i;
                logBuffer.commit(logMessageObtain);
                KeyguardUpdateMonitor.this.handleFingerprintAcquired(i);
                Trace.endSection();
            }

            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
            public final void onAuthenticationError(int i, CharSequence charSequence) {
                Trace.beginSection("KeyguardUpdateMonitor#onAuthenticationError");
                KeyguardUpdateMonitor.this.handleFingerprintError(i, charSequence.toString());
                Trace.endSection();
            }

            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
            public final void onAuthenticationFailed() {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                ActiveUnlockConfig.ActiveUnlockRequestOrigin activeUnlockRequestOrigin = ActiveUnlockConfig.ActiveUnlockRequestOrigin.BIOMETRIC_FAIL;
                keyguardUpdateMonitor.getClass();
                keyguardUpdateMonitor.requestActiveUnlock(activeUnlockRequestOrigin, "fingerprintFailure-dismissKeyguard", true);
                KeyguardUpdateMonitor.this.handleFingerprintAuthFailed();
            }

            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
            public final void onAuthenticationHelp(int i, CharSequence charSequence) {
                Trace.beginSection("KeyguardUpdateMonitor#onAuthenticationHelp");
                KeyguardUpdateMonitor.this.handleFingerprintHelp(i, charSequence.toString());
                Trace.endSection();
            }

            @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
            public final void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult authenticationResult) {
                Trace.beginSection("KeyguardUpdateMonitor#onAuthenticationSucceeded");
                KeyguardUpdateMonitor.this.handleFingerprintAuthenticated(authenticationResult.getUserId(), authenticationResult.isStrongBiometric());
                Trace.endSection();
            }

            public final void onUdfpsPointerDown(int i) {
                KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger2 = KeyguardUpdateMonitor.this.mLogger;
                keyguardUpdateMonitorLogger2.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(4);
                LogBuffer logBuffer = keyguardUpdateMonitorLogger2.logBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
                ((LogMessageImpl) logMessageObtain).int1 = i;
                logBuffer.commit(logMessageObtain);
                KeyguardUpdateMonitor.this.requestFaceAuth("Face auth triggered due to finger down on UDFPS");
            }

            public final void onUdfpsPointerUp(int i) {
                KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger2 = KeyguardUpdateMonitor.this.mLogger;
                keyguardUpdateMonitorLogger2.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(28);
                LogBuffer logBuffer = keyguardUpdateMonitorLogger2.logBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
                ((LogMessageImpl) logMessageObtain).int1 = i;
                logBuffer.commit(logMessageObtain);
            }
        };
        this.mFingerprintDetectionCallback = new FingerprintManager.FingerprintDetectionCallback() { // from class: com.android.keyguard.KeyguardUpdateMonitor.13
            public final void onDetectionError(int i) {
                KeyguardUpdateMonitor.this.handleFingerprintError(i, "");
            }

            public final void onFingerprintDetected(int i, int i2, boolean z) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                if (keyguardUpdateMonitor.mHandler.hasCallbacks(keyguardUpdateMonitor.mFpCancelNotReceived)) {
                    KeyguardUpdateMonitor.this.mLogger.d("onFingerprintDetected() triggered while waiting for cancellation, removing watchdog");
                    KeyguardUpdateMonitor keyguardUpdateMonitor2 = KeyguardUpdateMonitor.this;
                    keyguardUpdateMonitor2.mHandler.removeCallbacks(keyguardUpdateMonitor2.mFpCancelNotReceived);
                }
                KeyguardUpdateMonitor keyguardUpdateMonitor3 = KeyguardUpdateMonitor.this;
                keyguardUpdateMonitor3.mFingerprintCancelSignal = null;
                keyguardUpdateMonitor3.setFingerprintRunningState(0);
                KeyguardUpdateMonitor keyguardUpdateMonitor4 = KeyguardUpdateMonitor.this;
                BiometricSourceType biometricSourceType = BiometricSourceType.FINGERPRINT;
                keyguardUpdateMonitor4.getClass();
                Trace.beginSection("KeyGuardUpdateMonitor#handlerBiometricDetected");
                Assert.isMainThread();
                Trace.beginSection("KeyGuardUpdateMonitor#onBiometricDetected");
                for (int i3 = 0; i3 < keyguardUpdateMonitor4.mCallbacks.size(); i3++) {
                    KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor4.mCallbacks.get(i3)).get();
                    if (keyguardUpdateMonitorCallback != null) {
                        keyguardUpdateMonitorCallback.onBiometricDetected(i2, biometricSourceType, z);
                    }
                }
                Trace.endSection();
                BiometricSourceType biometricSourceType2 = BiometricSourceType.FINGERPRINT;
                KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger2 = keyguardUpdateMonitor4.mLogger;
                keyguardUpdateMonitorLogger2.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(27);
                LogBuffer logBuffer = keyguardUpdateMonitorLogger2.logBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("KeyguardFingerprintLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.int1 = i2;
                logMessageImpl.bool1 = z;
                logBuffer.commit(logMessageObtain);
                Trace.endSection();
            }
        };
        this.mFaceAuthenticationCallback = new FaceManager.AuthenticationCallback() { // from class: com.android.keyguard.KeyguardUpdateMonitor.14
            public final void onAuthenticationAcquired(int i) {
                KeyguardUpdateMonitor.this.handleFaceAcquired(i);
            }

            public final void onAuthenticationError(int i, CharSequence charSequence) {
                KeyguardUpdateMonitor.this.handleFaceError(i, charSequence.toString());
            }

            public final void onAuthenticationFailed() {
                KeyguardUpdateMonitor.this.handleFaceAuthFailed();
            }

            public final void onAuthenticationHelp(int i, CharSequence charSequence) {
                KeyguardUpdateMonitor.this.handleFaceHelp(i, charSequence.toString());
            }

            public final void onAuthenticationSucceeded(FaceManager.AuthenticationResult authenticationResult) {
                KeyguardUpdateMonitor.this.handleFaceAuthenticated(authenticationResult.getUserId(), authenticationResult.isStrongBiometric());
            }
        };
        this.mPostureCallback = new DevicePostureController.Callback() { // from class: com.android.keyguard.KeyguardUpdateMonitor.15
            @Override // com.android.systemui.statusbar.policy.DevicePostureController.Callback
            public final void onPostureChanged(int i) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                int i2 = keyguardUpdateMonitor.mPostureState;
                int i3 = keyguardUpdateMonitor.mConfigFaceAuthSupportedPosture;
                boolean z = i3 == 0 || i2 == i3;
                boolean z2 = i3 == 0 || i == i3;
                keyguardUpdateMonitor.mPostureState = i;
                if (!z || z2) {
                    return;
                }
                keyguardUpdateMonitor.mLogger.d("New posture does not allow face auth, stopping it");
                keyguardUpdateMonitor.updateFaceListeningState(1, FaceAuthUiEvent.FACE_AUTH_UPDATED_POSTURE_CHANGED);
            }
        };
        List list = Collections.EMPTY_LIST;
        this.mFingerprintSensorProperties = list;
        this.mFaceSensorProperties = list;
        this.mUserChangedCallback = new UserTracker.Callback() { // from class: com.android.keyguard.KeyguardUpdateMonitor.21
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                AnonymousClass16 anonymousClass16 = KeyguardUpdateMonitor.this.mHandler;
                anonymousClass16.sendMessage(anonymousClass16.obtainMessage(314, i, 0));
            }

            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanging(int i, Context context2, Runnable runnable) {
                AnonymousClass16 anonymousClass16 = KeyguardUpdateMonitor.this.mHandler;
                anonymousClass16.sendMessage(anonymousClass16.obtainMessage(310, i, 0, runnable));
            }
        };
        this.mTaskStackListener = new TaskStackChangeListener() { // from class: com.android.keyguard.KeyguardUpdateMonitor.23
            @Override // com.android.systemui.shared.system.TaskStackChangeListener
            public final void onTaskStackChangedBackground() {
                ComponentName componentName;
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                try {
                    boolean z = true;
                    ActivityTaskManager.RootTaskInfo rootTaskInfo = keyguardUpdateMonitor.mActivityTaskManager.getRootTaskInfo(1, 1);
                    boolean z2 = keyguardUpdateMonitor.mAllowFingerprintOnCurrentOccludingActivity;
                    if (rootTaskInfo == null || (componentName = rootTaskInfo.topActivity) == null || TextUtils.isEmpty(componentName.getPackageName()) || !keyguardUpdateMonitor.mAllowFingerprintOnOccludingActivitiesFromPackage.contains(rootTaskInfo.topActivity.getPackageName()) || !rootTaskInfo.visible) {
                        z = false;
                    }
                    keyguardUpdateMonitor.mAllowFingerprintOnCurrentOccludingActivity = z;
                    if (z != z2) {
                        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger2 = keyguardUpdateMonitor.mLogger;
                        keyguardUpdateMonitorLogger2.getClass();
                        LogLevel logLevel = LogLevel.VERBOSE;
                        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(15);
                        LogBuffer logBuffer = keyguardUpdateMonitorLogger2.logBuffer;
                        LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
                        ((LogMessageImpl) logMessageObtain).bool1 = z;
                        logBuffer.commit(logMessageObtain);
                        keyguardUpdateMonitor.updateFingerprintListeningState(2);
                    }
                    ActivityTaskManager.RootTaskInfo rootTaskInfo2 = keyguardUpdateMonitor.mActivityTaskManager.getRootTaskInfo(0, 4);
                    if (rootTaskInfo2 == null) {
                        return;
                    }
                    KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger3 = keyguardUpdateMonitor.mLogger;
                    boolean z3 = rootTaskInfo2.visible;
                    keyguardUpdateMonitorLogger3.getClass();
                    LogLevel logLevel2 = LogLevel.VERBOSE;
                    KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda32 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(9);
                    LogBuffer logBuffer2 = keyguardUpdateMonitorLogger3.logBuffer;
                    LogMessage logMessageObtain2 = logBuffer2.obtain("KeyguardUpdateMonitorLog", logLevel2, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda32, null);
                    ((LogMessageImpl) logMessageObtain2).bool1 = z3;
                    logBuffer2.commit(logMessageObtain2);
                    AnonymousClass16 anonymousClass16 = keyguardUpdateMonitor.mHandler;
                    anonymousClass16.sendMessage(anonymousClass16.obtainMessage(335, Boolean.valueOf(rootTaskInfo2.visible)));
                } catch (RemoteException e) {
                    keyguardUpdateMonitor.mLogger.logException("unable to check task stack ", e);
                }
            }
        };
        this.mContext = context;
        this.mSubscriptionManager = subscriptionManager;
        this.mUserTracker = userTracker;
        this.mTelephonyListenerManager = telephonyListenerManager;
        this.mDeviceProvisioned = Settings.Global.getInt(context.getContentResolver(), "device_provisioned", 0) != 0;
        this.mStrongAuthTracker = new StrongAuthTracker(context);
        this.mBackgroundExecutor = executor;
        this.mMainExecutor = executor2;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mInteractionJankMonitor = interactionJankMonitor;
        this.mLatencyTracker = latencyTracker;
        this.mStatusBarStateController = statusBarStateController;
        statusBarStateController.addCallback(r0);
        this.mStatusBarState = statusBarStateController.getState();
        this.mLockPatternUtils = lockPatternUtils;
        this.mAuthController = authController;
        dumpManager.registerDumpable(this);
        this.mSensorPrivacyManager = sensorPrivacyManager;
        this.mActiveUnlockConfig = activeUnlockConfig;
        this.mLogger = keyguardUpdateMonitorLogger;
        this.mSimLogger = simLogger;
        this.mUiEventLogger = uiEventLogger;
        this.mSessionTrackerProvider = provider;
        this.mTrustManager = trustManager;
        this.mUserManager = userManager;
        this.mDreamManager = iDreamManager;
        this.mTelephonyManager = telephonyManager;
        this.mDevicePolicyManager = devicePolicyManager;
        this.mPackageManager = packageManager;
        this.mFpm = fingerprintManager;
        this.mFaceManager = faceManager;
        this.mFaceAcquiredInfoIgnoreList = (Set) Arrays.stream(context.getResources().getIntArray(R.array.config_face_acquire_device_entry_ignorelist)).boxed().collect(Collectors.toSet());
        this.mBiometricManager = biometricManager;
        this.mConfigFaceAuthSupportedPosture = context.getResources().getInteger(R.integer.config_face_auth_supported_posture);
        this.mFaceWakeUpTriggersConfig = faceWakeUpTriggersConfig;
        this.mCarrierConfigManager = carrierConfigManager;
        this.mAllowFingerprintOnOccludingActivitiesFromPackage = (Set) Arrays.stream(context.getResources().getStringArray(R.array.config_fingerprint_listen_on_occluding_activity_packages)).collect(Collectors.toSet());
        this.mDevicePostureController = devicePostureController;
        this.mTaskStackChangeListeners = taskStackChangeListeners;
        this.mActivityTaskManager = iActivityTaskManager;
        this.mSelectedUserInteractor = selectedUserInteractor;
        if (optional.orElse(null) != null) {
            throw new ClassCastException();
        }
        this.mIsSystemUser = userManager.isSystemUser();
        ?? r6 = new Handler(looper) { // from class: com.android.keyguard.KeyguardUpdateMonitor.16
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                BiometricSourceType biometricSourceType;
                int i = message.what;
                int i2 = 0;
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                switch (i) {
                    case 301:
                        int i3 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.getClass();
                        Assert.isMainThread();
                        while (i2 < keyguardUpdateMonitor.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor.mCallbacks.get(i2)).get();
                            if (keyguardUpdateMonitorCallback != null) {
                                keyguardUpdateMonitorCallback.onTimeChanged();
                            }
                            i2++;
                        }
                        break;
                    case 302:
                        keyguardUpdateMonitor.handleBatteryUpdate((BatteryStatus) message.obj);
                        break;
                    case 303:
                    case 305:
                    case VpnErrorValues.ERROR_VPN_RECREATE_PROFILE_FAIL /* 307 */:
                    case 311:
                    case 313:
                    case 315:
                    case 316:
                    case 317:
                    case 323:
                    case 324:
                    case 325:
                    case 326:
                    case 327:
                    case CustomDeviceManager.SOURCE_ADDRESS /* 331 */:
                    case 337:
                    case 343:
                    default:
                        keyguardUpdateMonitor.handleSecMessage(message);
                        break;
                    case 304:
                        keyguardUpdateMonitor.handleSimStateChange(message.arg1, message.arg2, ((Integer) message.obj).intValue());
                        break;
                    case VpnErrorValues.ERROR_STOPPING_CONNECTION_BEFORE_REMOVING /* 306 */:
                        keyguardUpdateMonitor.handlePhoneStateChanged((String) message.obj);
                        break;
                    case 308:
                        int i4 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.getClass();
                        Assert.isMainThread();
                        while (i2 < keyguardUpdateMonitor.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback2 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor.mCallbacks.get(i2)).get();
                            if (keyguardUpdateMonitorCallback2 != null) {
                                keyguardUpdateMonitorCallback2.onDeviceProvisioned();
                            }
                            i2++;
                        }
                        if (keyguardUpdateMonitor.mDeviceProvisionedObserver != null) {
                            keyguardUpdateMonitor.mContext.getContentResolver().unregisterContentObserver(keyguardUpdateMonitor.mDeviceProvisionedObserver);
                            keyguardUpdateMonitor.mDeviceProvisionedObserver = null;
                            break;
                        }
                        break;
                    case 309:
                        keyguardUpdateMonitor.handleDevicePolicyManagerStateChanged(message.arg1);
                        break;
                    case 310:
                        keyguardUpdateMonitor.handleUserSwitching(message.arg1, (Runnable) message.obj);
                        break;
                    case 312:
                        keyguardUpdateMonitor.handleKeyguardReset();
                        break;
                    case 314:
                        keyguardUpdateMonitor.handleUserSwitchComplete(message.arg1);
                        break;
                    case 318:
                        int i5 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.handleReportEmergencyCallAction();
                        break;
                    case 319:
                        Trace.beginSection("KeyguardUpdateMonitor#handler MSG_STARTED_WAKING_UP");
                        keyguardUpdateMonitor.handleStartedWakingUp(message.arg1);
                        Trace.endSection();
                        break;
                    case 320:
                        keyguardUpdateMonitor.handleFinishedGoingToSleep(message.arg1);
                        break;
                    case 321:
                        keyguardUpdateMonitor.handleStartedGoingToSleep(message.arg1);
                        break;
                    case 322:
                        keyguardUpdateMonitor.handlePrimaryBouncerChanged(message.arg1, message.arg2);
                        break;
                    case 328:
                        SimLogger simLogger2 = keyguardUpdateMonitor.mSimLogger;
                        simLogger2.getClass();
                        LogBuffer.log$default(simLogger2.logBuffer, "SimLog", LogLevel.VERBOSE, "onSubscriptionInfoChanged()");
                        keyguardUpdateMonitor.mBackgroundExecutor.execute(new KeyguardUpdateMonitor$$ExternalSyntheticLambda4(keyguardUpdateMonitor, 4));
                        break;
                    case 329:
                        int i6 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.callbacksRefreshCarrierInfo(null);
                        break;
                    case 330:
                        keyguardUpdateMonitor.handleServiceStateChange(message.arg1, (ServiceState) message.obj);
                        break;
                    case CustomDeviceManager.DESTINATION_ADDRESS /* 332 */:
                        Trace.beginSection("KeyguardUpdateMonitor#handler MSG_SCREEN_TURNED_OFF");
                        int i7 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.getClass();
                        Assert.isMainThread();
                        keyguardUpdateMonitor.mHardwareFingerprintUnavailableRetryCount = 0;
                        keyguardUpdateMonitor.mHardwareFaceUnavailableRetryCount = 0;
                        Trace.endSection();
                        break;
                    case 333:
                        keyguardUpdateMonitor.handleDreamingStateChanged(message.arg1);
                        break;
                    case 334:
                        keyguardUpdateMonitor.handleUserUnlocked(message.arg1);
                        break;
                    case 335:
                        keyguardUpdateMonitor.setAssistantVisible(((Boolean) message.obj).booleanValue());
                        break;
                    case 336:
                        keyguardUpdateMonitor.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_FP_AUTHENTICATED);
                        break;
                    case 338:
                        keyguardUpdateMonitor.updateTelephonyCapable(((Boolean) message.obj).booleanValue());
                        break;
                    case 339:
                        String str = (String) message.obj;
                        int i8 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.getClass();
                        Assert.isMainThread();
                        keyguardUpdateMonitor.mLogger.d("handleTimeZoneUpdate");
                        while (i2 < keyguardUpdateMonitor.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback3 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor.mCallbacks.get(i2)).get();
                            if (keyguardUpdateMonitorCallback3 != null) {
                                keyguardUpdateMonitorCallback3.onTimeZoneChanged(TimeZone.getTimeZone(str));
                                keyguardUpdateMonitorCallback3.onTimeChanged();
                            }
                            i2++;
                        }
                        break;
                    case 340:
                        int i9 = message.arg1;
                        int i10 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.getClass();
                        Assert.isMainThread();
                        boolean zIsUserUnlocked = keyguardUpdateMonitor.mUserManager.isUserUnlocked(i9);
                        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger2 = keyguardUpdateMonitor.mLogger;
                        keyguardUpdateMonitorLogger2.getClass();
                        LogLevel logLevel = LogLevel.DEBUG;
                        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(18);
                        LogBuffer logBuffer = keyguardUpdateMonitorLogger2.logBuffer;
                        LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
                        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                        logMessageImpl.int1 = i9;
                        logMessageImpl.bool1 = zIsUserUnlocked;
                        logBuffer.commit(logMessageObtain);
                        keyguardUpdateMonitor.mUserIsUnlocked.put(i9, zIsUserUnlocked);
                        break;
                    case 341:
                        keyguardUpdateMonitor.handleUserRemoved(message.arg1);
                        break;
                    case 342:
                        boolean zBooleanValue = ((Boolean) message.obj).booleanValue();
                        int i11 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.getClass();
                        Assert.isMainThread();
                        keyguardUpdateMonitor.setKeyguardGoingAway(zBooleanValue);
                        break;
                    case 344:
                        String str2 = (String) message.obj;
                        int i12 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.getClass();
                        Assert.isMainThread();
                        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger3 = keyguardUpdateMonitor.mLogger;
                        keyguardUpdateMonitorLogger3.getClass();
                        LogLevel logLevel2 = LogLevel.DEBUG;
                        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda32 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(10);
                        LogBuffer logBuffer2 = keyguardUpdateMonitorLogger3.logBuffer;
                        LogMessage logMessageObtain2 = logBuffer2.obtain("KeyguardUpdateMonitorLog", logLevel2, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda32, null);
                        ((LogMessageImpl) logMessageObtain2).str1 = str2;
                        logBuffer2.commit(logMessageObtain2);
                        while (i2 < keyguardUpdateMonitor.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback4 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor.mCallbacks.get(i2)).get();
                            if (keyguardUpdateMonitorCallback4 != null) {
                                keyguardUpdateMonitorCallback4.onTimeFormatChanged(str2);
                            }
                            i2++;
                        }
                        break;
                    case 345:
                        int i13 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.getClass();
                        Assert.isMainThread();
                        while (i2 < keyguardUpdateMonitor.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback5 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor.mCallbacks.get(i2)).get();
                            if (keyguardUpdateMonitorCallback5 != null) {
                                keyguardUpdateMonitorCallback5.onRequireUnlockForNfc();
                            }
                            i2++;
                        }
                        break;
                    case 346:
                        int i14 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.getClass();
                        Assert.isMainThread();
                        while (i2 < keyguardUpdateMonitor.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback6 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor.mCallbacks.get(i2)).get();
                            if (keyguardUpdateMonitorCallback6 != null) {
                                keyguardUpdateMonitorCallback6.onKeyguardDismissAnimationFinished();
                            }
                            i2++;
                        }
                        break;
                    case 347:
                        Intent intent = (Intent) message.obj;
                        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger4 = keyguardUpdateMonitor.mLogger;
                        keyguardUpdateMonitorLogger4.getClass();
                        LogLevel logLevel3 = LogLevel.VERBOSE;
                        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda33 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(17);
                        LogBuffer logBuffer3 = keyguardUpdateMonitorLogger4.logBuffer;
                        LogMessage logMessageObtain3 = logBuffer3.obtain("KeyguardUpdateMonitorLog", logLevel3, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda33, null);
                        LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain3;
                        logMessageImpl2.int1 = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                        logMessageImpl2.str1 = intent.getStringExtra("android.telephony.extra.SPN");
                        logMessageImpl2.str2 = intent.getStringExtra("android.telephony.extra.PLMN");
                        logBuffer3.commit(logMessageObtain3);
                        keyguardUpdateMonitor.callbacksRefreshCarrierInfo(intent);
                        break;
                    case 348:
                        int i15 = message.arg1;
                        int i16 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.getClass();
                        if (i15 == 2) {
                            biometricSourceType = BiometricSourceType.FINGERPRINT;
                        } else if (i15 == 8) {
                            biometricSourceType = BiometricSourceType.FACE;
                        }
                        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger5 = keyguardUpdateMonitor.mLogger;
                        keyguardUpdateMonitorLogger5.getClass();
                        LogLevel logLevel4 = LogLevel.DEBUG;
                        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda34 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(19);
                        LogBuffer logBuffer4 = keyguardUpdateMonitorLogger5.logBuffer;
                        LogMessage logMessageObtain4 = logBuffer4.obtain("KeyguardUpdateMonitorLog", logLevel4, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda34, null);
                        ((LogMessageImpl) logMessageObtain4).str1 = String.valueOf(biometricSourceType);
                        logBuffer4.commit(logMessageObtain4);
                        Assert.isMainThread();
                        while (i2 < keyguardUpdateMonitor.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback7 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor.mCallbacks.get(i2)).get();
                            if (keyguardUpdateMonitorCallback7 != null) {
                                keyguardUpdateMonitorCallback7.onBiometricEnrollmentStateChanged(biometricSourceType);
                            }
                            i2++;
                        }
                        break;
                }
            }
        };
        this.mHandler = r6;
        this.mTimeFormatChangeObserver = new ContentObserver(r6) { // from class: com.android.keyguard.KeyguardUpdateMonitor.17
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                AnonymousClass16 anonymousClass16 = keyguardUpdateMonitor.mHandler;
                anonymousClass16.sendMessage(anonymousClass16.obtainMessage(344, Settings.System.getString(keyguardUpdateMonitor.mContext.getContentResolver(), SettingsHelper.INDEX_TIME_12_24)));
            }
        };
    }

    public static synchronized int getCurrentUser() {
        return sCurrentUser;
    }

    public final void callbacksRefreshCarrierInfo(Intent intent) {
        Assert.isMainThread();
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onRefreshCarrierInfo(intent);
            }
        }
    }

    public final void clearFingerprintRecognized() {
        clearFingerprintRecognized(-10000);
    }

    public void dispatchDreamingStarted() {
        AnonymousClass16 anonymousClass16 = this.mHandler;
        anonymousClass16.sendMessage(anonymousClass16.obtainMessage(333, 1, 0));
    }

    public void dispatchDreamingStopped() {
        AnonymousClass16 anonymousClass16 = this.mHandler;
        anonymousClass16.sendMessage(anonymousClass16.obtainMessage(333, 0, 0));
    }

    public void dispatchStartedWakingUp(int i) {
        synchronized (this) {
            this.mDeviceInteractive = true;
        }
        AnonymousClass16 anonymousClass16 = this.mHandler;
        anonymousClass16.sendMessage(anonymousClass16.obtainMessage(319, i, 0));
    }

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    @NeverCompile
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder sbM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "KeyguardUpdateMonitor state:", "  forceIsDismissible="), this.mForceIsDismissible, printWriter, "  forceIsDismissibleIsKeepingDeviceUnlocked=");
        sbM.append(forceIsDismissibleIsKeepingDeviceUnlocked());
        printWriter.println(sbM.toString());
        printWriter.println("  getUserHasTrust()=" + getUserHasTrust(this.mSelectedUserInteractor.getSelectedUserId()));
        printWriter.println("  getUserUnlockedWithBiometric()=" + getUserUnlockedWithBiometric(this.mSelectedUserInteractor.getSelectedUserId()));
        printWriter.println("  SIM States:");
        Iterator it = this.mSimDatasBySlotId.values().iterator();
        while (it.hasNext()) {
            printWriter.println("    " + ((SimData) it.next()).toString());
        }
        printWriter.println("  Subs:");
        if (this.mSubscriptionInfo != null) {
            for (int i = 0; i < this.mSubscriptionInfo.size(); i++) {
                printWriter.println("    " + this.mSubscriptionInfo.get(i));
            }
        }
        printWriter.println("  Current active data subId=" + this.mActiveMobileDataSubscription);
        printWriter.println("  Service states:");
        for (Integer num : this.mServiceStates.keySet()) {
            StringBuilder sbM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(num.intValue(), "    ", "=");
            sbM2.append(this.mServiceStates.get(num));
            printWriter.println(sbM2.toString());
        }
        dumpAllUsers(printWriter);
    }

    public final boolean forceIsDismissibleIsKeepingDeviceUnlocked() {
        return this.mFoldGracePeriodProvider.isEnabled() && this.mForceIsDismissible && isUnlockingWithBiometricAllowed(false);
    }

    public final List getFilteredSubscriptionInfo() {
        boolean z;
        List subscriptionInfo = getSubscriptionInfo(false);
        ArrayList arrayList = (ArrayList) subscriptionInfo;
        if (arrayList.size() == 2) {
            SubscriptionInfo subscriptionInfo2 = (SubscriptionInfo) arrayList.get(0);
            SubscriptionInfo subscriptionInfo3 = (SubscriptionInfo) arrayList.get(1);
            if (subscriptionInfo2.getGroupUuid() != null && subscriptionInfo2.getGroupUuid().equals(subscriptionInfo3.getGroupUuid()) && (subscriptionInfo2.isOpportunistic() || subscriptionInfo3.isOpportunistic())) {
                CarrierConfigManager carrierConfigManager = this.mCarrierConfigManager;
                if (carrierConfigManager != null) {
                    z = carrierConfigManager.getConfigForSubId(subscriptionInfo2.isOpportunistic() ? subscriptionInfo2.getSubscriptionId() : subscriptionInfo3.getSubscriptionId()).getBoolean("always_show_primary_signal_bar_in_opportunistic_network_boolean");
                } else {
                    z = CarrierConfigManager.getDefaultConfig().getBoolean("always_show_primary_signal_bar_in_opportunistic_network_boolean");
                }
                if (z) {
                    if (!subscriptionInfo2.isOpportunistic()) {
                        subscriptionInfo2 = subscriptionInfo3;
                    }
                    arrayList.remove(subscriptionInfo2);
                    return subscriptionInfo;
                }
                if (subscriptionInfo2.getSubscriptionId() == this.mActiveMobileDataSubscription) {
                    subscriptionInfo2 = subscriptionInfo3;
                }
                arrayList.remove(subscriptionInfo2);
            }
        }
        return subscriptionInfo;
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    public final boolean getIsFaceAuthenticated() {
        BiometricAuthenticated biometricAuthenticated = this.mUserFaceAuthenticated.get(this.mSelectedUserInteractor.getSelectedUserId());
        if (biometricAuthenticated != null) {
            return biometricAuthenticated.mAuthenticated;
        }
        return false;
    }

    public final int getNextSubIdForState(int i) {
        int i2 = 0;
        List subscriptionInfo = getSubscriptionInfo(false);
        int i3 = -1;
        int i4 = Integer.MAX_VALUE;
        while (true) {
            ArrayList arrayList = (ArrayList) subscriptionInfo;
            if (i2 >= arrayList.size()) {
                return i3;
            }
            SubscriptionInfo subscriptionInfo2 = (SubscriptionInfo) arrayList.get(i2);
            int subscriptionId = subscriptionInfo2.getSubscriptionId();
            int simSlotIndex = subscriptionInfo2.getSimSlotIndex();
            if (i == getSimStateForSlotId(simSlotIndex) && i4 > simSlotIndex) {
                if (isSimPinPassed(simSlotIndex, i)) {
                    this.mLogger.v("getNextSubIdForState() PIN_REQUIRED happen on isSimPinPassed slot");
                } else if (LsRune.SECURITY_ESIM && isESimRemoveButtonClicked()) {
                    this.mLogger.v("getNextSubIdForState() " + i + " happen on isESimRemoveButtonClicked slotId = " + simSlotIndex);
                } else {
                    i4 = simSlotIndex;
                    i3 = subscriptionId;
                }
            }
            i2++;
        }
    }

    public final int getSimStateForSlotId(int i) {
        synchronized (this.mSimDataLockObject) {
            try {
                if (!this.mSimDatasBySlotId.containsKey(Integer.valueOf(i))) {
                    return 0;
                }
                return ((SimData) this.mSimDatasBySlotId.get(Integer.valueOf(i))).simState;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getSlotId(int i) {
        int i2;
        synchronized (this.mSimDataLockObject) {
            try {
                HashMap map = this.mSimDatasBySlotId;
                int slotIndex = SubscriptionManager.getSlotIndex(i);
                if (!map.containsKey(Integer.valueOf(slotIndex))) {
                    refreshSimState(i, slotIndex);
                }
                SimData simData = (SimData) map.get(Integer.valueOf(slotIndex));
                i2 = simData != null ? simData.slotId : -1;
            } catch (Throwable th) {
                throw th;
            }
        }
        return i2;
    }

    public final List getSubscriptionInfo(boolean z) {
        if (this.mSubscriptionInfo == null || z) {
            this.mSubscriptionInfo = this.mSubscriptionManager.getCompleteActiveSubscriptionInfoList().stream().filter(new KeyguardUpdateMonitor$$ExternalSyntheticLambda7()).toList();
        }
        return new ArrayList(this.mSubscriptionInfo);
    }

    public final SubscriptionInfo getSubscriptionInfoForSubId(int i) {
        int i2 = 0;
        List subscriptionInfo = getSubscriptionInfo(false);
        while (true) {
            ArrayList arrayList = (ArrayList) subscriptionInfo;
            if (i2 >= arrayList.size()) {
                return null;
            }
            SubscriptionInfo subscriptionInfo2 = (SubscriptionInfo) arrayList.get(i2);
            if (i == subscriptionInfo2.getSubscriptionId()) {
                return subscriptionInfo2;
            }
            i2++;
        }
    }

    public boolean getUserCanSkipBouncer(int i) {
        return getUserHasTrust(i) || getUserUnlockedWithBiometric(i) || forceIsDismissibleIsKeepingDeviceUnlocked();
    }

    public final boolean getUserHasTrust(int i) {
        return !isSimPinSecure() && this.mUserHasTrust.get(i) && isUnlockingWithBiometricAllowed(true);
    }

    public final boolean getUserTrustIsManaged(int i) {
        return this.mUserTrustIsManaged.get(i) && !isSimPinSecure();
    }

    public final boolean getUserUnlockedWithBiometric(int i) {
        BiometricAuthenticated biometricAuthenticated = this.mUserFingerprintAuthenticated.get(i);
        return (biometricAuthenticated != null && biometricAuthenticated.mAuthenticated && isUnlockingWithBiometricAllowed(biometricAuthenticated.mIsStrongBiometric)) || getUserUnlockedWithFace(i);
    }

    public boolean getUserUnlockedWithBiometricAndIsBypassing(int i) {
        BiometricAuthenticated biometricAuthenticated = this.mUserFingerprintAuthenticated.get(i);
        if (biometricAuthenticated != null && biometricAuthenticated.mAuthenticated && isUnlockingWithBiometricAllowed(biometricAuthenticated.mIsStrongBiometric)) {
            return true;
        }
        return getUserUnlockedWithFace(i) && this.mKeyguardBypassController.canBypass();
    }

    public final boolean getUserUnlockedWithFace(int i) {
        BiometricAuthenticated biometricAuthenticated = this.mUserFaceAuthenticated.get(i);
        return biometricAuthenticated != null && biometricAuthenticated.mAuthenticated && isUnlockingWithBiometricAllowed(biometricAuthenticated.mIsStrongBiometric);
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x0009  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void handleBatteryUpdate(BatteryStatus batteryStatus) {
        boolean z;
        Assert.isMainThread();
        BatteryStatus batteryStatus2 = this.mBatteryStatus;
        if (batteryStatus2 == null) {
            z = true;
        } else {
            boolean zIsPluggedIn = batteryStatus.isPluggedIn();
            boolean zIsPluggedIn2 = batteryStatus2.isPluggedIn();
            boolean z2 = zIsPluggedIn2 && zIsPluggedIn && batteryStatus2.status != batteryStatus.status;
            if (zIsPluggedIn2 == zIsPluggedIn && !z2 && batteryStatus2.level == batteryStatus.level && ((!zIsPluggedIn || batteryStatus.maxChargingWattage == batteryStatus2.maxChargingWattage) && batteryStatus2.present == batteryStatus.present && batteryStatus2.incompatibleCharger.equals(batteryStatus.incompatibleCharger) && batteryStatus.chargingStatus == batteryStatus2.chargingStatus)) {
                z = false;
            }
        }
        this.mBatteryStatus = batteryStatus;
        if (z) {
            KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
            keyguardUpdateMonitorLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(22);
            LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
            LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.bool1 = batteryStatus != null;
            logMessageImpl.int1 = batteryStatus != null ? batteryStatus.status : -1;
            logMessageImpl.int2 = batteryStatus != null ? batteryStatus.chargingStatus : -1;
            logMessageImpl.long1 = batteryStatus != null ? batteryStatus.level : -1;
            logMessageImpl.long2 = batteryStatus != null ? batteryStatus.maxChargingWattage : -1;
            logMessageImpl.str1 = String.valueOf(batteryStatus != null ? batteryStatus.plugged : -1);
            logBuffer.commit(logMessageObtain);
            for (int i = 0; i < this.mCallbacks.size(); i++) {
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i)).get();
                if (keyguardUpdateMonitorCallback != null) {
                    keyguardUpdateMonitorCallback.onRefreshBatteryInfo(batteryStatus);
                }
            }
        }
    }

    public void handleDevicePolicyManagerStateChanged(int i) {
        Assert.isMainThread();
        updateFingerprintListeningState(2);
        updateSecondaryLockscreenRequirement(i);
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onDevicePolicyManagerStateChanged();
            }
        }
    }

    public void handleDreamingStateChanged(int i) {
        Assert.isMainThread();
        this.mIsDreaming = i == 1;
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onDreamingStateChanged(this.mIsDreaming);
            }
        }
        updateFingerprintListeningState(2);
        if (this.mIsDreaming) {
            updateFaceListeningState(1, FaceAuthUiEvent.FACE_AUTH_STOPPED_DREAM_STARTED);
        }
    }

    public final void handleFaceAcquired(int i) {
        Assert.isMainThread();
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricAcquired(BiometricSourceType.FACE, i);
            }
        }
        if (this.mActiveUnlockConfig.faceAcquireInfoToTriggerBiometricFailOn.contains(Integer.valueOf(i))) {
            requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin.BIOMETRIC_FAIL, "faceAcquireInfo-" + i);
        }
    }

    public void handleFaceAuthFailed() {
        String str;
        Assert.isMainThread();
        if (this.mKeyguardBypassController.canBypass()) {
            str = "bypass";
        } else {
            int i = SceneContainerFlag.$r8$clinit;
            str = this.mAlternateBouncerShowing ? "alternateBouncer" : this.mPrimaryBouncerFullyShown ? "bouncer" : "udfpsFpDown";
        }
        requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin.BIOMETRIC_FAIL, "faceFailure-".concat(str));
        this.mLogger.d("onFaceAuthFailed");
        this.mFaceCancelSignal = null;
        setFaceRunningState(0);
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricAuthFailed(BiometricSourceType.FACE);
            }
        }
        handleFaceHelp(-2, this.mContext.getString(R.string.kg_face_not_recognized));
    }

    public void handleFaceAuthenticated(int i, boolean z) {
        Trace.beginSection("KeyGuardUpdateMonitor#handlerFaceAuthenticated");
        try {
            if (this.mGoingToSleep) {
                this.mLogger.d("Aborted successful auth because device is going to sleep.");
                return;
            }
            int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
            if (selectedUserId != i) {
                KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
                keyguardUpdateMonitorLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(15);
                LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
                ((LogMessageImpl) logMessageObtain).int1 = i;
                logBuffer.commit(logMessageObtain);
                return;
            }
            if (isFaceDisabled(selectedUserId)) {
                KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger2 = this.mLogger;
                keyguardUpdateMonitorLogger2.getClass();
                LogLevel logLevel2 = LogLevel.DEBUG;
                KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda12 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(17);
                LogBuffer logBuffer2 = keyguardUpdateMonitorLogger2.logBuffer;
                LogMessage logMessageObtain2 = logBuffer2.obtain("KeyguardUpdateMonitorLog", logLevel2, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda12, null);
                ((LogMessageImpl) logMessageObtain2).int1 = selectedUserId;
                logBuffer2.commit(logMessageObtain2);
                return;
            }
            KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger3 = this.mLogger;
            keyguardUpdateMonitorLogger3.getClass();
            LogLevel logLevel3 = LogLevel.DEBUG;
            KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda13 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(20);
            LogBuffer logBuffer3 = keyguardUpdateMonitorLogger3.logBuffer;
            LogMessage logMessageObtain3 = logBuffer3.obtain("KeyguardUpdateMonitorLog", logLevel3, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda13, null);
            ((LogMessageImpl) logMessageObtain3).int1 = selectedUserId;
            logBuffer3.commit(logMessageObtain3);
            onFaceAuthenticated(selectedUserId, z);
            setFaceRunningState(0);
            Trace.endSection();
        } finally {
            setFaceRunningState(0);
        }
    }

    public void handleFaceError(int i, String str) {
        int i2;
        boolean z;
        Assert.isMainThread();
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(25);
        LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.int1 = i;
        logBuffer.commit(logMessageObtain);
        if (hasCallbacks(this.mFaceCancelNotReceived)) {
            removeCallbacks(this.mFaceCancelNotReceived);
        }
        this.mFaceCancelSignal = null;
        boolean zIsSensorPrivacyEnabled = this.mSensorPrivacyManager.isSensorPrivacyEnabled(1, 2);
        if (i == 5 && this.mFaceRunningState == 3) {
            setFaceRunningState(0);
            updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_DURING_CANCELLATION);
        } else {
            setFaceRunningState(0);
        }
        boolean z2 = i == 1;
        if ((z2 || i == 2) && (i2 = this.mHardwareFaceUnavailableRetryCount) < 20) {
            this.mHardwareFaceUnavailableRetryCount = i2 + 1;
            removeCallbacks(this.mRetryFaceAuthentication);
            postDelayed(this.mRetryFaceAuthentication, 500L);
        }
        if (i == 9) {
            z = !this.mFaceLockedOutPermanent;
            this.mFaceLockedOutPermanent = true;
            if (isFaceClass3()) {
                updateFingerprintListeningState(1);
            }
        } else {
            z = false;
        }
        if (z2 && zIsSensorPrivacyEnabled) {
            str = this.mContext.getString(R.string.kg_face_sensor_privacy_enabled);
        }
        for (int i3 = 0; i3 < this.mCallbacks.size(); i3++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i3)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricError(i, str, BiometricSourceType.FACE);
            }
        }
        if (z) {
            notifyLockedOutStateChanged(BiometricSourceType.FACE);
        }
        if (this.mActiveUnlockConfig.faceErrorsToTriggerBiometricFailOn.contains(Integer.valueOf(i))) {
            requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin.BIOMETRIC_FAIL, "faceError-" + i);
        }
    }

    public final void handleFaceHelp(int i, String str) {
        if (this.mFaceAcquiredInfoIgnoreList.contains(Integer.valueOf(i))) {
            return;
        }
        Assert.isMainThread();
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricHelp(i, str, BiometricSourceType.FACE);
            }
        }
    }

    public final void handleFaceLockoutReset(int i) {
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(2);
        LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
        ((LogMessageImpl) logMessageObtain).int1 = i;
        logBuffer.commit(logMessageObtain);
        boolean z = this.mFaceLockedOutPermanent;
        boolean z2 = i == 2;
        this.mFaceLockedOutPermanent = z2;
        boolean z3 = z2 != z;
        postDelayed(new KeyguardUpdateMonitor$$ExternalSyntheticLambda4(this, 8), VolteConstants.ErrorCode.BUSY_EVERYWHERE);
        if (z3) {
            notifyLockedOutStateChanged(BiometricSourceType.FACE);
        }
    }

    public void handleFingerprintAcquired(int i) {
        Assert.isMainThread();
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricAcquired(BiometricSourceType.FINGERPRINT, i);
            }
        }
    }

    public void handleFingerprintAuthFailed() {
        Assert.isMainThread();
        if (hasCallbacks(this.mFpCancelNotReceived)) {
            this.mLogger.d("handleFingerprintAuthFailed() triggered while waiting for cancellation, removing watchdog");
            removeCallbacks(this.mFpCancelNotReceived);
        }
        this.mLogger.d("handleFingerprintAuthFailed");
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricAuthFailed(BiometricSourceType.FINGERPRINT);
            }
        }
        if (isUdfpsSupported()) {
            handleFingerprintHelp(-1, this.mContext.getString(android.R.string.mediasize_iso_c0));
        } else {
            handleFingerprintHelp(-1, this.mContext.getString(android.R.string.mediasize_iso_a5));
        }
    }

    public void handleFingerprintAuthenticated(int i, boolean z) {
        Trace.beginSection("KeyGuardUpdateMonitor#handlerFingerPrintAuthenticated");
        if (hasCallbacks(this.mFpCancelNotReceived)) {
            this.mLogger.d("handleFingerprintAuthenticated() triggered while waiting for cancellation, removing watchdog");
            removeCallbacks(this.mFpCancelNotReceived);
        }
        try {
            int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
            if (selectedUserId != i) {
                KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
                keyguardUpdateMonitorLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(23);
                LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("KeyguardFingerprintLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
                ((LogMessageImpl) logMessageObtain).int1 = i;
                logBuffer.commit(logMessageObtain);
                return;
            }
            if (!isFingerprintDisabled(selectedUserId)) {
                onFingerprintAuthenticated(selectedUserId, z);
                setFingerprintRunningState(0);
                Trace.endSection();
                return;
            }
            KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger2 = this.mLogger;
            keyguardUpdateMonitorLogger2.getClass();
            LogLevel logLevel2 = LogLevel.DEBUG;
            KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(3);
            LogBuffer logBuffer2 = keyguardUpdateMonitorLogger2.logBuffer;
            LogMessage logMessageObtain2 = logBuffer2.obtain("KeyguardFingerprintLog", logLevel2, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
            ((LogMessageImpl) logMessageObtain2).int1 = selectedUserId;
            logBuffer2.commit(logMessageObtain2);
        } finally {
            setFingerprintRunningState(0);
        }
    }

    public void handleFingerprintError(int i, String str) {
        boolean z;
        Assert.isMainThread();
        if (hasCallbacks(this.mFpCancelNotReceived)) {
            removeCallbacks(this.mFpCancelNotReceived);
        }
        this.mFingerprintCancelSignal = null;
        if (i == 5 && this.mFingerprintRunningState == 3) {
            setFingerprintRunningState(0);
            updateFingerprintListeningState(2);
        } else {
            setFingerprintRunningState(0);
        }
        if (i == 1) {
            this.mLogger.logRetryAfterFpErrorWithDelay(i, 500, str);
            postDelayed(this.mRetryFingerprintAuthenticationAfterHwUnavailable, 500L);
        }
        if (i == 19) {
            this.mLogger.logRetryAfterFpErrorWithDelay(i, 50, str);
            postDelayed(new KeyguardUpdateMonitor$$ExternalSyntheticLambda4(this, 2), 50L);
        }
        if (i == 9) {
            z = !this.mFingerprintLockedOutPermanent;
            this.mFingerprintLockedOutPermanent = true;
            this.mLogger.d("Fingerprint permanently locked out - requiring stronger auth");
            this.mLockPatternUtils.requireStrongAuth(8, this.mSelectedUserInteractor.getSelectedUserId());
        } else {
            z = false;
        }
        if (i == 7 || i == 9) {
            z |= !this.mFingerprintLockedOut;
            this.mFingerprintLockedOut = true;
            this.mLogger.d("Fingerprint temporarily locked out - requiring stronger auth");
            if (isUdfpsEnrolled()) {
                updateFingerprintListeningState(2);
            }
            stopListeningForFace(FaceAuthUiEvent.FACE_AUTH_STOPPED_FP_LOCKED_OUT);
        }
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(14);
        LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardFingerprintLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = str;
        logMessageImpl.int1 = i;
        logBuffer.commit(logMessageObtain);
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricError(i, str, BiometricSourceType.FINGERPRINT);
            }
        }
        if (z) {
            notifyLockedOutStateChanged(BiometricSourceType.FINGERPRINT);
        }
    }

    public void handleFingerprintHelp(int i, String str) {
        Assert.isMainThread();
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricHelp(i, str, BiometricSourceType.FINGERPRINT);
            }
        }
    }

    public final void handleFingerprintLockoutReset(int i) {
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(18);
        LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardFingerprintLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
        ((LogMessageImpl) logMessageObtain).int1 = i;
        logBuffer.commit(logMessageObtain);
        boolean z = this.mFingerprintLockedOut;
        boolean z2 = this.mFingerprintLockedOutPermanent;
        boolean z3 = i == 1 || i == 2;
        this.mFingerprintLockedOut = z3;
        boolean z4 = i == 2;
        this.mFingerprintLockedOutPermanent = z4;
        boolean z5 = (z3 == z && z4 == z2) ? false : true;
        if (isUdfpsEnrolled()) {
            postDelayed(new KeyguardUpdateMonitor$$ExternalSyntheticLambda4(this, 12), VolteConstants.ErrorCode.BUSY_EVERYWHERE);
        } else {
            if (z && !this.mFingerprintLockedOut) {
                this.mLogger.d("temporaryLockoutReset - stopListeningForFingerprint() to stop detectFingerprint");
                stopListeningForFingerprint();
            }
            updateFingerprintListeningState(2);
        }
        if (z5) {
            notifyLockedOutStateChanged(BiometricSourceType.FINGERPRINT);
        }
    }

    public void handleFinishedGoingToSleep(int i) {
        Assert.isMainThread();
        this.mGoingToSleep = false;
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onFinishedGoingToSleep(i);
            }
        }
        if (!this.mDeviceInteractive) {
            updateFaceListeningState(1, FaceAuthUiEvent.FACE_AUTH_STOPPED_FINISHED_GOING_TO_SLEEP);
        }
        updateFingerprintListeningState(2);
    }

    public void handleKeyguardReset() {
        this.mLogger.d("handleKeyguardReset");
        updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_KEYGUARD_RESET);
        this.mNeedsSlowUnlockTransition = resolveNeedsSlowUnlockTransition();
    }

    public void handlePhoneStateChanged(String str) {
        Assert.isMainThread();
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(6);
        LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
        ((LogMessageImpl) logMessageObtain).str1 = str;
        logBuffer.commit(logMessageObtain);
        if (TelephonyManager.EXTRA_STATE_IDLE.equals(str)) {
            this.mPhoneState = 0;
        } else if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(str)) {
            this.mPhoneState = 2;
        } else if (TelephonyManager.EXTRA_STATE_RINGING.equals(str)) {
            this.mPhoneState = 1;
        }
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onPhoneStateChanged(this.mPhoneState);
            }
        }
    }

    public void handlePrimaryBouncerChanged(int i, int i2) {
        Assert.isMainThread();
        boolean z = this.mPrimaryBouncerIsOrWillBeShowing;
        boolean z2 = this.mPrimaryBouncerFullyShown;
        boolean z3 = i == 1;
        this.mPrimaryBouncerIsOrWillBeShowing = z3;
        boolean z4 = i2 == 1;
        this.mPrimaryBouncerFullyShown = z4;
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(27);
        LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.bool1 = z3;
        logMessageImpl.bool2 = z4;
        logBuffer.commit(logMessageObtain);
        if (this.mPrimaryBouncerFullyShown) {
            this.mSecureCameraLaunched = false;
        } else {
            this.mCredentialAttempted = false;
        }
        if (z != this.mPrimaryBouncerIsOrWillBeShowing) {
            for (int i3 = 0; i3 < this.mCallbacks.size(); i3++) {
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i3)).get();
                if (keyguardUpdateMonitorCallback != null) {
                    keyguardUpdateMonitorCallback.onKeyguardBouncerStateChanged(this.mPrimaryBouncerIsOrWillBeShowing);
                }
            }
            updateFingerprintListeningState(2);
        }
        boolean z5 = this.mPrimaryBouncerFullyShown;
        if (z2 != z5) {
            if (z5) {
                requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin.UNLOCK_INTENT, "bouncerFullyShown");
            }
            for (int i4 = 0; i4 < this.mCallbacks.size(); i4++) {
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback2 = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i4)).get();
                if (keyguardUpdateMonitorCallback2 != null) {
                    keyguardUpdateMonitorCallback2.onKeyguardBouncerFullyShowingChanged(this.mPrimaryBouncerFullyShown);
                }
            }
            updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_PRIMARY_BOUNCER_SHOWN);
        }
    }

    public final void handleReportEmergencyCallAction() {
        Assert.isMainThread();
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onEmergencyCallAction();
            }
        }
    }

    public void handleServiceStateChange(int i, ServiceState serviceState) {
        SimLogger simLogger = this.mSimLogger;
        simLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        SimLogger$$ExternalSyntheticLambda0 simLogger$$ExternalSyntheticLambda0 = new SimLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = simLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("SimLog", logLevel, simLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i;
        logMessageImpl.str1 = String.valueOf(serviceState);
        logBuffer.commit(logMessageObtain);
        if (SubscriptionManager.isValidSubscriptionId(i)) {
            updateTelephonyCapable(true);
            this.mServiceStates.put(Integer.valueOf(i), serviceState);
            callbacksRefreshCarrierInfo(null);
        } else {
            SimLogger simLogger2 = this.mSimLogger;
            simLogger2.getClass();
            LogBuffer.log$default(simLogger2.logBuffer, "SimLog", LogLevel.WARNING, "invalid subId in handleServiceStateChange()");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda9] */
    public void handleSimStateChange(final int i, final int i2, final int i3) {
        Assert.isMainThread();
        SimLogger simLogger = this.mSimLogger;
        String strSimStateToString = TelephonyManager.simStateToString(i3);
        simLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        SimLogger$$ExternalSyntheticLambda0 simLogger$$ExternalSyntheticLambda0 = new SimLogger$$ExternalSyntheticLambda0(3);
        LogBuffer logBuffer = simLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("SimLog", logLevel, simLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logMessageImpl.str1 = strSimStateToString;
        logBuffer.commit(logMessageObtain);
        Log.d("KeyguardUpdateMonitor", "handleSimStateChange(subId=" + i + ", slotId=" + i2 + ", state=" + TelephonyManager.simStateToString(i3) + ")");
        if (isSimPinPassed(i2, i3)) {
            this.mLogger.d("handleSimStateChange isSimPinPassed");
            return;
        }
        boolean z = LsRune.SECURITY_ESIM;
        if (z && isESimRemoveButtonClicked()) {
            dispatchCallback(new Consumer() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda9
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i4 = i;
                    int i5 = i2;
                    int i6 = i3;
                    int i7 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                    ((KeyguardUpdateMonitorCallback) obj).onSimStateChanged(i4, i5, i6);
                }
            });
            return;
        }
        boolean zContains = ABSENT_SIM_STATE_LIST.contains(Integer.valueOf(i3));
        boolean z2 = true;
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            SimLogger simLogger2 = this.mSimLogger;
            simLogger2.getClass();
            LogBuffer.log$default(simLogger2.logBuffer, "SimLog", LogLevel.WARNING, "invalid subId in handleSimStateChange()");
            if (i3 == 1 || i3 == 8) {
                updateTelephonyCapable(true);
            } else if (z) {
                updateEsimState(i2, i3);
            }
        }
        ArrayList arrayList = new ArrayList();
        synchronized (this.mSimDataLockObject) {
            try {
                SimData simData = (SimData) this.mSimDatasBySlotId.get(Integer.valueOf(i2));
                if (simData == null) {
                    this.mSimDatasBySlotId.put(Integer.valueOf(i2), new SimData(i3, i2, i));
                } else {
                    if (simData.simState == i3 && simData.subId == i && simData.slotId == i2) {
                        z2 = false;
                    }
                    simData.simState = i3;
                    simData.subId = i;
                    simData.slotId = i2;
                }
                Log.d("KeyguardUpdateMonitor", "    handleSimStateChange changed=" + z2 + ", becameAbsent=" + zContains);
                if (z2 || zContains) {
                    arrayList.add(new SimData(i3, i2, i));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        sendOnSimStateChangedCallback(arrayList);
    }

    public void handleStartedGoingToSleep(int i) {
        Assert.isMainThread();
        setForceIsDismissibleKeyguard(false);
        clearFingerprintRecognized();
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onStartedGoingToSleep(i);
            }
        }
        this.mGoingToSleep = true;
        this.mAssistantVisible = false;
        this.mLogger.d("Started going to sleep, mGoingToSleep=true, mAssistantVisible=false");
        updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_GOING_TO_SLEEP);
    }

    public void handleStartedWakingUp(int i) {
        Trace.beginSection("KeyguardUpdateMonitor#handleStartedWakingUp");
        Assert.isMainThread();
        updateFingerprintListeningState(2);
        if (((FaceWakeUpTriggersConfigImpl) this.mFaceWakeUpTriggersConfig).triggerFaceAuthOnWakeUpFrom.contains(Integer.valueOf(i))) {
            FaceAuthUiEvent faceAuthUiEvent = FaceAuthUiEvent.FACE_AUTH_UPDATED_STARTED_WAKING_UP;
            faceAuthUiEvent.setExtraInfo(i);
            updateFaceListeningState(2, faceAuthUiEvent);
        } else {
            KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
            keyguardUpdateMonitorLogger.getClass();
            LogLevel logLevel = LogLevel.VERBOSE;
            KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(21);
            LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
            LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
            ((LogMessageImpl) logMessageObtain).str1 = PowerManager.wakeReasonToString(i);
            logBuffer.commit(logMessageObtain);
        }
        if (((FaceWakeUpTriggersConfigImpl) this.mFaceWakeUpTriggersConfig).triggerFaceAuthOnWakeUpFrom.contains(Integer.valueOf(i))) {
            ActiveUnlockConfig.ActiveUnlockRequestOrigin activeUnlockRequestOrigin = this.mActiveUnlockConfig.wakeupsConsideredUnlockIntents.contains(Integer.valueOf(i)) ? ActiveUnlockConfig.ActiveUnlockRequestOrigin.UNLOCK_INTENT : ActiveUnlockConfig.ActiveUnlockRequestOrigin.WAKE;
            String str = "wakingUp - " + PowerManager.wakeReasonToString(i) + " powerManagerWakeup=true";
            if (this.mActiveUnlockConfig.wakeupsToForceDismissKeyguard.contains(Integer.valueOf(i))) {
                requestActiveUnlock(activeUnlockRequestOrigin, str + "-dismissKeyguard", true);
            } else {
                requestActiveUnlock(activeUnlockRequestOrigin, str);
            }
        } else {
            KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger2 = this.mLogger;
            keyguardUpdateMonitorLogger2.getClass();
            LogLevel logLevel2 = LogLevel.DEBUG;
            KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda32 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(16);
            LogBuffer logBuffer2 = keyguardUpdateMonitorLogger2.logBuffer;
            LogMessage logMessageObtain2 = logBuffer2.obtain("ActiveUnlock", logLevel2, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda32, null);
            ((LogMessageImpl) logMessageObtain2).int1 = i;
            logBuffer2.commit(logMessageObtain2);
        }
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onStartedWakingUp();
            }
        }
        Trace.endSection();
    }

    public void handleUserRemoved(int i) {
        Assert.isMainThread();
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(0);
        LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
        ((LogMessageImpl) logMessageObtain).int1 = i;
        logBuffer.commit(logMessageObtain);
        this.mUserIsUnlocked.delete(i);
        this.mUserTrustIsUsuallyManaged.delete(i);
    }

    public void handleUserSwitchComplete(int i) {
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(4);
        LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i;
        logMessageImpl.str1 = "from UserTracker";
        logBuffer.commit(logMessageObtain);
        Assert.isMainThread();
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onUserSwitchComplete(i);
            }
        }
        if (isFaceSupported()) {
            stopListeningForFace(FaceAuthUiEvent.FACE_AUTH_UPDATED_USER_SWITCHING);
            handleFaceLockoutReset(this.mFaceManager.getLockoutModeForUser(((FaceSensorPropertiesInternal) this.mFaceSensorProperties.get(0)).sensorId, i));
        }
        if (isFingerprintSupported()) {
            stopListeningForFingerprint();
            handleFingerprintLockoutReset(this.mFpm.getLockoutModeForUser(((FingerprintSensorPropertiesInternal) this.mFingerprintSensorProperties.get(0)).sensorId, i));
        }
        if (LsRune.KEYGUARD_FBE) {
            this.mUserIsUnlocked.put(i, this.mUserManager.isUserUnlocked(i));
            updateUserUnlockNotification(i);
        }
        this.mInteractionJankMonitor.end(37);
        this.mLatencyTracker.onActionEnd(12);
    }

    public void handleUserSwitching(int i, Runnable runnable) {
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(13);
        LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i;
        logMessageImpl.str1 = "from UserTracker";
        logBuffer.commit(logMessageObtain);
        Assert.isMainThread();
        setForceIsDismissibleKeyguard(false);
        clearFingerprintRecognized();
        boolean zIsTrustUsuallyManaged = this.mTrustManager.isTrustUsuallyManaged(i);
        this.mLogger.logTrustUsuallyManagedUpdated("userSwitching", i, this.mUserTrustIsUsuallyManaged.get(i), zIsTrustUsuallyManaged);
        this.mUserTrustIsUsuallyManaged.put(i, zIsTrustUsuallyManaged);
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onUserSwitching(i);
            }
        }
        runnable.run();
    }

    public void handleUserUnlocked(int i) {
        Assert.isMainThread();
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(5);
        LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
        ((LogMessageImpl) logMessageObtain).int1 = i;
        logBuffer.commit(logMessageObtain);
        this.mUserIsUnlocked.put(i, true);
        this.mNeedsSlowUnlockTransition = resolveNeedsSlowUnlockTransition();
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onUserUnlocked();
            }
        }
    }

    public final boolean isEncryptedOrLockdown(int i) {
        int strongAuthForUser = this.mStrongAuthTracker.getStrongAuthForUser(i);
        return (strongAuthForUser & 1) != 0 || (((strongAuthForUser & 2) != 0) || (strongAuthForUser & 32) != 0);
    }

    public boolean isFaceClass3() {
        int iSemGetSecurityLevel = this.mFaceManager.semGetSecurityLevel(true);
        Log.d("KeyguardUpdateMonitor", "faceSecurityLevel : " + iSemGetSecurityLevel);
        return isFaceSupported() && iSemGetSecurityLevel == 1;
    }

    public final boolean isFaceDetectionRunning() {
        return this.mFaceRunningState == 1;
    }

    public final boolean isFaceDisabled(final int i) {
        return ((Boolean) DejankUtils.whitelistIpcs(new Supplier() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda8
            @Override // java.util.function.Supplier
            public final Object get() {
                KeyguardUpdateMonitor keyguardUpdateMonitor = this.f$0;
                return Boolean.valueOf((keyguardUpdateMonitor.mDevicePolicyManager.getKeyguardDisabledFeatures(null, i) & 128) != 0 || keyguardUpdateMonitor.isSimPinSecure());
            }
        })).booleanValue();
    }

    public boolean isFaceEnabledAndEnrolled() {
        return false;
    }

    public final boolean isFaceSupported() {
        return (this.mFaceManager == null || this.mFaceSensorProperties.isEmpty()) ? false : true;
    }

    public boolean isFingerprintClass3() {
        return isFingerprintSupported() && ((SensorPropertiesInternal) this.mFingerprintSensorProperties.get(0)).sensorStrength == 2;
    }

    public final boolean isFingerprintDetectionRunning() {
        return this.mFingerprintRunningState == 1;
    }

    public boolean isFingerprintDisabled(int i) {
        return (this.mDevicePolicyManager.getKeyguardDisabledFeatures(null, i) & 32) != 0 || isSimPinSecure();
    }

    public final boolean isFingerprintLockedOut() {
        return this.mFingerprintLockedOut || this.mFingerprintLockedOutPermanent;
    }

    public final boolean isFingerprintSupported() {
        return (this.mFpm == null || this.mFingerprintSensorProperties.isEmpty()) ? false : true;
    }

    public final boolean isKeyguardVisible() {
        return this.mKeyguardShowing && !this.mKeyguardOccluded;
    }

    public boolean isSimPinSecure() {
        synchronized (this.mSimDataLockObject) {
            try {
                Iterator it = this.mSimDatasBySlotId.values().iterator();
                while (it.hasNext()) {
                    if (isSimPinSecure(((SimData) it.next()).simState)) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public boolean isUdfpsEnrolled() {
        return this.mAuthController.isUdfpsEnrolled(this.mSelectedUserInteractor.getSelectedUserId());
    }

    public boolean isUdfpsSupported() {
        return this.mAuthController.isUdfpsSupported();
    }

    @Deprecated
    public boolean isUnlockWithFacePossible() {
        return false;
    }

    public final boolean isUnlockWithFingerprintPossible(int i) {
        if (!isFingerprintSupported() || isFingerprintDisabled(i)) {
            return false;
        }
        return ((Boolean) ((HashMap) this.mAuthController.mFpEnrolledForUser).getOrDefault(Integer.valueOf(i), Boolean.FALSE)).booleanValue();
    }

    public boolean isUnlockingWithBiometricAllowed(boolean z) {
        StrongAuthTracker strongAuthTracker = this.mStrongAuthTracker;
        if (strongAuthTracker.isBiometricAllowedForUser(z, KeyguardUpdateMonitor.this.mSelectedUserInteractor.getSelectedUserId())) {
            return (isFingerprintClass3() && isFingerprintLockedOut()) ? false : true;
        }
        return false;
    }

    public final boolean isUserInLockdown(int i) {
        return (this.mStrongAuthTracker.getStrongAuthForUser(i) & 32) != 0;
    }

    public final void logListenerModelData(KeyguardListenModel keyguardListenModel) {
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(10);
        LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
        ((LogMessageImpl) logMessageObtain).str1 = String.valueOf(keyguardListenModel);
        logBuffer.commit(logMessageObtain);
        if (!(keyguardListenModel instanceof KeyguardFingerprintListenModel)) {
            if (keyguardListenModel instanceof KeyguardActiveUnlockModel) {
                KeyguardActiveUnlockModel keyguardActiveUnlockModel = (KeyguardActiveUnlockModel) keyguardListenModel;
                KeyguardActiveUnlockModel keyguardActiveUnlockModel2 = (KeyguardActiveUnlockModel) this.mActiveUnlockTriggerBuffer.buffer.advance();
                keyguardActiveUnlockModel2.timeMillis = keyguardActiveUnlockModel.timeMillis;
                keyguardActiveUnlockModel2.userId = keyguardActiveUnlockModel.userId;
                keyguardActiveUnlockModel2.listening = keyguardActiveUnlockModel.listening;
                keyguardActiveUnlockModel2.awakeKeyguard = keyguardActiveUnlockModel.awakeKeyguard;
                keyguardActiveUnlockModel2.authInterruptActive = keyguardActiveUnlockModel.authInterruptActive;
                keyguardActiveUnlockModel2.fpLockedOut = keyguardActiveUnlockModel.fpLockedOut;
                keyguardActiveUnlockModel2.primaryAuthRequired = keyguardActiveUnlockModel.primaryAuthRequired;
                keyguardActiveUnlockModel2.switchingUser = keyguardActiveUnlockModel.switchingUser;
                keyguardActiveUnlockModel2.triggerActiveUnlockForAssistant = keyguardActiveUnlockModel.triggerActiveUnlockForAssistant;
                keyguardActiveUnlockModel2.userCanDismissLockScreen = keyguardActiveUnlockModel.userCanDismissLockScreen;
                return;
            }
            return;
        }
        KeyguardFingerprintListenModel keyguardFingerprintListenModel = (KeyguardFingerprintListenModel) keyguardListenModel;
        KeyguardFingerprintListenModel keyguardFingerprintListenModel2 = (KeyguardFingerprintListenModel) this.mFingerprintListenBuffer.buffer.advance();
        keyguardFingerprintListenModel2.timeMillis = keyguardFingerprintListenModel.timeMillis;
        keyguardFingerprintListenModel2.userId = keyguardFingerprintListenModel.userId;
        keyguardFingerprintListenModel2.listening = keyguardFingerprintListenModel.listening;
        keyguardFingerprintListenModel2.allowOnCurrentOccludingActivity = keyguardFingerprintListenModel.allowOnCurrentOccludingActivity;
        keyguardFingerprintListenModel2.alternateBouncerShowing = keyguardFingerprintListenModel.alternateBouncerShowing;
        keyguardFingerprintListenModel2.biometricEnabledForUser = keyguardFingerprintListenModel.biometricEnabledForUser;
        keyguardFingerprintListenModel2.biometricPromptShowing = keyguardFingerprintListenModel.biometricPromptShowing;
        keyguardFingerprintListenModel2.bouncerIsOrWillShow = keyguardFingerprintListenModel.bouncerIsOrWillShow;
        keyguardFingerprintListenModel2.canSkipBouncer = keyguardFingerprintListenModel.canSkipBouncer;
        keyguardFingerprintListenModel2.credentialAttempted = keyguardFingerprintListenModel.credentialAttempted;
        keyguardFingerprintListenModel2.deviceInteractive = keyguardFingerprintListenModel.deviceInteractive;
        keyguardFingerprintListenModel2.dreaming = keyguardFingerprintListenModel.dreaming;
        keyguardFingerprintListenModel2.fingerprintDisabled = keyguardFingerprintListenModel.fingerprintDisabled;
        keyguardFingerprintListenModel2.fingerprintLockedOut = keyguardFingerprintListenModel.fingerprintLockedOut;
        keyguardFingerprintListenModel2.goingToSleep = keyguardFingerprintListenModel.goingToSleep;
        keyguardFingerprintListenModel2.keyguardGoingAway = keyguardFingerprintListenModel.keyguardGoingAway;
        keyguardFingerprintListenModel2.keyguardIsVisible = keyguardFingerprintListenModel.keyguardIsVisible;
        keyguardFingerprintListenModel2.keyguardOccluded = keyguardFingerprintListenModel.keyguardOccluded;
        keyguardFingerprintListenModel2.occludingAppRequestingFp = keyguardFingerprintListenModel.occludingAppRequestingFp;
        keyguardFingerprintListenModel2.shouldListenForFingerprintAssistant = keyguardFingerprintListenModel.shouldListenForFingerprintAssistant;
        keyguardFingerprintListenModel2.strongerAuthRequired = keyguardFingerprintListenModel.strongerAuthRequired;
        keyguardFingerprintListenModel2.switchingUser = keyguardFingerprintListenModel.switchingUser;
        keyguardFingerprintListenModel2.systemUser = keyguardFingerprintListenModel.systemUser;
        keyguardFingerprintListenModel2.udfps = keyguardFingerprintListenModel.udfps;
        keyguardFingerprintListenModel2.userDoesNotHaveTrust = keyguardFingerprintListenModel.userDoesNotHaveTrust;
        keyguardFingerprintListenModel2.communalShowing = keyguardFingerprintListenModel.communalShowing;
    }

    public final void notifyLockedOutStateChanged(BiometricSourceType biometricSourceType) {
        Assert.isMainThread();
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onLockedOutStateChanged(biometricSourceType);
            }
        }
    }

    public void notifyNonStrongBiometricAllowedChanged(int i) {
        Assert.isMainThread();
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onNonStrongBiometricAllowedChanged(i);
            }
        }
        if (i == this.mSelectedUserInteractor.getSelectedUserId()) {
            FaceAuthUiEvent faceAuthUiEvent = FaceAuthUiEvent.FACE_AUTH_NON_STRONG_BIOMETRIC_ALLOWED_CHANGED;
            faceAuthUiEvent.setExtraInfo(this.mStrongAuthTracker.isNonStrongBiometricAllowedAfterIdleTimeout(this.mSelectedUserInteractor.getSelectedUserId()) ? -1 : 1);
            updateBiometricListeningState(1, faceAuthUiEvent);
        }
    }

    public void notifyStrongAuthAllowedChanged(int i) {
        Assert.isMainThread();
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onStrongAuthStateChanged(i);
            }
        }
        if (i == this.mSelectedUserInteractor.getSelectedUserId()) {
            FaceAuthUiEvent faceAuthUiEvent = FaceAuthUiEvent.FACE_AUTH_UPDATED_STRONG_AUTH_CHANGED;
            faceAuthUiEvent.setExtraInfo(this.mStrongAuthTracker.getStrongAuthForUser(this.mSelectedUserInteractor.getSelectedUserId()));
            updateBiometricListeningState(1, faceAuthUiEvent);
        }
    }

    public void onAlternateBouncerVisibilityChange(boolean z) {
        setAlternateBouncerShowing(z);
    }

    public void onCommunalShowingChanged(boolean z) {
        this.mCommunalShowing = z;
        updateFingerprintListeningState(2);
    }

    public final void onEnabledTrustAgentsChanged(int i) {
        Assert.isMainThread();
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onEnabledTrustAgentsChanged(i);
            }
        }
    }

    public void onFaceAuthenticated(int i, boolean z) {
        Trace.beginSection("KeyGuardUpdateMonitor#onFaceAuthenticated");
        Assert.isMainThread();
        this.mUserFaceAuthenticated.put(i, new BiometricAuthenticated(true, z));
        if (getUserCanSkipBouncer(i)) {
            this.mTrustManager.unlockedByBiometricForUser(i, BiometricSourceType.FACE);
        }
        this.mFaceCancelSignal = null;
        updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_ON_FACE_AUTHENTICATED);
        this.mLogger.d("onFaceAuthenticated");
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricAuthenticated(i, BiometricSourceType.FACE, z);
            }
        }
        this.mAssistantVisible = false;
        this.mBackgroundExecutor.execute(new KeyguardUpdateMonitor$$ExternalSyntheticLambda6(this, z, i));
        Trace.endSection();
    }

    public void onFingerprintAuthenticated(final int i, boolean z) {
        Assert.isMainThread();
        Trace.beginSection("KeyGuardUpdateMonitor#onFingerPrintAuthenticated");
        this.mUserFingerprintAuthenticated.put(i, new BiometricAuthenticated(true, z));
        if (getUserCanSkipBouncer(i)) {
            getFastBioUnlockController().executor.submit(new KeyguardFastBioUnlockController.Task(new Runnable() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda10
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardUpdateMonitor keyguardUpdateMonitor = this.f$0;
                    keyguardUpdateMonitor.mTrustManager.unlockedByBiometricForUser(i, BiometricSourceType.FINGERPRINT);
                }
            }, "TrustManager#unlockedByBiometricForUser"));
        }
        this.mFingerprintCancelSignal = null;
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(16);
        LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardFingerprintLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i;
        logMessageImpl.bool1 = z;
        logBuffer.commit(logMessageObtain);
        updateFingerprintListeningState(2);
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricAuthenticated(i, BiometricSourceType.FINGERPRINT, z);
            }
        }
        AnonymousClass16 anonymousClass16 = this.mHandler;
        anonymousClass16.sendMessageDelayed(anonymousClass16.obtainMessage(336), 500L);
        this.mAssistantVisible = false;
        this.mBackgroundExecutor.execute(new KeyguardUpdateMonitor$$ExternalSyntheticLambda6(this, z, i));
        Trace.endSection();
    }

    public void onTransitionStateChanged(ObservableTransitionState observableTransitionState) {
        SceneContainerFlag.unsafeAssertInNewMode();
        throw null;
    }

    public final void onTrustChanged(boolean z, boolean z2, int i, int i2, List list) {
        String str;
        Assert.isMainThread();
        boolean z3 = this.mUserHasTrust.get(i, false);
        this.mUserHasTrust.put(i, z);
        if (z3 == z || z) {
            updateBiometricListeningState(1, FaceAuthUiEvent.FACE_AUTH_STOPPED_TRUST_ENABLED);
        } else {
            updateBiometricListeningState(0, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_TRUST_DISABLED);
        }
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(2);
        LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.bool1 = z3;
        logMessageImpl.bool2 = z;
        logMessageImpl.int1 = i;
        logBuffer.commit(logMessageObtain);
        for (int i3 = 0; i3 < this.mCallbacks.size(); i3++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i3)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onTrustChanged(i);
            }
        }
        if (z) {
            if (this.mSelectedUserInteractor.getSelectedUserId() == i && list != null) {
                Iterator it = list.iterator();
                str = null;
                while (it.hasNext()) {
                    str = (String) it.next();
                    if (!TextUtils.isEmpty(str)) {
                        break;
                    }
                }
            } else {
                str = null;
            }
            KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger2 = this.mLogger;
            keyguardUpdateMonitorLogger2.getClass();
            LogLevel logLevel2 = LogLevel.DEBUG;
            KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda12 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(12);
            LogBuffer logBuffer2 = keyguardUpdateMonitorLogger2.logBuffer;
            LogMessage logMessageObtain2 = logBuffer2.obtain("KeyguardUpdateMonitorLog", logLevel2, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda12, null);
            LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
            logMessageImpl2.int1 = i2;
            logMessageImpl2.bool1 = z2;
            logMessageImpl2.int2 = i;
            logMessageImpl2.str1 = str;
            logBuffer2.commit(logMessageObtain2);
            if (i == this.mSelectedUserInteractor.getSelectedUserId()) {
                if (z2) {
                    this.mUiEventLogger.log(TrustAgentUiEvent.TRUST_AGENT_NEWLY_UNLOCKED, ((SessionTracker) this.mSessionTrackerProvider.get()).getSessionId(1));
                }
                TrustGrantFlags trustGrantFlags = new TrustGrantFlags(i2);
                for (int i4 = 0; i4 < this.mCallbacks.size(); i4++) {
                    KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback2 = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i4)).get();
                    if (keyguardUpdateMonitorCallback2 != null) {
                        int i5 = SceneContainerFlag.$r8$clinit;
                        boolean z4 = this.mPrimaryBouncerIsOrWillBeShowing || this.mAlternateBouncerShowing;
                        int i6 = trustGrantFlags.mFlags;
                        keyguardUpdateMonitorCallback2.onTrustGrantedForCurrentUser(((i6 & 1) != 0 || trustGrantFlags.dismissKeyguardRequested()) && (this.mDeviceInteractive || (i6 & 4) != 0) && (z4 || trustGrantFlags.dismissKeyguardRequested()), z2, trustGrantFlags, str);
                    }
                }
            }
        }
    }

    public final void onTrustError(CharSequence charSequence) {
        Assert.isMainThread();
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onTrustAgentErrorMessage(charSequence);
            }
        }
    }

    public final void onTrustManagedChanged(boolean z, int i) {
        Assert.isMainThread();
        this.mUserTrustIsManaged.put(i, z);
        boolean zIsTrustUsuallyManaged = this.mTrustManager.isTrustUsuallyManaged(i);
        this.mLogger.logTrustUsuallyManagedUpdated("onTrustManagedChanged", i, this.mUserTrustIsUsuallyManaged.get(i), zIsTrustUsuallyManaged);
        this.mUserTrustIsUsuallyManaged.put(i, zIsTrustUsuallyManaged);
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onTrustManagedChanged(i);
            }
        }
    }

    public final boolean refreshSimState(int i, int i2) {
        int simState = LsRune.SECURITY_SIM_PERSO_LOCK ? DeviceState.getSimState(this.mContext, i2) : this.mTelephonyManager.getSimState(i2);
        if (isSimPinPassed(i2, simState)) {
            this.mLogger.d("refreshSimState isSimPinPassed slotId" + i2);
            return false;
        }
        synchronized (this.mSimDataLockObject) {
            try {
                SubscriptionManager.isValidSubscriptionId(i);
                SimData simData = (SimData) this.mSimDatasBySlotId.get(Integer.valueOf(i2));
                if (simData != null) {
                    z = simData.simState != simState;
                    if (z) {
                        Log.d("KeyguardUpdateMonitor", "refreshSimState [subId=" + i + ", slotId=" + i2 + "] state [" + simData.simState + "] changed to TelephonyManager state[" + simState + "]");
                    }
                    simData.simState = simState;
                    simData.subId = i;
                    simData.slotId = i2;
                } else if (SubscriptionManager.isValidSubscriptionId(i)) {
                    Log.d("KeyguardUpdateMonitor", "refreshSimState put [subId=" + i + ", slotId=" + i2 + ", state[" + simState + "]");
                    this.mSimDatasBySlotId.put(Integer.valueOf(i2), new SimData(simState, i2, i));
                    z = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public void registerCallback(KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback) {
        Assert.isMainThread();
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(7);
        LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
        ((LogMessageImpl) logMessageObtain).str1 = String.valueOf(keyguardUpdateMonitorCallback);
        logBuffer.commit(logMessageObtain);
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            if (((WeakReference) this.mCallbacks.get(i)).get() == keyguardUpdateMonitorCallback) {
                this.mLogger.logException("Object tried to add another callback", new Exception("Called by"));
                return;
            }
        }
        this.mCallbacks.add(new WeakReference(keyguardUpdateMonitorCallback));
        removeCallback(null);
        sendUpdates(keyguardUpdateMonitorCallback);
    }

    public void removeCallback(final KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback) {
        Assert.isMainThread();
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(22);
        LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
        ((LogMessageImpl) logMessageObtain).str1 = String.valueOf(keyguardUpdateMonitorCallback);
        logBuffer.commit(logMessageObtain);
        this.mCallbacks.removeIf(new Predicate() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda3
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback2 = keyguardUpdateMonitorCallback;
                int i = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                return ((WeakReference) obj).get() == keyguardUpdateMonitorCallback2;
            }
        });
    }

    public final void reportEmergencyCallAction() {
        Assert.isMainThread();
        handleReportEmergencyCallAction();
    }

    public final void reportSimUnlocked(int i) {
        SimLogger simLogger = this.mSimLogger;
        simLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        SimLogger$$ExternalSyntheticLambda0 simLogger$$ExternalSyntheticLambda0 = new SimLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = simLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("SimLog", logLevel, simLogger$$ExternalSyntheticLambda0, null);
        ((LogMessageImpl) logMessageObtain).int1 = i;
        logBuffer.commit(logMessageObtain);
        updatedSimPinPassed(getSlotId(i));
        handleSimStateChange(i, getSlotId(i), 5);
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0051  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin activeUnlockRequestOrigin, String str, boolean z) {
        if (hasMessages(336)) {
            return;
        }
        ActiveUnlockConfig activeUnlockConfig = this.mActiveUnlockConfig;
        activeUnlockConfig.getClass();
        int i = ActiveUnlockConfig.WhenMappings.$EnumSwitchMapping$0[activeUnlockRequestOrigin.ordinal()];
        boolean z2 = true;
        if (i == 1) {
            z2 = activeUnlockConfig.requestActiveUnlockOnWakeup;
        } else if (i == 2) {
            z2 = activeUnlockConfig.requestActiveUnlockOnUnlockIntentLegacy;
        } else if (i != 3) {
            if (i != 4) {
                if (i != 5) {
                    throw new NoWhenBranchMatchedException();
                }
                if (!activeUnlockConfig.requestActiveUnlockOnWakeup && !activeUnlockConfig.requestActiveUnlockOnUnlockIntent && !activeUnlockConfig.requestActiveUnlockOnBioFail && !activeUnlockConfig.requestActiveUnlockOnUnlockIntentLegacy) {
                    z2 = false;
                }
            } else if (!activeUnlockConfig.requestActiveUnlockOnBioFail && !activeUnlockConfig.requestActiveUnlockOnUnlockIntentLegacy && !activeUnlockConfig.requestActiveUnlockOnUnlockIntent && !activeUnlockConfig.requestActiveUnlockOnWakeup) {
            }
        } else if (!activeUnlockConfig.requestActiveUnlockOnUnlockIntent && !activeUnlockConfig.requestActiveUnlockOnUnlockIntentLegacy && !activeUnlockConfig.requestActiveUnlockOnWakeup && !activeUnlockConfig.shouldRequestActiveUnlockOnUnlockIntentFromBiometricEnrollment()) {
        }
        if (activeUnlockRequestOrigin == ActiveUnlockConfig.ActiveUnlockRequestOrigin.WAKE && !z2) {
            ActiveUnlockConfig activeUnlockConfig2 = this.mActiveUnlockConfig;
            if (activeUnlockConfig2.requestActiveUnlockOnWakeup || activeUnlockConfig2.requestActiveUnlockOnUnlockIntent || activeUnlockConfig2.requestActiveUnlockOnBioFail || activeUnlockConfig2.requestActiveUnlockOnUnlockIntentLegacy) {
                if (!hasMessages(336) && shouldTriggerActiveUnlock()) {
                    KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
                    keyguardUpdateMonitorLogger.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(28);
                    LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
                    LogMessage logMessageObtain = logBuffer.obtain("ActiveUnlock", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
                    ((LogMessageImpl) logMessageObtain).str1 = str;
                    logBuffer.commit(logMessageObtain);
                    this.mTrustManager.reportUserMayRequestUnlock(this.mSelectedUserInteractor.getSelectedUserId());
                    return;
                }
                return;
            }
        }
        if (z2 && shouldTriggerActiveUnlock()) {
            KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger2 = this.mLogger;
            keyguardUpdateMonitorLogger2.getClass();
            LogLevel logLevel2 = LogLevel.DEBUG;
            KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda32 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(24);
            LogBuffer logBuffer2 = keyguardUpdateMonitorLogger2.logBuffer;
            LogMessage logMessageObtain2 = logBuffer2.obtain("ActiveUnlock", logLevel2, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda32, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain2;
            logMessageImpl.str1 = activeUnlockRequestOrigin.name();
            logMessageImpl.str2 = str;
            logMessageImpl.bool1 = z;
            logBuffer2.commit(logMessageObtain2);
            this.mTrustManager.reportUserRequestedUnlock(this.mSelectedUserInteractor.getSelectedUserId(), z);
        }
    }

    public final void requestFaceAuth(String str) {
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(21);
        LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
        ((LogMessageImpl) logMessageObtain).str1 = str;
        logBuffer.commit(logMessageObtain);
        Object obj = FaceAuthReasonKt.apiRequestReasonToUiEvent.get(str);
        obj.getClass();
        updateFaceListeningState(0, (FaceAuthUiEvent) obj);
    }

    public void resetBiometricListeningState() {
        this.mFingerprintRunningState = 0;
        this.mFingerprintDetectRunning = false;
        this.mFaceRunningState = 0;
    }

    public final boolean resolveNeedsSlowUnlockTransition() {
        if (this.mUserManager.isUserUnlocked(this.mSelectedUserInteractor.getSelectedUserId())) {
            return false;
        }
        ResolveInfo resolveInfoResolveActivityAsUser = this.mPackageManager.resolveActivityAsUser(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME"), 0, this.mSelectedUserInteractor.getSelectedUserId());
        if (resolveInfoResolveActivityAsUser != null) {
            return FALLBACK_HOME_COMPONENT.equals(resolveInfoResolveActivityAsUser.getComponentInfo().getComponentName());
        }
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        LogBuffer.log$default(keyguardUpdateMonitorLogger.logBuffer, "KeyguardUpdateMonitorLog", LogLevel.WARNING, "resolveNeedsSlowUnlockTransition: returning false since activity could not be resolved.");
        return false;
    }

    public abstract void semSetScreenStatus();

    public final void sendOnSimStateChangedCallback(List list) {
        int i = 0;
        while (true) {
            ArrayList arrayList = (ArrayList) list;
            if (i >= arrayList.size()) {
                return;
            }
            SimData simData = (SimData) arrayList.get(i);
            for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
                if (keyguardUpdateMonitorCallback != null) {
                    keyguardUpdateMonitorCallback.onSimStateChanged(simData.subId, simData.slotId, simData.simState);
                }
            }
            i++;
        }
    }

    public final void sendPrimaryBouncerChanged(boolean z, boolean z2) {
        int i = SceneContainerFlag.$r8$clinit;
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(1);
        LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.bool1 = z;
        logMessageImpl.bool2 = z2;
        logBuffer.commit(logMessageObtain);
        Message messageObtainMessage = obtainMessage(322);
        messageObtainMessage.arg1 = z ? 1 : 0;
        messageObtainMessage.arg2 = z2 ? 1 : 0;
        messageObtainMessage.sendToTarget();
    }

    public final void sendUpdates(KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback) {
        if (this.mBatteryStatus != null) {
            keyguardUpdateMonitorCallback.onRefreshBatteryInfo(getKeyguardBatteryStatus());
        }
        keyguardUpdateMonitorCallback.onTimeChanged();
        keyguardUpdateMonitorCallback.onPhoneStateChanged(this.mPhoneState);
        keyguardUpdateMonitorCallback.onRefreshCarrierInfo(null);
        keyguardUpdateMonitorCallback.onKeyguardVisibilityChanged(isKeyguardVisible());
        keyguardUpdateMonitorCallback.onTelephonyCapable(this.mTelephonyCapable);
        ArrayList arrayList = new ArrayList();
        synchronized (this.mSimDataLockObject) {
            try {
                Iterator it = this.mSimDatasBySlotId.entrySet().iterator();
                while (it.hasNext()) {
                    SimData simData = (SimData) ((Map.Entry) it.next()).getValue();
                    if (isSimPinPassed(simData.slotId, simData.simState)) {
                        this.mLogger.d("sendUpdates isSimPinPassed state.slotId = " + simData.slotId);
                        return;
                    }
                    arrayList.add(new SimData(simData.simState, simData.slotId, simData.subId));
                }
                for (int i = 0; i < arrayList.size(); i++) {
                    SimData simData2 = (SimData) arrayList.get(i);
                    keyguardUpdateMonitorCallback.onSimStateChanged(simData2.subId, simData2.slotId, simData2.simState);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setAlternateBouncerShowing(boolean z) {
        this.mAlternateBouncerShowing = z;
        int i = SceneContainerFlag.$r8$clinit;
        if (z) {
            updateFaceListeningState(0, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_ALTERNATE_BIOMETRIC_BOUNCER_SHOWN);
            requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin.UNLOCK_INTENT, "alternateBouncer");
        }
        updateFingerprintListeningState(2);
    }

    public void setAssistantVisible(boolean z) {
        this.mAssistantVisible = z;
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(29);
        LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
        ((LogMessageImpl) logMessageObtain).bool1 = z;
        logBuffer.commit(logMessageObtain);
        updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_ASSISTANT_VISIBILITY_CHANGED);
        if (this.mAssistantVisible) {
            requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin.ASSISTANT, SettingsHelper.INDEX_ASSISTANT, true);
        }
    }

    public void setCredentialAttempted() {
        this.mCredentialAttempted = true;
        updateFingerprintListeningState(2);
    }

    public final void setFaceRunningState(int i) {
        boolean z = this.mFaceRunningState == 1;
        boolean z2 = i == 1;
        this.mFaceRunningState = i;
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(13);
        LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
        ((LogMessageImpl) logMessageObtain).int1 = i;
        logBuffer.commit(logMessageObtain);
        if (z != z2) {
            Assert.isMainThread();
            for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
                if (keyguardUpdateMonitorCallback != null) {
                    keyguardUpdateMonitorCallback.onBiometricRunningStateChanged(isFaceDetectionRunning(), BiometricSourceType.FACE);
                }
            }
        }
    }

    public final void setFingerprintRunningState(int i) {
        boolean z = this.mFingerprintRunningState == 1;
        boolean z2 = i == 1;
        this.mFingerprintRunningState = i;
        if (i == 0) {
            this.mFingerprintDetectRunning = false;
        }
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(14);
        LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardFingerprintLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
        ((LogMessageImpl) logMessageObtain).int1 = i;
        logBuffer.commit(logMessageObtain);
        if (z != z2) {
            Assert.isMainThread();
            for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
                if (keyguardUpdateMonitorCallback != null) {
                    keyguardUpdateMonitorCallback.onBiometricRunningStateChanged(isFingerprintDetectionRunning(), BiometricSourceType.FINGERPRINT);
                }
            }
        }
    }

    public final void setForceIsDismissibleKeyguard(boolean z) {
        Assert.isMainThread();
        if (this.mFoldGracePeriodProvider.isEnabled()) {
            if (this.mKeyguardShowing && z) {
                this.mLogger.d("Skip setting forceIsDismissibleKeyguard to true. Keyguard already showing.");
                return;
            }
            if (this.mForceIsDismissible != z) {
                this.mForceIsDismissible = z;
                KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
                keyguardUpdateMonitorLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(9);
                LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
                ((LogMessageImpl) logMessageObtain).bool1 = z;
                logBuffer.commit(logMessageObtain);
                for (int i = 0; i < this.mCallbacks.size(); i++) {
                    KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i)).get();
                    if (keyguardUpdateMonitorCallback != null) {
                        keyguardUpdateMonitorCallback.onForceIsDismissibleChanged(forceIsDismissibleIsKeepingDeviceUnlocked());
                    }
                }
            }
        }
    }

    public void setKeyguardGoingAway(boolean z) {
        this.mKeyguardGoingAway = z;
        if (z) {
            updateFingerprintListeningState(1);
            for (int i = 0; i < this.mCallbacks.size(); i++) {
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i)).get();
                if (keyguardUpdateMonitorCallback != null) {
                    keyguardUpdateMonitorCallback.onKeyguardGoingAway();
                }
            }
        }
        updateFingerprintListeningState(2);
    }

    public void setKeyguardShowing(boolean z, boolean z2) {
        boolean z3 = this.mKeyguardOccluded != z2;
        boolean z4 = this.mKeyguardShowing != z;
        if (z3 || z4) {
            boolean zIsKeyguardVisible = isKeyguardVisible();
            this.mKeyguardShowing = z;
            this.mKeyguardOccluded = z2;
            boolean zIsKeyguardVisible2 = isKeyguardVisible();
            this.mLogger.logKeyguardShowingChanged(z, z2, zIsKeyguardVisible2);
            if (zIsKeyguardVisible2 != zIsKeyguardVisible) {
                if (zIsKeyguardVisible2) {
                    this.mSecureCameraLaunched = false;
                }
                for (int i = 0; i < this.mCallbacks.size(); i++) {
                    KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i)).get();
                    if (keyguardUpdateMonitorCallback != null) {
                        keyguardUpdateMonitorCallback.onKeyguardVisibilityChanged(zIsKeyguardVisible2);
                    }
                }
            }
            if (z3) {
                updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_KEYGUARD_OCCLUSION_CHANGED);
            } else if (z4) {
                updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_KEYGUARD_VISIBILITY_CHANGED);
            }
        }
    }

    public void setStrongAuthTracker(StrongAuthTracker strongAuthTracker) {
        StrongAuthTracker strongAuthTracker2 = this.mStrongAuthTracker;
        if (strongAuthTracker2 != null) {
            this.mLockPatternUtils.unregisterStrongAuthTracker(strongAuthTracker2);
        }
        this.mStrongAuthTracker = strongAuthTracker;
        this.mLockPatternUtils.registerStrongAuthTracker(strongAuthTracker);
    }

    public void setSwitchingUser(boolean z) {
        if (z) {
            KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
            int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
            keyguardUpdateMonitorLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(13);
            LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
            LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.int1 = selectedUserId;
            logMessageImpl.str1 = "from setSwitchingUser";
            logBuffer.commit(logMessageObtain);
        } else {
            KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger2 = this.mLogger;
            int selectedUserId2 = this.mSelectedUserInteractor.getSelectedUserId();
            keyguardUpdateMonitorLogger2.getClass();
            LogLevel logLevel2 = LogLevel.DEBUG;
            KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(4);
            LogBuffer logBuffer2 = keyguardUpdateMonitorLogger2.logBuffer;
            LogMessage logMessageObtain2 = logBuffer2.obtain("KeyguardUpdateMonitorLog", logLevel2, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
            LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
            logMessageImpl2.int1 = selectedUserId2;
            logMessageImpl2.str1 = "from setSwitchingUser";
            logBuffer2.commit(logMessageObtain2);
        }
        this.mSwitchingUser = z;
        post(new KeyguardUpdateMonitor$$ExternalSyntheticLambda4(this, 1));
    }

    public boolean shouldListenForFace() {
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x00b9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean shouldListenForFingerprint(boolean z) {
        boolean z2;
        boolean z3;
        boolean z4;
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
        boolean userHasTrust = getUserHasTrust(selectedUserId);
        boolean z5 = !userHasTrust;
        BiometricAuthenticated biometricAuthenticated = this.mUserFingerprintAuthenticated.get(this.mSelectedUserInteractor.getSelectedUserId());
        boolean z6 = this.mAssistantVisible && this.mKeyguardOccluded && (biometricAuthenticated == null || !biometricAuthenticated.mAuthenticated) && !this.mUserHasTrust.get(this.mSelectedUserInteractor.getSelectedUserId(), false);
        if (isKeyguardVisible() || !this.mDeviceInteractive) {
            z2 = true;
        } else {
            int i = SceneContainerFlag.$r8$clinit;
            if ((!this.mPrimaryBouncerIsOrWillBeShowing || this.mKeyguardGoingAway) && !this.mGoingToSleep && !z6 && ((!(z4 = this.mKeyguardOccluded) || !this.mIsDreaming) && (!z4 || userHasTrust || !this.mKeyguardShowing || (!this.mOccludingAppRequestingFp && !z && !this.mAlternateBouncerShowing && !this.mAllowFingerprintOnCurrentOccludingActivity)))) {
                z2 = false;
            }
        }
        boolean z7 = this.mBiometricEnabledForUser.get(selectedUserId);
        boolean userCanSkipBouncer = getUserCanSkipBouncer(selectedUserId);
        boolean zIsFingerprintDisabled = isFingerprintDisabled(selectedUserId);
        boolean z8 = (this.mSwitchingUser || zIsFingerprintDisabled || (this.mKeyguardGoingAway && this.mDeviceInteractive) || !this.mIsSystemUser || !z7 || isUserInLockdown(selectedUserId)) ? false : true;
        boolean zIsUnlockingWithBiometricAllowed = isUnlockingWithBiometricAllowed(BiometricSourceType.FINGERPRINT);
        boolean z9 = !zIsUnlockingWithBiometricAllowed;
        if (!zIsUnlockingWithBiometricAllowed) {
            int i2 = SceneContainerFlag.$r8$clinit;
            z3 = !this.mPrimaryBouncerIsOrWillBeShowing;
        }
        if (this.mCommunalShowing) {
            int i3 = SceneContainerFlag.$r8$clinit;
            boolean z10 = this.mAlternateBouncerShowing;
        }
        boolean z11 = z2 && z8 && z3 && (!z || (!userCanSkipBouncer && zIsUnlockingWithBiometricAllowed && !userHasTrust)) && !this.mBiometricPromptShowing;
        long jCurrentTimeMillis = System.currentTimeMillis();
        boolean z12 = this.mAllowFingerprintOnCurrentOccludingActivity;
        int i4 = SceneContainerFlag.$r8$clinit;
        logListenerModelData(new KeyguardFingerprintListenModel(jCurrentTimeMillis, selectedUserId, z11, z12, this.mAlternateBouncerShowing, z7, this.mBiometricPromptShowing, this.mPrimaryBouncerIsOrWillBeShowing, userCanSkipBouncer, this.mCredentialAttempted, this.mDeviceInteractive, this.mIsDreaming, zIsFingerprintDisabled, this.mFingerprintLockedOut, this.mGoingToSleep, this.mKeyguardGoingAway, isKeyguardVisible(), this.mKeyguardOccluded, this.mOccludingAppRequestingFp, z6, z9, this.mSwitchingUser, this.mIsSystemUser, z, z5, this.mCommunalShowing));
        return z11;
    }

    public final boolean shouldTriggerActiveUnlock() {
        boolean z = this.mAssistantVisible && this.mKeyguardOccluded && !this.mUserHasTrust.get(this.mSelectedUserInteractor.getSelectedUserId(), false);
        int i = SceneContainerFlag.$r8$clinit;
        boolean z2 = this.mPrimaryBouncerFullyShown || this.mAlternateBouncerShowing || !(!isKeyguardVisible() || this.mGoingToSleep || this.mStatusBarState == 2);
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
        boolean z3 = getUserCanSkipBouncer(selectedUserId) || !this.mLockPatternUtils.isSecure(selectedUserId);
        boolean zIsFingerprintLockedOut = isFingerprintLockedOut();
        boolean zIsUnlockingWithBiometricAllowed = isUnlockingWithBiometricAllowed(true);
        boolean z4 = !zIsUnlockingWithBiometricAllowed;
        boolean z5 = ((!this.mAuthInterruptActive && !z && !z2) || this.mSwitchingUser || z3 || zIsFingerprintLockedOut || !zIsUnlockingWithBiometricAllowed || this.mKeyguardGoingAway || this.mSecureCameraLaunched) ? false : true;
        logListenerModelData(new KeyguardActiveUnlockModel(System.currentTimeMillis(), selectedUserId, z5, z2, this.mAuthInterruptActive, zIsFingerprintLockedOut, z4, this.mSwitchingUser, z, z3));
        return z5;
    }

    /* JADX WARN: Type inference failed for: r0v37, types: [com.android.keyguard.KeyguardUpdateMonitor$22] */
    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (!this.mDeviceProvisioned) {
            this.mDeviceProvisionedObserver = new ContentObserver(this.mHandler) { // from class: com.android.keyguard.KeyguardUpdateMonitor.22
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    super.onChange(z);
                    KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                    int i = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                    keyguardUpdateMonitor.mDeviceProvisioned = Settings.Global.getInt(keyguardUpdateMonitor.mContext.getContentResolver(), "device_provisioned", 0) != 0;
                    KeyguardUpdateMonitor keyguardUpdateMonitor2 = KeyguardUpdateMonitor.this;
                    if (keyguardUpdateMonitor2.mDeviceProvisioned) {
                        keyguardUpdateMonitor2.mHandler.sendEmptyMessage(308);
                    }
                    KeyguardUpdateMonitor keyguardUpdateMonitor3 = KeyguardUpdateMonitor.this;
                    KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = keyguardUpdateMonitor3.mLogger;
                    boolean z2 = keyguardUpdateMonitor3.mDeviceProvisioned;
                    keyguardUpdateMonitorLogger.getClass();
                    LogLevel logLevel = LogLevel.DEBUG;
                    KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(26);
                    LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
                    LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
                    ((LogMessageImpl) logMessageObtain).bool1 = z2;
                    logBuffer.commit(logMessageObtain);
                }
            };
            this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("device_provisioned"), false, this.mDeviceProvisionedObserver);
            boolean z = Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0;
            if (z != this.mDeviceProvisioned) {
                this.mDeviceProvisioned = z;
                if (z) {
                    sendEmptyMessage(308);
                }
            }
        }
        this.mBackgroundExecutor.execute(new KeyguardUpdateMonitor$$ExternalSyntheticLambda4(this, 5));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_TICK");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m(intentFilter, "android.intent.action.TIMEZONE_CHANGED", "android.intent.action.AIRPLANE_MODE", "android.intent.action.SIM_STATE_CHANGED", "android.intent.action.SERVICE_STATE");
        KeyguardUpdateMonitor$$ExternalSyntheticOutline0.m(intentFilter, "android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED", "android.intent.action.PHONE_STATE", "android.telephony.action.SERVICE_PROVIDERS_UPDATED", "android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        intentFilter.addAction("android.hardware.usb.action.USB_PORT_COMPLIANCE_CHANGED");
        intentFilter.addAction("com.samsung.server.BatteryService.action.SEC_BATTERY_REMAINING_CHARGING_TIME_CHANGED");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        this.mBroadcastDispatcher.registerReceiverWithHandler(this.mBroadcastReceiver, intentFilter, this.mHandler);
        this.mBackgroundExecutor.execute(new KeyguardUpdateMonitor$$ExternalSyntheticLambda4(this, 7));
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.app.action.NEXT_ALARM_CLOCK_CHANGED");
        intentFilter2.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        intentFilter2.addAction("android.intent.action.USER_UNLOCKED");
        intentFilter2.addAction("android.intent.action.USER_STOPPED");
        intentFilter2.addAction("android.intent.action.USER_REMOVED");
        intentFilter2.addAction("android.nfc.action.REQUIRE_UNLOCK_FOR_NFC");
        this.mBroadcastDispatcher.registerReceiverWithHandler(this.mBroadcastAllReceiver, intentFilter2, this.mHandler, UserHandle.ALL);
        this.mSubscriptionManager.addOnSubscriptionsChangedListener(this.mSubscriptionListener);
        ((UserTrackerImpl) this.mUserTracker).addCallback(this.mUserChangedCallback, this.mMainExecutor);
        runSystemUserOnly(new KeyguardUpdateMonitor$$ExternalSyntheticLambda4(this, 9));
        FingerprintManager fingerprintManager = this.mFpm;
        if (fingerprintManager != null) {
            fingerprintManager.addAuthenticatorsRegisteredCallback(new IFingerprintAuthenticatorsRegisteredCallback.Stub() { // from class: com.android.keyguard.KeyguardUpdateMonitor.18
                public final void onAllAuthenticatorsRegistered(List list) {
                    KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                    keyguardUpdateMonitor.mFingerprintSensorProperties = list;
                    keyguardUpdateMonitor.updateFingerprintListeningState(2);
                    KeyguardUpdateMonitor.this.mLogger.d("FingerprintManager onAllAuthenticatorsRegistered");
                }
            });
            this.mFpm.addLockoutResetCallback(this.mFingerprintLockoutResetCallback);
        }
        FaceManager faceManager = this.mFaceManager;
        if (faceManager != null) {
            faceManager.addAuthenticatorsRegisteredCallback(new IFaceAuthenticatorsRegisteredCallback.Stub() { // from class: com.android.keyguard.KeyguardUpdateMonitor.19
                public final void onAllAuthenticatorsRegistered(List list) {
                    KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                    keyguardUpdateMonitor.mFaceSensorProperties = list;
                    keyguardUpdateMonitor.mLogger.d("FaceManager onAllAuthenticatorsRegistered");
                }
            });
            this.mFaceManager.addLockoutResetCallback(this.mFaceLockoutResetCallback);
        }
        runSystemUserOnly(new KeyguardUpdateMonitor$$ExternalSyntheticLambda4(this, 10));
        this.mAuthController.addCallback(new AnonymousClass20());
        ((DevicePostureControllerImpl) this.mDevicePostureController).addCallback(this.mPostureCallback);
        this.mTaskStackChangeListeners.registerTaskStackListener(this.mTaskStackListener);
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
        boolean zIsUserUnlocked = this.mUserManager.isUserUnlocked(selectedUserId);
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(25);
        LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = selectedUserId;
        logMessageImpl.bool1 = zIsUserUnlocked;
        logBuffer.commit(logMessageObtain);
        this.mUserIsUnlocked.put(selectedUserId, zIsUserUnlocked);
        updateSecondaryLockscreenRequirement(selectedUserId);
        for (UserInfo userInfo : this.mUserManager.getUsers()) {
            boolean zIsTrustUsuallyManaged = this.mTrustManager.isTrustUsuallyManaged(userInfo.id);
            KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger2 = this.mLogger;
            int i = userInfo.id;
            keyguardUpdateMonitorLogger2.logTrustUsuallyManagedUpdated("init from constructor", i, this.mUserTrustIsUsuallyManaged.get(i), zIsTrustUsuallyManaged);
            this.mUserTrustIsUsuallyManaged.put(userInfo.id, zIsTrustUsuallyManaged);
        }
        if (WirelessUtils.isAirplaneModeOn(this.mContext) && !hasMessages(329)) {
            sendEmptyMessage(329);
        }
        runSystemUserOnly(new KeyguardUpdateMonitor$$ExternalSyntheticLambda4(this, 11), this.mBackgroundExecutor);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(SettingsHelper.INDEX_TIME_12_24), false, this.mTimeFormatChangeObserver, -1);
        int i2 = SceneContainerFlag.$r8$clinit;
        KeyguardWmStateRefactor keyguardWmStateRefactor = KeyguardWmStateRefactor.INSTANCE;
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) this.mUserTracker;
        if (userTrackerImpl.isUserSwitching) {
            handleUserSwitching(userTrackerImpl.getUserId(), new KeyguardUpdateMonitor$$ExternalSyntheticLambda23());
        }
        this.mBackgroundExecutor.execute(new KeyguardUpdateMonitor$$ExternalSyntheticLambda4(this, 6));
    }

    public void startBiometricWatchdog() {
        this.mBackgroundExecutor.execute(new KeyguardUpdateMonitor$$ExternalSyntheticLambda4(this, 3));
    }

    public void startListeningForFingerprint(boolean z) {
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
        boolean zIsUnlockWithFingerprintPossible = isUnlockWithFingerprintPossible(selectedUserId);
        if (this.mFingerprintCancelSignal != null) {
            KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
            int i = this.mFingerprintRunningState;
            keyguardUpdateMonitorLogger.getClass();
            LogLevel logLevel = LogLevel.ERROR;
            KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(1);
            LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
            LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
            logMessageImpl.int1 = i;
            logMessageImpl.bool1 = zIsUnlockWithFingerprintPossible;
            logBuffer.commit(logMessageObtain);
        }
        int i2 = this.mFingerprintRunningState;
        if (i2 == 2) {
            setFingerprintRunningState(3);
            return;
        }
        if (i2 != 3 && zIsUnlockWithFingerprintPossible) {
            this.mFingerprintCancelSignal = new CancellationSignal();
            FingerprintAuthenticateOptions fingerprintAuthenticateOptionsBuild = new FingerprintAuthenticateOptions.Builder().setUserId(selectedUserId).build();
            if (z) {
                this.mLogger.v("startListeningForFingerprint - detect");
                this.mFpm.detectFingerprint(this.mFingerprintCancelSignal, this.mFingerprintDetectionCallback, fingerprintAuthenticateOptionsBuild);
                this.mFingerprintDetectRunning = true;
            } else {
                this.mLogger.v("startListeningForFingerprint");
                this.mFpm.authenticate((FingerprintManager.CryptoObject) null, this.mFingerprintCancelSignal, this.mFingerprintAuthenticationCallback, (Handler) null, fingerprintAuthenticateOptionsBuild);
                this.mFingerprintDetectRunning = false;
            }
            setFingerprintRunningState(1);
        }
    }

    public void stopListeningForFingerprint() {
        this.mLogger.v("stopListeningForFingerprint()");
        if (this.mFingerprintRunningState == 1) {
            CancellationSignal cancellationSignal = this.mFingerprintCancelSignal;
            if (cancellationSignal != null) {
                cancellationSignal.cancel();
                this.mFingerprintCancelSignal = null;
                removeCallbacks(this.mFpCancelNotReceived);
                postDelayed(this.mFpCancelNotReceived, 3000L);
            }
            setFingerprintRunningState(2);
        }
        if (this.mFingerprintRunningState == 3) {
            setFingerprintRunningState(2);
        }
    }

    public void updateBiometricListeningState(int i, FaceAuthUiEvent faceAuthUiEvent) {
        updateFingerprintListeningState(i);
        updateFaceListeningState(i, faceAuthUiEvent);
    }

    public void updateFingerprintListeningState(int i) {
        if (hasMessages(336)) {
            KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
            keyguardUpdateMonitorLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(5);
            LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
            LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
            ((LogMessageImpl) logMessageObtain).int1 = i;
            logBuffer.commit(logMessageObtain);
            return;
        }
        if (!this.mAuthController.mAllFingerprintAuthenticatorsRegistered) {
            this.mLogger.d("All FP authenticators not registered, skipping FP listening state update");
            return;
        }
        boolean zShouldListenForFingerprint = shouldListenForFingerprint(isUdfpsSupported());
        int i2 = this.mFingerprintRunningState;
        boolean z = i2 == 1;
        boolean z2 = z || i2 == 3;
        boolean zIsUnlockingWithBiometricAllowed = isUnlockingWithBiometricAllowed(BiometricSourceType.FINGERPRINT);
        boolean z3 = !zIsUnlockingWithBiometricAllowed;
        if (z2 && !zShouldListenForFingerprint) {
            if (i == 0) {
                this.mLogger.v("Ignoring stopListeningForFingerprint()");
                return;
            } else {
                stopListeningForFingerprint();
                return;
            }
        }
        if (!z2 && zShouldListenForFingerprint) {
            if (i == 1) {
                this.mLogger.v("Ignoring startListeningForFingerprint()");
                return;
            } else {
                startListeningForFingerprint(z3);
                return;
            }
        }
        if (!z || z3 == this.mFingerprintDetectRunning) {
            return;
        }
        if (i == 1) {
            if (zIsUnlockingWithBiometricAllowed) {
                this.mLogger.v("Ignoring startListeningForFingerprint() switch detect -> auth");
                return;
            }
            this.mLogger.v("Allowing startListeningForFingerprint(detect) despite BIOMETRIC_ACTION_STOP since auth was running before.");
        }
        startListeningForFingerprint(z3);
    }

    public final void updateSecondaryLockscreenRequirement(int i) {
        Intent intent = (Intent) ((HashMap) this.mSecondaryLockscreenRequirement).get(Integer.valueOf(i));
        boolean zIsSecondaryLockscreenEnabled = this.mDevicePolicyManager.isSecondaryLockscreenEnabled(UserHandle.of(i));
        if (zIsSecondaryLockscreenEnabled && intent == null) {
            ComponentName profileOwnerOrDeviceOwnerSupervisionComponent = this.mDevicePolicyManager.getProfileOwnerOrDeviceOwnerSupervisionComponent(UserHandle.of(i));
            if (profileOwnerOrDeviceOwnerSupervisionComponent == null) {
                KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
                keyguardUpdateMonitorLogger.getClass();
                LogLevel logLevel = LogLevel.ERROR;
                KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(12);
                LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
                LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
                ((LogMessageImpl) logMessageObtain).int1 = i;
                logBuffer.commit(logMessageObtain);
                return;
            }
            ResolveInfo resolveInfoResolveService = this.mPackageManager.resolveService(new Intent("android.app.action.BIND_SECONDARY_LOCKSCREEN_SERVICE").setPackage(profileOwnerOrDeviceOwnerSupervisionComponent.getPackageName()), 0);
            if (resolveInfoResolveService == null || resolveInfoResolveService.serviceInfo == null) {
                return;
            }
            Intent component = new Intent().setComponent(resolveInfoResolveService.serviceInfo.getComponentName());
            ((HashMap) this.mSecondaryLockscreenRequirement).put(Integer.valueOf(i), component);
        } else {
            if (zIsSecondaryLockscreenEnabled || intent == null) {
                return;
            }
            ((HashMap) this.mSecondaryLockscreenRequirement).put(Integer.valueOf(i), null);
        }
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onSecondaryLockscreenRequirementChanged(i);
            }
        }
    }

    public void updateTelephonyCapable(boolean z) {
        Assert.isMainThread();
        if (z == this.mTelephonyCapable) {
            return;
        }
        this.mTelephonyCapable = z;
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onTelephonyCapable(this.mTelephonyCapable);
            }
        }
    }

    public final void clearFingerprintRecognized(int i) {
        Assert.isMainThread();
        this.mUserFingerprintAuthenticated.clear();
        this.mUserFaceAuthenticated.clear();
        this.mTrustManager.clearAllBiometricRecognized(BiometricSourceType.FINGERPRINT, i);
        this.mLogger.d("clearFingerprintRecognized");
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onFingerprintsCleared();
            }
        }
    }

    @Deprecated
    public boolean isUnlockWithFacePossible(int i) {
        return isFaceSupported() && isFaceEnabledAndEnrolled() && !isFaceDisabled(i);
    }

    public final boolean isUnlockingWithBiometricAllowed(BiometricSourceType biometricSourceType) {
        int i = AnonymousClass24.$SwitchMap$android$hardware$biometrics$BiometricSourceType[biometricSourceType.ordinal()];
        if (i == 1) {
            return isUnlockingWithBiometricAllowed(isFingerprintClass3());
        }
        if (i != 2) {
            return false;
        }
        return isUnlockingWithBiometricAllowed(isFaceClass3());
    }

    public static boolean isSimPinSecure(int i) {
        if (i == 2 || i == 3 || i == 7) {
            return true;
        }
        return LsRune.SECURITY_SIM_PERSO_LOCK && i == 12;
    }

    public void stopListeningForFace(FaceAuthUiEvent faceAuthUiEvent) {
    }

    public final void onIsActiveUnlockRunningChanged(boolean z, int i) {
    }

    public void updateFaceListeningState(int i, FaceAuthUiEvent faceAuthUiEvent) {
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin activeUnlockRequestOrigin, String str) {
        boolean z;
        KeyguardBypassController keyguardBypassController;
        if (!isFaceEnabledAndEnrolled() || (keyguardBypassController = this.mKeyguardBypassController) == null || !keyguardBypassController.canBypass()) {
            int i = SceneContainerFlag.$r8$clinit;
            z = this.mAlternateBouncerShowing || this.mPrimaryBouncerFullyShown || isUdfpsFingerDown();
        }
        requestActiveUnlock(activeUnlockRequestOrigin, str, z);
    }
}
