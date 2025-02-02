package com.samsung.android.biometrics.app.setting.credential;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.samsung.android.biometrics.app.setting.R;
import com.samsung.android.biometrics.app.setting.prompt.PromptConfig;

/* loaded from: classes.dex */
public class AuthCredentialPatternCoverView extends AuthCredentialPatternView {
    public AuthCredentialPatternCoverView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final void clearErrorMessage() {
        super.clearErrorMessage();
        if (this.mIsBiometricInfoModeOnCover) {
            setErrorTextVisibility(8);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final void disableButton(Button button) {
        super.disableButton(button);
        if (button != null) {
            button.setVisibility(8);
        }
        View findViewById = findViewById(R.id.btn_layout);
        if (findViewById != null) {
            findViewById.setVisibility(8);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final void enableButton(Button button) {
        super.enableButton(button);
        View findViewById = findViewById(R.id.btn_layout);
        if (findViewById != null) {
            findViewById.setVisibility(0);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialPatternView, com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final void enterAlertMode(int i) {
        super.enterAlertMode(i);
        setPatternViewVisibility(8);
        setTitleVisibility(0);
        setDescriptionVisibility(0);
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialPatternView, com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final void exitAlertMode() {
        super.exitAlertMode();
        setPatternViewVisibility(0);
        setTitleVisibility(8);
        setDescriptionVisibility(8);
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialPatternView
    protected final void hidePattern() {
        setPatternViewVisibility(8);
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final boolean isScreenLandscape() {
        return true;
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialPatternView, com.samsung.android.biometrics.app.setting.credential.AuthCredentialView, android.view.ViewGroup, android.view.View
    protected final void onAttachedToWindow() {
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
            button.setText(((LinearLayout) this).mContext.getString(R.string.biometric_dialog_use_pattern));
        }
        enableButton(this.mBtnContinue);
        enableButton(this.mBtnCancel);
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final void onContinueButtonClicked() {
        this.mIsBiometricInfoModeOnCover = false;
        setTitleVisibility(8);
        setSubTitleVisibility(8);
        setDescriptionVisibility(8);
        setPatternViewVisibility(0);
        setErrorTextVisibility(0);
        disableButton(this.mBtnContinue);
        disableButton(this.mBtnCancel);
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialPatternView, com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final void onLockoutTimeoutFinish() {
        super.onLockoutTimeoutFinish();
        if (this.mIsBiometricInfoModeOnCover) {
            setErrorTextVisibility(8);
            setPatternViewVisibility(8);
            this.mBtnContinue.setEnabled(true);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialPatternView, com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final void onLockoutTimeoutStart() {
        hidePattern();
        Button button = this.mBtnContinue;
        if (button != null) {
            button.setEnabled(false);
        }
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    void setPromptConfig(PromptConfig promptConfig) {
        super.setPromptConfig(promptConfig);
        this.mIsBiometricInfoModeOnCover = promptConfig.getNumberOfAvailableBiometrics() == 0;
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final void showLockoutMessage(String str) {
        setErrorTextVisibility(0);
        super.showLockoutMessage(str);
    }

    @Override // com.samsung.android.biometrics.app.setting.credential.AuthCredentialView
    protected final void applyRotationGui() {
    }
}
