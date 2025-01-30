package com.android.keyguard;

import android.animation.AnimatorSet;
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
import android.os.SystemClock;
import android.sec.enterprise.auditlog.AuditLog;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
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
import com.android.systemui.classifier.FalsingCollectorImpl;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.keyguard.Log;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.vibrate.VibrationUtil;
import com.android.systemui.wallpaper.WallpaperEventNotifier;
import com.android.systemui.widget.SystemUIWidgetUtil;
import com.samsung.android.graphics.spr.animation.interpolator.SineInOut90;
import com.samsung.android.knox.ContainerProxy;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardSecPatternViewController extends KeyguardPatternViewController {
    public final LinearLayout mBottomView;
    public boolean mBouncerShowing;
    public final ConfigurationController mConfigurationController;
    public final C07221 mConfigurationListener;
    public final LinearLayout mContainer;
    public SecCountDownTimer mCountdownTimer;
    public int mCurrentRotation;
    public final C07265 mDisplayListener;
    public final LinearLayout mEcaFlexContainer;
    public final View mEcaView;
    public final EmergencyButtonController mEmergencyButtonController;
    public boolean mGoingToSleep;
    public final Handler mHandler;
    public final KeyguardHintTextArea mHintText;
    public final PathInterpolator mInterpolator;
    public boolean mIsNightModeOn;
    public final KeyguardTextBuilder mKeyguardTextBuilder;
    public final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    public final C07254 mKnoxStateCallback;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public final LinearLayout mMessageArea;
    public int mPromptReason;
    public final KeyguardSecPatternViewController$$ExternalSyntheticLambda1 mRotationConsumer;
    public final SecRotationWatcher mRotationWatcher;
    public int mSecondsRemaining;
    public final LinearLayout mSecurityView;
    public final LinearLayout mSplitTouchView;
    public final KeyguardSecPatternViewController$$ExternalSyntheticLambda0 mUpdateLayoutRunnable;
    public final VibrationUtil mVibrationUtil;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.keyguard.KeyguardSecPatternViewController$6 */
    public abstract /* synthetic */ class AbstractC07276 {
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
    public final class SecUnlockPatternListener extends KeyguardPatternViewController.UnlockPatternListener {
        public SecUnlockPatternListener() {
            super();
        }

        @Override // com.android.keyguard.KeyguardPatternViewController.UnlockPatternListener
        public final void onPatternChecked(int i, int i2, boolean z, boolean z2) {
            boolean z3 = KeyguardUpdateMonitor.getCurrentUser() == i;
            Log.m139d("KeyguardSecPatternViewController", "!@onPatternChecked matched=%b timeoutMs=%d userId=%d", Boolean.valueOf(z), Integer.valueOf(i2), Integer.valueOf(i));
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
                    keyguardSecurityCallback.dismiss(i, KeyguardSecPatternViewController.this.mSecurityMode, true);
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
                    KeyguardSecPatternViewController.this.disableDevicePermanently();
                } else {
                    ((KnoxStateMonitorImpl) KeyguardSecPatternViewController.this.mKnoxStateMonitor).isDisableDeviceByMultifactor();
                    if (i2 == 0) {
                        if (KeyguardSecPatternViewController.this.isHintText() && KeyguardSecPatternViewController.this.mHintText.getVisibility() == 8) {
                            KeyguardSecPatternViewController.this.mHintText.setVisibility(0);
                        }
                    } else if (i2 > 0 && !((KeyguardPatternViewController) KeyguardSecPatternViewController.this).mKeyguardUpdateMonitor.isPermanentLock()) {
                        if (KeyguardSecPatternViewController.this.isHintText()) {
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
                    string = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(AbstractC0000x2c234b15.m2m(string, " ("), KeyguardSecPatternViewController.this.getResources().getQuantityString(R.plurals.kg_attempt_left, remainingAttempt, Integer.valueOf(remainingAttempt)), ")");
                }
                KeyguardSecPatternViewController.this.mMessageAreaController.setMessage(string, false);
                KeyguardSecPatternViewController.this.mMessageAreaController.announceForAccessibility(string);
                KeyguardSecPatternViewController.this.mMessageAreaController.displayFailedAnimation();
                KeyguardSecPatternViewController keyguardSecPatternViewController2 = KeyguardSecPatternViewController.this;
                keyguardSecPatternViewController2.mLockPatternView.postDelayed(keyguardSecPatternViewController2.mCancelPatternRunnable, 2000L);
            }
        }

        @Override // com.android.keyguard.KeyguardPatternViewController.UnlockPatternListener
        public final void onPatternDetected(List list) {
            Log.m138d("KeyguardSecPatternViewController", "onPatternDetected");
            KnoxStateMonitor knoxStateMonitor = KeyguardSecPatternViewController.this.mKnoxStateMonitor;
            if (knoxStateMonitor != null) {
                if (((KnoxStateMonitorImpl) knoxStateMonitor).isDualDarDeviceOwner(KeyguardUpdateMonitor.getCurrentUser())) {
                    if (!((KnoxStateMonitorImpl) KeyguardSecPatternViewController.this.mKnoxStateMonitor).isDualDarInnerLayerUnlocked(KeyguardUpdateMonitor.getCurrentUser())) {
                        android.util.Log.d("KeyguardSecPatternViewController.DDAR", "Pattern detected from DualDAR DO");
                        ((KeyguardPatternViewController) KeyguardSecPatternViewController.this).mKeyguardUpdateMonitor.setCredentialAttempted();
                        KeyguardSecPatternViewController.this.mLockPatternView.disableInput();
                        AsyncTask asyncTask = KeyguardSecPatternViewController.this.mPendingLockCheck;
                        if (asyncTask != null) {
                            asyncTask.cancel(false);
                        }
                        final int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                        if (list.size() < 4) {
                            android.util.Log.e("KeyguardSecPatternViewController", "!@Password too short!");
                            if (list.size() == 1) {
                                ((FalsingCollectorImpl) KeyguardSecPatternViewController.this.mFalsingCollector).updateFalseConfidence(FalsingClassifier.Result.falsed(0.7d, SecUnlockPatternListener.class.getSimpleName(), "empty pattern input"));
                            }
                            KeyguardSecPatternViewController.this.mLockPatternView.enableInput();
                            onPatternChecked(currentUser, 0, false, false);
                            return;
                        }
                        KeyguardSecPatternViewController.this.mLatencyTracker.onActionStart(3);
                        KeyguardSecPatternViewController.this.mLatencyTracker.onActionStart(4);
                        KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                        keyguardSecPatternViewController.mPendingLockCheck = LockPatternChecker.checkCredential(keyguardSecPatternViewController.mLockPatternUtils, LockscreenCredential.createPattern(list), currentUser, 1, new LockPatternChecker.OnCheckCallbackForDualDarDo() { // from class: com.android.keyguard.KeyguardSecPatternViewController.SecUnlockPatternListener.1
                            public final void onCancelled() {
                                KeyguardSecPatternViewController.this.mLatencyTracker.onActionEnd(4);
                            }

                            public final void onChecked(boolean z, int i) {
                                KeyguardSecPatternViewController.this.mLatencyTracker.onActionEnd(3);
                                StringBuilder sb = new StringBuilder("onPatternDetected - onChecked - matched : ");
                                sb.append(z);
                                sb.append(", timeoutMs : ");
                                RecyclerView$$ExternalSyntheticOutline0.m46m(sb, i, "KeyguardSecPatternViewController.DDAR");
                                if (z) {
                                    SecUnlockPatternListener.this.onPatternChecked(currentUser, 0, true, true);
                                    return;
                                }
                                KeyguardSecPatternViewController.this.mLockPatternView.enableInput();
                                SecUnlockPatternListener secUnlockPatternListener = SecUnlockPatternListener.this;
                                KeyguardSecPatternViewController.this.mPendingLockCheck = null;
                                secUnlockPatternListener.onPatternChecked(currentUser, i, false, true);
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
            Log.m138d("KeyguardSecPatternViewController", "onPatternStart");
            KeyguardSecPatternViewController.this.setSubSecurityMessage(0);
        }
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.keyguard.KeyguardSecPatternViewController$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.keyguard.KeyguardSecPatternViewController$1] */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.android.keyguard.KeyguardSecPatternViewController$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.keyguard.KeyguardSecPatternViewController$4] */
    /* JADX WARN: Type inference failed for: r2v8, types: [com.android.keyguard.KeyguardSecPatternViewController$5] */
    public KeyguardSecPatternViewController(KeyguardSecPatternView keyguardSecPatternView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, LatencyTracker latencyTracker, FalsingCollector falsingCollector, EmergencyButtonController emergencyButtonController, KeyguardMessageAreaController.Factory factory, DevicePostureController devicePostureController, FeatureFlags featureFlags, ConfigurationController configurationController, SecRotationWatcher secRotationWatcher, VibrationUtil vibrationUtil) {
        super(keyguardSecPatternView, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, latencyTracker, falsingCollector, emergencyButtonController, factory, devicePostureController, featureFlags);
        this.mInterpolator = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
        this.mHandler = new Handler();
        this.mIsNightModeOn = false;
        this.mSecondsRemaining = -1;
        this.mUpdateLayoutRunnable = new Runnable() { // from class: com.android.keyguard.KeyguardSecPatternViewController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                keyguardSecPatternViewController.mSecondsRemaining = -1;
                keyguardSecPatternViewController.updateLayout();
            }
        };
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardSecPatternViewController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onConfigChanged(Configuration configuration) {
                boolean z = (configuration.uiMode & 32) != 0;
                KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                if (keyguardSecPatternViewController.mIsNightModeOn != z) {
                    KeyguardCarrierViewController$$ExternalSyntheticOutline0.m63m(new StringBuilder("night mode changed : "), keyguardSecPatternViewController.mIsNightModeOn, " -> ", z, "KeyguardSecPatternViewController");
                    keyguardSecPatternViewController.mIsNightModeOn = z;
                    if (LsRune.SECURITY_OPEN_THEME) {
                        keyguardSecPatternViewController.updateStyle(WallpaperEventNotifier.getInstance().mCurStatusFlag, WallpaperEventNotifier.getInstance().getSemWallpaperColors(false));
                    } else {
                        android.util.Log.d("KeyguardSecPatternViewController", "Can't apply night mode! NOT supported OPEN THEME feature");
                    }
                }
            }
        };
        this.mRotationConsumer = new IntConsumer() { // from class: com.android.keyguard.KeyguardSecPatternViewController$$ExternalSyntheticLambda1
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                KeyguardSecPatternViewController.this.updateLayout(i);
            }
        };
        this.mKeyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardSecPatternViewController.3
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
                if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                    KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                    if (((KeyguardPatternViewController) keyguardSecPatternViewController).mKeyguardUpdateMonitor.is2StepVerification()) {
                        keyguardSecPatternViewController.reset();
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricRunningStateChanged(BiometricSourceType biometricSourceType, boolean z) {
                int i = AbstractC07276.$SwitchMap$android$hardware$biometrics$BiometricSourceType[biometricSourceType.ordinal()];
                if (i == 1 || i == 2) {
                    KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                    if (keyguardSecPatternViewController.mBouncerShowing && ((KeyguardPatternViewController) keyguardSecPatternViewController).mKeyguardUpdateMonitor.isUpdateSecurityMessage()) {
                        keyguardSecPatternViewController.displayDefaultSecurityMessage();
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onFinishedGoingToSleep(int i) {
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
                    if (((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isDualView()) {
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
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSimulationFailToUnlock(int i) {
                KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                keyguardSecPatternViewController.mVibrationUtil.playVibration(114);
                LockPatternView.DisplayMode displayMode = LockPatternView.DisplayMode.Wrong;
                LockPatternView lockPatternView = keyguardSecPatternViewController.mLockPatternView;
                lockPatternView.setDisplayMode(displayMode);
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = ((KeyguardPatternViewController) keyguardSecPatternViewController).mKeyguardUpdateMonitor;
                int failedUnlockAttempts = keyguardUpdateMonitor2.getFailedUnlockAttempts(i) + 1;
                int i2 = failedUnlockAttempts % 5 == 0 ? 30000 : 0;
                android.util.Log.d("KeyguardSecPatternViewController", "onSimulationFailToUnlock failedAttempts : " + failedUnlockAttempts + " timeoutMs : " + i2);
                keyguardSecPatternViewController.getKeyguardSecurityCallback().reportUnlockAttempt(i, i2, false);
                KeyguardHintTextArea keyguardHintTextArea = keyguardSecPatternViewController.mHintText;
                if (i2 == 0) {
                    if (keyguardSecPatternViewController.isHintText() && keyguardHintTextArea.getVisibility() == 8) {
                        keyguardHintTextArea.setVisibility(0);
                    }
                } else if (i2 > 0) {
                    if (keyguardSecPatternViewController.isHintText()) {
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
                        string = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(AbstractC0000x2c234b15.m2m(string, " ("), keyguardSecPatternViewController.getResources().getQuantityString(R.plurals.kg_attempt_left, remainingAttempt, Integer.valueOf(remainingAttempt)), ")");
                    }
                    KeyguardSecMessageAreaController keyguardSecMessageAreaController = keyguardSecPatternViewController.mMessageAreaController;
                    keyguardSecMessageAreaController.setMessage(string, false);
                    keyguardSecMessageAreaController.announceForAccessibility(string);
                    keyguardSecMessageAreaController.displayFailedAnimation();
                    lockPatternView.postDelayed(keyguardSecPatternViewController.mCancelPatternRunnable, 2000L);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStartedGoingToSleep(int i) {
                KeyguardSecPatternViewController.this.mGoingToSleep = true;
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStartedWakingUp() {
                KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                if (keyguardSecPatternViewController.mSecondsRemaining > 0 && ((KeyguardPatternViewController) keyguardSecPatternViewController).mKeyguardUpdateMonitor.getLockoutAttemptDeadline() == 0) {
                    keyguardSecPatternViewController.mSecondsRemaining = -1;
                }
                if (((KeyguardFastBioUnlockController) Dependency.get(KeyguardFastBioUnlockController.class)).isFastWakeAndUnlockMode()) {
                    return;
                }
                keyguardSecPatternViewController.updateLayout();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onTableModeChanged(boolean z) {
                KeyguardSecPatternViewController.this.updateLayout();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitching(int i) {
                KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                KeyguardHintTextArea keyguardHintTextArea = keyguardSecPatternViewController.mHintText;
                if (keyguardHintTextArea != null) {
                    keyguardHintTextArea.mPasswordHintText = keyguardSecPatternViewController.mLockPatternUtils.getPasswordHint(i);
                    keyguardSecPatternViewController.mHintText.updateHintButton();
                    if (keyguardSecPatternViewController.isHintText()) {
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
                keyguardSecPatternViewController.mLockPatternUtils.requireStrongAuth(2, KeyguardUpdateMonitor.getCurrentUser());
                keyguardSecPatternViewController.disableDevicePermanently();
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
                    if (knoxStateMonitorImpl.isPersona(intValue)) {
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
                KeyguardSecPatternViewController.this.reset();
            }
        };
        this.mEmergencyButtonController = emergencyButtonController;
        this.mConfigurationController = configurationController;
        this.mRotationWatcher = secRotationWatcher;
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
        if (isHintText()) {
            keyguardHintTextArea.setVisibility(0);
        }
        this.mKeyguardTextBuilder = KeyguardTextBuilder.getInstance(getContext());
        this.mKnoxStateMonitor = (KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class);
        this.mEcaFlexContainer = (LinearLayout) ((KeyguardSecPatternView) this.mView).findViewById(R.id.emergency_button_container_flex);
    }

    public final void disableDevicePermanently() {
        android.util.Log.d("KeyguardSecPatternViewController", "disableDevicePermanently");
        LockPatternView lockPatternView = this.mLockPatternView;
        lockPatternView.clearPattern();
        lockPatternView.setEnabled(false);
        lockPatternView.setVisibility(4);
        this.mMessageAreaController.setMessage(this.mKeyguardTextBuilder.getDefaultSecurityMessage(KeyguardSecurityModel.SecurityMode.Pattern), false);
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
        int strongAuthPrompt = SecurityUtils.getStrongAuthPrompt();
        if (strongAuthPrompt != 0) {
            this.mPromptReason = strongAuthPrompt;
            KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m71m(new StringBuilder("displayDefaultSecurityMessage - strongAuth ( "), this.mPromptReason, " )", "KeyguardSecPatternViewController");
            showPromptReason(this.mPromptReason);
        } else {
            String defaultSecurityMessage = this.mKeyguardTextBuilder.getDefaultSecurityMessage(KeyguardSecurityModel.SecurityMode.Pattern);
            AbstractC0689x6838b71d.m68m("displayDefaultSecurityMessage( ", defaultSecurityMessage, " )", "KeyguardSecPatternViewController");
            keyguardSecMessageAreaController.setMessage(defaultSecurityMessage, false);
            keyguardSecMessageAreaController.announceForAccessibility(defaultSecurityMessage);
        }
        if (keyguardUpdateMonitor.is2StepVerification()) {
            int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            if (keyguardUpdateMonitor.getLockoutBiometricAttemptDeadline() > 0) {
                keyguardSecMessageAreaController.setMessage("", false);
            }
            if (keyguardUpdateMonitor.getUserUnlockedWithBiometric(currentUser)) {
                setSubSecurityMessage(R.string.kg_biometrics_has_confirmed);
            } else {
                setSubSecurityMessage(0);
            }
        }
    }

    @Override // com.android.keyguard.KeyguardPatternViewController, com.android.keyguard.KeyguardInputViewController
    public final int getInitialMessageResId() {
        return 0;
    }

    @Override // com.android.keyguard.KeyguardPatternViewController
    public final void handleAttemptLockout(long j) {
        android.util.Log.d("KeyguardSecPatternViewController", "handleAttemptLockout deadline = " + j);
        LockPatternView lockPatternView = this.mLockPatternView;
        lockPatternView.clearPattern();
        lockPatternView.setEnabled(false);
        lockPatternView.setVisibility(4);
        KeyguardHintTextArea keyguardHintTextArea = this.mHintText;
        if (keyguardHintTextArea != null) {
            keyguardHintTextArea.setVisibility(8);
        }
        updateForgotPasswordTextVisibility();
        SecCountDownTimer secCountDownTimer = this.mCountdownTimer;
        if (secCountDownTimer != null) {
            secCountDownTimer.cancel();
            this.mCountdownTimer = null;
        }
        SecCountDownTimer secCountDownTimer2 = new SecCountDownTimer(j - SystemClock.elapsedRealtime(), 1000L, getContext(), ((KeyguardPatternViewController) this).mKeyguardUpdateMonitor, this.mKeyguardTextBuilder, true) { // from class: com.android.keyguard.KeyguardSecPatternViewController.2
            @Override // com.android.keyguard.SecCountDownTimer, android.os.CountDownTimer
            public final void onFinish() {
                android.util.Log.d("KeyguardSecPatternViewController", "handleAttemptLockout onFinish");
                int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                if (KeyguardSecPatternViewController.this.isHintText()) {
                    KeyguardSecPatternViewController.this.mHintText.setVisibility(0);
                }
                KeyguardSecPatternViewController keyguardSecPatternViewController = KeyguardSecPatternViewController.this;
                keyguardSecPatternViewController.mSecondsRemaining = -1;
                ((BouncerKeyguardMessageArea) keyguardSecPatternViewController.mMessageAreaController.mView).scrollTo(0, 0);
                KeyguardSecPatternViewController.this.mMessageAreaController.setMovementMethod(null);
                KeyguardSecPatternViewController.this.mLockPatternView.setEnabled(true);
                KeyguardSecPatternViewController.this.mLockPatternView.setVisibility(0);
                KeyguardSecPatternViewController.this.mLockPatternUtils.clearBiometricAttemptDeadline(currentUser);
                ((KeyguardPatternViewController) KeyguardSecPatternViewController.this).mKeyguardUpdateMonitor.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_FACE_LOCKOUT_RESET);
                KeyguardSecPatternViewController.this.displayDefaultSecurityMessage();
                KeyguardSecPatternViewController.this.resetFor2StepVerification();
                KeyguardSecPatternViewController keyguardSecPatternViewController2 = KeyguardSecPatternViewController.this;
                keyguardSecPatternViewController2.mHandler.removeCallbacks(keyguardSecPatternViewController2.mUpdateLayoutRunnable);
                KeyguardSecPatternViewController keyguardSecPatternViewController3 = KeyguardSecPatternViewController.this;
                keyguardSecPatternViewController3.mHandler.post(keyguardSecPatternViewController3.mUpdateLayoutRunnable);
                if (DeviceType.isWeaverDevice()) {
                    return;
                }
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
                    KeyguardSecPatternViewController.this.mMessageAreaController.setMessage(str, false);
                }
                KeyguardSecPatternViewController.this.updateLayoutForAttemptRemainingBeforeWipe();
            }
        };
        this.mCountdownTimer = secCountDownTimer2;
        secCountDownTimer2.start();
    }

    public final boolean isHintText() {
        KeyguardHintTextArea keyguardHintTextArea = this.mHintText;
        if (keyguardHintTextArea != null && keyguardHintTextArea.mPasswordHintText != null) {
            int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardPatternViewController) this).mKeyguardUpdateMonitor;
            if (keyguardUpdateMonitor.getFailedUnlockAttempts(currentUser) > 0 && keyguardUpdateMonitor.getLockoutAttemptDeadline() == 0) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.keyguard.KeyguardPatternViewController, com.android.keyguard.KeyguardInputViewController
    public final void onResume(int i) {
        super.onResume(i);
        if (DeviceState.isTesting()) {
            return;
        }
        reset();
    }

    @Override // com.android.keyguard.KeyguardPatternViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        this.mEmergencyButtonController.mEmergencyButtonCallback = this.mEmergencyButtonCallback;
        updateLayout();
        ((KeyguardPatternViewController) this).mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateMonitorCallback);
        ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).registerCallback(this.mKnoxStateCallback);
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).addObserver(this.mDisplayListener);
        }
        if (DeviceState.shouldEnableKeyguardScreenRotation(getContext()) || DeviceType.isTablet()) {
            this.mRotationWatcher.addCallback(this.mRotationConsumer);
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
            ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).removeObserver(this.mDisplayListener);
        }
        if (DeviceState.shouldEnableKeyguardScreenRotation(getContext()) || DeviceType.isTablet()) {
            this.mRotationWatcher.removeCallback(this.mRotationConsumer);
        }
    }

    @Override // com.android.keyguard.KeyguardPatternViewController, com.android.keyguard.KeyguardInputViewController
    public final void reset() {
        boolean z = !this.mLockPatternUtils.isVisiblePatternEnabled(KeyguardUpdateMonitor.getCurrentUser());
        LockPatternView lockPatternView = this.mLockPatternView;
        lockPatternView.setInStealthMode(z);
        lockPatternView.enableInput();
        lockPatternView.setEnabled(true);
        lockPatternView.clearPattern();
        updateLayout();
        KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardPatternViewController) this).mKeyguardUpdateMonitor;
        long lockoutAttemptDeadline = keyguardUpdateMonitor.getLockoutAttemptDeadline();
        boolean isDualDarInnerAuthRequired = keyguardUpdateMonitor.isDualDarInnerAuthRequired(KeyguardUpdateMonitor.getCurrentUser());
        KnoxStateMonitor knoxStateMonitor = this.mKnoxStateMonitor;
        if (isDualDarInnerAuthRequired) {
            long dualDarInnerLockoutAttemptDeadline = ((KnoxStateMonitorImpl) knoxStateMonitor).getDualDarInnerLockoutAttemptDeadline();
            if (dualDarInnerLockoutAttemptDeadline != 0 && dualDarInnerLockoutAttemptDeadline > lockoutAttemptDeadline) {
                StringBuilder m17m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("reset() switch to inner deadline. deadline = ", lockoutAttemptDeadline, ", innerDeadline = ");
                m17m.append(dualDarInnerLockoutAttemptDeadline);
                android.util.Log.d("KeyguardSecPatternViewController", m17m.toString());
                lockoutAttemptDeadline = dualDarInnerLockoutAttemptDeadline;
            }
        }
        KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) knoxStateMonitor;
        if (knoxStateMonitorImpl.isDeviceDisabledForMaxFailedAttempt()) {
            disableDevicePermanently();
            return;
        }
        knoxStateMonitorImpl.isDisableDeviceByMultifactor();
        boolean shouldLockout = shouldLockout(lockoutAttemptDeadline);
        KeyguardHintTextArea keyguardHintTextArea = this.mHintText;
        if (shouldLockout) {
            if (isHintText()) {
                keyguardHintTextArea.setVisibility(8);
            }
            handleAttemptLockout(lockoutAttemptDeadline);
            return;
        }
        displayDefaultSecurityMessage();
        if (isHintText()) {
            keyguardHintTextArea.updateHintButton();
            keyguardHintTextArea.setVisibility(0);
        }
        updateForgotPasswordTextVisibility();
        if (lockoutAttemptDeadline > 0 && keyguardUpdateMonitor.getLockoutBiometricAttemptDeadline() == 0) {
            handleAttemptLockout(0L);
        }
        lockPatternView.setVisibility(0);
        resetFor2StepVerification();
    }

    public final void resetFor2StepVerification() {
        KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardPatternViewController) this).mKeyguardUpdateMonitor;
        if (!keyguardUpdateMonitor.is2StepVerification() || keyguardUpdateMonitor.getUserUnlockedWithBiometric(KeyguardUpdateMonitor.getCurrentUser())) {
            return;
        }
        android.util.Log.d("KeyguardSecPatternViewController", "reset() - 2 step verification");
        LockPatternView lockPatternView = this.mLockPatternView;
        lockPatternView.clearPattern();
        lockPatternView.setEnabled(false);
        lockPatternView.setVisibility(4);
    }

    public final void setSubSecurityMessage(int i) {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController;
        if (!((KeyguardPatternViewController) this).mKeyguardUpdateMonitor.is2StepVerification() || (keyguardSecMessageAreaController = this.mSubMessageAreaController) == null) {
            return;
        }
        KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) this.mKnoxStateMonitor;
        if (knoxStateMonitorImpl.isDeviceDisabledForMaxFailedAttempt()) {
            keyguardSecMessageAreaController.setMessage("", false);
            keyguardSecMessageAreaController.setVisibility(8);
            return;
        }
        knoxStateMonitorImpl.isDisableDeviceByMultifactor();
        if (i == 0) {
            keyguardSecMessageAreaController.setMessage("", false);
            keyguardSecMessageAreaController.setVisibility(8);
        } else {
            keyguardSecMessageAreaController.setMessage(getContext().getString(i), false);
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
            if (keyguardUpdateMonitor.isDualDarInnerAuthRequired(KeyguardUpdateMonitor.getCurrentUser())) {
                long dualDarInnerLockoutAttemptDeadline = ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).getDualDarInnerLockoutAttemptDeadline();
                if (dualDarInnerLockoutAttemptDeadline != 0 && dualDarInnerLockoutAttemptDeadline > lockoutAttemptDeadline) {
                    StringBuilder m17m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("showPromptReason() switch to inner deadline. deadline = ", lockoutAttemptDeadline, ", innerDeadline = ");
                    m17m.append(dualDarInnerLockoutAttemptDeadline);
                    android.util.Log.d("KeyguardSecPatternViewController", m17m.toString());
                    lockoutAttemptDeadline = dualDarInnerLockoutAttemptDeadline;
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
                keyguardSecMessageAreaController.setMessage(strongAuthPopupString, false);
                ((BouncerKeyguardMessageArea) keyguardSecMessageAreaController.mView).scrollTo(0, 0);
            } else {
                if (promptSecurityMessage.isEmpty() || SecurityUtils.isEmptyStrongAuthPopupMessage(getContext(), securityMode)) {
                    promptSecurityMessage = keyguardTextBuilder.getDefaultSecurityMessage(securityMode);
                }
                keyguardSecMessageAreaController.setMessage(promptSecurityMessage, false);
            }
        }
    }

    @Override // com.android.keyguard.KeyguardPatternViewController, com.android.keyguard.KeyguardInputViewController
    public final void startAppearAnimation() {
        LinearLayout linearLayout = this.mBottomView;
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(linearLayout, (Property<LinearLayout, Float>) View.SCALE_X, 0.7f, 1.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(linearLayout, (Property<LinearLayout, Float>) View.SCALE_Y, 0.7f, 1.0f);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(linearLayout, (Property<LinearLayout, Float>) View.ALPHA, 0.0f, 1.0f);
        ofFloat3.setInterpolator(new LinearInterpolator());
        linearLayout.clearAnimation();
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2, ofFloat3);
        animatorSet.setInterpolator(this.mInterpolator);
        animatorSet.setDuration(350L);
        animatorSet.setStartDelay(0L);
        animatorSet.start();
    }

    @Override // com.android.keyguard.KeyguardPatternViewController, com.android.keyguard.KeyguardInputViewController
    public final boolean startDisappearAnimation(Runnable runnable) {
        this.mLockPatternView.clearPattern();
        LinearLayout linearLayout = this.mBottomView;
        linearLayout.clearAnimation();
        linearLayout.animate().scaleX(1.3f).scaleY(1.3f).setDuration(200L).setInterpolator(new SineInOut90()).setStartDelay(0L).alpha(0.0f).withEndAction(runnable);
        return true;
    }

    public final void updateLayout() {
        updateLayout(DeviceState.shouldEnableKeyguardScreenRotation(getContext()) ? DeviceState.getRotation(getResources().getConfiguration().windowConfiguration.getRotation()) : 0);
    }

    public final void updateLayoutForAttemptRemainingBeforeWipe() {
        int dimensionPixelSize;
        int dimensionPixelSize2;
        if (this.mSecondsRemaining <= 1 && ((KeyguardPatternViewController) this).mKeyguardUpdateMonitor.getLockoutAttemptDeadline() == 0) {
            this.mSecondsRemaining = -1;
            return;
        }
        Resources resources = getResources();
        if (this.mSecondsRemaining >= 0) {
            LinearLayout linearLayout = this.mMessageArea;
            if (linearLayout != null) {
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
                int dimensionPixelSize3 = getResources().getDimensionPixelSize(android.R.dimen.notification_content_margin_end);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = (max - i2) - dimensionPixelSize3;
                if (z) {
                    dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.kg_message_area_padding_side);
                }
                linearLayout.setPadding(dimensionPixelSize3, 0, dimensionPixelSize3, 0);
                linearLayout.setLayoutParams(layoutParams);
            }
            KeyguardSecMessageAreaController keyguardSecMessageAreaController2 = this.mMessageAreaController;
            if (keyguardSecMessageAreaController2 != null) {
                keyguardSecMessageAreaController2.setMovementMethod(new ScrollingMovementMethod());
            }
            LinearLayout linearLayout2 = this.mContainer;
            if (linearLayout2 != null) {
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
                layoutParams2.width = -1;
                layoutParams2.height = -2;
                linearLayout2.setVisibility(0);
            }
            LockPatternView lockPatternView = this.mLockPatternView;
            if (lockPatternView != null) {
                lockPatternView.setVisibility(8);
            }
            LinearLayout linearLayout3 = this.mBottomView;
            if (linearLayout3 != null) {
                linearLayout3.setGravity(80);
                linearLayout3.setOrientation(1);
            }
        }
    }

    public final void updatePortraitLayout() {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController;
        LinearLayout linearLayout;
        LinearLayout linearLayout2;
        int dimensionPixelSize;
        Resources resources = getResources();
        LinearLayout linearLayout3 = this.mSplitTouchView;
        if (linearLayout3 != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout3.getLayoutParams();
            layoutParams.width = -1;
            layoutParams.height = -1;
            layoutParams.weight = 1.0f;
            linearLayout3.setLayoutParams(layoutParams);
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardPatternViewController) this).mKeyguardUpdateMonitor;
        LockPatternView lockPatternView = this.mLockPatternView;
        View view = this.mEcaView;
        if (lockPatternView != null && (linearLayout2 = this.mSecurityView) != null) {
            FrameLayout.LayoutParams layoutParams2 = (FrameLayout.LayoutParams) lockPatternView.getLayoutParams();
            linearLayout2.setGravity(81);
            linearLayout2.setOrientation(1);
            layoutParams2.width = resources.getDimensionPixelSize(R.dimen.kg_pattern_lock_pattern_view_width);
            layoutParams2.height = resources.getDimensionPixelSize(R.dimen.kg_pattern_lock_pattern_view_height);
            if (keyguardUpdateMonitor.isInDisplayFingerprintMarginAccepted() && DeviceState.isInDisplayFpSensorPositionHigh() && this.mSecondsRemaining == -1) {
                dimensionPixelSize = (((resources.getDimensionPixelSize(R.dimen.kg_pattern_lock_pattern_view_margin_bottom) + DeviceState.sInDisplayFingerprintHeight) - resources.getDimensionPixelSize(R.dimen.kg_pattern_eca_margin_bottom)) - resources.getDimensionPixelSize(android.R.dimen.notification_content_margin_end)) - ((view == null || view.findViewById(R.id.emergency_call_button).getVisibility() != 0) ? 0 : resources.getDimensionPixelSize(R.dimen.keyguard_bottom_area_emergency_button_area_min_height));
            } else {
                dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.kg_pattern_lock_pattern_view_margin_bottom);
            }
            layoutParams2.bottomMargin = dimensionPixelSize;
            lockPatternView.setLayoutParams(layoutParams2);
        }
        KeyguardSecMessageAreaController keyguardSecMessageAreaController2 = this.mMessageAreaController;
        if (keyguardSecMessageAreaController2 != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) keyguardSecMessageAreaController2.getLayoutParams();
            marginLayoutParams.bottomMargin = resources.getDimensionPixelSize(R.dimen.kg_pattern_message_area_margin_bottom);
            keyguardSecMessageAreaController2.setLayoutParams(marginLayoutParams);
        }
        LinearLayout linearLayout4 = this.mMessageArea;
        if (linearLayout4 != null && (linearLayout = this.mContainer) != null) {
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) linearLayout4.getLayoutParams();
            LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            int dimensionPixelSize2 = (LsRune.SECURITY_SUB_DISPLAY_LOCK && ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) ? 0 : resources.getDimensionPixelSize(R.dimen.kg_message_area_padding_side);
            linearLayout4.setPadding(dimensionPixelSize2, 0, dimensionPixelSize2, 0);
            linearLayout.setPadding(0, 0, 0, 0);
            layoutParams3.width = -1;
            layoutParams3.height = -2;
            layoutParams3.bottomMargin = 0;
            layoutParams4.width = -1;
            layoutParams4.height = -2;
            linearLayout.setVisibility(0);
            linearLayout4.setLayoutParams(layoutParams3);
            linearLayout.setLayoutParams(layoutParams4);
        }
        LinearLayout linearLayout5 = this.mBottomView;
        if (linearLayout5 != null) {
            linearLayout5.setGravity(80);
            linearLayout5.setOrientation(1);
        }
        if (view != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            marginLayoutParams2.setMargins(marginLayoutParams2.leftMargin, marginLayoutParams2.topMargin, marginLayoutParams2.rightMargin, resources.getDimensionPixelSize(R.dimen.kg_pattern_eca_margin_bottom));
            view.setLayoutParams(marginLayoutParams2);
            view.setVisibility(0);
            this.mEmergencyButtonController.setEmergencyView(view.findViewById(R.id.emergency_call_button));
        }
        LinearLayout linearLayout6 = this.mEcaFlexContainer;
        if (linearLayout6 != null) {
            linearLayout6.setVisibility(8);
        }
        if (keyguardUpdateMonitor.is2StepVerification() && (keyguardSecMessageAreaController = this.mSubMessageAreaController) != null) {
            keyguardSecMessageAreaController.setVisibility(0);
        }
        if (isHintText()) {
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
        LockPatternView lockPatternView = this.mLockPatternView;
        if (lockPatternView instanceof SecLockPatternView) {
            ((SecLockPatternView) lockPatternView).updateViewStyle(semWallpaperColors.getColorThemeColor(SystemUIWidgetUtil.convertFlag("background")), j);
        }
    }

    public final void updateLayout(int i) {
        int i2;
        KeyguardSecMessageAreaController keyguardSecMessageAreaController;
        LinearLayout linearLayout;
        this.mCurrentRotation = i;
        boolean isTablet = DeviceType.isTablet();
        View view = this.mEcaView;
        LockPatternView lockPatternView = this.mLockPatternView;
        LinearLayout linearLayout2 = this.mContainer;
        LinearLayout linearLayout3 = this.mMessageArea;
        KeyguardSecMessageAreaController keyguardSecMessageAreaController2 = this.mMessageAreaController;
        LinearLayout linearLayout4 = this.mBottomView;
        if (isTablet) {
            Resources resources = getResources();
            if (linearLayout4 != null) {
                ViewGroup.LayoutParams layoutParams = linearLayout4.getLayoutParams();
                layoutParams.width = resources.getDimensionPixelSize(R.dimen.kg_message_area_width_tablet);
                linearLayout4.setLayoutParams(layoutParams);
            }
            if (keyguardSecMessageAreaController2 != null) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) keyguardSecMessageAreaController2.getLayoutParams();
                marginLayoutParams.bottomMargin = resources.getDimensionPixelSize(R.dimen.kg_pattern_message_area_margin_bottom_tablet);
                keyguardSecMessageAreaController2.setLayoutParams(marginLayoutParams);
            }
            if (linearLayout3 != null) {
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout3.getLayoutParams();
                layoutParams2.width = -1;
                layoutParams2.height = -2;
                linearLayout3.setLayoutParams(layoutParams2);
            }
            if (linearLayout2 != null) {
                linearLayout2.setVisibility(0);
            }
            if (lockPatternView != null) {
                FrameLayout.LayoutParams layoutParams3 = (FrameLayout.LayoutParams) lockPatternView.getLayoutParams();
                layoutParams3.width = resources.getDimensionPixelSize(R.dimen.kg_pattern_lock_pattern_view_width_tablet);
                layoutParams3.height = resources.getDimensionPixelSize(R.dimen.kg_pattern_lock_pattern_view_height_tablet);
                layoutParams3.bottomMargin = resources.getDimensionPixelSize(R.dimen.kg_pattern_lock_pattern_view_margin_bottom_tablet);
                lockPatternView.setLayoutParams(layoutParams3);
            }
            if (view != null) {
                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                marginLayoutParams2.setMargins(marginLayoutParams2.leftMargin, marginLayoutParams2.topMargin, marginLayoutParams2.rightMargin, resources.getDimensionPixelSize(R.dimen.kg_pattern_eca_margin_bottom_tablet));
                view.setLayoutParams(marginLayoutParams2);
                EmergencyButton emergencyButton = (EmergencyButton) view.findViewById(R.id.emergency_call_button);
                if (emergencyButton != null) {
                    ViewGroup.LayoutParams layoutParams4 = emergencyButton.getLayoutParams();
                    layoutParams4.height = resources.getDimensionPixelSize(R.dimen.keyguard_bottom_area_emergency_button_area_min_height_tablet);
                    emergencyButton.setLayoutParams(layoutParams4);
                    return;
                }
                return;
            }
            return;
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardPatternViewController) this).mKeyguardUpdateMonitor;
        if (keyguardUpdateMonitor.isDualDisplayPolicyAllowed()) {
            updatePortraitLayout();
        } else if (i != 0 && i != 2) {
            Resources resources2 = getResources();
            Rect bounds = resources2.getConfiguration().windowConfiguration.getBounds();
            int max = Math.max(bounds.width(), bounds.height());
            int min = Math.min(bounds.width(), bounds.height());
            int calculateLandscapeViewWidth = SecurityUtils.calculateLandscapeViewWidth(max, getContext());
            int dimensionPixelSize = resources2.getDimensionPixelSize(R.dimen.kg_message_area_padding_side);
            LinearLayout linearLayout5 = this.mSplitTouchView;
            if (linearLayout5 != null) {
                LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) linearLayout5.getLayoutParams();
                layoutParams5.width = 0;
                layoutParams5.height = 0;
                layoutParams5.weight = 0.0f;
                linearLayout5.setLayoutParams(layoutParams5);
            }
            if (lockPatternView == null || (linearLayout = this.mSecurityView) == null) {
                i2 = 0;
            } else {
                FrameLayout.LayoutParams layoutParams6 = (FrameLayout.LayoutParams) lockPatternView.getLayoutParams();
                int dimensionPixelSize2 = ((min - resources2.getDimensionPixelSize(R.dimen.kg_pattern_lock_pattern_view_margin_bottom)) - resources2.getDimensionPixelSize(R.dimen.keyguard_bottom_area_emergency_button_area_min_height)) - (dimensionPixelSize * 2);
                layoutParams6.height = dimensionPixelSize2;
                layoutParams6.width = dimensionPixelSize2;
                layoutParams6.bottomMargin = resources2.getDimensionPixelSize(R.dimen.kg_pattern_lock_pattern_view_margin_bottom);
                linearLayout.setGravity(17);
                i2 = 0;
                linearLayout.setOrientation(0);
                lockPatternView.setLayoutParams(layoutParams6);
            }
            if (keyguardSecMessageAreaController2 != null) {
                ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) keyguardSecMessageAreaController2.getLayoutParams();
                marginLayoutParams3.bottomMargin = i2;
                keyguardSecMessageAreaController2.setLayoutParams(marginLayoutParams3);
            }
            if (linearLayout3 != null && linearLayout2 != null) {
                LinearLayout.LayoutParams layoutParams7 = (LinearLayout.LayoutParams) linearLayout3.getLayoutParams();
                LinearLayout.LayoutParams layoutParams8 = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
                linearLayout3.setPadding(i2, dimensionPixelSize, dimensionPixelSize, dimensionPixelSize);
                linearLayout2.setPadding(i2, dimensionPixelSize, i2, dimensionPixelSize);
                layoutParams7.width = calculateLandscapeViewWidth;
                layoutParams7.height = -1;
                layoutParams7.bottomMargin = i2;
                layoutParams8.width = calculateLandscapeViewWidth;
                layoutParams8.height = -1;
                linearLayout2.setGravity(17);
                linearLayout2.setVisibility(i2);
                linearLayout3.setLayoutParams(layoutParams7);
                linearLayout2.setLayoutParams(layoutParams8);
            }
            if (linearLayout4 != null) {
                linearLayout4.setOrientation(i2);
                FrameLayout.LayoutParams layoutParams9 = (FrameLayout.LayoutParams) linearLayout4.getLayoutParams();
                layoutParams9.width = -1;
                linearLayout4.setLayoutParams(layoutParams9);
            }
            if (view != null) {
                ViewGroup.MarginLayoutParams marginLayoutParams4 = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                marginLayoutParams4.setMargins(marginLayoutParams4.leftMargin, marginLayoutParams4.topMargin, marginLayoutParams4.rightMargin, 0);
                view.setLayoutParams(marginLayoutParams4);
                view.setVisibility(0);
                this.mEmergencyButtonController.setEmergencyView(view.findViewById(R.id.emergency_call_button));
            }
            LinearLayout linearLayout6 = this.mEcaFlexContainer;
            if (linearLayout6 != null) {
                linearLayout6.setVisibility(8);
            }
            if (keyguardUpdateMonitor.is2StepVerification() && (keyguardSecMessageAreaController = this.mSubMessageAreaController) != null) {
                keyguardSecMessageAreaController.setVisibility(8);
            }
            this.mHintText.setVisibility(8);
            updatePrevInfoTextSize();
        } else {
            updatePortraitLayout();
        }
        updateLayoutForAttemptRemainingBeforeWipe();
    }
}
