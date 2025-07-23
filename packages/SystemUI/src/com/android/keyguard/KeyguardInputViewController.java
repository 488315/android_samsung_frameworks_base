package com.android.keyguard;

import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.input.InputManager;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.EmergencyButtonController;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.domain.interactor.KeyguardKeyboardInteractor;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.bouncer.domain.interactor.BouncerMessageInteractor;
import com.android.systemui.bouncer.ui.BouncerMessageView;
import com.android.systemui.bouncer.ui.binder.BouncerMessageViewBinder;
import com.android.systemui.bouncer.ui.helper.BouncerHapticPlayer;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.ScreenLifecycle;
import com.android.systemui.keyguard.animator.KeyguardTouchSwipeDetector;
import com.android.systemui.log.BouncerLogger;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.vibrate.VibrationUtil;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUITextView;
import com.android.systemui.widget.SystemUIWidgetCallback;
import com.samsung.android.widget.SemTipPopup;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class KeyguardInputViewController extends ViewController implements KeyguardSecurityView, SystemUIWidgetCallback {
    public final BouncerHapticPlayer mBouncerHapticPlayer;
    public final EmergencyButtonController mEmergencyButtonController;
    public final SystemUITextView mForgotPasswordText;
    public final KeyguardSecurityCallback mKeyguardSecurityCallback;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final KeyguardSecMessageAreaController mMessageAreaController;
    public final AnonymousClass1 mNullCallback;
    public boolean mPaused;
    public final SystemUITextView mPrevInfoSubText;
    public final SystemUITextView mPrevInfoText;
    public final LinearLayout mPrevInfoTextContainer;
    public final KeyguardSecurityModel.SecurityMode mSecurityMode;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final KeyguardInputViewController$$ExternalSyntheticLambda0 mShowTipsRunnable;
    public final KeyguardSecMessageAreaController mSubMessageAreaController;
    public SemTipPopup mTipPopup;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Factory {
        public final AccessibilityManager mAccessibilityManager;
        public final Executor mBackgroundExecutor;
        public final BouncerHapticPlayer mBouncerHapticPlayer;
        public final ConfigurationController mConfigurationController;
        public final DevicePostureController mDevicePostureController;
        public final EmergencyButtonController.Factory mEmergencyButtonControllerFactory;
        public final FalsingCollector mFalsingCollector;
        public final FeatureFlags mFeatureFlags;
        public final InputManager mInputManager;
        public final InputMethodManager mInputMethodManager;
        public final KeyguardKeyboardInteractor mKeyguardKeyboardInteractor;
        public final KeyguardTouchSwipeDetector mKeyguardTouchSwipeDetector;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final KeyguardViewController mKeyguardViewController;
        public final LatencyTracker mLatencyTracker;
        public final LockPatternUtils mLockPatternUtils;
        public final DelayableExecutor mMainExecutor;
        public final KeyguardMessageAreaController.Factory mMessageAreaControllerFactory;
        public final NetworkController mNetworkController;
        public final Resources mResources;
        public final ScreenLifecycle mScreenLifecycle;
        public final SelectedUserInteractor mSelectedUserInteractor;
        public final TelephonyManager mTelephonyManager;
        public final UiEventLogger mUiEventLogger;
        public final UserActivityNotifier mUserActivityNotifier;
        public final VibrationUtil mVibrationUtil;
        public final WifiManager mWifiManager;

        public Factory(KeyguardUpdateMonitor keyguardUpdateMonitor, ConfigurationController configurationController, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, WifiManager wifiManager, NetworkController networkController, ScreenLifecycle screenLifecycle, KeyguardTouchSwipeDetector keyguardTouchSwipeDetector, Executor executor, LockPatternUtils lockPatternUtils, LatencyTracker latencyTracker, KeyguardMessageAreaController.Factory factory, InputMethodManager inputMethodManager, DelayableExecutor delayableExecutor, Resources resources, TelephonyManager telephonyManager, FalsingCollector falsingCollector, EmergencyButtonController.Factory factory2, DevicePostureController devicePostureController, KeyguardViewController keyguardViewController, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor, UiEventLogger uiEventLogger, KeyguardKeyboardInteractor keyguardKeyboardInteractor, BouncerHapticPlayer bouncerHapticPlayer, UserActivityNotifier userActivityNotifier, InputManager inputManager) {
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mConfigurationController = configurationController;
            this.mVibrationUtil = vibrationUtil;
            this.mAccessibilityManager = accessibilityManager;
            this.mWifiManager = wifiManager;
            this.mNetworkController = networkController;
            this.mScreenLifecycle = screenLifecycle;
            this.mKeyguardTouchSwipeDetector = keyguardTouchSwipeDetector;
            this.mBackgroundExecutor = executor;
            this.mLockPatternUtils = lockPatternUtils;
            this.mLatencyTracker = latencyTracker;
            this.mMessageAreaControllerFactory = factory;
            this.mInputMethodManager = inputMethodManager;
            this.mMainExecutor = delayableExecutor;
            this.mResources = resources;
            this.mTelephonyManager = telephonyManager;
            this.mEmergencyButtonControllerFactory = factory2;
            this.mFalsingCollector = falsingCollector;
            this.mDevicePostureController = devicePostureController;
            this.mKeyguardViewController = keyguardViewController;
            this.mFeatureFlags = featureFlags;
            this.mSelectedUserInteractor = selectedUserInteractor;
            this.mUiEventLogger = uiEventLogger;
            this.mKeyguardKeyboardInteractor = keyguardKeyboardInteractor;
            this.mBouncerHapticPlayer = bouncerHapticPlayer;
            this.mUserActivityNotifier = userActivityNotifier;
            this.mInputManager = inputManager;
        }

        public final KeyguardInputViewController create(KeyguardInputView keyguardInputView, KeyguardSecurityModel.SecurityMode securityMode, KeyguardSecurityCallback keyguardSecurityCallback) {
            EmergencyButtonController create = this.mEmergencyButtonControllerFactory.create((EmergencyButton) keyguardInputView.findViewById(R.id.emergency_call_button));
            if (keyguardInputView instanceof KeyguardSecPatternView) {
                return new KeyguardSecPatternViewController((KeyguardSecPatternView) keyguardInputView, this.mBackgroundExecutor, this.mConfigurationController, this.mVibrationUtil, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mLatencyTracker, this.mFalsingCollector, create, this.mMessageAreaControllerFactory, this.mDevicePostureController, this.mFeatureFlags, this.mSelectedUserInteractor, this.mBouncerHapticPlayer);
            }
            if (keyguardInputView instanceof KeyguardSecPasswordView) {
                if (keyguardInputView instanceof KeyguardKnoxDualDarInnerPasswordView) {
                    return new KeyguardKnoxDualDarInnerPasswordViewController((KeyguardKnoxDualDarInnerPasswordView) keyguardInputView, this.mConfigurationController, this.mVibrationUtil, this.mAccessibilityManager, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, this.mInputMethodManager, create, this.mMainExecutor, this.mResources, this.mFalsingCollector, this.mKeyguardViewController, this.mDevicePostureController, this.mFeatureFlags, this.mSelectedUserInteractor, this.mKeyguardKeyboardInteractor, this.mBouncerHapticPlayer, this.mUserActivityNotifier);
                }
                return new KeyguardSecPasswordViewController((KeyguardSecPasswordView) keyguardInputView, this.mConfigurationController, this.mVibrationUtil, this.mAccessibilityManager, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, this.mInputMethodManager, create, this.mMainExecutor, this.mResources, this.mFalsingCollector, this.mKeyguardViewController, this.mDevicePostureController, this.mFeatureFlags, this.mSelectedUserInteractor, this.mKeyguardKeyboardInteractor, this.mBouncerHapticPlayer, this.mUserActivityNotifier);
            }
            if (keyguardInputView instanceof KeyguardSecPINView) {
                if (keyguardInputView instanceof KeyguardKnoxDualDarInnerPinView) {
                    return new KeyguardKnoxDualDarInnerPinViewController((KeyguardKnoxDualDarInnerPinView) keyguardInputView, this.mConfigurationController, this.mVibrationUtil, this.mAccessibilityManager, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, create, this.mFalsingCollector, this.mDevicePostureController, this.mFeatureFlags, this.mSelectedUserInteractor, this.mUiEventLogger, this.mKeyguardKeyboardInteractor, this.mBouncerHapticPlayer, this.mUserActivityNotifier, this.mInputManager);
                }
                return new KeyguardSecPinViewController((KeyguardSecPINView) keyguardInputView, this.mConfigurationController, this.mVibrationUtil, this.mAccessibilityManager, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, create, this.mFalsingCollector, this.mDevicePostureController, this.mFeatureFlags, this.mSelectedUserInteractor, this.mUiEventLogger, this.mKeyguardKeyboardInteractor, this.mBouncerHapticPlayer, this.mUserActivityNotifier, this.mInputManager);
            }
            if (keyguardInputView instanceof KeyguardSecSimPinView) {
                return new KeyguardSecSimPinViewController((KeyguardSecSimPinView) keyguardInputView, this.mConfigurationController, this.mVibrationUtil, this.mAccessibilityManager, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, this.mTelephonyManager, this.mFalsingCollector, create, this.mFeatureFlags, this.mSelectedUserInteractor, this.mKeyguardKeyboardInteractor, this.mInputMethodManager, this.mBouncerHapticPlayer, this.mUserActivityNotifier, this.mInputManager);
            }
            if (keyguardInputView instanceof KeyguardSecSimPukView) {
                return new KeyguardSecSimPukViewController((KeyguardSecSimPukView) keyguardInputView, this.mConfigurationController, this.mVibrationUtil, this.mAccessibilityManager, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, this.mTelephonyManager, this.mFalsingCollector, create, this.mFeatureFlags, this.mSelectedUserInteractor, this.mKeyguardKeyboardInteractor, this.mInputMethodManager, this.mBouncerHapticPlayer, this.mUserActivityNotifier, this.mInputManager);
            }
            if (LsRune.SECURITY_SIM_PERSO_LOCK && (keyguardInputView instanceof KeyguardSimPersoView)) {
                return new KeyguardSimPersoViewController((KeyguardSimPersoView) keyguardInputView, this.mConfigurationController, this.mVibrationUtil, this.mAccessibilityManager, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, create, this.mFalsingCollector, this.mFeatureFlags, this.mSelectedUserInteractor, this.mKeyguardKeyboardInteractor, this.mBouncerHapticPlayer, this.mUserActivityNotifier, this.mInputManager);
            }
            if (keyguardInputView instanceof KeyguardPermanentView) {
                return new KeyguardPermanentViewController((KeyguardPermanentView) keyguardInputView, securityMode, keyguardSecurityCallback, create, this.mMessageAreaControllerFactory, this.mFeatureFlags, this.mSelectedUserInteractor, this.mBouncerHapticPlayer);
            }
            if (LsRune.SECURITY_SWIPE_BOUNCER && (keyguardInputView instanceof KeyguardSwipeView)) {
                return new KeyguardSwipeViewController((KeyguardSwipeView) keyguardInputView, securityMode, keyguardSecurityCallback, create, this.mAccessibilityManager, this.mConfigurationController, this.mKeyguardTouchSwipeDetector, this.mMessageAreaControllerFactory, this.mFeatureFlags, this.mSelectedUserInteractor, this.mBouncerHapticPlayer);
            }
            if (keyguardInputView instanceof KeyguardAdminView) {
                return new KeyguardAdminViewController((KeyguardAdminView) keyguardInputView, securityMode, keyguardSecurityCallback, create, this.mMessageAreaControllerFactory, this.mFeatureFlags, this.mSelectedUserInteractor, this.mBouncerHapticPlayer);
            }
            if (keyguardInputView instanceof KeyguardFMMView) {
                return new KeyguardFMMViewController((KeyguardFMMView) keyguardInputView, this.mConfigurationController, this.mVibrationUtil, this.mAccessibilityManager, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, create, this.mFalsingCollector, this.mFeatureFlags, this.mSelectedUserInteractor, this.mKeyguardKeyboardInteractor, this.mBouncerHapticPlayer, this.mUserActivityNotifier, this.mInputManager);
            }
            if (keyguardInputView instanceof KeyguardKnoxGuardView) {
                return new KeyguardKnoxGuardViewController((KeyguardKnoxGuardView) keyguardInputView, this.mConfigurationController, this.mVibrationUtil, this.mAccessibilityManager, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, this.mFalsingCollector, create, this.mFeatureFlags, this.mSelectedUserInteractor, this.mInputMethodManager, this.mTelephonyManager, this.mWifiManager, this.mNetworkController, this.mScreenLifecycle, this.mBouncerHapticPlayer, this.mUserActivityNotifier);
            }
            if (keyguardInputView instanceof KeyguardCarrierView) {
                return new KeyguardCarrierViewController((KeyguardCarrierView) keyguardInputView, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, create, this.mTelephonyManager, this.mMessageAreaControllerFactory, this.mFeatureFlags, this.mSelectedUserInteractor, this.mBouncerHapticPlayer);
            }
            if (keyguardInputView instanceof KeyguardCarrierPasswordView) {
                return new KeyguardCarrierPasswordViewController((KeyguardCarrierPasswordView) keyguardInputView, this.mConfigurationController, this.mVibrationUtil, this.mAccessibilityManager, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, this.mFalsingCollector, create, this.mFeatureFlags, this.mInputMethodManager, this.mSelectedUserInteractor, this.mBouncerHapticPlayer, this.mUserActivityNotifier);
            }
            if (!(keyguardInputView instanceof KeyguardUCMView)) {
                throw new RuntimeException("Unable to find controller for " + keyguardInputView);
            }
            return new KeyguardUCMViewController((KeyguardUCMView) keyguardInputView, this.mKeyguardUpdateMonitor, securityMode, this.mLockPatternUtils, keyguardSecurityCallback, this.mMessageAreaControllerFactory, this.mLatencyTracker, create, this.mFalsingCollector, this.mFeatureFlags, this.mVibrationUtil, this.mAccessibilityManager, this.mConfigurationController, this.mSelectedUserInteractor, this.mKeyguardKeyboardInteractor, this.mBouncerHapticPlayer, this.mUserActivityNotifier, this.mInputManager);
        }
    }

    /* JADX WARN: Type inference failed for: r6v1, types: [com.android.keyguard.KeyguardInputViewController$1] */
    /* JADX WARN: Type inference failed for: r6v2, types: [com.android.keyguard.KeyguardInputViewController$$ExternalSyntheticLambda0] */
    public KeyguardInputViewController(KeyguardInputView keyguardInputView, KeyguardSecurityModel.SecurityMode securityMode, KeyguardSecurityCallback keyguardSecurityCallback, EmergencyButtonController emergencyButtonController, KeyguardMessageAreaController.Factory factory, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor, BouncerHapticPlayer bouncerHapticPlayer) {
        super(keyguardInputView);
        this.mNullCallback = new KeyguardSecurityCallback(this) { // from class: com.android.keyguard.KeyguardInputViewController.1
        };
        this.mShowTipsRunnable = new Runnable() { // from class: com.android.keyguard.KeyguardInputViewController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                final KeyguardInputViewController keyguardInputViewController = KeyguardInputViewController.this;
                if (keyguardInputViewController.shouldTipsPopup()) {
                    SystemUITextView systemUITextView = keyguardInputViewController.mForgotPasswordText;
                    SemTipPopup semTipPopup = new SemTipPopup(systemUITextView);
                    keyguardInputViewController.mTipPopup = semTipPopup;
                    semTipPopup.setMessage(keyguardInputViewController.getPrevCredentialSubInfoText());
                    int[] iArr = new int[2];
                    systemUITextView.getLocationInWindow(iArr);
                    keyguardInputViewController.mTipPopup.setTargetPosition((int) (keyguardInputViewController.getResources().getDimension(R.dimen.kg_forgot_password_text_smart_tips_margin) + systemUITextView.getWidth() + iArr[0]), (systemUITextView.getHeight() / 2) + iArr[1]);
                    keyguardInputViewController.mTipPopup.setAction(keyguardInputViewController.getResources().getString(R.string.ok), new View.OnClickListener() { // from class: com.android.keyguard.KeyguardInputViewController.2
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            KeyguardInputViewController.this.dismissTips(true);
                        }
                    });
                    keyguardInputViewController.mTipPopup.show(1);
                }
            }
        };
        this.mSecurityMode = securityMode;
        this.mKeyguardSecurityCallback = keyguardSecurityCallback;
        this.mEmergencyButtonController = emergencyButtonController;
        this.mSelectedUserInteractor = selectedUserInteractor;
        this.mBouncerHapticPlayer = bouncerHapticPlayer;
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
        this.mKeyguardUpdateMonitor = (KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class);
        this.mForgotPasswordText = keyguardInputView == null ? null : (SystemUITextView) keyguardInputView.findViewById(R.id.forgot_password_text);
        this.mPrevInfoTextContainer = keyguardInputView == null ? null : (LinearLayout) keyguardInputView.findViewById(R.id.prev_info_message);
        this.mPrevInfoText = keyguardInputView == null ? null : (SystemUITextView) keyguardInputView.findViewById(R.id.prev_info_text);
        this.mPrevInfoSubText = keyguardInputView != null ? (SystemUITextView) keyguardInputView.findViewById(R.id.prev_sub_info_text) : null;
    }

    public final void bindMessageView(BouncerMessageInteractor bouncerMessageInteractor, KeyguardMessageAreaController.Factory factory, BouncerLogger bouncerLogger) {
        BouncerMessageView bouncerMessageView = (BouncerMessageView) ((KeyguardInputView) this.mView).mBouncerMessageView;
        if (bouncerMessageView != null) {
            BouncerMessageViewBinder.bind(bouncerMessageView, bouncerMessageInteractor, factory, bouncerLogger);
        }
    }

    public final void dismissTips(boolean z) {
        SemTipPopup semTipPopup = this.mTipPopup;
        if (semTipPopup == null || !semTipPopup.isShowing()) {
            return;
        }
        this.mTipPopup.dismiss(z);
        this.mTipPopup = null;
    }

    public final int getIndexIn(KeyguardSecurityViewFlipper keyguardSecurityViewFlipper) {
        return keyguardSecurityViewFlipper.indexOfChild(this.mView);
    }

    public abstract int getInitialMessageResId();

    public final KeyguardSecurityCallback getKeyguardSecurityCallback() {
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (this.mSecurityMode != keyguardUpdateMonitor.getCurrentSecurityMode()) {
            boolean isDualDarInnerAuthShowing = keyguardUpdateMonitor.isDualDarInnerAuthShowing();
            AnonymousClass1 anonymousClass1 = this.mNullCallback;
            if (!isDualDarInnerAuthShowing) {
                Log.d("KeyguardInputViewController", "getKeyguardSecurityCallback() returns NULL callback");
                return anonymousClass1;
            }
            if (this.mPaused) {
                Log.d("KeyguardInputViewController", "isDualDarInnerAuthShowing() return true and getKeyguardSecurityCallback() returns NULL callback");
                return anonymousClass1;
            }
        }
        return this.mKeyguardSecurityCallback;
    }

    public final String getPrevCredentialSubInfoText() {
        int credentialTypeForUser = this.mKeyguardUpdateMonitor.getCredentialTypeForUser(this.mSelectedUserInteractor.getSelectedUserId());
        return credentialTypeForUser != 1 ? credentialTypeForUser != 3 ? getResources().getString(R.string.kg_prev_sub_info_message_for_password) : getResources().getString(R.string.kg_prev_sub_info_message_for_pin) : getResources().getString(R.string.kg_prev_sub_info_message_for_pattern);
    }

    @Override // com.android.systemui.util.ViewController
    public void onInit() {
        this.mEmergencyButtonController.init();
    }

    public void onPause() {
        this.mPaused = true;
        dismissTips(false);
    }

    public void onResume(int i) {
        this.mPaused = false;
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewAttached() {
        updateMessageAreaVisibility();
        if (LsRune.SECURITY_OPEN_THEME) {
            WallpaperUtils.registerSystemUIWidgetCallback(this, 1536L);
        }
        SystemUITextView systemUITextView = this.mForgotPasswordText;
        if (systemUITextView != null) {
            systemUITextView.setOnClickListener(new View.OnClickListener() { // from class: com.android.keyguard.KeyguardInputViewController$$ExternalSyntheticLambda1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    KeyguardInputViewController keyguardInputViewController = KeyguardInputViewController.this;
                    keyguardInputViewController.getClass();
                    SystemUIAnalytics.sendEventLog("102", SystemUIAnalytics.EID_PREVIOUS_CREDENTIAL);
                    keyguardInputViewController.dismissTips(false);
                    keyguardInputViewController.getKeyguardSecurityCallback().showBackupSecurity(KeyguardSecurityModel.SecurityMode.ForgotPassword);
                }
            });
        }
        SystemUITextView systemUITextView2 = this.mPrevInfoSubText;
        if (systemUITextView2 != null) {
            systemUITextView2.setText(getPrevCredentialSubInfoText());
        }
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (!TextUtils.isEmpty(keyguardSecMessageAreaController.getMessage()) || getInitialMessageResId() == 0) {
            return;
        }
        keyguardSecMessageAreaController.setMessage(((KeyguardInputView) this.mView).getResources().getString(getInitialMessageResId()), false);
    }

    @Override // com.android.systemui.util.ViewController
    public void onViewDetached() {
        if (LsRune.SECURITY_OPEN_THEME) {
            WallpaperUtils.removeSystemUIWidgetCallback(this);
        }
    }

    public final void restoreAppearance() {
        if (((KeyguardInputView) this.mView).getAlpha() < 1.0f || Float.compare(((KeyguardInputView) this.mView).getScaleX(), 1.0f) != 0 || Float.compare(((KeyguardInputView) this.mView).getScaleY(), 1.0f) != 0) {
            Log.d("KeyguardInputViewController", "restoreAppearance - inputView");
            ((KeyguardInputView) this.mView).setAlpha(1.0f);
            ((KeyguardInputView) this.mView).setScaleX(1.0f);
            ((KeyguardInputView) this.mView).setScaleY(1.0f);
        }
        View findViewById = ((KeyguardInputView) this.mView).findViewById(R.id.bottom_container);
        if (findViewById != null) {
            if (findViewById.getAlpha() >= 1.0f && Float.compare(findViewById.getScaleX(), 1.0f) == 0 && Float.compare(findViewById.getScaleY(), 1.0f) == 0) {
                return;
            }
            Log.d("KeyguardInputViewController", "restoreAppearance - bottom");
            findViewById.setAlpha(1.0f);
            findViewById.setScaleX(1.0f);
            findViewById.setScaleY(1.0f);
        }
    }

    public final void setMessageTimeout(boolean z) {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController;
        KeyguardSecMessageAreaController keyguardSecMessageAreaController2 = this.mMessageAreaController;
        if (keyguardSecMessageAreaController2 == null || (keyguardSecMessageAreaController = this.mSubMessageAreaController) == null) {
            return;
        }
        int i = z ? 0 : 3000;
        keyguardSecMessageAreaController2.setTimeout(i);
        keyguardSecMessageAreaController.setTimeout(i);
    }

    public boolean shouldLockout(long j) {
        return (j == 0 || this.mKeyguardUpdateMonitor.isForgotPasswordView()) ? false : true;
    }

    public final boolean shouldTipsPopup() {
        SystemUITextView systemUITextView;
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        return keyguardUpdateMonitor.getLockoutAttemptDeadline() > 0 && keyguardUpdateMonitor.isHiddenInputContainer() && keyguardUpdateMonitor.isBouncerFullyShown() && (systemUITextView = this.mForgotPasswordText) != null && systemUITextView.getVisibility() == 0 && ((KeyguardInputView) this.mView).isShown();
    }

    public void startAppearAnimation() {
        ((KeyguardInputView) this.mView).startAppearAnimation();
    }

    public boolean startDisappearAnimation(Runnable runnable) {
        return ((KeyguardInputView) this.mView).startDisappearAnimation(runnable);
    }

    public final void updateForgotPasswordTextVisibility() {
        float dimensionPixelSize;
        int dimensionPixelSize2;
        int dimensionPixelSize3;
        SelectedUserInteractor selectedUserInteractor = this.mSelectedUserInteractor;
        int selectedUserId = selectedUserInteractor.getSelectedUserId();
        SystemUITextView systemUITextView = this.mForgotPasswordText;
        if (systemUITextView == null || selectedUserId != 0) {
            return;
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = this.mKeyguardUpdateMonitor;
        if (keyguardUpdateMonitor.isDualDarInnerAuthRequired(selectedUserId)) {
            return;
        }
        systemUITextView.setVisibility((keyguardUpdateMonitor.isForgotPasswordView() || keyguardUpdateMonitor.getFailedUnlockAttempts(selectedUserInteractor.getSelectedUserId()) < 5 || !keyguardUpdateMonitor.checkValidPrevCredentialType() || keyguardUpdateMonitor.mKeyguardOccluded || !(DeviceType.isWeaverDevice() || keyguardUpdateMonitor.getLockoutAttemptDeadline() == 0)) ? 8 : 0);
        LinearLayout linearLayout = this.mPrevInfoTextContainer;
        if (linearLayout != null) {
            linearLayout.setVisibility(keyguardUpdateMonitor.isForgotPasswordView() ? 0 : 8);
        }
        Configuration configuration = getResources().getConfiguration();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) systemUITextView.getLayoutParams();
        systemUITextView.setGravity(17);
        int credentialTypeForUser = keyguardUpdateMonitor.getCredentialTypeForUser(selectedUserInteractor.getSelectedUserId());
        systemUITextView.setText(credentialTypeForUser != 1 ? credentialTypeForUser != 3 ? getResources().getString(R.string.kg_forgot_password) : getResources().getString(R.string.kg_forgot_pin) : getResources().getString(R.string.kg_forgot_pattern));
        if (keyguardUpdateMonitor.isHiddenInputContainer()) {
            if (configuration.orientation == 1) {
                marginLayoutParams.bottomMargin = (int) (configuration.windowConfiguration.getBounds().height() * 0.071d);
            } else {
                marginLayoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.kg_forgot_password_text_bottom_margin_hide_input_container_landscape);
            }
            dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_forgot_password_font_size_hide_input_container);
            dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_forgot_password_text_padding_start_hide_input_container);
            dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.kg_forgot_password_text_padding_top_hide_input_container);
        } else {
            dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.kg_forgot_password_font_size);
            marginLayoutParams.bottomMargin = getResources().getDimensionPixelSize(R.dimen.kg_forgot_password_margin_bottom);
            dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.kg_forgot_password_text_padding_start);
            dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.kg_forgot_password_text_padding_top);
        }
        systemUITextView.setTextSize(0, dimensionPixelSize);
        systemUITextView.setMinWidth((int) getResources().getDimension(R.dimen.kg_forgot_password_min_width));
        systemUITextView.setMaxWidth((int) getResources().getDimension(R.dimen.kg_forgot_password_max_width));
        systemUITextView.setPadding(dimensionPixelSize2, dimensionPixelSize3, dimensionPixelSize2, dimensionPixelSize3);
        systemUITextView.setLayoutParams(marginLayoutParams);
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

    public void reset$1() {
    }

    public void updateMessageAreaVisibility() {
    }

    public void showPromptReason(int i) {
    }

    public void showMessage(CharSequence charSequence, ColorStateList colorStateList, boolean z) {
    }
}
