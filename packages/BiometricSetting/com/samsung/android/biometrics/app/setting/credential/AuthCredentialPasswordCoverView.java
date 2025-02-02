package com.samsung.android.biometrics.app.setting.credential;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;

/* loaded from: classes.dex */
public class AuthCredentialPasswordCoverView extends AuthCredentialPasswordView {
    private boolean mIsKeyboardVisible;

    public AuthCredentialPasswordCoverView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final void clearErrorMessage() {
        super.clearErrorMessage();
        String charSequence = getDefaultUnlockGuide().toString();
        AuthCredentialEditText authCredentialEditText = this.mPasswordField;
        if (authCredentialEditText == null || charSequence == null) {
            return;
        }
        authCredentialEditText.setHint(charSequence);
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialPasswordView, com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final void enterAlertMode(int i) {
        super.enterAlertMode(i);
        setTitleVisibility(0);
        setDescriptionVisibility(0);
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialPasswordView, com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final void exitAlertMode() {
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

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final boolean isScreenLandscape() {
        return true;
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialPasswordView, com.samsung.android.biometrics.app.setting.credential.AuthCredentialView, android.view.ViewGroup, android.view.View
    protected final void onAttachedToWindow() {
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

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialPasswordView
    protected final void onKeyboardVisibilityChanged(boolean z) {
        if (this.mIsKeyboardVisible == z) {
            return;
        }
        this.mIsKeyboardVisible = z;
        if (z) {
            this.mIsBiometricInfoModeOnCover = false;
            if (this.mPromptConfig.getCredentialType() == 3) {
                setTitleVisibility(8);
            }
            setSubTitleVisibility(8);
            setDescriptionVisibility(8);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialPasswordView, android.view.View
    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialPasswordView
    protected final void reactivatePasswordInputFieldAndShowKeyboard() {
        if (!this.mIsBiometricInfoModeOnCover) {
            super.reactivatePasswordInputFieldAndShowKeyboard();
            return;
        }
        AuthCredentialEditText authCredentialEditText = this.mPasswordField;
        if (authCredentialEditText != null) {
            authCredentialEditText.setEnabled(true);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialPasswordView, com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    void setPromptConfig(PromptConfig promptConfig) {
        super.setPromptConfig(promptConfig);
        this.mIsBiometricInfoModeOnCover = promptConfig.getNumberOfAvailableBiometrics() == 0;
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final void showLockoutMessage(String str) {
        AuthCredentialEditText authCredentialEditText = this.mPasswordField;
        if (authCredentialEditText == null || str == null) {
            return;
        }
        authCredentialEditText.setHint(str);
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final void showRetryMessage() {
        super.showRetryMessage();
        String string = ((LinearLayout) this).mContext.getString(R.string.sec_lockpassword_need_to_unlock_wrong);
        AuthCredentialEditText authCredentialEditText = this.mPasswordField;
        if (authCredentialEditText == null || string == null) {
            return;
        }
        authCredentialEditText.setHint(string);
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final void applyRotationGui() {
    }
}
