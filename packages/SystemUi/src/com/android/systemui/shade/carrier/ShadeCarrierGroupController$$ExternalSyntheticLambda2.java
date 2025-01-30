package com.android.systemui.shade.carrier;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShadeCarrierGroupController$$ExternalSyntheticLambda2 {
    public final /* synthetic */ ShadeCarrierGroupController f$0;

    public final void updateCarrierInfo(final String str) {
        final ShadeCarrierGroupController shadeCarrierGroupController = this.f$0;
        shadeCarrierGroupController.getClass();
        shadeCarrierGroupController.mMainHandler.post(new Runnable() { // from class: com.android.systemui.shade.carrier.ShadeCarrierGroupController$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                ShadeCarrierGroupController shadeCarrierGroupController2 = ShadeCarrierGroupController.this;
                shadeCarrierGroupController2.mCarrierGroups[0].setCarrierText(str);
            }
        });
    }
}
