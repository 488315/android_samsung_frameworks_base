package com.samsung.android.biometrics.app.setting.prompt.credential;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
public class AuthCredentialPatternCoverView extends AuthCredentialPatternView {
    public AuthCredentialPatternCoverView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void clearErrorMessage() {
        super.clearErrorMessage();
        if (this.mIsBiometricInfoModeOnCover) {
            setErrorTextVisibility(8);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void disableButton(Button button) {
        super.disableButton(button);
        if (button != null) {
            button.setVisibility(8);
        }
        View findViewById = findViewById(R.id.btn_layout);
        if (findViewById != null) {
            findViewById.setVisibility(8);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void enableButton(Button button) {
        super.enableButton(button);
        View findViewById = findViewById(R.id.btn_layout);
        if (findViewById != null) {
            findViewById.setVisibility(0);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialPatternView, com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void enterAlertMode(int i) {
        super.enterAlertMode(i);
        setPatternViewVisibility(8);
        setTitleVisibility(0);
        setDescriptionVisibility(0);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialPatternView, com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void exitAlertMode() {
        super.exitAlertMode();
        setPatternViewVisibility(0);
        setTitleVisibility(8);
        setDescriptionVisibility(8);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialPatternView
    public final void hidePattern() {
        setPatternViewVisibility(8);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final boolean isScreenLandscape() {
        return true;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialPatternView, com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!this.mIsBiometricInfoModeOnCover) {
            setTitleVisibility(8);
            setSubTitleVisibility(8);
            setDescriptionVisibility(8);
            setErrorTextVisibility(0);
            return;
        }
        setPatternViewVisibility(8);
        Button button = this.mBtnContinue;
        if (button != null) {
            button.setText(
                    ((LinearLayout) this)
                            .mContext.getString(R.string.biometric_dialog_use_pattern));
        }
        enableButton(this.mBtnContinue);
        enableButton(this.mBtnCancel);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void onContinueButtonClicked() {
        this.mIsBiometricInfoModeOnCover = false;
        setTitleVisibility(8);
        setSubTitleVisibility(8);
        setDescriptionVisibility(8);
        setPatternViewVisibility(0);
        setErrorTextVisibility(0);
        disableButton(this.mBtnContinue);
        disableButton(this.mBtnCancel);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialPatternView, com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void onLockoutTimeoutFinish() {
        super.onLockoutTimeoutFinish();
        if (this.mIsBiometricInfoModeOnCover) {
            setErrorTextVisibility(8);
            setPatternViewVisibility(8);
            this.mBtnContinue.setEnabled(true);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialPatternView, com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void onLockoutTimeoutStart() {
        hidePattern();
        Button button = this.mBtnContinue;
        if (button != null) {
            button.setEnabled(false);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public void setPromptConfig(PromptConfig promptConfig) {
        super.setPromptConfig(promptConfig);
        this.mIsBiometricInfoModeOnCover = promptConfig.mNumberOfAvailableBiometrics == 0;
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void showLockoutMessage(String str) {
        setErrorTextVisibility(0);
        super.showLockoutMessage(str);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void resizeMainLayout() {}

    @Override // com.samsung.android.biometrics.app.setting.prompt.credential.AuthCredentialView
    public final void setDescriptionMargins() {}
}
