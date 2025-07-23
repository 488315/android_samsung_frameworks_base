package com.android.systemui.biometrics;

import com.android.systemui.biometrics.AuthContainerView;
import com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractor;
import com.android.systemui.biometrics.domain.interactor.PromptSelectorInteractorImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class AuthContainerView$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AuthContainerView f$0;

    public /* synthetic */ AuthContainerView$$ExternalSyntheticLambda0(AuthContainerView authContainerView, int i) {
        this.$r8$classId = i;
        this.f$0 = authContainerView;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                AuthContainerView authContainerView = this.f$0;
                int i = AuthContainerView.$r8$clinit;
                authContainerView.setVisibility(4);
                ((PromptSelectorInteractorImpl) ((PromptSelectorInteractor) authContainerView.mPromptSelectorInteractorProvider.get())).resetPrompt(authContainerView.mConfig.mRequestId);
                authContainerView.removeWindowIfAttached();
                break;
            case 1:
                AuthContainerView authContainerView2 = this.f$0;
                int i2 = AuthContainerView.$r8$clinit;
                authContainerView2.animate().alpha(1.0f).translationY(0.0f).setDuration(250L).setInterpolator(authContainerView2.mLinearOutSlowIn).withLayer().setListener(new AuthContainerView.AnonymousClass1(authContainerView2, authContainerView2, "show", 250L)).withEndAction(new AuthContainerView$$ExternalSyntheticLambda0(authContainerView2, 2)).start();
                break;
            default:
                AuthContainerView authContainerView3 = this.f$0;
                int i3 = AuthContainerView.$r8$clinit;
                authContainerView3.onDialogAnimatedIn();
                break;
        }
    }
}
