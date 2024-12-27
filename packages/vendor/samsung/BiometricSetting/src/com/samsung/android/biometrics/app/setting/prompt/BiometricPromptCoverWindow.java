package com.samsung.android.biometrics.app.setting.prompt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.samsung.android.biometrics.app.setting.R;

public final class BiometricPromptCoverWindow extends BiometricPromptWindow {
    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptWindow
    public final BiometricPromptGuiHelper createGuiHelper(int i) {
        return new BiometricPromptCoverGuiHelper(
                this.mContext, this.mUIHandler.getLooper(), this.mBaseView, this.mPromptConfig, i);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptWindow
    public final View getBaseView() {
        return LayoutInflater.from(this.mContext)
                .inflate(R.layout.biometric_prompt_generic_cover_dialog, (ViewGroup) null);
    }
}
