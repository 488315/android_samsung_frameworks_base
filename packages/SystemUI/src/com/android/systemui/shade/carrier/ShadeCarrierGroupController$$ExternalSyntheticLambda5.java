package com.android.systemui.shade.carrier;

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
