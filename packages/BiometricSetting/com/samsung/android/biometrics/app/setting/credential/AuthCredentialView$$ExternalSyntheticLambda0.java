package com.samsung.android.biometrics.app.setting.credential;

import android.os.Handler;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class AuthCredentialView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AuthCredentialView f$0;

    public /* synthetic */ AuthCredentialView$$ExternalSyntheticLambda0(AuthCredentialView authCredentialView, int i) {
        this.$r8$classId = i;
        this.f$0 = authCredentialView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.clearErrorMessage();
                break;
            case 1:
                this.f$0.onLockoutTimeoutStart();
                break;
            case 2:
                AuthCredentialView authCredentialView = this.f$0;
                authCredentialView.enterAlertMode(authCredentialView.mAlertMode);
                break;
            case 3:
                AuthCredentialView authCredentialView2 = this.f$0;
                int i = AuthCredentialView.$r8$clinit;
                authCredentialView2.enterAlertMode(1);
                break;
            case 4:
                AuthCredentialView authCredentialView3 = this.f$0;
                int i2 = AuthCredentialView.$r8$clinit;
                authCredentialView3.enterAlertMode(2);
                Handler handler = authCredentialView3.mHandler;
                AuthCredentialView$$ExternalSyntheticLambda0 authCredentialView$$ExternalSyntheticLambda0 = new AuthCredentialView$$ExternalSyntheticLambda0(authCredentialView3, 5);
                authCredentialView3.mPromptConfig.getClass();
                handler.postDelayed(authCredentialView$$ExternalSyntheticLambda0, 3000L);
                break;
            default:
                AuthCredentialView.$r8$lambda$yPTQs_IlP1Y3Z_Rni8lKBlSRriA(this.f$0);
                break;
        }
    }
}
