package com.android.systemui.qp.flashlight;

import com.android.systemui.qp.SubscreenQSControllerContract$FlashLightView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SubscreenFlashLightController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SubscreenFlashLightController f$0;

    public /* synthetic */ SubscreenFlashLightController$$ExternalSyntheticLambda1(SubscreenFlashLightController subscreenFlashLightController, int i) {
        this.$r8$classId = i;
        this.f$0 = subscreenFlashLightController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.finishFlashLightActivity();
                break;
            case 1:
                SubscreenQSControllerContract$FlashLightView subscreenQSControllerContract$FlashLightView = this.f$0.mFlashLightPresentationView;
                if (subscreenQSControllerContract$FlashLightView != null) {
                    ((SubroomFlashLightSettingsActivity) subscreenQSControllerContract$FlashLightView).showTurnOffView();
                    break;
                }
                break;
            default:
                this.f$0.finishFlashLightActivity();
                break;
        }
    }
}
