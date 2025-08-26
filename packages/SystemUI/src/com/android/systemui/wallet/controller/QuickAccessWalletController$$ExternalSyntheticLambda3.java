package com.android.systemui.wallet.controller;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.UserHandle;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.wallet.ui.WalletActivity;

/* loaded from: classes3.dex */
public final /* synthetic */ class QuickAccessWalletController$$ExternalSyntheticLambda3 implements QuickAccessWalletClient.WalletPendingIntentCallback {
    public final /* synthetic */ QuickAccessWalletController f$0;
    public final /* synthetic */ ActivityStarter f$1;
    public final /* synthetic */ ActivityTransitionAnimator.Controller f$2;
    public final /* synthetic */ boolean f$3;

    public /* synthetic */ QuickAccessWalletController$$ExternalSyntheticLambda3(QuickAccessWalletController quickAccessWalletController, ActivityStarter activityStarter, ActivityTransitionAnimator.Controller controller, boolean z) {
        this.f$0 = quickAccessWalletController;
        this.f$1 = activityStarter;
        this.f$2 = controller;
        this.f$3 = z;
    }

    public final void onWalletPendingIntentRetrieved(PendingIntent pendingIntent) {
        QuickAccessWalletController quickAccessWalletController = this.f$0;
        ActivityStarter activityStarter = this.f$1;
        ActivityTransitionAnimator.Controller controller = this.f$2;
        boolean z = this.f$3;
        int i = QuickAccessWalletController.$r8$clinit;
        quickAccessWalletController.getClass();
        if (pendingIntent != null) {
            activityStarter.postStartActivityDismissingKeyguard(pendingIntent, controller);
            return;
        }
        Intent intentCreateWalletIntent = !z ? quickAccessWalletController.mQuickAccessWalletClient.createWalletIntent() : null;
        if (intentCreateWalletIntent == null) {
            intentCreateWalletIntent = new Intent(quickAccessWalletController.mContext, (Class<?>) WalletActivity.class).setAction("android.intent.action.VIEW");
        }
        Intent intent = intentCreateWalletIntent;
        UserHandle user = quickAccessWalletController.mQuickAccessWalletClient.getUser();
        if (z) {
            activityStarter.startActivity(intent, true, controller, true);
        } else {
            activityStarter.postStartActivityDismissingKeyguard(intent, 0, controller, null, user);
        }
    }
}
