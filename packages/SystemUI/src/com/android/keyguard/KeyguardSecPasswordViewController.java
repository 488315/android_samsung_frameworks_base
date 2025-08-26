package com.android.keyguard;

import android.app.SemWallpaperColors;
import android.content.res.Resources;
import android.hardware.biometrics.BiometricSourceType;
import android.os.UserHandle;
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
import com.android.systemui.aibrief.ui.BriefViewController;
import com.android.systemui.bouncer.ui.helper.BouncerHapticPlayer;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.keyguard.KeyguardFoldController;
import com.android.systemui.keyguard.KeyguardFoldControllerImpl;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.DevicePostureController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.util.DeviceType;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.vibrate.VibrationUtil;
import com.android.systemui.wallpaper.WallpaperUtils;
import com.android.systemui.widget.SystemUIImageView;
import com.android.systemui.widget.SystemUITextView;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class KeyguardSecPasswordViewController extends KeyguardPasswordViewController {
    public boolean mIsShownSIP;
    public final KnoxStateMonitor mKnoxStateMonitor;
    public final KeyguardSecPasswordViewController$$ExternalSyntheticLambda1 mOnLayoutChangeListener;
    public final KeyguardSecPasswordViewController$$ExternalSyntheticLambda2 mOnWindowFocusChangeListener;
    public final SystemUIImageView mShowPasswordButton;
    public final KeyguardUpdateMonitorCallback mUpdateMonitorCallbacks;

    public static /* synthetic */ void $r8$lambda$rzhao_6IpdekoE8DgYOjovE2sYI(final KeyguardSecPasswordViewController keyguardSecPasswordViewController, int i) {
        if (((KeyguardSecPasswordView) keyguardSecPasswordViewController.mView).isShown() && keyguardSecPasswordViewController.mPasswordEntry.isEnabled()) {
            keyguardSecPasswordViewController.mPasswordEntry.requestFocus();
            if (keyguardSecPasswordViewController.isHideKeyboardByDefault()) {
                return;
            }
            if ((i != 1 || keyguardSecPasswordViewController.mShowImeAtScreenOn) && !keyguardSecPasswordViewController.mInputMethodManager.showSoftInput(keyguardSecPasswordViewController.mPasswordEntry, 1)) {
                ((KeyguardSecPasswordView) keyguardSecPasswordViewController.mView).postDelayed(new Runnable() { // from class: com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda6
                    @Override // java.lang.Runnable
                    public final void run() {
                        KeyguardSecPasswordViewController keyguardSecPasswordViewController2 = this.f$0;
                        keyguardSecPasswordViewController2.mInputMethodManager.showSoftInput(keyguardSecPasswordViewController2.mPasswordEntry, 1);
                    }
                }, 100L);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda2] */
    public KeyguardSecPasswordViewController(KeyguardSecPasswordView keyguardSecPasswordView, ConfigurationController configurationController, VibrationUtil vibrationUtil, AccessibilityManager accessibilityManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardSecurityModel.SecurityMode securityMode, LockPatternUtils lockPatternUtils, KeyguardSecurityCallback keyguardSecurityCallback, KeyguardMessageAreaController.Factory factory, LatencyTracker latencyTracker, InputMethodManager inputMethodManager, EmergencyButtonController emergencyButtonController, DelayableExecutor delayableExecutor, Resources resources, FalsingCollector falsingCollector, KeyguardViewController keyguardViewController, DevicePostureController devicePostureController, FeatureFlags featureFlags, SelectedUserInteractor selectedUserInteractor, KeyguardKeyboardInteractor keyguardKeyboardInteractor, BouncerHapticPlayer bouncerHapticPlayer, UserActivityNotifier userActivityNotifier) {
        super(keyguardSecPasswordView, configurationController, vibrationUtil, accessibilityManager, keyguardUpdateMonitor, securityMode, lockPatternUtils, keyguardSecurityCallback, factory, latencyTracker, inputMethodManager, emergencyButtonController, delayableExecutor, resources, falsingCollector, keyguardViewController, devicePostureController, featureFlags, selectedUserInteractor, keyguardKeyboardInteractor, bouncerHapticPlayer, userActivityNotifier);
        this.mOnLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) throws Resources.NotFoundException {
                KeyguardSecPasswordViewController keyguardSecPasswordViewController = this.f$0;
                if (i4 == i8 && keyguardSecPasswordViewController.mInputMethodManager.semIsInputMethodShown() == keyguardSecPasswordViewController.mIsShownSIP) {
                    return;
                }
                keyguardSecPasswordViewController.mIsShownSIP = keyguardSecPasswordViewController.mInputMethodManager.semIsInputMethodShown();
                keyguardSecPasswordViewController.setMessageAreaLandscapeAdditionalPadding();
                keyguardSecPasswordViewController.updateSwitchImeButton();
                if (keyguardSecPasswordViewController.mPrevInfoTextContainer != null) {
                    KeyguardUpdateMonitor keyguardUpdateMonitor2 = ((KeyguardAbsKeyInputViewController) keyguardSecPasswordViewController).mKeyguardUpdateMonitor;
                    if (keyguardUpdateMonitor2.isForgotPasswordView() && !keyguardUpdateMonitor2.isDualDisplayPolicyAllowed()) {
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) keyguardSecPasswordViewController.mPrevInfoTextContainer.getLayoutParams();
                        marginLayoutParams.bottomMargin = keyguardSecPasswordViewController.getResources().getDimensionPixelSize(R.dimen.kg_prev_message_area_margin_bottom);
                        marginLayoutParams.topMargin = (keyguardSecPasswordViewController.mIsShownSIP && keyguardSecPasswordViewController.getResources().getConfiguration().orientation == 2) ? keyguardSecPasswordViewController.getResources().getDimensionPixelSize(R.dimen.kg_biometric_view_height) : 0;
                        keyguardSecPasswordViewController.mPrevInfoTextContainer.setLayoutParams(marginLayoutParams);
                    }
                }
                keyguardSecPasswordViewController.updateForgotTextMargin();
            }
        };
        this.mOnWindowFocusChangeListener = new ViewTreeObserver.OnWindowFocusChangeListener() { // from class: com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda2
            @Override // android.view.ViewTreeObserver.OnWindowFocusChangeListener
            public final void onWindowFocusChanged(boolean z) {
                KeyguardSecPasswordViewController keyguardSecPasswordViewController = this.f$0;
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
                if (keyguardSecPasswordViewController.mInputMethodManager != null) {
                    ((KeyguardAbsKeyInputViewController) keyguardSecPasswordViewController).mKeyguardUpdateMonitor.isUnlockingWithBiometricAllowed(z);
                }
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public final void onBiometricLockoutChanged(boolean z) {
                KeyguardSecPasswordViewController keyguardSecPasswordViewController = KeyguardSecPasswordViewController.this;
                keyguardSecPasswordViewController.setMessageAreaLandscapeAdditionalPadding();
                keyguardSecPasswordViewController.updateForgotTextMargin();
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
        int strongAuthPrompt = SecurityUtils.getStrongAuthPrompt(selectedUserInteractor.getSelectedUserId());
        if (keyguardUpdateMonitor.isShowEditModeRequest()) {
            keyguardSecMessageAreaController.setMessage(getContext().getString(R.string.kg_edit_mode_instructions), false);
            if (CscRune.SECURITY_VZW_INSTRUCTION) {
                setSubSecurityMessage(R.string.kg_password_sub_instructions_vzw);
            } else {
                setSubSecurityMessage(R.string.kg_password_sub_instructions);
            }
        } else if (strongAuthPrompt != 0) {
            this.mPromptReason = strongAuthPrompt;
            EmergencyButtonController$$ExternalSyntheticOutline0.m(new StringBuilder("displayDefaultSecurityMessage - strongAuth ( "), this.mPromptReason, " )", "KeyguardSecPasswordViewController");
            showPromptReason(this.mPromptReason);
        } else {
            String defaultSecurityMessage = this.mKeyguardTextBuilder.getDefaultSecurityMessage(KeyguardSecurityModel.SecurityMode.Password);
            if (this.mBouncerMessage.isEmpty() || !this.mBouncerMessage.equals(defaultSecurityMessage)) {
                this.mBouncerMessage = defaultSecurityMessage;
                KeyguardKnoxDualDarInnerPasswordViewController$$ExternalSyntheticOutline0.m("displayDefaultSecurityMessage( ", defaultSecurityMessage, " )", "KeyguardSecPasswordViewController");
                keyguardSecMessageAreaController.setMessage(defaultSecurityMessage, false);
                keyguardSecMessageAreaController.announceForAccessibility(defaultSecurityMessage);
                if (CscRune.SECURITY_VZW_INSTRUCTION) {
                    setSubSecurityMessage(R.string.kg_password_sub_instructions_vzw);
                } else {
                    setSubSecurityMessage(R.string.kg_password_sub_instructions);
                }
            }
        }
        if (keyguardUpdateMonitor.is2StepVerification()) {
            int selectedUserId = selectedUserInteractor.getSelectedUserId();
            if (keyguardUpdateMonitor.getLockoutBiometricAttemptDeadline() > 0) {
                keyguardSecMessageAreaController.setMessage("", false);
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
        boolean zIsWhiteKeyguardWallpaper = WallpaperUtils.isWhiteKeyguardWallpaper(BriefViewController.SUGGESTION_BACKGROUND_KEY);
        if (z) {
            this.mPasswordEntry.setTransformationMethod(PasswordTransformationMethod.getInstance());
            systemUIImageView.setImageResource(zIsWhiteKeyguardWallpaper ? R.drawable.lock_whitebg_password_hide_btn : R.drawable.lock_password_hide_btn);
            systemUIImageView.setStateDescription(getResources().getString(R.string.kg_show_password_accessibility_off));
        } else {
            this.mPasswordEntry.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            systemUIImageView.setImageResource(zIsWhiteKeyguardWallpaper ? R.drawable.lock_whitebg_password_show_btn : R.drawable.lock_password_show_btn);
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
        Iterator it = inputMethodManager.getEnabledInputMethodListAsUser(UserHandle.getUserHandleForUid(this.mSelectedUserInteractor.getSelectedUserId())).iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                if (i > 1) {
                    break;
                }
                return false;
            }
            InputMethodInfo inputMethodInfo = (InputMethodInfo) it.next();
            if (i > 1) {
                break;
            }
            List<InputMethodSubtype> enabledInputMethodSubtypeList = inputMethodManager.getEnabledInputMethodSubtypeList(inputMethodInfo, true);
            enabledInputMethodSubtypeList.size();
            if (!"com.sec.android.inputmethod/.SamsungKeypad".equals(inputMethodInfo.getId()) && !"com.sec.android.inputmethod.beta/com.sec.android.inputmethod.SamsungKeypad".equals(inputMethodInfo.getId())) {
                "com.samsung.android.honeyboard/.SamsungKeypad".equals(inputMethodInfo.getId());
            }
            if (!enabledInputMethodSubtypeList.isEmpty()) {
                Iterator<InputMethodSubtype> it2 = enabledInputMethodSubtypeList.iterator();
                int i2 = 0;
                while (it2.hasNext()) {
                    if (it2.next().isAuxiliary()) {
                        i2++;
                    }
                }
                if (enabledInputMethodSubtypeList.size() - i2 <= 0) {
                }
            }
            i++;
        }
        return true;
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

    @Override // com.android.keyguard.KeyguardPasswordViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public final void onPause() {
        if (((KeyguardPasswordViewController) this).mPaused) {
            return;
        }
        super.onPause();
        this.mInputMethodManager.forceHideSoftInput();
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x006d  */
    @Override // com.android.keyguard.KeyguardPasswordViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onResume(int i) {
        super.onResume(i);
        if (this.mPasswordEntryBoxLayout != null) {
            this.mPasswordEntryBoxLayout.setBackgroundResource(WallpaperUtils.isWhiteKeyguardWallpaper(BriefViewController.SUGGESTION_BACKGROUND_KEY) ? R.drawable.keyguard_security_input_box_whitebg : R.drawable.keyguard_security_input_box);
        }
        if (this.mPasswordEntry != null && getResources().getConfiguration().getLayoutDirection() == 1) {
            this.mPasswordEntry.setPaddingRelative(getResources().getDimensionPixelSize(R.dimen.kg_security_input_box_padding_left), 0, getResources().getDimensionPixelSize(R.dimen.kg_security_input_box_padding_right), 0);
        }
        if (isHideKeyboardByDefault()) {
            this.mSwitchImeButton.setVisibility(8);
        }
        KnoxStateMonitor knoxStateMonitor = this.mKnoxStateMonitor;
        if (knoxStateMonitor != null) {
            EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) knoxStateMonitor).mEdmMonitor;
            if (edmMonitor != null) {
                Log.d("EdmMonitor", "isPasswordVisibilityEnabled ");
                if (!edmMonitor.mPasswordVisibilityEnabled) {
                    Log.d("KeyguardSecPasswordViewController", "<<<--->>> hide button");
                    SystemUIImageView systemUIImageView = this.mShowPasswordButton;
                    if (systemUIImageView != null) {
                        systemUIImageView.setVisibility(8);
                    }
                    EditText editText = this.mPasswordEntry;
                    if (editText != null) {
                        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
                }
            }
        }
        showKeyboard(i);
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
        this.mPasswordEntry.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda3
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
            systemUIImageView.setOnClickListener(new View.OnClickListener(this) { // from class: com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda3
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
    }

    @Override // com.android.keyguard.KeyguardPasswordViewController, com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController, com.android.systemui.util.ViewController
    public final void onViewDetached() {
        super.onViewDetached();
        ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor.removeCallback(this.mUpdateMonitorCallbacks);
        ((KeyguardSecPasswordView) this.mView).removeOnLayoutChangeListener(this.mOnLayoutChangeListener);
        if (LsRune.SECURITY_SUB_DISPLAY_LOCK) {
            ((KeyguardSecPasswordView) this.mView).getViewTreeObserver().removeOnWindowFocusChangeListener(this.mOnWindowFocusChangeListener);
        }
    }

    @Override // com.android.keyguard.KeyguardSecAbsKeyInputViewController, com.android.keyguard.KeyguardAbsKeyInputViewController, com.android.keyguard.KeyguardInputViewController
    public void reset$1() throws Resources.NotFoundException {
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
        SystemUITextView systemUITextView;
        Resources resources = getResources();
        SelectedUserInteractor selectedUserInteractor = this.mSelectedUserInteractor;
        int selectedUserId = selectedUserInteractor.getSelectedUserId();
        KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor;
        if ((keyguardUpdateMonitor.isUnlockWithFacePossible(selectedUserId) || keyguardUpdateMonitor.isUnlockWithFingerprintPossible(selectedUserId)) && (keyguardSecMessageAreaController = this.mMessageAreaController) != null) {
            keyguardSecMessageAreaController.setPadding(0, (this.mIsShownSIP && isLandscapeDisplay() && ((systemUITextView = this.mForgotPasswordText) == null || !systemUITextView.isShown()) && !DeviceType.isTablet() && !keyguardUpdateMonitor.isDualDisplayPolicyAllowed() && (keyguardUpdateMonitor.getLockoutBiometricAttemptDeadline() > 0 || SecurityUtils.getStrongAuthPrompt(selectedUserInteractor.getSelectedUserId()) != 0)) ? (resources.getDimensionPixelSize(R.dimen.kg_message_area_font_size) * 4) + resources.getDimensionPixelSize(R.dimen.kg_biometric_view_height) : 0, 0, 0);
        }
    }

    public final void showKeyboard(final int i) {
        ((KeyguardSecPasswordView) this.mView).postDelayed(new Runnable() { // from class: com.android.keyguard.KeyguardSecPasswordViewController$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                KeyguardSecPasswordViewController.$r8$lambda$rzhao_6IpdekoE8DgYOjovE2sYI(this.f$0, i);
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
                keyguardSecMessageAreaController.setMessage(strongAuthPopupString, false);
                keyguardSecMessageAreaController.scrollTo(0, 0);
            } else {
                if (promptSecurityMessage.isEmpty() || KeyguardTextBuilder.getInstance(getContext()).getStrongAuthTimeOutMessage(securityMode).isEmpty()) {
                    promptSecurityMessage = keyguardTextBuilder.getDefaultSecurityMessage(securityMode);
                }
                keyguardSecMessageAreaController.setMessage(promptSecurityMessage, false);
            }
        }
    }

    public final void updateForgotTextMargin() {
        SystemUITextView systemUITextView = this.mForgotPasswordText;
        if (systemUITextView == null || !systemUITextView.isShown() || DeviceType.isTablet()) {
            return;
        }
        KeyguardUpdateMonitor keyguardUpdateMonitor = ((KeyguardAbsKeyInputViewController) this).mKeyguardUpdateMonitor;
        if (keyguardUpdateMonitor.isDualDisplayPolicyAllowed()) {
            return;
        }
        int dimensionPixelSize = 0;
        boolean z = keyguardUpdateMonitor.getLockoutBiometricAttemptDeadline() > 0 || SecurityUtils.getStrongAuthPrompt(this.mSelectedUserInteractor.getSelectedUserId()) != 0;
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) systemUITextView.getLayoutParams();
        if (this.mIsShownSIP && z && getResources().getConfiguration().orientation == 2) {
            dimensionPixelSize = (getResources().getDimensionPixelSize(R.dimen.kg_message_area_font_size) * 4) + getResources().getDimensionPixelSize(R.dimen.kg_biometric_view_height);
        }
        marginLayoutParams.topMargin = dimensionPixelSize;
        systemUITextView.setLayoutParams(marginLayoutParams);
    }

    @Override // com.android.systemui.widget.SystemUIWidgetCallback
    public final void updateStyle(long j, SemWallpaperColors semWallpaperColors) {
        if (skipUpdateWhenCloseFolder() || this.mPasswordEntryBoxLayout == null) {
            return;
        }
        this.mPasswordEntryBoxLayout.setBackgroundResource(WallpaperUtils.isWhiteKeyguardWallpaper(BriefViewController.SUGGESTION_BACKGROUND_KEY) ? R.drawable.keyguard_security_input_box_whitebg : R.drawable.keyguard_security_input_box);
    }

    @Override // com.android.keyguard.KeyguardPasswordViewController
    public final void updateSwitchImeButton() throws Resources.NotFoundException {
        if (this.mSwitchImeButton == null) {
            Log.e("KeyguardSecPasswordViewController", "mSwitchImeButton is null");
            return;
        }
        boolean zHasMultipleEnabledIMEsOrSubtypes = hasMultipleEnabledIMEsOrSubtypes(this.mInputMethodManager);
        boolean z = this.mSwitchImeButton.getVisibility() == 0;
        boolean z2 = this.mIsShownSIP && zHasMultipleEnabledIMEsOrSubtypes;
        KeyguardSecPasswordViewController$$ExternalSyntheticOutline0.m(EmergencyButtonController$$ExternalSyntheticOutline0.m("updateSwitchImeButton, wasVisible = ", ", shouldBeVisible = ", ", needImeBtn = ", z, z2), zHasMultipleEnabledIMEsOrSubtypes, "KeyguardSecPasswordViewController");
        if (z != z2) {
            this.mSwitchImeButton.setVisibility(z2 ? 0 : 8);
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
