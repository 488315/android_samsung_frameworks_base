package com.android.keyguard;

import android.app.SemWallpaperColors;
import android.content.res.Resources;
import android.hardware.biometrics.BiometricSourceType;
import android.net.Uri;
import android.os.UserHandle;
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
import android.widget.LinearLayout;
import com.android.internal.util.LatencyTracker;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.KeyguardMessageAreaController;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.domain.interactor.KeyguardKeyboardInteractor;
import com.android.systemui.CscRune;
import com.android.systemui.Dependency;
import com.android.systemui.LsRune;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.vibrate.VibrationUtil;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIImageView;
import java.util.Iterator;
import java.util.List;

public class KeyguardSecPasswordViewController extends KeyguardPasswordViewController {
    public boolean mIsShownSIP;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public final KeyguardSecPasswordViewController$$ExternalSyntheticLambda2 mOnLayoutChangeListener;
    public final KeyguardSecPasswordViewController$$ExternalSyntheticLambda3 mOnWindowFocusChangeListener;
    private SettingsHelper.OnChangedCallback mSettingsListener;
    public final SystemUIImageView mShowPasswordButton;
    public final KeyguardUpdateMonitorCallback mUpdateMonitorCallbacks;

    public static /* synthetic */ void $r8$lambda$IllLLJgt3WVQL4g_jzNcNLAdgo0(final KeyguardSecPasswordViewController keyguardSecPasswordViewController, int i) {
        if (((KeyguardSecPasswordView) keyguardSecPasswordViewController.mView).isShown() && keyguardSecPasswordViewController.mPasswordEntry.isEnabled()) {
            keyguardSecPasswordViewController.mPasswordEntry.requestFocus();
            if (keyguardSecPasswordViewController.isHideKeyboardByDefault()) {
                return;
            }
            if ((i != 1 || keyguardSecPasswordViewController.mShowImeAtScreenOn) && !keyguardSecPasswordViewController.mInputMethodManager.showSoftInput(keyguardSecPasswordViewController.mPasswordEntry, 1)) {
                ((KeyguardSecPasswordView) keyguardSecPasswordViewController.mView).postDelayed(new Runnable() { // from class: com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda8
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardSecPasswordViewController keyguardSecPasswordViewController2 = KeyguardSecPasswordViewController.this;
                        keyguardSecPasswordViewController2.mInputMethodManager.showSoftInput(keyguardSecPasswordViewController2.mPasswordEntry, 1);
                    }
                }, 100L);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda2] */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda3] */
    public KeyguardSecPasswordViewController(KeyguardSecPasswordView keyguardSecPasswordView, ConfigurationController configurationController, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, InputMethodManager inputMethodManager, EmergencyButtonController emergencyButtonController, DelayableExecutor delayableExecutor, Resources resources, FalsingCollector falsingCollector, KeyguardViewController keyguardViewController, DevicePostureController devicePostureController, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor, KeyguardKeyboardInteractor keyguardKeyboardInteractor) {
        super(keyguardSecPasswordView, configurationController, vibrationUtil, accessibilityManager, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, inputMethodManager, emergencyButtonController, delayableExecutor, resources, falsingCollector, keyguardViewController, devicePostureController, featureFlags, selectedUserInteractor, keyguardKeyboardInteractor);
        this.mOnLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda2
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                KeyguardSecPasswordViewController keyguardSecPasswordViewController = KeyguardSecPasswordViewController.this;
                if (i4 == i8 && keyguardSecPasswordViewController.mInputMethodManager.semIsInputMethodShown() == keyguardSecPasswordViewController.mIsShownSIP) {
                    return;
                }
                keyguardSecPasswordViewController.mIsShownSIP = keyguardSecPasswordViewController.mInputMethodManager.semIsInputMethodShown();
                keyguardSecPasswordViewController.setMessageAreaLandscapeAdditionalPadding();
                if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowKeyboardButton()) {
                    if (!keyguardSecPasswordViewController.mIsShownSIP) {
                        keyguardSecPasswordViewController.mSwitchImeButton.setVisibility(8);
                    }
                    keyguardSecPasswordViewController.updateSwitchImeButton();
                }
                if (keyguardSecPasswordViewController.mPrevInfoTextContainer != null) {
                    KeyguardUpdateMonitor keyguardUpdateMonitor2 = ((KeyguardAbsKeyInputViewController) keyguardSecPasswordViewController).mKeyguardUpdateMonitor;
                    if (!keyguardUpdateMonitor2.isForgotPasswordView() || keyguardUpdateMonitor2.isDualDisplayPolicyAllowed()) {
                        return;
                    }
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) keyguardSecPasswordViewController.mPrevInfoTextContainer.getLayoutParams();
                    marginLayoutParams.bottomMargin = keyguardSecPasswordViewController.getResources().getDimensionPixelSize(R.dimen.kg_prev_message_area_margin_bottom);
                    marginLayoutParams.topMargin = (keyguardSecPasswordViewController.mIsShownSIP && keyguardSecPasswordViewController.getResources().getConfiguration().orientation == 2) ? keyguardSecPasswordViewController.getResources().getDimensionPixelSize(R.dimen.kg_biometric_view_height) : 0;
                    keyguardSecPasswordViewController.mPrevInfoTextContainer.setLayoutParams(marginLayoutParams);
                }
            }
        };
        this.mOnWindowFocusChangeListener = new ViewTreeObserver.OnWindowFocusChangeListener() { // from class: com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda3
            @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
            public final void onWindowFocusChanged(boolean z) {
                KeyguardSecPasswordViewController keyguardSecPasswordViewController = KeyguardSecPasswordViewController.this;
                keyguardSecPasswordViewController.getClass();
                if (z && ((KeyguardFoldControllerImpl) ((KeyguardFoldController) Dependency.sDependency.getDependencyInner(KeyguardFoldController.class))).isBouncerOnFoldOpened()) {
                    keyguardSecPasswordViewController.showKeyboard(0);
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
                KeyguardSecPasswordViewController.this.setMessageAreaLandscapeAdditionalPadding();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricRunningStateChanged(boolean z, BiometricSourceType biometricSourceType) {
                KeyguardSecPasswordViewController keyguardSecPasswordViewController = KeyguardSecPasswordViewController.this;
                if (((KeyguardAbsKeyInputViewController) keyguardSecPasswordViewController).mKeyguardUpdateMonitor.is2StepVerification() && biometricSourceType == BiometricSourceType.FINGERPRINT && keyguardSecPasswordViewController.mBouncerShowing && !z && keyguardSecPasswordViewController.mPasswordEntry.isEnabled()) {
                    keyguardSecPasswordViewController.mPasswordEntry.requestFocus();
                    keyguardSecPasswordViewController.mInputMethodManager.showSoftInput(keyguardSecPasswordViewController.mPasswordEntry, 1);
                }
            }
        };
        this.mKnoxStateMonitor = (KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class);
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
        SelectedUserInteractor selectedUserInteractor = this.mSelectedUserInteractor;
        int strongAuthPrompt = SecurityUtils.getStrongAuthPrompt(selectedUserInteractor.getSelectedUserId(false));
        if (keyguardUpdateMonitor.isShowEditModeRequest()) {
            keyguardSecMessageAreaController.setMessage$1(getContext().getString(R.string.kg_edit_mode_instructions), false);
            if (CscRune.SECURITY_VZW_INSTRUCTION) {
                setSubSecurityMessage(R.string.kg_password_sub_instructions_vzw);
            } else {
                setSubSecurityMessage(R.string.kg_password_sub_instructions);
            }
        } else {
            if (strongAuthPrompt != 0) {
                this.mPromptReason = strongAuthPrompt;
                KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(new StringBuilder("displayDefaultSecurityMessage - strongAuth ( "), this.mPromptReason, " )", "KeyguardSecPasswordViewController");
                showPromptReason(this.mPromptReason);
            } else {
                String defaultSecurityMessage = this.mKeyguardTextBuilder.getDefaultSecurityMessage(KeyguardSecurityModel.SecurityMode.Password);
                if (this.mBouncerMessage.isEmpty() || !this.mBouncerMessage.equals(defaultSecurityMessage)) {
                    this.mBouncerMessage = defaultSecurityMessage;
                    KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("displayDefaultSecurityMessage( ", defaultSecurityMessage, " )", "KeyguardSecPasswordViewController");
                    keyguardSecMessageAreaController.setMessage$1(defaultSecurityMessage, false);
                    keyguardSecMessageAreaController.announceForAccessibility(defaultSecurityMessage);
                    if (CscRune.SECURITY_VZW_INSTRUCTION) {
                        setSubSecurityMessage(R.string.kg_password_sub_instructions_vzw);
                    } else {
                        setSubSecurityMessage(R.string.kg_password_sub_instructions);
                    }
                }
            }
        }
        if (keyguardUpdateMonitor.is2StepVerification()) {
            int selectedUserId = selectedUserInteractor.getSelectedUserId(false);
            if (keyguardUpdateMonitor.getLockoutBiometricAttemptDeadline() > 0) {
                keyguardSecMessageAreaController.setMessage$1("", false);
            }
            if (keyguardUpdateMonitor.getUserUnlockedWithBiometric(selectedUserId)) {
                setSubSecurityMessage(R.string.kg_biometrics_has_confirmed);
            } else {
                setSubSecurityMessage(0);
            }
        }
    }

    public final void enableHidingPassword(boolean z) {
        SystemUIImageView systemUIImageView;
        if (this.mPasswordEntry == null || (systemUIImageView = this.mShowPasswordButton) == null) {
            Log.e("KeyguardSecPasswordViewController", "enableHidingPassword() view is null");
            return;
        }
        boolean isWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper("background");
        if (z) {
            this.mPasswordEntry.setTransformationMethod(PasswordTransformationMethod.getInstance());
            systemUIImageView.setImageResource(isWhiteKeyguardWallpaper ? R.drawable.lock_whitebg_password_hide_btn : R.drawable.lock_password_hide_btn);
            systemUIImageView.setStateDescription(getResources().getString(R.string.kg_show_password_accessibility_off));
        } else {
            this.mPasswordEntry.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
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
        for (InputMethodInfo inputMethodInfo : inputMethodManager.getEnabledInputMethodListAsUser(UserHandle.getUserHandleForUid(this.mSelectedUserInteractor.getSelectedUserId(false)))) {
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

    /* JADX WARN: Code restructure failed: missing block: B:22:0x007a, code lost:
    
        if (r0.mPasswordVisibilityEnabled != false) goto L32;
     */
    @Override // com.android.keyguard.KeyguardPasswordViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onResume(int r5) {
        /*
            r4 = this;
            super.onResume(r5)
            android.view.ViewGroup r0 = r4.mPasswordEntryBoxLayout
            if (r0 == 0) goto L1b
            java.lang.String r0 = "background"
            boolean r0 = com.android.systemui.wallpaper.WallpaperUtils.isWhiteKeyguardWallpaper(r0)
            android.view.ViewGroup r1 = r4.mPasswordEntryBoxLayout
            if (r0 == 0) goto L15
            r0 = 2131233695(0x7f080b9f, float:1.8083535E38)
            goto L18
        L15:
            r0 = 2131233694(0x7f080b9e, float:1.8083533E38)
        L18:
            r1.setBackgroundResource(r0)
        L1b:
            android.widget.EditText r0 = r4.mPasswordEntry
            if (r0 == 0) goto L4a
            android.content.res.Resources r0 = r4.getResources()
            android.content.res.Configuration r0 = r0.getConfiguration()
            int r0 = r0.getLayoutDirection()
            r1 = 1
            if (r0 != r1) goto L4a
            android.widget.EditText r0 = r4.mPasswordEntry
            android.content.res.Resources r1 = r4.getResources()
            r2 = 2131166654(0x7f0705be, float:1.794756E38)
            int r1 = r1.getDimensionPixelSize(r2)
            android.content.res.Resources r2 = r4.getResources()
            r3 = 2131166656(0x7f0705c0, float:1.7947564E38)
            int r2 = r2.getDimensionPixelSize(r3)
            r3 = 0
            r0.setPaddingRelative(r1, r3, r2, r3)
        L4a:
            com.android.systemui.Dependency r0 = com.android.systemui.Dependency.sDependency
            java.lang.Class<com.android.systemui.util.SettingsHelper> r1 = com.android.systemui.util.SettingsHelper.class
            java.lang.Object r0 = r0.getDependencyInner(r1)
            com.android.systemui.util.SettingsHelper r0 = (com.android.systemui.util.SettingsHelper) r0
            boolean r0 = r0.isShowKeyboardButton()
            r1 = 8
            if (r0 == 0) goto L62
            boolean r0 = r4.isHideKeyboardByDefault()
            if (r0 == 0) goto L67
        L62:
            android.widget.ImageView r0 = r4.mSwitchImeButton
            r0.setVisibility(r1)
        L67:
            com.android.systemui.knox.KnoxStateMonitor r0 = r4.mKnoxStateMonitor
            if (r0 == 0) goto L96
            com.android.systemui.knox.KnoxStateMonitorImpl r0 = (com.android.systemui.knox.KnoxStateMonitorImpl) r0
            com.android.systemui.knox.EdmMonitor r0 = r0.mEdmMonitor
            if (r0 == 0) goto L7d
            java.lang.String r2 = "EdmMonitor"
            java.lang.String r3 = "isPasswordVisibilityEnabled "
            android.util.Log.d(r2, r3)
            boolean r0 = r0.mPasswordVisibilityEnabled
            if (r0 == 0) goto L7d
            goto L96
        L7d:
            java.lang.String r0 = "KeyguardSecPasswordViewController"
            java.lang.String r2 = "<<<--->>> hide button"
            android.util.Log.d(r0, r2)
            com.android.systemui.widget.SystemUIImageView r0 = r4.mShowPasswordButton
            if (r0 == 0) goto L8b
            r0.setVisibility(r1)
        L8b:
            android.widget.EditText r0 = r4.mPasswordEntry
            if (r0 == 0) goto L96
            android.text.method.PasswordTransformationMethod r1 = android.text.method.PasswordTransformationMethod.getInstance()
            r0.setTransformationMethod(r1)
        L96:
            r4.showKeyboard(r5)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardSecPasswordViewController.onResume(int):void");
    }

    @Override // com.android.keyguard.KeyguardPasswordViewController, com.android.keyguard.KeyguardSecurityView
    public final void onStartingToHide() {
        this.mInputMethodManager.hideSoftInputFromWindow(((KeyguardSecPasswordView) this.mView).getWindowToken(), 0);
    }

    @Override // com.android.keyguard.KeyguardPasswordViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewAttached() {
        super.onViewAttached();
        ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor.registerCallback(this.mUpdateMonitorCallbacks);
        ((KeyguardSecPasswordView) this.mView).addOnLayoutChangeListener(this.mOnLayoutChangeListener);
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            ((KeyguardSecPasswordView) this.mView).getViewTreeObserver().addOnWindowFocusChangeListener(this.mOnWindowFocusChangeListener);
        }
        final int i = 0;
        this.mPasswordEntry.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda4
            public final /* synthetic */ KeyguardSecPasswordViewController f$0;

            {
                this.f$0 = this;
            }

            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                int i2 = i;
                KeyguardSecPasswordViewController keyguardSecPasswordViewController = this.f$0;
                switch (i2) {
                    case 0:
                        keyguardSecPasswordViewController.getKeyguardSecurityCallback().userActivity();
                        break;
                    default:
                        keyguardSecPasswordViewController.getKeyguardSecurityCallback().userActivity();
                        keyguardSecPasswordViewController.enableHidingPassword(keyguardSecPasswordViewController.mPasswordEntry.getTransformationMethod() == HideReturnsTransformationMethod.getInstance());
                        EditText editText = keyguardSecPasswordViewController.mPasswordEntry;
                        editText.setAccessibilitySelection(editText.getText().length(), keyguardSecPasswordViewController.mPasswordEntry.getText().length());
                        break;
                }
            }
        });
        this.mPasswordEntry.setLongClickable(false);
        SystemUIImageView systemUIImageView = this.mShowPasswordButton;
        if (systemUIImageView != null) {
            systemUIImageView.setContentDescription(getResources().getString(R.string.kg_show_password_accessibility));
            systemUIImageView.setPointerIcon(PointerIcon.getSystemIcon(getContext(), 1000));
            final int i2 = 1;
            systemUIImageView.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda4
                public final /* synthetic */ KeyguardSecPasswordViewController f$0;

                {
                    this.f$0 = this;
                }

                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    int i22 = i2;
                    KeyguardSecPasswordViewController keyguardSecPasswordViewController = this.f$0;
                    switch (i22) {
                        case 0:
                            keyguardSecPasswordViewController.getKeyguardSecurityCallback().userActivity();
                            break;
                        default:
                            keyguardSecPasswordViewController.getKeyguardSecurityCallback().userActivity();
                            keyguardSecPasswordViewController.enableHidingPassword(keyguardSecPasswordViewController.mPasswordEntry.getTransformationMethod() == HideReturnsTransformationMethod.getInstance());
                            EditText editText = keyguardSecPasswordViewController.mPasswordEntry;
                            editText.setAccessibilitySelection(editText.getText().length(), keyguardSecPasswordViewController.mPasswordEntry.getText().length());
                            break;
                    }
                }
            });
        }
        if (this.mAccessibilityManager.isTouchExplorationEnabled()) {
            this.mPasswordEntry.setSelected(false);
        }
        this.mSettingsListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda6
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                KeyguardSecPasswordViewController keyguardSecPasswordViewController = KeyguardSecPasswordViewController.this;
                keyguardSecPasswordViewController.getClass();
                if (!uri.equals(Settings.Secure.getUriFor(SettingsHelper.INDEX_SHOW_KEYBOARD_BUTTON)) || ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowKeyboardButton()) {
                    return;
                }
                SystemUIImageView systemUIImageView2 = keyguardSecPasswordViewController.mShowPasswordButton;
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) systemUIImageView2.getLayoutParams();
                marginLayoutParams.setMarginEnd(keyguardSecPasswordViewController.getResources().getDimensionPixelSize(R.dimen.kg_security_show_password_side_margin));
                systemUIImageView2.setLayoutParams(marginLayoutParams);
                keyguardSecPasswordViewController.mSwitchImeButton.setVisibility(8);
            }
        };
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(this.mSettingsListener, Settings.Secure.getUriFor(SettingsHelper.INDEX_SHOW_KEYBOARD_BUTTON));
    }

    @Override // com.android.keyguard.KeyguardPasswordViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor.removeCallback(this.mUpdateMonitorCallbacks);
        ((KeyguardSecPasswordView) this.mView).removeOnLayoutChangeListener(this.mOnLayoutChangeListener);
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).unregisterCallback(this.mSettingsListener);
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            ((KeyguardSecPasswordView) this.mView).getViewTreeObserver().removeOnWindowFocusChangeListener(this.mOnWindowFocusChangeListener);
        }
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void reset$1() {
        super.reset$1();
        enableHidingPassword(true);
    }

    @Override // com.android.keyguard.KeyguardPasswordViewController, com.android.keyguard.KeyguardAbsKeyInputViewController
    public void resetState() {
        displayDefaultSecurityMessage();
        resetFor2StepVerification();
    }

    public final void setMessageAreaLandscapeAdditionalPadding() {
        KeyguardSecMessageAreaController keyguardSecMessageAreaController;
        Resources resources = getResources();
        SelectedUserInteractor selectedUserInteractor = this.mSelectedUserInteractor;
        int selectedUserId = selectedUserInteractor.getSelectedUserId(false);
        KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor;
        if ((keyguardUpdateMonitor.isUnlockWithFacePossible(selectedUserId) || keyguardUpdateMonitor.isUnlockWithFingerprintPossible(selectedUserId)) && (keyguardSecMessageAreaController = this.mMessageAreaController) != null) {
            keyguardSecMessageAreaController.setPadding(0, (!this.mIsShownSIP || !isLandscapeDisplay() || DeviceType.isTablet() || keyguardUpdateMonitor.isDualDisplayPolicyAllowed() || (keyguardUpdateMonitor.getLockoutBiometricAttemptDeadline() <= 0 && SecurityUtils.getStrongAuthPrompt(selectedUserInteractor.getSelectedUserId(false)) == 0)) ? 0 : (resources.getDimensionPixelSize(R.dimen.kg_message_area_font_size) * 4) + resources.getDimensionPixelSize(R.dimen.kg_biometric_view_height), 0, 0);
        }
    }

    public final void showKeyboard(final int i) {
        ((KeyguardSecPasswordView) this.mView).postDelayed(new Runnable() { // from class: com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda7
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardSecPasswordViewController.$r8$lambda$IllLLJgt3WVQL4g_jzNcNLAdgo0(KeyguardSecPasswordViewController.this, i);
            }
        }, 100L);
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
                if (CscRune.SECURITY_VZW_INSTRUCTION) {
                    setSubSecurityMessage(R.string.kg_password_sub_instructions_vzw);
                } else {
                    setSubSecurityMessage(R.string.kg_password_sub_instructions);
                }
            }
            SpannableStringBuilder strongAuthPopupString = SecurityUtils.getStrongAuthPopupString(getContext(), securityMode, this.mPasswordEntry, i);
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

    @Override // com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        if (skipUpdateWhenCloseFolder() || this.mPasswordEntryBoxLayout == null) {
            return;
        }
        this.mPasswordEntryBoxLayout.setBackgroundResource(WallpaperUtils.isWhiteKeyguardWallpaper("background") ? R.drawable.keyguard_security_input_box_whitebg : R.drawable.keyguard_security_input_box);
    }

    @Override // com.android.keyguard.KeyguardPasswordViewController
    public final void updateSwitchImeButton() {
        if (this.mSwitchImeButton == null) {
            Log.e("KeyguardSecPasswordViewController", "mSwitchImeButton is null");
            return;
        }
        boolean hasMultipleEnabledIMEsOrSubtypes = hasMultipleEnabledIMEsOrSubtypes(this.mInputMethodManager);
        boolean z = this.mSwitchImeButton.getVisibility() == 0;
        boolean z2 = this.mIsShownSIP && hasMultipleEnabledIMEsOrSubtypes;
        KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("updateSwitchImeButton, wasVisible = ", ", shouldBeVisible = ", ", needImeBtn = ", z, z2), hasMultipleEnabledIMEsOrSubtypes, "KeyguardSecPasswordViewController");
        if (!((DesktopManager) Dependency.sDependency.getDependencyInner(DesktopManager.class)).isDesktopMode() && z != z2) {
            this.mSwitchImeButton.setVisibility(z2 ? 0 : 8);
        }
        if (!((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isShowKeyboardButton()) {
            this.mSwitchImeButton.setVisibility(8);
        }
        SystemUIImageView systemUIImageView = this.mShowPasswordButton;
        if (systemUIImageView != null) {
            Resources resources = getResources();
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) systemUIImageView.getLayoutParams();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.kg_security_show_password_side_margin);
            if (this.mSwitchImeButton.getVisibility() == 0) {
                if (marginLayoutParams.getMarginEnd() != dimensionPixelSize) {
                    return;
                } else {
                    marginLayoutParams.setMarginEnd(resources.getDimensionPixelSize(R.dimen.kg_security_ime_button_side_margin) + resources.getDimensionPixelSize(R.dimen.kg_security_show_password_side_margin_for_ime_button) + resources.getDimensionPixelSize(R.dimen.kg_security_ime_button_width));
                }
            } else if (marginLayoutParams.getMarginEnd() == dimensionPixelSize) {
                return;
            } else {
                marginLayoutParams.setMarginEnd(dimensionPixelSize);
            }
            systemUIImageView.setLayoutParams(marginLayoutParams);
        }
    }
}
