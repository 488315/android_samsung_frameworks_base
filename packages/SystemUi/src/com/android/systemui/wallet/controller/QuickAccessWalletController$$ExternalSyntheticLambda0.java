package com.android.systemui.wallet.controller;

import android.app.PendingIntent;
import android.content.Intent;
import android.service.quickaccesswallet.QuickAccessWalletClient;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.wallet.ui.WalletActivity;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QuickAccessWalletController$$ExternalSyntheticLambda0 implements QuickAccessWalletClient.WalletPendingIntentCallback {
    public final /* synthetic */ QuickAccessWalletController f$0;
    public final /* synthetic */ ActivityStarter f$1;
    public final /* synthetic */ ActivityLaunchAnimator.Controller f$2;
    public final /* synthetic */ boolean f$3;

    public /* synthetic */ QuickAccessWalletController$$ExternalSyntheticLambda0(QuickAccessWalletController quickAccessWalletController, ActivityStarter activityStarter, ActivityLaunchAnimator.Controller controller, boolean z) {
        this.f$0 = quickAccessWalletController;
        this.f$1 = activityStarter;
        this.f$2 = controller;
        this.f$3 = z;
    }

    public final void onWalletPendingIntentRetrieved(PendingIntent pendingIntent) {
        QuickAccessWalletController quickAccessWalletController = this.f$0;
        ActivityStarter activityStarter = this.f$1;
        ActivityLaunchAnimator.Controller controller = this.f$2;
        boolean z = this.f$3;
        quickAccessWalletController.getClass();
        if (pendingIntent != null) {
            activityStarter.postStartActivityDismissingKeyguard(pendingIntent, controller);
            return;
        }
        Intent createWalletIntent = !z ? quickAccessWalletController.mQuickAccessWalletClient.createWalletIntent() : null;
        if (createWalletIntent == null) {
            createWalletIntent = new Intent(quickAccessWalletController.mContext, (Class<?>) WalletActivity.class).setAction("android.intent.action.VIEW");
        }
        if (z) {
            activityStarter.startActivity(createWalletIntent, true, controller, true);
        } else {
            activityStarter.postStartActivityDismissingKeyguard(createWalletIntent, 0, controller);
        }
    }
}
