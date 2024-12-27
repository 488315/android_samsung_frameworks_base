package com.android.systemui.qp.flashlight;

import com.android.systemui.qp.SubscreenQSControllerContract$FlashLightView;

public final /* synthetic */ class SubscreenFlashLightController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SubscreenFlashLightController f$0;

    public /* synthetic */ SubscreenFlashLightController$$ExternalSyntheticLambda0(SubscreenFlashLightController subscreenFlashLightController, int i) {
        this.$r8$classId = i;
        this.f$0 = subscreenFlashLightController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        SubscreenFlashLightController subscreenFlashLightController = this.f$0;
        switch (i) {
            case 0:
                SubscreenQSControllerContract$FlashLightView subscreenQSControllerContract$FlashLightView = subscreenFlashLightController.mFlashLightPresentationView;
                if (subscreenQSControllerContract$FlashLightView != null) {
                    ((SubroomFlashLightSettingsActivity) subscreenQSControllerContract$FlashLightView).showTurnOffView();
                    break;
                }
                break;
            case 1:
                subscreenFlashLightController.finishFlashLightActivity();
                break;
            default:
                subscreenFlashLightController.finishFlashLightActivity();
                break;
        }
    }
}
