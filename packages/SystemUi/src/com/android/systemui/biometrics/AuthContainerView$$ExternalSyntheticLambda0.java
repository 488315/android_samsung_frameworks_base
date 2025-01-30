package com.android.systemui.biometrics;

import com.android.systemui.biometrics.AuthContainerView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class AuthContainerView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ AuthContainerView$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                AuthContainerView authContainerView = (AuthContainerView) this.f$0;
                authContainerView.setVisibility(4);
                authContainerView.removeWindowIfAttached();
                break;
            case 1:
                ((AuthContainerView) this.f$0).animateAway(1, true);
                break;
            case 2:
                ((AuthContainerView) this.f$0).onDialogAnimatedIn();
                break;
            default:
                AuthContainerView.BiometricCallback biometricCallback = (AuthContainerView.BiometricCallback) this.f$0;
                biometricCallback.getClass();
                int i = AuthContainerView.$r8$clinit;
                biometricCallback.this$0.addCredentialView(false, true);
                break;
        }
    }
}
