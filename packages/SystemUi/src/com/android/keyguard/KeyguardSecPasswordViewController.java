package com.android.keyguard;

import android.app.SemWallpaperColors;
import android.content.res.Resources;
import android.hardware.biometrics.BiometricSourceType;
import android.net.Uri;
import android.provider.Settings;
import android.text.SpannableStringBuilder;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.view.inputmethod.InputMethodSubtype;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.vibrate.VibrationUtil;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIImageView;
import com.android.systemui.widget.SystemUITextView;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class KeyguardSecPasswordViewController extends KeyguardPasswordViewController {
    public boolean mIsShownSIP;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public final KeyguardSecPasswordViewController$$ExternalSyntheticLambda1 mOnLayoutChangeListener;
    public final KeyguardSecPasswordViewController$$ExternalSyntheticLambda2 mOnWindowFocusChangeListener;
    public KeyguardSecPasswordViewController$$ExternalSyntheticLambda4 mSettingsListener;
    public final SystemUIImageView mShowPasswordButton;
    public final KeyguardUpdateMonitorCallback mUpdateMonitorCallbacks;

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda2] */
    public KeyguardSecPasswordViewController(KeyguardSecPasswordView keyguardSecPasswordView, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, InputMethodManager inputMethodManager, EmergencyButtonController emergencyButtonController, DelayableExecutor delayableExecutor, Resources resources, FalsingCollector falsingCollector, KeyguardViewController keyguardViewController, FeatureFlags featureFlags, SecRotationWatcher secRotationWatcher, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager) {
        super(keyguardSecPasswordView, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, inputMethodManager, emergencyButtonController, delayableExecutor, resources, falsingCollector, keyguardViewController, featureFlags, secRotationWatcher, vibrationUtil, accessibilityManager);
        this.mOnLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                KeyguardSecPasswordViewController keyguardSecPasswordViewController = KeyguardSecPasswordViewController.this;
                InputMethodManager inputMethodManager2 = keyguardSecPasswordViewController.mInputMethodManager;
                if (i4 == i8 && inputMethodManager2.semIsInputMethodShown() == keyguardSecPasswordViewController.mIsShownSIP) {
                    return;
                }
                keyguardSecPasswordViewController.mIsShownSIP = inputMethodManager2.semIsInputMethodShown();
                keyguardSecPasswordViewController.setMessageAreaLandscapeAdditionalPadding();
                if (((SettingsHelper) Dependency.get(SettingsHelper.class)).isShowKeyboardButton()) {
                    if (!keyguardSecPasswordViewController.mIsShownSIP) {
                        keyguardSecPasswordViewController.mSwitchImeButton.setVisibility(8);
                    }
                    keyguardSecPasswordViewController.updateSwitchImeButton();
                }
                LinearLayout linearLayout = keyguardSecPasswordViewController.mPrevInfoTextContainer;
                if (linearLayout != null) {
                    KeyguardUpdateMonitor keyguardUpdateMonitor2 = ((KeyguardAbsKeyInputViewController) keyguardSecPasswordViewController).mKeyguardUpdateMonitor;
                    if (keyguardUpdateMonitor2.isForgotPasswordView() && !keyguardUpdateMonitor2.isDualDisplayPolicyAllowed()) {
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) linearLayout.getLayoutParams();
                        marginLayoutParams.bottomMargin = keyguardSecPasswordViewController.getResources().getDimensionPixelSize(R.dimen.kg_prev_message_area_margin_bottom);
                        marginLayoutParams.topMargin = (keyguardSecPasswordViewController.mIsShownSIP && keyguardSecPasswordViewController.getResources().getConfiguration().orientation == 2) ? keyguardSecPasswordViewController.getResources().getDimensionPixelSize(R.dimen.kg_biometric_view_height) : 0;
                        linearLayout.setLayoutParams(marginLayoutParams);
                    }
                }
                keyguardSecPasswordViewController.updateForgotTextMargin();
            }
        };
        this.mOnWindowFocusChangeListener = new ViewTreeObserver.OnWindowFocusChangeListener() { // from class: com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda2
            @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
            public final void onWindowFocusChanged(boolean z) {
                KeyguardSecPasswordViewController keyguardSecPasswordViewController = KeyguardSecPasswordViewController.this;
                keyguardSecPasswordViewController.getClass();
                if (z && ((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.get(KeyguardFoldController.class))).isBouncerOnFoldOpened()) {
                    ((KeyguardSecPasswordView) keyguardSecPasswordViewController.mView).postDelayed(new KeyguardSecPasswordViewController$$ExternalSyntheticLambda5(keyguardSecPasswordViewController, 0), 100L);
                }
            }
        };
        this.mUpdateMonitorCallbacks = new KeyguardUpdateMonitorCallback() { // from class: com.android.keyguard.KeyguardSecPasswordViewController.1
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricAuthenticated(int i, BiometricSourceType biometricSourceType, boolean z) {
                KeyguardSecPasswordViewController keyguardSecPasswordViewController = KeyguardSecPasswordViewController.this;
                if (keyguardSecPasswordViewController.mInputMethodManager == null || !((KeyguardAbsKeyInputViewController) keyguardSecPasswordViewController).mKeyguardUpdateMonitor.isUnlockingWithBiometricAllowed(z)) {
                    return;
                }
                keyguardSecPasswordViewController.mInputMethodManager.hideSoftInputFromWindow(keyguardSecPasswordViewController.mPasswordEntry.getWindowToken(), 0);
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricLockoutChanged(boolean z) {
                KeyguardSecPasswordViewController keyguardSecPasswordViewController = KeyguardSecPasswordViewController.this;
                keyguardSecPasswordViewController.setMessageAreaLandscapeAdditionalPadding();
                keyguardSecPasswordViewController.updateForgotTextMargin();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricRunningStateChanged(BiometricSourceType biometricSourceType, boolean z) {
                KeyguardSecPasswordViewController keyguardSecPasswordViewController = KeyguardSecPasswordViewController.this;
                if (((KeyguardAbsKeyInputViewController) keyguardSecPasswordViewController).mKeyguardUpdateMonitor.is2StepVerification() && biometricSourceType == BiometricSourceType.FINGERPRINT && keyguardSecPasswordViewController.mBouncerShowing && !z && keyguardSecPasswordViewController.mPasswordEntry.isEnabled()) {
                    keyguardSecPasswordViewController.mPasswordEntry.requestFocus();
                    keyguardSecPasswordViewController.mInputMethodManager.showSoftInput(keyguardSecPasswordViewController.mPasswordEntry, 1);
                }
            }
        };
        this.mKnoxStateMonitor = (KnoxStateMonitor) Dependency.get(KnoxStateMonitor.class);
        this.mContainer = (LinearLayout) ((KeyguardSecPasswordView) this.mView).findViewById(R.id.container);
        this.mShowPasswordButton = (SystemUIImageView) ((KeyguardSecPasswordView) this.mView).findViewById(R.id.password_show_button);
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public void displayDefaultSecurityMessage() {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (keyguardSecMessageAreaController == null) {
            Log.e("KeyguardSecPasswordViewController", "displayDefaultSecurityMessage mMessageAreaController is null");
            return;
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor;
        if (keyguardUpdateMonitor.isFingerprintLockedOut() || keyguardUpdateMonitor.mFaceLockedOutPermanent || keyguardUpdateMonitor.isKeyguardUnlocking()) {
            return;
        }
        setMessageTimeout(true);
        int strongAuthPrompt = SecurityUtils.getStrongAuthPrompt();
        if (strongAuthPrompt != 0) {
            this.mPromptReason = strongAuthPrompt;
            KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m71m(new StringBuilder("displayDefaultSecurityMessage - strongAuth ( "), this.mPromptReason, " )", "KeyguardSecPasswordViewController");
            showPromptReason(this.mPromptReason);
        } else {
            String defaultSecurityMessage = this.mKeyguardTextBuilder.getDefaultSecurityMessage(KeyguardSecurityModel.SecurityMode.Password);
            if (this.mBouncerMessage.isEmpty() || !this.mBouncerMessage.equals(defaultSecurityMessage)) {
                this.mBouncerMessage = defaultSecurityMessage;
                AbstractC0689x6838b71d.m68m("displayDefaultSecurityMessage( ", defaultSecurityMessage, " )", "KeyguardSecPasswordViewController");
                keyguardSecMessageAreaController.setMessage(defaultSecurityMessage, false);
                keyguardSecMessageAreaController.announceForAccessibility(defaultSecurityMessage);
                if (LsRune.SECURITY_VZW_INSTRUCTION) {
                    setSubSecurityMessage(R.string.kg_password_sub_instructions_vzw);
                } else {
                    setSubSecurityMessage(R.string.kg_password_sub_instructions);
                }
            }
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

    public final void enableHidingPassword(boolean z) {
        SystemUIImageView systemUIImageView;
        EditText editText = this.mPasswordEntry;
        if (editText == null || (systemUIImageView = this.mShowPasswordButton) == null) {
            Log.e("KeyguardSecPasswordViewController", "enableHidingPassword() view is null");
            return;
        }
        boolean isWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper("background");
        if (z) {
            editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            systemUIImageView.setImageResource(isWhiteKeyguardWallpaper ? R.drawable.lock_whitebg_password_hide_btn : R.drawable.lock_password_hide_btn);
            systemUIImageView.setStateDescription(getResources().getString(R.string.kg_show_password_accessibility_off));
        } else {
            editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            systemUIImageView.setImageResource(isWhiteKeyguardWallpaper ? R.drawable.lock_whitebg_password_show_btn : R.drawable.lock_password_show_btn);
            systemUIImageView.setStateDescription(getResources().getString(R.string.kg_show_password_accessibility_on));
        }
    }

    @Override // com.android.keyguard.KeyguardPasswordViewController, com.android.keyguard.KeyguardInputViewController
    public final int getInitialMessageResId() {
        return 0;
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController
    public final int getSecurityViewId() {
        return R.id.keyguard_password_view;
    }

    @Override // com.android.keyguard.KeyguardPasswordViewController
    public final boolean hasMultipleEnabledIMEsOrSubtypes(InputMethodManager inputMethodManager) {
        int i = 0;
        for (InputMethodInfo inputMethodInfo : inputMethodManager.getEnabledInputMethodListAsUser(KeyguardUpdateMonitor.getCurrentUser())) {
            if (i > 1) {
                return true;
            }
            List<InputMethodSubtype> enabledInputMethodSubtypeList = inputMethodManager.getEnabledInputMethodSubtypeList(inputMethodInfo, true);
            enabledInputMethodSubtypeList.size();
            if (!"com.sec.android.inputmethod/.SamsungKeypad".equals(inputMethodInfo.getId()) && !"com.sec.android.inputmethod.beta/com.sec.android.inputmethod.SamsungKeypad".equals(inputMethodInfo.getId())) {
                "com.samsung.android.honeyboard/.SamsungKeypad".equals(inputMethodInfo.getId());
            }
            if (!enabledInputMethodSubtypeList.isEmpty()) {
                Iterator<InputMethodSubtype> it = enabledInputMethodSubtypeList.iterator();
                int i2 = 0;
                while (it.hasNext()) {
                    if (it.next().isAuxiliary()) {
                        i2++;
                    }
                }
                if (enabledInputMethodSubtypeList.size() - i2 <= 0) {
                }
            }
            i++;
        }
        return i > 1;
    }

    @Override // com.android.keyguard.KeyguardPasswordViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardSecurityView
    public final boolean needsInput() {
        return true;
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public void onPasswordChecked(int i, int i2, boolean z, boolean z2) {
        if (z) {
            this.mInputMethodManager.forceHideSoftInput();
        }
        super.onPasswordChecked(i, i2, z, z2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0074, code lost:
    
        if (r3.mPasswordVisibilityEnabled != false) goto L27;
     */
    @Override // com.android.keyguard.KeyguardPasswordViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onResume(int i) {
        super.onResume(i);
        ViewGroup viewGroup = this.mPasswordEntryBoxLayout;
        if (viewGroup != null) {
            viewGroup.setBackgroundResource(WallpaperUtils.isWhiteKeyguardWallpaper("background") ? R.drawable.keyguard_security_input_box_whitebg : R.drawable.keyguard_security_input_box);
        }
        boolean z = true;
        EditText editText = this.mPasswordEntry;
        if (editText != null && getResources().getConfiguration().getLayoutDirection() == 1) {
            editText.setPaddingRelative(getResources().getDimensionPixelSize(R.dimen.kg_security_input_box_padding_left), 0, getResources().getDimensionPixelSize(R.dimen.kg_security_input_box_padding_right), 0);
        }
        if (!((SettingsHelper) Dependency.get(SettingsHelper.class)).isShowKeyboardButton() || isHideKeyboardByDefault()) {
            this.mSwitchImeButton.setVisibility(8);
        }
        KnoxStateMonitor knoxStateMonitor = this.mKnoxStateMonitor;
        if (knoxStateMonitor != null) {
            EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) knoxStateMonitor).mEdmMonitor;
            if (edmMonitor != null) {
                Log.d("EdmMonitor", "isPasswordVisibilityEnabled ");
            }
            z = false;
            if (!z) {
                Log.d("KeyguardSecPasswordViewController", "<<<--->>> hide button");
                SystemUIImageView systemUIImageView = this.mShowPasswordButton;
                if (systemUIImageView != null) {
                    systemUIImageView.setVisibility(8);
                }
                if (editText != null) {
                    editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        }
        ((KeyguardSecPasswordView) this.mView).postDelayed(new KeyguardSecPasswordViewController$$ExternalSyntheticLambda5(this, i), 100L);
    }

    @Override // com.android.keyguard.KeyguardPasswordViewController, com.android.keyguard.KeyguardSecurityView
    public final void onStartingToHide() {
        this.mInputMethodManager.hideSoftInputFromWindow(((KeyguardSecPasswordView) this.mView).getWindowToken(), 0);
    }

    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda4] */
    @Override // com.android.keyguard.KeyguardPasswordViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor.registerCallback(this.mUpdateMonitorCallbacks);
        ((KeyguardSecPasswordView) this.mView).addOnLayoutChangeListener(this.mOnLayoutChangeListener);
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            ((KeyguardSecPasswordView) this.mView).getViewTreeObserver().addOnWindowFocusChangeListener(this.mOnWindowFocusChangeListener);
        }
        final int i = 0;
        View.OnClickListener onClickListener = new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda3
            public final /* synthetic */ KeyguardSecPasswordViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                switch (i) {
                    case 0:
                        this.f$0.getKeyguardSecurityCallback().userActivity();
                        break;
                    default:
                        KeyguardSecPasswordViewController keyguardSecPasswordViewController = this.f$0;
                        keyguardSecPasswordViewController.getKeyguardSecurityCallback().userActivity();
                        EditText editText = keyguardSecPasswordViewController.mPasswordEntry;
                        keyguardSecPasswordViewController.enableHidingPassword(editText.getTransformationMethod() == HideReturnsTransformationMethod.getInstance());
                        editText.setAccessibilitySelection(editText.getText().length(), editText.getText().length());
                        break;
                }
            }
        };
        EditText editText = this.mPasswordEntry;
        editText.setOnClickListener(onClickListener);
        editText.setLongClickable(false);
        SystemUIImageView systemUIImageView = this.mShowPasswordButton;
        if (systemUIImageView != null) {
            systemUIImageView.setContentDescription(getResources().getString(R.string.kg_show_password_accessibility));
            systemUIImageView.setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1000));
            final int i2 = 1;
            systemUIImageView.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda3
                public final /* synthetic */ KeyguardSecPasswordViewController f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    switch (i2) {
                        case 0:
                            this.f$0.getKeyguardSecurityCallback().userActivity();
                            break;
                        default:
                            KeyguardSecPasswordViewController keyguardSecPasswordViewController = this.f$0;
                            keyguardSecPasswordViewController.getKeyguardSecurityCallback().userActivity();
                            EditText editText2 = keyguardSecPasswordViewController.mPasswordEntry;
                            keyguardSecPasswordViewController.enableHidingPassword(editText2.getTransformationMethod() == HideReturnsTransformationMethod.getInstance());
                            editText2.setAccessibilitySelection(editText2.getText().length(), editText2.getText().length());
                            break;
                    }
                }
            });
        }
        if (this.mAccessibilityManager.isTouchExplorationEnabled()) {
            editText.setSelected(false);
        }
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda4
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                KeyguardSecPasswordViewController keyguardSecPasswordViewController = KeyguardSecPasswordViewController.this;
                keyguardSecPasswordViewController.getClass();
                if (!uri.equals(Settings.Secure.getUriFor("show_keyboard_button")) || ((SettingsHelper) Dependency.get(SettingsHelper.class)).isShowKeyboardButton()) {
                    return;
                }
                SystemUIImageView systemUIImageView2 = keyguardSecPasswordViewController.mShowPasswordButton;
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) systemUIImageView2.getLayoutParams();
                marginLayoutParams.setMarginEnd(keyguardSecPasswordViewController.getResources().getDimensionPixelSize(R.dimen.kg_security_show_password_side_margin));
                systemUIImageView2.setLayoutParams(marginLayoutParams);
                keyguardSecPasswordViewController.mSwitchImeButton.setVisibility(8);
            }
        };
        ((SettingsHelper) Dependency.get(SettingsHelper.class)).registerCallback(this.mSettingsListener, Settings.Secure.getUriFor("show_keyboard_button"));
    }

    @Override // com.android.keyguard.KeyguardPasswordViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor.removeCallback(this.mUpdateMonitorCallbacks);
        ((KeyguardSecPasswordView) this.mView).removeOnLayoutChangeListener(this.mOnLayoutChangeListener);
        ((SettingsHelper) Dependency.get(SettingsHelper.class)).unregisterCallback(this.mSettingsListener);
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            ((KeyguardSecPasswordView) this.mView).getViewTreeObserver().removeOnWindowFocusChangeListener(this.mOnWindowFocusChangeListener);
        }
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void reset() {
        super.reset();
        enableHidingPassword(true);
    }

    @Override // com.android.keyguard.KeyguardPasswordViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public void resetState() {
        displayDefaultSecurityMessage();
        resetFor2StepVerification();
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0048, code lost:
    
        if ((com.android.keyguard.SecurityUtils.getStrongAuthPrompt() != 0) != false) goto L25;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void setMessageAreaLandscapeAdditionalPadding() {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController;
        int i;
        SystemUITextView systemUITextView;
        Resources resources = getResources();
        int currentUser = KeyguardUpdateMonitor.getCurrentUser();
        KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor;
        if (!keyguardUpdateMonitor.isUnlockingWithBiometricsPossible(currentUser) || (keyguardSecMessageAreaController = this.mMessageAreaController) == null) {
            return;
        }
        if (this.mIsShownSIP && isLandscapeDisplay() && (((systemUITextView = this.mForgotPasswordText) == null || !systemUITextView.isShown()) && !DeviceType.isTablet() && !keyguardUpdateMonitor.isDualDisplayPolicyAllowed())) {
            if (keyguardUpdateMonitor.getLockoutBiometricAttemptDeadline() <= 0) {
            }
            i = (resources.getDimensionPixelSize(R.dimen.kg_message_area_font_size) * 4) + resources.getDimensionPixelSize(R.dimen.kg_biometric_view_height);
            ((BouncerKeyguardMessageArea) keyguardSecMessageAreaController.mView).setPadding(0, i, 0, 0);
        }
        i = 0;
        ((BouncerKeyguardMessageArea) keyguardSecMessageAreaController.mView).setPadding(0, i, 0, 0);
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void showPromptReason(int i) {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController = this.mMessageAreaController;
        if (keyguardSecMessageAreaController == null) {
            Log.d("KeyguardSecPasswordViewController", "showPromptReason mMessageAreaController is null");
            return;
        }
        this.mPromptReason = i;
        if (i != 0) {
            KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor;
            if (keyguardUpdateMonitor.getLockoutAttemptDeadline() > 0) {
                return;
            }
            KeyguardSecurityModel.SecurityMode securityMode = KeyguardSecurityModel.SecurityMode.Password;
            KeyguardTextBuilder keyguardTextBuilder = this.mKeyguardTextBuilder;
            String promptSecurityMessage = keyguardTextBuilder.getPromptSecurityMessage(securityMode, i);
            if (!shouldLockout(keyguardUpdateMonitor.getLockoutAttemptDeadline())) {
                if (LsRune.SECURITY_VZW_INSTRUCTION) {
                    setSubSecurityMessage(R.string.kg_password_sub_instructions_vzw);
                } else {
                    setSubSecurityMessage(R.string.kg_password_sub_instructions);
                }
            }
            SpannableStringBuilder strongAuthPopupString = SecurityUtils.getStrongAuthPopupString(getContext(), securityMode, this.mPasswordEntry, i);
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

    public final void updateForgotTextMargin() {
        boolean z = true;
        KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor;
        int i = 0;
        SystemUITextView systemUITextView = this.mForgotPasswordText;
        if ((systemUITextView == null || !systemUITextView.isShown() || DeviceType.isTablet() || keyguardUpdateMonitor.isDualDisplayPolicyAllowed()) ? false : true) {
            if (keyguardUpdateMonitor.getLockoutBiometricAttemptDeadline() <= 0) {
                if (!(SecurityUtils.getStrongAuthPrompt() != 0)) {
                    z = false;
                }
            }
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) systemUITextView.getLayoutParams();
            if (this.mIsShownSIP && z && getResources().getConfiguration().orientation == 2) {
                i = (getResources().getDimensionPixelSize(R.dimen.kg_message_area_font_size) * 4) + getResources().getDimensionPixelSize(R.dimen.kg_biometric_view_height);
            }
            marginLayoutParams.topMargin = i;
            systemUITextView.setLayoutParams(marginLayoutParams);
        }
    }

    @Override // com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        ViewGroup viewGroup;
        if (skipUpdateWhenCloseFolder() || (viewGroup = this.mPasswordEntryBoxLayout) == null) {
            return;
        }
        viewGroup.setBackgroundResource(WallpaperUtils.isWhiteKeyguardWallpaper("background") ? R.drawable.keyguard_security_input_box_whitebg : R.drawable.keyguard_security_input_box);
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x00b1  */
    /* JADX WARN: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
    @Override // com.android.keyguard.KeyguardPasswordViewController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateSwitchImeButton() {
        ImageView imageView = this.mSwitchImeButton;
        if (imageView == null) {
            Log.e("KeyguardSecPasswordViewController", "mSwitchImeButton is null");
            return;
        }
        boolean hasMultipleEnabledIMEsOrSubtypes = hasMultipleEnabledIMEsOrSubtypes(this.mInputMethodManager);
        boolean z = false;
        boolean z2 = imageView.getVisibility() == 0;
        boolean z3 = this.mIsShownSIP && hasMultipleEnabledIMEsOrSubtypes;
        StringBuilder m69m = KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m69m("updateSwitchImeButton, wasVisible = ", z2, ", shouldBeVisible = ", z3, ", needImeBtn = ");
        m69m.append(hasMultipleEnabledIMEsOrSubtypes);
        Log.i("KeyguardSecPasswordViewController", m69m.toString());
        if (!((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).isDesktopMode() && z2 != z3) {
            imageView.setVisibility(z3 ? 0 : 8);
        }
        if (!((SettingsHelper) Dependency.get(SettingsHelper.class)).isShowKeyboardButton()) {
            imageView.setVisibility(8);
        }
        SystemUIImageView systemUIImageView = this.mShowPasswordButton;
        if (systemUIImageView == null) {
            return;
        }
        Resources resources = getResources();
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) systemUIImageView.getLayoutParams();
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.kg_security_show_password_side_margin);
        if (imageView.getVisibility() == 0) {
            if (marginLayoutParams.getMarginEnd() == dimensionPixelSize) {
                marginLayoutParams.setMarginEnd(resources.getDimensionPixelSize(R.dimen.kg_security_ime_button_side_margin) + resources.getDimensionPixelSize(R.dimen.kg_security_show_password_side_margin_for_ime_button) + resources.getDimensionPixelSize(R.dimen.kg_security_ime_button_width));
                z = true;
            }
            if (z) {
                return;
            }
            systemUIImageView.setLayoutParams(marginLayoutParams);
            return;
        }
        if (marginLayoutParams.getMarginEnd() != dimensionPixelSize) {
            marginLayoutParams.setMarginEnd(dimensionPixelSize);
            z = true;
        }
        if (z) {
        }
    }
}
