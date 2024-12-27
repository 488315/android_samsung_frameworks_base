package com.android.systemui.shade.carrier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShadeCarrierGroupController$$ExternalSyntheticLambda5 {
    public final /* synthetic */ ShadeCarrierGroupController f$0;

    public final void updateCarrierInfo(final String str) {
        final ShadeCarrierGroupController shadeCarrierGroupController = this.f$0;
        shadeCarrierGroupController.getClass();
        shadeCarrierGroupController.mMainHandler.post(new Runnable() { // from class: com.android.systemui.shade.carrier.ShadeCarrierGroupController$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                ShadeCarrierGroupController shadeCarrierGroupController2 = ShadeCarrierGroupController.this;
                shadeCarrierGroupController2.mCarrierGroups[0].setCarrierText(str);
            }
        });
    }
}
