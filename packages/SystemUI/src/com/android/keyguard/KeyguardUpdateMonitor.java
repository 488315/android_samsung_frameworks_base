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
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import com.android.internal.foldables.FoldGracePeriodProvider;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.ActiveUnlockConfig;
import com.android.keyguard.KeyguardActiveUnlockModel;
import com.android.keyguard.KeyguardFingerprintListenModel;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger;
import com.android.settingslib.WirelessUtils;
import com.android.settingslib.fuelgauge.BatteryStatus;
import com.android.systemui.CoreStartable;
import com.android.systemui.CscRune;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.deviceentry.data.repository.FaceWakeUpTriggersConfig;
import com.android.systemui.deviceentry.data.repository.FaceWakeUpTriggersConfigImpl;
import com.android.systemui.deviceentry.shared.FaceAuthReasonKt;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.shared.constants.TrustAgentUiEvent;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
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
import com.sec.ims.volte2.data.VolteConstants;
import dalvik.annotation.optimization.NeverCompile;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.inject.Provider;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class KeyguardUpdateMonitor implements TrustManager.TrustListener, Dumpable, CoreStartable, KeyguardSecUpdateMonitor {
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
    public final KeyguardUpdateMonitor$$ExternalSyntheticLambda1 mFaceCancelNotReceived;
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
    public boolean mLogoutEnabled;
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
    public final HashMap mSimDatas;
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.keyguard.KeyguardUpdateMonitor$2, reason: invalid class name */
    public final class AnonymousClass2 extends IBiometricEnabledOnKeyguardCallback.Stub {
        public AnonymousClass2() {
        }

        public final void onChanged(boolean z, int i) {
            post(new KeyguardUpdateMonitor$$ExternalSyntheticLambda5(this, i, z));
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.keyguard.KeyguardUpdateMonitor$20, reason: invalid class name */
    public final class AnonymousClass20 implements AuthController.Callback {
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    class BiometricAuthenticated {
        public final boolean mAuthenticated;
        public final boolean mIsStrongBiometric;

        public BiometricAuthenticated(boolean z, boolean z2) {
            this.mAuthenticated = z;
            this.mIsStrongBiometric = z2;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SimData {
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
            return Anchor$$ExternalSyntheticOutline0.m(this.subId, "}", sb);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class StrongAuthTracker extends LockPatternUtils.StrongAuthTracker {
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

    /* JADX WARN: Type inference failed for: r1v13, types: [android.os.Handler, com.android.keyguard.KeyguardUpdateMonitor$16] */
    /* JADX WARN: Type inference failed for: r2v13, types: [com.android.keyguard.KeyguardUpdateMonitor$17] */
    /* JADX WARN: Type inference failed for: r4v20, types: [com.android.keyguard.KeyguardUpdateMonitor$5] */
    /* JADX WARN: Type inference failed for: r4v21, types: [com.android.keyguard.KeyguardUpdateMonitor$6] */
    /* JADX WARN: Type inference failed for: r4v25, types: [com.android.keyguard.KeyguardUpdateMonitor$10] */
    /* JADX WARN: Type inference failed for: r4v26, types: [com.android.keyguard.KeyguardUpdateMonitor$11] */
    /* JADX WARN: Type inference failed for: r4v28, types: [com.android.keyguard.KeyguardUpdateMonitor$13] */
    /* JADX WARN: Type inference failed for: r4v34, types: [com.android.keyguard.KeyguardUpdateMonitor$23] */
    public KeyguardUpdateMonitor(FaceManager faceManager, Context context, UserTracker userTracker, Looper looper, BroadcastDispatcher broadcastDispatcher, DumpManager dumpManager, Executor executor, Executor executor2, StatusBarStateController statusBarStateController, LockPatternUtils lockPatternUtils, AuthController authController, TelephonyListenerManager telephonyListenerManager, InteractionJankMonitor interactionJankMonitor, LatencyTracker latencyTracker, ActiveUnlockConfig activeUnlockConfig, KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger, UiEventLogger uiEventLogger, Provider provider, TrustManager trustManager, SubscriptionManager subscriptionManager, UserManager userManager, IDreamManager iDreamManager, DevicePolicyManager devicePolicyManager, SensorPrivacyManager sensorPrivacyManager, TelephonyManager telephonyManager, PackageManager packageManager, FingerprintManager fingerprintManager, BiometricManager biometricManager, FaceWakeUpTriggersConfig faceWakeUpTriggersConfig, CarrierConfigManager carrierConfigManager, DevicePostureController devicePostureController, Optional<Object> optional, TaskStackChangeListeners taskStackChangeListeners, SelectedUserInteractor selectedUserInteractor, IActivityTaskManager iActivityTaskManager) {
        StatusBarStateController.StateListener stateListener = new StatusBarStateController.StateListener() { // from class: com.android.keyguard.KeyguardUpdateMonitor.1
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
        this.mSimDatas = new HashMap();
        this.mServiceStates = new HashMap();
        this.mCallbacks = Lists.newArrayList();
        this.mFoldGracePeriodProvider = new FoldGracePeriodProvider();
        this.mFingerprintRunningState = 0;
        this.mFaceRunningState = 0;
        this.mActiveMobileDataSubscription = -1;
        this.mPostureState = 0;
        this.mHardwareFingerprintUnavailableRetryCount = 0;
        this.mHardwareFaceUnavailableRetryCount = 0;
        this.mFpCancelNotReceived = new KeyguardUpdateMonitor$$ExternalSyntheticLambda1(this, 0);
        this.mFaceCancelNotReceived = new KeyguardUpdateMonitor$$ExternalSyntheticLambda1(this, 9);
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
                keyguardUpdateMonitor.mLogger.logRetryAfterFpHwUnavailable(keyguardUpdateMonitor.mHardwareFingerprintUnavailableRetryCount);
                if (!KeyguardUpdateMonitor.this.mFingerprintSensorProperties.isEmpty()) {
                    KeyguardUpdateMonitor.this.updateFingerprintListeningState(2);
                    return;
                }
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = KeyguardUpdateMonitor.this;
                int i = keyguardUpdateMonitor2.mHardwareFingerprintUnavailableRetryCount;
                if (i < 20) {
                    keyguardUpdateMonitor2.mHardwareFingerprintUnavailableRetryCount = i + 1;
                    keyguardUpdateMonitor2.mHandler.postDelayed(keyguardUpdateMonitor2.mRetryFingerprintAuthenticationAfterHwUnavailable, 500L);
                }
            }
        };
        this.mRetryFaceAuthentication = new Runnable() { // from class: com.android.keyguard.KeyguardUpdateMonitor.6
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                keyguardUpdateMonitor.mLogger.logRetryingAfterFaceHwUnavailable(keyguardUpdateMonitor.mHardwareFaceUnavailableRetryCount);
                KeyguardUpdateMonitor.this.updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_RETRY_AFTER_HW_UNAVAILABLE);
            }
        };
        new Object(this) { // from class: com.android.keyguard.KeyguardUpdateMonitor.7
        };
        this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.keyguard.KeyguardUpdateMonitor.8
            /* JADX WARN: Code restructure failed: missing block: B:59:0x00f3, code lost:
            
                if ("NETWORK".equals(r1) != false) goto L33;
             */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onReceive(android.content.Context r12, android.content.Intent r13) {
                /*
                    Method dump skipped, instructions count: 583
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardUpdateMonitor.AnonymousClass8.onReceive(android.content.Context, android.content.Intent):void");
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
                    int sendingUserId = getSendingUserId();
                    if (sendingUserId == -1) {
                        sendingUserId = KeyguardUpdateMonitor.sCurrentUser;
                    }
                    AnonymousClass16 anonymousClass16 = KeyguardUpdateMonitor.this.mHandler;
                    anonymousClass16.sendMessage(anonymousClass16.obtainMessage(309, sendingUserId, 0));
                    return;
                }
                if ("android.intent.action.USER_UNLOCKED".equals(action)) {
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                    KeyguardSecPatternView$$ExternalSyntheticOutline0.m(intExtra, "ACTION_USER_UNLOCKED. userId:", "KeyguardUpdateMonitor");
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
                KeyguardUpdateMonitor.this.mLogger.logFingerprintAcquired(i);
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
                KeyguardUpdateMonitor.this.mLogger.logUdfpsPointerDown(i);
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                keyguardUpdateMonitor.mLogger.logFaceAuthRequested("Face auth triggered due to finger down on UDFPS");
                Object obj = FaceAuthReasonKt.apiRequestReasonToUiEvent.get("Face auth triggered due to finger down on UDFPS");
                Intrinsics.checkNotNull(obj);
                keyguardUpdateMonitor.updateFaceListeningState(0, (FaceAuthUiEvent) obj);
            }

            public final void onUdfpsPointerUp(int i) {
                KeyguardUpdateMonitor.this.mLogger.logUdfpsPointerUp(i);
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
                if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                    keyguardUpdateMonitor4.mLogger.logFingerprintDetected(i2, z);
                } else if (biometricSourceType == BiometricSourceType.FACE) {
                    keyguardUpdateMonitor4.mLogger.logFaceDetected(i2, z);
                    keyguardUpdateMonitor4.setFaceRunningState(0);
                }
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
        this.mFingerprintSensorProperties = Collections.emptyList();
        this.mFaceSensorProperties = Collections.emptyList();
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
                        keyguardUpdateMonitor.mLogger.allowFingerprintOnCurrentOccludingActivityChanged(z);
                        keyguardUpdateMonitor.updateFingerprintListeningState(2);
                    }
                    ActivityTaskManager.RootTaskInfo rootTaskInfo2 = keyguardUpdateMonitor.mActivityTaskManager.getRootTaskInfo(0, 4);
                    if (rootTaskInfo2 == null) {
                        return;
                    }
                    keyguardUpdateMonitor.mLogger.logTaskStackChangedForAssistant(rootTaskInfo2.visible);
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
        this.mDeviceProvisioned = isDeviceProvisionedInSettingsDb();
        this.mStrongAuthTracker = new StrongAuthTracker(context);
        this.mBackgroundExecutor = executor;
        this.mMainExecutor = executor2;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mInteractionJankMonitor = interactionJankMonitor;
        this.mLatencyTracker = latencyTracker;
        this.mStatusBarStateController = statusBarStateController;
        statusBarStateController.addCallback(stateListener);
        this.mStatusBarState = statusBarStateController.getState();
        this.mLockPatternUtils = lockPatternUtils;
        this.mAuthController = authController;
        dumpManager.registerDumpable(this);
        this.mSensorPrivacyManager = sensorPrivacyManager;
        this.mActiveUnlockConfig = activeUnlockConfig;
        this.mLogger = keyguardUpdateMonitorLogger;
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
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(optional.orElse(null));
        this.mIsSystemUser = userManager.isSystemUser();
        ?? r1 = new Handler(looper) { // from class: com.android.keyguard.KeyguardUpdateMonitor.16
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i;
                BiometricSourceType biometricSourceType;
                int i2 = message.what;
                int i3 = 0;
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                switch (i2) {
                    case 301:
                        int i4 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.getClass();
                        Assert.isMainThread();
                        while (i3 < keyguardUpdateMonitor.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor.mCallbacks.get(i3)).get();
                            if (keyguardUpdateMonitorCallback != null) {
                                keyguardUpdateMonitorCallback.onTimeChanged();
                            }
                            i3++;
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
                        int i5 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.getClass();
                        Assert.isMainThread();
                        while (i3 < keyguardUpdateMonitor.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback2 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor.mCallbacks.get(i3)).get();
                            if (keyguardUpdateMonitorCallback2 != null) {
                                keyguardUpdateMonitorCallback2.onDeviceProvisioned();
                            }
                            i3++;
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
                        int i6 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
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
                        int i7 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.getClass();
                        Assert.isMainThread();
                        keyguardUpdateMonitor.mLogger.v("onSubscriptionInfoChanged()");
                        List subscriptionInfo = keyguardUpdateMonitor.getSubscriptionInfo(true);
                        StringBuilder sb = new StringBuilder("onSubscriptionInfoChanged(): list size is ");
                        ArrayList arrayList = (ArrayList) subscriptionInfo;
                        sb.append(arrayList.size());
                        Log.i("KeyguardUpdateMonitor", sb.toString());
                        if (arrayList.isEmpty()) {
                            keyguardUpdateMonitor.mLogger.v("onSubscriptionInfoChanged: list is null");
                        } else {
                            Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                keyguardUpdateMonitor.mLogger.logSubInfo((SubscriptionInfo) it.next());
                            }
                        }
                        ArrayList arrayList2 = new ArrayList();
                        HashSet hashSet = new HashSet();
                        for (int i8 = 0; i8 < arrayList.size(); i8++) {
                            SubscriptionInfo subscriptionInfo2 = (SubscriptionInfo) arrayList.get(i8);
                            hashSet.add(Integer.valueOf(subscriptionInfo2.getSubscriptionId()));
                            if (keyguardUpdateMonitor.refreshSimState(subscriptionInfo2.getSubscriptionId(), subscriptionInfo2.getSimSlotIndex())) {
                                arrayList2.add(subscriptionInfo2);
                            }
                        }
                        Iterator it2 = keyguardUpdateMonitor.mSimDatas.entrySet().iterator();
                        while (it2.hasNext()) {
                            Map.Entry entry = (Map.Entry) it2.next();
                            if (!hashSet.contains(entry.getKey())) {
                                SimData simData = (SimData) entry.getValue();
                                boolean refreshSimState = keyguardUpdateMonitor.refreshSimState(simData.subId, simData.slotId);
                                keyguardUpdateMonitor.mLogger.logInvalidSubId(((Integer) entry.getKey()).intValue());
                                Log.d("KeyguardUpdateMonitor", "    └ onSubscriptionInfoChanged(): Previously active sub id " + simData.subId + " is now invalid, will remove");
                                it2.remove();
                                if (simData.subId == -1 && ((i = simData.simState) == 2 || i == 3)) {
                                    refreshSimState = false;
                                }
                                if (refreshSimState) {
                                    for (int i9 = 0; i9 < keyguardUpdateMonitor.mCallbacks.size(); i9++) {
                                        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback3 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor.mCallbacks.get(i9)).get();
                                        if (keyguardUpdateMonitorCallback3 != null) {
                                            keyguardUpdateMonitorCallback3.onSimStateChanged(simData.subId, simData.slotId, simData.simState);
                                        }
                                    }
                                }
                            }
                        }
                        for (int i10 = 0; i10 < arrayList2.size(); i10++) {
                            SimData simData2 = (SimData) keyguardUpdateMonitor.mSimDatas.get(Integer.valueOf(((SubscriptionInfo) arrayList2.get(i10)).getSubscriptionId()));
                            for (int i11 = 0; i11 < keyguardUpdateMonitor.mCallbacks.size(); i11++) {
                                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback4 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor.mCallbacks.get(i11)).get();
                                if (keyguardUpdateMonitorCallback4 != null) {
                                    keyguardUpdateMonitorCallback4.onSimStateChanged(simData2.subId, simData2.slotId, simData2.simState);
                                }
                            }
                        }
                        keyguardUpdateMonitor.callbacksRefreshCarrierInfo(null);
                        break;
                    case 329:
                        int i12 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.callbacksRefreshCarrierInfo(null);
                        break;
                    case 330:
                        keyguardUpdateMonitor.handleServiceStateChange(message.arg1, (ServiceState) message.obj);
                        break;
                    case CustomDeviceManager.DESTINATION_ADDRESS /* 332 */:
                        Trace.beginSection("KeyguardUpdateMonitor#handler MSG_SCREEN_TURNED_OFF");
                        int i13 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
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
                    case 337:
                        int i14 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.getClass();
                        Assert.isMainThread();
                        boolean isLogoutEnabled = keyguardUpdateMonitor.mDevicePolicyManager.isLogoutEnabled();
                        if (keyguardUpdateMonitor.mLogoutEnabled != isLogoutEnabled) {
                            keyguardUpdateMonitor.mLogoutEnabled = isLogoutEnabled;
                            while (i3 < keyguardUpdateMonitor.mCallbacks.size()) {
                                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback5 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor.mCallbacks.get(i3)).get();
                                if (keyguardUpdateMonitorCallback5 != null) {
                                    keyguardUpdateMonitorCallback5.onLogoutEnabledChanged();
                                }
                                i3++;
                            }
                            break;
                        }
                        break;
                    case 338:
                        keyguardUpdateMonitor.updateTelephonyCapable(((Boolean) message.obj).booleanValue());
                        break;
                    case 339:
                        String str = (String) message.obj;
                        int i15 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.getClass();
                        Assert.isMainThread();
                        keyguardUpdateMonitor.mLogger.d("handleTimeZoneUpdate");
                        while (i3 < keyguardUpdateMonitor.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback6 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor.mCallbacks.get(i3)).get();
                            if (keyguardUpdateMonitorCallback6 != null) {
                                keyguardUpdateMonitorCallback6.onTimeZoneChanged(TimeZone.getTimeZone(str));
                                keyguardUpdateMonitorCallback6.onTimeChanged();
                            }
                            i3++;
                        }
                        break;
                    case 340:
                        int i16 = message.arg1;
                        int i17 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.getClass();
                        Assert.isMainThread();
                        keyguardUpdateMonitor.mUserIsUnlocked.put(i16, keyguardUpdateMonitor.mUserManager.isUserUnlocked(i16));
                        break;
                    case 341:
                        keyguardUpdateMonitor.handleUserRemoved(message.arg1);
                        break;
                    case 342:
                        boolean booleanValue = ((Boolean) message.obj).booleanValue();
                        int i18 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.getClass();
                        Assert.isMainThread();
                        keyguardUpdateMonitor.setKeyguardGoingAway(booleanValue);
                        break;
                    case 344:
                        String str2 = (String) message.obj;
                        int i19 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.getClass();
                        Assert.isMainThread();
                        keyguardUpdateMonitor.mLogger.logTimeFormatChanged(str2);
                        while (i3 < keyguardUpdateMonitor.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback7 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor.mCallbacks.get(i3)).get();
                            if (keyguardUpdateMonitorCallback7 != null) {
                                keyguardUpdateMonitorCallback7.onTimeFormatChanged(str2);
                            }
                            i3++;
                        }
                        break;
                    case 345:
                        int i20 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.getClass();
                        Assert.isMainThread();
                        while (i3 < keyguardUpdateMonitor.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback8 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor.mCallbacks.get(i3)).get();
                            if (keyguardUpdateMonitorCallback8 != null) {
                                keyguardUpdateMonitorCallback8.onRequireUnlockForNfc();
                            }
                            i3++;
                        }
                        break;
                    case 346:
                        int i21 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.getClass();
                        Assert.isMainThread();
                        while (i3 < keyguardUpdateMonitor.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback9 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor.mCallbacks.get(i3)).get();
                            if (keyguardUpdateMonitorCallback9 != null) {
                                keyguardUpdateMonitorCallback9.onKeyguardDismissAnimationFinished();
                            }
                            i3++;
                        }
                        break;
                    case 347:
                        Intent intent = (Intent) message.obj;
                        keyguardUpdateMonitor.mLogger.logServiceProvidersUpdated(intent);
                        keyguardUpdateMonitor.callbacksRefreshCarrierInfo(intent);
                        break;
                    case 348:
                        int i22 = message.arg1;
                        int i23 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.getClass();
                        if (i22 == 2) {
                            biometricSourceType = BiometricSourceType.FINGERPRINT;
                        } else if (i22 == 8) {
                            biometricSourceType = BiometricSourceType.FACE;
                        }
                        keyguardUpdateMonitor.mLogger.notifyAboutEnrollmentsChanged(biometricSourceType);
                        Assert.isMainThread();
                        while (i3 < keyguardUpdateMonitor.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback10 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor.mCallbacks.get(i3)).get();
                            if (keyguardUpdateMonitorCallback10 != null) {
                                keyguardUpdateMonitorCallback10.onBiometricEnrollmentStateChanged(biometricSourceType);
                            }
                            i3++;
                        }
                        break;
                }
            }
        };
        this.mHandler = r1;
        this.mTimeFormatChangeObserver = new ContentObserver(r1) { // from class: com.android.keyguard.KeyguardUpdateMonitor.17
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                AnonymousClass16 anonymousClass16 = keyguardUpdateMonitor.mHandler;
                anonymousClass16.sendMessage(anonymousClass16.obtainMessage(344, Settings.System.getString(keyguardUpdateMonitor.mContext.getContentResolver(), SettingsHelper.INDEX_TIME_12_24)));
            }
        };
    }

    public static synchronized int getCurrentUser() {
        int i;
        synchronized (KeyguardUpdateMonitor.class) {
            i = sCurrentUser;
        }
        return i;
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

    @Override // com.android.systemui.Dumpable
    @NeverCompile
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m(CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "KeyguardUpdateMonitor state:", "  forceIsDismissible="), this.mForceIsDismissible, printWriter, "  forceIsDismissibleIsKeepingDeviceUnlocked=");
        m.append(forceIsDismissibleIsKeepingDeviceUnlocked());
        printWriter.println(m.toString());
        printWriter.println("  getUserHasTrust()=" + getUserHasTrust(this.mSelectedUserInteractor.getSelectedUserId()));
        printWriter.println("  getUserUnlockedWithBiometric()=" + getUserUnlockedWithBiometric(this.mSelectedUserInteractor.getSelectedUserId()));
        printWriter.println("  SIM States:");
        Iterator it = this.mSimDatas.values().iterator();
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
            StringBuilder m2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(num.intValue(), "    ", "=");
            m2.append(this.mServiceStates.get(num));
            printWriter.println(m2.toString());
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
            if (subscriptionInfo2.getGroupUuid() != null && subscriptionInfo2.getGroupUuid().equals(subscriptionInfo3.getGroupUuid())) {
                if (!subscriptionInfo2.isOpportunistic() && !subscriptionInfo3.isOpportunistic()) {
                    return subscriptionInfo;
                }
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
                } else {
                    if (subscriptionInfo2.getSubscriptionId() == this.mActiveMobileDataSubscription) {
                        subscriptionInfo2 = subscriptionInfo3;
                    }
                    arrayList.remove(subscriptionInfo2);
                }
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
            int subscriptionId = ((SubscriptionInfo) arrayList.get(i2)).getSubscriptionId();
            int slotId = getSlotId(subscriptionId);
            if (i == getSimState(subscriptionId) && i4 > slotId) {
                if (isSimPinPassed(slotId, i)) {
                    this.mLogger.v("getNextSubIdForState() PIN_REQUIRED happen on isSimPinPassed slot");
                } else if (LsRune.SECURITY_ESIM && isESimRemoveButtonClicked()) {
                    this.mLogger.v("getNextSubIdForState() " + i + " happen on isESimRemoveButtonClicked slotId = " + slotId);
                } else {
                    i3 = subscriptionId;
                    i4 = slotId;
                }
            }
            i2++;
        }
    }

    public final int getSimState(int i) {
        if (this.mSimDatas.containsKey(Integer.valueOf(i))) {
            return ((SimData) this.mSimDatas.get(Integer.valueOf(i))).simState;
        }
        return 0;
    }

    public final int getSlotId(int i) {
        if (!this.mSimDatas.containsKey(Integer.valueOf(i))) {
            refreshSimState(i, SubscriptionManager.getSlotIndex(i));
        }
        return ((SimData) this.mSimDatas.get(Integer.valueOf(i))).slotId;
    }

    public final List getSubscriptionInfo(boolean z) {
        if (this.mSubscriptionInfo == null || z) {
            this.mSubscriptionInfo = this.mSubscriptionManager.getCompleteActiveSubscriptionInfoList().stream().filter(new KeyguardUpdateMonitor$$ExternalSyntheticLambda6()).toList();
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
        return (biometricAuthenticated != null && biometricAuthenticated.mAuthenticated && isUnlockingWithBiometricAllowed(biometricAuthenticated.mIsStrongBiometric)) || (getUserUnlockedWithFace(i) && this.mKeyguardBypassController.canBypass());
    }

    public final boolean getUserUnlockedWithFace(int i) {
        BiometricAuthenticated biometricAuthenticated = this.mUserFaceAuthenticated.get(i);
        return biometricAuthenticated != null && biometricAuthenticated.mAuthenticated && isUnlockingWithBiometricAllowed(biometricAuthenticated.mIsStrongBiometric);
    }

    public void handleBatteryUpdate(BatteryStatus batteryStatus) {
        Assert.isMainThread();
        BatteryStatus batteryStatus2 = this.mBatteryStatus;
        boolean isPluggedIn = batteryStatus.isPluggedIn();
        boolean isPluggedIn2 = batteryStatus2.isPluggedIn();
        boolean z = true;
        boolean z2 = isPluggedIn2 && isPluggedIn && batteryStatus2.status != batteryStatus.status;
        if (isPluggedIn2 == isPluggedIn && !z2 && batteryStatus2.level == batteryStatus.level && ((!isPluggedIn || batteryStatus.maxChargingWattage == batteryStatus2.maxChargingWattage) && batteryStatus2.present == batteryStatus.present && batteryStatus2.incompatibleCharger.equals(batteryStatus.incompatibleCharger) && batteryStatus.chargingStatus == batteryStatus2.chargingStatus)) {
            z = false;
        }
        this.mBatteryStatus = batteryStatus;
        if (z) {
            this.mLogger.logHandleBatteryUpdate(batteryStatus);
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
        Assert.isMainThread();
        requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin.BIOMETRIC_FAIL, "faceFailure-".concat(this.mKeyguardBypassController.canBypass() ? "bypass" : this.mAlternateBouncerShowing ? "alternateBouncer" : this.mPrimaryBouncerFullyShown ? "bouncer" : "udfpsFpDown"));
        this.mLogger.d("onFaceAuthFailed");
        this.mFaceCancelSignal = null;
        setFaceRunningState(0);
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i)).get();
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
            int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId(true);
            if (selectedUserId != i) {
                this.mLogger.logFaceAuthForWrongUser(i);
                return;
            }
            if (isFaceDisabled(selectedUserId)) {
                this.mLogger.logFaceAuthDisabledForUser(selectedUserId);
                return;
            }
            this.mLogger.logFaceAuthSuccess(selectedUserId);
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
        this.mLogger.logFaceAuthError(i, str);
        if (hasCallbacks(this.mFaceCancelNotReceived)) {
            removeCallbacks(this.mFaceCancelNotReceived);
        }
        this.mFaceCancelSignal = null;
        boolean isSensorPrivacyEnabled = this.mSensorPrivacyManager.isSensorPrivacyEnabled(1, 2);
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
        if (z2 && isSensorPrivacyEnabled) {
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
        this.mLogger.logFaceLockoutReset(i);
        boolean z = this.mFaceLockedOutPermanent;
        boolean z2 = i == 2;
        this.mFaceLockedOutPermanent = z2;
        boolean z3 = z2 != z;
        postDelayed(new KeyguardUpdateMonitor$$ExternalSyntheticLambda1(this, 1), VolteConstants.ErrorCode.BUSY_EVERYWHERE);
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
            handleFingerprintHelp(-1, this.mContext.getString(android.R.string.managed_profile_label_badge_3));
        } else {
            handleFingerprintHelp(-1, this.mContext.getString(android.R.string.lockscreen_transport_play_description));
        }
    }

    public void handleFingerprintAuthenticated(int i, boolean z) {
        Trace.beginSection("KeyGuardUpdateMonitor#handlerFingerPrintAuthenticated");
        if (hasCallbacks(this.mFpCancelNotReceived)) {
            this.mLogger.d("handleFingerprintAuthenticated() triggered while waiting for cancellation, removing watchdog");
            removeCallbacks(this.mFpCancelNotReceived);
        }
        try {
            int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId(true);
            if (selectedUserId != i) {
                this.mLogger.logFingerprintAuthForWrongUser(i);
            } else {
                if (isFingerprintDisabled(selectedUserId)) {
                    this.mLogger.logFingerprintDisabledForUser(selectedUserId);
                    return;
                }
                onFingerprintAuthenticated(selectedUserId, z);
                setFingerprintRunningState(0);
                Trace.endSection();
            }
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
            postDelayed(new KeyguardUpdateMonitor$$ExternalSyntheticLambda1(this, 3), 50L);
        }
        if (i == 9) {
            z = !this.mFingerprintLockedOutPermanent;
            this.mFingerprintLockedOutPermanent = true;
            this.mLogger.d("Fingerprint permanently locked out - requiring stronger auth");
            this.mLockPatternUtils.requireStrongAuth(8, this.mSelectedUserInteractor.getSelectedUserId(false));
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
        this.mLogger.logFingerprintError(i, str);
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
        this.mLogger.logFingerprintLockoutReset(i);
        boolean z = this.mFingerprintLockedOut;
        boolean z2 = this.mFingerprintLockedOutPermanent;
        boolean z3 = i == 1 || i == 2;
        this.mFingerprintLockedOut = z3;
        boolean z4 = i == 2;
        this.mFingerprintLockedOutPermanent = z4;
        boolean z5 = (z3 == z && z4 == z2) ? false : true;
        if (isUdfpsEnrolled()) {
            postDelayed(new KeyguardUpdateMonitor$$ExternalSyntheticLambda1(this, 10), VolteConstants.ErrorCode.BUSY_EVERYWHERE);
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
        updateFaceListeningState(1, FaceAuthUiEvent.FACE_AUTH_STOPPED_FINISHED_GOING_TO_SLEEP);
        updateFingerprintListeningState(2);
    }

    public void handleKeyguardReset() {
        this.mLogger.d("handleKeyguardReset");
        updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_KEYGUARD_RESET);
        this.mNeedsSlowUnlockTransition = resolveNeedsSlowUnlockTransition();
    }

    public void handlePhoneStateChanged(String str) {
        Assert.isMainThread();
        this.mLogger.logPhoneStateChanged(str);
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
        this.mLogger.logPrimaryKeyguardBouncerChanged(z3, z4);
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
        this.mLogger.logServiceStateChange(i, serviceState);
        if (SubscriptionManager.isValidSubscriptionId(i)) {
            updateTelephonyCapable(true);
            this.mServiceStates.put(Integer.valueOf(i), serviceState);
            callbacksRefreshCarrierInfo(null);
        } else {
            KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
            keyguardUpdateMonitorLogger.getClass();
            keyguardUpdateMonitorLogger.logBuffer.log("KeyguardUpdateMonitorLog", LogLevel.WARNING, "invalid subId in handleServiceStateChange()", null);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00b1  */
    /* JADX WARN: Type inference failed for: r0v18, types: [com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda9] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void handleSimStateChange(final int r10, final int r11, final int r12) {
        /*
            Method dump skipped, instructions count: 239
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardUpdateMonitor.handleSimStateChange(int, int, int):void");
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
            this.mLogger.logSkipUpdateFaceListeningOnWakeup(i);
        }
        if (((FaceWakeUpTriggersConfigImpl) this.mFaceWakeUpTriggersConfig).triggerFaceAuthOnWakeUpFrom.contains(Integer.valueOf(i))) {
            this.mLogger.logActiveUnlockRequestSkippedForWakeReasonDueToFaceConfig(i);
        } else {
            ActiveUnlockConfig.ActiveUnlockRequestOrigin activeUnlockRequestOrigin = this.mActiveUnlockConfig.wakeupsConsideredUnlockIntents.contains(Integer.valueOf(i)) ? ActiveUnlockConfig.ActiveUnlockRequestOrigin.UNLOCK_INTENT : ActiveUnlockConfig.ActiveUnlockRequestOrigin.WAKE;
            String str = "wakingUp - " + PowerManager.wakeReasonToString(i) + " powerManagerWakeup=true";
            if (this.mActiveUnlockConfig.wakeupsToForceDismissKeyguard.contains(Integer.valueOf(i))) {
                requestActiveUnlock(activeUnlockRequestOrigin, str + "-dismissKeyguard", true);
            } else {
                requestActiveUnlock(activeUnlockRequestOrigin, str);
            }
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
        this.mUserIsUnlocked.delete(i);
        this.mUserTrustIsUsuallyManaged.delete(i);
    }

    public void handleUserSwitchComplete(int i) {
        this.mLogger.logUserSwitchComplete(i, "from UserTracker");
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
        this.mLogger.logUserSwitching(i, "from UserTracker");
        Assert.isMainThread();
        setForceIsDismissibleKeyguard(false);
        clearFingerprintRecognized();
        boolean isTrustUsuallyManaged = this.mTrustManager.isTrustUsuallyManaged(i);
        this.mLogger.logTrustUsuallyManagedUpdated("userSwitching", this.mUserTrustIsUsuallyManaged.get(i), i, isTrustUsuallyManaged);
        this.mUserTrustIsUsuallyManaged.put(i, isTrustUsuallyManaged);
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
        this.mUserIsUnlocked.put(i, true);
        this.mNeedsSlowUnlockTransition = resolveNeedsSlowUnlockTransition();
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onUserUnlocked();
            }
        }
    }

    public final boolean isDeviceProvisionedInSettingsDb() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) != 0;
    }

    public final boolean isEncryptedOrLockdown(int i) {
        int strongAuthForUser = this.mStrongAuthTracker.getStrongAuthForUser(i);
        return (strongAuthForUser & 1) != 0 || (((strongAuthForUser & 2) != 0) || (strongAuthForUser & 32) != 0);
    }

    public boolean isFaceClass3() {
        int semGetSecurityLevel = this.mFaceManager.semGetSecurityLevel(true);
        Log.d("KeyguardUpdateMonitor", "faceSecurityLevel : " + semGetSecurityLevel);
        return isFaceSupported() && semGetSecurityLevel == 1;
    }

    public final boolean isFaceDetectionRunning() {
        return this.mFaceRunningState == 1;
    }

    public final boolean isFaceDisabled(final int i) {
        return ((Boolean) DejankUtils.whitelistIpcs(new Supplier() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda7
            @Override // java.util.function.Supplier
            public final Object get() {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
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

    public final boolean isSimPinSecure() {
        Iterator it = ((ArrayList) getSubscriptionInfo(false)).iterator();
        while (it.hasNext()) {
            SubscriptionInfo subscriptionInfo = (SubscriptionInfo) it.next();
            if (CscRune.SECURITY_SIM_PERM_DISABLED) {
                int simState = getSimState(subscriptionInfo.getSubscriptionId());
                if (!this.mDeviceProvisioned && simState == 7) {
                    return false;
                }
                if (isSimPinSecure(simState) && (simState != 7 || !subscriptionInfo.isEmbedded())) {
                    return true;
                }
            } else if (isSimPinSecure(getSimState(subscriptionInfo.getSubscriptionId()))) {
                return true;
            }
        }
        return false;
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
        if (isFingerprintSupported() && !isFingerprintDisabled(i)) {
            if (((Boolean) ((HashMap) this.mAuthController.mFpEnrolledForUser).getOrDefault(Integer.valueOf(i), Boolean.FALSE)).booleanValue()) {
                return true;
            }
        }
        return false;
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
        this.mLogger.logKeyguardListenerModel(keyguardListenModel);
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
        this.mBackgroundExecutor.execute(new KeyguardUpdateMonitor$$ExternalSyntheticLambda5(this, z, i));
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
                    KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                    keyguardUpdateMonitor.mTrustManager.unlockedByBiometricForUser(i, BiometricSourceType.FINGERPRINT);
                }
            }, "TrustManager#unlockedByBiometricForUser"));
        }
        this.mFingerprintCancelSignal = null;
        this.mLogger.logFingerprintSuccess(i, z);
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
        this.mBackgroundExecutor.execute(new KeyguardUpdateMonitor$$ExternalSyntheticLambda5(this, z, i));
        Trace.endSection();
    }

    public final void onTrustChanged(boolean z, boolean z2, int i, int i2, List list) {
        Assert.isMainThread();
        boolean z3 = this.mUserHasTrust.get(i, false);
        this.mUserHasTrust.put(i, z);
        if (z3 == z || z) {
            updateBiometricListeningState(1, FaceAuthUiEvent.FACE_AUTH_STOPPED_TRUST_ENABLED);
        } else {
            updateBiometricListeningState(0, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_TRUST_DISABLED);
        }
        this.mLogger.logTrustChanged(i, z3, z);
        for (int i3 = 0; i3 < this.mCallbacks.size(); i3++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i3)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onTrustChanged(i);
            }
        }
        if (z) {
            String str = null;
            if (this.mSelectedUserInteractor.getSelectedUserId(false) == i && list != null) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    str = (String) it.next();
                    if (!TextUtils.isEmpty(str)) {
                        break;
                    }
                }
            }
            this.mLogger.logTrustGrantedWithFlags(str, i2, i, z2);
            if (i == this.mSelectedUserInteractor.getSelectedUserId(false)) {
                if (z2) {
                    this.mUiEventLogger.log(TrustAgentUiEvent.TRUST_AGENT_NEWLY_UNLOCKED, ((SessionTracker) this.mSessionTrackerProvider.get()).getSessionId(1));
                }
                TrustGrantFlags trustGrantFlags = new TrustGrantFlags(i2);
                for (int i4 = 0; i4 < this.mCallbacks.size(); i4++) {
                    KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback2 = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i4)).get();
                    if (keyguardUpdateMonitorCallback2 != null) {
                        boolean z4 = this.mPrimaryBouncerIsOrWillBeShowing || this.mAlternateBouncerShowing;
                        int i5 = trustGrantFlags.mFlags;
                        keyguardUpdateMonitorCallback2.onTrustGrantedForCurrentUser(((i5 & 1) != 0 || trustGrantFlags.dismissKeyguardRequested()) && (this.mDeviceInteractive || (i5 & 4) != 0) && (z4 || trustGrantFlags.dismissKeyguardRequested()), z2, trustGrantFlags, str);
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
        boolean isTrustUsuallyManaged = this.mTrustManager.isTrustUsuallyManaged(i);
        this.mLogger.logTrustUsuallyManagedUpdated("onTrustManagedChanged", this.mUserTrustIsUsuallyManaged.get(i), i, isTrustUsuallyManaged);
        this.mUserTrustIsUsuallyManaged.put(i, isTrustUsuallyManaged);
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
        SimData simData = (SimData) this.mSimDatas.get(Integer.valueOf(i));
        if (simData == null) {
            if (!SubscriptionManager.isValidSubscriptionId(i)) {
                return false;
            }
            StringBuilder m = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "refreshSimState put [subId=", ", slotId=", ", state[");
            m.append(simState);
            m.append("]");
            Log.d("KeyguardUpdateMonitor", m.toString());
            this.mSimDatas.put(Integer.valueOf(i), new SimData(simState, i2, i));
            return true;
        }
        boolean z = simData.simState != simState;
        if (z) {
            StringBuilder m2 = RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(i, i2, "refreshSimState [subId=", ", slotId=", "] state [");
            m2.append(simData.simState);
            m2.append("] changed to TelephonyManager state[");
            m2.append(simState);
            m2.append("]");
            Log.d("KeyguardUpdateMonitor", m2.toString());
        }
        simData.simState = simState;
        simData.subId = i;
        simData.slotId = i2;
        return z;
    }

    public void registerCallback(KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback) {
        Assert.isMainThread();
        this.mLogger.logRegisterCallback(keyguardUpdateMonitorCallback);
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
        this.mLogger.logUnregisterCallback(keyguardUpdateMonitorCallback);
        this.mCallbacks.removeIf(new Predicate() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((WeakReference) obj).get() == KeyguardUpdateMonitorCallback.this;
            }
        });
    }

    public final void reportEmergencyCallAction() {
        Assert.isMainThread();
        handleReportEmergencyCallAction();
    }

    public final void reportSimUnlocked(int i) {
        this.mLogger.logSimUnlocked(i);
        updatedSimPinPassed(getSlotId(i));
        handleSimStateChange(i, getSlotId(i), 5);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0036, code lost:
    
        if (r0.requestActiveUnlockOnUnlockIntentLegacy == false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0050, code lost:
    
        r3 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x004d, code lost:
    
        if (r0.requestActiveUnlockOnWakeup == false) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0062, code lost:
    
        if (r0.shouldRequestActiveUnlockOnUnlockIntentFromBiometricEnrollment() == false) goto L35;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void requestActiveUnlock(com.android.keyguard.ActiveUnlockConfig.ActiveUnlockRequestOrigin r7, java.lang.String r8, boolean r9) {
        /*
            r6 = this;
            com.android.keyguard.KeyguardUpdateMonitor$16 r0 = r6.mHandler
            r1 = 336(0x150, float:4.71E-43)
            boolean r0 = r0.hasMessages(r1)
            if (r0 == 0) goto Lb
            return
        Lb:
            com.android.keyguard.ActiveUnlockConfig r0 = r6.mActiveUnlockConfig
            r0.getClass()
            int[] r2 = com.android.keyguard.ActiveUnlockConfig.WhenMappings.$EnumSwitchMapping$0
            int r3 = r7.ordinal()
            r2 = r2[r3]
            r3 = 1
            r4 = 0
            if (r2 == r3) goto L68
            r5 = 2
            if (r2 == r5) goto L65
            r5 = 3
            if (r2 == r5) goto L52
            r5 = 4
            if (r2 == r5) goto L3f
            r5 = 5
            if (r2 != r5) goto L39
            boolean r2 = r0.requestActiveUnlockOnWakeup
            if (r2 != 0) goto L6a
            boolean r2 = r0.requestActiveUnlockOnUnlockIntent
            if (r2 != 0) goto L6a
            boolean r2 = r0.requestActiveUnlockOnBioFail
            if (r2 != 0) goto L6a
            boolean r0 = r0.requestActiveUnlockOnUnlockIntentLegacy
            if (r0 == 0) goto L50
            goto L6a
        L39:
            kotlin.NoWhenBranchMatchedException r6 = new kotlin.NoWhenBranchMatchedException
            r6.<init>()
            throw r6
        L3f:
            boolean r2 = r0.requestActiveUnlockOnBioFail
            if (r2 != 0) goto L6a
            boolean r2 = r0.requestActiveUnlockOnUnlockIntentLegacy
            if (r2 != 0) goto L6a
            boolean r2 = r0.requestActiveUnlockOnUnlockIntent
            if (r2 != 0) goto L6a
            boolean r0 = r0.requestActiveUnlockOnWakeup
            if (r0 == 0) goto L50
            goto L6a
        L50:
            r3 = r4
            goto L6a
        L52:
            boolean r2 = r0.requestActiveUnlockOnUnlockIntent
            if (r2 != 0) goto L6a
            boolean r2 = r0.requestActiveUnlockOnUnlockIntentLegacy
            if (r2 != 0) goto L6a
            boolean r2 = r0.requestActiveUnlockOnWakeup
            if (r2 != 0) goto L6a
            boolean r0 = r0.shouldRequestActiveUnlockOnUnlockIntentFromBiometricEnrollment()
            if (r0 == 0) goto L50
            goto L6a
        L65:
            boolean r3 = r0.requestActiveUnlockOnUnlockIntentLegacy
            goto L6a
        L68:
            boolean r3 = r0.requestActiveUnlockOnWakeup
        L6a:
            com.android.keyguard.ActiveUnlockConfig$ActiveUnlockRequestOrigin r0 = com.android.keyguard.ActiveUnlockConfig.ActiveUnlockRequestOrigin.WAKE
            if (r7 != r0) goto La2
            if (r3 != 0) goto La2
            com.android.keyguard.ActiveUnlockConfig r0 = r6.mActiveUnlockConfig
            boolean r2 = r0.requestActiveUnlockOnWakeup
            if (r2 != 0) goto L82
            boolean r2 = r0.requestActiveUnlockOnUnlockIntent
            if (r2 != 0) goto L82
            boolean r2 = r0.requestActiveUnlockOnBioFail
            if (r2 != 0) goto L82
            boolean r0 = r0.requestActiveUnlockOnUnlockIntentLegacy
            if (r0 == 0) goto La2
        L82:
            com.android.keyguard.KeyguardUpdateMonitor$16 r7 = r6.mHandler
            boolean r7 = r7.hasMessages(r1)
            if (r7 == 0) goto L8b
            goto La1
        L8b:
            boolean r7 = r6.shouldTriggerActiveUnlock()
            if (r7 == 0) goto La1
            com.android.keyguard.logging.KeyguardUpdateMonitorLogger r7 = r6.mLogger
            r7.logActiveUnlockTriggered(r8)
            android.app.trust.TrustManager r7 = r6.mTrustManager
            com.android.systemui.user.domain.interactor.SelectedUserInteractor r6 = r6.mSelectedUserInteractor
            int r6 = r6.getSelectedUserId(r4)
            r7.reportUserMayRequestUnlock(r6)
        La1:
            return
        La2:
            if (r3 == 0) goto Lba
            boolean r0 = r6.shouldTriggerActiveUnlock()
            if (r0 == 0) goto Lba
            com.android.keyguard.logging.KeyguardUpdateMonitorLogger r0 = r6.mLogger
            r0.logUserRequestedUnlock(r7, r8, r9)
            android.app.trust.TrustManager r7 = r6.mTrustManager
            com.android.systemui.user.domain.interactor.SelectedUserInteractor r6 = r6.mSelectedUserInteractor
            int r6 = r6.getSelectedUserId(r4)
            r7.reportUserRequestedUnlock(r6, r9)
        Lba:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardUpdateMonitor.requestActiveUnlock(com.android.keyguard.ActiveUnlockConfig$ActiveUnlockRequestOrigin, java.lang.String, boolean):void");
    }

    public void resetBiometricListeningState() {
        this.mFingerprintRunningState = 0;
        this.mFingerprintDetectRunning = false;
        this.mFaceRunningState = 0;
    }

    public final boolean resolveNeedsSlowUnlockTransition() {
        if (this.mUserIsUnlocked.get(this.mSelectedUserInteractor.getSelectedUserId(false))) {
            return false;
        }
        ResolveInfo resolveActivityAsUser = this.mPackageManager.resolveActivityAsUser(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME"), 0, this.mSelectedUserInteractor.getSelectedUserId(false));
        if (resolveActivityAsUser != null) {
            return FALLBACK_HOME_COMPONENT.equals(resolveActivityAsUser.getComponentInfo().getComponentName());
        }
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        keyguardUpdateMonitorLogger.logBuffer.log("KeyguardUpdateMonitorLog", LogLevel.WARNING, "resolveNeedsSlowUnlockTransition: returning false since activity could not be resolved.", null);
        return false;
    }

    public final void sendUpdates(KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback) {
        keyguardUpdateMonitorCallback.onRefreshBatteryInfo(getKeyguardBatteryStatus());
        keyguardUpdateMonitorCallback.onTimeChanged();
        keyguardUpdateMonitorCallback.onPhoneStateChanged(this.mPhoneState);
        keyguardUpdateMonitorCallback.onRefreshCarrierInfo(null);
        keyguardUpdateMonitorCallback.onKeyguardVisibilityChanged(isKeyguardVisible());
        keyguardUpdateMonitorCallback.onTelephonyCapable(this.mTelephonyCapable);
        Iterator it = this.mSimDatas.entrySet().iterator();
        while (it.hasNext()) {
            SimData simData = (SimData) ((Map.Entry) it.next()).getValue();
            if (isSimPinPassed(simData.slotId, simData.simState)) {
                this.mLogger.d("sendUpdates isSimPinPassed state.slotId = " + simData.slotId);
                return;
            }
            keyguardUpdateMonitorCallback.onSimStateChanged(simData.subId, simData.slotId, simData.simState);
        }
    }

    public void setAlternateBouncerShowing(boolean z) {
        this.mAlternateBouncerShowing = z;
        if (z) {
            updateFaceListeningState(0, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_ALTERNATE_BIOMETRIC_BOUNCER_SHOWN);
            requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin.UNLOCK_INTENT, "alternateBouncer");
        }
        updateFingerprintListeningState(2);
    }

    public void setAssistantVisible(boolean z) {
        this.mAssistantVisible = z;
        this.mLogger.logAssistantVisible(z);
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
        this.mLogger.logFaceRunningState(i);
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
        this.mLogger.logFingerprintRunningState(i);
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
                this.mLogger.logForceIsDismissibleKeyguard(z);
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
            boolean isKeyguardVisible = isKeyguardVisible();
            this.mKeyguardShowing = z;
            this.mKeyguardOccluded = z2;
            boolean isKeyguardVisible2 = isKeyguardVisible();
            this.mLogger.logKeyguardShowingChanged(z, z2, isKeyguardVisible2);
            if (isKeyguardVisible2 != isKeyguardVisible) {
                if (isKeyguardVisible2) {
                    this.mSecureCameraLaunched = false;
                }
                for (int i = 0; i < this.mCallbacks.size(); i++) {
                    KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i)).get();
                    if (keyguardUpdateMonitorCallback != null) {
                        keyguardUpdateMonitorCallback.onKeyguardVisibilityChanged(isKeyguardVisible2);
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
            this.mLogger.logUserSwitching(this.mSelectedUserInteractor.getSelectedUserId(false), "from setSwitchingUser");
        } else {
            this.mLogger.logUserSwitchComplete(this.mSelectedUserInteractor.getSelectedUserId(false), "from setSwitchingUser");
        }
        this.mSwitchingUser = z;
        post(new KeyguardUpdateMonitor$$ExternalSyntheticLambda1(this, 2));
    }

    public boolean shouldListenForFace() {
        return false;
    }

    public boolean shouldListenForFingerprint(boolean z) {
        boolean z2;
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
        boolean z3 = !getUserHasTrust(selectedUserId);
        BiometricAuthenticated biometricAuthenticated = this.mUserFingerprintAuthenticated.get(this.mSelectedUserInteractor.getSelectedUserId());
        boolean z4 = this.mAssistantVisible && this.mKeyguardOccluded && (biometricAuthenticated == null || !biometricAuthenticated.mAuthenticated) && !this.mUserHasTrust.get(this.mSelectedUserInteractor.getSelectedUserId(), false);
        boolean z5 = isKeyguardVisible() || !this.mDeviceInteractive || (this.mPrimaryBouncerIsOrWillBeShowing && !this.mKeyguardGoingAway) || this.mGoingToSleep || z4 || (((z2 = this.mKeyguardOccluded) && this.mIsDreaming) || (z2 && z3 && this.mKeyguardShowing && (this.mOccludingAppRequestingFp || z || this.mAlternateBouncerShowing || this.mAllowFingerprintOnCurrentOccludingActivity)));
        boolean z6 = this.mBiometricEnabledForUser.get(selectedUserId);
        boolean userCanSkipBouncer = getUserCanSkipBouncer(selectedUserId);
        boolean isFingerprintDisabled = isFingerprintDisabled(selectedUserId);
        boolean z7 = (this.mSwitchingUser || isFingerprintDisabled || (this.mKeyguardGoingAway && this.mDeviceInteractive) || !this.mIsSystemUser || !z6 || isUserInLockdown(selectedUserId)) ? false : true;
        boolean z8 = !isUnlockingWithBiometricAllowed(BiometricSourceType.FINGERPRINT);
        boolean z9 = z5 && z7 && (!z8 || !this.mPrimaryBouncerIsOrWillBeShowing) && (!z || (!userCanSkipBouncer && !z8 && z3)) && !this.mBiometricPromptShowing;
        logListenerModelData(new KeyguardFingerprintListenModel(System.currentTimeMillis(), selectedUserId, z9, this.mAllowFingerprintOnCurrentOccludingActivity, this.mAlternateBouncerShowing, z6, this.mBiometricPromptShowing, this.mPrimaryBouncerIsOrWillBeShowing, userCanSkipBouncer, this.mCredentialAttempted, this.mDeviceInteractive, this.mIsDreaming, isFingerprintDisabled, this.mFingerprintLockedOut, this.mGoingToSleep, this.mKeyguardGoingAway, isKeyguardVisible(), this.mKeyguardOccluded, this.mOccludingAppRequestingFp, z4, z8, this.mSwitchingUser, this.mIsSystemUser, z, z3));
        return z9;
    }

    public final boolean shouldTriggerActiveUnlock() {
        boolean z = false;
        boolean z2 = this.mAssistantVisible && this.mKeyguardOccluded && !this.mUserHasTrust.get(this.mSelectedUserInteractor.getSelectedUserId(), false);
        boolean z3 = this.mPrimaryBouncerFullyShown || this.mAlternateBouncerShowing || !(!isKeyguardVisible() || this.mGoingToSleep || this.mStatusBarState == 2);
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
        boolean z4 = getUserCanSkipBouncer(selectedUserId) || !this.mLockPatternUtils.isSecure(selectedUserId);
        boolean isFingerprintLockedOut = isFingerprintLockedOut();
        boolean z5 = !isUnlockingWithBiometricAllowed(true);
        if ((this.mAuthInterruptActive || z2 || z3) && !this.mSwitchingUser && !z4 && !isFingerprintLockedOut && !z5 && !this.mKeyguardGoingAway && !this.mSecureCameraLaunched) {
            z = true;
        }
        logListenerModelData(new KeyguardActiveUnlockModel(System.currentTimeMillis(), selectedUserId, z, z3, this.mAuthInterruptActive, isFingerprintLockedOut, z5, this.mSwitchingUser, z2, z4));
        return z;
    }

    /* JADX WARN: Type inference failed for: r0v31, types: [com.android.keyguard.KeyguardUpdateMonitor$22] */
    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (!this.mDeviceProvisioned) {
            this.mDeviceProvisionedObserver = new ContentObserver(this.mHandler) { // from class: com.android.keyguard.KeyguardUpdateMonitor.22
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    super.onChange(z);
                    KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                    int i = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                    keyguardUpdateMonitor.mDeviceProvisioned = keyguardUpdateMonitor.isDeviceProvisionedInSettingsDb();
                    KeyguardUpdateMonitor keyguardUpdateMonitor2 = KeyguardUpdateMonitor.this;
                    if (keyguardUpdateMonitor2.mDeviceProvisioned) {
                        keyguardUpdateMonitor2.mHandler.sendEmptyMessage(308);
                    }
                    KeyguardUpdateMonitor keyguardUpdateMonitor3 = KeyguardUpdateMonitor.this;
                    keyguardUpdateMonitor3.mLogger.logDeviceProvisionedState(keyguardUpdateMonitor3.mDeviceProvisioned);
                }
            };
            this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("device_provisioned"), false, this.mDeviceProvisionedObserver);
            boolean isDeviceProvisionedInSettingsDb = isDeviceProvisionedInSettingsDb();
            if (isDeviceProvisionedInSettingsDb != this.mDeviceProvisioned) {
                this.mDeviceProvisioned = isDeviceProvisionedInSettingsDb;
                if (isDeviceProvisionedInSettingsDb) {
                    sendEmptyMessage(308);
                }
            }
        }
        this.mBatteryStatus = new BatteryStatus(1, 100, 0, 1, 0, true);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_TICK");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        intentFilter.addAction("android.intent.action.SIM_STATE_CHANGED");
        intentFilter.addAction("android.intent.action.SERVICE_STATE");
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        intentFilter.addAction("android.telephony.action.SERVICE_PROVIDERS_UPDATED");
        intentFilter.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        intentFilter.addAction("android.hardware.usb.action.USB_PORT_COMPLIANCE_CHANGED");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        this.mBroadcastDispatcher.registerReceiverWithHandler(this.mBroadcastReceiver, intentFilter, this.mHandler);
        this.mBackgroundExecutor.execute(new KeyguardUpdateMonitor$$ExternalSyntheticLambda1(this, 5));
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
        runSystemUserOnly(new KeyguardUpdateMonitor$$ExternalSyntheticLambda1(this, 6));
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
        runSystemUserOnly(new KeyguardUpdateMonitor$$ExternalSyntheticLambda1(this, 7));
        this.mAuthController.addCallback(new AnonymousClass20());
        ((DevicePostureControllerImpl) this.mDevicePostureController).addCallback(this.mPostureCallback);
        this.mTaskStackChangeListeners.registerTaskStackListener(this.mTaskStackListener);
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId(true);
        this.mUserIsUnlocked.put(selectedUserId, this.mUserManager.isUserUnlocked(selectedUserId));
        this.mLogoutEnabled = this.mDevicePolicyManager.isLogoutEnabled();
        updateSecondaryLockscreenRequirement(selectedUserId);
        for (UserInfo userInfo : this.mUserManager.getUsers()) {
            boolean isTrustUsuallyManaged = this.mTrustManager.isTrustUsuallyManaged(userInfo.id);
            KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
            int i = userInfo.id;
            keyguardUpdateMonitorLogger.logTrustUsuallyManagedUpdated("init from constructor", this.mUserTrustIsUsuallyManaged.get(i), i, isTrustUsuallyManaged);
            this.mUserTrustIsUsuallyManaged.put(userInfo.id, isTrustUsuallyManaged);
        }
        if (WirelessUtils.isAirplaneModeOn(this.mContext) && !hasMessages(329)) {
            sendEmptyMessage(329);
        }
        runSystemUserOnly(new KeyguardUpdateMonitor$$ExternalSyntheticLambda1(this, 8), this.mBackgroundExecutor);
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor(SettingsHelper.INDEX_TIME_12_24), false, this.mTimeFormatChangeObserver, -1);
    }

    public void startBiometricWatchdog() {
        this.mBackgroundExecutor.execute(new KeyguardUpdateMonitor$$ExternalSyntheticLambda1(this, 4));
    }

    public void startListeningForFingerprint(boolean z) {
        int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId(false);
        boolean isUnlockWithFingerprintPossible = isUnlockWithFingerprintPossible(selectedUserId);
        if (this.mFingerprintCancelSignal != null) {
            this.mLogger.logUnexpectedFpCancellationSignalState(this.mFingerprintRunningState, isUnlockWithFingerprintPossible);
        }
        int i = this.mFingerprintRunningState;
        if (i == 2) {
            setFingerprintRunningState(3);
            return;
        }
        if (i != 3 && isUnlockWithFingerprintPossible) {
            this.mFingerprintCancelSignal = new CancellationSignal();
            FingerprintAuthenticateOptions build = new FingerprintAuthenticateOptions.Builder().setUserId(selectedUserId).build();
            if (z) {
                this.mLogger.v("startListeningForFingerprint - detect");
                this.mFpm.detectFingerprint(this.mFingerprintCancelSignal, this.mFingerprintDetectionCallback, build);
                this.mFingerprintDetectRunning = true;
            } else {
                this.mLogger.v("startListeningForFingerprint");
                this.mFpm.authenticate((FingerprintManager.CryptoObject) null, this.mFingerprintCancelSignal, this.mFingerprintAuthenticationCallback, (Handler) null, build);
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
            this.mLogger.logHandlerHasAuthContinueMsgs(i);
            return;
        }
        if (!this.mAuthController.mAllFingerprintAuthenticatorsRegistered) {
            this.mLogger.d("All FP authenticators not registered, skipping FP listening state update");
            return;
        }
        boolean shouldListenForFingerprint = shouldListenForFingerprint(isUdfpsSupported());
        int i2 = this.mFingerprintRunningState;
        boolean z = i2 == 1;
        boolean z2 = z || i2 == 3;
        boolean z3 = !isUnlockingWithBiometricAllowed(BiometricSourceType.FINGERPRINT);
        if (z2 && !shouldListenForFingerprint) {
            if (i == 0) {
                this.mLogger.v("Ignoring stopListeningForFingerprint()");
                return;
            } else {
                stopListeningForFingerprint();
                return;
            }
        }
        if (!z2 && shouldListenForFingerprint) {
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
            if (!z3) {
                this.mLogger.v("Ignoring startListeningForFingerprint() switch detect -> auth");
                return;
            }
            this.mLogger.v("Allowing startListeningForFingerprint(detect) despite BIOMETRIC_ACTION_STOP since auth was running before.");
        }
        startListeningForFingerprint(z3);
    }

    public final void updateSecondaryLockscreenRequirement(int i) {
        Intent intent = (Intent) ((HashMap) this.mSecondaryLockscreenRequirement).get(Integer.valueOf(i));
        boolean isSecondaryLockscreenEnabled = this.mDevicePolicyManager.isSecondaryLockscreenEnabled(UserHandle.of(i));
        if (isSecondaryLockscreenEnabled && intent == null) {
            ComponentName profileOwnerOrDeviceOwnerSupervisionComponent = this.mDevicePolicyManager.getProfileOwnerOrDeviceOwnerSupervisionComponent(UserHandle.of(i));
            if (profileOwnerOrDeviceOwnerSupervisionComponent == null) {
                this.mLogger.logMissingSupervisorAppError(i);
                return;
            }
            ResolveInfo resolveService = this.mPackageManager.resolveService(new Intent("android.app.action.BIND_SECONDARY_LOCKSCREEN_SERVICE").setPackage(profileOwnerOrDeviceOwnerSupervisionComponent.getPackageName()), 0);
            if (resolveService == null || resolveService.serviceInfo == null) {
                return;
            }
            Intent component = new Intent().setComponent(resolveService.serviceInfo.getComponentName());
            ((HashMap) this.mSecondaryLockscreenRequirement).put(Integer.valueOf(i), component);
        } else {
            if (isSecondaryLockscreenEnabled || intent == null) {
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
        return i == 2 || i == 3 || i == 7 || (LsRune.SECURITY_SIM_PERSO_LOCK && i == 12);
    }

    public void stopListeningForFace(FaceAuthUiEvent faceAuthUiEvent) {
    }

    public final void requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin activeUnlockRequestOrigin, String str) {
        KeyguardBypassController keyguardBypassController;
        requestActiveUnlock(activeUnlockRequestOrigin, str, (isFaceEnabledAndEnrolled() && (keyguardBypassController = this.mKeyguardBypassController) != null && keyguardBypassController.canBypass()) || this.mAlternateBouncerShowing || this.mPrimaryBouncerFullyShown || isUdfpsFingerDown());
    }

    public final void onIsActiveUnlockRunningChanged(boolean z, int i) {
    }

    public void updateFaceListeningState(int i, FaceAuthUiEvent faceAuthUiEvent) {
    }
}
