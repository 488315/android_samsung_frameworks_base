package com.android.keyguard;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.animator.KeyguardTouchSwipeDetector;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.vibrate.VibrationUtil;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUITextView;
import com.android.systemui.widget.SystemUIWidgetCallback;
import com.android.systemui.widget.SystemUIWidgetUtil;
import com.sec.ims.configuration.DATA;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class KeyguardInputViewController extends ViewController implements KeyguardSecurityView, SystemUIWidgetCallback {
    public final EmergencyButtonController mEmergencyButtonController;
    public final FeatureFlags mFeatureFlags;
    public final SystemUITextView mForgotPasswordText;
    public final KeyguardSecurityCallback mKeyguardSecurityCallback;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardSecMessageAreaController mMessageAreaController;
    public final C06881 mNullCallback;
    public boolean mPaused;
    public final SystemUITextView mPrevInfoSubText;
    public final SystemUITextView mPrevInfoText;
    public final LinearLayout mPrevInfoTextContainer;
    public final KeyguardSecurityModel.SecurityMode mSecurityMode;
    public final KeyguardSecMessageAreaController mSubMessageAreaController;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Factory {
        public final AccessibilityManager mAccessibilityManager;
        public final ConfigurationController mConfigurationController;
        public final DevicePostureController mDevicePostureController;
        public final EmergencyButtonController.Factory mEmergencyButtonControllerFactory;
        public final FalsingCollector mFalsingCollector;
        public final FeatureFlags mFeatureFlags;
        public final InputMethodManager mInputMethodManager;
        public final KeyguardTouchSwipeDetector mKeyguardTouchSwipeDetector;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final KeyguardViewController mKeyguardViewController;
        public final LatencyTracker mLatencyTracker;
        public final LiftToActivateListener mLiftToActivateListener;
        public final LockPatternUtils mLockPatternUtils;
        public final DelayableExecutor mMainExecutor;
        public final KeyguardMessageAreaController.Factory mMessageAreaControllerFactory;
        public final NetworkController mNetworkController;
        public final Resources mResources;
        public final SecRotationWatcher mRotationWatcher;
        public final ScreenLifecycle mScreenLifecycle;
        public final TelephonyManager mTelephonyManager;
        public final VibrationUtil mVibrationUtil;
        public final WifiManager mWifiManager;

        public Factory(KeyguardUpdateMonitor keyguardUpdateMonitor, SecRotationWatcher secRotationWatcher, ConfigurationController configurationController, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, KeyguardTouchSwipeDetector keyguardTouchSwipeDetector, WifiManager wifiManager, NetworkController networkController, ScreenLifecycle screenLifecycle, LockPatternUtils lockPatternUtils, LatencyTracker latencyTracker, KeyguardMessageAreaController.Factory factory, InputMethodManager inputMethodManager, DelayableExecutor delayableExecutor, Resources resources, LiftToActivateListener liftToActivateListener, TelephonyManager telephonyManager, FalsingCollector falsingCollector, EmergencyButtonController.Factory factory2, DevicePostureController devicePostureController, KeyguardViewController keyguardViewController, FeatureFlags featureFlags) {
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mRotationWatcher = secRotationWatcher;
            this.mConfigurationController = configurationController;
            this.mVibrationUtil = vibrationUtil;
            this.mAccessibilityManager = accessibilityManager;
            this.mKeyguardTouchSwipeDetector = keyguardTouchSwipeDetector;
            this.mWifiManager = wifiManager;
            this.mNetworkController = networkController;
            this.mScreenLifecycle = screenLifecycle;
            this.mLockPatternUtils = lockPatternUtils;
            this.mLatencyTracker = latencyTracker;
            this.mMessageAreaControllerFactory = factory;
            this.mInputMethodManager = inputMethodManager;
            this.mMainExecutor = delayableExecutor;
            this.mResources = resources;
            this.mLiftToActivateListener = liftToActivateListener;
            this.mTelephonyManager = telephonyManager;
            this.mEmergencyButtonControllerFactory = factory2;
            this.mFalsingCollector = falsingCollector;
            this.mDevicePostureController = devicePostureController;
            this.mKeyguardViewController = keyguardViewController;
            this.mFeatureFlags = featureFlags;
        }

        public final KeyguardInputViewController create(KeyguardInputView keyguardInputView, KeyguardSecurityModel.SecurityMode securityMode, KeyguardSecurityCallback keyguardSecurityCallback) {
            EmergencyButtonController create = this.mEmergencyButtonControllerFactory.create((EmergencyButton) keyguardInputView.findViewById(R.id.emergency_call_button));
            if (keyguardInputView instanceof KeyguardSecPatternView) {
                return new KeyguardSecPatternViewController((KeyguardSecPatternView) keyguardInputView, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mLatencyTracker, this.mFalsingCollector, create, this.mMessageAreaControllerFactory, this.mDevicePostureController, this.mFeatureFlags, this.mConfigurationController, this.mRotationWatcher, this.mVibrationUtil);
            }
            if (keyguardInputView instanceof KeyguardSecPasswordView) {
                return keyguardInputView instanceof KeyguardKnoxDualDarInnerPasswordView ? new KeyguardKnoxDualDarInnerPasswordViewController((KeyguardKnoxDualDarInnerPasswordView) keyguardInputView, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, this.mInputMethodManager, create, this.mMainExecutor, this.mResources, this.mFalsingCollector, this.mKeyguardViewController, this.mFeatureFlags, this.mRotationWatcher, this.mVibrationUtil, this.mAccessibilityManager) : new KeyguardSecPasswordViewController((KeyguardSecPasswordView) keyguardInputView, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, this.mInputMethodManager, create, this.mMainExecutor, this.mResources, this.mFalsingCollector, this.mKeyguardViewController, this.mFeatureFlags, this.mRotationWatcher, this.mVibrationUtil, this.mAccessibilityManager);
            }
            if (keyguardInputView instanceof KeyguardSecPINView) {
                return keyguardInputView instanceof KeyguardKnoxDualDarInnerPinView ? new KeyguardKnoxDualDarInnerPinViewController((KeyguardKnoxDualDarInnerPinView) keyguardInputView, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, this.mLiftToActivateListener, create, this.mFalsingCollector, this.mDevicePostureController, this.mFeatureFlags, this.mRotationWatcher, this.mVibrationUtil, this.mAccessibilityManager, this.mConfigurationController) : new KeyguardSecPinViewController((KeyguardSecPINView) keyguardInputView, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, this.mLiftToActivateListener, create, this.mFalsingCollector, this.mDevicePostureController, this.mFeatureFlags, this.mRotationWatcher, this.mVibrationUtil, this.mAccessibilityManager, this.mConfigurationController);
            }
            if (keyguardInputView instanceof KeyguardSecSimPinView) {
                return new KeyguardSecSimPinViewController((KeyguardSecSimPinView) keyguardInputView, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, this.mLiftToActivateListener, this.mTelephonyManager, this.mFalsingCollector, create, this.mFeatureFlags, this.mRotationWatcher, this.mVibrationUtil, this.mAccessibilityManager, this.mConfigurationController, this.mInputMethodManager);
            }
            boolean z = LsRune.SECURITY_NOT_REQUIRE_SIMPUK_CODE;
            if (!z ? !(keyguardInputView instanceof KeyguardSecSimPukView) : !(keyguardInputView instanceof KeyguardSimPukTMOView)) {
                return z ? new KeyguardSimPukTMOViewController((KeyguardSimPukTMOView) keyguardInputView, this.mKeyguardUpdateMonitor, securityMode, keyguardSecurityCallback, this.mMessageAreaControllerFactory, create, this.mFeatureFlags) : new KeyguardSecSimPukViewController((KeyguardSecSimPukView) keyguardInputView, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, this.mLiftToActivateListener, this.mTelephonyManager, this.mFalsingCollector, create, this.mFeatureFlags, this.mRotationWatcher, this.mVibrationUtil, this.mAccessibilityManager, this.mConfigurationController, this.mInputMethodManager);
            }
            if (LsRune.SECURITY_SIM_PERSO_LOCK && (keyguardInputView instanceof KeyguardSimPersoView)) {
                return new KeyguardSimPersoViewController((KeyguardSimPersoView) keyguardInputView, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, this.mLiftToActivateListener, create, this.mFalsingCollector, this.mFeatureFlags, this.mRotationWatcher, this.mVibrationUtil, this.mAccessibilityManager, this.mConfigurationController);
            }
            if (keyguardInputView instanceof KeyguardPermanentView) {
                return new KeyguardPermanentViewController((KeyguardPermanentView) keyguardInputView, securityMode, keyguardSecurityCallback, create, this.mMessageAreaControllerFactory, this.mFeatureFlags);
            }
            if (LsRune.SECURITY_SWIPE_BOUNCER && (keyguardInputView instanceof KeyguardSwipeView)) {
                return new KeyguardSwipeViewController((KeyguardSwipeView) keyguardInputView, securityMode, keyguardSecurityCallback, create, this.mAccessibilityManager, this.mConfigurationController, this.mKeyguardTouchSwipeDetector, this.mMessageAreaControllerFactory, this.mFeatureFlags);
            }
            if (keyguardInputView instanceof KeyguardAdminView) {
                return new KeyguardAdminViewController((KeyguardAdminView) keyguardInputView, securityMode, keyguardSecurityCallback, create, this.mMessageAreaControllerFactory, this.mFeatureFlags);
            }
            if (keyguardInputView instanceof KeyguardFMMView) {
                return new KeyguardFMMViewController((KeyguardFMMView) keyguardInputView, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, this.mLiftToActivateListener, create, this.mFalsingCollector, this.mFeatureFlags, this.mRotationWatcher, this.mVibrationUtil, this.mAccessibilityManager, this.mConfigurationController);
            }
            if (keyguardInputView instanceof KeyguardRMMView) {
                return new KeyguardRMMViewController((KeyguardRMMView) keyguardInputView, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, this.mLiftToActivateListener, create, this.mFalsingCollector, this.mFeatureFlags, this.mRotationWatcher, this.mVibrationUtil, this.mAccessibilityManager, this.mConfigurationController);
            }
            if (keyguardInputView instanceof KeyguardKnoxGuardView) {
                return new KeyguardKnoxGuardViewController((KeyguardKnoxGuardView) keyguardInputView, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, this.mFalsingCollector, create, this.mFeatureFlags, this.mRotationWatcher, this.mVibrationUtil, this.mAccessibilityManager, this.mInputMethodManager, this.mTelephonyManager, this.mWifiManager, this.mNetworkController, this.mScreenLifecycle);
            }
            if (keyguardInputView instanceof KeyguardCarrierView) {
                return new KeyguardCarrierViewController((KeyguardCarrierView) keyguardInputView, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, create, this.mTelephonyManager, this.mMessageAreaControllerFactory, this.mFeatureFlags);
            }
            if (keyguardInputView instanceof KeyguardCarrierPasswordView) {
                return new KeyguardCarrierPasswordViewController((KeyguardCarrierPasswordView) keyguardInputView, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, this.mFalsingCollector, create, this.mFeatureFlags, this.mRotationWatcher, this.mVibrationUtil, this.mAccessibilityManager, this.mConfigurationController, this.mInputMethodManager);
            }
            if (keyguardInputView instanceof KeyguardUCMView) {
                return new KeyguardUCMViewController((KeyguardUCMView) keyguardInputView, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, this.mLiftToActivateListener, create, this.mFalsingCollector, this.mFeatureFlags, this.mRotationWatcher, this.mVibrationUtil, this.mAccessibilityManager, this.mConfigurationController);
            }
            throw new RuntimeException("Unable to find controller for " + keyguardInputView);
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.keyguard.KeyguardInputViewController$1] */
    public KeyguardInputViewController(KeyguardInputView keyguardInputView, KeyguardSecurityModel.SecurityMode securityMode, KeyguardSecurityCallback keyguardSecurityCallback, EmergencyButtonController emergencyButtonController, KeyguardMessageAreaController.Factory factory, FeatureFlags featureFlags) {
        super(keyguardInputView);
        this.mNullCallback = new KeyguardSecurityCallback(this) { // from class: com.android.keyguard.KeyguardInputViewController.1
        };
        this.mSecurityMode = securityMode;
        this.mKeyguardSecurityCallback = keyguardSecurityCallback;
        if (keyguardInputView != null) {
        }
        this.mEmergencyButtonController = emergencyButtonController;
        this.mFeatureFlags = featureFlags;
        if (factory != null) {
            ConfigurationController configurationController = factory.mConfigurationController;
            KeyguardUpdateMonitor keyguardUpdateMonitor = factory.mKeyguardUpdateMonitor;
            if (keyguardInputView != null) {
                try {
                    KeyguardSecMessageAreaController keyguardSecMessageAreaController = new KeyguardSecMessageAreaController((BouncerKeyguardMessageArea) keyguardInputView.requireViewById(R.id.sec_bouncer_message_area), keyguardUpdateMonitor, configurationController);
                    this.mMessageAreaController = keyguardSecMessageAreaController;
                    keyguardSecMessageAreaController.init();
                    keyguardSecMessageAreaController.setIsVisible(true);
                    KeyguardSecMessageAreaController keyguardSecMessageAreaController2 = new KeyguardSecMessageAreaController((BouncerKeyguardMessageArea) keyguardInputView.requireViewById(R.id.keyguard_sub_help_text), keyguardUpdateMonitor, configurationController);
                    this.mSubMessageAreaController = keyguardSecMessageAreaController2;
                    keyguardSecMessageAreaController2.init();
                    keyguardSecMessageAreaController2.setIsVisible(true);
                } catch (IllegalArgumentException unused) {
                    Log.e("KeyguardInputViewController", "Ensure that a BouncerKeyguardMessageArea is included in the layout");
                }
            }
        }
        this.mKeyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.get(KeyguardUpdateMonitor.class);
        this.mForgotPasswordText = keyguardInputView == null ? null : (SystemUITextView) keyguardInputView.findViewById(R.id.forgot_password_text);
        this.mPrevInfoTextContainer = keyguardInputView == null ? null : (LinearLayout) keyguardInputView.findViewById(R.id.prev_info_message);
        this.mPrevInfoText = keyguardInputView == null ? null : (SystemUITextView) keyguardInputView.findViewById(R.id.prev_info_text);
        this.mPrevInfoSubText = keyguardInputView != null ? (SystemUITextView) keyguardInputView.findViewById(R.id.prev_sub_info_text) : null;
    }

    public abstract int getInitialMessageResId();

    public final KeyguardSecurityCallback getKeyguardSecurityCallback() {
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (this.mSecurityMode != keyguardUpdateMonitor.getCurrentSecurityMode()) {
            boolean isDualDarInnerAuthShowing = keyguardUpdateMonitor.isDualDarInnerAuthShowing();
            C06881 c06881 = this.mNullCallback;
            if (!isDualDarInnerAuthShowing) {
                Log.d("KeyguardInputViewController", "getKeyguardSecurityCallback() returns NULL callback");
                return c06881;
            }
            if (this.mPaused) {
                Log.d("KeyguardInputViewController", "isDualDarInnerAuthShowing() return true and getKeyguardSecurityCallback() returns NULL callback");
                return c06881;
            }
        }
        return this.mKeyguardSecurityCallback;
    }

    @Override // com.android.systemui.util.ViewController
    public void onInit() {
        this.mEmergencyButtonController.init();
    }

    public void onPause() {
        this.mPaused = true;
    }

    public void onResume(int i) {
        this.mPaused = false;
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewAttached() {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (keyguardSecMessageAreaController != null) {
            Flags flags = Flags.INSTANCE;
            this.mFeatureFlags.getClass();
            keyguardSecMessageAreaController.setIsVisible(true);
        }
        if (LsRune.SECURITY_OPEN_THEME) {
            WallpaperUtils.registerSystemUIWidgetCallback(this, SystemUIWidgetUtil.convertFlag("background") | 1024);
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        SystemUITextView systemUITextView = this.mForgotPasswordText;
        if (systemUITextView != null) {
            int credentialTypeForUser = keyguardUpdateMonitor.getCredentialTypeForUser(KeyguardUpdateMonitor.getCurrentUser());
            SpannableString spannableString = new SpannableString(credentialTypeForUser != 1 ? credentialTypeForUser != 3 ? getResources().getString(R.string.kg_forgot_password) : getResources().getString(R.string.kg_forgot_pin) : getResources().getString(R.string.kg_forgot_pattern));
            spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
            systemUITextView.setMaxFontScale(1.1f);
            systemUITextView.setText(spannableString);
            systemUITextView.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.KeyguardInputViewController$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    KeyguardInputViewController keyguardInputViewController = KeyguardInputViewController.this;
                    keyguardInputViewController.getClass();
                    SystemUIAnalytics.sendEventLog(DATA.DM_FIELD_INDEX.VOLTE_DOMAIN_UI_SHOW, "2039");
                    keyguardInputViewController.getKeyguardSecurityCallback().showBackupSecurity(KeyguardSecurityModel.SecurityMode.ForgotPassword);
                }
            });
        }
        SystemUITextView systemUITextView2 = this.mPrevInfoSubText;
        if (systemUITextView2 != null) {
            int credentialTypeForUser2 = keyguardUpdateMonitor.getCredentialTypeForUser(KeyguardUpdateMonitor.getCurrentUser());
            systemUITextView2.setText(credentialTypeForUser2 != 1 ? credentialTypeForUser2 != 3 ? getResources().getString(R.string.kg_prev_sub_info_message_for_password) : getResources().getString(R.string.kg_prev_sub_info_message_for_pin) : getResources().getString(R.string.kg_prev_sub_info_message_for_pattern));
        }
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
        if (LsRune.SECURITY_OPEN_THEME) {
            WallpaperUtils.removeSystemUIWidgetCallback(this);
        }
    }

    public final void setMessageTimeout(boolean z) {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController;
        KeyguardSecMessageAreaController keyguardSecMessageAreaController2 = this.mMessageAreaController;
        if (keyguardSecMessageAreaController2 == null || (keyguardSecMessageAreaController = this.mSubMessageAreaController) == null) {
            return;
        }
        long j = z ? 0 : 3000;
        ((BouncerKeyguardMessageArea) keyguardSecMessageAreaController2.mView).mTimeout = j;
        ((BouncerKeyguardMessageArea) keyguardSecMessageAreaController.mView).mTimeout = j;
    }

    public boolean shouldLockout(long j) {
        return (j == 0 || this.mKeyguardUpdateMonitor.isForgotPasswordView()) ? false : true;
    }

    public void startAppearAnimation() {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (TextUtils.isEmpty(((KeyguardMessageArea) keyguardSecMessageAreaController.mView).getText()) && getInitialMessageResId() != 0) {
            keyguardSecMessageAreaController.setMessage(((KeyguardInputView) this.mView).getResources().getString(getInitialMessageResId()), false);
        }
        ((KeyguardInputView) this.mView).startAppearAnimation();
    }

    public boolean startDisappearAnimation(Runnable runnable) {
        return ((KeyguardInputView) this.mView).startDisappearAnimation(runnable);
    }

    public final void updateForgotPasswordTextVisibility() {
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        SystemUITextView systemUITextView = this.mForgotPasswordText;
        if (systemUITextView == null || currentUser != 0) {
            return;
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (keyguardUpdateMonitor.isDualDarInnerAuthRequired(currentUser)) {
            return;
        }
        systemUITextView.setVisibility((keyguardUpdateMonitor.isForgotPasswordView() || keyguardUpdateMonitor.getFailedUnlockAttempts(KeyguardUpdateMonitor.getCurrentUser()) < 5 || !keyguardUpdateMonitor.checkValidPrevCredentialType() || keyguardUpdateMonitor.mKeyguardOccluded || !(DeviceType.isWeaverDevice() || keyguardUpdateMonitor.getLockoutAttemptDeadline() == 0)) ? 8 : 0);
        LinearLayout linearLayout = this.mPrevInfoTextContainer;
        if (linearLayout != null) {
            linearLayout.setVisibility(keyguardUpdateMonitor.isForgotPasswordView() ? 0 : 8);
        }
    }

    public final void updatePrevInfoTextSize() {
        if (this.mKeyguardUpdateMonitor.isForgotPasswordView()) {
            SystemUITextView systemUITextView = this.mPrevInfoText;
            if (systemUITextView != null) {
                systemUITextView.setMaxFontScale(1.1f);
                systemUITextView.setTextSize(0, getResources().getDimensionPixelSize(R.dimen.kg_prev_help_text_font_size));
            }
            SystemUITextView systemUITextView2 = this.mPrevInfoSubText;
            if (systemUITextView2 != null) {
                systemUITextView2.setMaxFontScale(1.1f);
                systemUITextView2.setTextSize(0, getResources().getDimensionPixelSize(R.dimen.kg_prev_sub_help_text_font_size));
            }
        }
    }

    public void showPromptReason(int i) {
    }

    public void reset() {
    }

    public void showMessage(CharSequence charSequence, ColorStateList colorStateList, boolean z) {
    }
}
