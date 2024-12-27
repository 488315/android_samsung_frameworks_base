package com.android.keyguard;

import android.animation.ObjectAnimator;
import android.app.SemWallpaperColors;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.hardware.biometrics.BiometricSourceType;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.sec.enterprise.auditlog.AuditLog;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.SuggestionsAdapter$$ExternalSyntheticOutline0;
import androidx.compose.runtime.snapshots.SnapshotStateObserver$$ExternalSyntheticOutline0;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockPatternView;
import com.android.internal.widget.LockscreenCredential;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardPatternViewController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.Log;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.pluginlock.PluginLockInstancePolicy;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DelayableMarqueeTextView;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.vibrate.VibrationUtil;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.samsung.android.graphics.spr.animation.interpolator.SineInOut90;
import com.samsung.android.knox.ContainerProxy;
import com.samsung.android.knox.zt.internal.IKnoxZtInternalService;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class KeyguardSecPatternViewController extends KeyguardPatternViewController {
    public final LinearLayout mBottomView;
    public boolean mBouncerShowing;
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass1 mConfigurationListener;
    public final LinearLayout mContainer;
    public SecCountDownTimer mCountdownTimer;
    public int mCurrentOrientation;
    public int mCurrentRotation;
    public final AnonymousClass5 mDisplayListener;
    public final LinearLayout mEcaFlexContainer;
    public final View mEcaView;
    public final EmergencyButtonController mEmergencyButtonController;
    public boolean mGoingToSleep;
    public final Handler mHandler;
    public final KeyguardHintTextArea mHintText;
    public boolean mIsNightModeOn;
    public final KeyguardTextBuilder mKeyguardTextBuilder;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public final AnonymousClass4 mKnoxStateCallback;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public final LinearLayout mMessageArea;
    public int mPromptReason;
    public int mSecondsRemaining;
    public final LinearLayout mSecurityView;
    public final LinearLayout mSplitTouchView;
    public final KeyguardSecPatternViewController$$ExternalSyntheticLambda1 mUpdateLayoutRunnable;
    public final VibrationUtil mVibrationUtil;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.keyguard.KeyguardSecPatternViewController$6, reason: invalid class name */
    public abstract /* synthetic */ class AnonymousClass6 {
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
    public final class SecUnlockPatternListener extends KeyguardPatternViewController.UnlockPatternListener {
        public SecUnlockPatternListener() {
            super();
        }

        @Override // com.android.keyguard.KeyguardPatternViewController.UnlockPatternListener
        public final void onPatternChecked(int i, int i2, boolean z, boolean z2) {
            boolean z3 = KeyguardSecPatternViewController.this.mSelectedUserInteractor.getSelectedUserId(false) == i;
            Log.d("KeyguardSecPatternViewController", "!@onPatternChecked matched=%b timeoutMs=%d userId=%d", Boolean.valueOf(z), Integer.valueOf(i2), Integer.valueOf(i));
            KeyguardSecurityCallback keyguardSecurityCallback = KeyguardSecPatternViewController.this.getKeyguardSecurityCallback();
            if (z) {
                KeyguardSecPatternViewController.this.mVibrationUtil.playVibration(1);
                if (((KeyguardPatternViewController) KeyguardSecPatternViewController.this).mKeyguardUpdateMonitor.isForgotPasswordView()) {
                    KeyguardSecPatternViewController.this.getKeyguardSecurityCallback().setPrevCredential(KeyguardSecPatternViewController.this.mPrevCredential);
                }
                KeyguardSecPatternViewController.this.getKeyguardSecurityCallback().reportUnlockAttempt(i, 0, true);
                if (z3) {
                    KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                    if (keyguardSecPatternViewController.mGoingToSleep || !((KeyguardPatternViewController) keyguardSecPatternViewController).mKeyguardUpdateMonitor.mDeviceInteractive) {
                        return;
                    }
                    keyguardSecPatternViewController.mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Correct);
                    keyguardSecurityCallback.dismiss(true, i, KeyguardSecPatternViewController.this.mSecurityMode);
                    return;
                }
                return;
            }
            KeyguardSecPatternViewController.this.mVibrationUtil.playVibration(114);
            KeyguardSecPatternViewController.this.mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
            if (z2) {
                keyguardSecurityCallback.reportUnlockAttempt(i, i2, false);
                if (((KnoxStateMonitorImpl) KeyguardSecPatternViewController.this.mKnoxStateMonitor).isDeviceDisabledForMaxFailedAttempt()) {
                    KeyguardSecPatternViewController.this.getClass();
                    AuditLog.logAsUser(5, 1, true, Process.myPid(), "KeyguardPatternView", String.format("User %d has exceeded number of authentication failure limit when using pattern authentication", Integer.valueOf(i)), i);
                    KeyguardSecPatternViewController.this.getClass();
                    try {
                        IKnoxZtInternalService asInterface = IKnoxZtInternalService.Stub.asInterface(ServiceManager.getService("knoxztinternal"));
                        if (asInterface != null) {
                            asInterface.notifyFrameworkEvent(5, 0, (Bundle) null);
                        }
                    } catch (Throwable th) {
                        th.printStackTrace();
                    }
                    KeyguardSecPatternViewController.this.disableDevicePermanently$1();
                } else {
                    ((KnoxStateMonitorImpl) KeyguardSecPatternViewController.this.mKnoxStateMonitor).isDisableDeviceByMultifactor();
                    if (i2 == 0) {
                        if (KeyguardSecPatternViewController.this.isHintText$1() && KeyguardSecPatternViewController.this.mHintText.getVisibility() == 8) {
                            KeyguardSecPatternViewController.this.mHintText.setVisibility(0);
                        }
                    } else if (i2 > 0 && !((KeyguardPatternViewController) KeyguardSecPatternViewController.this).mKeyguardUpdateMonitor.isPermanentLock()) {
                        if (KeyguardSecPatternViewController.this.isHintText$1()) {
                            KeyguardSecPatternViewController.this.mHintText.setVisibility(8);
                        }
                        KeyguardSecPatternViewController.this.setMessageTimeout(true);
                        KeyguardSecPatternViewController.this.handleAttemptLockout((!((KeyguardPatternViewController) KeyguardSecPatternViewController.this).mKeyguardUpdateMonitor.isForgotPasswordView() || DeviceType.isWeaverDevice()) ? ((KeyguardPatternViewController) KeyguardSecPatternViewController.this).mKeyguardUpdateMonitor.setLockoutAttemptDeadline(i, i2) : ((KeyguardPatternViewController) KeyguardSecPatternViewController.this).mKeyguardUpdateMonitor.setLockoutAttemptDeadline(0, i2));
                        ((KeyguardPatternViewController) KeyguardSecPatternViewController.this).mKeyguardUpdateMonitor.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_STRONG_AUTH_CHANGED);
                    }
                }
            }
            if (i2 == 0) {
                KeyguardSecPatternViewController.this.setMessageTimeout(false);
                int remainingAttempt = ((KeyguardPatternViewController) KeyguardSecPatternViewController.this).mKeyguardUpdateMonitor.getRemainingAttempt(2);
                String string = KeyguardSecPatternViewController.this.getContext().getString(R.string.kg_incorrect_pattern);
                if (remainingAttempt > 0) {
                    string = ComponentActivity$1$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, " ("), KeyguardSecPatternViewController.this.getResources().getQuantityString(R.plurals.kg_attempt_left, remainingAttempt, Integer.valueOf(remainingAttempt)), ")");
                }
                KeyguardSecPatternViewController.this.mMessageAreaController.setMessage$1(string, false);
                KeyguardSecPatternViewController.this.mMessageAreaController.announceForAccessibility(string);
                KeyguardSecPatternViewController.this.mMessageAreaController.displayFailedAnimation();
                KeyguardSecPatternViewController keyguardSecPatternViewController2 = KeyguardSecPatternViewController.this;
                keyguardSecPatternViewController2.mLockPatternView.postDelayed(keyguardSecPatternViewController2.mCancelPatternRunnable, DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
            }
        }

        @Override // com.android.keyguard.KeyguardPatternViewController.UnlockPatternListener
        public final void onPatternDetected(List list) {
            Log.d("KeyguardSecPatternViewController", "onPatternDetected");
            KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
            KnoxStateMonitor knoxStateMonitor = keyguardSecPatternViewController.mKnoxStateMonitor;
            if (knoxStateMonitor != null) {
                if (((KnoxStateMonitorImpl) knoxStateMonitor).isDualDarDeviceOwner(keyguardSecPatternViewController.mSelectedUserInteractor.getSelectedUserId(false))) {
                    KeyguardSecPatternViewController keyguardSecPatternViewController2 = KeyguardSecPatternViewController.this;
                    if (!((KnoxStateMonitorImpl) keyguardSecPatternViewController2.mKnoxStateMonitor).isDualDarInnerLayerUnlocked(keyguardSecPatternViewController2.mSelectedUserInteractor.getSelectedUserId(false))) {
                        android.util.Log.d("KeyguardSecPatternViewController.DDAR", "Pattern detected from DualDAR DO");
                        ((KeyguardPatternViewController) KeyguardSecPatternViewController.this).mKeyguardUpdateMonitor.setCredentialAttempted();
                        KeyguardSecPatternViewController.this.mLockPatternView.disableInput();
                        AsyncTask asyncTask = KeyguardSecPatternViewController.this.mPendingLockCheck;
                        if (asyncTask != null) {
                            asyncTask.cancel(false);
                        }
                        final int selectedUserId = KeyguardSecPatternViewController.this.mSelectedUserInteractor.getSelectedUserId(false);
                        if (list.size() < 4) {
                            android.util.Log.e("KeyguardSecPatternViewController", "!@Password too short!");
                            if (list.size() == 1) {
                                KeyguardSecPatternViewController.this.mFalsingCollector.updateFalseConfidence(FalsingClassifier.Result.falsed(0.7d, SecUnlockPatternListener.class.getSimpleName(), "empty pattern input"));
                            }
                            KeyguardSecPatternViewController.this.mLockPatternView.enableInput();
                            onPatternChecked(selectedUserId, 0, false, false);
                            return;
                        }
                        KeyguardSecPatternViewController.this.mLatencyTracker.onActionStart(3);
                        KeyguardSecPatternViewController.this.mLatencyTracker.onActionStart(4);
                        KeyguardSecPatternViewController keyguardSecPatternViewController3 = KeyguardSecPatternViewController.this;
                        keyguardSecPatternViewController3.mPendingLockCheck = LockPatternChecker.checkCredential(keyguardSecPatternViewController3.mLockPatternUtils, LockscreenCredential.createPattern(list), selectedUserId, 1, new LockPatternChecker.OnCheckCallbackForDualDarDo() { // from class: com.android.keyguard.KeyguardSecPatternViewController.SecUnlockPatternListener.1
                            public final void onCancelled() {
                                KeyguardSecPatternViewController.this.mLatencyTracker.onActionEnd(4);
                            }

                            public final void onChecked(boolean z, int i) {
                                KeyguardSecPatternViewController.this.mLatencyTracker.onActionEnd(3);
                                StringBuilder sb = new StringBuilder("onPatternDetected - onChecked - matched : ");
                                sb.append(z);
                                sb.append(", timeoutMs : ");
                                RecyclerView$$ExternalSyntheticOutline0.m(i, "KeyguardSecPatternViewController.DDAR", sb);
                                if (z) {
                                    SecUnlockPatternListener.this.onPatternChecked(selectedUserId, 0, true, true);
                                    return;
                                }
                                KeyguardSecPatternViewController.this.mLockPatternView.enableInput();
                                SecUnlockPatternListener secUnlockPatternListener = SecUnlockPatternListener.this;
                                KeyguardSecPatternViewController.this.mPendingLockCheck = null;
                                secUnlockPatternListener.onPatternChecked(selectedUserId, i, false, true);
                            }

                            public final void onInnerLayerUnlockFailed() {
                                android.util.Log.d("KeyguardSecPatternViewController.DDAR", "onPatternDetected - onInnerLayerUnlockFailed");
                                KeyguardSecPatternViewController.this.mLockPatternView.enableInput();
                                KeyguardSecPatternViewController.this.mPendingLockCheck = null;
                            }

                            public final void onInnerLayerUnlocked() {
                                KeyguardSecPatternViewController.this.mLatencyTracker.onActionEnd(4);
                                android.util.Log.d("KeyguardSecPatternViewController.DDAR", "onPatternDetected - onInnerLayerUnlocked");
                                KeyguardSecPatternViewController.this.mPendingLockCheck = null;
                            }
                        });
                        if (list.size() > 2) {
                            KeyguardSecPatternViewController.this.getKeyguardSecurityCallback().userActivity();
                            KeyguardSecPatternViewController.this.getKeyguardSecurityCallback().onUserInput();
                            return;
                        }
                        return;
                    }
                }
            }
            super.onPatternDetected(list);
        }

        @Override // com.android.keyguard.KeyguardPatternViewController.UnlockPatternListener
        public final void onPatternStart() {
            super.onPatternStart();
            Log.d("KeyguardSecPatternViewController", "onPatternStart");
            KeyguardSecPatternViewController.this.setSubSecurityMessage$1(0);
        }
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.keyguard.KeyguardSecPatternViewController$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r1v5, types: [com.android.keyguard.KeyguardSecPatternViewController$1] */
    /* JADX WARN: Type inference failed for: r1v7, types: [com.android.keyguard.KeyguardSecPatternViewController$4] */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.android.keyguard.KeyguardSecPatternViewController$5] */
    public KeyguardSecPatternViewController(KeyguardSecPatternView keyguardSecPatternView, ConfigurationController configurationController, VibrationUtil vibrationUtil, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, LatencyTracker latencyTracker, FalsingCollector falsingCollector, EmergencyButtonController emergencyButtonController, KeyguardMessageAreaController.Factory factory, DevicePostureController devicePostureController, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor) {
        super(keyguardSecPatternView, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, latencyTracker, falsingCollector, emergencyButtonController, factory, devicePostureController, featureFlags, selectedUserInteractor);
        new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
        this.mHandler = new Handler();
        this.mIsNightModeOn = false;
        this.mSecondsRemaining = -1;
        this.mUpdateLayoutRunnable = new Runnable() { // from class: com.android.keyguard.KeyguardSecPatternViewController$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                keyguardSecPatternViewController.mSecondsRemaining = -1;
                keyguardSecPatternViewController.updateLayout$1$2();
            }
        };
        this.mCurrentOrientation = 1;
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardSecPatternViewController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                keyguardSecPatternViewController.dismissTips(false);
                boolean z = (configuration.uiMode & 32) != 0;
                if (keyguardSecPatternViewController.mIsNightModeOn != z) {
                    KeyguardCarrierViewController$$ExternalSyntheticOutline0.m(new StringBuilder("night mode changed : "), keyguardSecPatternViewController.mIsNightModeOn, " -> ", z, "KeyguardSecPatternViewController");
                    keyguardSecPatternViewController.mIsNightModeOn = z;
                    if (LsRune.SECURITY_OPEN_THEME) {
                        keyguardSecPatternViewController.updateStyle(WallpaperEventNotifier.getInstance().mCurStatusFlag, WallpaperEventNotifier.getInstance().getSemWallpaperColors(false));
                    } else {
                        android.util.Log.d("KeyguardSecPatternViewController", "Can't apply night mode! NOT supported OPEN THEME feature");
                    }
                }
            }

            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onOrientationChanged(int i) {
                KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                if (keyguardSecPatternViewController.mCurrentOrientation == i || !DeviceState.shouldEnableKeyguardScreenRotation(keyguardSecPatternViewController.getContext())) {
                    return;
                }
                keyguardSecPatternViewController.mCurrentOrientation = i;
                keyguardSecPatternViewController.updateLayout$1$2();
            }
        };
        this.mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardSecPatternViewController.3
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
                if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                    KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                    if (((KeyguardPatternViewController) keyguardSecPatternViewController).mKeyguardUpdateMonitor.is2StepVerification()) {
                        keyguardSecPatternViewController.reset$1();
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) {
                int i = AnonymousClass6.$SwitchMap$android$hardware$biometrics$BiometricSourceType[biometricSourceType.ordinal()];
                if (i == 1 || i == 2) {
                    KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                    if (keyguardSecPatternViewController.mBouncerShowing && ((KeyguardPatternViewController) keyguardSecPatternViewController).mKeyguardUpdateMonitor.isUpdateSecurityMessage()) {
                        keyguardSecPatternViewController.displayDefaultSecurityMessage();
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onFinishedGoingToSleep(int i) {
                super.onFinishedGoingToSleep(i);
                KeyguardSecPatternViewController.this.mGoingToSleep = false;
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardBouncerFullyShowingChanged(boolean z) {
                KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                if (keyguardSecPatternViewController.mBouncerShowing != z) {
                    keyguardSecPatternViewController.mBouncerShowing = z;
                    if (z) {
                        keyguardSecPatternViewController.setMessageTimeout(true);
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onLockModeChanged() {
                KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                long lockoutAttemptDeadline = ((KeyguardPatternViewController) keyguardSecPatternViewController).mKeyguardUpdateMonitor.getLockoutAttemptDeadline();
                if (lockoutAttemptDeadline != 0) {
                    if (((DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class)).isDualView()) {
                        keyguardSecPatternViewController.handleAttemptLockout(lockoutAttemptDeadline);
                        return;
                    }
                    return;
                }
                keyguardSecPatternViewController.mSecondsRemaining = -1;
                SecCountDownTimer secCountDownTimer = keyguardSecPatternViewController.mCountdownTimer;
                if (secCountDownTimer != null) {
                    secCountDownTimer.cancel();
                    keyguardSecPatternViewController.mCountdownTimer = null;
                    keyguardSecPatternViewController.displayDefaultSecurityMessage();
                }
                if (LsRune.SECURITY_FINGERPRINT_IN_DISPLAY) {
                    keyguardSecPatternViewController.updateLayout$1$2();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSimulationFailToUnlock(int i) {
                KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                keyguardSecPatternViewController.mVibrationUtil.playVibration(114);
                keyguardSecPatternViewController.mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = ((KeyguardPatternViewController) keyguardSecPatternViewController).mKeyguardUpdateMonitor;
                int failedUnlockAttempts = keyguardUpdateMonitor2.getFailedUnlockAttempts(i) + 1;
                int i2 = failedUnlockAttempts % 5 == 0 ? PluginLockInstancePolicy.DISABLED_BY_SUB_USER : 0;
                SuggestionsAdapter$$ExternalSyntheticOutline0.m(failedUnlockAttempts, i2, "onSimulationFailToUnlock failedAttempts : ", " timeoutMs : ", "KeyguardSecPatternViewController");
                keyguardSecPatternViewController.getKeyguardSecurityCallback().reportUnlockAttempt(i, i2, false);
                KeyguardHintTextArea keyguardHintTextArea = keyguardSecPatternViewController.mHintText;
                if (i2 == 0) {
                    if (keyguardSecPatternViewController.isHintText$1() && keyguardHintTextArea.getVisibility() == 8) {
                        keyguardHintTextArea.setVisibility(0);
                    }
                } else if (i2 > 0) {
                    if (keyguardSecPatternViewController.isHintText$1()) {
                        keyguardHintTextArea.setVisibility(8);
                    }
                    keyguardSecPatternViewController.handleAttemptLockout(keyguardUpdateMonitor2.setLockoutAttemptDeadline(i, i2));
                    keyguardUpdateMonitor2.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_STRONG_AUTH_CHANGED);
                }
                if (i2 == 0) {
                    keyguardSecPatternViewController.setMessageTimeout(false);
                    int remainingAttempt = keyguardUpdateMonitor2.getRemainingAttempt(2);
                    String string = keyguardSecPatternViewController.getContext().getString(R.string.kg_incorrect_pattern);
                    if (remainingAttempt > 0) {
                        string = ComponentActivity$1$$ExternalSyntheticOutline0.m(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(string, " ("), keyguardSecPatternViewController.getResources().getQuantityString(R.plurals.kg_attempt_left, remainingAttempt, Integer.valueOf(remainingAttempt)), ")");
                    }
                    keyguardSecPatternViewController.mMessageAreaController.setMessage$1(string, false);
                    keyguardSecPatternViewController.mMessageAreaController.announceForAccessibility(string);
                    keyguardSecPatternViewController.mMessageAreaController.displayFailedAnimation();
                    keyguardSecPatternViewController.mLockPatternView.postDelayed(keyguardSecPatternViewController.mCancelPatternRunnable, DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStartedGoingToSleep(int i) {
                super.onStartedGoingToSleep(i);
                KeyguardSecPatternViewController.this.mGoingToSleep = true;
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStartedWakingUp() {
                KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                if (keyguardSecPatternViewController.mSecondsRemaining > 0 && ((KeyguardPatternViewController) keyguardSecPatternViewController).mKeyguardUpdateMonitor.getLockoutAttemptDeadline() == 0) {
                    keyguardSecPatternViewController.mSecondsRemaining = -1;
                }
                if (((KeyguardFastBioUnlockController) Dependency.sDependency.getDependencyInner(KeyguardFastBioUnlockController.class)).isFastWakeAndUnlockMode()) {
                    return;
                }
                keyguardSecPatternViewController.updateLayout$1$2();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onTableModeChanged(boolean z) {
                KeyguardSecPatternViewController.this.updateLayout$1$2();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitching(int i) {
                KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                KeyguardHintTextArea keyguardHintTextArea = keyguardSecPatternViewController.mHintText;
                if (keyguardHintTextArea != null) {
                    keyguardHintTextArea.mPasswordHintText = keyguardSecPatternViewController.mLockPatternUtils.getPasswordHint(i);
                    keyguardSecPatternViewController.mHintText.updateHintButton();
                    if (keyguardSecPatternViewController.isHintText$1()) {
                        keyguardSecPatternViewController.mHintText.setVisibility(0);
                    } else {
                        keyguardSecPatternViewController.mHintText.setVisibility(8);
                    }
                }
            }
        };
        this.mKnoxStateCallback = new KnoxStateMonitorCallback() { // from class: com.android.keyguard.KeyguardSecPatternViewController.4
            @Override // com.android.systemui.knox.KnoxStateMonitorCallback
            public final void onDisableDeviceWhenReachMaxFailed() {
                KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                keyguardSecPatternViewController.mLockPatternUtils.requireStrongAuth(2, keyguardSecPatternViewController.mSelectedUserInteractor.getSelectedUserId());
                keyguardSecPatternViewController.disableDevicePermanently$1();
            }

            @Override // com.android.systemui.knox.KnoxStateMonitorCallback
            public final void onDisableProfileWhenReachMaxFailed() {
                KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                keyguardSecPatternViewController.getClass();
                android.util.Log.d("KeyguardSecPatternViewController", "disableProfilePermanently");
                KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) keyguardSecPatternViewController.mKnoxStateMonitor;
                Iterator it = knoxStateMonitorImpl.getContainerIds().iterator();
                int i = -1;
                while (it.hasNext()) {
                    int intValue = ((Integer) it.next()).intValue();
                    if (knoxStateMonitorImpl.isPersona(intValue) && !knoxStateMonitorImpl.isSecureFolder(intValue)) {
                        i = intValue;
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putInt("android.intent.extra.user_handle", i);
                ContainerProxy.sendPolicyUpdate("knox.container.proxy.POLICY_ADMIN_LOCK", bundle);
            }
        };
        this.mDisplayListener = new DisplayLifecycle.Observer() { // from class: com.android.keyguard.KeyguardSecPatternViewController.5
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z) {
                KeyguardSecPatternViewController.this.reset$1();
            }
        };
        this.mEmergencyButtonController = emergencyButtonController;
        this.mConfigurationController = configurationController;
        this.mVibrationUtil = vibrationUtil;
        this.mSplitTouchView = (LinearLayout) ((KeyguardSecPatternView) this.mView).findViewById(R.id.split_touch_top_container);
        this.mMessageArea = (LinearLayout) ((KeyguardSecPatternView) this.mView).findViewById(R.id.message_area);
        this.mContainer = (LinearLayout) ((KeyguardSecPatternView) this.mView).findViewById(R.id.container);
        this.mSecurityView = (LinearLayout) ((KeyguardSecPatternView) this.mView).findViewById(R.id.keyguard_pattern_view);
        this.mBottomView = (LinearLayout) ((KeyguardSecPatternView) this.mView).findViewById(R.id.bottom_container);
        this.mEcaView = ((KeyguardSecPatternView) this.mView).findViewById(R.id.keyguard_selector_fade_container);
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mSubMessageAreaController;
        if (keyguardSecMessageAreaController != null) {
            keyguardSecMessageAreaController.setVisibility(8);
        }
        KeyguardHintTextArea keyguardHintTextArea = (KeyguardHintTextArea) ((KeyguardSecPatternView) this.mView).findViewById(R.id.hint_text_box);
        this.mHintText = keyguardHintTextArea;
        if (isHintText$1()) {
            keyguardHintTextArea.setVisibility(0);
        }
        this.mKeyguardTextBuilder = KeyguardTextBuilder.getInstance(getContext());
        this.mKnoxStateMonitor = (KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class);
        this.mEcaFlexContainer = (LinearLayout) ((KeyguardSecPatternView) this.mView).findViewById(R.id.emergency_button_container_flex);
    }

    public final void disableDevicePermanently$1() {
        android.util.Log.d("KeyguardSecPatternViewController", "disableDevicePermanently");
        this.mLockPatternView.clearPattern();
        this.mLockPatternView.setEnabled(false);
        this.mLockPatternView.setVisibility(4);
        this.mMessageAreaController.setMessage(this.mKeyguardTextBuilder.getDefaultSecurityMessage(KeyguardSecurityModel.SecurityMode.Pattern));
    }

    @Override // com.android.keyguard.KeyguardPatternViewController
    public final void displayDefaultSecurityMessage() {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (keyguardSecMessageAreaController == null) {
            android.util.Log.e("KeyguardSecPatternViewController", "displayDefaultSecurityMessage mMessageAreaController is null");
            return;
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardPatternViewController) this).mKeyguardUpdateMonitor;
        if (keyguardUpdateMonitor.isFingerprintLockedOut() || keyguardUpdateMonitor.mFaceLockedOutPermanent || keyguardUpdateMonitor.isKeyguardUnlocking()) {
            return;
        }
        setMessageTimeout(true);
        SelectedUserInteractor selectedUserInteractor = this.mSelectedUserInteractor;
        int strongAuthPrompt = SecurityUtils.getStrongAuthPrompt(selectedUserInteractor.getSelectedUserId(false));
        if (keyguardUpdateMonitor.isShowEditModeRequest()) {
            keyguardSecMessageAreaController.setMessage$1(getContext().getString(R.string.kg_edit_mode_instructions), false);
        } else {
            if (strongAuthPrompt != 0) {
                this.mPromptReason = strongAuthPrompt;
                KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(new StringBuilder("displayDefaultSecurityMessage - strongAuth ( "), this.mPromptReason, " )", "KeyguardSecPatternViewController");
                showPromptReason(this.mPromptReason);
            } else {
                String defaultSecurityMessage = this.mKeyguardTextBuilder.getDefaultSecurityMessage(KeyguardSecurityModel.SecurityMode.Pattern);
                KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("displayDefaultSecurityMessage( ", defaultSecurityMessage, " )", "KeyguardSecPatternViewController");
                keyguardSecMessageAreaController.setMessage$1(defaultSecurityMessage, false);
                keyguardSecMessageAreaController.announceForAccessibility(defaultSecurityMessage);
            }
        }
        if (keyguardUpdateMonitor.is2StepVerification()) {
            int selectedUserId = selectedUserInteractor.getSelectedUserId(false);
            if (keyguardUpdateMonitor.getLockoutBiometricAttemptDeadline() > 0) {
                keyguardSecMessageAreaController.setMessage$1("", false);
            }
            if (keyguardUpdateMonitor.getUserUnlockedWithBiometric(selectedUserId)) {
                setSubSecurityMessage$1(R.string.kg_biometrics_has_confirmed);
            } else {
                setSubSecurityMessage$1(0);
            }
        }
    }

    @Override // com.android.keyguard.KeyguardPatternViewController, com.android.keyguard.KeyguardInputViewController
    public final int getInitialMessageResId() {
        return 0;
    }

    @Override // com.android.keyguard.KeyguardPatternViewController
    public final void handleAttemptLockout(long j) {
        this.mLockPatternView.clearPattern();
        this.mLockPatternView.setEnabled(false);
        this.mLockPatternView.setVisibility(4);
        KeyguardHintTextArea keyguardHintTextArea = this.mHintText;
        if (keyguardHintTextArea != null) {
            keyguardHintTextArea.setVisibility(8);
        }
        updateForgotPasswordTextVisibility();
        if (shouldTipsPopup()) {
            Handler handler = this.mHandler;
            KeyguardInputViewController$$ExternalSyntheticLambda1 keyguardInputViewController$$ExternalSyntheticLambda1 = this.mShowTipsRunnable;
            handler.removeCallbacks(keyguardInputViewController$$ExternalSyntheticLambda1);
            handler.postDelayed(keyguardInputViewController$$ExternalSyntheticLambda1, 500L);
        }
        SecCountDownTimer secCountDownTimer = this.mCountdownTimer;
        if (secCountDownTimer != null) {
            secCountDownTimer.cancel();
            this.mCountdownTimer = null;
        }
        SecCountDownTimer secCountDownTimer2 = new SecCountDownTimer(j - SystemClock.elapsedRealtime(), 1000L, getContext(), this.mSelectedUserInteractor, ((KeyguardPatternViewController) this).mKeyguardUpdateMonitor, this.mKeyguardTextBuilder, true) { // from class: com.android.keyguard.KeyguardSecPatternViewController.2
            @Override // com.android.keyguard.SecCountDownTimer, android.os.CountDownTimer
            public final void onFinish() {
                android.util.Log.d("KeyguardSecPatternViewController", "handleAttemptLockout onFinish");
                int selectedUserId = KeyguardSecPatternViewController.this.mSelectedUserInteractor.getSelectedUserId(false);
                if (KeyguardSecPatternViewController.this.isHintText$1()) {
                    KeyguardSecPatternViewController.this.mHintText.setVisibility(0);
                }
                KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                keyguardSecPatternViewController.mSecondsRemaining = -1;
                keyguardSecPatternViewController.mMessageAreaController.scrollTo(0, 0);
                KeyguardSecPatternViewController.this.mMessageAreaController.setMovementMethod(null);
                KeyguardSecPatternViewController.this.mLockPatternView.setEnabled(true);
                KeyguardSecPatternViewController.this.mLockPatternView.setVisibility(0);
                KeyguardSecPatternViewController.this.mLockPatternUtils.clearBiometricAttemptDeadline(selectedUserId);
                ((KeyguardPatternViewController) KeyguardSecPatternViewController.this).mKeyguardUpdateMonitor.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_FACE_LOCKOUT_RESET);
                KeyguardSecPatternViewController.this.displayDefaultSecurityMessage();
                KeyguardSecPatternViewController.this.resetFor2StepVerification$1();
                KeyguardSecPatternViewController keyguardSecPatternViewController2 = KeyguardSecPatternViewController.this;
                keyguardSecPatternViewController2.mHandler.removeCallbacks(keyguardSecPatternViewController2.mUpdateLayoutRunnable);
                KeyguardSecPatternViewController keyguardSecPatternViewController3 = KeyguardSecPatternViewController.this;
                keyguardSecPatternViewController3.mHandler.post(keyguardSecPatternViewController3.mUpdateLayoutRunnable);
                KeyguardSecPatternViewController.this.dismissTips(true);
                KeyguardSecPatternViewController.this.updateForgotPasswordTextVisibility();
            }

            @Override // com.android.keyguard.SecCountDownTimer, android.os.CountDownTimer
            public final void onTick(long j2) {
                int round = (int) Math.round(j2 / 1000.0d);
                if (((KeyguardPatternViewController) KeyguardSecPatternViewController.this).mKeyguardUpdateMonitor.isHiddenInputContainer()) {
                    KeyguardSecPatternViewController.this.mSecondsRemaining = round;
                }
                super.onTick(j2);
                String str = this.mTimerText;
                if (!str.isEmpty()) {
                    KeyguardSecPatternViewController.this.mMessageAreaController.setMessage(str);
                }
                KeyguardSecPatternViewController.this.updateLayoutForAttemptRemainingBeforeWipe$1();
            }
        };
        this.mCountdownTimer = secCountDownTimer2;
        secCountDownTimer2.start();
    }

    public final boolean isHintText$1() {
        KeyguardHintTextArea keyguardHintTextArea = this.mHintText;
        if (keyguardHintTextArea != null && keyguardHintTextArea.mPasswordHintText != null) {
            int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
            KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardPatternViewController) this).mKeyguardUpdateMonitor;
            if (keyguardUpdateMonitor.getFailedUnlockAttempts(selectedUserId) > 0 && keyguardUpdateMonitor.getLockoutAttemptDeadline() == 0) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.keyguard.KeyguardPatternViewController, com.android.keyguard.KeyguardInputViewController
    public final void onResume(int i) {
        this.mPaused = false;
        if (DeviceState.isTesting()) {
            return;
        }
        reset$1();
    }

    @Override // com.android.keyguard.KeyguardPatternViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        this.mCurrentOrientation = getContext().getResources().getConfiguration().orientation;
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        this.mEmergencyButtonController.mEmergencyButtonCallback = this.mEmergencyButtonCallback;
        updateLayout$1$2();
        ((KeyguardPatternViewController) this).mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).registerCallback(this.mKnoxStateCallback);
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).addObserver(this.mDisplayListener);
        }
        this.mLockPatternView.setOnPatternListener(new SecUnlockPatternListener());
    }

    @Override // com.android.keyguard.KeyguardPatternViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        this.mEmergencyButtonController.mEmergencyButtonCallback = null;
        ((KeyguardPatternViewController) this).mKeyguardUpdateMonitor.removeCallback(this.mKeyguardUpdateMonitorCallback);
        ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).removeCallback(this.mKnoxStateCallback);
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).removeObserver(this.mDisplayListener);
        }
    }

    @Override // com.android.keyguard.KeyguardPatternViewController, com.android.keyguard.KeyguardInputViewController
    public final void reset$1() {
        SelectedUserInteractor selectedUserInteractor = this.mSelectedUserInteractor;
        this.mLockPatternView.setInStealthMode(!this.mLockPatternUtils.isVisiblePatternEnabled(selectedUserInteractor.getSelectedUserId(false)));
        this.mLockPatternView.enableInput();
        this.mLockPatternView.setEnabled(true);
        this.mLockPatternView.clearPattern();
        updateLayout$1$2();
        KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardPatternViewController) this).mKeyguardUpdateMonitor;
        long lockoutAttemptDeadline = keyguardUpdateMonitor.getLockoutAttemptDeadline();
        boolean isDualDarInnerAuthRequired = keyguardUpdateMonitor.isDualDarInnerAuthRequired(selectedUserInteractor.getSelectedUserId(false));
        KnoxStateMonitor knoxStateMonitor = this.mKnoxStateMonitor;
        if (isDualDarInnerAuthRequired) {
            long dualDarInnerLockoutAttemptDeadline$1 = ((KnoxStateMonitorImpl) knoxStateMonitor).getDualDarInnerLockoutAttemptDeadline$1();
            if (dualDarInnerLockoutAttemptDeadline$1 != 0 && dualDarInnerLockoutAttemptDeadline$1 > lockoutAttemptDeadline) {
                StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m("reset() switch to inner deadline. deadline = ", lockoutAttemptDeadline, ", innerDeadline = ");
                m.append(dualDarInnerLockoutAttemptDeadline$1);
                android.util.Log.d("KeyguardSecPatternViewController", m.toString());
                lockoutAttemptDeadline = dualDarInnerLockoutAttemptDeadline$1;
            }
        }
        KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) knoxStateMonitor;
        if (knoxStateMonitorImpl.isDeviceDisabledForMaxFailedAttempt()) {
            disableDevicePermanently$1();
            return;
        }
        knoxStateMonitorImpl.isDisableDeviceByMultifactor();
        boolean shouldLockout = shouldLockout(lockoutAttemptDeadline);
        KeyguardHintTextArea keyguardHintTextArea = this.mHintText;
        if (shouldLockout) {
            if (isHintText$1()) {
                keyguardHintTextArea.setVisibility(8);
            }
            handleAttemptLockout(lockoutAttemptDeadline);
            return;
        }
        displayDefaultSecurityMessage();
        if (isHintText$1()) {
            keyguardHintTextArea.updateHintButton();
            keyguardHintTextArea.setVisibility(0);
        }
        updateForgotPasswordTextVisibility();
        if (lockoutAttemptDeadline > 0 && keyguardUpdateMonitor.getLockoutBiometricAttemptDeadline() == 0) {
            handleAttemptLockout(0L);
        }
        this.mLockPatternView.setVisibility(0);
        resetFor2StepVerification$1();
    }

    public final void resetFor2StepVerification$1() {
        KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardPatternViewController) this).mKeyguardUpdateMonitor;
        if (!keyguardUpdateMonitor.is2StepVerification() || keyguardUpdateMonitor.getUserUnlockedWithBiometric(this.mSelectedUserInteractor.getSelectedUserId())) {
            return;
        }
        android.util.Log.d("KeyguardSecPatternViewController", "reset() - 2 step verification");
        this.mLockPatternView.clearPattern();
        this.mLockPatternView.setEnabled(false);
        this.mLockPatternView.setVisibility(4);
    }

    public final void setSubSecurityMessage$1(int i) {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController;
        if (!((KeyguardPatternViewController) this).mKeyguardUpdateMonitor.is2StepVerification() || (keyguardSecMessageAreaController = this.mSubMessageAreaController) == null) {
            return;
        }
        KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) this.mKnoxStateMonitor;
        if (knoxStateMonitorImpl.isDeviceDisabledForMaxFailedAttempt()) {
            keyguardSecMessageAreaController.setMessage$1("", false);
            keyguardSecMessageAreaController.setVisibility(8);
            return;
        }
        knoxStateMonitorImpl.isDisableDeviceByMultifactor();
        if (i == 0) {
            keyguardSecMessageAreaController.setMessage$1("", false);
            keyguardSecMessageAreaController.setVisibility(8);
        } else {
            keyguardSecMessageAreaController.setMessage$1(getContext().getString(i), false);
        }
        int i2 = this.mCurrentRotation;
        if (i2 == 1 || i2 == 3) {
            keyguardSecMessageAreaController.setVisibility(LsRune.SECURITY_SUB_DISPLAY_LOCK ? 0 : 8);
        } else {
            keyguardSecMessageAreaController.setVisibility(0);
        }
    }

    @Override // com.android.keyguard.KeyguardPatternViewController, com.android.keyguard.KeyguardInputViewController
    public final void showMessage(CharSequence charSequence, ColorStateList colorStateList, boolean z) {
        setMessageTimeout(false);
        this.mMessageAreaController.displayFailedAnimation();
        super.showMessage(charSequence, colorStateList, z);
    }

    @Override // com.android.keyguard.KeyguardPatternViewController, com.android.keyguard.KeyguardInputViewController
    public final void showPromptReason(int i) {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (keyguardSecMessageAreaController == null) {
            android.util.Log.d("KeyguardSecPatternViewController", "showPromptReason mMessageAreaController is null");
            return;
        }
        this.mPromptReason = i;
        if (i != 0) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardPatternViewController) this).mKeyguardUpdateMonitor;
            long lockoutAttemptDeadline = keyguardUpdateMonitor.getLockoutAttemptDeadline();
            if (keyguardUpdateMonitor.isDualDarInnerAuthRequired(this.mSelectedUserInteractor.getSelectedUserId(false))) {
                long dualDarInnerLockoutAttemptDeadline$1 = ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).getDualDarInnerLockoutAttemptDeadline$1();
                if (dualDarInnerLockoutAttemptDeadline$1 != 0 && dualDarInnerLockoutAttemptDeadline$1 > lockoutAttemptDeadline) {
                    StringBuilder m = SnapshotStateObserver$$ExternalSyntheticOutline0.m("showPromptReason() switch to inner deadline. deadline = ", lockoutAttemptDeadline, ", innerDeadline = ");
                    m.append(dualDarInnerLockoutAttemptDeadline$1);
                    android.util.Log.d("KeyguardSecPatternViewController", m.toString());
                    lockoutAttemptDeadline = dualDarInnerLockoutAttemptDeadline$1;
                }
            }
            if (lockoutAttemptDeadline > 0) {
                return;
            }
            KeyguardSecurityModel.SecurityMode securityMode = KeyguardSecurityModel.SecurityMode.Pattern;
            KeyguardTextBuilder keyguardTextBuilder = this.mKeyguardTextBuilder;
            String promptSecurityMessage = keyguardTextBuilder.getPromptSecurityMessage(securityMode, i);
            SpannableStringBuilder strongAuthPopupString = SecurityUtils.getStrongAuthPopupString(getContext(), securityMode, null, i);
            if (strongAuthPopupString != null) {
                keyguardSecMessageAreaController.setMovementMethod(LinkMovementMethod.getInstance());
                keyguardSecMessageAreaController.setMessage$1(strongAuthPopupString, false);
                keyguardSecMessageAreaController.scrollTo(0, 0);
            } else {
                if (promptSecurityMessage.isEmpty() || KeyguardTextBuilder.getInstance(getContext()).getStrongAuthTimeOutMessage(securityMode).isEmpty()) {
                    promptSecurityMessage = keyguardTextBuilder.getDefaultSecurityMessage(securityMode);
                }
                keyguardSecMessageAreaController.setMessage$1(promptSecurityMessage, false);
            }
        }
    }

    @Override // com.android.keyguard.KeyguardInputViewController
    public final void startAppearAnimation() {
        this.mBottomView.clearAnimation();
        SpringForce springForce = new SpringForce(1.0f);
        springForce.setStiffness(200.0f);
        springForce.setDampingRatio(0.71f);
        SpringAnimation springAnimation = new SpringAnimation(this.mBottomView, DynamicAnimation.SCALE_X);
        SpringAnimation springAnimation2 = new SpringAnimation(this.mBottomView, DynamicAnimation.SCALE_Y);
        springAnimation.mSpring = springForce;
        springAnimation.mValue = 0.7f;
        springAnimation.mStartValueIsSet = true;
        springAnimation.start();
        springAnimation2.mSpring = springForce;
        springAnimation2.mValue = 0.7f;
        springAnimation2.mStartValueIsSet = true;
        springAnimation2.start();
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mBottomView, (Property<LinearLayout, Float>) View.ALPHA, 0.0f, 1.0f);
        KeyguardSecPatternViewController$$ExternalSyntheticOutline0.m(0.17f, 0.17f, 0.4f, 1.0f, ofFloat);
        ofFloat.setDuration(300L);
        ofFloat.start();
    }

    @Override // com.android.keyguard.KeyguardPatternViewController, com.android.keyguard.KeyguardInputViewController
    public final boolean startDisappearAnimation(Runnable runnable) {
        this.mLockPatternView.clearPattern();
        this.mBottomView.clearAnimation();
        this.mBottomView.animate().scaleX(1.3f).scaleY(1.3f).setDuration(200L).setInterpolator(new SineInOut90()).setStartDelay(0L).alpha(0.0f).withEndAction(runnable);
        return true;
    }

    public final void updateLayout$1$2() {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController;
        int rotation = DeviceState.shouldEnableKeyguardScreenRotation(getContext()) ? DeviceState.getRotation(getResources().getConfiguration().windowConfiguration.getRotation()) : 0;
        this.mCurrentRotation = rotation;
        boolean isTablet = DeviceType.isTablet();
        KeyguardSecMessageAreaController keyguardSecMessageAreaController2 = this.mMessageAreaController;
        if (isTablet) {
            Resources resources = getResources();
            LinearLayout linearLayout = this.mBottomView;
            if (linearLayout != null) {
                ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
                layoutParams.width = resources.getDimensionPixelSize(R.dimen.kg_message_area_width_tablet);
                this.mBottomView.setLayoutParams(layoutParams);
            }
            if (keyguardSecMessageAreaController2 != null) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) keyguardSecMessageAreaController2.getLayoutParams();
                marginLayoutParams.bottomMargin = resources.getDimensionPixelSize(R.dimen.kg_pattern_message_area_margin_bottom_tablet);
                keyguardSecMessageAreaController2.setLayoutParams(marginLayoutParams);
            }
            LinearLayout linearLayout2 = this.mMessageArea;
            if (linearLayout2 != null) {
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
                layoutParams2.width = -1;
                layoutParams2.height = -2;
                this.mMessageArea.setLayoutParams(layoutParams2);
            }
            LinearLayout linearLayout3 = this.mContainer;
            if (linearLayout3 != null) {
                linearLayout3.setVisibility(0);
            }
            LockPatternView lockPatternView = this.mLockPatternView;
            if (lockPatternView != null) {
                FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) lockPatternView.getLayoutParams();
                layoutParams3.width = resources.getDimensionPixelSize(R.dimen.kg_pattern_lock_pattern_view_width_tablet);
                layoutParams3.height = resources.getDimensionPixelSize(R.dimen.kg_pattern_lock_pattern_view_height_tablet);
                layoutParams3.bottomMargin = resources.getDimensionPixelSize(R.dimen.kg_pattern_lock_pattern_view_margin_bottom_tablet);
                this.mLockPatternView.setLayoutParams(layoutParams3);
            }
            View view = this.mEcaView;
            if (view != null) {
                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                marginLayoutParams2.setMargins(marginLayoutParams2.leftMargin, marginLayoutParams2.topMargin, marginLayoutParams2.rightMargin, resources.getDimensionPixelSize(R.dimen.kg_pattern_eca_margin_bottom_tablet));
                this.mEcaView.setLayoutParams(marginLayoutParams2);
                EmergencyButton emergencyButton = (EmergencyButton) this.mEcaView.findViewById(R.id.emergency_call_button);
                if (emergencyButton != null) {
                    ViewGroup.LayoutParams layoutParams4 = emergencyButton.getLayoutParams();
                    layoutParams4.height = resources.getDimensionPixelSize(R.dimen.keyguard_bottom_area_emergency_button_area_min_height_tablet);
                    emergencyButton.setLayoutParams(layoutParams4);
                }
            }
        } else {
            KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardPatternViewController) this).mKeyguardUpdateMonitor;
            if (keyguardUpdateMonitor.isDualDisplayPolicyAllowed()) {
                updatePortraitLayout();
            } else if (rotation == 0 || rotation == 2) {
                updatePortraitLayout();
            } else {
                Resources resources2 = getResources();
                Rect bounds = resources2.getConfiguration().windowConfiguration.getBounds();
                int max = Math.max(bounds.width(), bounds.height());
                int min = Math.min(bounds.width(), bounds.height());
                int calculateLandscapeViewWidth = SecurityUtils.calculateLandscapeViewWidth(max, getContext());
                int dimensionPixelSize = resources2.getDimensionPixelSize(R.dimen.kg_message_area_padding_side);
                LinearLayout linearLayout4 = this.mSplitTouchView;
                if (linearLayout4 != null) {
                    LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) linearLayout4.getLayoutParams();
                    layoutParams5.width = 0;
                    layoutParams5.height = 0;
                    layoutParams5.weight = 0.0f;
                    this.mSplitTouchView.setLayoutParams(layoutParams5);
                }
                LockPatternView lockPatternView2 = this.mLockPatternView;
                if (lockPatternView2 != null && this.mSecurityView != null) {
                    FrameLayout.LayoutParams layoutParams6 = (FrameLayout.LayoutParams) lockPatternView2.getLayoutParams();
                    int dimensionPixelSize2 = ((min - resources2.getDimensionPixelSize(R.dimen.kg_pattern_lock_pattern_view_margin_bottom)) - resources2.getDimensionPixelSize(R.dimen.keyguard_bottom_area_emergency_button_area_min_height)) - (dimensionPixelSize * 2);
                    layoutParams6.height = dimensionPixelSize2;
                    layoutParams6.width = dimensionPixelSize2;
                    layoutParams6.bottomMargin = resources2.getDimensionPixelSize(R.dimen.kg_pattern_lock_pattern_view_margin_bottom);
                    this.mSecurityView.setGravity(17);
                    this.mSecurityView.setOrientation(0);
                    this.mLockPatternView.setLayoutParams(layoutParams6);
                }
                if (keyguardSecMessageAreaController2 != null) {
                    ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) keyguardSecMessageAreaController2.getLayoutParams();
                    marginLayoutParams3.bottomMargin = 0;
                    keyguardSecMessageAreaController2.setLayoutParams(marginLayoutParams3);
                }
                LinearLayout linearLayout5 = this.mMessageArea;
                if (linearLayout5 != null && this.mContainer != null) {
                    LinearLayout.LayoutParams layoutParams7 = (LinearLayout.LayoutParams) linearLayout5.getLayoutParams();
                    LinearLayout.LayoutParams layoutParams8 = (LinearLayout.LayoutParams) this.mContainer.getLayoutParams();
                    this.mMessageArea.setPadding(0, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
                    this.mContainer.setPadding(0, dimensionPixelSize, 0, dimensionPixelSize);
                    layoutParams7.width = calculateLandscapeViewWidth;
                    layoutParams7.height = -1;
                    layoutParams7.bottomMargin = 0;
                    layoutParams8.width = calculateLandscapeViewWidth;
                    layoutParams8.height = -1;
                    this.mContainer.setGravity(17);
                    this.mContainer.setVisibility(0);
                    this.mMessageArea.setLayoutParams(layoutParams7);
                    this.mContainer.setLayoutParams(layoutParams8);
                }
                LinearLayout linearLayout6 = this.mBottomView;
                if (linearLayout6 != null) {
                    linearLayout6.setOrientation(0);
                    FrameLayout.LayoutParams layoutParams9 = (FrameLayout.LayoutParams) this.mBottomView.getLayoutParams();
                    layoutParams9.width = -1;
                    this.mBottomView.setLayoutParams(layoutParams9);
                }
                View view2 = this.mEcaView;
                if (view2 != null) {
                    ViewGroup.MarginLayoutParams marginLayoutParams4 = (ViewGroup.MarginLayoutParams) view2.getLayoutParams();
                    marginLayoutParams4.setMargins(marginLayoutParams4.leftMargin, marginLayoutParams4.topMargin, marginLayoutParams4.rightMargin, 0);
                    this.mEcaView.setLayoutParams(marginLayoutParams4);
                    this.mEcaView.setVisibility(0);
                    this.mEmergencyButtonController.setEmergencyView(this.mEcaView.findViewById(R.id.emergency_call_button));
                }
                LinearLayout linearLayout7 = this.mEcaFlexContainer;
                if (linearLayout7 != null) {
                    linearLayout7.setVisibility(8);
                }
                if (keyguardUpdateMonitor.is2StepVerification() && (keyguardSecMessageAreaController = this.mSubMessageAreaController) != null) {
                    keyguardSecMessageAreaController.setVisibility(8);
                }
                this.mHintText.setVisibility(8);
                updatePrevInfoTextSize();
            }
            updateLayoutForAttemptRemainingBeforeWipe$1();
        }
        if (shouldTipsPopup()) {
            updateForgotPasswordTextVisibility();
            Handler handler = this.mHandler;
            KeyguardInputViewController$$ExternalSyntheticLambda1 keyguardInputViewController$$ExternalSyntheticLambda1 = this.mShowTipsRunnable;
            handler.removeCallbacks(keyguardInputViewController$$ExternalSyntheticLambda1);
            handler.postDelayed(keyguardInputViewController$$ExternalSyntheticLambda1, 500L);
        }
    }

    public final void updateLayoutForAttemptRemainingBeforeWipe$1() {
        int dimensionPixelSize;
        int dimensionPixelSize2;
        if (this.mSecondsRemaining <= 1 && ((KeyguardPatternViewController) this).mKeyguardUpdateMonitor.getLockoutAttemptDeadline() == 0) {
            this.mSecondsRemaining = -1;
            return;
        }
        Resources resources = getResources();
        if (this.mSecondsRemaining >= 0) {
            if (this.mMessageArea != null) {
                KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mSubMessageAreaController;
                if (keyguardSecMessageAreaController != null) {
                    keyguardSecMessageAreaController.setVisibility(8);
                }
                Rect bounds = resources.getConfiguration().windowConfiguration.getBounds();
                int i = this.mCurrentRotation;
                boolean z = i == 0 || i == 2;
                int max = z ? Math.max(bounds.width(), bounds.height()) : Math.min(bounds.width(), bounds.height());
                if (DeviceType.isTablet()) {
                    dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.keyguard_bottom_area_emergency_button_area_min_height_tablet);
                    dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.kg_pattern_eca_margin_bottom_tablet);
                } else {
                    dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.keyguard_bottom_area_emergency_button_area_min_height);
                    dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.kg_pattern_eca_margin_bottom);
                }
                int i2 = dimensionPixelSize2 + dimensionPixelSize;
                int dimensionPixelSize3 = getResources().getDimensionPixelSize(android.R.dimen.resolver_empty_state_container_padding_top);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mMessageArea.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = (max - i2) - dimensionPixelSize3;
                if (z) {
                    dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.kg_message_area_padding_side);
                }
                this.mMessageArea.setPadding(dimensionPixelSize3, 0, dimensionPixelSize3, 0);
                this.mMessageArea.setLayoutParams(layoutParams);
            }
            KeyguardSecMessageAreaController keyguardSecMessageAreaController2 = this.mMessageAreaController;
            if (keyguardSecMessageAreaController2 != null) {
                keyguardSecMessageAreaController2.setMovementMethod(new ScrollingMovementMethod());
            }
            LinearLayout linearLayout = this.mContainer;
            if (linearLayout != null) {
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                layoutParams2.width = -1;
                layoutParams2.height = -2;
                this.mContainer.setVisibility(0);
            }
            LockPatternView lockPatternView = this.mLockPatternView;
            if (lockPatternView != null) {
                lockPatternView.setVisibility(8);
            }
            LinearLayout linearLayout2 = this.mBottomView;
            if (linearLayout2 != null) {
                linearLayout2.setGravity(80);
                this.mBottomView.setOrientation(1);
            }
        }
    }

    public final void updatePortraitLayout() {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController;
        int dimensionPixelSize;
        Resources resources = getResources();
        LinearLayout linearLayout = this.mSplitTouchView;
        if (linearLayout != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -1;
            layoutParams.weight = 1.0f;
            this.mSplitTouchView.setLayoutParams(layoutParams);
        }
        LockPatternView lockPatternView = this.mLockPatternView;
        KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardPatternViewController) this).mKeyguardUpdateMonitor;
        if (lockPatternView != null && this.mSecurityView != null) {
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) lockPatternView.getLayoutParams();
            this.mSecurityView.setGravity(81);
            this.mSecurityView.setOrientation(1);
            layoutParams2.width = resources.getDimensionPixelSize(R.dimen.kg_pattern_lock_pattern_view_width);
            layoutParams2.height = resources.getDimensionPixelSize(R.dimen.kg_pattern_lock_pattern_view_height);
            if (keyguardUpdateMonitor.isInDisplayFingerprintMarginAccepted() && DeviceState.isInDisplayFpSensorPositionHigh() && this.mSecondsRemaining == -1) {
                int dimensionPixelSize2 = ((resources.getDimensionPixelSize(R.dimen.kg_pattern_lock_pattern_view_margin_bottom) + DeviceState.getInDisplayFingerprintHeight()) - resources.getDimensionPixelSize(R.dimen.kg_pattern_eca_margin_bottom)) - resources.getDimensionPixelSize(android.R.dimen.resolver_empty_state_container_padding_top);
                View view = this.mEcaView;
                dimensionPixelSize = dimensionPixelSize2 - ((view == null || view.findViewById(R.id.emergency_call_button).getVisibility() != 0) ? 0 : resources.getDimensionPixelSize(R.dimen.keyguard_bottom_area_emergency_button_area_min_height));
            } else {
                dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.kg_pattern_lock_pattern_view_margin_bottom);
            }
            layoutParams2.bottomMargin = dimensionPixelSize;
            this.mLockPatternView.setLayoutParams(layoutParams2);
        }
        KeyguardSecMessageAreaController keyguardSecMessageAreaController2 = this.mMessageAreaController;
        if (keyguardSecMessageAreaController2 != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) keyguardSecMessageAreaController2.getLayoutParams();
            marginLayoutParams.bottomMargin = resources.getDimensionPixelSize(R.dimen.kg_pattern_message_area_margin_bottom);
            keyguardSecMessageAreaController2.setLayoutParams(marginLayoutParams);
        }
        LinearLayout linearLayout2 = this.mMessageArea;
        if (linearLayout2 != null && this.mContainer != null) {
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
            LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) this.mContainer.getLayoutParams();
            int dimensionPixelSize3 = (LsRune.SECURITY_SUB_DISPLAY_LOCK && ((DisplayLifecycle) Dependency.sDependency.getDependencyInner(DisplayLifecycle.class)).mIsFolderOpened) ? 0 : resources.getDimensionPixelSize(R.dimen.kg_message_area_padding_side);
            this.mMessageArea.setPadding(dimensionPixelSize3, 0, dimensionPixelSize3, 0);
            this.mContainer.setPadding(0, 0, 0, 0);
            layoutParams3.width = -1;
            layoutParams3.height = -2;
            layoutParams3.bottomMargin = 0;
            layoutParams4.width = -1;
            layoutParams4.height = -2;
            this.mContainer.setVisibility(0);
            this.mMessageArea.setLayoutParams(layoutParams3);
            this.mContainer.setLayoutParams(layoutParams4);
        }
        LinearLayout linearLayout3 = this.mBottomView;
        if (linearLayout3 != null) {
            linearLayout3.setGravity(80);
            this.mBottomView.setOrientation(1);
        }
        View view2 = this.mEcaView;
        if (view2 != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) view2.getLayoutParams();
            marginLayoutParams2.setMargins(marginLayoutParams2.leftMargin, marginLayoutParams2.topMargin, marginLayoutParams2.rightMargin, resources.getDimensionPixelSize(R.dimen.kg_pattern_eca_margin_bottom));
            this.mEcaView.setLayoutParams(marginLayoutParams2);
            this.mEcaView.setVisibility(0);
            this.mEmergencyButtonController.setEmergencyView(this.mEcaView.findViewById(R.id.emergency_call_button));
        }
        LinearLayout linearLayout4 = this.mEcaFlexContainer;
        if (linearLayout4 != null) {
            linearLayout4.setVisibility(8);
        }
        if (keyguardUpdateMonitor.is2StepVerification() && (keyguardSecMessageAreaController = this.mSubMessageAreaController) != null) {
            keyguardSecMessageAreaController.setVisibility(0);
        }
        if (isHintText$1()) {
            this.mHintText.setVisibility(0);
        }
        updatePrevInfoTextSize();
    }

    @Override // com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        if (semWallpaperColors == null) {
            android.util.Log.d("KeyguardSecPatternViewController", "updateStyle: colors is null.");
            return;
        }
        android.util.Log.d("KeyguardSecPatternViewController", "updateStyle theme color: " + semWallpaperColors.getColorThemeColor(j));
        if (this.mLockPatternView instanceof SecLockPatternView) {
            ((SecLockPatternView) this.mLockPatternView).updateViewStyle(semWallpaperColors.getColorThemeColor(512L), j);
        }
    }
}
