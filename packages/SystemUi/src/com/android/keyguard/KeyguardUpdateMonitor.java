package com.android.keyguard;

import android.app.ActivityTaskManager;
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
import android.graphics.Point;
import android.hardware.SensorPrivacyManager;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.biometrics.CryptoObject;
import android.hardware.biometrics.IBiometricEnabledOnKeyguardCallback;
import android.hardware.biometrics.SensorPropertiesInternal;
import android.hardware.face.FaceAuthenticateOptions;
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
import android.support.v4.media.AbstractC0000x2c234b15;
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
import android.util.SparseIntArray;
import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.ActiveUnlockConfig;
import com.android.keyguard.KeyguardActiveUnlockModel;
import com.android.keyguard.KeyguardFaceListenModel;
import com.android.keyguard.KeyguardFingerprintListenModel;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger;
import com.android.settingslib.WirelessUtils;
import com.android.settingslib.fuelgauge.BatteryStatus;
import com.android.systemui.DejankUtils;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.shared.constants.TrustAgentUiEvent;
import com.android.systemui.keyguard.shared.model.SysUiFaceAuthenticateOptions;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.SessionTracker;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.system.TaskStackChangeListener;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.statusbar.policy.DevicePostureControllerImpl;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.settings.SecureSettings;
import com.google.android.collect.Lists;
import com.samsung.android.knox.custom.CustomDeviceManager;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.knox.net.vpn.VpnErrorValues;
import com.sec.ims.settings.ImsProfile;
import com.sec.ims.volte2.data.VolteConstants;
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
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import javax.inject.Provider;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class KeyguardUpdateMonitor implements TrustManager.TrustListener, Dumpable, KeyguardSecUpdateMonitor {
    public static final int BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED = -1;
    protected static final int BIOMETRIC_STATE_CANCELLING = 2;
    protected static final int BIOMETRIC_STATE_CANCELLING_RESTARTING = 3;
    protected static final int DEFAULT_CANCEL_SIGNAL_TIMEOUT = 3000;
    protected static final int HAL_POWER_PRESS_TIMEOUT = 50;
    public static int sCurrentUser;
    public int mActiveMobileDataSubscription;
    public final ActiveUnlockConfig mActiveUnlockConfig;
    public final KeyguardActiveUnlockModel.Buffer mActiveUnlockTriggerBuffer;
    public boolean mAlternateBouncerShowing;
    public boolean mAssistantVisible;
    public final AuthController mAuthController;
    public boolean mAuthInterruptActive;
    public final Executor mBackgroundExecutor;
    BatteryStatus mBatteryStatus;
    public final C08052 mBiometricEnabledCallback;
    public final SparseBooleanArray mBiometricEnabledForUser;
    public final SparseIntArray mBiometricType;
    public final SparseBooleanArray mBiometricsFace;
    public final SparseBooleanArray mBiometricsFingerprint;
    protected final BroadcastReceiver mBroadcastAllReceiver;
    protected final BroadcastReceiver mBroadcastReceiver;
    public final ArrayList mCallbacks;
    public final CarrierConfigManager mCarrierConfigManager;
    protected int mConfigFaceAuthSupportedPosture;
    public final Context mContext;
    public boolean mCredentialAttempted;
    public boolean mDeviceInteractive;
    public final DevicePolicyManager mDevicePolicyManager;
    public boolean mDeviceProvisioned;
    public C080721 mDeviceProvisionedObserver;
    final FaceManager.AuthenticationCallback mFaceAuthenticationCallback;
    public final KeyguardUpdateMonitor$$ExternalSyntheticLambda3 mFaceCancelNotReceived;
    CancellationSignal mFaceCancelSignal;
    public final KeyguardUpdateMonitor$$ExternalSyntheticLambda7 mFaceDetectionCallback;
    public final KeyguardFaceListenModel.Buffer mFaceListenBuffer;
    public boolean mFaceLockedOutPermanent;
    public final C079611 mFaceLockoutResetCallback;
    public final FaceManager mFaceManager;
    public int mFaceRunningState;
    public List mFaceSensorProperties;
    public final FaceWakeUpTriggersConfig mFaceWakeUpTriggersConfig;
    final FingerprintManager.AuthenticationCallback mFingerprintAuthenticationCallback;
    CancellationSignal mFingerprintCancelSignal;
    public final KeyguardUpdateMonitor$$ExternalSyntheticLambda6 mFingerprintDetectionCallback;
    public final KeyguardFingerprintListenModel.Buffer mFingerprintListenBuffer;
    public boolean mFingerprintLockedOut;
    public boolean mFingerprintLockedOutPermanent;
    public final C079510 mFingerprintLockoutResetCallback;
    protected int mFingerprintRunningState;
    public List mFingerprintSensorProperties;
    protected final Runnable mFpCancelNotReceived;
    public final FingerprintManager mFpm;
    public boolean mGoingToSleep;
    public final HandlerC080015 mHandler;
    public int mHardwareFaceUnavailableRetryCount;
    public int mHardwareFingerprintUnavailableRetryCount;
    boolean mIncompatibleCharger;
    public final InteractionJankMonitor mInteractionJankMonitor;
    public boolean mIsDreaming;
    public boolean mIsFaceEnrolled;
    public final boolean mIsSystemUser;
    public final HashMap mIsUnlockWithFingerprintPossible;
    public KeyguardBypassController mKeyguardBypassController;
    public boolean mKeyguardGoingAway;
    public boolean mKeyguardOccluded;
    public boolean mKeyguardShowing;
    public final LatencyTracker mLatencyTracker;
    public final LockPatternUtils mLockPatternUtils;
    public final KeyguardUpdateMonitorLogger mLogger;
    public boolean mLogoutEnabled;
    public boolean mNeedsSlowUnlockTransition;
    public boolean mOccludingAppRequestingFace;
    public boolean mOccludingAppRequestingFp;
    public final PackageManager mPackageManager;
    public int mPhoneState;
    public TelephonyCallback.ActiveDataSubscriptionIdListener mPhoneStateListener;
    final DevicePostureController.Callback mPostureCallback;
    public int mPostureState;
    public boolean mPrimaryBouncerFullyShown;
    public boolean mPrimaryBouncerIsOrWillBeShowing;
    public final RunnableC08136 mRetryFaceAuthentication;
    public final RunnableC08125 mRetryFingerprintAuthenticationAfterHwUnavailable;
    public final Map mSecondaryLockscreenRequirement;
    public boolean mSecureCameraLaunched;
    public final SensorPrivacyManager mSensorPrivacyManager;
    public final HashMap mServiceStates;
    public final Provider mSessionTrackerProvider;
    public final HashMap mSimDatas;
    public int mStatusBarState;
    public final StatusBarStateController mStatusBarStateController;
    public final C07941 mStatusBarStateControllerListener;
    public StrongAuthTracker mStrongAuthTracker;
    public List mSubscriptionInfo;
    public final C08114 mSubscriptionListener;
    public final SubscriptionManager mSubscriptionManager;
    public boolean mSwitchingUser;
    public final C080822 mTaskStackListener;
    protected boolean mTelephonyCapable;
    public final TelephonyListenerManager mTelephonyListenerManager;
    public final TelephonyManager mTelephonyManager;
    public final C080419 mTimeFormatChangeObserver;
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.KeyguardUpdateMonitor$18 */
    public final class C080318 implements AuthController.Callback {
        public final /* synthetic */ Executor val$mainExecutor;

        public C080318(Executor executor) {
            this.val$mainExecutor = executor;
        }

        @Override // com.android.systemui.biometrics.AuthController.Callback
        public final void onAllAuthenticatorsRegistered(int i) {
            this.val$mainExecutor.execute(new KeyguardUpdateMonitor$2$$ExternalSyntheticLambda0(this, 1));
        }

        @Override // com.android.systemui.biometrics.AuthController.Callback
        public final void onEnrollmentsChanged(int i) {
            this.val$mainExecutor.execute(new KeyguardUpdateMonitor$2$$ExternalSyntheticLambda0(this, 2));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.KeyguardUpdateMonitor$2 */
    public final class C08052 extends IBiometricEnabledOnKeyguardCallback.Stub {
        public C08052() {
        }

        public final void onChanged(boolean z, int i) {
            post(new KeyguardUpdateMonitor$$ExternalSyntheticLambda2(this, i, z));
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.KeyguardUpdateMonitor$23 */
    public abstract /* synthetic */ class AbstractC080923 {
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

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    class BiometricAuthenticated {
        public final boolean mAuthenticated;
        public final boolean mIsStrongBiometric;

        public BiometricAuthenticated(boolean z, boolean z2) {
            this.mAuthenticated = z;
            this.mIsStrongBiometric = z2;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
            return ConstraintWidget$$ExternalSyntheticOutline0.m19m(sb, this.subId, "}");
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class StrongAuthTracker extends LockPatternUtils.StrongAuthTracker {
        public StrongAuthTracker(Context context) {
            super(context);
        }

        public final boolean hasUserAuthenticatedSinceBoot() {
            return (getStrongAuthForUser(KeyguardUpdateMonitor.getCurrentUser()) & 1) == 0;
        }

        public final void onIsNonStrongBiometricAllowedChanged(int i) {
            KeyguardUpdateMonitor.this.notifyNonStrongBiometricAllowedChanged(i);
        }

        public final void onStrongAuthRequiredChanged(int i) {
            KeyguardUpdateMonitor.this.notifyStrongAuthAllowedChanged(i);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v0, types: [com.android.keyguard.KeyguardUpdateMonitor$1, com.android.systemui.plugins.statusbar.StatusBarStateController$StateListener] */
    /* JADX WARN: Type inference failed for: r13v12, types: [com.android.keyguard.KeyguardUpdateMonitor$21] */
    /* JADX WARN: Type inference failed for: r14v10, types: [android.hardware.face.FaceManager$LockoutResetCallback, com.android.keyguard.KeyguardUpdateMonitor$11] */
    /* JADX WARN: Type inference failed for: r14v12, types: [com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda6] */
    /* JADX WARN: Type inference failed for: r14v13, types: [com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda7] */
    /* JADX WARN: Type inference failed for: r14v20, types: [com.android.keyguard.KeyguardUpdateMonitor$22, com.android.systemui.shared.system.TaskStackChangeListener] */
    /* JADX WARN: Type inference failed for: r14v8, types: [android.telephony.SubscriptionManager$OnSubscriptionsChangedListener, com.android.keyguard.KeyguardUpdateMonitor$4] */
    /* JADX WARN: Type inference failed for: r14v9, types: [android.hardware.fingerprint.FingerprintManager$LockoutResetCallback, com.android.keyguard.KeyguardUpdateMonitor$10] */
    /* JADX WARN: Type inference failed for: r15v17, types: [com.android.keyguard.KeyguardUpdateMonitor$5] */
    /* JADX WARN: Type inference failed for: r15v18, types: [com.android.keyguard.KeyguardUpdateMonitor$6] */
    /* JADX WARN: Type inference failed for: r1v14, types: [android.database.ContentObserver, com.android.keyguard.KeyguardUpdateMonitor$19] */
    /* JADX WARN: Type inference failed for: r8v11, types: [android.os.Handler, com.android.keyguard.KeyguardUpdateMonitor$15] */
    public KeyguardUpdateMonitor(Context context, UserTracker userTracker, Looper looper, BroadcastDispatcher broadcastDispatcher, SecureSettings secureSettings, DumpManager dumpManager, Executor executor, Executor executor2, StatusBarStateController statusBarStateController, LockPatternUtils lockPatternUtils, AuthController authController, TelephonyListenerManager telephonyListenerManager, InteractionJankMonitor interactionJankMonitor, LatencyTracker latencyTracker, ActiveUnlockConfig activeUnlockConfig, KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger, UiEventLogger uiEventLogger, Provider provider, TrustManager trustManager, SubscriptionManager subscriptionManager, UserManager userManager, IDreamManager iDreamManager, DevicePolicyManager devicePolicyManager, SensorPrivacyManager sensorPrivacyManager, TelephonyManager telephonyManager, PackageManager packageManager, FaceManager faceManager, FingerprintManager fingerprintManager, BiometricManager biometricManager, FaceWakeUpTriggersConfig faceWakeUpTriggersConfig, CarrierConfigManager carrierConfigManager, DevicePostureController devicePostureController, Optional<Object> optional) {
        ?? r13 = new StatusBarStateController.StateListener() { // from class: com.android.keyguard.KeyguardUpdateMonitor.1
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
        this.mStatusBarStateControllerListener = r13;
        this.mSimDatas = new HashMap();
        this.mServiceStates = new HashMap();
        this.mCallbacks = Lists.newArrayList();
        this.mFingerprintRunningState = 0;
        this.mFaceRunningState = 0;
        this.mActiveMobileDataSubscription = -1;
        this.mPostureState = 0;
        this.mHardwareFingerprintUnavailableRetryCount = 0;
        this.mHardwareFaceUnavailableRetryCount = 0;
        this.mFpCancelNotReceived = new KeyguardUpdateMonitor$$ExternalSyntheticLambda3(this, 2);
        this.mFaceCancelNotReceived = new KeyguardUpdateMonitor$$ExternalSyntheticLambda3(this, 3);
        this.mBiometricEnabledCallback = new C08052();
        this.mPhoneStateListener = new TelephonyCallback.ActiveDataSubscriptionIdListener() { // from class: com.android.keyguard.KeyguardUpdateMonitor.3
            @Override // android.telephony.TelephonyCallback.ActiveDataSubscriptionIdListener
            public final void onActiveDataSubscriptionIdChanged(int i) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                keyguardUpdateMonitor.mActiveMobileDataSubscription = i;
                keyguardUpdateMonitor.mHandler.sendEmptyMessage(328);
            }
        };
        ?? r14 = new SubscriptionManager.OnSubscriptionsChangedListener() { // from class: com.android.keyguard.KeyguardUpdateMonitor.4
            @Override // android.telephony.SubscriptionManager.OnSubscriptionsChangedListener
            public final void onSubscriptionsChanged() {
                sendEmptyMessage(328);
            }
        };
        this.mSubscriptionListener = r14;
        SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
        this.mUserIsUnlocked = sparseBooleanArray;
        this.mUserHasTrust = new SparseBooleanArray();
        this.mUserTrustIsManaged = new SparseBooleanArray();
        this.mUserTrustIsUsuallyManaged = new SparseBooleanArray();
        this.mBiometricEnabledForUser = new SparseBooleanArray();
        this.mSecondaryLockscreenRequirement = new HashMap();
        this.mFingerprintListenBuffer = new KeyguardFingerprintListenModel.Buffer();
        this.mFaceListenBuffer = new KeyguardFaceListenModel.Buffer();
        this.mActiveUnlockTriggerBuffer = new KeyguardActiveUnlockModel.Buffer();
        this.mUserFingerprintAuthenticated = new SparseArray<>();
        this.mUserFaceAuthenticated = new SparseArray<>();
        this.mBiometricType = new SparseIntArray();
        this.mBiometricsFingerprint = new SparseBooleanArray();
        this.mBiometricsFace = new SparseBooleanArray();
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
        new Object() { // from class: com.android.keyguard.KeyguardUpdateMonitor.7
        };
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.keyguard.KeyguardUpdateMonitor.8
            /* JADX WARN: Code restructure failed: missing block: B:57:0x00f0, code lost:
            
                if ("NETWORK".equals(r1) != false) goto L47;
             */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onReceive(Context context2, Intent intent) {
                int i;
                String action = intent.getAction();
                KeyguardUpdateMonitor.this.mLogger.logBroadcastReceived(action);
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
                if ("android.hardware.usb.action.USB_PORT_COMPLIANCE_CHANGED".equals(action)) {
                    KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                    keyguardUpdateMonitor.mHandler.sendMessage(keyguardUpdateMonitor.getKeyguardBatteryMessage(intent));
                    return;
                }
                if (!"android.intent.action.SIM_STATE_CHANGED".equals(action)) {
                    if ("android.intent.action.PHONE_STATE".equals(action)) {
                        String stringExtra = intent.getStringExtra("state");
                        HandlerC080015 handlerC080015 = KeyguardUpdateMonitor.this.mHandler;
                        handlerC080015.sendMessage(handlerC080015.obtainMessage(VpnErrorValues.ERROR_STOPPING_CONNECTION_BEFORE_REMOVING, stringExtra));
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
                    if (!LsRune.SECURITY_DISABLE_EMERGENCY_CALL_WHEN_OFFLINE && "android.intent.action.SERVICE_STATE".equals(action)) {
                        ServiceState newFromBundle = ServiceState.newFromBundle(intent.getExtras());
                        int intExtra = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                        KeyguardUpdateMonitor.this.mLogger.logServiceStateIntent(action, newFromBundle, intExtra);
                        HandlerC080015 handlerC0800152 = KeyguardUpdateMonitor.this.mHandler;
                        handlerC0800152.sendMessage(handlerC0800152.obtainMessage(330, intExtra, 0, newFromBundle));
                        return;
                    }
                    if ("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED".equals(action)) {
                        sendEmptyMessage(328);
                        return;
                    } else if ("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED".equals(action)) {
                        sendEmptyMessage(337);
                        return;
                    } else {
                        if ("android.intent.action.LOCALE_CHANGED".equals(action)) {
                            sendEmptyMessage(VolteConstants.ErrorCode.RTP_TIME_OUT);
                            return;
                        }
                        return;
                    }
                }
                if (!"android.intent.action.SIM_STATE_CHANGED".equals(intent.getAction())) {
                    throw new IllegalArgumentException("only handles intent ACTION_SIM_STATE_CHANGED");
                }
                String stringExtra2 = intent.getStringExtra(ImsProfile.SERVICE_SS);
                int intExtra2 = intent.getIntExtra("android.telephony.extra.SLOT_INDEX", 0);
                int intExtra3 = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                if ("ABSENT".equals(stringExtra2)) {
                    i = 1;
                } else if ("LOCKED".equals(stringExtra2)) {
                    String stringExtra3 = intent.getStringExtra("reason");
                    if ("PIN".equals(stringExtra3)) {
                        i = 2;
                    } else if ("PUK".equals(stringExtra3)) {
                        i = 3;
                    } else {
                        if (!"NETWORK".equals(stringExtra3)) {
                            if (!"PERM_DISABLED".equals(stringExtra3)) {
                                if (LsRune.SECURITY_SIM_PERSO_LOCK && "PERSO".equals(stringExtra3)) {
                                    i = 12;
                                } else if (!"PERM_DISABLED".equals(stringExtra3)) {
                                }
                            }
                            i = 7;
                        }
                        i = 4;
                    }
                } else if ("CARD_IO_ERROR".equals(stringExtra2)) {
                    i = 8;
                } else if ("CARD_RESTRICTED".equals(stringExtra2)) {
                    i = 9;
                } else if ("NOT_READY".equals(stringExtra2)) {
                    i = 6;
                } else {
                    if ("READY".equals(stringExtra2) || "LOADED".equals(stringExtra2) || "IMSI".equals(stringExtra2)) {
                        i = 5;
                    }
                    i = 0;
                }
                SimData simData = new SimData(i, intExtra2, intExtra3);
                if (intent.getBooleanExtra("rebroadcastOnUnlock", false)) {
                    if (simData.simState == 1) {
                        obtainMessage(338, Boolean.TRUE).sendToTarget();
                        return;
                    }
                    return;
                }
                KeyguardUpdateMonitor.this.mLogger.logSimStateFromIntent(simData.slotId, simData.subId, action, intent.getStringExtra(ImsProfile.SERVICE_SS));
                KeyguardUpdateMonitor.this.resetSimPinPassed(simData.slotId);
                if (LsRune.SECURITY_ESIM) {
                    KeyguardUpdateMonitor.this.clearESimRemoved();
                }
                obtainMessage(304, simData.subId, simData.slotId, Integer.valueOf(simData.simState)).sendToTarget();
            }
        };
        this.mBroadcastReceiver = broadcastReceiver;
        BroadcastReceiver broadcastReceiver2 = new BroadcastReceiver() { // from class: com.android.keyguard.KeyguardUpdateMonitor.9
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
                    HandlerC080015 handlerC080015 = KeyguardUpdateMonitor.this.mHandler;
                    handlerC080015.sendMessage(handlerC080015.obtainMessage(309, sendingUserId, 0));
                    return;
                }
                if ("android.intent.action.USER_UNLOCKED".equals(action)) {
                    int intExtra = intent.getIntExtra("android.intent.extra.user_handle", -1);
                    SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("ACTION_USER_UNLOCKED. userId:", intExtra, "KeyguardUpdateMonitor");
                    HandlerC080015 handlerC0800152 = KeyguardUpdateMonitor.this.mHandler;
                    handlerC0800152.sendMessage(handlerC0800152.obtainMessage(334, intExtra, 0));
                    return;
                }
                if ("android.intent.action.USER_STOPPED".equals(action)) {
                    HandlerC080015 handlerC0800153 = KeyguardUpdateMonitor.this.mHandler;
                    handlerC0800153.sendMessage(handlerC0800153.obtainMessage(340, intent.getIntExtra("android.intent.extra.user_handle", -1), 0));
                } else if ("android.intent.action.USER_REMOVED".equals(action)) {
                    HandlerC080015 handlerC0800154 = KeyguardUpdateMonitor.this.mHandler;
                    handlerC0800154.sendMessage(handlerC0800154.obtainMessage(341, intent.getIntExtra("android.intent.extra.user_handle", -1), 0));
                } else if ("android.nfc.action.REQUIRE_UNLOCK_FOR_NFC".equals(action)) {
                    sendEmptyMessage(345);
                }
            }
        };
        this.mBroadcastAllReceiver = broadcastReceiver2;
        ?? r142 = new FingerprintManager.LockoutResetCallback() { // from class: com.android.keyguard.KeyguardUpdateMonitor.10
            public final void onLockoutReset(int i) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                int i2 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                keyguardUpdateMonitor.handleFingerprintLockoutReset(0);
            }
        };
        this.mFingerprintLockoutResetCallback = r142;
        ?? r143 = new FaceManager.LockoutResetCallback() { // from class: com.android.keyguard.KeyguardUpdateMonitor.11
            public final void onLockoutReset(int i) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                int i2 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                keyguardUpdateMonitor.handleFaceLockoutReset(0);
            }
        };
        this.mFaceLockoutResetCallback = r143;
        this.mFingerprintAuthenticationCallback = new FingerprintManager.AuthenticationCallback() { // from class: com.android.keyguard.KeyguardUpdateMonitor.12
            public final void onAuthenticationAcquired(int i) {
                Trace.beginSection("KeyguardUpdateMonitor#onAuthenticationAcquired");
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
                KeyguardUpdateMonitor.this.requestFaceAuth("Face auth triggered due to finger down on UDFPS");
            }

            public final void onUdfpsPointerUp(int i) {
                KeyguardUpdateMonitor.this.mLogger.logUdfpsPointerUp(i);
            }
        };
        this.mFingerprintDetectionCallback = new FingerprintManager.FingerprintDetectionCallback() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda6
            public final void onFingerprintDetected(int i, int i2, boolean z) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                keyguardUpdateMonitor.getClass();
                keyguardUpdateMonitor.handleBiometricDetected(i2, BiometricSourceType.FINGERPRINT, z);
            }
        };
        this.mFaceDetectionCallback = new FaceManager.FaceDetectionCallback() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda7
            public final void onFaceDetected(int i, int i2, boolean z) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                keyguardUpdateMonitor.getClass();
                keyguardUpdateMonitor.handleBiometricDetected(i2, BiometricSourceType.FACE, z);
            }
        };
        this.mFaceAuthenticationCallback = new FaceManager.AuthenticationCallback() { // from class: com.android.keyguard.KeyguardUpdateMonitor.13
            public final void onAuthenticationAcquired(int i) {
                KeyguardUpdateMonitor.this.handleFaceAcquired(i);
            }

            public final void onAuthenticationError(int i, CharSequence charSequence) {
                KeyguardUpdateMonitor.this.handleFaceError(i, charSequence.toString());
            }

            public final void onAuthenticationFailed() {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                keyguardUpdateMonitor.getClass();
                if (keyguardUpdateMonitor.mFaceLockedOutPermanent) {
                    KeyguardUpdateMonitor.this.mLogger.m84d("onAuthenticationFailed called after face has been locked out");
                } else {
                    KeyguardUpdateMonitor.this.handleFaceAuthFailed();
                }
            }

            public final void onAuthenticationHelp(int i, CharSequence charSequence) {
                KeyguardUpdateMonitor.this.handleFaceHelp(i, charSequence.toString());
            }

            public final void onAuthenticationSucceeded(FaceManager.AuthenticationResult authenticationResult) {
                KeyguardUpdateMonitor.this.handleFaceAuthenticated(authenticationResult.getUserId(), authenticationResult.isStrongBiometric());
            }
        };
        DevicePostureController.Callback callback = new DevicePostureController.Callback() { // from class: com.android.keyguard.KeyguardUpdateMonitor.14
            @Override // com.android.systemui.statusbar.policy.DevicePostureController.Callback
            public final void onPostureChanged(int i) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                int i2 = keyguardUpdateMonitor.mPostureState;
                int i3 = keyguardUpdateMonitor.mConfigFaceAuthSupportedPosture;
                boolean z = i3 == 0 || i2 == i3;
                boolean z2 = i3 == 0 || i == i3;
                keyguardUpdateMonitor.mPostureState = i;
                if (z && !z2) {
                    keyguardUpdateMonitor.mLogger.m84d("New posture does not allow face auth, stopping it");
                    keyguardUpdateMonitor.updateFaceListeningState(1, FaceAuthUiEvent.FACE_AUTH_UPDATED_POSTURE_CHANGED);
                }
                if (keyguardUpdateMonitor.mPostureState == 3) {
                    keyguardUpdateMonitor.mLogger.m84d("Posture changed to open - attempting to request active unlock");
                    keyguardUpdateMonitor.requestActiveUnlockFromWakeReason(12, false);
                }
            }
        };
        this.mPostureCallback = callback;
        this.mFingerprintSensorProperties = Collections.emptyList();
        this.mFaceSensorProperties = Collections.emptyList();
        this.mIsUnlockWithFingerprintPossible = new HashMap();
        UserTracker.Callback callback2 = new UserTracker.Callback() { // from class: com.android.keyguard.KeyguardUpdateMonitor.20
            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanged(int i, Context context2) {
                HandlerC080015 handlerC080015 = KeyguardUpdateMonitor.this.mHandler;
                handlerC080015.sendMessage(handlerC080015.obtainMessage(314, i, 0));
            }

            @Override // com.android.systemui.settings.UserTracker.Callback
            public final void onUserChanging(int i, Context context2, CountDownLatch countDownLatch) {
                HandlerC080015 handlerC080015 = KeyguardUpdateMonitor.this.mHandler;
                handlerC080015.sendMessage(handlerC080015.obtainMessage(310, i, 0, countDownLatch));
            }
        };
        this.mUserChangedCallback = callback2;
        ?? r144 = new TaskStackChangeListener() { // from class: com.android.keyguard.KeyguardUpdateMonitor.22
            @Override // com.android.systemui.shared.system.TaskStackChangeListener
            public final void onTaskStackChangedBackground() {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                try {
                    ActivityTaskManager.RootTaskInfo rootTaskInfo = ActivityTaskManager.getService().getRootTaskInfo(0, 4);
                    if (rootTaskInfo == null) {
                        return;
                    }
                    keyguardUpdateMonitor.mLogger.logTaskStackChangedForAssistant(rootTaskInfo.visible);
                    HandlerC080015 handlerC080015 = keyguardUpdateMonitor.mHandler;
                    handlerC080015.sendMessage(handlerC080015.obtainMessage(335, Boolean.valueOf(rootTaskInfo.visible)));
                } catch (RemoteException e) {
                    keyguardUpdateMonitor.mLogger.logException("unable to check task stack ", e);
                }
            }
        };
        this.mTaskStackListener = r144;
        this.mContext = context;
        this.mSubscriptionManager = subscriptionManager;
        this.mUserTracker = userTracker;
        this.mTelephonyListenerManager = telephonyListenerManager;
        this.mDeviceProvisioned = isDeviceProvisionedInSettingsDb();
        this.mStrongAuthTracker = new StrongAuthTracker(context);
        this.mBackgroundExecutor = executor;
        this.mInteractionJankMonitor = interactionJankMonitor;
        this.mLatencyTracker = latencyTracker;
        this.mStatusBarStateController = statusBarStateController;
        statusBarStateController.addCallback(r13);
        this.mStatusBarState = statusBarStateController.getState();
        this.mLockPatternUtils = lockPatternUtils;
        this.mAuthController = authController;
        String name = getClass().getName();
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, name, this);
        this.mSensorPrivacyManager = sensorPrivacyManager;
        this.mActiveUnlockConfig = activeUnlockConfig;
        this.mLogger = keyguardUpdateMonitorLogger;
        this.mUiEventLogger = uiEventLogger;
        this.mSessionTrackerProvider = provider;
        this.mTrustManager = trustManager;
        this.mUserManager = userManager;
        this.mTelephonyManager = telephonyManager;
        this.mDevicePolicyManager = devicePolicyManager;
        this.mPackageManager = packageManager;
        this.mFpm = fingerprintManager;
        this.mFaceManager = faceManager;
        activeUnlockConfig.keyguardUpdateMonitor = this;
        this.mConfigFaceAuthSupportedPosture = context.getResources().getInteger(R.integer.config_face_auth_supported_posture);
        this.mFaceWakeUpTriggersConfig = faceWakeUpTriggersConfig;
        this.mCarrierConfigManager = carrierConfigManager;
        ?? r8 = new Handler(looper) { // from class: com.android.keyguard.KeyguardUpdateMonitor.15
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i = 0;
                switch (message.what) {
                    case 301:
                        KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                        int i2 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor.getClass();
                        Assert.isMainThread();
                        while (i < keyguardUpdateMonitor.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor.mCallbacks.get(i)).get();
                            if (keyguardUpdateMonitorCallback != null) {
                                keyguardUpdateMonitorCallback.onTimeChanged();
                            }
                            i++;
                        }
                        break;
                    case 302:
                        KeyguardUpdateMonitor.this.handleBatteryUpdate((BatteryStatus) message.obj);
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
                        KeyguardUpdateMonitor.this.handleSecMessage(message);
                        break;
                    case 304:
                        KeyguardUpdateMonitor.this.handleSimStateChange(message.arg1, message.arg2, ((Integer) message.obj).intValue());
                        break;
                    case VpnErrorValues.ERROR_STOPPING_CONNECTION_BEFORE_REMOVING /* 306 */:
                        KeyguardUpdateMonitor.this.handlePhoneStateChanged((String) message.obj);
                        break;
                    case 308:
                        KeyguardUpdateMonitor keyguardUpdateMonitor2 = KeyguardUpdateMonitor.this;
                        int i3 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor2.getClass();
                        Assert.isMainThread();
                        while (i < keyguardUpdateMonitor2.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback2 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor2.mCallbacks.get(i)).get();
                            if (keyguardUpdateMonitorCallback2 != null) {
                                keyguardUpdateMonitorCallback2.onDeviceProvisioned();
                            }
                            i++;
                        }
                        if (keyguardUpdateMonitor2.mDeviceProvisionedObserver != null) {
                            keyguardUpdateMonitor2.mContext.getContentResolver().unregisterContentObserver(keyguardUpdateMonitor2.mDeviceProvisionedObserver);
                            keyguardUpdateMonitor2.mDeviceProvisionedObserver = null;
                            break;
                        }
                        break;
                    case 309:
                        KeyguardUpdateMonitor.this.handleDevicePolicyManagerStateChanged(message.arg1);
                        break;
                    case 310:
                        KeyguardUpdateMonitor.this.handleUserSwitching(message.arg1, (CountDownLatch) message.obj);
                        break;
                    case 312:
                        KeyguardUpdateMonitor.this.handleKeyguardReset();
                        break;
                    case 314:
                        KeyguardUpdateMonitor.this.handleUserSwitchComplete(message.arg1);
                        break;
                    case 318:
                        KeyguardUpdateMonitor keyguardUpdateMonitor3 = KeyguardUpdateMonitor.this;
                        int i4 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor3.handleReportEmergencyCallAction();
                        break;
                    case 319:
                        Trace.beginSection("KeyguardUpdateMonitor#handler MSG_STARTED_WAKING_UP");
                        KeyguardUpdateMonitor.this.handleStartedWakingUp(message.arg1);
                        Trace.endSection();
                        break;
                    case 320:
                        KeyguardUpdateMonitor.this.handleFinishedGoingToSleep(message.arg1);
                        break;
                    case 321:
                        KeyguardUpdateMonitor.this.handleStartedGoingToSleep(message.arg1);
                        break;
                    case 322:
                        KeyguardUpdateMonitor.this.handlePrimaryBouncerChanged(message.arg1, message.arg2);
                        break;
                    case 328:
                        KeyguardUpdateMonitor keyguardUpdateMonitor4 = KeyguardUpdateMonitor.this;
                        int i5 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor4.getClass();
                        Assert.isMainThread();
                        Log.i("KeyguardUpdateMonitor", "onSubscriptionInfoChanged()");
                        keyguardUpdateMonitor4.mLogger.m85v("onSubscriptionInfoChanged()");
                        List<SubscriptionInfo> completeActiveSubscriptionInfoList = keyguardUpdateMonitor4.mSubscriptionManager.getCompleteActiveSubscriptionInfoList();
                        if (completeActiveSubscriptionInfoList != null) {
                            Iterator<SubscriptionInfo> it = completeActiveSubscriptionInfoList.iterator();
                            while (it.hasNext()) {
                                keyguardUpdateMonitor4.mLogger.logSubInfo(it.next());
                            }
                        } else {
                            keyguardUpdateMonitor4.mLogger.m85v("onSubscriptionInfoChanged: list is null");
                        }
                        List subscriptionInfo = keyguardUpdateMonitor4.getSubscriptionInfo(true);
                        ArrayList arrayList = new ArrayList();
                        HashSet hashSet = new HashSet();
                        int i6 = 0;
                        while (true) {
                            ArrayList arrayList2 = (ArrayList) subscriptionInfo;
                            if (i6 >= arrayList2.size()) {
                                Iterator it2 = keyguardUpdateMonitor4.mSimDatas.entrySet().iterator();
                                while (it2.hasNext()) {
                                    Map.Entry entry = (Map.Entry) it2.next();
                                    if (!hashSet.contains(entry.getKey())) {
                                        SimData simData = (SimData) entry.getValue();
                                        boolean refreshSimState = keyguardUpdateMonitor4.refreshSimState(simData.subId, simData.slotId);
                                        keyguardUpdateMonitor4.mLogger.logInvalidSubId(((Integer) entry.getKey()).intValue());
                                        it2.remove();
                                        if (refreshSimState) {
                                            for (int i7 = 0; i7 < keyguardUpdateMonitor4.mCallbacks.size(); i7++) {
                                                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback3 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor4.mCallbacks.get(i7)).get();
                                                if (keyguardUpdateMonitorCallback3 != null) {
                                                    keyguardUpdateMonitorCallback3.onSimStateChanged(simData.subId, simData.slotId, simData.simState);
                                                }
                                            }
                                        }
                                    }
                                }
                                for (int i8 = 0; i8 < arrayList.size(); i8++) {
                                    SimData simData2 = (SimData) keyguardUpdateMonitor4.mSimDatas.get(Integer.valueOf(((SubscriptionInfo) arrayList.get(i8)).getSubscriptionId()));
                                    for (int i9 = 0; i9 < keyguardUpdateMonitor4.mCallbacks.size(); i9++) {
                                        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback4 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor4.mCallbacks.get(i9)).get();
                                        if (keyguardUpdateMonitorCallback4 != null) {
                                            keyguardUpdateMonitorCallback4.onSimStateChanged(simData2.subId, simData2.slotId, simData2.simState);
                                        }
                                    }
                                }
                                keyguardUpdateMonitor4.callbacksRefreshCarrierInfo(null);
                                break;
                            } else {
                                SubscriptionInfo subscriptionInfo2 = (SubscriptionInfo) arrayList2.get(i6);
                                hashSet.add(Integer.valueOf(subscriptionInfo2.getSubscriptionId()));
                                if (keyguardUpdateMonitor4.refreshSimState(subscriptionInfo2.getSubscriptionId(), subscriptionInfo2.getSimSlotIndex())) {
                                    arrayList.add(subscriptionInfo2);
                                }
                                i6++;
                            }
                        }
                    case 329:
                        KeyguardUpdateMonitor keyguardUpdateMonitor5 = KeyguardUpdateMonitor.this;
                        int i10 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor5.callbacksRefreshCarrierInfo(null);
                        break;
                    case 330:
                        KeyguardUpdateMonitor.this.handleServiceStateChange(message.arg1, (ServiceState) message.obj);
                        break;
                    case CustomDeviceManager.DESTINATION_ADDRESS /* 332 */:
                        Trace.beginSection("KeyguardUpdateMonitor#handler MSG_SCREEN_TURNED_OFF");
                        KeyguardUpdateMonitor keyguardUpdateMonitor6 = KeyguardUpdateMonitor.this;
                        int i11 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor6.getClass();
                        Assert.isMainThread();
                        keyguardUpdateMonitor6.mHardwareFingerprintUnavailableRetryCount = 0;
                        keyguardUpdateMonitor6.mHardwareFaceUnavailableRetryCount = 0;
                        Trace.endSection();
                        break;
                    case 333:
                        KeyguardUpdateMonitor.this.handleDreamingStateChanged(message.arg1);
                        break;
                    case 334:
                        KeyguardUpdateMonitor.this.handleUserUnlocked(message.arg1);
                        break;
                    case 335:
                        KeyguardUpdateMonitor.this.setAssistantVisible(((Boolean) message.obj).booleanValue());
                        break;
                    case 336:
                        KeyguardUpdateMonitor.this.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_FP_AUTHENTICATED);
                        break;
                    case 337:
                        KeyguardUpdateMonitor keyguardUpdateMonitor7 = KeyguardUpdateMonitor.this;
                        int i12 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor7.getClass();
                        Assert.isMainThread();
                        boolean isLogoutEnabled = keyguardUpdateMonitor7.mDevicePolicyManager.isLogoutEnabled();
                        if (keyguardUpdateMonitor7.mLogoutEnabled != isLogoutEnabled) {
                            keyguardUpdateMonitor7.mLogoutEnabled = isLogoutEnabled;
                            while (i < keyguardUpdateMonitor7.mCallbacks.size()) {
                                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback5 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor7.mCallbacks.get(i)).get();
                                if (keyguardUpdateMonitorCallback5 != null) {
                                    keyguardUpdateMonitorCallback5.onLogoutEnabledChanged();
                                }
                                i++;
                            }
                            break;
                        }
                        break;
                    case 338:
                        KeyguardUpdateMonitor.this.updateTelephonyCapable(((Boolean) message.obj).booleanValue());
                        break;
                    case 339:
                        KeyguardUpdateMonitor keyguardUpdateMonitor8 = KeyguardUpdateMonitor.this;
                        String str = (String) message.obj;
                        int i13 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor8.getClass();
                        Assert.isMainThread();
                        keyguardUpdateMonitor8.mLogger.m84d("handleTimeZoneUpdate");
                        while (i < keyguardUpdateMonitor8.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback6 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor8.mCallbacks.get(i)).get();
                            if (keyguardUpdateMonitorCallback6 != null) {
                                keyguardUpdateMonitorCallback6.onTimeZoneChanged(TimeZone.getTimeZone(str));
                                keyguardUpdateMonitorCallback6.onTimeChanged();
                            }
                            i++;
                        }
                        break;
                    case 340:
                        KeyguardUpdateMonitor keyguardUpdateMonitor9 = KeyguardUpdateMonitor.this;
                        int i14 = message.arg1;
                        int i15 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor9.getClass();
                        Assert.isMainThread();
                        keyguardUpdateMonitor9.mUserIsUnlocked.put(i14, keyguardUpdateMonitor9.mUserManager.isUserUnlocked(i14));
                        break;
                    case 341:
                        KeyguardUpdateMonitor.this.handleUserRemoved(message.arg1);
                        break;
                    case 342:
                        KeyguardUpdateMonitor keyguardUpdateMonitor10 = KeyguardUpdateMonitor.this;
                        boolean booleanValue = ((Boolean) message.obj).booleanValue();
                        int i16 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor10.getClass();
                        Assert.isMainThread();
                        keyguardUpdateMonitor10.setKeyguardGoingAway(booleanValue);
                        break;
                    case 344:
                        KeyguardUpdateMonitor keyguardUpdateMonitor11 = KeyguardUpdateMonitor.this;
                        String str2 = (String) message.obj;
                        int i17 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor11.getClass();
                        Assert.isMainThread();
                        keyguardUpdateMonitor11.mLogger.logTimeFormatChanged(str2);
                        while (i < keyguardUpdateMonitor11.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback7 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor11.mCallbacks.get(i)).get();
                            if (keyguardUpdateMonitorCallback7 != null) {
                                keyguardUpdateMonitorCallback7.onTimeFormatChanged(str2);
                            }
                            i++;
                        }
                        break;
                    case 345:
                        KeyguardUpdateMonitor keyguardUpdateMonitor12 = KeyguardUpdateMonitor.this;
                        int i18 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor12.getClass();
                        Assert.isMainThread();
                        while (i < keyguardUpdateMonitor12.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback8 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor12.mCallbacks.get(i)).get();
                            if (keyguardUpdateMonitorCallback8 != null) {
                                keyguardUpdateMonitorCallback8.onRequireUnlockForNfc();
                            }
                            i++;
                        }
                        break;
                    case 346:
                        KeyguardUpdateMonitor keyguardUpdateMonitor13 = KeyguardUpdateMonitor.this;
                        int i19 = KeyguardUpdateMonitor.BIOMETRIC_HELP_FINGERPRINT_NOT_RECOGNIZED;
                        keyguardUpdateMonitor13.getClass();
                        Assert.isMainThread();
                        while (i < keyguardUpdateMonitor13.mCallbacks.size()) {
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback9 = (KeyguardUpdateMonitorCallback) ((WeakReference) keyguardUpdateMonitor13.mCallbacks.get(i)).get();
                            if (keyguardUpdateMonitorCallback9 != null) {
                                keyguardUpdateMonitorCallback9.onKeyguardDismissAnimationFinished();
                            }
                            i++;
                        }
                        break;
                    case 347:
                        KeyguardUpdateMonitor keyguardUpdateMonitor14 = KeyguardUpdateMonitor.this;
                        Intent intent = (Intent) message.obj;
                        keyguardUpdateMonitor14.mLogger.logServiceProvidersUpdated(intent);
                        keyguardUpdateMonitor14.callbacksRefreshCarrierInfo(intent);
                        break;
                }
            }
        };
        this.mHandler = r8;
        if (!this.mDeviceProvisioned) {
            this.mDeviceProvisionedObserver = new ContentObserver(r8) { // from class: com.android.keyguard.KeyguardUpdateMonitor.21
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
            context.getContentResolver().registerContentObserver(Settings.Global.getUriFor("device_provisioned"), false, this.mDeviceProvisionedObserver);
            boolean isDeviceProvisionedInSettingsDb = isDeviceProvisionedInSettingsDb();
            if (isDeviceProvisionedInSettingsDb != this.mDeviceProvisioned) {
                this.mDeviceProvisioned = isDeviceProvisionedInSettingsDb;
                if (isDeviceProvisionedInSettingsDb) {
                    r8.sendEmptyMessage(308);
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
        if (!LsRune.SECURITY_DISABLE_EMERGENCY_CALL_WHEN_OFFLINE) {
            intentFilter.addAction("android.intent.action.SERVICE_STATE");
        }
        intentFilter.addAction("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED");
        intentFilter.addAction("android.intent.action.PHONE_STATE");
        intentFilter.addAction("android.telephony.action.SERVICE_PROVIDERS_UPDATED");
        intentFilter.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        intentFilter.addAction("android.hardware.usb.action.USB_PORT_COMPLIANCE_CHANGED");
        intentFilter.addAction("android.intent.action.LOCALE_CHANGED");
        broadcastDispatcher.registerReceiverWithHandler(broadcastReceiver, intentFilter, r8);
        executor.execute(new KeyguardUpdateMonitor$$ExternalSyntheticLambda3(this, 4));
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.app.action.NEXT_ALARM_CLOCK_CHANGED");
        intentFilter2.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
        intentFilter2.addAction("android.intent.action.USER_UNLOCKED");
        intentFilter2.addAction("android.intent.action.USER_STOPPED");
        intentFilter2.addAction("android.intent.action.USER_REMOVED");
        intentFilter2.addAction("android.nfc.action.REQUIRE_UNLOCK_FOR_NFC");
        broadcastDispatcher.registerReceiverWithHandler(broadcastReceiver2, intentFilter2, r8, UserHandle.ALL);
        subscriptionManager.addOnSubscriptionsChangedListener(r14);
        UserTrackerImpl userTrackerImpl = (UserTrackerImpl) userTracker;
        userTrackerImpl.addCallback(callback2, executor2);
        runSystemUserOnly(new KeyguardUpdateMonitor$$ExternalSyntheticLambda3(this, 5));
        if (fingerprintManager != 0) {
            fingerprintManager.addAuthenticatorsRegisteredCallback(new IFingerprintAuthenticatorsRegisteredCallback.Stub() { // from class: com.android.keyguard.KeyguardUpdateMonitor.16
                public final void onAllAuthenticatorsRegistered(List list) {
                    KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                    keyguardUpdateMonitor.mFingerprintSensorProperties = list;
                    keyguardUpdateMonitor.updateFingerprintListeningState(2);
                    KeyguardUpdateMonitor.this.mLogger.m84d("FingerprintManager onAllAuthenticatorsRegistered");
                }
            });
            fingerprintManager.addLockoutResetCallback(r142);
        }
        if (faceManager != null) {
            faceManager.addAuthenticatorsRegisteredCallback(new IFaceAuthenticatorsRegisteredCallback.Stub() { // from class: com.android.keyguard.KeyguardUpdateMonitor.17
                public final void onAllAuthenticatorsRegistered(List list) {
                    KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                    keyguardUpdateMonitor.mFaceSensorProperties = list;
                    keyguardUpdateMonitor.mLogger.m84d("FaceManager onAllAuthenticatorsRegistered");
                }
            });
            faceManager.addLockoutResetCallback((FaceManager.LockoutResetCallback) r143);
        }
        runSystemUserOnly(new KeyguardUpdateMonitor$$ExternalSyntheticLambda5(this, biometricManager, 1));
        authController.addCallback(new C080318(executor2));
        if (this.mConfigFaceAuthSupportedPosture != 0) {
            ((DevicePostureControllerImpl) devicePostureController).addCallback(callback);
        }
        TaskStackChangeListeners.INSTANCE.registerTaskStackListener(r144);
        this.mIsSystemUser = userManager.isSystemUser();
        int userId = userTrackerImpl.getUserId();
        sparseBooleanArray.put(userId, userManager.isUserUnlocked(userId));
        this.mLogoutEnabled = devicePolicyManager.isLogoutEnabled();
        updateSecondaryLockscreenRequirement(userId);
        for (UserInfo userInfo : userManager.getUsers()) {
            boolean isTrustUsuallyManaged = this.mTrustManager.isTrustUsuallyManaged(userInfo.id);
            KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger2 = this.mLogger;
            int i = userInfo.id;
            keyguardUpdateMonitorLogger2.logTrustUsuallyManagedUpdated(i, "init from constructor", this.mUserTrustIsUsuallyManaged.get(i), isTrustUsuallyManaged);
            this.mUserTrustIsUsuallyManaged.put(userInfo.id, isTrustUsuallyManaged);
        }
        if (WirelessUtils.isAirplaneModeOn(this.mContext) && !hasMessages(329)) {
            sendEmptyMessage(329);
        }
        runSystemUserOnly(new KeyguardUpdateMonitor$$ExternalSyntheticLambda3(this, 6), this.mBackgroundExecutor);
        ?? r1 = new ContentObserver(this.mHandler) { // from class: com.android.keyguard.KeyguardUpdateMonitor.19
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                HandlerC080015 handlerC080015 = keyguardUpdateMonitor.mHandler;
                handlerC080015.sendMessage(handlerC080015.obtainMessage(344, Settings.System.getString(keyguardUpdateMonitor.mContext.getContentResolver(), "time_12_24")));
            }
        };
        this.mTimeFormatChangeObserver = r1;
        this.mContext.getContentResolver().registerContentObserver(Settings.System.getUriFor("time_12_24"), false, r1, -1);
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(optional.orElse(null));
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

    public final void clearBiometricRecognized() {
        clearBiometricRecognized(-10000);
    }

    public void dispatchDreamingStarted() {
        HandlerC080015 handlerC080015 = this.mHandler;
        handlerC080015.sendMessage(handlerC080015.obtainMessage(333, 1, 0));
    }

    public void dispatchDreamingStopped() {
        HandlerC080015 handlerC080015 = this.mHandler;
        handlerC080015.sendMessage(handlerC080015.obtainMessage(333, 0, 0));
    }

    public void dispatchStartedWakingUp(int i) {
        synchronized (this) {
            this.mDeviceInteractive = true;
        }
        HandlerC080015 handlerC080015 = this.mHandler;
        handlerC080015.sendMessage(handlerC080015.obtainMessage(319, i, 0));
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        StringBuilder m75m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "KeyguardUpdateMonitor state:", "  getUserHasTrust()=");
        m75m.append(getUserHasTrust(getCurrentUser()));
        printWriter.println(m75m.toString());
        printWriter.println("  getUserUnlockedWithBiometric()=" + getUserUnlockedWithBiometric(getCurrentUser()));
        printWriter.println("  isFaceAuthInteractorEnabled: false");
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
        Iterator it2 = this.mServiceStates.keySet().iterator();
        while (it2.hasNext()) {
            int intValue = ((Integer) it2.next()).intValue();
            StringBuilder m1m = AbstractC0000x2c234b15.m1m("    ", intValue, "=");
            m1m.append(this.mServiceStates.get(Integer.valueOf(intValue)));
            printWriter.println(m1m.toString());
        }
        dumpAllUsers(printWriter);
    }

    public final boolean getCachedIsUnlockWithFingerprintPossible(int i) {
        return ((Boolean) this.mIsUnlockWithFingerprintPossible.getOrDefault(Integer.valueOf(i), Boolean.FALSE)).booleanValue();
    }

    public final List getFilteredSubscriptionInfo() {
        List subscriptionInfo = getSubscriptionInfo(false);
        ArrayList arrayList = (ArrayList) subscriptionInfo;
        if (arrayList.size() == 2) {
            SubscriptionInfo subscriptionInfo2 = (SubscriptionInfo) arrayList.get(0);
            SubscriptionInfo subscriptionInfo3 = (SubscriptionInfo) arrayList.get(1);
            if (subscriptionInfo2.getGroupUuid() != null && subscriptionInfo2.getGroupUuid().equals(subscriptionInfo3.getGroupUuid())) {
                if (!subscriptionInfo2.isOpportunistic() && !subscriptionInfo3.isOpportunistic()) {
                    return subscriptionInfo;
                }
                if (this.mCarrierConfigManager.getConfigForSubId(subscriptionInfo2.isOpportunistic() ? subscriptionInfo2.getSubscriptionId() : subscriptionInfo3.getSubscriptionId()).getBoolean("always_show_primary_signal_bar_in_opportunistic_network_boolean")) {
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
        BiometricAuthenticated biometricAuthenticated = this.mUserFaceAuthenticated.get(getCurrentUser());
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
                    this.mLogger.m85v("getNextSubIdForState() PIN_REQUIRED happen on isSimPinPassed slot");
                } else if (LsRune.SECURITY_ESIM && isESimRemoveButtonClicked()) {
                    this.mLogger.m85v("getNextSubIdForState() " + i + " happen on isESimRemoveButtonClicked slotId = " + slotId);
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
        List<SubscriptionInfo> list = this.mSubscriptionInfo;
        if (list == null || z) {
            list = this.mSubscriptionManager.getCompleteActiveSubscriptionInfoList();
        }
        if (list == null) {
            this.mSubscriptionInfo = new ArrayList();
        } else {
            this.mSubscriptionInfo = list;
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
        return getUserHasTrust(i) || getUserUnlockedWithBiometric(i);
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
        BiometricAuthenticated biometricAuthenticated2 = this.mUserFaceAuthenticated.get(i);
        boolean z = biometricAuthenticated != null && biometricAuthenticated.mAuthenticated && isUnlockingWithBiometricAllowed(biometricAuthenticated.mIsStrongBiometric);
        boolean z2 = biometricAuthenticated2 != null && biometricAuthenticated2.mAuthenticated && isUnlockingWithBiometricAllowed(biometricAuthenticated2.mIsStrongBiometric);
        if (z) {
            return true;
        }
        return z2 && this.mKeyguardBypassController.canBypass();
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
                    keyguardUpdateMonitorCallback.onRefreshBatteryInfo();
                }
            }
        }
    }

    public final void handleBiometricDetected(int i, BiometricSourceType biometricSourceType, boolean z) {
        Trace.beginSection("KeyGuardUpdateMonitor#handlerBiometricDetected");
        Assert.isMainThread();
        Trace.beginSection("KeyGuardUpdateMonitor#onBiometricDetected");
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricDetected();
            }
        }
        Trace.endSection();
        if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
            this.mLogger.logFingerprintDetected(i, z);
        } else if (biometricSourceType == BiometricSourceType.FACE) {
            this.mLogger.logFaceDetected(i, z);
            setFaceRunningState(0);
        }
        Trace.endSection();
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
        this.mLogger.m84d("onFaceAuthFailed");
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
                this.mLogger.m84d("Aborted successful auth because device is going to sleep.");
                return;
            }
            int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
            if (userId != i) {
                this.mLogger.logFaceAuthForWrongUser(i);
                return;
            }
            if (isFaceDisabled(userId)) {
                this.mLogger.logFaceAuthDisabledForUser(userId);
                return;
            }
            this.mLogger.logFaceAuthSuccess(userId);
            onFaceAuthenticated(userId, z);
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
        int i2 = 0;
        boolean z2 = i == 2;
        this.mFaceLockedOutPermanent = z2;
        boolean z3 = z2 != z;
        postDelayed(new KeyguardUpdateMonitor$$ExternalSyntheticLambda3(this, i2), VolteConstants.ErrorCode.BUSY_EVERYWHERE);
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
            this.mLogger.m84d("handleFingerprintAuthFailed() triggered while waiting for cancellation, removing watchdog");
            removeCallbacks(this.mFpCancelNotReceived);
        }
        this.mLogger.m84d("handleFingerprintAuthFailed");
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricAuthFailed(BiometricSourceType.FINGERPRINT);
            }
        }
        if (isUdfpsSupported()) {
            handleFingerprintHelp(-1, this.mContext.getString(android.R.string.lockscreen_forgot_pattern_button_text));
        } else {
            handleFingerprintHelp(-1, this.mContext.getString(android.R.string.lock_pattern_view_aspect));
        }
    }

    public void handleFingerprintAuthenticated(int i, boolean z) {
        Trace.beginSection("KeyGuardUpdateMonitor#handlerFingerPrintAuthenticated");
        if (hasCallbacks(this.mFpCancelNotReceived)) {
            this.mLogger.m84d("handleFingerprintAuthenticated() triggered while waiting for cancellation, removing watchdog");
            removeCallbacks(this.mFpCancelNotReceived);
        }
        try {
            int i2 = sCurrentUser;
            if (i2 != i) {
                this.mLogger.logFingerprintAuthForWrongUser(i);
            } else {
                if (isFingerprintDisabled(i2)) {
                    this.mLogger.logFingerprintDisabledForUser(i2);
                    return;
                }
                onFingerprintAuthenticated(i2, z);
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
        int i2 = 8;
        if (i == 19) {
            this.mLogger.logRetryAfterFpErrorWithDelay(i, 50, str);
            postDelayed(new KeyguardUpdateMonitor$$ExternalSyntheticLambda3(this, i2), 50L);
        }
        if (i == 9) {
            z = !this.mFingerprintLockedOutPermanent;
            this.mFingerprintLockedOutPermanent = true;
            this.mLogger.m84d("Fingerprint permanently locked out - requiring stronger auth");
            this.mLockPatternUtils.requireStrongAuth(8, getCurrentUser());
        } else {
            z = false;
        }
        if (i == 7 || i == 9) {
            z |= !this.mFingerprintLockedOut;
            this.mFingerprintLockedOut = true;
            this.mLogger.m84d("Fingerprint temporarily locked out - requiring stronger auth");
            if (isUdfpsEnrolled()) {
                updateFingerprintListeningState(1);
            }
        }
        this.mLogger.logFingerprintError(i, str);
        for (int i3 = 0; i3 < this.mCallbacks.size(); i3++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i3)).get();
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
        boolean z3 = false;
        int i2 = 1;
        boolean z4 = i == 1 || i == 2;
        this.mFingerprintLockedOut = z4;
        boolean z5 = i == 2;
        this.mFingerprintLockedOutPermanent = z5;
        boolean z6 = (z4 == z && z5 == z2) ? false : true;
        if (isUdfpsEnrolled()) {
            postDelayed(new KeyguardUpdateMonitor$$ExternalSyntheticLambda3(this, i2), VolteConstants.ErrorCode.BUSY_EVERYWHERE);
        } else {
            if (z && !this.mFingerprintLockedOut) {
                z3 = true;
            }
            if (z3) {
                this.mLogger.m84d("temporaryLockoutReset - stopListeningForFingerprint() to stop detectFingerprint");
                stopListeningForFingerprint();
            }
            updateFingerprintListeningState(2);
        }
        if (z6) {
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
        this.mLogger.m84d("handleKeyguardReset");
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
            keyguardUpdateMonitorLogger.log("invalid subId in handleServiceStateChange()", LogLevel.WARNING);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0097  */
    /* JADX WARN: Type inference failed for: r0v17, types: [com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda8] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void handleSimStateChange(final int i, final int i2, final int i3) {
        boolean z;
        SimData simData;
        Assert.isMainThread();
        this.mLogger.logSimState(i, i2, i3);
        if (isSimPinPassed(i2, i3)) {
            this.mLogger.m84d("handleSimStateChange isSimPinPassed");
            return;
        }
        boolean z2 = LsRune.SECURITY_ESIM;
        if (z2 && isESimRemoveButtonClicked()) {
            dispatchCallback(new Consumer() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda8
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ((KeyguardUpdateMonitorCallback) obj).onSimStateChanged(i, i2, i3);
                }
            });
            return;
        }
        boolean z3 = true;
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
            keyguardUpdateMonitorLogger.getClass();
            keyguardUpdateMonitorLogger.log("invalid subId in handleSimStateChange()", LogLevel.WARNING);
            if (i3 == 1) {
                updateTelephonyCapable(true);
                for (SimData simData2 : this.mSimDatas.values()) {
                    if (simData2.slotId == i2) {
                        simData2.simState = 1;
                    }
                }
                z = true;
                boolean contains = z | ABSENT_SIM_STATE_LIST.contains(Integer.valueOf(i3));
                simData = (SimData) this.mSimDatas.get(Integer.valueOf(i));
                if (simData != null) {
                    this.mSimDatas.put(Integer.valueOf(i), new SimData(i3, i2, i));
                } else {
                    if (simData.simState == i3 && simData.subId == i && simData.slotId == i2) {
                        z3 = false;
                    }
                    simData.simState = i3;
                    simData.subId = i;
                    simData.slotId = i2;
                }
                if (!z3 || contains) {
                    for (int i4 = 0; i4 < this.mCallbacks.size(); i4++) {
                        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i4)).get();
                        if (keyguardUpdateMonitorCallback != null) {
                            keyguardUpdateMonitorCallback.onSimStateChanged(i, i2, i3);
                        }
                    }
                }
                return;
            }
            if (i3 == 8) {
                updateTelephonyCapable(true);
            } else if (z2) {
                updateEsimState(i2, i3);
            }
        }
        z = false;
        boolean contains2 = z | ABSENT_SIM_STATE_LIST.contains(Integer.valueOf(i3));
        simData = (SimData) this.mSimDatas.get(Integer.valueOf(i));
        if (simData != null) {
        }
        if (z3) {
        }
        while (i4 < this.mCallbacks.size()) {
        }
    }

    public void handleStartedGoingToSleep(int i) {
        Assert.isMainThread();
        clearBiometricRecognized();
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onStartedGoingToSleep(i);
            }
        }
        this.mGoingToSleep = true;
        this.mAssistantVisible = false;
        this.mLogger.m84d("Started going to sleep, mGoingToSleep=true, mAssistantVisible=false");
        updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_GOING_TO_SLEEP);
    }

    public void handleStartedWakingUp(int i) {
        Trace.beginSection("KeyguardUpdateMonitor#handleStartedWakingUp");
        Assert.isMainThread();
        updateFingerprintListeningState(2);
        if (this.mFaceWakeUpTriggersConfig.triggerFaceAuthOnWakeUpFrom.contains(Integer.valueOf(i))) {
            FaceAuthUiEvent faceAuthUiEvent = FaceAuthUiEvent.FACE_AUTH_UPDATED_STARTED_WAKING_UP;
            faceAuthUiEvent.setExtraInfo(i);
            updateFaceListeningState(2, faceAuthUiEvent);
        } else {
            this.mLogger.logSkipUpdateFaceListeningOnWakeup(i);
        }
        requestActiveUnlockFromWakeReason(i, true);
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

    public void handleUserSwitching(int i, CountDownLatch countDownLatch) {
        Assert.isMainThread();
        clearBiometricRecognized();
        boolean isTrustUsuallyManaged = this.mTrustManager.isTrustUsuallyManaged(i);
        this.mLogger.logTrustUsuallyManagedUpdated(i, "userSwitching", this.mUserTrustIsUsuallyManaged.get(i), isTrustUsuallyManaged);
        this.mUserTrustIsUsuallyManaged.put(i, isTrustUsuallyManaged);
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onUserSwitching(i);
            }
        }
        countDownLatch.countDown();
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

    /* JADX WARN: Removed duplicated region for block: B:12:0x0021  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isEncryptedOrLockdown(int i) {
        boolean z;
        int strongAuthForUser = this.mStrongAuthTracker.getStrongAuthForUser(i);
        if (!((strongAuthForUser & 2) != 0)) {
            if (!((strongAuthForUser & 32) != 0)) {
                z = false;
                return ((strongAuthForUser & 1) == 0) || z;
            }
        }
        z = true;
        if ((strongAuthForUser & 1) == 0) {
            return true;
        }
    }

    public boolean isFaceAuthEnabledForUser(int i) {
        boolean z = false;
        if (isFaceSupported() && this.mBiometricEnabledForUser.get(i)) {
            AuthController authController = this.mAuthController;
            if (authController.mFaceProps == null ? false : authController.mFaceEnrolledForUser.get(i)) {
                z = true;
            }
        }
        Boolean valueOf = Boolean.valueOf(z);
        if (this.mIsFaceEnrolled != valueOf.booleanValue()) {
            this.mLogger.logFaceEnrolledUpdated(this.mIsFaceEnrolled, valueOf.booleanValue());
        }
        boolean booleanValue = valueOf.booleanValue();
        this.mIsFaceEnrolled = booleanValue;
        return booleanValue;
    }

    public boolean isFaceClass3() {
        if (isFaceSupported()) {
            return ((SensorPropertiesInternal) this.mFaceSensorProperties.get(0)).sensorStrength == 2;
        }
        return false;
    }

    public final boolean isFaceDetectionRunning() {
        return this.mFaceRunningState == 1;
    }

    public final boolean isFaceDisabled(final int i) {
        return ((Boolean) DejankUtils.whitelistIpcs(new Supplier() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda4
            @Override // java.util.function.Supplier
            public final Object get() {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                return Boolean.valueOf((keyguardUpdateMonitor.mDevicePolicyManager.getKeyguardDisabledFeatures(null, i) & 128) != 0 || keyguardUpdateMonitor.isSimPinSecure());
            }
        })).booleanValue();
    }

    public final boolean isFaceSupported() {
        return (this.mFaceManager == null || this.mFaceSensorProperties.isEmpty()) ? false : true;
    }

    public boolean isFingerprintClass3() {
        if (isFingerprintSupported()) {
            return ((SensorPropertiesInternal) this.mFingerprintSensorProperties.get(0)).sensorStrength == 2;
        }
        return false;
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

    public final boolean isSfpsEnrolled() {
        AuthController authController = this.mAuthController;
        int currentUser = getCurrentUser();
        if (authController.mSideFpsController == null) {
            return false;
        }
        return authController.mSfpsEnrolledForUser.get(currentUser);
    }

    public final boolean isSimPinSecure() {
        Iterator it = ((ArrayList) getSubscriptionInfo(false)).iterator();
        while (it.hasNext()) {
            SubscriptionInfo subscriptionInfo = (SubscriptionInfo) it.next();
            if (LsRune.SECURITY_SIM_PERM_DISABLED) {
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
        return this.mAuthController.isUdfpsEnrolled(getCurrentUser());
    }

    public boolean isUdfpsSupported() {
        List list = this.mAuthController.mUdfpsProps;
        return (list == null || list.isEmpty()) ? false : true;
    }

    public final boolean isUnlockWithFacePossible(int i) {
        return isFaceAuthEnabledForUser(i) && !isFaceDisabled(i);
    }

    public boolean isUnlockWithFingerprintPossible(int i) {
        boolean z = isFingerprintSupported() && !isFingerprintDisabled(i) && this.mFpm.hasEnrolledTemplates(i);
        Boolean bool = (Boolean) this.mIsUnlockWithFingerprintPossible.getOrDefault(Integer.valueOf(i), Boolean.FALSE);
        if (bool.booleanValue() != z) {
            this.mLogger.logFpEnrolledUpdated(i, bool.booleanValue(), z);
        }
        this.mIsUnlockWithFingerprintPossible.put(Integer.valueOf(i), Boolean.valueOf(z));
        return ((Boolean) this.mIsUnlockWithFingerprintPossible.get(Integer.valueOf(i))).booleanValue();
    }

    public boolean isUnlockingWithBiometricAllowed(boolean z) {
        StrongAuthTracker strongAuthTracker = this.mStrongAuthTracker;
        strongAuthTracker.getClass();
        return strongAuthTracker.isBiometricAllowedForUser(z, getCurrentUser()) && !((isFingerprintClass3() && isFingerprintLockedOut()) || (isFaceClass3() && this.mFaceLockedOutPermanent));
    }

    public final boolean isUnlockingWithBiometricsPossible(int i) {
        return isUnlockWithFacePossible(i) || isUnlockWithFingerprintPossible(i);
    }

    public final boolean isUnlockingWithFingerprintAllowed() {
        return isUnlockingWithBiometricAllowed(BiometricSourceType.FINGERPRINT);
    }

    public final boolean isUserInLockdown(int i) {
        return (this.mStrongAuthTracker.getStrongAuthForUser(i) & 32) != 0;
    }

    public final boolean isUserUnlocked(int i) {
        return this.mUserIsUnlocked.get(i);
    }

    public final void logListenerModelData(KeyguardListenModel keyguardListenModel) {
        this.mLogger.logKeyguardListenerModel(keyguardListenModel);
        if (keyguardListenModel instanceof KeyguardFingerprintListenModel) {
            KeyguardFingerprintListenModel keyguardFingerprintListenModel = (KeyguardFingerprintListenModel) keyguardListenModel;
            KeyguardFingerprintListenModel keyguardFingerprintListenModel2 = (KeyguardFingerprintListenModel) this.mFingerprintListenBuffer.buffer.advance();
            keyguardFingerprintListenModel2.timeMillis = keyguardFingerprintListenModel.timeMillis;
            keyguardFingerprintListenModel2.userId = keyguardFingerprintListenModel.userId;
            keyguardFingerprintListenModel2.listening = keyguardFingerprintListenModel.listening;
            keyguardFingerprintListenModel2.alternateBouncerShowing = keyguardFingerprintListenModel.alternateBouncerShowing;
            keyguardFingerprintListenModel2.biometricEnabledForUser = keyguardFingerprintListenModel.biometricEnabledForUser;
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
            keyguardFingerprintListenModel2.shouldListenSfpsState = keyguardFingerprintListenModel.shouldListenSfpsState;
            keyguardFingerprintListenModel2.shouldListenForFingerprintAssistant = keyguardFingerprintListenModel.shouldListenForFingerprintAssistant;
            keyguardFingerprintListenModel2.strongerAuthRequired = keyguardFingerprintListenModel.strongerAuthRequired;
            keyguardFingerprintListenModel2.switchingUser = keyguardFingerprintListenModel.switchingUser;
            keyguardFingerprintListenModel2.systemUser = keyguardFingerprintListenModel.systemUser;
            keyguardFingerprintListenModel2.udfps = keyguardFingerprintListenModel.udfps;
            keyguardFingerprintListenModel2.userDoesNotHaveTrust = keyguardFingerprintListenModel.userDoesNotHaveTrust;
            return;
        }
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
        if (keyguardListenModel instanceof KeyguardFaceListenModel) {
            KeyguardFaceListenModel keyguardFaceListenModel = (KeyguardFaceListenModel) keyguardListenModel;
            KeyguardFaceListenModel keyguardFaceListenModel2 = (KeyguardFaceListenModel) this.mFaceListenBuffer.buffer.advance();
            keyguardFaceListenModel2.timeMillis = keyguardFaceListenModel.timeMillis;
            keyguardFaceListenModel2.userId = keyguardFaceListenModel.userId;
            keyguardFaceListenModel2.listening = keyguardFaceListenModel.listening;
            keyguardFaceListenModel2.alternateBouncerShowing = keyguardFaceListenModel.alternateBouncerShowing;
            keyguardFaceListenModel2.biometricSettingEnabledForUser = keyguardFaceListenModel.biometricSettingEnabledForUser;
            keyguardFaceListenModel2.bouncerFullyShown = keyguardFaceListenModel.bouncerFullyShown;
            keyguardFaceListenModel2.faceAndFpNotAuthenticated = keyguardFaceListenModel.faceAndFpNotAuthenticated;
            keyguardFaceListenModel2.faceAuthAllowed = keyguardFaceListenModel.faceAuthAllowed;
            keyguardFaceListenModel2.faceDisabled = keyguardFaceListenModel.faceDisabled;
            keyguardFaceListenModel2.faceLockedOut = keyguardFaceListenModel.faceLockedOut;
            keyguardFaceListenModel2.goingToSleep = keyguardFaceListenModel.goingToSleep;
            keyguardFaceListenModel2.keyguardAwake = keyguardFaceListenModel.keyguardAwake;
            keyguardFaceListenModel2.goingToSleep = keyguardFaceListenModel.goingToSleep;
            keyguardFaceListenModel2.keyguardGoingAway = keyguardFaceListenModel.keyguardGoingAway;
            keyguardFaceListenModel2.listeningForFaceAssistant = keyguardFaceListenModel.listeningForFaceAssistant;
            keyguardFaceListenModel2.occludingAppRequestingFaceAuth = keyguardFaceListenModel.occludingAppRequestingFaceAuth;
            keyguardFaceListenModel2.postureAllowsListening = keyguardFaceListenModel.postureAllowsListening;
            keyguardFaceListenModel2.secureCameraLaunched = keyguardFaceListenModel.secureCameraLaunched;
            keyguardFaceListenModel2.supportsDetect = keyguardFaceListenModel.supportsDetect;
            keyguardFaceListenModel2.switchingUser = keyguardFaceListenModel.switchingUser;
            keyguardFaceListenModel2.systemUser = keyguardFaceListenModel.systemUser;
            keyguardFaceListenModel2.udfpsFingerDown = keyguardFaceListenModel.udfpsFingerDown;
            keyguardFaceListenModel2.userNotTrustedOrDetectionIsNeeded = keyguardFaceListenModel.userNotTrustedOrDetectionIsNeeded;
        }
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
        }
        if (i == getCurrentUser()) {
            FaceAuthUiEvent faceAuthUiEvent = FaceAuthUiEvent.FACE_AUTH_NON_STRONG_BIOMETRIC_ALLOWED_CHANGED;
            faceAuthUiEvent.setExtraInfo(this.mStrongAuthTracker.isNonStrongBiometricAllowedAfterIdleTimeout(getCurrentUser()) ? -1 : 1);
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
        if (i == getCurrentUser()) {
            FaceAuthUiEvent faceAuthUiEvent = FaceAuthUiEvent.FACE_AUTH_UPDATED_STRONG_AUTH_CHANGED;
            faceAuthUiEvent.setExtraInfo(this.mStrongAuthTracker.getStrongAuthForUser(getCurrentUser()));
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
        this.mLogger.m84d("onFaceAuthenticated");
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricAuthenticated(i, BiometricSourceType.FACE, z);
            }
        }
        this.mAssistantVisible = false;
        this.mBackgroundExecutor.execute(new KeyguardUpdateMonitor$$ExternalSyntheticLambda2(i, this, z));
        Trace.endSection();
    }

    public void onFingerprintAuthenticated(final int i, boolean z) {
        Assert.isMainThread();
        Trace.beginSection("KeyGuardUpdateMonitor#onFingerPrintAuthenticated");
        this.mUserFingerprintAuthenticated.put(i, new BiometricAuthenticated(true, z));
        if (getUserCanSkipBouncer(i)) {
            getFastBioUnlockController().executor.submit(new KeyguardFastBioUnlockController.Task(new Runnable() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                    keyguardUpdateMonitor.mTrustManager.unlockedByBiometricForUser(i, BiometricSourceType.FINGERPRINT);
                }
            }, "TrustManager#unlockedByBiometricForUser"));
        }
        this.mFingerprintCancelSignal = null;
        this.mLogger.logFingerprintSuccess(i, z);
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricAuthenticated(i, BiometricSourceType.FINGERPRINT, z);
            }
        }
        HandlerC080015 handlerC080015 = this.mHandler;
        handlerC080015.sendMessageDelayed(handlerC080015.obtainMessage(336), 500L);
        this.mAssistantVisible = false;
        this.mBackgroundExecutor.execute(new KeyguardUpdateMonitor$$ExternalSyntheticLambda2(i, this, z));
        if (is2StepVerification()) {
            this.mLogger.m84d("onFingerprintAuthenticated ( stop fingerprint )");
            updateFingerprintListeningState(1);
        }
        Trace.endSection();
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x00c2, code lost:
    
        if (((r9 & 2) != 0) != false) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00dd, code lost:
    
        r8 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00cf, code lost:
    
        if (((r9 & 4) != 0) != false) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00da, code lost:
    
        if (((r9 & 2) != 0) != false) goto L65;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
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
            if (getCurrentUser() == i && list != null) {
                Iterator it = list.iterator();
                while (it.hasNext()) {
                    str = (String) it.next();
                    if (!TextUtils.isEmpty(str)) {
                        break;
                    }
                }
            }
            this.mLogger.logTrustGrantedWithFlags(z2, str, i2, i);
            if (i == getCurrentUser()) {
                if (z2) {
                    this.mUiEventLogger.log(TrustAgentUiEvent.TRUST_AGENT_NEWLY_UNLOCKED, ((SessionTracker) this.mSessionTrackerProvider.get()).getSessionId(1));
                }
                TrustGrantFlags trustGrantFlags = new TrustGrantFlags(i2);
                for (int i4 = 0; i4 < this.mCallbacks.size(); i4++) {
                    KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback2 = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i4)).get();
                    if (keyguardUpdateMonitorCallback2 != null) {
                        boolean z4 = this.mPrimaryBouncerIsOrWillBeShowing || this.mAlternateBouncerShowing;
                        int i5 = trustGrantFlags.mFlags;
                        if (!((i5 & 1) != 0)) {
                        }
                        if (!this.mDeviceInteractive) {
                        }
                        if (!z4) {
                        }
                        boolean z5 = true;
                        keyguardUpdateMonitorCallback2.onTrustGrantedForCurrentUser(z5, trustGrantFlags, str);
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
        this.mLogger.logTrustUsuallyManagedUpdated(i, "onTrustManagedChanged", this.mUserTrustIsUsuallyManaged.get(i), isTrustUsuallyManaged);
        this.mUserTrustIsUsuallyManaged.put(i, isTrustUsuallyManaged);
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onTrustManagedChanged(i);
            }
        }
    }

    public final boolean refreshSimState(int i, int i2) {
        int simState;
        boolean z = LsRune.SECURITY_SIM_PERSO_LOCK;
        if (z) {
            Context context = this.mContext;
            Point point = DeviceState.sDisplaySize;
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager != null) {
                simState = telephonyManager.getSimState(i2);
                if (z && simState == 4) {
                    if ("PERSO_LOCKED".equals(DeviceState.getMSimSystemProperty(i2, "gsm.sim.state", "NOT_READY"))) {
                        simState = 12;
                    }
                }
            }
            simState = 0;
        } else {
            simState = this.mTelephonyManager.getSimState(i2);
        }
        if (isSimPinPassed(i2, simState)) {
            this.mLogger.m84d("refreshSimState isSimPinPassed slotId" + i2);
            return false;
        }
        SimData simData = (SimData) this.mSimDatas.get(Integer.valueOf(i));
        if (simData == null) {
            if (!SubscriptionManager.isValidSubscriptionId(i)) {
                return false;
            }
            this.mSimDatas.put(Integer.valueOf(i), new SimData(simState, i2, i));
            return true;
        }
        boolean z2 = simData.simState != simState;
        simData.simState = simState;
        simData.subId = i;
        simData.slotId = i2;
        return z2;
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
        this.mCallbacks.removeIf(new Predicate() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda0
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

    public final void requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin activeUnlockRequestOrigin, String str) {
        KeyguardBypassController keyguardBypassController;
        boolean z = true;
        if (!(this.mIsFaceEnrolled && (keyguardBypassController = this.mKeyguardBypassController) != null && keyguardBypassController.canBypass()) && !this.mAlternateBouncerShowing && !this.mPrimaryBouncerFullyShown && !isUdfpsFingerDown()) {
            z = false;
        }
        requestActiveUnlock(activeUnlockRequestOrigin, str, z);
    }

    public final void requestActiveUnlockFromWakeReason(int i, boolean z) {
        if (!this.mFaceWakeUpTriggersConfig.triggerFaceAuthOnWakeUpFrom.contains(Integer.valueOf(i))) {
            this.mLogger.logActiveUnlockRequestSkippedForWakeReasonDueToFaceConfig(i);
            return;
        }
        ActiveUnlockConfig.ActiveUnlockRequestOrigin activeUnlockRequestOrigin = this.mActiveUnlockConfig.wakeupsConsideredUnlockIntents.contains(Integer.valueOf(i)) ? ActiveUnlockConfig.ActiveUnlockRequestOrigin.UNLOCK_INTENT : ActiveUnlockConfig.ActiveUnlockRequestOrigin.WAKE;
        String str = "wakingUp - " + PowerManager.wakeReasonToString(i) + " powerManagerWakeup=" + z;
        if (!this.mActiveUnlockConfig.wakeupsToForceDismissKeyguard.contains(Integer.valueOf(i))) {
            requestActiveUnlock(activeUnlockRequestOrigin, str);
            return;
        }
        requestActiveUnlock(activeUnlockRequestOrigin, str + "-dismissKeyguard", true);
    }

    public final boolean requestFaceAuth(String str) {
        this.mLogger.logFaceAuthRequested(str);
        Object obj = FaceAuthReasonKt.apiRequestReasonToUiEvent.get(str);
        Intrinsics.checkNotNull(obj);
        updateFaceListeningState(0, (FaceAuthUiEvent) obj);
        return isFaceDetectionRunning();
    }

    public final void requestFaceAuthOnOccludingApp(boolean z) {
        this.mOccludingAppRequestingFace = z;
        updateFaceListeningState(z ? 2 : 1, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_OCCLUDING_APP_REQUESTED);
    }

    public void resetBiometricListeningState() {
        this.mFingerprintRunningState = 0;
        this.mFaceRunningState = 0;
    }

    public final boolean resolveNeedsSlowUnlockTransition() {
        if (isUserUnlocked(getCurrentUser())) {
            return false;
        }
        ResolveInfo resolveActivityAsUser = this.mPackageManager.resolveActivityAsUser(new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME"), 0, getCurrentUser());
        if (resolveActivityAsUser != null) {
            return FALLBACK_HOME_COMPONENT.equals(resolveActivityAsUser.getComponentInfo().getComponentName());
        }
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        keyguardUpdateMonitorLogger.getClass();
        keyguardUpdateMonitorLogger.log("resolveNeedsSlowUnlockTransition: returning false since activity could not be resolved.", LogLevel.WARNING);
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
                this.mLogger.m84d("sendUpdates isSimPinPassed state.slotId = " + simData.slotId);
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
            requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin.ASSISTANT, "assistant", true);
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
                    keyguardUpdateMonitorCallback.onBiometricRunningStateChanged(BiometricSourceType.FACE, isFaceDetectionRunning());
                }
            }
        }
    }

    public final void setFingerprintRunningState(int i) {
        boolean z = this.mFingerprintRunningState == 1;
        boolean z2 = i == 1;
        this.mFingerprintRunningState = i;
        this.mLogger.logFingerprintRunningState(i);
        if (z != z2) {
            Assert.isMainThread();
            for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
                if (keyguardUpdateMonitorCallback != null) {
                    keyguardUpdateMonitorCallback.onBiometricRunningStateChanged(BiometricSourceType.FINGERPRINT, isFingerprintDetectionRunning());
                }
            }
        }
    }

    public void setKeyguardGoingAway(boolean z) {
        this.mKeyguardGoingAway = z;
        if (z) {
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

    public boolean shouldListenForFace() {
        boolean z = false;
        if (this.mFaceManager == null) {
            return false;
        }
        boolean z2 = isKeyguardVisible() && this.mDeviceInteractive && !(this.mStatusBarState == 2);
        int currentUser = getCurrentUser();
        boolean isUnlockingWithBiometricAllowed = isUnlockingWithBiometricAllowed(BiometricSourceType.FACE);
        KeyguardBypassController keyguardBypassController = this.mKeyguardBypassController;
        boolean z3 = keyguardBypassController != null && keyguardBypassController.canBypass();
        boolean z4 = !getUserHasTrust(currentUser) || z3;
        boolean z5 = isFaceSupported() && ((FaceSensorPropertiesInternal) this.mFaceSensorProperties.get(0)).supportsFaceDetection && z3 && !this.mPrimaryBouncerIsOrWillBeShowing && !isUserInLockdown(currentUser);
        boolean z6 = isUnlockingWithBiometricAllowed || z5;
        boolean z7 = !getUserUnlockedWithBiometric(currentUser);
        boolean isFaceDisabled = isFaceDisabled(currentUser);
        boolean z8 = this.mBiometricEnabledForUser.get(currentUser);
        BiometricAuthenticated biometricAuthenticated = this.mUserFaceAuthenticated.get(getCurrentUser());
        boolean z9 = this.mAssistantVisible && this.mKeyguardShowing && this.mKeyguardOccluded && (biometricAuthenticated == null || !biometricAuthenticated.mAuthenticated) && !this.mUserHasTrust.get(getCurrentUser(), false);
        UdfpsController udfpsController = this.mAuthController.mUdfpsController;
        boolean z10 = udfpsController == null ? false : udfpsController.mOnFingerDown;
        int i = this.mPostureState;
        int i2 = this.mConfigFaceAuthSupportedPosture;
        boolean z11 = i2 == 0 || i == i2;
        if ((this.mPrimaryBouncerFullyShown || this.mAuthInterruptActive || this.mOccludingAppRequestingFace || z2 || z9 || z10 || this.mAlternateBouncerShowing) && !this.mSwitchingUser && !isFaceDisabled && z4 && !this.mKeyguardGoingAway && z8 && z6 && this.mIsSystemUser && ((!this.mSecureCameraLaunched || this.mAlternateBouncerShowing) && z7 && !this.mGoingToSleep && z11)) {
            z = true;
        }
        logListenerModelData(new KeyguardFaceListenModel(System.currentTimeMillis(), currentUser, z, this.mAlternateBouncerShowing, this.mAuthInterruptActive, z8, this.mPrimaryBouncerFullyShown, z7, isUnlockingWithBiometricAllowed, isFaceDisabled, this.mFaceLockedOutPermanent, this.mGoingToSleep, z2, this.mKeyguardGoingAway, z9, this.mOccludingAppRequestingFace, z11, this.mSecureCameraLaunched, z5, this.mSwitchingUser, this.mIsSystemUser, z10, z4));
        return z;
    }

    public boolean shouldListenForFingerprint(boolean z) {
        boolean z2;
        int currentUser = getCurrentUser();
        boolean z3 = !getUserHasTrust(currentUser);
        BiometricAuthenticated biometricAuthenticated = this.mUserFingerprintAuthenticated.get(getCurrentUser());
        boolean z4 = this.mAssistantVisible && this.mKeyguardOccluded && (biometricAuthenticated == null || !biometricAuthenticated.mAuthenticated) && !this.mUserHasTrust.get(getCurrentUser(), false);
        boolean z5 = isKeyguardVisible() || !this.mDeviceInteractive || (this.mPrimaryBouncerIsOrWillBeShowing && !this.mKeyguardGoingAway) || this.mGoingToSleep || z4 || (((z2 = this.mKeyguardOccluded) && this.mIsDreaming) || (z2 && z3 && this.mKeyguardShowing && (this.mOccludingAppRequestingFp || z || this.mAlternateBouncerShowing)));
        boolean z6 = this.mBiometricEnabledForUser.get(currentUser);
        boolean userCanSkipBouncer = getUserCanSkipBouncer(currentUser);
        boolean isFingerprintDisabled = isFingerprintDisabled(currentUser);
        boolean z7 = (this.mSwitchingUser || isFingerprintDisabled || (this.mKeyguardGoingAway && this.mDeviceInteractive) || !this.mIsSystemUser || !z6 || isUserInLockdown(currentUser)) ? false : true;
        boolean z8 = !isUnlockingWithFingerprintAllowed();
        List list = this.mAuthController.mSidefpsProps;
        if ((list == null || list.isEmpty()) ? false : true) {
            isSfpsEnrolled();
        }
        boolean z9 = z5 && z7 && (!z8 || !this.mPrimaryBouncerIsOrWillBeShowing) && (!z || (!userCanSkipBouncer && !z8 && z3));
        logListenerModelData(new KeyguardFingerprintListenModel(System.currentTimeMillis(), currentUser, z9, this.mAlternateBouncerShowing, z6, this.mPrimaryBouncerIsOrWillBeShowing, userCanSkipBouncer, this.mCredentialAttempted, this.mDeviceInteractive, this.mIsDreaming, isFingerprintDisabled, this.mFingerprintLockedOut, this.mGoingToSleep, this.mKeyguardGoingAway, isKeyguardVisible(), this.mKeyguardOccluded, this.mOccludingAppRequestingFp, true, z4, z8, this.mSwitchingUser, this.mIsSystemUser, z, z3));
        return z9;
    }

    public final boolean shouldTriggerActiveUnlock() {
        boolean z = this.mAssistantVisible && this.mKeyguardOccluded && !this.mUserHasTrust.get(getCurrentUser(), false);
        boolean z2 = this.mPrimaryBouncerFullyShown || this.mAlternateBouncerShowing || !(!isKeyguardVisible() || this.mGoingToSleep || this.mStatusBarState == 2);
        int currentUser = getCurrentUser();
        boolean z3 = getUserCanSkipBouncer(currentUser) || !this.mLockPatternUtils.isSecure(currentUser);
        boolean isFingerprintLockedOut = isFingerprintLockedOut();
        boolean z4 = !isUnlockingWithBiometricAllowed(true);
        boolean z5 = (!(this.mAuthInterruptActive || z || z2) || this.mSwitchingUser || z3 || isFingerprintLockedOut || z4 || this.mKeyguardGoingAway || this.mSecureCameraLaunched) ? false : true;
        logListenerModelData(new KeyguardActiveUnlockModel(System.currentTimeMillis(), currentUser, z5, z2, this.mAuthInterruptActive, isFingerprintLockedOut, z4, this.mSwitchingUser, z, z3));
        return z5;
    }

    public void startBiometricWatchdog() {
        this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticLambda9
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardUpdateMonitor keyguardUpdateMonitor = KeyguardUpdateMonitor.this;
                keyguardUpdateMonitor.getClass();
                Trace.beginSection("#startBiometricWatchdog");
                if (keyguardUpdateMonitor.mFaceManager != null) {
                    LogBuffer.log$default(keyguardUpdateMonitor.mLogger.logBuffer, "KeyguardUpdateMonitorLog", LogLevel.DEBUG, "Scheduling biometric watchdog for ".concat("face"), null, 8, null);
                    keyguardUpdateMonitor.mFaceManager.scheduleWatchdog();
                }
                if (keyguardUpdateMonitor.mFpm != null) {
                    LogBuffer.log$default(keyguardUpdateMonitor.mLogger.logBuffer, "KeyguardUpdateMonitorLog", LogLevel.DEBUG, "Scheduling biometric watchdog for ".concat("fingerprint"), null, 8, null);
                    keyguardUpdateMonitor.mFpm.scheduleWatchdog();
                }
                Trace.endSection();
            }
        });
    }

    public void startListeningForFace(FaceAuthUiEvent faceAuthUiEvent) {
        int currentUser = getCurrentUser();
        boolean isUnlockWithFacePossible = isUnlockWithFacePossible(currentUser);
        if (this.mFaceCancelSignal != null) {
            this.mLogger.logUnexpectedFaceCancellationSignalState(this.mFaceRunningState, isUnlockWithFacePossible);
        }
        int i = this.mFaceRunningState;
        if (i == 2) {
            setFaceRunningState(3);
            return;
        }
        if (i == 3) {
            return;
        }
        this.mLogger.logStartedListeningForFace(i, faceAuthUiEvent);
        this.mUiEventLogger.logWithInstanceIdAndPosition(faceAuthUiEvent, 0, (String) null, ((SessionTracker) this.mSessionTrackerProvider.get()).getSessionId(1), faceAuthUiEvent.getExtraInfo());
        this.mLogger.logFaceUnlockPossible(isUnlockWithFacePossible);
        if (isUnlockWithFacePossible) {
            this.mFaceCancelSignal = new CancellationSignal();
            boolean z = false;
            SysUiFaceAuthenticateOptions sysUiFaceAuthenticateOptions = new SysUiFaceAuthenticateOptions(currentUser, faceAuthUiEvent, faceAuthUiEvent == FaceAuthUiEvent.FACE_AUTH_UPDATED_STARTED_WAKING_UP ? faceAuthUiEvent.getExtraInfo() : 0);
            FaceAuthenticateOptions build = new FaceAuthenticateOptions.Builder().setUserId(sysUiFaceAuthenticateOptions.userId).setAuthenticateReason(sysUiFaceAuthenticateOptions.authenticateReason).setWakeReason(sysUiFaceAuthenticateOptions.wakeReason).build();
            boolean z2 = isFaceSupported() && ((FaceSensorPropertiesInternal) this.mFaceSensorProperties.get(0)).supportsFaceDetection;
            if (isUnlockingWithBiometricAllowed(BiometricSourceType.FACE)) {
                this.mLogger.m85v("startListeningForFace - authenticate");
                KeyguardBypassController keyguardBypassController = this.mKeyguardBypassController;
                if (keyguardBypassController != null) {
                    keyguardBypassController.getBypassEnabled();
                }
                this.mFaceManager.authenticate((CryptoObject) null, this.mFaceCancelSignal, this.mFaceAuthenticationCallback, (Handler) null, build);
            } else {
                if (isUdfpsSupported() && isFingerprintDetectionRunning()) {
                    z = true;
                }
                if (!z2 || z) {
                    this.mLogger.m85v("Ignoring \"startListeningForFace - detect\". Informing user face isn't available.");
                    this.mFaceAuthenticationCallback.onAuthenticationHelp(-3, this.mContext.getResources().getString(R.string.keyguard_face_unlock_unavailable));
                    return;
                } else {
                    this.mLogger.m85v("startListeningForFace - detect");
                    this.mFaceManager.detectFace(this.mFaceCancelSignal, this.mFaceDetectionCallback, build);
                }
            }
            setFaceRunningState(1);
        }
    }

    public void startListeningForFingerprint() {
        int currentUser = getCurrentUser();
        boolean isUnlockWithFingerprintPossible = isUnlockWithFingerprintPossible(currentUser);
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
            if (isUnlockingWithFingerprintAllowed()) {
                this.mLogger.m85v("startListeningForFingerprint");
                this.mFpm.authenticate((FingerprintManager.CryptoObject) null, this.mFingerprintCancelSignal, this.mFingerprintAuthenticationCallback, (Handler) null, new FingerprintAuthenticateOptions.Builder().setUserId(currentUser).build());
            } else {
                this.mLogger.m85v("startListeningForFingerprint - detect");
                this.mFpm.detectFingerprint(this.mFingerprintCancelSignal, this.mFingerprintDetectionCallback, new FingerprintAuthenticateOptions.Builder().setUserId(currentUser).build());
            }
            setFingerprintRunningState(1);
        }
    }

    public void stopListeningForFace(FaceAuthUiEvent faceAuthUiEvent) {
        this.mLogger.m85v("stopListeningForFace()");
        this.mLogger.logStoppedListeningForFace(this.mFaceRunningState, faceAuthUiEvent.getReason());
        this.mUiEventLogger.log(faceAuthUiEvent, ((SessionTracker) this.mSessionTrackerProvider.get()).getSessionId(1));
        if (this.mFaceRunningState == 1) {
            CancellationSignal cancellationSignal = this.mFaceCancelSignal;
            if (cancellationSignal != null) {
                cancellationSignal.cancel();
                this.mFaceCancelSignal = null;
                removeCallbacks(this.mFaceCancelNotReceived);
                postDelayed(this.mFaceCancelNotReceived, 3000L);
            }
            setFaceRunningState(2);
        }
        if (this.mFaceRunningState == 3) {
            setFaceRunningState(2);
        }
    }

    public void stopListeningForFingerprint() {
        this.mLogger.m85v("stopListeningForFingerprint()");
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

    public void updateFaceListeningState(int i, FaceAuthUiEvent faceAuthUiEvent) {
        if (hasMessages(336)) {
            return;
        }
        removeCallbacks(this.mRetryFaceAuthentication);
        boolean shouldListenForFace = shouldListenForFace();
        int i2 = this.mFaceRunningState;
        if (i2 == 1 && !shouldListenForFace) {
            if (i == 0) {
                this.mLogger.m85v("Ignoring stopListeningForFace()");
                return;
            } else {
                stopListeningForFace(faceAuthUiEvent);
                return;
            }
        }
        if (i2 == 1 || !shouldListenForFace) {
            return;
        }
        if (i == 1) {
            this.mLogger.m85v("Ignoring startListeningForFace()");
        } else {
            startListeningForFace(faceAuthUiEvent);
        }
    }

    public void updateFingerprintListeningState(int i) {
        if (hasMessages(336)) {
            this.mLogger.logHandlerHasAuthContinueMsgs(i);
            return;
        }
        if (!this.mAuthController.mAllFingerprintAuthenticatorsRegistered) {
            this.mLogger.m84d("All FP authenticators not registered, skipping FP listening state update");
            return;
        }
        boolean shouldListenForFingerprint = shouldListenForFingerprint(isUdfpsSupported());
        int i2 = this.mFingerprintRunningState;
        boolean z = i2 == 1 || i2 == 3;
        if (z && !shouldListenForFingerprint) {
            if (i == 0) {
                this.mLogger.m85v("Ignoring stopListeningForFingerprint()");
                return;
            } else {
                stopListeningForFingerprint();
                return;
            }
        }
        if (z || !shouldListenForFingerprint) {
            return;
        }
        if (i == 1) {
            this.mLogger.m85v("Ignoring startListeningForFingerprint()");
        } else {
            startListeningForFingerprint();
        }
    }

    public final void updateSecondaryLockscreenRequirement(int i) {
        Intent intent = (Intent) this.mSecondaryLockscreenRequirement.get(Integer.valueOf(i));
        boolean isSecondaryLockscreenEnabled = this.mDevicePolicyManager.isSecondaryLockscreenEnabled(UserHandle.of(i));
        boolean z = true;
        if (isSecondaryLockscreenEnabled && intent == null) {
            ComponentName profileOwnerOrDeviceOwnerSupervisionComponent = this.mDevicePolicyManager.getProfileOwnerOrDeviceOwnerSupervisionComponent(UserHandle.of(i));
            if (profileOwnerOrDeviceOwnerSupervisionComponent == null) {
                this.mLogger.logMissingSupervisorAppError(i);
            } else {
                ResolveInfo resolveService = this.mPackageManager.resolveService(new Intent("android.app.action.BIND_SECONDARY_LOCKSCREEN_SERVICE").setPackage(profileOwnerOrDeviceOwnerSupervisionComponent.getPackageName()), 0);
                if (resolveService != null && resolveService.serviceInfo != null) {
                    this.mSecondaryLockscreenRequirement.put(Integer.valueOf(i), new Intent().setComponent(resolveService.serviceInfo.getComponentName()));
                }
            }
            z = false;
        } else {
            if (!isSecondaryLockscreenEnabled && intent != null) {
                this.mSecondaryLockscreenRequirement.put(Integer.valueOf(i), null);
            }
            z = false;
        }
        if (z) {
            for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
                if (keyguardUpdateMonitorCallback != null) {
                    keyguardUpdateMonitorCallback.onSecondaryLockscreenRequirementChanged(i);
                }
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

    public final void clearBiometricRecognized(int i) {
        Assert.isMainThread();
        this.mUserFingerprintAuthenticated.clear();
        this.mUserFaceAuthenticated.clear();
        this.mTrustManager.clearAllBiometricRecognized(BiometricSourceType.FINGERPRINT, i);
        this.mTrustManager.clearAllBiometricRecognized(BiometricSourceType.FACE, i);
        this.mLogger.m84d("clearBiometricRecognized");
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricsCleared();
            }
        }
    }

    public final boolean isUnlockingWithBiometricAllowed(BiometricSourceType biometricSourceType) {
        int i = AbstractC080923.$SwitchMap$android$hardware$biometrics$BiometricSourceType[biometricSourceType.ordinal()];
        if (i == 1) {
            return isUnlockingWithBiometricAllowed(isFingerprintClass3());
        }
        if (i != 2) {
            return false;
        }
        return isUnlockingWithBiometricAllowed(isFaceClass3());
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x002f, code lost:
    
        if (r0.requestActiveUnlockOnBioFail == false) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0054, code lost:
    
        r0 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0042, code lost:
    
        if (r0.requestActiveUnlockOnWakeup == false) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0051, code lost:
    
        if (r0.shouldRequestActiveUnlockOnUnlockIntentFromBiometricEnrollment() == false) goto L36;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin activeUnlockRequestOrigin, String str, boolean z) {
        boolean z2;
        if (hasMessages(336)) {
            return;
        }
        ActiveUnlockConfig activeUnlockConfig = this.mActiveUnlockConfig;
        activeUnlockConfig.getClass();
        int i = ActiveUnlockConfig.WhenMappings.$EnumSwitchMapping$0[activeUnlockRequestOrigin.ordinal()];
        if (i == 1) {
            z2 = activeUnlockConfig.requestActiveUnlockOnWakeup;
        } else if (i == 2) {
            if (!activeUnlockConfig.requestActiveUnlockOnUnlockIntent) {
                if (!activeUnlockConfig.requestActiveUnlockOnWakeup) {
                }
            }
            z2 = true;
        } else if (i == 3) {
            if (!activeUnlockConfig.requestActiveUnlockOnBioFail) {
                if (!activeUnlockConfig.requestActiveUnlockOnUnlockIntent) {
                }
            }
            z2 = true;
        } else if (i == 4) {
            if (!activeUnlockConfig.requestActiveUnlockOnWakeup) {
                if (!activeUnlockConfig.requestActiveUnlockOnUnlockIntent) {
                }
            }
            z2 = true;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        if (activeUnlockRequestOrigin == ActiveUnlockConfig.ActiveUnlockRequestOrigin.WAKE && !z2) {
            ActiveUnlockConfig activeUnlockConfig2 = this.mActiveUnlockConfig;
            if (activeUnlockConfig2.requestActiveUnlockOnWakeup || activeUnlockConfig2.requestActiveUnlockOnUnlockIntent || activeUnlockConfig2.requestActiveUnlockOnBioFail) {
                if (!hasMessages(336) && shouldTriggerActiveUnlock()) {
                    this.mLogger.logActiveUnlockTriggered(str);
                    this.mTrustManager.reportUserMayRequestUnlock(getCurrentUser());
                    return;
                }
                return;
            }
        }
        if (z2 && shouldTriggerActiveUnlock()) {
            this.mLogger.logUserRequestedUnlock(activeUnlockRequestOrigin, str, z);
            this.mTrustManager.reportUserRequestedUnlock(getCurrentUser(), z);
        }
    }

    public static boolean isSimPinSecure(int i) {
        return i == 2 || i == 3 || i == 7 || (LsRune.SECURITY_SIM_PERSO_LOCK && i == 12);
    }

    public final void onIsActiveUnlockRunningChanged(boolean z, int i) {
    }
}
