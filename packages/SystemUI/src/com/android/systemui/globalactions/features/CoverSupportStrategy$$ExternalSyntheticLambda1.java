package com.android.systemui.globalactions.features;

import com.samsung.android.globalactions.presentation.viewmodel.ActionViewModel;
import com.samsung.android.globalactions.util.SystemConditions;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class CoverSupportStrategy$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ CoverSupportStrategy f$0;
    public final /* synthetic */ ActionViewModel f$1;
    public final /* synthetic */ String f$2;

    public /* synthetic */ CoverSupportStrategy$$ExternalSyntheticLambda1(CoverSupportStrategy coverSupportStrategy, ActionViewModel actionViewModel, String str) {
        this.f$0 = coverSupportStrategy;
        this.f$1 = actionViewModel;
        this.f$2 = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        CoverSupportStrategy coverSupportStrategy = this.f$0;
        ActionViewModel actionViewModel = this.f$1;
        String str = this.f$2;
        coverSupportStrategy.mKeyGuardManagerWrapper.setRegisterState(false);
        coverSupportStrategy.mGlobalActions.registerSecureConfirmAction(actionViewModel);
        if (coverSupportStrategy.mConditionChecker.isEnabled(SystemConditions.IS_SECURE_KEYGUARD)) {
            coverSupportStrategy.mKeyGuardManagerWrapper.setPendingIntentAfterUnlock(str);
        }
    }
}
