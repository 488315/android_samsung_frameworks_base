package com.android.keyguard;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Rect;
import android.hardware.biometrics.BiometricSourceType;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.os.SystemClock;
import android.sec.enterprise.auditlog.AuditLog;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Toast;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternChecker;
import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.keyguard.KeyguardAbsKeyInputViewController;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.keyguard.KeyguardFastBioUnlockController;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorCallback;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.vibrate.VibrationUtil;
import com.samsung.android.knox.ContainerProxy;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.security.mdf.MdfUtils;
import java.util.Iterator;
import java.util.function.IntConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class KeyguardSecAbsKeyInputViewController extends KeyguardAbsKeyInputViewController {
    public final AccessibilityManager mAccessibilityManager;
    public final LinearLayout mBottomView;
    public String mBouncerMessage;
    public boolean mBouncerShowing;
    public String mBouncerSubMessage;
    public LinearLayout mContainer;
    public SecCountDownTimer mCountdownTimer;
    public final C07195 mDisplayListener;
    public final Space mDummyEcaSpace;
    public final LinearLayout mEcaFlexContainer;
    public View mEcaView;
    public final EmergencyButtonController mEmergencyButtonController;
    public final Handler mHandler;
    public final KeyguardHintTextArea mHintText;
    public int mImeBottom;
    public final LinearLayout mInputContainer;
    public final PathInterpolator mInterpolator;
    public boolean mIsFaceRunning;
    public boolean mIsImeShown;
    public final KeyguardTextBuilder mKeyguardTextBuilder;
    public final C07162 mKnoxStateCallback;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public final LinearLayout mMessageArea;
    public final LinearLayout mMessageContainer;
    public final ViewGroup mPasswordEntryBoxLayout;
    public int mPromptReason;
    public final KeyguardSecAbsKeyInputViewController$$ExternalSyntheticLambda1 mRotationConsumer;
    public final SecRotationWatcher mRotationWatcher;
    public int mSecondsRemaining;
    public final LinearLayout mSplitTouchView;
    public final KeyguardSecAbsKeyInputViewController$$ExternalSyntheticLambda0 mUpdateLayoutRunnable;
    public final KeyguardUpdateMonitorCallback mUpdateMonitorCallbacks;
    public final VibrationUtil mVibrationUtil;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class OnApplyWindowInsetsListener implements View.OnApplyWindowInsetsListener {
        public /* synthetic */ OnApplyWindowInsetsListener(KeyguardSecAbsKeyInputViewController keyguardSecAbsKeyInputViewController, int i) {
            this();
        }

        @Override // android.view.View.OnApplyWindowInsetsListener
        public final WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
            int i = view.getRootView().getRootWindowInsets().getInsets(WindowInsets.Type.ime()).bottom;
            KeyguardSecAbsKeyInputViewController keyguardSecAbsKeyInputViewController = KeyguardSecAbsKeyInputViewController.this;
            if (keyguardSecAbsKeyInputViewController.mImeBottom != i) {
                keyguardSecAbsKeyInputViewController.mImeBottom = i;
                keyguardSecAbsKeyInputViewController.mIsImeShown = i != 0;
                keyguardSecAbsKeyInputViewController.updateLayout();
            }
            return windowInsets;
        }

        private OnApplyWindowInsetsListener() {
        }
    }

    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.keyguard.KeyguardSecAbsKeyInputViewController$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r2v4, types: [com.android.keyguard.KeyguardSecAbsKeyInputViewController$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r2v6, types: [com.android.keyguard.KeyguardSecAbsKeyInputViewController$2] */
    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.keyguard.KeyguardSecAbsKeyInputViewController$5] */
    public KeyguardSecAbsKeyInputViewController(KeyguardSecAbsKeyInputView keyguardSecAbsKeyInputView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, FalsingCollector falsingCollector, EmergencyButtonController emergencyButtonController, FeatureFlags featureFlags, SecRotationWatcher secRotationWatcher, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager) {
        super(keyguardSecAbsKeyInputView, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, falsingCollector, emergencyButtonController, featureFlags);
        this.mInterpolator = new PathInterpolator(0.22f, 0.25f, 0.0f, 1.0f);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mBouncerMessage = "";
        this.mBouncerSubMessage = "";
        this.mSecondsRemaining = -1;
        int i = 0;
        this.mImeBottom = 0;
        this.mIsFaceRunning = false;
        this.mUpdateLayoutRunnable = new Runnable() { // from class: com.android.keyguard.KeyguardSecAbsKeyInputViewController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardSecAbsKeyInputViewController keyguardSecAbsKeyInputViewController = KeyguardSecAbsKeyInputViewController.this;
                keyguardSecAbsKeyInputViewController.mSecondsRemaining = -1;
                keyguardSecAbsKeyInputViewController.updateLayout();
            }
        };
        this.mRotationConsumer = new IntConsumer() { // from class: com.android.keyguard.KeyguardSecAbsKeyInputViewController$$ExternalSyntheticLambda1
            @Override // java.util.function.IntConsumer
            public final void accept(int i2) {
                KeyguardSecAbsKeyInputViewController keyguardSecAbsKeyInputViewController = KeyguardSecAbsKeyInputViewController.this;
                keyguardSecAbsKeyInputViewController.updateLayout(i2);
                keyguardSecAbsKeyInputViewController.initializeBottomContainerView();
            }
        };
        this.mUpdateMonitorCallbacks = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardSecAbsKeyInputViewController.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthenticated(int i2, BiometricSourceType biometricSourceType, boolean z) {
                if (biometricSourceType == BiometricSourceType.FINGERPRINT) {
                    KeyguardSecAbsKeyInputViewController keyguardSecAbsKeyInputViewController = KeyguardSecAbsKeyInputViewController.this;
                    if (((KeyguardAbsKeyInputViewController) keyguardSecAbsKeyInputViewController).mKeyguardUpdateMonitor.is2StepVerification()) {
                        keyguardSecAbsKeyInputViewController.reset();
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricLockoutChanged(boolean z) {
                KeyguardSecAbsKeyInputViewController keyguardSecAbsKeyInputViewController = KeyguardSecAbsKeyInputViewController.this;
                keyguardSecAbsKeyInputViewController.handleLandscapePINSecurityMessage(((KeyguardAbsKeyInputViewController) keyguardSecAbsKeyInputViewController).mKeyguardUpdateMonitor.getLockoutBiometricAttemptDeadline());
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricRunningStateChanged(BiometricSourceType biometricSourceType, boolean z) {
                KeyguardSecMessageAreaController keyguardSecMessageAreaController;
                BiometricSourceType biometricSourceType2 = BiometricSourceType.FACE;
                KeyguardSecAbsKeyInputViewController keyguardSecAbsKeyInputViewController = KeyguardSecAbsKeyInputViewController.this;
                if (biometricSourceType == biometricSourceType2) {
                    keyguardSecAbsKeyInputViewController.mIsFaceRunning = z;
                }
                boolean z2 = keyguardSecAbsKeyInputViewController.mBouncerShowing;
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = ((KeyguardAbsKeyInputViewController) keyguardSecAbsKeyInputViewController).mKeyguardUpdateMonitor;
                if (z2 && keyguardUpdateMonitor2.isUpdateSecurityMessage()) {
                    keyguardSecAbsKeyInputViewController.displayDefaultSecurityMessage();
                }
                if (keyguardSecAbsKeyInputViewController.isAllowedToAdjustSecurityView() && KeyguardSecAbsKeyInputViewController.isPINSecurityView(keyguardSecAbsKeyInputViewController.getSecurityViewId())) {
                    Resources resources = keyguardSecAbsKeyInputViewController.getResources();
                    if (!keyguardSecAbsKeyInputViewController.isLandscapeDisplay() || (keyguardSecMessageAreaController = keyguardSecAbsKeyInputViewController.mSubMessageAreaController) == null) {
                        return;
                    }
                    if (LsRune.SECURITY_SUB_DISPLAY_LOCK && ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) {
                        keyguardSecMessageAreaController.setVisibility(z ? 0 : 8);
                    }
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) keyguardSecMessageAreaController.getLayoutParams();
                    marginLayoutParams.topMargin = keyguardUpdateMonitor2.getLockoutBiometricAttemptDeadline() <= 0 ? resources.getDimensionPixelSize(R.dimen.kg_sub_message_margin_top) : 0;
                    keyguardSecMessageAreaController.setLayoutParams(marginLayoutParams);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onKeyguardBouncerFullyShowingChanged(boolean z) {
                KeyguardSecAbsKeyInputViewController keyguardSecAbsKeyInputViewController = KeyguardSecAbsKeyInputViewController.this;
                if (keyguardSecAbsKeyInputViewController.mBouncerShowing != z) {
                    keyguardSecAbsKeyInputViewController.mBouncerShowing = z;
                    if (z) {
                        keyguardSecAbsKeyInputViewController.setMessageTimeout(true);
                    } else {
                        keyguardSecAbsKeyInputViewController.mBouncerMessage = "";
                        keyguardSecAbsKeyInputViewController.mBouncerSubMessage = "";
                    }
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onLockModeChanged() {
                KeyguardSecAbsKeyInputViewController keyguardSecAbsKeyInputViewController = KeyguardSecAbsKeyInputViewController.this;
                long lockoutAttemptDeadline = ((KeyguardAbsKeyInputViewController) keyguardSecAbsKeyInputViewController).mKeyguardUpdateMonitor.getLockoutAttemptDeadline();
                if (lockoutAttemptDeadline != 0) {
                    if (((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isDualView()) {
                        keyguardSecAbsKeyInputViewController.handleAttemptLockout(lockoutAttemptDeadline);
                        return;
                    }
                    return;
                }
                keyguardSecAbsKeyInputViewController.mSecondsRemaining = -1;
                SecCountDownTimer secCountDownTimer = keyguardSecAbsKeyInputViewController.mCountdownTimer;
                if (secCountDownTimer != null) {
                    secCountDownTimer.cancel();
                    keyguardSecAbsKeyInputViewController.mCountdownTimer = null;
                    keyguardSecAbsKeyInputViewController.displayDefaultSecurityMessage();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onSimulationFailToUnlock(int i2) {
                KeyguardSecAbsKeyInputViewController keyguardSecAbsKeyInputViewController = KeyguardSecAbsKeyInputViewController.this;
                keyguardSecAbsKeyInputViewController.mVibrationUtil.playVibration(114);
                KeyguardUpdateMonitor keyguardUpdateMonitor2 = ((KeyguardAbsKeyInputViewController) keyguardSecAbsKeyInputViewController).mKeyguardUpdateMonitor;
                int failedUnlockAttempts = keyguardUpdateMonitor2.getFailedUnlockAttempts(i2) + 1;
                int i3 = failedUnlockAttempts % 5 == 0 ? 30000 : 0;
                Log.d("KeyguardSecAbsKeyInputViewController", "onSimulationFailToUnlock failedAttempts : " + failedUnlockAttempts + " timeoutMs : " + i3);
                keyguardSecAbsKeyInputViewController.getKeyguardSecurityCallback().reportUnlockAttempt(i2, i3, false);
                if (i3 == 0) {
                    boolean isHintText = keyguardSecAbsKeyInputViewController.isHintText();
                    KeyguardHintTextArea keyguardHintTextArea = keyguardSecAbsKeyInputViewController.mHintText;
                    if (isHintText && keyguardHintTextArea.getVisibility() == 8) {
                        keyguardHintTextArea.setVisibility(0);
                    }
                    if (keyguardSecAbsKeyInputViewController.isHintText()) {
                        keyguardHintTextArea.setVisibility(8);
                    }
                    keyguardSecAbsKeyInputViewController.handleAttemptLockout(keyguardUpdateMonitor2.setLockoutAttemptDeadline(i2, i3));
                    keyguardUpdateMonitor2.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_LOCKOUT_DEADLINE);
                }
                if (i3 == 0) {
                    keyguardSecAbsKeyInputViewController.setMessageTimeout(false);
                    int remainingAttempt = keyguardUpdateMonitor2.getRemainingAttempt(2);
                    String string = keyguardSecAbsKeyInputViewController.getContext().getString(((KeyguardSecAbsKeyInputView) keyguardSecAbsKeyInputViewController.mView).getWrongPasswordStringId());
                    if (remainingAttempt > 0) {
                        string = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(AbstractC0000x2c234b15.m2m(string, " ("), keyguardSecAbsKeyInputViewController.getResources().getQuantityString(R.plurals.kg_attempt_left, remainingAttempt, Integer.valueOf(remainingAttempt)), ")");
                    }
                    KeyguardSecMessageAreaController keyguardSecMessageAreaController = keyguardSecAbsKeyInputViewController.mMessageAreaController;
                    keyguardSecMessageAreaController.setMessage(string, false);
                    keyguardSecMessageAreaController.announceForAccessibility(string);
                    keyguardSecMessageAreaController.displayFailedAnimation();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onStartedWakingUp() {
                KeyguardSecAbsKeyInputViewController keyguardSecAbsKeyInputViewController = KeyguardSecAbsKeyInputViewController.this;
                if (keyguardSecAbsKeyInputViewController.mSecondsRemaining > 0 && ((KeyguardAbsKeyInputViewController) keyguardSecAbsKeyInputViewController).mKeyguardUpdateMonitor.getLockoutAttemptDeadline() == 0) {
                    keyguardSecAbsKeyInputViewController.mSecondsRemaining = -1;
                }
                if (((KeyguardFastBioUnlockController) Dependency.get(KeyguardFastBioUnlockController.class)).isFastWakeAndUnlockMode()) {
                    return;
                }
                keyguardSecAbsKeyInputViewController.updateLayout();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onTableModeChanged(boolean z) {
                boolean z2 = LsRune.SECURITY_SUB_DISPLAY_LOCK;
                KeyguardSecAbsKeyInputViewController keyguardSecAbsKeyInputViewController = KeyguardSecAbsKeyInputViewController.this;
                if (!z2 || keyguardSecAbsKeyInputViewController.isLandscapeDisplay()) {
                    keyguardSecAbsKeyInputViewController.updateLayout();
                    keyguardSecAbsKeyInputViewController.initializeBottomContainerView();
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onUserSwitching(int i2) {
                KeyguardSecAbsKeyInputViewController keyguardSecAbsKeyInputViewController = KeyguardSecAbsKeyInputViewController.this;
                KeyguardHintTextArea keyguardHintTextArea = keyguardSecAbsKeyInputViewController.mHintText;
                if (keyguardHintTextArea != null) {
                    keyguardHintTextArea.mPasswordHintText = keyguardSecAbsKeyInputViewController.mLockPatternUtils.getPasswordHint(i2);
                    keyguardSecAbsKeyInputViewController.mHintText.updateHintButton();
                    if (keyguardSecAbsKeyInputViewController.isHintText()) {
                        keyguardSecAbsKeyInputViewController.mHintText.setVisibility(0);
                    } else {
                        keyguardSecAbsKeyInputViewController.mHintText.setVisibility(8);
                    }
                }
            }
        };
        this.mKnoxStateCallback = new KnoxStateMonitorCallback() { // from class: com.android.keyguard.KeyguardSecAbsKeyInputViewController.2
            @Override // com.android.systemui.knox.KnoxStateMonitorCallback
            public final void onDisableDeviceWhenReachMaxFailed() {
                KeyguardSecAbsKeyInputViewController keyguardSecAbsKeyInputViewController = KeyguardSecAbsKeyInputViewController.this;
                keyguardSecAbsKeyInputViewController.mLockPatternUtils.requireStrongAuth(2, KeyguardUpdateMonitor.getCurrentUser());
                keyguardSecAbsKeyInputViewController.disableDevicePermanently();
            }

            @Override // com.android.systemui.knox.KnoxStateMonitorCallback
            public final void onDisableProfileWhenReachMaxFailed() {
                KeyguardSecAbsKeyInputViewController keyguardSecAbsKeyInputViewController = KeyguardSecAbsKeyInputViewController.this;
                keyguardSecAbsKeyInputViewController.getClass();
                Log.d("KeyguardSecAbsKeyInputViewController", "disableProfilePermanently");
                KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) keyguardSecAbsKeyInputViewController.mKnoxStateMonitor;
                Iterator it = knoxStateMonitorImpl.getContainerIds().iterator();
                int i2 = -1;
                while (it.hasNext()) {
                    int intValue = ((Integer) it.next()).intValue();
                    if (knoxStateMonitorImpl.isPersona(intValue)) {
                        i2 = intValue;
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putInt("android.intent.extra.user_handle", i2);
                ContainerProxy.sendPolicyUpdate("knox.container.proxy.POLICY_ADMIN_LOCK", bundle);
            }
        };
        this.mDisplayListener = new DisplayLifecycle.Observer() { // from class: com.android.keyguard.KeyguardSecAbsKeyInputViewController.5
            @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
            public final void onFolderStateChanged(boolean z) {
                KeyguardSecAbsKeyInputViewController.this.reset();
            }
        };
        this.mSplitTouchView = (LinearLayout) ((KeyguardSecAbsKeyInputView) this.mView).findViewById(R.id.split_touch_top_container);
        this.mMessageContainer = (LinearLayout) ((KeyguardSecAbsKeyInputView) this.mView).findViewById(R.id.message_container);
        this.mMessageArea = (LinearLayout) ((KeyguardSecAbsKeyInputView) this.mView).findViewById(R.id.message_area);
        this.mContainer = (LinearLayout) ((KeyguardSecAbsKeyInputView) this.mView).findViewById(R.id.container);
        this.mInputContainer = (LinearLayout) ((KeyguardSecAbsKeyInputView) this.mView).findViewById(R.id.input_container);
        this.mPasswordEntryBoxLayout = (ViewGroup) ((KeyguardSecAbsKeyInputView) this.mView).findViewById(R.id.password_entry_box);
        this.mBottomView = (LinearLayout) ((KeyguardSecAbsKeyInputView) this.mView).findViewById(R.id.bottom_container);
        this.mEcaView = ((KeyguardSecAbsKeyInputView) this.mView).findViewById(R.id.keyguard_selector_fade_container);
        this.mDummyEcaSpace = (Space) ((KeyguardSecAbsKeyInputView) this.mView).findViewById(R.id.dummy_emergency_call_button_space);
        KeyguardHintTextArea keyguardHintTextArea = (KeyguardHintTextArea) ((KeyguardSecAbsKeyInputView) this.mView).findViewById(R.id.hint_text_box);
        this.mHintText = keyguardHintTextArea;
        if (isHintText()) {
            keyguardHintTextArea.setVisibility(0);
        }
        ((BouncerKeyguardMessageArea) this.mMessageAreaController.mView).setMaxFontScale(1.1f);
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mSubMessageAreaController;
        if (keyguardSecMessageAreaController != null) {
            ((BouncerKeyguardMessageArea) keyguardSecMessageAreaController.mView).setMaxFontScale(1.1f);
        }
        setMessageTimeout(true);
        this.mRotationWatcher = secRotationWatcher;
        this.mVibrationUtil = vibrationUtil;
        this.mKeyguardTextBuilder = KeyguardTextBuilder.getInstance(getContext());
        this.mKnoxStateMonitor = (KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class);
        this.mAccessibilityManager = accessibilityManager;
        this.mEmergencyButtonController = emergencyButtonController;
        this.mEcaFlexContainer = (LinearLayout) ((KeyguardSecAbsKeyInputView) this.mView).findViewById(R.id.emergency_button_container_flex);
        ((KeyguardSecAbsKeyInputView) this.mView).setOnApplyWindowInsetsListener(new OnApplyWindowInsetsListener(this, i));
    }

    public static byte[] charSequenceToByteArray(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        byte[] bArr = new byte[charSequence.length()];
        for (int i = 0; i < charSequence.length(); i++) {
            bArr[i] = (byte) charSequence.charAt(i);
        }
        return bArr;
    }

    public static boolean isPINSecurityView(int i) {
        return i == R.id.keyguard_pin_view;
    }

    public static boolean isPasswordView(int i) {
        return i == R.id.keyguard_password_view;
    }

    public final void disableDevicePermanently() {
        Log.d("KeyguardSecAbsKeyInputViewController", "disableDevicePermanently");
        ((KeyguardSecAbsKeyInputView) this.mView).setPasswordEntryEnabled(false);
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (keyguardSecMessageAreaController != null) {
            keyguardSecMessageAreaController.setMessage(this.mKeyguardTextBuilder.getDefaultSecurityMessage(KeyguardSecurityModel.SecurityMode.None), false);
        }
    }

    public final int getInDisplayFpContainerBottomMargin(boolean z) {
        Resources resources = getResources();
        int dimensionPixelSize = resources.getDimensionPixelSize(z ? R.dimen.kg_password_container_margin_bottom : R.dimen.kg_pin_container_margin_bottom) + DeviceState.sInDisplayFingerprintHeight;
        View view = this.mEcaView;
        return ((dimensionPixelSize - ((view == null || view.findViewById(R.id.emergency_call_button).getVisibility() != 0) ? 0 : resources.getDimensionPixelSize(R.dimen.keyguard_bottom_area_emergency_button_area_min_height))) - resources.getDimensionPixelSize(z ? R.dimen.kg_password_eca_margin_bottom : R.dimen.kg_pin_eca_margin_bottom)) - resources.getDimensionPixelSize(android.R.dimen.notification_content_margin_end);
    }

    public int getSecurityViewId() {
        return 0;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController
    public void handleAttemptLockout(long j) {
        ((KeyguardSecAbsKeyInputView) this.mView).setPasswordEntryEnabled(false);
        setSubSecurityMessage(0);
        KeyguardHintTextArea keyguardHintTextArea = this.mHintText;
        if (keyguardHintTextArea != null) {
            keyguardHintTextArea.setVisibility(8);
        }
        if (isForgotPasswordAvailable()) {
            updateForgotPasswordTextVisibility();
        }
        SecCountDownTimer secCountDownTimer = this.mCountdownTimer;
        if (secCountDownTimer != null) {
            secCountDownTimer.cancel();
            this.mCountdownTimer = null;
        }
        SecCountDownTimer secCountDownTimer2 = new SecCountDownTimer(j - SystemClock.elapsedRealtime(), 1000L, getContext(), ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor, this.mKeyguardTextBuilder, true) { // from class: com.android.keyguard.KeyguardSecAbsKeyInputViewController.3
            @Override // com.android.keyguard.SecCountDownTimer, android.os.CountDownTimer
            public final void onFinish() {
                int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                if (KeyguardSecAbsKeyInputViewController.this.isHintText()) {
                    KeyguardSecAbsKeyInputViewController.this.mHintText.setVisibility(0);
                }
                KeyguardSecAbsKeyInputViewController.this.setMessageTimeout(true);
                ((BouncerKeyguardMessageArea) KeyguardSecAbsKeyInputViewController.this.mMessageAreaController.mView).scrollTo(0, 0);
                KeyguardSecAbsKeyInputViewController.this.mMessageAreaController.setMovementMethod(null);
                KeyguardSecAbsKeyInputViewController.this.mMessageAreaController.setMessage("", false);
                KeyguardSecAbsKeyInputViewController keyguardSecAbsKeyInputViewController = KeyguardSecAbsKeyInputViewController.this;
                keyguardSecAbsKeyInputViewController.mBouncerMessage = "";
                keyguardSecAbsKeyInputViewController.mBouncerSubMessage = "";
                keyguardSecAbsKeyInputViewController.mLockPatternUtils.clearBiometricAttemptDeadline(currentUser);
                ((KeyguardAbsKeyInputViewController) KeyguardSecAbsKeyInputViewController.this).mKeyguardUpdateMonitor.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_TRIGGERED_FACE_LOCKOUT_RESET);
                KeyguardSecAbsKeyInputViewController.this.resetState();
                KeyguardSecAbsKeyInputViewController keyguardSecAbsKeyInputViewController2 = KeyguardSecAbsKeyInputViewController.this;
                keyguardSecAbsKeyInputViewController2.mHandler.removeCallbacks(keyguardSecAbsKeyInputViewController2.mUpdateLayoutRunnable);
                KeyguardSecAbsKeyInputViewController keyguardSecAbsKeyInputViewController3 = KeyguardSecAbsKeyInputViewController.this;
                keyguardSecAbsKeyInputViewController3.mHandler.post(keyguardSecAbsKeyInputViewController3.mUpdateLayoutRunnable);
                if (!KeyguardSecAbsKeyInputViewController.this.isForgotPasswordAvailable() || DeviceType.isWeaverDevice()) {
                    return;
                }
                KeyguardSecAbsKeyInputViewController.this.updateForgotPasswordTextVisibility();
            }

            @Override // com.android.keyguard.SecCountDownTimer, android.os.CountDownTimer
            public final void onTick(long j2) {
                int round = (int) Math.round(j2 / 1000.0d);
                if (((KeyguardAbsKeyInputViewController) KeyguardSecAbsKeyInputViewController.this).mKeyguardUpdateMonitor.isHiddenInputContainer()) {
                    KeyguardSecAbsKeyInputViewController keyguardSecAbsKeyInputViewController = KeyguardSecAbsKeyInputViewController.this;
                    keyguardSecAbsKeyInputViewController.mSecondsRemaining = round;
                    keyguardSecAbsKeyInputViewController.mPasswordEntryBoxLayout.setVisibility(8);
                }
                super.onTick(j2);
                String str = this.mTimerText;
                if (!str.isEmpty()) {
                    KeyguardSecAbsKeyInputViewController.this.mMessageAreaController.setMessage(str, false);
                }
                KeyguardSecAbsKeyInputViewController.this.updateLayoutForAttemptRemainingBeforeWipe();
            }
        };
        this.mCountdownTimer = secCountDownTimer2;
        secCountDownTimer2.start();
    }

    public final void handleLandscapePINSecurityMessage(long j) {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (keyguardSecMessageAreaController == null || !isPINSecurityView(getSecurityViewId()) || !isAllowedToAdjustSecurityView() || ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor.isSimPinSecure()) {
            return;
        }
        int dimensionPixelSize = j > 0 ? getResources().getDimensionPixelSize(R.dimen.kg_security_input_box_height) : 0;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) keyguardSecMessageAreaController.getLayoutParams();
        ViewGroup viewGroup = this.mPasswordEntryBoxLayout;
        ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) viewGroup.getLayoutParams();
        marginLayoutParams.topMargin = dimensionPixelSize;
        marginLayoutParams2.topMargin = getResources().getDimensionPixelSize(R.dimen.kg_message_area_font_size) + getResources().getDimensionPixelSize(R.dimen.kg_security_input_box_margin_top) + dimensionPixelSize;
        ((BouncerKeyguardMessageArea) keyguardSecMessageAreaController.mView).setTextSize(0, getResources().getDimensionPixelSize(R.dimen.kg_biometric_view_text_font_size));
        keyguardSecMessageAreaController.setLayoutParams(marginLayoutParams);
        viewGroup.setLayoutParams(marginLayoutParams2);
    }

    public final boolean isAllowedToAdjustSecurityView() {
        Context context = getContext();
        boolean isFingerprintOptionEnabled = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor.isFingerprintOptionEnabled();
        Resources resources = context.getResources();
        return Math.max(resources.getConfiguration().windowConfiguration.getBounds().width(), resources.getConfiguration().windowConfiguration.getBounds().height()) < ((resources.getDimensionPixelSize(R.dimen.kg_biometric_view_min_height) + (resources.getDimensionPixelSize(R.dimen.kg_lock_icon_top_margin) + resources.getDimensionPixelSize(R.dimen.status_bar_header_height_keyguard))) + (resources.getDimensionPixelSize(R.dimen.kg_pin_eca_margin_bottom) + (resources.getDimensionPixelSize(R.dimen.keyguard_bottom_area_emergency_button_area_min_height) + (resources.getDimensionPixelSize(R.dimen.kg_pin_container_margin_bottom) + ((resources.getDimensionPixelSize(R.dimen.kg_security_input_box_height) + (resources.getDimensionPixelSize(R.dimen.kg_sub_help_text_font_size) + (resources.getDimensionPixelSize(R.dimen.kg_message_area_font_size) + (resources.getDimensionPixelSize(R.dimen.keyguard_hint_text) + (resources.getDimensionPixelSize(R.dimen.kg_message_area_font_size) * 3))))) + ((!LsRune.SECURITY_SUB_DISPLAY_LOCK || !((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) ? SecurityUtils.getPINContainerHeight(context) : SecurityUtils.getFoldPINContainerHeight(context))))))) + (isFingerprintOptionEnabled ? DeviceState.sInDisplayFingerprintHeight : 0);
    }

    public final boolean isBiometricLockoutLandscape() {
        return LsRune.SECURITY_SUB_DISPLAY_LOCK && isLandscapeDisplay() && isPasswordView(getSecurityViewId()) && ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor.isMaxFailedBiometricUnlockAttempts(KeyguardUpdateMonitor.getCurrentUser());
    }

    public final boolean isForgotPasswordAvailable() {
        int securityViewId = getSecurityViewId();
        return isPINSecurityView(securityViewId) || isPasswordView(securityViewId);
    }

    public final boolean isHiddenPasswordSubMessageVisibility() {
        return DeviceType.isTablet() && isPasswordView(getSecurityViewId()) && getResources().getBoolean(R.bool.small_tablet_password_sub_message_policy);
    }

    public final boolean isHintText() {
        KeyguardHintTextArea keyguardHintTextArea = this.mHintText;
        if (keyguardHintTextArea != null && keyguardHintTextArea.mPasswordHintText != null) {
            int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor;
            if (keyguardUpdateMonitor.getFailedUnlockAttempts(currentUser) > 0 && keyguardUpdateMonitor.getLockoutAttemptDeadline() == 0) {
                return true;
            }
        }
        return false;
    }

    public final boolean isLandscapeDisplay() {
        return getResources().getConfiguration().orientation == 2;
    }

    public final boolean isLandscapePolicyAllowed() {
        return (!isLandscapeDisplay() || DeviceType.isTablet() || ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor.isDualDisplayPolicyAllowed()) ? false : true;
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController
    public void onPasswordChecked(int i, int i2, boolean z, boolean z2) {
        boolean z3 = KeyguardUpdateMonitor.getCurrentUser() == i;
        com.android.systemui.keyguard.Log.m139d("KeyguardSecAbsKeyInputViewController", "!@onPasswordChecked matched=%b timeoutMs=%d userId=%d", Boolean.valueOf(z), Integer.valueOf(i2), Integer.valueOf(i));
        VibrationUtil vibrationUtil = this.mVibrationUtil;
        KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor;
        if (z) {
            Log.e("KeyguardSecAbsKeyInputViewController", "onPasswordChecked");
            vibrationUtil.playVibration(1);
            if (keyguardUpdateMonitor.isForgotPasswordView()) {
                getKeyguardSecurityCallback().setPrevCredential(this.mPrevCredential);
            }
            getKeyguardSecurityCallback().reportUnlockAttempt(i, 0, true);
            if (z3) {
                this.mDismissing = true;
                getKeyguardSecurityCallback().dismiss(i, this.mSecurityMode, true);
            }
        } else {
            vibrationUtil.playVibration(114);
            if (z2) {
                getKeyguardSecurityCallback().reportUnlockAttempt(i, i2, false);
                KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) this.mKnoxStateMonitor;
                if (knoxStateMonitorImpl.isDeviceDisabledForMaxFailedAttempt()) {
                    AuditLog.logAsUser(5, 1, true, Process.myPid(), "KeyguardSecAbsKeyInputViewController", String.format("User %d has exceeded number of authentication failure limit", Integer.valueOf(i)), i);
                    disableDevicePermanently();
                } else {
                    knoxStateMonitorImpl.isDisableDeviceByMultifactor();
                    KeyguardHintTextArea keyguardHintTextArea = this.mHintText;
                    if (i2 == 0) {
                        if (isHintText() && keyguardHintTextArea.getVisibility() == 8) {
                            keyguardHintTextArea.setVisibility(0);
                        }
                    } else if (i2 > 0 && !keyguardUpdateMonitor.isPermanentLock()) {
                        if (isHintText()) {
                            keyguardHintTextArea.setVisibility(8);
                        }
                        setMessageTimeout(true);
                        handleAttemptLockout((!keyguardUpdateMonitor.isForgotPasswordView() || DeviceType.isWeaverDevice()) ? keyguardUpdateMonitor.setLockoutAttemptDeadline(i, i2) : keyguardUpdateMonitor.setLockoutAttemptDeadline(0, i2));
                        keyguardUpdateMonitor.updateBiometricListeningState(2, FaceAuthUiEvent.FACE_AUTH_UPDATED_STRONG_AUTH_CHANGED);
                    }
                }
            }
            if (i2 == 0) {
                setMessageTimeout(false);
                int remainingAttempt = keyguardUpdateMonitor.getRemainingAttempt(2);
                String string = getContext().getString(((KeyguardSecAbsKeyInputView) this.mView).getWrongPasswordStringId());
                if (remainingAttempt > 0) {
                    string = AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(AbstractC0000x2c234b15.m2m(string, " ("), getResources().getQuantityString(R.plurals.kg_attempt_left, remainingAttempt, Integer.valueOf(remainingAttempt)), ")");
                }
                KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
                keyguardSecMessageAreaController.setMessage(string, false);
                keyguardSecMessageAreaController.announceForAccessibility(string);
                keyguardSecMessageAreaController.displayFailedAnimation();
            }
        }
        ((KeyguardSecAbsKeyInputView) this.mView).resetPasswordText(true, !z);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void onResume(int i) {
        super.onResume(i);
        if (DeviceState.isTesting()) {
            return;
        }
        reset();
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController
    public void onUserInput() {
        super.onUserInput();
        setSubSecurityMessage(0);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public void onViewAttached() {
        super.onViewAttached();
        ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor.registerCallback(this.mUpdateMonitorCallbacks);
        ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).registerCallback(this.mKnoxStateCallback);
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).addObserver(this.mDisplayListener);
        }
        if (DeviceState.shouldEnableKeyguardScreenRotation(getContext()) || DeviceType.isTablet()) {
            this.mRotationWatcher.addCallback(this.mRotationConsumer);
        }
        setEmergencyButtonCallback(true);
    }

    @Override // com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public void onViewDetached() {
        super.onViewDetached();
        ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor.removeCallback(this.mUpdateMonitorCallbacks);
        ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).removeCallback(this.mKnoxStateCallback);
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).removeObserver(this.mDisplayListener);
        }
        if (DeviceState.shouldEnableKeyguardScreenRotation(getContext()) || DeviceType.isTablet()) {
            this.mRotationWatcher.removeCallback(this.mRotationConsumer);
        }
        setEmergencyButtonCallback(false);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void reset() {
        this.mDismissing = false;
        ((KeyguardSecAbsKeyInputView) this.mView).resetPasswordText(false, false);
        KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor;
        long lockoutAttemptDeadline = keyguardUpdateMonitor.getLockoutAttemptDeadline();
        boolean isDualDarInnerAuthRequired = keyguardUpdateMonitor.isDualDarInnerAuthRequired(KeyguardUpdateMonitor.getCurrentUser());
        KnoxStateMonitor knoxStateMonitor = this.mKnoxStateMonitor;
        if (isDualDarInnerAuthRequired) {
            long dualDarInnerLockoutAttemptDeadline = ((KnoxStateMonitorImpl) knoxStateMonitor).getDualDarInnerLockoutAttemptDeadline();
            if (dualDarInnerLockoutAttemptDeadline != 0 && dualDarInnerLockoutAttemptDeadline > lockoutAttemptDeadline) {
                StringBuilder m17m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("reset() switch to inner deadline. deadline = ", lockoutAttemptDeadline, ", innerDeadline = ");
                m17m.append(dualDarInnerLockoutAttemptDeadline);
                Log.d("KeyguardSecAbsKeyInputViewController", m17m.toString());
                ((KeyguardSecAbsKeyInputView) this.mView).enableTouch();
                lockoutAttemptDeadline = dualDarInnerLockoutAttemptDeadline;
            }
        }
        updateLayout();
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
        if (isHintText()) {
            keyguardHintTextArea.updateHintButton();
            keyguardHintTextArea.setVisibility(0);
        }
        if (isForgotPasswordAvailable()) {
            updateForgotPasswordTextVisibility();
            if (lockoutAttemptDeadline > 0 && keyguardUpdateMonitor.getLockoutBiometricAttemptDeadline() == 0) {
                handleAttemptLockout(0L);
            }
        }
        resetState();
    }

    public final void resetFor2StepVerification() {
        boolean isPasswordView = isPasswordView(getSecurityViewId());
        KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor;
        if (!keyguardUpdateMonitor.is2StepVerification() || keyguardUpdateMonitor.getUserUnlockedWithBiometric(KeyguardUpdateMonitor.getCurrentUser())) {
            ((KeyguardSecAbsKeyInputView) this.mView).setPasswordEntryEnabled(true);
            if (isPasswordView) {
                ((KeyguardSecAbsKeyInputView) this.mView).setPasswordEntryInputEnabled(true);
                return;
            }
            return;
        }
        Log.d("KeyguardSecAbsKeyInputViewController", "reset() - 2 step verification");
        ((KeyguardSecAbsKeyInputView) this.mView).setPasswordEntryEnabled(false);
        if (isPasswordView) {
            ((KeyguardSecAbsKeyInputView) this.mView).setPasswordEntryInputEnabled(false);
        }
    }

    public final void setEmergencyButtonCallback(boolean z) {
        View view;
        if (LsRune.SECURITY_DIRECT_CALL_TO_ECC) {
            KeyguardSecAbsKeyInputView keyguardSecAbsKeyInputView = (KeyguardSecAbsKeyInputView) this.mView;
            view = keyguardSecAbsKeyInputView.findViewById(keyguardSecAbsKeyInputView.getPasswordTextViewId());
        } else {
            view = null;
        }
        KeyguardAbsKeyInputViewController.C06541 c06541 = z ? this.mEmergencyButtonCallback : null;
        EmergencyButtonController emergencyButtonController = this.mEmergencyButtonController;
        emergencyButtonController.mEmergencyButtonCallback = c06541;
        emergencyButtonController.mPasswordEntry = view;
    }

    public final void setLandscapeLayoutPadding(LinearLayout linearLayout, boolean z) {
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_message_area_padding_side);
        linearLayout.setPadding(0, 0, z ? dimensionPixelSize : 0, 0);
        if (((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor.isInDisplayFingerprintMarginAccepted()) {
            if (SecurityUtils.getCurrentRotation(getContext()) == 1) {
                linearLayout.setPadding(0, 0, dimensionPixelSize, 0);
            } else {
                linearLayout.setPadding(dimensionPixelSize, 0, 0, 0);
            }
        }
    }

    public final void setSubSecurityMessage(int i) {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mSubMessageAreaController;
        if (keyguardSecMessageAreaController != null) {
            int currentUser = KeyguardUpdateMonitor.getCurrentUser();
            KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) this.mKnoxStateMonitor;
            if (!knoxStateMonitorImpl.isDeviceDisabledForMaxFailedAttempt()) {
                knoxStateMonitorImpl.isDisableDeviceByMultifactor();
                if ((!SemPersonaManager.isDoEnabled(currentUser) || ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor.is2StepVerification()) && !this.mLockPatternUtils.isManagedProfileWithUnifiedChallenge(currentUser)) {
                    if (isLandscapePolicyAllowed() || isHiddenPasswordSubMessageVisibility() || isBiometricLockoutLandscape()) {
                        keyguardSecMessageAreaController.setVisibility(8);
                        return;
                    }
                    if (i == 0) {
                        keyguardSecMessageAreaController.setMessage("", false);
                        ((BouncerKeyguardMessageArea) keyguardSecMessageAreaController.mView).setFocusable(false);
                    } else {
                        String string = getResources().getString(i, 4);
                        if (this.mBouncerMessage.isEmpty() || !this.mBouncerSubMessage.equals(string)) {
                            keyguardSecMessageAreaController.formatMessage(i, 4);
                            ((BouncerKeyguardMessageArea) keyguardSecMessageAreaController.mView).setFocusable(true);
                            this.mBouncerSubMessage = string;
                        }
                    }
                    keyguardSecMessageAreaController.setVisibility(0);
                    return;
                }
            }
            keyguardSecMessageAreaController.setMessage("", false);
            keyguardSecMessageAreaController.setVisibility(8);
        }
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public final void showMessage(CharSequence charSequence, ColorStateList colorStateList, boolean z) {
        setMessageTimeout(false);
        this.mMessageAreaController.displayFailedAnimation();
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mSubMessageAreaController;
        if (keyguardSecMessageAreaController != null && !isLandscapePolicyAllowed() && !isHiddenPasswordSubMessageVisibility()) {
            keyguardSecMessageAreaController.setVisibility(4);
        }
        super.showMessage(charSequence, colorStateList, z);
    }

    @Override // com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void showPromptReason(int i) {
        if (this.mMessageAreaController == null) {
            Log.d("KeyguardSecAbsKeyInputViewController", "showPromptReason mMessageAreaController is null");
            return;
        }
        this.mPromptReason = i;
        if (i != 0) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor;
            long lockoutAttemptDeadline = keyguardUpdateMonitor.getLockoutAttemptDeadline();
            if (keyguardUpdateMonitor.isDualDarInnerAuthRequired(KeyguardUpdateMonitor.getCurrentUser())) {
                long dualDarInnerLockoutAttemptDeadline = ((KnoxStateMonitorImpl) this.mKnoxStateMonitor).getDualDarInnerLockoutAttemptDeadline();
                if (dualDarInnerLockoutAttemptDeadline != 0 && dualDarInnerLockoutAttemptDeadline > lockoutAttemptDeadline) {
                    StringBuilder m17m = AbstractResolvableFuture$$ExternalSyntheticOutline0.m17m("showPromptReason() switch to inner deadline. deadline = ", lockoutAttemptDeadline, ", innerDeadline = ");
                    m17m.append(dualDarInnerLockoutAttemptDeadline);
                    Log.d("KeyguardSecAbsKeyInputViewController", m17m.toString());
                    lockoutAttemptDeadline = dualDarInnerLockoutAttemptDeadline;
                }
            }
            if (lockoutAttemptDeadline > 0) {
                return;
            }
            ((KeyguardSecAbsKeyInputView) this.mView).getPromptReasonStringRes(i);
        }
    }

    public final boolean skipUpdateWhenCloseFolder() {
        if (!LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            return false;
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor;
        return keyguardUpdateMonitor.mGoingToSleep || !keyguardUpdateMonitor.mDeviceInteractive;
    }

    public void updateLayout() {
        updateLayout(SecurityUtils.getCurrentRotation(getContext()));
    }

    public final void updateLayoutForAttemptRemainingBeforeWipe() {
        int dimensionPixelSize;
        Space space;
        int dimensionPixelSize2;
        int dimensionPixelSize3;
        if (this.mSecondsRemaining <= 1 && ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor.getLockoutAttemptDeadline() == 0) {
            this.mSecondsRemaining = -1;
            return;
        }
        Resources resources = getResources();
        if (this.mSecondsRemaining >= 0) {
            boolean isTablet = DeviceType.isTablet();
            KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mSubMessageAreaController;
            LinearLayout linearLayout = this.mMessageArea;
            if (linearLayout != null) {
                if (keyguardSecMessageAreaController != null) {
                    keyguardSecMessageAreaController.setVisibility(8);
                }
                Rect bounds = resources.getConfiguration().windowConfiguration.getBounds();
                int currentRotation = SecurityUtils.getCurrentRotation(getContext());
                boolean z = currentRotation == 0 || currentRotation == 2;
                int max = z ? Math.max(bounds.width(), bounds.height()) : Math.min(bounds.width(), bounds.height());
                if (isTablet) {
                    dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.keyguard_bottom_area_emergency_button_area_min_height_tablet);
                    dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.kg_pattern_eca_margin_bottom_tablet);
                } else {
                    dimensionPixelSize2 = resources.getDimensionPixelSize(R.dimen.keyguard_bottom_area_emergency_button_area_min_height);
                    dimensionPixelSize3 = resources.getDimensionPixelSize(R.dimen.kg_pattern_eca_margin_bottom);
                }
                int i = dimensionPixelSize3 + dimensionPixelSize2;
                int dimensionPixelSize4 = getResources().getDimensionPixelSize(android.R.dimen.notification_content_margin_end);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                layoutParams.width = -1;
                layoutParams.height = (max - i) - dimensionPixelSize4;
                if (z) {
                    dimensionPixelSize4 = resources.getDimensionPixelSize(R.dimen.kg_message_area_padding_side);
                }
                linearLayout.setPadding(dimensionPixelSize4, 0, dimensionPixelSize4, 0);
                linearLayout.setLayoutParams(layoutParams);
            }
            if (keyguardSecMessageAreaController != null) {
                keyguardSecMessageAreaController.setMovementMethod(new ScrollingMovementMethod());
            }
            LinearLayout linearLayout2 = this.mInputContainer;
            if (linearLayout2 != null) {
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
                layoutParams2.width = -1;
                layoutParams2.height = -2;
                layoutParams2.bottomMargin = 0;
                linearLayout2.setPadding(0, 0, 0, 0);
                linearLayout2.setLayoutParams(layoutParams2);
            }
            LinearLayout linearLayout3 = this.mContainer;
            if (linearLayout3 != null) {
                linearLayout3.setVisibility(8);
            }
            View view = this.mEcaView;
            if (view != null) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                boolean isPasswordView = isPasswordView(getSecurityViewId());
                if (isPasswordView) {
                    dimensionPixelSize = resources.getDimensionPixelSize(isTablet ? R.dimen.kg_password_eca_margin_bottom_tablet : R.dimen.kg_password_eca_margin_bottom);
                } else {
                    dimensionPixelSize = resources.getDimensionPixelSize(isTablet ? R.dimen.kg_pin_eca_margin_bottom_tablet : R.dimen.kg_pin_eca_margin_bottom);
                }
                marginLayoutParams.setMargins(0, 0, 0, dimensionPixelSize);
                this.mEcaView.setLayoutParams(marginLayoutParams);
                if (isPasswordView && (space = this.mDummyEcaSpace) != null) {
                    space.setVisibility(8);
                }
            }
            LinearLayout linearLayout4 = this.mBottomView;
            if (linearLayout4 != null) {
                linearLayout4.setGravity(80);
                linearLayout4.setOrientation(1);
            }
        }
    }

    public final void updatePortraitLayout(int i) {
        int i2;
        Space space;
        LinearLayout linearLayout;
        Resources resources = getResources();
        boolean isPasswordView = isPasswordView(i);
        boolean z = LsRune.SECURITY_SUB_DISPLAY_LOCK;
        boolean z2 = z && ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened;
        LinearLayout linearLayout2 = this.mBottomView;
        if (linearLayout2 != null) {
            linearLayout2.setGravity(80);
            linearLayout2.setOrientation(1);
            if ((linearLayout2.getLayoutParams() instanceof FrameLayout.LayoutParams) && !z2) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) linearLayout2.getLayoutParams();
                layoutParams.width = -1;
                linearLayout2.setLayoutParams(layoutParams);
            }
        }
        LinearLayout linearLayout3 = this.mSplitTouchView;
        if (linearLayout3 != null) {
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout3.getLayoutParams();
            layoutParams2.width = -1;
            layoutParams2.height = -1;
            layoutParams2.weight = 1.0f;
            linearLayout3.setLayoutParams(layoutParams2);
        }
        if (isHintText()) {
            this.mHintText.setVisibility(0);
        }
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (keyguardSecMessageAreaController != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) keyguardSecMessageAreaController.getLayoutParams();
            marginLayoutParams.topMargin = 0;
            keyguardSecMessageAreaController.setLayoutParams(marginLayoutParams);
        }
        LinearLayout linearLayout4 = this.mMessageArea;
        if (linearLayout4 != null && (linearLayout = this.mInputContainer) != null) {
            LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) linearLayout4.getLayoutParams();
            LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams3.width = -1;
            layoutParams3.height = -2;
            layoutParams3.bottomMargin = 0;
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.kg_security_input_box_side_margin);
            linearLayout4.setPadding(z2 ? 0 : dimensionPixelSize, 0, z2 ? 0 : dimensionPixelSize, 0);
            layoutParams4.width = -1;
            layoutParams4.height = -2;
            linearLayout.setPadding(0, 0, 0, 0);
            linearLayout.setGravity(17);
            LinearLayout linearLayout5 = this.mContainer;
            if (linearLayout5 != null) {
                LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) linearLayout5.getLayoutParams();
                KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor;
                if (isPasswordView) {
                    layoutParams5.width = -1;
                    layoutParams5.height = -2;
                    layoutParams5.setMarginStart(z2 ? 0 : dimensionPixelSize);
                    if (z2) {
                        dimensionPixelSize = 0;
                    }
                    layoutParams5.setMarginEnd(dimensionPixelSize);
                    layoutParams5.topMargin = resources.getDimensionPixelSize(R.dimen.kg_security_input_box_margin_top);
                    if (keyguardUpdateMonitor.isInDisplayFingerprintMarginAccepted() && DeviceState.isInDisplayFpSensorPositionHigh() && this.mSecondsRemaining == -1 && !this.mIsImeShown) {
                        layoutParams5.bottomMargin = getInDisplayFpContainerBottomMargin(true);
                    } else {
                        layoutParams5.bottomMargin = resources.getDimensionPixelSize(R.dimen.kg_password_container_margin_bottom);
                    }
                } else {
                    layoutParams5.width = -2;
                    layoutParams5.height = -2;
                    layoutParams5.topMargin = 0;
                    if (keyguardUpdateMonitor.isInDisplayFingerprintMarginAccepted() && this.mSecondsRemaining == -1 && DeviceState.isInDisplayFpSensorPositionHigh()) {
                        layoutParams5.bottomMargin = getInDisplayFpContainerBottomMargin(false);
                    } else if (!z) {
                        layoutParams5.bottomMargin = resources.getDimensionPixelSize(R.dimen.kg_pin_container_margin_bottom);
                    } else if (z2) {
                        boolean isLandscapeDisplay = isLandscapeDisplay();
                        int i3 = R.dimen.kg_fold_pin_container_margin_bottom;
                        if (isLandscapeDisplay) {
                            if (!(resources.getDimensionPixelSize(R.dimen.kg_fold_pin_container_margin_bottom) == resources.getDimensionPixelSize(R.dimen.kg_fold_pin_container_margin_bottom_small))) {
                                i3 = R.dimen.kg_fold_pin_container_margin_bottom_small;
                            }
                            layoutParams5.bottomMargin = resources.getDimensionPixelSize(i3);
                        } else {
                            layoutParams5.bottomMargin = resources.getDimensionPixelSize(R.dimen.kg_fold_pin_container_margin_bottom);
                        }
                        SecurityUtils.sPINContainerBottomMargin = layoutParams5.bottomMargin;
                    } else {
                        layoutParams5.bottomMargin = resources.getDimensionPixelSize(R.dimen.kg_fold_sub_pin_container_margin_bottom);
                    }
                }
                this.mContainer.setLayoutParams(layoutParams5);
            }
            linearLayout4.setLayoutParams(layoutParams3);
            linearLayout.setLayoutParams(layoutParams4);
            linearLayout.setVisibility(0);
        }
        ViewGroup viewGroup = this.mPasswordEntryBoxLayout;
        if (viewGroup != null && isPINSecurityView(i)) {
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) viewGroup.getLayoutParams();
            marginLayoutParams2.setMargins(marginLayoutParams2.leftMargin, 0, marginLayoutParams2.rightMargin, marginLayoutParams2.bottomMargin);
            viewGroup.setLayoutParams(marginLayoutParams2);
        }
        View view = this.mEcaView;
        if (view != null) {
            LinearLayout.LayoutParams layoutParams6 = (LinearLayout.LayoutParams) view.getLayoutParams();
            layoutParams6.width = -1;
            layoutParams6.height = -2;
            if (isPasswordView) {
                i2 = R.dimen.kg_password_eca_margin_bottom;
            } else {
                i2 = i == R.id.keyguard_fmm_view ? R.dimen.kg_fmm_eca_margin_bottom : R.dimen.kg_pin_eca_margin_bottom;
            }
            layoutParams6.bottomMargin = resources.getDimensionPixelSize(i2);
            this.mEcaView.setLayoutParams(layoutParams6);
            LinearLayout linearLayout6 = this.mEcaFlexContainer;
            if (linearLayout6 != null) {
                linearLayout6.setVisibility(8);
            }
            this.mEcaView.setVisibility(0);
            this.mEmergencyButtonController.setEmergencyView(this.mEcaView.findViewById(R.id.emergency_call_button));
            if (isPasswordView && (space = this.mDummyEcaSpace) != null) {
                space.setVisibility(8);
            }
        }
        if (keyguardSecMessageAreaController != null && isPINSecurityView(i) && isAllowedToAdjustSecurityView()) {
            ((BouncerKeyguardMessageArea) keyguardSecMessageAreaController.mView).setTextSize(0, getResources().getDimensionPixelSize(R.dimen.kg_message_area_font_size));
        }
        KeyguardSecMessageAreaController keyguardSecMessageAreaController2 = this.mSubMessageAreaController;
        if (keyguardSecMessageAreaController2 != null) {
            keyguardSecMessageAreaController2.setVisibility(0);
        }
        updatePrevInfoTextSize();
    }

    public void verifyPasswordAndUnlock() {
        if (this.mDismissing) {
            Log.e("KeyguardSecAbsKeyInputViewController", "verifyPasswordAndUnlock! already verified but haven't been dismissed. don't do it again.");
            return;
        }
        com.android.systemui.keyguard.Log.m138d("KeyguardSecAbsKeyInputViewController", "verifyPasswordAndUnlock");
        if (MdfUtils.isMdfDisabled()) {
            Toast.makeText(getContext(), "User authentication is blocked by CC mode since it detects the device has been tampered", 1).show();
            return;
        }
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor;
        KnoxStateMonitor knoxStateMonitor = this.mKnoxStateMonitor;
        LatencyTracker latencyTracker = this.mLatencyTracker;
        if (knoxStateMonitor != null) {
            KnoxStateMonitorImpl knoxStateMonitorImpl = (KnoxStateMonitorImpl) knoxStateMonitor;
            if (knoxStateMonitorImpl.isDualDarDeviceOwner(KeyguardUpdateMonitor.getCurrentUser()) && !knoxStateMonitorImpl.isDualDarInnerLayerUnlocked(KeyguardUpdateMonitor.getCurrentUser())) {
                Log.d("KeyguardSecAbsKeyInputViewController.DDAR", "Check password for DualDAR on DO");
                final LockscreenCredential enteredCredential = ((KeyguardSecAbsKeyInputView) this.mView).getEnteredCredential();
                ((KeyguardSecAbsKeyInputView) this.mView).setPasswordEntryInputEnabled(false);
                ((KeyguardSecAbsKeyInputView) this.mView).disableTouch();
                AsyncTask asyncTask = this.mPendingLockCheck;
                if (asyncTask != null) {
                    asyncTask.cancel(false);
                }
                final int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                if (enteredCredential.size() > 3) {
                    latencyTracker.onActionStart(3);
                    latencyTracker.onActionStart(4);
                    keyguardUpdateMonitor.setCredentialAttempted();
                    this.mPendingLockCheck = LockPatternChecker.checkCredential(lockPatternUtils, enteredCredential, currentUser, 1, new LockPatternChecker.OnCheckCallbackForDualDarDo() { // from class: com.android.keyguard.KeyguardSecAbsKeyInputViewController.4
                        public final void onCancelled() {
                            KeyguardSecAbsKeyInputViewController.this.mLatencyTracker.onActionEnd(4);
                            enteredCredential.zeroize();
                        }

                        public final void onChecked(boolean z, int i) {
                            KeyguardSecAbsKeyInputViewController.this.mLatencyTracker.onActionEnd(3);
                            StringBuilder sb = new StringBuilder("verifyPasswordAndUnlock - onChecked - matched : ");
                            sb.append(z);
                            sb.append(", timeoutMs : ");
                            RecyclerView$$ExternalSyntheticOutline0.m46m(sb, i, "KeyguardSecAbsKeyInputViewController.DDAR");
                            if (z) {
                                KeyguardSecAbsKeyInputViewController.this.onPasswordChecked(currentUser, 0, true, true);
                                return;
                            }
                            ((KeyguardSecAbsKeyInputView) KeyguardSecAbsKeyInputViewController.this.mView).setPasswordEntryInputEnabled(true);
                            ((KeyguardSecAbsKeyInputView) KeyguardSecAbsKeyInputViewController.this.mView).enableTouch();
                            KeyguardSecAbsKeyInputViewController keyguardSecAbsKeyInputViewController = KeyguardSecAbsKeyInputViewController.this;
                            keyguardSecAbsKeyInputViewController.mPendingLockCheck = null;
                            keyguardSecAbsKeyInputViewController.onPasswordChecked(currentUser, i, false, true);
                            enteredCredential.zeroize();
                        }

                        public final void onInnerLayerUnlockFailed() {
                            Log.d("KeyguardSecAbsKeyInputViewController.DDAR", "verifyPasswordAndUnlock - onInnerLayerUnlockFailed");
                        }

                        public final void onInnerLayerUnlocked() {
                            KeyguardSecAbsKeyInputViewController.this.mLatencyTracker.onActionEnd(4);
                            Log.d("KeyguardSecAbsKeyInputViewController.DDAR", "verifyPasswordAndUnlock - onInnerLayerUnlocked");
                            ((KeyguardSecAbsKeyInputView) KeyguardSecAbsKeyInputViewController.this.mView).setPasswordEntryInputEnabled(true);
                            ((KeyguardSecAbsKeyInputView) KeyguardSecAbsKeyInputViewController.this.mView).enableTouch();
                            KeyguardSecAbsKeyInputViewController.this.mPendingLockCheck = null;
                            enteredCredential.zeroize();
                        }
                    });
                    return;
                }
                Log.e("!@KeyguardAbsKeyInputView", "Password too short!");
                ((KeyguardSecAbsKeyInputView) this.mView).setPasswordEntryInputEnabled(true);
                ((KeyguardSecAbsKeyInputView) this.mView).enableTouch();
                onPasswordChecked(currentUser, 0, false, false);
                enteredCredential.zeroize();
                return;
            }
        }
        if (this.mDismissing || this.mLockedOut) {
            return;
        }
        final LockscreenCredential enteredCredential2 = ((KeyguardAbsKeyInputView) this.mView).getEnteredCredential();
        boolean isForgotPasswordView = keyguardUpdateMonitor.isForgotPasswordView();
        if (isForgotPasswordView) {
            this.mPrevCredential = enteredCredential2;
        }
        ((KeyguardAbsKeyInputView) this.mView).setPasswordEntryInputEnabled(false);
        AsyncTask asyncTask2 = this.mPendingLockCheck;
        if (asyncTask2 != null) {
            asyncTask2.cancel(false);
        }
        final int currentUser2 = isForgotPasswordView ? -9998 : KeyguardUpdateMonitor.getCurrentUser();
        if (LsRune.SECURITY_UNPACK) {
            Log.i("KeyguardAbsKeyInputViewController", "just for UNPACK device. Always match success");
        } else if (enteredCredential2.size() <= 3) {
            ((KeyguardAbsKeyInputView) this.mView).setPasswordEntryInputEnabled(true);
            onPasswordChecked(currentUser2, 0, false, false);
            enteredCredential2.zeroize();
            return;
        }
        latencyTracker.onActionStart(3);
        latencyTracker.onActionStart(4);
        keyguardUpdateMonitor.setCredentialAttempted();
        this.mPendingLockCheck = LockPatternChecker.checkCredential(lockPatternUtils, isForgotPasswordView ? this.mPrevCredential : enteredCredential2, currentUser2, new LockPatternChecker.OnCheckCallback() { // from class: com.android.keyguard.KeyguardAbsKeyInputViewController.3
            public final /* synthetic */ LockscreenCredential val$password;
            public final /* synthetic */ int val$userId;

            public C06563(final int currentUser22, final LockscreenCredential enteredCredential22) {
                r2 = currentUser22;
                r3 = enteredCredential22;
            }

            public final void onCancelled() {
                KeyguardAbsKeyInputViewController.this.mLatencyTracker.onActionEnd(4);
                r3.zeroize();
            }

            public final void onChecked(boolean z, int i) {
                KeyguardAbsKeyInputViewController.this.mLatencyTracker.onActionEnd(4);
                ((KeyguardAbsKeyInputView) KeyguardAbsKeyInputViewController.this.mView).setPasswordEntryInputEnabled(true);
                KeyguardAbsKeyInputViewController keyguardAbsKeyInputViewController = KeyguardAbsKeyInputViewController.this;
                keyguardAbsKeyInputViewController.mPendingLockCheck = null;
                if (!z) {
                    keyguardAbsKeyInputViewController.onPasswordChecked(r2, i, false, true);
                }
                r3.zeroize();
            }

            public final void onEarlyMatched() {
                KeyguardAbsKeyInputViewController.this.mLatencyTracker.onActionEnd(3);
                KeyguardAbsKeyInputViewController.this.onPasswordChecked(r2, 0, true, true);
                r3.zeroize();
            }
        });
    }

    public final void updateLayout(int i) {
        Space space;
        int dimensionPixelSize;
        int securityViewId = getSecurityViewId();
        boolean isTablet = DeviceType.isTablet();
        ViewGroup viewGroup = this.mPasswordEntryBoxLayout;
        LinearLayout linearLayout = this.mInputContainer;
        LinearLayout linearLayout2 = this.mMessageArea;
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mSubMessageAreaController;
        LinearLayout linearLayout3 = this.mBottomView;
        if (isTablet) {
            int securityViewId2 = getSecurityViewId();
            boolean isPINSecurityView = isPINSecurityView(securityViewId2);
            boolean isPasswordView = isPasswordView(securityViewId2);
            Resources resources = getResources();
            if (linearLayout3 != null) {
                ViewGroup.LayoutParams layoutParams = linearLayout3.getLayoutParams();
                layoutParams.width = resources.getDimensionPixelSize(R.dimen.kg_message_area_width_tablet);
                linearLayout3.setLayoutParams(layoutParams);
                linearLayout3.setGravity(80);
                linearLayout3.setOrientation(1);
            }
            if (linearLayout2 != null) {
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
                layoutParams2.width = -1;
                layoutParams2.height = -2;
                linearLayout2.setLayoutParams(layoutParams2);
            }
            LinearLayout linearLayout4 = this.mMessageContainer;
            if (linearLayout4 != null && isPINSecurityView) {
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) linearLayout4.getLayoutParams();
                marginLayoutParams.bottomMargin = resources.getDimensionPixelSize(R.dimen.kg_security_pin_input_box_margin_bottom_tablet);
                linearLayout4.setLayoutParams(marginLayoutParams);
            }
            if (keyguardSecMessageAreaController != null) {
                keyguardSecMessageAreaController.setVisibility(isHiddenPasswordSubMessageVisibility() ? 8 : 0);
            }
            if (linearLayout != null) {
                linearLayout.setVisibility(0);
            }
            LinearLayout linearLayout5 = this.mContainer;
            if (linearLayout5 != null) {
                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) linearLayout5.getLayoutParams();
                if (isPINSecurityView) {
                    layoutParams3.bottomMargin = resources.getDimensionPixelSize(R.dimen.kg_pin_container_margin_bottom_tablet);
                } else if (isPasswordView) {
                    layoutParams3.topMargin = resources.getDimensionPixelSize(R.dimen.kg_security_input_box_margin_top_tablet);
                    layoutParams3.bottomMargin = 0;
                }
                this.mContainer.setLayoutParams(layoutParams3);
            }
            if (viewGroup != null && isPasswordView) {
                LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) viewGroup.getLayoutParams();
                layoutParams4.width = resources.getDimensionPixelSize(R.dimen.kg_security_input_box_width_tablet);
                layoutParams4.height = resources.getDimensionPixelSize(R.dimen.kg_security_input_box_height_tablet);
                layoutParams4.bottomMargin = resources.getDimensionPixelSize(R.dimen.kg_security_password_input_box_margin_bottom_tablet);
                viewGroup.setLayoutParams(layoutParams4);
            }
            View view = this.mEcaView;
            if (view != null) {
                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                int i2 = marginLayoutParams2.leftMargin;
                int i3 = marginLayoutParams2.topMargin;
                int i4 = marginLayoutParams2.rightMargin;
                if (isPasswordView) {
                    dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.kg_password_eca_margin_bottom_tablet);
                } else {
                    dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.kg_pin_eca_margin_bottom_tablet);
                }
                marginLayoutParams2.setMargins(i2, i3, i4, dimensionPixelSize);
                this.mEcaView.setLayoutParams(marginLayoutParams2);
                EmergencyButton emergencyButton = (EmergencyButton) this.mEcaView.findViewById(R.id.emergency_call_button);
                if (emergencyButton != null) {
                    ViewGroup.LayoutParams layoutParams5 = emergencyButton.getLayoutParams();
                    layoutParams5.height = resources.getDimensionPixelSize(R.dimen.keyguard_bottom_area_emergency_button_area_min_height_tablet);
                    emergencyButton.setLayoutParams(layoutParams5);
                    return;
                }
                return;
            }
            return;
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor;
        if (keyguardUpdateMonitor.isDualDisplayPolicyAllowed() && !keyguardUpdateMonitor.isRearSelfie()) {
            boolean isAllowedToAdjustSecurityView = isAllowedToAdjustSecurityView();
            updatePortraitLayout(securityViewId);
            if (linearLayout3 != null && (linearLayout3.getLayoutParams() instanceof FrameLayout.LayoutParams)) {
                FrameLayout.LayoutParams layoutParams6 = (FrameLayout.LayoutParams) linearLayout3.getLayoutParams();
                layoutParams6.width = SecurityUtils.getMainSecurityViewFlipperSize(getContext(), isPasswordView(getSecurityViewId()));
                linearLayout3.setLayoutParams(layoutParams6);
            }
            if (keyguardSecMessageAreaController != null) {
                if (!isBiometricLockoutLandscape() && (this.mIsFaceRunning || !isAllowedToAdjustSecurityView || !isLandscapeDisplay())) {
                    keyguardSecMessageAreaController.setVisibility(0);
                } else {
                    keyguardSecMessageAreaController.setVisibility(8);
                }
            }
        } else if (i != 0 && i != 2) {
            Resources resources2 = getResources();
            Rect bounds = resources2.getConfiguration().windowConfiguration.getBounds();
            int calculateLandscapeViewWidth = SecurityUtils.calculateLandscapeViewWidth(Math.max(bounds.width(), bounds.height()), getContext());
            int dimensionPixelSize2 = resources2.getDimensionPixelSize(R.dimen.kg_message_area_padding_side);
            boolean isPasswordView2 = isPasswordView(getSecurityViewId());
            if (linearLayout3 != null) {
                linearLayout3.setGravity(8388691);
                linearLayout3.setOrientation(0);
                if (linearLayout3.getLayoutParams() instanceof FrameLayout.LayoutParams) {
                    FrameLayout.LayoutParams layoutParams7 = (FrameLayout.LayoutParams) linearLayout3.getLayoutParams();
                    layoutParams7.width = -1;
                    linearLayout3.setLayoutParams(layoutParams7);
                }
            }
            LinearLayout linearLayout6 = this.mSplitTouchView;
            if (linearLayout6 != null) {
                LinearLayout.LayoutParams layoutParams8 = (LinearLayout.LayoutParams) linearLayout6.getLayoutParams();
                layoutParams8.width = 0;
                layoutParams8.height = 0;
                layoutParams8.weight = 0.0f;
                linearLayout6.setLayoutParams(layoutParams8);
            }
            KeyguardHintTextArea keyguardHintTextArea = this.mHintText;
            if (keyguardHintTextArea != null) {
                keyguardHintTextArea.setVisibility(8);
            }
            if (linearLayout2 != null && linearLayout != null) {
                LinearLayout.LayoutParams layoutParams9 = (LinearLayout.LayoutParams) linearLayout2.getLayoutParams();
                LinearLayout.LayoutParams layoutParams10 = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
                layoutParams9.width = calculateLandscapeViewWidth;
                layoutParams9.height = -1;
                layoutParams9.bottomMargin = 0;
                linearLayout2.setLayoutParams(layoutParams9);
                layoutParams10.width = calculateLandscapeViewWidth;
                layoutParams10.height = -1;
                layoutParams10.topMargin = 0;
                linearLayout.setLayoutParams(layoutParams10);
                linearLayout.setVisibility(0);
                if (isPasswordView2) {
                    setLandscapeLayoutPadding(linearLayout2, true);
                    setLandscapeLayoutPadding(linearLayout, false);
                    linearLayout.setGravity(17);
                } else {
                    linearLayout2.setPadding(0, dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize2);
                }
                LinearLayout linearLayout7 = this.mContainer;
                if (linearLayout7 != null) {
                    LinearLayout.LayoutParams layoutParams11 = (LinearLayout.LayoutParams) linearLayout7.getLayoutParams();
                    layoutParams11.width = -2;
                    layoutParams11.height = -2;
                    if (isPasswordView2) {
                        layoutParams11.leftMargin = 0;
                        layoutParams11.rightMargin = 0;
                        layoutParams11.topMargin = 0;
                        layoutParams11.bottomMargin = resources2.getDimensionPixelSize(R.dimen.kg_password_container_margin_bottom);
                        this.mContainer.setGravity(17);
                    } else if (LsRune.SECURITY_SUB_DISPLAY_LOCK && !((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).mIsFolderOpened) {
                        layoutParams11.bottomMargin = resources2.getDimensionPixelSize(R.dimen.kg_fold_sub_pin_container_margin_bottom);
                    } else {
                        layoutParams11.bottomMargin = resources2.getDimensionPixelSize(R.dimen.kg_pin_container_margin_bottom);
                    }
                    this.mContainer.setLayoutParams(layoutParams11);
                }
            }
            if (viewGroup != null && isPINSecurityView(securityViewId)) {
                ViewGroup.MarginLayoutParams marginLayoutParams3 = (ViewGroup.MarginLayoutParams) viewGroup.getLayoutParams();
                marginLayoutParams3.setMargins(marginLayoutParams3.leftMargin, resources2.getDimensionPixelSize(R.dimen.kg_message_area_font_size) + resources2.getDimensionPixelSize(R.dimen.kg_security_input_box_margin_top), marginLayoutParams3.rightMargin, marginLayoutParams3.bottomMargin);
                viewGroup.setLayoutParams(marginLayoutParams3);
            }
            View view2 = this.mEcaView;
            if (view2 != null) {
                ViewGroup.MarginLayoutParams marginLayoutParams4 = (ViewGroup.MarginLayoutParams) view2.getLayoutParams();
                marginLayoutParams4.setMargins(0, 0, 0, 0);
                this.mEcaView.setLayoutParams(marginLayoutParams4);
                this.mEcaView.setVisibility(0);
                this.mEmergencyButtonController.setEmergencyView(this.mEcaView.findViewById(R.id.emergency_call_button));
                if (isPasswordView2 && (space = this.mDummyEcaSpace) != null) {
                    ViewGroup.MarginLayoutParams marginLayoutParams5 = (ViewGroup.MarginLayoutParams) space.getLayoutParams();
                    marginLayoutParams5.setMargins(0, 0, 0, resources2.getDimensionPixelSize(R.dimen.kg_password_container_margin_bottom));
                    space.setLayoutParams(marginLayoutParams5);
                    space.setVisibility(0);
                }
            }
            LinearLayout linearLayout8 = this.mEcaFlexContainer;
            if (linearLayout8 != null) {
                linearLayout8.setVisibility(8);
            }
            if (keyguardSecMessageAreaController != null) {
                keyguardSecMessageAreaController.setVisibility(8);
            }
            handleLandscapePINSecurityMessage(keyguardUpdateMonitor.getLockoutBiometricAttemptDeadline());
            updatePrevInfoTextSize();
        } else {
            updatePortraitLayout(securityViewId);
        }
        updateLayoutForAttemptRemainingBeforeWipe();
    }

    public void displayDefaultSecurityMessage() {
    }

    public void initializeBottomContainerView() {
    }
}
