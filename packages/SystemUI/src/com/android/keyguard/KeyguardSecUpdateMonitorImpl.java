package com.android.keyguard;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.IActivityTaskManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.app.trust.TrustManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.hardware.SensorPrivacyManager;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricSourceType;
import android.hardware.face.FaceManager;
import android.hardware.fingerprint.FingerprintAuthenticateOptions;
import android.hardware.fingerprint.FingerprintManager;
import android.hardware.fingerprint.IFingerprintService;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Debug;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.sec.enterprise.auditlog.AuditLog;
import android.service.dreams.IDreamManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.telephony.CarrierConfigManager;
import android.telephony.ServiceState;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.ContextThemeWrapper;
import android.view.IWindowManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import androidx.activity.result.ActivityResultRegistry$register$3$$ExternalSyntheticOutline0;
import androidx.appcompat.app.AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.collection.MutableObjectList$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.compose.ui.autofill.PopulateViewStructure_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.internal.app.IBatteryStats;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.ILockSettings;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.RemoteLockInfo;
import com.android.keyguard.ActiveUnlockConfig;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardTextBuilder;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.SecurityUtils;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3;
import com.android.keyguard.logging.SimLogger;
import com.android.settingslib.fuelgauge.BatteryStatus;
import com.android.systemui.CscRune;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.Rune;
import com.android.systemui.UiOffloadThread;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.deviceentry.data.repository.FaceWakeUpTriggersConfig;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.doze.PluginAODManager;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.dump.DumpsysTableLogger;
import com.android.systemui.facewidget.plugin.PluginFaceWidgetManager;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardDumpLog;
import com.android.systemui.keyguard.KeyguardEditModeController;
import com.android.systemui.keyguard.KeyguardEditModeControllerImpl;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.keyguard.SecurityLog;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.noticenter.NotiCenterPlugin;
import com.android.systemui.pluginlock.PluginLockInstancePolicy;
import com.android.systemui.plugins.aod.PluginAOD;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shared.system.TaskStackChangeListeners;
import com.android.systemui.statusbar.KeyguardBatteryStatus;
import com.android.systemui.statusbar.phone.BiometricUnlockController;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.uithreadmonitor.BinderCallMonitor;
import com.android.systemui.uithreadmonitor.LooperSlowLogController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.Assert;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SafeUIState;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.settings.SecureSettings;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.samsung.android.aod.AODManager;
import com.samsung.android.aod.AODToast;
import com.samsung.android.bio.face.SemBioFaceManager;
import com.samsung.android.cocktailbar.SemCocktailBarManager;
import com.samsung.android.cocktailbar.SemCocktailBarStateInfo;
import com.samsung.android.cover.CoverState;
import com.samsung.android.knox.ex.peripheral.PeripheralBarcodeConstants;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import com.samsung.android.security.mdf.MdfUtils;
import com.samsung.android.view.SemWindowManager;
import com.sec.ims.IMSParameter;
import com.sec.ims.volte2.data.VolteConstants;
import dagger.Lazy;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import javax.inject.Provider;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.TransformingSequence;

/* loaded from: classes.dex */
public class KeyguardSecUpdateMonitorImpl extends KeyguardUpdateMonitor {
    public static SemBioFaceManager sFaceManager;
    public final String[] RemoteLockString;
    public int mActiveRemoteLockIndex;
    public final AlarmManager mAlarmManager;
    public Handler mAuthHandler;
    public final Executor mBackgroundExecutor;
    public final IBatteryStats mBatteryInfo;
    public final SparseIntArray mBiometricFailedAttempts;
    public int mBiometricId;
    public final AnonymousClass5 mBiometricLockoutResetRunnable;
    public final SparseIntArray mBiometricType;
    public final Lazy mBiometricUnlockControllerLazy;
    public final SparseBooleanArray mBiometricsFace;
    public final SparseBooleanArray mBiometricsFingerprint;
    public final AnonymousClass4 mBroadcastReceiver;
    public boolean mCarrierLock;
    public int mCocktailBarWindowType;
    public final Context mContext;
    public CoverState mCoverState;
    public int mCredentialType;
    public KeyguardSecurityModel.SecurityMode mCurrentSecurityMode;
    public final Lazy mDesktopManagerLazy;
    public String mDeviceOwnerInfoText;
    public final DevicePolicyManager mDevicePolicyManager;
    public boolean mDisableCamera;
    public boolean mDisabledBiometricBySecurityDialog;
    public final DisplayLifecycle mDisplayLifecycle;
    public final AnonymousClass3 mDisplayListener;
    public final boolean[] mESimRemoved;
    public boolean mFMMLock;
    public final FaceManager mFaceManager;
    public final ConcurrentLinkedQueue mFaceMessages;
    public final KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda12 mFaceMsgConsumer;
    public final KeyguardFastBioUnlockController mFastUnlockController;
    public final SparseIntArray mFingerPrintBadQualityCounts;
    public final SparseIntArray mFingerPrintFailedAttempts;
    public int mFingerprintAuthenticationSequence;
    public int mFocusWindow;
    public boolean mForceStartFinger;
    public int mFpInDisplayState;
    public final IBinder mFpMaskToken;
    public final ConcurrentLinkedQueue mFpMessages;
    public final KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda12 mFpMsgConsumer;
    public final FingerprintManager mFpm;
    public boolean mHasFocus;
    public boolean mHasLoggedOnceAuditlog;
    public boolean mHasRedactedNotifications;
    public boolean mIsDismissActionExist;
    public boolean mIsDispatching;
    public boolean mIsDreamingForBiometrics;
    public boolean mIsDualDarInnerAuthRequired;
    public boolean mIsDualDarInnerAuthShowing;
    public boolean mIsDynamicLockViewMode;
    public boolean mIsEarlyWakeUp;
    public boolean mIsFODStrictMode;
    public boolean mIsFPCanceledByForegroundApp;
    public boolean mIsFPCanceledByProximity;
    public boolean mIsFaceReady;
    public boolean mIsFaceWidgetFullScreenMode;
    public boolean mIsFpLeave;
    public boolean mIsKidsModeRunning;
    public boolean mIsNotiStarShown;
    public boolean mIsNowBarExpandMode;
    public boolean mIsOutOfService;
    public boolean mIsOwnerInfoEnabled;
    public boolean mIsPanelExpandingStarted;
    public boolean mIsQsFullyExpanded;
    public boolean mIsRunningBlackMemo;
    public boolean mIsScreenSaverRunning;
    public boolean mIsShortcutLaunchInProgress;
    public boolean mIsStartFacePossible;
    public boolean mIsTspFpGuideShown;
    public KeyguardBatteryStatus mKeyguardBatteryStatus;
    public KeyguardConstants$KeyguardDismissActionType mKeyguardDismissActionType;
    public final Provider mKeyguardDisplayManager;
    public final Lazy mKeyguardEditModeControllerLazy;
    public boolean mKeyguardUnlocking;
    public final LockPatternUtils mLockPatternUtils;
    public ILockSettings mLockSettingsService;
    public long mLockoutAttemptDeadline;
    public long mLockoutAttemptTimeout;
    public long mLockoutBiometricAttemptDeadline;
    public long mLockoutBiometricAttemptTimeout;
    public boolean mLockscreenDisabled;
    public final LooperSlowLogController mLooperSlowLogController;
    public int mMaximumFailedPasswordsForWipe;
    public boolean mNeedSubBioAuth;
    public boolean mNeedSubWofFpAuth;
    public final NotificationManager mNotificationManager;
    private final SettingsHelper.OnChangedCallback mOneHandModeSettingsCallback;
    public String mOwnerInfoText;
    public boolean mPermanentLock;
    public final Lazy mPluginAODManagerLazy;
    public final Lazy mPluginFaceWidgetManagerLazy;
    public final PowerManager mPowerManager;
    public final ArrayList mRemoteLockInfo;
    public RemoteLockInfo mRemoteLockSimulationInfo;
    public boolean mSIPShown;
    public boolean mScreenOrientationNoSensorValue;
    public final AnonymousClass2 mSecureLockChangedCallback;
    public CancellationSignal mSemFaceCancelSignal;
    public final HashMap mServiceStatesBySlotId;
    private final SettingsHelper.OnChangedCallback mSettingsCallbackForUPSM;
    private final SettingsHelper mSettingsHelper;
    public final Uri[] mSettingsValueListForPSM;
    public boolean mSimDisabledPermanently;
    public final boolean[] mSimPinPassed;
    public boolean mSystemReady;
    public final AnonymousClass1 mTimeoutSkipFPResponse;
    public final TrustManager mTrustManager;
    public boolean mUdfpsFingerDown;
    public final Lazy mViewMediatorCallbackLazy;
    public final KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda11 mWaitingFocusRunnable;
    public final IWindowManager mWindowManagerService;

    /* renamed from: com.android.keyguard.KeyguardSecUpdateMonitorImpl$8, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass8 {
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

    public static /* synthetic */ void $r8$lambda$5lhNrxzXYmtVuTwfeiQe_uCItoQ(KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl, boolean z) {
        keyguardSecUpdateMonitorImpl.getClass();
        Log.d("KeyguardUpdateMonitor", "showFingerprintBlockPopup onCheckedChanged :  " + z);
        keyguardSecUpdateMonitorImpl.mSettingsHelper.setFingerprintSensorPopupDoNotShowAgain(z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v4, types: [android.content.BroadcastReceiver, com.android.keyguard.KeyguardSecUpdateMonitorImpl$4] */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.keyguard.KeyguardSecUpdateMonitorImpl$5] */
    /* JADX WARN: Type inference failed for: r7v13, types: [com.android.keyguard.KeyguardSecUpdateMonitorImpl$3] */
    /* JADX WARN: Type inference failed for: r7v5, types: [com.android.keyguard.KeyguardSecUpdateMonitorImpl$1] */
    /* JADX WARN: Type inference failed for: r7v7, types: [com.android.keyguard.KeyguardSecUpdateMonitorImpl$2] */
    public KeyguardSecUpdateMonitorImpl(IBatteryStats iBatteryStats, NotificationManager notificationManager, KeyguardFastBioUnlockController keyguardFastBioUnlockController, BinderCallMonitor binderCallMonitor, LooperSlowLogController looperSlowLogController, SettingsHelper settingsHelper, AlarmManager alarmManager, PowerManager powerManager, Lazy lazy, Lazy lazy2, Lazy lazy3, Lazy lazy4, IWindowManager iWindowManager, Lazy lazy5, Lazy lazy6, DisplayLifecycle displayLifecycle, Provider provider, Context context, UserTracker userTracker, Looper looper, BroadcastDispatcher broadcastDispatcher, SecureSettings secureSettings, DumpManager dumpManager, Executor executor, Executor executor2, StatusBarStateController statusBarStateController, LockPatternUtils lockPatternUtils, AuthController authController, TelephonyListenerManager telephonyListenerManager, InteractionJankMonitor interactionJankMonitor, LatencyTracker latencyTracker, ActiveUnlockConfig activeUnlockConfig, KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger, SimLogger simLogger, UiEventLogger uiEventLogger, Provider provider2, TrustManager trustManager, SubscriptionManager subscriptionManager, UserManager userManager, IDreamManager iDreamManager, DevicePolicyManager devicePolicyManager, SensorPrivacyManager sensorPrivacyManager, TelephonyManager telephonyManager, PackageManager packageManager, FaceManager faceManager, FingerprintManager fingerprintManager, BiometricManager biometricManager, FaceWakeUpTriggersConfig faceWakeUpTriggersConfig, CarrierConfigManager carrierConfigManager, DevicePostureController devicePostureController, Optional<Object> optional, TaskStackChangeListeners taskStackChangeListeners, SelectedUserInteractor selectedUserInteractor, IActivityTaskManager iActivityTaskManager, Provider provider3, Provider provider4, Provider provider5, Provider provider6, Provider provider7) {
        super(faceManager, context, userTracker, looper, broadcastDispatcher, dumpManager, executor, executor2, statusBarStateController, lockPatternUtils, authController, telephonyListenerManager, interactionJankMonitor, latencyTracker, activeUnlockConfig, keyguardUpdateMonitorLogger, simLogger, uiEventLogger, provider2, trustManager, subscriptionManager, userManager, iDreamManager, devicePolicyManager, sensorPrivacyManager, telephonyManager, packageManager, fingerprintManager, biometricManager, faceWakeUpTriggersConfig, carrierConfigManager, devicePostureController, optional, taskStackChangeListeners, selectedUserInteractor, iActivityTaskManager, provider3, provider4, provider5, provider6, provider7);
        this.mCoverState = null;
        this.mCurrentSecurityMode = KeyguardSecurityModel.SecurityMode.Invalid;
        this.mSimPinPassed = new boolean[2];
        this.mIsEarlyWakeUp = false;
        this.mIsDismissActionExist = false;
        this.mKeyguardDismissActionType = KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_DEFAULT;
        this.mIsRunningBlackMemo = false;
        this.mRemoteLockInfo = new ArrayList();
        this.mActiveRemoteLockIndex = -1;
        this.mHasFocus = true;
        this.mFocusWindow = 3;
        this.mAuthHandler = null;
        this.mBiometricFailedAttempts = new SparseIntArray();
        this.mUdfpsFingerDown = false;
        this.mFingerPrintFailedAttempts = new SparseIntArray();
        this.mFingerPrintBadQualityCounts = new SparseIntArray();
        this.mDisabledBiometricBySecurityDialog = false;
        this.mFingerprintAuthenticationSequence = 0;
        this.mTimeoutSkipFPResponse = new Runnable() { // from class: com.android.keyguard.KeyguardSecUpdateMonitorImpl.1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardSecUpdateMonitorImpl.this.getClass();
                Log.i("KeyguardFingerprint", "Timeout skip FP response");
            }
        };
        this.mLockoutAttemptDeadline = 0L;
        this.mLockoutAttemptTimeout = 0L;
        this.mLockoutBiometricAttemptDeadline = 0L;
        this.mLockoutBiometricAttemptTimeout = 0L;
        this.mIsOwnerInfoEnabled = false;
        this.mOwnerInfoText = null;
        this.mDeviceOwnerInfoText = null;
        this.mSecureLockChangedCallback = new IRemoteCallback.Stub() { // from class: com.android.keyguard.KeyguardSecUpdateMonitorImpl.2
            public final void sendResult(Bundle bundle) {
                int i = bundle.getInt("secureState");
                ListPopupWindow$$ExternalSyntheticOutline0.m(i, "mSecureLockChangedCallback sendResult : secureState ", "KeyguardUpdateMonitor");
                KeyguardSecUpdateMonitorImpl.this.dispatchSecureState(i);
            }
        };
        this.mBiometricType = new SparseIntArray();
        this.mBiometricsFingerprint = new SparseBooleanArray();
        this.mBiometricsFace = new SparseBooleanArray();
        final int i = 0;
        this.mSettingsCallbackForUPSM = new SettingsHelper.OnChangedCallback(this) { // from class: com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda9
            public final /* synthetic */ KeyguardSecUpdateMonitorImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl = this.f$0;
                switch (i) {
                    case 0:
                        SemBioFaceManager semBioFaceManager = KeyguardSecUpdateMonitorImpl.sFaceManager;
                        if (keyguardSecUpdateMonitorImpl.updateBiometricsOptionState(((UserTrackerImpl) keyguardSecUpdateMonitorImpl.mUserTracker).getUserId())) {
                            keyguardSecUpdateMonitorImpl.dispatchLockModeChanged();
                        }
                        if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                            keyguardSecUpdateMonitorImpl.updateFingerprintListeningState(2);
                            break;
                        }
                        break;
                    default:
                        SemBioFaceManager semBioFaceManager2 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                        keyguardSecUpdateMonitorImpl.updateFingerprintListeningState(2);
                        String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
                        break;
                }
            }
        };
        this.mSettingsValueListForPSM = new Uri[]{Settings.System.getUriFor(SettingsHelper.INDEX_ULTRA_POWERSAVING_MODE), Settings.System.getUriFor(SettingsHelper.INDEX_EMERGENCY_MODE)};
        this.mDisplayListener = new DisplayLifecycle.Observer() { // from class: com.android.keyguard.KeyguardSecUpdateMonitorImpl.3
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z) {
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl = KeyguardSecUpdateMonitorImpl.this;
                if (keyguardSecUpdateMonitorImpl.isFaceDetectionRunning()) {
                    keyguardSecUpdateMonitorImpl.stopListeningForFace(FaceAuthUiEvent.FACE_AUTH_STOPPED_USER_INPUT_ON_BOUNCER);
                }
                if (!LsRune.SECURITY_SUB_DISPLAY_LOCK) {
                    if (LsRune.SECURITY_SUB_DISPLAY_COVER) {
                        if (!LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY || z) {
                            keyguardSecUpdateMonitorImpl.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_FOLDER_STATE_CHANGED);
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (z) {
                    keyguardSecUpdateMonitorImpl.updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_FOLDER_STATE_CHANGED);
                    return;
                }
                int wofType = keyguardSecUpdateMonitorImpl.mSettingsHelper.getWofType();
                if (wofType == 1 || wofType == 2) {
                    keyguardSecUpdateMonitorImpl.updateFingerprintListeningState(2);
                }
            }
        };
        this.mESimRemoved = new boolean[2];
        this.mFpInDisplayState = 0;
        this.mFpMaskToken = null;
        this.mIsShortcutLaunchInProgress = false;
        this.mIsPanelExpandingStarted = false;
        this.mIsTspFpGuideShown = false;
        this.mCocktailBarWindowType = 0;
        final int i2 = 1;
        this.mOneHandModeSettingsCallback = new SettingsHelper.OnChangedCallback(this) { // from class: com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda9
            public final /* synthetic */ KeyguardSecUpdateMonitorImpl f$0;

            {
                this.f$0 = this;
            }

            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl = this.f$0;
                switch (i2) {
                    case 0:
                        SemBioFaceManager semBioFaceManager = KeyguardSecUpdateMonitorImpl.sFaceManager;
                        if (keyguardSecUpdateMonitorImpl.updateBiometricsOptionState(((UserTrackerImpl) keyguardSecUpdateMonitorImpl.mUserTracker).getUserId())) {
                            keyguardSecUpdateMonitorImpl.dispatchLockModeChanged();
                        }
                        if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                            keyguardSecUpdateMonitorImpl.updateFingerprintListeningState(2);
                            break;
                        }
                        break;
                    default:
                        SemBioFaceManager semBioFaceManager2 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                        keyguardSecUpdateMonitorImpl.updateFingerprintListeningState(2);
                        String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
                        break;
                }
            }
        };
        this.mWaitingFocusRunnable = new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda11(this, 0);
        this.mIsNowBarExpandMode = false;
        this.mForceStartFinger = false;
        this.mNeedSubBioAuth = false;
        this.mNeedSubWofFpAuth = false;
        this.mServiceStatesBySlotId = new HashMap();
        this.mIsOutOfService = true;
        this.mIsDispatching = false;
        ?? r0 = new BroadcastReceiver() { // from class: com.android.keyguard.KeyguardSecUpdateMonitorImpl.4
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                int i3;
                String action = intent.getAction();
                Log.d("KeyguardUpdateMonitor", "received broadcast " + action);
                if ("android.intent.action.PACKAGE_ADDED".equals(action) || "android.intent.action.PACKAGE_REMOVED".equals(action) || "android.intent.action.PACKAGE_CHANGED".equals(action) || "android.intent.action.PACKAGE_DATA_CLEARED".equals(action)) {
                    if (intent.getData() == null) {
                        return;
                    }
                    action.getClass();
                    switch (action) {
                        case "android.intent.action.PACKAGE_CHANGED":
                            i3 = VolteConstants.ErrorCode.PPP_OPEN_FAILURE;
                            break;
                        case "android.intent.action.PACKAGE_DATA_CLEARED":
                            i3 = 1305;
                            break;
                        case "android.intent.action.PACKAGE_REMOVED":
                            i3 = 1303;
                            break;
                        case "android.intent.action.PACKAGE_ADDED":
                            i3 = VolteConstants.ErrorCode.PPP_STATUS_CLOSE_EVENT;
                            break;
                        default:
                            return;
                    }
                    Message messageObtainMessage = KeyguardSecUpdateMonitorImpl.this.mHandler.obtainMessage(i3, intent.getData().getSchemeSpecificPart());
                    if (i3 == 1303) {
                        if (intent.getExtras() != null) {
                            messageObtainMessage.arg1 = intent.getExtras().getBoolean("android.intent.extra.REPLACING") ? 1 : 0;
                        } else {
                            messageObtainMessage.arg1 = 0;
                        }
                    }
                    KeyguardSecUpdateMonitorImpl.this.mHandler.sendMessage(messageObtainMessage);
                    return;
                }
                if ("com.samsung.intent.action.EMERGENCY_STATE_CHANGED".equals(action)) {
                    int intExtra = intent.getIntExtra("reason", 0);
                    KeyguardUpdateMonitor.AnonymousClass16 anonymousClass16 = KeyguardSecUpdateMonitorImpl.this.mHandler;
                    anonymousClass16.sendMessage(anonymousClass16.obtainMessage(1306, Integer.valueOf(intExtra)));
                    return;
                }
                if ("com.samsung.intent.action.USB_RESTRICTION_STATE".equals(action)) {
                    String stringExtra = intent.getStringExtra("RestrictionState");
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("usb restriction state = ", stringExtra, "KeyguardUpdateMonitor");
                    KeyguardUpdateMonitor.AnonymousClass16 anonymousClass162 = KeyguardSecUpdateMonitorImpl.this.mHandler;
                    anonymousClass162.sendMessage(anonymousClass162.obtainMessage(1308, Boolean.valueOf(stringExtra.equals("ON"))));
                    return;
                }
                if ("com.sec.android.intent.action.BLACK_MEMO".equals(action)) {
                    String stringExtra2 = intent.getStringExtra("state");
                    boolean zEquals = TextUtils.equals(stringExtra2, "show");
                    KeyguardSecUpdateMonitorImpl.this.mHandler.removeMessages(1001);
                    StringBuilder sb = new StringBuilder("screen off memo state changed, state = ");
                    sb.append(stringExtra2);
                    sb.append(", running ");
                    CarrierTextManager$$ExternalSyntheticOutline0.m(sb, KeyguardSecUpdateMonitorImpl.this.mIsRunningBlackMemo, " -> ", zEquals, "KeyguardUpdateMonitor");
                    KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl = KeyguardSecUpdateMonitorImpl.this;
                    if (!keyguardSecUpdateMonitorImpl.mGoingToSleep || !keyguardSecUpdateMonitorImpl.mIsRunningBlackMemo || zEquals) {
                        keyguardSecUpdateMonitorImpl.setIsRunningBlackMemo(zEquals);
                        return;
                    } else {
                        KeyguardUpdateMonitor.AnonymousClass16 anonymousClass163 = keyguardSecUpdateMonitorImpl.mHandler;
                        anonymousClass163.sendMessageDelayed(anonymousClass163.obtainMessage(1001), 600L);
                        return;
                    }
                }
                if ("com.samsung.intent.action.SET_SCREEN_RATIO_VALUE".equals(action)) {
                    KeyguardUpdateMonitor.AnonymousClass16 anonymousClass164 = KeyguardSecUpdateMonitorImpl.this.mHandler;
                    anonymousClass164.sendMessage(anonymousClass164.obtainMessage(VolteConstants.ErrorCode.CALL_TRANSFER_FAILED));
                    return;
                }
                if ("com.samsung.keyguard.BIOMETRIC_LOCKOUT_RESET".equals(action)) {
                    KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl2 = KeyguardSecUpdateMonitorImpl.this;
                    if (keyguardSecUpdateMonitorImpl2.mHandler.hasCallbacks(keyguardSecUpdateMonitorImpl2.mBiometricLockoutResetRunnable)) {
                        KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl3 = KeyguardSecUpdateMonitorImpl.this;
                        keyguardSecUpdateMonitorImpl3.mHandler.removeCallbacks(keyguardSecUpdateMonitorImpl3.mBiometricLockoutResetRunnable);
                    }
                    KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl4 = KeyguardSecUpdateMonitorImpl.this;
                    keyguardSecUpdateMonitorImpl4.mHandler.post(keyguardSecUpdateMonitorImpl4.mBiometricLockoutResetRunnable);
                    return;
                }
                if (PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action)) {
                    if ("globalactions".equals(intent.getStringExtra("reason"))) {
                        KeyguardSecUpdateMonitorImpl.this.mHandler.sendEmptyMessage(VolteConstants.ErrorCode.CALL_REJECT_REASON_USR_BUSY_CS_CALL);
                        return;
                    }
                    return;
                }
                if ("android.intent.action.ACTION_SCREEN_ON_BY_PROXIMITY".equals(action)) {
                    com.android.systemui.keyguard.Log.d("KeyguardFingerprint", "onReceive : " + action);
                    KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl5 = KeyguardSecUpdateMonitorImpl.this;
                    keyguardSecUpdateMonitorImpl5.mIsFPCanceledByProximity = false;
                    keyguardSecUpdateMonitorImpl5.updateFingerprintListeningState(2);
                    return;
                }
                if ("android.intent.action.ACTION_SCREEN_OFF_BY_PROXIMITY".equals(action)) {
                    com.android.systemui.keyguard.Log.d("KeyguardFingerprint", "onReceive : " + action);
                    KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl6 = KeyguardSecUpdateMonitorImpl.this;
                    keyguardSecUpdateMonitorImpl6.mIsFPCanceledByProximity = true;
                    keyguardSecUpdateMonitorImpl6.updateFingerprintListeningState(2);
                    return;
                }
                if (CscRune.SECURITY_DISABLE_EMERGENCY_CALL_WHEN_OFFLINE && "android.intent.action.SERVICE_STATE".equals(action)) {
                    ServiceState serviceStateNewFromBundle = ServiceState.newFromBundle(intent.getExtras());
                    int intExtra2 = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                    int intExtra3 = intent.getIntExtra("android.telephony.extra.SLOT_INDEX", -1);
                    Objects.toString(serviceStateNewFromBundle);
                    KeyguardUpdateMonitor.AnonymousClass16 anonymousClass165 = KeyguardSecUpdateMonitorImpl.this.mHandler;
                    anonymousClass165.sendMessage(anonymousClass165.obtainMessage(VolteConstants.ErrorCode.CALL_END_REASON_TELEPHONY_NOT_RESPONDING, intExtra2, intExtra3, serviceStateNewFromBundle));
                    return;
                }
                if (!"com.samsung.intent.action.BCS_REQUEST".equals(action) || !"AT+SVCIFPGM=1,7".equalsIgnoreCase(intent.getStringExtra("command"))) {
                    if ("com.sec.android.app.kidshome.action.DEFAULT_HOME_CHANGE".equals(action)) {
                        KeyguardSecUpdateMonitorImpl.this.mIsKidsModeRunning = intent.getBooleanExtra("kids_home_mode", false);
                        ActionBarContextView$$ExternalSyntheticOutline0.m(ActivityResultRegistry$register$3$$ExternalSyntheticOutline0.m("onReceive : ", action, ", isKidsMode : "), KeyguardSecUpdateMonitorImpl.this.mIsKidsModeRunning, "KeyguardUpdateMonitor");
                        return;
                    }
                    return;
                }
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl7 = KeyguardSecUpdateMonitorImpl.this;
                SemBioFaceManager semBioFaceManager = KeyguardSecUpdateMonitorImpl.sFaceManager;
                int userId = ((UserTrackerImpl) keyguardSecUpdateMonitorImpl7.mUserTracker).getUserId();
                boolean z = (keyguardSecUpdateMonitorImpl7.isSecure(userId) && keyguardSecUpdateMonitorImpl7.mKeyguardShowing && !keyguardSecUpdateMonitorImpl7.getUserCanSkipBouncer(userId)) ? false : true;
                StringBuilder sbM = RowView$$ExternalSyntheticOutline0.m("isUnlocked = ", ", isSecure() = ", z);
                sbM.append(keyguardSecUpdateMonitorImpl7.isSecure(userId));
                sbM.append(", mKeyguardShowing = ");
                sbM.append(keyguardSecUpdateMonitorImpl7.mKeyguardShowing);
                sbM.append(", getUserCanSkipBouncer() = ");
                sbM.append(keyguardSecUpdateMonitorImpl7.getUserCanSkipBouncer(userId));
                Log.i("KeyguardUpdateMonitor", sbM.toString());
                StringBuilder sb2 = new StringBuilder("AT+SVCIFPGM=1,7\r\n+SVCIFPGM:1,");
                StringBuilder sb3 = new StringBuilder();
                int credentialTypeForUser = keyguardSecUpdateMonitorImpl7.getCredentialTypeForUser(userId);
                if (credentialTypeForUser != -1) {
                    sb3.append(LockPatternUtils.credentialTypeToString(credentialTypeForUser));
                } else if (keyguardSecUpdateMonitorImpl7.isLockscreenDisabled()) {
                    sb3.append(PeripheralBarcodeConstants.Symbology.Type.TYPE_NONE);
                } else {
                    sb3.append("SWIPE");
                }
                int i4 = keyguardSecUpdateMonitorImpl7.mBiometricType.get(userId);
                if (KeyguardSecUpdateMonitorImpl.containsFlag(i4, 1)) {
                    sb3.append("/FINGERPRINT");
                }
                if (KeyguardSecUpdateMonitorImpl.containsFlag(i4, 256)) {
                    sb3.append("/FACE");
                }
                if (keyguardSecUpdateMonitorImpl7.isRemoteLockEnabled()) {
                    sb3.append("/" + keyguardSecUpdateMonitorImpl7.RemoteLockString[keyguardSecUpdateMonitorImpl7.getRemoteLockType()]);
                }
                if (keyguardSecUpdateMonitorImpl7.mCarrierLock) {
                    sb3.append("/CARRIERLOCK");
                }
                if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isAdminLockEnabled()) {
                    sb3.append("/ADMIN");
                }
                sb2.append(sb3.toString());
                String strM = TransitionKt$$ExternalSyntheticOutline0.m(sb2, z ? ",UNLOCK" : ",LOCK", "\r\n");
                Intent intent2 = new Intent("com.samsung.intent.action.BCS_RESPONSE");
                intent2.putExtra("response", strM);
                KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("response: ", strM, "KeyguardUpdateMonitor");
                keyguardSecUpdateMonitorImpl7.mContext.sendBroadcastAsUser(intent2, UserHandle.SYSTEM);
            }
        };
        this.mBroadcastReceiver = r0;
        this.mBiometricLockoutResetRunnable = new Runnable() { // from class: com.android.keyguard.KeyguardSecUpdateMonitorImpl.5
            public final int userId;

            {
                this.userId = ((UserTrackerImpl) KeyguardSecUpdateMonitorImpl.this.mUserTracker).getUserId();
            }

            @Override // java.lang.Runnable
            public final void run() {
                Log.d("KeyguardUpdateMonitor", "mBiometricLockoutResetRunnable()");
                KeyguardSecUpdateMonitorImpl.this.mLockPatternUtils.clearBiometricAttemptDeadline(this.userId);
                KeyguardSecUpdateMonitorImpl.this.dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15(14), null);
                KeyguardSecUpdateMonitorImpl.this.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_FACE_LOCKOUT_RESET);
            }
        };
        this.mFpMessages = new ConcurrentLinkedQueue();
        this.mFaceMessages = new ConcurrentLinkedQueue();
        this.mFpMsgConsumer = new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda12(this, 0);
        this.mFaceMsgConsumer = new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda12(this, 1);
        this.mHasLoggedOnceAuditlog = false;
        this.RemoteLockString = new String[]{"FMM", "CARRIERLOCK", "RMM", "KNOXGUARD"};
        this.mFastUnlockController = keyguardFastBioUnlockController;
        this.mContext = context;
        this.mDevicePolicyManager = devicePolicyManager;
        this.mLockPatternUtils = lockPatternUtils;
        this.mFpm = fingerprintManager;
        this.mFaceManager = faceManager;
        this.mBackgroundExecutor = executor;
        this.mDesktopManagerLazy = lazy2;
        this.mLooperSlowLogController = looperSlowLogController;
        this.mSettingsHelper = settingsHelper;
        this.mAlarmManager = alarmManager;
        this.mPowerManager = powerManager;
        this.mBatteryInfo = iBatteryStats;
        this.mNotificationManager = notificationManager;
        this.mPluginFaceWidgetManagerLazy = lazy4;
        this.mDisplayLifecycle = displayLifecycle;
        this.mBiometricUnlockControllerLazy = lazy6;
        this.mKeyguardDisplayManager = provider;
        this.mTrustManager = (TrustManager) context.getSystemService(TrustManager.class);
        this.mKeyguardBatteryStatus = new KeyguardBatteryStatus(1, 100, 0, 0, 0, 0, false, false);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        intentFilter.addAction("com.samsung.intent.action.EMERGENCY_STATE_CHANGED");
        intentFilter.addDataScheme("package");
        broadcastDispatcher.registerReceiver(intentFilter, r0);
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.samsung.intent.action.USB_RESTRICTION_STATE");
        broadcastDispatcher.registerReceiver(r0, intentFilter2, null, null, 2, "android.permission.MANAGE_USB");
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("com.sec.android.intent.action.BLACK_MEMO");
        broadcastDispatcher.registerReceiver(intentFilter3, r0);
        IntentFilter intentFilter4 = new IntentFilter();
        intentFilter4.addAction("com.samsung.keyguard.BIOMETRIC_LOCKOUT_RESET");
        broadcastDispatcher.registerReceiver(intentFilter4, r0);
        SemCocktailBarManager semCocktailBarManager = SemCocktailBarManager.getInstance(context);
        if (semCocktailBarManager != null) {
            semCocktailBarManager.registerStateListener(new SemCocktailBarManager.CocktailBarStateChangedListener() { // from class: com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda14
                public final void onCocktailBarStateChanged(SemCocktailBarStateInfo semCocktailBarStateInfo) {
                    KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl = this.f$0;
                    SemBioFaceManager semBioFaceManager = KeyguardSecUpdateMonitorImpl.sFaceManager;
                    keyguardSecUpdateMonitorImpl.getClass();
                    int i3 = semCocktailBarStateInfo.windowType;
                    keyguardSecUpdateMonitorImpl.mCocktailBarWindowType = i3;
                    if (i3 == 2 || i3 == 1) {
                        if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && keyguardSecUpdateMonitorImpl.isFingerprintOptionEnabled()) {
                            keyguardSecUpdateMonitorImpl.updateFingerprintListeningState(2);
                        }
                        keyguardSecUpdateMonitorImpl.updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_COCKTAIL_BAR_SHOWING_CHANGED);
                    }
                }
            });
        }
        if (CscRune.SECURITY_DISABLE_EMERGENCY_CALL_WHEN_OFFLINE) {
            IntentFilter intentFilter5 = new IntentFilter();
            intentFilter5.addAction("android.intent.action.SERVICE_STATE");
            broadcastDispatcher.registerReceiver(intentFilter5, r0);
        }
        broadcastDispatcher.registerReceiver(r0, AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0.m("com.sec.android.app.kidshome.action.DEFAULT_HOME_CHANGE"), null, null, 2, "com.samsung.kidshome.broadcast.DEFAULT_HOME_CHANGE_PERMISSION");
        IntentFilter intentFilter6 = new IntentFilter();
        intentFilter6.addAction("com.samsung.intent.action.SET_SCREEN_RATIO_VALUE");
        broadcastDispatcher.registerReceiver(intentFilter6, r0);
        updateDualDARInnerLockscreenRequirement(ActivityManager.getCurrentUser());
        this.mViewMediatorCallbackLazy = lazy;
        if (this.mLockSettingsService == null) {
            this.mLockSettingsService = ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
        }
        try {
            this.mLockSettingsService.setShellCommandCallback(new IRemoteCallback.Stub() { // from class: com.android.keyguard.KeyguardSecUpdateMonitorImpl.7
                public final void sendResult(Bundle bundle) {
                    if (bundle == null) {
                        return;
                    }
                    String string = bundle.getString("command", "");
                    string.getClass();
                    switch (string) {
                        case "unlock":
                            String string2 = bundle.getString("type", "");
                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("shellCommandCallback: unlock by ", string2, "KeyguardUpdateMonitor");
                            KeyguardSecUpdateMonitorImpl.this.mHandler.obtainMessage(VolteConstants.ErrorCode.CALL_ENDED_BY_NW_HANDOVER_BEFORE_100_TRYING, string2).sendToTarget();
                            break;
                        case "reconnect-pf":
                            if (Build.IS_USERDEBUG || Build.IS_ENG) {
                                Log.d("KeyguardUpdateMonitor", "shellCommandCallback: reconnect plugin face_widget ");
                                KeyguardSecUpdateMonitorImpl.this.mHandler.obtainMessage(1307).sendToTarget();
                                break;
                            }
                            break;
                        case "fail":
                            String string3 = bundle.getString("type", "");
                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("shellCommandCallback: fail to unlock by ", string3, "KeyguardUpdateMonitor");
                            KeyguardSecUpdateMonitorImpl.this.mHandler.obtainMessage(VolteConstants.ErrorCode.CALL_TRANSFER_SUCCESS, string3).sendToTarget();
                            break;
                        case "lock":
                            String string4 = bundle.getString("type", "");
                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("shellCommandCallback: lock by ", string4, "KeyguardUpdateMonitor");
                            KeyguardSecUpdateMonitorImpl.this.mHandler.obtainMessage(VolteConstants.ErrorCode.CALL_BARRED_DUE_TO_SSAC, string4).sendToTarget();
                            break;
                    }
                }
            });
        } catch (RemoteException e) {
            Log.d("KeyguardUpdateMonitor", "setShellCommandCallback RemoteException! " + e);
        }
        broadcastDispatcher.registerReceiver(AppCompatDelegateImpl$AutoBatteryNightModeManager$$ExternalSyntheticOutline0.m("com.samsung.intent.action.BCS_REQUEST"), this.mBroadcastReceiver);
        this.mPluginAODManagerLazy = lazy3;
        broadcastDispatcher.registerReceiver(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("android.intent.action.ACTION_SCREEN_ON_BY_PROXIMITY", "android.intent.action.ACTION_SCREEN_OFF_BY_PROXIMITY"), this.mBroadcastReceiver);
        if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
            this.mWindowManagerService = iWindowManager;
        }
        this.mKeyguardEditModeControllerLazy = lazy5;
    }

    public static void addAdditionalLog(String str) {
        SecurityLog.d("KeyguardUpdateMonitor", str);
    }

    public static boolean containsFlag(int i, int i2) {
        return (i & i2) != 0;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void addFailedFMMUnlockAttempt(int i) {
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        if (lockPatternUtils != null) {
            lockPatternUtils.addFailedFMMUnlockAttempt(i);
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean checkValidPrevCredentialType() {
        int credentialTypeForUser = this.mLockPatternUtils.getCredentialTypeForUser(-9899);
        return credentialTypeForUser == 1 || credentialTypeForUser == 2 || credentialTypeForUser == 3 || credentialTypeForUser == 4;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void clearESimRemoved() {
        boolean[] zArr = this.mESimRemoved;
        zArr[1] = false;
        zArr[0] = false;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void clearFailedUnlockAttempts(final boolean z) {
        if (isForgotPasswordView()) {
            return;
        }
        final int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        if (isSecure(userId)) {
            this.mBiometricFailedAttempts.delete(userId);
            this.mFingerPrintFailedAttempts.delete(userId);
            this.mFingerPrintBadQualityCounts.delete(userId);
            setFodStrictMode(false);
        }
        ((UiOffloadThread) Dependency.sDependency.getDependencyInner(UiOffloadThread.class)).execute(new Runnable() { // from class: com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda21
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl = this.f$0;
                boolean z2 = z;
                int i = userId;
                if (z2) {
                    keyguardSecUpdateMonitorImpl.mLockPatternUtils.clearLockoutAttemptDeadline(i);
                }
                keyguardSecUpdateMonitorImpl.mLockPatternUtils.clearBiometricAttemptDeadline(i);
                keyguardSecUpdateMonitorImpl.mLockPatternUtils.clearFailedFMMUnlockAttempt(i);
            }
        });
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void clearFingerBadQualityCounts() {
        this.mFingerPrintBadQualityCounts.delete(((UserTrackerImpl) this.mUserTracker).getUserId());
    }

    public final void createChannels() {
        NotificationChannel notificationChannel = new NotificationChannel("fbe_channel_id", this.mContext.getResources().getString(R.string.kg_fbe_notification_header), 2);
        notificationChannel.enableLights(true);
        notificationChannel.setLockscreenVisibility(0);
        this.mNotificationManager.createNotificationChannel(notificationChannel);
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void dispatchCallback(KeyguardUpdateMonitor$$ExternalSyntheticLambda9 keyguardUpdateMonitor$$ExternalSyntheticLambda9) {
        dispatchCallback(keyguardUpdateMonitor$$ExternalSyntheticLambda9, null);
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void dispatchCoverState(CoverState coverState) {
        synchronized (this) {
            this.mCoverState = coverState;
        }
        Message messageObtainMessage = this.mHandler.obtainMessage(VolteConstants.ErrorCode.SERVER_UNREACHABLE);
        messageObtainMessage.obj = coverState;
        messageObtainMessage.sendToTarget();
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void dispatchDlsBiometricMode(boolean z) {
        if (this.mHandler.hasMessages(1402)) {
            this.mHandler.removeMessages(1402);
        }
        KeyguardUpdateMonitor.AnonymousClass16 anonymousClass16 = this.mHandler;
        anonymousClass16.sendMessage(anonymousClass16.obtainMessage(1402, Boolean.valueOf(z)));
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void dispatchDlsViewMode(int i) {
        ListPopupWindow$$ExternalSyntheticOutline0.m(i, "dispatchDlsViewMode(), mode=", "KeyguardUpdateMonitor");
        if (this.mHandler.hasMessages(1403)) {
            this.mHandler.removeMessages(1403);
        }
        KeyguardUpdateMonitor.AnonymousClass16 anonymousClass16 = this.mHandler;
        anonymousClass16.sendMessageAtFrontOfQueue(anonymousClass16.obtainMessage(1403, Integer.valueOf(i)));
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void dispatchDreamingStarted() {
        synchronized (this) {
            this.mIsEarlyWakeUp = false;
            this.mIsDreamingForBiometrics = true;
        }
        super.dispatchDreamingStarted();
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void dispatchDreamingStopped() {
        synchronized (this) {
            this.mIsDreamingForBiometrics = false;
        }
        super.dispatchDreamingStopped();
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void dispatchDualDarInnerLockScreenState(int i, boolean z) {
        this.mIsDualDarInnerAuthShowing = z;
        if (this.mHandler.hasMessages(VolteConstants.ErrorCode.CALL_CANCEL_MODIFY_REQUESTED)) {
            this.mHandler.removeMessages(VolteConstants.ErrorCode.CALL_CANCEL_MODIFY_REQUESTED);
        }
        KeyguardUpdateMonitor.AnonymousClass16 anonymousClass16 = this.mHandler;
        anonymousClass16.sendMessage(anonymousClass16.obtainMessage(VolteConstants.ErrorCode.CALL_CANCEL_MODIFY_REQUESTED, i, z ? 1 : 0));
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void dispatchForceStartFingerprint() {
        Log.d("KeyguardUpdateMonitor", "dispatchForceStartFingerprint");
        if (!isUnlockingWithBiometricAllowed(true)) {
            Log.d("KeyguardUpdateMonitor", "did not start force start fingerprint");
        } else {
            KeyguardUpdateMonitor.AnonymousClass16 anonymousClass16 = this.mHandler;
            anonymousClass16.sendMessage(anonymousClass16.obtainMessage(VolteConstants.ErrorCode.CALL_TEMP_UNAVAILABLE_415_CAUSE));
        }
    }

    public final void dispatchLockModeChanged() {
        if (this.mHandler.hasMessages(VolteConstants.ErrorCode.CALL_SESSION_TIMEOUT)) {
            this.mHandler.removeMessages(VolteConstants.ErrorCode.CALL_SESSION_TIMEOUT);
        }
        this.mHandler.sendEmptyMessage(VolteConstants.ErrorCode.CALL_SESSION_TIMEOUT);
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void dispatchNotiStarState(boolean z) {
        if (this.mHandler.hasMessages(1028)) {
            this.mHandler.removeMessages(1028);
        }
        KeyguardUpdateMonitor.AnonymousClass16 anonymousClass16 = this.mHandler;
        anonymousClass16.sendMessage(anonymousClass16.obtainMessage(1028, Boolean.valueOf(z)));
    }

    public final void dispatchSecureState(int i) {
        if (this.mHandler.hasMessages(VolteConstants.ErrorCode.CALL_SESSION_TERMINATED, Integer.valueOf(i))) {
            this.mHandler.removeMessages(VolteConstants.ErrorCode.CALL_SESSION_TERMINATED, Integer.valueOf(i));
        }
        KeyguardUpdateMonitor.AnonymousClass16 anonymousClass16 = this.mHandler;
        anonymousClass16.sendMessage(anonymousClass16.obtainMessage(VolteConstants.ErrorCode.CALL_SESSION_TERMINATED, Integer.valueOf(i)));
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void dispatchSecurityModeChanged(KeyguardSecurityModel.SecurityMode securityMode) {
        if (this.mCurrentSecurityMode != securityMode) {
            this.mCurrentSecurityMode = securityMode;
            KeyguardUpdateMonitor.AnonymousClass16 anonymousClass16 = this.mHandler;
            anonymousClass16.sendMessage(anonymousClass16.obtainMessage(VolteConstants.ErrorCode.CALL_STATUS_CONF_START_SESSION_FAILURE, securityMode));
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void dispatchStartSubscreenBiometric(Intent intent) {
        Log.d("KeyguardUpdateMonitor", "dispatchStartSubscreenBiometric");
        if (!this.mDeviceInteractive) {
            Log.d("KeyguardUpdateMonitor", "did not start subscreen biometric in non interactive state.");
            return;
        }
        if (!isUnlockingWithBiometricAllowed(true)) {
            Log.d("KeyguardUpdateMonitor", "did not start subscreen biometric in not unlocking allowed state.");
            if (intent != null) {
                intent.putExtra("authSinceBoot", this.mStrongAuthTracker.hasUserAuthenticatedSinceBoot());
                return;
            }
            return;
        }
        this.mNeedSubBioAuth = true;
        if (intent != null) {
            intent.putExtra("finger", shouldListenForFingerprint(false));
            intent.putExtra("face", shouldListenForFace());
        }
        KeyguardUpdateMonitor.AnonymousClass16 anonymousClass16 = this.mHandler;
        anonymousClass16.sendMessage(anonymousClass16.obtainMessage(VolteConstants.ErrorCode.CALL_CANCEL_TRANSFER_FAILED));
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void dispatchStartSubscreenFingerprint() {
        Log.d("KeyguardUpdateMonitor", "dispatchStartSubscreenFingerprint");
        if (!isEnabledWofOnFold()) {
            Log.d("KeyguardUpdateMonitor", "did not start subscreen fingerprint in wof off.");
        } else {
            if (!isUnlockingWithBiometricAllowed(true)) {
                Log.d("KeyguardUpdateMonitor", "did not start subscreen biometric in not unlocking allowed state.");
                return;
            }
            this.mNeedSubWofFpAuth = true;
            KeyguardUpdateMonitor.AnonymousClass16 anonymousClass16 = this.mHandler;
            anonymousClass16.sendMessage(anonymousClass16.obtainMessage(VolteConstants.ErrorCode.CALL_CANCEL_TRANSFER_SUCCESS));
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void dispatchStartedEarlyWakingUp(int i) {
        synchronized (this) {
            this.mIsEarlyWakeUp = true;
            this.mIsDreamingForBiometrics = false;
        }
        KeyguardUpdateMonitor.AnonymousClass16 anonymousClass16 = this.mHandler;
        anonymousClass16.sendMessage(anonymousClass16.obtainMessage(1002, i, 0));
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void dispatchStartedWakingUp(int i) {
        super.dispatchStartedWakingUp(i);
        synchronized (this) {
            try {
                this.mIsEarlyWakeUp = false;
                if (104 != i) {
                    this.mIsScreenSaverRunning = false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void dispatchStatusBarState(boolean z) {
        if (this.mHandler.hasMessages(VolteConstants.ErrorCode.CALL_SWITCH_FAILURE)) {
            this.mHandler.removeMessages(VolteConstants.ErrorCode.CALL_SWITCH_FAILURE);
        }
        KeyguardUpdateMonitor.AnonymousClass16 anonymousClass16 = this.mHandler;
        anonymousClass16.sendMessage(anonymousClass16.obtainMessage(VolteConstants.ErrorCode.CALL_SWITCH_FAILURE, Boolean.valueOf(z)));
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void dispatchStopSubscreenBiometric() {
        Log.d("KeyguardUpdateMonitor", "dispatchStopSubscreenBiometric");
        int wofType = this.mSettingsHelper.getWofType();
        if (!this.mNeedSubBioAuth && (wofType == 1 || wofType == 3)) {
            ListPopupWindow$$ExternalSyntheticOutline0.m(wofType, "did not stop subscreen biometric because already mNeedSubBioAuth is false and subscreen wof is on. wofType = ", "KeyguardUpdateMonitor");
            return;
        }
        this.mNeedSubBioAuth = false;
        this.mNeedSubWofFpAuth = false;
        KeyguardUpdateMonitor.AnonymousClass16 anonymousClass16 = this.mHandler;
        anonymousClass16.sendMessage(anonymousClass16.obtainMessage(VolteConstants.ErrorCode.CALL_CANCEL_TRANSFER_FAILED));
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void dispatchSubScreenBouncerStateChanged(boolean z) {
        if (!z) {
            this.mPrimaryBouncerFullyShown = false;
        }
        StringBuilder sb = new StringBuilder("dispatchSubScreenBouncerStateChanged mKeyguardShowing = ");
        sb.append(this.mKeyguardShowing);
        sb.append(" mKeyguardOccluded = ");
        sb.append(this.mKeyguardOccluded);
        sb.append(" mBouncerFullyShown = ");
        CarrierTextManager$$ExternalSyntheticOutline0.m(sb, this.mPrimaryBouncerFullyShown, " isBouncerShowing = ", z, "KeyguardUpdateMonitor");
        sendKeyguardStateUpdated(this.mKeyguardShowing, this.mKeyguardOccluded, this.mPrimaryBouncerFullyShown || z, false);
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void dumpAllUsers(PrintWriter printWriter) {
        String str;
        printWriter.println("  Current user id=" + ActivityManager.getCurrentUser());
        Iterator it = UserManager.get(this.mContext).getUsers().iterator();
        while (it.hasNext()) {
            int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
            Iterator it2 = it;
            if (isFingerprintSupported()) {
                int strongAuthForUser = this.mStrongAuthTracker.getStrongAuthForUser(identifier);
                KeyguardUpdateMonitor.BiometricAuthenticated biometricAuthenticated = this.mUserFingerprintAuthenticated.get(identifier);
                printWriter.println("  Fingerprint state (user=" + identifier + ")");
                StringBuilder sb = new StringBuilder("    isFingerprintClass3=");
                sb.append(isFingerprintClass3());
                printWriter.println(sb.toString());
                StringBuilder sbM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("    areAllFpAuthenticatorsRegistered="), this.mAuthController.mAllFingerprintAuthenticatorsRegistered, printWriter, "    allowed="), biometricAuthenticated != null && isUnlockingWithBiometricAllowed(biometricAuthenticated.mIsStrongBiometric), printWriter, "    auth'd="), biometricAuthenticated != null && biometricAuthenticated.mAuthenticated, printWriter, "    authSinceBoot=");
                sbM.append(this.mStrongAuthTracker.hasUserAuthenticatedSinceBoot());
                printWriter.println(sbM.toString());
                printWriter.println("    disabled(DPM)=" + isFingerprintDisabled(identifier));
                printWriter.println("    possible=" + isUnlockWithFingerprintPossible(identifier));
                StringBuilder sbM2 = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "    listening: actual=" + this.mFingerprintRunningState + " expected=" + (shouldListenForFingerprint(false) ? 1 : 0), "    strongAuthFlags=");
                sbM2.append(Integer.toHexString(strongAuthForUser));
                printWriter.println(sbM2.toString());
                printWriter.println("    trustManaged=" + getUserTrustIsManaged(identifier));
                StringBuilder sbM3 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("    mFingerprintLockedOut="), this.mFingerprintLockedOut, printWriter, "    mFingerprintLockedOutPermanent="), this.mFingerprintLockedOutPermanent, printWriter, "    enabledByUser=");
                sbM3.append(this.mBiometricEnabledForUser.get(identifier));
                printWriter.println(sbM3.toString());
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("    mKeyguardOccluded="), this.mKeyguardOccluded, printWriter, "    mIsDreaming="), this.mIsDreaming, printWriter);
                List list = this.mAuthController.mSidefpsProps;
                if (list == null || list.isEmpty()) {
                    str = "    enabledByUser=";
                } else {
                    StringBuilder sb2 = new StringBuilder("        sfpsEnrolled=");
                    AuthController authController = this.mAuthController;
                    str = "    enabledByUser=";
                    StringBuilder sbM4 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb2, authController.mSidefpsProps == null ? false : authController.mSfpsEnrolledForUser.get(this.mSelectedUserInteractor.getSelectedUserId()), printWriter, "        shouldListenForSfps=");
                    sbM4.append(shouldListenForFingerprint(false));
                    printWriter.println(sbM4.toString());
                }
                new DumpsysTableLogger("KeyguardFingerprintListen", KeyguardFingerprintListenModel.TABLE_HEADERS, this.mFingerprintListenBuffer.toList()).printTableData(printWriter);
            } else {
                str = "    enabledByUser=";
                if (this.mFpm != null && this.mFingerprintSensorProperties.isEmpty()) {
                    printWriter.println("  Fingerprint state (user=" + identifier + ")");
                    StringBuilder sb3 = new StringBuilder("    mFingerprintSensorProperties.isEmpty=");
                    sb3.append(this.mFingerprintSensorProperties.isEmpty());
                    printWriter.println(sb3.toString());
                    printWriter.println("    mFpm.isHardwareDetected=" + this.mFpm.isHardwareDetected());
                    new DumpsysTableLogger("KeyguardFingerprintListen", KeyguardFingerprintListenModel.TABLE_HEADERS, this.mFingerprintListenBuffer.toList()).printTableData(printWriter);
                }
            }
            if (isFaceSupported()) {
                int strongAuthForUser2 = this.mStrongAuthTracker.getStrongAuthForUser(identifier);
                KeyguardUpdateMonitor.BiometricAuthenticated biometricAuthenticated2 = this.mUserFaceAuthenticated.get(identifier);
                printWriter.println("  Face authentication state (user=" + identifier + ")");
                StringBuilder sb4 = new StringBuilder("    isFaceClass3=");
                sb4.append(isFaceClass3());
                printWriter.println(sb4.toString());
                StringBuilder sbM5 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("    allowed="), biometricAuthenticated2 != null && isUnlockingWithBiometricAllowed(biometricAuthenticated2.mIsStrongBiometric), printWriter, "    auth'd="), biometricAuthenticated2 != null && biometricAuthenticated2.mAuthenticated, printWriter, "    authSinceBoot=");
                sbM5.append(this.mStrongAuthTracker.hasUserAuthenticatedSinceBoot());
                printWriter.println(sbM5.toString());
                printWriter.println("    disabled(DPM)=" + isFaceDisabled(identifier));
                printWriter.println("    possible=" + isUnlockWithFacePossible(identifier));
                printWriter.println("    listening: actual=" + this.mFaceRunningState + " expected=(" + (shouldListenForFace() ? 1 : 0));
                StringBuilder sb5 = new StringBuilder("    strongAuthFlags=");
                sb5.append(Integer.toHexString(strongAuthForUser2));
                printWriter.println(sb5.toString());
                printWriter.println("    isNonStrongBiometricAllowedAfterIdleTimeout=" + this.mStrongAuthTracker.isNonStrongBiometricAllowedAfterIdleTimeout(identifier));
                printWriter.println("    trustManaged=" + getUserTrustIsManaged(identifier));
                StringBuilder sbM6 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("    mFaceLockedOutPermanent="), this.mFaceLockedOutPermanent, printWriter, str);
                sbM6.append(this.mBiometricEnabledForUser.get(identifier));
                printWriter.println(sbM6.toString());
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("    mSecureCameraLaunched="), this.mSecureCameraLaunched, printWriter, "    mPrimaryBouncerFullyShown="), this.mPrimaryBouncerFullyShown, printWriter, "    mNeedsSlowUnlockTransition="), this.mNeedsSlowUnlockTransition, printWriter);
            } else if (this.mFaceManager != null && this.mFaceSensorProperties.isEmpty()) {
                printWriter.println("  Face state (user=" + identifier + ")");
                StringBuilder sb6 = new StringBuilder("    mFaceSensorProperties.isEmpty=");
                sb6.append(this.mFaceSensorProperties.isEmpty());
                printWriter.println(sb6.toString());
                printWriter.println("    mFaceManager.isHardwareDetected=" + this.mFaceManager.isHardwareDetected());
                new DumpsysTableLogger("KeyguardFaceListen", KeyguardFingerprintListenModel.TABLE_HEADERS, this.mFingerprintListenBuffer.toList()).printTableData(printWriter);
            }
            printWriter.println("ActiveUnlockRunning=" + this.mTrustManager.isActiveUnlockRunning(this.mSelectedUserInteractor.getSelectedUserId()));
            new DumpsysTableLogger("KeyguardActiveUnlockTriggers", KeyguardActiveUnlockModel.TABLE_HEADERS, SequencesKt___SequencesKt.toList(new TransformingSequence(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(this.mActiveUnlockTriggerBuffer.buffer), new KeyguardActiveUnlockModel$Buffer$$ExternalSyntheticLambda0()))).printTableData(printWriter);
            printWriter.println("  Security state (user=" + identifier + ") - cached state");
            printWriter.println("    getBiometricType=" + this.mLockPatternUtils.getBiometricType(identifier) + " - " + this.mBiometricType.get(identifier));
            StringBuilder sb7 = new StringBuilder("    isFingerprintOptionEnabled=");
            sb7.append(this.mLockPatternUtils.getBiometricState(1, identifier) == 1);
            sb7.append(" - ");
            sb7.append(this.mBiometricsFingerprint.get(identifier));
            printWriter.println(sb7.toString());
            StringBuilder sb8 = new StringBuilder("    isFaceOptionEnabled=");
            sb8.append(this.mLockPatternUtils.getBiometricState(256, identifier) == 1);
            sb8.append(" - ");
            sb8.append(this.mBiometricsFace.get(identifier));
            printWriter.println(sb8.toString());
            printWriter.println("    getFailedUnlockAttempt=" + this.mLockPatternUtils.getCurrentFailedPasswordAttempts(identifier));
            StringBuilder sb9 = new StringBuilder("    mCredentialType=");
            sb9.append(this.mLockPatternUtils.getCredentialTypeForUser(identifier));
            sb9.append(" - ");
            StringBuilder sbM7 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb9, this.mCredentialType, printWriter, "    mPrevCredentialType=");
            sbM7.append(this.mLockPatternUtils.getCredentialTypeForUser(-9899));
            printWriter.println(sbM7.toString());
            StringBuilder sbM8 = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "    isSecure=" + this.mLockPatternUtils.isSecure(identifier) + " - " + isSecure(), "    isAutoPinConfirmEnabled=");
            sbM8.append(this.mLockPatternUtils.isAutoPinConfirmEnabled(identifier));
            printWriter.println(sbM8.toString());
            StringBuilder sb10 = new StringBuilder("    mDisableCamera=");
            sb10.append(this.mDevicePolicyManager.getCameraDisabled(null));
            sb10.append(" - ");
            StringBuilder sbM9 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb10, this.mDisableCamera, printWriter, "    mMaximumFailedPasswordsForWipe="), this.mMaximumFailedPasswordsForWipe, printWriter, "    getUserCanSkipBouncer=");
            sbM9.append(getUserCanSkipBouncer(identifier));
            printWriter.println(sbM9.toString());
            printWriter.println("    getUserHasTrust=" + getUserHasTrust(identifier));
            printWriter.println("    isUserUnlocked=" + this.mUserManager.isUserUnlocked(identifier));
            if (identifier != 0) {
                printWriter.println("    hasSeparateChallenge=" + this.mLockPatternUtils.isSeparateProfileChallengeEnabled(identifier));
            }
            it = it2;
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void enableSecurityDebug() {
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(3, "KeyguardUpdateMonitor", new StringBuilder("enableSecurityDebug by "));
        this.mLockPatternUtils.setSecurityDebugLevel(1);
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final int getBiometricType() {
        return this.mBiometricType.get(this.mSelectedUserInteractor.getSelectedUserId());
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final CoverState getCoverState() {
        CoverState coverState;
        synchronized (this) {
            coverState = this.mCoverState;
        }
        return coverState;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final int getCredentialTypeForUser(int i) {
        return i == ((UserTrackerImpl) this.mUserTracker).getUserId() ? this.mCredentialType : this.mLockPatternUtils.getCredentialTypeForUser(i);
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final KeyguardSecurityModel.SecurityMode getCurrentSecurityMode() {
        return this.mCurrentSecurityMode;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final String getDeviceOwnerInfo() {
        return this.mDeviceOwnerInfoText;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final KeyguardConstants$KeyguardDismissActionType getDismissActionType() {
        return this.mKeyguardDismissActionType;
    }

    public final long getDualDarInnerLockScreenTimeOut(int i) {
        if (Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class) == null || !((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isDualDarDeviceOwner(i) || !((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isDualDarInnerAuthRequired(i)) {
            return 0L;
        }
        return this.mLockPatternUtils.getLockoutAttemptDeadline(((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).getInnerAuthUserId(i));
    }

    public final boolean getFaceAuthenticated(int i) {
        KeyguardUpdateMonitor.BiometricAuthenticated biometricAuthenticated = this.mUserFaceAuthenticated.get(i);
        return biometricAuthenticated != null && biometricAuthenticated.mAuthenticated;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean getFaceStrongBiometric() {
        KeyguardUpdateMonitor.BiometricAuthenticated biometricAuthenticated = this.mUserFaceAuthenticated.get(((UserTrackerImpl) this.mUserTracker).getUserId());
        return biometricAuthenticated != null && biometricAuthenticated.mIsStrongBiometric;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final int getFailedBiometricUnlockAttempts() {
        return this.mBiometricFailedAttempts.get(this.mSelectedUserInteractor.getSelectedUserId(), 0);
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final int getFailedFMMUnlockAttempt(int i) {
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        if (lockPatternUtils != null) {
            return (int) lockPatternUtils.getFailedFMMUnlockAttempt(i);
        }
        return 0;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final int getFailedUnlockAttempts(int i) {
        return this.mLockPatternUtils.getCurrentFailedPasswordAttempts(i);
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final KeyguardFastBioUnlockController getFastBioUnlockController() {
        return this.mFastUnlockController;
    }

    public final boolean getFingerprintAuthenticated(int i) {
        KeyguardUpdateMonitor.BiometricAuthenticated biometricAuthenticated = this.mUserFingerprintAuthenticated.get(i);
        return biometricAuthenticated != null && biometricAuthenticated.mAuthenticated;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final Message getKeyguardBatteryMessage(Intent intent) {
        int intExtra = intent.getIntExtra(IMSParameter.CALL.STATUS, 1);
        int intExtra2 = intent.getIntExtra("plugged", 0);
        int intExtra3 = intent.getIntExtra(ActionResults.RESULT_SET_VOLUME_SUCCESS, 0);
        int intExtra4 = intent.getIntExtra("android.os.extra.CHARGING_STATUS", 1);
        int intExtra5 = intent.getIntExtra("max_charging_current", -1);
        int intExtra6 = intent.getIntExtra("charge_type", 0);
        int intExtra7 = intent.getIntExtra("max_charging_voltage", -1);
        int intExtra8 = intent.getIntExtra("online", 0);
        boolean booleanExtra = intent.getBooleanExtra("hv_charger", false);
        int intExtra9 = intent.getIntExtra("current_event", 0);
        int intExtra10 = intent.getIntExtra("charger_type", 0);
        boolean z = (intent.getIntExtra("misc_event", 0) & 16777216) != 0;
        if (intExtra7 <= 0) {
            intExtra7 = 5000000;
        }
        return this.mHandler.obtainMessage(302, new KeyguardBatteryStatus(intExtra, intExtra3, intExtra2, intExtra4, intExtra5 > 0 ? (intExtra7 / 1000) * (intExtra5 / 1000) : -1, intExtra8, booleanExtra, intExtra9, intExtra10, z, intExtra6));
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final KeyguardBatteryStatus getKeyguardBatteryStatus() {
        return this.mKeyguardBatteryStatus;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final long getLockoutAttemptDeadline() {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (this.mLockoutAttemptDeadline < getDualDarInnerLockScreenTimeOut(((UserTrackerImpl) this.mUserTracker).getUserId())) {
            return getDualDarInnerLockScreenTimeOut(((UserTrackerImpl) this.mUserTracker).getUserId());
        }
        long j = this.mLockoutAttemptDeadline;
        return (j >= jElapsedRealtime || this.mLockoutAttemptTimeout == 0) ? j > jElapsedRealtime + this.mLockoutAttemptTimeout ? this.mLockPatternUtils.getLockoutAttemptDeadline(((UserTrackerImpl) this.mUserTracker).getUserId()) : j : this.mLockPatternUtils.getLockoutAttemptDeadline(((UserTrackerImpl) this.mUserTracker).getUserId());
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final long getLockoutBiometricAttemptDeadline() {
        if (!this.mStrongAuthTracker.hasUserAuthenticatedSinceBoot()) {
            return 0L;
        }
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long biometricAttemptDeadline = this.mLockoutBiometricAttemptDeadline;
        if ((biometricAttemptDeadline < jElapsedRealtime && this.mLockoutBiometricAttemptTimeout != 0) || biometricAttemptDeadline > jElapsedRealtime + this.mLockoutBiometricAttemptTimeout) {
            biometricAttemptDeadline = this.mLockPatternUtils.getBiometricAttemptDeadline(((UserTrackerImpl) this.mUserTracker).getUserId());
        }
        if (biometricAttemptDeadline <= 0 || this.mHasLoggedOnceAuditlog) {
            if (biometricAttemptDeadline <= 0 && this.mHasLoggedOnceAuditlog) {
                this.mHasLoggedOnceAuditlog = false;
            }
            return biometricAttemptDeadline;
        }
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        int failedBiometricUnlockAttempts = getFailedBiometricUnlockAttempts(userId);
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            AuditLog.logEventAsUser(userId, 375, new Object[]{Integer.valueOf(failedBiometricUnlockAttempts)});
            this.mHasLoggedOnceAuditlog = true;
            return biometricAttemptDeadline;
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final int getMaxFailedUnlockAttempts() {
        if (Build.IS_USERDEBUG || Build.IS_ENG) {
            return SystemProperties.getInt("persist.lock.max_attempts", 50);
        }
        return 50;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final String getOwnerInfo() {
        return this.mOwnerInfoText;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final int getPrevCredentialType() {
        return this.mLockPatternUtils.getCredentialTypeForUser(-9899);
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final int getRemainingAttempt(int i) {
        if (isAutoWipe()) {
            int currentFailedPasswordAttempts = this.mLockPatternUtils.getCurrentFailedPasswordAttempts(((UserTrackerImpl) this.mUserTracker).getUserId());
            int currentFailedPasswordAttempts2 = this.mLockPatternUtils.getCurrentFailedPasswordAttempts(((UserTrackerImpl) this.mUserTracker).getUserId());
            boolean zIsAutoWipe = isAutoWipe();
            int i2 = this.mMaximumFailedPasswordsForWipe;
            if (i2 <= 0) {
                i2 = zIsAutoWipe ? 20 : 0;
            }
            int i3 = i2 > 0 ? i2 - currentFailedPasswordAttempts2 : -1;
            RecyclerView$$ExternalSyntheticOutline0.m(i3, "KeyguardUpdateMonitor", MutableObjectList$$ExternalSyntheticOutline0.m(i, currentFailedPasswordAttempts, "getRemainingAttempt type : ", ", failedAttempts : ", ", remainingAttempts : "));
            if (i == 0 ? i3 <= 10 : !(i == 1 ? i3 >= 10 : i != 2 || currentFailedPasswordAttempts < 6 || currentFailedPasswordAttempts > 9)) {
                return i3;
            }
        }
        return 0;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final int getRemainingAttemptsBeforePermanentLock() {
        return getMaxFailedUnlockAttempts() - this.mLockPatternUtils.getCurrentFailedPasswordAttempts(((UserTrackerImpl) this.mUserTracker).getUserId());
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final RemoteLockInfo getRemoteLockInfo() {
        int i = this.mActiveRemoteLockIndex;
        if (i >= 0) {
            return (RemoteLockInfo) this.mRemoteLockInfo.get(i);
        }
        return null;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final int getRemoteLockType() {
        int i = this.mActiveRemoteLockIndex;
        if (i >= 0) {
            return ((RemoteLockInfo) this.mRemoteLockInfo.get(i)).lockType;
        }
        return -1;
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final boolean getUserCanSkipBouncer(int i) {
        if (isForcedLock()) {
            return false;
        }
        return getUserHasTrust(i) || (getUserUnlockedWithBiometric(i) && !is2StepVerification()) || (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mCustomSdkMonitor.mKnoxCustomLockScreenOverrideMode & 1) != 0 || (LsRune.SECURITY_CONTINUITY_LOCKSCREEN_VI && forceIsDismissibleIsKeepingDeviceUnlocked());
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final boolean getUserUnlockedWithBiometricAndIsBypassing(int i) {
        return getUserUnlockedWithBiometric(i) && this.mKeyguardBypassController.canBypass();
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0086  */
    @Override // com.android.keyguard.KeyguardUpdateMonitor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleBatteryUpdate(BatteryStatus batteryStatus) {
        int i;
        if (!(batteryStatus instanceof KeyguardBatteryStatus)) {
            super.handleBatteryUpdate(batteryStatus);
            return;
        }
        Assert.isMainThread();
        Log.d("KeyguardUpdateMonitor", "handleBatteryUpdate");
        KeyguardBatteryStatus keyguardBatteryStatus = (KeyguardBatteryStatus) batteryStatus;
        try {
            if (this.mBatteryInfo != null && (keyguardBatteryStatus.isPluggedIn() || keyguardBatteryStatus.isPluggedInWired())) {
                keyguardBatteryStatus.remaining = this.mBatteryInfo.computeChargeTimeRemaining();
            }
        } catch (RemoteException unused) {
        }
        KeyguardBatteryStatus keyguardBatteryStatus2 = this.mKeyguardBatteryStatus;
        boolean zIsPluggedIn = keyguardBatteryStatus.isPluggedIn();
        boolean zIsPluggedIn2 = keyguardBatteryStatus2.isPluggedIn();
        boolean z = false;
        boolean z2 = zIsPluggedIn2 && zIsPluggedIn && keyguardBatteryStatus2.status != keyguardBatteryStatus.status;
        if (zIsPluggedIn2 != zIsPluggedIn || z2 || keyguardBatteryStatus2.level != keyguardBatteryStatus.level || ((zIsPluggedIn && keyguardBatteryStatus2.highVoltage != keyguardBatteryStatus.highVoltage) || ((zIsPluggedIn && keyguardBatteryStatus2.online != keyguardBatteryStatus.online) || ((zIsPluggedIn && (i = keyguardBatteryStatus.swellingMode) > 0 && keyguardBatteryStatus2.swellingMode != i) || (zIsPluggedIn && keyguardBatteryStatus2.chargingStatus != keyguardBatteryStatus.chargingStatus))))) {
            z = true;
        } else if (zIsPluggedIn) {
            long j = keyguardBatteryStatus.remaining;
            if (j <= 0 || keyguardBatteryStatus2.remaining == j) {
                if ((zIsPluggedIn && keyguardBatteryStatus2.mSuperFastCharger != keyguardBatteryStatus.mSuperFastCharger) || ((zIsPluggedIn && keyguardBatteryStatus2.mSlowCharger != keyguardBatteryStatus.mSlowCharger) || (zIsPluggedIn && keyguardBatteryStatus.maxChargingWattage != keyguardBatteryStatus2.maxChargingWattage))) {
                }
            }
        }
        this.mKeyguardBatteryStatus = keyguardBatteryStatus;
        if (z) {
            dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda53(batteryStatus, 2), null);
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void handleDevicePolicyManagerStateChanged(int i) {
        super.handleDevicePolicyManagerStateChanged(i);
        DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
        if (devicePolicyManager != null) {
            this.mDisableCamera = devicePolicyManager.getCameraDisabled(null, i);
            this.mMaximumFailedPasswordsForWipe = this.mDevicePolicyManager.getMaximumFailedPasswordsForWipe(null, i);
        }
        updateDualDARInnerLockscreenRequirement(i);
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void handleDreamingStateChanged(int i) {
        boolean z = i == 1;
        if (this.mIsDreaming != z) {
            CarrierTextManager$$ExternalSyntheticOutline0.m(new StringBuilder("handleDreamingStateChanged() dreaming : "), this.mIsDreaming, " -> ", z, "KeyguardUpdateMonitor");
            this.mIsDreaming = z;
            setScreenSaverRunningState();
            dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda12(this, 5), null);
            updateFingerprintListeningState(2);
            if (this.mIsDreaming) {
                updateFaceListeningState(1, FaceAuthUiEvent.FACE_AUTH_STOPPED_DREAM_STARTED);
            } else if (this.mIsStartFacePossible) {
                updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_STOPPED_DREAM_STARTED);
            }
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void handleFaceAuthFailed() {
        Assert.isMainThread();
        reportFailedBiometricUnlockAttempt(((UserTrackerImpl) this.mUserTracker).getUserId());
        stopListeningForFace(FaceAuthUiEvent.FACE_AUTH_STOPPED_FACE_FAILED);
        dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15(4), null);
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void handleFaceAuthenticated(int i, boolean z) {
        if (this.mSettingsHelper.isEnabledFaceStayOnLock()) {
            this.mBiometricFailedAttempts.delete(((UserTrackerImpl) this.mUserTracker).getUserId());
        } else {
            String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
        }
        if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && this.mFingerprintRunningState == 1) {
            Log.d("KeyguardFingerprint", "Face onAuthenticationSucceeded. FP will be stop!");
            stopListeningForFingerprint();
        }
        dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda40(0, z, this), "onBiometricAuthenticated");
        setFaceRunningState(0);
        super.mBackgroundExecutor.execute(new KeyguardUpdateMonitor$$ExternalSyntheticLambda6(this, z, i));
        if (LsRune.SECURITY_SUB_DISPLAY_COVER) {
            this.mNeedSubBioAuth = false;
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void handleFaceError(int i, String str) {
        Assert.isMainThread();
        if (!isFaceDetectionRunning()) {
            Log.d("KeyguardFace", "onAuthenticationError(), Face is not running");
            return;
        }
        if (i == 5) {
            Log.d("KeyguardFace", "onAuthenticationError(), FACE_ERROR_CANCELED ignore");
            return;
        }
        if (i == 10003 && this.mSecureCameraLaunched) {
            Log.d("KeyguardFace", "onAuthenticationError(), FACE_ERROR_CAMERA_FAILURE ignore");
            return;
        }
        KeyguardCarrierViewController$2$$ExternalSyntheticOutline0.m(i, "onAuthenticationError(), errorCode=", ", errString=", str, "KeyguardFace");
        if (str == null) {
            Toast.makeText(this.mContext, this.mContext.getResources().getString(R.string.kg_face_not_working_text) + "\n" + this.mContext.getResources().getString(R.string.kg_face_error_restart_text), 0).show();
            str = "";
        }
        stopListeningForFace(FaceAuthUiEvent.FACE_AUTH_STOPPED_FACE_ERROR);
        if (i == 100001 && !this.mPrimaryBouncerFullyShown) {
            Toast toastMakeText = Toast.makeText(this.mContext, str, 0);
            toastMakeText.setGravity(49, 0, this.mContext.getResources().getDimensionPixelOffset(R.dimen.status_bar_height));
            toastMakeText.show();
        }
        dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda29(i, str, 1), null);
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void handleFingerprintAcquired(int i) {
        Assert.isMainThread();
        Log.d("KeyguardFingerprint", "handleFingerprintAcquired( " + i + "  )");
        if (i == 10002) {
            this.mIsFpLeave = false;
        } else if (i == 10004) {
            this.mIsFpLeave = true;
        }
        Rune.runIf(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda11(this, 5), i != 1004);
        if (isUnlockingWithBiometricAllowed(true) || this.mDisabledBiometricBySecurityDialog) {
            if (i != 0) {
                return;
            }
            dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda16(i, 3), null);
        } else if (i == 10002) {
            dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda16(i, 2), null);
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void handleFingerprintAuthFailed() {
        Assert.isMainThread();
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        if (!isUnlockingWithBiometricAllowed(true)) {
            Log.d("KeyguardFingerprint", "handleFingerprintAuthFailed( unlock is not allowed. )");
            return;
        }
        reportFailedBiometricUnlockAttempt(userId);
        dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15(3), null);
        handleFingerprintHelp(-1, this.mContext.getString(R.string.kg_fingerprint_no_match));
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void handleFingerprintAuthenticated(int i, boolean z) {
        if (LsRune.SECURITY_SUB_DISPLAY_COVER) {
            this.mForceStartFinger = false;
            this.mNeedSubBioAuth = false;
            this.mNeedSubWofFpAuth = false;
        }
        super.handleFingerprintAuthenticated(i, z);
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void handleFingerprintError(int i, String str) {
        NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i, "handleFingerprintError( ", " )", "KeyguardFingerprint");
        if (5000 == i) {
            if (isFingerprintDetectionRunning()) {
                Log.d("KeyguardFingerprint", "handleFingerprintError( restart FingerPrint by FINGERPRINT_ERROR_NEED_TO_RETRY !! )");
                startListeningForFingerprint(false);
                return;
            }
            return;
        }
        if (1004 == i) {
            str = this.mContext.getString(R.string.kg_finger_print_database_error_message);
        } else if (1002 == i || 1003 == i) {
            str = this.mContext.getString(R.string.kg_finger_print_not_responding_error_message);
        } else if (1001 == i) {
            String str2 = LsRune.VALUE_SUB_DISPLAY_POLICY;
            str = this.mContext.getString(R.string.kg_finger_print_sensor_with_recalibration_error_message);
        } else if (1005 == i) {
            str = this.mContext.getString(R.string.kg_finger_print_sensor_changed_error_message);
        }
        this.mPowerManager.userActivity(SystemClock.uptimeMillis(), 2, 0);
        if (LsRune.SECURITY_BACKGROUND_AUTHENTICATION && i == 5 && this.mKeyguardOccluded && this.mFingerprintRunningState == 1) {
            Log.d("KeyguardFingerprint", "mIsFPCanceledByForegroundApp true");
            this.mIsFPCanceledByForegroundApp = true;
            setFingerprintRunningState(0);
        }
        super.handleFingerprintError(i, str);
    }

    /* JADX WARN: Removed duplicated region for block: B:62:0x01fa  */
    /* JADX WARN: Removed duplicated region for block: B:67:? A[RETURN, SYNTHETIC] */
    @Override // com.android.keyguard.KeyguardUpdateMonitor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleFingerprintHelp(int i, String str) {
        AODToast.Builder builder;
        long j;
        String strM;
        PluginAOD pluginAOD;
        Assert.isMainThread();
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        if (i != -1 && !isUnlockingWithBiometricAllowed(true)) {
            Log.d("KeyguardFingerprint", "handleFingerprintHelp( unlock is not allowed. )");
            stopListeningForFingerprint();
            return;
        }
        if (!this.mDeviceInteractive || this.mIsDreamingForBiometrics) {
            if (i == 1004 || i == 1005) {
                Log.d("KeyguardFingerprint", "handleFingerprintHelp( skip TSP block/unblock )");
                return;
            }
            if (i != -1) {
                int i2 = this.mFingerPrintBadQualityCounts.get(((UserTrackerImpl) this.mUserTracker).getUserId(), 0) + 1;
                this.mFingerPrintBadQualityCounts.put(((UserTrackerImpl) this.mUserTracker).getUserId(), i2);
                StringBuilder sb = new StringBuilder("handleFingerprintHelp( Update Bad Quality Count = ");
                sb.append(i2);
                ExifInterface$$ExternalSyntheticOutline0.m(sb, " )", "KeyguardFingerprint");
                if (i2 >= 50) {
                    if (this.mSettingsHelper.isFingerprintSensorPopupShowAgain()) {
                        Resources resources = this.mContext.getResources();
                        SystemUIDialog systemUIDialog = new SystemUIDialog(this.mContext, R.style.Theme_SystemUI_POPUPUI);
                        systemUIDialog.setTitle(resources.getString(R.string.kg_fingerprint_bad_quality_popup_title));
                        systemUIDialog.setMessage(resources.getString(R.string.kg_fingerprint_bad_quality_popup_message));
                        systemUIDialog.setPositiveButton(R.string.kg_keycode_ok, new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda61());
                        CheckBox checkBox = new CheckBox(new ContextThemeWrapper(this.mContext, R.style.Theme_SystemUI_Dialog));
                        checkBox.setText(resources.getString(R.string.kg_fingerprint_bad_quality_popup_checkbox));
                        checkBox.setPadding(resources.getDimensionPixelSize(R.dimen.kg_checkbox_text_side_padding), 0, 0, 0);
                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda62
                            @Override // android.widget.CompoundButton.OnCheckedChangeListener
                            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                                KeyguardSecUpdateMonitorImpl.$r8$lambda$5lhNrxzXYmtVuTwfeiQe_uCItoQ(this.f$0, z);
                            }
                        });
                        systemUIDialog.setView(checkBox, resources.getDimensionPixelSize(R.dimen.checkbox_popup_text_margin), 0, resources.getDimensionPixelSize(R.dimen.checkbox_popup_text_margin), 0);
                        systemUIDialog.setCancelable(false);
                        systemUIDialog.show();
                    } else {
                        Log.d("KeyguardFingerprint", "Skip to show fingerprint sensor block popup");
                    }
                    updateFingerprintListeningState(2);
                    setFodStrictMode(false);
                } else if (i2 == 10) {
                    setFodStrictMode(true);
                }
                PluginAOD pluginAOD2 = ((PluginAODManager) this.mPluginAODManagerLazy.get()).mAODPlugin;
                if (pluginAOD2 != null) {
                    pluginAOD2.hideChargingInfoByFinger(0L);
                    return;
                }
                return;
            }
            int failedBiometricUnlockAttempts = getFailedBiometricUnlockAttempts(userId);
            boolean userHasTrust = getUserHasTrust(userId);
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(failedBiometricUnlockAttempts, "handleFingerprintHelp( Failed count when screen off = ", " ) - ");
            sbM.append(this.mCurrentSecurityMode);
            sbM.append(", t = ");
            sbM.append(userHasTrust);
            Log.d("KeyguardFingerprint", sbM.toString());
            if (getFailedBiometricUnlockAttempts(userId) == 3) {
                this.mKeyguardBatteryStatus.getClass();
                String str2 = LsRune.VALUE_SUB_DISPLAY_POLICY;
            }
            int failedBiometricUnlockAttempts2 = getFailedBiometricUnlockAttempts(userId);
            if (failedBiometricUnlockAttempts2 != 0 && failedBiometricUnlockAttempts2 % 5 == 0) {
                KeyguardTextBuilder keyguardTextBuilder = KeyguardTextBuilder.getInstance(this.mContext);
                KeyguardSecurityModel.SecurityMode securityMode = this.mCurrentSecurityMode;
                String strM2 = TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder(), keyguardTextBuilder.mContext.getResources().getQuantityString(R.plurals.kg_secure_attempts_to_unlock_with_fingerprints, failedBiometricUnlockAttempts, Integer.valueOf(failedBiometricUnlockAttempts)), " ");
                if (userHasTrust) {
                    String str3 = LsRune.VALUE_SUB_DISPLAY_POLICY;
                    strM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(keyguardTextBuilder.mContext, R.string.kg_secure_press_power_key, PopulateViewStructure_androidKt$$ExternalSyntheticOutline0.m(strM2));
                } else {
                    int i3 = KeyguardTextBuilder.AnonymousClass2.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[securityMode.ordinal()];
                    strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(strM2, i3 != 1 ? i3 != 2 ? keyguardTextBuilder.mContext.getString(R.string.kg_secure_enter_password_instead) : keyguardTextBuilder.mContext.getString(R.string.kg_secure_draw_pattern_instead) : keyguardTextBuilder.mContext.getString(R.string.kg_secure_enter_pin_instead));
                }
                builder = new AODToast.Builder(strM);
            } else {
                builder = null;
            }
            if (builder != null) {
                j = 10000;
                builder.setDurationInMillis(10000L);
                AODManager.getInstance(this.mContext).requestAODToast(builder.build());
            }
            dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda29(i, str, 0), null);
            pluginAOD = ((PluginAODManager) this.mPluginAODManagerLazy.get()).mAODPlugin;
            if (pluginAOD == null) {
                pluginAOD.hideChargingInfoByFinger(j);
                return;
            }
            return;
        }
        if (i != 1004) {
            this.mPowerManager.userActivity(SystemClock.uptimeMillis(), 2, 0);
        } else if (this.mIsTspFpGuideShown) {
            return;
        } else {
            updateTspFpGuideShown(true);
        }
        j = 0;
        dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda29(i, str, 0), null);
        pluginAOD = ((PluginAODManager) this.mPluginAODManagerLazy.get()).mAODPlugin;
        if (pluginAOD == null) {
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void handleFinishedGoingToSleep(int i) {
        ((UiOffloadThread) Dependency.sDependency.getDependencyInner(UiOffloadThread.class)).execute(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda11(this, 8));
        super.handleFinishedGoingToSleep(i);
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void handleKeyguardReset() {
        super.handleKeyguardReset();
        if (isForcedLock() && isFaceDetectionRunning()) {
            stopListeningForFace(FaceAuthUiEvent.FACE_AUTH_UPDATED_KEYGUARD_RESET);
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void handlePhoneStateChanged(String str) {
        super.handlePhoneStateChanged(str);
        if (TelephonyManager.EXTRA_STATE_IDLE.equals(str)) {
            this.mIsFPCanceledByProximity = false;
        }
        updateFingerprintListeningState(2);
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void handlePrimaryBouncerChanged(int i, int i2) {
        FingerprintManager fingerprintManager;
        FaceManager faceManager;
        Assert.isMainThread();
        boolean z = this.mPrimaryBouncerIsOrWillBeShowing;
        boolean z2 = this.mPrimaryBouncerFullyShown;
        this.mPrimaryBouncerIsOrWillBeShowing = i == 1;
        this.mPrimaryBouncerFullyShown = i2 == 1;
        StringBuilder sb = new StringBuilder("handlePrimaryBouncerChanged primaryBouncerIsOrWillBeShowing=");
        sb.append(this.mPrimaryBouncerIsOrWillBeShowing);
        sb.append(" primaryBouncerFullyShown=");
        ActionBarContextView$$ExternalSyntheticOutline0.m(sb, this.mPrimaryBouncerFullyShown, "KeyguardUpdateMonitor");
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        if (this.mCurrentSecurityMode != KeyguardSecurityModel.SecurityMode.Swipe && isFaceOptionEnabled() && getFaceAuthenticated(userId) && isFaceDetectionRunning()) {
            setFaceRunningState(0);
            setFaceAuthenticated(false);
        }
        if (this.mPrimaryBouncerFullyShown && this.mIsQsFullyExpanded) {
            this.mIsQsFullyExpanded = false;
        }
        if (isUnlockCompleted()) {
            updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_PRIMARY_BOUNCER_SHOWN);
        }
        if (this.mPrimaryBouncerFullyShown && isFaceDetectionRunning() && (faceManager = this.mFaceManager) != null) {
            faceManager.semResetAuthenticationTimeout();
        }
        if (this.mPrimaryBouncerFullyShown) {
            this.mSecureCameraLaunched = false;
        } else {
            this.mCredentialAttempted = false;
        }
        if (z != this.mPrimaryBouncerIsOrWillBeShowing) {
            dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda12(this, 3), null);
        }
        boolean z3 = this.mPrimaryBouncerFullyShown;
        if (z2 != z3) {
            if (z3) {
                requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin.UNLOCK_INTENT, "bouncerFullyShown");
            }
            dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda12(this, 4), null);
        }
        if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && (fingerprintManager = this.mFpm) != null) {
            fingerprintManager.semShowBouncerScreen(this.mPrimaryBouncerFullyShown ? 1 : 0);
        }
        if (!isKeyguardVisible() && !this.mPrimaryBouncerFullyShown) {
            setUnlockingKeyguard(false);
        }
        setShortcutLaunchInProgress(false);
        updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_PRIMARY_BOUNCER_SHOWN);
        if (this.mPrimaryBouncerFullyShown) {
            SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_GO_TO_SECOND_SCREEN, "1");
        }
        ((PluginAODManager) this.mPluginAODManagerLazy.get()).updateAnimateScreenOff();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:129:0x02d4  */
    /* JADX WARN: Removed duplicated region for block: B:388:0x0974 A[Catch: all -> 0x08f8, DONT_GENERATE, TryCatch #0 {all -> 0x08f8, blocks: (B:349:0x08cb, B:354:0x08f4, B:357:0x08fb, B:360:0x090b, B:362:0x0910, B:364:0x0916, B:366:0x091e, B:367:0x0923, B:377:0x0945, B:370:0x0928, B:373:0x0936, B:375:0x093b, B:376:0x0940, B:380:0x094b, B:382:0x0951, B:383:0x095b, B:385:0x096d, B:386:0x0972, B:388:0x0974), top: B:477:0x08cb }] */
    /* JADX WARN: Type inference failed for: r8v34 */
    /* JADX WARN: Type inference failed for: r8v35 */
    /* JADX WARN: Type inference failed for: r8v36 */
    /* JADX WARN: Type inference failed for: r8v37 */
    /* JADX WARN: Type inference failed for: r8v38 */
    /* JADX WARN: Type inference failed for: r8v39 */
    /* JADX WARN: Type inference failed for: r8v40 */
    /* JADX WARN: Type inference failed for: r9v11 */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v6 */
    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void handleSecMessage(Message message) throws NoSuchMethodException, ClassNotFoundException, SecurityException {
        String str;
        int i;
        int iMatch;
        KnoxStateMonitor knoxStateMonitor;
        int i2 = 9;
        final int i3 = 2;
        z = false;
        boolean z = false;
        final int i4 = 1;
        int i5 = message.what;
        if (i5 == 1001) {
            setIsRunningBlackMemo(false);
            return;
        }
        if (i5 == 1002) {
            int i6 = message.arg1;
            Trace.beginSection("KeyguardUpdateMonitor#handleStartedEarlyWakingUp");
            Log.d("KeyguardUpdateMonitor", "handleStartedEarlyWakingUp start " + i6);
            if (this.mIsQsFullyExpanded) {
                this.mIsQsFullyExpanded = false;
            }
            boolean z2 = i6 == 1 || i6 == 3 || i6 == 15 || i6 == 106 || i6 == 6 || i6 == 7 || i6 == 11 || i6 == 12 || i6 == 102 || i6 == 103 || i6 == 112 || i6 == 113;
            this.mIsStartFacePossible = z2;
            if (z2) {
                updateFaceListeningState(0, FaceAuthUiEvent.FACE_AUTH_UPDATED_STARTED_WAKING_UP);
            }
            if (this.mKeyguardShowing && !this.mHandler.hasCallbacks(this.mWaitingFocusRunnable)) {
                this.mHandler.postDelayed(this.mWaitingFocusRunnable, 500L);
            }
            if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && !LsRune.AOD_DISABLE_CLOCK_TRANSITION) {
                sendScreenStatus(true);
            }
            Trace.endSection();
            return;
        }
        if (i5 == 1028) {
            boolean zBooleanValue = ((Boolean) message.obj).booleanValue();
            if (this.mIsNotiStarShown != zBooleanValue) {
                Log.d("KeyguardUpdateMonitor", "handleNotiStarState( prev:" + this.mIsNotiStarShown + "-> next:" + zBooleanValue + " )");
                this.mIsNotiStarShown = zBooleanValue;
                if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                    updateFingerprintListeningState(2);
                }
                updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_NOTI_STAR_STATE_CHANGED);
                return;
            }
            return;
        }
        if (i5 == 1501) {
            dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda53((CoverState) message.obj, 0), null);
            if (isCoverClosed()) {
                if (getIsFaceAuthenticated()) {
                    setFaceAuthenticated(false);
                }
            } else if (!isKeyguardVisible() && !this.mDeviceInteractive) {
                Log.d("KeyguardUpdateMonitor", "handleUpdateCoverState did not call updateBiometricListeningState");
                return;
            }
            updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_COVER_STATE_CHANGED);
            return;
        }
        switch (i5) {
            case VolteConstants.ErrorCode.CALL_SESSION_ABORT /* 1101 */:
                int identifier = Process.myUserHandle().getIdentifier();
                ListPopupWindow$$ExternalSyntheticOutline0.m(identifier, "Process.myUserHandle().getIdentifier() = ", "KeyguardUpdateMonitor");
                if (identifier == 0) {
                    try {
                        if (this.mLockSettingsService == null) {
                            this.mLockSettingsService = ILockSettings.Stub.asInterface(ServiceManager.getService("lock_settings"));
                        }
                        this.mLockSettingsService.setLockModeChangedCallback(this.mSecureLockChangedCallback);
                        Log.d("KeyguardUpdateMonitor", "handleUpdateLockModeCallback, userID = " + identifier);
                        return;
                    } catch (RemoteException e) {
                        Log.d("KeyguardUpdateMonitor", "RemoteException! " + e);
                        return;
                    }
                }
                return;
            case VolteConstants.ErrorCode.CALL_SESSION_TERMINATED /* 1102 */:
                int iIntValue = ((Integer) message.obj).intValue();
                int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
                boolean zUpdateLockscreenDisabled = containsFlag(iIntValue, 512) ? updateLockscreenDisabled(userId) : false;
                boolean zUpdateCredentialType = containsFlag(iIntValue, 2) ? updateCredentialType(userId) : false;
                if (containsFlag(iIntValue, 4)) {
                    zUpdateCredentialType |= updateFMMLock(userId, false);
                }
                if (containsFlag(iIntValue, 8)) {
                    zUpdateCredentialType |= updateCarrierLock(userId);
                }
                if (containsFlag(iIntValue, 16)) {
                    zUpdateCredentialType |= updateBiometricsOptionState(userId);
                }
                if (containsFlag(iIntValue, 32)) {
                    zUpdateCredentialType |= updateSecureLockTimeout(userId);
                }
                if (containsFlag(iIntValue, 64)) {
                    zUpdateCredentialType |= updateBiometricLockTimeout(userId);
                }
                if (containsFlag(iIntValue, 1)) {
                    zUpdateCredentialType = zUpdateCredentialType | updateCredentialType(userId) | updateFMMLock(userId, false) | updateCarrierLock(userId) | updatePermanentLock(userId) | updateBiometricsOptionState(userId);
                    Intent intent = new Intent();
                    intent.setAction("com.samsung.keyguard.CLEAR_LOCK");
                    LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this.mContext);
                    synchronized (localBroadcastManager.mReceivers) {
                        try {
                            String action = intent.getAction();
                            String strResolveTypeIfNeeded = intent.resolveTypeIfNeeded(localBroadcastManager.mAppContext.getContentResolver());
                            Uri data = intent.getData();
                            String scheme = intent.getScheme();
                            Set<String> categories = intent.getCategories();
                            ?? r9 = (intent.getFlags() & 8) != 0;
                            if (r9 != false) {
                                intent.toString();
                            }
                            ArrayList arrayList = (ArrayList) localBroadcastManager.mActions.get(intent.getAction());
                            if (arrayList != null) {
                                if (r9 != false) {
                                    arrayList.toString();
                                }
                                ArrayList arrayList2 = null;
                                for (int i7 = 0; i7 < arrayList.size(); i7++) {
                                    LocalBroadcastManager.ReceiverRecord receiverRecord = (LocalBroadcastManager.ReceiverRecord) arrayList.get(i7);
                                    if (r9 != false) {
                                        Objects.toString(receiverRecord.filter);
                                    }
                                    if (!receiverRecord.broadcasting && (iMatch = receiverRecord.filter.match(action, strResolveTypeIfNeeded, scheme, data, categories, "LocalBroadcastManager")) >= 0) {
                                        if (r9 != false) {
                                            Integer.toHexString(iMatch);
                                        }
                                        if (arrayList2 == null) {
                                            arrayList2 = new ArrayList();
                                        }
                                        arrayList2.add(receiverRecord);
                                        receiverRecord.broadcasting = true;
                                    }
                                }
                                if (arrayList2 != null) {
                                    for (int i8 = 0; i8 < arrayList2.size(); i8++) {
                                        ((LocalBroadcastManager.ReceiverRecord) arrayList2.get(i8)).broadcasting = false;
                                    }
                                    localBroadcastManager.mPendingBroadcasts.add(new LocalBroadcastManager.BroadcastRecord(intent, arrayList2));
                                    if (!localBroadcastManager.mHandler.hasMessages(1)) {
                                        localBroadcastManager.mHandler.sendEmptyMessage(1);
                                    }
                                }
                            }
                        } finally {
                        }
                    }
                }
                boolean zUpdateOwnerInfo = containsFlag(iIntValue, 128) ? updateOwnerInfo(userId) : false;
                if (containsFlag(iIntValue, 256)) {
                    zUpdateOwnerInfo |= updateDeviceOwnerInfo();
                }
                ActionBarContextView$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("handleSecureStateChanged secureState : ", iIntValue, " isSecureStateUpdated : ", zUpdateCredentialType, ", isOwnerInfoStateUpdated : "), zUpdateOwnerInfo, "KeyguardUpdateMonitor");
                if (zUpdateCredentialType) {
                    str = null;
                    dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15(1), null);
                    i = 2;
                    updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_SECURE_STATE_UNLOCK_CHANGED);
                } else {
                    str = null;
                    i = 2;
                }
                if (zUpdateLockscreenDisabled) {
                    dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda12(this, i), str);
                }
                if (zUpdateOwnerInfo) {
                    dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15(0), str);
                    return;
                }
                return;
            case VolteConstants.ErrorCode.CALL_SESSION_TIMEOUT /* 1103 */:
                dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15(1), null);
                return;
            case VolteConstants.ErrorCode.CALL_STATUS_CONF_START_SESSION_FAILURE /* 1104 */:
                KeyguardSecurityModel.SecurityMode securityMode = (KeyguardSecurityModel.SecurityMode) message.obj;
                addAdditionalLog("handleSecurityViewChanged: securityMode " + securityMode);
                if (isForgotPasswordView()) {
                    updateBiometricListeningState(1, FaceAuthUiEvent.FACE_AUTH_STOPPED_PREV_CREDENTIAL_VIEW);
                }
                dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda53(securityMode, 1), null);
                return;
            case VolteConstants.ErrorCode.CALL_STATUS_CONF_ADD_USER_TO_SESSION_FAILURE /* 1105 */:
                callbacksRefreshCarrierInfo(null);
                return;
            case VolteConstants.ErrorCode.CALL_STATUS_CONF_REMOVE_USER_FROM_SESSION_FAILURE /* 1106 */:
                dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15(8), null);
                return;
            default:
                switch (i5) {
                    case VolteConstants.ErrorCode.CALL_REJECT_REASON_USR_BUSY_CS_CALL /* 1108 */:
                        if (isFaceDetectionRunning()) {
                            stopListeningForFace(FaceAuthUiEvent.FACE_AUTH_STOPPED_USER_INPUT_ON_BOUNCER);
                        }
                        dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15(7), null);
                        return;
                    case VolteConstants.ErrorCode.CALL_SWITCH_FAILURE /* 1109 */:
                        boolean zBooleanValue2 = ((Boolean) message.obj).booleanValue();
                        if (this.mIsQsFullyExpanded != zBooleanValue2) {
                            Log.d("KeyguardUpdateMonitor", "handleStatusBarState( prev:" + this.mIsQsFullyExpanded + "-> next:" + zBooleanValue2 + " )");
                            this.mIsQsFullyExpanded = zBooleanValue2;
                            if (isUnlockCompleted()) {
                                if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                                    updateFingerprintListeningState(2);
                                }
                                updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_QS_FULLY_EXPANDED);
                                return;
                            }
                            return;
                        }
                        return;
                    case VolteConstants.ErrorCode.CALL_SWITCH_REJECTED /* 1110 */:
                        SecFpMsg secFpMsg = (SecFpMsg) message.obj;
                        int iStartTime = LogUtil.startTime(-1);
                        int i9 = 0;
                        while (true) {
                            SecFpMsg secFpMsg2 = (SecFpMsg) this.mFpMessages.poll();
                            if (secFpMsg2 == null) {
                                if (this.mFpMessages.size() > 0 && !this.mHandler.hasMessages(VolteConstants.ErrorCode.CALL_SWITCH_REJECTED)) {
                                    Log.d("KeyguardFingerprint", "remained message size : " + this.mFpMessages.size());
                                    this.mHandler.sendEmptyMessage(VolteConstants.ErrorCode.CALL_SWITCH_REJECTED);
                                }
                                LogUtil.endTime(iStartTime, "KeyguardFingerprint", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i9, "handleFingerprintAuth dispatchCount = "), new Object[0]);
                                return;
                            }
                            if (secFpMsg2 == secFpMsg) {
                                RecyclerView$$ExternalSyntheticOutline0.m(secFpMsg.type, "KeyguardFingerprint", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i9, "handleFingerprintAuth fpMsg index = ", " / type = "));
                            }
                            switch (secFpMsg2.type) {
                                case 0:
                                    int i10 = secFpMsg2.sequence;
                                    int i11 = secFpMsg2.arg;
                                    CharSequence charSequence = secFpMsg2.msgString;
                                    if (this.mFingerprintAuthenticationSequence != i10) {
                                        EmergencyButtonController$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i10, "onAuthenticationError() - return, sequence error (", "/"), this.mFingerprintAuthenticationSequence, ")", "KeyguardFingerprint");
                                    } else if (isTimerRunning()) {
                                        Log.d("KeyguardFingerprint", "onAuthenticationError() - return, isTimerRunning is true");
                                    } else {
                                        Log.d("KeyguardFingerprint", "onAuthenticationError()");
                                        handleFingerprintError(i11, charSequence.toString());
                                    }
                                    i9++;
                                    break;
                                case 1:
                                    int i12 = secFpMsg2.sequence;
                                    int i13 = secFpMsg2.arg;
                                    CharSequence charSequence2 = secFpMsg2.msgString;
                                    if (this.mFingerprintAuthenticationSequence != i12) {
                                        EmergencyButtonController$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i12, "onAuthenticationHelp() - return, sequence error (", "/"), this.mFingerprintAuthenticationSequence, ")", "KeyguardFingerprint");
                                    } else if (isTimerRunning()) {
                                        Log.d("KeyguardFingerprint", "onAuthenticationHelp() - return, isTimerRunning is true");
                                    } else {
                                        Log.d("KeyguardFingerprint", "onAuthenticationHelp( helpMsgId = " + i13 + " , helpString = " + ((Object) charSequence2) + " )");
                                        handleFingerprintHelp(i13, charSequence2.toString());
                                    }
                                    i9++;
                                    break;
                                case 2:
                                    int i14 = secFpMsg2.sequence;
                                    FingerprintManager.AuthenticationResult authenticationResult = secFpMsg2.result;
                                    if (this.mFingerprintAuthenticationSequence != i14) {
                                        EmergencyButtonController$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i14, "onAuthenticationSucceeded() - return, sequence error (", "/"), this.mFingerprintAuthenticationSequence, ")", "KeyguardFingerprint");
                                    } else if (isTimerRunning()) {
                                        Log.d("KeyguardFingerprint", "onAuthenticationSucceeded() - return, isTimerRunning is true");
                                    } else if (this.mKeyguardGoingAway) {
                                        com.android.systemui.keyguard.Log.d("KeyguardFingerprint", "onAuthenticationSucceeded() - return, goingAway is true");
                                    } else {
                                        boolean zIsUnlockingWithBiometricAllowed = isUnlockingWithBiometricAllowed(authenticationResult.isStrongBiometric());
                                        KeyguardFastBioUnlockController keyguardFastBioUnlockController = this.mFastUnlockController;
                                        Objects.requireNonNull(keyguardFastBioUnlockController);
                                        Rune.runIf(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda11(keyguardFastBioUnlockController, i2), zIsUnlockingWithBiometricAllowed);
                                        Trace.beginSection("KeyguardUpdateMonitor#onAuthenticationSucceeded");
                                        com.android.systemui.keyguard.Log.d("KeyguardFingerprint", "onAuthenticationSucceeded()");
                                        int biometricId = authenticationResult.getFingerprint().getBiometricId();
                                        this.mBiometricId = biometricId;
                                        Log.d("KeyguardFingerprint", "Fingerprint id : " + biometricId);
                                        handleFingerprintAuthenticated(authenticationResult.getUserId(), authenticationResult.isStrongBiometric());
                                        this.mFastUnlockController.logLapTime("onFpAuthenticationSucceeded end", new Object[0]);
                                        Trace.endSection();
                                    }
                                    i9++;
                                    break;
                                case 3:
                                    int i15 = secFpMsg2.sequence;
                                    if (this.mFingerprintAuthenticationSequence != i15) {
                                        EmergencyButtonController$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i15, "onAuthenticationFailed() - return, sequence error (", "/"), this.mFingerprintAuthenticationSequence, ")", "KeyguardFingerprint");
                                    } else if (isTimerRunning()) {
                                        Log.d("KeyguardFingerprint", "onAuthenticationFailed() - return, isTimerRunning is true");
                                    } else {
                                        Log.d("KeyguardFingerprint", "onAuthenticationFailed()");
                                        requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin.BIOMETRIC_FAIL, "fingerprintFailure");
                                        handleFingerprintAuthFailed();
                                    }
                                    i9++;
                                    break;
                                case 4:
                                    int i16 = secFpMsg2.sequence;
                                    int i17 = secFpMsg2.arg;
                                    if (this.mFingerprintAuthenticationSequence != i16) {
                                        EmergencyButtonController$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i16, "onAuthenticationAcquired() - return, sequence error (", "/"), this.mFingerprintAuthenticationSequence, ")", "KeyguardFingerprint");
                                    } else {
                                        NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i17, "onAuthenticationAcquired( ", "  )", "KeyguardFingerprint");
                                        handleFingerprintAcquired(i17);
                                    }
                                    i9++;
                                    break;
                                case 5:
                                    this.mUdfpsFingerDown = true;
                                    int i18 = secFpMsg2.sequence;
                                    if (this.mFingerprintAuthenticationSequence != i18) {
                                        EmergencyButtonController$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i18, "onUdfpsFingerDown() - return, sequence error (", "/"), this.mFingerprintAuthenticationSequence, ")", "KeyguardFingerprint");
                                    } else {
                                        Log.d("KeyguardFingerprint", "onUdfpsFingerDown()");
                                        dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15(10), null);
                                    }
                                    i9++;
                                    break;
                                case 6:
                                    this.mUdfpsFingerDown = false;
                                    int i19 = secFpMsg2.sequence;
                                    if (this.mFingerprintAuthenticationSequence != i19) {
                                        EmergencyButtonController$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i19, "onUdfpsFingerUp() - return, sequence error (", "/"), this.mFingerprintAuthenticationSequence, ")", "KeyguardFingerprint");
                                    } else {
                                        Log.d("KeyguardFingerprint", "onUdfpsFingerUp()");
                                        dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15(12), null);
                                    }
                                    i9++;
                                    break;
                            }
                        }
                        break;
                    case VolteConstants.ErrorCode.CALL_HOLD_FAILED /* 1111 */:
                        SecFaceMsg secFaceMsg = (SecFaceMsg) message.obj;
                        int iStartTime2 = LogUtil.startTime(-1);
                        int i20 = 0;
                        while (true) {
                            SecFaceMsg secFaceMsg2 = (SecFaceMsg) this.mFaceMessages.poll();
                            if (secFaceMsg2 == null) {
                                LogUtil.endTime(iStartTime2, "KeyguardFace", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i20, "handleFaceAuth dispatchCount = "), new Object[0]);
                                return;
                            }
                            if (secFaceMsg2 == secFaceMsg) {
                                RecyclerView$$ExternalSyntheticOutline0.m(secFaceMsg.type, "KeyguardFace", MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i20, "handleFaceAuth faceMsg index = ", " / type = "));
                            }
                            int i21 = secFaceMsg2.type;
                            if (i21 == 0) {
                                int i22 = secFaceMsg2.arg;
                                CharSequence charSequence3 = secFaceMsg2.msgString;
                                handleFaceError(i22, charSequence3 == null ? null : charSequence3.toString());
                                if (this.mActiveUnlockConfig.faceErrorsToTriggerBiometricFailOn.contains(Integer.valueOf(i22))) {
                                    requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin.BIOMETRIC_FAIL, "faceError-" + i22);
                                }
                            } else if (i21 == 1) {
                                int i23 = secFaceMsg2.arg;
                                CharSequence charSequence4 = secFaceMsg2.msgString;
                                if (isFaceDetectionRunning()) {
                                    Log.d("KeyguardFace", "onAuthenticationHelp(), helpCode=" + i23 + ", helpString=" + ((Object) charSequence4));
                                    handleFaceHelp(i23, charSequence4.toString());
                                } else {
                                    Log.d("KeyguardFace", "onAuthenticationHelp(), Face is not running");
                                }
                            } else if (i21 == 2) {
                                SemBioFaceManager.AuthenticationResult authenticationResult2 = secFaceMsg2.result;
                                if (!isFaceDetectionRunning()) {
                                    Log.d("KeyguardFace", "onAuthenticationSucceeded(), Face is not running");
                                } else if (this.mKeyguardGoingAway) {
                                    com.android.systemui.keyguard.Log.d("KeyguardFace", "onAuthenticationSucceeded() - return, goingAway is true");
                                } else {
                                    boolean z3 = !((BiometricUnlockController) this.mBiometricUnlockControllerLazy.get()).mKeyguardBypassController.getLockStayEnabled();
                                    KeyguardFastBioUnlockController keyguardFastBioUnlockController2 = this.mFastUnlockController;
                                    Objects.requireNonNull(keyguardFastBioUnlockController2);
                                    Rune.runIf(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda11(keyguardFastBioUnlockController2, i2), z3);
                                    com.android.systemui.keyguard.Log.d("KeyguardFace", "onAuthenticationSucceeded()");
                                    setFaceAuthenticated(true);
                                    handleFaceAuthenticated(0, authenticationResult2.isStrongBiometric());
                                    this.mFastUnlockController.logLapTime("onFaceAuthenticationSucceeded end", new Object[0]);
                                }
                            } else if (i21 != 3) {
                                if (i21 == 4) {
                                    int i24 = secFaceMsg2.arg;
                                    if (isFaceDetectionRunning()) {
                                        if (!this.mIsFaceReady) {
                                            this.mIsFaceReady = true;
                                        }
                                        Log.d("KeyguardFace", "onAuthenticationAcquired(), acquireInfo=" + i24);
                                        handleFaceAcquired(i24);
                                        if (this.mActiveUnlockConfig.faceAcquireInfoToTriggerBiometricFailOn.contains(Integer.valueOf(i24))) {
                                            requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin.BIOMETRIC_FAIL, "faceAcquireInfo-" + i24);
                                        }
                                    } else {
                                        Log.d("KeyguardFace", "onAuthenticationAcquired(), Face is not running");
                                    }
                                }
                            } else if (isFaceDetectionRunning()) {
                                Log.d("KeyguardFace", "onAuthenticationFailed()");
                                requestActiveUnlock(ActiveUnlockConfig.ActiveUnlockRequestOrigin.BIOMETRIC_FAIL, "faceFailure-".concat(this.mKeyguardBypassController.canBypass() ? "bypass" : this.mAlternateBouncerShowing ? "alternateBouncer" : this.mPrimaryBouncerFullyShown ? "bouncer" : "udfpsFpDown"));
                                handleFaceAuthFailed();
                            } else {
                                Log.d("KeyguardFace", "onAuthenticationFailed(), Face is not running");
                            }
                            i20++;
                        }
                    case VolteConstants.ErrorCode.CALL_RESUME_FAILED /* 1112 */:
                        dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda24(((Boolean) message.obj).booleanValue(), 3), null);
                        return;
                    case VolteConstants.ErrorCode.CALL_TEMP_UNAVAILABLE_415_CAUSE /* 1113 */:
                        Log.d("KeyguardUpdateMonitor", "forceStartFingerprintAuthentication");
                        this.mForceStartFinger = true;
                        updateFingerprintListeningState(2);
                        return;
                    default:
                        switch (i5) {
                            case VolteConstants.ErrorCode.CALL_BARRED_DUE_TO_SSAC /* 1116 */:
                                String str2 = (String) message.obj;
                                knoxStateMonitor = (KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class);
                                str2.getClass();
                                switch (str2) {
                                    case "rmm":
                                        this.mRemoteLockSimulationInfo = new RemoteLockInfo.Builder(2, true).setClientName("Samsung Lockscreen").setPhoneNumber("000-000-0000").setMessage("This is RMM Lock Test Message.").build();
                                        try {
                                            this.mLockSettingsService.setRemoteLock(((UserTrackerImpl) this.mUserTracker).getUserId(), this.mRemoteLockSimulationInfo);
                                            return;
                                        } catch (RemoteException e2) {
                                            Log.d("KeyguardUpdateMonitor", "Failed setRemoteLock(RMM)" + e2);
                                            return;
                                        }
                                    case "admin":
                                        EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) knoxStateMonitor).mEdmMonitor;
                                        if (edmMonitor != null) {
                                            edmMonitor.setAdminLock(true, false);
                                            return;
                                        }
                                        return;
                                    case "clear":
                                        EdmMonitor edmMonitor2 = ((KnoxStateMonitorImpl) knoxStateMonitor).mEdmMonitor;
                                        if (edmMonitor2 != null) {
                                            edmMonitor2.setAdminLock(false, false);
                                        }
                                        try {
                                            RemoteLockInfo remoteLockInfo = this.mRemoteLockSimulationInfo;
                                            if (remoteLockInfo != null) {
                                                remoteLockInfo.lockState = false;
                                                this.mLockSettingsService.setRemoteLock(((UserTrackerImpl) this.mUserTracker).getUserId(), this.mRemoteLockSimulationInfo);
                                                this.mRemoteLockSimulationInfo = null;
                                                return;
                                            }
                                            return;
                                        } catch (RemoteException e3) {
                                            Log.d("KeyguardUpdateMonitor", "Failed setRemoteLock" + e3);
                                            return;
                                        }
                                    case "license":
                                        EdmMonitor edmMonitor3 = ((KnoxStateMonitorImpl) knoxStateMonitor).mEdmMonitor;
                                        if (edmMonitor3 != null) {
                                            edmMonitor3.setAdminLock(false, true);
                                            return;
                                        }
                                        return;
                                    case "knoxguard":
                                        Bundle bundle = new Bundle();
                                        bundle.putCharSequence("customer_package_name", "com.samsung.android.calendar");
                                        bundle.putCharSequence("customer_app_name", "Samsung Calendar");
                                        this.mRemoteLockSimulationInfo = new RemoteLockInfo.Builder(3, true).setClientName("KnoxGuard Lockscreen").setBundle(bundle).setPhoneNumber("010-000-0000").setMessage("This is Knoxguard Lock Test Message.\n\n\n\n\nKG LOCK\n\n\n\n\nKnoxGuard Lockscreen").setSkipSupportContainer(false).setLockTimeOut(60000L).setAllowFailCount(5).build();
                                        try {
                                            this.mLockSettingsService.setRemoteLock(((UserTrackerImpl) this.mUserTracker).getUserId(), this.mRemoteLockSimulationInfo);
                                            return;
                                        } catch (RemoteException e4) {
                                            Log.d("KeyguardUpdateMonitor", "Failed setRemoteLock(KNOXGUARD)" + e4);
                                            return;
                                        }
                                    default:
                                        return;
                                }
                            case VolteConstants.ErrorCode.CALL_ENDED_BY_NW_HANDOVER_BEFORE_100_TRYING /* 1117 */:
                                String str3 = (String) message.obj;
                                KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_SHELL);
                                if ("finger".equals(str3) || "face".equals(str3)) {
                                    this.mFastUnlockController.setEnabled();
                                }
                                str3.getClass();
                                if (str3.equals("finger")) {
                                    Log.d("KeyguardFingerprint", "onAuthenticationSucceeded()");
                                    handleFingerprintAuthenticated(0, false);
                                    return;
                                } else {
                                    if (!str3.equals("face")) {
                                        ((ViewMediatorCallback) this.mViewMediatorCallbackLazy.get()).keyguardDone(((UserTrackerImpl) this.mUserTracker).getUserId());
                                        return;
                                    }
                                    Log.d("KeyguardFace", "onAuthenticationSucceeded()");
                                    setFaceAuthenticated(true);
                                    handleFaceAuthenticated(0, false);
                                    return;
                                }
                            case VolteConstants.ErrorCode.CALL_TRANSFER_SUCCESS /* 1118 */:
                                String str4 = (String) message.obj;
                                Log.d("KeyguardUpdateMonitor", "handleFailToUnlockSimulation unlockType : " + str4);
                                str4.getClass();
                                if (str4.equals("finger")) {
                                    Log.d("KeyguardFingerprint", "onAuthenticationFailed()");
                                    handleFingerprintAuthFailed();
                                    return;
                                } else if (!str4.equals("face")) {
                                    dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda12(this, 6), null);
                                    return;
                                } else {
                                    Log.d("KeyguardFace", "onAuthenticationFailed()");
                                    handleFaceAuthFailed();
                                    return;
                                }
                            case VolteConstants.ErrorCode.CALL_TRANSFER_FAILED /* 1119 */:
                                DisplayLifecycle displayLifecycle = this.mDisplayLifecycle;
                                displayLifecycle.mIsFitToActiveDisplay = displayLifecycle.mDisplayManager.semIsFitToActiveDisplay();
                                ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("updateSmartViewFitToActiveDisplay : "), displayLifecycle.mIsFitToActiveDisplay, "DisplayLifecycle");
                                return;
                            case VolteConstants.ErrorCode.CALL_CANCEL_TRANSFER_SUCCESS /* 1120 */:
                                updateFingerprintListeningState(2);
                                return;
                            case VolteConstants.ErrorCode.CALL_CANCEL_TRANSFER_FAILED /* 1121 */:
                                updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_SUB_SCREEN);
                                return;
                            case VolteConstants.ErrorCode.CALL_CANCEL_MODIFY_REQUESTED /* 1122 */:
                                final int i25 = message.arg1;
                                z = message.arg2 == 1;
                                dispatchCallback(new Consumer() { // from class: com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda48
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        int i26 = i25;
                                        boolean z4 = z;
                                        SemBioFaceManager semBioFaceManager = KeyguardSecUpdateMonitorImpl.sFaceManager;
                                        ((KeyguardUpdateMonitorCallback) obj).onDualDarInnerLockScreenStateChanged(i26, z4);
                                    }
                                }, null);
                                return;
                            case VolteConstants.ErrorCode.CALL_END_REASON_TELEPHONY_NOT_RESPONDING /* 1123 */:
                                int i26 = message.arg1;
                                int i27 = message.arg2;
                                ServiceState serviceState = (ServiceState) message.obj;
                                StringBuilder sbM = MutableObjectList$$ExternalSyntheticOutline0.m(i26, i27, "handleServiceStateChange(subId=", ", slotId=", ", serviceState=");
                                sbM.append(serviceState);
                                Log.d("KeyguardUpdateMonitor", sbM.toString());
                                if (CscRune.SECURITY_DISABLE_EMERGENCY_CALL_WHEN_OFFLINE) {
                                    this.mServiceStatesBySlotId.put(Integer.valueOf(i27), serviceState);
                                    Iterator it = this.mServiceStatesBySlotId.entrySet().iterator();
                                    while (true) {
                                        if (it.hasNext()) {
                                            ServiceState serviceState2 = (ServiceState) ((Map.Entry) it.next()).getValue();
                                            if (serviceState2 == null || ((serviceState2.getVoiceRegState() == 1 || serviceState2.getVoiceRegState() == 3) && (serviceState2.getVoiceRegState() != 1 || !serviceState2.isEmergencyOnly()))) {
                                            }
                                        } else {
                                            z = true;
                                        }
                                    }
                                    if (this.mIsOutOfService != z) {
                                        this.mIsOutOfService = z;
                                        Log.d("KeyguardUpdateMonitor", "updateOfflineState isOutOfService : " + this.mIsOutOfService);
                                        dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15(5), null);
                                    }
                                }
                                super.handleServiceStateChange(i26, serviceState);
                                return;
                            case VolteConstants.ErrorCode.CALL_18X_RETRANSMISSION_TIMEOUT /* 1124 */:
                                boolean z4 = message.arg1 == 1;
                                EmergencyButtonController$$ExternalSyntheticOutline0.m("handlePrimaryBouncerVisibilityChanged ", "KeyguardUpdateMonitor", z4);
                                if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && z4) {
                                    this.mHandler.postDelayed(this.mWaitingFocusRunnable, 200L);
                                }
                                dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda24(z4, 4), null);
                                return;
                            default:
                                switch (i5) {
                                    case VolteConstants.ErrorCode.PPP_STATUS_CLOSE_EVENT /* 1301 */:
                                        final String str5 = (String) message.obj;
                                        dispatchCallback(new Consumer() { // from class: com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda46
                                            @Override // java.util.function.Consumer
                                            public final void accept(Object obj) {
                                                int i28 = i3;
                                                String str6 = str5;
                                                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) obj;
                                                switch (i28) {
                                                    case 0:
                                                        SemBioFaceManager semBioFaceManager = KeyguardSecUpdateMonitorImpl.sFaceManager;
                                                        keyguardUpdateMonitorCallback.onPackageDataCleared(str6);
                                                        break;
                                                    case 1:
                                                        SemBioFaceManager semBioFaceManager2 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                                                        keyguardUpdateMonitorCallback.onPackageChanged(str6);
                                                        break;
                                                    default:
                                                        SemBioFaceManager semBioFaceManager3 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                                                        keyguardUpdateMonitorCallback.onPackageAdded(str6);
                                                        break;
                                                }
                                            }
                                        }, null);
                                        return;
                                    case VolteConstants.ErrorCode.PPP_OPEN_FAILURE /* 1302 */:
                                        final String str6 = (String) message.obj;
                                        dispatchCallback(new Consumer() { // from class: com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda46
                                            @Override // java.util.function.Consumer
                                            public final void accept(Object obj) {
                                                int i28 = i4;
                                                String str62 = str6;
                                                KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) obj;
                                                switch (i28) {
                                                    case 0:
                                                        SemBioFaceManager semBioFaceManager = KeyguardSecUpdateMonitorImpl.sFaceManager;
                                                        keyguardUpdateMonitorCallback.onPackageDataCleared(str62);
                                                        break;
                                                    case 1:
                                                        SemBioFaceManager semBioFaceManager2 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                                                        keyguardUpdateMonitorCallback.onPackageChanged(str62);
                                                        break;
                                                    default:
                                                        SemBioFaceManager semBioFaceManager3 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                                                        keyguardUpdateMonitorCallback.onPackageAdded(str62);
                                                        break;
                                                }
                                            }
                                        }, null);
                                        return;
                                    case 1303:
                                        dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda40(1, message.arg1 == 1, (String) message.obj), null);
                                        return;
                                    default:
                                        switch (i5) {
                                            case 1305:
                                                final String str7 = (String) message.obj;
                                                if (KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(str7)) {
                                                    this.mSettingsHelper.readSettingsDB();
                                                }
                                                final int i28 = z ? 1 : 0;
                                                dispatchCallback(new Consumer() { // from class: com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda46
                                                    @Override // java.util.function.Consumer
                                                    public final void accept(Object obj) {
                                                        int i282 = i28;
                                                        String str62 = str7;
                                                        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) obj;
                                                        switch (i282) {
                                                            case 0:
                                                                SemBioFaceManager semBioFaceManager = KeyguardSecUpdateMonitorImpl.sFaceManager;
                                                                keyguardUpdateMonitorCallback.onPackageDataCleared(str62);
                                                                break;
                                                            case 1:
                                                                SemBioFaceManager semBioFaceManager2 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                                                                keyguardUpdateMonitorCallback.onPackageChanged(str62);
                                                                break;
                                                            default:
                                                                SemBioFaceManager semBioFaceManager3 = KeyguardSecUpdateMonitorImpl.sFaceManager;
                                                                keyguardUpdateMonitorCallback.onPackageAdded(str62);
                                                                break;
                                                        }
                                                    }
                                                }, null);
                                                return;
                                            case 1306:
                                                dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda16(((Integer) message.obj).intValue(), 5), null);
                                                return;
                                            case 1307:
                                                if (Build.IS_USERDEBUG || Build.IS_ENG) {
                                                    ((PluginFaceWidgetManager) this.mPluginFaceWidgetManagerLazy.get()).reconnectPluginModule();
                                                    return;
                                                }
                                                return;
                                            case 1308:
                                                dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda24(((Boolean) message.obj).booleanValue(), 5), null);
                                                return;
                                            default:
                                                switch (i5) {
                                                    case VolteConstants.ErrorCode.RTP_TIME_OUT /* 1401 */:
                                                        Log.d("KeyguardUpdateMonitor", "handleLocaleChanged()");
                                                        if (LsRune.KEYGUARD_FBE) {
                                                            createChannels();
                                                        }
                                                        dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15(11), null);
                                                        Log.d("KeyguardUpdateMonitor", "updateCarrierTextInfo");
                                                        if (this.mHandler.hasMessages(VolteConstants.ErrorCode.CALL_STATUS_CONF_ADD_USER_TO_SESSION_FAILURE)) {
                                                            this.mHandler.removeMessages(VolteConstants.ErrorCode.CALL_STATUS_CONF_ADD_USER_TO_SESSION_FAILURE);
                                                        }
                                                        this.mHandler.sendEmptyMessage(VolteConstants.ErrorCode.CALL_STATUS_CONF_ADD_USER_TO_SESSION_FAILURE);
                                                        return;
                                                    case 1402:
                                                        boolean zBooleanValue3 = ((Boolean) message.obj).booleanValue();
                                                        EmergencyButtonController$$ExternalSyntheticOutline0.m("handleDlsBiometricMode(), enabled=", "KeyguardUpdateMonitor", zBooleanValue3);
                                                        if (this.mIsDynamicLockViewMode != zBooleanValue3) {
                                                            this.mIsDynamicLockViewMode = zBooleanValue3;
                                                            if (isFingerprintOptionEnabled()) {
                                                                updateFingerprintListeningState(2);
                                                            }
                                                            if (isFaceOptionEnabled()) {
                                                                updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_DYNAMIC_LOCK);
                                                                return;
                                                            }
                                                            return;
                                                        }
                                                        return;
                                                    case 1403:
                                                        int iIntValue2 = ((Integer) message.obj).intValue();
                                                        Log.d("KeyguardUpdateMonitor", "handleDlsViewMode(), mode=" + iIntValue2);
                                                        dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda16(iIntValue2, 4), null);
                                                        return;
                                                    default:
                                                        return;
                                                }
                                        }
                                }
                        }
                }
                break;
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void handleStartedGoingToSleep(int i) {
        if (LsRune.SECURITY_BACKGROUND_AUTHENTICATION) {
            this.mIsFPCanceledByForegroundApp = false;
        }
        this.mIsShortcutLaunchInProgress = false;
        this.mIsFaceWidgetFullScreenMode = false;
        this.mIsStartFacePossible = false;
        if (LsRune.SECURITY_SUB_DISPLAY_COVER) {
            this.mForceStartFinger = false;
            this.mNeedSubBioAuth = false;
        }
        setFaceAuthenticated(false);
        setFingerprintAuthenticated(false);
        if (LsRune.SECURITY_ESIM) {
            clearESimRemoved();
        }
        setUnlockingKeyguard(false);
        super.handleStartedGoingToSleep(i);
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void handleStartedWakingUp(int i) {
        if (LsRune.SECURITY_SUB_DISPLAY_COVER) {
            this.mNeedSubWofFpAuth = false;
        }
        if (this.mPhoneState == 0) {
            this.mIsFPCanceledByProximity = false;
        }
        if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
            sendScreenStatus(false);
        }
        super.handleStartedWakingUp(i);
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void handleUserSwitchComplete(int i) throws NoSuchMethodException, ClassNotFoundException, SecurityException {
        this.mSettingsHelper.onUserSwitched();
        if (updateCredentialType(i) | updateFMMLock(i, false) | updateCarrierLock(i) | updatePermanentLock(i) | updateSecureLockTimeout(i) | updateBiometricLockTimeout(i) | updateBiometricsOptionState(i)) {
            dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15(1), null);
        }
        if (updateLockscreenDisabled(i)) {
            dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda12(this, 2), null);
        }
        if (updateOwnerInfo(i) | updateDeviceOwnerInfo()) {
            dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15(0), null);
        }
        updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_USER_SWITCHING);
        DevicePolicyManager devicePolicyManager = this.mDevicePolicyManager;
        if (devicePolicyManager != null) {
            this.mDisableCamera = devicePolicyManager.getCameraDisabled(null, i);
            this.mMaximumFailedPasswordsForWipe = this.mDevicePolicyManager.getMaximumFailedPasswordsForWipe(null, i);
        }
        super.handleUserSwitchComplete(i);
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void handleUserUnlocked(int i) throws Resources.NotFoundException {
        Assert.isMainThread();
        Log.i("KeyguardUpdateMonitor", "handleUserUnlocked(" + i + ")");
        this.mUserIsUnlocked.put(i, true);
        this.mNeedsSlowUnlockTransition = resolveNeedsSlowUnlockTransition();
        if (LsRune.KEYGUARD_FBE) {
            updateUserUnlockNotification(i);
        }
        updateFMMLock(((UserTrackerImpl) this.mUserTracker).getUserId(), true);
        updateDualDARInnerLockscreenRequirement(i);
        dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15(6), null);
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean hasRedactedNotifications() {
        return this.mHasRedactedNotifications;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean is2StepVerification() {
        return ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isMultifactorAuthEnforced();
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isActiveDismissAction() {
        return this.mKeyguardDismissActionType != KeyguardConstants$KeyguardDismissActionType.KEYGUARD_DISMISS_ACTION_DEFAULT;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isAllEnabledSlotOutOfService() {
        if (this.mServiceStates == null) {
            return false;
        }
        List subscriptionInfo = getSubscriptionInfo(false);
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        StringBuilder sb = new StringBuilder("Active subscriptionInfo count = ");
        ArrayList arrayList = (ArrayList) subscriptionInfo;
        sb.append(arrayList.size());
        sb.append(", All serviceStates count = ");
        sb.append(this.mServiceStates.size());
        keyguardUpdateMonitorLogger.d(sb.toString());
        if (arrayList.isEmpty()) {
            return false;
        }
        boolean z = true;
        for (int i = 0; i < arrayList.size(); i++) {
            int subscriptionId = ((SubscriptionInfo) arrayList.get(i)).getSubscriptionId();
            ServiceState serviceState = (ServiceState) this.mServiceStates.get(Integer.valueOf(subscriptionId));
            if (serviceState != null) {
                this.mLogger.d("    subId[" + subscriptionId + "] serviceState = " + serviceState);
                if (serviceState.getVoiceRegState() == 0) {
                    z = false;
                }
            }
        }
        return z;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isAllSimState() {
        synchronized (this.mSimDataLockObject) {
            try {
                Iterator it = this.mSimDatasBySlotId.values().iterator();
                while (it.hasNext()) {
                    if (((KeyguardUpdateMonitor.SimData) it.next()).simState != 1) {
                        return false;
                    }
                }
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isAnimationDisabled() {
        return this.mSettingsHelper.isAnimationDisabled();
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isAuthenticatedWithBiometric(int i) {
        return getFingerprintAuthenticated(i) || getFaceAuthenticated(i);
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isAutoWipe() {
        return !this.mFMMLock && this.mMaximumFailedPasswordsForWipe <= 0 && this.mSettingsHelper.isAutoWipeEnable();
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isBiometricErrorLockoutPermanent() {
        return this.mFingerprintLockedOutPermanent || this.mFaceLockedOutPermanent;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isBiometricsAuthenticatedOnLock() {
        boolean z = isFaceOptionEnabled() && this.mSettingsHelper.isEnabledFaceStayOnLock() && getIsFaceAuthenticated();
        if (isFingerprintOptionEnabled() && this.mSettingsHelper.isEnabledFingerprintStayOnLock()) {
            KeyguardUpdateMonitor.BiometricAuthenticated biometricAuthenticated = this.mUserFingerprintAuthenticated.get(((UserTrackerImpl) this.mUserTracker).getUserId());
            if (biometricAuthenticated != null && biometricAuthenticated.mAuthenticated) {
                return true;
            }
        }
        return z;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isBouncerFullyShown() {
        return this.mPrimaryBouncerFullyShown;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isCameraDisabledByPolicy() {
        EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mEdmMonitor;
        if (edmMonitor == null || !edmMonitor.mIsCameraDisabledByMdm || edmMonitor.mIsFaceRecognitionAllowedEvenCameraBlocked) {
            return this.mDisableCamera;
        }
        return true;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isCarrierLock() {
        return this.mCarrierLock;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isCoverClosed() {
        synchronized (this) {
            try {
                CoverState coverState = this.mCoverState;
                if (coverState == null || !coverState.attached) {
                    return false;
                }
                return !coverState.getSwitchState();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isDeviceOwnerInfoEnabled() {
        return !TextUtils.isEmpty(this.mDeviceOwnerInfoText);
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isDismissActionExist() {
        return this.mIsDismissActionExist;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isDualDarInnerAuthRequired(int i) {
        return this.mIsDualDarInnerAuthRequired;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isDualDarInnerAuthShowing() {
        return this.mIsDualDarInnerAuthShowing;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isDualDisplayPolicyAllowed() {
        return LsRune.SECURITY_SUB_DISPLAY_LOCK && this.mDisplayLifecycle.mIsFolderOpened && !DeviceState.isSmartViewFitToActiveDisplay();
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isESimEmbedded() {
        ArrayList arrayList = (ArrayList) getSubscriptionInfo(false);
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (((SubscriptionInfo) obj).isEmbedded()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isESimRemoveButtonClicked() {
        for (boolean z : this.mESimRemoved) {
            if (z) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isEarlyWakeUp() {
        return this.mIsEarlyWakeUp;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isEmergencyCallOnly() {
        if (this.mServiceStates == null) {
            return false;
        }
        List subscriptionInfo = getSubscriptionInfo(false);
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        StringBuilder sb = new StringBuilder("Active subscriptionInfo count = ");
        ArrayList arrayList = (ArrayList) subscriptionInfo;
        sb.append(arrayList.size());
        sb.append(", All serviceStates count = ");
        sb.append(this.mServiceStates.size());
        keyguardUpdateMonitorLogger.d(sb.toString());
        boolean z = arrayList.size() < this.mTelephonyManager.getActiveModemCount();
        boolean z2 = false;
        for (int i = 0; i < arrayList.size(); i++) {
            int subscriptionId = ((SubscriptionInfo) arrayList.get(i)).getSubscriptionId();
            ServiceState serviceState = (ServiceState) this.mServiceStates.get(Integer.valueOf(subscriptionId));
            if (serviceState != null) {
                this.mLogger.d("    subId[" + subscriptionId + "] serviceState = " + serviceState);
                if (serviceState.getVoiceRegState() == 0) {
                    z2 = true;
                }
                if (serviceState.getVoiceRegState() == 1 && serviceState.isEmergencyOnly()) {
                    z = true;
                }
            }
        }
        return z && !z2;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isEnabledWof() {
        return (LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY || LsRune.SECURITY_SUB_DISPLAY_LOCK) ? isEnabledWofOnFold() : this.mSettingsHelper.isEnabledWof();
    }

    public final boolean isEnabledWofOnFold() {
        if (!this.mSettingsHelper.isEnabledWof()) {
            return false;
        }
        int wofType = this.mSettingsHelper.getWofType();
        return wofType != 1 ? wofType != 2 ? wofType == 3 : this.mDisplayLifecycle.mIsFolderOpened : !this.mDisplayLifecycle.mIsFolderOpened;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isFMMLock() {
        return this.mFMMLock;
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final boolean isFaceEnabledAndEnrolled() {
        if (isFaceOptionEnabled()) {
            return !isForcedLock();
        }
        return false;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isFaceOptionEnabled() {
        if (SafeUIState.isSysUiSafeModeEnabled()) {
            return false;
        }
        return this.mBiometricsFace.get(((UserTrackerImpl) this.mUserTracker).getUserId());
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final boolean isFingerprintDisabled(int i) {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) this.mContext.getSystemService("device_policy");
        return !(devicePolicyManager == null || (devicePolicyManager.getKeyguardDisabledFeatures(null, i) & 32) == 0) || isSimPinSecure();
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isFingerprintDisabledWithBadQuality() {
        return this.mFingerPrintBadQualityCounts.get(((UserTrackerImpl) this.mUserTracker).getUserId(), 0) >= 50;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isFingerprintLeave() {
        return this.mIsFpLeave;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isFingerprintOptionEnabled() {
        if (SafeUIState.isSysUiSafeModeEnabled()) {
            return false;
        }
        return this.mBiometricsFingerprint.get(((UserTrackerImpl) this.mUserTracker).getUserId());
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isForcedLock() {
        if (!MdfUtils.isMdfDisabled()) {
            return isSimPinSecure() || isRemoteLock() || this.mPermanentLock;
        }
        Log.d("KeyguardUpdateMonitor", "Prevent the Biometric from unlocking because CC mode is disabled.");
        return true;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isForgotPasswordView() {
        return this.mCurrentSecurityMode == KeyguardSecurityModel.SecurityMode.ForgotPassword;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isFullscreenBouncer() {
        return SecurityUtils.checkFullscreenBouncer(this.mCurrentSecurityMode);
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isHiddenInputContainer() {
        if (this.mPermanentLock) {
            return true;
        }
        return getLockoutAttemptDeadline() > 0 && !isForgotPasswordView();
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isIccBlockedPermanently() {
        int nextSubIdForState = getNextSubIdForState(7);
        SubscriptionInfo subscriptionInfoForSubId = getSubscriptionInfoForSubId(nextSubIdForState);
        boolean z = false;
        boolean zIsEmbedded = subscriptionInfoForSubId != null ? subscriptionInfoForSubId.isEmbedded() : false;
        if (SubscriptionManager.isValidSubscriptionId(nextSubIdForState) && !zIsEmbedded) {
            z = true;
        }
        this.mSimDisabledPermanently = z;
        return z;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isInDisplayFingerprintMarginAccepted() {
        KeyguardSecurityModel.SecurityMode securityMode;
        if (!is2StepVerification()) {
            KeyguardUpdateMonitor.StrongAuthTracker strongAuthTracker = this.mStrongAuthTracker;
            if (!strongAuthTracker.isBiometricAllowedForUser(true, KeyguardUpdateMonitor.this.mSelectedUserInteractor.getSelectedUserId())) {
                return false;
            }
        }
        return (!LsRune.SECURITY_FINGERPRINT_IN_DISPLAY || !isFingerprintOptionEnabled() || (securityMode = this.mCurrentSecurityMode) == KeyguardSecurityModel.SecurityMode.Swipe || SecurityUtils.checkFullscreenBouncer(securityMode) || DeviceState.isSmartViewDisplayWithFitToAspectRatio(this.mContext) || isForgotPasswordView()) ? false : true;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isKeyguardUnlocking() {
        return this.mKeyguardGoingAway || this.mKeyguardUnlocking;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isKidsModeRunning() {
        return this.mIsKidsModeRunning;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isLockScreenRotationAllowed$1() {
        if (this.mSettingsHelper.isLockScreenRotationAllowed()) {
            return WallpaperUtils.mIsEmergencyMode || WallpaperUtils.mIsUltraPowerSavingMode || !this.mScreenOrientationNoSensorValue;
        }
        return false;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isLockscreenDisabled() {
        if (isSecure()) {
            return false;
        }
        return this.mLockscreenDisabled;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isMaxFailedBiometricUnlockAttempts(int i) {
        return getFailedBiometricUnlockAttempts(i) >= 20;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isMaxFailedBiometricUnlockAttemptsShort() {
        int failedBiometricUnlockAttempts = getFailedBiometricUnlockAttempts(((UserTrackerImpl) this.mUserTracker).getUserId());
        return failedBiometricUnlockAttempts != 0 && failedBiometricUnlockAttempts % 5 == 0;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isNowBarExpandMode() {
        return this.mIsNowBarExpandMode;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isOutOfService() {
        return this.mIsOutOfService;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isOwnerInfoEnabled() {
        return this.mIsOwnerInfoEnabled;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isPerformingWipeOut() {
        int currentFailedPasswordAttempts = this.mLockPatternUtils.getCurrentFailedPasswordAttempts(((UserTrackerImpl) this.mUserTracker).getUserId());
        boolean zIsAutoWipe = isAutoWipe();
        int i = this.mMaximumFailedPasswordsForWipe;
        if (i <= 0) {
            i = zIsAutoWipe ? 20 : 0;
        }
        return (currentFailedPasswordAttempts == 0 || i == 0 || currentFailedPasswordAttempts != i) ? false : true;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isPermanentLock() {
        return this.mPermanentLock;
    }

    public final boolean isRemoteLock() {
        return this.mFMMLock || this.mCarrierLock || isRemoteLockEnabled() || ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isAdminLockEnabled();
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isRemoteLockEnabled() {
        return this.mActiveRemoteLockIndex >= 0;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isRemoteLockMode() {
        KeyguardSecurityModel.SecurityMode securityMode = this.mCurrentSecurityMode;
        int i = SecurityUtils.sPINContainerBottomMargin;
        switch (SecurityUtils.AnonymousClass1.$SwitchMap$com$android$keyguard$KeyguardSecurityModel$SecurityMode[securityMode.ordinal()]) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return true;
            default:
                return false;
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isScreenOffMemoRunning() {
        return this.mIsRunningBlackMemo;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isScreenOn() {
        return ((ViewMediatorCallback) this.mViewMediatorCallbackLazy.get()).isScreenOn();
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isSecure() {
        return isSecure(this.mSelectedUserInteractor.getSelectedUserId());
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isShortcutLaunchInProgress() {
        return this.mIsShortcutLaunchInProgress;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isShowEditModeRequest() {
        return ((KeyguardEditModeControllerImpl) ((KeyguardEditModeController) this.mKeyguardEditModeControllerLazy.get())).isShowEditorRequested && this.mIsDismissActionExist && !isActiveDismissAction();
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isSimDisabledPermanently() {
        return this.mSimDisabledPermanently;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isSimPinPassed(int i, int i2) {
        if (i2 != 2 && i2 != 3 && i2 != 12) {
            return false;
        }
        if (i >= 0 && i <= 1) {
            return this.mSimPinPassed[i];
        }
        RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "isSimPinPassed  Slot Boundary Exception SlotNum: ", "KeyguardUpdateMonitor");
        return false;
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final boolean isSimPinSecure() {
        synchronized (this.mSimDataLockObject) {
            try {
                for (KeyguardUpdateMonitor.SimData simData : this.mSimDatasBySlotId.values()) {
                    if (CscRune.SECURITY_SIM_PERM_DISABLED && simData.simState == 7) {
                        if (this.mDeviceProvisioned) {
                            SubscriptionInfo subscriptionInfoForSubId = getSubscriptionInfoForSubId(simData.subId);
                            if (subscriptionInfoForSubId != null && subscriptionInfoForSubId.isEmbedded()) {
                                Log.d("KeyguardUpdateMonitor", "isSimPinSecure() : subId=" + simData.subId + " is eSIM. continue");
                            } else if (subscriptionInfoForSubId == null) {
                                Log.d("KeyguardUpdateMonitor", "isSimPinSecure() : subId=" + simData.subId + " subscriptionInfo is not exist");
                            }
                        }
                    }
                    if (KeyguardUpdateMonitor.isSimPinSecure(simData.simState)) {
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isSimState(int i) {
        ArrayList arrayList = (ArrayList) getSubscriptionInfo(false);
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            if (getSimStateForSlotId(((SubscriptionInfo) obj).getSimSlotIndex()) == i) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isTimerRunning() {
        return getLockoutBiometricAttemptDeadline() + getLockoutAttemptDeadline() != 0;
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final boolean isUdfpsEnrolled() {
        return false;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isUdfpsFingerDown() {
        return this.mUdfpsFingerDown;
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final boolean isUdfpsSupported() {
        return false;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isUnlockCompleted() {
        if (is2StepVerification()) {
            return true;
        }
        return this.mStrongAuthTracker.hasUserAuthenticatedSinceBoot();
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final boolean isUnlockingWithBiometricAllowed(boolean z) {
        if (!is2StepVerification()) {
            KeyguardUpdateMonitor.StrongAuthTracker strongAuthTracker = this.mStrongAuthTracker;
            if (!strongAuthTracker.isBiometricAllowedForUser(z, KeyguardUpdateMonitor.this.mSelectedUserInteractor.getSelectedUserId())) {
                return false;
            }
        }
        return (this.mDisabledBiometricBySecurityDialog || getLockoutBiometricAttemptDeadline() > 0 || this.mSettingsHelper.isUltraPowerSavingModeLegacy()) ? false : true;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isUpdateSecurityMessage() {
        return (isAuthenticatedWithBiometric(((UserTrackerImpl) this.mUserTracker).getUserId()) || getLockoutAttemptDeadline() != 0 || isKeyguardUnlocking()) ? false : true;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isUserUnlocked$1() {
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        boolean zIsUserUnlocked = this.mUserManager.isUserUnlocked(userId);
        LogUtil.d("KeyguardUpdateMonitor", "isUserUnlocked userId:%s, unlocked:%s", Integer.valueOf(userId), Boolean.valueOf(zIsUserUnlocked));
        return zIsUserUnlocked;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void notifyFailedUnlockAttemptChanged() {
        dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15(9), null);
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void notifyStrongAuthAllowedChanged(int i) {
        boolean z = false;
        if (isUserInLockdown(i)) {
            setKeyguardGoingAway(false);
        }
        super.notifyStrongAuthAllowedChanged(i);
        if (!isUnlockingWithBiometricAllowed(getFaceStrongBiometric())) {
            clearFingerprintRecognized();
            setFaceAuthenticated(false);
        }
        if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && isFingerprintOptionEnabled()) {
            KeyguardUpdateMonitor.BiometricAuthenticated biometricAuthenticated = this.mUserFingerprintAuthenticated.get(((UserTrackerImpl) this.mUserTracker).getUserId());
            if (biometricAuthenticated != null && biometricAuthenticated.mIsStrongBiometric) {
                z = true;
            }
            setFodStrictMode(!isUnlockingWithBiometricAllowed(z));
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void onFingerprintAuthenticated(int i, boolean z) {
        setFingerprintAuthenticated(true);
        this.mFingerprintCancelSignal = null;
        for (int i2 = 0; i2 < this.mCallbacks.size(); i2++) {
            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) this.mCallbacks.get(i2)).get();
            if (keyguardUpdateMonitorCallback != null) {
                keyguardUpdateMonitorCallback.onBiometricAuthenticated(i, BiometricSourceType.FINGERPRINT, z);
            }
        }
        super.mBackgroundExecutor.execute(new KeyguardUpdateMonitor$$ExternalSyntheticLambda6(this, z, i));
        if (is2StepVerification()) {
            this.mLogger.d("onFingerprintAuthenticated ( stop fingerprint )");
            updateFingerprintListeningState(1);
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void onLockIconPressed() {
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        this.mUserFaceAuthenticated.put(userId, null);
        updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_LOCK_ICON_PRESSED);
        this.mUserFingerprintAuthenticated.clear();
        updateFingerprintListeningState(2);
        this.mStrongAuthTracker.onStrongAuthRequiredChanged(userId);
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void registerCallback(KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback) {
        if (!this.mIsDispatching) {
            super.registerCallback(keyguardUpdateMonitorCallback);
        } else {
            throw new IllegalStateException("do not add or remove a listener on dispatching: " + keyguardUpdateMonitorCallback);
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void registerPreCallback(KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback) {
        Assert.isMainThread();
        Objects.toString(keyguardUpdateMonitorCallback);
        for (int i = 0; i < this.mCallbacks.size(); i++) {
            if (((WeakReference) this.mCallbacks.get(i)).get() == keyguardUpdateMonitorCallback) {
                Log.e("KeyguardUpdateMonitor", "Object tried to add another callback", new Exception("Called by"));
                return;
            }
        }
        if (this.mCallbacks.size() > 0) {
            this.mCallbacks.add(0, new WeakReference(keyguardUpdateMonitorCallback));
        } else {
            this.mCallbacks.add(new WeakReference(keyguardUpdateMonitorCallback));
        }
        removeCallback(null);
        sendUpdates(keyguardUpdateMonitorCallback);
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void removeCallback(KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback) {
        if (!this.mIsDispatching) {
            super.removeCallback(keyguardUpdateMonitorCallback);
        } else {
            throw new IllegalStateException("do not add or remove a listener on dispatching: " + keyguardUpdateMonitorCallback);
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void removeESim(int i) {
        int slotId = getSlotId(i);
        if (slotId == 0 || slotId == 1) {
            this.mESimRemoved[slotId] = true;
        }
        super.handleSimStateChange(i, slotId, 0);
    }

    public final void reportFailedBiometricUnlockAttempt(int i) {
        int failedBiometricUnlockAttempts = getFailedBiometricUnlockAttempts(i) + 1;
        NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(failedBiometricUnlockAttempts, "reportFailedBiometricUnlockAttempt ( failedBiometricUnlockAttempts = ", " )", "KeyguardUpdateMonitor");
        this.mBiometricFailedAttempts.put(i, failedBiometricUnlockAttempts);
        if (failedBiometricUnlockAttempts >= 20) {
            Log.d("KeyguardUpdateMonitor", "reportFailedBiometricUnlockAttempt ( too many failed. )");
            this.mLockPatternUtils.requireStrongAuth(2, i);
        } else if (failedBiometricUnlockAttempts != 0 && failedBiometricUnlockAttempts % 5 == 0) {
            this.mLockPatternUtils.setBiometricAttemptDeadline(i, PluginLockInstancePolicy.DISABLED_BY_SUB_USER);
            long jElapsedRealtime = SystemClock.elapsedRealtime() + 30000;
            if (updateSecureLockTimeout(i) || updateBiometricLockTimeout(i)) {
                dispatchLockModeChanged();
            }
            updateBiometricListeningState(1, FaceAuthUiEvent.FACE_AUTH_UPDATED_BIOMETRIC_LOCKOUT_DEADLINE);
            Intent intent = new Intent("com.samsung.keyguard.BIOMETRIC_LOCKOUT_RESET");
            intent.addFlags(268435456);
            PendingIntent broadcast = PendingIntent.getBroadcast(this.mContext, 0, intent, 335544320);
            Log.d("KeyguardFingerprint", "setting Biometric lockout alarm !!");
            this.mAlarmManager.setExact(2, jElapsedRealtime, broadcast);
            dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15(13), null);
        }
        if (!MdfUtils.isMdfEnforced() || failedBiometricUnlockAttempts < 10) {
            return;
        }
        Log.d("KeyguardUpdateMonitor", "MDF : reportFailedBiometricUnlockAttempt ( too many failures. )");
        this.mLockPatternUtils.requireStrongAuth(2, i);
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void requestSessionClose() {
        if (isFingerprintOptionEnabled()) {
            Log.d("KeyguardFingerprint", "requestSessionClose() - isFingerprintDetectionRunning : " + isFingerprintDetectionRunning());
            if (isFingerprintDetectionRunning()) {
                String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
                stopListeningForFingerprint();
            } else {
                CancellationSignal cancellationSignal = this.mFingerprintCancelSignal;
                if (cancellationSignal != null) {
                    cancellationSignal.cancel();
                    this.mFingerprintCancelSignal = null;
                }
            }
        }
        ((UiOffloadThread) Dependency.sDependency.getDependencyInner(UiOffloadThread.class)).execute(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda11(this, 7));
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void resetSimPinPassed(int i) {
        if (i < 0 || i > 1) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "resetSimPinPassed  Slot Boundary Exception SlotNum: ", "KeyguardUpdateMonitor");
            return;
        }
        boolean[] zArr = this.mSimPinPassed;
        if (zArr[i]) {
            zArr[i] = false;
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void runSystemUserOnly(Runnable runnable) {
        runSystemUserOnly(runnable, null);
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void semSetScreenStatus() {
        sendScreenStatus(true);
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void sendBiometricUnlockState(final BiometricSourceType biometricSourceType) {
        final Intent intent = new Intent();
        int i = AnonymousClass8.$SwitchMap$android$hardware$biometrics$BiometricSourceType[biometricSourceType.ordinal()];
        if (i == 1) {
            intent.setAction("com.samsung.keyguard.FINGERPRINT_UNLOCK_STATE").putExtra("biometricId", this.mBiometricId);
        } else if (i == 2) {
            intent.setAction("com.samsung.keyguard.FACE_UNLOCK_STATE");
        }
        if (intent.getAction() != null) {
            this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda17
                @Override // java.lang.Runnable
                public final void run() {
                    KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl = this.f$0;
                    BiometricSourceType biometricSourceType2 = biometricSourceType;
                    Intent intent2 = intent;
                    SemBioFaceManager semBioFaceManager = KeyguardSecUpdateMonitorImpl.sFaceManager;
                    keyguardSecUpdateMonitorImpl.getClass();
                    LogUtil.d("KeyguardUpdateMonitor", "sendBiometricUnlockState type=%s biometricId=%d", biometricSourceType2, Integer.valueOf(biometricSourceType2 == BiometricSourceType.FINGERPRINT ? keyguardSecUpdateMonitorImpl.mBiometricId : -1));
                    keyguardSecUpdateMonitorImpl.mContext.sendBroadcast(intent2);
                }
            });
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void sendKeyguardStateUpdated(final boolean z, final boolean z2, final boolean z3, final boolean z4) {
        StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("sendKeyguardStateUpdated(", ", ", ", ", z, z2);
        sbM.append(z3);
        sbM.append(", ");
        sbM.append(z4);
        sbM.append(")");
        Log.d("KeyguardUpdateMonitor", sbM.toString());
        if (!this.mKeyguardShowing && z) {
            this.mHandler.postDelayed(this.mWaitingFocusRunnable, 500L);
        }
        this.mBackgroundExecutor.execute(new Runnable() { // from class: com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda33
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl = this.f$0;
                boolean z5 = z;
                boolean z6 = z2;
                boolean z7 = z3;
                boolean z8 = z4;
                SemBioFaceManager semBioFaceManager = KeyguardSecUpdateMonitorImpl.sFaceManager;
                keyguardSecUpdateMonitorImpl.getClass();
                Intent intent = new Intent("com.samsung.keyguard.KEYGUARD_STATE_UPDATE");
                intent.putExtra("showing", z5);
                intent.putExtra("occluded", z6);
                intent.putExtra("bouncerShowing", z7);
                intent.putExtra("bouncerModeChanging", z8);
                intent.putExtra("timeStamp", System.currentTimeMillis());
                Log.d("KeyguardUpdateMonitor", "broadcast intent= " + intent);
                keyguardSecUpdateMonitorImpl.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
            }
        });
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void sendPrimaryBouncerVisibilityChanged(boolean z) {
        Message messageObtainMessage = this.mHandler.obtainMessage(VolteConstants.ErrorCode.CALL_18X_RETRANSMISSION_TIMEOUT);
        messageObtainMessage.arg1 = z ? 1 : 0;
        messageObtainMessage.sendToTarget();
    }

    public final void sendScreenStatus(boolean z) {
        if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
            int i = (((!this.mDeviceInteractive && !this.mIsEarlyWakeUp) || this.mGoingToSleep || this.mIsDreamingForBiometrics) ? 0 : 1) ^ 1;
            if ((this.mFpInDisplayState != i || z) && this.mFpm != null) {
                KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(new StringBuilder("mFpInDisplayState is changed : "), this.mFpInDisplayState, " -> ", i, "KeyguardFingerprint");
                this.mFpInDisplayState = i;
                this.mFpm.semSetScreenStatus(i);
            }
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void setCredentialAttempted() {
        this.mCredentialAttempted = true;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void setDisableBiometricBySecurityDialog(boolean z) {
        Log.d("KeyguardFingerprint", "setDisableBiometricBySecurityDialog( " + z + " )");
        this.mDisabledBiometricBySecurityDialog = z;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void setDismissActionExist(boolean z) {
        this.mIsDismissActionExist = z;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void setDismissActionType(KeyguardConstants$KeyguardDismissActionType keyguardConstants$KeyguardDismissActionType) {
        this.mKeyguardDismissActionType = keyguardConstants$KeyguardDismissActionType;
    }

    public final void setFaceAuthenticated(boolean z) {
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        if (getFaceAuthenticated(userId) != z) {
            this.mUserFaceAuthenticated.put(userId, new KeyguardUpdateMonitor.BiometricAuthenticated(z, getFaceStrongBiometric()));
            if (z && getUserCanSkipBouncer(userId)) {
                this.mTrustManager.unlockedByBiometricForUser(userId, BiometricSourceType.FACE);
            }
            if (this.mSettingsHelper.isEnabledFaceStayOnLock()) {
                Log.d("KeyguardFace", "Lock stay is " + z + " by Face");
                dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda16(userId, 0), null);
            }
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void setFaceWidgetFullScreenMode(final boolean z) {
        Assert.isMainThread();
        if (this.mIsFaceWidgetFullScreenMode != z) {
            CarrierTextManager$$ExternalSyntheticOutline0.m(new StringBuilder("setFaceWidgetFullScreenMode(), enabled = "), this.mIsFaceWidgetFullScreenMode, " -> ", z, "KeyguardUpdateMonitor");
            this.mIsFaceWidgetFullScreenMode = z;
            if (isUnlockCompleted()) {
                if (isFingerprintOptionEnabled() || isFaceOptionEnabled()) {
                    if (z) {
                        updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_FULL_SCREEN_FACE_WIDGET);
                    } else {
                        this.mHandler.postDelayed(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda11(this, 4), 300L);
                    }
                }
                this.mHandler.post(new Runnable() { // from class: com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda28
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardSecUpdateMonitorImpl keyguardSecUpdateMonitorImpl = this.f$0;
                        boolean z2 = z;
                        SemBioFaceManager semBioFaceManager = KeyguardSecUpdateMonitorImpl.sFaceManager;
                        keyguardSecUpdateMonitorImpl.getClass();
                        keyguardSecUpdateMonitorImpl.dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda24(z2, 2), null);
                    }
                });
            }
        }
    }

    public final void setFingerprintAuthenticated(boolean z) {
        EmergencyButtonController$$ExternalSyntheticOutline0.m("setFingerprintAuthenticated : ", "KeyguardUpdateMonitor", z);
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        if (getFingerprintAuthenticated(userId) != z) {
            SparseArray<KeyguardUpdateMonitor.BiometricAuthenticated> sparseArray = this.mUserFingerprintAuthenticated;
            KeyguardUpdateMonitor.BiometricAuthenticated biometricAuthenticated = sparseArray.get(((UserTrackerImpl) this.mUserTracker).getUserId());
            sparseArray.put(userId, new KeyguardUpdateMonitor.BiometricAuthenticated(z, biometricAuthenticated != null && biometricAuthenticated.mIsStrongBiometric));
            if (z && getUserCanSkipBouncer(userId)) {
                this.mTrustManager.unlockedByBiometricForUser(userId, BiometricSourceType.FINGERPRINT);
            }
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void setFocusForBiometrics(int i, boolean z) {
        StringBuilder sb = new StringBuilder("setFocusForBiometrics : ");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sb, this.mHasFocus, " -> ", z, ", Focus window : ");
        sb.append(this.mFocusWindow);
        sb.append(" -> ");
        sb.append(i);
        String string = sb.toString();
        if (z) {
            if (!this.mHasFocus) {
                this.mHasFocus = true;
            } else if (this.mFocusWindow == i) {
                return;
            }
            this.mFocusWindow = i;
        } else {
            if (!this.mHasFocus) {
                return;
            }
            if (this.mFocusWindow != i) {
                KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(new StringBuilder("setFocusForBiometrics : Cannot change focus. current Window is "), this.mFocusWindow, ", reqWin : ", i, "KeyguardUpdateMonitor");
                return;
            } else {
                this.mHasFocus = false;
                this.mFocusWindow = 0;
            }
        }
        Log.d("KeyguardUpdateMonitor", string);
        if (!LsRune.SUBSCREEN_LARGE_FRONT_SUB_DISPLAY || this.mDisplayLifecycle.mIsFolderOpened) {
            if (isKeyguardVisible() || this.mPrimaryBouncerFullyShown) {
                if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && isFingerprintOptionEnabled()) {
                    this.mHandler.post(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda11(this, 2));
                }
                if (isFaceOptionEnabled()) {
                    this.mHandler.post(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda11(this, 3));
                }
            }
        }
    }

    public final void setFodStrictMode(boolean z) {
        if (!LsRune.SECURITY_FINGERPRINT_IN_DISPLAY || this.mIsFODStrictMode == z) {
            return;
        }
        int strongAuthForUser = this.mStrongAuthTracker.getStrongAuthForUser(((UserTrackerImpl) this.mUserTracker).getUserId());
        if (z && this.mFingerPrintBadQualityCounts.get(((UserTrackerImpl) this.mUserTracker).getUserId(), 0) < 10 && strongAuthForUser == 0) {
            return;
        }
        StringBuilder sbM = KeyguardFMMViewController$$ExternalSyntheticOutline0.m("setFodStrictMode : ", strongAuthForUser, " strongAuth : ", z, " callStack : ");
        sbM.append(Debug.getCallers(15));
        KeyguardDumpLog.log("KeyguardFingerprint", LogLevel.DEBUG, sbM.toString(), null);
        this.mIsFODStrictMode = z;
        FingerprintManager fingerprintManager = this.mFpm;
        if (fingerprintManager != null) {
            fingerprintManager.semSetFodStrictMode(z);
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void setHasRedactedNotifications(boolean z) {
        this.mHasRedactedNotifications = z;
    }

    public final void setIsRunningBlackMemo(boolean z) {
        if (this.mIsRunningBlackMemo != z) {
            CarrierTextManager$$ExternalSyntheticOutline0.m(new StringBuilder("setIsRunningBlackMemo : "), this.mIsRunningBlackMemo, " -> ", z, "KeyguardUpdateMonitor");
            this.mIsRunningBlackMemo = z;
            if (isFingerprintOptionEnabled()) {
                updateFingerprintListeningState(2);
            }
            ((PluginAODManager) this.mPluginAODManagerLazy.get()).updateAnimateScreenOff();
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void setKeyguardGoingAway(boolean z) {
        boolean z2 = this.mKeyguardGoingAway;
        if (z2 == z) {
            return;
        }
        Log.d("KeyguardUpdateMonitor", "setKeyguardGoingAway( " + z2 + " -> " + z + " )");
        super.setKeyguardGoingAway(z);
        if (z) {
            updateBiometricListeningState(1, FaceAuthUiEvent.FACE_AUTH_STOPPED_KEYGUARD_GOING_AWAY);
        } else {
            updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_STOPPED_KEYGUARD_GOING_AWAY);
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void setKeyguardShowing(boolean z, boolean z2) {
        boolean z3 = this.mKeyguardOccluded != z2;
        boolean z4 = this.mKeyguardShowing;
        boolean z5 = z4 != z;
        if (z3 || z5) {
            if (!z4 && z) {
                this.mHandler.postDelayed(this.mWaitingFocusRunnable, 500L);
            }
            boolean zIsKeyguardVisible = isKeyguardVisible();
            this.mKeyguardShowing = z;
            this.mKeyguardOccluded = z2;
            setScreenSaverRunningState();
            boolean zIsKeyguardVisible2 = isKeyguardVisible();
            this.mLogger.logKeyguardShowingChanged(z, z2, zIsKeyguardVisible2);
            if (LsRune.SECURITY_BACKGROUND_AUTHENTICATION && !z2) {
                this.mIsFPCanceledByForegroundApp = false;
            }
            if (zIsKeyguardVisible2 != zIsKeyguardVisible) {
                if (zIsKeyguardVisible2) {
                    this.mSecureCameraLaunched = false;
                }
                dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda24(zIsKeyguardVisible2, 1), null);
            }
            if (!zIsKeyguardVisible2 && !this.mPrimaryBouncerFullyShown) {
                setUnlockingKeyguard(false);
            }
            updateFingerprintListeningState(2);
            if (this.mKeyguardUnlocking || !isUnlockCompleted()) {
                return;
            }
            updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_KEYGUARD_VISIBILITY_CHANGED);
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final long setLockoutAttemptDeadline(int i, int i2) {
        long lockoutAttemptDeadline = this.mLockPatternUtils.setLockoutAttemptDeadline(i, i2);
        long j = i2;
        if (lockoutAttemptDeadline != this.mLockoutAttemptDeadline || j != this.mLockoutAttemptTimeout) {
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "setLockoutAttemptDeadline() userId ", ", AD:");
            sbM.append(this.mLockoutAttemptDeadline);
            sbM.append("->");
            sbM.append(lockoutAttemptDeadline);
            sbM.append(", AT:");
            sbM.append(this.mLockoutAttemptTimeout);
            sbM.append("->");
            sbM.append(j);
            String string = sbM.toString();
            this.mLockoutAttemptDeadline = lockoutAttemptDeadline;
            this.mLockoutAttemptTimeout = j;
            addAdditionalLog(string);
            dispatchLockModeChanged();
        }
        return this.mLockoutAttemptDeadline;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void setNowBarExpandMode(boolean z) {
        if (this.mIsNowBarExpandMode != z) {
            CarrierTextManager$$ExternalSyntheticOutline0.m(new StringBuilder("setNowBarExpandMode() mIsNowBarExpandMode = "), this.mIsNowBarExpandMode, " -> ", z, "KeyguardUpdateMonitor");
            this.mIsNowBarExpandMode = z;
            if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && isFingerprintOptionEnabled()) {
                updateFingerprintListeningState(2);
            }
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void setPanelExpandingStarted(boolean z) {
        boolean z2 = (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && isFingerprintOptionEnabled()) ? z : false;
        if (isFaceOptionEnabled() && !z) {
            updateFaceListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_QS_FULLY_EXPANDED);
        }
        if (this.mIsPanelExpandingStarted != z2) {
            CarrierTextManager$$ExternalSyntheticOutline0.m(new StringBuilder("setPanelExpandingStarted() mIsPanelExpandingStarted = "), this.mIsPanelExpandingStarted, " -> ", z2, "KeyguardUpdateMonitor");
            this.mIsPanelExpandingStarted = z2;
            updateFingerprintListeningState(2);
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void setScreenOrientationNoSensorValue(boolean z) {
        this.mScreenOrientationNoSensorValue = z;
    }

    public final void setScreenSaverRunningState() {
        boolean z = this.mIsScreenSaverRunning;
        boolean z2 = this.mIsDreaming && this.mKeyguardOccluded;
        this.mIsScreenSaverRunning = z2;
        if (z2 != z) {
            ActionBarContextView$$ExternalSyntheticOutline0.m(RowView$$ExternalSyntheticOutline0.m(" setScreenSaverRunningState : screen saver : ", " -> ", z), this.mIsScreenSaverRunning, "KeyguardUpdateMonitor");
            if (this.mIsScreenSaverRunning) {
                clearFingerprintRecognized();
                setFaceAuthenticated(false);
                setFingerprintAuthenticated(false);
            }
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void setShortcutLaunchInProgress(boolean z) {
        if (this.mIsShortcutLaunchInProgress != z) {
            CarrierTextManager$$ExternalSyntheticOutline0.m(new StringBuilder("setShortcutLaunchInProgress() mIsShortcutLaunchInProgress = "), this.mIsShortcutLaunchInProgress, " -> ", z, "KeyguardUpdateMonitor");
            this.mIsShortcutLaunchInProgress = z;
            if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                updateFingerprintListeningState(2);
            }
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void setSwitchingUser(boolean z) {
        addAdditionalLog("setSwitchingUser() switching: " + z);
        super.setSwitchingUser(z);
        dispatchSecureState(4094);
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void setUnlockingKeyguard(boolean z) {
        if (this.mKeyguardUnlocking == z) {
            return;
        }
        Log.d("KeyguardUpdateMonitor", "setUnlockingKeyguard( " + z + " )");
        this.mKeyguardUnlocking = z;
        if (z) {
            stopListeningForFingerprint();
            stopListeningForFace(FaceAuthUiEvent.FACE_AUTH_UPDATED_KEYGUARD_UNLOCKING);
            String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
            dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda15(2), null);
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void setupLocked$2() {
        Log.d("KeyguardUpdateMonitor", "setupLocked");
        this.mSystemReady = true;
        int currentUser = ActivityManager.getCurrentUser();
        Log.d("KeyguardUpdateMonitor", "ActivityManager.getCurrentUser() = " + currentUser);
        updateCredentialType(currentUser);
        boolean zEquals = false;
        updateFMMLock(currentUser, false);
        updateCarrierLock(currentUser);
        updatePermanentLock(currentUser);
        updateSecureLockTimeout(currentUser);
        updateBiometricLockTimeout(currentUser);
        updateLockscreenDisabled(currentUser);
        updateBiometricsOptionState(currentUser);
        updateOwnerInfo(currentUser);
        updateDeviceOwnerInfo();
        this.mHandler.obtainMessage(VolteConstants.ErrorCode.CALL_SESSION_ABORT).sendToTarget();
        this.mDisableCamera = this.mDevicePolicyManager.getCameraDisabled(null, ((UserTrackerImpl) this.mUserTracker).getUserId());
        this.mMaximumFailedPasswordsForWipe = this.mDevicePolicyManager.getMaximumFailedPasswordsForWipe(null, ((UserTrackerImpl) this.mUserTracker).getUserId());
        this.mSettingsHelper.registerCallback(this.mSettingsCallbackForUPSM, this.mSettingsValueListForPSM);
        if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
            this.mSettingsHelper.registerCallback(this.mOneHandModeSettingsCallback, Settings.System.getUriFor(SettingsHelper.INDEX_ONE_HAND_MODE_RUNNING));
        }
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK || LsRune.SECURITY_SUB_DISPLAY_COVER) {
            this.mDisplayLifecycle.addObserver(this.mDisplayListener);
        }
        ComponentName componentName = new ComponentName("com.sec.android.app.kidshome", "com.sec.android.app.kidshome.apps.ui.AppsActivity");
        PackageManager packageManager = this.mContext.getPackageManager();
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        ResolveInfo resolveInfoResolveActivity = packageManager.resolveActivity(intent, 65536);
        if (resolveInfoResolveActivity != null) {
            ActivityInfo activityInfo = resolveInfoResolveActivity.activityInfo;
            zEquals = new ComponentName(activityInfo.packageName, activityInfo.name).equals(componentName);
        }
        this.mIsKidsModeRunning = zEquals;
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final boolean shouldListenForFace() {
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        if (!isFaceOptionEnabled()) {
            return false;
        }
        boolean z = this.mIsEarlyWakeUp;
        boolean z2 = this.mGoingToSleep;
        boolean z3 = this.mSwitchingUser;
        boolean z4 = this.mKeyguardGoingAway;
        boolean z5 = this.mDeviceInteractive;
        boolean z6 = LsRune.SECURITY_SUB_DISPLAY_COVER;
        boolean zIsKeyguardVisible = (!z6 || this.mDisplayLifecycle.mIsFolderOpened) ? isKeyguardVisible() : this.mKeyguardShowing;
        boolean z7 = ((!zIsKeyguardVisible && !this.mPrimaryBouncerFullyShown) || z4 || getUserUnlockedWithBiometric(userId) || z3 || ((!z5 || this.mIsDreamingForBiometrics || this.mGoingToSleep || this.mIsScreenSaverRunning) && !z) || this.mKeyguardUnlocking || !this.mSystemReady) ? false : true;
        StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("shouldListenForFace ( isFaceDefaultCondition = ", " , isKeyguardVisible = ", " , isDeviceInteractive = ", z7, zIsKeyguardVisible);
        sbM.append(z5);
        sbM.append(" , mPrimaryBouncerFullyShown = ");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, this.mPrimaryBouncerFullyShown, " , isSwitchingUser = ", z3, " , mIsDreamingForBiometrics = ");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, this.mIsDreamingForBiometrics, " , isGoingToSleep = ", z2, " , isKeyguardGoingAway = ");
        sbM.append(z4);
        sbM.append(" , mKeyguardUnlocking = ");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, this.mKeyguardUnlocking, " , isEarlyWakeUp = ", z, " , mIsScreenSaverRunning = ");
        sbM.append(this.mIsScreenSaverRunning);
        sbM.append(" , mSystemReady = ");
        sbM.append(this.mSystemReady);
        sbM.append(" , getUserUnlockedWithBiometric = ");
        sbM.append(getUserUnlockedWithBiometric(userId));
        sbM.append(" , isKeyguardShowing = ");
        sbM.append(this.mKeyguardShowing);
        sbM.append(" , isKeyguardOccluded = ");
        sbM.append(this.mKeyguardOccluded);
        sbM.append(" , mHasFocus = ");
        sbM.append(this.mHasFocus);
        sbM.append(")");
        Log.d("KeyguardFace", sbM.toString());
        if (!z7) {
            Log.d("KeyguardFace", "shouldListenForFace ( return false, Face is not default condition)");
            return false;
        }
        if (getLockoutBiometricAttemptDeadline() > 0) {
            Log.d("KeyguardFace", "shouldListenForFace ( return false, because of Biometric lockoutAttemptDeadline)");
            return false;
        }
        if (getLockoutAttemptDeadline() > 0) {
            Log.d("KeyguardFace", "shouldListenForFace ( return false, because of lockoutAttemptDeadline)");
            return false;
        }
        if (isFaceDisabled(userId)) {
            Log.e("KeyguardFace", "shouldListenForFace (return false, because face is disabled by dpm)");
            return false;
        }
        if (isForcedLock()) {
            Log.d("KeyguardFace", "shouldListenForFace ( return false, because security policy)");
            return false;
        }
        if (this.mCocktailBarWindowType == 2) {
            Log.d("KeyguardFace", "shouldListenForFace ( return false, the cocktail bar is expanded)");
            return false;
        }
        if (isCameraDisabledByPolicy()) {
            Log.d("KeyguardFace", "shouldListenForFace ( return false, the camera is block by policy)");
            return false;
        }
        if (LsRune.COVER_SUPPORTED && isCoverClosed()) {
            Log.d("KeyguardFace", "shouldListenForFace ( return false as cover is closed. )");
            return false;
        }
        if ((!this.mPrimaryBouncerFullyShown && this.mIsQsFullyExpanded) || this.mIsFaceWidgetFullScreenMode) {
            Log.d("KeyguardFace", "shouldListenForFace ( return false, because mIsQsFullyExpanded = " + this.mIsQsFullyExpanded + ", or mIsFaceWidgetFullScreen=" + this.mIsFaceWidgetFullScreenMode + " )");
            return false;
        }
        if (!isUnlockCompleted()) {
            Log.d("KeyguardFace", "shouldListenForFace ( return false, unlocking never happened )");
            return false;
        }
        if (!isUnlockingWithBiometricAllowed(getFaceStrongBiometric())) {
            Log.d("KeyguardFace", "shouldListenForFace ( return false, because isUnlockingWithBiometricAllowed() = " + isUnlockingWithBiometricAllowed(getFaceStrongBiometric()) + ")");
            return false;
        }
        if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isDeviceDisabledForMaxFailedAttempt()) {
            Log.d("KeyguardFace", "shouldListenForFace ( return false, device is locked by administrator )");
            return false;
        }
        NotiCenterPlugin.INSTANCE.getClass();
        if (NotiCenterPlugin.isNotiCenterPluginConnected() && this.mIsNotiStarShown && !this.mPrimaryBouncerFullyShown) {
            Log.d("TAG_FACE", "shouldListenForFace ( return false, NotiStar is shown )");
            return false;
        }
        if (this.mSettingsHelper.isEnabledFaceStayOnLock() && zIsKeyguardVisible && getUserHasTrust(userId)) {
            Log.d("KeyguardFace", "shouldListenForFace ( return false, getUserHasTrust() is true)");
            return false;
        }
        boolean z8 = this.mPrimaryBouncerFullyShown;
        if (!z8 && this.mIsDynamicLockViewMode) {
            Log.d("KeyguardFace", "shouldListenForFace ( return false, DynamicLockViewMode");
            return false;
        }
        if (z5 && !z8 && this.mKeyguardShowing && !this.mHasFocus && !this.mHandler.hasCallbacks(this.mWaitingFocusRunnable) && !this.mNeedSubBioAuth && !((KeyguardDisplayManager) this.mKeyguardDisplayManager.get()).isExternalDesktopWindowing()) {
            Log.d("KeyguardFace", "shouldListenForFace ( return false, Not focus on NotificationShade )");
            return false;
        }
        if (z6 && !this.mNeedSubBioAuth && !this.mDisplayLifecycle.mIsFolderOpened) {
            Log.d("KeyguardFace", "shouldListenForFace ( return false, Dual LCD folder is closed )");
            return false;
        }
        if (isForgotPasswordView()) {
            Log.d("KeyguardFace", "shouldListenForFace ( return false, Showing forgot password view )");
            return false;
        }
        if (!((KeyguardEditModeControllerImpl) ((KeyguardEditModeController) this.mKeyguardEditModeControllerLazy.get())).getVIRunning()) {
            return true;
        }
        Log.d("KeyguardFace", "shouldListenForFace ( return false, Lock Edit VI running )");
        return false;
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final boolean shouldListenForFingerprint(boolean z) {
        boolean z2;
        KeyguardUpdateMonitor.BiometricAuthenticated biometricAuthenticated;
        boolean z3;
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        boolean z4 = SystemProperties.getBoolean("keyguard.fingerprint_test", false);
        if (isFingerprintOptionEnabled()) {
            boolean z5 = this.mDeviceInteractive;
            boolean z6 = this.mSwitchingUser;
            boolean z7 = this.mKeyguardGoingAway;
            boolean z8 = this.mGoingToSleep;
            boolean zIsKeyguardVisible = isKeyguardVisible();
            boolean z9 = !isEnabledWof() ? (!zIsKeyguardVisible && !(z2 = this.mPrimaryBouncerFullyShown) && ((!LsRune.SECURITY_BACKGROUND_AUTHENTICATION || !this.mKeyguardShowing || !this.mKeyguardOccluded || z2 || this.mIsFPCanceledByForegroundApp) && !z4)) || z6 || !z5 || z8 || this.mIsDreamingForBiometrics || this.mKeyguardUnlocking || z7 || !this.mSystemReady : (!zIsKeyguardVisible && z5 && !(z3 = this.mPrimaryBouncerFullyShown) && !z8 && !z4 && (!LsRune.SECURITY_BACKGROUND_AUTHENTICATION || !this.mKeyguardShowing || !this.mKeyguardOccluded || z3 || this.mIsFPCanceledByForegroundApp)) || z6 || this.mKeyguardUnlocking || z7 || (((biometricAuthenticated = this.mUserFingerprintAuthenticated.get(userId)) != null && biometricAuthenticated.mAuthenticated) || !this.mSystemReady);
            StringBuilder sbM = EmergencyButtonController$$ExternalSyntheticOutline0.m("shouldListenForFingerprint ( isFingerprintEnabled = ", " , mKeyguardIsVisible = ", " , mDeviceInteractive = ", z9, zIsKeyguardVisible);
            sbM.append(z5);
            sbM.append(" , mPrimaryBouncerIsOrWillBeShowing = ");
            sbM.append(this.mPrimaryBouncerIsOrWillBeShowing);
            sbM.append(" , mPrimaryBouncerFullyShown = ");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, this.mPrimaryBouncerFullyShown, " , mGoingToSleep = ", z8, " , mSwitchingUser = ");
            sbM.append(z6);
            sbM.append(" , mIsDreamingForBiometrics = ");
            sbM.append(this.mIsDreamingForBiometrics);
            sbM.append(" , mKeyguardUnlocking = ");
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, this.mKeyguardUnlocking, " , mKeyguardGoingAway = ", z7, " , mKeyguardShowing = ");
            sbM.append(this.mKeyguardShowing);
            sbM.append(" , mKeyguardOccluded = ");
            sbM.append(this.mKeyguardOccluded);
            sbM.append(" , mSystemReady = ");
            sbM.append(this.mSystemReady);
            sbM.append(" , mHasFocus = ");
            ActionBarContextView$$ExternalSyntheticOutline0.m(sbM, this.mHasFocus, "KeyguardFingerprint");
            if (z9) {
                if (getLockoutBiometricAttemptDeadline() > 0) {
                    Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, because of Biometric lockoutAttemptDeadline )");
                    return false;
                }
                if (getLockoutAttemptDeadline() > 0) {
                    Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, because of lockoutAttemptDeadline )");
                    return false;
                }
                if (isFingerprintDisabled(userId)) {
                    Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, because fingerprint is disabled by dpm )");
                    return false;
                }
                if (isForcedLock()) {
                    Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, because lock to force a lockscreen )");
                    return false;
                }
                if (LsRune.COVER_SUPPORTED && isCoverClosed()) {
                    Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false as cover is closed. )");
                    return false;
                }
                if (isEnabledWof() && ((!z5 || this.mIsDreamingForBiometrics) && isFingerprintDisabledWithBadQuality())) {
                    Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( bad quality count is maximum. )");
                    return false;
                }
                if (is2StepVerification() && isAuthenticatedWithBiometric(userId)) {
                    Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, authenticated with biometric)");
                    return false;
                }
                if (((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isDeviceDisabledForMaxFailedAttempt()) {
                    Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, device is locked by administrator )");
                    return false;
                }
                if (LsRune.SECURITY_SUB_DISPLAY_COVER && (((!this.mForceStartFinger && !this.mNeedSubBioAuth && !this.mNeedSubWofFpAuth) || is2StepVerification()) && !this.mDisplayLifecycle.mIsFolderOpened)) {
                    Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, folder is closed )");
                    return false;
                }
                if (this.mSettingsHelper.isScreenOffMemoEnabled() && this.mIsRunningBlackMemo) {
                    Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, because Screen off Memo is running. )");
                    return false;
                }
                if (!isUnlockingWithBiometricAllowed(true)) {
                    Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, unlocking with biometric not allowed. )");
                    return false;
                }
                if (is2StepVerification() && Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class) != null && ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isDualDarDeviceOwner(userId) && ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isDualDarInnerAuthRequired(userId) && ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).getDualDarInnerLockoutAttemptDeadline$1() > 0) {
                    Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, because of lockoutAttemptDeadline of dualdar do inner user )");
                    return false;
                }
                if (this.mIsFPCanceledByProximity) {
                    Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, screen off by proximity)");
                    return false;
                }
                if (isForgotPasswordView()) {
                    Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, Showing forgot password view )");
                    return false;
                }
                if (((KeyguardEditModeControllerImpl) ((KeyguardEditModeController) this.mKeyguardEditModeControllerLazy.get())).getVIRunning()) {
                    Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, Lock Edit VI running )");
                    return false;
                }
                if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                    NotiCenterPlugin.INSTANCE.getClass();
                    if (NotiCenterPlugin.isNotiCenterPluginConnected() && this.mIsNotiStarShown && !this.mPrimaryBouncerFullyShown && z5) {
                        Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, NotiStar is shown )");
                        return false;
                    }
                    boolean z10 = this.mPrimaryBouncerFullyShown;
                    if ((!z10 && this.mIsQsFullyExpanded) || this.mIsFaceWidgetFullScreenMode) {
                        Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, because mIsQsFullyExpanded = " + this.mIsQsFullyExpanded + ", or mIsFaceWidgetFullScreen=" + this.mIsFaceWidgetFullScreenMode + " )");
                        return false;
                    }
                    if (this.mIsShortcutLaunchInProgress) {
                        Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, shortcut preview is showing )");
                        return false;
                    }
                    if (!z10 && this.mIsPanelExpandingStarted) {
                        Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, quick panel is showing )");
                        return false;
                    }
                    if (this.mSettingsHelper.isOneHandModeRunning()) {
                        Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, one hand mode is running)");
                        return false;
                    }
                    if (this.mCocktailBarWindowType == 2) {
                        Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, the cocktail bar is expanded)");
                        return false;
                    }
                    if (z5 && !this.mIsDreamingForBiometrics && zIsKeyguardVisible && getUserHasTrust(userId)) {
                        Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, getUserHasTrust() is true)");
                        return false;
                    }
                    if (Process.myUserHandle().getIdentifier() != 0) {
                        Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, this process is for the sub-user)");
                        return false;
                    }
                    boolean z11 = this.mPrimaryBouncerFullyShown;
                    if (!z11 && this.mIsDynamicLockViewMode) {
                        Log.d("KeyguardUpdateMonitor", "shouldListenForFingerprint ( return false, DynamicLockViewMode");
                        return false;
                    }
                    if (z11 && this.mSIPShown) {
                        Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, SIP is showing )");
                        return false;
                    }
                    if (z5 && this.mKeyguardShowing && !this.mHasFocus && !this.mHandler.hasCallbacks(this.mWaitingFocusRunnable) && !z8 && !((DesktopManager) this.mDesktopManagerLazy.get()).isDualView() && !((KeyguardDisplayManager) this.mKeyguardDisplayManager.get()).isExternalDesktopWindowing()) {
                        Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, Not focus on NotificationShade )");
                        return false;
                    }
                    if (getFaceAuthenticated(userId)) {
                        Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, Face authenticated)");
                        return false;
                    }
                    if (((DesktopManager) this.mDesktopManagerLazy.get()).isDualView() && !this.mPrimaryBouncerFullyShown) {
                        try {
                            for (SemWindowManager.VisibleWindowInfo visibleWindowInfo : this.mWindowManagerService.getVisibleWindowInfoList()) {
                                if (visibleWindowInfo.type == 2009) {
                                    Log.d("KeyguardUpdateMonitor", "hasPopupOnDualView " + visibleWindowInfo.name + " is showing");
                                    Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, Popup showing on Dex dual view )");
                                    return false;
                                }
                            }
                        } catch (RemoteException e) {
                            Log.e("KeyguardUpdateMonitor", "Fail to check windows by RemoteException", e);
                        } catch (NullPointerException e2) {
                            Log.e("KeyguardUpdateMonitor", e2.toString());
                        }
                    }
                    if (this.mIsNowBarExpandMode && !this.mPrimaryBouncerFullyShown) {
                        Log.d("KeyguardFingerprint", "shouldListenForFingerprint ( return false, NowBarExpandMode)");
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void startListeningForFingerprint(boolean z) {
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        if (!isUnlockWithFingerprintPossible(userId)) {
            Log.d("KeyguardFingerprint", "Can't start startListeningForFingerprint()");
            return;
        }
        this.mFingerprintAuthenticationSequence++;
        KeyguardDumpLog.log("KeyguardFingerprint", LogLevel.DEBUG, "startListeningForFingerprint", null);
        Log.d("KeyguardFingerprint", "startListeningForFingerprint() " + this.mFingerprintAuthenticationSequence + "\n    callers: " + Debug.getCallers(3, " - "));
        CancellationSignal cancellationSignal = this.mFingerprintCancelSignal;
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
            this.mFingerprintCancelSignal = null;
        }
        if (!this.mContext.getPackageManager().hasSystemFeature("android.hardware.fingerprint")) {
            Log.e("KeyguardFingerprint", "startListeningForFingerprint() return - fingerprint service is not supported");
            return;
        }
        this.mFingerprintCancelSignal = new CancellationSignal();
        FingerprintManager fingerprintManager = new FingerprintManager(this.mContext, IFingerprintService.Stub.asInterface(ServiceManager.getService("fingerprint")));
        if (!isEncryptedOrLockdown(userId)) {
            fingerprintManager.authenticate(null, this.mFingerprintCancelSignal, new SecFpAuthCallback(this.mFingerprintAuthenticationSequence, this.mFpMsgConsumer, new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda11(this, 1)), this.mAuthHandler, -1, userId, 0);
        } else if (is2StepVerification()) {
            fingerprintManager.authenticate(null, this.mFingerprintCancelSignal, new SecFpAuthCallback(this.mFingerprintAuthenticationSequence, this.mFpMsgConsumer, new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda11(this, 1)), this.mAuthHandler, new FingerprintAuthenticateOptions.Builder().setUserId(userId).build(), true);
        } else {
            fingerprintManager.detectFingerprint(this.mFingerprintCancelSignal, this.mFingerprintDetectionCallback, new FingerprintAuthenticateOptions.Builder().setUserId(userId).build());
        }
        setFingerprintRunningState(1);
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void stopListeningForFace(FaceAuthUiEvent faceAuthUiEvent) {
        if (this.mFaceRunningState == 0) {
            if (isFaceOptionEnabled()) {
                RecyclerView$$ExternalSyntheticOutline0.m(this.mFaceRunningState, "KeyguardFace", new StringBuilder("Can't stop stopListeningForFace(), mFaceRunningState = "));
                return;
            }
            return;
        }
        com.android.systemui.keyguard.Log.d("KeyguardFace", "stopListeningForFace()");
        KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
        int i = this.mFaceRunningState;
        String reason = faceAuthUiEvent.getReason();
        keyguardUpdateMonitorLogger.getClass();
        LogLevel logLevel = LogLevel.VERBOSE;
        KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(3);
        LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.int1 = i;
        logMessageImpl.str1 = reason;
        logBuffer.commit(logMessageObtain);
        CancellationSignal cancellationSignal = this.mSemFaceCancelSignal;
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
            this.mSemFaceCancelSignal = null;
        }
        this.mIsFaceReady = false;
        setFaceRunningState(0);
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void stopListeningForFingerprint() {
        Log.d("KeyguardUpdateMonitor", "stopListeningForFingerprint()");
        CancellationSignal cancellationSignal = this.mFingerprintCancelSignal;
        if (cancellationSignal != null) {
            cancellationSignal.cancel();
            this.mFingerprintCancelSignal = null;
        }
        setFingerprintRunningState(0);
        String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void updateBatteryRemainTime(Intent intent) {
        if (intent.getLongExtra("remaining_charging_time", -1L) > 0) {
            this.mKeyguardBatteryStatus.remaining = intent.getLongExtra("remaining_charging_time", 0L) * 1000;
        }
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void updateBiometricListeningState(int i, FaceAuthUiEvent faceAuthUiEvent) {
        Log.d("KeyguardUpdateMonitor", "updateBiometricListeningState()");
        if (this.mAuthHandler == null) {
            HandlerThread handlerThread = new HandlerThread("mAuthHandler", 10);
            handlerThread.start();
            this.mAuthHandler = handlerThread.getThreadHandler();
        }
        updateFingerprintListeningState(i);
        updateFaceListeningState(i, faceAuthUiEvent);
    }

    public final boolean updateBiometricLockTimeout(int i) {
        long biometricAttemptDeadline = this.mLockPatternUtils.getBiometricAttemptDeadline(i);
        long biometricAttemptTimeout = this.mLockPatternUtils.getBiometricAttemptTimeout(i);
        if (this.mLockoutBiometricAttemptDeadline == biometricAttemptDeadline && this.mLockoutBiometricAttemptTimeout == biometricAttemptTimeout) {
            return false;
        }
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "updateBiometricLockTimeout() userId ", ", BD:");
        sbM.append(this.mLockoutBiometricAttemptDeadline);
        sbM.append("->");
        sbM.append(biometricAttemptDeadline);
        sbM.append(", BT:");
        sbM.append(this.mLockoutBiometricAttemptTimeout);
        sbM.append("->");
        sbM.append(biometricAttemptTimeout);
        String string = sbM.toString();
        this.mLockoutBiometricAttemptDeadline = biometricAttemptDeadline;
        this.mLockoutBiometricAttemptTimeout = biometricAttemptTimeout;
        addAdditionalLog(string);
        return true;
    }

    public final boolean updateBiometricsOptionState(int i) {
        int biometricType = this.mLockPatternUtils.getBiometricType(i);
        boolean z = this.mLockPatternUtils.getBiometricState(1, i) == 1;
        boolean z2 = this.mLockPatternUtils.getBiometricState(256, i) == 1;
        boolean z3 = this.mBiometricsFace.get(i) != z2;
        if (this.mBiometricType.get(i) == biometricType && this.mBiometricsFingerprint.get(i) == z && this.mBiometricsFace.get(i) == z2) {
            return false;
        }
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "updateBiometricsOptionState() userId ", ", BT:");
        sbM.append(this.mBiometricType.get(i));
        sbM.append("->");
        sbM.append(biometricType);
        sbM.append(", FP:");
        sbM.append(this.mBiometricsFingerprint.get(i));
        sbM.append("->");
        sbM.append(z);
        sbM.append(", FC:");
        sbM.append(this.mBiometricsFace.get(i));
        sbM.append("->");
        sbM.append(z2);
        String string = sbM.toString();
        this.mBiometricType.put(i, biometricType);
        this.mBiometricsFingerprint.put(i, z);
        this.mBiometricsFace.put(i, z2);
        addAdditionalLog(string);
        if (z3) {
            dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda24(z2, 0), null);
        }
        return true;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean updateCarrierLock(int i) {
        boolean zUpdateCarrierLock = this.mLockPatternUtils.updateCarrierLock(i);
        if (zUpdateCarrierLock == this.mCarrierLock) {
            return false;
        }
        String strM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "updateCarrierLock() userId ", ", CR:"), this.mCarrierLock, "->", zUpdateCarrierLock);
        this.mCarrierLock = zUpdateCarrierLock;
        addAdditionalLog(strM);
        return true;
    }

    public final boolean updateCredentialType(int i) {
        int credentialTypeForUser = this.mLockPatternUtils.getCredentialTypeForUser(i);
        if (this.mCredentialType == credentialTypeForUser) {
            return false;
        }
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "updateCredentialType() userId ", ", credentialType:");
        sbM.append(this.mCredentialType);
        sbM.append("->");
        sbM.append(credentialTypeForUser);
        String string = sbM.toString();
        this.mCredentialType = credentialTypeForUser;
        StringBuilder sbM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, ", isSecure=");
        sbM2.append(isSecure());
        addAdditionalLog(sbM2.toString());
        return true;
    }

    public final boolean updateDeviceOwnerInfo() {
        String deviceOwnerInfo = this.mLockPatternUtils.getDeviceOwnerInfo();
        String str = this.mDeviceOwnerInfoText;
        if (str != null && str.equals(deviceOwnerInfo)) {
            return false;
        }
        String str2 = "updateDeviceOwnerInfo() DO(isEmpty):" + TextUtils.isEmpty(this.mDeviceOwnerInfoText) + "->" + TextUtils.isEmpty(deviceOwnerInfo);
        this.mDeviceOwnerInfoText = deviceOwnerInfo;
        addAdditionalLog(str2);
        return true;
    }

    public final void updateDualDARInnerLockscreenRequirement(int i) {
        boolean z = this.mIsDualDarInnerAuthRequired;
        boolean z2 = Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class) != null && ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isDualDarDeviceOwner(i) && ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).isDualDarInnerAuthRequired(i);
        if (z2) {
            boolean zIsSecure = isSecure(((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).getInnerAuthUserId(i));
            EmergencyButtonController$$ExternalSyntheticOutline0.m("DualDAR Inner isSecure? ", "KeyguardUpdateMonitor", zIsSecure);
            if (!zIsSecure) {
                z2 = false;
            }
        }
        EmergencyButtonController$$ExternalSyntheticOutline0.m("Inner lockscreen is required? ", "KeyguardUpdateMonitor", z2);
        if (z2 && !z) {
            this.mIsDualDarInnerAuthRequired = true;
        } else if (z2 || !z) {
            return;
        } else {
            this.mIsDualDarInnerAuthRequired = false;
        }
        dispatchCallback(new KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda16(i, 1), null);
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void updateEsimState(int i, int i2) {
        int i3;
        if (i2 == 0) {
            synchronized (this.mSimDataLockObject) {
                try {
                    for (KeyguardUpdateMonitor.SimData simData : this.mSimDatasBySlotId.values()) {
                        if (simData.slotId == i && ((i3 = simData.simState) == 2 || i3 == 3)) {
                            int simState = this.mTelephonyManager.getSimState(i);
                            if (simState == 2 || simState == 3) {
                                Log.d("KeyguardUpdateMonitor", "    handleSimStateChange : [Skip] updateEsimState [subId=" + simData.subId + ", slotId=" + i + "] TelephonyManager state [" + simState + "] is not matched with request state");
                            } else {
                                Log.d("KeyguardUpdateMonitor", "    handleSimStateChange : updateEsimState [subId=" + simData.subId + ", slotId=" + i + "] state from [" + simData.simState + "] to [SIM_STATE_UNKNOWN]");
                                simData.simState = i2;
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean updateFMMLock(int i, boolean z) {
        boolean zIsFMMLockEnabled = this.mLockPatternUtils.isFMMLockEnabled(i);
        updateRemoteLockInfo(new RemoteLockInfo.Builder(0, zIsFMMLockEnabled).setMessage(this.mSettingsHelper.getFMMMessage()).setPhoneNumber(this.mSettingsHelper.getFMMPhone()).build());
        if (this.mFMMLock == zIsFMMLockEnabled) {
            return false;
        }
        String strM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "updateFMMLock() userId ", ", FM:"), this.mFMMLock, "->", zIsFMMLockEnabled);
        this.mFMMLock = zIsFMMLockEnabled;
        addAdditionalLog(strM);
        if (!z) {
            return true;
        }
        dispatchLockModeChanged();
        return true;
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void updateFaceListeningState(int i, FaceAuthUiEvent faceAuthUiEvent) {
        SemBioFaceManager semBioFaceManager;
        SemBioFaceManager semBioFaceManager2;
        if (!shouldListenForFace()) {
            if (i == 0) {
                return;
            }
            stopListeningForFace(faceAuthUiEvent);
            return;
        }
        if (i == 1) {
            return;
        }
        boolean zIsUnlockWithFacePossible = isUnlockWithFacePossible(((UserTrackerImpl) this.mUserTracker).getUserId());
        if (this.mFaceRunningState != 1) {
            Context context = this.mContext;
            synchronized (KeyguardSecUpdateMonitorImpl.class) {
                try {
                    if (sFaceManager == null) {
                        sFaceManager = SemBioFaceManager.getInstance(context);
                    }
                    semBioFaceManager = sFaceManager;
                } finally {
                }
            }
            if (semBioFaceManager != null) {
                com.android.systemui.keyguard.Log.d("KeyguardFace", "startListeningForFace()");
                setFaceAuthenticated(false);
                CancellationSignal cancellationSignal = this.mSemFaceCancelSignal;
                if (cancellationSignal != null) {
                    KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = this.mLogger;
                    int i2 = this.mFaceRunningState;
                    keyguardUpdateMonitorLogger.getClass();
                    LogLevel logLevel = LogLevel.ERROR;
                    KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(11);
                    LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
                    LogMessage logMessageObtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
                    LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                    logMessageImpl.int1 = i2;
                    logMessageImpl.bool1 = zIsUnlockWithFacePossible;
                    logBuffer.commit(logMessageObtain);
                    cancellationSignal.cancel();
                    this.mSemFaceCancelSignal = null;
                }
                this.mSemFaceCancelSignal = new CancellationSignal();
                setFaceRunningState(1);
                KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger2 = this.mLogger;
                int i3 = this.mFaceRunningState;
                keyguardUpdateMonitorLogger2.getClass();
                LogLevel logLevel2 = LogLevel.VERBOSE;
                KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda3(0);
                LogBuffer logBuffer2 = keyguardUpdateMonitorLogger2.logBuffer;
                LogMessage logMessageObtain2 = logBuffer2.obtain("KeyguardUpdateMonitorLog", logLevel2, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda3, null);
                LogMessageImpl logMessageImpl2 = (LogMessageImpl) logMessageObtain2;
                logMessageImpl2.int1 = i3;
                logMessageImpl2.str1 = faceAuthUiEvent.getReason();
                logMessageImpl2.str2 = faceAuthUiEvent.extraInfoToString();
                logBuffer2.commit(logMessageObtain2);
                KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger3 = this.mLogger;
                keyguardUpdateMonitorLogger3.getClass();
                LogLevel logLevel3 = LogLevel.DEBUG;
                KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda12 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(6);
                LogBuffer logBuffer3 = keyguardUpdateMonitorLogger3.logBuffer;
                LogMessage logMessageObtain3 = logBuffer3.obtain("KeyguardUpdateMonitorLog", logLevel3, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda12, null);
                ((LogMessageImpl) logMessageObtain3).bool1 = zIsUnlockWithFacePossible;
                logBuffer3.commit(logMessageObtain3);
                Context context2 = this.mContext;
                synchronized (KeyguardSecUpdateMonitorImpl.class) {
                    try {
                        if (sFaceManager == null) {
                            sFaceManager = SemBioFaceManager.getInstance(context2);
                        }
                        semBioFaceManager2 = sFaceManager;
                    } finally {
                    }
                }
                semBioFaceManager2.authenticate((SemBioFaceManager.CryptoObject) null, this.mSemFaceCancelSignal, 0, new SecFaceAuthCallback(this.mFaceMsgConsumer), this.mAuthHandler, ((UserTrackerImpl) this.mUserTracker).getUserId(), (Bundle) null, (View) null);
                return;
            }
        }
        RecyclerView$$ExternalSyntheticOutline0.m(this.mFaceRunningState, "KeyguardFace", new StringBuilder("Can't start startListeningForFace(), mFaceRunningState = "));
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void updateFingerprintListeningState(int i) {
        boolean zShouldListenForFingerprint = shouldListenForFingerprint(false);
        int i2 = this.mFingerprintRunningState;
        boolean z = i2 == 1 || i2 == 3;
        Log.d("KeyguardFingerprint", "updateFingerprintListeningState#mFingerprintRunningState=" + this.mFingerprintRunningState + " shouldListenForFingerprint=" + zShouldListenForFingerprint + " isUdfpsEnrolled=false bioType : " + this.mBiometricType.get(this.mSelectedUserInteractor.getSelectedUserId()));
        if (!z || zShouldListenForFingerprint) {
            if (z || !zShouldListenForFingerprint) {
                if (!z && !zShouldListenForFingerprint) {
                    String str = LsRune.VALUE_SUB_DISPLAY_POLICY;
                }
            } else {
                if (i == 1) {
                    return;
                }
                String str2 = LsRune.VALUE_SUB_DISPLAY_POLICY;
                startListeningForFingerprint(false);
            }
        } else {
            if (i == 0) {
                return;
            }
            if (this.mFingerprintRunningState == 1) {
                String str3 = LsRune.VALUE_SUB_DISPLAY_POLICY;
                stopListeningForFingerprint();
            }
        }
        if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY && zShouldListenForFingerprint) {
            sendScreenStatus(false);
        }
    }

    public final boolean updateLockscreenDisabled(int i) {
        boolean zIsLockScreenDisabled = this.mLockPatternUtils.isLockScreenDisabled(i);
        if (this.mLockscreenDisabled == zIsLockScreenDisabled) {
            return false;
        }
        String strM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "updateLockscreenDisabled() userId ", ", lockScreenDisabled:"), this.mLockscreenDisabled, "->", zIsLockScreenDisabled);
        this.mLockscreenDisabled = zIsLockScreenDisabled;
        addAdditionalLog(strM);
        return true;
    }

    public final boolean updateOwnerInfo(int i) {
        String str;
        boolean zIsOwnerInfoEnabled = this.mLockPatternUtils.isOwnerInfoEnabled(i);
        String ownerInfo = this.mLockPatternUtils.getOwnerInfo(i);
        if (this.mIsOwnerInfoEnabled == zIsOwnerInfoEnabled && (str = this.mOwnerInfoText) != null && str.equals(ownerInfo)) {
            return false;
        }
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "updateOwnerInfoEnabled() userId ", ", OE:");
        KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(sbM, this.mIsOwnerInfoEnabled, "->", zIsOwnerInfoEnabled, ", OI(isEmpty):");
        sbM.append(TextUtils.isEmpty(this.mOwnerInfoText));
        sbM.append("->");
        sbM.append(TextUtils.isEmpty(ownerInfo));
        String string = sbM.toString();
        this.mIsOwnerInfoEnabled = zIsOwnerInfoEnabled;
        this.mOwnerInfoText = ownerInfo;
        addAdditionalLog(string);
        return true;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean updatePermanentLock(int i) {
        boolean z = this.mLockPatternUtils.getCurrentFailedPasswordAttempts(i) >= getMaxFailedUnlockAttempts();
        if (this.mPermanentLock == z) {
            return false;
        }
        String strM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "updatePermanentLock() userId ", ", PML:"), this.mPermanentLock, "->", z);
        this.mPermanentLock = z;
        addAdditionalLog(strM);
        return true;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void updateRemoteLockInfo(RemoteLockInfo remoteLockInfo) {
        RemoteLockInfo remoteLockInfo2 = null;
        for (int i = 0; i < this.mRemoteLockInfo.size(); i++) {
            if (((RemoteLockInfo) this.mRemoteLockInfo.get(i)).lockType == remoteLockInfo.lockType) {
                remoteLockInfo2 = (RemoteLockInfo) this.mRemoteLockInfo.remove(i);
            }
        }
        if (remoteLockInfo.lockState) {
            this.mRemoteLockInfo.add(remoteLockInfo);
        }
        int i2 = -1;
        this.mActiveRemoteLockIndex = -1;
        for (int i3 = 0; i3 < this.mRemoteLockInfo.size(); i3++) {
            if (i2 < ((RemoteLockInfo) this.mRemoteLockInfo.get(i3)).lockType) {
                this.mActiveRemoteLockIndex = i3;
                i2 = ((RemoteLockInfo) this.mRemoteLockInfo.get(i3)).lockType;
            }
        }
        int iDiff = remoteLockInfo.diff(remoteLockInfo2);
        Log.d("KeyguardUpdateMonitor", "updateRemoteLockInfo() diff=" + Integer.toHexString(iDiff));
        if (iDiff != 0) {
            this.mHandler.sendEmptyMessage(VolteConstants.ErrorCode.CALL_STATUS_CONF_REMOVE_USER_FROM_SESSION_FAILURE);
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void updateSIPShownState(boolean z) {
        if (this.mSIPShown != z) {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("updateSIPShownState : ", "KeyguardUpdateMonitor", z);
            this.mSIPShown = z;
            if (isFingerprintOptionEnabled()) {
                updateFingerprintListeningState(2);
            }
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean updateSecureLockTimeout(int i) {
        long lockoutAttemptDeadline = this.mLockPatternUtils.getLockoutAttemptDeadline(i);
        long lockoutAttemptTimeout = this.mLockPatternUtils.getLockoutAttemptTimeout(i);
        if (this.mLockoutAttemptDeadline == lockoutAttemptDeadline && this.mLockoutAttemptTimeout == lockoutAttemptTimeout) {
            return false;
        }
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "updateSecureLockTimeout() userId ", ", AD:");
        sbM.append(this.mLockoutAttemptDeadline);
        sbM.append("->");
        sbM.append(lockoutAttemptDeadline);
        sbM.append(", AT:");
        sbM.append(this.mLockoutAttemptTimeout);
        sbM.append("->");
        sbM.append(lockoutAttemptTimeout);
        String string = sbM.toString();
        this.mLockoutAttemptDeadline = lockoutAttemptDeadline;
        this.mLockoutAttemptTimeout = lockoutAttemptTimeout;
        addAdditionalLog(string);
        long j = this.mLockoutAttemptTimeout;
        if (j <= 0) {
            return true;
        }
        this.mHandler.sendEmptyMessageDelayed(1003, j);
        return true;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void updateTspFpGuideShown(boolean z) {
        EmergencyButtonController$$ExternalSyntheticOutline0.m("updateTspFpGuideShown is set ", "KeyguardUpdateMonitor", z);
        this.mIsTspFpGuideShown = z;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void updateUserUnlockNotification(int i) throws Resources.NotFoundException {
        StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "updateUserUnlockNotification(), isUserUnlocked(", ") : ");
        sbM.append(this.mUserManager.isUserUnlocked(i));
        Log.d("KeyguardUpdateMonitor", sbM.toString());
        if (this.mUserManager.isUserUnlocked(i)) {
            this.mNotificationManager.cancelAsUser(null, 1001, UserHandle.ALL);
            return;
        }
        String string = this.mContext.getResources().getString(R.string.kg_fbe_notification_header);
        String string2 = this.mContext.getResources().getString(R.string.kg_fbe_notification_message);
        createChannels();
        this.mNotificationManager.notifyAsUser(null, 1001, new Notification.Builder(this.mContext, "fbe_channel_id").setSmallIcon(17304559).setOngoing(true).setContentTitle(string).setContentText(string2).build(), UserHandle.ALL);
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void updatedSimPinPassed(int i) {
        if (i < 0 || i > 1) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "updatedSimPinPassed  Slot Boundary Exception SlotNum: ", "KeyguardUpdateMonitor");
        } else {
            this.mSimPinPassed[i] = true;
        }
    }

    public final void dispatchCallback(final Consumer consumer, String str) {
        this.mIsDispatching = true;
        ArrayList arrayList = this.mCallbacks;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            final KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) ((WeakReference) obj).get();
            if (keyguardUpdateMonitorCallback != null) {
                if (this.mFastUnlockController.isMode(KeyguardFastBioUnlockController.MODE_FLAG_ENABLED) && KeyguardFastBioUnlockController.DEBUG) {
                    Runnable runnable = new Runnable() { // from class: com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda42
                        @Override // java.lang.Runnable
                        public final void run() {
                            Consumer consumer2 = consumer;
                            KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback2 = keyguardUpdateMonitorCallback;
                            SemBioFaceManager semBioFaceManager = KeyguardSecUpdateMonitorImpl.sFaceManager;
                            consumer2.accept(keyguardUpdateMonitorCallback2);
                        }
                    };
                    StringBuilder sb = new StringBuilder("dispatchCallback ");
                    sb.append(str != null ? str : "");
                    sb.append(" / ");
                    sb.append(keyguardUpdateMonitorCallback);
                    LogUtil.execTime(runnable, 5, "BioUnlock", sb.toString(), new Object[0]);
                } else {
                    consumer.accept(keyguardUpdateMonitorCallback);
                }
            }
        }
        this.mIsDispatching = false;
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final int getFailedBiometricUnlockAttempts(int i) {
        return this.mBiometricFailedAttempts.get(i, 0);
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean isSecure(int i) {
        return i == this.mSelectedUserInteractor.getSelectedUserId() ? getCredentialTypeForUser(i) != -1 || isSimPinSecure() || isRemoteLock() : this.mLockPatternUtils.isSecure(i) || isSimPinSecure() || isRemoteLock();
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final void runSystemUserOnly(Runnable runnable, Executor executor) {
        if (Process.myUserHandle().equals(UserHandle.SYSTEM)) {
            if (executor != null) {
                executor.execute(runnable);
            } else {
                runnable.run();
            }
        }
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final int getBiometricType(int i) {
        return this.mBiometricType.get(i);
    }

    @Override // com.android.keyguard.KeyguardSecUpdateMonitor
    public final boolean getFingerprintAuthenticated() {
        return getFingerprintAuthenticated(this.mSelectedUserInteractor.getSelectedUserId());
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void startBiometricWatchdog() {
    }

    @Override // com.android.keyguard.KeyguardUpdateMonitor
    public final void setAlternateBouncerShowing(boolean z) {
    }
}
