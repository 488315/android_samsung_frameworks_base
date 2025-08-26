package com.android.systemui.statusbar;

import android.app.AlarmManager;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.hardware.biometrics.BiometricSourceType;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.UserManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.util.Pair;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.app.IBatteryStats;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.CarrierTextController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.TrustGrantFlags;
import com.android.keyguard.logging.KeyguardLogger;
import com.android.keyguard.logging.KeyguardLogger$$ExternalSyntheticLambda0;
import com.android.settingslib.Utils;
import com.android.systemui.DejankUtils;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.FaceHelpMessageDeferral;
import com.android.systemui.biometrics.FaceHelpMessageDeferralFactory;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.bouncer.data.repository.BouncerMessageRepositoryImpl;
import com.android.systemui.bouncer.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor;
import com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractorKt;
import com.android.systemui.bouncer.shared.model.BouncerMessageModel;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFingerprintAuthInteractor;
import com.android.systemui.dock.DockManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardIndication;
import com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor;
import com.android.systemui.keyguard.util.IndicationHelper;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogBuffer$$ExternalSyntheticLambda0;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.NotificationPanelViewController$$ExternalSyntheticLambda43;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.KeyguardIndicationTextView;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.user.domain.interactor.UserLogoutInteractor;
import com.android.systemui.util.AlarmTimeout;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.kotlin.JavaAdapterKt;
import com.android.systemui.util.wakelock.SettableWakeLock;
import com.android.systemui.util.wakelock.WakeLock;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Set;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public class KeyguardIndicationController {
    public final AccessibilityManager mAccessibilityManager;
    public final AlternateBouncerInteractor mAlternateBouncerInteractor;
    public final AuthController mAuthController;
    public final DelayableExecutor mBackgroundExecutor;
    public boolean mBatteryDefender;
    public final IBatteryStats mBatteryInfo;
    public Pair mBiometricErrorMessageToShowOnScreenOn;
    public CharSequence mBiometricMessage;
    public CharSequence mBiometricMessageFollowUp;
    public final BiometricMessageInteractor mBiometricMessageInteractor;
    public BiometricSourceType mBiometricMessageSource;
    public final BouncerMessageInteractor mBouncerMessageInteractor;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public AnonymousClass3 mBroadcastReceiver;
    public int mChangingType;
    public int mChargingSpeed;
    public int mChargingStatus;
    public long mChargingTimeRemaining;
    public int mChargingWattage;
    final Consumer<Set<Integer>> mCoExAcquisitionMsgIdsToShowCallback;
    public Set mCoExFaceAcquisitionMsgIdsToShow;
    public final Context mContext;
    public final DeviceEntryFaceAuthInteractor mDeviceEntryFaceAuthInteractor;
    public final DeviceEntryFingerprintAuthInteractor mDeviceEntryFingerprintAuthInteractor;
    public final DevicePolicyManager mDevicePolicyManager;
    public final DockManager mDockManager;
    public boolean mDozing;
    public final DelayableExecutor mExecutor;
    public final FaceHelpMessageDeferral mFaceAcquiredMessageDeferral;
    public boolean mFaceLockedOutThisAuthSession;
    public final FalsingManager mFalsingManager;
    public final FeatureFlags mFeatureFlags;
    public boolean mForceIsDismissible;
    public final AnonymousClass2 mHandler;
    public final AlarmTimeout mHideBiometricMessageHandler;
    public final AlarmTimeout mHideTransientMessageHandler;
    public boolean mIncompatibleCharger;
    public ViewGroup mIndicationArea;
    public final IndicationHelper mIndicationHelper;
    public boolean mInited;
    public ColorStateList mInitialTextColorState;
    final Consumer<Boolean> mIsFingerprintEngagedCallback;
    final Consumer<Boolean> mIsLogoutEnabledCallback;
    public final KeyguardBypassController mKeyguardBypassController;
    public final KeyguardLogger mKeyguardLogger;
    public final AnonymousClass5 mKeyguardStateCallback;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public KeyguardIndicationTextView mLifeStyleIndicationView;
    public KeyguardIndicationTextView mLockScreenIndicationView;
    public boolean mOrganizationOwnedDevice;
    public boolean mPowerCharged;
    public boolean mPowerPluggedIn;
    public boolean mPowerPluggedInDock;
    public boolean mPowerPluggedInWired;
    public boolean mPowerPluggedInWireless;
    public boolean mProtectedFullyCharged;
    public KeyguardIndicationRotateTextViewController mRotateTextViewController;
    public final ScreenLifecycle mScreenLifecycle;
    public final AnonymousClass1 mScreenObserver;
    public StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final StatusBarStateController mStatusBarStateController;
    public final AnonymousClass4 mStatusBarStateListener;
    public KeyguardIndicationTextView mTopIndicationView;
    public CharSequence mTransientIndication;
    public CharSequence mTrustAgentErrorMessage;
    public CharSequence mTrustGrantedIndication;
    public KeyguardUpdateMonitorCallback mUpdateMonitorCallback;
    public NotificationPanelViewController$$ExternalSyntheticLambda43 mUpdatePosition;
    public final UserLogoutInteractor mUserLogoutInteractor;
    public final UserManager mUserManager;
    public final UserTracker mUserTracker;
    public boolean mVisible;
    public final SettableWakeLock mWakeLock;
    public boolean mIsNeededShowChargingType = false;
    public int mBatteryLevel = -1;
    public boolean mBatteryPresent = true;
    public String mSleepChargingEvent = null;
    public String mSleepChargingEventFinishTime = null;

    /* renamed from: com.android.systemui.statusbar.KeyguardIndicationController$4, reason: invalid class name */
    public class AnonymousClass4 implements StatusBarStateController.StateListener {
        public AnonymousClass4() {
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onDozingChanged(boolean z) {
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            if (keyguardIndicationController.mDozing == z) {
                return;
            }
            keyguardIndicationController.mDozing = z;
            keyguardIndicationController.setVisible(keyguardIndicationController.mStatusBarStateController.getState() == 1);
            keyguardIndicationController.setDozing(z);
            if (keyguardIndicationController.mDozing) {
                keyguardIndicationController.hideBiometricMessage();
            }
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStateChanged(int i) {
            KeyguardIndicationController.this.setVisible(i == 1);
        }
    }

    public class BaseKeyguardCallback extends KeyguardUpdateMonitorCallback {
        public BaseKeyguardCallback() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricAcquired(BiometricSourceType biometricSourceType, int i) {
            if (biometricSourceType == BiometricSourceType.FACE) {
                KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
                if (i == 20) {
                    keyguardIndicationController.hideBiometricMessage();
                    keyguardIndicationController.mBiometricErrorMessageToShowOnScreenOn = null;
                }
                keyguardIndicationController.mFaceAcquiredMessageDeferral.processFrame(i);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
            if (biometricSourceType == BiometricSourceType.FACE) {
                KeyguardIndicationController.this.mFaceAcquiredMessageDeferral.reset$1();
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
            super.onBiometricAuthenticated(i, biometricSourceType, z);
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            keyguardIndicationController.hideBiometricMessage();
            if (biometricSourceType == BiometricSourceType.FACE) {
                keyguardIndicationController.mFaceAcquiredMessageDeferral.reset$1();
                if (keyguardIndicationController.mKeyguardBypassController.canBypass()) {
                    return;
                }
                keyguardIndicationController.showActionToUnlock();
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onBiometricError(int i, String str, BiometricSourceType biometricSourceType) {
            BiometricSourceType biometricSourceType2 = BiometricSourceType.FACE;
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            if (biometricSourceType != biometricSourceType2) {
                BiometricSourceType biometricSourceType3 = BiometricSourceType.FINGERPRINT;
                if (biometricSourceType == biometricSourceType3) {
                    if (keyguardIndicationController.mIndicationHelper.shouldSuppressErrorMsg(biometricSourceType3, i)) {
                        keyguardIndicationController.mKeyguardLogger.logBiometricMessage("KIC suppressingFingerprintError", Integer.valueOf(i), str);
                        return;
                    } else {
                        keyguardIndicationController.showErrorMessageNowOrLater(str, null, biometricSourceType3);
                        return;
                    }
                }
                return;
            }
            CharSequence deferredMessage = keyguardIndicationController.mFaceAcquiredMessageDeferral.getDeferredMessage();
            keyguardIndicationController.mFaceAcquiredMessageDeferral.reset$1();
            boolean zShouldSuppressErrorMsg = keyguardIndicationController.mIndicationHelper.shouldSuppressErrorMsg(biometricSourceType2, i);
            KeyguardLogger keyguardLogger = keyguardIndicationController.mKeyguardLogger;
            if (zShouldSuppressErrorMsg) {
                keyguardLogger.logBiometricMessage("KIC suppressingFaceError", Integer.valueOf(i), str);
                return;
            }
            int i2 = R.string.keyguard_unlock;
            if (i == 3) {
                keyguardLogger.logBiometricMessage("deferred message after face auth timeout", null, String.valueOf(deferredMessage));
                if (keyguardIndicationController.canUnlockWithFingerprint()) {
                    if (deferredMessage == null || keyguardIndicationController.mStatusBarKeyguardViewManager.isBouncerShowing()) {
                        keyguardLogger.logBiometricMessage("skip showing FACE_ERROR_TIMEOUT due to co-ex logic", null, null);
                        return;
                    } else {
                        keyguardIndicationController.showBiometricMessage(deferredMessage, keyguardIndicationController.mContext.getString(R.string.keyguard_suggest_fingerprint), biometricSourceType2, false);
                        return;
                    }
                }
                if (deferredMessage == null) {
                    keyguardIndicationController.showActionToUnlock();
                    return;
                } else {
                    keyguardIndicationController.mBouncerMessageInteractor.setFaceAcquisitionMessage(deferredMessage.toString());
                    keyguardIndicationController.showBiometricMessage(deferredMessage, keyguardIndicationController.mContext.getString(R.string.keyguard_unlock), biometricSourceType2, false);
                    return;
                }
            }
            if (i != 7 && i != 9) {
                keyguardIndicationController.showErrorMessageNowOrLater(str, null, biometricSourceType2);
                return;
            }
            if (keyguardIndicationController.canUnlockWithFingerprint()) {
                i2 = R.string.keyguard_suggest_fingerprint;
            }
            String string = keyguardIndicationController.mContext.getString(i2);
            if (!keyguardIndicationController.mFaceLockedOutThisAuthSession) {
                keyguardIndicationController.mFaceLockedOutThisAuthSession = true;
                keyguardIndicationController.showErrorMessageNowOrLater(str, string, biometricSourceType2);
            } else {
                UdfpsController udfpsController = keyguardIndicationController.mAuthController.mUdfpsController;
                if (udfpsController != null ? udfpsController.mOnFingerDown : false) {
                    return;
                }
                keyguardIndicationController.showErrorMessageNowOrLater(keyguardIndicationController.mContext.getString(R.string.keyguard_face_unlock_unavailable), string, biometricSourceType2);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onBiometricHelp(int i, String str, BiometricSourceType biometricSourceType) {
            BiometricSourceType biometricSourceType2 = BiometricSourceType.FACE;
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            if (biometricSourceType == biometricSourceType2) {
                keyguardIndicationController.mFaceAcquiredMessageDeferral.updateMessage(i, str);
                if (keyguardIndicationController.mFaceAcquiredMessageDeferral.messagesToDefer.contains(Integer.valueOf(i))) {
                    return;
                }
            }
            boolean z = biometricSourceType == biometricSourceType2 && i == -3;
            if (keyguardIndicationController.mKeyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true) || z) {
                boolean z2 = (biometricSourceType != biometricSourceType2 || i == -2 || i == -3) ? false : true;
                boolean z3 = biometricSourceType == biometricSourceType2 && i == -2;
                KeyguardLogger keyguardLogger = keyguardIndicationController.mKeyguardLogger;
                if (z3 && keyguardIndicationController.mFaceLockedOutThisAuthSession) {
                    keyguardLogger.logBiometricMessage("skipped showing faceAuthFailed message due to lockout", Integer.valueOf(i), str);
                    return;
                }
                BiometricSourceType biometricSourceType3 = BiometricSourceType.FINGERPRINT;
                boolean z4 = biometricSourceType == biometricSourceType3 && i == -1;
                boolean zCanUnlockWithFingerprint = keyguardIndicationController.canUnlockWithFingerprint();
                boolean z5 = z2 && zCanUnlockWithFingerprint;
                if (z5 && !keyguardIndicationController.mCoExFaceAcquisitionMsgIdsToShow.contains(Integer.valueOf(i))) {
                    keyguardLogger.logBiometricMessage("skipped showing help message due to co-ex logic", Integer.valueOf(i), str);
                    return;
                }
                if (keyguardIndicationController.mStatusBarKeyguardViewManager.isBouncerShowing()) {
                    BouncerMessageInteractor bouncerMessageInteractor = keyguardIndicationController.mBouncerMessageInteractor;
                    if (biometricSourceType == biometricSourceType3 && !z4) {
                        BouncerMessageModel bouncerMessageModelAccess$defaultMessage = BouncerMessageInteractorKt.access$defaultMessage(bouncerMessageInteractor.getCurrentSecurityMode(), str, ((Boolean) bouncerMessageInteractor.isFingerprintAuthCurrentlyAllowedOnBouncer.$$delegate_0.getValue()).booleanValue());
                        BouncerMessageRepositoryImpl bouncerMessageRepositoryImpl = (BouncerMessageRepositoryImpl) bouncerMessageInteractor.repository;
                        bouncerMessageRepositoryImpl._bouncerMessage.setValue(bouncerMessageModelAccess$defaultMessage);
                        bouncerMessageRepositoryImpl.messageSource = biometricSourceType3;
                    } else if (z2) {
                        bouncerMessageInteractor.setFaceAcquisitionMessage(str);
                    }
                    keyguardIndicationController.mStatusBarKeyguardViewManager.setKeyguardMessage(str, keyguardIndicationController.mInitialTextColorState);
                    return;
                }
                if (keyguardIndicationController.mScreenLifecycle.mScreenState != 2) {
                    AnonymousClass2 anonymousClass2 = keyguardIndicationController.mHandler;
                    if (z3) {
                        anonymousClass2.sendMessageDelayed(anonymousClass2.obtainMessage(1), 1300L);
                        return;
                    } else {
                        keyguardIndicationController.mBiometricErrorMessageToShowOnScreenOn = new Pair(str, biometricSourceType);
                        anonymousClass2.sendMessageDelayed(anonymousClass2.obtainMessage(2), 1000L);
                        return;
                    }
                }
                if (z5 && i == 3) {
                    keyguardIndicationController.showBiometricMessage(str, keyguardIndicationController.mContext.getString(R.string.keyguard_suggest_fingerprint), biometricSourceType, false);
                    return;
                }
                if (z3 && zCanUnlockWithFingerprint) {
                    keyguardIndicationController.showBiometricMessage(keyguardIndicationController.mContext.getString(R.string.keyguard_face_failed), keyguardIndicationController.mContext.getString(R.string.keyguard_suggest_fingerprint), biometricSourceType, false);
                    return;
                }
                KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardIndicationController.mKeyguardUpdateMonitor;
                if (!z4) {
                    keyguardUpdateMonitor.getClass();
                } else if (keyguardUpdateMonitor.getUserUnlockedWithFace(keyguardIndicationController.getCurrentUser$1())) {
                    keyguardIndicationController.showBiometricMessage(keyguardIndicationController.mContext.getString(R.string.keyguard_face_successful_unlock), keyguardIndicationController.mContext.getString(R.string.keyguard_unlock), null, true);
                    return;
                }
                if (z4 && keyguardUpdateMonitor.getUserHasTrust(keyguardIndicationController.getCurrentUser$1())) {
                    keyguardIndicationController.showBiometricMessage(keyguardIndicationController.getTrustGrantedIndication(), keyguardIndicationController.mContext.getString(R.string.keyguard_unlock), null, true);
                } else if (z) {
                    keyguardIndicationController.showBiometricMessage(str, zCanUnlockWithFingerprint ? keyguardIndicationController.mContext.getString(R.string.keyguard_suggest_fingerprint) : keyguardIndicationController.mContext.getString(R.string.keyguard_unlock), biometricSourceType, false);
                } else {
                    keyguardIndicationController.showBiometricMessage(str, null, biometricSourceType, false);
                }
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) {
            if (z || biometricSourceType != BiometricSourceType.FACE) {
                return;
            }
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            keyguardIndicationController.showTrustAgentErrorMessage(keyguardIndicationController.mTrustAgentErrorMessage);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onForceIsDismissibleChanged(boolean z) {
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            keyguardIndicationController.mForceIsDismissible = z;
            keyguardIndicationController.getClass();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onLockedOutStateChanged(BiometricSourceType biometricSourceType) {
            BiometricSourceType biometricSourceType2 = BiometricSourceType.FACE;
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            if (biometricSourceType == biometricSourceType2 && !keyguardIndicationController.mKeyguardUpdateMonitor.mFaceLockedOutPermanent) {
                keyguardIndicationController.mFaceLockedOutThisAuthSession = false;
            } else if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                if (keyguardIndicationController.mKeyguardUpdateMonitor.isFingerprintLockedOut()) {
                    keyguardIndicationController.mContext.getString(R.string.keyguard_unlock);
                }
                keyguardIndicationController.getClass();
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onRefreshBatteryInfo(KeyguardBatteryStatus keyguardBatteryStatus) {
            boolean z = keyguardBatteryStatus.status == 2 || keyguardBatteryStatus.isCharged();
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            boolean z2 = keyguardIndicationController.mPowerPluggedIn;
            KeyguardLogger keyguardLogger = keyguardIndicationController.mKeyguardLogger;
            keyguardIndicationController.mPowerPluggedInWired = keyguardBatteryStatus.isPluggedInWired() && z;
            int i = keyguardBatteryStatus.plugged;
            keyguardIndicationController.mPowerPluggedInWireless = i == 4 && z;
            keyguardIndicationController.mPowerPluggedInDock = i == 8 && z;
            keyguardIndicationController.mPowerPluggedIn = keyguardBatteryStatus.isPluggedIn() && z;
            keyguardIndicationController.mPowerCharged = keyguardBatteryStatus.isCharged();
            keyguardIndicationController.mProtectedFullyCharged = keyguardBatteryStatus.protectedFully;
            keyguardIndicationController.mChargingWattage = keyguardBatteryStatus.maxChargingWattage;
            keyguardIndicationController.mChargingSpeed = keyguardBatteryStatus.getChargingSpeed(keyguardIndicationController.mContext);
            int i2 = keyguardBatteryStatus.chargingStatus;
            keyguardIndicationController.mChargingStatus = i2;
            keyguardIndicationController.mBatteryLevel = keyguardBatteryStatus.level;
            keyguardIndicationController.mBatteryPresent = keyguardBatteryStatus.present;
            keyguardIndicationController.mBatteryDefender = i2 == 4;
            int i3 = keyguardBatteryStatus.mSuperFastCharger;
            keyguardIndicationController.mChangingType = i3 == 3 ? 14 : i3 == 4 ? 15 : keyguardBatteryStatus.highVoltage ? 11 : keyguardBatteryStatus.mSlowCharger == 2 ? 2 : keyguardBatteryStatus.plugged == 4 ? keyguardBatteryStatus.online == 100 ? 13 : 12 : 10;
            if (keyguardIndicationController.mBatteryDefender) {
                keyguardBatteryStatus.isPluggedIn();
            }
            keyguardIndicationController.mIncompatibleCharger = ((Boolean) keyguardBatteryStatus.incompatibleCharger.orElse(Boolean.FALSE)).booleanValue();
            try {
                keyguardIndicationController.mChargingTimeRemaining = keyguardIndicationController.mPowerPluggedIn ? keyguardIndicationController.mBatteryInfo.computeChargeTimeRemaining() : -1L;
            } catch (RemoteException e) {
                LogLevel logLevel = LogLevel.ERROR;
                LogBuffer$$ExternalSyntheticLambda0 logBuffer$$ExternalSyntheticLambda0 = new LogBuffer$$ExternalSyntheticLambda0(0);
                LogBuffer logBuffer = keyguardLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("KeyguardIndication", logLevel, logBuffer$$ExternalSyntheticLambda0, e);
                ((LogMessageImpl) logMessageObtain).str1 = "Error calling IBatteryStats";
                logBuffer.commit(logMessageObtain);
                keyguardIndicationController.mChargingTimeRemaining = -1L;
            }
            boolean z3 = keyguardIndicationController.mPowerPluggedIn;
            int i4 = keyguardIndicationController.mBatteryLevel;
            boolean z4 = keyguardIndicationController.mBatteryDefender;
            keyguardLogger.getClass();
            LogLevel logLevel2 = LogLevel.DEBUG;
            KeyguardLogger$$ExternalSyntheticLambda0 keyguardLogger$$ExternalSyntheticLambda0 = new KeyguardLogger$$ExternalSyntheticLambda0(6);
            LogBuffer logBuffer2 = keyguardLogger.buffer;
            LogMessage logMessageObtain2 = logBuffer2.obtain("KeyguardIndication", logLevel2, keyguardLogger$$ExternalSyntheticLambda0, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain2;
            logMessageImpl.bool1 = z;
            logMessageImpl.bool2 = z3;
            logMessageImpl.bool3 = z4;
            logMessageImpl.int1 = i4;
            logBuffer2.commit(logMessageObtain2);
            AnonymousClass2 anonymousClass2 = keyguardIndicationController.mHandler;
            if (!z2 && keyguardIndicationController.mPowerPluggedIn) {
                keyguardIndicationController.mIsNeededShowChargingType = true;
                anonymousClass2.removeMessages(100);
                anonymousClass2.sendEmptyMessageDelayed(100, 3000L);
            } else {
                if (keyguardIndicationController.mPowerPluggedIn) {
                    return;
                }
                anonymousClass2.removeMessages(100);
                keyguardIndicationController.mIsNeededShowChargingType = false;
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onRequireUnlockForNfc() {
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            keyguardIndicationController.showTransientIndication(keyguardIndicationController.mContext.getString(R.string.require_unlock_for_nfc));
            keyguardIndicationController.hideTransientIndicationDelayed(4100L);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onTimeChanged() {
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            if (keyguardIndicationController.mVisible) {
                keyguardIndicationController.getClass();
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onTrustAgentErrorMessage(CharSequence charSequence) {
            KeyguardIndicationController.this.showTrustAgentErrorMessage(charSequence);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onTrustChanged(int i) {
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            if (keyguardIndicationController.getCurrentUser$1() == i) {
                keyguardIndicationController.getClass();
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onTrustGrantedForCurrentUser(boolean z, boolean z2, TrustGrantFlags trustGrantFlags, String str) {
            KeyguardIndicationController.this.mTrustGrantedIndication = str;
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitchComplete(int i) {
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            if (keyguardIndicationController.mVisible) {
                keyguardIndicationController.getClass();
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onUserUnlocked() {
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            if (keyguardIndicationController.mVisible) {
                keyguardIndicationController.getClass();
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.systemui.statusbar.KeyguardIndicationController$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.android.systemui.statusbar.KeyguardIndicationController$5] */
    /* JADX WARN: Type inference failed for: r5v19, types: [android.os.Handler, com.android.systemui.statusbar.KeyguardIndicationController$2] */
    public KeyguardIndicationController(Context context, Looper looper, WakeLock.Builder builder, KeyguardStateController keyguardStateController, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, DockManager dockManager, BroadcastDispatcher broadcastDispatcher, DevicePolicyManager devicePolicyManager, IBatteryStats iBatteryStats, UserManager userManager, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, FalsingManager falsingManager, AuthController authController, LockPatternUtils lockPatternUtils, ScreenLifecycle screenLifecycle, KeyguardBypassController keyguardBypassController, AccessibilityManager accessibilityManager, FaceHelpMessageDeferralFactory faceHelpMessageDeferralFactory, KeyguardLogger keyguardLogger, AlternateBouncerInteractor alternateBouncerInteractor, AlarmManager alarmManager, UserTracker userTracker, BouncerMessageInteractor bouncerMessageInteractor, FeatureFlags featureFlags, IndicationHelper indicationHelper, KeyguardInteractor keyguardInteractor, BiometricMessageInteractor biometricMessageInteractor, DeviceEntryFingerprintAuthInteractor deviceEntryFingerprintAuthInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, UserLogoutInteractor userLogoutInteractor) {
        final int i = 0;
        this.mCoExAcquisitionMsgIdsToShowCallback = new Consumer(this) { // from class: com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda0
            public final /* synthetic */ KeyguardIndicationController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i2 = i;
                KeyguardIndicationController keyguardIndicationController = this.f$0;
                switch (i2) {
                    case 0:
                        keyguardIndicationController.mCoExFaceAcquisitionMsgIdsToShow = (Set) obj;
                        break;
                    case 1:
                        keyguardIndicationController.getClass();
                        if (!((Boolean) obj).booleanValue()) {
                            keyguardIndicationController.showTrustAgentErrorMessage(keyguardIndicationController.mTrustAgentErrorMessage);
                            break;
                        }
                        break;
                    default:
                        keyguardIndicationController.getClass();
                        break;
                }
            }
        };
        final int i2 = 1;
        this.mIsFingerprintEngagedCallback = new Consumer(this) { // from class: com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda0
            public final /* synthetic */ KeyguardIndicationController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i22 = i2;
                KeyguardIndicationController keyguardIndicationController = this.f$0;
                switch (i22) {
                    case 0:
                        keyguardIndicationController.mCoExFaceAcquisitionMsgIdsToShow = (Set) obj;
                        break;
                    case 1:
                        keyguardIndicationController.getClass();
                        if (!((Boolean) obj).booleanValue()) {
                            keyguardIndicationController.showTrustAgentErrorMessage(keyguardIndicationController.mTrustAgentErrorMessage);
                            break;
                        }
                        break;
                    default:
                        keyguardIndicationController.getClass();
                        break;
                }
            }
        };
        final int i3 = 2;
        this.mIsLogoutEnabledCallback = new Consumer(this) { // from class: com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda0
            public final /* synthetic */ KeyguardIndicationController f$0;

            {
                this.f$0 = this;
            }

            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i22 = i3;
                KeyguardIndicationController keyguardIndicationController = this.f$0;
                switch (i22) {
                    case 0:
                        keyguardIndicationController.mCoExFaceAcquisitionMsgIdsToShow = (Set) obj;
                        break;
                    case 1:
                        keyguardIndicationController.getClass();
                        if (!((Boolean) obj).booleanValue()) {
                            keyguardIndicationController.showTrustAgentErrorMessage(keyguardIndicationController.mTrustAgentErrorMessage);
                            break;
                        }
                        break;
                    default:
                        keyguardIndicationController.getClass();
                        break;
                }
            }
        };
        ?? r2 = new ScreenLifecycle.Observer() { // from class: com.android.systemui.statusbar.KeyguardIndicationController.1
            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public final void onScreenTurnedOn() {
                String string;
                KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
                keyguardIndicationController.mHandler.removeMessages(2);
                if (keyguardIndicationController.mBiometricErrorMessageToShowOnScreenOn != null) {
                    if (keyguardIndicationController.mFaceLockedOutThisAuthSession) {
                        string = keyguardIndicationController.mContext.getString(keyguardIndicationController.canUnlockWithFingerprint() ? R.string.keyguard_suggest_fingerprint : R.string.keyguard_unlock);
                    } else {
                        string = null;
                    }
                    Pair pair = keyguardIndicationController.mBiometricErrorMessageToShowOnScreenOn;
                    keyguardIndicationController.showBiometricMessage((CharSequence) pair.first, string, (BiometricSourceType) pair.second, false);
                    keyguardIndicationController.mHideBiometricMessageHandler.schedule(4100L, 2);
                    keyguardIndicationController.mBiometricErrorMessageToShowOnScreenOn = null;
                }
            }
        };
        this.mScreenObserver = r2;
        this.mStatusBarStateListener = new AnonymousClass4();
        this.mKeyguardStateCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.KeyguardIndicationController.5
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardShowingChanged() {
                KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
                if (((KeyguardStateControllerImpl) keyguardIndicationController.mKeyguardStateController).mShowing) {
                    return;
                }
                LogLevel logLevel = LogLevel.DEBUG;
                LogBuffer$$ExternalSyntheticLambda0 logBuffer$$ExternalSyntheticLambda0 = new LogBuffer$$ExternalSyntheticLambda0(0);
                LogBuffer logBuffer = keyguardIndicationController.mKeyguardLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("KeyguardIndication", logLevel, logBuffer$$ExternalSyntheticLambda0, null);
                ((LogMessageImpl) logMessageObtain).str1 = "clear messages";
                logBuffer.commit(logMessageObtain);
                KeyguardIndicationTextView keyguardIndicationTextView = keyguardIndicationController.mTopIndicationView;
                if (keyguardIndicationTextView != null) {
                    keyguardIndicationTextView.clearMessages();
                }
                KeyguardIndicationTextView keyguardIndicationTextView2 = keyguardIndicationController.mLifeStyleIndicationView;
                if (keyguardIndicationTextView2 != null) {
                    keyguardIndicationTextView2.clearMessages();
                }
                KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController = keyguardIndicationController.mRotateTextViewController;
                if (keyguardIndicationRotateTextViewController != null) {
                    keyguardIndicationRotateTextViewController.clearMessages();
                }
                keyguardIndicationController.mTrustAgentErrorMessage = null;
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onUnlockedChanged() {
                KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
                keyguardIndicationController.mTrustAgentErrorMessage = null;
                keyguardIndicationController.getClass();
            }
        };
        this.mContext = context;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mDevicePolicyManager = devicePolicyManager;
        this.mKeyguardStateController = keyguardStateController;
        this.mStatusBarStateController = statusBarStateController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mDockManager = dockManager;
        this.mWakeLock = new SettableWakeLock(builder.setTag("Doze:KeyguardIndication").build(), "KeyguardIndication");
        this.mBatteryInfo = iBatteryStats;
        this.mUserManager = userManager;
        this.mExecutor = delayableExecutor;
        this.mBackgroundExecutor = delayableExecutor2;
        this.mAuthController = authController;
        this.mFalsingManager = falsingManager;
        this.mKeyguardBypassController = keyguardBypassController;
        this.mAccessibilityManager = accessibilityManager;
        this.mScreenLifecycle = screenLifecycle;
        this.mKeyguardLogger = keyguardLogger;
        screenLifecycle.addObserver(r2);
        this.mAlternateBouncerInteractor = alternateBouncerInteractor;
        this.mUserTracker = userTracker;
        this.mBouncerMessageInteractor = bouncerMessageInteractor;
        this.mFeatureFlags = featureFlags;
        this.mIndicationHelper = indicationHelper;
        this.mBiometricMessageInteractor = biometricMessageInteractor;
        this.mDeviceEntryFingerprintAuthInteractor = deviceEntryFingerprintAuthInteractor;
        this.mDeviceEntryFaceAuthInteractor = deviceEntryFaceAuthInteractor;
        this.mUserLogoutInteractor = userLogoutInteractor;
        this.mFaceAcquiredMessageDeferral = faceHelpMessageDeferralFactory.create();
        ?? r5 = new Handler(looper) { // from class: com.android.systemui.statusbar.KeyguardIndicationController.2
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i4 = message.what;
                KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
                if (i4 == 1) {
                    keyguardIndicationController.showActionToUnlock();
                } else if (i4 == 2) {
                    keyguardIndicationController.mBiometricErrorMessageToShowOnScreenOn = null;
                } else if (i4 == 100) {
                    keyguardIndicationController.mIsNeededShowChargingType = false;
                }
            }
        };
        this.mHandler = r5;
        final int i4 = 0;
        this.mHideTransientMessageHandler = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener(this) { // from class: com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda3
            public final /* synthetic */ KeyguardIndicationController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                int i5 = i4;
                KeyguardIndicationController keyguardIndicationController = this.f$0;
                switch (i5) {
                    case 0:
                        keyguardIndicationController.hideTransientIndication();
                        break;
                    default:
                        keyguardIndicationController.hideBiometricMessage();
                        break;
                }
            }
        }, "KeyguardIndication", r5);
        final int i5 = 1;
        this.mHideBiometricMessageHandler = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener(this) { // from class: com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda3
            public final /* synthetic */ KeyguardIndicationController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                int i52 = i5;
                KeyguardIndicationController keyguardIndicationController = this.f$0;
                switch (i52) {
                    case 0:
                        keyguardIndicationController.hideTransientIndication();
                        break;
                    default:
                        keyguardIndicationController.hideBiometricMessage();
                        break;
                }
            }
        }, "KeyguardIndication", r5);
    }

    public final boolean canUnlockWithFingerprint() {
        int currentUser$1 = getCurrentUser$1();
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (!keyguardUpdateMonitor.isUnlockWithFingerprintPossible(currentUser$1)) {
            return false;
        }
        keyguardUpdateMonitor.getClass();
        return keyguardUpdateMonitor.isUnlockingWithBiometricAllowed(BiometricSourceType.FINGERPRINT);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0149  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x01a5  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void dump(PrintWriter printWriter, String[] strArr) {
        int i;
        String string;
        StringBuilder sbM = CarrierTextController$$ExternalSyntheticOutline0.m(printWriter, "KeyguardIndicationController:", "  mInitialTextColorState: ");
        sbM.append(this.mInitialTextColorState);
        printWriter.println(sbM.toString());
        StringBuilder sbM2 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mPowerPluggedInWired: "), this.mPowerPluggedInWired, printWriter, "  mPowerPluggedIn: "), this.mPowerPluggedIn, printWriter, "  mPowerCharged: "), this.mPowerCharged, printWriter, "  mChargingSpeed: "), this.mChargingSpeed, printWriter, "  mChargingWattage: "), this.mChargingWattage, printWriter, "  mChargingStatus: "), this.mChargingStatus, printWriter, "  mMessageToShowOnScreenOn: ");
        sbM2.append(this.mBiometricErrorMessageToShowOnScreenOn);
        printWriter.println(sbM2.toString());
        StringBuilder sbM3 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mDozing: "), this.mDozing, printWriter, "  mTransientIndication: ");
        sbM3.append((Object) this.mTransientIndication);
        printWriter.println(sbM3.toString());
        printWriter.println("  mBiometricMessage: " + ((Object) this.mBiometricMessage));
        printWriter.println("  mBiometricMessageFollowUp: " + ((Object) this.mBiometricMessageFollowUp));
        StringBuilder sbM4 = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("  mBatteryLevel: "), this.mBatteryLevel, printWriter, "  mBatteryPresent: "), this.mBatteryPresent, printWriter, "  AOD text: ");
        KeyguardIndicationTextView keyguardIndicationTextView = this.mTopIndicationView;
        sbM4.append((Object) (keyguardIndicationTextView == null ? null : keyguardIndicationTextView.getText()));
        printWriter.println(sbM4.toString());
        StringBuilder sb = new StringBuilder("  computePowerIndication(): ");
        if (this.mBatteryDefender) {
            string = this.mContext.getResources().getString(R.string.keyguard_plugged_in_charging_limited, NumberFormat.getPercentInstance().format(this.mBatteryLevel / 100.0f));
        } else if (this.mPowerPluggedIn && this.mIncompatibleCharger) {
            string = this.mContext.getResources().getString(R.string.keyguard_plugged_in_incompatible_charger, NumberFormat.getPercentInstance().format(this.mBatteryLevel / 100.0f));
        } else if (this.mPowerCharged) {
            string = this.mContext.getResources().getString(R.string.keyguard_charged);
        } else {
            boolean z = this.mChargingTimeRemaining > 0;
            boolean z2 = this.mPowerPluggedInWired;
            int i2 = R.string.keyguard_plugged_in;
            if (z2) {
                int i3 = this.mChargingSpeed;
                if (i3 == 0) {
                    i = z ? R.string.keyguard_indication_charging_time_slowly : R.string.keyguard_plugged_in_charging_slowly;
                } else if (i3 != 2) {
                    if (z) {
                        i2 = R.string.keyguard_indication_charging_time;
                    }
                    String str = NumberFormat.getPercentInstance().format(this.mBatteryLevel / 100.0f);
                    string = !z ? this.mContext.getResources().getString(i2, Formatter.formatShortElapsedTimeRoundingUpToMinutes(this.mContext, this.mChargingTimeRemaining), str) : this.mContext.getResources().getString(i2, str);
                } else {
                    i = z ? R.string.keyguard_indication_charging_time_fast : R.string.keyguard_plugged_in_charging_fast;
                }
                i2 = i;
                String str2 = NumberFormat.getPercentInstance().format(this.mBatteryLevel / 100.0f);
                if (!z) {
                }
            } else {
                if (this.mPowerPluggedInWireless) {
                    i = z ? R.string.keyguard_indication_charging_time_wireless : R.string.keyguard_plugged_in_wireless;
                } else if (this.mPowerPluggedInDock) {
                    i = z ? R.string.keyguard_indication_charging_time_dock : R.string.keyguard_plugged_in_dock;
                } else {
                    if (z) {
                    }
                    String str22 = NumberFormat.getPercentInstance().format(this.mBatteryLevel / 100.0f);
                    if (!z) {
                    }
                }
                i2 = i;
                String str222 = NumberFormat.getPercentInstance().format(this.mBatteryLevel / 100.0f);
                if (!z) {
                }
            }
        }
        sb.append(string);
        printWriter.println(sb.toString());
        printWriter.println("  trustGrantedIndication: " + getTrustGrantedIndication());
        printWriter.println("    mCoExFaceHelpMsgIdsToShow=" + this.mCoExFaceAcquisitionMsgIdsToShow);
        KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController = this.mRotateTextViewController;
        if (keyguardIndicationRotateTextViewController != null) {
            keyguardIndicationRotateTextViewController.dump(printWriter, strArr);
        }
    }

    public final int getCurrentUser$1() {
        return ((UserTrackerImpl) this.mUserTracker).getUserId();
    }

    public KeyguardUpdateMonitorCallback getKeyguardCallback() {
        if (this.mUpdateMonitorCallback == null) {
            this.mUpdateMonitorCallback = new BaseKeyguardCallback();
        }
        return this.mUpdateMonitorCallback;
    }

    public String getTrustGrantedIndication() {
        CharSequence charSequence = this.mTrustGrantedIndication;
        return charSequence == null ? this.mContext.getString(R.string.keyguard_indication_trust_unlocked) : ((String) charSequence).toString();
    }

    public final void hideBiometricMessage() {
        if (this.mBiometricMessage == null && this.mBiometricMessageFollowUp == null) {
            return;
        }
        this.mBiometricMessage = null;
        this.mBiometricMessageFollowUp = null;
        this.mBiometricMessageSource = null;
        this.mHideBiometricMessageHandler.cancel();
        updateBiometricMessage();
    }

    public void hideTransientIndication() {
        if (this.mTransientIndication != null) {
            this.mTransientIndication = null;
            this.mHideTransientMessageHandler.cancel();
            updateTransient();
        }
    }

    public void hideTransientIndicationDelayed(long j) {
        this.mHideTransientMessageHandler.schedule(j, 2);
    }

    public final void init() {
        if (this.mInited) {
            return;
        }
        this.mInited = true;
        this.mDockManager.getClass();
        this.mKeyguardUpdateMonitor.registerCallback(getKeyguardCallback());
        AnonymousClass4 anonymousClass4 = this.mStatusBarStateListener;
        StatusBarStateController statusBarStateController = this.mStatusBarStateController;
        statusBarStateController.addCallback(anonymousClass4);
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).addCallback(this.mKeyguardStateCallback);
        anonymousClass4.onDozingChanged(statusBarStateController.isDozing());
    }

    /* JADX WARN: Type inference failed for: r7v16, types: [com.android.systemui.statusbar.KeyguardIndicationController$3] */
    public void setIndicationArea(ViewGroup viewGroup) {
        this.mIndicationArea = viewGroup;
        this.mTopIndicationView = (KeyguardIndicationTextView) viewGroup.findViewById(R.id.keyguard_indication_text);
        this.mLifeStyleIndicationView = (KeyguardIndicationTextView) viewGroup.findViewById(R.id.keyguard_life_style_text);
        this.mLockScreenIndicationView = (KeyguardIndicationTextView) viewGroup.findViewById(R.id.keyguard_indication_text_bottom);
        this.mInitialTextColorState = ColorStateList.valueOf(Utils.getColorAttrDefaultColor(this.mContext, R.attr.wallpaperTextColor, 0));
        if (this.mRotateTextViewController == null) {
            this.mRotateTextViewController = new KeyguardIndicationRotateTextViewController(this.mLockScreenIndicationView, this.mExecutor, this.mStatusBarStateController, this.mKeyguardLogger, this.mFeatureFlags);
        }
        boolean zBooleanValue = ((Boolean) DejankUtils.whitelistIpcs(new KeyguardIndicationController$$ExternalSyntheticLambda5(this, 0))).booleanValue();
        this.mOrganizationOwnedDevice = zBooleanValue;
        if (zBooleanValue) {
            this.mBackgroundExecutor.execute(new KeyguardIndicationController$$ExternalSyntheticLambda6(this));
        } else {
            this.mRotateTextViewController.hideIndication(1);
        }
        if (this.mBroadcastReceiver == null) {
            this.mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.statusbar.KeyguardIndicationController.3
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    Log.i("KeyguardIndication", "onReceive : " + action);
                    if ("com.samsung.android.app.routines.MODE_INFO_FOR_LOCKSCREEN".equals(action)) {
                        KeyguardIndicationController.this.updateLifeStyleInfo(intent);
                        return;
                    }
                    if (!"com.samsung.server.BatteryService.action.ACTION_SLEEP_CHARGING".equals(action)) {
                        KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
                        keyguardIndicationController.getClass();
                        boolean zBooleanValue2 = ((Boolean) DejankUtils.whitelistIpcs(new KeyguardIndicationController$$ExternalSyntheticLambda5(keyguardIndicationController, 0))).booleanValue();
                        keyguardIndicationController.mOrganizationOwnedDevice = zBooleanValue2;
                        if (!zBooleanValue2) {
                            keyguardIndicationController.mRotateTextViewController.hideIndication(1);
                            return;
                        } else {
                            keyguardIndicationController.mBackgroundExecutor.execute(new KeyguardIndicationController$$ExternalSyntheticLambda6(keyguardIndicationController));
                            return;
                        }
                    }
                    KeyguardIndicationController keyguardIndicationController2 = KeyguardIndicationController.this;
                    keyguardIndicationController2.getClass();
                    keyguardIndicationController2.mSleepChargingEvent = intent.getStringExtra("sleep_charging_event");
                    Log.d("KeyguardIndication", "sleepCharging Changed - " + keyguardIndicationController2.mSleepChargingEvent);
                    if ("off".equals(intent.getStringExtra("sleep_charging_event"))) {
                        Log.d("KeyguardIndication", "sleepCharging finish time - null");
                        keyguardIndicationController2.mSleepChargingEventFinishTime = null;
                    } else {
                        String stringExtra = intent.getStringExtra("sleep_charging_finish_time");
                        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("sleepCharging finish time - ", stringExtra, "KeyguardIndication");
                        keyguardIndicationController2.mSleepChargingEventFinishTime = stringExtra;
                    }
                }
            };
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED");
            intentFilter.addAction("android.intent.action.USER_REMOVED");
            intentFilter.addAction("com.samsung.server.BatteryService.action.ACTION_SLEEP_CHARGING");
            intentFilter.addAction("com.samsung.android.app.routines.MODE_INFO_FOR_LOCKSCREEN");
            this.mBroadcastDispatcher.registerReceiver(intentFilter, this.mBroadcastReceiver);
        }
        JavaAdapterKt.collectFlow(this.mIndicationArea, this.mBiometricMessageInteractor.coExFaceAcquisitionMsgIdsToShow, this.mCoExAcquisitionMsgIdsToShowCallback);
        JavaAdapterKt.collectFlow(this.mIndicationArea, this.mDeviceEntryFingerprintAuthInteractor.isEngaged, this.mIsFingerprintEngagedCallback);
        JavaAdapterKt.collectFlow(this.mIndicationArea, this.mUserLogoutInteractor.isLogoutEnabled, this.mIsLogoutEnabledCallback);
    }

    public void setPowerPluggedIn(boolean z) {
        this.mPowerPluggedIn = z;
    }

    public void setVisible(boolean z) {
        if (this.mDozing) {
            Log.d("KeyguardIndication", "setVisible() false in dozing");
            z = false;
        }
        this.mVisible = z;
        ViewGroup viewGroup = this.mIndicationArea;
        if (viewGroup != null) {
            viewGroup.setVisibility(z ? 0 : 4);
        }
        if (!z) {
            hideTransientIndication();
            KeyguardIndicationTextView keyguardIndicationTextView = this.mTopIndicationView;
            if (keyguardIndicationTextView != null) {
                keyguardIndicationTextView.setText("");
            }
            KeyguardIndicationTextView keyguardIndicationTextView2 = this.mLifeStyleIndicationView;
            if (keyguardIndicationTextView2 != null) {
                keyguardIndicationTextView2.clearMessages();
            }
        } else if (!this.mHideTransientMessageHandler.isScheduled()) {
            hideTransientIndication();
        }
        if (this.mLockScreenIndicationView != null) {
            if (this.mOrganizationOwnedDevice) {
                this.mBackgroundExecutor.execute(new KeyguardIndicationController$$ExternalSyntheticLambda6(this));
            } else {
                this.mRotateTextViewController.hideIndication(1);
            }
            this.mLockScreenIndicationView.setVisibility(z ? 0 : 8);
        }
    }

    public final void showActionToUnlock() {
        boolean z = this.mDozing;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (!z || keyguardUpdateMonitor.getUserCanSkipBouncer(getCurrentUser$1())) {
            if (this.mStatusBarKeyguardViewManager.isBouncerShowing()) {
                if (this.mAlternateBouncerInteractor.isVisibleState() || !keyguardUpdateMonitor.isFaceEnabledAndEnrolled() || keyguardUpdateMonitor.getIsFaceAuthenticated()) {
                    return;
                }
                this.mStatusBarKeyguardViewManager.setKeyguardMessage((this.mAccessibilityManager.isEnabled() || this.mAccessibilityManager.isTouchExplorationEnabled()) ? this.mContext.getString(R.string.accesssibility_keyguard_retry) : this.mContext.getString(R.string.keyguard_retry), this.mInitialTextColorState);
                return;
            }
            if (!keyguardUpdateMonitor.getUserCanSkipBouncer(getCurrentUser$1())) {
                showBiometricMessage(this.mContext.getString(R.string.keyguard_unlock), null, null, false);
                return;
            }
            boolean isFaceAuthenticated = keyguardUpdateMonitor.getIsFaceAuthenticated();
            boolean zIsUdfpsSupported = keyguardUpdateMonitor.isUdfpsSupported();
            boolean z2 = this.mAccessibilityManager.isEnabled() || this.mAccessibilityManager.isTouchExplorationEnabled();
            if (zIsUdfpsSupported && isFaceAuthenticated) {
                if (z2) {
                    showBiometricMessage(this.mContext.getString(R.string.keyguard_face_successful_unlock), this.mContext.getString(R.string.keyguard_unlock), BiometricSourceType.FACE, true);
                    return;
                } else {
                    showBiometricMessage(this.mContext.getString(R.string.keyguard_face_successful_unlock), this.mContext.getString(R.string.keyguard_unlock_press), BiometricSourceType.FACE, true);
                    return;
                }
            }
            if (isFaceAuthenticated) {
                showBiometricMessage(this.mContext.getString(R.string.keyguard_face_successful_unlock), this.mContext.getString(R.string.keyguard_unlock), BiometricSourceType.FACE, true);
                return;
            }
            if (!zIsUdfpsSupported) {
                showBiometricMessage(this.mContext.getString(R.string.keyguard_unlock), null, null, true);
            } else if (z2) {
                showBiometricMessage(this.mContext.getString(R.string.keyguard_unlock), null, null, true);
            } else {
                showBiometricMessage(this.mContext.getString(R.string.keyguard_unlock_press), null, null, true);
            }
        }
    }

    public final void showBiometricMessage(CharSequence charSequence, CharSequence charSequence2, BiometricSourceType biometricSourceType, boolean z) {
        if (TextUtils.equals(charSequence, this.mBiometricMessage) && biometricSourceType == this.mBiometricMessageSource && TextUtils.equals(charSequence2, this.mBiometricMessageFollowUp)) {
            return;
        }
        if (z || this.mBiometricMessageSource != BiometricSourceType.FINGERPRINT || biometricSourceType != BiometricSourceType.FACE) {
            if (this.mBiometricMessageSource == null || biometricSourceType != null) {
                this.mBiometricMessage = charSequence;
                this.mBiometricMessageFollowUp = charSequence2;
                this.mBiometricMessageSource = biometricSourceType;
            } else {
                this.mBiometricMessageFollowUp = charSequence;
            }
            removeMessages(1);
            this.mHideBiometricMessageHandler.schedule((TextUtils.isEmpty(this.mBiometricMessage) || TextUtils.isEmpty(this.mBiometricMessageFollowUp)) ? 4100L : 5200L, 2);
            updateBiometricMessage();
            return;
        }
        KeyguardLogger keyguardLogger = this.mKeyguardLogger;
        keyguardLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardLogger$$ExternalSyntheticLambda0 keyguardLogger$$ExternalSyntheticLambda0 = new KeyguardLogger$$ExternalSyntheticLambda0(0);
        LogBuffer logBuffer = keyguardLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardIndication", logLevel, keyguardLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = charSequence.toString();
        logMessageImpl.str2 = charSequence2 != null ? charSequence2.toString() : null;
        logBuffer.commit(logMessageObtain);
    }

    public final void showErrorMessageNowOrLater(String str, String str2, BiometricSourceType biometricSourceType) {
        if (this.mStatusBarKeyguardViewManager.isBouncerShowing()) {
            this.mStatusBarKeyguardViewManager.setKeyguardMessage(str, this.mInitialTextColorState);
        } else if (this.mScreenLifecycle.mScreenState == 2) {
            showBiometricMessage(str, str2, biometricSourceType, false);
        } else {
            this.mBiometricErrorMessageToShowOnScreenOn = new Pair(str, biometricSourceType);
        }
    }

    public void showTransientIndication(int i) {
        showTransientIndication(this.mContext.getResources().getString(i));
    }

    public final void showTrustAgentErrorMessage(CharSequence charSequence) {
        if (charSequence == null) {
            this.mTrustAgentErrorMessage = null;
            return;
        }
        boolean zBooleanValue = ((Boolean) this.mDeviceEntryFingerprintAuthInteractor.isEngaged.$$delegate_0.getValue()).booleanValue();
        boolean zIsRunning = this.mDeviceEntryFaceAuthInteractor.isRunning();
        if (!zBooleanValue && !zIsRunning) {
            this.mTrustAgentErrorMessage = null;
            showBiometricMessage(charSequence, null, null, false);
            return;
        }
        KeyguardLogger keyguardLogger = this.mKeyguardLogger;
        keyguardLogger.getClass();
        LogLevel logLevel = LogLevel.DEBUG;
        KeyguardLogger$$ExternalSyntheticLambda0 keyguardLogger$$ExternalSyntheticLambda0 = new KeyguardLogger$$ExternalSyntheticLambda0(1);
        LogBuffer logBuffer = keyguardLogger.buffer;
        LogMessage logMessageObtain = logBuffer.obtain("KeyguardLog", logLevel, keyguardLogger$$ExternalSyntheticLambda0, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
        logMessageImpl.str1 = charSequence.toString();
        logMessageImpl.bool1 = zBooleanValue;
        logMessageImpl.bool2 = zIsRunning;
        logBuffer.commit(logMessageObtain);
        this.mTrustAgentErrorMessage = charSequence;
    }

    public final void updateBiometricMessage() {
        if (this.mDozing) {
            return;
        }
        if (TextUtils.isEmpty(this.mBiometricMessage)) {
            this.mRotateTextViewController.hideIndication(11);
        } else {
            KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController = this.mRotateTextViewController;
            KeyguardIndication.Builder builder = new KeyguardIndication.Builder();
            builder.mMessage = this.mBiometricMessage;
            builder.mForceAccessibilityLiveRegionAssertive = true;
            builder.mMinVisibilityMillis = 2600L;
            builder.mTextColor = this.mInitialTextColorState;
            builder.build();
            keyguardIndicationRotateTextViewController.getClass();
        }
        if (TextUtils.isEmpty(this.mBiometricMessageFollowUp)) {
            this.mRotateTextViewController.hideIndication(12);
            return;
        }
        KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController2 = this.mRotateTextViewController;
        KeyguardIndication.Builder builder2 = new KeyguardIndication.Builder();
        builder2.mMessage = this.mBiometricMessageFollowUp;
        builder2.mMinVisibilityMillis = 2600L;
        builder2.mTextColor = this.mInitialTextColorState;
        builder2.build();
        keyguardIndicationRotateTextViewController2.getClass();
    }

    public final void updateTransient() {
        if (this.mDozing) {
            return;
        }
        if (TextUtils.isEmpty(this.mTransientIndication)) {
            this.mRotateTextViewController.hideIndication(5);
            return;
        }
        KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController = this.mRotateTextViewController;
        CharSequence charSequence = this.mTransientIndication;
        keyguardIndicationRotateTextViewController.getClass();
        KeyguardIndication.Builder builder = new KeyguardIndication.Builder();
        builder.mMessage = charSequence;
        builder.mMinVisibilityMillis = 2600L;
        builder.mTextColor = keyguardIndicationRotateTextViewController.mInitialTextColorState;
        builder.build();
    }

    public void showTransientIndication(CharSequence charSequence) {
        this.mTransientIndication = charSequence;
        hideTransientIndicationDelayed(4100L);
        updateTransient();
    }

    public void setDozing(boolean z) {
    }

    public void setNowBarExpandMode(boolean z) {
    }

    public void setUpperTextView(KeyguardIndicationTextView keyguardIndicationTextView) {
    }

    public void updateLifeStyleInfo(Intent intent) {
    }
}
