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
import android.os.UserManager;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import com.android.internal.app.IBatteryStats;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.keyguard.TrustGrantFlags;
import com.android.keyguard.logging.KeyguardLogger;
import com.android.systemui.DejankUtils;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.biometrics.FaceHelpMessageDeferral;
import com.android.systemui.biometrics.UdfpsController;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.dock.DockManager;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardIndication;
import com.android.systemui.keyguard.KeyguardIndicationRotateTextViewController;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.domain.interactor.AlternateBouncerInteractor;
import com.android.systemui.log.LogLevel;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.statusbar.phone.KeyguardBypassController;
import com.android.systemui.statusbar.phone.KeyguardIndicationTextView;
import com.android.systemui.statusbar.phone.StatusBarKeyguardViewManager;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.util.AlarmTimeout;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.concurrency.ExecutorImpl;
import com.android.systemui.util.wakelock.SettableWakeLock;
import com.android.systemui.util.wakelock.WakeLock;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class KeyguardIndicationController {
    public final AccessibilityManager mAccessibilityManager;
    public final AlternateBouncerInteractor mAlternateBouncerInteractor;
    public final AuthController mAuthController;
    public final DelayableExecutor mBackgroundExecutor;
    public boolean mBatteryDefender;
    public final IBatteryStats mBatteryInfo;
    public int mBatteryLevel;
    public String mBiometricErrorMessageToShowOnScreenOn;
    public CharSequence mBiometricMessage;
    public CharSequence mBiometricMessageFollowUp;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public C25163 mBroadcastReceiver;
    public int mChangingType;
    public int mChargingSpeed;
    public long mChargingTimeRemaining;
    public int mChargingWattage;
    public final Set mCoExFaceAcquisitionMsgIdsToShow;
    public final Context mContext;
    public final DevicePolicyManager mDevicePolicyManager;
    public final DockManager mDockManager;
    public boolean mDozing;
    public final DelayableExecutor mExecutor;
    public final FaceHelpMessageDeferral mFaceAcquiredMessageDeferral;
    public boolean mFaceLockedOutThisAuthSession;
    public final FalsingManager mFalsingManager;
    public final FeatureFlags mFeatureFlags;
    public final HandlerC25152 mHandler;
    public final AlarmTimeout mHideBiometricMessageHandler;
    public final AlarmTimeout mHideTransientMessageHandler;
    public boolean mIncompatibleCharger;
    public ViewGroup mIndicationArea;
    public boolean mInited;
    public ColorStateList mInitialTextColorState;
    public final KeyguardBypassController mKeyguardBypassController;
    public final KeyguardLogger mKeyguardLogger;
    public final C25185 mKeyguardStateCallback;
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
    public boolean mPowerPluggedInWithoutCharging;
    public boolean mProtectedFullyCharged;
    public KeyguardIndicationRotateTextViewController mRotateTextViewController;
    public final ScreenLifecycle mScreenLifecycle;
    public final C25141 mScreenObserver;
    public StatusBarKeyguardViewManager mStatusBarKeyguardViewManager;
    public final StatusBarStateController mStatusBarStateController;
    public final C25174 mStatusBarStateListener;
    public KeyguardIndicationTextView mTopIndicationView;
    public CharSequence mTransientIndication;
    public CharSequence mTrustGrantedIndication;
    public KeyguardUpdateMonitorCallback mUpdateMonitorCallback;
    public final UserManager mUserManager;
    public final UserTracker mUserTracker;
    public boolean mVisible;
    public final SettableWakeLock mWakeLock;
    public boolean mIsNeededShowChargingType = false;
    public boolean mBatteryPresent = true;
    public String mSleepChargingEvent = null;
    public boolean mResumedChargingAdaptiveProtection = false;
    public String mSleepChargingEventFinishTime = null;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.KeyguardIndicationController$4 */
    public final class C25174 implements StatusBarStateController.StateListener {
        public C25174() {
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
            keyguardIndicationController.getClass();
        }

        @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
        public final void onStateChanged(int i) {
            KeyguardIndicationController.this.setVisible(i == 1);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class BaseKeyguardCallback extends KeyguardUpdateMonitorCallback {
        public BaseKeyguardCallback() {
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricAcquired(BiometricSourceType biometricSourceType, int i) {
            Integer num;
            if (biometricSourceType == BiometricSourceType.FACE) {
                FaceHelpMessageDeferral faceHelpMessageDeferral = KeyguardIndicationController.this.mFaceAcquiredMessageDeferral;
                Set set = faceHelpMessageDeferral.messagesToDefer;
                if (set.isEmpty()) {
                    return;
                }
                faceHelpMessageDeferral.totalFrames++;
                HashMap hashMap = (HashMap) faceHelpMessageDeferral.acquiredInfoToFrequency;
                int intValue = ((Number) hashMap.getOrDefault(Integer.valueOf(i), 0)).intValue() + 1;
                hashMap.put(Integer.valueOf(i), Integer.valueOf(intValue));
                if (set.contains(Integer.valueOf(i)) && ((num = faceHelpMessageDeferral.mostFrequentAcquiredInfoToDefer) == null || intValue > ((Number) hashMap.getOrDefault(num, 0)).intValue())) {
                    faceHelpMessageDeferral.mostFrequentAcquiredInfoToDefer = Integer.valueOf(i);
                }
                int i2 = faceHelpMessageDeferral.totalFrames;
                Integer num2 = faceHelpMessageDeferral.mostFrequentAcquiredInfoToDefer;
                faceHelpMessageDeferral.logBuffer.logFrameProcessed(i, i2, num2 != null ? num2.toString() : null);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onBiometricAuthFailed(BiometricSourceType biometricSourceType) {
            if (biometricSourceType == BiometricSourceType.FACE) {
                KeyguardIndicationController.this.mFaceAcquiredMessageDeferral.reset();
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            keyguardIndicationController.hideBiometricMessage();
            if (biometricSourceType == BiometricSourceType.FACE) {
                keyguardIndicationController.mFaceAcquiredMessageDeferral.reset();
                if (keyguardIndicationController.mKeyguardBypassController.canBypass()) {
                    return;
                }
                keyguardIndicationController.showActionToUnlock();
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x0064  */
        /* JADX WARN: Removed duplicated region for block: B:17:0x0070  */
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onBiometricError(int i, String str, BiometricSourceType biometricSourceType) {
            CharSequence charSequence;
            boolean z;
            BiometricSourceType biometricSourceType2 = BiometricSourceType.FACE;
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            if (biometricSourceType != biometricSourceType2) {
                if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                    if (shouldSuppressFingerprintError(i)) {
                        keyguardIndicationController.mKeyguardLogger.logBiometricMessage("suppressingFingerprintError", Integer.valueOf(i), str);
                        return;
                    } else {
                        keyguardIndicationController.showErrorMessageNowOrLater(str, null);
                        return;
                    }
                }
                return;
            }
            FaceHelpMessageDeferral faceHelpMessageDeferral = keyguardIndicationController.mFaceAcquiredMessageDeferral;
            Integer num = faceHelpMessageDeferral.mostFrequentAcquiredInfoToDefer;
            if (num != null) {
                int intValue = num.intValue();
                if (((Number) ((HashMap) faceHelpMessageDeferral.acquiredInfoToFrequency).getOrDefault(Integer.valueOf(intValue), 0)).intValue() > faceHelpMessageDeferral.threshold * faceHelpMessageDeferral.totalFrames) {
                    charSequence = (CharSequence) ((HashMap) faceHelpMessageDeferral.acquiredInfoToHelpString).get(Integer.valueOf(intValue));
                    keyguardIndicationController.mFaceAcquiredMessageDeferral.reset();
                    z = !((keyguardIndicationController.mKeyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true) ^ true) || i == 9) || i == 5 || i == 2;
                    KeyguardLogger keyguardLogger = keyguardIndicationController.mKeyguardLogger;
                    if (!z) {
                        keyguardLogger.logBiometricMessage("suppressingFaceError", Integer.valueOf(i), str);
                        return;
                    }
                    int i2 = R.string.keyguard_suggest_fingerprint;
                    Context context = keyguardIndicationController.mContext;
                    if (i == 3) {
                        keyguardLogger.logBiometricMessage("deferred message after face auth timeout", null, String.valueOf(charSequence));
                        if (!keyguardIndicationController.canUnlockWithFingerprint()) {
                            if (charSequence != null) {
                                keyguardIndicationController.showBiometricMessage(charSequence, context.getString(R.string.keyguard_unlock));
                                return;
                            } else {
                                keyguardIndicationController.showActionToUnlock();
                                return;
                            }
                        }
                        if (charSequence == null || keyguardIndicationController.mStatusBarKeyguardViewManager.isBouncerShowing()) {
                            keyguardLogger.logBiometricMessage("skip showing FACE_ERROR_TIMEOUT due to co-ex logic", null, null);
                            return;
                        } else {
                            keyguardIndicationController.showBiometricMessage(charSequence, context.getString(R.string.keyguard_suggest_fingerprint));
                            return;
                        }
                    }
                    if (!(i == 9 || i == 7)) {
                        keyguardIndicationController.showErrorMessageNowOrLater(str, null);
                        return;
                    }
                    if (!keyguardIndicationController.canUnlockWithFingerprint()) {
                        i2 = R.string.keyguard_unlock;
                    }
                    String string = context.getString(i2);
                    if (!keyguardIndicationController.mFaceLockedOutThisAuthSession) {
                        keyguardIndicationController.mFaceLockedOutThisAuthSession = true;
                        keyguardIndicationController.showErrorMessageNowOrLater(str, string);
                        return;
                    } else {
                        UdfpsController udfpsController = keyguardIndicationController.mAuthController.mUdfpsController;
                        if (udfpsController != null ? udfpsController.mOnFingerDown : false) {
                            return;
                        }
                        keyguardIndicationController.showErrorMessageNowOrLater(context.getString(R.string.keyguard_face_unlock_unavailable), string);
                        return;
                    }
                }
            }
            charSequence = null;
            keyguardIndicationController.mFaceAcquiredMessageDeferral.reset();
            if (keyguardIndicationController.mKeyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true) ^ true) {
            }
            KeyguardLogger keyguardLogger2 = keyguardIndicationController.mKeyguardLogger;
            if (!z) {
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onBiometricHelp(int i, String str, BiometricSourceType biometricSourceType) {
            BiometricSourceType biometricSourceType2 = BiometricSourceType.FACE;
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            if (biometricSourceType == biometricSourceType2) {
                FaceHelpMessageDeferral faceHelpMessageDeferral = keyguardIndicationController.mFaceAcquiredMessageDeferral;
                if (faceHelpMessageDeferral.messagesToDefer.contains(Integer.valueOf(i))) {
                    HashMap hashMap = (HashMap) faceHelpMessageDeferral.acquiredInfoToHelpString;
                    if (!Objects.equals(hashMap.get(Integer.valueOf(i)), str)) {
                        faceHelpMessageDeferral.logBuffer.logUpdateMessage(i, str);
                        hashMap.put(Integer.valueOf(i), str);
                    }
                }
                if (keyguardIndicationController.mFaceAcquiredMessageDeferral.messagesToDefer.contains(Integer.valueOf(i))) {
                    return;
                }
            }
            boolean z = false;
            boolean z2 = biometricSourceType == BiometricSourceType.FACE && i == -3;
            if (!(!keyguardIndicationController.mKeyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true)) || z2) {
                BiometricSourceType biometricSourceType3 = BiometricSourceType.FACE;
                boolean z3 = (biometricSourceType != biometricSourceType3 || i == -2 || i == -3) ? false : true;
                boolean z4 = biometricSourceType == biometricSourceType3 && i == -2;
                boolean z5 = biometricSourceType == BiometricSourceType.FINGERPRINT && i == -1;
                boolean canUnlockWithFingerprint = keyguardIndicationController.canUnlockWithFingerprint();
                if (z3 && canUnlockWithFingerprint) {
                    z = true;
                }
                if (z) {
                    if (!((HashSet) keyguardIndicationController.mCoExFaceAcquisitionMsgIdsToShow).contains(Integer.valueOf(i))) {
                        keyguardIndicationController.mKeyguardLogger.logBiometricMessage("skipped showing help message due to co-ex logic", Integer.valueOf(i), str);
                        return;
                    }
                }
                if (keyguardIndicationController.mStatusBarKeyguardViewManager.isBouncerShowing()) {
                    keyguardIndicationController.mStatusBarKeyguardViewManager.setKeyguardMessage(str, keyguardIndicationController.mInitialTextColorState);
                    return;
                }
                if (keyguardIndicationController.mScreenLifecycle.mScreenState != 2) {
                    HandlerC25152 handlerC25152 = keyguardIndicationController.mHandler;
                    if (z4) {
                        handlerC25152.sendMessageDelayed(handlerC25152.obtainMessage(1), 1300L);
                        return;
                    } else {
                        keyguardIndicationController.mBiometricErrorMessageToShowOnScreenOn = str;
                        handlerC25152.sendMessageDelayed(handlerC25152.obtainMessage(2), 1000L);
                        return;
                    }
                }
                Context context = keyguardIndicationController.mContext;
                if (z && i == 3) {
                    keyguardIndicationController.showBiometricMessage(str, context.getString(R.string.keyguard_suggest_fingerprint));
                    return;
                }
                if (z4 && canUnlockWithFingerprint) {
                    keyguardIndicationController.showBiometricMessage(context.getString(R.string.keyguard_face_failed), context.getString(R.string.keyguard_suggest_fingerprint));
                    return;
                }
                UserTracker userTracker = keyguardIndicationController.mUserTracker;
                KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardIndicationController.mKeyguardUpdateMonitor;
                if (z5 && keyguardUpdateMonitor.getUserUnlockedWithFace(((UserTrackerImpl) userTracker).getUserId())) {
                    keyguardIndicationController.showBiometricMessage(context.getString(R.string.keyguard_face_successful_unlock), context.getString(R.string.keyguard_unlock));
                    return;
                }
                if (z5 && keyguardUpdateMonitor.getUserHasTrust(((UserTrackerImpl) userTracker).getUserId())) {
                    keyguardIndicationController.showBiometricMessage(keyguardIndicationController.getTrustGrantedIndication(), context.getString(R.string.keyguard_unlock));
                } else if (z2) {
                    keyguardIndicationController.showBiometricMessage(str, canUnlockWithFingerprint ? context.getString(R.string.keyguard_suggest_fingerprint) : context.getString(R.string.keyguard_unlock));
                } else {
                    keyguardIndicationController.showBiometricMessage(str, null);
                }
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onBiometricRunningStateChanged(BiometricSourceType biometricSourceType, boolean z) {
            if (z && biometricSourceType == BiometricSourceType.FACE) {
                KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
                keyguardIndicationController.hideBiometricMessage();
                keyguardIndicationController.mBiometricErrorMessageToShowOnScreenOn = null;
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onLockedOutStateChanged(BiometricSourceType biometricSourceType) {
            BiometricSourceType biometricSourceType2 = BiometricSourceType.FACE;
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            if (biometricSourceType == biometricSourceType2) {
                KeyguardUpdateMonitor keyguardUpdateMonitor = keyguardIndicationController.mKeyguardUpdateMonitor;
                keyguardUpdateMonitor.getClass();
                if (!keyguardUpdateMonitor.mFaceLockedOutPermanent) {
                    keyguardIndicationController.mFaceLockedOutThisAuthSession = false;
                    return;
                }
            }
            if (biometricSourceType == BiometricSourceType.FINGERPRINT && keyguardIndicationController.mKeyguardUpdateMonitor.isFingerprintLockedOut()) {
                keyguardIndicationController.mContext.getString(R.string.keyguard_unlock);
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onLogoutEnabledChanged() {
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            if (keyguardIndicationController.mVisible) {
                keyguardIndicationController.getClass();
            }
        }

        /* JADX WARN: Can't wrap try/catch for region: R(39:0|1|(2:(1:4)(1:76)|(29:6|7|(1:75)(1:10)|(1:74)(1:13)|14|(1:16)(1:73)|(1:72)(1:19)|20|(1:22)(1:71)|(1:70)(1:25)|26|(1:69)(1:29)|30|(1:32)(1:68)|33|(1:35)(1:(1:65)(1:(1:67)))|36|(1:38)(1:63)|39|40|41|(1:43)(1:60)|44|45|(1:47)|48|(2:56|(1:58))(1:52)|53|54))|77|7|(0)|75|(0)|74|14|(0)(0)|(0)|72|20|(0)(0)|(0)|70|26|(0)|69|30|(0)(0)|33|(0)(0)|36|(0)(0)|39|40|41|(0)(0)|44|45|(0)|48|(1:50)|56|(0)|53|54) */
        /* JADX WARN: Code restructure failed: missing block: B:61:0x00cd, code lost:
        
            r1 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:62:0x00ce, code lost:
        
            r6.buffer.log("KeyguardIndication", com.android.systemui.log.LogLevel.ERROR, "Error calling IBatteryStats", r1);
            r12.mChargingTimeRemaining = -1;
         */
        /* JADX WARN: Removed duplicated region for block: B:16:0x0035  */
        /* JADX WARN: Removed duplicated region for block: B:22:0x0045  */
        /* JADX WARN: Removed duplicated region for block: B:32:0x0062  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x0089  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x00a1  */
        /* JADX WARN: Removed duplicated region for block: B:43:0x00c2 A[Catch: RemoteException -> 0x00cd, TryCatch #0 {RemoteException -> 0x00cd, blocks: (B:41:0x00be, B:43:0x00c2, B:44:0x00ca), top: B:40:0x00be }] */
        /* JADX WARN: Removed duplicated region for block: B:47:0x00e1  */
        /* JADX WARN: Removed duplicated region for block: B:58:0x00fc  */
        /* JADX WARN: Removed duplicated region for block: B:60:0x00c9  */
        /* JADX WARN: Removed duplicated region for block: B:63:0x00a3  */
        /* JADX WARN: Removed duplicated region for block: B:64:0x008b  */
        /* JADX WARN: Removed duplicated region for block: B:68:0x0064  */
        /* JADX WARN: Removed duplicated region for block: B:71:0x0047  */
        /* JADX WARN: Removed duplicated region for block: B:73:0x0037  */
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void onRefreshBatteryInfo(KeyguardBatteryStatus keyguardBatteryStatus) {
            boolean z;
            KeyguardIndicationController keyguardIndicationController;
            boolean z2;
            int i;
            int i2 = keyguardBatteryStatus.status;
            int i3 = 2;
            if (i2 != 2) {
                if (!(i2 == 5)) {
                    z = false;
                    keyguardIndicationController = KeyguardIndicationController.this;
                    z2 = keyguardIndicationController.mPowerPluggedIn;
                    KeyguardLogger keyguardLogger = keyguardIndicationController.mKeyguardLogger;
                    keyguardIndicationController.mPowerPluggedInWithoutCharging = keyguardBatteryStatus.isPluggedIn();
                    int i4 = keyguardBatteryStatus.plugged;
                    keyguardIndicationController.mPowerPluggedInWired = !(i4 != 1 || i4 == 2) && z;
                    keyguardIndicationController.mPowerPluggedInWireless = !(i4 != 4) && z;
                    keyguardIndicationController.mPowerPluggedInDock = !(i4 != 8) && z;
                    keyguardIndicationController.mPowerPluggedIn = !keyguardBatteryStatus.isPluggedIn() && z;
                    keyguardIndicationController.mPowerCharged = keyguardBatteryStatus.status != 5;
                    keyguardIndicationController.mProtectedFullyCharged = keyguardBatteryStatus.protectedFully;
                    i = keyguardBatteryStatus.maxChargingWattage;
                    keyguardIndicationController.mChargingWattage = i;
                    Context context = keyguardIndicationController.mContext;
                    int integer = context.getResources().getInteger(R.integer.config_chargingSlowlyThreshold);
                    int integer2 = context.getResources().getInteger(R.integer.config_chargingFastThreshold);
                    if (i > 0) {
                        i3 = -1;
                    } else if (i < integer) {
                        i3 = 0;
                    } else if (i <= integer2) {
                        i3 = 1;
                    }
                    keyguardIndicationController.mChargingSpeed = i3;
                    keyguardIndicationController.mBatteryLevel = keyguardBatteryStatus.level;
                    keyguardIndicationController.mBatteryPresent = keyguardBatteryStatus.present;
                    keyguardIndicationController.mBatteryDefender = keyguardBatteryStatus.chargingStatus != 4;
                    keyguardIndicationController.mChangingType = keyguardBatteryStatus.getChargingType();
                    keyguardIndicationController.mIncompatibleCharger = ((Boolean) keyguardBatteryStatus.incompatibleCharger.orElse(Boolean.FALSE)).booleanValue();
                    keyguardIndicationController.mChargingTimeRemaining = !keyguardIndicationController.mPowerPluggedIn ? keyguardIndicationController.mBatteryInfo.computeChargeTimeRemaining() : -1L;
                    if (!keyguardBatteryStatus.isPluggedIn()) {
                        keyguardIndicationController.mResumedChargingAdaptiveProtection = false;
                    }
                    HandlerC25152 handlerC25152 = keyguardIndicationController.mHandler;
                    if (z2 && keyguardIndicationController.mPowerPluggedIn) {
                        keyguardIndicationController.mIsNeededShowChargingType = true;
                        handlerC25152.removeMessages(100);
                        handlerC25152.sendEmptyMessageDelayed(100, 3000L);
                    } else if (!keyguardIndicationController.mPowerPluggedIn) {
                        handlerC25152.removeMessages(100);
                        keyguardIndicationController.mIsNeededShowChargingType = false;
                    }
                    keyguardLogger.logRefreshBatteryInfo(z, keyguardIndicationController.mBatteryLevel, keyguardIndicationController.mPowerPluggedIn, keyguardIndicationController.mBatteryDefender);
                }
            }
            z = true;
            keyguardIndicationController = KeyguardIndicationController.this;
            z2 = keyguardIndicationController.mPowerPluggedIn;
            KeyguardLogger keyguardLogger2 = keyguardIndicationController.mKeyguardLogger;
            keyguardIndicationController.mPowerPluggedInWithoutCharging = keyguardBatteryStatus.isPluggedIn();
            int i42 = keyguardBatteryStatus.plugged;
            keyguardIndicationController.mPowerPluggedInWired = !(i42 != 1 || i42 == 2) && z;
            keyguardIndicationController.mPowerPluggedInWireless = !(i42 != 4) && z;
            keyguardIndicationController.mPowerPluggedInDock = !(i42 != 8) && z;
            keyguardIndicationController.mPowerPluggedIn = !keyguardBatteryStatus.isPluggedIn() && z;
            keyguardIndicationController.mPowerCharged = keyguardBatteryStatus.status != 5;
            keyguardIndicationController.mProtectedFullyCharged = keyguardBatteryStatus.protectedFully;
            i = keyguardBatteryStatus.maxChargingWattage;
            keyguardIndicationController.mChargingWattage = i;
            Context context2 = keyguardIndicationController.mContext;
            int integer3 = context2.getResources().getInteger(R.integer.config_chargingSlowlyThreshold);
            int integer22 = context2.getResources().getInteger(R.integer.config_chargingFastThreshold);
            if (i > 0) {
            }
            keyguardIndicationController.mChargingSpeed = i3;
            keyguardIndicationController.mBatteryLevel = keyguardBatteryStatus.level;
            keyguardIndicationController.mBatteryPresent = keyguardBatteryStatus.present;
            keyguardIndicationController.mBatteryDefender = keyguardBatteryStatus.chargingStatus != 4;
            keyguardIndicationController.mChangingType = keyguardBatteryStatus.getChargingType();
            keyguardIndicationController.mIncompatibleCharger = ((Boolean) keyguardBatteryStatus.incompatibleCharger.orElse(Boolean.FALSE)).booleanValue();
            keyguardIndicationController.mChargingTimeRemaining = !keyguardIndicationController.mPowerPluggedIn ? keyguardIndicationController.mBatteryInfo.computeChargeTimeRemaining() : -1L;
            if (!keyguardBatteryStatus.isPluggedIn()) {
            }
            HandlerC25152 handlerC251522 = keyguardIndicationController.mHandler;
            if (z2) {
            }
            if (!keyguardIndicationController.mPowerPluggedIn) {
            }
            keyguardLogger2.logRefreshBatteryInfo(z, keyguardIndicationController.mBatteryLevel, keyguardIndicationController.mPowerPluggedIn, keyguardIndicationController.mBatteryDefender);
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
            KeyguardIndicationController.this.showBiometricMessage(charSequence, null);
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onTrustChanged(int i) {
            KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
            if (KeyguardIndicationController.m1701$$Nest$misCurrentUser(keyguardIndicationController, i)) {
                keyguardIndicationController.getClass();
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onTrustGrantedForCurrentUser(boolean z, TrustGrantFlags trustGrantFlags, String str) {
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

        public final boolean shouldSuppressFingerprintError(int i) {
            if (!KeyguardIndicationController.this.mKeyguardUpdateMonitor.isUnlockingWithBiometricAllowed(true)) {
                if (!(i == 9 || i == 7)) {
                    return true;
                }
            }
            return i == 5 || i == 10 || i == 19;
        }
    }

    /* renamed from: -$$Nest$misCurrentUser, reason: not valid java name */
    public static boolean m1701$$Nest$misCurrentUser(KeyguardIndicationController keyguardIndicationController, int i) {
        return ((UserTrackerImpl) keyguardIndicationController.mUserTracker).getUserId() == i;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v8, types: [android.os.Handler, com.android.systemui.statusbar.KeyguardIndicationController$2] */
    /* JADX WARN: Type inference failed for: r5v1, types: [com.android.systemui.statusbar.KeyguardIndicationController$1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v1, types: [com.android.systemui.statusbar.KeyguardIndicationController$5] */
    public KeyguardIndicationController(Context context, Looper looper, WakeLock.Builder builder, KeyguardStateController keyguardStateController, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, DockManager dockManager, BroadcastDispatcher broadcastDispatcher, DevicePolicyManager devicePolicyManager, IBatteryStats iBatteryStats, UserManager userManager, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, FalsingManager falsingManager, AuthController authController, LockPatternUtils lockPatternUtils, ScreenLifecycle screenLifecycle, KeyguardBypassController keyguardBypassController, AccessibilityManager accessibilityManager, FaceHelpMessageDeferral faceHelpMessageDeferral, KeyguardLogger keyguardLogger, AlternateBouncerInteractor alternateBouncerInteractor, AlarmManager alarmManager, UserTracker userTracker, FeatureFlags featureFlags) {
        final int i = 0;
        final int i2 = 1;
        ?? r5 = new ScreenLifecycle.Observer() { // from class: com.android.systemui.statusbar.KeyguardIndicationController.1
            @Override // com.android.systemui.keyguard.ScreenLifecycle.Observer
            public final void onScreenTurnedOn() {
                String str;
                KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
                keyguardIndicationController.mHandler.removeMessages(2);
                if (keyguardIndicationController.mBiometricErrorMessageToShowOnScreenOn != null) {
                    if (keyguardIndicationController.mFaceLockedOutThisAuthSession) {
                        str = keyguardIndicationController.mContext.getString(keyguardIndicationController.canUnlockWithFingerprint() ? R.string.keyguard_suggest_fingerprint : R.string.keyguard_unlock);
                    } else {
                        str = null;
                    }
                    keyguardIndicationController.showBiometricMessage(keyguardIndicationController.mBiometricErrorMessageToShowOnScreenOn, str);
                    keyguardIndicationController.mHideBiometricMessageHandler.schedule(2, 4100L);
                    keyguardIndicationController.mBiometricErrorMessageToShowOnScreenOn = null;
                }
            }
        };
        this.mScreenObserver = r5;
        this.mStatusBarStateListener = new C25174();
        this.mKeyguardStateCallback = new KeyguardStateController.Callback() { // from class: com.android.systemui.statusbar.KeyguardIndicationController.5
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardShowingChanged() {
                KeyguardIndicationController keyguardIndicationController = KeyguardIndicationController.this;
                if (((KeyguardStateControllerImpl) keyguardIndicationController.mKeyguardStateController).mShowing) {
                    return;
                }
                keyguardIndicationController.mKeyguardLogger.buffer.log("KeyguardIndication", LogLevel.DEBUG, "clear messages", (Throwable) null);
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
                    keyguardIndicationRotateTextViewController.mCurrIndicationType = -1;
                    ((ArrayList) keyguardIndicationRotateTextViewController.mIndicationQueue).clear();
                    ((HashMap) keyguardIndicationRotateTextViewController.mIndicationMessages).clear();
                    ((KeyguardIndicationTextView) keyguardIndicationRotateTextViewController.mView).clearMessages();
                }
            }

            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onUnlockedChanged() {
                KeyguardIndicationController.this.getClass();
            }
        };
        this.mContext = context;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mDevicePolicyManager = devicePolicyManager;
        this.mKeyguardStateController = keyguardStateController;
        this.mStatusBarStateController = statusBarStateController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mDockManager = dockManager;
        builder.mTag = "Doze:KeyguardIndication";
        this.mWakeLock = new SettableWakeLock(builder.build(), "KeyguardIndication");
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
        screenLifecycle.addObserver(r5);
        this.mAlternateBouncerInteractor = alternateBouncerInteractor;
        this.mUserTracker = userTracker;
        this.mFeatureFlags = featureFlags;
        this.mFaceAcquiredMessageDeferral = faceHelpMessageDeferral;
        this.mCoExFaceAcquisitionMsgIdsToShow = new HashSet();
        for (int i3 : context.getResources().getIntArray(R.array.config_face_help_msgs_when_fingerprint_enrolled)) {
            ((HashSet) this.mCoExFaceAcquisitionMsgIdsToShow).add(Integer.valueOf(i3));
        }
        ?? r1 = new Handler(looper) { // from class: com.android.systemui.statusbar.KeyguardIndicationController.2
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i4 = message.what;
                if (i4 == 1) {
                    KeyguardIndicationController.this.showActionToUnlock();
                } else if (i4 == 2) {
                    KeyguardIndicationController.this.mBiometricErrorMessageToShowOnScreenOn = null;
                } else if (i4 == 100) {
                    KeyguardIndicationController.this.mIsNeededShowChargingType = false;
                }
            }
        };
        this.mHandler = r1;
        this.mHideTransientMessageHandler = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener(this) { // from class: com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda0
            public final /* synthetic */ KeyguardIndicationController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                switch (i) {
                    case 0:
                        this.f$0.hideTransientIndication();
                        break;
                    default:
                        this.f$0.hideBiometricMessage();
                        break;
                }
            }
        }, "KeyguardIndication", r1);
        this.mHideBiometricMessageHandler = new AlarmTimeout(alarmManager, new AlarmManager.OnAlarmListener(this) { // from class: com.android.systemui.statusbar.KeyguardIndicationController$$ExternalSyntheticLambda0
            public final /* synthetic */ KeyguardIndicationController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.app.AlarmManager.OnAlarmListener
            public final void onAlarm() {
                switch (i2) {
                    case 0:
                        this.f$0.hideTransientIndication();
                        break;
                    default:
                        this.f$0.hideBiometricMessage();
                        break;
                }
            }
        }, "KeyguardIndication", r1);
    }

    public final boolean canUnlockWithFingerprint() {
        int userId = ((UserTrackerImpl) this.mUserTracker).getUserId();
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        return keyguardUpdateMonitor.getCachedIsUnlockWithFingerprintPossible(userId) && keyguardUpdateMonitor.isUnlockingWithFingerprintAllowed();
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0135, code lost:
    
        if (r1 != false) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x016e, code lost:
    
        r4 = com.android.systemui.R.string.keyguard_plugged_in;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x016a, code lost:
    
        r4 = com.android.systemui.R.string.keyguard_indication_charging_time;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0168, code lost:
    
        if (r1 != false) goto L46;
     */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0180  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0193  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void dump(PrintWriter printWriter, String[] strArr) {
        int i;
        String string;
        StringBuilder m75m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m75m(printWriter, "KeyguardIndicationController:", "  mInitialTextColorState: ");
        m75m.append(this.mInitialTextColorState);
        printWriter.println(m75m.toString());
        StringBuilder m77m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder("  mPowerPluggedInWired: "), this.mPowerPluggedInWired, printWriter, "  mPowerPluggedIn: "), this.mPowerPluggedIn, printWriter, "  mPowerCharged: "), this.mPowerCharged, printWriter, "  mChargingSpeed: "), this.mChargingSpeed, printWriter, "  mChargingWattage: "), this.mChargingWattage, printWriter, "  mMessageToShowOnScreenOn: ");
        m77m.append(this.mBiometricErrorMessageToShowOnScreenOn);
        printWriter.println(m77m.toString());
        StringBuilder m64m = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder("  mDozing: "), this.mDozing, printWriter, "  mTransientIndication: ");
        m64m.append((Object) this.mTransientIndication);
        printWriter.println(m64m.toString());
        printWriter.println("  mBiometricMessage: " + ((Object) this.mBiometricMessage));
        printWriter.println("  mBiometricMessageFollowUp: " + ((Object) this.mBiometricMessageFollowUp));
        StringBuilder m64m2 = KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m77m(new StringBuilder("  mBatteryLevel: "), this.mBatteryLevel, printWriter, "  mBatteryPresent: "), this.mBatteryPresent, printWriter, "  AOD text: ");
        KeyguardIndicationTextView keyguardIndicationTextView = this.mTopIndicationView;
        m64m2.append((Object) (keyguardIndicationTextView == null ? null : keyguardIndicationTextView.getText()));
        printWriter.println(m64m2.toString());
        StringBuilder sb = new StringBuilder("  computePowerIndication(): ");
        boolean z = this.mBatteryDefender;
        Context context = this.mContext;
        if (z) {
            string = context.getResources().getString(R.string.keyguard_plugged_in_charging_limited, NumberFormat.getPercentInstance().format(this.mBatteryLevel / 100.0f));
        } else if (this.mPowerPluggedIn && this.mIncompatibleCharger) {
            string = context.getResources().getString(R.string.keyguard_plugged_in_incompatible_charger, NumberFormat.getPercentInstance().format(this.mBatteryLevel / 100.0f));
        } else if (this.mPowerCharged) {
            string = context.getResources().getString(R.string.keyguard_charged);
        } else {
            boolean z2 = this.mChargingTimeRemaining > 0;
            if (this.mPowerPluggedInWired) {
                int i2 = this.mChargingSpeed;
                if (i2 == 0) {
                    i = z2 ? R.string.keyguard_indication_charging_time_slowly : R.string.keyguard_plugged_in_charging_slowly;
                } else if (i2 == 2) {
                    i = z2 ? R.string.keyguard_indication_charging_time_fast : R.string.keyguard_plugged_in_charging_fast;
                }
                String format = NumberFormat.getPercentInstance().format(this.mBatteryLevel / 100.0f);
                string = !z2 ? context.getResources().getString(i, Formatter.formatShortElapsedTimeRoundingUpToMinutes(context, this.mChargingTimeRemaining), format) : context.getResources().getString(i, format);
            } else {
                if (this.mPowerPluggedInWireless) {
                    i = z2 ? R.string.keyguard_indication_charging_time_wireless : R.string.keyguard_plugged_in_wireless;
                } else if (this.mPowerPluggedInDock) {
                    i = z2 ? R.string.keyguard_indication_charging_time_dock : R.string.keyguard_plugged_in_dock;
                }
                String format2 = NumberFormat.getPercentInstance().format(this.mBatteryLevel / 100.0f);
                if (!z2) {
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
        this.mHideTransientMessageHandler.schedule(2, j);
    }

    public final void init() {
        if (this.mInited) {
            return;
        }
        this.mInited = true;
        this.mDockManager.getClass();
        this.mKeyguardUpdateMonitor.registerCallback(getKeyguardCallback());
        StatusBarStateController statusBarStateController = this.mStatusBarStateController;
        C25174 c25174 = this.mStatusBarStateListener;
        statusBarStateController.addCallback(c25174);
        ((KeyguardStateControllerImpl) this.mKeyguardStateController).addCallback(this.mKeyguardStateCallback);
        c25174.onDozingChanged(statusBarStateController.isDozing());
    }

    public boolean isInLifeStyleArea(MotionEvent motionEvent) {
        return false;
    }

    /* JADX WARN: Type inference failed for: r7v15, types: [com.android.systemui.statusbar.KeyguardIndicationController$3] */
    public void setIndicationArea(ViewGroup viewGroup) {
        this.mIndicationArea = viewGroup;
        this.mTopIndicationView = (KeyguardIndicationTextView) viewGroup.findViewById(R.id.keyguard_indication_text);
        this.mLifeStyleIndicationView = (KeyguardIndicationTextView) viewGroup.findViewById(R.id.keyguard_life_style_text);
        this.mLockScreenIndicationView = (KeyguardIndicationTextView) viewGroup.findViewById(R.id.keyguard_indication_text_bottom);
        KeyguardIndicationTextView keyguardIndicationTextView = this.mTopIndicationView;
        this.mInitialTextColorState = keyguardIndicationTextView != null ? keyguardIndicationTextView.getTextColors() : ColorStateList.valueOf(-1);
        KeyguardIndicationRotateTextViewController keyguardIndicationRotateTextViewController = this.mRotateTextViewController;
        if (keyguardIndicationRotateTextViewController != null) {
            keyguardIndicationRotateTextViewController.mView.removeOnAttachStateChangeListener(keyguardIndicationRotateTextViewController.mOnAttachStateListener);
            keyguardIndicationRotateTextViewController.onViewDetached();
        }
        if (this.mRotateTextViewController == null) {
            this.mRotateTextViewController = new KeyguardIndicationRotateTextViewController(this.mLockScreenIndicationView, this.mExecutor, this.mStatusBarStateController, this.mKeyguardLogger, this.mFeatureFlags);
        }
        boolean booleanValue = ((Boolean) DejankUtils.whitelistIpcs(new KeyguardIndicationController$$ExternalSyntheticLambda1(this, 0))).booleanValue();
        this.mOrganizationOwnedDevice = booleanValue;
        if (booleanValue) {
            ((ExecutorImpl) this.mBackgroundExecutor).execute(new KeyguardIndicationController$$ExternalSyntheticLambda2(this));
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
                        boolean booleanValue2 = ((Boolean) DejankUtils.whitelistIpcs(new KeyguardIndicationController$$ExternalSyntheticLambda1(keyguardIndicationController, 0))).booleanValue();
                        keyguardIndicationController.mOrganizationOwnedDevice = booleanValue2;
                        if (!booleanValue2) {
                            keyguardIndicationController.mRotateTextViewController.hideIndication(1);
                            return;
                        } else {
                            ((ExecutorImpl) keyguardIndicationController.mBackgroundExecutor).execute(new KeyguardIndicationController$$ExternalSyntheticLambda2(keyguardIndicationController));
                            return;
                        }
                    }
                    KeyguardIndicationController keyguardIndicationController2 = KeyguardIndicationController.this;
                    keyguardIndicationController2.getClass();
                    keyguardIndicationController2.mSleepChargingEvent = intent.getStringExtra("sleep_charging_event");
                    Log.d("KeyguardIndication", "sleepCharging Changed - " + keyguardIndicationController2.mSleepChargingEvent);
                    if (!"off".equals(intent.getStringExtra("sleep_charging_event"))) {
                        String stringExtra = intent.getStringExtra("sleep_charging_finish_time");
                        AbstractC0000x2c234b15.m3m("sleepCharging finish time - ", stringExtra, "KeyguardIndication");
                        keyguardIndicationController2.mSleepChargingEventFinishTime = stringExtra;
                    } else {
                        if (keyguardIndicationController2.mPowerPluggedInWithoutCharging) {
                            keyguardIndicationController2.mResumedChargingAdaptiveProtection = true;
                        }
                        Log.d("KeyguardIndication", "sleepCharging finish time - null");
                        keyguardIndicationController2.mSleepChargingEventFinishTime = null;
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
        } else if (!this.mHideTransientMessageHandler.mScheduled) {
            hideTransientIndication();
        }
        if (this.mLockScreenIndicationView != null) {
            if (this.mOrganizationOwnedDevice) {
                ((ExecutorImpl) this.mBackgroundExecutor).execute(new KeyguardIndicationController$$ExternalSyntheticLambda2(this));
            } else {
                this.mRotateTextViewController.hideIndication(1);
            }
            this.mLockScreenIndicationView.setVisibility(z ? 0 : 8);
        }
    }

    public final void showActionToUnlock() {
        boolean z = this.mDozing;
        UserTracker userTracker = this.mUserTracker;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (!z || keyguardUpdateMonitor.getUserCanSkipBouncer(((UserTrackerImpl) userTracker).getUserId())) {
            boolean isBouncerShowing = this.mStatusBarKeyguardViewManager.isBouncerShowing();
            Context context = this.mContext;
            if (isBouncerShowing) {
                if (this.mAlternateBouncerInteractor.isVisibleState() || !keyguardUpdateMonitor.mIsFaceEnrolled || keyguardUpdateMonitor.getIsFaceAuthenticated()) {
                    return;
                }
                this.mStatusBarKeyguardViewManager.setKeyguardMessage(context.getString(R.string.keyguard_retry), this.mInitialTextColorState);
                return;
            }
            if (!keyguardUpdateMonitor.getUserCanSkipBouncer(((UserTrackerImpl) userTracker).getUserId())) {
                showBiometricMessage(context.getString(R.string.keyguard_unlock), null);
                return;
            }
            boolean isFaceAuthenticated = keyguardUpdateMonitor.getIsFaceAuthenticated();
            boolean isUdfpsSupported = keyguardUpdateMonitor.isUdfpsSupported();
            AccessibilityManager accessibilityManager = this.mAccessibilityManager;
            boolean z2 = accessibilityManager.isEnabled() || accessibilityManager.isTouchExplorationEnabled();
            if (isUdfpsSupported && isFaceAuthenticated) {
                if (z2) {
                    showBiometricMessage(context.getString(R.string.keyguard_face_successful_unlock), context.getString(R.string.keyguard_unlock));
                    return;
                } else {
                    showBiometricMessage(context.getString(R.string.keyguard_face_successful_unlock), context.getString(R.string.keyguard_unlock_press));
                    return;
                }
            }
            if (isFaceAuthenticated) {
                showBiometricMessage(context.getString(R.string.keyguard_face_successful_unlock), context.getString(R.string.keyguard_unlock));
                return;
            }
            if (!isUdfpsSupported) {
                showBiometricMessage(context.getString(R.string.keyguard_unlock), null);
            } else if (z2) {
                showBiometricMessage(context.getString(R.string.keyguard_unlock), null);
            } else {
                showBiometricMessage(context.getString(R.string.keyguard_unlock_press), null);
            }
        }
    }

    public final void showBiometricMessage(CharSequence charSequence, CharSequence charSequence2) {
        if (TextUtils.equals(charSequence, this.mBiometricMessage) && TextUtils.equals(charSequence2, this.mBiometricMessageFollowUp)) {
            return;
        }
        this.mBiometricMessage = charSequence;
        this.mBiometricMessageFollowUp = charSequence2;
        removeMessages(1);
        this.mHideBiometricMessageHandler.schedule(2, (TextUtils.isEmpty(this.mBiometricMessage) || TextUtils.isEmpty(this.mBiometricMessageFollowUp)) ? 4100L : 5200L);
        updateBiometricMessage();
    }

    public final void showErrorMessageNowOrLater(String str, String str2) {
        if (this.mStatusBarKeyguardViewManager.isBouncerShowing()) {
            this.mStatusBarKeyguardViewManager.setKeyguardMessage(str, this.mInitialTextColorState);
        } else if (this.mScreenLifecycle.mScreenState == 2) {
            showBiometricMessage(str, str2);
        } else {
            this.mBiometricErrorMessageToShowOnScreenOn = str;
        }
    }

    public void showTransientIndication(int i) {
        showTransientIndication(this.mContext.getResources().getString(i));
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
            builder.mMinVisibilityMillis = 2600L;
            builder.mTextColor = this.mInitialTextColorState;
            keyguardIndicationRotateTextViewController.updateIndication(11, builder.build(), true);
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
        keyguardIndicationRotateTextViewController2.updateIndication(12, builder2.build(), true);
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
        keyguardIndicationRotateTextViewController.updateIndication(5, builder.build(), true);
    }

    public void showTransientIndication(CharSequence charSequence) {
        this.mTransientIndication = charSequence;
        hideTransientIndicationDelayed(4100L);
        updateTransient();
    }

    public void setDozing(boolean z) {
    }

    public void setUpperTextView(KeyguardIndicationTextView keyguardIndicationTextView) {
    }

    public void updateLifeStyleInfo(Intent intent) {
    }

    public void onConfigChanged() {
    }
}
