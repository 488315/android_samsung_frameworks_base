package com.android.keyguard;

import android.animation.AnimatorSet;
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
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.util.DeviceState;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.vibrate.VibrationUtil;
import com.samsung.android.graphics.spr.animation.interpolator.SineInOut90;
import java.util.function.IntConsumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class KeyguardSecPinViewController extends KeyguardPinViewController {
    public final LinearLayout mBottomView;
    public final KeyguardSecPinViewController$$ExternalSyntheticLambda1 mClickCallback;
    public final Handler mHandler;
    public boolean mIsStrongAuthPopupAllowed;
    public final LockPatternUtils mLockPatternUtils;
    public final KeyguardSecPinViewController$$ExternalSyntheticLambda2 mRotationConsumer;
    public final SecRotationWatcher mRotationWatcher;
    public final KeyguardSecurityModel.SecurityMode mSecurityMode;
    public final KeyguardSecPinViewController$$ExternalSyntheticLambda0 mVerifyNDigitsPINRunnable;

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.keyguard.KeyguardSecPinViewController$$ExternalSyntheticLambda2] */
    public KeyguardSecPinViewController(KeyguardSecPINView keyguardSecPINView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, LiftToActivateListener liftToActivateListener, EmergencyButtonController emergencyButtonController, FalsingCollector falsingCollector, DevicePostureController devicePostureController, FeatureFlags featureFlags, SecRotationWatcher secRotationWatcher, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, ConfigurationController configurationController) {
        super(keyguardSecPINView, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, liftToActivateListener, emergencyButtonController, falsingCollector, devicePostureController, featureFlags, secRotationWatcher, vibrationUtil, accessibilityManager, configurationController);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mVerifyNDigitsPINRunnable = new KeyguardSecPinViewController$$ExternalSyntheticLambda0(this, 0);
        this.mClickCallback = new KeyguardSecPinViewController$$ExternalSyntheticLambda1(this);
        this.mRotationConsumer = new IntConsumer() { // from class: com.android.keyguard.KeyguardSecPinViewController$$ExternalSyntheticLambda2
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                KeyguardSecPinViewController keyguardSecPinViewController = KeyguardSecPinViewController.this;
                keyguardSecPinViewController.mHandler.postDelayed(new KeyguardSecPinViewController$$ExternalSyntheticLambda0(keyguardSecPinViewController, 1), 100L);
            }
        };
        this.mIsStrongAuthPopupAllowed = false;
        this.mBottomView = (LinearLayout) ((KeyguardSecPINView) this.mView).findViewById(R.id.bottom_container);
        this.mSecurityMode = securityMode;
        this.mRotationWatcher = secRotationWatcher;
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
            keyguardUpdateMonitor.getClass();
            if (keyguardUpdateMonitor.mFaceLockedOutPermanent || ((KeyguardPinViewController) this).mKeyguardUpdateMonitor.isKeyguardUnlocking()) {
                return;
            }
            setMessageTimeout(true);
            int strongAuthPrompt = SecurityUtils.getStrongAuthPrompt();
            if (strongAuthPrompt != 0) {
                this.mPromptReason = strongAuthPrompt;
                KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m71m(new StringBuilder("displayDefaultSecurityMessage - strongAuth ( "), this.mPromptReason, " )", "KeyguardSecPinViewController");
                showPromptReason(this.mPromptReason);
            } else {
                String defaultSecurityMessage = this.mKeyguardTextBuilder.getDefaultSecurityMessage(KeyguardSecurityModel.SecurityMode.PIN);
                if (this.mBouncerMessage.isEmpty() || !this.mBouncerMessage.equals(defaultSecurityMessage)) {
                    this.mBouncerMessage = defaultSecurityMessage;
                    AbstractC0689x6838b71d.m68m("displayDefaultSecurityMessage( ", defaultSecurityMessage, " )", "KeyguardSecPinViewController");
                    keyguardSecMessageAreaController.setMessage(defaultSecurityMessage, false);
                    keyguardSecMessageAreaController.announceForAccessibility(defaultSecurityMessage);
                    if (LsRune.SECURITY_VZW_INSTRUCTION) {
                        setSubSecurityMessage(R.string.kg_pin_sub_instructions_vzw);
                    } else {
                        setSubSecurityMessage(R.string.kg_pin_sub_instructions);
                    }
                }
            }
            if (((KeyguardPinViewController) this).mKeyguardUpdateMonitor.is2StepVerification()) {
                int currentUser = KeyguardUpdateMonitor.getCurrentUser();
                if (((KeyguardPinViewController) this).mKeyguardUpdateMonitor.getLockoutBiometricAttemptDeadline() > 0) {
                    keyguardSecMessageAreaController.setMessage("", false);
                }
                if (((KeyguardPinViewController) this).mKeyguardUpdateMonitor.getUserUnlockedWithBiometric(currentUser)) {
                    setSubSecurityMessage(R.string.kg_biometrics_has_confirmed);
                } else {
                    setSubSecurityMessage(0);
                }
            }
        }
    }

    public final int getDigitsPIN(int i) {
        return this.mLockPatternUtils.isAutoPinConfirmEnabled(i) ? this.mLockPatternUtils.getPinLength(KeyguardUpdateMonitor.getCurrentUser()) : ((SettingsHelper) Dependency.get(SettingsHelper.class)).mItemLists.get("n_digits_pin_enabled").getIntValue();
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final int getSecurityViewId() {
        return R.id.keyguard_pin_view;
    }

    @Override // com.android.keyguard.KeyguardPinViewController, com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        if (DeviceState.shouldEnableKeyguardScreenRotation(getContext()) || DeviceType.isTablet()) {
            this.mRotationWatcher.addCallback(this.mRotationConsumer);
        }
        PasswordTextView passwordTextView = this.mPasswordEntry;
        if (passwordTextView instanceof SecPasswordTextView) {
            ((SecPasswordTextView) passwordTextView).mClickCallback = this.mClickCallback;
        }
    }

    @Override // com.android.keyguard.KeyguardPinViewController, com.android.keyguard.KeyguardSecPinBasedInputViewController, com.android.keyguard.KeyguardPinBasedInputViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        if (DeviceState.shouldEnableKeyguardScreenRotation(getContext()) || DeviceType.isTablet()) {
            this.mRotationWatcher.removeCallback(this.mRotationConsumer);
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
            if (getDigitsPIN(KeyguardUpdateMonitor.getCurrentUser()) <= 0 || !z2) {
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
                if (LsRune.SECURITY_VZW_INSTRUCTION) {
                    setSubSecurityMessage(R.string.kg_pin_sub_instructions_vzw);
                } else {
                    setSubSecurityMessage(R.string.kg_pin_sub_instructions);
                }
            }
            SpannableStringBuilder strongAuthPopupString = SecurityUtils.getStrongAuthPopupString(getContext(), securityMode, null, i);
            if (strongAuthPopupString != null) {
                keyguardSecMessageAreaController.setMovementMethod(LinkMovementMethod.getInstance());
                keyguardSecMessageAreaController.setMessage(strongAuthPopupString, false);
                ((BouncerKeyguardMessageArea) keyguardSecMessageAreaController.mView).scrollTo(0, 0);
                this.mIsStrongAuthPopupAllowed = true;
                updateMessageLayout();
                return;
            }
            this.mIsStrongAuthPopupAllowed = false;
            if (promptSecurityMessage.isEmpty() || SecurityUtils.isEmptyStrongAuthPopupMessage(getContext(), securityMode)) {
                promptSecurityMessage = keyguardTextBuilder.getDefaultSecurityMessage(securityMode);
            }
            keyguardSecMessageAreaController.setMessage(promptSecurityMessage, false);
        }
    }

    @Override // com.android.keyguard.KeyguardPinViewController, com.android.keyguard.KeyguardInputViewController
    public final void startAppearAnimation() {
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.mBottomView, (Property<LinearLayout, Float>) View.SCALE_X, 0.7f, 1.0f);
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.mBottomView, (Property<LinearLayout, Float>) View.SCALE_Y, 0.7f, 1.0f);
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(this.mBottomView, (Property<LinearLayout, Float>) View.ALPHA, 0.0f, 1.0f);
        ofFloat3.setInterpolator(new LinearInterpolator());
        this.mBottomView.clearAnimation();
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2, ofFloat3);
        animatorSet.setInterpolator(this.mInterpolator);
        animatorSet.setDuration(350L);
        animatorSet.setStartDelay(0L);
        animatorSet.start();
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
            viewGroup.setLayoutParams(marginLayoutParams);
        }
        LinearLayout linearLayout = this.mMessageContainer;
        if (linearLayout != null) {
            ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) linearLayout.getLayoutParams();
            marginLayoutParams2.bottomMargin = !z ? getResources().getDimensionPixelSize(R.dimen.kg_message_area_font_size) + getResources().getDimensionPixelSize(R.dimen.kg_pin_message_area_margin_bottom) : marginLayoutParams2.bottomMargin;
            linearLayout.setLayoutParams(marginLayoutParams2);
        }
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mSubMessageAreaController;
        if (keyguardSecMessageAreaController != null) {
            keyguardSecMessageAreaController.setVisibility(z ? 4 : 0);
        }
    }

    public void verifyNDigitsPIN() {
        int digitsPIN = getDigitsPIN(KeyguardUpdateMonitor.getCurrentUser());
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
                this.mHandler.removeCallbacks(this.mVerifyNDigitsPINRunnable);
                this.mHandler.postDelayed(this.mVerifyNDigitsPINRunnable, 200L);
            }
        }
    }
}
