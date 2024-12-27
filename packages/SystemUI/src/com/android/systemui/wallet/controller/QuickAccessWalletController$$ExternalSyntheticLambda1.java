package com.android.systemui.wallet.controller;

import android.service.quickaccesswallet.QuickAccessWalletClient;
import com.android.systemui.wallet.controller.QuickAccessWalletController;

public final /* synthetic */ class QuickAccessWalletController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ QuickAccessWalletClient.OnWalletCardsRetrievedCallback f$1;

    public /* synthetic */ QuickAccessWalletController$$ExternalSyntheticLambda1(Object obj, QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = onWalletCardsRetrievedCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                QuickAccessWalletController quickAccessWalletController = (QuickAccessWalletController) this.f$0;
                QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback = this.f$1;
                quickAccessWalletController.reCreateWalletClient();
                quickAccessWalletController.updateWalletPreference();
                quickAccessWalletController.queryWalletCards(onWalletCardsRetrievedCallback, 1);
                break;
            default:
                QuickAccessWalletController.AnonymousClass1 anonymousClass1 = (QuickAccessWalletController.AnonymousClass1) this.f$0;
                QuickAccessWalletClient.OnWalletCardsRetrievedCallback onWalletCardsRetrievedCallback2 = this.f$1;
                anonymousClass1.this$0.reCreateWalletClient();
                anonymousClass1.this$0.updateWalletPreference();
                anonymousClass1.this$0.queryWalletCards(onWalletCardsRetrievedCallback2, 1);
                break;
        }
    }
}
