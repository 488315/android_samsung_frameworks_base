package com.samsung.android.biometrics.app.setting.prompt.credential;

import android.os.Handler;

import com.samsung.android.biometrics.app.setting.prompt.BiometricPromptClient;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
public final /* synthetic */ class AuthCredentialView$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AuthCredentialView f$0;

    public /* synthetic */ AuthCredentialView$$ExternalSyntheticLambda0(
            AuthCredentialView authCredentialView, int i) {
        this.$r8$classId = i;
        this.f$0 = authCredentialView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 5;
        int i2 = this.$r8$classId;
        AuthCredentialView authCredentialView = this.f$0;
        switch (i2) {
            case 0:
                authCredentialView.clearErrorMessage();
                break;
            case 1:
                authCredentialView.enterAlertMode(authCredentialView.mAlertMode);
                break;
            case 2:
                int i3 = AuthCredentialView.$r8$clinit;
                authCredentialView.enterAlertMode(1);
                break;
            case 3:
                int i4 = AuthCredentialView.$r8$clinit;
                authCredentialView.enterAlertMode(2);
                Handler handler = authCredentialView.mHandler;
                AuthCredentialView$$ExternalSyntheticLambda0
                        authCredentialView$$ExternalSyntheticLambda0 =
                                new AuthCredentialView$$ExternalSyntheticLambda0(
                                        authCredentialView, i);
                authCredentialView.mPromptConfig.getClass();
                handler.postDelayed(authCredentialView$$ExternalSyntheticLambda0, 3000L);
                break;
            case 4:
                authCredentialView.onLockoutTimeoutStart();
                break;
            case 5:
                if (!authCredentialView.mIsDetachedFromWindow) {
                    ((BiometricPromptClient) authCredentialView.getCallback()).onDismissed(5, null);
                    break;
                }
                break;
            default:
                authCredentialView.onLockoutTimeoutFinish();
                break;
        }
    }
}
