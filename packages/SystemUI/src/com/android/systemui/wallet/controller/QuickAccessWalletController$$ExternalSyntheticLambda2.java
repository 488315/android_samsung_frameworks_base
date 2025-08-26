package com.android.systemui.wallet.controller;

import android.app.PendingIntent;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.plugins.ActivityStarter;

/* loaded from: classes3.dex */
public final /* synthetic */ class QuickAccessWalletController$$ExternalSyntheticLambda2 implements QuickAccessWalletClient.GesturePendingIntentCallback {
    public final /* synthetic */ QuickAccessWalletController f$0;
    public final /* synthetic */ ActivityStarter f$1;

    public /* synthetic */ QuickAccessWalletController$$ExternalSyntheticLambda2(QuickAccessWalletController quickAccessWalletController, ActivityStarter activityStarter) {
        this.f$0 = quickAccessWalletController;
        this.f$1 = activityStarter;
    }

    public final void onGesturePendingIntentRetrieved(PendingIntent pendingIntent) {
        QuickAccessWalletController quickAccessWalletController = this.f$0;
        ActivityStarter activityStarter = this.f$1;
        int i = QuickAccessWalletController.$r8$clinit;
        ActivityTransitionAnimator.Controller controller = null;
        if (pendingIntent == null) {
            quickAccessWalletController.mQuickAccessWalletClient.getWalletPendingIntent(quickAccessWalletController.mExecutor, new QuickAccessWalletController$$ExternalSyntheticLambda3(quickAccessWalletController, activityStarter, controller, true));
        } else {
            quickAccessWalletController.getClass();
            activityStarter.startPendingIntentMaybeDismissingKeyguard(pendingIntent, null, null);
        }
    }
}
