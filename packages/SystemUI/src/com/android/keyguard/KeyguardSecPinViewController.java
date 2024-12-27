package com.android.keyguard;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.LinearLayout;
import androidx.dynamicanimation.animation.DynamicAnimation;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.domain.interactor.KeyguardKeyboardInteractor;
import com.android.systemui.CscRune;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.vibrate.VibrationUtil;
import com.samsung.android.graphics.spr.animation.interpolator.SineInOut90;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public class KeyguardSecPinViewController extends KeyguardPinViewController {
    public final LinearLayout mBottomView;
    public final KeyguardSecPinViewController$$ExternalSyntheticLambda1 mClickCallback;
    public final ConfigurationController mConfigurationController;
    public final AnonymousClass1 mConfigurationListener;
    public int mCurrentOrientation;
    public final Handler mHandler;
    public boolean mIsStrongAuthPopupAllowed;
    public final LockPatternUtils mLockPatternUtils;
    public final KeyguardSecurityModel.SecurityMode mSecurityMode;
    public final KeyguardSecPinViewController$$ExternalSyntheticLambda0 mVerifyNDigitsPINRunnable;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.keyguard.KeyguardSecPinViewController$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r1v4, types: [com.android.keyguard.KeyguardSecPinViewController$1] */
    public KeyguardSecPinViewController(KeyguardSecPINView keyguardSecPINView, ConfigurationController configurationController, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, EmergencyButtonController emergencyButtonController, FalsingCollector falsingCollector, DevicePostureController devicePostureController, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor, UiEventLogger uiEventLogger, KeyguardKeyboardInteractor keyguardKeyboardInteractor) {
        super(keyguardSecPINView, configurationController, vibrationUtil, accessibilityManager, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, liftToActivateListener, emergencyButtonController, falsingCollector, devicePostureController, featureFlags, selectedUserInteractor, uiEventLogger, keyguardKeyboardInteractor);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mVerifyNDigitsPINRunnable = new Runnable() { // from class: com.android.keyguard.KeyguardSecPinViewController$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardSecPinViewController.this.verifyPasswordAndUnlock();
            }
        };
        this.mClickCallback = new KeyguardSecPinViewController$$ExternalSyntheticLambda1(this);
        this.mCurrentOrientation = 1;
        this.mConfigurationListener = new ConfigurationController.ConfigurationListener() { // from class: com.android.keyguard.KeyguardSecPinViewController.1
            @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
            public final void onOrientationChanged(int i) {
                KeyguardSecPinViewController keyguardSecPinViewController = KeyguardSecPinViewController.this;
                if (keyguardSecPinViewController.mCurrentOrientation != i) {
                    keyguardSecPinViewController.mCurrentOrientation = i;
                    keyguardSecPinViewController.updateMessageLayout();
                }
            }
        };
        this.mIsStrongAuthPopupAllowed = false;
        this.mBottomView = (LinearLayout) ((KeyguardSecPINView) this.mView).findViewById(R.id.bottom_container);
        this.mSecurityMode = securityMode;
        this.mConfigurationController = configurationController;
        this.mLockPatternUtils = lockPatternUtils;
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, android.text.TextWatcher
    public final void afterTextChanged(Editable editable) {
        super.afterTextChanged(editable);
        verifyNDigitsPIN();
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public void displayDefaultSecurityMessage() {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (keyguardSecMessageAreaController == null) {
            Log.e("KeyguardSecPinViewController", "displayDefaultSecurityMessage mMessageAreaController is null");
            return;
        }
        PasswordTextView passwordTextView = this.mPasswordEntry;
        if ((passwordTextView == null || ((SecPasswordTextView) passwordTextView).mText.length() <= 0) && !((KeyguardPinViewController) this).mKeyguardUpdateMonitor.isFingerprintLockedOut()) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardPinViewController) this).mKeyguardUpdateMonitor;
            if (keyguardUpdateMonitor.mFaceLockedOutPermanent || keyguardUpdateMonitor.isKeyguardUnlocking()) {
                return;
            }
            setMessageTimeout(true);
            SelectedUserInteractor selectedUserInteractor = this.mSelectedUserInteractor;
            int strongAuthPrompt = SecurityUtils.getStrongAuthPrompt(selectedUserInteractor.getSelectedUserId(false));
            if (((KeyguardPinViewController) this).mKeyguardUpdateMonitor.isShowEditModeRequest()) {
                keyguardSecMessageAreaController.setMessage$1(getContext().getString(R.string.kg_edit_mode_instructions), false);
                if (CscRune.SECURITY_VZW_INSTRUCTION) {
                    setSubSecurityMessage(R.string.kg_pin_sub_instructions_vzw);
                } else {
                    setSubSecurityMessage(R.string.kg_pin_sub_instructions);
                }
            } else {
                if (strongAuthPrompt != 0) {
                    this.mPromptReason = strongAuthPrompt;
                    KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(new StringBuilder("displayDefaultSecurityMessage - strongAuth ( "), this.mPromptReason, " )", "KeyguardSecPinViewController");
                    showPromptReason(this.mPromptReason);
                } else {
                    String defaultSecurityMessage = this.mKeyguardTextBuilder.getDefaultSecurityMessage(KeyguardSecurityModel.SecurityMode.PIN);
                    if (this.mBouncerMessage.isEmpty() || !this.mBouncerMessage.equals(defaultSecurityMessage)) {
                        this.mBouncerMessage = defaultSecurityMessage;
                        KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("displayDefaultSecurityMessage( ", defaultSecurityMessage, " )", "KeyguardSecPinViewController");
                        keyguardSecMessageAreaController.setMessage$1(defaultSecurityMessage, false);
                        keyguardSecMessageAreaController.announceForAccessibility(defaultSecurityMessage);
                        if (CscRune.SECURITY_VZW_INSTRUCTION) {
                            setSubSecurityMessage(R.string.kg_pin_sub_instructions_vzw);
                        } else {
                            setSubSecurityMessage(R.string.kg_pin_sub_instructions);
                        }
                    }
                }
            }
            if (((KeyguardPinViewController) this).mKeyguardUpdateMonitor.is2StepVerification()) {
                int selectedUserId = selectedUserInteractor.getSelectedUserId(false);
                if (((KeyguardPinViewController) this).mKeyguardUpdateMonitor.getLockoutBiometricAttemptDeadline() > 0) {
                    keyguardSecMessageAreaController.setMessage$1("", false);
                }
                if (((KeyguardPinViewController) this).mKeyguardUpdateMonitor.getUserUnlockedWithBiometric(selectedUserId)) {
                    setSubSecurityMessage(R.string.kg_biometrics_has_confirmed);
                } else {
                    setSubSecurityMessage(0);
                }
            }
        }
    }

    public final int getDigitsPIN(int i) {
        return this.mLockPatternUtils.isAutoPinConfirmEnabled(i) ? this.mLockPatternUtils.getPinLength(this.mSelectedUserInteractor.getSelectedUserId()) : ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getNDigitsPIN();
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final int getSecurityViewId() {
        return R.id.keyguard_pin_view;
    }

    @Override // com.android.keyguard.KeyguardPinViewController, com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        if (DeviceState.shouldEnableKeyguardScreenRotation(getContext())) {
            this.mCurrentOrientation = getContext().getResources().getConfiguration().orientation;
            ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this.mConfigurationListener);
        }
        PasswordTextView passwordTextView = this.mPasswordEntry;
        if (passwordTextView instanceof SecPasswordTextView) {
            ((SecPasswordTextView) passwordTextView).mClickCallback = this.mClickCallback;
        }
    }

    @Override // com.android.keyguard.KeyguardPinViewController, com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        if (DeviceState.shouldEnableKeyguardScreenRotation(getContext())) {
            ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this.mConfigurationListener);
        }
        PasswordTextView passwordTextView = this.mPasswordEntry;
        if (passwordTextView instanceof SecPasswordTextView) {
            ((SecPasswordTextView) passwordTextView).mClickCallback = null;
        }
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public void resetState() {
        super.resetState();
        displayDefaultSecurityMessage();
        resetFor2StepVerification();
    }

    @Override // com.android.keyguard.KeyguardSecPinBasedInputViewController
    public void setOkButtonEnabled(boolean z) {
        if (this.mOkButton != null) {
            boolean z2 = this.mSecurityMode == KeyguardSecurityModel.SecurityMode.PIN;
            if (getDigitsPIN(this.mSelectedUserInteractor.getSelectedUserId(false)) <= 0 || !z2) {
                super.setOkButtonEnabled(z);
                return;
            }
            this.mOkButton.setFocusable(false);
            this.mOkButton.setClickable(false);
            this.mOkButton.setAlpha(0.4f);
        }
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void showPromptReason(int i) {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (keyguardSecMessageAreaController == null) {
            Log.d("KeyguardSecPinViewController", "showPromptReason mMessageAreaController is null");
            return;
        }
        this.mPromptReason = i;
        if (i != 0) {
            KeyguardSecurityModel.SecurityMode securityMode = KeyguardSecurityModel.SecurityMode.PIN;
            KeyguardTextBuilder keyguardTextBuilder = this.mKeyguardTextBuilder;
            String promptSecurityMessage = keyguardTextBuilder.getPromptSecurityMessage(securityMode, i);
            if (!shouldLockout(((KeyguardPinViewController) this).mKeyguardUpdateMonitor.getLockoutAttemptDeadline())) {
                if (CscRune.SECURITY_VZW_INSTRUCTION) {
                    setSubSecurityMessage(R.string.kg_pin_sub_instructions_vzw);
                } else {
                    setSubSecurityMessage(R.string.kg_pin_sub_instructions);
                }
            }
            SpannableStringBuilder strongAuthPopupString = SecurityUtils.getStrongAuthPopupString(getContext(), securityMode, null, i);
            if (strongAuthPopupString != null) {
                keyguardSecMessageAreaController.setMovementMethod(LinkMovementMethod.getInstance());
                keyguardSecMessageAreaController.setMessage$1(strongAuthPopupString, false);
                keyguardSecMessageAreaController.scrollTo(0, 0);
                this.mIsStrongAuthPopupAllowed = true;
                updateMessageLayout();
                return;
            }
            this.mIsStrongAuthPopupAllowed = false;
            if (promptSecurityMessage.isEmpty() || KeyguardTextBuilder.getInstance(getContext()).getStrongAuthTimeOutMessage(securityMode).isEmpty()) {
                promptSecurityMessage = keyguardTextBuilder.getDefaultSecurityMessage(securityMode);
            }
            keyguardSecMessageAreaController.setMessage$1(promptSecurityMessage, false);
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
        ofFloat.setInterpolator(this.mInterpolator);
        ofFloat.setDuration(300L);
        ofFloat.start();
    }

    @Override // com.android.keyguard.KeyguardPinViewController, com.android.keyguard.KeyguardInputViewController
    public final boolean startDisappearAnimation(Runnable runnable) {
        this.mBottomView.clearAnimation();
        this.mBottomView.animate().scaleX(1.3f).scaleY(1.3f).setDuration(200L).setInterpolator(new SineInOut90()).setStartDelay(0L).alpha(0.0f).withEndAction(runnable);
        return true;
    }

    public final void updateMessageLayout() {
        if (!this.mIsStrongAuthPopupAllowed || DeviceType.isTablet() || ((KeyguardPinViewController) this).mKeyguardUpdateMonitor.isDualDisplayPolicyAllowed()) {
            return;
        }
        Resources resources = getResources();
        int rotation = DeviceState.getRotation(resources.getConfiguration().windowConfiguration.getRotation());
        boolean z = true;
        if (rotation != 1 && rotation != 3) {
            z = false;
        }
        ViewGroup viewGroup = this.mPasswordEntryBoxLayout;
        if (viewGroup != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) viewGroup.getLayoutParams();
            marginLayoutParams.topMargin = z ? (resources.getDimensionPixelSize(R.dimen.kg_message_area_font_size) * 2) + getResources().getDimensionPixelSize(R.dimen.kg_security_input_box_margin_top) : 0;
            this.mPasswordEntryBoxLayout.setLayoutParams(marginLayoutParams);
        }
        LinearLayout linearLayout = this.mMessageContainer;
        if (linearLayout != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) linearLayout.getLayoutParams();
            marginLayoutParams2.bottomMargin = !z ? getResources().getDimensionPixelSize(R.dimen.kg_message_area_font_size) + getResources().getDimensionPixelSize(R.dimen.kg_pin_message_area_margin_bottom) : marginLayoutParams2.bottomMargin;
            this.mMessageContainer.setLayoutParams(marginLayoutParams2);
        }
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mSubMessageAreaController;
        if (keyguardSecMessageAreaController != null) {
            keyguardSecMessageAreaController.setVisibility(z ? 4 : 0);
        }
    }

    public void verifyNDigitsPIN() {
        int digitsPIN = getDigitsPIN(this.mSelectedUserInteractor.getSelectedUserId(false));
        boolean z = this.mSecurityMode == KeyguardSecurityModel.SecurityMode.PIN;
        if (digitsPIN <= 0 || !z) {
            return;
        }
        PasswordTextView passwordTextView = this.mPasswordEntry;
        if ((passwordTextView instanceof SecPasswordTextView) && passwordTextView.isEnabled()) {
            SecPasswordTextView secPasswordTextView = (SecPasswordTextView) passwordTextView;
            if (secPasswordTextView.mText.length() == digitsPIN) {
                Log.d("KeyguardSecPinViewController", "verifyPassword by N digits pin option");
                secPasswordTextView.mMaxLength = digitsPIN;
                passwordTextView.setEnabled(false);
                this.mHandler.removeCallbacks(this.mVerifyNDigitsPINRunnable);
                this.mHandler.postDelayed(this.mVerifyNDigitsPINRunnable, 200L);
            }
        }
    }
}
