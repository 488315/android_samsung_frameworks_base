package com.samsung.android.biometrics.app.setting.prompt.credential;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public class AuthCredentialPasswordCoverView extends AuthCredentialPasswordView {
    public AuthCredentialPasswordCoverView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void clearErrorMessage() {
        super.clearErrorMessage();
        String charSequence = getDefaultUnlockGuide().toString();
        AuthCredentialEditText authCredentialEditText = this.mPasswordField;
        if (authCredentialEditText == null || charSequence == null) {
            return;
        }
        authCredentialEditText.setHint(charSequence);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialPasswordView, com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void enterAlertMode(int i) {
        super.enterAlertMode(i);
        setTitleVisibility(0);
        setDescriptionVisibility(0);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialPasswordView, com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void exitAlertMode() {
        super.exitAlertMode();
        if (this.mIsBiometricInfoModeOnCover) {
            return;
        }
        setDescriptionVisibility(8);
        if (this.mIsPinType) {
            return;
        }
        setTitleVisibility(8);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final boolean isScreenLandscape() {
        return true;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialPasswordView, com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        String charSequence = getDefaultUnlockGuide().toString();
        AuthCredentialEditText authCredentialEditText = this.mPasswordField;
        if (authCredentialEditText != null && charSequence != null) {
            authCredentialEditText.setHint(charSequence);
        }
        if (this.mIsBiometricInfoModeOnCover) {
            return;
        }
        if (!this.mIsPinType) {
            setTitleVisibility(8);
        }
        setSubTitleVisibility(8);
        setDescriptionVisibility(8);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialPasswordView
    public final void onKeyboardVisibilityChanged(boolean z) {
        super.onKeyboardVisibilityChanged(z);
        if (z) {
            this.mIsBiometricInfoModeOnCover = false;
            if (!this.mIsPinType) {
                setTitleVisibility(8);
            }
            setSubTitleVisibility(8);
            setDescriptionVisibility(8);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialPasswordView
    public final void reactivatePasswordInputFieldAndShowKeyboard() {
        if (!this.mIsBiometricInfoModeOnCover) {
            super.reactivatePasswordInputFieldAndShowKeyboard();
            return;
        }
        AuthCredentialEditText authCredentialEditText = this.mPasswordField;
        if (authCredentialEditText != null) {
            authCredentialEditText.setEnabled(true);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialPasswordView, com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public void setPromptConfig(PromptConfig promptConfig) {
        super.setPromptConfig(promptConfig);
        this.mIsBiometricInfoModeOnCover = promptConfig.mNumberOfAvailableBiometrics == 0;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void showLockoutMessage(String str) {
        AuthCredentialEditText authCredentialEditText = this.mPasswordField;
        if (authCredentialEditText == null || str == null) {
            return;
        }
        authCredentialEditText.setHint(str);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void showRetryMessage() {
        super.showRetryMessage();
        String string =
                ((LinearLayout) this)
                        .mContext.getString(R.string.sec_lockpassword_need_to_unlock_wrong);
        AuthCredentialEditText authCredentialEditText = this.mPasswordField;
        if (authCredentialEditText == null || string == null) {
            return;
        }
        authCredentialEditText.setHint(string);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void resizeMainLayout() {}

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void setDescriptionMargins() {}

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialPasswordView
    public final void updateScrollView() {}
}
