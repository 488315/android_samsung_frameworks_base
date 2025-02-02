package com.samsung.android.biometrics.app.setting.prompt;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import com.samsung.android.biometrics.app.setting.R;

/* loaded from: classes.dex */
public final class BiometricPromptCoverWindow extends BiometricPromptWindow {
    public BiometricPromptCoverWindow(Context context, PromptConfig promptConfig, Looper looper) {
        super(context, promptConfig, looper, null);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptWindow
    protected final BiometricPromptGuiHelper createGuiHelper(int i) {
        return new BiometricCoverGuiHelper(getContext(), this.mUIHandler.getLooper(), this.mBaseView, this.mPromptConfig, i);
    }

    @Override // com.samsung.android.biometrics.app.setting.prompt.BiometricPromptWindow
    @SuppressLint({"InflateParams"})
    protected final View getBaseView() {
        return getLayoutInflater().inflate(R.layout.biometric_prompt_generic_cover_dialog, (ViewGroup) null);
    }
}
