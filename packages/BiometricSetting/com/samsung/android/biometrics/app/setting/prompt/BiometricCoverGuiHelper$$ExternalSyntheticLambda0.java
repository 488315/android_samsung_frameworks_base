package com.samsung.android.biometrics.app.setting.prompt;

import android.view.View;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class BiometricCoverGuiHelper$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BiometricCoverGuiHelper f$0;

    public /* synthetic */ BiometricCoverGuiHelper$$ExternalSyntheticLambda0(BiometricCoverGuiHelper biometricCoverGuiHelper, int i) {
        this.$r8$classId = i;
        this.f$0 = biometricCoverGuiHelper;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.handleOnClickSwitchButton(2);
                break;
            case 1:
                this.f$0.handleOnClickSwitchButton(8);
                break;
            case 2:
                BiometricCoverGuiHelper.m241$r8$lambda$sZ3yAzKtGBERKcoGrCNXoT5G_0(this.f$0);
                break;
            case 3:
                BiometricCoverGuiHelper.$r8$lambda$SUfGDoiSA7N3qtPCMxYFe9wvgq8(this.f$0);
                break;
            default:
                this.f$0.mPromptConfig.getCallback().onConfirmPressed();
                break;
        }
    }
}
