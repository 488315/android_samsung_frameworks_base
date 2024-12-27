package com.android.systemui.biometrics;

import com.android.internal.hidden_from_bootclasspath.android.hardware.biometrics.Flags;
import com.android.systemui.biometrics.AuthContainerView;
import com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractor;
import com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl;

public final /* synthetic */ class AuthContainerView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AuthContainerView f$0;

    public /* synthetic */ AuthContainerView$$ExternalSyntheticLambda0(AuthContainerView authContainerView, int i) {
        this.$r8$classId = i;
        this.f$0 = authContainerView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        AuthContainerView authContainerView = this.f$0;
        switch (i) {
            case 0:
                authContainerView.setVisibility(4);
                if (Flags.customBiometricPrompt()) {
                    com.android.systemui.Flags.constraintBp();
                    ((PromptSelectorInteractorImpl) ((PromptSelectorInteractor) authContainerView.mPromptSelectorInteractorProvider.get())).resetPrompt(authContainerView.mConfig.mRequestId);
                }
                authContainerView.removeWindowIfAttached();
                break;
            case 1:
                authContainerView.animate().alpha(1.0f).translationY(0.0f).setDuration(250L).setInterpolator(authContainerView.mLinearOutSlowIn).withLayer().setListener(new AuthContainerView.AnonymousClass2(authContainerView, authContainerView, "show", 250L)).withEndAction(new AuthContainerView$$ExternalSyntheticLambda0(authContainerView, 2)).start();
                break;
            default:
                authContainerView.onDialogAnimatedIn();
                break;
        }
    }
}
