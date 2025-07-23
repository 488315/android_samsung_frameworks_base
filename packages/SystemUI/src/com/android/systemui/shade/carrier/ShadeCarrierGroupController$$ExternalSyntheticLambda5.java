package com.android.systemui.shade.carrier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class ShadeCarrierGroupController$$ExternalSyntheticLambda5 {
    public final /* synthetic */ ShadeCarrierGroupController f$0;

    public final void updateCarrierInfo(final String str) {
        final ShadeCarrierGroupController shadeCarrierGroupController = this.f$0;
        shadeCarrierGroupController.mMainHandler.post(new Runnable() { // from class: com.android.systemui.shade.carrier.ShadeCarrierGroupController$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                ShadeCarrierGroupController shadeCarrierGroupController2 = ShadeCarrierGroupController.this;
                shadeCarrierGroupController2.mCarrierGroups[0].setCarrierText(str);
            }
        });
    }
}
