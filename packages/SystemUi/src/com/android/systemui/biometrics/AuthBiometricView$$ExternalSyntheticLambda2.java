package com.android.systemui.biometrics;

import com.android.systemui.biometrics.AuthContainerView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class AuthBiometricView$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AuthBiometricView f$0;

    public /* synthetic */ AuthBiometricView$$ExternalSyntheticLambda2(AuthBiometricView authBiometricView, int i) {
        this.$r8$classId = i;
        this.f$0 = authBiometricView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((AuthContainerView.BiometricCallback) this.f$0.mCallback).onAction(8);
                break;
            case 1:
                ((AuthContainerView.BiometricCallback) this.f$0.mCallback).onAction(1);
                break;
            case 2:
                AuthBiometricView authBiometricView = this.f$0;
                int i = AuthBiometricView.$r8$clinit;
                authBiometricView.updateState(authBiometricView.getStateForAfterError());
                authBiometricView.handleResetAfterError();
                Utils.notifyAccessibilityContentChanged(authBiometricView.mAccessibilityManager, authBiometricView);
                break;
            case 3:
                AuthBiometricView authBiometricView2 = this.f$0;
                int i2 = AuthBiometricView.$r8$clinit;
                authBiometricView2.updateState(2);
                authBiometricView2.handleResetAfterHelp();
                Utils.notifyAccessibilityContentChanged(authBiometricView2.mAccessibilityManager, authBiometricView2);
                break;
            default:
                ((AuthContainerView.BiometricCallback) this.f$0.mCallback).onAction(5);
                break;
        }
    }
}
